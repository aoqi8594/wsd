/**
 * 车辆管理
 */
const Router = require("koa-router");
const common = require("../../lib/common");
const ApiError = require("../../error/ApiError");
const ApiErrorNames = require("../../error/ApiErrorNames");
const { emit } = require("nodemon");
let router = new Router({
    prefix: "car",
});
/**
 * 新增车辆
 */
router.post("/add", async(ctx) => {
    let fields = ctx.request.fields;
    console.log(fields);
    let { basic, equipment } = fields;
    // 增加车辆的基本信息
    let carId = common.getUUID();
    basic.id = carId;
    let keys = [];
    let vals = [];
    for (const i in basic) {
        keys.push(i);
        vals.push(basic[i]);
    }
    // 如果存在关联温度计，更新温度计状态
    if (basic.dev_id) {
        await ctx.db.startTransaction();
        await ctx.db.executeTransaction(`INSERT INTO wsd_car_b (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
        await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID=?`, ["QY", basic.dev_id]);
        await ctx.db.stopTransaction();
    } else {
        await ctx.db.query(`INSERT INTO wsd_car_b (${keys.join(",")}) VALUES(${keys.map((key) => "?").join(",")})`, vals);
    }
    // 如果有设备，增加设备以及设备关联温度计
    if (equipment instanceof Array && equipment.length > 0) {
        for (let i = 0; i < equipment.length; i++) {
            let eqid = common.getUUID();
            let field = ["id", "st", "icebox_name", "duty_name", "contact_phone1"];
            let v = [eqid, "ZC", equipment[i].icebox_name, equipment[i].duty_name, equipment[i].contact_phone1];
            await ctx.db.startTransaction();
            await ctx.db.executeTransaction(`INSERT INTO wsd_icebox_b (${field.join(",")}) VALUES(${field.map((key) => "?").join(",")})`, v);
            await ctx.db.executeTransaction(`INSERT INTO wsd_carroom_icebox_f (id,st,type,icebox_id,carroom_id) VALUES(?,?,?,?,?)`, [common.getUUID(), "ZC", "LLC", eqid, carId]);
            await ctx.db.stopTransaction();
            // 当前设备所关联的温度计
            let addwdjArr = equipment[i].dev_ids;
            if (addwdjArr.length > 0) {
                addwdjArr.forEach(async(wdj) => {
                    // 生成一个关联id
                    let gid = common.getUUID();
                    await ctx.db.startTransaction();
                    // 增加设备和温度计的关系
                    await ctx.db.executeTransaction(`INSERT INTO wsd_icebox_dev_f (id,st,icebox_id,dev_id) VALUES(?,?,?,?)`, [gid, "ZC", eqid, wdj]);
                    // 修改温度计的状态
                    await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID=?`, ["QY", wdj]);
                    await ctx.db.stopTransaction();
                });
            }
        }
    }
});
/**
 * 删除车辆
 */
router.post("/delete", async(ctx) => {
    let fields = ctx.request.fields;
    console.log(fields);
    let { ids } = fields;
    let datas = await ctx.db.query(`SELECT * FROM wsd_car_b WHERE ID IN (${ids.map((id) => "?")})`, ids);
    if (datas.length <= 0) {
        throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
    }
    for (let i = 0; i < datas.length; i++) {
        // 更新当前车辆所关联的温度计状态
        await ctx.db.startTransaction();
        await ctx.db.executeTransaction(`DELETE FROM wsd_car_b WHERE ID=?`, [datas[i].id]);
        await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID=?`, ["XZ", datas[i].dev_id]);
        await ctx.db.stopTransaction();
        // 查询当前车辆所关联的设备
        let iceboxSql = `select  b.id from  wsd_carroom_icebox_f a  inner join wsd_icebox_b b  on a.icebox_id = b.id where a.carroom_id=?`;
        let iceboxList = await ctx.db.query(iceboxSql, [datas[i].id]);
        console.log("_____" + iceboxList);
        if (iceboxList.length > 0) {
            for (let j = 0; j < iceboxList.length; j++) {
                // 查询当前设备所关联的温度计，并更新温度计的状态
                let devSql = `SELECT a.id FROM wsd_dev_b a inner join wsd_icebox_dev_f b on a.id=b.dev_id where b.icebox_id=?`;
                let devList = await ctx.db.query(devSql, [iceboxList[j].id]);
                devList = devList.map((dev) => {
                    return dev.id;
                });
                console.log("===" + JSON.stringify(devList));
                await ctx.db.startTransaction();
                // 删除设备
                await ctx.db.executeTransaction(`DELETE FROM wsd_icebox_b WHERE ID=?`, iceboxList[j].id);
                await ctx.db.executeTransaction(`DELETE FROM wsd_carroom_icebox_f WHERE icebox_id=?`, iceboxList[j].id);
                await ctx.db.executeTransaction(`DELETE FROM wsd_icebox_dev_f WHERE icebox_id=?`, iceboxList[j].id);
                // 更新关联温度计状态
                await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID IN (${ids.map((id) => "?")})`, ["XZ", devList]);
                await ctx.db.stopTransaction();
            }
        }
    }
});
/**
 *编辑车辆
 */
router.post("/update", async(ctx) => {
            let fields = ctx.request.fields;
            let { basic, equipment } = fields;
            console.log(fields);
            let carId = basic.id;
            let car = null;
            if (carId) {
                rows = await ctx.db.query(`SELECT * FROM wsd_car_b WHERE id=?`, [carId]);
                car = rows[0];
                if (rows.length <= 0) {
                    throw new ApiError(ApiErrorNames.NOT_FIND_RESULT);
                }
            }
            console.log("@@@@" + JSON.stringify(car));
            //更新车辆的基本信息,如果存在关联温度计并更新温度计状态
            let keys = [];
            let vals = [];
            for (const i in basic) {
                if (i != "id") {
                    keys.push(i);
                    vals.push(basic[i]);
                }
            }
            if (basic.dev_id && basic.dev_id === car.dev_id) {
                await ctx.db.query(`UPDATE wsd_car_b SET ${keys.map((key) => `${key}=?`).join(",")} WHERE ID=?`, [...vals, carId]);
  } else if (basic.dev_id && basic.dev_id != car.dev_id) {
    await ctx.db.startTransaction();
    //更新原来温度计的状态
    await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID=?`, ["TY", car.dev_id]);
    //更新车辆基本信息，更新关联温度计的状态
    await ctx.db.executeTransaction(`UPDATE wsd_car_b SET ${keys.map((key) => `${key}=?`).join(",")} WHERE ID=?`, [...vals, carId]);
    await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID=?`, ["QY", basic.dev_id]);
    await ctx.db.stopTransaction();
  }
  //更新设备以及设备关联的温度计
  //①：查询原来车辆所关联的冰箱
  let oldIceBox = await ctx.db.query(`SELECT * FROM wsd_carroom_icebox_f WHERE carroom_id=?`, [carId]);
  if (equipment && equipment.length > 0) {
    let oArr = [];
    let nArr = [];
    oArr = oldIceBox.map((item) => {
      return item.icebox_id + "";
    });

    equipment.map((eq) => {
      if (eq.id) {
        nArr.push(eq.id + "");
      }
    });
    console.log("oArr" + JSON.stringify(oArr));
    console.log("nArr" + JSON.stringify(nArr));
    // 需要更新的冰箱
    let updateData = common.arrIntersect(oArr, nArr);
    if (updateData.length > 0) {
      console.log("1111111111111111111");
      let upData = equipment.filter((i) => {
        if (updateData.indexOf(i.id + "") != -1) return i;
      });

      console.log(JSON.stringify(upData));
      upData.forEach(async (up) => {
        // 设备id
        let eqid = up.id;
        // 关联的温度计
        let newDevIds = up.dev_ids;
        //更新设备信息
        let updevSql = `UPDATE wsd_icebox_b SET icebox_name=?,duty_name=? ,contact_phone1=? WHERE ID=?`;
        await ctx.db.query(updevSql, [up.icebox_name, up.duty_name, up.contact_phone1, eqid]);
        // 如果该设备所关联的温度计改变，判断出温度计的增加，删除
        // 根据设备的ID找出设备所关联的温度计
        let oldDevIds = await ctx.db.query(`SELECT dev_id FROM wsd_icebox_dev_f WHERE icebox_id=?`, [eqid]);
        oldDevIds = oldDevIds.map((it) => {
          return it.dev_id;
        });
        console.log("oldDevIds" + JSON.stringify(oldDevIds));
        console.log("newDevIds" + JSON.stringify(newDevIds));
        // 需要关联的温度计
        let addDev = common.arrDifference(newDevIds, oldDevIds);
        if (addDev.length > 0) {
          for (let k = 0; k < addDev.length; k++) {
            // 生成一个关联id
            let gid = common.getUUID();
            await ctx.db.startTransaction();
            await ctx.db.executeTransaction(`INSERT INTO wsd_icebox_dev_f (id,st,icebox_id,dev_id) VALUES(?,?,?,?)`, [gid, "ZC", eqid, addDev[k]]);
            await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE ID=?`, ["QY", addDev[k]]);
            await ctx.db.stopTransaction();
          }
        }
        // 需要移除的温度计
        let remDev = common.arrDifference(oldDevIds, newDevIds);
        if (remDev.length > 0) {
          await ctx.db.startTransaction();
          await ctx.db.executeTransaction(`DELETE FROM wsd_icebox_dev_f WHERE dev_id IN (${remDev.map((id) => "?")})`, remDev);
          await ctx.db.executeTransaction(`UPDATE wsd_dev_b SET st=? WHERE id IN (${remDev.map((id) => "?")})`, ["XZ", remDev]);
          ctx.db.stopTransaction();
        }
        console.log("addDev" + JSON.stringify(addDev));
        console.log("remDev" + JSON.stringify(remDev));
      });
    }
    // 需要删除的冰箱
    let deleteData = common.arrDifference(nArr, oArr);
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
    // 需要增加的冰箱
    let addData = equipment.filter((a) => {
      return !a.id;
    });
    if (addData.length > 0) {
      addData.forEach(async (dt) => {
        let currsb = dt;
        let sbId = common.getUUID();
        await ctx.db.startTransaction();
        // 增加一个设备
        await ctx.db.executeTransaction(`INSERT INTO wsd_icebox_b (id,st,icebox_name,duty_name,contact_phone1) VALUES(?,?,?,?,?)`, [
          sbId,
          "ZC",
          currsb.icebox_name,
          currsb.duty_name,
          currsb.contact_phone1,
        ]);
        // 增加一个车辆设备关系
        await ctx.db.executeTransaction(`INSERT INTO wsd_carroom_icebox_f (id,st,type,icebox_id,carroom_id) VALUES(?,?,?,?,?)`, [common.getUUID(), "ZC", "FJ", sbId, carId]);
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
    console.log("up===" + JSON.stringify(updateData));
    console.log("dl===" + JSON.stringify(deleteData));
    console.log("ad===" + JSON.stringify(addData));
  }
  // equipment 为空删除所有设备以及关联温度计
  if (equipment && equipment.length === 0 && oldIceBox.length > 0) {
    // 删除所有设备以及设备关联的温度计
    let widArr = [];
    let iceBoxIds = oldIceBox.map((item) => {
      return item.icebox_id + "";
    });
    for (let i = 0; i < oldIceBox.length; i++) {
      let sbid = oldIceBox[i].icebox_id;
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
 * 查询车辆以及车辆所关联的设备及温度计
 */
router.get("/list", async (ctx) => {
  let { car_no, driver_name, page, limit } = ctx.query;
  let params = [(parseInt(page) - 1) * parseInt(limit), parseInt(limit)];
  let sqlTotal = "select count(*) as total from wsd_car_b";
  let total = await ctx.db.query(sqlTotal);
  total = total[0]["total"];
  let count = {
    online: 0,
    offline: 0,
    alarmNums: 0,
  };
  // 查询车的基本信息
  let carKeys = ["id", "st", "car_no", "driver_name", "driver_lname", "contact_phone", "start_place", "end_place", "tachograph", "dev_id", "mark"];
  let querySql = `SELECT ${carKeys.join(",")} FROM wsd_car_b WHERE 1=1 `;
  if (car_no) {
    querySql += ` AND car_no LIKE ${"'%" + car_no + "%'"}`;
  }
  if (driver_name) {
    querySql += ` AND driver_name LIKE ${"'%" + driver_name + "%'"}`;
  }
  let results = await ctx.db.query(querySql);
  total = results.length;
  if (page && limit) {
    querySql += ` LIMIT ?,?`;
  }
  let carList = await ctx.db.query(querySql, [...params]);
  console.log(JSON.stringify(carList));
  for (let i = 0; i < carList.length; i++) {
    // 查询当前车辆所关联的温度计信息
    if (carList[i].dev_id) {
      let relationDevInfo = await ctx.db.query(`SELECT * FROM wsd_dev_b WHERE ID =?`, [carList[i].dev_id]);
      if (relationDevInfo.length > 0) {
        if (relationDevInfo[0].alarm_yn === "Y") {
          count.alarmNums += 1;
        }
        if (relationDevInfo[0].net_st === "WLZT_ZX") {
          count.online += 1;
        } else {
          count.offline += 1;
        }
      }
      carList[i]["relationDevInfo"] = relationDevInfo;
    }
    // 查询当前车辆所关联的设备信息
    let iceboxSql = `select  b.* from  wsd_carroom_icebox_f a  inner join wsd_icebox_b b  on a.icebox_id = b.id where a.carroom_id=?`;
    let iceboxList = await ctx.db.query(iceboxSql, [carList[i].id]);
    for (let j = 0; j < iceboxList.length; j++) {
      // 查询当前设备所关联的温度计
      let devSql = `SELECT a.* FROM wsd_dev_b a inner join wsd_icebox_dev_f b on a.id=b.dev_id where b.icebox_id=?`;
      let devList = await ctx.db.query(devSql, [iceboxList[j].id]);
      for (let k = 0; k < devList.length; k++) {
        if (devList[k].alarm_yn === "Y") {
          count.alarmNums += 1;
        }
        if (devList[k].net_st === "WLZT_ZX") {
          count.online += 1;
        } else {
          count.offline += 1;
        }
      }
      iceboxList[j]["devList"] = devList;
    }
    carList[i]["iceboxList"] = iceboxList;
    carList[i]["count"] = count;
  }

  ctx.body = {
    list: carList,
    paging: {
      page: page,
      limit: limit,
      total: total,
    },
  };
});
/**
 * 查询所有车辆状态统计
 */
router.get("/getAllCarState", async (ctx) => {
  let rows = await ctx.db.query(`select a.alarm_yn as st from wsd_dev_b a
  inner join wsd_icebox_dev_f b on a.id = b.dev_id
  inner join wsd_carroom_icebox_f c on b.icebox_id = c.icebox_id
  inner join wsd_car_b d on d.id = c.carroom_id
  union all 
  select  b.alarm_yn as st  from wsd_car_b a 
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
 * 更新车辆运行状态
 */
router.post("/updateCarRunState", async (ctx) => {
  let { id, st } = ctx.request.fields;
  await ctx.db.query(`UPDATE wsd_car_b SET st=? WHERE id=?`, [st, id]);
});
/**
 * 查询运行车辆的详情
 */
router.get("/getRunCarInfo", async (ctx) => {
  let { id } = ctx.query;
  let sql = `SELECT a.id,a.car_no,a.driver_name,a.contact_phone,a.start_place,a.end_place,a.tachograph,b.temperature,b.humidity 
  FROM wsd_car_b a  left join wsd_dev_b b on a.dev_id = b.id WHERE a.id =?`;
  let taskCar = await ctx.db.query(sql, [id]);

  taskCar[0]["lngAndLat"] = await ctx.db.query(`SELECT lat,lng FROM wsd_car_gps_l WHERE device_id = '${taskCar[0].tachograph}'`);
  ctx.body = {
    list: taskCar,
  };
});
/**
 * 查询所有运行车辆的当前位置
 */
router.get("/getRunCarPosition", async (ctx) => {
  let sql = `SELECT c.* FROM
  (select DISTINCT a.id,a.car_no, a.tachograph, b.lat,b.lng from wsd_car_b a
  LEFT JOIN wsd_car_gps_l b on a.tachograph = b.device_id
  WHERE a.st='ZXZ'
  ORDER BY b.create_time) c GROUP BY c.id`;
  let allCarInfo = await ctx.db.query(sql);
  let alarmSql = `select COUNT(1) alarmCount from wsd_car_b a
  left JOIN wsd_carroom_icebox_f b ON a.id = b.carroom_id
  left JOIN wsd_dev_b c ON a.dev_id = c.id
  left JOIN wsd_dev_b d on b.icebox_id = d.id
  WHERE a.id=? and (c.alarm_yn = 'Y' OR d.alarm_yn = 'Y')`;
  for (let i = 0; i < allCarInfo.length; i++) {
    let alarmNum = await ctx.db.query(alarmSql, [allCarInfo[i].id]);
    if (alarmNum > 0) {
      allCarInfo[i]["isAlarm"] = "Y";
    } else {
      allCarInfo[i]["isAlarm"] = "N";
    }
  }
  ctx.body = {
    list: allCarInfo,
  };
});
/**
 * 检查车牌号是否存在
 */
router.get("/checkCarNo", async (ctx) => {
  let { car_no } = ctx.query;
  let isExist = false;
  rows = await ctx.db.query(`SELECT car_no FROM wsd_car_b WHERE car_no=?`, [car_no]);
  if (rows.length > 0) {
    isExist = true
  } else {
    isExist = false
  }
  ctx.body = {
    isExist
  }
})
module.exports = router.routes();