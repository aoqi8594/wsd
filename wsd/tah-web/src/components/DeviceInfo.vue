<template>
  <el-dialog class="room" :visible.sync="visible" :width="width" :top="top" :modal="modal" append-to-body :close-on-press-escape="false" destroy-on-close :close-on-click-modal="false" :show-close="false">
    <div class="devicetab">
      <span :class="tabfalg=='1'?'active':''" @click="tab(1)">温湿度详情</span>
      <span :class="tabfalg=='2'?'active':''" @click="tab(2)">校对记录</span>
    </div>
    <i class="el-icon-close" @click="closeDialog"></i>
    <div class="roomcontent" v-show="tabfalg==1">
      <p>基本信息</p>
      <el-form ref="form" :model="form" label-width="130px">
        <el-form-item class="box" label="厂家:">
          <el-input class="win50" size="mini" disabled v-model="form.manufacturer"></el-input>
        </el-form-item>
        <el-form-item class="box" label="mac地址:">
          <el-input class="win50" size="mini" disabled v-model="form.dev_mac"></el-input>
        </el-form-item>
        <el-form-item class="box" label="名称:">
          <el-input class="win50" size="mini" disabled v-model="form.dev_name"></el-input>
        </el-form-item>
        <el-form-item class="box" label="编号:">
          <el-input class="win50" size="mini" disabled v-model="form.dev_no"></el-input>
        </el-form-item>
        <el-form-item class="box" label="类型:">
          <el-select v-model="form.belong_type" class="win50" placeholder="请选择" size="mini" disabled>
            <el-option v-for="(item, index) in stist" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box" label="用途:">
          <el-select v-model="form.use_type" class="win50" placeholder="请选择" size="mini" disabled>
            <el-option v-for="(item, index) in belong_typelist" :key="index" :label="item.val" :value="item.code"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box" label="温度阈值:">
          上限
          <el-input-number size="mini" :disabled="true" v-model="form.temp_up" :min="1" :max="1000" label="描述文字"></el-input-number>
          下限
          <el-input-number size="mini" :disabled="true" v-model="form.temp_low" :min="1" :max="1000" label="描述文字"></el-input-number>
        </el-form-item>
        <el-form-item class="box" label="湿度阈值:">
          上限
          <el-input-number size="mini" :disabled="true" v-model="form.hum_up" :min="1" :max="1000" label="描述文字"></el-input-number>
          下限
          <el-input-number size="mini" :disabled="true" v-model="form.hum_low" :min="1" :max="1000" label="描述文字"></el-input-number>
        </el-form-item>
        <el-form-item class="box" label="采集机制:">
          <el-input class="win50" disabled size="mini" v-model="form.acqu_freq1" placeholder="min/次"></el-input>
        </el-form-item>
        <el-form-item class="box" label="应急采集机制1:">
          <el-input class="win50" disabled size="mini" v-model="form.acqu_freq2" placeholder="min/次"></el-input>
        </el-form-item>
        <el-form-item class="box" label="应急采集机制2:">
          <el-input class="win50" disabled size="mini" v-model="form.acqu_freq3" placeholder="min/次"></el-input>
        </el-form-item>
        <el-form-item class="box" label="采集时间:">
          <el-date-picker size="mini" v-model="form.time" @change="gettime" type="datetimerange" value-format="yyyy-MM-dd HH:mm:ss" range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" disabled></el-date-picker>
        </el-form-item>
        <el-form-item class="box" label="负责人:">
          <el-input class="win50" disabled size="mini" v-model="form.duty_name"></el-input>
        </el-form-item>
        <el-form-item class="box" label="联系方式:">
          <el-input class="win50" disabled size="mini" v-model="form.duty_cont_phon"></el-input>
        </el-form-item>
        <el-form-item class="box" label="邮箱:">
          <el-input class="win50" disabled size="mini" v-model="form.duty_email"></el-input>
        </el-form-item>
        <el-form-item class="box" label="校对周期:">
          <el-input class="win50" disabled size="mini" v-model="form.check_cycle"></el-input>
        </el-form-item>
        <el-form-item class="box" label="运维人员:">
          <el-input class="win50" disabled size="mini" v-model="form.operator_name"></el-input>
        </el-form-item>
        <el-form-item class="box" label="联系方式:">
          <el-input class="win50" disabled size="mini" v-model="form.oper_cont_phon"></el-input>
        </el-form-item>
        <el-form-item class="box" label="邮箱:">
          <el-input class="win50" disabled size="mini" v-model="form.oper_email"></el-input>
        </el-form-item>
        <el-form-item class="box" label="备注:">
          <el-input class="win50" disabled type="textarea" v-model="form.remark"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="proofreadrecord" v-show="tabfalg==2">
      <el-table ref="multipleTable" :data="tableData" border tooltip-effect="dark">
        <el-table-column type="index" label="序号" fixed width="50" align="center"></el-table-column>
        <el-table-column prop="w1" align="center" width="90" label="校对温度1"></el-table-column>
        <el-table-column prop="s1" align="center" width="90" label="校对湿度1"></el-table-column>
        <el-table-column prop="w2" align="center" width="90" label="校对温度2"></el-table-column>
        <el-table-column prop="s2" align="center" width="90" label="校对湿度2"></el-table-column>
        <el-table-column prop="w3" align="center" width="90" label="校对温度3"></el-table-column>
        <el-table-column prop="s3" align="center" width="90" label="校对湿度3"></el-table-column>
        <el-table-column prop="w4" align="center" width="90" label="校对温度4"></el-table-column>
        <el-table-column prop="s4" align="center" width="90" label="校对湿度4"></el-table-column>
        <el-table-column prop="w5" align="center" width="90" label="校对温度5"></el-table-column>
        <el-table-column prop="s5" align="center" width="90" label="校对湿度5"></el-table-column>
        <el-table-column prop="w6" align="center" width="90" label="校对温度6"></el-table-column>
        <el-table-column prop="s6" align="center" width="90" label="校对湿度6"></el-table-column>
        <el-table-column prop="w7" align="center" width="90" label="校对温度7"></el-table-column>
        <el-table-column prop="s7" align="center" width="90" label="校对湿度7"></el-table-column>
        <el-table-column prop="w8" align="center" width="90" label="校对温度8"></el-table-column>
        <el-table-column prop="s8" align="center" width="90" label="校对湿度8"></el-table-column>
        <el-table-column prop="belong_type" label="校对人" width="80" align="center"></el-table-column>
        <el-table-column prop="use_type" label="联系电话" width="80" align="center"></el-table-column>
        <el-table-column prop="create_time" align="center" width="140" label="校对时间"></el-table-column>
      </el-table>
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
    id: {
      type: [Number, String],
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
      floorList: [],
      form: {
        st: '',
        dev_name: '',
        dev_no: '',
        belong_type: '',
        use_type: '',
        temp_up: '',
        temp_low: '',
        hum_up: '',
        hum_low: '',
        dev_mac: '',
        acqu_freq1: '',
        acqu_freq2: '',
        acqu_freq3: '',
        start_time: '',
        end_time: '',
        time: '',
        duty_name: '',
        duty_cont_phon: '',
        duty_email: '',
        check_cycle: '',
        operator_name: '',
        oper_cont_phon: '',
        oper_email: '',
        remark: '',
        manufacturer: ''
      },
      tableData: [],
      belong_typelist: [], //类型列表
      stist: [], //状态列表
      tabfalg: 1//切换状态
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
  mounted() { },
  methods: {
    dictionary(id) {
      let that = this;
      that.$api.sensor.getSensordetails({ id: that.id }).then(data => {
        if (data.code == 0) {
          let res = data.data.equipmentInfo[0]
          res.start_time = this.$date.getNowFormatDate(
            res.start_time
          );
          res.end_time = this.$date.getNowFormatDate(
            res.end_time
          );
          if (res.belong_type == "SD") {
            res.belong_type = "湿度";
          } else if (res.belong_type == "WD") {
            res.belong_type = "温度";
          } else if (res.belong_type == "WSD") {
            res.belong_type = "温湿度";
          }
          if (res.use_type == "PYX") {
            res.use_type = "培养箱";
          } else if (res.use_type == "BX") {
            res.use_type = "冰箱";
          } else if (res.use_type == "GYG") {
            res.use_type = "高压锅";
          }
          res.time = [res.start_time, res.end_time]
          that.form = res;
        } else {
          this.$message.error(data.msg);
        }
      });
      this.getListByDevid(id);
    },
    getListByDevid(id) {
      let that = this;
      that.$api.proofread.getListByDevid({ dev_id: id }).then(data => {
        if (data.code == 0) {
          if (data.data.list.length > 0) {
            let array = data.data.list;
            for (let i = 0; i < array.length; i++) {
              array[i].create_time = this.$date.getNowFormatDate(array[i].create_time).substring(0, 10);
              array[i].temp = array[i].check_temp.split(",");
              array[i].hum = array[i].check_temp.split(",");
              array[i].w1 = array[i].temp[0] != 'null' ? array[i].temp[0] : '';
              array[i].w2 = array[i].temp[1] != 'null' ? array[i].temp[1] : '';
              array[i].w3 = array[i].temp[2] != 'null' ? array[i].temp[2] : '';
              array[i].w4 = array[i].temp[3] != 'null' ? array[i].temp[3] : '';
              array[i].w5 = array[i].temp[4] != 'null' ? array[i].temp[4] : '';
              array[i].w6 = array[i].temp[5] != 'null' ? array[i].temp[5] : '';
              array[i].w7 = array[i].temp[6] != 'null' ? array[i].temp[6] : '';
              array[i].w8 = array[i].temp[7] != 'null' ? array[i].temp[7] : '';
              array[i].s1 = array[i].hum[0] != 'null' ? array[i].hum[0] : '';
              array[i].s2 = array[i].hum[1] != 'null' ? array[i].hum[1] : '';
              array[i].s3 = array[i].hum[2] != 'null' ? array[i].hum[2] : '';
              array[i].s4 = array[i].hum[3] != 'null' ? array[i].hum[3] : '';
              array[i].s5 = array[i].hum[4] != 'null' ? array[i].hum[4] : '';
              array[i].s6 = array[i].hum[5] != 'null' ? array[i].hum[5] : '';
              array[i].s7 = array[i].hum[6] != 'null' ? array[i].hum[6] : '';
              array[i].s8 = array[i].hum[7] != 'null' ? array[i].hum[7] : '';
            }
            this.tableData = array
          }
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
    closeDialog() {
      this.$emit('close');
    },
    //切换状态
    tab(e) {
      if (this.tabfalg != e) {
        this.tabfalg = e
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.room {
  ::v-deep .el-dialog__header {
    padding-bottom: 20px;
  }
  .devicetab {
    position: absolute;
    top: 0;
    height: 0.4rem;
    line-height: 0.4rem;
    span {
      margin-right: 0.2rem;
      display: inline-block;
    }
    .active {
      color: #1a90fd;
      position: relative;
      &::before {
        content: "";
        position: absolute;
        left: 20%;
        bottom: -0.04rem;
        background-color: #1a90fd;
        width: 60%;
        height: 2px;
      }
    }
  }
  .el-icon-close {
    position: absolute;
    top: 0.1rem;
    right: 10px;
    font-size: 0.2rem;
  }
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
      .box {
        display: inline-block;
        width: 50%;
        margin-bottom: 0.06rem;
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
      }
    }
  }
}
</style>
