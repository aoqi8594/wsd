import http from "@/utils/httpRequest";
export default {
    /**
    * 各时间段温湿度统计接口
    * @param {Object} data
    * @returns
    */
    // getDevTimePointChart(data) {
    //     return http({
    //         url: http.adornUrl("/alarms/addpz"),
    //         method: "post",
    //         data: http.adornData(data, false),
    //     });
    // },
   
    /**
     * 各时间段温湿度统计接口
     * @param {Object} data
     * @returns
     */
    getDevTimePointChart(data) {
        return http({
            url: http.adornUrl("/chart/getDevTimePointChart"),
            method: "get",
            params: http.adornParams(data),
        });
    }, 
    /**
     * 数据正确率统计接口
     * @param {Object} data
     * @returns
     */
    getDevAccChart(data) {
        return http({
            url: http.adornUrl("/chart/getDevAccChart"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
     * 数据可用性统计接口
     * @param {Object} data
     * @returns
     */
    getAvailabilityChart(data) {
        return http({
            url: http.adornUrl("/alarms/availability"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
     * 获取谋层楼的报警次数统计
     * @param {Object} data
     * @returns
     */ 
    getFloorCountChart(data) {
        return http({
            url: http.adornUrl("/chart/getFloorCountChart"),
            method: "get",
            params: http.adornParams(data),
        });
    },
};
