package com.github.anno.service.impl;

import com.github.anno.entity.Training;
import com.github.anno.repository.BaseRepository;
import com.github.anno.repository.TrainingRepository;
import com.github.anno.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 培训信息 服务接口 实现类
 *
 * @author zhuyifa
 * @since 2019-12-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    @Override
    public BaseRepository<Training> getRepository() {
        return trainingRepository;
    }

}
