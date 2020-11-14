package com.github.anno.repository;

import com.github.anno.entity.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基础数据接口
 *
 * @author zhuyifa
 * @version 2020-11-14
 */
@NoRepositoryBean
public interface BaseRepository extends JpaRepository<Base, Long> {

}
