const Router = require("koa-router");
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
let router = new Router({
    prefix: "user",
});
// 用户注册
router.post("/regist", async(ctx) => {
    let fileds = ctx.request.fields;
    try {} catch (err) {}
});
// 用户登录
router.post("/login", async(ctx) => {
    try {
        let { username, password } = ctx.request.fields;
        if (!username || !password) {
            ctx.body = {
                code: -1,
                msg: "参数不合法",
                data: null,
            };
        }
        let datas = await ctx.db.query(`SELECT * FROM user WHERE username=?`, [username]);
        ctx.assert(datas.length, 400, "未找到该用户");
        let row = datas[0];
        if (row.password != common.md5(password + ctx.config.ADMIN_SUFFIX)) {
            ctx.body = {
                code: -1,
                msg: "密码错误",
                data: null,
            };
        } else {
            const userInfo = {
                username: row.username, // 用户名
                userId: row.userId, // 用户id
                role: row.role, // 用户角色
            };
            const token = common.getToken(userInfo);
            ctx.body = {
                code: 0,
                msg: "登录成功",
                data: {
                    username: row.username,
                    userId: row.userId,
                    role: row.role,
                    token: "Bearer " + token,
                },
            };
        }
    } catch (err) {
        throw new Error(err);
    }
});
// 用户信息
router.get("/getUserInfo", async(ctx) => {
    let { userid } = ctx.query;
    console.log(userid)
    let rows = await ctx.db.query(`SELECT * FROM ywpz_user_s WHERE id=?`, [userid]);
    console.log(rows)
    if (rows.length <= 0) {
        ctx.body = {
            code: -1,
            msg: "该用户不存在"
        }
    }
    ctx.body = {
        userInfo: rows
    };
});
/**
 * 获取用户列表
 */
router.get("/getUserList", async(ctx) => {
    let field = ["id", "user_name", "login_name", "mobile_phone", "email"];
    let rows = await ctx.db.query(`SELECT ${field.join(",")} FROM ywpz_user_s`);
    ctx.body = {
        list: rows,
    };
});
/**
 * 获取司机列表
 */
router.get("/getDriverList", async(ctx) => {
    let sql = `SELECT DISTINCT a.id,a.user_name, a.login_name, a.email, a.mobile_phone, a.gps_device FROM ywpz_user_s a
  LEFT JOIN wsd_car_b b ON b.driver_lname = a.login_name AND b.st != 'YWC'
  WHERE b.id is null OR b.st='YWC'`;
    let rows = await ctx.db.query(sql);

    ctx.body = {
        list: rows,
    };
});

module.exports = router.routes();