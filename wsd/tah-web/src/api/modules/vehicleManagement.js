import http from "@/utils/httpRequest";
export default {
    /**
     * 获取车辆列表
     * @param {Object} data
     * @returns 
     */
    getvehicleList(data) {
        return http({
            url: http.adornUrl("/car/list"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
     * 添加车辆
     * @param {Object} token
     * @returns
     */
    addvehicle(data) {
        return http({
            url: http.adornUrl("/car/add"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
     * 删除车辆，根据车辆ID
     * @param {Object} token
     * @returns
     */
    removevehicle(data) {
        return http({
            url: http.adornUrl("/car/delete"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
     * 新增车辆
     * @param {Object} token
     * @returns
     */
    addcar(data) {
        return http({
            url: http.adornUrl("/car/add"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
    * 修改车辆信息
    * @param {Object} token
    * @returns
    */
    updateCar(data) {
        return http({
            url: http.adornUrl("/car/update"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
     * 查询车辆状态统计
     * @param {Object} data
     * @returns 
     */
    allcar(data) {
        return http({
            url: http.adornUrl("/car/getAllCarState"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
     * 查询所有运行车辆的当前位置
     * @param {Object} data
     * @returns 
     */
    getRunCarPosition(data) {
        return http({
            url: http.adornUrl("/car/getRunCarPosition"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
     * 查询运行车辆的详情
     * @param {Object} data
     * @returns 
     */
    getRunCarInfo(data) {
        return http({
            url: http.adornUrl("/car/getRunCarInfo"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
    * 更新车辆运行状态
    * @param {Object} token
    * @returns
    */
    updateCarRunState(data) {
        return http({
            url: http.adornUrl("/car/updateCarRunState"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
}