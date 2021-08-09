const Router = require("koa-router");
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
let router = new Router({
  prefix: "dictSearch",
});
/**
 * 根据parent_code查询字典
 */
router.get("/searchByPcode", async (ctx) => {
  let { parent_code } = ctx.query;
  let rows = await ctx.db.query(`SELECT code,val FROM sz_dic2_b where parent_code =? order by  disp_or;`, [parent_code]);
  if (rows.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  ctx.body = {
    list: rows,
  };
});
module.exports = router.routes();
