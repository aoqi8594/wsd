package com.qzsoft.tah.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 设备历史记录
 */
@Table(name = "wsd_dev_tah_h")
@Entity
@Data
public class WsdDevTahH {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "st")
    private String st;

    @Column(name = "up_ver")
    private Integer upVer;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "creator_lname")
    private String creatorLname;

    @Column(name = "operator_lname")
    private String operatorLname;

    @Column(name = "use_type")
    private String useType;

    /**
     * 温度
     */
    @Column(name = "temperature")
    private String temperature;

    /**
     * 温度上限
     */
    @Column(name = "temp_up")
    private String tempUp;

    /**
     * 温度下限
     */
    @Column(name = "temp_low")
    private String tempLow;

    /**
     * 湿度
     */
    @Column(name = "humidity")
    private String humidity;

    /**
     * 湿度上限
     */
    @Column(name = "hum_up")
    private String humUp;

    /**
     * 湿度下限
     */
    @Column(name = "hum_low")
    private String humLow;

    @Column(name = "dev_id")
    private Long devId;

    @Column(name = "dev_name")
    private String devName;

    @Column(name = "dev_no")
    private String devNo;

    /**
     * 是否报警
     */
    @Column(name = "alarm_yn")
    private String alarmYn;

    /**
     * 所属类型（LLC/SYS）
     */
    @Column(name = "belong_type")
    private String belongType;

    /**
     * 位置
     */
    @Column(name = "location")
    private String location;

}
