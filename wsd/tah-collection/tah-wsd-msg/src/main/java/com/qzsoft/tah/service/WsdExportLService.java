package com.qzsoft.tah.service;



/**
 * 导出记录表(WsdExportL)表服务接口
 *
 * @author makejava
 * @since 2021-04-26 15:20:08
 */
public interface WsdExportLService {


    void saveVo(String reportName, String filePath, String exportType);
}