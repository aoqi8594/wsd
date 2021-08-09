<template>
  <div class="car">
    <el-row>
      <el-col :span="24">
        <div class="carname">
          <span class="">导出记录</span>
          <el-date-picker v-model="value1" format="yyyy-MM-dd" value-format="yyyy-MM-dd" size="mini" type="datetime" placeholder="选择导出日期"></el-date-picker>
          <el-button type="primary" size="mini" @click="thermometer">查询</el-button>
        </div>
        <div class="historybox">
          <el-table ref="multipleTable" :height="tableHeight" :data="tableData" :row-style="{height: '40px'}" :cell-style="{padding: '0'}" border tooltip-effect="dark">
            <el-table-column type="selection" fixed align="center" width="50"></el-table-column>
            <el-table-column fixed width="50" align="center" type="index" label="序号" :index="indexMethod"> </el-table-column>
            <el-table-column prop="report_name" align="center"  label="报表名称" show-overflow-tooltip></el-table-column>
            <el-table-column  align="center"  label="导出方式" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.export_type=='YJTS'?'邮件推送':'直接导出'}}
              </template>
            </el-table-column>
            <el-table-column prop="create_time" label="导出时间"  align="center"></el-table-column>
            <el-table-column prop="creator_lname" align="center"  label="操作人"></el-table-column>
            <el-table-column align="center"  label="附件">
              <template slot-scope="scope">
                 <el-button size="mini" type="primary" v-if="scope.row.export_type=='YJTS'" @click="thermometer"><a :href='scope.row.file_path' download=""  title="下载">下载</a></el-button>
              </template>
            </el-table-column>
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
  components: { Pagination },
  data() {
    return {
      value1: "",
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
    indexMethod(index) {
      return (index + 1) + ((this.pageIndex - 1) * this.pageLimt);
    },
    //查询获取设备列表
    thermometer() {
      this.$api.exporset
        .getExportLog({createTime:this.value1, page: this.pageIndex,limit: this.pageLimt})
        .then((data) => {
          if (data.code == 0) {
            this.totalPage=data.data.paging.total;
            let res = data.data.list;
            for (let i = 0; i < res.length; i++) {
              res[i].create_time=this.$date.getNowFormatDate(res[i].create_time)
            }
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
