import http from "@/utils/httpRequest";
export default {
    /**
     * 获取楼栋列表
     * @param {Object} data
     * @returns 
     */
    getBuildList(data) {
        return http({
            url: http.adornUrl("/building/list"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
     * 添加楼栋
     * @param {Object} token
     * @returns
     */
    addbuilding(data) {
        return http({
            url: http.adornUrl("/building/add"),
            method: "post",
            data: http.adornData(data,false),
        });
    },
    /**
    * 移除楼栋
    * @param {Object} token
    * @returns
    */
    removebuilding(data) {
        return http({
            url: http.adornUrl("/building/delete"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
    /**
   * 查询谋楼栋某楼层的所有房间
   * @param {Object} data
   * @returns
   */
    buildingfloor(data) {
        return http({
            url: http.adornUrl("/room/getListBybuiidAndfloor"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
   * 保存修改删除房间
   * @param {Object} data
   * @returns
   */
    roommanage(data) {
        return http({
            url: http.adornUrl("/room/manage"),
            method: "post",
            data: http.adornData(data),
        });
    },
    /**
  * 删除楼层
  * @param {Object} data
  * @returns
  */
    roommanage(data) {
        return http({
            url: http.adornUrl("/room/manage"),
            method: "post",
            data: http.adornData(data),
        });
    },
    /**
 * 更新楼栋
 * @param {Object} data
 * @returns
 */
    updateFloor(data) {
        return http({
            url: http.adornUrl("/building/update"),
            method: "post",
            data: http.adornData(data,false),
        });
    },
    /**
  * 通过房间id获取房间详情
  * @param {Object} data
  * @returns
  */
    buildingdetails(data) {
        return http({
            url: http.adornUrl("/room/getRoomInfo"),
            method: "get",
            params: http.adornParams(data),
        });
    },
    /**
* 房间设备管理
* @param {Object} data
* @returns
*/
    editRoomInfo(data) {
        return http({
            url: http.adornUrl("/room/editRoomInfo"),
            method: "post",
            data: http.adornData(data, false),
        });
    },
     /**
      * 查询所有房间， 报警优先
      * @param {Object} data
      * @returns
      */
     roomhome(data) {
         return http({
             url: http.adornUrl("/room/getAlarmsRoom"),
             method: "get",
             params: http.adornParams(data),
         });
     },
      /**
       * 查询所有房间， 报警优先
       * @param {Object} data
       * @returns
       */
       allroom(data) {
          return http({
              url: http.adornUrl("/room/getAllRoomState"),
              method: "get",
              params: http.adornParams(data),
          });
      },
    /**
     * 退出登录
     */
    logout() { },
};
