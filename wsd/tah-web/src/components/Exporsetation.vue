<template>
  <el-dialog class="room" @close="closeservewindow" :title="title" :visible.sync="visible" :width="width" :top="top" :modal="modal" append-to-body :close-on-press-escape="false" destroy-on-close :close-on-click-modal="false" :show-close="false">
    <div class="roombutton">
      <el-button size="mini" type="primary" @click="preserve">保存</el-button>
      <el-button size="mini" @click="closeDialog">取消</el-button>
    </div>
    <div class="roomcontent">
      <p>规则设置</p>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item class="box" label="取值方式:" prop="val_type">
          <el-radio-group v-model="form.val_type" @change="changeval_type">
            <el-radio v-for="(item, index) in approaches" :key="index" :label="item.val"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item class="box custom" label="导出时间:" prop="time">
          <el-date-picker @change="changetime" v-model="form.time" :picker-options="pickerOptions" format="yyyy-MM-dd HH" value-format="yyyy-MM-dd HH" type="datetimerange" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item class="box fl" label="报表记录时间:">
          <el-radio-group v-model="form.record_time">
            <el-radio v-for="(item, index) in recordtime" :key="index" :label="item.val"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item class="box" label="自定义时间:" prop="cust_record_time" v-if="form.record_time=='自定义'">
          <el-input-number size="mini" v-model="form.cust_record_time" placeholder="请输入时间"></el-input-number>
        </el-form-item>
        <el-form-item class="box" label="数据修约:" prop="data_round">
          <el-select v-model="form.data_round" placeholder="请选择">
            <el-option v-for="(item, index) in takingabout" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box" label="导出至:" prop="sender_type">
          <el-checkbox-group v-model="form.sender_type">
            <el-checkbox v-for="(item, index) in sender_type" :key="index" :label="item.val" name="type"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="生报表时间:" class="box" prop="export_time">
          <el-date-picker v-model="form.export_time" :picker-options="timeChange" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注:" class="box">
          <el-input type="textarea" v-model="form.mark"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="prepare">
      <p>设备信息
        <el-button size="mini" class="fr undock" @click="removethermometer">移除</el-button>
        <el-button type="primary" class="fr" size="mini" @click="addthermometer">新增</el-button>
      </p>
      <div class="equipment">
        <div class="facility">
          <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" height="200px" :row-style="{height: '40px'}" :cell-style="{padding: '0'}" @selection-change="handleSelectionChange">
            <el-table-column type="selection" align="center" width="50"></el-table-column>
            <el-table-column prop="dev_no" align="center" label="编号"></el-table-column>
            <el-table-column prop="dev_name" align="center" label="名称"></el-table-column>
            <el-table-column prop="name" align="center" label="设备位置"></el-table-column>
            <el-table-column prop="duty_name" align="center" label="负责人"></el-table-column>
            <el-table-column prop="duty_cont_phon" align="center" label="联系电话"></el-table-column>
            <el-table-column prop="remark" align="center" label="备注"></el-table-column>
          </el-table>
        </div>
      </div>
    </div>
    <el-dialog class="core" width="10.6rem" @close="closewindow" :visible.sync="innerVisible" :close-on-click-modal="false" :show-close="false" append-to-body>
      <div class="devicetab">
        <span>新增设备</span>
        <el-input size="mini" clearable v-model="dev_name2" placeholder="设备名称"></el-input>
        <el-input size="mini" clearable v-model="dev_no2" placeholder="设备编号"></el-input>
        <el-select size="mini" v-model="use_type2" placeholder="用途" @change="application">
          <el-option v-for="(item, index) in belong_typelist" :key="index" :label="item.val" :value="item.code"></el-option>
        </el-select>
        <el-button size="mini" type="primary" @click="getthermometerlist">查询</el-button>
      </div>
      <i class="el-icon-close" @click="innerVisible = false"></i>
      <el-table :data="tableData2" tooltip-effect="dark" style="width: 100%" height="6rem" :row-style="{height: '40px'}" :cell-style="{padding: '0'}" @selection-change="handleSelectionChange2">
        <el-table-column type="selection" align="center" width="50"></el-table-column>
        <el-table-column type="index" align="center" label="序号" width="50"></el-table-column>
        <el-table-column prop="dev_name" align="center" label="温湿度计名称"></el-table-column>
        <el-table-column prop="dev_no" align="center" label="温湿度计编号"></el-table-column>
        <el-table-column prop="duty_name" align="center" label="负责人"></el-table-column>
        <el-table-column prop="duty_cont_phon" align="center" label="联系电话"></el-table-column>
        <el-table-column align="center" label="用途">
          <template slot-scope="scope">
            {{scope.row.use_type |formatUse}}
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="totalPage > 0" :total="totalPage" :page.sync="pageIndex" :limit.sync="pageLimt" @pagination="getthermometerlist" />
      <div class="confirmbutton">
        <el-button size="mini" @click="innerVisible=false">取消</el-button>
        <el-button size="mini" type="primary" @click="confirmoperate">确认</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>
<script>
import Pagination from "@/components/Pagination";
export default {
  components: { Pagination },
  props: {
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
  },
  computed: {
    timeChange() {
      var _this = this;
      return {
        disabledDate(time) {
          let date1=Date.now();
          let date2=new Date(_this.form.time[1]+':00:00').getTime();
          if(date1<date2){
            return time.getTime() <date2;//如果没有后面的-8.64e7就是不可以选择今天的
          }else{
            return time.getTime() <date1;//如果没有后面的-8.64e7就是不可以选择今天的
          }
        }
      }
    },
  }, 
  data() {
    return {
      value1: '',
      form: {
        val_type: "平均值",
        record_time: "1小时",
        data_round: "",
        time: ['', ''],
        sender_type: [],
        export_time: "",
        cust_record_time: '',
        mark: ""
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now();//禁止选择以前的时间
          return time.getTime() > Date.now();//禁止选择以后的时间
        },
      },
      visible: false,
      innerVisible: false,
      dev_name2: '',//内层筛选仪器名称
      use_type2: '',//内层筛选仪器用途
      dev_no2: '',//内层筛选仪器编号
      belong_typelist: [],//设备用途
      belong_typelist2: [],
      multipleSelection: [],
      multipleSelection2: [],
      tableData: [],//最终渲染在绑定列表中的温度计
      tableData2: [],//最终渲染在可选温度计列表中的数据
      allXZlist: [],//所有闲置温度计
      medianlist: [],//所有被选中的闲置温度计
      pageIndex: 1,
      pageLimt: 20,
      totalPage: 0,
      approaches: [],//取值方式
      recordtime: [],//导出记录时间
      sender_type: [],//接收人员类型
      takingabout: [],//数据修约
      rules: {
        time: [
          { required: true, message: '请选择导出时间', trigger: 'change' }
        ],
        data_round: [
          { required: true, message: '请选择数据修约', trigger: 'change' }
        ],
        export_time: [
          { required: true, message: '请选择生成报表时间', trigger: 'change' }
        ],
        sender_type: [
          { type: 'array', required: true, message: '请至少选择一个导出人', trigger: 'change' }
        ],
        cust_record_time: [
          { required: true, message: "自定义时间不能为空", trigger: "blur" },
          { type: 'number', min: 0, max: 10000000, message: '自定义时间必须大于0的数字' }
        ]
      },
      bidvalue: false
    };
  },
  created() { },
  mounted() {
    this.getDictionaryList('DCQZFS');//导出取值方式
    this.getDictionaryList('DCJLSJ');//导出记录时间
    this.getDictionaryList('DCSJXY');//数据修约
    this.getDictionaryList('JSXXRYLX');//推送人
    this.purpose();//获取用途列表
  },
  methods: {
    changetime(e){
      let  time1=new Date(e[1]+':00:00').getTime();
      let  time2=new Date(this.form.export_time+' 00:00:00').getTime();
         if(time1>=time2){
           this.form.export_time=''
         }
    },
    changeval_type(e) {
      console.log(e)
      if (e == "近标值") {
        this.bidvalue = true;
        this.belong_typelist = [
          { val: '全部', code: '' },
          { code: "LK", val: "冷库" },
          { code: "TPJ", val: "天平间" },
          { code: "CWK", val: "常温库" },
        ]
      } else {
        this.bidvalue = false;
        this.belong_typelist = this.belong_typelist2;
      }
    },
    drawLine(id, type) {
      this.visible = true;
      if (id) {
        this.$api.exporset
          .getexportdetails({
            "id": id,
          })
          .then((data) => {
            if (data.code == 0) {
              this.form.id = id;
              let senderarray = data.data.info.sender_type.split('#');
              console.log(senderarray)
              if (senderarray.length > 0) {
                for (let i = 0; i < senderarray.length; i++) {
                  senderarray[i] = this.$options.filters['formataSender'](senderarray[i])
                }
              }
              this.form.sender_type = senderarray;
              this.form.time = [
                this.$date.getNowFormatDateh(data.data.info.start_time),
                this.$date.getNowFormatDateh(data.data.info.end_time),
              ];
              this.form.val_type = this.$options.filters['valtype'](data.data.info.val_type);
              this.form.record_time = this.$options.filters['record_time'](data.data.info.record_time);
              this.form.data_round = data.data.info.data_round;
              this.form.export_time = data.data.info.export_time;
              this.form.cust_record_time = data.data.info.cust_record_time;
              this.form.mark = data.data.info.mark;
              this.tableData = data.data.info.devs;
              this.tableData = data.data.info.devs;
            } else {
              this.$message.error(data.msg);
            }
          });
      } else {
        this.form.id = '';
      }
    },
    //获取数据字典
    getDictionaryList(parent_code) {
      this.$api.dictionary.getDictionaryList({ parent_code: parent_code }).then((data) => {
        if (data.code == 0) {
          if (parent_code == 'DCQZFS') {
            this.approaches = data.data.list;
          } else if (parent_code == 'DCJLSJ') {
            this.recordtime = data.data.list;
          } else if (parent_code == 'JSXXRYLX') {
            this.sender_type = data.data.list;
          } else if (parent_code == 'DCSJXY') {
            this.takingabout = data.data.list;
          }
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //新增温度计
    addthermometer() {
      this.innerVisible = true;
      this.getthermometerlist();//获取闲置的列表
    },
    //获取温度计的列表
    getthermometerlist() {
      let jbz = '';
      if (this.bidvalue == true) {
        jbz = 'JBZ'
      }
      this.$api.sensor
        .getSensorList({
          dev_name: this.dev_name2,
          st: "",
          jbz: jbz,
          use_type: this.use_type2,
          dev_no: this.dev_no2,
          page: this.pageIndex,
          limit: this.pageLimt,
        })
        .then((data) => {
          if (data.code == 0) {
            this.allXZlist = data.data.list;
            this.tableData2 = this.duplicateremoval(this.tableData, this.allXZlist);
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    //获取没有被选择的
    duplicateremoval(arr1, arr2) {
      var array = arr2;
      var array2 = arr1;
      var arr3 = [];
      for (var i = 0; i < array.length; i++) {  //用来遍历的数组使用两个数组长度比较长的，不然可能没有效果
        var stra = array[i];
        var count = 0;
        for (var j = 0; j < array2.length; j++) {
          var strb = array2[j];
          if (stra.id == strb.id) {
            count++;
          }
        }
        if (count === 0) {
          arr3.push(stra);
        }
      }
      return arr3
    },
    //获取用途列表
    purpose() {
      this.$api.dictionary
        .getDictionaryList({ parent_code: 'SBYT' })
        .then((data) => {
          if (data.code == 0) {
            let obj = { val: '全部', code: '' }
            data.data.list.unshift(obj)
            this.belong_typelist = data.data.list;
            this.belong_typelist2 = data.data.list;
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    //获取选中用途
    application(e) {
      this.use_type2 = e;
    },
    //选中列表
    handleSelectionChange2(val) {
      this.multipleSelection2 = val;
    },
    //点击内层弹框
    confirmoperate() {
      let that = this;
      if (this.multipleSelection2.length == 0) {
        this.$message({
          message: "您尚未选择要操作的对象",
          type: "warning",
        });
      } else {
        this.innerVisible = false;
        this.tableData = this.tableData.concat(this.multipleSelection2);
      }
    },
    //内层弹框关闭触发清空事件
    closewindow() {
      this.dev_name2 = '';//内层筛选仪器名称
      this.use_type2 = '';//内层筛选仪器用途
      this.dev_no2 = '';//内层筛选仪器编号
      this.multipleSelection2 = []
    },
    //外层弹框关闭触发清空事件
    closeservewindow() {
      this.form.val_type = "平均值";
      this.form.record_time = "1小时";
      this.form.data_round = "";
      this.form.time = "";
      this.form.sender_type = [];
      this.form.export_time = '';
      this.form.mark = '';
      this.form.cust_record_time = '';
      this.multipleSelection = []
      this.tableData = []
    },
    preserve() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.form.id) {
            this.updatealarm();
          } else {
            this.addalarm();
          }
        } else {
          return false;
        }
      });
    },
    //编辑更新报警配置
    updatealarm() {
      let obj = {
        "rules": {
          "id": this.form.id,
          "val_type": "",
          "record_time": "",
          "data_round": this.form.data_round,
          "start_time": this.form.time[0],
          "end_time": this.form.time[1],
          "sender_type": "",
          "export_time": this.form.export_time,
          "mark": this.form.mark
        },
        "devids": []
      }
      obj.rules.val_type = this.matching(this.approaches, this.form.val_type);
      obj.rules.record_time = this.matching(this.recordtime, this.form.record_time);
      obj.rules.sender_type = this.matching2(this.sender_type, this.form.sender_type);
      if (this.form.record_time == '自定义') {
        obj.rules.cust_record_time = this.form.cust_record_time;
      }
      let array = []
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.bidvalue == true) {
          if (this.tableData[i].use_type != 'LK' && this.tableData[i].use_type != 'TPJ' && this.tableData[i].use_type != 'CWK') {
            this.$message({
              message: "取值方式为竞标值时，设备只能为冷库、天平间、常温库",
              type: "warning",
            });
            return false
          }
        }
        array.push(this.tableData[i].id)
      }
      obj.devids = array
      this.$api.exporset
        .updateexportset({
          rules: obj.rules,
          devids: obj.devids
        })
        .then((data) => {
          if (data.code == 0) {
            this.visible = false;
            this.$emit('updata')
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    matching(array, formdata) {
      let type = '';
      for (let i = 0; i < array.length; i++) {
        if (formdata == array[i].val) {
          type = array[i].code
        }
      }
      return type
    },
    matching2(array, formdata) {
      let str = [];
      for (let i = 0; i < formdata.length; i++) {
        for (let j = 0; j < array.length; j++) {
          if (formdata[i] == array[j].val) {
            str.push(array[j].code)
          }
        }
      }
      return str.join("#")
    },
    //新增报警配置
    addalarm() {
      let obj = {
        "rules": {
          "val_type": "",
          "record_time": "",
          "data_round": this.form.data_round,
          "start_time": this.form.time[0],
          "end_time": this.form.time[1],
          "sender_type": "",
          "export_time": this.form.export_time,
          "mark": this.form.mark
        },
        "devids": []
      }
      obj.rules.val_type = this.matching(this.approaches, this.form.val_type);
      obj.rules.record_time = this.matching(this.recordtime, this.form.record_time);
      obj.rules.sender_type = this.matching2(this.sender_type, this.form.sender_type);
      if (this.form.record_time == '自定义') {
        obj.rules.cust_record_time = this.form.cust_record_time;
      }
      let array = []
      for (let i = 0; i < this.tableData.length; i++) {
        if (this.bidvalue == true) {
          if (this.tableData[i].use_type != 'LK' && this.tableData[i].use_type != 'TPJ' && this.tableData[i].use_type != 'CWK') {
            this.$message({
              message: "取值方式为竞标值时，设备只能为冷库、天平间、常温库",
              type: "warning",
            });
            return false
          }
        }
        array.push(this.tableData[i].id)
      }
      obj.devids = array;
      this.$api.exporset
        .addexportlist(obj)
        .then((data) => {
          if (data.code == 0) {
            this.visible = false;
            this.$emit('updata')
            this.$message({
              message: data.msg,
              type: 'success'
            });
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    closeDialog() {
      this.visible = false;
    },
    //选中要绑定的温度计
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //移除温度计
    removethermometer() {
      this.tableData = this.duplicateremoval(this.multipleSelection, this.tableData);
    },
  },
};
</script>
<style lang="scss" scoped>
.core {
  ::v-deep .el-dialog__header {
    padding-bottom: 30px;
  }
  ::v-deep .el-dialog__body {
    border-top: 1px solid #eee;
    padding: 0 0.2rem;
    .el-table thead {
      color: #8c8c8c;
    }
    .el-table th {
      color: #8c8c8c;
      padding: 0.08rem 0;
    }
    .el-table .cell {
      color: #8c8c8c;
    }
  }
  .devicetab {
    position: absolute;
    top: 0.04rem;
    height: 0.4rem;
    line-height: 0.4rem;
    span {
      display: inline-block;
      margin-right: 0.3rem;
      font-weight: 600;
    }
    .el-input,
    .el-select {
      width: 20%;
      margin-left: 0.1rem;
    }
    .el-select {
      margin-right: 0.1rem;
    }
  }
  .confirmbutton {
    height: 0.6rem;
    text-align: center;
    .el-button {
      margin-top: 0.15rem;
      margin-right: 0.2rem;
    }
  }
  .el-icon-close {
    position: absolute;
    top: 0.1rem;
    right: 10px;
    font-size: 0.2rem;
  }
}
.room {
  .roombutton {
    position: absolute;
    top: 0.2rem;
    right: 0.2rem;
  }
  ::v-deep .el-dialog__header {
    border-bottom: 1px solid #e6e6e6;
  }
  .roomcontent {
    border: 1px solid #f0f0f0;
    opacity: 1;
    border-radius: 0.09rem;
    margin-bottom: 0.1rem;
    padding-bottom: 0.1rem;
    p {
      height: 0.4rem;
      line-height: 0.4rem;
      padding-left: 0.2rem;
    }
    .el-form {
      ::v-deep .el-form-item {
        margin-bottom: 0.1rem;
        .el-form-item__content {
          .el-checkbox-group {
            height: 0.4rem;
          }
          .el-form-item__error {
            padding-top: 0.02rem;
          }
        }
      }
      .box {
        display: inline-block;
        width: 50%;
        ::v-deep .el-input__inner {
          height: 0.3rem !important;
        }
      }
      .custom {
        ::v-deep .el-input__inner {
          width: 80%;
          .el-range__icon,
          .el-range-separator,
          .el-range__close-icon {
            line-height: 0.22rem;
          }
        }
      }
    }
  }
  .prepare {
    border: 1px solid #f0f0f0;
    opacity: 1;
    border-radius: 0.09rem;
    p {
      height: 0.4rem;
      line-height: 0.4rem;
      padding-left: 0.2rem;
      .fr {
        margin-top: 0.05rem;
      }
      .undock {
        margin-right: 3%;
        margin-left: 0.1rem;
      }
    }
    .equipment {
      margin-bottom: 0.1rem;
      .facility {
        border: 1px solid #f0f0f0;
        width: 94%;
        margin-left: 3%;
        border-radius: 0.06rem;
      }
    }
  }
}
</style>