package com.qzsoft.tah.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelDevBXVo {
    private Long devId;
    @Excel(name="温度计名称", orderNum = "1", mergeVertical = true)
    private String devName;

    @Excel( name="日期", orderNum = "2", mergeRely={0}, mergeVertical = true)
    private String date;

    @Excel(name="时间", orderNum = "3", groupName = "上午")
    private String swTime;

    @Excel(name="温度", orderNum = "4", groupName = "上午")
    private String swValue;

    @Excel(name="时间", orderNum = "5", groupName="下午")

    private String xwTime;
    @Excel(name="温度", orderNum = "6", groupName="下午")
    private String xwValue;

    @Excel(name="监察人", orderNum = "7", mergeRely={0}, mergeVertical = true)
    private String checkName;

    @Excel(name="备注", orderNum = "8")
    private String mark;

    private String iceboxName;
    private String FZREmail;
    private String YWEmail;
}
