package com.kaituo.comparison.back.core.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaituo.comparison.back.common.bean.ResponseCode;
import com.kaituo.comparison.back.common.exception.RequestException;
import com.kaituo.comparison.back.core.dto.system.log.FindLogDTO;
import com.kaituo.comparison.back.core.entity.system.SysLog;
import com.kaituo.comparison.back.core.mapper.system.SysLogMapper;
import com.kaituo.comparison.back.core.service.system.SysLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @version 2018/4/28/9:57
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper,SysLog> implements SysLogService {

    @Override
    public Page<SysLog> list(FindLogDTO findLogDTO) {
        QueryWrapper<SysLog> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, findLogDTO.getAsc(), "create_date");

        return (Page<SysLog>) this.page(new Page<>(findLogDTO.getPage(), findLogDTO.getPageSize()), wrapper);
    }

    @Override
    public void remove(List<String> idList) {
        try {
            this.removeByIds(idList);
        }catch (Exception e){
            throw new RequestException(ResponseCode.FAIL.code,"批量删除日志失败",e);
        }
    }
}
