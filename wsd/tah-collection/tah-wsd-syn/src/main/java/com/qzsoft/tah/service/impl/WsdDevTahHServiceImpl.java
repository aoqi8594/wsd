package com.qzsoft.tah.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.qzsoft.tah.dao.wsd.WsdDevAlarmLDao;
import com.qzsoft.tah.dao.wsd.WsdDevAlarmTreaMethBDao;
import com.qzsoft.tah.dao.wsd.WsdDevBDao;
import com.qzsoft.tah.dao.wsd.WsdDevTahHDao;
import com.qzsoft.tah.entity.*;
import com.qzsoft.tah.enums.AlarmTypeEnum;
import com.qzsoft.tah.service.WsdDevTahHService;
import com.qzsoft.tah.util.DateUtiles;
import com.qzsoft.tah.util.UIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WsdDevTahHServiceImpl implements WsdDevTahHService {

    private Logger logger = LoggerFactory.getLogger(WsdDevTahHServiceImpl.class);

    @Autowired
    private WsdDevTahHDao wsdDevTahHDao;

    @Autowired
    private WsdDevBDao wsdDevBDao;

    @Autowired
    private WsdDevAlarmLDao wsdDevAlarmLDao;

    @Autowired
    private WsdDevAlarmTreaMethBDao wsdDevAlarmTreaMethBDao;

    @Override
    public void saveDevTahH(WsdDevB wsdDevB, WsdPtSyncUndoRecordInfo info) {
        // 起始序列号等于总序列号  就会回归0
        wsdDevBDao.updateStartNumber(wsdDevB.getId(), 0);
        // 温度上限
        String tempUp = wsdDevB.getTempUp();
        String tempLow = wsdDevB.getTempLow();
        // 湿度上限
        String humUp = wsdDevB.getHumUp();
        String humLow = wsdDevB.getHumLow();

        // 判断采集的数据是否有效
        WsdDevTahH wsdDevTahH = wsdDevTahHDao.selectLastInfoByDevNo(wsdDevB.getDevNo());
        Map<String,String> belongTypeLocationMap =  findDevBelongTypeAndLocation(wsdDevB.getId());
        logger.info("设备位置信息：{}", JSON.toJSONString(belongTypeLocationMap));
        if (info == null || (wsdDevTahH != null && info.getCreateTime().compareTo(wsdDevTahH.getCreateTime()) < 0)) {
            //如果采集时间相等，则数据无效，警报设备异常
            logger.info("此次采集时间小于前一次采集时间，数据无效，设备异常");
            saveAlarml(wsdDevB, AlarmTypeEnum.SBYC.getCode(),belongTypeLocationMap.get("belongType"),belongTypeLocationMap.get("location"));
            return;
        }
        // 设备温湿度
        double tep = Double.parseDouble(info.getTemperature01());
        double hum = Double.parseDouble(info.getHumidity());
        // 判断是否需要报警
        boolean alarmYn = new Double(tempUp) < tep || new Double(tempLow) > tep
                || new Double(humUp) < hum || new Double(humLow) >  hum;
        String alarmYnStr = alarmYn?"Y":"N";
        // 更新温湿度
        wsdDevBDao.updateTemperatureAndHumidity(wsdDevB.getId(), hum, tep,alarmYnStr,"WLZT_ZX");
        logger.info(String.format("\n【更新温湿度】 温度: %s,湿度:%s 成功。。。 ",tep, hum));
        // 生成采集记录
        wsdDevTahH = new WsdDevTahH();
        BeanUtils.copyProperties(wsdDevB,wsdDevTahH);
        wsdDevTahH.setId(UIDGenerator.getUID());
        wsdDevTahH.setDevId(wsdDevB.getId());
        wsdDevTahH.setCreateTime(info.getCreateTime());
        wsdDevTahH.setHumidity(info.getHumidity());
        wsdDevTahH.setTemperature(info.getTemperature01());
        wsdDevTahH.setAlarmYn(alarmYnStr);
        wsdDevTahH.setBelongType(belongTypeLocationMap.get("belongType"));
        wsdDevTahH.setLocation(belongTypeLocationMap.get("location"));
        wsdDevTahH.setUseType(wsdDevB.getUseType());
        wsdDevTahHDao.save(wsdDevTahH);
        logger.info("\n【历史记录】 成功。。。。 ");
        // 验证是否需要报警 如果需要则插入报警记录
        if (!alarmYn){
            return;
        }
        // 插入报警记录
        if (new Double(tempUp) < tep){
            // 大于最大湿度
            saveAlarml(wsdDevB, AlarmTypeEnum.CGSD.getCode() ,belongTypeLocationMap.get("belongType"),belongTypeLocationMap.get("location"));
        }
        if (new Double(tempLow) > tep){
            // 小于最小湿度
            saveAlarml(wsdDevB,AlarmTypeEnum.CDSD.getCode(),belongTypeLocationMap.get("belongType"),belongTypeLocationMap.get("location"));
        }
        if (new Double(humUp) < hum){
            // 大于最大温度
            saveAlarml(wsdDevB,AlarmTypeEnum.CDWD.getCode(),belongTypeLocationMap.get("belongType"),belongTypeLocationMap.get("location"));
        }
        if (new Double(humLow) > hum){
            // 小于最小温度
            saveAlarml(wsdDevB,AlarmTypeEnum.CDWD.getCode(),belongTypeLocationMap.get("belongType"),belongTypeLocationMap.get("location"));
        }
        logger.info("\n【报警记录】 生成成功。。。。 ");
    }


    private Map<String,String> findDevBelongTypeAndLocation(long devId){
        Map<String,String> map = new HashMap<>();
        //实验室
        Map<String,Object> sqlMap1 = wsdDevBDao.findDevBelongTypeAndLocation1(devId);
        if(sqlMap1 != null && sqlMap1.get("location") != null){
            map.put("belongType","SYS") ;
            map.put("location",sqlMap1.get("location").toString()) ;
        }else {
            //冷链车
            Map<String,Object>  sqlMap2 = wsdDevBDao.findDevBelongTypeAndLocation2(devId);
            if(sqlMap2 != null && sqlMap2.get("location")  != null){
                map.put("belongType","LLC") ;
                map.put("location",sqlMap2.get("location").toString()) ;
            }
        }
        return map;
    }


    /**
     * 生成记录
     * @param wsdDevB 设备信息
     * @param avghumidity 湿度
     * @param avgtemperature01 温度
     * @param alarmYnStr
     */
    public void saveTahH(WsdDevB wsdDevB,double avghumidity, double avgtemperature01,String alarmYnStr){
        // 生成历史记录
        WsdDevTahH wsdDevTahH = new WsdDevTahH();
        BeanUtils.copyProperties(wsdDevB,wsdDevTahH);
        wsdDevTahH.setId(UIDGenerator.getUID());
        wsdDevTahH.setDevId(wsdDevB.getId());
        wsdDevTahH.setCreateTime(new Date());
        wsdDevTahH.setHumidity(avghumidity+"");
        wsdDevTahH.setTemperature(avgtemperature01+"");
        wsdDevTahH.setAlarmYn(alarmYnStr);
        wsdDevTahHDao.save(wsdDevTahH);
        logger.info("\n【历史记录】 成功。。。。 ");
    }

    /**
     * 保存报警记录
     * @param wsdDevB
     * @param contentType
     */
    public void saveAlarml(WsdDevB wsdDevB,String contentType,String belongType,String location){
        // 设备Id
        Long devId = wsdDevB.getId();
        WsdDevAlarmL wsdDevAlarmL = new WsdDevAlarmL();
        BeanUtils.copyProperties(wsdDevB,wsdDevAlarmL);
        // 查询报警处理方式
        WsdDevAlarmTreaMethB wsdDevAlarmTreaMethB = wsdDevAlarmTreaMethBDao.selectByDevIdLastTime(devId);
        // 赋值默认值
        wsdDevAlarmL.setIgnoreYn("N");
        wsdDevAlarmL.setSt("DCL");
        wsdDevAlarmL.setDevId(devId);
        if (wsdDevAlarmTreaMethB!=null){
            String type = wsdDevAlarmTreaMethB.getType();
            if ("HL".equals(type)){
                // 报警信息变成忽略状态
                wsdDevAlarmL.setIgnoreYn("Y");
                wsdDevAlarmL.setSt("YCL");
            }
            Date newTime = new Date();
            Date createTime = wsdDevAlarmTreaMethB.getCreateTime();
            // 延迟分钟数
            String treaMethBContent = wsdDevAlarmTreaMethB.getContent();
            if ("YC".equals(type)){
                // 计算延迟分钟数据与当前时间
                int contentInt = treaMethBContent == null?0:Integer.parseInt(treaMethBContent);
                // 计算新的时间与当前时间做比较
                Date dateminut = contentInt>0?DateUtiles.addDateMinut(createTime,contentInt):createTime;
                if (newTime.before(dateminut)){
                    wsdDevAlarmL.setSt("YCL");
                }
            }
        }
        // 参数复制
        wsdDevAlarmL.setId(UIDGenerator.getUID());
        wsdDevAlarmL.setCreateTime(new Date());
        wsdDevAlarmL.setAlarmYn("Y");
        wsdDevAlarmL.setLocation(location);
        wsdDevAlarmL.setBelongType(belongType);
        wsdDevAlarmL.setAlarmType(contentType);
        if(AlarmTypeEnum.CGWD.getCode().equals(contentType)){
            wsdDevAlarmL.setAlarmContent(AlarmTypeEnum.getDescByCode(contentType) + String.format("（温度：%s℃）",wsdDevB.getTemperature()));
        }else if(AlarmTypeEnum.CDWD.getCode().equals(contentType)){
            wsdDevAlarmL.setAlarmContent(AlarmTypeEnum.getDescByCode(contentType) + String.format("（温度：%s℃）",wsdDevB.getTemperature()));
        }else if(AlarmTypeEnum.CGSD.getCode().equals(contentType)){
            wsdDevAlarmL.setAlarmContent(AlarmTypeEnum.getDescByCode(contentType) + String.format("（湿度：%s%%RH）",wsdDevB.getHumidity()));
        }else if(AlarmTypeEnum.CDSD.getCode().equals(contentType)){
            wsdDevAlarmL.setAlarmContent(AlarmTypeEnum.getDescByCode(contentType) + String.format("（湿度：%s%%RH）",wsdDevB.getHumidity()));
        }else if(AlarmTypeEnum.SBYC.getCode().equals(contentType)){
            wsdDevAlarmL.setAlarmContent(AlarmTypeEnum.getDescByCode(contentType));
        }
        wsdDevAlarmL.setSendMsgYn("N");
        wsdDevAlarmLDao.save(wsdDevAlarmL);
    }

}
