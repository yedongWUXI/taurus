package com.kaituo.comparison.back.core.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaituo.comparison.back.common.exception.RequestException;
import com.kaituo.comparison.back.core.dto.system.role.FindRoleDTO;
import com.kaituo.comparison.back.core.dto.system.role.RoleAddDTO;
import com.kaituo.comparison.back.core.dto.system.role.RoleUpdateDTO;
import com.kaituo.comparison.back.core.entity.system.SysResource;
import com.kaituo.comparison.back.core.entity.system.SysRole;
import com.kaituo.comparison.back.core.entity.system.SysRoleResource;
import com.kaituo.comparison.back.core.entity.system.SysUserRole;
import com.kaituo.comparison.back.core.mapper.system.SysRoleMapper;
import com.kaituo.comparison.back.core.service.global.ShiroService;
import com.kaituo.comparison.back.core.service.system.SysRoleResourceService;
import com.kaituo.comparison.back.core.service.system.SysRoleService;
import com.kaituo.comparison.back.core.service.system.SysUserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {

    @Autowired
    private SysRoleResourceService roleResourceService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private ShiroService shiroService;

    @Override
    public List<SysRole> findAllRoleByUserId(String uid,Boolean hasResource) {
        List<SysUserRole> userRoles = userRoleService.list(new QueryWrapper<SysUserRole>().eq("uid", uid));
        List<SysRole> roles = new ArrayList<>();
        userRoles.forEach(v->{
            SysRole role = this.getById(v.getRid());
            if(role!=null){
                if(hasResource){
                    List<SysResource> permissions = roleResourceService.findAllResourceByRoleId(role.getId());
                    role.setResources(permissions);
                }
            }
            roles.add(role);
        });
        return roles;
    }

    @Override
    public Page<SysRole> list(FindRoleDTO findRoleDTO) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, findRoleDTO.getAsc(), "id");
        Page<SysRole> rolePage = (Page<SysRole>) this.page(new Page<>(findRoleDTO.getPage(),
                findRoleDTO.getPageSize()), wrapper);
        if(findRoleDTO.getHasResource()){
            if(rolePage.getRecords()!=null){
                rolePage.getRecords().forEach(v->
                        v.setResources(roleResourceService.findAllResourceByRoleId(v.getId())));
            }
        }
        return rolePage;
    }

    @Override
    public void remove(String rid) {
        SysRole role = this.getById(rid);
        if(role==null) throw RequestException.fail("角色不存在！");
        try {
            this.removeById(rid);
            this.updateCache(role,true,false);
        }catch (DataIntegrityViolationException e){
            throw RequestException.fail(
                    String.format("请先解除角色为 %s 角色的全部用户！",role.getName()),e);
        }catch (Exception e){
            throw RequestException.fail("角色删除失败！",e);
        }
    }

    @Override
    public void update(String rid, RoleUpdateDTO roleUpdateDTO) {
        SysRole role = this.getById(rid);
        if(role==null) throw RequestException.fail("角色不存在！");
        BeanUtils.copyProperties(roleUpdateDTO,role);
        try {
            this.updateById(role);
            roleResourceService.remove(new QueryWrapper<SysRoleResource>()
                    .eq("rid",rid));
            for (SysResource sysResource : roleUpdateDTO.getResources()) {
                roleResourceService.save(SysRoleResource.builder()
                        .pid(sysResource.getId())
                        .rid(role.getId())
                        .build());
            }
            this.updateCache(role,true,false);
        }catch (Exception e){
            throw RequestException.fail("角色更新失败！",e);
        }

    }

    @Override
    public void add(RoleAddDTO addDTO) {
        SysRole role = this.getOne(new QueryWrapper<SysRole>().eq("name", addDTO.getName()));
        if(role!=null){
            throw RequestException.fail(
                    String.format("已经存在名称为 %s 的角色",addDTO.getName()));
        }
        role = new SysRole();
        BeanUtils.copyProperties(addDTO,role);
        try {
            this.save(role);
            for (SysResource sysResource : addDTO.getResources()) {
                roleResourceService.save(SysRoleResource.builder()
                        .pid(sysResource.getId())
                        .rid(role.getId())
                        .build());
            }
        }catch (Exception e){
            throw RequestException.fail("添加失败",e);
        }
    }

    @Override
    public void updateCache(SysRole role,Boolean author, Boolean out) {
        List<SysUserRole> sysUserRoles = userRoleService.list(new QueryWrapper<SysUserRole>()
                .eq("rid", role.getId())
                .groupBy("uid"));
        List<String> userIdList = new ArrayList<>();
        if(sysUserRoles!=null && sysUserRoles.size()>0){
            sysUserRoles.forEach(v-> userIdList.add(v.getUid()));
        }
        shiroService.clearAuthByUserIdCollection(userIdList,author,out);
    }
}
