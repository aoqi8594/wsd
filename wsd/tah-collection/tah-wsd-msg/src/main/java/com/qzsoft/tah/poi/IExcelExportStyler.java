package com.qzsoft.tah.poi;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import org.apache.poi.ss.usermodel.CellStyle;

public interface IExcelExportStyler {
    /**
     * 列表头样式
     * @param headerColor
     * @return
     */
    public CellStyle getHeaderStyle(short headerColor);
    /**
     * 标题样式
     * @param color
     * @return
     */
    public CellStyle getTitleStyle(short color);
    /**
     * 获取样式方法
     * @param Parity
     * @param entity
     * @return
     */
    public CellStyle getStyles(boolean Parity, ExcelExportEntity entity);
}
