import http from "@/utils/httpRequest";
export default {
    /**
    * 添加导出设置
    * @param {Object} data
    * @returns
    */
    addexportlist(data) {
        return http({
            url: http.adornUrl("/export/addExportSeting"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
      * 获取导出设置列表
      * @param {Object} data
      * @returns
      */
    getexportlist(data) {
        return http({
            url: http.adornUrl("/export/getExportSetingList"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
      * 获取导出配置详情
      * @param {Object} data
      * @returns
      */
    getexportdetails(data) {
        return http({
            url: http.adornUrl("/export/info"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
      * 删除导出设置
      * @param {Object} data
      * @returns
      */
    deleteexportset(data) {
        return http({
            url: http.adornUrl("/export/deleteExportSeting"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
      * 编辑导出设置
      * @param {Object} data
      * @returns
      */
    updateexportset(data) {
        return http({
            url: http.adornUrl("/export/updateExportSeting"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
     * 获取导出记录列表
     * @param {Object} data
     * @returns
     */
    getExportLog(data) {
        return http({
            url: http.adornUrl("/export/getExportLog"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
      * 添加导出记录
      * @param {Object} data
      * @returns
      */
    addexportrecord(data) {
        return http({
            url: http.adornUrl("/export/addExportLog"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
      * 获取导出路径
      * @param {Object} data
      * @returns
      */
    getexportpath(data) {
        return http({
            url: http.adornUrl("/export/getPath"),
            method: "get",
            params: http.adornParams(data),
            responseType: 'blob'
        });
    },
};
