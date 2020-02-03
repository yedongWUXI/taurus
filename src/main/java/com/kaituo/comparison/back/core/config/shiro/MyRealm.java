package com.kaituo.comparison.back.core.config.shiro;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaituo.comparison.back.common.exception.RequestException;
import com.kaituo.comparison.back.common.util.JwtUtil;
import com.kaituo.comparison.back.core.config.jwt.JwtToken;
import com.kaituo.comparison.back.core.entity.system.SysUser;
import com.kaituo.comparison.back.core.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 *
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("Shiro权限验证执行");
        JwtToken jwtToken = new JwtToken();
        BeanUtils.copyProperties(principalCollection.getPrimaryPrincipal(),jwtToken);
        if(jwtToken.getUsername()!=null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            SysUser findUser = userService.findUserByName(jwtToken.getUsername(),true);
            if(findUser!=null){
                if(findUser.getRoles()!=null){
                    findUser.getRoles().forEach(role->{
                        info.addRole(role.getName());
                        if(role.getResources()!=null){
                            role.getResources().forEach(v->{
                                if(!"".equals(v.getPermission().trim())){
                                    info.addStringPermission(v.getPermission());
                                }
                            });
                        }
                    });
                }
                return info;
            }
        }
        throw new DisabledAccountException("用户信息异常，请重新登录！");
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken token  = (JwtToken) authenticationToken;
        SysUser user;
        String username = token.getUsername()!=null ? token.getUsername() : JwtUtil.getUsername(token.getToken());
        try {
            user = userService.getOne(new QueryWrapper<SysUser>()
                    .eq("username", username))
            ;
        }catch (RequestException e){
            throw new DisabledAccountException(e.getMsg());
        }
        if(user==null){
            throw new DisabledAccountException("用户不存在！");
        }
        if(user.getStatus()!=1){
            throw new DisabledAccountException("用户账户已锁定，暂无法登陆！");
        }
        if(token.getUsername()==null) token.setUsername(user.getUsername());
        String sign = JwtUtil.sign(user.getId(), user.getUsername(), user.getPassword());
        if(token.getToken()==null) token.setToken(sign);
        token.setUid(user.getId());
        return new SimpleAuthenticationInfo(token,user.getPassword(),user.getId());
    }

    public void clearAuthByUserId(String uid,Boolean author, Boolean out){
        //获取所有session
        Cache<Object, Object> cache = cacheManager
                .getCache(MyRealm.class.getName()+".authorizationCache");
        cache.remove(uid);
    }

    public void clearAuthByUserIdCollection(List<String> userList,Boolean author, Boolean out){
        Cache<Object, Object> cache = cacheManager
                .getCache(MyRealm.class.getName()+".authorizationCache");
        userList.forEach(cache::remove);
    }
}
