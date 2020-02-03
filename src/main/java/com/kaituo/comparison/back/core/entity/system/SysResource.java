package com.kaituo.comparison.back.core.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @version 2018/4/20/16:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysResource implements Serializable {

    @TableId
    private String id;

    private String name;

    private String parentId;

    private Short type;

    private String url;

    private String permission;

    private String color;

    private String icon;

    private Long sort;

    private Boolean verification;

    private Date createDate;

    @TableField(exist = false)
    private List<SysResource> children;

}
