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
@Data
public class SbglEqpBVO {

    private long id;

    private String prn;

    private Double upVer;

    private Date createTime;

    private Date updateTime;

    private String eqpNo;

    private String oldEqpNo;

    private String eqpName;

    private String category;

    private String eqpSpec;

    private String brandName;

    private String manufacturer;

    private Date dateOfProduction;

    private Date purchasingDate;

    private String originalNo;

    private String supplierPrn;

    private String supplierName;

    private String location;

    private String locationOrg;

    private String locationOrgPrn;

    private String eqpStatus;

    private String eqpType;

    private String measuringRange;

    private String technicalTarget;

    private String sectionName;

    private String sectionPrn;

    private String dutyPerson;

    private String resolution;

    private String accuracy;

    private String uncertainty;

    private String remark;

    private String isMeter;

    private String meterCompany;

    private String meterType;

    private String meterCycle;

    private String meterBasis;

    private Date meterLastDate;

    private Date meterValidity;

    private Date meterPlanDate;

    private String meterPara;

    private String isInspection;

    private String inspectionCycle;

    private Date inspectionLastDate;

    private Date inspectionValidity;

    private String maintCycle;

    private Date maintLastDate;

    private Date maintValidity;

    private String repairCycle;

    private Date repairLastDate;

    private Date repairValidity;

    private String eqpLevel;

    private Long parentId;

    private Date valveMeterLastDate;

    private Date manometerMeterLastDate;

    private String isSpecialEquipment;

}
