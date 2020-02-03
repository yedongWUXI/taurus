package com.kaituo.comparison.back.common.controller;

import com.kaituo.comparison.back.common.service.BaseService;

/**
 *
 * @param <E> 控制器对象实体
 * @param <AD> 添加DTO
 * @param <UD> 更新DTO
 * @param <UID> 对象ID
 * @param <FD> 查找DTO
 * @param <S> 服务对象
 * <span> 此控制器只是针对于一般的操作设计，若需求不一样可自行定制 </span>
 */
public interface CrudController<E,AD,UD,UID,FD,S extends BaseService<E,AD,UD,UID,FD>> extends
        CreateController<AD,S>,
        DeleteController<UID,S>,
        QueryController<E,FD,S>,
        UpdateController<UID,UD,S>

{











}
