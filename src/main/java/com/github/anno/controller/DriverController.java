package com.github.anno.controller;

import com.github.anno.entity.Driver;
import com.github.anno.service.BaseService;
import com.github.anno.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 司机接口控制类
 *
 * @author zhuyifa
 * @version 2020-11-16
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DriverController extends BaseController<Driver> {

    private final DriverService driverService;

    @Override
    public BaseService<Driver> getService() {
        return driverService;
    }

}
