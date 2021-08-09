package com.qzsoft.tah.vo.bj;

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
@Data
public class LcglSamplingCarBVO {

    private long id;

    private Date createTime;

    private String createUser;

    private String st;

    private Date lastUpdateTime;

    private String carNum;

    private String carDriver;

    private String carKey;

    private String driverTel;

    private String tachograph;

    private String tachographNo;

    private String thermoHygrometer;

    private String thermoHygrometerNo;

    private String remark;

}
