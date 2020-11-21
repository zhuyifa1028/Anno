package com.github.anno.controller;

import com.github.anno.entity.Overtime;
import com.github.anno.service.BaseService;
import com.github.anno.service.OvertimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 加班记录 接口控制类
 *
 * @author zhuyifa
 * @version 2020-11-21
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OvertimeController extends BaseController<Overtime> {

    private final OvertimeService overtimeService;

    @Override
    public BaseService<Overtime> getService() {
        return overtimeService;
    }

}
