<template>
  <div class="car">
    <el-row>
      <el-col :span="24">
        <div class="carname">
          <span class="">温湿度计管理</span>
          <el-input placeholder="温湿度计名称" size="mini" clearable v-model="input2"></el-input>
          <el-select size="mini" v-model="state" placeholder="设备状态" @change="devicestate">
            <el-option v-for="(item, index) in stist" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
          <el-select size="mini" v-model="purpose" placeholder="用途" @change="application">
            <el-option v-for="(item, index) in belong_typelist" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
          <el-button type="primary" size="mini" @click="thermometer">查询</el-button>
          <el-button class="export fr hu" type="danger" size="mini" :disabled='disable2' @click="cancelSensor">删除</el-button>
          <el-button class="export fr" @click="roomopen" type="primary" :disabled='disable2' size="mini">校对</el-button>
          <el-button class="export fr" type="warning" size="mini" :disabled='disable2' @click="changestate('TY')">停用</el-button>
          <el-button class="export fr" type="success" size="mini" :disabled='disable' @click="changestate('QY')">启用</el-button>
          <el-button class="export fr" type="primary" size="mini" @click="roomdialog">新增</el-button>
        </div>
        <div class="historybox">
          <el-table ref="multipleTable" :height="tableHeight" :data="tableData" :row-style="{height: '40px'}" :cell-style="{padding: '0'}" border tooltip-effect="dark" @row-dblclick="handledbClick" @selection-change="handleSelectionChange">
            <el-table-column type="selection" fixed align="center" width="40"></el-table-column>
            <el-table-column fixed width="50" align="center" type="index" label="序号" :index="indexMethod"> </el-table-column>
            <el-table-column prop="dev_name" align="center" width="130" label="名称" show-overflow-tooltip></el-table-column>
            <el-table-column prop="dev_no" align="center" width="130" label="编号" show-overflow-tooltip></el-table-column>
            <el-table-column prop="belong_type" label="类型" width="80" align="center"></el-table-column>
            <el-table-column prop="use_type" label="用途" width="80" align="center"></el-table-column>
            <el-table-column prop="temp_up" align="center" width="80" label="温度上限"></el-table-column>
            <el-table-column prop="temp_low" align="center" width="80" label="温度下限"></el-table-column>
            <el-table-column prop="hum_up" align="center" width="80" label="湿度上限"></el-table-column>
            <el-table-column prop="hum_low" align="center" width="80" label="湿度下限"></el-table-column>
            <el-table-column prop="dev_mac" align="center" label="mac位置" show-overflow-tooltip></el-table-column>
            <el-table-column prop="duty_name" align="center" width="80" label="负责人"></el-table-column>
            <el-table-column prop="duty_cont_phon" align="center" width="110" label="联系电话" show-overflow-tooltip></el-table-column>
            <el-table-column prop="duty_email" align="center" width="130" label="邮箱" show-overflow-tooltip></el-table-column>
            <el-table-column prop="st" align="center" width="80" label="设备状态"></el-table-column>
            <el-table-column prop="create_time" align="center" width="130" label="操作时间" show-overflow-tooltip></el-table-column>
            <el-table-column prop="remark" align="center" width="140" label="备注" show-overflow-tooltip></el-table-column>
          </el-table>
          <pagination v-show="totalPage > 0" :total="totalPage" :page.sync="pageIndex" :limit.sync="pageLimt" @pagination="thermometer" />
        </div>
      </el-col>
    </el-row>
    <Humiture :title="title" ref='editor' :show.sync="dialogTableVisible" height="6.26rem" width="10.6rem" @close="closepage"></Humiture>
    <DeviceInfo ref="deviceinfo" :show.sync="opendetails" height="6.26rem" width="10.6rem" :id="id" @close="closedetails"></DeviceInfo>
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
import Pagination from "@/components/Pagination";
import Humiture from "@/components/Humiture.vue";
import Proofread from "@/components/Proofread.vue";
import DeviceInfo from "@/components/DeviceInfo.vue";
export default {
  components: { Humiture, Proofread, DeviceInfo, Pagination },
  data() {
    return {
      input: "",
      form: {
        time: ''
      },
      tableData: [],
      tableHeight: window.innerHeight - 210,
      multipleSelection: [], //选中的列表数据
      dialogTableVisible: false,
      dialogopen: false,
      opendetails: false,
      enablebox: false,
      belong_typelist: [], //用途列表
      stist: [],
      purpose: "", //用途
      state: "", //状态
      input2: "", //温湿度计名称
      id: '',//双击选中的温湿度计ID
      pageIndex: 1,
      pageLimt: 20,
      totalPage: 0,
      disable: true,
      disable2: true,
      title:'新增',
      rules: {
        time: [
          { required: true, message: '请选择采集时间', trigger: 'change' }
        ],
      },
    };
  },
  mounted() {
    let that = this;
    that.thermometer();
    that.dictionary("SBYT");
    that.dictionary("SBSYZT"); 
  },
  methods: {
    indexMethod(index) {
      return (index + 1) + ((this.pageIndex - 1) * this.pageLimt);
    },
    //查询获取设备列表
    thermometer() {
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
      this.multipleSelection = [];
      this.disable = true;
      this.disable2 = true;
      this.$api.sensor
        .getSensorList({
          dev_name: this.input2,
          st: st,
          use_type: use_type,
          page: this.pageIndex,
          limit: this.pageLimt,
        })
        .then((data) => {
          if (data.code == 0) {
            let res = data.data.list;
            // console.log(data.data.list)
            this.totalPage = data.data.paging.total;
            for (let i = 0; i < res.length; i++) {
              res[i].create_time = this.$date.getNowFormatDate(res[i].create_time);
              res[i].st  = this.$options.filters['formatState'](res[i].st);
              res[i].belong_type  = this.$options.filters['formatType'](res[i].belong_type);
              res[i].use_type  = this.$options.filters['formatUse'](res[i].use_type);
            }
            this.tableData = data.data.list;
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    //获取数据字典列表
    dictionary(parent_code) {
      this.$api.dictionary
        .getDictionaryList({ parent_code: parent_code })
        .then((data) => {
          if (data.code == 0) {
            if (parent_code == "SBYT") {
              if (data.data) {
                let obj = {
                  val: '全部',
                  code: 'QB'
                }
                data.data.list.unshift(obj)
                this.belong_typelist = data.data.list;
              }
            } else if (parent_code == "SBSYZT") {
              if (data.data) {
                let obj = {
                  val: '全部',
                  code: 'Q'
                }
                data.data.list.unshift(obj)
                this.stist = data.data.list;
              }
            }
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    //删除设备
    cancelSensor() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: "您尚未选择要删除的对象",
          type: "warning",
        });
      } else {
        this.$confirm('此操作将删除设备, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.request()
        }).catch(() => {
        });
      }
    },
    request() {
      let array = this.multipleSelection;
      let ids = [];
      for (let i = 0; i < array.length; i++) {
        ids.push(array[i].id);
      }
      this.$api.sensor.cancelSensor({ ids: ids }).then((data) => {
        if (data.code == 0) {
          this.$message({
            message: data.msg,
            type: "success",
          });
          this.thermometer();
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //获取选中用途
    application(e) {
      this.purpose = e;
    },
    //获取设备状态
    devicestate(e) {
      this.state = e;
    },
    //添加成功关闭弹框更新数据
    closepage() {
      this.dialogTableVisible = false;
      this.purpose = ""; //用途
      this.state = ""; //状态
      this.input2 = ""; //温湿度计名称
      this.thermometer();
    },
    //关闭详情弹框
    closedetails() {
      this.opendetails = false;
    },
    //打开添加设备弹框
    roomdialog() {
      this.dialogTableVisible = true;
    },
    //打开校对设备弹框
    roomopen() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: "您尚未选择要校对的对象",
          type: "warning",
        });
      } else {
        this.dialogopen = true;
        this.$refs.Proofread.init(this.multipleSelection, 'wen')
      }
    },
    //选中列表的数据
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if (val.length > 0) {
        this.disable2 = false
      }else{
        this.disable2 = true
      }
      if (val.length == 1) {
        if (val[0].st == '停用') {
          this.disable = false
        } else {
          this.disable = true
        }
      } else {
        this.disable = true
      }
    },
    //改变设备状态
    changestate(st) {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: "您尚未选择操作的对象",
          type: "warning",
        });
      } else {
        if (st == 'TY') {
          this.$confirm('停用后设备不再采集任何信息！', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.changetype(st);
          }).catch(() => {
          });
        } else {
          this.enablebox = true;
        }
      }
    },
    enableConfirm(){
       this.$refs['form'].validate((valid) => {
         if (valid) {
         this.changetype('QY')
        } else {
          return false;
        }
       });
    },
    changetype(st) {
      let array = this.multipleSelection;
      let ids = [];
      for (let i = 0; i < array.length; i++) {
        let idlist = array[i].id.toString();
        ids.push(idlist);
      }
      let ojb={
        ids: ids, 
        st: st
      }
      if(st=='QY'){
        //  console.log(this.form.time)
         ojb.start_time=this.form.time[0];
         ojb.end_time=this.form.time[1];
      }
      this.$api.sensor
        .changestate(ojb)
        .then((data) => {
          if (data.code == 0) {
            this.$message({
              message: data.msg,
              type: "success",
            });
            this.thermometer();
            this.enablebox = false;
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    //双击
    handledbClick(row, event, column) {
      let that = this;
      this.id = row.id;
      if(row.st=='闲置'){
        this.title='编辑';
        this.dialogTableVisible = true;
        this.$refs.editor.init(row)
      }else{
        this.opendetails = true;
        setTimeout(function () {
          that.$refs.deviceinfo.dictionary(row.id);
        }, 100);
      }
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
        height: 0.5rem;
        padding-top: 0.05rem;
        line-height: 0.4rem;
        padding-left: 3%;
        background-color: #fff;
        .export {
          margin-top: 0.06rem;
          margin-left: 1%;
        }
        .hu {
          margin-right: 3%;
        }
        .el-input {
          width: 12%;
          font-size: 0.14rem;
          margin-right: 0.1rem;
          margin-left: 0.1rem;
        }
        .el-select {
          margin-right: 0.1rem;
        }
      }
      .historybox {
        margin: 1% 2%;
        padding: 1% 1% 0.5% 1%;
        border: 1px solid #ebebeb;
        background-color: #fff;
        opacity: 1;
        border-radius: 0.08rem;
        .el-pagination {
          margin-top: 0.1rem;
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
