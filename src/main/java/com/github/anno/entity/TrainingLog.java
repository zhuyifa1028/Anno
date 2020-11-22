package com.github.anno.entity;

import lombok.Data;

import javax.persistence.Entity;

/**
 * 培训记录 实体类
 *
 * @author zhuyifa
 * @version 2020-11-22
 */
@Data
@Entity(name = "tbl_training_log")
public class TrainingLog extends Base {

    /**
     * 司机ID
     */
    private Long driverId;
    /**
     * 培训ID
     */
    private Long trainingId;

}
