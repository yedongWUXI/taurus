package com.kaituo.comparison.back.core.service.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kaituo.comparison.back.common.service.QueryService;
import com.kaituo.comparison.back.core.dto.system.log.FindLogDTO;
import com.kaituo.comparison.back.core.entity.system.SysLog;

import java.util.List;

/**
 *
 * @version 2018/4/28/9:56
 */
public interface SysLogService extends IService<SysLog>,QueryService<SysLog,FindLogDTO>
{

    Page<SysLog> list(FindLogDTO findLogDTO);

    void remove(List<String> idList);

}
