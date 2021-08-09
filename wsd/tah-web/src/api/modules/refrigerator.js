import http from "@/utils/httpRequest";
export default {

  /**
   * 查询房间或者车辆冰箱以及冰箱温度计
   * @param {Object} data
   * @returns
   */
  getrefrigeratorlist(data) {
    return http({
      url: http.adornUrl("/icebox/getIceboxByid"),
      method: "get",
      params: http.adornParams(data),
    });
  },
  
};
