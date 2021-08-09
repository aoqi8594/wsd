<template>
  <div class="home">
    <el-row>
      <el-col :span="18">
        <Layergroup @returnvalue='returnvalue'></Layergroup>
        <div class="grid-content bg-purple">
          <el-scrollbar ref="myScrollbar" style="height: 84vh">
            <Roomlist ref="roomlist"></Roomlist>
          </el-scrollbar>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple-light">
          <div class="light-top">
            <div class="target">
              <div class="targetbox">
                <div class="targetclass">
                  <div class="targetlist" style="width: 49%">
                    <span><i class="iconfont iconqita2_huaban1fuben2"></i>正常</span>
                    <span class="num">{{total-alarms}}</span>
                  </div>
                  <div class="targetlist" style="width: 49%">
                    <span><i class="iconfont iconbaojing"></i>报警</span>
                    <span class="num">{{alarms}}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="light-up">
            <p>报警次数统计</p>
            <div id='statistical' :style="{ width: '100%', height: '96%' }"></div>
          </div>
        </div>
      </el-col>
    </el-row>
    <Eldialog ref="eldialog" title="报警/预警信息" height="6.26rem" width="12.6rem"></Eldialog>
  </div>
</template>
<script> 
import Layergroup from "@/components/Layergroup.vue";
import Roomlist from "@/components/Roomlist.vue";
import Brokenline from "@/components/Brokenline.vue";
export default {
  components: {
    Layergroup,
    Roomlist,
    Brokenline,
  },
  data() {
    return {
      roomlist: [],
      total: 0,
      alarms: 0,
    };
  },
  mounted() { 
        this.$refs.eldialog.thermometer('SYS')
  },
  methods: {
    returnvalue(id, floor_cd, build_name) {
      this.total = 0;
      this.alarms = 0;
      this.$api.building.buildingfloor({ build_id: id, floor_cd: floor_cd }).then((data) => {
        if (data.code == 0) {
          let roomlist = data.data.list;
          for (let i = 0; i < roomlist.length; i++) {
            this.total += parseFloat(roomlist[i].total_dev);
            this.alarms += parseFloat(roomlist[i].alarmsNum);
            if (roomlist[i].devInfo) {
              roomlist[i]["alarmNum"] = roomlist[i].devInfo.filter(item => {
                if (item.alarm_yn === "Y") {
                  return item
                }
              }).length
            } else {
              roomlist[i]["alarmNum"] = 0
            }
          }
          this.$refs.roomlist.init(roomlist, build_name)
        } else {
          this.$refs.roomlist.init([], build_name);
          this.total = 0;
          this.alarms = 0;
          this.$message.error(data.msg);
        }
      });
      this.policestatistics(id, floor_cd)
    },
    policestatistics(id, floor_cd) {
      this.$api.chartapi.getFloorCountChart({ build_id: id, floor_cd: floor_cd }).then((data) => {
        if (data.code == 0) {
          let list=data.data.list;
          if(list.length>0){
            // let time=[],num=[];
             let time = ["1h", "2h", "3h", "4h", "5h", "6h", "7h", "8h", "9h", "10h", "11h", "12h", "13h", "14h", "15h", "16h", "17h", "18h", "19h", "20h", "21h", "22h", "23h", "24h"];
            let num = ["", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""];
            for (let j = 1; j < 24; j++) {
              // for (let i = 0; i < array.length; i++) {
              //   if (j == array[i].create_time) {
              //     temperature[j - 1] = array[i].temperature;
              //     humidity[j - 1] = array[i].humidity;
              //   }
              // }
              for (let i = 0; i < list.length; i++) {
                if (j == list[i].hours) {
                  //  time[i]=list[i].hours
                   num[i]=list[i].num
                }
              }
            }
            
            this.statistical(time,num);
          }else{
            this.statistical([],[]);
          }
        } 
      });
    },
    statistical(time,num) {
      let myChart = this.$echarts.init(document.getElementById("statistical"));
      var option;
      option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '5%',
          top: '7%',
          right: '12%',
          bottom: '3%',
          containLabel: true
        }, 
        xAxis: {
          name: '次数',
          nameLocation: 'end',
          splitNumber:'10',
          type: 'value',
          axisLabel: {
            textStyle: {
              color: '#CCCCCC',
            },
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "#CCCCCC",
              width: 1,
              type: "solid"
            }
          },
          axisTick:{       //y轴刻度线
              show:true
          },
          splitLine: {
            show: true,
            lineStyle: {
              type: 'dashed'
            }
          },
          boundaryGap: [0, 0.01]
        },
        yAxis: {
          name: '小时',
          nameLocation: 'start',
          type: 'category',
          inverse: true,
          axisLabel: {
            textStyle: {
              color: '#CCCCCC',
            },
          },
          axisLine: {//y轴线的颜色以及宽度
            show: true,
            lineStyle: {
              color: "#CCCCCC",
              width: 1,
              type: "solid"
            },
          },
          axisTick: {
            show: false
          },
          data: time
        },
        series: [
          {
            type: 'bar',
            barWidth: 10,//柱图宽度
            itemStyle: {
              //柱形图圆角，鼠标移上去效果，如果只是一个数字则说明四个参数全部设置为那么多
              normal: {
                //柱形图圆角，初始化效果
                barBorderRadius: [0, 5, 5, 0],
                color: '#1A90FD'
              }
            },
            data: num
          }
        ]
      };
      option && myChart.setOption(option);
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
    padding: 0.3rem;
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
              background: rgba(42, 195, 180, 0.08);
              border: 2px solid rgba(42, 195, 180, 0.6);
              color: rgba(42, 195, 180, 0.6);
            }
            .targetlist:nth-child(2) {
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
      padding: 0.2rem 0 0;
      height: calc(100vh - 2.6rem);
      p {
        font-size: 0.14rem;
        font-weight: bold;
        color: #1a1a1a;
        padding: 0 0.2rem;
      }
    }
  }
}
</style>
