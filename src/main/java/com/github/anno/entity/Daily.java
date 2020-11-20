package com.github.anno.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * 日常记录 实体类
 *
 * @author zhuyifa
 * @version 2020-11-20
 */
@Data
@Entity(name = "tbl_daily")
public class Daily extends Base {

    /**
     * 司机出发时路码
     */
    private Integer goOutKms;
    /**
     * 司机回家时路码
     */
    private Integer goHomeKms;
    /**
     * 用户上车时路码
     */
    private Integer getOnKms;
    /**
     * 用户下车时路码
     */
    private Integer getOffKms;
    /**
     * 营业公里数
     */
    private Integer kilometers;

    /**
     * 日志日期
     */
    private LocalDateTime logDate;
    /**
     * 司机出发时间
     */
    private LocalDateTime goOutTime;
    /**
     * 司机回家时间
     */
    private LocalDateTime goHomeTime;
    /**
     * 用户上车时间
     */
    private LocalDateTime getOnTime;
    /**
     * 用户下车时间
     */
    private LocalDateTime getOffTime;

}
