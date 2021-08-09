/**
 * 楼栋管理
 */
const Router = require("koa-router");
const datalize = require("datalize");
const field = datalize.field;
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
let router = new Router({
    prefix: "building",
});
/**
 * 新增楼栋
 */
let rules = [field("build_name").trim().required(), field("all_floor_cd").trim().required().int().range(1, 30)];
router.post("/add", datalize(rules), async(ctx) => {
    let fields = ctx.request.fields;
    if (!ctx.form.isValid) {
        throw new ApiError(ApiErrorNames.ValidateFailed, ctx.form.errors);
    }
    fields.id = common.getUUID();
    console.log(fields);
    let { build_name, all_floor_cd } = fields;
    let str = Array.from(Array(all_floor_cd), (v, k) => k + 1).join(",");
    fields.all_floor_cd = str;
    let rows = await ctx.db.query(`SELECT build_name FROM wsd_build_b WHERE build_name =?`, [build_name]);
    if (rows.length > 0) {
        throw new ApiError(ApiErrorNames.BUILDING_EXISTS);
    }
    let keys = [];
    let vals = [];
    for (const i in fields) {
        keys.push(i);
        vals.push(fields[i]);
    }
    await ctx.db.query(`INSERT INTO wsd_build_b (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
});
/**
 * 删除楼栋
 */
router.post("/delete", async(ctx) => {
    let fields = ctx.request.fields;
    console.log(fields);
    let { ids } = fields;
    let datas = await ctx.db.query(`SELECT * FROM wsd_build_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
    if (datas.length < 0) {
        throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
    }
    // 如果楼栋有房间，先删除房间，房间有设备，先删除设备，设备有关联温度计，解除温度计绑定的关系
    for (let i = 0; i < datas.length; i++) {
        // 根据楼栋id查询所有房间
        let rooms = await ctx.db.query(`SELECT * FROM wsd_room_b WHERE build_id=?`, [datas[i].id]);
        if (rooms.length > 0) {
            // 存在房间，房间是否存在设备，是否关联温度计
            for (let j = 0; j < rooms.length; j++) {
                let updateWdj = [];
                // 房间存在关联温度计
                if (rooms[j].dev_id) {
                    updateWdj.push(rooms[j].dev_id);
                }
                // 查询房间所关联的冰箱
                let iceSql = `select b.* from wsd_carroom_icebox_f a  inner join wsd_icebox_b b on a.icebox_id = b.id  where a.carroom_id=? `;
                let iceBoxRows = await ctx.db.query(iceSql, [rooms[j].id]);
                if (iceBoxRows.length > 0) {
                    // 存在设备,查询冰箱所关联的温度计
                    let wdjSql = `select b.id from wsd_icebox_dev_f a inner join wsd_dev_b b on a.dev_id = b.id where a.icebox_id=?`;
                    for (let k = 0; k < iceBoxRows.length; k++) {
                        let wdjRows = await ctx.db.query(wdjSql, [iceBoxRows[k].id]);
                        updateWdj = [...updateWdj, ...wdjRows];
                        await ctx.db.query(`DELETE FROM wsd_icebox_b WHERE ID=?`, [iceBoxRows[k].id]);
                        await ctx.db.query(`DELETE FROM wsd_carroom_icebox_f WHERE icebox_id=?`, [iceBoxRows[k].id]);
                    }
                    // 删除房间
                    await ctx.db.query(`DELETE FROM wsd_room_b WHERE ID=?`, [rooms[j].id]);
                } else {
                    // 不存在设备，直接删除房间
                    await ctx.db.query(`DELETE FROM wsd_room_b WHERE ID=?`, [rooms[j].id]);
                }
                // 需要更新的温度计
                if (updateWdj.length > 0) {
                    await ctx.db.query(`UPDATE wsd_dev_b SET st=? WHERE ID IN (${updateWdj.map((id) => "?")})`, ["XZ", updateWdj]);
                }
            }
            await ctx.db.query(`DELETE FROM wsd_build_b WHERE ID=?`, [datas[i].id]);
        } else {
            await ctx.db.query(`DELETE FROM wsd_build_b WHERE ID=?`, [datas[i].id]);
        }
    }
});
/**
 * 编辑楼栋
 */
router.post("/update", async(ctx) => {
            let fields = ctx.request.fields;
            let { id } = fields;
            let rows = await ctx.db.query(`SELECT * FROM wsd_build_b WHERE ID=?`, [id]);
            if (rows.length <= 0) {
                throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
            }
            let keys = [];
            let vals = [];
            for (const i in fields) {
                keys.push(i);
                vals.push(fields[i]);
            }
            await ctx.db.query(`UPDATE wsd_build_b SET ${keys.map((key) => `${key}=?`).join(",")} WHERE ID=?`, [...vals, id]);
});
/**
 * 查询楼栋
 */
router.get("/list", async (ctx) => {
  let datas = await ctx.db.query(`SELECT * FROM wsd_build_b`);
  for (let i = 0; i < datas.length; i++) {
    if (datas[i].id) {
      let buildId = datas[i].id;
      let rooms = await ctx.db.query(`SELECT * FROM wsd_room_b WHERE build_id=? order by room_cd `, [buildId]);
      datas[i]["roomList"] = rooms;
    }
  }
  ctx.body = {
    list: datas,
  };
});

module.exports = router.routes();