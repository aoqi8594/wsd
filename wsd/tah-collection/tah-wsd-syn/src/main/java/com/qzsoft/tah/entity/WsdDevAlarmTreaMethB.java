package com.qzsoft.tah.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 设备报警处理方式表
 */
@Entity
@Data
@Table(name = "wsd_dev_alarm_trea_meth_b")
public class WsdDevAlarmTreaMethB {

    @Id
    private Long id;

    @Column(name = "st")
    private String st;

    @Column(name = "up_ver")
    private Integer upVer;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "creator_lname")
    private String creatorLname;

    @Column(name = "operator_lname")
    private String operatorLname;

    @Column(name = "dev_id")
    private Long devId;

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    private String content;

}
