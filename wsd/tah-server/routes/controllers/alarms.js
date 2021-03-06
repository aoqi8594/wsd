const Router = require("koa-router");
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
let router = new Router({
    prefix: "alarms",
});
/**
 * 增加报警配置
 */
router.post("/addpz", async(ctx) => {
    let fields = ctx.request.fields;
    console.log(fields);
    let { alarms, devids } = fields;
    alarms["id"] = common.getUUID();
    let keys = [];
    let vals = [];
    for (const i in alarms) {
        keys.push(i);
        vals.push(alarms[i]);
    }
    await ctx.db.query(`INSERT INTO wsd_alarm_cat_b (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
    if (devids && devids.length > 0) {
        for (let i = 0; i < devids.length; i++) {
            await ctx.db.query(`INSERT INTO wsd_dev_alarm_cat_f (id,alarm_cat_id,dev_id) values(${common.getUUID()},${alarms.id},${devids[i]})`);
        }
    }
});
/**
 * 删除报警配置
 */
router.post("/deletepz", async(ctx) => {
    let fields = ctx.request.fields;
    console.log(fields);
    let { ids } = fields;
    let data = await ctx.db.query(`SELECT * FROM wsd_alarm_cat_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
    if (data.length <= 0) {
        throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
    }
    await ctx.db.startTransaction();
    await ctx.db.executeTransaction(`DELETE FROM wsd_alarm_cat_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
    await ctx.db.executeTransaction(`DELETE FROM wsd_dev_alarm_cat_f WHERE alarm_cat_id IN (${ids.map((id) => "?")})`, ids);
    await ctx.db.stopTransaction();
});
/**
 * 编辑报警配置
 */
router.post("/updatepz", async(ctx) => {
            let fields = ctx.request.fields;
            console.log(fields);
            let { alarms, devids } = fields;
            console.log("id" + alarms.id);
            if (alarms.id) {
                let keys = [];
                let vals = [];
                for (const i in alarms) {
                    if (i != "id") {
                        keys.push(i);
                        vals.push(alarms[i]);
                    }
                }
                await ctx.db.startTransaction();
                await ctx.db.executeTransaction(`UPDATE wsd_alarm_cat_b SET ${keys.map((key) => `${key}=?`).join(",")} WHERE ID=?`, [...vals, alarms.id]);
    await ctx.db.executeTransaction(`DELETE FROM wsd_dev_alarm_cat_f WHERE alarm_cat_id = ?`, [alarms.id]);
    if (devids && devids.length > 0) {
      for (let i = 0; i < devids.length; i++) {
        await ctx.db.executeTransaction(`INSERT INTO wsd_dev_alarm_cat_f (id,alarm_cat_id,dev_id) values(${common.getUUID()},${alarms.id},${devids[i]})`);
      }
    }
    await ctx.db.stopTransaction();
  }
});
/**
 * 报警配置列表
 */
router.get("/pzlist", async (ctx) => {
  let { name, page, limit } = ctx.query;
  console.log(ctx.query);
  let params = [(parseInt(page) - 1) * parseInt(limit), parseInt(limit)];
  let datas = await ctx.db.query(`SELECT * FROM wsd_alarm_cat_b WHERE name LIKE '%${name}%' LIMIT ?,?`, [...params]);
  console.log(JSON.stringify(datas));
  let sqlTotal = "select count(*) as total from wsd_alarm_cat_b";
  let total = await ctx.db.query(sqlTotal);
  total = total[0]["total"];
  if (name) {
    let results = await ctx.db.query(`SELECT * FROM wsd_alarm_cat_b WHERE name LIKE '%${name}%'`);
    total = results.length;
  }
  ctx.body = {
    pzlist: datas,
    paging: {
      page: page,
      limit: limit,
      total: total,
    },
  };
});
/**
 * 更新报警配置状态
 */
router.post("/updateAlarmsPzState", async (ctx) => {
  let { ids, st } = ctx.request.fields;
  console.log(ctx.request.fields);
  let datas = await ctx.db.query(`SELECT * FROM wsd_alarm_cat_b WHERE ID IN (${ids})`);
  if (datas.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  await ctx.db.query(`UPDATE wsd_alarm_cat_b SET st=? WHERE ID IN (${ids.map((id) => "?")})`, [st, ...ids]);
});
/**
 * 获取报警配置详情
 */
router.get("/info", async (ctx) => {
  let { id } = ctx.query;
  let rows = await ctx.db.query(`SELECT * FROM wsd_alarm_cat_b WHERE id = ?`, [id]);
  if (rows.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  let devsql = `select b.* from wsd_dev_alarm_cat_f a inner join wsd_dev_b b on a.dev_id = b.id  where a.alarm_cat_id=?`;
  let devs = await ctx.db.query(devsql, [id]);
  rows[0]["devs"] = devs;
  ctx.body = {
    info: rows[0],
  };
});
/**
 * 获取车载温度计报警详情列表
 */
router.get("/getCarOrRoomAlarms", async (ctx) => {
  let { carroom_id } = ctx.query;
  let sql = `select c.*  from wsd_dev_alarm_l c 
  inner join  wsd_icebox_dev_f a on c.dev_id=a.dev_id
  inner join wsd_carroom_icebox_f b on a.icebox_id= b.icebox_id
  where b.carroom_id= ?`;
  let rows = await ctx.db.query(sql, [carroom_id]);
  ctx.body = {
    list: rows,
  };
});
/**
 * 获取所有温度计报警信息
 */
router.get("/getAllAlarms", async (ctx) => {
  let { handleState, startTime, endTime, dev_no, dev_id, page, limit } = ctx.query;
  let params = [(parseInt(page) - 1) * parseInt(limit), parseInt(limit)];
  let sqlTotal = "select count(*) as total from wsd_dev_alarm_l";
  let total = await ctx.db.query(sqlTotal);
  total = total[0]["total"];
  let sql = `SELECT * FROM wsd_dev_alarm_l WHERE 1=1 `;
  if (handleState) {
    sql += `AND st='${handleState}'`;
  }
  if (startTime && endTime) {
    sql += ` AND date_format(create_time,'%Y-%m-%d') between  '${startTime}'  and   '${endTime}'`;
  }
  if (dev_id) {
    sql += ` AND dev_id='${dev_id}'`;
  }
  if (dev_no) {
    sql += ` AND dev_no='${dev_no}'`;
  }
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
/**
 * 获取弹出报警列表
 */
router.get("/getAlarmsTips", async (ctx) => {
  let { type, page, limit } = ctx.query;
  let params = [type];
  let sql = `select * from wsd_dev_alarm_l where id in (
    select aa.id from (select dev_no,alarm_type,max(id) as id from wsd_dev_alarm_l where st=? group by dev_no,alarm_type) aa ) order by create_time desc`;
  let results = await ctx.db.query(sql, [...params]);
  let total = results.length;
  if (page && limit) {
    sql += ` LIMIT ?,?`;
  }
  params = [type, (parseInt(page) - 1) * parseInt(limit), parseInt(limit)]
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
/**
 * 报警处理
 */
router.post("/alarmsHandle", async (ctx) => {
  let fields = ctx.request.fields;
  let { dev_ids, type, content } = fields;
  console.log(fields);
  if (dev_ids.length > 0) {
    await ctx.db.startTransaction();
    await ctx.db.executeTransaction(`UPDATE wsd_dev_alarm_l SET st='YCL' WHERE dev_id IN (${dev_ids.map((id) => "?")}) AND st in ('DCL','CLZ') `, [...dev_ids]);
    for (let i = 0; i < dev_ids.length; i++) {
      let sql = `INSERT INTO wsd_dev_alarm_trea_meth_b (id,dev_id,type,content) VALUES(?,?,?,?)`;
      await ctx.db.executeTransaction(sql, [common.getUUID(), dev_ids[i], type, content]);
      if (type === "TZPL") {
        let sql = `update wsd_dev_b 
        set end_number = acqu_freq1*60/(select param_val_convert FROM wsd_sys_param_c where param_code='PL_CJ2'),
        start_number=0 where id=?`;
        await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET acqu_freq1=? WHERE id =?`, [content, dev_ids[i]]);
        await ctx.db.executeTransaction(`UPDATE wsd_dev_alarm_l SET st='CLZ' WHERE dev_id =?`, [dev_ids[i]]);
        await ctx.db.executeTransaction(sql, [dev_ids[i]]);
      }
    }
    await ctx.db.stopTransaction();
  }
});

/**
 * 报警处理
 */
router.get("/availability", async (ctx) => {
  let { devId } = ctx.query
  let sql = `SELECT ( SELECT count( 1 ) FROM wsd_dev_tah_h WHERE dev_id = ? AND (alarm_yn = 'N' or alarm_yn is null) ) AS normalNum,
            (SELECT count( 1 ) AS allNum FROM wsd_dev_alarm_l WHERE dev_id = ?  ) AS alarmNum`;
  ctx.body = await ctx.db.query(sql, [devId, devId]);
});
// 获取采集可用性
module.exports = router.routes();