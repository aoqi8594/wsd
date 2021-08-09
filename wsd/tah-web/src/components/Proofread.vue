<template>
  <el-dialog class="room" :title="title" :visible.sync="visible" :width="width" :height="height" :top="top" :modal="modal" append-to-body :close-on-press-escape="false" destroy-on-close :close-on-click-modal="false" :show-close="false">
    <div class="roombutton">
      <el-button size="mini" type="primary" @click="saveproofreading">保存</el-button>
      <el-button size="mini" @click="closeDialog">取消</el-button>
    </div>
    <div class="prepare">
      <div class="facility">
        <el-form ref="form" :model="form" :rules="rules" label-width="110px">
          <el-form-item class="box" label="温度标定:" prop="std_temp">
            <el-input size="mini" v-model="form.std_temp"></el-input>
          </el-form-item>
          <el-form-item class="box" label="湿度标定:" prop="std_hum">
            <el-input size="mini" v-model="form.std_hum"></el-input>
          </el-form-item>
          <el-form-item class="box" label="校对次数:">
            <el-select size="mini" v-model="form.num" placeholder="校对次数" @change="changenum">
              <el-option v-for="(item, index) in time" :key="index" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>
          <el-button size="mini" class="remove fr" type="primary" v-if="pagh=='wen'" @click="removefacility">移除</el-button>
        </el-form>
        <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" border style="width: 100%" height="400px" :row-style="{height: '40px'}" :cell-style="{padding: '0'}" @selection-change="handleSelectionChange">
          <el-table-column align="center" type="selection" width="40" v-if="pagh=='wen'"></el-table-column>
          <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
          <el-table-column align="center" prop="dev_name" label="温湿度计名称" width="130"></el-table-column>
          <el-table-column align="center" prop="dev_no" label="温湿度计编号" width="130"></el-table-column>
          <el-table-column align="center" label="校对时间" width="140">
            <template slot-scope="scope">
              <el-date-picker size="mini" v-model="scope.row.date" value-format="yyyy-MM-dd" type="date" placeholder="选择日期"></el-date-picker>
            </template>
          </el-table-column>
          <el-table-column align="center" width="120" v-for="(col, index) in cols" :key="index" :label="col.label">
            <template slot-scope="scope">
              <el-input size="mini" v-if="index== 0" v-model="scope.row.w1" placeholder="校对温度值1"></el-input>
              <el-input size="mini" v-if="index== 1" v-model="scope.row.s1" placeholder="校对湿度值1"></el-input>
              <el-input size="mini" v-if="index== 2" v-model="scope.row.w2" placeholder="校对温度值2"></el-input>
              <el-input size="mini" v-if="index== 3" v-model="scope.row.s2" placeholder="校对湿度值2"></el-input>
              <el-input size="mini" v-if="index== 4" v-model="scope.row.w3" placeholder="校对温度值3"></el-input>
              <el-input size="mini" v-if="index== 5" v-model="scope.row.s3" placeholder="校对湿度值3"></el-input>
              <el-input size="mini" v-if="index== 6" v-model="scope.row.w4" placeholder="校对温度值4"></el-input>
              <el-input size="mini" v-if="index== 7" v-model="scope.row.s4" placeholder="校对湿度值4"></el-input>
              <el-input size="mini" v-if="index== 8" v-model="scope.row.w5" placeholder="校对温度值5"></el-input>
              <el-input size="mini" v-if="index== 9" v-model="scope.row.s5" placeholder="校对湿度值5"></el-input>
              <el-input size="mini" v-if="index== 10" v-model="scope.row.w6" placeholder="校对温度值6"></el-input>
              <el-input size="mini" v-if="index== 11" v-model="scope.row.s6" placeholder="校对湿度值6"></el-input>
              <el-input size="mini" v-if="index== 12" v-model="scope.row.w7" placeholder="校对温度值7"></el-input>
              <el-input size="mini" v-if="index== 13" v-model="scope.row.s7" placeholder="校对湿度值7"></el-input>
              <el-input size="mini" v-if="index== 14" v-model="scope.row.w8" placeholder="校对温度值8"></el-input>
              <el-input size="mini" v-if="index== 15" v-model="scope.row.s8" placeholder="校对湿度值8"></el-input>
            </template>
          </el-table-column>
          <el-table-column align="center" label="备注">
            <template slot-scope="scope">
              <el-input size="mini" v-model="scope.row.remark" placeholder=""></el-input>
            </template>
          </el-table-column>
        </el-table>
      </div>

    </div>
  </el-dialog>
</template>
<script>
export default {
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
      default: "3vh",
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
  data() {
    return {
      cols: [{ prop: 'w1', label: '校对温度值1' }, { prop: 's1', label: '校对湿度值1' }],
      time: [1, 2, 3, 4, 5, 6, 7, 8],//校对次数
      num: 1,//默认
      floorList: [],
      form: {
        std_temp: "",
        std_hum: "",
        num: 1,
      },
      datalist: [],//父页面传过来来的设备列表
      tableData: [],//渲染的设备列表数据
      rules: {
        std_temp: [
          { required: true, message: '请输入计量后温度', trigger: 'blur' },
        ],
        std_hum: [
          { required: true, message: '请输入计量后湿度', trigger: 'blur' },
        ],
      },
      pagh: ''
    };
  },
  computed: {
    visible: {
      get() {
        return this.show;
      },
      set(val) {
        // 当visible改变的时候，触发父组件的 updateVisible方法，在该方法中更改传入子组件的 centerDialogVisible的值
        // this.$emit('updateVisible', val)
      },
    },
  },
  created() { },
  mounted() {
    let that = this;
    that.drawLine();
  },
  methods: {
    //温度计列表
    init(obj, pagh) {
      this.pagh = pagh;
      this.datalist = obj;
      for (let i = 0; i < obj.length; i++) {
        let data = {
          dev_name: obj[i].dev_name,
          dev_no: obj[i].dev_no,
          date: '',
          remark: '',
          id: obj[i].id,
          w1: '',
          s1: '',
          w2: '',
          s2: '',
          w3: '',
          s3: '',
          w4: '',
          s4: '',
          w5: '',
          s5: '',
          w6: '',
          s6: '',
          w7: '',
          s7: '',
          w8: '',
          s8: '',
        };
        this.tableData.push(data)
      }
    },
    //选择校对次数
    changenum(e) {
      this.form.num = e;
      this.cols = [];
      for (let i = 0; i < e; i++) {
        let str = { prop: 'w' + (i + 1), label: '校对温度值' + (i + 1) };
        let str2 = { prop: 's' + (i + 1), label: '校对湿度值' + (i + 1) };
        this.cols.push(str);
        this.cols.push(str2);
      }
    },
    //移除设备
    removefacility() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: "您尚未选择要移除的对象",
          type: "warning",
        });
      } else {
        let arr1 = this.multipleSelection;
        let arr2 = this.tableData;
        arr1.forEach((item1) => {
          arr2.forEach((item2, j) => {
            if (item2 == item1) {
              arr2.splice(j, 1)
              j -= 1
            }
          })
        })
      }
    },
    drawLine() { },
    closeDialog() {
      this.tableData = [];
      this.form.std_temp = '';
      this.form.std_hum = '';
      this.form.num = 1;
      this.$emit("close");
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(val)
    },
    saveproofreading() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.save()
        } else {
          return false;
        }
      });
    },
    save() {
      let array = this.tableData;
      let arr = [];
      for (let i = 0; i < array.length; i++) {
        if (array[i].date) {
          let check_temp = `${array[i].w1 ? array[i].w1 : null},${array[i].w2 ? array[i].w2 : null},${array[i].w3 ? array[i].w3 : null},${array[i].w4 ? array[i].w4 : null},${array[i].w5 ? array[i].w5 : null},${array[i].w6 ? array[i].w6 : null},${array[i].w7 ? array[i].w7 : null},${array[i].w8 ? array[i].w8 : null}`;
          let check_hum = `${array[i].s1 ? array[i].s1 : null},${array[i].s2 ? array[i].s2 : null},${array[i].s3 ? array[i].s3 : null},${array[i].s4 ? array[i].s4 : null},${array[i].s5 ? array[i].s5 : null},${array[i].s6 ? array[i].s6 : null},${array[i].s7 ? array[i].s7 : null},${array[i].s8 ? array[i].s8 : null}`;
          console.log(check_temp)
          let obj = {
            "std_temp": this.form.std_temp,
            "std_hum": this.form.std_hum,
            "disp_or": this.form.num,
            "dev_id": array[i].id,
            "check_time": array[i].date,
            "check_temp": check_temp,
            "check_hum": check_hum,
            "remark": array[i].remark
          }
          arr.push(obj);
        }else{
          this.$message({
            message: "请先完善列表中校对时间",
            type: "warning",
          });
          return false
        }
      }
      this.$api.proofread
        .proofreadadd(arr)
        .then((data) => {
          if (data.code == 0) {
            this.$message({
              message: data.msg,
              type: "success",
            });
            this.tableData = [];
            this.$emit("close");
          } else {
            this.$message.error(data.msg);
          }
        });
    }
  },
};
</script>
<style lang="scss" scoped>
.room {
  .roombutton {
    position: absolute;
    top: 0.2rem;
    right: 0.2rem;
  }
  ::v-deep .el-dialog__header {
    border-bottom: 1px solid #e6e6e6;
  }

  .prepare {
    .facility {
      border: 1px solid #f0f0f0;
      width: 98%;
      margin-left: 1%;
      .el-form {
        ::v-deep .el-form-item {
          margin-bottom: 0rem;
        }
        .box {
          display: inline-block;
          width: 25%;
          ::v-deep .el-input__inner {
            height: 0.26rem !important;
          }
        }
        .remove {
          margin-right: 0.2rem;
          margin-top: 0.06rem;
        }
      }
      ::v-deep .el-table {
        .el-table__body-wrapper {
          .el-input {
            .el-input__inner {
              padding: 0 0.06rem;
            }
          }
          .el-date-editor.el-input,
          .el-date-editor.el-input__inner {
            width: 100%;
            .el-input__prefix {
              display: none;
            }
          }
        }
      }
    }
  }
}
</style>