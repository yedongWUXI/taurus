package com.kaituo.comparison.back.core.controller.system;

import com.kaituo.comparison.back.common.controller.CrudController;
import com.kaituo.comparison.back.core.dto.system.role.FindRoleDTO;
import com.kaituo.comparison.back.core.dto.system.role.RoleAddDTO;
import com.kaituo.comparison.back.core.dto.system.role.RoleUpdateDTO;
import com.kaituo.comparison.back.core.entity.system.SysRole;
import com.kaituo.comparison.back.core.service.system.SysRoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 *
 * @version 2018/4/19/9:41
 */
@RestController
@RequestMapping(value = {"/system/role"})
@Api(tags = {"角色管理"})
public class RoleController implements CrudController<SysRole,RoleAddDTO,RoleUpdateDTO,String,FindRoleDTO,SysRoleService> {

    private final SysRoleService sysRoleService;

    @Autowired
    public RoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override
    public SysRoleService getService() {
        return sysRoleService;
    }
}
