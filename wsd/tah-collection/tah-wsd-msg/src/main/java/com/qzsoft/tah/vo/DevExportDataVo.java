package com.qzsoft.tah.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DevExportDataVo {
    private String useType;
    private String iceboxName;
    private Long devId;
    private String devName;
    private String operEmail;
    private String dutyEmail;
    private LocalDateTime dataTime;
    private String timeSlot;
    private String value;
    private String checkName;
    private String mark;

}
