package com.kaituo.comparison.back.core.vo.system;

import com.kaituo.comparison.back.core.entity.system.SysResource;
import com.kaituo.comparison.back.core.entity.system.SysRole;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *
 * @version 2018/4/18/11:34
 */
@Data
public class SysUserVO {

    private String id;
    private String username;
    private Integer age;
    private Integer status;
    private List<SysRole> roles;
    private Date createDate;
    private List<SysResource> resources;

}
