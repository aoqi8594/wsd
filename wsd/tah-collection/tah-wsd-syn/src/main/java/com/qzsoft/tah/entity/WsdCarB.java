package com.qzsoft.tah.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 车辆
 */
@Entity
@Data
@Table(name = "wsd_car_b")
public class WsdCarB {

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

    @Column(name = "car_no")
    private String carNo;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "start_place")
    private String startPlace;

    @Column(name = "end_place")
    private String endPlace;

    @Column(name = "dev_id")
    private Long devId;

    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "tachograph")
    private String tachograph;

    @Column(name = "mark")
    private String mark;

    @Column(name = "driver_lname")
    private String driverLname;

}
