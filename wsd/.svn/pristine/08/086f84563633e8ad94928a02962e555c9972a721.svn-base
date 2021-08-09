package com.qzsoft.tah.task;

import com.qzsoft.tah.service.WsdExportCatBService;
import com.qzsoft.tah.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportTask extends Task {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Long exportCatId = -1L;

    public ExportTask(Long exportCatId, long delayInMilliseconds){
        super("ExportTask-" + exportCatId, delayInMilliseconds);
        this.exportCatId = exportCatId;
    }

//    public ExportTask(Long exportCatId){
//        super("ExportTask-" + exportCatId,  60 * 1000);
//        this.exportCatId = exportCatId;
//    }

    @Override
    public void run() {
        log.info("系统开始处理导出任务=================>>>>>导出种类id={}", this.exportCatId);
        WsdExportCatBService exportCatBService = BeanUtil.getBean(WsdExportCatBService.class);
        exportCatBService.recordExportCat(this.exportCatId ,false);
        log.info("系统结束处理导出任务=================<<<<<导出种类id={}", this.exportCatId);
    }
}
