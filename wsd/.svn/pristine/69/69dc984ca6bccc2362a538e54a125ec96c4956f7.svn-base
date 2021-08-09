<template>
  <div class="ullists swiper-container">
    <div class="swiper-wrapper" v-show="falg==true">
      <div class="list swiper-slide" @click="gotopage(item)" v-for="(item, index) in roomlist" :key="index">
        <P>{{item.build_name}}{{item.floor_cd}}楼{{item.room_cd}}</P>
        <div class="meter">
          <div class="meter-list">
            <div>
              <div class="listechart" :id="'myChart1' + index" :style="{ width: '100%', height: '100%' }"></div>
            </div>
            <div class="unit"><i class="iconfont iconqita_huaban1fuben4"></i>温度(℃)</div>
          </div>
          <div class="meter-list">
            <div>
              <div class="listechart" :id="'myChart2' + index" :style="{ width: '100%', height: '100%' }"></div>
            </div>
            <div class="unit"><i class="iconfont iconqita_huaban1fuben5" style="font-size:.13rem"></i>湿度(%RH)</div>
          </div>
        </div>
        <div class="statistical">
          <div class="statistical-box">在线：<span>{{item.online_dev}}</span>/{{item.total_dev}}</div>
          <div class="statistical-box">报警：<span :class="item.alarmsNum==0?'Num0':''">{{item.alarmsNum}}</span></div>
        </div>
        <img alt="" v-if="item.alarm_yn=='Y'" class="police" src="../assets/img/police.png" />
      </div>
    </div>
    <div class="swiper-button-prev" v-if="left" @click="slidePrev">
      <i class="el-icon-arrow-left"></i>
    </div>
    <div class="swiper-button-next" v-if="right"  @click="slideNext">
        <i class="el-icon-arrow-right"></i>
    </div>
  </div>
</template>
<script> 
var swiper = '';
export default {
  props: {
    types: {
      type: String,
      default: '',
    }
  },
  data() {
    return {
      floorList: [],
      roomlist: [],
      falg: false,
      num:0,
      eg:0,
      left:false,
      right:false
    }
  },
  created() { },
  mounted() { },
  methods: {
    init(roomlist) {
      let that = this;
      this.roomlist = roomlist;
      if(roomlist.length>8){
        this.right =true;
        this.eg= Math.ceil((roomlist.length-8)/2);
      }
      this.$nextTick(() => {
        that.swiperlist();
        that.drawLine();
        that.falg = true;
      });
    },
    swiperlist() {
      let that = this;
      swiper = new Swiper('.swiper-container', {
        slidesPerView: 4,
        slidesPerColumn: 2,
        spaceBetween: 30,
        pagination: {
          el: '.swiper-pagination',
          clickable: true,
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev',
        },
        //滑到最后一个隐藏前进按钮
        on: {
          slideChangeTransitionEnd: function () {
            console.log(11111)
            if (this.isEnd) {
              this.navigation.$nextEl.css('display', 'none');
            } else {
              this.navigation.$nextEl.css('display', 'block');
            }
          },
        },
      });
    },
    slideNext() {
      if(this.eg>this.num){
        swiper.slideNext();
        this.num=this.num+1;
        this.left =true;
        if(this.eg==this.num){
          this.right =false;
        }
      }else{
         this.right =false;
      }
    },
    slidePrev() {
      if(this.num>0){
        this.num=this.num-1;
        swiper.slidePrev();
        this.right =true;
        if(this.num==0){
          this.left =false;
        }
      }else{
         this.left =false;
      }
    },
    drawLine() {
      let that = this;
      for (let i = 0; i < that.roomlist.length; i++) {
        let color1 = '#F0F0F0';
        let color2 = '#F0F0F0';
        if (that.roomlist[i].temp_up == null) {
          that.roomlist[i].temperature = 0;
        } else {
          if (that.roomlist[i].temperature == '0.0' || that.roomlist[i].temperature == null) {
            that.roomlist[i].temperature = 0;
          } else {
            let ture = parseFloat(that.roomlist[i].temperature);
            let up = parseFloat(that.roomlist[i].temp_up);
            let low = parseFloat(that.roomlist[i].temp_low);
            if (ture < up && ture > low) {
              color1 = '#2AC3B4'
            } else {
              color1 = '#FF656D'
            }
          }
        }
        if (that.roomlist[i].hum_up == null) {
          that.roomlist[i].humidity = 0;
        } else {
          if (that.roomlist[i].humidity == '0.0' || that.roomlist[i].humidity == null) {
            that.roomlist[i].humidity = 0;
          } else {
            that.roomlist[i].humidity = parseFloat(that.roomlist[i].humidity).toFixed(2);
            let humidity = parseFloat(that.roomlist[i].humidity);
            let hum_up = parseFloat(that.roomlist[i].hum_up);
            let hum_low = parseFloat(that.roomlist[i].hum_low);
            if (hum_low < humidity && humidity < hum_up) {
              color2 = '#2AC3B4'
            } else {
              color2 = '#FF656D'
            }
          }
        }
        if (!that.roomlist[i].humidity || that.roomlist[i].humidity == '0.0') {
          that.roomlist[i].humidity = 0
        }
        // 基于准备好的dom，初始化echarts实例
        let myChart1 = 'myChart1' + i;
        let myChart2 = 'myChart2' + i;
        Object.defineProperty(document.getElementById(myChart1), 'clientWidth', { get: function () { return 100; } })
        Object.defineProperty(document.getElementById(myChart1), 'clientHeight', { get: function () { return 100; } })
        Object.defineProperty(document.getElementById(myChart2), 'clientWidth', { get: function () { return 100; } })
        Object.defineProperty(document.getElementById(myChart2), 'clientHeight', { get: function () { return 100; } })
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
                width: 8,
                roundCap: true,
                itemStyle: {
                  color: color1,
                },
              },
              axisLine: {
                roundCap: true,
                lineStyle: {
                  width: 8,
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
                valueAnimation: true,
                offsetCenter: [0, "0%"],
              },
              data: [
                {
                  value: that.roomlist[i].temperature,
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
              min: -200,				// 最小的数据值,默认 0 。映射到 minAngle。
              max: 151,
              type: "gauge",
              progress: {
                show: true,
                width: 8,
                roundCap: true,
                itemStyle: {
                  color: color2,
                },
              },
              axisLine: {
                roundCap: true,
                lineStyle: {
                  width: 8,
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
                  value: that.roomlist[i].humidity,
                },
              ],
            },
          ],
        };
        option && myChart.setOption(option);
        option2 && myChartow.setOption(option2);
      }
    },
    gotopage(item) {
      this.$store.commit('user/setstatus', {
        status: 'devicemanagement'
      });
      localStorage.setItem("floordata", JSON.stringify(item));
      localStorage.setItem("pash", 'home');
      this.$router.push({ name: 'devicemanagement' })
    }
  }
};
</script>
<style lang="scss" scoped>
.ullists {
  width: 100%;
  height: 44vh;
  padding:0 .3rem ;
  .swiper-button-disabled {
    display: none;
  }
  .swiper-slide {
    height: 1.95rem;
    margin-top: 0 !important;
  }
  .list {
    padding: 0.15rem 0 0;
    text-align: center;
    background-color: #fff;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.04);
    opacity: 1;
    border-radius: 6px;
    position: relative;
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
        .unit {
          position: absolute;
          width: 100%;
          bottom: 0.04rem;
          font-weight: 400;
          font-size: 0.14rem;
          color: #999999;
        }
        ::v-deep .listechart {
          div {
            margin: 0 auto !important;
          }
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
      .statistical-box:nth-child(2) {
        .Num0 {
          color: #999999;
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