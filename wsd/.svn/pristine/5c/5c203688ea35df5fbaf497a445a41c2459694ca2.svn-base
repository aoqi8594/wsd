<template>
  <div class="car">
    <el-row>
      <el-col :span="18">
        <div class="carname">
          <span class="">历史数据</span>
          <el-select size="mini" v-model="form.belong_type" placeholder="类型">
            <el-option v-for="(item, index) in belong_typelist" :key="index" :label="item.label" :value="item.code"></el-option>
          </el-select>
          <el-select size="mini" v-model="form.use_type" placeholder="设备类型">
            <el-option v-for="(item, index) in use_typelist" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
          <el-date-picker size="mini" v-model="form.time" type="daterange" align="right" format="yyyy-MM-dd" value-format="yyyy-MM-dd" unlink-panels range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions">
          </el-date-picker>
          <el-input size="mini" v-model="form.dev_no" clearable placeholder="温湿度计编号"></el-input>
          <el-input size="mini" v-model="form.dev_name" clearable placeholder="温湿度计名称"></el-input>
          <el-select class="alarm" size="mini" v-model="form.alarm_yn" placeholder="是否报警">
            <el-option label="全部" value="Q"></el-option>
            <el-option label="是" value="Y"></el-option>
            <el-option label="否" value="N"></el-option>
          </el-select>
          <el-button type="primary" size="mini" @click="demand">查询</el-button>
          <el-button class="print fr" type="primary" size="mini " @click="printContent">打印</el-button>
          <el-button type="primary" size="mini" @click="exportExcel()" class="export fr">导出</el-button>
        </div>
        <div class="historybox">
          <el-table ref="multipleTable" id="out-table" :row-class-name="tableRowClassName" highlight-current-row height="76vh" :data="tableData" border tooltip-effect="dark" style="width: 100%;" @row-click="rowclick" @selection-change="handleSelectionChange">
            <el-table-column prop="dev_no" header-align="center" align="center" label="计编号"></el-table-column>
            <el-table-column prop="dev_name" header-align="center" align="center" label="计名称"></el-table-column>
            <el-table-column prop="name" header-align="center" align="center" label="位置"></el-table-column>
            <el-table-column label="温度" header-align="center" align="center" width="80">
              <template slot-scope="scope">
                {{ scope.row.temperature }}℃
              </template>
            </el-table-column>
            <el-table-column label="温度准值" align="center" width="100">
              <template slot-scope="scope">
                {{ scope.row.temp_low }}~{{ scope.row.temp_up }}℃
              </template>
            </el-table-column>
            <el-table-column align="center" label="湿度" width="100">
              <template slot-scope="scope">
                {{ scope.row.humidity }}%RH
              </template>
            </el-table-column>
            <el-table-column label="湿度准值" align="center" width="120">
              <template slot-scope="scope">
                {{ scope.row.hum_low }}~{{ scope.row.hum_up }}%RH
              </template>
            </el-table-column>
            <el-table-column prop="alarm_yn" label="是否报警" align="center" width="80"></el-table-column>
            <el-table-column align="center" label="记录时间">
              <template slot-scope="scope">
                {{ scope.row.create_time }}
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="totalPage > 0" :total="totalPage" :page.sync="pageIndex" :limit.sync="pageLimt" @pagination="getHistoryList" />
        </div>
      </el-col>
      <el-col :span="6" class="colright">
        <div class="light-up databin">
          <p>监测数据</p>
          <Brokenline ref="detection"></Brokenline>
        </div>
        <div class="light-up piebin">
          <p>数据正确率</p>
          <div id="pie" :style="{ width: '48%', height: '70%' }"></div>
          <div class="onlinenum" v-if='count.allNum'>
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
  </div>
</template>
<script>
import Brokenline from "@/components/Brokenline.vue";
import Pagination from "@/components/Pagination";
import FileSaver from 'file-saver'
import XLSX from 'xlsx'
export default {
  components: {
    Brokenline,
    Pagination
  },
  data() {
    return {
      form: {
        belong_type: "",
        use_type: "",
        dev_no: "",
        alarm_yn: "",
        dev_name: "",
        time: ['', '']
      },
      pickerOptions: {
        shortcuts: [
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
      belong_typelist: [
        {
          label: '全部',
          code: 'QB'
        },
        {
          label: '冷链车',
          code: 'LLC'
        },
        {
          label: '实验室',
          code: 'SYS'
        },
      ],
      use_typelist: [],
      tableData: [],
      pageIndex: 1,
      pageLimt: 20,
      totalPage: 0,
      multipleSelection: "",
      count: {
        alarmNum: 0,
        correctNum: 0,
        allNum: 0
      }
    };
  },
  mounted() {
    let that = this;
    if (this.$route.params.dev_no) {
      this.form.dev_no = this.$route.params.dev_no
    }
    that.dictionary();
    that.getHistoryList();
  },
  methods: {
    //获取数据字典列表
    dictionary() {
      this.$api.dictionary
        .getDictionaryList({ parent_code: 'SBYT' })
        .then((data) => {
          if (data.code == 0) {
            if (data.data) {
              let obj = {
                val: '全部',
                code: 'QB'
              }
              data.data.list.unshift(obj)
              this.use_typelist = data.data.list;
            }
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    exportExcel() {
      var wb = XLSX.utils.table_to_book(document.querySelector('#out-table'))
      var wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' })
      try {
        FileSaver.saveAs(new Blob([wbout], { type: 'application/octet-stream' }), '历史数据.xlsx');
        this.$api.exporset.addexportrecord({
          report_name: "历史数据",
          export_type: "ZJDJ",
          file_path: "",
          creator_lname: "系统管理员"
        }).then(data => {
          if (data.code == 0) {
            this.$message({
              message: data.msg,
              type: 'success'
            });
          } else {
            this.$message.error(data.msg);
          }
        });
      } catch (e) {
        if (typeof console !== 'undefined')
          console.log(e, wbout)
      }
      return wbout
    },
    printContent() {
      // 打开一个新的浏览器窗口
      let str = ``
      for (let i = 0; i < this.tableData.length; i++) {
        str += `<div class="table">
            <div class="olumn_1">${this.tableData[i].dev_no ? this.tableData[i].dev_no : ''}</div>
            <div class="olumn_2">${this.tableData[i].dev_name ? this.tableData[i].dev_name : ''}</div>
            <div class="olumn_3">${this.tableData[i].name ? this.tableData[i].name : ''}</div>
            <div class="olumn_4">${this.tableData[i].temperature ? this.tableData[i].temperature : ''}℃</div>
            <div class="olumn_5">${this.tableData[i].temp_low ? this.tableData[i].temp_low : ''}~${this.tableData[i].temp_up ? this.tableData[i].temp_up : ''}℃</div>
            <div class="olumn_6">${this.tableData[i].humidity ? this.tableData[i].humidity : ''}%RH</div>
            <div class="olumn_7">${this.tableData[i].hum_low ? this.tableData[i].hum_low : ''}~${this.tableData[i].hum_up ? this.tableData[i].hum_up : ''}%RH</div>
            <div class="olumn_8">${this.tableData[i].alarm_yn ? this.tableData[i].alarm_yn : ''}</div>
            <div class="olumn_9">${this.tableData[i].create_time ? this.tableData[i].create_time : ''}</div>
          </div>`
      }
      let html = `<div class="box">
        <div class="header">
          <div class="olumn_1">计编号</div>
          <div class="olumn_2">计名称</div>
          <div class="olumn_3">位置</div>
          <div class="olumn_4">温度</div>
          <div class="olumn_5">湿度准值</div>
          <div class="olumn_6">湿度</div>
          <div class="olumn_7">湿度准值</div>
          <div class="olumn_8">是否报警</div>
          <div class="olumn_9">记录时间</div>
        </div>
        <div class="table_body">
          ${str}
        </div>
      </div>`;
      let newContent = html;
      let oldContent = document.body.innerHTML;
      let newhtml = `<div class="printpege">${newContent}</div>`;
      document.body.innerHTML = newhtml;
      window.print(); //打印方法
      location.reload();
      document.body.innerHTML = oldContent;
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.alarm_yn === '是') {
        return 'warning-row';
      }
      return '';
    },
    demand() {
      this.pageIndex = 1;
      this.pageLimt = 20;
      this.totalPage = 0;
      this.getHistoryList();
    },
    getHistoryList() {
      this.$api.historical.getHistoryList({
        belong_type: this.form.belong_type == 'QB' ? '' : this.form.belong_type,
        use_type: this.form.use_type == 'QB' ? '' : this.form.use_type,
        dev_no: this.form.dev_no,
        startTime: this.form.time ? this.form.time[0] : '',
        endTime: this.form.time ? this.form.time[1] : '',
        alarm_yn: this.form.alarm_yn == 'Q' ? '' : this.form.alarm_yn,
        dev_name: this.form.dev_name,
        page: this.pageIndex,
        limit: this.pageLimt
      }).then(data => {
        this.totalPage = data.data.paging.total;
        if (data.code == 0) {
          let array = data.data.list;
          if (array.length > 0) {
            for (let i = 0; i < array.length; i++) {
              if (array[i].alarm_yn == 'N') {
                array[i].alarm_yn = '否'
              } else if (array[i].alarm_yn == 'Y') {
                array[i].alarm_yn = '是'
              }
              array[i].create_time = this.$date.getNowFormatDate(array[i].create_time);
            }
            this.tableData = array;
            this.$refs.multipleTable.setCurrentRow(array[0]);
            this.$refs.detection.init(array[0].dev_id);
            this.statistical(array[0].dev_id);
          } else {
            this.tableData = [];
          }
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    statistical(id) {
      this.$api.chartapi.getDevAccChart({ dev_id: id }).then(data => {
        if (data.code == 0) {
          if (data.data.list.length > 0) {
            let alarmNum = data.data.list[0].alarmNum;
            let correctNum = data.data.list[0].allNum - data.data.list[0].alarmNum;
            let correct = 0
            if (correctNum != 0) {
              correct = correctNum / data.data.list[0].allNum*100;
            }
            this.count.alarmNum = alarmNum;
            this.count.correctNum = correctNum;
            this.count.allNum = data.data.list[0].allNum;
            this.drawLine2(alarmNum, correctNum, correct.toFixed(2))
          }
        }
      });
    },
    drawLine2(alarmNum, correctNum, correct) {
      let that = this;
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
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    rowclick(val) {
      this.$refs.detection.init(val.dev_id);
    },
  },
};
</script>
<style lang="scss" scoped>
.car {
  height: calc(100vh - 50px);
  background-color: #f5f7fa;
  .el-row {
    height: 100%;
    margin-bottom: 20px;
    .el-col {
      height: 100%;
      .carname {
        line-height: 0.4rem;
        height: 0.5rem;
        padding-top: 0.05rem;
        padding-left: 3%;
        background-color: #fff;
        span {
          margin-right: 3%;
        }
        .export {
          margin-left: 3%;
        }
        .print,
        .export {
          padding: 0.07rem 0.15rem;
          font-size: 0.14rem;
          margin-top: 0.06rem;
        }
        .print {
          margin-right: 3%;
        }
        .el-select {
          width: 7%;
          margin-right: 0.1rem;
          ::v-deep .el-input__inner {
            padding: 0 0.1rem;
          }
        }
        .el-date-editor--daterange.el-input__inner {
          width: 20%;
          margin-right: 0.1rem;
          padding: 0 0.1rem;
        }
        ::v-deep .el-range-editor--mini .el-range-separator {
          line-height: 2.2;
        }
        .el-input {
          width: 7%;
          font-size: 0.14rem;
          margin-right: 0.1rem;
          ::v-deep .el-input__inner {
            padding: 0 0.1rem;
            font-size: 0.12rem;
          }
        }
        .alarm {
          width: 7%;
        }
      }
      .historybox {
        margin: 2%;
        padding: 1%;
        border: 1px solid #ebebeb;
        opacity: 1;
        border-radius: 0.08rem;
        background-color: #fff;
        ::v-deep .el-table .warning-row {
          .cell {
            color: #ff656d;
          }
        }
      }
    }
    .colright {
      background-color: #fff;
      box-shadow: 0px 0px 13px rgba(0, 0, 0, 0.08);
      padding: 0.16rem;
      .databin {
        position: relative;
        border: 1px solid #f0f0f0;
        border-radius: 0.09rem;
        margin-bottom: 0.2rem;
        p {
          margin-top: 0.1rem;
          height: 0.3rem;
          line-height: 0.3rem;
          padding-left: 5%;
        }
      }
      .piebin {
        height: 22vh;
        position: relative;
        border: 1px solid #f0f0f0;
        border-radius: 0.09rem;
        p {
          margin-top: 0.1rem;
          height: 0.3rem;
          line-height: 0.3rem;
          padding-left: 5%;
        }
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
    }
  }
}
</style>
<style  lang="scss">
::v-deep .el-table__body-wrapper {
  .el-table__row {
    .cell {
      text-align: center;
    }
  }
}
.header div,
.table_body .table div {
  height: 40px;
  float: left;
  line-height: 40px;
  text-align: center;
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  word-break: break-all;
}
.header,
.table_body .table {
  width: 1100px;
  border: 1px solid #eee;
  box-sizing: border-box;
  overflow: hidden;
}
.table_body .table {
  border-top: 0px solid #eee;
}
.header div,
.table_body .table div {
  border-right: 1px solid #eee;
  box-sizing: border-box;
}
.olumn_1,
.olumn_2,
.olumn_3 {
  width: 12%;
}
.olumn_4,
.olumn_6 {
  width: 8%;
}
.olumn_5,
.olumn_7,
.olumn_8 {
  width: 10%;
}
.olumn_9 {
  width: 18%;
}
.header .olumn_9,
.table_body .table .olumn_9 {
  border-right: 0px solid #eee;
}
</style>