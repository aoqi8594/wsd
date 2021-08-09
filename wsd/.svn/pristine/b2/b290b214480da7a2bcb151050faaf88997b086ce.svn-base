package com.qzsoft.tah.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.qzsoft.tah.dao.WsdCarGpsLDao;
import com.qzsoft.tah.entity.WsdCarB;
import com.qzsoft.tah.entity.WsdCarGpsL;
import com.qzsoft.tah.service.WsdCarGpsLService;
import com.qzsoft.tah.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 温湿度车辆定位记录(WsdCarGpsL)表服务实现类
 *
 * @author makejava
 * @since 2021-05-10 15:56:41
 */
@Service("wsdCarGpsLService")
public class WsdCarGpsLServiceImpl implements WsdCarGpsLService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private WsdCarGpsLDao wsdCarGpsLDao;

    @Resource
    private WsdCarGpsLService wsdCarGpsLService;

    @Override
    @DS("sqlserver")
    public void syncDeviceGps(List<WsdCarB> list) {
        LocalDateTime now = LocalDateTime.now();
        for (WsdCarB wcb : list) {
            LocalDateTime createTime = wcb.getCreateTime();
            List<Map<String, Object>> maps = wsdCarGpsLDao.queryGpsData(wcb.getTachograph(), createTime);
            if (CollectionUtils.isEmpty(maps)) {
                log.info("当前车辆【{}】未有任何行动轨迹",wcb.getCarNo());
                continue;
            }
            List<WsdCarGpsL> res = JacksonUtil.parseBeanList(JacksonUtil.toJson(maps), WsdCarGpsL.class);
            wsdCarGpsLService.saveGpsData(res, wcb.getId(), now);
        }
    }

    @Override
    @DS("mysql")
    public void saveGpsData(List<WsdCarGpsL> list, Long carTaskId, LocalDateTime now) {
        // 查询本地库已存在的记录
        List<WsdCarGpsL> gpsLS = wsdCarGpsLDao.findWsdCarGpsLSByCarTaskId(carTaskId);
        if (list.size() > gpsLS.size()) {
            // 获取条数差的数据， list已经排好序，取最前面的就可以
            int num = list.size() - gpsLS.size();
            List<WsdCarGpsL> ls = list.subList(0, num);
            ls.forEach(wsdCarGpsL -> {
                wsdCarGpsL.setSyncTime(now);
                wsdCarGpsL.setCarTaskId(carTaskId);
            });
            // 存入本地库
            wsdCarGpsLDao.saveAll(ls);
        }
    }
}