import http from "@/utils/httpRequest";
export default {
    /**
    * 新增报警配置
    * @param {Object} data
    * @returns
    */
    addalarm(data) {
        return http({
            url: http.adornUrl("/alarms/addpz"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
     * 删除报警配置列表
     * @param {Object} data
     * @returns
     */
    deletealarm(data) {
        return http({
            url: http.adornUrl("/alarms/deletepz"),
            method: "post",
            data: http.adornData(data),
        });
    },
    /**
     * 编辑报警配置
     * @param {Object} data
     * @returns
     */
    updatealarm(data) {
        return http({
            url: http.adornUrl("/alarms/updatepz"),
            method: "post",
            data: http.adornData(data),
        });
    },
    /**
    * 更新报警配置状态
    * @param {Object} data
    * @returns
    */
    updatealarmstate(data) {
        return http({
            url: http.adornUrl("/alarms/updateAlarmsPzState"),
            method: "post",
            data: http.adornData(data),
        });
    },
    /**
     * 获取报警配置列表
     * @param {Object} data
     * @returns
     */
    getalarm(data) {
        return http({
            url: http.adornUrl("/alarms/pzlist"),
            method: "get",
            params: http.adornParams(data),
        });
    }, 
    /**
     * 获取车载温度计报警详情列表
     * @param {Object} data
     * @returns
     */
    getCaralarmlist(data) {
        return http({
            url: http.adornUrl("/alarms/getCarOrRoomAlarms"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
     * 获取所有报警详情列表
     * @param {Object} data
     * @returns
     */
    getAllAlarms(data) {
        return http({
            url: http.adornUrl("/alarms/getAllAlarms"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
    * 获取弹出报警列表
    * @param {Object} data
    * @returns
    */
    getAlarmsTips(data) {
        return http({
            url: http.adornUrl("/alarms/getAlarmsTips"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
     * 获取报警配置详情
     * @param {Object} data
     * @returns
     */
    getAlarmsDetails(data) {
        return http({
            url: http.adornUrl("/alarms/info"),
            method: "get",
            params: http.adornParams(data),
        });
    },
     /**
     * 报警处理
     * @param {Object} data
     * @returns
     */
      alarmsHandle(data) { 
        return http({
            url: http.adornUrl("/alarms/alarmsHandle"),
            method: "post",
            data: http.adornData(data),
        });
    },
};
