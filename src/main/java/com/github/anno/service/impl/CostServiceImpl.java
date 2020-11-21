package com.github.anno.service.impl;

import com.github.anno.entity.Cost;
import com.github.anno.repository.BaseRepository;
import com.github.anno.repository.CostRepository;
import com.github.anno.service.CostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 费用记录 服务接口 实现类
 *
 * @author zhuyifa
 * @since 2019-11-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CostServiceImpl implements CostService {

    private final CostRepository costRepository;

    @Override
    public BaseRepository<Cost> getRepository() {
        return costRepository;
    }

}
