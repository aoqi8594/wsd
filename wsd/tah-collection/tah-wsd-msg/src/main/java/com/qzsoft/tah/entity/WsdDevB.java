package com.qzsoft.tah.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author aq
 * @version 1.0 2021/7/31
 */
@Data
@Entity
@Table(name = "wsd_dev_b")
public class WsdDevB implements Serializable  {

    @Id
    private Long id;

    @Column(name = "alarm_yn")
    private String alarmYn;

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

    @Column(name = "belong_type")
    private String belongType;

    @Column(name = "net_st")
    private String netSt;
}
