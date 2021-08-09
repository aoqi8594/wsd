<template>
  <div class="car">
    <el-row>
      <el-col :span="18">
        <div class="carname"><div class="goback" @click="goback"><i class="iconfont iconweibiaoti-1_huaban1fuben4"></i>返回</div>{{cardata.car_no}}冷链车信息</div>
        <div id="container" style="height:calc(100vh - 0.9rem);"></div>
      </el-col>
      <el-col :span="6" class="colright">
        <Eltable ref="Eltable"></Eltable>
        <Warningmessage height="26vh" height2="20vh"  ref="alarmlist"></Warningmessage>
        <Eltable2 ref="Eltable2"></Eltable2>
      </el-col>
    </el-row> 
  </div>
</template>
<script>
import Eltable from "@/components/Eltable.vue";
import Eltable2 from "@/components/Eltable2.vue";
import Warningmessage from "@/components/Warningmessage.vue";
export default {
  components: {
    Eltable,
    Eltable2,
    Warningmessage
  },
  data() {
    return {
      path: [],
      cardata:'',
      devlist:[],
      
    };
  },
  mounted() {
    let that = this;
    if(this.$route.params.id){
      this.trajectory(this.$route.params.id);
      this.getLayerdata(this.$route.params.id);
      this.getCaralarmlist(this.$route.params.id);
    }else{
      this.trajectory(JSON.parse(localStorage.getItem("cardata")).id);
      this.getLayerdata(JSON.parse(localStorage.getItem("cardata")).id);
      this.getCaralarmlist(JSON.parse(localStorage.getItem("cardata")).id);
    }
    this.fun1();
  },
  methods: {
    trajectory(id){
       this.$api.vehicleManagement.getRunCarInfo({id:id}).then(data => {
        if (data.code == 0) {
           this.cardata=data.data.list[0]
           this.path=data.data.list[0].lngAndLat;
          this.pathStart();
        }else{
            this.$message.error(data.msg);
        }
      }); 
    },
    fun1(){
      const timer = setInterval(()=>{
         this.getLayerdata(this.cardata.id);
         this.getCaralarmlist(this.cardata.id);
      },30000);
      this.$once('hook:beforeDestroy',()=>{
        clearInterval(timer);
      })
    },
    getCaralarmlist(id){
      this.$api.alarm.getCaralarmlist({ carroom_id: id}).then(data => {
        if (data.code == 0) {
              this.$refs.alarmlist.init(data.data.list)
        }else{
            this.$message.error(data.msg);
        }
      }); 
    },
    getLayerdata(id) {
      this.ftag=true;
      this.$api.refrigerator
        .getrefrigeratorlist({ carroom_id: id, type: "LLC" })
        .then(data => {
          if (data.code == 0) {
            let array = data.data.list;
            let devlist = [];
            for (let i = 0; i < array.length; i++) {
              devlist = devlist.concat(array[i].devList);
            }
            this.devlist = devlist;
            this.$refs.Eltable.init(devlist,id);
            this.$refs.Eltable2.init(devlist);
          } else {
            this.$message.error(data.msg);
          }
        }); 
    },
    // 创建地图实例,并给设置移动路径
    pathStart() {
      let that = this;
      // GL版命名空间为BMapGL
      // 按住鼠标右键，修改倾斜角和角度
      var map = new BMap.Map("container"); // 创建Map实例
      var point2 = new BMap.Point(this.path[this.path.length-1].lng, this.path[this.path.length-1].lat);
      map.centerAndZoom(point2, 16); // 初始化地图,设置中心点坐标和地图级别
      map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
      // map.addEventListener('click', function (e) {
      //     console.log(e)
      // });
      var gc = new BMap.Geocoder();
      let addr='';
gc.getLocation(point2, function(rs){
   var addComp = rs.addressComponents;
   addr=addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber;
   var point = [];
      for (var i = 0; i < that.path.length; i++) {
        point.push(new BMap.Point(that.path[i].lng, that.path[i].lat));
      }
      var pl = new BMap.Polyline(point);
      map.addOverlay(new BMap.Polyline(point, {
        strokeColor: "#1A90FD",
        strokeWeight: 5,
        strokeOpacity: 1
      }));
      var myIcon = new BMap.Icon("https://z3.ax1x.com/2021/04/13/crHwEn.png", new BMap.Size(62, 62));
      // var myIcon = new BMap.Icon("https://webapi.amap.com/images/car.png", new BMap.Size(52, 26));
      // 创建Marker标注，使用小车图标
       var pt = new BMap.Point(that.path[0].lng, that.path[0].lat);
      var pt2 = new BMap.Point(that.path[that.path.length-1].lng, that.path[that.path.length-1].lat);
      var marker = new BMap.Marker(pt, {
        icon: myIcon
      });
      var marker2 = new BMap.Marker(pt2, {
        icon: myIcon
      });

      // 创建信息窗口
      var opts = {
        width: 300,
        height: 200,
        title: that.cardata.car_no
      };
      let str='';
      // if(this.cardata.length>0){
         str = `<div class="targetclass">
                  <div id="myChar" style="width: '100%'; height: '180px';margin-top: 10px; ">
                    <div style="line-height: 24px;">车内温度：${that.cardata.temperature}℃</div>
                    <div style="line-height: 24px;">车内湿度：${that.cardata.humidity}%RH</div>
                    <div style="line-height: 24px;">驾驶人：${that.cardata.driver_name}</div>
                    <div style="line-height: 24px;">联系电话：${that.cardata.contact_phone}</div>
                    <div style="line-height: 24px;">出发地：${that.cardata.start_place}</div>
                    <div style="line-height: 24px;">目的地：${that.cardata.end_place}</div>
                    <div style="line-height: 24px;">当前车辆位置：${addr}</div>
                  </div>
                </div>`
      // }else{
      //     str= `<div id="myChar" style="width: '100%';height: '80';pxtext-align: center;margin-top: 10px; ">
      //              未获取到车辆信息
      //           </div>`
      // }
      
      var infoWindow = new BMap.InfoWindow(str, opts);
      map.openInfoWindow(infoWindow, new BMap.Point(that.path[that.path.length-1].lng, that.path[that.path.length-1].lat)); // 开启信息窗口
      marker2.addEventListener('click', function (e) {
         map.openInfoWindow(infoWindow, new BMap.Point(that.path[that.path.length-1].lng, that.path[that.path.length-1].lat)); // 开启信息窗口
      })
      // 将标注添加到地图
      map.addOverlay(marker);
      map.addOverlay(marker2);
});
      
    },
    goback(){
      this.$router.push({name:'coldcar'})
    },
  },
};
</script>
<style lang="scss" scoped>
.car {
  height: calc(100vh - 50px);
  .el-row {
    height: 100%;
    margin-bottom: 20px;
    .el-col {
      height: 100%;
      .carname {
        height: 0.4rem;
        line-height: 0.4rem;
        padding-left: 2%;
        .goback{
          width: 4%;  
            display: inline-block;
            height: 0.28rem;   
            margin-top: .06rem;  
            line-height: 0.28rem;
            font-size: .12rem;
            border-radius: .04rem;
            background-color: #1a90fd;
            text-align: center;
            color: #fff;
            margin-right: 1%;
            // margin-left: 1.5%;
            &:hover{
              background-color: #369EFD;
              cursor:pointer;
            }
        }
      }
    }

    .colright {
      box-shadow: 0px 0px 13px rgba(0, 0, 0, 0.08);
      padding: 0.16rem;
    }
  }
}
</style>
