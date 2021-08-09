import http from "@/utils/httpRequest";
export default {
    /**
     * 校对
     * @param {Object} data
     * @returns 
     */
    getDictionaryList(data) {
        return http({
            url: http.adornUrl("/dictSearch/searchByPcode"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
     * 添加校对记录
     * @param {Object} token
     * @returns
     */
    proofreadadd(data) {
        return http({
            url: http.adornUrl("/proofread/add"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
     * 通过dev_id查询校对记录
     * @param {Object} data
     * @returns 
     */
    getListByDevid(data) {
        return http({
            url: http.adornUrl("/proofread/getListByDevid"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
    * 查询校对记录
    * @param {Object} data
    * @returns 
    */
    getList(data) {
        return http({
            url: http.adornUrl("/proofread/list"),
            method: "get",
            params: http.adornParams(data),
        });
    },
};
