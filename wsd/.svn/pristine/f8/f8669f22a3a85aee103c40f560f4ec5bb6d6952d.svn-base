package com.qzsoft.tah.controller;


import com.qzsoft.tah.service.BjTaskService;
import com.qzsoft.tah.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 **/
@RestController
@RequestMapping("/bjTask")
public class BjTaskController {

    @Autowired
    private BjTaskService bjTaskService;

    @GetMapping("/synLimsData")
    public String synLimsData(){
        bjTaskService.updLimsData();
        return "success";
    }

}
