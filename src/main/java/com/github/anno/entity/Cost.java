package com.github.anno.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用记录 实体类
 *
 * @author zhuyifa
 * @version 2020-11-21
 */
@Data
@Entity(name = "tbl_refueling")
public class Cost extends Base {

    /**
     * 司机ID
     */
    private Long driverId;
    /**
     * 费用承担
     */
    private String costBear;
    /**
     * 费用类型
     */
    private String costType;
    /**
     * 费用目的
     */
    private String costPurpose;
    /**
     * 费用金额
     */
    private BigDecimal costAmount;
    /**
     * 费用发生日期
     */
    private LocalDateTime costDate;

}
