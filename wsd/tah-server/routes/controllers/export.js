const Router = require("koa-router");
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
const fs = require("fs");
const path = require("path")

let router = new Router({
    prefix: "export",
});
/**
 * 添加导出设置
 */
router.post("/addExportSeting", async(ctx) => {
    let fields = ctx.request.fields;
    let { rules, devids } = fields;
    rules["id"] = common.getUUID();
    let keys = [];
    let vals = [];
    for (const i in rules) {
        keys.push(i);
        vals.push(rules[i]);
    }
    await ctx.db.query(`INSERT INTO wsd_export_cat_b (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
    if (devids && devids.length > 0) {
        for (let i = 0; i < devids.length; i++) {
            await ctx.db.query(`INSERT INTO wsd_dev_export_f (id,export_id,dev_id) VALUES(${common.getUUID()},${rules.id},${devids[i]})`);
        }
    }
});
/**
 * 删除导出设置
 */
router.post("/deleteExportSeting", async(ctx) => {
    let fields = ctx.request.fields;
    console.log(fields);
    let { ids } = fields;
    let data = await ctx.db.query(`SELECT * FROM wsd_export_cat_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
    if (data.length <= 0) {
        throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
    }
    await ctx.db.startTransaction();
    await ctx.db.executeTransaction(`DELETE FROM wsd_export_cat_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
    await ctx.db.executeTransaction(`DELETE FROM wsd_dev_export_f WHERE export_id IN (${ids.map((id) => "?")})`, ids);
    await ctx.db.stopTransaction();
});



/**
 * 编辑导出设置
 */
router.post("/updateExportSeting", async(ctx) => {
            let fields = ctx.request.fields;
            let { rules, devids } = fields;
            if (rules.id) {
                let keys = [];
                let vals = [];
                for (const i in rules) {
                    if (i != "id") {
                        keys.push(i);
                        vals.push(rules[i]);
                    }
                }
                await ctx.db.startTransaction();
                await ctx.db.executeTransaction(`UPDATE wsd_export_cat_b SET ${keys.map((key) => `${key}=?`).join(",")} WHERE ID=?`, [...vals, rules.id]);
    await ctx.db.executeTransaction(`DELETE FROM wsd_dev_export_f WHERE export_id = ?`, [rules.id]);
    if (devids && devids.length > 0) {
      for (let i = 0; i < devids.length; i++) {
        await ctx.db.executeTransaction(`INSERT INTO wsd_dev_export_f (id,export_id,dev_id) VALUES(${common.getUUID()},${rules.id},${devids[i]})`);
      }
    }
    await ctx.db.stopTransaction();
  }
});
/**
 * 获取导出设置列表
 */
router.get("/getExportSetingList", async (ctx) => {
  let { page, limit } = ctx.query;
  console.log(ctx.query);
  let now = new Date();
  let params = [now, (parseInt(page) - 1) * parseInt(limit), parseInt(limit)];
  let sqlTotal = "select count(*) as total from wsd_export_cat_b";
  let total = await ctx.db.query(sqlTotal);
  total = total[0]["total"];
  //let datas = await ctx.db.query(`SELECT * FROM wsd_export_cat_b WHERE name LIKE '%${name}%' LIMIT ?,?`, [...params]);
  let datas = await ctx.db.query(`SELECT id,val_type, data_round, export_time, sender_type, mark, (CASE WHEN ?>=export_time THEN 0 ELSE 1 END) isExport FROM wsd_export_cat_b LIMIT ?,?`, [...params]);
  console.log(JSON.stringify(datas));
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
 * 获取导出配置详情
 */
router.get("/info", async (ctx) => {
  let { id } = ctx.query;
  let rows = await ctx.db.query(`SELECT * FROM wsd_export_cat_b WHERE id = ?`, [id]);
  if (rows.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  let devsql = `select b.* from wsd_dev_export_f a inner join wsd_dev_b b on a.dev_id = b.id  where a.export_id=?`;
  let devs = await ctx.db.query(devsql, [id]);
  rows[0]["devs"] = devs;
  ctx.body = {
    info: rows[0],
  };
});
/**
 * 增加导出记录
 */
router.post("/addExportLog", async (ctx) => {
  let log = ctx.request.fields;
  log["id"] = common.getUUID();
  let keys = [];
  let vals = [];
  for (const i in log) {
    keys.push(i);
    vals.push(log[i]);
  }
  await ctx.db.query(`INSERT INTO wsd_export_l (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
});
/**
 * 导出记录列表
 */
router.get("/getExportLog", async (ctx) => {
  let { createTime, page, limit } = ctx.query;
  let params = [(parseInt(page) - 1) * parseInt(limit), parseInt(limit)];
  let sqlTotal = "select count(*) as total from wsd_export_l";
  let total = await ctx.db.query(sqlTotal);
  total = total[0]["total"];
  let sql = `SELECT * FROM wsd_export_l where 1=1 `;
  if (createTime) {
    sql += `AND DATE_FORMAT(create_time, '%Y-%m-%d' ) ='${createTime}'`;
  }
  sql += ` order by create_time desc `;
  let results = await ctx.db.query(sql);
  total = results.length;
  if (page && limit) {
    sql += ` LIMIT ?,?`;
  }
  let rows = await ctx.db.query(sql, [...params]);
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