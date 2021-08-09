<template>
  <div class="light-up">
    <p>监测仪监控</p>
    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%;height:23vh;" @selection-change="handleSelectionChange" @row-click="rowClick">
      <el-table-column prop="dev_no" label="编号"></el-table-column>
      <el-table-column prop="dev_name" label="名称"></el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          {{scope.row.alarm_yn=='N'?'正常':'报警'}}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="联网状态">
        <template slot-scope="scope">
          {{scope.row.net_st=='WLZT_ZX'?'连接':'断开'}}
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  props: {},
  data() {
    return {
      tableData: [],
      multipleSelection: '',
      carid:''
    };
  },
  created() { },
  mounted() { },
  methods: {
    init(data,id) {
      this.carid=id;
      this.tableData = data;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    rowClick(row, event, column) {
       this.$router.push({name:'car',params:{id:this.carid,parent:"cardetails"}})
    },
  },
};
</script>
<style lang="scss" scoped>
.light-up {
  margin-top: 0.14rem;
  border: 1px solid #f0f0f0;
  opacity: 1;
  border-radius: 0.09rem;
  padding: 0.2rem 0;
  .el-table::before{
    height: 0;
  }
  p {
    font-size: 0.14rem;
    font-weight: bold;
    color: #1a1a1a;
    padding: 0 0.2rem 0.1rem;
  }
}
</style>