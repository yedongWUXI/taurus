package com.kaituo.comparison.back.core.config.shiro;


import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 *
 *
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
//        JwtToken jwtToken = (JwtToken) token;
//        Object accountCredentials = getCredentials(info);
//        if(jwtToken.getPassword()!=null){
//            Object tokenCredentials = MD5EncryptUtil.encrypt(String.valueOf(
//                    jwtToken.getPassword())+jwtToken.getUsername());
//            if(!accountCredentials.equals(tokenCredentials)){
//                throw new DisabledAccountException("密码不正确！");
//            }
//        }else{
//            boolean verify = JwtUtil.verify(jwtToken.getToken(), jwtToken.getUsername(), accountCredentials.toString());
//            if(!verify){
//                throw new DisabledAccountException("verifyFail");
//            }
//        }
        return true;
    }

}
