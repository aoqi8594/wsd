/**
 * 设备管理
 */
const Router = require("koa-router");
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
let router = new Router({
    prefix: "equipment",
});
/**
 * 新增设备
 */
router.post("/add", async(ctx) => {
    let fields = ctx.request.fields;
    fields.id = common.getUUID();
    console.log(fields);
    let sql = `update wsd_dev_b 
   set end_number = acqu_freq1*60/(select param_val_convert FROM wsd_sys_param_c where param_code='PL_CJ2'),
   start_number=0 where id=?`;
    let keys = [];
    let vals = [];
    for (const i in fields) {
        keys.push(i);
        vals.push(fields[i]);
    }
    try {
        await ctx.db.query(`INSERT INTO wsd_dev_b (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
        await ctx.db.query(sql, [fields.id]);
    } catch (error) {
        throw error;
    }
});

/**
 * 删除设备
 */
router.post("/delete", async(ctx) => {
    let fields = ctx.request.fields;
    console.log(fields);
    let { ids } = fields;
    let data = await ctx.db.query(`SELECT * FROM wsd_dev_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
    if (data.length <= 0) {
        throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
    }
    await ctx.db.query(`DELETE FROM wsd_dev_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
});
/**
 *编辑设备
 */
router.post("/update", async(ctx) => {
            let fields = ctx.request.fields;
            let { id, acqu_freq1 } = fields;
            let sql = `update wsd_dev_b 
  set end_number = acqu_freq1*60/(select param_val_convert FROM wsd_sys_param_c where param_code='PL_CJ2'),
  start_number=0 where id=?`;
            let rows = await ctx.db.query(`SELECT * FROM wsd_dev_b WHERE ID=?`, [id]);
            if (rows.length <= 0) {
                throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
            }
            let keys = [];
            let vals = [];
            for (const i in fields) {
                keys.push(i);
                vals.push(fields[i]);
            }
            await ctx.db.query(`UPDATE wsd_dev_b SET ${keys.map((key) => `${key}=?`).join(",")} WHERE ID=?`, [...vals, id]);
  if (acqu_freq1 && rows[0].acqu_freq1 != acqu_freq1) {
    await ctx.db.query(sql, [id]);
  }
});
/**
 * 更新设备状态
 */
router.post("/updateState", async (ctx) => {
  let { ids, st, start_time, end_time } = ctx.request.fields;
  console.log(ctx.request.fields);
  let datas = await ctx.db.query(`SELECT * FROM wsd_dev_b WHERE ID IN (${ids})`);
  if (datas.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  await ctx.db.query(`UPDATE wsd_dev_b SET st=?,start_time=?,end_time=? WHERE ID IN (${ids.map((id) => "?")})`, [st, start_time, end_time, ...ids]);
});
/**
 * 更新设备阈值
 */
router.post("/updateDevThreshold", async (ctx) => {
  let fields = ctx.request.fields;
  let { id, temp_up, temp_low, hum_up, hum_low } = fields;
  let rows = await ctx.db.query(`SELECT temperature,humidity,alarm_yn FROM wsd_dev_b WHERE id =?`, [id]);
  if (rows.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  let temperature = rows[0].temperature;
  let humidity = rows[0].humidity;
  let alarm_yn = rows[0].alarm_yn;
  let flag = false;
  let keys = [];
  let vals = [];
  for (const i in fields) {
    if (i === "id") {
      continue;
    }
    keys.push(i);
    vals.push(fields[i]);
  }
  if ((!isNaN(temp_low) && !isNaN(temp_up) && temperature < temp_low) || temperature > temp_up) {
    flag = true;
  }
  if ((!isNaN(hum_low) && !isNaN(hum_up) && humidity < hum_low) || humidity > hum_up) {
    flag = true;
  }
  // 更新温度计报警状态
  if (flag) {
    if (alarm_yn === "N") {
      keys.push("alarm_yn");
      vals.push("Y");
    }
  } else {
    if (alarm_yn === "Y") {
      keys.push("alarm_yn");
      vals.push("N");
    }
  }
  console.log(fields);
  console.log(keys);
  console.log(vals);
  await ctx.db.query(`UPDATE wsd_dev_b SET ${keys.map((key) => `${key}=?`).join(",")} WHERE ID=?`, [...vals, id]);
});

/**
 * 查询设备
 */
router.get("/list", async (ctx) => {
  let { dev_name, st, use_type, dev_no, jbz, page, limit } = ctx.query;
  let params = [];
  let sqlTotal = "select count(*) as total from wsd_dev_b";
  let total = await ctx.db.query(sqlTotal);
  total = total[0]["total"];
  let sql = `SELECT * FROM wsd_dev_b WHERE 1=1 `;
  let content = [];
  if (dev_name) {
    sql += "AND dev_name LIKE ?";
    content.push("%" + dev_name + "%");
  }
  if (st) {
    sql += "and st= ?";
    content.push(st);
  }
  if (use_type) {
    sql += "and use_type= ?";
    content.push(use_type);
  }
  if (dev_no) {
    sql += "and dev_no= ?";
    content.push(dev_no);
  }
  if (jbz) {
    sql += "and use_type in ('TPJ','LK','CWK')";
  }
  let results = await ctx.db.query(sql, [...content]);
  total = results.length;
  if (page && limit) {
    sql += " ORDER BY create_time desc LIMIT ?,?";
    params = [(parseInt(page) - 1) * parseInt(limit), parseInt(limit)];
  }

  let datas = await ctx.db.query(sql, [...content, ...params]);

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
 * 查询设备详情
 */
router.get("/info", async (ctx) => {
  let { id } = ctx.query;
  let info = await ctx.db.query(`SELECT * FROM wsd_dev_b WHERE ID=?`, [id]);
  ctx.body = {
    equipmentInfo: info,
  };
});

/**
 * 检测设备字段是否存在
 */
router.get("/fieldCheck",async(ctx)=>{
  let {dev_mac,dev_no} = ctx.query;
  let rows = null;
  let isExist=false;
  if(dev_mac){
   rows = await ctx.db.query(`SELECT dev_mac FROM wsd_dev_b WHERE dev_mac=?`,[dev_mac]);
  }
  if(dev_no){
    rows = await ctx.db.query(`SELECT dev_mac FROM wsd_dev_b WHERE dev_no=?`,[dev_no]);
  }
  if(rows.length>0){
    isExist = true
  }else{
    isExist = false
  }
  ctx.body = {
    isExist
  }

})
module.exports = router.routes();