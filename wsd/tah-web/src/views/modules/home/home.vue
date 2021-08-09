<template>
  <div class="home">
    <el-row>
      <el-col :span="18">
        <div class="grid-content bg-purple">
          <Roomlist2 ref="roomlist2"></Roomlist2>
          <div class="vehicle">
            <div id="container2"></div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple-light">
          <div class="light-top">
            <!-- <div class="personnel">
              <span class="name">鹿野</span> 
              <span class="position">负责人</span>
              <span class="phone">
                <i class="iconfont icon-shouji"></i>
                13688828555
              </span>
            </div> -->
            <div class="target">
              <div class="targetbox">
                <p>实验室</p>
                <div class="targetclass">
                  <div class="targetlist" style="width: 31%">
                    <span>全部</span>
                    <span class="num">{{roomnum.total}}</span>
                  </div>
                  <div class="targetlist" style="width: 31%">
                    <span>正常</span>
                    <span class="num">{{roomnum.normal}}</span>
                  </div>
                  <div class="targetlist" style="width: 31%">
                    <span>报警</span>
                    <span class="num">{{roomnum.alarms}}</span>
                  </div>
                </div>
              </div>
              <div class="targetbox">
                <p>冷链车</p>
                <div class="targetclass">
                  <div class="targetlist" style="width: 31%">
                    <span>全部</span>
                    <span class="num">{{carnum.total}}</span>
                  </div>
                  <div class="targetlist" style="width: 31%">
                    <span>正常</span>
                    <span class="num">{{carnum.normal}}</span>
                  </div>
                  <div class="targetlist" style="width: 31%">
                    <span>报警</span>
                    <span class="num">{{carnum.alarms}}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <Warningmessage height="61vh" height2="55vh" ref='alarmlist'></Warningmessage>
        </div>
      </el-col>
    </el-row>
    <Eldialog ref="eldialog" title="报警/预警信息" height="6.26rem" width="12.6rem"></Eldialog>
  </div>
</template>
<script>
import Roomlist2 from "@/components/Roomlist2.vue";
import Warningmessage from "@/components/Warningmessage.vue";
import BMap from 'BMap'
export default {
  components: {
    Roomlist2,
    Warningmessage
  },
  data() {
    return {
      // alarmbox: true,
      multipleSelection: [],
      path: [],
      carnum: '',
      roomnum: ''
    };
  },
  mounted() {
    let that = this;
    this.$refs.eldialog.thermometer('')
    this.$nextTick(() => {
      this.getRunCarPosition()
    });
    this.allroom();//查询所有房间状态统计
    this.allcar();//查询车辆状态统计
    this.getAllAlarms();
    this.$api.building.roomhome().then(data => {
      if (data.code == 0) {
        this.$refs.roomlist2.init(data.data)
      } else {
        this.$message.error(data.msg);
      }
    });
  },
  methods: {
    getRunCarPosition() {
      this.$api.vehicleManagement.getRunCarPosition({}).then(data => {
        if (data.code == 0) {
          let array = data.data.list;
          for (let i = 0; i < array.length; i++) {
            this.path.push(array[i])
          }
          this.pathStart();
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    getAllAlarms() {
      this.$api.alarm.getAllAlarms({
        "page": 1,
        "limit": 100
      }).then(data => {
        if (data.code == 0) {
          this.$refs.alarmlist.init(data.data.list)
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //查询所有房间状态统计
    allroom() {
      this.$api.building.allroom().then(data => {
        if (data.code == 0) {
          this.roomnum = data.data;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //查询车辆状态统计
    allcar() {
      this.$api.vehicleManagement.allcar().then(data => {
        if (data.code == 0) {
          this.carnum = data.data;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    // 创建地图实例,并给设置移动路径
    pathStart() {
      let that = this;
      // GL版命名空间为BMapGL
      var map = new BMap.Map("container2"); // 创建Map实例
      if (this.path.length > 0) {
        map.centerAndZoom(new BMap.Point(this.path[0].lng, this.path[0].lat), 11); // 初始化地图,设置中心点坐标和地图级别
      } else {
        map.centerAndZoom(new BMap.Point(116.427265, 39.918698), 11); // 初始化地图,设置中心点坐标和地图级别
      }
      map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
      let imglist = [
        "https://z3.ax1x.com/2021/04/13/crHwEn.png",
        "https://z3.ax1x.com/2021/04/13/crXdfI.png"
      ];
      var myIcon = new BMap.Icon(
        imglist[0],
        new BMap.Size(62, 62)
      );
      var myIcon3 = new BMap.Icon(
        imglist[1],
        new BMap.Size(83, 168)
      );
      if (this.path.length > 0) {
        for (let i = 0; i < this.path.length; i++) {
          // 创建Marker标注，使用小车图标
          var pt2 = new BMap.Point(this.path[i].lng, this.path[i].lat);
          var marker;
          if (i == 4) {
            marker = new BMap.Marker(pt2, { icon: myIcon3 });
          } else {
            marker = new BMap.Marker(pt2, { icon: myIcon });
          }
          map.addOverlay(marker);
          marker.addEventListener('click', e => {
            this.$router.push({ name: 'car', params: { id: this.path[i].id, parent: "home" } })
          });
        }
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.home {
  height: calc(100vh - 50px);
  .el-row {
    height: 100%;
    margin-bottom: 20px;
    .el-col {
      height: 100%;
    }
  }
  .bg-purple {
    height: 100%;
    background: #f5f7fa;
    padding: 0.3rem 0;
    .vehicle {
      margin-top: 1vh;
      margin-left: 0.3rem;
      margin-right: 0.3rem;
      height: calc(100% - 4.2rem);
      #container {
        height: 100%;
      }
      #container2 {
        height: 100%;
        ::v-deep .BMap_bubble_content {
          .targetclass {
            .targetlist {
              height: 100px;
            }
          }
        }
      }
    }
  }
  .bg-purple-light {
    height: 100%;
    background: #fff;
    padding: 0.16rem;
    .light-top {
      border: 1px solid #f0f0f0;
      opacity: 1;
      border-radius: 0.09rem;
      padding: 0.2rem;
      .personnel {
        font-size: 0.12rem;
        margin-bottom: 0.4rem;
        color: #999999;
        .phone {
          float: right;
        }
        .name {
          font-size: 0.18rem;
          padding-right: 0.1rem;
          color: #1a1a1a;
        }
      }
      .target {
        font-size: 0.14rem;
        .targetbox {
          .targetclass {
            margin-top: 0.1rem;
            display: flex;
            justify-content: space-between;
            .targetlist {
              height: 0.8rem;
              box-shadow: 0px 0.03rem 0.12rem rgba(0, 0, 0, 0.06);
              opacity: 0.6;
              border-radius: 0.1rem;
              padding: 0.14rem;
              .num {
                font-size: 0.19rem;
                font-weight: 600;
                display: block;
                margin-top: 0.1rem;
              }
            }
            .targetlist:nth-child(1) {
              background: rgba(26, 144, 253, 0.08);
              border: 0.02rem solid #1a90fd;
              color: #1a90fd;
            }
            .targetlist:nth-child(2) {
              background: rgba(42, 195, 180, 0.08);
              border: 2px solid rgba(42, 195, 180, 0.6);
              color: rgba(42, 195, 180, 0.6);
            }
            .targetlist:nth-child(3) {
              background: rgba(255, 101, 109, 0.08);
              border: 2px solid rgba(255, 101, 109, 0.6);
              color: rgba(255, 101, 109, 0.6);
            }
          }
        }
        .targetbox:nth-child(1) {
          margin-bottom: 0.2rem;
        }
      }
    }
  }
}
</style>
