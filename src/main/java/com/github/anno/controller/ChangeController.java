package com.github.anno.controller;

import com.github.anno.entity.Change;
import com.github.anno.service.BaseService;
import com.github.anno.service.ChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 换车记录 接口控制类
 *
 * @author zhuyifa
 * @version 2020-11-21
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ChangeController extends BaseController<Change> {

    private final ChangeService changeService;

    @Override
    public BaseService<Change> getService() {
        return changeService;
    }

}
