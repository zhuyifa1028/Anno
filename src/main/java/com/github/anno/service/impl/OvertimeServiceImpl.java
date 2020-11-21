package com.github.anno.service.impl;

import com.github.anno.entity.Overtime;
import com.github.anno.repository.BaseRepository;
import com.github.anno.repository.OvertimeRepository;
import com.github.anno.service.OvertimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 加班记录 服务接口 实现类
 *
 * @author zhuyifa
 * @since 2019-12-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OvertimeServiceImpl implements OvertimeService {

    private final OvertimeRepository overtimeRepository;

    @Override
    public BaseRepository<Overtime> getRepository() {
        return overtimeRepository;
    }

}
