package com.kaituo.comparison.back.core.service.system.impl;

import com.kaituo.comparison.back.core.entity.system.SysRole;
import com.kaituo.comparison.back.core.service.system.SysRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




/**
 *
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceImplTest {

    @Autowired
    private SysRoleService roleService;

    @Test
    public void test(){
        SysRole role = SysRole.builder().name("1").build();
        roleService.save(role);
    }

}