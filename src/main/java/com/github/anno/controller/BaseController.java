package com.github.anno.controller;

import com.github.anno.entity.Base;
import com.github.anno.service.BaseService;

/**
 * 基础接口控制类
 *
 * @author zhuyifa
 * @version 2020-11-16
 */
public abstract class BaseController<T extends Base> {

    /**
     * 获取服务接口
     *
     * @return 服务接口
     */
    abstract BaseService<T> getService();

}
