<template>
<section >
  <el-table :data="pageData" border style="width: 100%">
    <el-table-column prop="date" label="日期" width="180"></el-table-column>
    <el-table-column prop="name" label="姓名" width="180"></el-table-column>
    <el-table-column prop="address" label="地址"></el-table-column>
  </el-table>
  <el-pagination layout="total, sizes, prev, pager, next, jumper"
                 @current-change="handleCurrentChange"
                 @size-change="handleSizeChange"
                 :page-sizes="[2,10, 20, 50]"
                 :page-size="pageSize"
                 :total="tableData.length" style="float:right;" >
  </el-pagination >
</section >
</template>

<script>
export default {
    data() {
      return {
        currentPage: 1,
        pageSize: 2,
        allData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '李小龙',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '张麻子',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '王二小',
          address: '上海市普陀区金沙江路 1516 弄'
        }]
      }
    },
    computed: {
      tableData: function() {
        return this.allData;
      },
      pageData: function() {
        return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
      }
    },
    methods: {
      handleCurrentChange(val) {
        this.currentPage = val
      },
      handleSizeChange(val) {
        this.currentPage = 1
        this.pageSize = val
      }
    }
  }
</script>
