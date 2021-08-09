import http from "@/utils/httpRequest";
export default {
  /**
   * 用户登录
   * @param {Object} data
   * @returns 
   */
  login(data) {
    return http({
      url: http.adornUrl("/login"),
      method: "post",
      data: http.adornData(data),
    });
  },
  /**
   * 获取用户信息
   * @param {Object} token
   * @returns
   */
  getUserInfo(token) {
    return http({
      url: http.adornUrl("/user/info"),
      method: "get",
      params: http.adornParams({ token }),
    });
  },
  /**
   * 获取司机列表
   * @param {Object} token
   * @returns
   */
  getDriverList(token) {
    return http({
      url: http.adornUrl("/user/getDriverList"),
      method: "get",
      params: http.adornParams({ token }),
    });
  },
  /**
   * 退出登录
   */
  logout() {},
};
