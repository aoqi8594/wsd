/**
 * 统计图相关
 */
const Router = require("koa-router");
const datalize = require("datalize");
const field = datalize.field;
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
const Moment = require("moment");
let router = new Router({
  prefix: "chart",
});
/**
 * 温湿度计监测数据变化统计点位图
 */
router.get("/getDevTimePointChart", async (ctx) => {
  let { dev_id } = ctx.query;
  let sql = `SELECT a.dev_id,a.dev_no,a.temperature,a.humidity ,
  DATE_FORMAT( create_time, '%H' ) AS create_time FROM wsd_dev_tah_h a
  INNER JOIN ( SELECT max( id ) as id, DATE_FORMAT( create_time, '%Y-%m-%d %H:00:00' ) AS create_time1 
  FROM wsd_dev_tah_h WHERE dev_id = ? GROUP BY create_time1 ) aa ON aa.id = a.id 
  where DATE_FORMAT( create_time, '%Y-%m-%d' ) = '${Moment().format("YYYY-MM-DD")}'`;
  console.log(sql);
  let rows = await ctx.db.query(sql, [dev_id]);
  console.log(Moment().format("YYYY-MM-DD"));
  ctx.body = {
    list: rows,
  };
});
/**
 * 温湿度计数据正确率
 */
router.get("/getDevAccChart", async (ctx) => {
  let { dev_id } = ctx.query;
  let sql = `SELECT ( SELECT count( 1 ) FROM wsd_dev_tah_h WHERE dev_id = ? ) AS allNum,
  ( SELECT count( 1 ) AS allNum FROM wsd_dev_tah_h WHERE dev_id = ? AND alarm_yn = 'Y' ) AS alarmNum`;
  let rows = await ctx.db.query(sql, [dev_id, dev_id]);
  ctx.body = {
    list: rows,
  };
});
/**
 * 获取谋层楼的报警次数统计
 */
router.get("/getFloorCountChart", async (ctx) => {
  let { build_id, floor_cd } = ctx.query;
  let sql = `select count(1) as num, FLOOR(HOUR(alarm.create_time)/2)*2 as hours 
  from wsd_dev_alarm_l alarm 
  inner join (
  select bb.dev_id from wsd_room_b aa
  inner join (
  select a.id as dev_id,b.id as romm_id from wsd_dev_b a 
  inner join wsd_room_b b on b.dev_id = a.id 
  union 
  select a.id as dev_id,c.carroom_id as room_id from wsd_dev_b a
  inner join wsd_icebox_dev_f b on b.dev_id  = a.id
  inner join wsd_carroom_icebox_f c on c.icebox_id=b.icebox_id ) bb on aa.id = bb.romm_id
  where aa.build_id=? and aa.floor_cd=? ) aaa on alarm.dev_id = aaa.dev_id
  where DATE_FORMAT( alarm.create_time, '%Y-%m-%d' ) ='${Moment().format("YYYY-MM-DD")}'
  group by hours`;
  let rows = await ctx.db.query(sql, [build_id, floor_cd]);
  ctx.body = {
    list: rows,
  };
});
module.exports = router.routes();
