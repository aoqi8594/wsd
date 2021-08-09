package com.qzsoft.tah.vo;

import lombok.Data;

import java.util.List;

@Data
public class DevUseTypeVo {
    private String useType;
    private List<ExcelDevVo> excelDevVos;
}
