package com.qzsoft.tah.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "wsd_pt_sync_do_record_info")
public class WsdPtSyncDoRecordInfo {

    @Id
    @Column(name = "id")
    private String id;

    /**
     * 关联采集Id
     */
    @Column(name = "record_id")
    private String recordId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 设备编号
     */
    @Column(name = "shebeibianhao")
    private String shebeibianhao;

    /**
     * 采集时间
     */
    @Column(name = "time")
    private String time;

    /**
     * 上报时间
     */
    @Column(name = "servicetime")
    private String servicetime;

    /**
     * 温度1
     */
    @Column(name = "temperature01")
    private String temperature01;

    @Column(name = "temperature02")
    private String temperature02;

    /**
     * 湿度
     */
    @Column(name = "humidity")
    private String humidity;

    /**
     * 速度
     */
    @Column(name = "speed")
    private String speed;

    /**
     * 经度
     */
    @Column(name = "jingdu")
    private String jingdu;

    /**
     * 纬度
     */
    @Column(name = "weidu")
    private String weidu;

    /**
     * 电量
     */
    @Column(name = "power")
    private String power;

    /**
     * 数据类型
     */
    @Column(name = "shujuleixingbiaozhi")
    private String shujuleixingbiaozhi;

    @Column(name = "xiangzistate")
    private String xiangzistate;

    /**
     * 定位类型
     */
    @Column(name = "net_leixing")
    private String netLeixing;

    /**
     * 设备类型
     */
    @Column(name = "shebeineixing")
    private String shebeineixing;

    /**
     * 信号强度
     */
    @Column(name = "xinghaoqiangdu")
    private int xinghaoqiangdu;

}
