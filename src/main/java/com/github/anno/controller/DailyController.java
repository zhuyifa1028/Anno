package com.github.anno.controller;

import com.github.anno.entity.Daily;
import com.github.anno.service.BaseService;
import com.github.anno.service.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 日常记录 接口控制类
 *
 * @author zhuyifa
 * @version 2020-11-20
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DailyController extends BaseController<Daily> {

    private final DailyService dailyService;

    @Override
    public BaseService<Daily> getService() {
        return dailyService;
    }

}
