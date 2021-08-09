package com.qzsoft.tah.service.impl;


import com.qzsoft.tah.dao.WsdExportLDao;
import com.qzsoft.tah.entity.WsdExportL;
import com.qzsoft.tah.service.WsdExportLService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 导出记录表(WsdExportL)表服务实现类
 *
 * @author makejava
 * @since 2021-04-26 15:20:08
 */
@Service("wsdExportLService")
public class WsdExportLServiceImpl implements WsdExportLService {
    @Resource
    private WsdExportLDao wsdExportLDao;

    @Override
    public void saveVo(String reportName, String filePath, String exportType) {
        WsdExportL wsdExportL = new WsdExportL();
        wsdExportL.setUpVer(1);
        wsdExportL.setCreatorLname("系统管理员");
        wsdExportL.setReportName(reportName);
        wsdExportL.setExportType(exportType);
        wsdExportL.setFilePath(filePath);
        wsdExportLDao.save(wsdExportL);
    }
}