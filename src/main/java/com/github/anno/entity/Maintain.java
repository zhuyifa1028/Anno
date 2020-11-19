package com.github.anno.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * 例行保养 实体类
 *
 * @author zhuyifa
 * @version 2020-11-19
 */
@Data
@Entity(name = "tbl_maintain")
public class Maintain extends Base {

    /**
     * 司机名称
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 车牌号码
     */
    private String carNum;
    /**
     * 是否打卡
     */
    private Boolean isClockIn;
    /**
     * 出行公里数
     */
    private Integer kilometers;
    /**
     * 例行保养
     */
    private LocalDateTime maintainDate;

}
