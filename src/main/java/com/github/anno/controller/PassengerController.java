package com.github.anno.controller;

import com.github.anno.entity.Passenger;
import com.github.anno.service.BaseService;
import com.github.anno.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 乘车人接口控制类
 *
 * @author zhuyifa
 * @version 2020-11-18
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PassengerController extends BaseController<Passenger> {

    private final PassengerService passengerService;

    @Override
    BaseService<Passenger> getService() {
        return passengerService;
    }

}
