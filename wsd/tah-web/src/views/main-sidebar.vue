<template>
  <div class="asidebox">
    
    <div class="menu" v-for="(item, index) in menulist" :key="index" :class="item.name==isCollapse?'active':''" @click="menuClick(item.name)">
      <i class="iconfont ifont" :class="item.iconfont"></i>
      <span class="">{{item.label}}</span>
      <div class="menubox" v-if="item.child.length>0">
          <div class="menuboxcentent">
            <div class="menuchild" v-for="(it, idx) in item.child" :key="idx" @click.stop="menuClick(it.name)" :class="menuData==it.name?'active2':''">
              <i class="iconfont" :class="it.iconfont"></i><span >{{it.label}}</span>
            </div>
          </div>
      </div>
    </div>
    <div class="setup">
      <i class="iconfont iconshezhi"></i>
      <div class="menubox">
          <div class="menuboxcentent">
            <div class="menuchild" @click="menuClick('Alarmset')">
              <i class="iconfont iconweibiaoti-1_huaban1fuben5"></i><span >报警设置</span>
            </div>
            <div class="menuchild"  @click="menuClick('Exportset')">
              <i class="iconfont iconweibiaoti-1_huaban1fuben2"></i><span >导出设置</span>
            </div>
          </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      menulist:[
        {
          name:'home',
          label:'首页',
          iconfont:'iconshouye',
          child:[]
        },
        {
          name:'laboratory',
          label:'实验室首页',
          iconfont:'iconshouye',
          child:[]
        },
        {
          name:'coldcar',
          label:'冷链车首页',
          iconfont:'iconshouye',
          child:[]
        },
        {
          name:'build',
          label:'楼栋管理',
          iconfont:'iconloudongguanli',
          child:[]
        },
        {
          name:'gauge',
          label:'温湿度计',
          iconfont:'iconweibiaoti-1_huaban1',
          child:[
            {
              name:'Thermometer',
              label:'温湿度计管理',
              iconfont:'iconyiqiguanli',
            },
            {
              name:'parameter',
              label:'温湿度计台账',
              iconfont:'iconyiqitaizhang',
            },
            {
              name:'checkrecord',
              label:'校对记录',
              iconfont:'iconweibiaoti-1_huaban1fuben3',
            }
          ]
        },
        {
          name:'Vehiclemanager',
          label:'车载管理',
          iconfont:'iconcheliangguanli',
          child:[]
        },
        {
          name:'Alertmanager',
          label:'报警管理',
          iconfont:'iconbaojing',
          child:[]
        },
        {
          name:'Historicaldata',
          label:'历史数据',
          iconfont:'iconlishishuju',
          child:[]
        },
        {
          name:'Exportrecord',
          label:'导出记录',
          iconfont:'iconweibiaoti-1_huaban1fuben2',
          child:[]
        },
      ],
      isCollapse:'home',
      menuData:''
    };
  },

  // //利用计算属性
  // computed: {
  //   getlist() {
  //     return this.$store.state.user.status;
  //   }
  // },
  // // //监听执行
  // watch: {
  //   getlist(val) {
  //     this.isCollapse = val.status;
  //   }
  // },
  watch:{
    $route(to,from){
      if(to.path=='/parameter'&&from.path=='/devicemanagement'){
        this.isCollapse = 'gauge';
        this.menuData='parameter';
      }
      else if(to.path=='/Alertmanager'&&from.path=='/home'){
        this.isCollapse = 'Alertmanager';
        this.menuData='';
      }
      else if(to.path=='/Historicaldata'&&from.path=='/cardetails'){
        this.isCollapse = 'Historicaldata';
        this.menuData='';
      }
    }
},
  created() {
    // console.log(11111)
    // console.log(this.$store.state.user.status)
    var str = this.$route.path.substr(1);
    //  console.log(str)
    if(str=='Thermometer'){
      this.isCollapse = 'gauge';
       this.menuData='Thermometer';
    }else if(str=='parameter'){
      this.isCollapse = 'gauge';
       this.menuData='parameter';
    }else if(str=='checkrecord'){
      this.isCollapse = 'gauge';
       this.menuData='checkrecord';
    }else if(str=='devicemanagement'){
       if(localStorage.getItem("pash")=='home'){
          this.isCollapse = localStorage.getItem("pash");
       }else{
         this.isCollapse = localStorage.getItem("pash");
       }
       this.menuData='';
    }else{
      this.menuData='';
      this.isCollapse = str;
    }
  },
  methods: {
    // 点击菜单回调
    menuClick(urlPath) {
      // console.log(urlPath);
      if(urlPath){
        if(urlPath=='gauge'){

        }else if(urlPath=='Thermometer'){
          this.isCollapse='gauge';
          this.menuData=urlPath;
          this.$router.push({ name: urlPath }).catch(err => { });
        }else if(urlPath=='parameter'){
          this.isCollapse='gauge';
          this.menuData=urlPath;
          this.$router.push({ name: urlPath }).catch(err => { });
        }else if(urlPath=='checkrecord'){
          this.isCollapse='gauge';
          this.menuData=urlPath;
          this.$router.push({ name: urlPath }).catch(err => { });
        }else{
          this.isCollapse=urlPath;
          this.menuData='';
          this.$router.push({ name: urlPath }).catch(err => { });
        }
      } 
    }
  }
};
</script>
<style lang="scss" scoped>
.asidebox {
  padding-top: .4rem;
  .active{
    color: #fff !important;
    background-color: #369EFD;
  }
  .menu{
    text-align: center;
    height: 0.8rem;
    padding-top: .15rem;
    font-size: .14rem;
    color: #8DC7FE;
    &:hover{
      cursor:pointer;
      color: #fff;
      background-color: #369EFD;
      .menubox{
        display: block;
        color: #666666;
      }
    }
    .ifont {
      font-size: 0.24rem;
      display: block;
      margin-bottom: .04rem;
    }
    .menubox {
      display: none;
      // width: 1.2rem;
      position: fixed;
      top:3.6rem;
      left: 0.82rem;
      .menuboxcentent{
        padding: .1rem 0;
        margin-left: .06rem;
        text-align: left;
         background-color: #fff;
          box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.16);
          height: 100%;
          border-radius: .06rem;
          .menuchild{
            padding: 0 .2rem;
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
          .active2{
            background: #F7F7F7;
            color: #1A90FD;
            .iconfont{
               color: #1A90FD;
            }
          }
      }
    }
  }
  .setup {
    position: absolute;
    bottom: 0;
    width: 100%;
    color: rgb(104, 183, 255);
    text-align: center;
    height: 0.66rem;
    &:hover{
      cursor:pointer;
       color: #fff;
      background-color: #369EFD;
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
      position: fixed;
      bottom: 0;
      left: 0.82rem;
      
      .menuboxcentent{
        padding: .1rem 0;
         margin-left: .06rem;
        text-align: left;
         background-color: #fff;
          box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.16);
          height: 100%;
          border-radius: .06rem;
          .menuchild{
            padding: 0 .2rem;
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
 
}
</style>