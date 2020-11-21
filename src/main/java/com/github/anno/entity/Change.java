package com.github.anno.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * 换车记录 实体类
 *
 * @author zhuyifa
 * @version 2020-11-21
 */
@Data
@Entity(name = "tbl_change")
public class Change extends Base {

    /**
     * 司机ID
     */
    private Long driverId;
    /**
     * 旧车公里数
     */
    private Integer oldCarKms;
    /**
     * 旧车车牌号
     */
    private String oldCarNumber;
    /**
     * 新车公里数
     */
    private Integer newCarKms;
    /**
     * 新车车牌号
     */
    private String newCarNumber;
    /**
     * 换车原因
     */
    private String changeReason;
    /**
     * 换车日期及时间
     */
    private LocalDateTime changeTime;

}
