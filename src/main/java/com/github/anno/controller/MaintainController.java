package com.github.anno.controller;

import com.github.anno.entity.Maintain;
import com.github.anno.service.BaseService;
import com.github.anno.service.MaintainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 例行保养 接口控制类
 *
 * @author zhuyifa
 * @version 2020-11-19
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MaintainController extends BaseController<Maintain> {

    private final MaintainService maintainService;

    @Override
    BaseService<Maintain> getService() {
        return maintainService;
    }

}
