<template>
  <aside class="asidebox">
    <el-menu ref="menu" :default-active="menuData" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" background-color="#1A90FD" text-color="#68B7FF" @select="menuClick" active-text-color="#fff">
      <!-- <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>首页</span>
        </template>
        <el-menu-item-group>
          <template slot="title">分组一</template>
          <el-menu-item index="1-1">选项1</el-menu-item>
          <el-menu-item index="1-2">选项2</el-menu-item>
        </el-menu-item-group>
        <el-menu-item-group title="分组2">
          <el-menu-item index="1-3">选项3</el-menu-item>
        </el-menu-item-group>
        <el-submenu index="1-4">
          <template slot="title">选项4</template>
          <el-menu-item index="1-4-1">选项1</el-menu-item>
        </el-submenu>
      </el-submenu> -->
      <el-menu-item index="home">
        <i class="iconfont iconshouye"></i>
        <span slot="title">首页</span>
      </el-menu-item>
      <el-menu-item index="build">
        <i class="iconfont iconloudongguanli"></i>
        <span slot="title">楼栋管理</span>
      </el-menu-item>
      <!-- <el-submenu index="1-4">
        <template slot="title">
          <i class="iconfont iconshouye"></i>冷链车
        </template>
        <el-menu-item index="coldcar">
          <i class="iconfont iconshouye"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item index="cardetails">
          <i class="iconfont iconqita_huaban1fuben6"></i>
          <span slot="title">车详情</span>
        </el-menu-item>
      </el-submenu> -->
      <el-menu-item index="coldcar">
        <i class="iconfont iconshouye"></i>
        <span slot="title">冷链车</span>
      </el-menu-item>
      <el-menu-item index="laboratory">
        <i class="iconfont iconshouye"></i>
        <span slot="title">实验室</span>
      </el-menu-item>
      <!-- <el-menu-item index="cardetails">
        <i class="iconfont iconqita_huaban1fuben6"></i>
        <span slot="title">车详情</span>
      </el-menu-item>-->
      <!-- <el-menu-item index="devicemanagement">
        <i class="iconfont iconbingxiangguanli"></i>
        <span slot="title">设备管理</span>
      </el-menu-item> -->
      <!-- <el-menu-item index="car">
        <i class="iconfont iconqita_huaban1fuben6"></i>
        <span slot="title">冷链车</span>
      </el-menu-item> -->
      <el-menu-item index="Historicaldata">
        <i class="iconfont iconlishishuju"></i>
        <span slot="title">历史数据</span>
      </el-menu-item>
      <!-- <el-menu-item index="Alertmanager">
        <i class="iconfont iconbaojing"></i>
        <span slot="title">报警管理</span>
      </el-menu-item>
      <el-menu-item index="Alarmset">
        <i class="iconfont iconqita_huaban1fuben6"></i>
        <span slot="title">报警设置</span>
      </el-menu-item> -->
      <el-menu-item index="Vehiclemanager">
        <i class="iconfont iconcheliangguanli"></i>
        <span slot="title">车载管理</span>
      </el-menu-item>
      <el-menu-item index="parameter">
        <i class="iconfont iconyiqitaizhang"></i>
        <span slot="title">仪器台账</span>
      </el-menu-item>
      <el-menu-item index="Thermometer" >
        <div class="yiqi" style="height: 0.66rem;" @mouseover="mouseover" @mouseleave="mouseleave">
           <i class="iconfont iconyiqiguanli"></i>
           仪器管理
        </div>
       
        <!-- <div class="menubox">
            <div class="menuboxcentent">
              <div class="menuchild">
                <i class="iconfont iconweibiaoti-1_huaban1fuben5"></i><span >报警设置</span>
              </div>
              <div class="menuchild">
                <i class="iconfont iconweibiaoti-1_huaban1fuben2"></i><span >导出设置</span>
              </div>
            </div>
        </div> -->
      </el-menu-item>
      <el-menu-item index="checkrecord">
        <i class="iconfont iconyiqiguanli"></i>
        <span slot="title">校对记录</span>
      </el-menu-item>
    </el-menu>
    <div class="setup">
      <i class="iconfont iconshezhi"></i>
      <div class="menubox">
          <div class="menuboxcentent">
            <div class="menuchild">
              <i class="iconfont iconweibiaoti-1_huaban1fuben5"></i><span >报警设置</span>
            </div>
            <div class="menuchild">
              <i class="iconfont iconweibiaoti-1_huaban1fuben2"></i><span >导出设置</span>
            </div>
          </div>
      </div>
    </div>
     <div class="menubox2" @mouseover="mouseover2" @mouseleave="mouseleave2" v-show="flag" :style="top">
          <div class="menuboxcentent">
            <div class="menuchild">
              <i class="iconfont iconweibiaoti-1_huaban1fuben5"></i><span >报警设置</span>
            </div>
            <div class="menuchild">
              <i class="iconfont iconweibiaoti-1_huaban1fuben2"></i><span >导出设置</span>
            </div>
          </div>
      </div>
  </aside>
</template>
<script>
export default {
  data() {
    return {
      // index:'Thermometer'
      menuData: "home",
      top:'top:0px',
      flag:false
    };
  },

  //利用计算属性
  computed: {
    getlist() {
      return this.$store.state.user.status;
    }
  },
  // //监听执行
  watch: {
    getlist(val) {
      this.menuData = val.status;
    }
  },
  created() {
    var str = this.$route.path;
    this.menuData = str.substr(1);
  },
  methods: {
    mouseover(event){
       console.log(event.target.getBoundingClientRect());
       this.top=`top:${event.target.getBoundingClientRect().top}px`;
       this.flag=true;
    },
    mouseleave(){
       this.flag=false;
    },
    mouseover2(){
       this.flag=true;
    },
    mouseleave2(){
       this.flag=false;
    },
    handleOpen(key, keyPath) {
      console.log(111);
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(333);
      console.log(key, keyPath);
    },
    // 点击菜单回调
    menuClick(index, urlPath) {
      console.log(urlPath);

      this.$router.push({ name: urlPath }).catch(err => { });
    }
  }
};
</script>
<style lang="scss" scoped>
.asidebox {
  .el-menu {
    border-right: solid 0px #e6e6e6;
    padding-top: 0.4rem;
    .el-menu-item {
      padding: 0 !important;
      text-align: center;
      line-height: 1;
      height: 0.66rem;
      .iconfont {
        display: block;
        padding-top: 0.1rem;
        padding-bottom: 0.06rem;
        font-size: 0.22rem;
        color: rgb(104, 183, 255);
      }
    }
    .is-active i {
      color: #fff !important;
    }
    .is-active .iconyiqiguanli{
      color: #68b7ff !important;   
    }
    .is-active .yiqi{
      color: #68b7ff !important;   
    }
    .yiqi:hover{
       color: #fff !important;
    }
  }
  .setup {
    position: absolute;
    bottom: 3%;
    width: 100%;

    color: rgb(104, 183, 255);
    
    text-align: center;
    // line-height: 0.66rem;
    height: 0.66rem;
    &:hover{
      cursor:pointer;
      color: #fff;
      background-color: rgb(21, 115, 202);
      .menubox{
        display: block;
        color: #666666;
      }
    }
    .iconshezhi {
      font-size: 0.24rem;
      margin-top: .21rem;
      display: block;
    }
    .menubox {
      display: none;
      width: 1.2rem;
      position: fixed;
      bottom: 3%;
      left: 0.82rem;
      
      .menuboxcentent{
        padding: .1rem 0;
         float: right;
         background-color: #fff;
          box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.16);
          width: 96%;
          height: 100%;
          border-radius: .06rem;
          .menuchild{
             height: .4rem;
            line-height: .4rem;
            font-size: .14rem;
            .iconfont{
              margin-right: .1rem;
              font-size: .2rem;
              color: #B3B3B3;
            }
          }
          .menuchild:hover{
            cursor:pointer;
            background: #F7F7F7;
            color: #1A90FD;
            .iconfont{
               color: #1A90FD;
            }
          }
      }
    }
  }
  .menubox2 {
      width: 1.2rem;
      position: fixed;
      left: 0.82rem;
      text-align: center;
      .menuboxcentent{
        padding: .1rem 0;
         float: right;
         background-color: #fff;
          box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.16);
          width: 96%;
          height: 100%;
          border-radius: .06rem;
          .menuchild{
             height: .4rem;
            line-height: .4rem;
            font-size: .14rem;
            .iconfont{
              margin-right: .1rem;
              font-size: .2rem;
              color: #B3B3B3;
            }
          }
          .menuchild:hover{
            cursor:pointer;
            background: #F7F7F7;
            color: #1A90FD;
            .iconfont{
               color: #1A90FD;
            }
          }
      }
    }
}
</style>