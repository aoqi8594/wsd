<template>
  <div class="car">
    <el-row>
      <el-col :span="24">
        <div class="carname">
          <span class="">导出设置</span>
          <!-- <el-input placeholder="请输入报警名称" size="mini" clearable v-model="input2">
          </el-input>
          <el-button type="primary" size="mini" @click="getexportlist">查询</el-button> -->
          <el-button class="export fr hu" @click="deleteset" :disabled='disable' size="mini">删除</el-button>
          <el-button class="export fr" type="primary" size="mini" @click="warnadd">新增</el-button>
        </div>
        <div class="historybox">
          <el-table ref="multipleTable" :data="tableData" height="76vh" border tooltip-effect="dark" style="width: 100%;" @selection-change="handleSelectionChange">
            <el-table-column align="center" type="selection" width="55"></el-table-column>
            <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
            <el-table-column align="center" prop="val_type" label="取值方式">
              <template slot-scope="scope">
                {{scope.row.val_type|valtype}}
              </template>
            </el-table-column>
            <el-table-column align="center" prop="data_round" label="数据修约">
              <template slot-scope="scope">
                {{scope.row.data_round|data_round}}
              </template>
            </el-table-column>
            <el-table-column align="center" prop="export_time" label="导出时间"></el-table-column>
            <el-table-column align="center" label="推送人">
              <template slot-scope="scope">
                {{scope.row.sender_type}}
              </template>
            </el-table-column>
            <el-table-column align="center" prop="mark" label="备注"></el-table-column>
            <el-table-column align="center" label="操作">
              <template slot-scope="scope">
                <el-button type="warning" size="mini" @click="handleEditor(scope.row)">编辑</el-button>
                <el-button type="success" size="mini" v-if="scope.row.isExport" @click="rowClick(scope.row.id)">导出</el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="totalPage > 0" :total="totalPage" :page.sync="pageIndex" :limit.sync="pageLimt" @pagination="getexportlist" />
        </div>
      </el-col>
    </el-row>
    <Exporsetation ref="exporsetation" title="导出设置" width="10.6rem" @updata="getexportlist"></Exporsetation>
  </div>
</template>
<script>
import Exporsetation from "@/components/Exporsetation.vue";
import Pagination from "@/components/Pagination";
export default {
  components: { Pagination, Exporsetation },
  data() {
    return {
      input2: '',
      tableData: [],
      multipleSelection: "",
      pageIndex: 1,
      pageLimt: 20,
      totalPage: 0,
      disable: true
    };
  },
  mounted() {
    let that = this;
    that.getexportlist();
  },
  methods: {
    rowClick(id) {
      this.$axios({
        url: window.SITE_CONFIG.javaUrl + '/node/mapping/path',
        method: "get",
        params: { exportId: id },
        responseType: 'blob',
      }).then(res => {
        const blob = new Blob([res.data], { type: 'application/vnd.ms-excel' });//处理文档流
        //获取heads中的filename文件名
        let temp = res.headers["content-disposition"].split(";")[1].split("filename=")[1];
        //对文件名乱码转义--【Node.js】使用iconv-lite解决中文乱码
        let iconv = require('iconv-lite');
        iconv.skipDecodeWarning = true;//忽略警告
        let fileName = iconv.decode(temp, 'utf-8');
        const elink = document.createElement('a');
        elink.download = fileName;
        elink.style.display = 'none';
        elink.href = URL.createObjectURL(blob);
        document.body.appendChild(elink);
        elink.click();
        URL.revokeObjectURL(elink.href); // 释放URL 对象
        document.body.removeChild(elink);
      })
    },
    //获取导出设置列表
    getexportlist() {
      this.$api.exporset.getexportlist({ page: this.pageIndex, limit: this.pageLimt, }).then((data) => {
        if (data.code == 0) {
          this.totalPage = data.data.paging.total;
          let array = data.data.list;
          for (let i = 0; i < array.length; i++) {
            var stringResult = array[i].sender_type.split('#');
            if (stringResult) {
              for (let j = 0; j < stringResult.length; j++) {
                stringResult[j] == 'ZJFZR'
                if (stringResult[j] == 'ZJFZR') {
                  stringResult[j] = '直接负责人'
                } else if (stringResult[j] == 'YWRY') {
                  stringResult[j] = '运维人员'
                }
              }
            }
            array[i].sender_type = stringResult.join(',')
          }
          this.tableData = array;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //点击添加按钮
    warnadd() {
      this.$refs.exporsetation.drawLine('', 'add');
    },
    //勾选列表设置
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if (val.length > 0) {
        this.disable = false
      } else {
        this.disable = true
      }
    },
    //点击列表
    handleEditor(row) {
      this.$refs.exporsetation.drawLine(row.id, 'dditor');
    },
    //删除导出设置
    deleteset() {
      let array = this.multipleSelection;
      if (array.length == 0) {
        this.$message({
          message: '请先选择要删除的设置',
          type: 'warning'
        });
        return false;
      } else {
        let ids = [];
        for (let i = 0; i < array.length; i++) {
          ids.push(array[i].id)
        }
        this.$confirm('是否确认删除设置?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$api.exporset.deleteexportset({ ids: ids }).then((data) => {
            if (data.code == 0) {
              this.$message({
                message: data.msg,
                type: 'success'
              });
              this.getexportlist();
              this.multipleSelection = []
            } else {
              this.$message.error(data.msg);
            }
          });
        }).catch(() => {
          return false;
        });
      }
    }
  },
};
</script>

<style lang="scss" scoped>
.car {
  height: calc(100vh - 50px);
  .el-row {
    height: 100%;
    margin-bottom: 20px;
    .el-col {
      height: 100%;
      .carname {
        height: 0.4rem;
        line-height: 0.4rem;
        padding-left: 3%;
        .export {
          margin-top: 0.1rem;
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
      }
      .historybox {
        margin: 2%;
        padding: 1%;
        border: 1px solid #ebebeb;
        opacity: 1;
        border-radius: 0.08rem;
      }
    }
  }
}
</style>
