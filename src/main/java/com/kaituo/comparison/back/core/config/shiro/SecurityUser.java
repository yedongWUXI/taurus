/**
 *
 *
 *
 *
 *
 */

package com.kaituo.comparison.back.core.config.shiro;

import com.kaituo.comparison.back.core.config.jwt.JwtToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public class SecurityUser {

    public static Subject getSubject() {
        try {
            return SecurityUtils.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户信息
     */
    public static JwtToken getUser() {
        Subject subject = getSubject();
        if (subject == null) {
            return new JwtToken();
        }

        JwtToken user = (JwtToken) subject.getPrincipal();
        if (user == null) {
            return new JwtToken();
        }

        return user;
    }

    /**
     * 获取用户ID
     */
    public static String getUserId() {
        return getUser().getUid();
    }

}