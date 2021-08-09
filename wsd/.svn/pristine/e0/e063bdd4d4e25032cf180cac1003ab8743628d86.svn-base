<template>
  <el-dialog class="room" @close="Shut" :title="title" :visible.sync="visible" :width="width" :top="top" :modal="modal" append-to-body :close-on-press-escape="false" destroy-on-close :close-on-click-modal="false" :show-close="false">
    <div class="roombutton">
      <el-button size="mini" type="primary" @click="preserve">保存</el-button>
      <el-button size="mini" @click="closeDialog">取消</el-button>
    </div>
    <div class="roomcontent">
      <p>基本信息</p>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item class="box" label="楼栋:">
          <el-input v-model.trim="form.build_name" disabled></el-input>
        </el-form-item>
        <el-form-item class="box" label="楼层:">
          <el-input v-model.trim="form.key" disabled></el-input>
        </el-form-item>
        <el-form-item class="box" label="房间:">
          <el-input v-model.trim="form.room_cd" disabled></el-input>
        </el-form-item>
        <el-form-item class="box" label="关联温湿度计:" prop="dev_id">
          <el-select v-model="form.dev_id" placeholder="请选择" @change="countselect" @focus="sitedata">
            <el-option v-for="(item, index) in tableData2" :key="index" :label="item.dev_name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box" label="温湿度计编号:">
          <el-input v-model="form.dev_no" disabled></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="prepare">
      <p><span class="">设备信息</span>
        <el-button icon="el-icon-plus" class="fr" size="mini" @click="addfacility">新增设备</el-button>
      </p>
      <el-scrollbar ref="myScrollbar" style="height:6rem;width:100%;">
        <el-form ref="form2" :model="form2" label-width="70px">
          <div class="equipment" v-for="(item, index) in form2.sbList" :key="index">
            <div class="facility">
              <el-form-item class="box" label="名称:" :prop="'sbList.'+ index +'.icebox_name'" :rules="[{ required: true, message: '名称不能为空',trigger: 'blur'}]">
                <el-input v-model.trim="item.icebox_name" placeholder="名称"></el-input>
              </el-form-item>
              <el-form-item class="box" label="负责人:" :prop="'sbList.'+ index +'.duty_name'" :rules="[{ required: true, message: '负责人不能为空',trigger: 'change'}]">
                <el-select v-model="item.duty_name" class="win50" placeholder="请选择" size="mini" @change="changeduty_name(index,item.duty_name)">
                  <el-option v-for="(it, inex) in personchargelist" :key="inex" :label="it.user_name" :value="it.login_name"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item class="box" label="电话:">
                <el-input v-model.trim.number="item.contact_phone1" maxlength="11" placeholder="联系电话" disabled></el-input>
              </el-form-item>
              <el-form-item class="box" label="用途:" :prop="'sbList.'+ index +'.use_type'" :rules="[{ required: true, message: '用途不能为空',trigger: 'change'}]">
                <el-select size="mini" v-model="item.use_type" placeholder="用途">
                  <el-option v-for="(item, index) in belong_typelist2" :key="index" :label="item.val" :value="item.code"></el-option>
                </el-select>
              </el-form-item>
              <span class="deleteinstrument" @click="removeapparatus(index)"><i class="iconfont iconqita_huaban1fuben7"></i>移除</span>
              <span class="addinstrument" @click="addapparatus(index)"><i class="iconfont iconqita_huaban1fuben6"></i>新增仪器</span>
              <el-table ref="multipleTable" :data="item.dev_ids" tooltip-effect="dark" style="width: 100%" height="200px" :row-style="{height: '40px'}" :cell-style="{padding: '0'}">
                <el-table-column type="index" align="center" label="序号" width="55"></el-table-column>
                <el-table-column prop="dev_name" align="center" label="温湿度计名称"></el-table-column>
                <el-table-column prop="dev_no" align="center" label="温湿度计编号"></el-table-column>
                <el-table-column prop="duty_name" align="center" label="负责人"></el-table-column>
                <el-table-column prop="duty_cont_phon" align="center" label="联系电话"></el-table-column>
                <el-table-column prop="use_type" align="center" label="用途">
                   <template slot-scope="scope">
                  {{scope.row.use_type|formatUse}}
                </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="80">
                  <template slot-scope="scope">
                    <i class="iconfont iconqita_huaban1fuben12" style="font-size: .2rem;" @click="handleDelete(scope.$index, scope.row,index)"></i>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </el-form>
      </el-scrollbar>
    </div>
    <el-dialog class="core" width="10.6rem" :visible.sync="innerVisible" :close-on-click-modal="false" :show-close="false" append-to-body>
      <div class="devicetab">
        <span>新增设备</span>
        <el-input size="mini" v-model="dev_name2" clearable placeholder="设备名称"></el-input>
        <el-input size="mini" v-model="dev_no2" clearable placeholder="设备编号"></el-input>
        <el-select size="mini" v-model="use_type2" placeholder="用途" @change="application">
          <el-option v-for="(item, index) in belong_typelist" :key="index" :label="item.val" :value="item.code"></el-option>
        </el-select>
        <el-button size="mini" type="primary" @click="demand">查询</el-button>
      </div>
      <i class="el-icon-close" @click="innerVisible = false"></i>
      <el-table :data="tableData" tooltip-effect="dark" style="width: 100%" height="6rem" :row-style="{height: '40px'}" :cell-style="{padding: '0'}" @selection-change="handleSelectionChange">
        <el-table-column type="selection" align="center" width="50"></el-table-column>
        <el-table-column type="index" align="center" label="序号" width="50"></el-table-column>
        <el-table-column prop="dev_name" align="center" label="温湿度计名称"></el-table-column>
        <el-table-column prop="dev_no" align="center" label="温湿度计编号"></el-table-column>
        <el-table-column prop="duty_name" align="center" label="负责人"></el-table-column>
        <el-table-column prop="duty_cont_phon" align="center" label="联系电话"></el-table-column>
        <el-table-column prop="use_type" align="center" label="用途">
          <template slot-scope="scope">
                  {{scope.row.use_type|formatUse}}
                </template>
        </el-table-column>
      </el-table>
      <pagination v-show="totalPage > 0" :total="totalPage" :page.sync="pageIndex" :limit.sync="pageLimt" @pagination="thermometer" />
      <div class="confirmbutton">
        <el-button size="mini" @click="innerVisible=false">取消</el-button>
        <el-button size="mini" type="primary" @click="confirmoperate">确认</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>
<script>
import Pagination from "@/components/Pagination";
export default {
  components: { Pagination },
  props: {
    show: {
      type: Boolean,
      default: false,
    },
    title: {
      //标题
      type: String,
      default: "",
    },
    width: {
      type: [Number, String],
      default: "800px",
    },
    top: {
      type: String,
      default: "3vh",
    },
    modal: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      form2: {
        sbList: []
      },
      count: [],//温度计列表
      count2: [],//温度计选择列表
      floorList: [],//选中的数组
      tableData: [],//去重后的数组
      tableData2: [],//去重后的数组
      form: {
        build_name: '',
        key: '',
        room_cd: '',
        dev_name: '',
        dev_no: '',
        dev_id: ''
      },
      innerVisible: false,//内层开关
      multipleSelection: [],//内层选中的设备
      index: 0,//打开内层是添加的那个冰箱下标
      confirmmultipleSelection: [],//渲染在冰箱上的最终设备
      dev_name2: '',//内层筛选仪器名称
      use_type2: '',//内层筛选仪器用途
      dev_no2: '',//内层筛选仪器编号
      belong_typelist: [],//设备用途
      belong_typelist2: [],
      em: '',//父组件传过来的楼栋信息
      rules: {
        dev_id: [
          { required: true, message: '请关联设备', trigger: 'change' }
        ],
      },
      pageIndex: 1,
      pageLimt: 10,
      totalPage: 10,
      cedev: [],
      siteid: '',
      personchargelist: [],
      visible:false
    };
  },
  // computed: {
  //   visible: {
  //     get() {
  //       return this.show;
  //     },
  //     set(val) {
  //       // 当visible改变的时候，触发父组件的 updateVisible方法，在该方法中更改传入子组件的 centerDialogVisible的值
  //       // this.$emit('updateVisible', val)
  //     },
  //   },
  // },
  created() { },
  mounted() {
    this.thermometer2();
    this.purpose();
    this.getDriverList()
  },
  methods: {
    changeduty_name(num, e) {
      let array = this.personchargelist;
      for (let i = 0; i < array.length; i++) {
        if (array[i].login_name == e) {
          this.form2.sbList[num].contact_phone1 = array[i].mobile_phone;
        }
      }
    },
    getDriverList() {
      this.$api.dictionary.getUserList().then(data => {
        if (data.code == 0) {
          this.personchargelist = data.data.list;
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    sitedata() {
      this.siteid = this.form.dev_id;
    },
    Shut() {
      this.$refs['form'].resetFields();
      // this.$refs['form2'].resetFields();
    },
    async drawLine(build_name, key, em) {
      this.visible=true;
      this.cedev = [];
      await this.thermometer()
      let that = this;
      this.form2.sbList = [];
      this.em = em;
      if (em.dev_id) {
        for (let i = 0; i < this.count.length; i++) {
          if (this.count[i].id == em.dev_id) {
            this.cedev.push(this.count[i]);
            this.form.dev_name = this.count[i].dev_name;
            this.form.dev_no = this.count[i].dev_no;
            this.form.dev_id = this.count[i].id;
          }
        }
      } else {
        this.form.dev_name = '';
        this.form.dev_no = '';
        this.form.dev_id = '';
      }
      this.form.build_name = build_name;
      this.form.key = key;
      this.form.room_cd = em.room_cd;
      this.$api.building.buildingdetails({ id: em.id }).then((data) => {
        if (data.code == 0) {
          let array = data.data.sbList
          if (array.length != 0) {
            for (let i = 0; i < array.length; i++) {
              let obj = {
                "id": array[i].id,
                "st": array[i].st,
                "icebox_name": array[i].icebox_name,
                "duty_name": array[i].duty_name,
                "contact_phone1": array[i].contact_phone1,
                "use_type": array[i].use_type,
                "dev_ids": []
              }
              for (let j = 0; j < array[i].wdjList.length; j++) {
                // if (array[i].wdjList[j].use_type == "PYX") {
                //   array[i].wdjList[j].use_type = "培养箱";
                // } else if (array[i].wdjList[j].use_type == "BX") {
                //   array[i].wdjList[j].use_type = "冰箱";
                // } else if (array[i].wdjList[j].use_type == "GYG") {
                //   array[i].wdjList[j].use_type = "高压锅";
                // } else if (array[i].wdjList[j].use_type == "TPJ") {
                //   array[i].wdjList[j].use_type = "天平间";
                // } else if (array[i].wdjList[j].use_type == "CWK") {
                //   array[i].wdjList[j].use_type = "常温库";
                // } else if (array[i].wdjList[j].use_type == "LCX") {
                //   array[i].wdjList[j].use_type = "常温库";
                // } else if (array[i].wdjList[j].use_type == "LC") {
                //   array[i].wdjList[j].use_type = "冷藏";
                // } else if (array[i].wdjList[j].use_type == "LK") {
                //   array[i].wdjList[j].use_type = "冷库";
                // }
                let dev_id = array[i].wdjList[j]
                obj.dev_ids.push(dev_id)
              }
              this.form2.sbList.push(obj)
            }
          }
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //查询获取设备列表
    thermometer2() {
      this.$api.sensor
        .getSensorList({
          dev_name: '',
          st: '',
          use_type: '',
          dev_no: '',
        })
        .then((data) => {
          if (data.code == 0) {
            this.count = data.data.list;
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    //查询获取设备列表
    thermometer() {
      this.$api.sensor
        .getSensorList({
          dev_name: this.dev_name2,
          st: 'XZ',
          use_type: this.use_type2,
          dev_no: this.dev_no2,
          page: this.pageIndex,
          limit: this.pageLimt,
        })
        .then((data) => {
          if (data.code == 0) {
            let array = data.data.list;
            this.totalPage = data.data.paging.total;
            // for (let i = 0; i < array.length; i++) {
            //   if (array[i].use_type == "PYX") {
            //     array[i].use_type = "培养箱";
            //   } else if (array[i].use_type == "BX") {
            //     array[i].use_type = "冰箱";
            //   } else if (array[i].use_type == "GYG") {
            //     array[i].use_type = "高压锅";
            //   } else if (array[i].use_type == "TPJ") {
            //     array[i].use_type = "天平间";
            //   } else if (array[i].use_type == "CWK") {
            //     array[i].use_type = "常温库";
            //   } else if (array[i].use_type == "LCX") {
            //     array[i].use_type = "常温库";
            //   } else if (array[i].use_type == "LC") {
            //     array[i].use_type = "冷藏";
            //   } else if (array[i].use_type == "LK") {
            //     array[i].use_type = "冷库";
            //   }
            // }
            this.count2 = array;
            this.tableData = this.duplicateremoval(this.floorList, this.count2);
            let Filterarra = [];
            for (let j = 0; j < this.tableData.length; j++) {
              if (this.tableData[j].belong_type == 'WSD') {
                Filterarra.push(this.tableData[j])
              }
            }
            this.tableData2 = this.cedev.concat(Filterarra);
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    //获取没有被选择的
    duplicateremoval(arr1, arr2) {
      var array = arr2;
      var array2 = arr1;
      var arr3 = [];
      for (var i = 0; i < array.length; i++) {  //用来遍历的数组使用两个数组长度比较长的，不然可能没有效果
        var stra = array[i];
        var count = 0;
        for (var j = 0; j < array2.length; j++) {
          var strb = array2[j];
          if (stra == strb) {
            count++;
          }
        }
        if (count === 0) {
          arr3.push(stra);
        }
      }
      return arr3
    },

    //获取用途列表
    purpose() {
      this.$api.dictionary
        .getDictionaryList({ parent_code: 'SBYT' })
        .then((data) => {
          this.belong_typelist2 = data.data.list;
          if (data.code == 0) {
            let obj = [{
              val: '全部',
              code: ''
            }]
            let newlist = data.data.list.concat(obj)
            this.belong_typelist = newlist;
          } else {
            this.$message.error(data.msg);
          }
        });
    },
    //获取选中用途
    application(e) {
      this.use_type2 = e;
    },
    //查询
    demand() {
      this.thermometer()
    },
    //关联设备选择
    countselect(e) {
      Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
          this.splice(index, 1);
        }
      };
      if (e != this.cedev.id) {
        for (let i = 0; i < this.count2.length; i++) {
          if (this.count2[i].id == e) {
            this.form.dev_no = this.count2[i].dev_no;
            this.floorList.push(this.count2[i]);
          }
        }
        for (let j = 0; j < this.count2.length; j++) {
          if (this.siteid != this.cedev.id) {
            if (this.count2[j].id == this.siteid) {
              this.floorList.remove(this.count2[j]);
            }
          }
        }
        this.tableData = this.duplicateremoval(this.floorList, this.count2);
      } else {
        this.form.dev_no = this.cedev.dev_no;
        for (let j = 0; j < this.count2.length; j++) {
          if (this.siteid != this.cedev.id) {
            if (this.count2[j].id == this.siteid) {
              this.floorList.remove(this.count2[j]);
            }
          }
        }
        this.tableData = this.duplicateremoval(this.floorList, this.count2);
      }
    },
    //添加冰箱
    addfacility() {
      let obj = {
        "id": "",
        "st": "ZC",
        "icebox_name": "",
        "duty_name": "",
        "contact_phone1": "",
        "use_type": '',
        "dev_ids": []
      };
      this.form2.sbList.push(obj)
    },
    //移除冰箱
    removeapparatus(index) {
      let that = this;
      let array = this.form2.sbList[index].dev_ids;
      Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
          this.splice(index, 1);
        }
      };
      for (let i = 0; i < array.length; i++) {
        if (!array.id) {
          this.floorList.remove(array[i])
        }
      }
      that.tableData = that.duplicateremoval(that.floorList, that.count2);
      let Filterarra = [];
      for (let j = 0; j < that.tableData.length; j++) {
        if (that.tableData[j].belong_type == 'WSD') {
          Filterarra.push(that.tableData[j])
        }
      }
      this.tableData2 = this.cedev.concat(Filterarra)
      this.form2.sbList.splice(index, 1);
    },
    //添加仪器打开内层弹框
    addapparatus(e) {
      this.index = e;
      this.innerVisible = true;
    },
    //点击内层弹框
    confirmoperate() {
      let that = this;
      if (this.multipleSelection.length == 0) {
        this.$message({
          message: "您尚未选择要操作的对象",
          type: "warning",
        });
      } else {
        this.innerVisible = false;
        this.form2.sbList[this.index].dev_ids = this.form2.sbList[this.index].dev_ids.concat(this.multipleSelection);
        this.floorList = this.floorList.concat(this.multipleSelection)
        this.tableData = this.duplicateremoval(this.floorList, this.count2);
        let Filterarra = [];
        for (let j = 0; j < this.tableData.length; j++) {
          if (this.tableData[j].belong_type == 'WSD') {
            Filterarra.push(this.tableData[j])
          }
        }
        this.tableData2 = this.cedev.concat(Filterarra)
      }
    },
    //删除
    handleDelete(e, data, index) {
      let that = this;
      Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
          this.splice(index, 1);
        }
      };
      this.form2.sbList[index].dev_ids.remove(data);
      if (data.st == 'XZ') {
        this.floorList.remove(data);
      }
      that.tableData = that.duplicateremoval(that.floorList, that.count2);
      let Filterarra = [];
      for (let j = 0; j < that.tableData.length; j++) {
        if (that.tableData[j].belong_type == 'WSD') {
          Filterarra.push(that.tableData[j])
        }
      }
      this.tableData2 = this.cedev.concat(Filterarra)
    },
    closeDialog() {
       this.visible=false;
      this.$emit("close");
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //保存
    preserve() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$refs['form2'].validate((valid) => {
            if (valid) {
              this.request()
            } else {
              return false;
            }
          });
        } else {
          return false;
        }
      });
    },
    request() {
      let dev_id = '';
      for (let i = 0; i < this.count.length; i++) {
        if (this.count[i].dev_no == this.form.dev_no) {
          dev_id = this.count[i].id
        }
      }
      let basic = {
        "building": this.em.build_id,//楼栋id
        "floor_cd": this.em.floor_cd,//楼层
        "id": this.em.id,//房间id
        "room_cd": this.em.room_cd,//房间号
        "dev_id": dev_id,//关联的温度计ID
        "type": "FJ"
      };
      let equipment = this.form2.sbList;
      for (let j = 0; j < equipment.length; j++) {
        if (!equipment[j].icebox_name) {
          this.$message({
            showClose: true,
            message: '名称不能为空',
            type: 'warning'
          });
          return false
        }
        if (!equipment[j].duty_name) {
          this.$message({
            showClose: true,
            message: '负责人不能为空',
            type: 'warning'
          });
          return false
        }
        if (!equipment[j].use_type) {
          this.$message({
            showClose: true,
            message: '用途不能为空',
            type: 'warning'
          });
          return false
        }
        if (!equipment[j].contact_phone1) {
          this.$message({
            showClose: true,
            message: '联系电话不能为空',
            type: 'warning'
          });
          return false
        }
        let array = []
        for (let k = 0; k < equipment[j].dev_ids.length; k++) {
          let str = equipment[j].dev_ids[k].id.toString();
          array.push(str)
        }
        equipment[j].dev_ids = array;
      }
      this.$api.building.editRoomInfo({ basic: basic, equipment: equipment }).then((data) => {
        if (data.code == 0) {
          this.$message({
            message: data.msg,
            type: 'success'
          });
          this.$emit("close");
          this.visible=false;
        } else {
          this.$message.error(data.msg);
        }
      });
    }
  },
};
</script>
<style lang="scss" scoped>
.core {
  ::v-deep .el-dialog__header {
    padding-bottom: 30px;
  }
  ::v-deep .el-dialog__body {
    border-top: 1px solid #eee;
    padding: 0 0.2rem;
    .el-table thead {
      color: #8c8c8c;
    }
    .el-table th {
      color: #8c8c8c;
      padding: 0.08rem 0;
    }
    .el-table .cell {
      color: #8c8c8c;
    }
  }
  .devicetab {
    position: absolute;
    top: 0.04rem;
    height: 0.4rem;
    line-height: 0.4rem;
    span {
      display: inline-block;
      margin-right: 0.3rem;
      font-weight: 600;
    }
    .el-input,
    .el-select {
      width: 20%;
      margin-left: 0.1rem;
    }
    .el-select {
      margin-right: 0.1rem;
    }
  }
  .confirmbutton {
    height: 0.6rem;
    text-align: center;
    .el-button {
      margin-top: 0.15rem;
      margin-right: 0.2rem;
    }
  }
  .el-icon-close {
    position: absolute;
    top: 0.1rem;
    right: 10px;
    font-size: 0.2rem;
  }
}
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
    margin-bottom: 0.1rem;
    padding-bottom: 0.1rem;
    p {
      height: 0.4rem;
      line-height: 0.4rem;
      padding-left: 0.2rem;
      font-weight: 600;
    }
    .el-form {
      ::v-deep .el-form-item {
        margin-bottom: 0rem;
      }
      .box {
        display: inline-block;
        width: 30%;

        ::v-deep .el-form-item__label {
          color: #8c8c8c;
        }
        ::v-deep .el-input__inner {
          height: 0.3rem !important;
        }
      }
    }
  }
  .prepare {
    border: 1px solid #f0f0f0;
    opacity: 1;
    border-radius: 0.09rem;

    p {
      height: 0.4rem;
      line-height: 0.4rem;
      padding-left: 0.2rem;
      span {
        font-weight: 600;
      }
      .el-button {
        margin-top: 0.05rem;
        margin-right: 0.2rem;
        color: #1a90fd;
        padding: 0.07rem 0.1rem;
        ::v-deep .el-icon-plus {
          font-weight: 600;
          font-size: 0.14rem;
        }
      }
    }
    .el-form {
      .equipment {
        margin-bottom: 0.1rem;
        margin: 0.1rem 0 0.06rem;
        ::v-deep .el-form-item {
          margin-bottom: 0.12rem;
        }
        .box {
          display: inline-block;
          width: 20%;
          ::v-deep .el-form-item__label {
            color: #8c8c8c;
          }
          ::v-deep .el-input__inner {
            height: 0.26rem !important;
          }
          ::v-deep .el-form-item__error {
            padding-top: 0;
          }
        }
        .el-button {
          margin-top: 0.05rem;
          margin-right: 0.1rem;
        }
        .addinstrument,
        .deleteinstrument {
          margin-top: 0.05rem;
          float: right;
          display: inline-block;
          width: 0.8rem;
          height: 0.4rem;
          text-align: center;
          font-size: 0.12rem;
          .iconfont {
            display: block;
            font-weight: 600;
          }
        }
        .addinstrument {
          color: #1a90fd;
        }
        .deleteinstrument {
          color: #ff656d;
        }
        .facility {
          border: 1px solid #f0f0f0;
          width: 94%;
          margin-left: 3%;
          border-radius: 0.06rem;
          ::v-deep .el-table th.is-leaf,
          .el-table td {
            border-top: 1px solid #ebeef5;
            padding: 8px 0;
          }
          .el-table::before {
            height: 0;
          }
          ::v-deep .el-table {
            .cell {
              color: #8c8c8c;
            }
          }
        }
      }
    }
  }
}
</style>