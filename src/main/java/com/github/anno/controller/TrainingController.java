package com.github.anno.controller;

import com.github.anno.entity.Training;
import com.github.anno.service.BaseService;
import com.github.anno.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 培训信息 接口控制类
 *
 * @author zhuyifa
 * @version 2020-11-22
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TrainingController extends BaseController<Training> {

    private final TrainingService trainingService;

    @Override
    public BaseService<Training> getService() {
        return trainingService;
    }

}
