<template>
  <el-dialog class="globalDia" :title="title" :visible.sync="visible" :width="width" :top="top" :modal="modal" append-to-body :close-on-press-escape="false" destroy-on-close :close-on-click-modal="false" :show-close="true" @close='closeDialog'>
    <div class="">
      <el-table ref="multipleTable" :height="tableHeight" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange" :row-class-name="tableRowClassName">
        <el-table-column type="selection" header-align="center" align="center" :selectable="checkSelectable" width="55"></el-table-column>
        <el-table-column prop="belong_type" header-align="center" align="center" label="类型" width="90"></el-table-column>
        <el-table-column prop="location" header-align="center" align="center" label="位置" width="120" show-overflow-tooltip></el-table-column>
        <el-table-column prop="dev_no" header-align="center" align="center" label="编号" width="100"></el-table-column>
        <el-table-column prop="dev_name" header-align="center" align="center" label="名称" width="120" show-overflow-tooltip></el-table-column>
        <el-table-column prop="alarm_content" header-align="center" align="center" label="报警类型" show-overflow-tooltip></el-table-column>
        <el-table-column prop="create_time" header-align="center" align="center" label="触发时间" width="120" show-overflow-tooltip></el-table-column>
        <el-table-column prop="duty_name" header-align="center" align="center" label="负责人" width="80"></el-table-column>
        <el-table-column label="处理状态" header-align="center" align="center" width="80">
          <template slot-scope="scope">
            {{scope.row.st=='DCL'?'待处理':scope.row.st=='YCL'?'已处理':'处理中'}}
          </template>
        </el-table-column>
        <el-table-column prop="duty_cont_phon" header-align="center" align="center" label="联系电话" width="120"></el-table-column>
        <el-table-column prop="alarm_remark" header-align="center" align="center" label="备注" show-overflow-tooltip></el-table-column>
      </el-table>
      <pagination v-show="totalPage > 0" :total="totalPage" :page.sync="pageIndex" :limit.sync="pageLimt" @pagination="thermometer" />
      <div class="operationalarm">
        <el-button size="mini" icon="el-icon-remove-outline" @click="ignore">忽略</el-button>
        <el-button size="mini" icon="el-icon-time" @click="later">稍后</el-button>
        <el-button size="mini" type="primary" icon="el-icon-video-play" @click="dispose">立即处理</el-button>
      </div>
    </div>
    <el-dialog title="稍后处理" width="4rem" top="20vh" :visible.sync="dialogTableVisible" :close-on-click-modal="false" append-to-body>
      <div class="laterdispose">
        <el-button size="mini" @click="guanbi(5)">5min后</el-button>
        <el-button size="mini" @click="guanbi(10)">10min后</el-button>
        <el-button size="mini" @click="open">自定义</el-button>
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
  </el-dialog>
</template>

<script>
import Pagination from "@/components/Pagination";
import { distinct } from "@/utils/index";
export default {
  name: 'eldialog',
  props: {
    show: {
      type: Boolean,
      default: false,
    },
    title: {
      //标题
      type: String,
      default: "",
    },
    width: {
      type: [Number, String],
      default: "800px",
    },
    top: {
      type: String,
      default: "10vh",
    },
    modal: {
      type: Boolean,
      default: true,
    },
    height: {
      type: [Number, String],
      default: "auto",
    },
  },
  components: { Pagination },
  data() {
    return {
      tableHeight: window.innerHeight - 400,
      tableData: [],
      visible: false,
      dialogTableVisible: false,
      dialogTableVisible2: false,
      dialogTableVisible3: false,
      dialogTableVisible4: false,
      multipleSelection: [],
      pageIndex: 1,
      pageLimt: 10,
      totalPage: 0,
      textarea: '',
      radio: '',
      list: []
    }
  },
  created() {},
  mounted() {
    // this.thermometer()
  },
  methods: {
    // 禁止选中
    checkSelectable(row) {
      return row.st !== "YCL";
    },
    //获取报警列表
    thermometer(type) {
      this.$api.alarm.getAlarmsTips({
        type:type,
        page: this.pageIndex,
        limit: this.pageLimt,
      }).then(data => {
        if (data.code == 0) {
          this.totalPage = data.data.paging.total;
          let array = data.data.list;
          if (array.length > 0) {
            this.visible = true;
          }
          for (let i = 0; i < array.length; i++) {
            array[i].create_time = this.$date.getNowFormatDate(array[i].create_time)
            if (array[i].belong_type == 'LLC') {
              array[i].belong_type = '冷链车'
            } else {
              array[i].belong_type = '实验室'
            }
          }
          console.log(array)
          this.tableData = array
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //弹出理解处理
    Haveprocessed() {
      this.dialogTableVisible3 = true;
    },
    //选择立即处理
    dispose() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: "您尚未选择操作的对象",
          type: "warning",
        });
      } else {
        this.dialogTableVisible2 = true;
      }
    },
    //选择采集机制
    Haveprocessed2() {
      this.list = [];
      let array = this.multipleSelection;
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
    //列表报警颜色
    tableRowClassName({ row, rowIndex }) {
      if (row.alarm_yn === 'Y') {
        return 'warning-row';
      }
      return '';
    },
    //报警框关闭
    closeDialog() {
      this.visible = false;
    },
    //选择忽略处理
    ignore() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: "您尚未选择操作的对象",
          type: "warning",
        });
      } else {
        this.$confirm('忽略后将不在推送设备报警信息?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.alarmprocessing('HL', '')
        }).catch(() => { });
      }
    },
    // 报警处理
    alarmprocessing(type, content) {
      let array = this.multipleSelection;
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
          this.$emit("close");
          this.$message({
            type: 'success',
            message: '操作成功'
          });
          this.multipleSelection = [];
          this.thermometer()
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //点击稍后处理
    later() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: "您尚未选择操作的对象",
          type: "warning",
        });
      } else {
        this.dialogTableVisible = true;
      }
    },
    //稍后自定义处理
    open() {
      this.$prompt('请输入分钟数', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^[0-9]*$/,
        inputErrorMessage: '只能是数字'
      }).then(({ value }) => {
        this.dialogTableVisible = false;
        this.alarmprocessing('YC', Number(value))
      }).catch(() => {
        this.dialogTableVisible = false;
      });
    },
    //稍后选择处理
    guanbi(num) {
      this.dialogTableVisible = false;
      this.alarmprocessing('YC', num)
    },
    //列表勾选事件
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
  }
};
</script>
<style lang="scss" scoped>
.globalDia {
  ::v-deep .el-dialog {
    border-radius: 0.08rem;
    .el-dialog__title {
      float: left;
    }
  }
  ::v-deep .el-table .warning-row {
    .cell {
      color: #ff656d;
    }
  }
  ::v-deep .operationalarm {
    margin-top: 0.2rem;
    text-align: center;
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
