<template>
  <div class="car">
    <el-row>
      <el-col :span="24">
        <div class="carname">
          <span class="">车载管理</span>
          <el-input placeholder="车牌号" size="mini" clearable v-model="car_no"></el-input>
          <el-input placeholder="驾驶人" size="mini" clearable v-model="driver_name"></el-input>
          <el-button type="primary" size="mini" @click="query">查询</el-button>
          <el-button class="export fr hu" :disabled='disable' size="mini" @click="removevehicle">移除</el-button>
          <el-button class="export fr" type="primary" size="mini" @click="addOrUpdate()">新增</el-button>
        </div>
        <div class="historybox">
          <el-table ref="multipleTable" :span-method="objectSpanMethod" :data="tableData" height="78vh" :row-style="{height: '40px'}" :cell-style="{padding: '0'}" border tooltip-effect="dark" style="width: 100%;" @selection-change="handleSelectionChange">
            <el-table-column type="selection" :selectable="checkSelectable" align="center" width="50"></el-table-column>
            <el-table-column prop="sequence" align="center" label="序号" width="50"></el-table-column>
            <el-table-column prop="car_no" align="center" label="车牌号" width="110"></el-table-column>
            <el-table-column prop="st" align="center" label="状态" width="80">
              <template slot-scope="scope">
                {{scope.row.st=='ZXZ'?'执行中':'已完成'}}
              </template> 
            </el-table-column>
            <el-table-column prop="driver_name" align="center" label="驾驶员" width="100"></el-table-column>
            <el-table-column prop="contact_phone" align="center" label="联系电话" width="125"></el-table-column>
            <el-table-column prop="start_place" align="center" label="出发点" show-overflow-tooltip></el-table-column>
            <el-table-column prop="end_place" align="center" label="目的地" show-overflow-tooltip></el-table-column>
            <el-table-column prop="bxname" align="center" label="冰箱名称" width="155"></el-table-column>
            <el-table-column prop="jname" align="center" label="计名称"></el-table-column>
            <el-table-column prop="jcode" align="center" label="计编号" width="155"></el-table-column>
            <el-table-column prop="fzr" align="center" label="负责人" width="155"></el-table-column>
            <el-table-column prop="lxdh" align="center" label="联系电话" width="120"></el-table-column>
            <el-table-column align="center" label=" 操作">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" v-if="scope.row.st=='ZXZ'" @click="handleEdit(scope.row)">完成</el-button>
                <el-button type="warning" size="mini" v-if="scope.row.st=='ZXZ'" @click="rowClick(scope.row,false)">编辑</el-button>
                <el-button type="success" size="mini" v-if="scope.row.st!='ZXZ'" @click="rowClick(scope.row,true)">查看</el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination v-show="totalPage > 0" :total="totalPage" :page.sync="pageIndex" :limit.sync="pageLimt" @pagination="thermometer" />
        </div>
      </el-col>
    </el-row>
    <Newvehicle ref="addorupdate" :show.sync="dialogopen" height="6.26rem" width="10.6rem" @close="closeupdeata"></Newvehicle>
  </div>
</template> 
<script>
import Newvehicle from "@/components/Newvehicle.vue";
import Pagination from "@/components/Pagination";
export default {
  components: { Newvehicle, Pagination },
  data() {
    return {
      input: "",
      tableData: [],
      multipleSelection: [],//选中的列表数据
      dialogopen: false,
      typeNameArr: [],
      typeNamePos: 0,
      storeArr: [],
      storePos: 0,
      car_no: '',
      driver_name: '',
      pageIndex: 1,
      pageLimt: 20,
      totalPage: 0,
      disable: true
    };
  },
  mounted() {
    let that = this;
    that.thermometer();//查询获取车辆列表
  },
  methods: {
    // 禁止选中
    checkSelectable(row) {
      return row.st !== "ZXZ";
    },
    handleEdit(row) {
      this.$api.vehicleManagement.updateCarRunState({ id: row.id, st: "YWC" }).then(data => {
        if (data.code == 0) {
          this.$message({
            message: data.msg,
            type: 'success'
          });
          this.thermometer();//更新列表
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    query() {
      this.thermometer();
    },
    addOrUpdate(row,flag) {
      this.dialogopen = true
      this.$nextTick(() => {
        this.$refs.addorupdate.init(row,flag)
      })
    },
    rowClick(row,flag) {
      this.addOrUpdate(row,flag)
    },
    //查询获取车辆列表
    thermometer() {
      this.$api.vehicleManagement.getvehicleList({ driver_name: this.driver_name, car_no: this.car_no, page: this.pageIndex, limit: this.pageLimt, }).then(data => {
        if (data.code == 0) {
          this.totalPage = data.data.paging.total;
          let array = data.data.list;
          let arrlist = [];
          for (let i = 0; i < array.length; i++) {
            if (array[i].iceboxList.length == 0) {
              array[i].bxname = '';
              array[i].jname = '';
              array[i].jcode = '';
              array[i].fzr = '';
              array[i].lxdh = '';
              arrlist.push(array[i])
            } else {
              for (let j = 0; j < array[i].iceboxList.length; j++) {
                if (array[i].iceboxList[j].devList.length == 0) {
                  let kong = {};
                  kong.bxname = array[i].iceboxList[j].icebox_name;
                  kong.jname = '';
                  kong.jcode = '';
                  kong.fzr = '';
                  kong.lxdh = '';
                  let obj2 = Object.assign(kong, array[i]);
                  arrlist.push(obj2)
                } else {
                  for (let k = 0; k < array[i].iceboxList[j].devList.length; k++) {
                    let num = {};
                    num.bxname = array[i].iceboxList[j].icebox_name;
                    num.jname = array[i].iceboxList[j].devList[k].dev_name;
                    num.jcode = array[i].iceboxList[j].devList[k].dev_no;
                    num.fzr = array[i].iceboxList[j].devList[k].duty_name;
                    num.lxdh = array[i].iceboxList[j].devList[k].duty_cont_phon;
                    let obj = Object.assign(num, array[i]);
                    arrlist.push(obj)
                  }
                }
              }
            }

          }
          this.tableData = arrlist;
          this.merage();
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //合并表格处理
    merageInit() {
      this.typeNameArr = [];
      this.typeNamePos = 0;
      this.storeArr = [];
      this.storePos = 0;
    },
    //合并表格处理
    merage() {
      this.merageInit();
      for (let i = 0; i < this.tableData.length; i += 1) {
        if (i === 0) {
          // 第一行必须存在
          this.typeNameArr.push(1);
          this.typeNamePos = 0;
          this.storeArr.push(1);
          this.storePos = 0;

        } else {
          // 判断当前元素与上一个元素是否相同,eg：this.typeNamePos 是 this.typeNameArr序号
          // 第一列
          // eslint-disable-next-line no-lonely-if
          if (this.tableData[i].car_no === this.tableData[i - 1].car_no) {
            this.typeNameArr[this.typeNamePos] += 1;
            this.typeNameArr.push(0);
          } else {
            this.typeNameArr.push(1);
            this.typeNamePos = i;
          }
          // 第二列
          if (this.tableData[i].bxname === this.tableData[i - 1].bxname && this.tableData[i].car_no ===
            this.tableData[i - 1].car_no) {
            this.storeArr[this.storePos] += 1;
            this.storeArr.push(0);
          } else {
            this.storeArr.push(1);
            this.storePos = i;
          }
        }
      }
    },
    //合并表格处理
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        // 前7列的合并方法
        const row1 = this.typeNameArr[rowIndex];
        const col1 = row1 > 0 ? 1 : 0; // 如果被合并了row = 0; 则他这个列需要取消
        return {
          rowspan: row1,
          colspan: col1,
        };
      }
      if (columnIndex === 13) {
        // 前7列的合并方法
        const row1 = this.typeNameArr[rowIndex];
        const col1 = row1 > 0 ? 1 : 0; // 如果被合并了row = 0; 则他这个列需要取消
        return {
          rowspan: row1,
          colspan: col1,
        };
      }
      if (columnIndex === 1) {
        // 前7列的合并方法
        const row1 = this.typeNameArr[rowIndex];
        const col1 = row1 > 0 ? 1 : 0; // 如果被合并了row = 0; 则他这个列需要取消
        let arr = this.flitterData(this.tableData).two,
          Nosort = 0;
        for (let i in arr) {
          if (arr[i] > 0) {
            Nosort += 1;
          }
          //为编号赋值，如果_row>0,序号+1，如果_row=0,序号保持不变
          for (let j in this.tableData) {
            if (i === j) {
              this.tableData[i].sequence = Nosort;
            }
          }
        }
        return {
          rowspan: row1,
          colspan: col1,
        };
      }
      for (let i = 2; i < 8; i++) {
        if (columnIndex === i) {
          // 前7列的合并方法
          const row1 = this.typeNameArr[rowIndex];
          const col1 = row1 > 0 ? 1 : 0; // 如果被合并了row = 0; 则他这个列需要取消
          return {
            rowspan: row1,
            colspan: col1,
          };
        }
      }
      if (columnIndex === 8) {
        // 第8列的合并方法
        const row2 = this.storeArr[rowIndex];
        const col2 = row2 > 0 ? 1 : 0; // 如果被合并了row = 0; 则他这个列需要取消
        return {
          rowspan: row2,
          colspan: col2,
        };
      }

    },
    flitterData(arr) {
      let spanTwoArr = [],
        concatTwo = 0;
      arr.forEach((item, index) => {
        if (index === 0) {
          spanTwoArr.push(1);
        } else {
          if (item.car_no === arr[index - 1].car_no) {//第二列需合并相同内容的判断条件
            spanTwoArr[concatTwo] += 1;
            spanTwoArr.push(0);
          } else {
            spanTwoArr.push(1);
            concatTwo = index;
          };
        }
      });
      return {
        two: spanTwoArr
      }
    },
    //删除车辆
    removevehicle() {
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: '您尚未选择要删除的对象',
          type: 'warning'
        });
      } else {
        this.$confirm('此操作将移除车辆信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let array = this.multipleSelection;
          let ids = [];
          for (let i = 0; i < array.length; i++) {
            ids.push(array[i].id)
          }
          this.$api.vehicleManagement.removevehicle({ ids: ids }).then(data => {
            if (data.code == 0) {
              this.$message({
                message: data.msg,
                type: 'success'
              });
              this.thermometer();//更新列表
            } else {
              this.$message.error(data.msg);
            }
          });
        }).catch(() => { });
      }
    },
    //添加更新成功更新列表
    closeupdeata() {
      this.dialogopen = false;
      this.thermometer();
    },
    warnadd() {
      this.dialogopen = true;
      this.$nextTick(() => {
        this.$refs.newadd.triggers();//点击新增调用温度计列表
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      if (val.length > 0) {
        this.disable = false
      } else {
        this.disable = true
      }
      console.log(val)
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
        line-height: 0.5rem;
        padding-left: 3%;
        background-color: #fff;
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
        margin: 1% 2%;
        padding: 1%;
        border: 1px solid #ebebeb;
        background-color: #fff;
        opacity: 1;
        border-radius: 0.08rem;
      }
    }
  }
}
</style>
