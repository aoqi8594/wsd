const Router = require("koa-router");
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
let router = new Router({
  prefix: "history",
});
/**
 * 查询历史数据
 */
router.get("/list", async (ctx) => {
  let { belong_type, use_type, dev_no, dev_name, alarm_yn, startTime, endTime, page, limit } = ctx.query;
  console.log(ctx.query);
  let params = [(parseInt(page) - 1) * parseInt(limit), parseInt(limit)];
  let sqlTotal = "select count(*) as total from wsd_dev_tah_h";
  let total = await ctx.db.query(sqlTotal);
  total = total[0]["total"];
  let querySql = `SELECT * FROM wsd_dev_tah_h WHERE 1=1 `;
  if (belong_type) {
    querySql += `AND belong_type='${belong_type}'`;
  }
  if (use_type) {
    querySql += `AND use_type='${use_type}'`;
  }
  if (dev_no) {
    querySql += `AND dev_no='${dev_no}'`;
  }
  if (dev_name) {
    querySql += `AND dev_name='${dev_name}'`;
  }
  if (alarm_yn) {
    querySql += `AND alarm_yn='${alarm_yn}'`;
  }
  if (startTime && endTime) {
    querySql += ` AND date_format(create_time,'%Y-%m-%d') between  '${startTime}'  and   '${endTime}'`;
  }
  let results = await ctx.db.query(querySql);
  total = results.length;
  if (page && limit) {
    querySql += ` LIMIT ?,?`;
  }
  let rows = await ctx.db.query(querySql, [...params]);

  ctx.body = {
    list: rows,
    paging: {
      page: page,
      limit: limit,
      total: total,
    },
  };
});

module.exports = router.routes();
