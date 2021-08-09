/**
 * 全站路由配置
 *
 * 建议:
 * 1. 代码中路由统一使用name属性跳转(不使用path属性)
 */
import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

// 开发环境不使用懒加载, 因为懒加载页面太多的话会造成webpack热更新太慢, 所以只有生产环境使用懒加载
const _import = require("./import-" + process.env.NODE_ENV);

// 全局路由(无需嵌套上左右整体布局)
const globalRoutes = [
  { path: "/404", component: _import("common/404"), name: "404", meta: { title: "404未找到" } },
  { path: "/login", component: _import("common/login"), name: "login", meta: { title: "登录" } },
];
// 主入口路由(需嵌套上左右整体布局)
const mainRoutes = [
  {
    path: "/",
    component: _import("main"),
    name: "main",
    redirect: { name: "home" },
    meta: { title: "主入口整体布局" },
    children: [
      {
        path: "/home",
        component: _import("modules/home/home"),
        name: "home",
        meta: { title: "首页" },
        // children: [
        //   {
        //     path: "/devicemanagement",
        //     meta: { title: "设备管理",},
        //     name: "devicemanagement",
        //     component: _import("modules/devicemanagement/devicemanagement"),
        //   },
        // ]
      },
      {
        path: "/build",
        component: _import("modules/build/build"),
        name: "build",
        meta: { title: "楼栋管理" },
      },
      {
        path: "/coldcar",
        component: _import("modules/coldcar/coldcar"),
        name: "coldcar",
        meta: { title: "冷链车" },
      },
      {
        path: "/laboratory",
        component: _import("modules/laboratory/laboratory"),
        name: "laboratory",
        meta: { title: "实验室" },
      },
      {
        path: "/devicemanagement",
        component: _import("modules/devicemanagement/devicemanagement"),
        name: "devicemanagement",
        meta: { title: "设备管理" },
      },
      {
        path: "/cardetails",
        component: _import("modules/cardetails/cardetails"),
        name: "cardetails",
        meta: { title: "冷链车详情" },
      },
      {
        path: "/car",
        component: _import("modules/car/car"),
        name: "car",
        meta: { title: "冷链车" },
      },
      {
        path: "/Historicaldata",
        component: _import("modules/Historicaldata/Historicaldata"),
        name: "Historicaldata",
        meta: { title: "历史数据" },
      },
      {
        path: "/Alertmanager",
        component: _import("modules/Alertmanager/Alertmanager"),
        name: "Alertmanager",
        meta: { title: "报警管理" },
      },
      {
        path: "/Alarmset",
        component: _import("modules/Alarmset/Alarmset"),
        name: "Alarmset",
        meta: { title: "报警设置" },
      },
      {
        path: "/Exportset",
        component: _import("modules/Exportset/Exportset"),
        name: "Exportset",
        meta: { title: "导出设置" },
      },
      {
        path: "/Vehiclemanager",
        component: _import("modules/Vehiclemanager/Vehiclemanager"),
        name: "Vehiclemanager",
        meta: { title: "车载管理" },
      },
      {
        path: "/parameter",
        component: _import("modules/parameter/parameter"),
        name: "parameter",
        meta: { title: "仪器台账" },
      },
      {
        path: "/Thermometer",
        component: _import("modules/Thermometer/Thermometer"),
        name: "Thermometer",
        meta: { title: "仪器管理" },
      },
      {
        path: "/checkrecord",
        component: _import("modules/checkrecord/checkrecord"),
        name: "checkrecord",
        meta: { title: "校对记录" },
      },
      {
        path: "/Exportrecord",
        component: _import("modules/Exportrecord/Exportrecord"),
        name: "Exportrecord",
        meta: { title: "导出记录" },
      },
      // {
      //   path: "/testss",
      //   component: _import("modules/testss/testss"),
      //   name: "testss",
      //   meta: { title: "测试" },
      // }
    ],
  },
];
const router = new VueRouter({
  mode: "hash",
  scrollBehavior: () => ({ y: 0 }),
  routes: globalRoutes.concat(mainRoutes),
});

export default router;
