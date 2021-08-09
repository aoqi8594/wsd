/**
 * API错误名称
 */
var ApiErrorNames = {};
ApiErrorNames.UNKNOW_ERROR = "unknowError";
ApiErrorNames.USER_NOT_EXIST = "userNotExist";
ApiErrorNames.PARAMETER_ERROR = "parameterError";
ApiErrorNames.NOT_FIND_RESULT = "notFindResult";
ApiErrorNames.OPERATION_FAILED = "operationFailed";
ApiErrorNames.NOT_TOKEN = "notToKEN";
ApiErrorNames.EXISTS = "exists";
ApiErrorNames.ValidateFailed = "ValidateFailed";
ApiErrorNames.BUILDING_EXISTS = "buildingExists";
/**
 * API错误名称对应的错误信息
 */
const error_map = new Map();
/**
 * 系统级别错误
 */
error_map.set(ApiErrorNames.UNKNOW_ERROR, { code: -1, message: "未知错误" });
error_map.set(ApiErrorNames.NOT_TOKEN, { code: -101, message: "token解析失败" });

/**
 * 服务级别错误
 */
error_map.set(ApiErrorNames.PARAMETER_ERROR, { code: 202101, message: "参数错误" });
error_map.set(ApiErrorNames.NOT_FIND_RESULT, { code: 202102, message: "未查询到结果" });
error_map.set(ApiErrorNames.USER_NOT_EXIST, { code: 101, message: "用户不存在" });
error_map.set(ApiErrorNames.OPERATION_FAILED, { code: 102, message: "操作失败" });
error_map.set(ApiErrorNames.EXISTS, { code: -200, message: "已存在" });
error_map.set(ApiErrorNames.ValidateFailed, { code: 400, message: "验证不通过" });
error_map.set(ApiErrorNames.BUILDING_EXISTS, { code: 400, message: "楼栋已存在" });
/**
 * 根据错误名称获取错误信息
 */
ApiErrorNames.getErrorInfo = (error_name) => {
  var error_info;
  if (error_name) {
    error_info = error_map.get(error_name);
  }
  //如果没有对应的错误信息，默认为未知错误
  if (!error_info) {
    error_name = UNKNOW_ERROR;
    error_info = error_map.get(error_name);
  }
  return error_info;
};

module.exports = ApiErrorNames;
