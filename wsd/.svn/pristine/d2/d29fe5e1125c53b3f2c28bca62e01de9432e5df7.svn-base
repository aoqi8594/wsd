import Vue from "vue";
import "normalize.css/normalize.css";
import Element from "element-ui";
import "@/utils/remResize.js"; // rem
import "./assets/scss/_element-variables.scss";
import "@/assets/scss/index.scss";
import "@/icons/iconfont.css";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import axios from 'axios'
import * as echarts from 'echarts';
Vue.prototype.$echarts = echarts;

import* as filters  from "./filters"; // global filters
import httpRequest from "@/utils/httpRequest";

import api from "./api";
import * as formatDate from "@/utils/formatDate";
Vue.use(Element);
// register global utility filters
Object.keys(filters).forEach((key) => {
  Vue.filter(key, filters[key]);
});
import vueSwiper from 'vue-awesome-swiper'
import 'swiper/dist/css/swiper.css'

Vue.use(vueSwiper)
// 挂载全局
Vue.prototype.$http = httpRequest; // ajax请求方法
Vue.prototype.$date = formatDate; // 时间格式化
Vue.prototype.$axios = axios;
Vue.prototype.$api = api; // api 接口
// // 引入vue-amap
// import AMap from 'vue-amap';
// setTimeout(() => {
//   localStorage.clear();
//   Vue.use(AMap);
// }, 10);
// AMap.initAMapApiLoader({
//   key: "2b7d8c853e159d20ee622a54f8508389",
//   plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor'],
//   // 默认高德 sdk 版本为 1.4.4
//   v: '1.4.4'
// });
// 全局组件
import Eldialog from "./components/Eldialog/index.js";
// import axios from "_axios@0.21.1@axios";
Vue.use(Eldialog);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
