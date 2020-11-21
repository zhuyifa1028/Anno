package com.github.anno.service.impl;

import com.github.anno.entity.Change;
import com.github.anno.repository.BaseRepository;
import com.github.anno.repository.ChangeRepository;
import com.github.anno.service.ChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 换车记录 服务接口 实现类
 *
 * @author zhuyifa
 * @since 2019-11-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ChangeServiceImpl implements ChangeService {

    private final ChangeRepository changeRepository;

    @Override
    public BaseRepository<Change> getRepository() {
        return changeRepository;
    }

}
