package com.github.anno.entity;

import lombok.Data;

import javax.persistence.Entity;

/**
 * 司机实体类
 *
 * @author zhuyifa
 * @version 2020-11-17
 */
@Data
@Entity(name = "tbl_driver")
public class Driver extends Base {

    /**
     * 中文名称
     */
    private String chName;
    /**
     * 英文名称
     */
    private String enName;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 工号
     */
    private String jobNum;
    /**
     * 车牌号码
     */
    private String carNum;
    /**
     * 账号状态
     */
    private String status;
    /**
     * 账号密码
     */
    private String password;

}
