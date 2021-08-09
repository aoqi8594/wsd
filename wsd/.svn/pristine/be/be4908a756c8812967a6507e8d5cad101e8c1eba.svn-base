package com.qzsoft.tah.service.impl;

import com.qzsoft.tah.dao.WsdDevTahHDao;
import com.qzsoft.tah.entity.WsdExportCatB;
import com.qzsoft.tah.service.WsdDevTahHService;
import com.qzsoft.tah.utils.JacksonUtil;
import com.qzsoft.tah.vo.DevExportDataVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 设备历史记录表(WsdDevTahH)表服务实现类
 *
 * @author makejava
 * @since 2021-04-26 15:02:49
 */
@Service("wsdDevTahHService")
public class WsdDevTahHServiceImpl implements WsdDevTahHService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private WsdDevTahHDao wsdDevTahHDao;

    @Override
    public List<DevExportDataVo> queryDevExportData(List<Long> devIds, WsdExportCatB b) {
        String valType = b.getValType(); //取值方式
        Integer hour = getHour(b);// 记录时间段
        Integer dataRound = getDataRound(b); // 数据修约
        LocalDateTime startTime = b.getStartTime(); // 开始时间
        LocalDateTime endTime = b.getEndTime(); // 开始时间
        List<Map<String, Object>> maps;
        switch (valType) {
            case "DCQZFS_PJZ":
                maps = wsdDevTahHDao.queryDevExportDataByAvg(devIds, hour, startTime, endTime);break;
            case "DCQZFS_ZJZ":
                maps = wsdDevTahHDao.queryDevExportDataByZJZ(devIds, hour, startTime, endTime);break;
            case "DCQZFS_JBZ":
                // 查询总条数
                Long count = wsdDevTahHDao.queryDevTahHCount(devIds);
                maps = wsdDevTahHDao.queryDevExportDataByJBZ(devIds, hour, count, startTime, endTime);break;
            default:
                log.error("取值方式={}不存在，请检查配置", valType);
                maps = new ArrayList<>();
        }
        // 调整数据
        if (!CollectionUtils.isEmpty(maps)) {
            List<DevExportDataVo> res = JacksonUtil.parseBeanList(JacksonUtil.toJson(maps), DevExportDataVo.class);
            if (dataRound == 0) {
                res = res.stream().peek(o -> o.setValue(new BigDecimal(o.getValue()).intValue() + "")).collect(Collectors.toList());
            } else if (dataRound > 0 && dataRound <= 3) {
                res = res.stream().peek(o -> o.setValue(new BigDecimal(o.getValue()).setScale(dataRound, BigDecimal.ROUND_FLOOR).toString())).collect(Collectors.toList());
            } else {
                log.error("数据修约不符合规定={}不存在，请检查配置", valType);
                return new ArrayList<>();
            }
            return res;
        }
        return new ArrayList<>();
    }

    public Integer getHour(WsdExportCatB b) {
        switch (b.getRecordTime()) {
            case "DCJLSJ_ONEXS":
                return 1;
            case "DCJLSJ_THIRXS":
                return 3;
            case "DCJLSJ_SIXXS":
                return 6;
            default:
                return Integer.parseInt(b.getCustRecordTime());
        }
    }

    public Integer getDataRound(WsdExportCatB b) {
        switch (b.getDataRound()) {
            case "DCSJXY_XSW1":
                return 1;
            case "DCSJXY_XSW2":
                return 2;
            case "DCSJXY_XSW3":
                return 3;
            case "DCSJXY_WXSW":
                return 0;
            default:
                return -1;
        }
    }
}