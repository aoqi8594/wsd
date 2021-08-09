<template>
  <div class="car">
    <el-row>
      <el-col :span="18">
        <div class="carname">
          <span class="">报警管理</span>
          <el-select class="alarm" size="mini" clearable v-model="formData.handleState">
            <el-option label="全部" value="QB"></el-option>
            <el-option label="已处理报警" value="YCL"></el-option>
            <el-option label="待处理报警" value="DCL"></el-option>
            <el-option label="处理中报警" value="CLZ"></el-option>
          </el-select>
           <el-input placeholder="请输入设备编号" size="mini" clearable v-model="formData.dev_no"></el-input>
          <el-date-picker size="mini" v-model="formData.timeFrame" type="daterange" align="right" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions" value-format="yyyy-MM-dd">
          </el-date-picker>
          <el-button type="primary" size="mini" @click="getDataList()">查询</el-button>
          <el-button type="primary" size="mini" class="export fr hu" @click="doHandle">立即处理</el-button>
        </div>
        <div class="historybox">
          <el-table :data="dataList" ref="alarmTable" border v-loading="dataListLoading" :row-class-name="tableRowClassName" @selection-change="selectionChangeHandle" highlight-current-row @current-change="handleCurrentChange" height="76vh" style="width: 100%;">
            <el-table-column type="selection" :selectable="checkSelectable" header-align="center" align="center" width="50">
            </el-table-column>
            <el-table-column prop="belong_type" header-align="center" align="center" label="类型" width="80">
              <template slot-scope="scope">
                <span>{{scope.row.belong_type === 'LLC'?'冷链车':'实验室'}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="location" header-align="center" align="center" label="位置" width="155" show-overflow-tooltip></el-table-column>
            <el-table-column prop="dev_no" header-align="center" align="center" label="编号" width="155"></el-table-column>
            <el-table-column prop="dev_name" header-align="center" align="center" label="名称" width="155"></el-table-column>
            <el-table-column prop="alarm_content" header-align="center" label="报警类型" width="200" show-overflow-tooltip></el-table-column>
            <el-table-column prop="create_time" header-align="center" align="center" label="触发时间" show-overflow-tooltip width="155"></el-table-column>
            <el-table-column prop="duty_name" header-align="center" align="center" label="负责人" width="155"></el-table-column>
            <el-table-column prop="duty_cont_phon" header-align="center" align="center" label="联系电话" width="120"></el-table-column>
            <el-table-column prop="alarm_remark" header-align="center" align="center" label="备注" width="120"></el-table-column>
            <el-table-column prop="st" header-align="center" align="center" fixed="right" label="处理状态" width="120">
              <template slot-scope="scope">
                <el-tag :type="scope.row.st != 'YCL' ? 'warning' : 'success'" disable-transitions>{{scope.row.st==='DCL'?'待处理':scope.row.st==='YCL'?'已处理':'处理中'}}</el-tag>
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="totalPage > 0" :total="totalPage" :page.sync="pageIndex" :limit.sync="pageLimt" @pagination="getDataList" />
        </div>
      </el-col>
      <el-col :span="6" class="colright">
        <div class="light-top">
          <div class="instrument">{{currentRow.dev_name}} <span class="fr online">设备状态：{{currentRow.net_st | isOnline}}</span></div>
          <div class="personnel">
            <span class="position">编号:{{currentRow.dev_no}}</span>
          </div>
          <div class="thechart">
            <div class="meter">
              <div class="meter-list">
                <div>
                  <div id="myChart1" :style="{ width: '100%', height: '100%' }"></div>
                </div>
                <div class="unit">温度(℃)</div>
                <div class="standard">标准温度：{{currentRow.currDevInfo.temp_low}}~{{currentRow.currDevInfo.temp_up}}℃</div>
              </div>
              <div class="meter-list">
                <div>
                  <div id="myChart2" :style="{ width: '100%', height: '100%' }"></div>
                </div>
                <div class="unit">湿度(%RH)</div>
                <div class="standard">标准湿度：{{currentRow.currDevInfo.hum_low}}~{{currentRow.currDevInfo.hum_up}}%RH</div>
              </div>
            </div>
          </div>
          <div class="handle">
            <el-button type="primary" size="mini" @click="updateYZ(currentRow.currDevInfo)">调节监测范围</el-button>
            <el-button type="primary" size="mini" @click="changestate(currentRow)" v-if="!(currentRow.devState==='XZ')">{{currentRow.devState==='QY'?'TY':'QY' | formatState}}</el-button>
          </div>
        </div>
        <div class="light-up piebin">
          <p>采集可用性</p>
          <div id="pie" :style="{ width: '48%', height: '80%' }"></div>
          <div class="onlinenum">
            <div class="onlinenumzx">
              <span class="zxbox1"></span>
              <span class="zxbox2">正常：</span>
              <span class="zxbox3">{{count.correctNum/count.allNum*100 | numToFixed(2)}}%</span>
              <span class="zxbox4">{{count.correctNum}}次</span>
            </div>
            <div class="onlinenumlx">
              <span class="zxbox1"></span>
              <span class="zxbox2">报警：</span>
              <span class="zxbox3">{{count.alarmNum/count.allNum*100 | numToFixed(2)}}%</span>
              <span class="zxbox4">{{count.alarmNum}}次</span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 阈值调整 -->
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
        <el-button size="mini" type="primary" @click="submitYZForm">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog class="promptlydialog" title="立即处理" width="4rem" top="20vh" :visible.sync="dialogTableVisible2" :close-on-click-modal="false" append-to-body>
      <div class="promptly">
        <el-button size="mini" @click="Haveprocessed">已处理</el-button>
        <el-button size="mini" @click="Haveprocessed2">调节采集机制</el-button>
      </div>
      <el-dialog class="promptlydialogchild" title="处理信息" width="4rem" top="20vh" :visible.sync="dialogTableVisible3" :close-on-click-modal="false" append-to-body>
        <el-input type="textarea" :rows="2" placeholder="请输入处理结果" v-model="textarea">
        </el-input>
        <el-button size="mini" type="primary" class="processedbutton" @click="processedguan1">已处理</el-button>
      </el-dialog>
      <el-dialog class="promptlydialogchild" title="调节采集机制" width="4.2rem" top="20vh" :visible.sync="dialogTableVisible4" :close-on-click-modal="false" append-to-body>
        <el-radio-group v-model="radio">
          <el-radio :label="item" v-for="(item, index) in list" :key="index">应急采集机制{{index+1}}:{{item}}min/次</el-radio>
        </el-radio-group>
        <el-button size="mini" type="primary" class="processedbutton" @click="processedguan2">确定</el-button>
      </el-dialog>
    </el-dialog>
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
import Pagination from "@/components/Pagination";
import { distinct } from "@/utils/index";
export default {
  components: { Pagination },
  data() {
    return {
      dataList: [],
      dataListLoading: false,
      dataListSelections: [],
      pageIndex: 1,
      pageLimt: 20,
      totalPage: 0,
      // doActionVisible: false,
      dialogFormVisible: false,
      dialogTableVisible2: false,
      dialogTableVisible3: false,
      dialogTableVisible4: false,
      textarea: '',
      radio: '',
      list: [],
      doresult: "",// 处理结果
      formData: {
        handleState: "",
        timeFrame: [],
        dev_no:''
      },
      thForm: {
        id: "",
        temp_up: '',
        temp_low: '',
        hum_up: '',
        hum_low: '',
      },
      currentRow: { devState: "", currDevInfo: {} },
      input: "",
      pickerOptions: {
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime());
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近三天",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 3);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            },
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            },
          },
        ],
      },
      value2: "",
      multipleSelection: "",
      currentPage4: 4,
      count: {
        alarmNum: 0,
        correctNum: 0,
        allNum: 0
      },
      enablebox: false,
      form: {
        time: ''
      },
      rules: {
        time: [
          { required: true, message: '请选择采集时间', trigger: 'change' }
        ],
      },
      datast: ''
    };
  },
  created() { 
    if(this.$route.params.dev_no){
      this.formData.dev_no=this.$route.params.dev_no
    }
    this.getDataList(); 
  },
  mounted() { },
  methods: {
    //已处理
    processedguan1() {
      this.dialogTableVisible3 = false;
      this.dialogTableVisible2 = false;
      this.alarmprocessing('XXLC', this.textarea)
    },
    //调节采集机制
    processedguan2() {
      this.dialogTableVisible2 = false;
      this.dialogTableVisible4 = false;
      this.alarmprocessing('TZPL', this.radio)
    },
    //改变设备状态
    changestate(st) {
      this.datast = st;
      if (st.devState == 'QY') {
        this.$confirm('停用后设备不再采集任何信息！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.changetype();
        }).catch(() => {
        });
      } else {
        this.enablebox = true;
      }
    },
    enableConfirm() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.changetype()
        } else {
          return false;
        }
      });
    },
    changetype() {
      let data=this.datast;
      let st = '';
      if (data.devState == 'QY') {
        st = 'TY'
      } else if (data.devState == 'TY') {
        st = 'QY'
      }
      let ojb = {
        ids: [data.dev_id],
        st: st
      }
      if (st == 'QY') {
        ojb.start_time = this.form.time[0];
        ojb.end_time = this.form.time[1];
      }
      this.$api.sensor
        .changestate(ojb)
        .then((data) => {
          if (data.code == 0) {
            this.$message({
              message: data.msg,
              type: "success",
            });
            this.enablebox = false;
            this.getDataList();
            
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    // 报警处理
    alarmprocessing(type, content) {
      let array = this.dataListSelections;
      let ids = [];
      for (let i = 0; i < array.length; i++) {
        ids.push(array[i].dev_id)
      }
      this.$api.alarm.alarmsHandle({
        "dev_ids": distinct(ids),
        "type": type,//XXLC 线下处理 TZPL调整频率 YC延迟 HL忽略
        "content": content
      }).then(data => {
        if (data.code == 0) {
          this.$message({
            type: 'success',
            message: '操作成功'
          });
          this.getDataList();
          this.dataListSelections = []
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //弹出理解处理
    Haveprocessed() {
      this.dialogTableVisible3 = true;
    },
    //选择采集机制
    Haveprocessed2() {
      this.list = [];
      let array = this.dataListSelections;
      let ids = [];
      for (let i = 0; i < array.length; i++) {
        ids.push(array[i].dev_id)
      }
      let id = distinct(ids)
      if (id.length > 1) {
        this.$message({
          message: "调节采集机制只能选择同一设备进行调节",
          type: "warning",
        });
      } else {
        this.dialogTableVisible4 = true;
        this.$api.sensor.getSensordetails({
          "id": id[0],
        }).then(data => {
          if (data.code == 0) {
            this.list.push(data.data.equipmentInfo[0].acqu_freq2)
            this.list.push(data.data.equipmentInfo[0].acqu_freq3)
          } else {
            this.$message.error(data.msg);
          }
        });
      }
    },
    // 获取列表数据
    async getDataList() {
      let handleState = '';
      if (this.formData.handleState == "QB") {
        handleState = '';
      } else {
        handleState = this.formData.handleState;
      }
      let params = {
        page: this.pageIndex,
        limit: this.pageLimt,
        handleState: handleState,
        dev_id:"",
        dev_no:this.formData.dev_no,
        startTime: this.formData.timeFrame && this.formData.timeFrame[0],
        endTime: this.formData.timeFrame && this.formData.timeFrame[1]
      }
      await this.$api.alarm.getAllAlarms(params).then(({ data }) => {
        if (data) {
          for (let i = 0; i < data.list.length; i++) {
            data.list[i].create_time = this.$date.getNowFormatDate(data.list[i].create_time)
          }
          this.dataList = data.list;
          this.$refs.alarmTable.setCurrentRow(data.list[0]);
          this.totalPage = data.paging.total;
        }
      })

    },
    // 立即处理
    doHandle() {
      if (this.dataListSelections.length <= 0) {
        this.$message({
          message: '请选择需要处理的数据',
          type: 'warning',
          duration: 1500,
        })
        return;
      }
      this.dialogTableVisible2 = true;
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.st === 'DCL') {
        return 'warning-row';
      } else if (row.st === 'YCL') {
        return 'success-row';
      }
      return '';
    },
    // 选中单行
    async handleCurrentChange(val) {
      await this.$api.sensor.getSensordetails({ id: val.dev_id }).then(({ data }) => {
        val["devState"] = data.equipmentInfo[0].st;
        val["currDevInfo"] = data.equipmentInfo[0];
      })
      this.currentRow = val;
      this.statistical(val.dev_id)
      this.drawLine2();
    },
    statistical(id) {
      this.$api.chartapi.getAvailabilityChart({ devId: id }).then(data => {
        if (data.code == 0) {
          if( data.data.length>0){
            let alarmNum = data.data[0].alarmNum;
            let correctNum = data.data[0].normalNum;
            let correct = 0
            if (correctNum != 0) {
              correct = correctNum / (alarmNum+ correctNum)*100;
            }
            this.count.alarmNum = alarmNum;
            this.count.correctNum = correctNum;
            this.count.allNum = alarmNum+ correctNum;
            this.drawLine(alarmNum, correctNum, correct.toFixed(2))
          }else{
            this.count.alarmNum = 0;
            this.count.correctNum = 0;
            this.count.allNum = 0;
            this.drawLine(0, 0, 0)
          }
        }
      });
    },
    // 阈值调整
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
    // 阈值表单提交
    submitYZForm() {
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
    drawLine(alarmNum, correctNum, correct) {
      let pie = this.$echarts.init(document.getElementById("pie"));
      // 绘制图表
      var option = {
        title: {
          text: '正确率',
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
            text: correct + "%",
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
              { value: correctNum, itemStyle: { color: '#2AC3B4' } },
              { value: alarmNum, itemStyle: { color: '#999999' } },
            ]
          }
        ]
      };
      option && pie.setOption(option);
    },
    drawLine2() {
      let that = this;
      let myChart1 = this.$echarts.init(document.getElementById('myChart1'));
      let myChart2 = this.$echarts.init(document.getElementById('myChart2'));
      let color1 = '#F0F0F0';
      let color2 = '#F0F0F0';
      let data1 = '', data2 = '';
      if (that.currentRow.temperature == null) {
        data1 = 0;
      } else {
        that.currentRow.temperature = parseFloat(that.currentRow.temperature).toFixed(2);
        data1 = that.currentRow.temperature;
        let ture = parseFloat(that.currentRow.temperature);
        let up = parseFloat(that.currentRow.temp_up);
        let low = parseFloat(that.currentRow.temp_low);
        if (ture < up && ture > low) {
          color1 = '#2AC3B4'
        } else {
          color1 = '#FF656D'
        }
      }
      if (that.currentRow.humidity == null) {
        data2 = 0;
      } else {
        that.currentRow.humidity = parseFloat(that.currentRow.humidity).toFixed(2);
        data2 = that.currentRow.humidity;
        let humidity = parseFloat(that.currentRow.humidity);
        let hum_up = parseFloat(that.currentRow.hum_up);
        let hum_low = parseFloat(that.currentRow.hum_low);
        if (humidity > hum_low && humidity < hum_up) {
          color2 = '#2AC3B4'
        } else {
          color2 = '#FF656D'
        }
      }
      var option1, option2, option3;
      option1 = {
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
            data: [{value: data1},
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
              color: color2,
              offsetCenter: [0, "0%"],
              formatter: function (value) {
                return +value.toFixed(2) + '%RH';
              },
            },
            data: [{value: data2},
            ],
          },
        ],
      };
      option1 && myChart1.setOption(option1);
      option2 && myChart2.setOption(option2);
    },
    // 多选
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    // 禁止选中
    checkSelectable(row) {
      return row.st !== "YCL";
    }
  },
};
</script>
<style lang="scss" scoped>
::v-deep .el-table .warning-row {
  background: #f3e5e5;
}
::v-deep .el-table .success-row {
  background: #f0f9eb;
}
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
        padding-left: 3%;
        .export {
          margin-left: 3%;
        }
        .hu {
          margin-right: 3%;
        }
        .print,
        .export {
          padding: 0.07rem 0.15rem;
          font-size: 0.14rem;
          margin-top: 0.06rem;
        }
        .el-select {
          width: 12%;
          margin-left: 2%;
          ::v-deep .el-input__inner {
            padding: 0 0.1rem;
          }
        }
        .el-date-editor--daterange.el-input__inner {
          width: 20%;
          margin-right: 0.1rem;
          margin-left: 2%;
          padding: 0 0.1rem;
        }
        ::v-deep .el-range-editor--mini .el-range-separator {
          line-height: 2.2;
        }
        .alarm ,.el-input{
          width: 10%;
        }
        .el-input{
           margin-left: 2%;
        }
      }
      .historybox {
        margin: 2%;
        padding: 1%;
        border: 1px solid #ebebeb;
        opacity: 1;
        border-radius: 0.08rem;
      }
    }
    .colright {
      box-shadow: 0px 0px 13px rgba(0, 0, 0, 0.08);
      padding: 0.16rem;
      .piebin {
        height: 25vh;
        border: 1px solid #f0f0f0;
        opacity: 1;
        border-radius: 0.09rem;
        padding: 0.2rem;
        position: relative;
        p {
          margin-bottom: 0.1rem;
        }
        .onlinenum {
          position: absolute;
          top: 42%;
          left: 48%;
          font-size: 0.14rem;
          .onlinenumzx {
            color: #2ac3b4;
            .zxbox1 {
              display: inline-block;
              width: 0.1rem;
              height: 0.1rem;
              border-radius: 0.05rem;
              margin-right: 0.1rem;
              background-color: #2ac3b4;
            }
            .zxbox3 {
              margin-right: 0.1rem;
            }
          }
          .onlinenumlx {
            margin-top: 12%;
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
      .rate {
        height: 26vh;
        margin-top: 0.1rem;
        p {
          margin-bottom: 0.4rem;
        }
      }
      .light-top {
        margin-bottom: 0.1rem;
        border: 1px solid #f0f0f0;
        opacity: 1;
        border-radius: 0.09rem;
        padding: 0.2rem;
        .instrument {
          font-size: 0.18rem;
          padding-right: 0.1rem;
          color: #1a1a1a;
          margin-bottom: 0.1rem;
          .online {
            font-size: 0.14rem;
          }
        }
        .personnel {
          font-size: 0.12rem;
          color: #999999;
        }
        .thechart {
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
                color: #999999;
                text-align: center;
              }
              .standard {
                position: absolute;
                width: 100%;
                bottom: -0.2rem;
                font-weight: 400;
                color: #666666;
                font-size: 0.14rem;
                text-align: center;
              }
            }
          }
        }
        .handle {
          padding: 0.1rem 0.3rem 0;
          margin-top: 0.1rem;
          display: flex;
          justify-content: space-between;
        }
      }
    }
  }
}
.promptlydialog {
  ::v-deep .promptly {
    text-align: center;
  }
}
.promptlydialogchild {
  ::v-deep .el-dialog__body {
    position: relative;
    padding-bottom: 0.6rem;
  }
  ::v-deep .processedbutton {
    margin-top: 0.1rem;
    position: absolute;
    bottom: 0.1rem;
    right: 0.2rem;
  }
}
</style>
