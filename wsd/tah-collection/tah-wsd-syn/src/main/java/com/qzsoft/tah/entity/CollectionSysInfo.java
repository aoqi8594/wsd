package com.qzsoft.tah.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 采集记录
 */
@Table(name = "collection_sys_info")
@Entity
@Data
public class CollectionSysInfo {

    @Id
    @Column(name = "id")
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 同步耗时
     */
    @Column(name = "sys_time")
    private int sysTime;

    /**
     * 开始同步时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束同步时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 设备数量
     */
    @Column(name = "equipment_number")
    private int equipmentNumber;

    /**
     * 状态 CG/SB
     */
    @Column(name = "st")
    private String st;

    /**
     * 同步类型 1(平台同步) 2(本地同步)
     */
    @Column(name = "type")
    private Integer type;

}
