package com.kaituo.comparison.back.core.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaituo.comparison.back.core.entity.system.SysResource;
import com.kaituo.comparison.back.core.entity.system.SysRoleResource;
import com.kaituo.comparison.back.core.mapper.system.SysRolePermissionMapper;
import com.kaituo.comparison.back.core.service.system.SysResourceService;
import com.kaituo.comparison.back.core.service.system.SysRoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @version 2018/4/16/9:01
 */
@Service
@Transactional
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRolePermissionMapper,SysRoleResource>
        implements SysRoleResourceService {

    @Autowired
    private SysResourceService resourceService;

    @Override
    public List<SysResource> findAllResourceByRoleId(String rid) {
        List<SysRoleResource> rps = this.list(new QueryWrapper<SysRoleResource>().eq("rid", rid));
        if(rps!=null){
            List<String> pids = new ArrayList<>();
            rps.forEach(v->pids.add(v.getPid()));
            if(pids.size()==0){
                return null;
            }
            return resourceService.list(new QueryWrapper<SysResource>()
                    .in("id", pids)
                    .orderBy(true, true, "sort")
            );
        }
        return null;
    }
}
