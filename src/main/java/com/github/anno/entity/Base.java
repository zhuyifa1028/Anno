package com.github.anno.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 *
 * @author zhuyifa
 * @version 2020-11-13
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Base implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 创建者
     */
    @CreatedBy
    private String createdBy;
    /**
     * 创建日期
     */
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    /**
     * 最后修改者
     */
    @LastModifiedBy
    private String lastModifiedBy;
    /**
     * 最后修改日期
     */
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedDate;

}
