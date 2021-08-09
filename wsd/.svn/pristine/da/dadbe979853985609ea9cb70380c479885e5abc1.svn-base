<template>
  <el-dialog class="room" @close="closeservewindow" :title="title" :visible.sync="visible" :width="width" :top="top" :modal="modal" append-to-body :close-on-press-escape="false" destroy-on-close :close-on-click-modal="false" :show-close="false">
    <div class="roombutton">
      <el-button size="mini" type="primary" @click="preserve">保存</el-button>
      <el-button size="mini" @click="closeDialog">取消</el-button>
    </div>
    <div class="roomcontent">
      <p>基本信息</p>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item class="box" label="报警名称:" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item class="box" label="推送方式:" prop="notice_type">
          <el-checkbox-group v-model="form.notice_type">
            <el-checkbox v-for="(item, index) in notice_type" :key="index" :label="item.val" name="type"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item class="box" label="推送人:" prop="sender_type">
          <el-checkbox-group v-model="form.sender_type">
            <el-checkbox v-for="(item, index) in sender_type" :key="index" :label="item.val" name="type"></el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item class="box" label="报警类型:" prop="alarm_type">
          <el-select v-model="form.alarm_type" placeholder="请选择">
            <el-option v-for="(item, index) in alarm_type" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box fl" label="报警时段:" prop="alarm_zone">
          <el-radio-group v-model="form.alarm_zone" >
            <el-radio v-for="(item, index) in alarm_zone" :key="index" :label="item.val"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item class="box custom fl" label="自定义时段:" required v-if="form.alarm_zone=='自定义'">
          <el-col :span="11">
            <el-form-item prop="startTime">
              <el-time-picker placeholder="起始时间" v-model="form.startTime" class="date-box" format="HH:mm" value-format="HH:mm" :picker-options="{
                  selectableRange:`00:00:00 -${form.endTime ? form.endTime+':00' : '23:59:00'}`
                }">
              </el-time-picker>
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-form-item prop="endTime">
              <el-time-picker placeholder="结束时间" v-model="form.endTime" format="HH:mm" value-format="HH:mm" :picker-options="{
                  selectableRange: `${form.startTime ? form.startTime+':00' : '00:00:00'}-23:59:00`
                }">
              </el-time-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item class="box" label="报警级别:">
          <el-radio-group v-model="form.level">
            <el-radio v-for="(item, index) in level" :key="index" :label="item.val"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item class="box" label="是否启用:" prop="st">
          <el-radio-group v-model="form.st">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item class="box" label="节假日推送:">
          <el-radio-group v-model="form.holiday_push_yn">
            <el-radio label="是"></el-radio>
            <el-radio label="否"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="报警内容:" class="box" prop="content">
          <el-input type="textarea" v-model="form.content"></el-input>
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
  data() {
    return {
      form: {
        name: '',//报警名称
        alarm_type: '',//报警类型
        notice_type: [],//推送方式
        sender_type: [],//推送人
        alarm_zone: '实时报警',//报警时段
        st: '是',//状态：启用QY 停用 TY
        holiday_push_yn:'是',
        level: 'A',//报警级别ABC
        content: '',//报警内容
        startTime: '',
        endTime: '',
      },
      visible: false,
      innerVisible: false,
      dev_name2: '',//内层筛选仪器名称
      use_type2: '',//内层筛选仪器用途
      dev_no2: '',//内层筛选仪器编号
      belong_typelist: [],//设备用途
      multipleSelection: [],
      multipleSelection2: [],
      tableData: [],//最终渲染在绑定列表中的温度计
      tableData2: [],//最终渲染在可选温度计列表中的数据
      allXZlist: [],//所有闲置温度计
      medianlist: [],//所有被选中的闲置温度计
      pageIndex: 1,
      pageLimt: 10,
      totalPage: 0,
      alarm_type: [],//报警类型
      notice_type: [],//通知方式
      sender_type: [],//接收人员类型
      level: [],//报警级别
      alarm_zone: [],//报警时段
      rules: {
        name: [
          { required: true, message: '请输入报警名称', trigger: 'blur' },
        ],
        notice_type: [
          { type: 'array', required: true, message: '请至少选择一个推送方式', trigger: 'change' }
        ],
        sender_type: [
          { type: 'array', required: true, message: '请至少选择一个推送人', trigger: 'change' }
        ],
        startTime: [
          { required: true, message: '请选择开始时间', trigger: 'change' }
        ],
        endTime: [
          { required: true, message: '请选择结束时间', trigger: 'change' }
        ],
        alarm_type: [
          { required: true, message: '请选择报警类型', trigger: 'change' }
        ],
        content: [
          { required: true, message: '请输入报警内容', trigger: 'blur' },
        ]
      }
    };
  },
  created() { },
  mounted() {
    this.getDictionaryList('BJLX');//报警类型
    this.getDictionaryList('BJTZLX');//通知方式
    this.getDictionaryList('JSXXRYLX');//推送人
    this.getDictionaryList('BJJB');//报警级别
    this.getDictionaryList('BJSD');//报警时段
    this.purpose();//获取用途列表
  },
  methods: {
    drawLine(id, type) {
      this.visible = true;
      if (id) {
        this.$api.alarm
          .getAlarmsDetails({
            "id": id,
          })
          .then((data) => {
            if (data.code == 0) {
              this.form.id = id;
              this.form.name = data.data.info.name;
              this.form.alarm_type = data.data.info.alarm_type;
              let noticearray = data.data.info.notice_type.split('#');
              let senderarray = data.data.info.sender_type.split('#');
              if (noticearray.length > 0) {
                for (let i = 0; i < noticearray.length; i++) {
                  noticearray[i] = this.$options.filters['formatNotice'](noticearray[i])
                }
              }
              if (senderarray.length > 0) {
                for (let i = 0; i < senderarray.length; i++) {
                  senderarray[i] = this.$options.filters['formataSender'](senderarray[i])
                }
              }
              this.form.notice_type = noticearray;
              this.form.sender_type = senderarray;
              this.form.alarm_zone = this.$options.filters['formataZone'](data.data.info.alarm_zone);
              this.form.level = this.$options.filters['formataLevel'](data.data.info.level);
              this.form.st = this.$options.filters['formatSt'](data.data.info.st);
              this.form.holiday_push_yn = this.$options.filters['formatHoliday'](data.data.info.holiday_push_yn);
              this.form.content = data.data.info.content;
              this.tableData = data.data.info.devs;
            } else {
              this.$message.error(data.msg);
            }
          });
      }
    },
    //获取数据字典
    getDictionaryList(parent_code) {
      this.$api.dictionary.getDictionaryList({ parent_code: parent_code }).then((data) => {
        if (data.code == 0) {
          if (parent_code == 'BJLX') {
            this.alarm_type = data.data.list;
          } else if (parent_code == 'BJTZLX') {
            this.notice_type = data.data.list;
          } else if (parent_code == 'JSXXRYLX') {
            this.sender_type = data.data.list;
          } else if (parent_code == 'BJJB') {
            this.level = data.data.list;
          } else if (parent_code == 'BJSD') {
            this.alarm_zone = data.data.list;
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
      this.$api.sensor
        .getSensorList({
          dev_name: this.dev_name2,
          st: "",
          use_type: this.use_type2,
          dev_no: this.dev_no2
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
            let obj = {
              val: '全部',
              code: ''
            }
            data.data.list.push(obj)
            this.belong_typelist = data.data.list;
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
      this.form.name = '';
      this.form.id = '';
      this.form.alarm_type = '';
      this.form.startTime = '';
      this.form.endTime = '';
      this.form.notice_type = [];
      this.form.sender_type = [];
      this.form.alarm_zone = '';
      this.form.st = '是';
      this.form.holiday_push_yn = '是';
      this.form.level = 'A';
      this.form.content = '';
      this.multipleSelection = []
      this.tableData = []
    },
    preserve() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          console.log(this.form.id)
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
        "alarms": {
          "id": this.form.id,
          "name": this.form.name,
          "alarm_type": '',
          "notice_type": "",
          "sender_type": "",
          "alarm_zone": "",
          "st": this.form.st == '是' ? 'QY' : 'TY',
          "holiday_push_yn": this.form.holiday_push_yn == '是' ? 'Y' : 'N',
          "level": '',
          "content": this.form.content
        },
        "devids": []
      }
      if(this.form.alarm_zone=='自定义'){
          obj.alarms.startTime = this.form.startTime;
          obj.alarms.endTime = this.form.endTime;
      }
      obj.alarms.alarm_type = this.form.alarm_type;
      obj.alarms.level = this.matching(this.level, this.form.level);
      obj.alarms.alarm_zone = this.matching(this.alarm_zone, this.form.alarm_zone);
      obj.alarms.notice_type = this.matching2(this.notice_type, this.form.notice_type);
      obj.alarms.sender_type = this.matching2(this.sender_type, this.form.sender_type);
      let array = []
      for (let i = 0; i < this.tableData.length; i++) {
        array.push(this.tableData[i].id)
      }
      obj.devids = array
      this.$api.alarm
        .updatealarm({
          alarms: obj.alarms,
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
        "alarms": {
          "name": this.form.name,
          "alarm_type": '',
          "notice_type": "",
          "sender_type": "",
          "alarm_zone": "",
          "st": this.form.st == '是' ? 'QY' : 'TY',
          "holiday_push_yn": this.form.holiday_push_yn == '是' ? 'Y' : 'N',
          "level": "",
          "content": this.form.content,
          "cust_zone_time":this.form.startTime+'#'+this.form.endTime
        },
        "devids": []
      }
      obj.alarms.alarm_type = this.form.alarm_type;
      obj.alarms.level = this.matching(this.level, this.form.level);
      obj.alarms.alarm_zone = this.matching(this.alarm_zone, this.form.alarm_zone);
      obj.alarms.notice_type = this.matching2(this.notice_type, this.form.notice_type);
      obj.alarms.sender_type = this.matching2(this.sender_type, this.form.sender_type);
      let array = []
      for (let i = 0; i < this.tableData.length; i++) {
        array.push(this.tableData[i].id)
      }
      obj.devids = array
      this.$api.alarm
        .addalarm(obj)
        .then((data) => {
          if (data.code == 0) {
            this.visible = false;
            this.$emit('updata')
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
        .el-form-item__content {
          .el-col-11 {
            width: 35.833333%;
            .el-form-item {
              margin-bottom: 0rem;
            }
          }
          .el-date-editor {
            width: 1.2rem !important;
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