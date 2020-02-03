package com.kaituo.comparison.back.core.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaituo.comparison.back.common.exception.RequestException;
import com.kaituo.comparison.back.core.dto.system.resource.ResourceDTO;
import com.kaituo.comparison.back.core.entity.system.SysResource;
import com.kaituo.comparison.back.core.mapper.system.SysResourceMapper;
import com.kaituo.comparison.back.core.service.global.ShiroService;
import com.kaituo.comparison.back.core.service.system.SysResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @version 2018/4/20/16:36
 */
@Service
@Transactional
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper,SysResource>
        implements SysResourceService {

    @Autowired
    private ShiroService shiroService;

    @Override
    public List<SysResource> list() {
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0)
                .or()
                .isNull("parent_id")
                .orderBy(true, true, "sort");
        List<SysResource> resources = this.list(wrapper);
        if(resources!=null && resources.size()>0){
            resources.forEach(this::findAllChild);
        }
        return resources;
    }

    @Override
    public void add(ResourceDTO dto) {
        SysResource resource = new SysResource();
        BeanUtils.copyProperties(dto,resource);
        resource.setCreateDate(new Date());
        this.save(resource);
        shiroService.reloadPerms();
    }

    @Override
    public void update(String id, ResourceDTO dto) {
        SysResource resource = this.getById(id);
        if(resource==null)
            throw RequestException.fail("更新失败，不存在ID为"+id+"的资源");
        BeanUtils.copyProperties(dto,resource);
        this.updateById(resource);
        shiroService.reloadPerms();
    }

    @Override
    public void remove(String id) {
        SysResource resource = this.getOne(new QueryWrapper<SysResource>()
                .eq("id", id));
        if(resource==null)
            throw RequestException.fail("删除失败，不存在ID为"+id+"的资源");
        this.removeById(id);
        shiroService.reloadPerms();
    }

    public void findAllChild(SysResource resource){
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", resource.getId()).orderBy(true, true, "sort");
        List<SysResource> resources = this.list(wrapper);
        resource.setChildren(resources);
        if(resources!=null && resources.size()>0){
            resources.forEach(this::findAllChild);
        }
    }

    public SysResource getResourceAllParent(SysResource resource,Map<String,SysResource> cacheMap,
                                            Map<String,SysResource> cacheMap2){
        if(resource.getParentId()!=null && !"".equals(resource.getParentId().trim())){
            SysResource cacheParent = cacheMap.get(resource.getParentId());
            SysResource parent;
            if(cacheParent!=null){
                parent = cacheParent;
            }else{
                cacheParent = cacheMap2.get(resource.getParentId());
                if(cacheParent!=null){
                    parent = cacheParent;
                }else{
                    parent = this.getById(resource.getParentId());
                }
            }
            if(parent!=null){
                if(parent.getChildren()==null){
                    parent.setChildren(new ArrayList<>());
                }
                //判断是否已经包含此对象
                if(!parent.getChildren().contains(resource)){
                    parent.getChildren().add(resource);
                }
                cacheMap.put(resource.getParentId(),parent);
                //如果此父级还有父级，继续递归查询
                if(parent.getParentId()!=null && !"".equals(parent.getParentId())){
                    return getResourceAllParent(parent,cacheMap,cacheMap2);
                }else{
                    return parent;
                }
            }
        }
        return resource;
    }
}
