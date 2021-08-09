const Koa = require("koa");
const app = new Koa();
const views = require("koa-views");
const json = require("koa-json");
const onerror = require("koa-onerror");
const body = require("koa-better-body");
const db = require("./lib/dbpro");
const convert = require("koa-convert");
const logger = require("koa-logger");
const log4js = require("./logger/log4js");
const cors = require("koa2-cors");
const jwtkoa = require("koa-jwt");
const path = require("path");
const fs = require("fs");
const Moment = require("moment");
const validate = require("koa-better-validation");
const routers = require("./routes");
const resFormatter = require("./middleware/resFormatter");
const config = require("./config");
const common = require("./lib/common");
const checkToken = require("./middleware/checkToken");

// error handler 优化错误信息
onerror(app);
// 解决跨域问题
app.use(cors());
//  解析请求体
app.use(
  convert(
    body({
      uploadDir: config.UPLOAD_DIR,
      keepExtensions: "true",
    })
  )
);
app.use(convert(validate()));

//美观的输出JSON response的Koa中间件
app.use(json());
// 日志
app.use(
  logger((str) => {
    console.log(Moment().format("YYYY-MM-DD HH:mm:ss") + str);
  })
);

//指定视图的渲染模板类型
app.use(
  views(__dirname + "/views", {
    extension: "pug",
  })
);

// 错误处理
app.use(async (ctx, next) => {
  return next().catch((err) => {
    if (err.status === 401) {
      ctx.status = 401;
      ctx.type = "text/html;charset=utf-8";
      ctx.body = `受保护的资源，请<a href='#' style='padding:0 5px;color:#e4393c;'>登录</a>使用授权标头获取访问权限`;
    } else {
      throw err;
    }
  });
});
// 路由拦截是否需要token
// 设置除了私有接口外的其它资源，可以不需要认证访问
// app.use(
//   jwtkoa({ secret: common.getSign() }).unless({
//     path: [/^\/api\/user\/login/, /^\/api\/user\/register/, /^((?!\/api).)*$/],
//   })
// );
// 验证token有无效
//app.use(checkToken());
// 数据库操作
app.context.db = db;
app.context.config = config;
//仅对/api开头的url进行格式化处理
app.use(resFormatter("^/api"));
// routes
app.use(routers.routes(), routers.allowedMethods());
//用于koa的静态文件指定映射路径
app.use(require("koa-static")(config.STATIC_PATH));
app.use(require("koa-static")(path.join(__dirname, "./static/")));
// error-handling
//在try-catch中可以使用ctx.app.emit('error', e, ctx)抛出错误；
//或者使用ctx.app.emit('error-info', e, ctx)抛出info错误
app.on("error", (err, ctx) => {
  console.log("接口错误********" + err);
  log4js.logError(err);
});
app.on("error-info", (err, ctx) => {
  log4js.logInfo(err);
});
setInterval(function () {
  db.query(`select 1`);
}, 9000);
module.exports = app;
