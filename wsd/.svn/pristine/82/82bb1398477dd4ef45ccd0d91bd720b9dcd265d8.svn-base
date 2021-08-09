package com.qzsoft.tah.controller;

import com.qzsoft.tah.entity.WsdDevTahH;
import com.qzsoft.tah.service.BJDapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Action;
import java.util.List;
import java.util.Map;

/**
 * @author aq
 * @version 1.0 2021/7/31
 */

@CrossOrigin
@RestController
@RequestMapping("/bjdaping/wsd")
public class BJDapingController {

    @Autowired
    private BJDapingService bjDapingService;

    @PostMapping("/dev")
    public Map<String,Object> getDevsInfo(){
        return  bjDapingService.getDevsInfo();
    }

    @PostMapping("/cars")
    public Map<String,Object> getCarsInfo(){
        return  bjDapingService.getCarsInfo();
    }

    @PostMapping("/carDevs")
    public Map<String,Object> getCarDevsInfo(@RequestParam("carId") String carId){
        return  bjDapingService.getCarDevsInfo(carId);
    }

    @PostMapping("/devHis")
    public List<WsdDevTahH> getDevHistoryInfo(@RequestParam("devId") String devId){
        return  bjDapingService.getDevHistoryInfo(devId);
    }

}
