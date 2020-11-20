package com.github.anno.service.impl;

import com.github.anno.entity.Refueling;
import com.github.anno.repository.BaseRepository;
import com.github.anno.repository.RefuelingRepository;
import com.github.anno.service.RefuelingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 加油记录 服务接口 实现类
 *
 * @author zhuyifa
 * @since 2019-12-20
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RefuelingServiceImpl implements RefuelingService {

    private final RefuelingRepository refuelingRepository;

    @Override
    public BaseRepository<Refueling> getRepository() {
        return refuelingRepository;
    }

}
