const jwt = require("jsonwebtoken");
const fs = require("fs");
const crypto = require("crypto");
//const { v4: uuidv4 } = require("uuid");

module.exports = {
  /**
   * 生成一个Token
   * @param {payload} 载荷
   * @param {expiresIn} 过期时间
   */
  getToken(payload = {}, expiresIn = "2h") {
    return jwt.sign(payload, this.getSign(), { expiresIn });
  },
  /**
   * 获取签名
   */
  getSign() {
    if (fs.readFileSync(".keys")) {
      return fs.readFileSync(".keys").toString();
    } else {
      return "abcdefghigklmnopqrstuvwxyz0123456789";
    }
  },
  /**
   * md5加密
   * @param {*} buffer
   */
  md5(buffer) {
    let obj = crypto.createHash("md5");
    obj.update(buffer);
    return obj.digest("hex");
  },
  /**
   * 生成UUID
   */
  getUUID() {
    return "xxxxxxxxxxxx".replace(/[x]/g, (c) => {
      return (c === "x" ? (Math.random() * 10) | 1 : "r&0x3" | "0x8").toString(10);
    });
  },
  /**
   * 并集
   * @param {Array} arr1
   * @param {Array} arr2
   */
  arrUnion(arr1 = [], arr2 = []) {
    let a = new Set(arr1);
    let b = new Set(arr2);
    let union = new Set([...a, ...b]);
    return [...union];
  },
  /**
   * 交集
   * @param {Array} arr1
   * @param {Array} arr2
   */
  arrIntersect(arr1 = [], arr2 = []) {
    let a = new Set(arr1);
    let b = new Set(arr2);
    let intersect = new Set([...a].filter((x) => b.has(x)));
    return [...intersect];
  },
  /**
   * 差集
   * @param {Array} arr1
   * @param {Array} arr2
   */
  arrDifference(arr1 = [], arr2 = []) {
    let a = new Set(arr1);
    let b = new Set(arr2);
    let difference = new Set([...a].filter((x) => !b.has(x)));
    return [...difference];
  },
};
