package com.github.anno.controller;

import com.github.anno.entity.Refueling;
import com.github.anno.service.BaseService;
import com.github.anno.service.RefuelingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 加油记录 接口控制类
 *
 * @author zhuyifa
 * @version 2020-11-20
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RefuelingController extends BaseController<Refueling> {

    private final RefuelingService refuelingService;

    @Override
    public BaseService<Refueling> getService() {
        return refuelingService;
    }

}
