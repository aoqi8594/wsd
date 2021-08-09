/**
 * 冰柜管理
 */
const Router = require("koa-router");
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
let router = new Router({
  prefix: "icebox",
});
/**
 * 新增冰柜
 */
router.post("/add", async (ctx) => {
  let fields = ctx.request.fields;
  fields.id = common.getUUID();
  console.log(fields);
  let keys = [];
  let vals = [];
  for (const i in fields) {
    keys.push(i);
    vals.push(fields[i]);
  }
  await ctx.db.query(`INSERT INTO wsd_icebox_b (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
});
/**
 * 删除冰柜
 */
router.post("/delete", async (ctx) => {
  let fields = ctx.request.fields;
  console.log(fields);
  let { ids } = fields;
  let data = await ctx.db.query(`SELECT * FROM wsd_icebox_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
  if (data.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  await ctx.db.query(`DELETE FROM wsd_icebox_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
});
/**
 *编辑冰柜
 */
router.post("/update", async (ctx) => {
  let fields = ctx.request.fields;
  let { id } = fields;
  let rows = await ctx.db.query(`SELECT * FROM wsd_icebox_b WHERE ID=?`, [id]);
  if (rows.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  let keys = [];
  let vals = [];
  for (const i in fields) {
    keys.push(i);
    vals.push(fields[i]);
  }
  await ctx.db.query(`UPDATE wsd_icebox_b SET ${keys.map((key) => `${key}=?`).join(",")} WHERE ID=?`, [...vals, id]);
});
/**
 * 查询冰柜
 */
router.get("/list", async (ctx) => {
  let datas = await ctx.db.query(`SELECT * FROM wsd_icebox_b`);
  ctx.body = {
    list: datas,
  };
});
/**
 * 查询房间或者车辆冰箱以及冰箱温度计
 */
router.get("/getIceboxByid", async (ctx) => {
  let { type, carroom_id } = ctx.query;
  let roomInfo = null;
  let carInfo = null;
  let roomsql = `select a.*,b.build_name,
  c.temp_up,c.temp_low,c.hum_up,c.hum_low,c.temperature,c.humidity
  from wsd_room_b a 
  inner join wsd_build_b b on a.build_id=b.id
  left join wsd_dev_b c on a.dev_id=c.id 
  where a.id = ?`;
  let carsql = `select a.*,c.temp_up,c.temp_low,c.hum_up,c.hum_low,c.temperature,c.humidity 
  from wsd_car_b a inner join wsd_dev_b c on a.dev_id=c.id where a.id = ?`;
  // 房间以及房间所有冰箱的温度计sql
  let wdjSql = `select a.net_st,a.alarm_yn from  wsd_dev_b a
   inner join wsd_icebox_dev_f b on a.id = b.dev_id
   inner  join wsd_carroom_icebox_f c on b.icebox_id=c.icebox_id
   where c.carroom_id=?
   union all 
   select b.net_st,b.alarm_yn from wsd_room_b a
   inner join wsd_dev_b b on a.dev_id = b.id
   where a.id=?`;
  let roomAllWdj = await ctx.db.query(wdjSql, [carroom_id, carroom_id]);
  if (type === "LLC") {
    carInfo = await ctx.db.query(carsql, [carroom_id]);
    if (carInfo.length > 0) {
      carInfo[0]["total_dev"] = roomAllWdj.length;
      carInfo[0]["online_dev"] = roomAllWdj.filter((item) => {
        return item.net_st === "WLZT_ZX";
      }).length;
      carInfo[0]["alarmsNum"] = roomAllWdj.filter((w) => {
        return w.alarm_yn === "Y";
      }).length;
    }
  }
  if (type === "FJ") {
    roomInfo = await ctx.db.query(roomsql, [carroom_id]);
    if (roomInfo.length > 0) {
      roomInfo[0]["total_dev"] = roomAllWdj.length;
      roomInfo[0]["online_dev"] = roomAllWdj.filter((item) => {
        return item.net_st === "WLZT_ZX";
      }).length;
      roomInfo[0]["alarmsNum"] = roomAllWdj.filter((w) => {
        return w.alarm_yn === "Y";
      }).length;
    }
  }
  let iceSql = `SELECT a.* FROM wsd_icebox_b a 
  inner JOIN wsd_carroom_icebox_f b ON a.id = b.icebox_id
  where b.carroom_id=?`;
  let devSql = `SELECT a.* FROM wsd_dev_b a 
  inner JOIN wsd_icebox_dev_f b ON a.id = b.dev_id
  where b.icebox_id=?`;
  let iceRows = await ctx.db.query(iceSql, [carroom_id]);
  if (iceRows.length > 0) {
    for (let i = 0; i < iceRows.length; i++) {
      let devList = await ctx.db.query(devSql, [iceRows[i].id]);
      iceRows[i]["devList"] = devList;
    }
  }
  ctx.body = {
    list: iceRows,
    roomInfo,
    carInfo,
  };
});
module.exports = router.routes();
