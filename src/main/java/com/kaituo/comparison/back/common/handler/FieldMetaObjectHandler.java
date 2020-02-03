/**
 *
 *
 *
 *
 *
 */

package com.kaituo.comparison.back.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.kaituo.comparison.back.core.config.jwt.JwtToken;
import com.kaituo.comparison.back.core.config.shiro.SecurityUser;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 公共字段，自动填充值
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_DATE = "createDate";
    private final static String CREATOR = "creator";
    private final static String UPDATE_DATE = "updateDate";
    private final static String UPDATER = "updater";

    @Override
    public void insertFill(MetaObject metaObject) {
        JwtToken user = SecurityUser.getUser();
        Date date = new Date();

        //创建者
        setFieldValByName(CREATOR, user.getUid(), metaObject);
        //创建时间
        setFieldValByName(CREATE_DATE, date, metaObject);

        //更新者
        setFieldValByName(UPDATER, user.getUid(), metaObject);
        //更新时间
        setFieldValByName(UPDATE_DATE, date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新者
        setFieldValByName(UPDATER, SecurityUser.getUserId(), metaObject);
        //更新时间
        setFieldValByName(UPDATE_DATE, new Date(), metaObject);
    }
}