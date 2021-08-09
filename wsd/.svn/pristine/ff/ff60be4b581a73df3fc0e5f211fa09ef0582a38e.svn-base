package com.qzsoft.tah.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 车辆表
 */
@Table(name = "wsd_dev_alarm_l")
@Entity
@Data
public class WsdDevAlarmL {

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

    @Column(name = "alarm_type")
    private String alarmType;

    /**
     * 创建用户登录名
     */
    @Column(name = "creator_lname")
    private String creatorLname;

    /**
     * 操作用户登录名
     */
    @Column(name = "operator_lname")
    private String operatorLname;

    /**
     * 是否忽略
     */
    @Column(name = "ignore_yn")
    private String ignoreYn;

    /**
     * 关联设备id
     */
    @Column(name = "dev_id")
    private Long devId;

    /**
     * 设备编号
     */
    @Column(name = "dev_no")
    private String devNo;

    /**
     * 设备名称
     */
    @Column(name = "dev_name")
    private String devName;

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

    /**
     * 是否报警
     */
    @Column(name = "alarm_yn")
    private String alarmYn;

    /**
     * 所属类型
     */
    @Column(name = "belong_type")
    private String belongType;

    /**
     * 位置
     */
    @Column(name = "location")
    private String location;

    /**
     * 网络状态
     */
    @Column(name = "net_st")
    private String netSt;

    /**
     * 负责人名称
     */
    @Column(name = "duty_name")
    private String dutyName;

    /**
     * 负责人联系电话
     */
    @Column(name = "duty_cont_phon")
    private String dutyContPhon;

    /**
     * 报警内容
     */
    @Column(name = "alarm_content")
    private String alarmContent;

    /**
     * 处理结果
     */
    @Column(name = "trea_results")
    private String treaResults;

    /**
     * 是否已发送消息
     */
    @Column(name = "send_msg_yn")
    private String sendMsgYn;

}
