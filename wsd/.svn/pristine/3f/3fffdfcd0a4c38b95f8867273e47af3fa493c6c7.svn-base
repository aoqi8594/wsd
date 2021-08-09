import http from "@/utils/httpRequest";
export default {
    /**
     * 获取数据字典
     * @param {Object} data parent_code
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
      * 获取用户列表
      * @param {Object} data parent_code
      * @returns 
      */
    getUserList(data) {
        return http({
            url: http.adornUrl("/user/getUserList"),
            method: "get",
            params: http.adornParams(data),
        });
    },
};
