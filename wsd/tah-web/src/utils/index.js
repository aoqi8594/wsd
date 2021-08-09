import Cookies from "js-cookie";
const TokenKey = "token";

/**
 * 获取token
 * @param {String} TokenKey
 * @returns
 */
export function getToken() {
  return Cookies.get(TokenKey);
}
/**
 * 设置token
 * @param {String} token
 * @returns
 */
export function setToken(token) {
  return Cookies.set(TokenKey, token);
}
/**
 * 移除token
 * @returns
 */
export function removeToken() {
  return Cookies.remove(TokenKey);
}
/**
 * 树形数据转换
 * @param {*} data
 * @param {*} id
 * @param {*} pid
 */
export function treeDataTranslate(data, id = "id", pid = "parentId") {
  var res = [];
  var temp = {};
  for (var i = 0; i < data.length; i++) {
    temp[data[i][id]] = data[i];
  }
  for (var k = 0; k < data.length; k++) {
    if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
      if (!temp[data[k][pid]]["children"]) {
        temp[data[k][pid]]["children"] = [];
      }
      if (!temp[data[k][pid]]["_level"]) {
        temp[data[k][pid]]["_level"] = 1;
      }
      data[k]["_level"] = temp[data[k][pid]]._level + 1;
      temp[data[k][pid]]["children"].push(data[k]);
    } else {
      res.push(data[k]);
    }
  }
  return res;
}
/**
 * 数组去重
 * @param {Array} a 
 * @param {Array} b 
 * @returns 
 */
export function distinct(a, b) {
  let arr = b ? a.concat(b) :a;
  let result = []
  let obj = {}

  for (let i of arr) {
      if (!obj[i]) {
          result.push(i)
          obj[i] = 1
      }
  }
  return result
}

