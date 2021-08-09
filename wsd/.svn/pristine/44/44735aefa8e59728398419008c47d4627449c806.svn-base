package com.qzsoft.tah.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "wsd_pt_sync_undo_record")
public class WsdPtSyncUndoRecord {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 设备编号
     */
    @Column(name = "dev_no")
    private String devNo;

    @Column(name = "device_printhead")
    private String devicePrinthead;

    @Column(name = "chepaihao")
    private String chepaihao;

    @Column(name = "baojingwendu_shangxian")
    private String baojingwenduShangxian;

    @Column(name = "baojingwendu_xiaxian")
    private String baojingwenduXiaxian;

    @Column(name = "hegewendu_shangxian")
    private String hegewenduShangxian;

    @Column(name = "hegewendu_xiaxian")
    private String hegewenduXiaxian;

    @Column(name = "daoqishijian")
    private String daoqishijian;

    @Column(name = "guigexinghao")
    private String guigexinghao;

    @Column(name = "two_temperature")
    private String twoTemperature;

    @Column(name = "maxtemperature01")
    private String maxtemperature01;

    @Column(name = "mintemperature01")
    private String mintemperature01;

    @Column(name = "avgtemperature01")
    private String avgtemperature01;

    @Column(name = "maxtemperature02")
    private String maxtemperature02;

    @Column(name = "mintemperature02")
    private String mintemperature02;

    @Column(name = "avgtemperature02")
    private String avgtemperature02;

    @Column(name = "maxhumidity")
    private String maxhumidity;

    @Column(name = "minhumidity")
    private String minhumidity;

    @Column(name = "avghumidity")
    private String avghumidity;

}
