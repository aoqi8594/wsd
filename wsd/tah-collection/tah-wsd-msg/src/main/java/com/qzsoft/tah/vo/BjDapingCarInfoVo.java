package com.qzsoft.tah.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BjDapingCarInfoVo {
    private String carNo; // 车牌
    private String driverName; //司机姓名
    private String contactPhone; //司机电话
    private String startPlace; //该车辆起始地
    private String endPlace; //该车辆目的地
    private String temperature; //该车辆当前温度
    private String humidity; //该车辆当前湿度
    private String lat; //维度
    private String lng; // 精度
    private String alarmYn; // 是否报警
}
