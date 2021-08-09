package com.qzsoft.tah.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 设备
 */
@Entity
@Data
@Table(name = "wsd_dev_b")
public class WsdDevB {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "st")
    private String st;

    @Column(name = "up_ver")
    private int upVer;

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

    @Column(name = "belong_type")
    private String belongType;

    @Column(name = "use_type")
    private String useType;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "temp_up")
    private String tempUp;

    @Column(name = "temp_low")
    private String tempLow;

    @Column(name = "humidity")
    private String humidity;

    @Column(name = "hum_up")
    private String humUp;

    @Column(name = "hum_low")
    private String humLow;

    @Column(name = "dev_name")
    private String devName;

    @Column(name = "dev_mac")
    private String devMac;

    @Column(name = "dev_no")
    private String devNo;

    @Column(name = "acqu_freq1")
    private String acquFreq1;

    @Column(name = "acqu_freq2")
    private String acquFreq2;

    @Column(name = "acqu_freq3")
    private String acquFreq3;

    @Column(name = "net_st")
    private String netSt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "start_time")
    private Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "check_cycle")
    private String checkCycle;

    @Column(name = "duty_name")
    private String dutyName;

    @Column(name = "duty_login_name")
    private String dutyLoginName;

    @Column(name = "duty_cont_phon")
    private String dutyContPhon;

    @Column(name = "duty_email")
    private String dutyEmail;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "oper_cont_phon")
    private String operContPhon;

    @Column(name = "oper_email")
    private String operEmail;

    @Column(name = "remark")
    private String remark;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "start_number")
    private Integer startNumber;

    @Column(name = "end_number")
    private Integer endNumber;

    @Column(name = "lims_st")
    private String limsSt;

    @Column(name = "alarm_yn")
    private String alarmYn;

}
