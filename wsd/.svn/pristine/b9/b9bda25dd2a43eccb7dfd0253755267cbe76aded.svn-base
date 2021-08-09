import http from "@/utils/httpRequest";
export default {
    /**
     * 查询历史数据
     * @param {Object} data
     * @returns 
     */
    getHistoryList(data) {
        return http({
            url: http.adornUrl("/history/list"),
            method: "get",
            params: http.adornParams(data),
        });
    },
   
};
