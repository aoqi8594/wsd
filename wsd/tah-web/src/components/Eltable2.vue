<template>
  <div class="light-up">
    <p>实时监测回传</p>
    <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%;height:23vh;" @row-click="rowClick" @selection-change="handleSelectionChange">
      <el-table-column prop="dev_name" label="名称" show-overflow-tooltip></el-table-column>
      <el-table-column label="温度">
        <template slot-scope="scope">
          <span v-if="scope.row.temperature">{{scope.row.temperature}}℃</span>
        </template>
      </el-table-column>
      <el-table-column label="湿度">
        <template slot-scope="scope">
          <span v-if="scope.row.temperature">{{scope.row.humidity}}%RH</span>
        </template>
      </el-table-column>
      <el-table-column prop="create_time" label="时间" show-overflow-tooltip></el-table-column>
    </el-table>
  </div>
</template>
<script>
export default {
  props: {},
  data() {
    return {
      tableData: [],
      multipleSelection: '' 
    };
  },
  created() { },
  mounted() {},
  methods: {
    init(data) {
      for (let i = 0; i < data.length; i++) {
        data[i].humidity=data[i].humidity?parseFloat(data[i].humidity).toFixed(2):'';
        data[i].temperature=data[i].temperature?parseFloat(data[i].temperature).toFixed(2):'';
        data[i].create_time=data[i].create_time?this.$date.getNowFormatDate(data[i].create_time):'';
      }
      this.tableData = data;
    }, 
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    rowClick(row, event, column) {
      this.$router.push({name:'Historicaldata',params:{dev_no:row.dev_no}})
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