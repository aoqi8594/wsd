<template>
  <div class="chamber">
    <div class="chamber-box1">
      <div class="box1-neme" v-if="pash=='room'">{{floordata.build_name}}{{floordata.floor_cd}}楼{{floordata.room_cd}}</div>
      <div class="box1-neme" v-if="pash=='car'">{{floordata.car_no}}</div>
      <div class="box1-button">
        <el-select size="mini" @change="trunk" v-model="value" placeholder="请选择设备">
          <el-option :label="item.icebox_name" v-for="(item, index) in refrigerator" :key="index" :value="item.id"></el-option>
        </el-select>
      </div>
    </div>
    <div class="chamber-box2" v-if="pash=='room'">
      <div class="box2-1"><span class="num1">{{floordata.online_dev}}</span>在线</div>
      <div class="box2-2"><span class="num2">{{floordata.total_dev-floordata.online_dev}}</span>离线</div>
      <div class="box2-3"><span class="num3">{{floordata.alarmsNum}}</span>报警</div>
    </div>
    <div class="chamber-box2" v-if="pash=='car'">
      <div class="box2-1"><span class="num1">{{floordata.online}}</span>在线</div>
      <div class="box2-2"><span class="num2">{{floordata.offline}}</span>离线</div>
      <div class="box2-3"><span class="num3">{{floordata.alarmNums}}</span>报警</div>
    </div>
    <div class="chamber-box3">
      <div class="meter-list">
        <div>
          <div id="roomChart1" :style="{ width: '100%', height: '100%',margin: '0 auto' }" class="juzhong"></div>
        </div>
        <div class="unit">温度(℃)</div>
      </div>
      <div class="meter-list">
        <div>
          <div id="roomChart2" :style="{ width: '100%', height: '100%',margin: '0 auto' }" class="juzhong"></div>
        </div>
        <div class="unit">湿度(%RH)</div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    // roomlist: {
    //   type: Array,
    //   default: [],
    // },
  },
  data() {
    return {
      floordata: '',
      refrigerator: [
      ],
      value: '',
      pash: ''//表示是车还是房间
    };
  },
  created() { },
  mounted() { },
  methods: {
    init(roomInfo, array, pash) {
      this.pash = pash;
      this.refrigerator = [
        {
          icebox_name: '全部冰箱',
          id: '',
        },
      ];
      let that = this;

      if (pash == 'car') {
        for (let i = 0; i < array.iceboxList.length; i++) {
          this.refrigerator.push(array.iceboxList[i])
        }
        this.floordata = array.relationDevInfo[0];
        this.floordata.online = array.count.online;
        this.floordata.offline = array.count.offline;
        this.floordata.alarmNums = array.count.alarmNums;
        this.floordata.car_no = roomInfo.car_no;
      } else {
        for (let i = 0; i < array.length; i++) {
          this.refrigerator.push(array[i])
        }
        this.floordata = roomInfo;
      }
      this.$nextTick(() => {
        that.drawLine();
      });
    },
    drawLine() {
      let that = this;
      // 基于准备好的dom，初始化echarts实例
      //  Object.defineProperty( document.getElementById("roomChart1"),'clientWidth',{get:function(){return 100;}})
      //   Object.defineProperty( document.getElementById("roomChart1"),'clientHeight',{get:function(){return 100;}})
      //   Object.defineProperty( document.getElementById("roomChart2"),'clientWidth',{get:function(){return 100;}})
      //   Object.defineProperty( document.getElementById("roomChart2"),'clientHeight',{get:function(){return 100;}})
      let roomChart1 = this.$echarts.init(
        document.getElementById("roomChart1")
      );
      let roomChart2 = this.$echarts.init(
        document.getElementById("roomChart2")
      );
      let color1 = '#F0F0F0';
      let color2 = '#F0F0F0';
      if (that.floordata.temp_up == null) {
        that.floordata.temperature = 0;
      } else {
        if (that.floordata.temperature == '0.0' || that.floordata.temperature == null) {
          that.floordata.temperature = 0;
        } else {
          let ture = parseFloat(that.floordata.temperature);
          let up = parseFloat(that.floordata.temp_up);
          let low = parseFloat(that.floordata.temp_low);
          if (ture < up && ture > low) {
            color1 = '#2AC3B4'
          } else {
            color1 = '#FF656D'
          }
        }
      }
      if (that.floordata.hum_up == null) {
        that.floordata.humidity = 0;
      } else {
        if (that.floordata.humidity == '0.0' || that.floordata.humidity == null) {
          that.floordata.humidity = 0;
        } else {
          that.floordata.humidity = parseFloat(that.floordata.humidity).toFixed(2);
          let humidity = parseFloat(that.floordata.humidity);
          let hum_up = parseFloat(that.floordata.hum_up);
          let hum_low = parseFloat(that.floordata.hum_low);
          if (hum_low < humidity && humidity < hum_up) {
            color2 = '#2AC3B4'
          } else {
            color2 = '#FF656D'
          }
        }
      }
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
              },
            },
            axisLine: {
              roundCap: true,
              lineStyle: {
                width: 7,
                color: [[1, "#F0F0F0"]],
              },
            },
            axisTick: {
              show: false,
            },
            splitLine: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
            pointer: {
              show: false,
            },
            detail: {
              valueAnimation: true,
              fontSize: 14,
              formatter: function (value) {
                return +value.toFixed(2) + '℃';
              },
              color: color1,
              offsetCenter: [0, "0%"],
            },
            data: [
              {
                value: that.floordata.temperature,
              },
            ],
          },
        ],
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
              },
            },
            axisLine: {
              roundCap: true,
              lineStyle: {
                width: 7,
                color: [[1, "#F0F0F0"]],
              },
            },
            axisTick: {
              show: false,
            },
            splitLine: {
              show: false,
            },
            axisLabel: {
              show: false,
            },
            pointer: {
              show: false,
            },
            detail: {
              valueAnimation: true,
              fontSize: 14,
              formatter: function (value) {
                return +value.toFixed(2) + '%RH';
              },
              color: color2,
              offsetCenter: [0, "0%"],
            },
            data: [
              {
                value: that.floordata.humidity,
              },
            ],
          },
        ],
      };
      option && roomChart1.setOption(option);
      option2 && roomChart2.setOption(option2);
    },
    trunk(e) {
      this.$emit("callBackroo", e)
    }
  },
};
</script>
<style lang="scss" scoped>
.chamber {
  background-color: #fff;
  border-radius: 0.06rem;
  margin-bottom: 0.2rem;
  display: flex;
  justify-content: space-between;
  .chamber-box1 {
    width: 30%;
    padding: 0.2rem;
    .box1-neme {
      height: 0.4rem;
      line-height: 0.3rem;
      margin-bottom: 0.2rem;
    }
  }
  .chamber-box2 {
    width: 30%;
    display: flex;
    justify-content: space-between;
    div {
      width: 24%;
      height: 0.8rem;
      margin-top: 0.25rem;
      border: 1px solid #f0f0f0;
      border-radius: 0.06rem;
      text-align: center;
      font-size: 0.16rem;
      color: #999999;
      span {
        font-size: 0.24rem;
        display: block;
        margin: 0.1rem 0;
      }
      .num1 {
        color: #1a90fd;
      }
      .num2 {
        color: #999999;
      }
      .num3 {
        color: #ff656d;
      }
    }
  }
  .chamber-box3 {
    width: 30%;
    display: flex;
    padding-top: 0.1rem;
    justify-content: space-between;
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
        color: #999999;
        bottom: 0.2rem;
        text-align: center;
      }
    }
  }
}
</style>