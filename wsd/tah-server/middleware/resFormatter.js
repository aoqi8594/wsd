var ApiError = require("../error/ApiError");
/**
 * 统一返回数据格式
 * @param {*} ctx
 * @param {*} next
 */
var response_formatter = (ctx) => {
    if (ctx.body) {
        ctx.body = {
            code: 0,
            msg: "操作成功！",
            data: ctx.body,
        };
    } else {
        ctx.body = {
            code: 0,
            msg: "操作成功！",
        };
    }
};
var url_filter = (pattern) => {
    return async(ctx, next) => {
        var reg = new RegExp(pattern);
        try {
            await next();
        } catch (error) {
            //如果异常类型是API异常并且通过正则验证的url，将错误信息添加到响应体中返回。
            if (error instanceof ApiError && reg.test(ctx.originalUrl)) {
                ctx.status = 200;
                if (error.errors) {
                    ctx.body = {
                        code: error.code,
                        msg: error.message,
                        errors: error.errors,
                    };
                } else {
                    ctx.body = {
                        code: error.code,
                        msg: error.message,
                    };
                }

                return;
            }
            //继续抛，让外层中间件处理日志
            throw error;
        }
        //通过正则的url进行格式化处理
        if (reg.test(ctx.originalUrl)) {
            response_formatter(ctx);
        }
    };
};

module.exports = url_filter;