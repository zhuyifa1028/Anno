package com.github.anno.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * 加油记录 实体类
 *
 * @author zhuyifa
 * @version 2020-11-20
 */
@Data
@Entity(name = "tbl_refueling")
public class Refueling extends Base {

    /**
     * 司机ID
     */
    private Long driverId;
    /**
     * 支付类型
     */
    private String paymentType;
    /**
     * 当前码表公里数
     */
    private Integer kilometers;
    /**
     * 加油日期及时间
     */
    private LocalDateTime refuelingTime;

}
