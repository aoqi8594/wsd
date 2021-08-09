import axios from "axios";
import router from "@/router";
import qs from "qs";
import merge from "lodash/merge";
import store from "@/store";
import { getToken } from "@/utils";
import { MessageBox, Message } from "element-ui";
const http = axios.create({
  //withCredentials: true,
  timeout: 1000 * 30,
  headers: {
    "Content-Type": "application/json; charset=utf-8",
  },
});
// console.log(process.env);
/**
 * 请求拦截
 */
http.interceptors.request.use(
  (config) => {
    if(getToken()){
      config.headers["token"] = getToken();
    }
    return config;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  }
);
/**
 * 响应拦截
 */
http.interceptors.response.use(
  (response) => {
    const res = response.data;
    if (res && res.code === 401) {
      // 401, token失效
      MessageBox.confirm("当前登录失效", "Confirm logout", {
        confirmButtonText: "重新登录",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        store.dispatch("user/resetToken").then(() => {
          router.push({ name: "login" });
        });
      });
      return Promise.reject(new Error(res.msg || "Error"));
    }
    return res;
  },
  (error) => {
    console.log("err" + error); // for debug
    Message({
      message: error.message,
      type: "error",
      duration: 5 * 1000,
    });
    return Promise.reject(error);
  }
);
/**
 * 请求地址处理
 * @param {*} actionName action方法名称
 */
http.adornUrl = (actionName) => {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  // return (process.env.NODE_ENV !== "production" && process.env.OPEN_PROXY ? "/proxyApi/" : window.SITE_CONFIG.baseUrl) + actionName;
  return window.SITE_CONFIG.baseUrl +actionName
};
/**
 * get请求参数处理
 * @param {*} params 参数对象
 * @param {*} openDefultParams 是否开启默认参数?
 */
http.adornParams = (params = {}, openDefultParams = false) => {
  var defaults = {
    t: new Date().getTime(),
  };
  return openDefultParams ? merge(defaults, params) : params;
};

/**
 * post请求数据处理
 * @param {*} data 数据对象
 * @param {*} openDefultdata 是否开启默认数据?
 * @param {*} contentType 数据格式
 *  json: 'application/json; charset=utf-8'
 *  form: 'application/x-www-form-urlencoded; charset=utf-8'
 */
http.adornData = (data = {}, openDefultdata = false, contentType = "json") => {
  
  var defaults = {
    t: new Date().getTime(),
  };
  data = openDefultdata ? merge(defaults, data) : data;
  return contentType === "json" ? JSON.stringify(data) : qs.stringify(data);
};
export default http;
