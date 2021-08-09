package com.qzsoft.tah.entity.plrs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "temphum_report")
@Data
public class TemphumReport {
    @Id
    @Column(name = "thr_id")
    private String thrId;

    @Column(name = "thr_temperature")
    private BigDecimal thrTemperature;

    @Column(name = "thr_humidity")
    private BigDecimal thrHumidity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "thr_collecttime")
    private Date thrCollecttime;
}
