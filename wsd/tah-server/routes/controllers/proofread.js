const Router = require("koa-router");
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
let router = new Router({
  prefix: "proofread",
});
/**
 * 增加校对记录
 */
router.post("/add", async (ctx) => {
  let fields = ctx.request.fields;
  console.log(fields);
  for (let i = 0; i < fields.length; i++) {
    let keys = [];
    let vals = [];
    fields[i].id = common.getUUID();
    for (const j in fields[i]) {
      keys.push(j);
      vals.push(fields[i][j]);
    }
    await ctx.db.query(`INSERT INTO wsd_dev_check_l (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
  }
});
/**
 * 删除校对记录
 */
router.post("/delete", async (ctx) => {
  let fields = ctx.request.fields;
  console.log(fields);
  let { ids } = fields;
  let data = await ctx.db.query(`SELECT * FROM wsd_dev_check_l WHERE ID IN (${ids.map((id) => "?")})`, ids);
  if (data.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  await ctx.db.query(`DELETE FROM wsd_dev_check_l WHERE ID IN (${ids.map((id) => "?")})`, ids);
});
/**
 * 查询校对记录列表
 */
router.get("/list", async (ctx) => {
  let { dev_name, page, limit } = ctx.query;
  let params = [];
  let sqlTotal = "select count(*) as total from wsd_dev_check_l";
  let total = await ctx.db.query(sqlTotal);
  total = total[0]["total"];
  let sql = `select a.*,c.* from  wsd_dev_check_l a
  left join wsd_dev_b c on a.dev_id = c.id 
  where 1=1 `;
  if (dev_name) {
    sql += ` and c.dev_name like ${"'%" + dev_name + "%'"}`;
  }
  // if (check_time) {
  //   sql += " and DATE_FORMAT(c.check_time,'yyyy-MM-dd') = ?";
  // }
  let results = await ctx.db.query(sql, params);
  total = results.length;
  if (page && limit) {
    sql += " ORDER BY create_time desc LIMIT ?,?";
    params = [(parseInt(page) - 1) * parseInt(limit), parseInt(limit)];
  }
  let datas = await ctx.db.query(sql, params);

  if (datas.length < 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  ctx.body = {
    list: datas,
    paging: {
      page: page,
      limit: limit,
      total: total,
    },
  };
});
/**
 * 根据温度计id校对记录
 */
router.get("/getListByDevid", async (ctx) => {
  let { dev_id } = ctx.query;
  let sql = `SELECT * FROM wsd_dev_check_l  WHERE dev_id=?`;
  let rows = await ctx.db.query(sql, [dev_id]);
  ctx.body = {
    list: rows,
  };
});
module.exports = router.routes();
