package com.qzsoft.tah.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author aq
 * @version 1.0 2021/6/1
 */
@Entity
@Table(name = "lcgl_sampling_car_b")
@Data
public class LcglSamplingCarBEntity {

    @Id
    @Column(name = "id")
    private long id;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "st")
    private String st;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @Column(name = "car_num")
    private String carNum;

    @Column(name = "car_driver")
    private String carDriver;

    @Column(name = "car_key")
    private String carKey;

    @Column(name = "driver_tel")
    private String driverTel;

    @Column(name = "tachograph")
    private String tachograph;

    @Column(name = "tachograph_no")
    private String tachographNo;

    @Column(name = "thermo_hygrometer")
    private String thermoHygrometer;

    @Column(name = "thermo_hygrometer_no")
    private String thermoHygrometerNo;

    @Column(name = "remark")
    private String remark;

}
