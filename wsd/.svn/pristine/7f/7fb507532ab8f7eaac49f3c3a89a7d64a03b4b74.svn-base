<template>
  <el-dialog class="room" :title="title" :visible.sync="visible" @close="closebox" :width="width" :top="top" :modal="modal" append-to-body :close-on-press-escape="false" destroy-on-close :close-on-click-modal="false" :show-close="false">
    <div class="roombutton">
      <el-button size="mini" type="primary" @click="Keepequipment">保存</el-button>
      <el-button size="mini" @click="closeDialog">取消</el-button>
    </div>
    <div class="roomcontent">
      <p>基本信息</p>
      <el-form ref="form" :model="form" label-width="130px" :rules="rules">
        <el-form-item class="box" label="厂家:" prop="manufacturer">
          <el-input class="win50" size="mini" v-model.trim="form.manufacturer"></el-input>
        </el-form-item>
        <el-form-item class="box" label="mac地址:" prop="dev_mac">
          <el-input class="win50" size="mini" v-model.trim="form.dev_mac"></el-input>
        </el-form-item>
        <el-form-item class="box" label="名称:" prop="dev_name">
          <el-input class="win50" size="mini" v-model.trim="form.dev_name"></el-input>
        </el-form-item>
        <el-form-item class="box" label="编号:" prop="dev_no">
          <el-input class="win50" size="mini" v-model.trim="form.dev_no"></el-input>
        </el-form-item>
        <el-form-item class="box" label="类型:" prop="belong_type">
          <el-select v-model="form.belong_type" class="win50" placeholder="请选择" size="mini" @change="belongtype">
            <el-option v-for="(item, index) in stist" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box" label="用途:" prop="use_type">
          <el-select v-model="form.use_type" class="win50" placeholder="请选择" size="mini">
            <el-option v-for="(item, index) in belong_typelist" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box fl" label="温度阈值:" required v-if="belong!='SD'">
          <el-col :span="12">
            <el-form-item prop="temp_up">
              上限
              <el-input-number size="mini" v-model.trim="form.temp_up" :min="form.temp_low" :max="1000" label="上限"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="temp_low">
              下限
              <el-input-number size="mini" v-model.trim="form.temp_low" :min="-274" :max="form.temp_up" label="下限"></el-input-number>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item class="box fl" label="湿度阈值:" required v-if="belong!='WD'">
          <el-col :span="12">
            <el-form-item prop="hum_up">
              上限
              <el-input-number size="mini" v-model.trim="form.hum_up" :min="form.hum_low" :max="1000" label="上限"></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="hum_low">
              下限
              <el-input-number size="mini" v-model.trim="form.hum_low" :min="-274" :max="form.hum_up" label="下限"></el-input-number>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item class="box" label="采集机制:">
          <el-input class="win50" size="mini" v-model.trim="form.acqu_freq1" placeholder="min/次"></el-input>
        </el-form-item>
        <el-form-item class="box" label="应急采集机制1:">
          <el-input class="win50" size="mini" v-model.trim="form.acqu_freq2" placeholder="min/次"></el-input>
        </el-form-item>
        <el-form-item class="box" label="应急采集机制2:">
          <el-input class="win50" size="mini" v-model.trim="form.acqu_freq3" placeholder="min/次"></el-input>
        </el-form-item>
        <el-form-item class="box" label="采集时间:" prop="time">
          <el-date-picker size="mini" v-model.trim="form.time" @change="gettime" type="datetimerange" value-format="yyyy-MM-dd HH:mm:ss" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间"></el-date-picker>
        </el-form-item>
        <el-form-item class="box" label="负责人:" prop="duty_name">
          <el-select v-model="form.duty_login_name" class="win50" placeholder="请选择" size="mini" @change="changeduty_name">
            <el-option v-for="(item, index) in personchargelist" :key="index" :label="item.user_name" :value="item.login_name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box" label="联系方式:">
          <el-input class="win50" size="mini" maxlength="11" v-model.trim="form.duty_cont_phon" disabled></el-input>
        </el-form-item>
        <el-form-item class="box" label="邮箱:">
          <el-input class="win50" size="mini" v-model.trim="form.duty_email" disabled></el-input>
        </el-form-item>
        <el-form-item class="box" label="校对周期:" prop="check_cycle">
          <el-input class="win50" size="mini" v-model.trim="form.check_cycle"></el-input>
        </el-form-item>
        <el-form-item class="box" label="运维人员:" prop="operator_name">
          <el-select v-model="form.operator_login_name" class="win50" placeholder="请选择" size="mini" @change="changeoperator_name">
            <el-option v-for="(item, index) in personchargelist" :key="index" :label="item.user_name" :value="item.login_name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box" label="联系方式:">
          <el-input class="win50" size="mini" v-model.trim="form.oper_cont_phon" disabled></el-input>
        </el-form-item>
        <el-form-item class="box" label="邮箱:">
          <el-input class="win50" size="mini" v-model.trim="form.oper_email" disabled></el-input>
        </el-form-item>
        <el-form-item class="box" label="备注:">
          <el-input class="win50" type="textarea" v-model.trim="form.remark"></el-input>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>
<script>
export default {
  props: {
    show: {
      type: Boolean,
      default: false
    },
    title: {
      //标题
      type: String,
      default: ''
    },
    width: {
      type: [Number, String],
      default: '800px'
    },
    top: {
      type: String,
      default: '15vh'
    },
    modal: {
      type: Boolean,
      default: true
    },
    height: {
      type: [Number, String],
      default: 'auto'
    }
  },
  data() {
    return {
      form: {
        st: 'XZ',
        dev_name: '',
        dev_no: '',
        belong_type: '',
        use_type: '',
        temp_up: 1,
        temp_low: 0,
        hum_up: 1,
        hum_low: 0,
        dev_mac: '',
        acqu_freq1: '',
        acqu_freq2: '',
        acqu_freq3: '',
        start_time: '',
        end_time: '',
        time: [],
        duty_name: '',
        duty_login_name: '',
        duty_cont_phon: '',
        duty_email: '',
        check_cycle: '',
        operator_name: '',
        operator_login_name: '',
        oper_cont_phon: '',
        oper_email: '',
        remark: '',
        manufacturer: ''
      },
      belong_typelist: [], //类型列表
      stist: [], //状态列表
      rules: {
        manufacturer: [
          { required: true, message: '请输入厂家名称', trigger: 'blur' },
        ],
        dev_mac: [
          { required: true, message: '请输入mac地址', trigger: 'blur' },
        ],
        dev_name: [
          { required: true, message: '请输入仪器名称', trigger: 'blur' },
        ],
        dev_no: [
          { required: true, message: '请输入编号', trigger: 'blur' },
        ],
        belong_type: [
          { required: true, message: '请选择类型', trigger: 'change' }
        ],
        use_type: [
          { required: true, message: '请选择用途', trigger: 'change' }
        ],
        temp_up: [
          { required: true, message: '请输入上限', trigger: 'blur' },
        ],
        temp_low: [
          { required: true, message: '请输入下限', trigger: 'blur' },
        ],
        hum_up: [
          { required: true, message: '请输入上限', trigger: 'blur' },
        ],
        hum_low: [
          { required: true, message: '请输入上限', trigger: 'blur' },
        ],
        time: [
          { required: true, message: '请选择采集时间', trigger: 'change' }
        ],
        duty_login_name: [
          { required: true, message: '请输入负责人', trigger: 'change' },
        ],
        check_cycle: [
          { required: true, message: '请输入校对周期', trigger: 'blur' },
        ],
        operator_login_name: [
          { required: true, message: '请输入运维人员', trigger: 'change' },
        ],
      },
      personchargelist: [],
      belong: ''
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
      }
    }
  },
  created() { },
  mounted() {
    let that = this;
    that.dictionary('SBYT');
    that.dictionary('SBLX');
    that.personcharge();
  },
  methods: {
    changeduty_name(e) {
      let array = this.personchargelist;
      for (let i = 0; i < array.length; i++) {
        if (array[i].login_name == e) {
          this.form.duty_cont_phon = array[i].mobile_phone;
          this.form.duty_email = array[i].email;
          this.form.duty_name = array[i].user_name;
        }
      }
    },
    changeoperator_name(e) {
      let array = this.personchargelist;
      for (let i = 0; i < array.length; i++) {
        if (array[i].login_name == e) {
          this.form.oper_cont_phon = array[i].mobile_phone;
          this.form.oper_email = array[i].email;
          this.form.operator_name = array[i].user_name;
        }
      }
    },
    closebox() {
      this.form = {
        st: 'XZ',
        dev_name: '',
        dev_no: '',
        belong_type: '',
        use_type: '',
        temp_up: 1,
        temp_low: 0,
        hum_up: 1,
        hum_low: 0,
        dev_mac: '',
        acqu_freq1: '',
        acqu_freq2: '',
        acqu_freq3: '',
        start_time: '',
        end_time: '',
        time: [],
        duty_name: '',
        duty_cont_phon: '',
        duty_login_name: '',
        duty_email: '',
        check_cycle: '',
        operator_name: '',
        operator_login_name: '',
        oper_cont_phon: '',
        oper_email: '',
        remark: '',
        manufacturer: ''
      };
      this.belong = '';
      this.$refs['form'].resetFields();
    },
    init(res) {
      this.form = {
        id: res.id,
        st: this.$options.filters['theformatState'](res.st),
        dev_name: res.dev_name,
        dev_no: res.dev_no,
        belong_type: this.$options.filters['theformatType'](res.belong_type),
        use_type: this.$options.filters['theformatUse'](res.use_type),
        temp_up: Number(res.temp_up),
        temp_low: Number(res.temp_low),
        hum_up: Number(res.hum_up),
        hum_low: Number(res.hum_low),
        dev_mac: res.dev_mac,
        acqu_freq1: Number(res.acqu_freq1),
        acqu_freq2: Number(res.acqu_freq2),
        acqu_freq3: Number(res.acqu_freq3),
        start_time: res.start_time ? this.$date.getNowFormatDate(res.start_time) : '',
        end_time: res.end_time ? this.$date.getNowFormatDate(res.end_time) : '',
        time: [res.start_time ? this.$date.getNowFormatDate(res.start_time) : '', res.end_time ? this.$date.getNowFormatDate(res.end_time) : ''],
        duty_name: res.duty_name,
        duty_cont_phon: res.duty_cont_phon,
        duty_login_name: res.duty_login_name,
        duty_email: res.duty_email,
        check_cycle: res.check_cycle,
        operator_name: res.operator_name,
        operator_login_name: res.operator_login_name,
        oper_cont_phon: res.oper_cont_phon,
        oper_email: res.oper_email,
        remark: res.remark,
        manufacturer: res.manufacturer
      };
      this.belong = this.$options.filters['theformatType'](res.belong_type)
    },
    belongtype(e) {
      this.belong = e
    },
    dictionary(parent_code) {
      this.$api.dictionary.getDictionaryList({ parent_code: parent_code }).then(data => {
        if (data.code == 0) {
          if (parent_code == 'SBYT') {
            if (data.data) {
              this.belong_typelist = data.data.list;
            }
          } else if (parent_code == 'SBLX') {
            if (data.data) {
              this.stist = data.data.list;
            }
          }
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    personcharge() {
      this.$api.dictionary.getUserList().then(data => {
        if (data.code == 0) {
          this.personchargelist = data.data.list;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    gettime(e) {
      this.form.time = e;
      this.form.start_time = e[0];
      this.form.end_time = e[1];
    },
    Keepequipment() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          let res = {
            dev_name: this.form.dev_name,
            dev_no: this.form.dev_no,
            belong_type: this.form.belong_type,
            use_type: this.form.use_type,
            dev_mac: this.form.dev_mac,
            acqu_freq1: this.form.acqu_freq1,
            acqu_freq2: this.form.acqu_freq2,
            acqu_freq3: this.form.acqu_freq3,
            start_time: this.form.start_time,
            end_time: this.form.end_time,
            duty_name: this.form.duty_name,
            duty_cont_phon: this.form.duty_cont_phon,
            duty_email: this.form.duty_email,
            check_cycle: this.form.check_cycle,
            operator_name: this.form.operator_name,
            oper_cont_phon: this.form.oper_cont_phon,
            oper_email: this.form.oper_email,
            remark: this.form.remark,
            manufacturer: this.form.manufacturer
          };
          for (let i = 0; i < this.personchargelist.length; i++) {
            if (this.personchargelist[i].user_name == this.form.duty_name) {
              res.duty_login_name = this.personchargelist[i].login_name
            }
            if (this.personchargelist[i].user_name == this.form.operator_name) {
              res.operator_login_name = this.personchargelist[i].login_name
            }
          }
          if (this.belong == 'WD') {
            res.temp_up = this.form.temp_up;
            res.temp_low = this.form.temp_low;
            res.hum_up = '';
            res.hum_low = '';
          } else if (this.belong == 'SD') {
            res.hum_up = this.form.hum_up;
            res.hum_low = this.form.hum_low;
            res.temp_up = '';
            res.temp_low = '';
          } else {
            res.temp_up = this.form.temp_up;
            res.temp_low = this.form.temp_low;
            res.hum_up = this.form.hum_up;
            res.hum_low = this.form.hum_low;
          }
          if (this.form.id) {
            res.st = this.form.st;
            res.id = this.form.id;
            this.updetafacility(res)
          } else {
            res.st = 'XZ';
            this.addfacility(res)
          }
        } else {
          return false;
        }
      });
    },
    addfacility(res) {
      this.$api.sensor.addSensor(res).then(data => {
        if (data.code == 0) {
          this.$message({
            message: data.msg,
            type: 'success'
          });
          this.$emit('close');
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    updetafacility(res) {
      this.$api.sensor.updatefacility(res).then(data => {
        if (data.code == 0) {
          this.$message({
            message: data.msg,
            type: 'success'
          });
          this.$emit('close');
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    closeDialog() {
      this.$emit('close');
    },
  }
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
  .roomcontent {
    border: 1px solid #f0f0f0;
    opacity: 1;
    border-radius: 0.09rem;
    p {
      height: 0.4rem;
      line-height: 0.4rem;
      padding-left: 0.2rem;
    }
    .el-form {
      overflow: hidden;
      .box {
        display: inline-block;
        width: 50%;
        margin-bottom: 0.12rem;
        .win50 {
          width: 90%;
        }
        .el-date-editor--datetimerange.el-input,
        .el-date-editor--datetimerange.el-input__inner {
          width: 90%;
        }
        ::v-deep .el-range-input {
          width: 58%;
        }
        .el-input-number--mini {
          width: 63%;
        }
      }
    }
  }
}
</style>
