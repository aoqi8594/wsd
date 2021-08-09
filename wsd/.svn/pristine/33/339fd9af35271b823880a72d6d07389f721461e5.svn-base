<template>
  <div class="ullists">
    <div class="list" v-for="(item, index) in roomlist" :key="index" @click="choices(index,item.id,item.dev_no)" @dblclick="storageDetail(item.id)">
      <i class="iconfont iconqita2_huaban1fuben2" v-if="num==index"></i>
      <P>{{item.dev_name}}</P>
      <P>{{item.net_st=='WLZT_ZX'?'在线':'离线'}}</P>
      <div class="meter">
        <div class="meter-list">
          <div>
            <div :id="'myChart1' + index" :style="{ width: '100%', height: '100%' }" class="juzhong"></div>
          </div>
          <div class="unit">
            <i class="iconfont iconqita_huaban1fuben4"></i>温度(℃)
          </div>
        </div>
        <div class="meter-list">
          <div>
            <div :id="'myChart2' + index" :style="{ width: '100%', height: '100%' }" class="juzhong"></div>
          </div>
          <div class="unit">
            <i class="iconfont iconqita_huaban1fuben5" style="font-size:.13rem"></i>湿度(%RH)
          </div>
        </div>
      </div>
      <img alt class="police" v-if="item.st=='BJ'" src="../../assets/img/police.png" />
    </div>
  </div>
</template>
<script>
export default {
  name: "Freezer",
  props: {
    types: {
      type: String,
      default: ""
    },
  },
  data() {
    return {
      floorList: [],
      roomlist: [],
      build_name: "",
      total: '',
      alarms: '',
      num: 0,
      pash: ''
    };
  },
  created() { },
  mounted() {
    let that = this;
  },
  methods: {
    init(roomlist, num, pash) {
      let that = this;
      this.roomlist = roomlist;
      this.num = num;
      this.pash = pash;
      this.$nextTick(() => {
        that.drawLine();
      });
    },
    drawLine() {
      let that = this;
      for (let i = 0; i < this.roomlist.length; i++) {
        let color1 = '#F0F0F0';
        let color2 = '#F0F0F0';
        let data1 = '', data2 = '';
        if (that.roomlist[i].temperature == null) {
          data1 = 0;
        } else {
          that.roomlist[i].temperature = parseFloat(that.roomlist[i].temperature).toFixed(2);
          data1 = that.roomlist[i].temperature;
          let ture = parseFloat(that.roomlist[i].temperature);
          let up = parseFloat(that.roomlist[i].temp_up);
          let low = parseFloat(that.roomlist[i].temp_low);
          if (ture < up && ture > low) {
            color1 = '#2AC3B4'
          } else {
            color1 = '#FF656D'
          }
        }
        if (that.roomlist[i].humidity == null) {
          data2 = 0;
        } else {
          that.roomlist[i].humidity = parseFloat(that.roomlist[i].humidity).toFixed(2);
          data2 = that.roomlist[i].humidity;
          let humidity = parseFloat(that.roomlist[i].humidity);
          let hum_up = parseFloat(that.roomlist[i].hum_up);
          let hum_low = parseFloat(that.roomlist[i].hum_low);
          if (humidity > hum_low && humidity < hum_up) {
            color2 = '#2AC3B4'
          } else {
            color2 = '#FF656D'
          }
        }
        // 基于准备好的dom，初始化echarts实例
        let myChart1 = "myChart1" + i;
        let myChart2 = "myChart2" + i;
        let myChart = this.$echarts.init(document.getElementById(myChart1));
        let myChartow = this.$echarts.init(document.getElementById(myChart2));
        // 绘制图表
        var option, option2;
        option = {
          series: [
            {
              radius: '95%',
              startAngle: 210,
              endAngle: -30,
              type: "gauge",
              min: -200,				// 最小的数据值,默认 0 。映射到 minAngle。
              max: 151,
              progress: {
                show: true,
                width: 7,
                roundCap: true,
                itemStyle: {
                  color: color1,
                }
              },
              axisLine: {
                roundCap: true,
                lineStyle: {
                  width: 7,
                  color: [[1, "#F0F0F0"]]
                }
              },
              axisTick: {
                show: false
              },
              splitLine: {
                show: false
              },
              axisLabel: {
                show: false
              },
              pointer: {
                show: false
              },
              detail: {
                valueAnimation: true,
                fontSize: 14,
                formatter: function (value) {
                  return value + "℃";
                },
                color: color1,
                valueAnimation: true,
                offsetCenter: [0, "0%"]
              },
              data: [
                {
                  value: data1
                }
              ]
            }
          ]
        };
        option2 = {
          series: [
            {
              radius: '95%',
              startAngle: 210,
              endAngle: -30,
              type: "gauge",
              min: -200,				// 最小的数据值,默认 0 。映射到 minAngle。
              max: 151,
              progress: {
                show: true,
                width: 7,
                roundCap: true,
                itemStyle: {
                  color: color2,
                }
              },
              axisLine: {
                roundCap: true,
                lineStyle: {
                  width: 7,
                  color: [[1, "#F0F0F0"]]
                }
              },
              axisTick: {
                show: false
              },
              splitLine: {
                show: false
              },
              axisLabel: {
                show: false
              },
              pointer: {
                show: false
              },
              detail: {
                valueAnimation: true,
                fontSize: 14,
                formatter: function (value) {
                  return +value.toFixed(2) + '%RH';
                },
                color: color2,
                offsetCenter: [0, "0%"]
              },
              data: [
                {
                  value: data2
                }
              ]
            }
          ]
        };
        option && myChart.setOption(option);
        option2 && myChartow.setOption(option2);
      }
    },
    choices(index, id, dev_no) {
      if (this.num != index) {
        this.num = index;
        this.$emit("callBackree", index, id, dev_no)
      }
    },
    storageDetail(id) {
      this.$router.push({ name: 'parameter', params: { id: id } }).catch(err => { });
    }
  }
};
</script>
<style lang="scss" scoped>
.ullists {
  overflow: hidden;
  .list {
    padding: 0.15rem 0 0;
    width: 23.5%;
    text-align: center;
    background-color: #fff;
    margin-bottom: 0.2rem;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
    opacity: 1;
    border-radius: 6px;
    position: relative;
    float: left;
    margin-right: 2%;
    .iconqita2_huaban1fuben2 {
      position: absolute;
      top: 0.1rem;
      left: 0.1rem;
      color: #1a90fd;
      font-size: 0.3rem;
    }
    .police {
      position: absolute;
      top: 0;
      right: 0;
    }
    p {
      padding-bottom: 0.1rem;
    }
    .meter {
      display: flex;
      justify-content: space-between;
      padding-bottom: 0.1rem;
      border-bottom: 1px solid rgba(0, 0, 0, 0.04);
      .meter-list {
        flex: 1;
        position: relative;
        ::v-deep .juzhong {
          div {
            margin: 0 auto !important;
          }
        }
        .unit {
          position: absolute;
          width: 100%;
          bottom: 0.04rem;
          font-weight: 400;
          font-size: 0.14rem;
          color: #999999;
        }
      }
    }
    .statistical {
      display: flex;
      .statistical-box {
        flex: 1;
        height: 0.42rem;
        line-height: 0.42rem;
        font-size: 0.14rem;
        color: #999999;
      }
      .statistical-box:nth-child(1) {
        border-right: 1px solid rgba(0, 0, 0, 0.04);
        span {
          color: #1a90fd;
        }
      }
      .statistical-box:nth-child(2) {
        span {
          color: #ff656d;
        }
      }
    }
  }
  .list:nth-child(4n) {
    float: right;
    margin-right: 0px;
  }
}
</style>