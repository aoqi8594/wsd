package com.qzsoft.tah.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.qzsoft.tah.dao.wsd.WsdCarBDao;
import com.qzsoft.tah.dao.wsd.WsdDevBDao;
import com.qzsoft.tah.dao.wsd.YwpzUserSDao;
import com.qzsoft.tah.entity.WsdCarB;
import com.qzsoft.tah.entity.WsdDevB;
import com.qzsoft.tah.entity.YwpzUserSEntity;
import com.qzsoft.tah.service.BjTaskService;
import com.qzsoft.tah.util.BeanParseUtils;
import com.qzsoft.tah.util.HttpClientsUtils;
import com.qzsoft.tah.vo.SbglEqpBVO;
import com.qzsoft.tah.vo.bj.LcglSamplingCarBVO;
import com.qzsoft.tah.vo.bj.YwpzUserSVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

/**
 * @author aq
 * @version 1.0 2021/6/2
 */
@Service
public class BjTaskServiceImpl implements BjTaskService {

    @Autowired
    private YwpzUserSDao ywpzUserSDao;

    @Autowired
    private WsdDevBDao wsdDevBDao;

    @Autowired
    private WsdCarBDao wsdCarBDao;

    private Logger logger = LoggerFactory.getLogger(BjTaskServiceImpl.class);

    @Value("${spring.outurl.bj}")
    private String url;

    @Override
    public void updLimsData() {
        String sqlParam = "'温度计','湿度计','温湿度计'";
        updUsers(sqlParam);
        updDevs(sqlParam);
        updCars();
    }

    private void updUsers(String sqlParam) {
        logger.info("\n------同步【用户】基础数据开始----\n");
//        ywpzUserSDao.delete();
        Map<String, String> postMap = Maps.newConcurrentMap();
        postMap.put("devsName",sqlParam);
        String str = HttpClientsUtils.postUrl(url + "sbgl-prj/syn/users", postMap);
        if (str != null) {
            String obj = String.valueOf(JSON.parseObject(str).get("obj"));
            JSON.parseArray(obj).forEach(parseObj -> {
                Map<String, Object> columnsMap = (Map<String, Object>) parseObj;
                Map<String, Object> valMap = (Map<String, Object>) columnsMap.get("columns");
                YwpzUserSEntity ywpzUserSEntity = BeanParseUtils.populate(YwpzUserSEntity.class, valMap);
                ywpzUserSDao.save(ywpzUserSEntity);
                logger.info(String.format("\n【更新成功】用户名称：%s",ywpzUserSEntity.getLoginName()));
            });
        }
        logger.info("\n------同步【用户】基础数据结束----\n");
    }


    private void updCars() {
        logger.info("\n------同步【车辆】基础数据开始----\n");
        Map<String, String> postMap = Maps.newConcurrentMap();
        String str = HttpClientsUtils.postUrl(url + "sbgl-prj/syn/cars", postMap);
        if (str != null) {
            String obj = String.valueOf(JSON.parseObject(str).get("obj"));
            JSON.parseArray(obj).forEach(parseObj -> {
                Map<String, Object> columnsMap = (Map<String, Object>) parseObj;
                Map<String, Object> valMap = (Map<String, Object>) columnsMap.get("columns");
                LcglSamplingCarBVO lcglSamplingCarBVO = BeanParseUtils.populate(LcglSamplingCarBVO.class, valMap);
                updCar(lcglSamplingCarBVO);
            });
        }
        logger.info("\n------同步【车辆】基础数据结束----\n");
    }

    private void updCar(LcglSamplingCarBVO lcglSamplingCarBVO){
            WsdCarB wsdCarB = wsdCarBDao.findById(lcglSamplingCarBVO.getId()).orElse(null);
            String msg = null;
            if(wsdCarB == null){
                wsdCarB = new WsdCarB();
                wsdCarB.setId(lcglSamplingCarBVO.getId());
                wsdCarB.setSt("ZXZ");
                wsdCarB.setUpVer(1);
                wsdCarB.setCreateTime(new Date());
                msg = "新增";
            }else {
                wsdCarB.setUpdateTime(new Date());
                wsdCarB.setUpVer(wsdCarB.getUpVer()+1);
                msg = "更新";
            }
            wsdCarB.setCarNo(lcglSamplingCarBVO.getCarNum());
            wsdCarB.setDriverLname(lcglSamplingCarBVO.getCarDriver());
            wsdCarB.setDriverName(lcglSamplingCarBVO.getCarDriver());
            YwpzUserSEntity ywpzUserSEntity = ywpzUserSDao.selUser(lcglSamplingCarBVO.getCarDriver());
            if(ywpzUserSEntity != null ){
                ywpzUserSEntity.setGpsDevice(lcglSamplingCarBVO.getTachographNo());
                ywpzUserSDao.save(ywpzUserSEntity);
                wsdCarB.setContactPhone(ywpzUserSEntity.getMobilePhone());
            }
            WsdDevB wsdDevB = wsdDevBDao.selDev(lcglSamplingCarBVO.getThermoHygrometerNo());
            if(wsdDevB != null){
                wsdCarB.setDevId(wsdDevB.getId());
            }
            wsdCarB.setTachograph(lcglSamplingCarBVO.getTachographNo());
            wsdCarB.setMark(lcglSamplingCarBVO.getRemark());
            wsdCarBDao.save(wsdCarB);
            logger.info(String.format("\n【" + msg + "成功】车辆编号：%s",lcglSamplingCarBVO.getCarNum()));
   }

    private void updDevs(String sqlParam){
        logger.info("\n------同步【设备】基础数据开始----\n");
        Map<String,String> postMap = Maps.newConcurrentMap();
        postMap.put("devsName",sqlParam);
        String str = HttpClientsUtils.postUrl(url+"sbgl-prj/syn/devs",postMap);
        if(str != null){
            String obj = String.valueOf(JSON.parseObject(str).get("obj"));
            JSON.parseArray(obj).forEach(parseObj->{
                Map<String,Object> columnsMap = (Map<String,Object>)parseObj;
                Map<String,Object> valMap = (Map<String,Object>)columnsMap.get("columns");
                updDev(BeanParseUtils.populate(SbglEqpBVO.class, valMap));
            });
        }
        logger.info("\n------同步【设备】基础数据结束----\n");
    }

    private void updDev(SbglEqpBVO sbglEqpBVO)
    {
            WsdDevB newWsdDevB = wsdDevBDao.findById(sbglEqpBVO.getId()).orElse(null);
            String dutyName = sbglEqpBVO.getDutyPerson();
            //多个责任人选取第一个
            if(StringUtils.isNotBlank(dutyName)){
                dutyName = dutyName.split("、")[0];
            }
            String msg = null;
            if(newWsdDevB == null){
                newWsdDevB = new WsdDevB();
                newWsdDevB.setId(sbglEqpBVO.getId());
                newWsdDevB.setNetSt("WLZT_LX");
                newWsdDevB.setSt("XZ");
                newWsdDevB.setUpVer(1);
                newWsdDevB.setCreateTime(new Date());
                newWsdDevB.setBelongType("WSD");
                newWsdDevB.setAlarmYn("N");
                msg = "新增";
            }else {
                newWsdDevB.setUpdateTime(new Date());
                newWsdDevB.setUpVer(newWsdDevB.getUpVer()+1);
                msg = "更新";
            }
            newWsdDevB.setDevName(sbglEqpBVO.getEqpName());
            newWsdDevB.setDevNo(sbglEqpBVO.getEqpNo());
            newWsdDevB.setCheckCycle(sbglEqpBVO.getMeterCycle());
            newWsdDevB.setLimsSt(sbglEqpBVO.getEqpStatus());
            newWsdDevB.setManufacturer(sbglEqpBVO.getManufacturer());
            YwpzUserSEntity ywpzUserSEntity = ywpzUserSDao.selUser(sbglEqpBVO.getDutyPerson());
            if(ywpzUserSEntity != null ){
                newWsdDevB.setDutyName(dutyName);
                newWsdDevB.setDutyLoginName(dutyName);
                newWsdDevB.setDutyContPhon(ywpzUserSEntity.getMobilePhone());
                newWsdDevB.setDutyEmail(ywpzUserSEntity.getEmail());
                newWsdDevB.setOperatorLname(dutyName);
                newWsdDevB.setOperatorName(dutyName);
                newWsdDevB.setOperContPhon(ywpzUserSEntity.getMobilePhone());
                newWsdDevB.setOperEmail(ywpzUserSEntity.getEmail());
            }
            wsdDevBDao.save(newWsdDevB);
            logger.info(String.format("\n【" + msg + "成功】设备编号：%s 设备名称：%s",sbglEqpBVO.getEqpNo(),sbglEqpBVO.getEqpName()));
       }
}
