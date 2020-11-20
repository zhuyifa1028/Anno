package com.github.anno.service.impl;

import com.github.anno.entity.Daily;
import com.github.anno.repository.BaseRepository;
import com.github.anno.repository.DailyRepository;
import com.github.anno.service.DailyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日常记录 服务接口 实现类
 *
 * @author zhuyifa
 * @since 2019-12-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DailyServiceImpl implements DailyService {

    private final DailyRepository dailyRepository;

    @Override
    public BaseRepository<Daily> getRepository() {
        return dailyRepository;
    }

}
