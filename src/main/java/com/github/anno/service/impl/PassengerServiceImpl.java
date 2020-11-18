package com.github.anno.service.impl;

import com.github.anno.entity.Passenger;
import com.github.anno.repository.BaseRepository;
import com.github.anno.repository.PassengerRepository;
import com.github.anno.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 乘车人服务接口实现类
 *
 * @author zhuyifa
 * @since 2019-12-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    @Override
    public BaseRepository<Passenger> getRepository() {
        return passengerRepository;
    }

}
