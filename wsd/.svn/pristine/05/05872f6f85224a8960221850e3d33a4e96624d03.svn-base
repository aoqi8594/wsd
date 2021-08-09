const Router = require("koa-router");
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
let router = new Router({
  prefix: "room",
});
/**
 * 根据楼层管理房间
 */
router.post("/manage", async (ctx) => {
  let fields = ctx.request.fields;
  let { build_id, floor_cd, roomList } = fields;
  // 通过roomList分析出新增，删除，编辑，房间
  // 查询出当前楼栋，该层的所有房间
  let oldRoomData = await ctx.db.query(`SELECT id FROM wsd_room_b WHERE build_id=? AND floor_cd=?`, [build_id, floor_cd]);
  let oldRoomId = oldRoomData.map((item) => {
    return item.id;
  });
  let reqRoomId = roomList
    .filter((item) => {
      return item.id;
    })
    .map((it) => {
      return it.id;
    });
  console.log(JSON.stringify(reqRoomId));
  // 需要增加的房间
  let addRoomData = roomList.filter((room) => {
    return !room.id;
  });
  if (addRoomData.length > 0) {
    addRoomData.forEach(async (item) => {
      let keys = ["build_id", "floor_cd"];
      let vals = [build_id, floor_cd];
      for (const i in item) {
        keys.push(i);
        if (i === "id") {
          vals.push(common.getUUID());
        } else {
          vals.push(item[i]);
        }
      }
      await ctx.db.query(`INSERT INTO wsd_room_b (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
    });
  }
  // 需要删除的房间
  let deleteRoomData = common.arrDifference(oldRoomId, reqRoomId);
  if (deleteRoomData.length > 0) {
    await ctx.db.query(`DELETE FROM wsd_room_b WHERE ID IN (${deleteRoomData.map((id) => "?")})`, deleteRoomData);
  }
  // 需要更新的房间
  let updateRoomData = common.arrIntersect(oldRoomId, reqRoomId);
  if (updateRoomData.length > 0) {
    let upData = roomList.filter((i) => {
      if (updateRoomData.indexOf(i.id) != -1) return i;
    });
    upData.forEach(async (item) => {
      let keys = [];
      let vals = [];
      for (const i in item) {
        keys.push(i);
        vals.push(item[i]);
      }
      await ctx.db.query(`UPDATE wsd_room_b SET ${keys.map((key) => `${key}=?`).join(",")} WHERE ID=?`, [...vals, item.id]);
    });
  }
  console.log(fields);
  console.log("old===" + JSON.stringify(oldRoomId));
  console.log("add===" + JSON.stringify(addRoomData));
  console.log("del===" + JSON.stringify(deleteRoomData));
  console.log("up===" + JSON.stringify(updateRoomData));
});
/**
 * 新增房间
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
  await ctx.db.query(`INSERT INTO wsd_room_b (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
});
/**
 * 编辑房间详情
 */
router.post("/editRoomInfo", async (ctx) => {
  let fields = ctx.request.fields;
  console.log(fields);
  let { basic, equipment } = fields;
  let id = basic.id; // 房间id
  let dev_id = basic.dev_id;
  // 房间基本信息,存在关联温度计，更新房间信息,并更新温度计的状态
  if (dev_id) {
    let devRows = await ctx.db.query(`SELECT * FROM wsd_dev_b WHERE id=?`, [dev_id]);
    let oldconnDev = await ctx.db.query(`SELECT dev_id FROM wsd_room_b WHERE id=?`, [id]);
    ctx.assert(devRows.length, 400, "该温度计不存在");
    try {
      // 开启事务
      await ctx.db.startTransaction();
      // ①更新房间信息
      await ctx.db.executeTransaction(`UPDATE wsd_room_b SET dev_id=? WHERE ID=?`, [dev_id, id]);
      // ②更新原来温度计状态
      await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID=?`, ["XZ", oldconnDev[0].dev_id]);
      // ②更新关联温度计状态
      await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID=?`, ["QY", dev_id]);
      // 关闭事务
      await ctx.db.stopTransaction();
    } catch (e) {
      throw new ApiError(ApiErrorNames.OPERATION_FAILED);
    }
  }
  // 根据房间ID查询房间的所有设备id
  let equRows = await ctx.db.query(`SELECT * FROM wsd_carroom_icebox_f WHERE (type=? AND carroom_id=?)`, ["FJ", id]);
  let iceBoxIds = equRows.map((item) => {
    return item.icebox_id;
  });
  console.log(iceBoxIds);
  // 如果有设备，查询出房间所有设备，筛选出新增设备和删除设备以及更新的设备
  if (equipment instanceof Array && equipment.length > 0) {
    let updateId = equipment
      .filter((eq) => {
        return eq.id;
      })
      .map((item) => {
        return item.id;
      });
    console.log(updateId);
    // 需要更新的设备
    let updateDataId = common.arrIntersect(updateId, iceBoxIds);
    if (updateDataId.length > 0) {
      let upData = equipment.filter((i) => {
        if (updateDataId.indexOf(i.id) != -1) return i;
      });
      console.log("upppp" + JSON.stringify(upData));
      upData.forEach(async (up) => {
        // 设备id
        let eqid = up.id;
        // 关联的温度计
        let newDevIds = up.dev_ids;
        //更新设备的名称和状态
        let upboxsql = `UPDATE wsd_icebox_b SET st=?,icebox_name=?,duty_name=?,contact_phone1=?,use_type=? WHERE ID=?`;
        await ctx.db.query(upboxsql, ["ZC", up.icebox_name, up.duty_name, up.contact_phone1, up.use_type, eqid]);
        // 如果该设备所关联的温度计改变，判断出温度计的增加，删除
        // 根据设备的ID找出设备所关联的温度计
        let oldDevIds = await ctx.db.query(`SELECT dev_id FROM wsd_icebox_dev_f WHERE icebox_id=?`, [eqid]);
        oldDevIds = oldDevIds.map((it) => {
          return it.dev_id + "";
        });
        console.log("oldDevIds" + JSON.stringify(oldDevIds));
        console.log("newDevIds" + JSON.stringify(newDevIds));
        // 需要关联的温度计
        let addDev = common.arrDifference(newDevIds, oldDevIds);
        if (addDev.length > 0) {
          addDev.forEach(async (devId) => {
            // 生成一个关联id
            let gid = common.getUUID();
            await ctx.db.startTransaction();
            await ctx.db.executeTransaction(`INSERT INTO wsd_icebox_dev_f (id,st,icebox_id,dev_id) VALUES(?,?,?,?)`, [gid, "ZC", eqid, devId]);
            await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID=?`, ["QY", devId]);
            await ctx.db.stopTransaction();
          });
        }
        // 需要移除的温度计
        let remDev = common.arrDifference(oldDevIds, newDevIds);
        if (remDev.length > 0) {
          await ctx.db.startTransaction();
          await ctx.db.executeTransaction(`DELETE FROM wsd_icebox_dev_f WHERE dev_id IN (${remDev.map((id) => "?")})`, remDev);
          await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE id IN (${remDev.map((id) => "?")})`, ["TY", remDev]);
          await ctx.db.stopTransaction();
        }
        console.log("addDev" + JSON.stringify(addDev));
        console.log("remDev" + JSON.stringify(remDev));
      });
    }
    // 需要删除的设备
    let deleteData = common.arrDifference(iceBoxIds, updateId);
    if (deleteData.length > 0) {
      // 根据设备的ID找出设备所关联的温度计
      let widArr = [];
      for (let i = 0; i <= deleteData.length; i++) {
        let sbid = deleteData[i];
        let delDevId = await ctx.db.query(`SELECT dev_id FROM wsd_icebox_dev_f WHERE icebox_id=?`, [sbid]);
        widArr = widArr.concat(
          delDevId.map((item) => {
            return item.dev_id + "";
          })
        );
      }
      console.log("widArr==" + JSON.stringify(widArr));
      await ctx.db.startTransaction();
      await ctx.db.executeTransaction(`DELETE FROM wsd_icebox_b WHERE ID IN (${deleteData.map((id) => "?")})`, deleteData);
      await ctx.db.executeTransaction(`DELETE FROM wsd_carroom_icebox_f WHERE icebox_id IN (${deleteData.map((id) => "?")})`, deleteData);
      if (widArr.length > 0) {
        await ctx.db.executeTransaction(`DELETE FROM wsd_icebox_dev_f WHERE dev_id IN (${widArr.map((id) => "?")})`, widArr);
        await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st="TY" WHERE id IN (${widArr.map((id) => "?")})`, widArr);
      }
      await ctx.db.stopTransaction();
    }
    // 需要增加的设备
    let addData = equipment.filter((eq) => {
      return !eq.id;
    });
    if (addData.length > 0) {
      addData.forEach(async (dt) => {
        let currsb = dt;
        let sbId = common.getUUID();
        await ctx.db.startTransaction();
        // 增加一个设备
        await ctx.db.executeTransaction(`INSERT INTO wsd_icebox_b (id,st,icebox_name,duty_name,contact_phone1,use_type) VALUES(?,?,?,?,?,?)`, [
          sbId,
          "ZC",
          currsb.icebox_name,
          currsb.duty_name,
          currsb.contact_phone1,
          currsb.use_type,
        ]);
        // 增加一个房间设备关系
        await ctx.db.executeTransaction(`INSERT INTO wsd_carroom_icebox_f (id,st,type,icebox_id,carroom_id) VALUES(?,?,?,?,?)`, [common.getUUID(), "ZC", "FJ", sbId, id]);
        await ctx.db.stopTransaction();
        let addwdjArr = currsb.dev_ids;
        if (addwdjArr.length > 0) {
          addwdjArr.forEach(async (wdj) => {
            // 生成一个关联id
            let gid = common.getUUID();
            await ctx.db.startTransaction();
            // 增加设备和温度计的关系
            await ctx.db.executeTransaction(`INSERT INTO wsd_icebox_dev_f (id,st,icebox_id,dev_id) VALUES(?,?,?,?)`, [gid, "ZC", sbId, wdj]);
            // 修改温度计的状态
            await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID=?`, ["QY", wdj]);
            await ctx.db.stopTransaction();
          });
        }
      });
    }
    console.log("up===" + JSON.stringify(updateDataId));
    console.log("dl===" + JSON.stringify(deleteData));
    console.log("ad===" + JSON.stringify(addData));
  } else if (equipment instanceof Array && equipment.length === 0 && equRows.length > 0) {
    // 删除所有设备以及设备关联的温度计
    let widArr = [];
    for (let i = 0; i <= iceBoxIds.length; i++) {
      let sbid = iceBoxIds[i];
      let delDevId = await ctx.db.query(`SELECT dev_id FROM wsd_icebox_dev_f WHERE icebox_id=?`, [sbid]);
      widArr = widArr.concat(
        delDevId.map((item) => {
          return item.dev_id + "";
        })
      );
    }
    await ctx.db.startTransaction();
    await ctx.db.executeTransaction(`DELETE FROM wsd_icebox_b WHERE ID IN (${iceBoxIds.map((id) => "?")})`, iceBoxIds);
    await ctx.db.executeTransaction(`DELETE FROM wsd_carroom_icebox_f WHERE icebox_id IN (${iceBoxIds.map((id) => "?")})`, iceBoxIds);
    if (widArr.length > 0) {
      await ctx.db.executeTransaction(`DELETE FROM wsd_icebox_dev_f WHERE dev_id IN (${widArr.map((id) => "?")})`, widArr);
      await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st="TY" WHERE id IN (${widArr.map((id) => "?")})`, widArr);
    }
    await ctx.db.stopTransaction();
  }
});
/**
 * 删除房间
 */
router.post("/delete", async (ctx) => {
  let fields = ctx.request.fields;
  console.log(fields);
  let { ids } = fields;
  let data = await ctx.db.query(`SELECT * FROM wsd_room_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
  if (data.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  await ctx.db.query(`DELETE FROM wsd_room_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
});
/**
 * 编辑房间
 */
router.post("/update", async (ctx) => {
  let fields = ctx.request.fields;
  let { id } = fields;
  let rows = await ctx.db.query(`SELECT * FROM wsd_room_b WHERE ID=?`, [id]);
  if (rows.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  let keys = [];
  let vals = [];
  for (const i in fields) {
    keys.push(i);
    vals.push(fields[i]);
  }
  await ctx.db.query(`UPDATE wsd_room_b SET ${keys.map((key) => `${key}=?`).join(",")} WHERE ID=?`, [...vals, id]);
});
/**
 * 查询房间
 */
router.get("/list", async (ctx) => {
  let datas = await ctx.db.query(`SELECT * FROM wsd_room_b`);
  ctx.body = {
    list: datas,
  };
});
/**
 *查询所有房间信息，报警优先
 */
router.get("/getAlarmsRoom", async (ctx) => {
  // 所有房间sql
  let roomSql = `select c.alarm_yn,a.*,b.build_name,
  c.temp_up,c.temp_low,c.hum_up,c.hum_low,c.temperature,c.humidity
  from wsd_room_b a 
  inner join wsd_build_b b on a.build_id=b.id
  left join wsd_dev_b c on a.dev_id=c.id
  order by c.alarm_yn desc ,b.build_name,a.floor_cd,a.room_cd`;
  // 房间以及房间所有冰箱的温度计sql
  let wdjSql = `select a.net_st,a.alarm_yn from  wsd_dev_b a
  inner join wsd_icebox_dev_f b on a.id = b.dev_id
  inner  join wsd_carroom_icebox_f c on b.icebox_id=c.icebox_id
  where c.carroom_id=?
  union all 
  select b.net_st,b.alarm_yn from wsd_room_b a
  inner join wsd_dev_b b on a.dev_id = b.id
  where a.id=?`;
  await ctx.db.startTransaction();
  let allRoom = await ctx.db.executeTransaction(roomSql);
  for (let i = 0; i < allRoom.length; i++) {
    let roomAllWdj = await ctx.db.executeTransaction(wdjSql, [allRoom[i].id, allRoom[i].id]);
    allRoom[i]["total_dev"] = roomAllWdj.length;
    allRoom[i]["online_dev"] = roomAllWdj.filter((item) => {
      return item.net_st === "WLZT_ZX";
    }).length;
    allRoom[i]["alarmsNum"] = roomAllWdj.filter((w) => {
      return w.alarm_yn === "Y";
    }).length;
  }
  await ctx.db.stopTransaction();
  ctx.body = allRoom;
});
/**
 * 查询所有房间状态统计
 */
router.get("/getAllRoomState", async (ctx) => {
  let rows = await ctx.db.query(`select a.alarm_yn as st from wsd_dev_b a
  inner join wsd_icebox_dev_f b on a.id = b.dev_id
  inner join wsd_carroom_icebox_f c on b.icebox_id = c.icebox_id
  inner join wsd_room_b d on d.id = c.carroom_id
  union all 
  select  b.alarm_yn as st  from wsd_room_b a 
  inner join wsd_dev_b b on a.dev_id= b.id`);
  let total = rows.length;
  let alarms = rows.filter((item) => {
    return item.st === "Y";
  }).length;
  
  let normal = total - alarms;
  ctx.body = {
    total,
    alarms,
    normal,
  };
});
/**
 * 通过楼栋id查询房间
 */
router.get("/getListBybuildId", async (ctx) => {
  let { build_id } = ctx.query;
  let datas = await ctx.db.query(`SELECT * FROM wsd_room_b WHERE build_id=?`, [build_id]);
  if (datas.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }
  ctx.body = {
    list: datas,
  };
});
/**
 * 查询谋楼栋某楼层的所有房间
 */
router.get("/getListBybuiidAndfloor", async (ctx) => {
  let { build_id, floor_cd } = ctx.query;
  let datas = await ctx.db.query(`select * from wsd_room_b where build_id = ? and substr(floor_cd,1,1) = ?`, [build_id, floor_cd]);
  // 房间以及房间所有冰箱的温度计sql
  let wdjSql = `select a.net_st,a.alarm_yn from  wsd_dev_b a
  inner join wsd_icebox_dev_f b on a.id = b.dev_id
  inner  join wsd_carroom_icebox_f c on b.icebox_id=c.icebox_id
  where c.carroom_id=?
  union all 
  select b.net_st,b.alarm_yn from wsd_room_b a
  inner join wsd_dev_b b on a.dev_id = b.id
  where a.id=?`;
  if (datas.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }

  for (let i = 0; i < datas.length; i++) {
    datas[i]["devInfo"] = null;
    if (datas[i].dev_id) {
      let sql = `SELECT * FROM wsd_dev_b WHERE id = ?`;
      let dt = await ctx.db.query(sql, [datas[i].dev_id]);
      datas[i]["devInfo"] = dt;
    }
    let roomAllWdj = await ctx.db.query(wdjSql, [datas[i].id, datas[i].id]);
    datas[i]["total_dev"] = roomAllWdj.length;
    datas[i]["online_dev"] = roomAllWdj.filter((item) => {
      return item.net_st === "WLZT_ZX";
    }).length;
    datas[i]["alarmsNum"] = roomAllWdj.filter((w) => {
      return w.alarm_yn === "Y";
    }).length;
  }
  ctx.body = {
    list: datas,
  };
});
/**
 * 通过房间id查询房间详情
 */
router.get("/getRoomInfo", async (ctx) => {
  let { id } = ctx.query;
  let field = ["id", "room_name", "dev_id", "floor_cd", "build_id", "remark", "room_cd", "duty_name", "contact_phone1"];
  // 查询房间的基本信息
  let basicRows = await ctx.db.query(`SELECT ${field.join(",")} FROM wsd_room_b WHERE ID=?`, [id]);
  if (basicRows.length <= 0) {
    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
  }

  // -- 温度计

  // 查询房间所关联的冰箱
  let iceSql = `select b.* from wsd_carroom_icebox_f a  inner join wsd_icebox_b b on a.icebox_id = b.id  where a.carroom_id=? `;
  let iceBoxRows = await ctx.db.query(iceSql, [id]);
  // 查询冰箱所关联的温度计
  let wdjSql = `select b.* from wsd_icebox_dev_f a inner join wsd_dev_b b on a.dev_id = b.id where a.icebox_id=?`;
  for (let i = 0; i < iceBoxRows.length; i++) {
    let wdjRows = await ctx.db.query(wdjSql, [iceBoxRows[i].id]);
    iceBoxRows[i]["wdjList"] = wdjRows;
  }

  ctx.body = {
    basicInfo: basicRows[0],
    sbList: iceBoxRows,
  };
});

module.exports = router.routes();
