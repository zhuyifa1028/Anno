package com.github.anno.service;

import com.github.anno.entity.Base;
import com.github.anno.repository.BaseRepository;

/**
 * 基础服务接口
 *
 * @author zhuyifa
 * @version 2020-11-15
 */
public interface BaseService<T extends Base> {

    /**
     * 获取数据接口
     *
     * @return 数据接口
     */
    BaseRepository<T> getRepository();

}
