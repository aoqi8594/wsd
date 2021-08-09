<template>
  <div class="home">
    <el-row>
      <el-col :span="18">
        <div class="carname">
          <span class="hu">温湿度计台账</span>
          <el-select size="mini" v-model="state" placeholder="设备状态" :clearable="true">
            <el-option v-for="(item, index) in stOptions" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
          <el-select size="mini" v-model="purpose" placeholder="用途" :clearable="true">
            <el-option v-for="(item, index) in useOptions" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
          <el-button type="primary" size="mini" @click="getDataList()">查询</el-button>
        </div>
        <div class="grid-content bg-purple">
          <el-scrollbar ref="myScrollbar" style="height: 84vh">
            <div class="list" v-for="(item, index) in devList" :key="index">
              <P>{{item.dev_name}}</P>
              <div class="statusvalue">
                <div class="statusvalue-box">在线:<span class="">{{item.net_st | isOnline}}</span></div>
                <div class="statusvalue-box">状态:<span class="">{{item.st | formatState}}</span></div>
              </div>
              <div class="statusvalue">
                <div class="statusvalue-box">机制:<span class="">{{item.acqu_freq1}}min/次</span></div>
                <div class="statusvalue-box">用途:<span class="">{{item.use_type |formatUse}}</span></div>
              </div>
              <div class="statusvalue">
                <div class="statusvalue-box">温度阀值:<span class="">{{item.temp_low}}~{{item.temp_up}}℃</span></div>
                <div class="statusvalue-box">湿度阀值:<span class="">{{item.hum_low}}~{{item.hum_up}}%RH</span></div>
              </div>
              <!-- <div class="statusvalue address">
                <div class="statusvalue-box"><i class="el-icon-location"></i>楼栋1一楼房间001楼栋1一楼房间001</div>
              </div> -->
              <div class="statistical">
                <div class="statistical-box foncolor" @click="updateYZ(item)">阀值调整</div>
                <div class="statistical-box foncolor" @click="proofread(item)">校对</div>
                <div class="statistical-box warningColor" v-if="!(item.st==='XZ')" @click="ChangeDevSt(item.id,item.st)">{{item.st==='QY'?'TY':'QY' | formatState}}</div>
              </div>
              <img alt="" class="police" v-if="item.alarm_yn==='Y'" src="../../../assets/img/police.png" />
            </div>
          </el-scrollbar>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content bg-purple-light">
          <div class="light-top">
            <div class="personnel">
              <p>设备监测状态监控统计</p>
              <!-- <span class="name">鹿野</span>
              <span class="position">负责人</span>
              <span class="phone">
                <i class="iconfont icon-shouji"></i>
                13688828555
              </span> -->
            </div>
            <div class="target">
              <div class="targetbox">
                <div class="targetclass">
                  <div class="targetlist">
                    <span class="num numbox1">{{count.total}}</span>
                    <span class="textfont">全部</span>
                  </div>
                  <div class="targetlist">
                    <span class="num numbox2">{{count.isUsed}}</span>
                    <span class="textfont">在用</span>
                  </div>
                  <div class="targetlist">
                    <span class="num numbox3">{{count.stopUsing}}</span>
                    <span class="textfont">停用</span>
                  </div>
                  <div class="targetlist">
                    <span class="num numbox4">{{count.noUsing}}</span>
                    <span class="textfont">闲置</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="light-up onlinebox">
            <p>设备在线率统计</p>
            <div id='online' :style="{ width: '48%', height: '100%' }"></div>
            <div class="onlinenum">
              <div class="onlinenumzx">
                <span class="zxbox1"></span>
                <span class="zxbox2">在线：</span>
                <span class="zxbox3">{{count.online/count.total*100 | numToFixed(2)}}%</span>
                <span class="zxbox4">{{count.online}}台</span>
              </div>
              <div class="onlinenumlx">
                <span class="zxbox1"></span>
                <span class="zxbox2">离线：</span>
                <span class="zxbox3">{{count.offline/count.total*100 | numToFixed(2)}}%</span>
                <span class="zxbox4">{{count.offline}}台</span>
              </div>
            </div>
          </div>
          <div class="light-up">
            <p>设备分类统计</p>
            <div id='statistical' :style="{ width: '100%', height: '100%' }"></div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-dialog title="阀值调整" top="30vh" width="500px" :visible.sync="dialogFormVisible">
      <el-form :model="thForm" label-width="88px">
        <el-form-item label="温度阈值：">
          <el-col :span="12">
            <el-form-item prop="temp_up">
              上限
              <el-input-number size="mini" v-model.trim="thForm.temp_up" :min="-274" :max="1000" label="温度上限"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="temp_low">
              下限
              <el-input-number size="mini" v-model.trim="thForm.temp_low" :min="-274" :max="1000" label="温度下限"></el-input-number>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="湿度阈值：">
          <el-col :span="12">
            <el-form-item prop="hum_up">
              上限
              <el-input-number size="mini" v-model.trim="thForm.hum_up" :min="-274" :max="1000" label="湿度上限"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="hum_low">
              下限
              <el-input-number size="mini" v-model.trim="thForm.hum_low" :min="-274" :max="1000" label="湿度下限"></el-input-number>
            </el-form-item>
          </el-col>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogFormVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
    <Proofread title="温湿度计校对" :show.sync="dialogopen" height="10.26rem" width="10.6rem" ref="Proofread" @close="dialogopen = false"></Proofread>
    <el-dialog class="core" title="启用" width="4.4rem" :visible.sync="enablebox" :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" :rules="rules">
        <div class="labelbox">请设置数据采集时间：</div>
        <el-form-item class="box" prop="time">
          <el-date-picker size="mini" v-model.trim="form.time" type="datetimerange" value-format="yyyy-MM-dd HH:mm:ss" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间"></el-date-picker>
        </el-form-item>
      </el-form>
      <div class="confirmbutton">
        <el-button size="mini" @click="enablebox=false">取消</el-button>
        <el-button size="mini" type="primary" @click="enableConfirm">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Brokenline from "@/components/Brokenline.vue";
import Proofread from "@/components/Proofread.vue";
export default {
  components: {
    Brokenline,
    Proofread
  },
  data() {
    return {
      dialogopen: false,
      dialogFormVisible: false,
      stOptions: [],
      useOptions: [],
      state: "",
      purpose: "",
      devList: [], 
      thForm: {
        id: "",
        temp_up: '',
        temp_low: '',
        hum_up: '',
        hum_low: '',
      },
      form: {
        time: ''
      },
      enablebox: false,
      rules: {
        time: [
          { required: true, message: '请选择采集时间', trigger: 'change' }
        ],
      },
      qyid: []
    };
  },
  computed: {
    count() {
      return {
        "total": this.devList.length,
        "isUsed": this.devList.filter(item => {
          return item.st === "QY";
        }).length,
        "stopUsing": this.devList.filter(item => {
          return item.st === "TY";
        }).length,
        "noUsing": this.devList.filter(item => {
          return item.st === "XZ";
        }).length,
        "online": this.devList.filter(item => {
          return item.net_st === "WLZT_ZX";
        }).length,
        "offline": this.devList.filter(item => {
          return item.net_st === "WLZT_LX";
        }).length
      }
    },
    useTypeLine() {
      let arr=this.useOptions.slice(1);
      return arr.map(item => {
        return item.val;
      })
    },
    useTypeVal() {
      let arr = this.useOptions;
      let dev = this.devList;
      let varr = [];
      for (let i = 0; i < arr.length; i++) {
        var colline = 0;
        for (let j = 0; j < dev.length; j++) {
          if (dev[j].use_type === arr[i].code) {
            colline += 1;
          }
        }
        varr.push(colline)
      }
      return varr.slice(1);
    }
  },
  created() {
    this.init();
  },
  mounted() {
    // console.log(this.$route.params.id)
    if (this.$route.params.id) {
      this.getDataDetails(this.$route.params.id);
    } else {
      this.getDataList();
    }
  },
  methods: {
    init() {
      this.$api.dictionary.getDictionaryList({ "parent_code": "SBSYZT" }).then(({ data }) => {
        let obj = {val: '全部',code: 'Q'}
        data.list.unshift(obj)
        this.stOptions = data.list;
      })
      this.$api.dictionary.getDictionaryList({ "parent_code": "SBYT" }).then(({ data }) => {
        let obj = {val: '全部',code: 'QB'}
        let list=data.list
        list.unshift(obj)
        this.useOptions = list;
      })
    },
    getDataDetails(id) {
      this.$api.sensor.getSensordetails({ id: id }).then(({ data }) => {
        if (data && data.equipmentInfo.length >= 0) {
          this.devList = data.equipmentInfo;
          this.state = "";
          this.purpose = "";
        }
      }).then(() => {
        this.statistical()
      })
    },
    getDataList() {
       let st='',use_type='';
       if(this.state=='Q'){
         st='' 
       }else{
         st=this.state
       }
       if(this.purpose=='QB'){
         use_type='' 
       }else{
         use_type=this.purpose
       }
      this.$api.sensor.getSensorList({ st: st, use_type: use_type }).then(({ data }) => {
        if (data && data.list.length >= 0) {
          this.devList = data.list;
        }
      }).then(() => {
        this.statistical()
      })
    },
    statistical() {
      let myChart = this.$echarts.init(
        document.getElementById("statistical")
      );
      let online = this.$echarts.init(
        document.getElementById("online")
      );
      var option, option2;
      option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          top: '10%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            textStyle: {
              color: '#CCCCCC',
            },
            interval: 0,
            rotate: 40
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "#CCCCCC",
              width: 0,
              type: "solid"
            }
          },
          axisTick: {
            show: false
          },
          splitLine: {
            show: false,
          },
          data: this.useTypeLine
        },
        yAxis: {
           name: '个数',
          nameLocation: 'end',
          type: 'value',
          axisLabel: {
            textStyle: {
              color: '#CCCCCC',
            },
          },
          max: Math.max.apply(Math, this.useTypeVal) * 2,
          axisLine: {//y轴线的颜色以及宽度
            show: false,
            lineStyle: {
              color: "#CCCCCC",
              width: 1,
              type: "solid"
            },
          },

          splitLine: {
            show: true,
            lineStyle: {
              type: 'dashed'
            }
          },
        },
        series: [
          {
            type: 'bar',
            barWidth: 10,//柱图宽度
            itemStyle: {
              //柱形图圆角，鼠标移上去效果，如果只是一个数字则说明四个参数全部设置为那么多
              normal: {
                //柱形图圆角，初始化效果
                barBorderRadius: [5, 5, 0, 0],
                color: '#1A90FD'
              }
            },
            data: this.useTypeVal
          }
        ]
      };
      option2 = {
        title: {
          text: '总数',
          x: 'center',
          y: 'center',
          top: '50%',
          textStyle: {
            fontSize: 14,
            color: '#999999'
          }
        },
        graphic: {
          type: 'text',
          left: 'center',
          top: '40%',
          style: {
            text: this.count.total + "",
            textAlign: 'center',
            fill: '#2AC3B4',
            fontSize: 14,
            width: 30,
            height: 60,
          }
        },
        series: [
          {
            name: '访问来源',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            startAngle: 270, //起始角度
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              show: false
            },
            labelLine: {
              show: true
            },
            data: [
              { value: this.count.online, itemStyle: { color: '#2AC3B4' } },
              { value: this.count.offline, itemStyle: { color: '#999999' } },
            ]
          }
        ]
      };
      option && myChart.setOption(option);
      option2 && online.setOption(option2);
    },
    updateYZ(dev) {
      if (!dev.id) {
        this.dialogFormVisible = false;
      }
      this.thForm.id = dev.id;
      this.thForm.temp_up = dev.temp_up;
      this.thForm.temp_low = dev.temp_low;
      this.thForm.hum_up = dev.hum_up;
      this.thForm.hum_low = dev.hum_low;
      this.dialogFormVisible = true;
    },
    // 阈值调整
    submitForm() {
      if (!this.thForm.id) {
        return;
      }
      this.$api.sensor.updateDevThreshold(this.thForm).then((res) => {
        if (res.code === 0) {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1000,
            onClose: () => {
              this.dialogFormVisible = false
              this.getDataList();
            }
          })
        }
      })
    },
    // 启用/停用
    ChangeDevSt(id, st) {
      let ids = id ? [id] : [];
      let changeSt = st === 'QY' ? 'TY' : 'QY';
      if (st == 'QY') {
        this.$confirm('停用后设备不再采集任何信息！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.changetype(ids, 'TY')
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消该操作！'
          });
        });
      } else {
        this.enablebox = true;
        this.qyid = ids
      }
    },
    enableConfirm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.changetype(this.qyid, 'QY')
        } else {
          return false;
        }
      });
    },
    changetype(id, st) {
      let ojb = {
        ids: id,
        st: st
      }
      if (st == 'QY') {
        ojb.start_time = this.form.time[0];
        ojb.end_time = this.form.time[1];
      }
      this.$api.sensor.changestate(ojb).then((res) => {
        if (res.code === 0) {
          this.$message({
            message: "操作成功",
            type: "success",
            duration: 1000,
            onClose: () => {
              this.getDataList();
              this.enablebox = false;
            }
          });
        }
      })
    },
    proofread(data) {
      this.dialogopen = true;
      let arr = [];
      arr.push(data)
      this.$refs.Proofread.init(arr, 'tai')
    }
  }
};
</script>
<style lang="scss" scoped>
.home {
  height: calc(100vh - 50px);
  ::v-deep .el-dialog__body {
    padding: 0 0.2rem;
  }
  .el-row {
    height: 100%;
    margin-bottom: 20px;
    .el-col {
      height: 100%;
      .carname {
        height: 0.5rem;
        padding-top: 0.05rem;
        line-height: 0.4rem;
        padding-left: 3%;
        background-color: #fff;
        .hu {
          margin-right: 3%;
        }
        .el-select {
          width: 12%;
          margin-right: 0.1rem;
        }
      }
    }
  }
  .bg-purple {
    height: 100%;
    background: #f5f7fa;
    padding: 0.3rem;
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
      .police {
        position: absolute;
        top: 0;
        right: 0;
      }
      p {
        padding-bottom: 0.1rem;
      }
      .statusvalue {
        display: flex;
        padding: 0 4%;
        .statusvalue-box {
          flex: 1;
          font-size: 0.14rem;
          color: #999999;
          text-align: left;
          height: 0.3rem;
          line-height: 0.22rem;
          span {
            color: #1a90fd;
          }
        }
      }
      .address {
        margin: 0.06rem 0;
        .statusvalue-box {
          height: auto;
        }
      }
      .statistical {
        display: flex;
        border-top: 1px solid rgba(0, 0, 0, 0.04);
        .statistical-box {
          flex: 1;
          height: 0.42rem;
          line-height: 0.42rem;
          font-size: 0.14rem;
          color: #999999;
          font-weight: 600;
          cursor: pointer;
        }
        .foncolor {
          border-right: 1px solid rgba(0, 0, 0, 0.04);
          color: #1a90fd;
        }
        .warningColor {
          color: #ff656d;
        }
      }
    }
    .list:nth-child(4n) {
      float: right;
      margin-right: 0px;
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
        margin-bottom: 0.16rem;
        color: #999999;
        p {
          font-size: 0.14rem;
          font-weight: bold;
          color: #1a1a1a;
        }
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
              flex: 1;
              text-align: center;
              .num {
                font-size: 0.19rem;
                font-weight: 600;
                display: block;
                height: 0.3rem;
                line-height: 0.3rem;
                margin-top: 0.1rem;
              }
              .numbox1 {
                color: #5fbfbc;
              }
              .numbox2 {
                color: #ff656d;
              }
              .numbox3 {
                color: #f2a816;
              }
              .numbox4 {
                color: #999999;
              }
              .textfont {
                color: #666666;
              }
            }
          }
        }
      }
    }
    .light-up {
      margin-top: 0.14rem;
      border: 1px solid #f0f0f0;
      opacity: 1;
      border-radius: 0.09rem;
      padding: 0.2rem 0;
      height: 54vh;
      p {
        font-size: 0.14rem;
        font-weight: bold;
        color: #1a1a1a;
        padding: 0 0.2rem 0rem;
      }
    }
    .light-up:nth-child(2) {
      height: 20vh;
    }
    .onlinebox {
      position: relative;
      .onlinenum {
        position: absolute;
        top: 42%;
        left: 48%;
        font-size: 0.14rem;
        .onlinenumzx {
          color: #30a1a7;
          .zxbox1 {
            display: inline-block;
            width: 0.1rem;
            height: 0.1rem;
            border-radius: 0.05rem;
            margin-right: 0.1rem;
            background-color: #30a1a7;
          }
          .zxbox3 {
            margin-right: 0.1rem;
          }
        }
        .onlinenumlx {
          margin-top: 20%;
          color: #999999;
          .zxbox1 {
            display: inline-block;
            width: 0.1rem;
            height: 0.1rem;
            border-radius: 0.05rem;
            margin-right: 0.1rem;
            background-color: #999999;
          }
          .zxbox3 {
            margin-right: 0.1rem;
          }
        }
      }
    }
  }
}
.core {
  ::v-deep .el-dialog {
    .el-dialog__body {
      padding: 0.2rem !important;
    }
    .confirmbutton {
      text-align: right;
    }
    .labelbox {
      margin-bottom: 0.1rem;
    }
  }
}
</style>
