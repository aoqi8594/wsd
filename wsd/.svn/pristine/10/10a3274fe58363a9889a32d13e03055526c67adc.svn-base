import http from "@/utils/httpRequest";
export default {
    /**
     * 获取楼温度计列表
     * @param {Object} data
     * @returns 
     */
    getSensorList(data) {
        return http({
            url: http.adornUrl("/equipment/list"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
     * 添加温度计
     * @param {Object} token
     * @returns
     */
    addSensor(data) {
        return http({
            url: http.adornUrl("/equipment/add"),
            method: "post",
            data: http.adornData(data,false),
        });
    },
    /**
    * 删除温度计
    * @param {Object} token
    * @returns
    */
    cancelSensor(data) {
        return http({
            url: http.adornUrl("/equipment/delete"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
    * 改变温度计状态
    * @param {Object} token
    * @returns
    */
    changestate(data) {
        return http({
            url: http.adornUrl("/equipment/updateState"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
    * 改变温度计阈值
    * @param {Object} token
    * @returns
    */
     updateDevThreshold(data) {
        return http({
            url: http.adornUrl("/equipment/updateDevThreshold"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
     * 获取楼温度计详情
     * @param {Object} data
     * @returns 
     */
    getSensordetails(data) {
        return http({
            url: http.adornUrl("/equipment/info"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
   * 更新设备
   * @param {Object} token
   * @returns
   */
    updatefacility(data) {
        return http({
            url: http.adornUrl("/equipment/update"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
     * 退出登录
     */
    logout() { },
};
