package com.github.anno.entity;

import lombok.Data;

import javax.persistence.Entity;

/**
 * 乘车人实体类
 *
 * @author zhuyifa
 * @version 2020-11-18
 */
@Data
@Entity(name = "tbl_passenger")
public class Passenger extends Base {

    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 账号状态
     */
    private String status;
    /**
     * 公司名称
     */
    private String company;
    /**
     * 账号密码
     */
    private String password;

}
