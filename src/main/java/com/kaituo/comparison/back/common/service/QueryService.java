package com.kaituo.comparison.back.common.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 *
 * @see BaseService 注释配置请参见BaseService
 */
public interface QueryService<E,FD> {

    Page<E> list(FD findDTO);

}
