package com.qzsoft.tah.controller;

import com.qzsoft.tah.dao.WsdDevTahHDao;
import com.qzsoft.tah.notify.NotifyService;
import com.qzsoft.tah.service.WsdExportCatBService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private NotifyService notifyService;

    @Resource
    private WsdExportCatBService wsdExportCatBService;

    @Resource
    private WsdDevTahHDao wsdDevTahHDao;

    @Resource
    private Environment environment;


    @GetMapping("/mail")
    public Object test() throws Exception {
        notifyService.notifyEmailHtml("672414262@qq.com", "测试", "<h5>测试测试</h5>");
        return "123";
    }

}
