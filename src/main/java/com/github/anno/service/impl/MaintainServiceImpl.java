package com.github.anno.service.impl;

import com.github.anno.entity.Maintain;
import com.github.anno.repository.BaseRepository;
import com.github.anno.repository.MaintainRepository;
import com.github.anno.service.MaintainService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 例行保养 服务接口 实现类
 *
 * @author zhuyifa
 * @since 2019-12-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MaintainServiceImpl implements MaintainService {

    private final MaintainRepository maintainRepository;

    @Override
    public BaseRepository<Maintain> getRepository() {
        return maintainRepository;
    }

}
