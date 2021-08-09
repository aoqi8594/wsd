// 域名 + 端口号 /api/功能类型/具体端口
// {
//   "code": 0,
//   "msg": "成功",
//   "data": obj
// }
const Router = require("koa-router");
let router = new Router({
  prefix: "/api",
});
// 用户相关
router.use("/", require("./controllers/user"));
// 车辆管理
router.use("/", require("./controllers/car"));
// 楼栋管理
router.use("/", require("./controllers/building"));
// 房间管理
router.use("/", require("./controllers/room"));
// 设备管理
router.use("/", require("./controllers/equipment"));
// 校对
router.use("/", require("./controllers/proofread"));
// 冰柜管理
router.use("/", require("./controllers/icebox"));
// 报警设置
router.use("/", require("./controllers/alarms"));
// 导出相关
router.use("/", require("./controllers/export"));
// 历史数据
router.use("/", require("./controllers/history"));
// 字典查询相关
router.use("/", require("./controllers/dictSearch"));
// 统计图相关
router.use("/", require("./controllers/chart"));

module.exports = router;
