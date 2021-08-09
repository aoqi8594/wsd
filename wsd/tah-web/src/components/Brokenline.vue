<template>
  <div class="lists">
    <div class="list">
      <div id="Brokenline" :style="{ width: '100%', height: '5.5rem' }"></div>
      <span class="note" v-if="falg">当天24小时</span>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    height: {
      type: String,
      default: 'height: calc(100vh - 3.4rem);',
    },
  },
  data() {
    return {
      list: [],
      falg:false
    };
  },
  created() { },
  mounted() {
    let that = this;
  },
  methods: {
    init(id) {
      this.$api.chartapi.getDevTimePointChart({ dev_id: id }).then(data => {
        if (data.code == 0) {
          if (data.data.list.length > 0) {
            let array = data.data.list;
            let temperature = ["", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""];
            let humidity = ["", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""];
            this.list = array;
            for (let k = 0; k < array.length; k++) {
              if (array[k].create_time.length == 19) {
                let time = array[k].create_time.slice(11, 13);
                if (time.slice(0, 1) == 0) {
                  array[k].create_time = time.slice(1, 2)
                } else {
                  array[k].create_time = time
                }
              }
            }
            for (let j = 0; j < 23; j++) {
              for (let i = 0; i < array.length; i++) {
                if (j == array[i].create_time) {
                  temperature[j] = array[i].temperature;
                  humidity[j] = array[i].humidity;
                }
              }
            }
            var arr3 = temperature.concat(humidity);
            Array.prototype.min = function () {
              var min = this[0];
              var len = this.length;
              for (var i = 1; i < len; i++) {
                if (this[i]) {
                  if (this[i] < min) {
                    min = this[i];
                  }
                }
              }
              return min;
            }
            Array.prototype.max = function () {
              var max = this[0];
              var len = this.length;
              for (var i = 1; i < len; i++) {
                if (this[i] > max) {
                  max = this[i];
                }
              }
              return max;
            }
            //最小值
            this.drawLine(temperature, humidity, Math.ceil(arr3.max()), Math.ceil(arr3.min()));
          }
        }
      });
    },
    drawLine(temperature, humidity, arr3max, arr3min) {
      let that = this;
      that.falg=true;
      let Brokenline = this.$echarts.init(
        document.getElementById("Brokenline")
      );
      // 绘制图表
      var option3;
      option3 = {
        legend: {
          data: ['温度', '湿度'],
          left: '5%',
          top:'1%'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: "category",
          // axisLabel: {
          //   interval: 0,
          //   rotate: 30
          // },
          axisLine:{
               show:false
          },
           axisTick:{
               show:false
          },
          data: ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"],
        },
        //滚动条
        // dataZoom: [
        //   {
        //     show: true,
        //     realtime: true,
        //     start: 1,
        //     end: 80,
        //     handleSize: "110%"
        //   }, {
        //     type: 'inside',
        //     show: false,
        //     realtime: false,

        //     disabled: true,
        //     zoomOnMouseWheel: false
        //   }
        // ],
        yAxis: [
          {
            name: '温度(℃)',
            type: 'value',
            max: arr3max<0?0:arr3max,
            min: arr3min>0?0:arr3min
          },
          {
            name: '湿度(%RH)',
            nameLocation: 'end',
            type: 'value',
            max: arr3max<0?0:arr3max,
            min: arr3min>0?0:arr3min,
          }
        ],
        series: [
          {
            data: temperature,
            type: "line",
            name: '温度',
            itemStyle: {
              normal: {
                color: "#1A90FD",
                lineStyle: {
                  color: "#1A90FD",
                },
              },
            },
          },
          {
            data: humidity,
            type: "line",
            name: '湿度',
            yAxisIndex: 1,
            lineStyle: {
              width: 1
            },
            itemStyle: {
              normal: {
                color: "#2AC3B4",
                lineStyle: {
                  color: "#2AC3B4",
                },
              },
            },
          },
        ],
        grid: {
          left: '1%',
          top: '14%',
          right: '1%',
          bottom: '3%',
          containLabel: true
        },
      };
      option3 && Brokenline.setOption(option3);
    },
  },
};
</script>
<style lang="scss" scoped>
.lists {
  margin: 0rem 0 .2rem;
  padding: 0 0.2rem 0.2rem;
  .list {
    position: relative;
    .note {
      position: absolute;
      bottom: -0.1rem;
      display: block;
      text-align: center;
      width: 100%;
      font-size: 0.12rem;
      color: #666;
    }
    #Brokenline{
      margin-top: 0.1rem;
    }
  }
}
</style>