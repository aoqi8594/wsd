<template>
  <div class="home">
    <el-row>
      <el-col :span="18">
        <div class="grid-content bg-purple">
          <div class="vehicle">
            <div id="container"></div>
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
              <!-- <div class="targetbox">
                <p>实验室</p>
                <div class="targetclass">
                  <div class="targetlist" style="width: 31%">
                    <span>全部</span>
                    <span class="num">30</span>
                  </div>
                  <div class="targetlist" style="width: 31%">
                    <span>正常</span>
                    <span class="num">30</span>
                  </div>
                  <div class="targetlist" style="width: 31%">
                    <span>报警</span>
                    <span class="num">30</span>
                  </div>
                </div>
              </div> -->
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
          <div class="light-up">
            <p>运行车辆</p>
            <el-scrollbar style="height:calc(100vh - 3.4rem);width: 100%;">
              <div class="lists">
                <div class="list" v-for="(item, index) in lists" :key="index" @click="gopage(item)">
                  <div class="listcar list-box">
                    <div class="listcar-box1">{{item.car_no}}</div>
                    <div class="listcar-box2">{{item.driver_name}}</div>
                    <div class="listcar-box2">{{item.contact_phone}}</div>
                  </div>
                  <div class="listaddress list-box">
                    <div class="startaddress addressbox"><span class=""></span>{{item.start_place}}</div>
                    <div class="endaddress addressbox"><span class=""></span>{{item.end_place}}</div>
                  </div>
                </div>
              </div>
            </el-scrollbar>
          </div>
        </div>
      </el-col>
    </el-row>
    <Eldialog ref="eldialog" title="报警/预警信息" height="6.26rem" width="12.6rem"></Eldialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      lists: [],
      path: [],
      carnum: '',
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.allcar();
      this.getRunCarPosition()
      this.thermometer();//查询获取车辆列表
    })
    this.$refs.eldialog.thermometer('LLC')
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
    //查询获取车辆列表
    thermometer() {
      this.$api.vehicleManagement.getvehicleList({ driver_name: '', car_no: '', page: 1, limit: 1000, }).then(data => {
        if (data.code == 0) {
          this.lists = data.data.list
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
      var map = new BMap.Map("container"); // 创建Map实例
      if (this.path.length > 0) {
        map.centerAndZoom(new BMap.Point(this.path[0].lng, this.path[0].lat), 11); // 初始化地图,设置中心点坐标和地图级别
      } else {
        map.centerAndZoom(new BMap.Point(116.427265, 39.918698), 11); // 初始化地图,设置中心点坐标和地图级别
      }
      map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
      var myIcon = new BMap.Icon("https://z3.ax1x.com/2021/04/13/crHwEn.png", new BMap.Size(62, 62));
      var myIcon3 = new BMap.Icon("https://z3.ax1x.com/2021/04/13/crXdfI.png", new BMap.Size(83, 108));
      if (this.path.length > 0) {
        for (let i = 0; i < this.path.length; i++) {
          // 创建Marker标注，使用小车图标
          var pt2 = new BMap.Point(this.path[i].lng, this.path[i].lat);
          var marker;
          if (i == 4) {
            marker = new BMap.Marker(pt2, {
              icon: myIcon3
            });
          } else {
            marker = new BMap.Marker(pt2, {
              icon: myIcon
            });
          }
          map.addOverlay(marker);
          marker.addEventListener('click', e => {
            this.$router.push({ name: 'cardetails', params: { id: this.path[i].id } })
          });
        }
      }
    },
    gopage(item) {
      localStorage.setItem("cardata", JSON.stringify(item));
      this.$router.push({ name: 'cardetails', params: { id: item.id } })
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
    .vehicle {
      height: 100%;
      #container {
        height: 100%;
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
    .light-up {
      margin-top: 0.14rem;
      border: 1px solid #f0f0f0;
      opacity: 1;
      border-radius: 0.09rem;
      padding: 0.2rem 0;
      height: calc(100vh - 2.8rem);
      p {
        font-size: 0.14rem;
        font-weight: bold;
        color: #1a1a1a;
        padding: 0 0.2rem 0.1rem;
      }
      .lists {
        padding: 0 0.2rem;
        .list {
          background: #ffffff;
          box-shadow: 0px 0.02rem 0.1rem rgba(0, 0, 0, 0.08);
          opacity: 1;
          margin-bottom: 0.08rem;
          border-radius: 0.08rem;
          border: 1px solid #eeeeee;
          padding-left: 0.2rem;
          display: flex;
          padding: 0.1rem 0 0.1rem 0.2rem;
          .listcar {
            width: 40%;
            .listcar-box1 {
              font-size: 0.16rem;
              color: #1a1a1a;
              font-weight: bold;
              line-height: 22px;
              margin-bottom: 0.06rem;
            }
            .listcar-box2 {
              font-size: 0.14rem;
              line-height: 18px;
              color: #999999;
            }
          }
          .listaddress {
            width: 60%;
            .addressbox {
              height: 50%;
              line-height: 2;
              font-size: 0.14rem;
              span {
                display: inline-block;
                width: 0.08rem;
                height: 0.08rem;
                border-radius: 50%;
                margin-right: 0.06rem;
              }
            }
            .startaddress {
              color: #1a90fd;
              span {
                background-color: #1a90fd;
              }
            }
            .endaddress {
              color: #ff656d;
              span {
                background-color: #ff656d;
              }
            }
          }
        }
      }
    }
  }
}
</style>
