package com.github.anno.controller;

import com.github.anno.entity.Cost;
import com.github.anno.service.BaseService;
import com.github.anno.service.CostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 费用记录 接口控制类
 *
 * @author zhuyifa
 * @version 2020-11-21
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CostController extends BaseController<Cost> {

    private final CostService costService;

    @Override
    public BaseService<Cost> getService() {
        return costService;
    }

}
