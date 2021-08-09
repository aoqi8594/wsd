<template>
  <div class="car">
    <el-row>
      <el-col :span="24">
        <div class="carname">
          <span class="">校对记录</span>
          <el-input placeholder="温湿度计名称" clearable size="mini" v-model="input"></el-input>
          <el-button type="primary" size="mini" @click="thermometer">查询</el-button>
        </div>
        <div class="historybox">
          <el-table ref="multipleTable" :height="tableHeight" :data="tableData" :row-style="{height: '40px'}" :cell-style="{padding: '0'}" border tooltip-effect="dark" >
            <el-table-column type="selection" fixed align="center" width="40"></el-table-column>
            <el-table-column fixed width="50" align="center" type="index"  label="序号" :index="indexMethod"> </el-table-column>
            <el-table-column prop="dev_name" align="center" width="130" label="名称" show-overflow-tooltip></el-table-column>
            <el-table-column prop="dev_no" align="center" width="130" label="编号" show-overflow-tooltip></el-table-column>
            <el-table-column prop="belong_type" label="类型" width="80" align="center"></el-table-column>
            <el-table-column prop="std_temp" align="center" width="100" label="计量后温度"></el-table-column>
            <el-table-column prop="std_hum" align="center" width="100" label="计量后湿度"></el-table-column>
            <el-table-column prop="remark" align="center" width="140" label="备注" show-overflow-tooltip></el-table-column>
            <el-table-column prop="remark" align="center" width="80" label="校对人" show-overflow-tooltip></el-table-column>
            <el-table-column prop="duty_cont_phon" align="center" width="110" label="联系电话" show-overflow-tooltip></el-table-column>
            <el-table-column prop="check_time" align="center" width="130" label="校对时间" show-overflow-tooltip></el-table-column>
            <el-table-column prop="duty_email" align="center" width="130" label="邮箱" show-overflow-tooltip></el-table-column>
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
          </el-table>
          <pagination v-show="totalPage > 0" :total="totalPage" :page.sync="pageIndex" :limit.sync="pageLimt" @pagination="thermometer" />
        </div>
      </el-col>
    </el-row>
    
  </div>
</template>
<script>
import Pagination from "@/components/Pagination";
export default {
  components: {Pagination },
  data() {
    return {
      input: "",
      tableData: [],
      tableHeight: window.innerHeight - 240,
      pageIndex: 1,
      pageLimt: 20,
      totalPage: 0,
    };
  },
  mounted() {
    let that = this;
    that.thermometer();
  },
  methods: {
    indexMethod(index){
      return (index+1) + ((this.pageIndex-1)*this.pageLimt);
    },
    //查询获取设备列表
    thermometer() {
      this.$api.proofread
        .getList({dev_name:this.input})
        .then((data) => {
          if (data.code == 0) {
            let res = data.data.list;
            for (let i = 0; i < res.length; i++) {
              res[i].check_time = this.$date.getNowFormatDate(res[i].check_time);
              if (res[i].belong_type == "SD") {
                res[i].belong_type = "湿度";
              } else if (res[i].belong_type == "WD") {
                res[i].belong_type = "温度";
              } else if (res[i].belong_type == "WSD") {
                res[i].belong_type = "温湿度";
              }
              var check_temp =  res[i].check_temp?res[i].check_temp.split(','):[];
              var check_hum =  res[i].check_hum?res[i].check_hum.split(','):[];
              if(check_temp.length>0){
                 res[i].w1=check_temp[0]=='null'?'':check_temp[0];
                 res[i].w2=check_temp[1]=='null'?'':check_temp[1];
                 res[i].w3=check_temp[2]=='null'?'':check_temp[2];
                 res[i].w4=check_temp[3]=='null'?'':check_temp[3];
                 res[i].w5=check_temp[4]=='null'?'':check_temp[4];
                 res[i].w6=check_temp[5]=='null'?'':check_temp[5];
                 res[i].w7=check_temp[6]=='null'?'':check_temp[6];
                 res[i].w8=check_temp[7]=='null'?'':check_temp[7];
              }
              if(check_hum.length>0){
                 res[i].s1=check_hum[0]=='null'?'':check_hum[0];
                 res[i].s2=check_hum[1]=='null'?'':check_hum[1];
                 res[i].s3=check_hum[2]=='null'?'':check_hum[2];
                 res[i].s4=check_hum[3]=='null'?'':check_hum[3];
                 res[i].s5=check_hum[4]=='null'?'':check_hum[4];
                 res[i].s6=check_hum[5]=='null'?'':check_hum[5];
                 res[i].s7=check_hum[6]=='null'?'':check_hum[6];
                 res[i].s8=check_hum[7]=='null'?'':check_hum[7];
              }
            }
            this.totalPage = data.data.paging.total;
            this.tableData = res;
          } else {
            this.$message.error(data.msg);
          }
        });
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
</style>
