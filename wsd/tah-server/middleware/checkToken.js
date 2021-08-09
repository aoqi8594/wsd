const jwt = require("jsonwebtoken");
const util = require("util");
const verify = util.promisify(jwt.verify);
const common = require("../lib/common");
/**
 * 判断token是否可用
 */
module.exports = function () {
  return async function (ctx, next) {
    // 可以通过ctx.state.user获取payload
    try {
      const token = ctx.header["authorization"];
      if (!!token) {
        // 解析token
        let payload = null;
        try {
          payload = await verify(token.split(" ")[1], common.getSign());
          // 查询改用户是否存在
          let user = await ctx.db.query(`SELECT * FROM user WHERE username=?`, [payload.username]);
          if (!!user) {
            ctx.user = {
              username: payload.username,
              userId: payload.userId,
              role: payload.role,
            };
          } else {
            console.log("解析出来的用户不在数据库中");
          }
        } catch (err) {
          console.log("token解析失败", err);
        }
      }
      await next();
    } catch (error) {
      if (error.status === 401) {
        ctx.status = 401;
        ctx.body = {
          code: 401,
          msg: "认证失败",
        };
      } else {
        ctx.status = 404;
        ctx.body = {
          code: 404,
          msg: "404",
        };
      }
    }
  };
};
