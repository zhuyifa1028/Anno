package com.github.anno.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * 加班记录 实体类
 *
 * @author zhuyifa
 * @version 2020-11-21
 */
@Data
@Entity(name = "tbl_overtime")
public class Overtime extends Base {

    /**
     * 司机ID
     */
    private Long driverId;
    /**
     * 加班类型
     */
    private String overtimeType;
    /**
     * 用车目的
     */
    private String useCarPurpose;
    /**
     * 加班时长
     */
    private Integer overtimeHours;
    /**
     * 加班日期
     */
    private LocalDateTime overtimeDate;

}
