package com.github.anno.service.impl;

import com.github.anno.entity.Driver;
import com.github.anno.repository.BaseRepository;
import com.github.anno.repository.DriverRepository;
import com.github.anno.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 司机服务接口实现类
 *
 * @author zhuyifa
 * @since 2019-12-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Override
    public BaseRepository<Driver> getRepository() {
        return driverRepository;
    }

}
