package com.qzsoft.tah.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @Author Yang Chunhai
  * @Description  设备导出包装类
  * @Date 2021/4/27 14:05
  **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget(value="excelDevVo")
public class ExcelDevVo {

    private Long devId;
    @Excel(name="温度计名称", mergeVertical = true)
    private String devName;

    @Excel(name="日期", mergeRely={0}, mergeVertical = true)
    private String date;

    @Excel(name="时间")
    private String time;

    @Excel(name="温度")
    private String value;

    @Excel(name="监察人", mergeRely={0}, mergeVertical = true)
    private String checkName;

    @Excel(name="备注")
    private String mark;

    private String iceboxName;
    private String FZREmail;
    private String YWEmail;
    private String useType;
}
