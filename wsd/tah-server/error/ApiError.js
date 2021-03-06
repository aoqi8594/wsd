const ApiErrorNames = require("./ApiErrorNames");
/**
 * 自定义Api异常
 */
class ApiError extends Error {
  constructor(error_name, errors) {
    super();
    var error_info = ApiErrorNames.getErrorInfo(error_name);
    this.name = error_name;
    this.code = error_info.code;
    this.message = error_info.message;
    console.log(error_name);
    if (error_name === "ValidateFailed") {
      this.errors = errors;
    }
  }
}
module.exports = ApiError;
