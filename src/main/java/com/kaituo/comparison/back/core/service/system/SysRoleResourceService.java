package com.kaituo.comparison.back.core.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaituo.comparison.back.core.entity.system.SysResource;
import com.kaituo.comparison.back.core.entity.system.SysRoleResource;

import java.util.List;

/**
 *
 * @version 2018/4/16/9:01
 */
public interface SysRoleResourceService extends IService<SysRoleResource> {

    List<SysResource> findAllResourceByRoleId(String rid);

}
