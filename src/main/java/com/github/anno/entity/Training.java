package com.github.anno.entity;

import lombok.Data;

import javax.persistence.Entity;

/**
 * 培训信息 实体类
 *
 * @author zhuyifa
 * @version 2020-11-22
 */
@Data
@Entity(name = "tbl_training")
public class Training extends Base {

    /**
     * 培训图片
     */
    private String image;
    /**
     * 培训标题
     */
    private String title;
    /**
     * 培训副标题
     */
    private String subtitle;
    /**
     * 培训内容简介
     */
    private String contentBrief;
    /**
     * 培训内容详情
     */
    private String contentDetails;
    /**
     * 是否隐藏
     */
    private Boolean isHidden;

}
