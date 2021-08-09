<template>
  <div class="car">
    <el-row>
      <el-col :span="24">
        <div class="carname">
          <span class="">报警设置</span>
          <el-input placeholder="请输入报警名称" size="mini" clearable v-model="input2">
          </el-input>
          <el-button type="primary" size="mini" @click="getalarmlist">查询</el-button>
          <el-button class="export fr hu" type="warning" :disabled='disable' @click="apply('TY')" size="mini">停用</el-button>
          <el-button class="export fr" type="success" size="mini" :disabled='disable' @click="apply('QY')">启用</el-button>
          <el-button class="export fr" type="primary" size="mini" @click="warnadd">新增</el-button>
        </div>
        <div class="historybox">
          <el-table ref="multipleTable" height="76vh" :data="tableData" border tooltip-effect="dark" style="width: 100%;" @selection-change="handleSelectionChange">
            <el-table-column  align="center" type="selection" width="55"></el-table-column>
            <el-table-column  align="center" type="index" label="序号" width="50"></el-table-column>
            <el-table-column align="center" prop="name" label="报警名称"></el-table-column>
            <el-table-column align="center" prop="content" label="报警内容"></el-table-column>
            <el-table-column align="center"  label="通知方式" show-overflow-tooltip>
               <template slot-scope="scope">
                 {{scope.row.notice_type2}}
              </template>
            </el-table-column> 
            <el-table-column align="center" prop="level" label="报警级别" width="155">
              <template slot-scope="scope">
                 {{scope.row.level|formataLevel}}
              </template>
            </el-table-column>
            <el-table-column align="center" label="是否启用" width="155">
              <template slot-scope="scope">
                 {{scope.row.st |formatSt}}
              </template>
            </el-table-column>
            <el-table-column align="center" label="操作" width="155">
              <template slot-scope="scope">
                <el-button size="mini" type="warning" @click="handleEditor(scope.row)">编辑</el-button>
                <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                <!-- <i class="iconfont iconqita_huaban1fuben8" style="font-size: .2rem;" @click="handleEditor(scope.row)"></i>
                <i class="iconfont iconqita_huaban1fuben12" style="font-size: .2rem;" @click="handleDelete(scope.row)"></i> -->
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="totalPage > 0" :total="totalPage" :page.sync="pageIndex" :limit.sync="pageLimt" @pagination="getalarmlist" />
        </div>
      </el-col>
    </el-row>
    <Allocation ref="allocation" title="配置详情" width="10.6rem" @updata="getalarmlist"></Allocation>
  </div>
</template>
<script>
import Allocation from "@/components/Allocation.vue";
import Pagination from "@/components/Pagination";
export default {
  components: { Allocation,Pagination },
  data() {
    return {
      input2: '',
      tableData: [],
      multipleSelection: "",
      dialogopen: false,
       pageIndex: 1,
      pageLimt: 20,
      totalPage: 0,
      disable:true
    };
  },
  mounted() {
    let that = this;
    that.getalarmlist();
  },
  methods: {
    getalarmlist() {
      this.$api.alarm.getalarm({name:this.input2, page: this.pageIndex, limit: this.pageLimt,}).then((data) => {
        if (data.code == 0) {
          this.totalPage = data.data.paging.total;
          let array=data.data.pzlist;
          for (let i = 0; i < array.length; i++) {
            let noticearray= array[i].notice_type.split('#')
            if (noticearray.length > 0) {
              let notice_type2=[];
              for (let j = 0; j < noticearray.length; j++) {
                notice_type2.push(this.$options.filters['formatNotice'](noticearray[j]))
              }
              array[i].notice_type2= notice_type2.join("、")
            }else{
              array[i].notice_type2= ''
            }
          }
          this.tableData = array;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    warnadd() {
      this.$refs.allocation.drawLine('','add');
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if (val.length > 0) {
        this.disable = false
      }else{
        this.disable = true
      }
    },
    handleEditor(row){
      this.$refs.allocation.drawLine(row.id,'dditor');
    },
    handleDelete(row){
      this.$confirm('是否确认移除该报警设置?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
           this.$api.alarm.deletealarm({ids:[row.id]}).then((data) => {
            if (data.code == 0) {
               this.$message({
                  message: data.msg,
                  type: 'success'
                });
                this.getalarmlist();
            } else {
              this.$message.error(data.msg);
            }
          });
        }).catch(() => {
          return false;
        });
        
    },
    apply(st){
      let array=this.multipleSelection;
      let ids=[];
      for (let i = 0; i < array.length; i++) {
        ids.push(array[i].id)
      }
      this.$confirm('是否确认此操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
           this.$api.alarm.updatealarmstate({ids:ids,st:st}).then((data) => {
            if (data.code == 0) {
               this.$message({
                  message: data.msg,
                  type: 'success'
                });
                this.getalarmlist();
            } else {
              this.$message.error(data.msg);
            }
          });
        }).catch(() => {
          return false;
        });
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
