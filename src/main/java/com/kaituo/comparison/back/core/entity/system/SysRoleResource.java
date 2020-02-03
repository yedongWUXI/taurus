package com.kaituo.comparison.back.core.entity.system;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @version 2018/4/16/8:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysRoleResource implements Serializable {

    @TableId
    private String id;

    private String rid;

    private String pid;

    private static final long serialVersionUID = 1L;
}
