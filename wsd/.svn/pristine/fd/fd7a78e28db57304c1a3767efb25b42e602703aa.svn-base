package com.qzsoft.tah.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qzsoft.tah.dao.WsdCarBDao;
import com.qzsoft.tah.dao.WsdCarGpsLDao;
import com.qzsoft.tah.dao.WsdDevBDao;
import com.qzsoft.tah.dao.WsdDevTahHDao;
import com.qzsoft.tah.entity.WsdCarB;
import com.qzsoft.tah.entity.WsdCarGpsL;
import com.qzsoft.tah.entity.WsdDevB;
import com.qzsoft.tah.entity.WsdDevTahH;
import com.qzsoft.tah.enums.DevNetStEnum;
import com.qzsoft.tah.enums.DevTypeEnum;
import com.qzsoft.tah.service.BJDapingService;
import com.qzsoft.tah.vo.BjDapingCarInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aq
 * @version 1.0 2021/7/31
 */
@Service
public class BJDapingServiceImpl implements BJDapingService {

    @Autowired
    private WsdDevBDao wsdDevBDao;

    @Autowired
    private WsdCarBDao wsdCarBDao;

    @Autowired
    private WsdCarGpsLDao wsdCarGpsLDao;

    @Autowired
    private WsdDevTahHDao wsdDevTahHDao;

    private static Logger LOG = LoggerFactory.getLogger(BJDapingServiceImpl.class);


    @Override
    public List<WsdDevTahH> getDevHistoryInfo(String devId) {
        return wsdDevTahHDao.findWsdDevTahHSByDevIdOrderByCreateTimeDesc(Long.valueOf(devId));
    }

    @Override
    public Map<String, Object> getCarDevsInfo(String carId) {
        LOG.info("读取北京食品大屏车辆的温湿度设备数据");
        Map<String, Object> resultMap = new HashMap<>();
        List<WsdDevB> devBLit = wsdDevBDao.selDevsByCar(Long.valueOf(carId));
        int alarmNum =0;
        int normalNum =0;
        int delayNum =0;
        List<Map> alarmMapList = new ArrayList<>();
        List<Map> normalMapList = new ArrayList<>();
        List<Map> delayMapList = new ArrayList<>();
        for(WsdDevB wsdDevB : devBLit){
            wsdDevB.setBelongType(DevTypeEnum.getDescByCode(wsdDevB.getBelongType()));
            wsdDevB.setNetSt(DevNetStEnum.getDescByCode(wsdDevB.getNetSt()));
            Map map = new HashMap(JSONObject.parseObject(JSONObject.toJSONString(wsdDevB), Map.class));
            if("Y".equals(wsdDevB.getAlarmYn())){
                map.put("msgList",getMsgList(wsdDevB));
                alarmNum ++;
                alarmMapList.add(map);
            }else {
                normalNum ++;
                normalMapList.add(map);
            }
        }
        resultMap.put("alarmNum",alarmNum);
        resultMap.put("normalNum",normalNum);
        resultMap.put("delayNum",delayNum);
        resultMap.put("alarmMapList",alarmMapList);
        resultMap.put("normalMapList",normalMapList);
        resultMap.put("delayMapList",delayMapList);
        LOG.info("返回数据：{}",resultMap.toString());
        return resultMap;
    }

    @Override
    public Map<String, Object> getDevsInfo() {
        LOG.info("读取北京食品大屏所有温湿度设备数据");
        Map<String, Object> resultMap = new HashMap<>();
        List<WsdDevB> devBLit = wsdDevBDao.findAll();
        int alarmNum =0;
        int normalNum =0;
        int delayNum =0;
        List<Map> alarmMapList = new ArrayList<>();
        List<Map> normalMapList = new ArrayList<>();
        List<Map> delayMapList = new ArrayList<>();
        for(WsdDevB wsdDevB : devBLit){
            wsdDevB.setBelongType(DevTypeEnum.getDescByCode(wsdDevB.getBelongType()));
            wsdDevB.setNetSt(DevNetStEnum.getDescByCode(wsdDevB.getNetSt()));
            Map map = new HashMap(JSONObject.parseObject(JSONObject.toJSONString(wsdDevB), Map.class));
            if("Y".equals(wsdDevB.getAlarmYn())){
                map.put("msgList",getMsgList(wsdDevB));
                alarmNum ++;
                alarmMapList.add(map);
            }else {
                normalNum ++;
                normalMapList.add(map);
            }
        }
        resultMap.put("alarmNum",alarmNum);
        resultMap.put("normalNum",normalNum);
        resultMap.put("delayNum",delayNum);
        resultMap.put("alarmMapList",alarmMapList);
        resultMap.put("normalMapList",normalMapList);
        resultMap.put("delayMapList",delayMapList);
        LOG.info("返回数据：{}",resultMap.toString());
        return resultMap;
    }


    @Override
    public Map<String, Object> getCarsInfo() {
        LOG.info("读取北京食品大屏所有车辆数据");
        Map<String, Object> resultMap = new HashMap<>();
        List<WsdCarB> carBLit = wsdCarBDao.findAll();
        int alarmNum =0;
        int normalNum =0;
        int warningNum =0;
        List<Map> alarmMapList = new ArrayList<>();
        List<Map> normalMapList = new ArrayList<>();
        List<Map> warningMapList = new ArrayList<>();
        BjDapingCarInfoVo carInfoVo = new BjDapingCarInfoVo();
        for(WsdCarB wsdCarB : carBLit){
            carInfoVo.setCarNo(wsdCarB.getCarNo());
            carInfoVo.setContactPhone(wsdCarB.getContactPhone());
            carInfoVo.setDriverName(wsdCarB.getDriverName());
            carInfoVo.setStartPlace(wsdCarB.getStartPlace());
            carInfoVo.setEndPlace(wsdCarB.getEndPlace());
            WsdDevB wsdDevB = wsdDevBDao.findById(wsdCarB.getId()).orElse(null);
            if(wsdDevB != null){
                carInfoVo.setHumidity(wsdDevB.getHumidity());
                carInfoVo.setTemperature(wsdDevB.getTemperature());
                carInfoVo.setAlarmYn(wsdDevB.getAlarmYn());
            }
            List<WsdCarGpsL> wsdCarGpsLLs = wsdCarGpsLDao.findWsdCarGpsLSByCarTaskIdOrderByCreateTimeDesc(wsdCarB.getId());
            if(!wsdCarGpsLLs.isEmpty()){
                carInfoVo.setLat(wsdCarGpsLLs.get(0).getLat());
                carInfoVo.setLng(wsdCarGpsLLs.get(0).getLng());
            }
            Map map = new HashMap(JSONObject.parseObject(JSONObject.toJSONString(carInfoVo), Map.class));
            if("Y".equals(carInfoVo.getAlarmYn())){
                alarmMapList.add(map);
            }else {
                normalMapList.add(map);
            }
        }
        resultMap.put("alarmNum",alarmNum);
        resultMap.put("normalNum",normalNum);
        resultMap.put("warningNum",warningNum);
        resultMap.put("alarmMapList",alarmMapList);
        resultMap.put("normalMapList",normalMapList);
        resultMap.put("warningMapList",warningMapList);
        LOG.info("返回数据：{}",resultMap.toString());
        return resultMap;
    }

    private List<String> getMsgList(WsdDevB wsdDevB){
        // 设备温湿度
        double tep = Double.parseDouble(wsdDevB.getTemperature());
        double hum = Double.parseDouble(wsdDevB.getHumidity());
        // 温度上限
        String tempUp = wsdDevB.getTempUp();
        String tempLow = wsdDevB.getTempLow();
        // 湿度上限
        String humUp = wsdDevB.getHumUp();
        String humLow = wsdDevB.getHumLow();

        List<String> msgList = new ArrayList<>();

        if (new Double(tempUp) < tep || new Double(tempLow) > tep){
            msgList.add("温度报警");
        }
        if (new Double(humUp) < hum || new Double(humLow) > hum){
            msgList.add("湿度报警");
        }
        return msgList;
    }
}
