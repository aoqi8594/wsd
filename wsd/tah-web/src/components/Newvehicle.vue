<template>
  <el-dialog class="room" @close="closegubi" :title="!!form.id?'车载编辑':'新增车载信息'" :visible.sync="visible" :width="width" :height="height" :top="top" :modal="modal" append-to-body :close-on-press-escape="false" destroy-on-close :close-on-click-modal="false" :show-close="false">
    <div class="roombutton">
      <el-button size="mini" type="primary" v-if="!falg" @click="submitForm">保存</el-button>
      <el-button size="mini" @click="closeDialog">取消</el-button>
    </div>
    <div class="roomcontent">
      <p>基本信息</p>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item class="box" label="车牌号:" prop="car_no">
          <el-input v-model.trim="form.car_no" :disabled="falg"></el-input>
        </el-form-item>
        <el-form-item class="box" label="驾驶人:" prop="driver_lname">
           <el-select v-model="form.driver_lname" placeholder="请选择" @change="userselect" :disabled="falg">
            <el-option v-for="(item, index) in personchargelist" :key="index" :label="item.user_name" :value="item.login_name"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box" label="联系电话:" >
          <el-input v-model.trim="form.contact_phone"  disabled></el-input>
        </el-form-item>
        <el-form-item class="box" label="出发地:"  prop="start_place">
          <el-input v-model.trim="form.start_place"></el-input>
        </el-form-item>
        <el-form-item class="box" label="目的地:" prop="end_place">
          <el-input v-model.trim="form.end_place" :disabled="falg"></el-input>
        </el-form-item>
        <el-form-item class="box" label="行车记录仪:" >
          <el-input v-model.trim="form.tachograph"  disabled></el-input>
        </el-form-item>
        <el-form-item class="box" label="关联温湿度计:" prop="dev_id">
          <el-select v-model="form.dev_id" placeholder="请选择" @change="countselect" @focus="sitedata" :disabled="falg">
            <el-option v-for="(item, index) in tableData2" :key="index" :label="item.dev_name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="box" label="温湿度计编号:">
          <el-input v-model="form.dev_no" disabled></el-input>
        </el-form-item>
        <el-form-item label="备注:" class="box2">
          <el-input type="textarea" v-model.trim="form.mark" :disabled="falg"></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="prepare">
      <p><span class="">设备信息</span>
        <el-button icon="el-icon-plus" class="fr" size="mini" @click="addfacility"  v-if="!falg">新增设备</el-button>
      </p>
      <el-scrollbar ref="myScrollbar" style="height:5rem;width:100%;">
        <el-form  ref="form2" :model="form2" label-width="110px">
        <div class="equipment" v-for="(item, index) in form2.sbList" :key="index">
          <div class="facility">
              <el-form-item class="box" label="名称:" :prop="'sbList.'+ index +'.icebox_name'" :rules="[{ required: true, message: '名称不能为空',trigger: 'blur'}]">
                <el-input v-model="item.icebox_name" :disabled="falg"></el-input>
              </el-form-item>
              <el-form-item class="box" label="负责人:" :prop="'sbList.'+ index +'.duty_name'" :rules="[{ required: true, message: '负责人不能为空',trigger: 'change'}]">
                 <el-select v-model="item.duty_name" class="win50" placeholder="请选择" size="mini" @change="changeduty_name(index,item.duty_name)" :disabled="falg">
                <el-option v-for="(it, inex) in personchargelist2" :key="inex" :label="it.user_name" :value="it.login_name"></el-option>
              </el-select>
              </el-form-item>
              <el-form-item class="box" label="负责人电话:">
                <el-input v-model="item.contact_phone1" disabled></el-input>
              </el-form-item>
              <span class="deleteinstrument" v-if="!falg" @click="removeapparatus(index)"><i class="iconfont iconqita_huaban1fuben7"></i>移除</span>
              <span class="addinstrument" v-if="!falg" @click="addapparatus(index)"><i class="iconfont iconqita_huaban1fuben6"></i>新增仪器</span>
            
            <el-table ref="multipleTable" :data="item.dev_ids" tooltip-effect="dark" style="width: 100%" height="200px" :row-style="{height: '40px'}" :cell-style="{padding: '0'}">
              <el-table-column type="index" label="序号" width="50"></el-table-column>
              <el-table-column prop="dev_name" label="温湿度计名称"></el-table-column>
              <el-table-column prop="dev_no" label="温湿度计编号"></el-table-column>
              <el-table-column prop="duty_name" label="负责人"></el-table-column>
              <el-table-column prop="duty_cont_phon" label="联系电话"></el-table-column>
              <el-table-column prop="use_type" label="用途">
                <template slot-scope="scope">
                  {{scope.row.use_type|formatUse}}
                </template>
              </el-table-column>
              <el-table-column label="操作" v-if="!falg">
                <template slot-scope="scope">
                  <i class="iconfont iconqita_huaban1fuben12" style="font-size: .2rem;" @click="handleDelete(scope.row,index)"></i>
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
        <el-table-column type="selection" width="50"></el-table-column>
        <el-table-column type="index" label="序号" width="50"></el-table-column>
        <el-table-column prop="dev_name" label="温湿度计名称"></el-table-column>
        <el-table-column prop="dev_no" label="温湿度计编号"></el-table-column>
        <el-table-column prop="duty_name" label="负责人"></el-table-column>
        <el-table-column prop="duty_cont_phon" label="联系电话"></el-table-column>
        <el-table-column prop="use_type" label="用途"></el-table-column>
      </el-table>
      <div class="confirmbutton">
        <el-button size="mini" @click="innerVisible=false">取消</el-button>
        <el-button size="mini" type="primary" @click="confirmoperate">确认</el-button>
      </div>
    </el-dialog>
  </el-dialog>
</template>
<script>
export default {
  props: {
    show: {
      type: Boolean,
      default: false,
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
    height: {
      type: [Number, String],
      default: "auto",
    },
  },
  data() {
    return {
      visible: false,
      form: {
        "id": 0,
        "st": "ZXZ",
        "car_no": "",//车牌号
        "driver_name": "",//驾驶人
        "driver_lname": "",//驾驶人登录名
        "contact_phone": "",//联系电话
        "start_place": "",//出发地
        "end_place": "",//目的地
        "tachograph": "",//行车记录仪
        "dev_name": "",//关联温度计名称
        "dev_no": "",//关联温度计编号
        "dev_id":"",//关联温度计id
        "mark": ""//备注
      },
      count: [],//温度计列表
       form2:{
         sbList:[]//添加的冰箱列表
      },
      innerVisible: false,//内层开关
      dev_name2: '',//内层筛选仪器名称
      use_type2: '',//内层筛选仪器用途
      dev_no2: '',//内层筛选仪器编号
      belong_typelist: [],//设备用途
      tableData: [],//去重后的数组
      tableData2: [],//去重后的数组
      count2: [],//温度计选择列表
      floorList: [],//选中的数组
      index: 0,//打开内层是添加的那个冰箱下标
      multipleSelection: [],//内层选中的设备
      rules: {
        car_no: [
          { required: true, message: '请输入车牌号', trigger: 'blur' },
        ],
        driver_lname: [
          { required: true, message: '请输入驾驶人员', trigger: 'blur' },
        ],
        start_place: [
          { required: true, message: '请输入出发地', trigger: 'blur' },
        ],
        end_place: [
          { required: true, message: '请输入目的地', trigger: 'blur' },
        ],
        dev_id: [
          { required: true, message: '请选择关联仪器', trigger: 'change' }
        ],
      },
      cedev:[],
      siteid:'',
      personchargelist:[],
      personchargelist2:[],
      falg:false
    };
  },
  computed: {},
  created() { },
  mounted() {
    this.getDriverList();
    this.getDriverList2();
   },
  methods: {
     changeduty_name(num,e){
       let  array=this.personchargelist;
       for (let i = 0; i < array.length; i++) {
         if(array[i].login_name==e){
            this.form2.sbList[num].contact_phone1=array[i].mobile_phone;
         }
       }
    },
    getDriverList2() {
      this.$api.dictionary.getUserList().then(data => {
        if (data.code == 0) {
          this.personchargelist2 = data.data.list;
        } else {
          this.$message.error(data.msg);
        }
      });
    },   
     getDriverList() {
      this.$api.user.getDriverList().then(data => {
        if (data.code == 0) {
          this.personchargelist = data.data.list;
        } else {
          this.$message.error(data.msg);
        }
      });
    },   
    async init(row,falg) {
      console.log(row)
      this.falg=falg;
      this.cedev=[];
      if (row) {
        this.form.id = row.id || 0;
         this.cedev.push(row.relationDevInfo[0]);
      } else {
        this.form.id = 0
      }
      await this.thermometer()
      this.visible = true;
      if (this.form.id) {
        this.form = {
          "id": row.id,
          "st": row.st,
          "car_no": row.car_no,//车牌号
          "driver_name": row.driver_name,//驾驶人
          "driver_lname": row.driver_lname,//驾驶人登录名
          "contact_phone": row.contact_phone,//联系电话
          "start_place": row.start_place,//出发地
          "end_place": row.end_place,//目的地
          "tachograph": row.tachograph,//行车记录仪
          "dev_name": row.relationDevInfo[0].dev_name,//关联温度计名称
          "dev_no": row.relationDevInfo[0].dev_no,//关联温度计编号
          "dev_id": row.relationDevInfo[0].id,//关联温度计id
          "mark": row.mark//备注
        };
        let array = row.iceboxList;
        for (let i = 0; i < array.length; i++) {
          let obj = {
            "id": array[i].id,
            "st": array[i].st,
            "icebox_name": array[i].icebox_name,
            "duty_name": array[i].duty_name,
            "contact_phone1": array[i].contact_phone1,
            "dev_ids": array[i].devList
          }
          this.form2.sbList.push(obj)
        }
      }
    },
    submitForm() {
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
    request(){
      let that=this;
        let dev_id = this.form.dev_id;
          let basic;
          if (this.form.id) {
            basic = {
              "id": this.form.id,
              "st": this.form.st,
              "car_no": this.form.car_no,
              "driver_name": this.form.driver_name,
               "driver_lname": this.form.driver_lname,//驾驶人登录名
              "contact_phone": this.form.contact_phone,
              "start_place": this.form.start_place,
              "end_place": this.form.end_place,
              "tachograph": this.form.tachograph,
              "dev_id": dev_id,
              "mark": this.form.mark
            };
          } else {
            basic = {
              "st": "ZXZ",
              "car_no": this.form.car_no,
              "driver_name": this.form.driver_name,
               "driver_lname": this.form.driver_lname,//驾驶人登录名
              "contact_phone": this.form.contact_phone,
              "start_place": this.form.start_place,
              "end_place": this.form.end_place,
              "tachograph": this.form.tachograph,
              "dev_id": dev_id,
              "mark": this.form.mark
            };
          }
          let equipment = this.form2.sbList;
          for (let j = 0; j < equipment.length; j++) {
            let array = []
            for (let k = 0; k < equipment[j].dev_ids.length; k++) {
              let str = equipment[j].dev_ids[k].id;
              array.push(str)
            }
            equipment[j].dev_ids = array;
          }
          console.log(basic)
          if (this.form.id) {
            that.$api.vehicleManagement.updateCar({ basic: basic, equipment: equipment }).then((data) => {
              if (data.code == 0) {
                that.$message({
                  message: data.msg,
                  type: 'success'
                });
                this.visible = false;
                that.$emit("close");
              } else {
                that.$message.error(data.msg);
              }
            });
          } else {
            that.$api.vehicleManagement.addcar({ basic: basic, equipment: equipment }).then((data) => {
              if (data.code == 0) {
                that.$message({
                  message: data.msg,
                  type: 'success'
                });
                this.visible = false;
                that.$emit("close");
              } else {
                that.$message.error(data.msg);
              }
            });
          }
    },
    closegubi() {
      this.count = [];//温度计列表
      this.form2.sbList = [];//添加的冰箱列表
      this.belong_typelist = [];//添加的冰箱列表
      this.tableData = [];//添加的冰箱列表
      this.tableData2 = [];//添加的冰箱列表
      this.count2 = [];//添加的冰箱列表
      this.floorList = [];//添加的冰箱列表
      this.multipleSelection = [];//添加的冰箱列表
      this.form = {
        "st": "ZC",
        "car_no": '',//车牌号
        "driver_name": '',//驾驶人
        "driver_lname": '',//驾驶人登录名
        "contact_phone": '',//联系电话
        "start_place": '',//出发地
        "end_place": '',//目的地
        "tachograph": '',//行车记录仪
        "dev_name": '',//关联温度计名称
        "dev_no": '',//关联温度计编号
        "dev_id": '',//关联温度计id
        "mark": ''//备注
      };
      this.$refs['form'].resetFields();
      // this.$refs['form2'].resetFields();
    },
    triggers() {
      this.purpose();
      this.thermometer();
    },
    parentcalls(row) {
      if (row) {
        this.form.id = row.id || 0;
      } else {
        this.form.id = 0
      }
      this.triggers();
      this.form = {
        "st": "ZC",
        "car_no": row.car_no,//车牌号
        "driver_name": row.driver_name,//驾驶人
        "driver_lname": row.driver_lname,//驾驶人登录名
        "contact_phone": row.contact_phone,//联系电话
        "start_place": row.start_place,//出发地
        "end_place": row.end_place,//目的地
        "tachograph": row.tachograph,//行车记录仪
        "dev_name": row.relationDevInfo[0].dev_name,//关联温度计名称
        "dev_no": row.relationDevInfo[0].dev_no,//关联温度计编号
        "mark": row.mark//备注
      };
      let array = row.iceboxList;
      for (let i = 0; i < array.length; i++) {
        let obj = {
          "id": array[i].id,
          "st": array[i].st,
          "icebox_name": array[i].icebox_name,
          "duty_name": array[i].duty_name,
          "contact_phone1": array[i].contact_phone1,
          "dev_ids": array[i].devList
        }
        this.form2.sbList.push(obj)
      }
    },
    sitedata(){
          this.siteid=this.form.dev_id;
    },
    userselect(e){
        for (let i = 0; i < this.personchargelist.length; i++) {
          if(e==this.personchargelist[i].login_name){
            this.form.driver_name=this.personchargelist[i].user_name;
            this.form.tachograph=this.personchargelist[i].gps_device;
            this.form.contact_phone=this.personchargelist[i].mobile_phone;
          }
        }
    },
    //关联设备选择
    countselect(e) {
      Array.prototype.remove = function (val) {
          var index = this.indexOf(val);
          if (index > -1) {
            this.splice(index, 1);
          }
        };
      if(e!=this.cedev.id){
        for (let i = 0; i < this.count2.length; i++) {
          if(this.count2[i].id==e){
            this.form.dev_no = this.count2[i].dev_no;
              this.floorList.push(this.count2[i]);
          }
        }
        for (let j = 0; j < this.count2.length; j++) {
          if(this.siteid!=this.cedev.id){
            if(this.count2[j].id==this.siteid){
              this.floorList.remove(this.count2[j]);
            }
          }
        }
        this.tableData = this.duplicateremoval(this.floorList, this.count2);
      }else{
        this.form.dev_no = this.cedev.dev_no;
        for (let j = 0; j < this.count2.length; j++) {
          if(this.siteid!=this.cedev.id){
            if(this.count2[j].id==this.siteid){
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
        "dev_ids": []
      }
      this.form2.sbList.push(obj)
    },
    //添加仪器打开内层弹框
    async addapparatus(e) {
      this.index = e;
      await this.purpose();
      this.innerVisible = true;
    },
    //获取用途列表
    purpose() {
      this.$api.dictionary
        .getDictionaryList({ parent_code: 'SBYT' })
        .then((data) => {
          if (data.code == 0) {
            let obj = {
              val: '全部',
              code: ''
            }
            data.data.list.push(obj)
            this.belong_typelist = data.data.list;
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
    //查询获取设备列表
    thermometer() {
      this.$api.sensor
        .getSensorList({
          dev_name: this.dev_name2,
          st:"XZ",
          use_type: this.use_type2,
          dev_no: this.dev_no2
        })
        .then((data) => {
          if (data.code == 0) {
            let array = data.data.list;
            for (let i = 0; i < array.length; i++) {
              if (array[i].use_type == "PYX") {
                array[i].use_type = "培养箱";
              } else if (array[i].use_type == "BX") {
                array[i].use_type = "冰箱";
              } else if (array[i].use_type == "GYG") {
                array[i].use_type = "高压锅";
              }else if (array[i].use_type == "TPJ") {
                array[i].use_type = "太平间";
              }else if (array[i].use_type == "CWK") {
                array[i].use_type = "常温库";
              }else if (array[i].use_type == "LCX") {
                array[i].use_type = "常温库";
              }else if (array[i].use_type == "LC") {
                array[i].use_type = "冷藏";
              }else if (array[i].use_type == "LK") {
                array[i].use_type = "冷库";
              } 
            }
            this.count2 = array;
            this.tableData = this.duplicateremoval(this.floorList, this.count2);
            let Filterarra=[];
            for (let j = 0; j < this.tableData.length; j++) {
              if(this.tableData[j].belong_type=='WSD'){
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
    handleSelectionChange(val) {
      this.multipleSelection = val;
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
         let Filterarra=[];
            for (let j = 0; j < this.tableData.length; j++) {
              if(this.tableData[j].belong_type=='WSD'){
                Filterarra.push(this.tableData[j])
              }
            }
        this.tableData2=this.cedev.concat(Filterarra) 
      }
    },
    //删除
    handleDelete(data, index) {
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
      let Filterarra=[];
            for (let j = 0; j < that.tableData.length; j++) {
              if(that.tableData[j].belong_type=='WSD'){
                Filterarra.push(that.tableData[j])
              }
            }
      this.tableData2=this.cedev.concat(Filterarra)
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
        if (array[i].st == 'XZ') {
          this.floorList.remove(array[i]);
        }
      }
      that.tableData = that.duplicateremoval(that.floorList, that.count2);
       let Filterarra=[];
            for (let j = 0; j < that.tableData.length; j++) {
              if(that.tableData[j].belong_type=='WSD'){
                Filterarra.push(that.tableData[j])
              }
            }
      this.tableData2=this.cedev.concat(Filterarra)
      this.form2.sbList.splice(index, 1);
    },
    //保存
    preserve() {
      let dev_id = '';
      for (let i = 0; i < this.count.length; i++) {
        if (this.count[i].dev_no == this.form.dev_no) {
          dev_id = this.count[i].id
        }
      }
      let basic;
      if (this.form.id) {
        basic = {
          "id": this.form.id,
          "st": "ZC",
          "car_no": this.form.car_no,
          "driver_name": this.form.driver_name,
          "driver_lname": this.form.driver_lname,
          "contact_phone": this.form.contact_phone,
          "start_place": this.form.start_place,
          "end_place": this.form.end_place,
          "tachograph": this.form.tachograph,
          "dev_id": dev_id,
          "mark": this.form.mark
        };
      } else {
        basic = {
          "st": "ZC",
          "car_no": this.form.car_no,
          "driver_name": this.form.driver_name,
          "driver_lname": this.form.driver_lname,
          "contact_phone": this.form.contact_phone,
          "start_place": this.form.start_place,
          "end_place": this.form.end_place,
          "tachograph": this.form.tachograph,
          "dev_id": dev_id,
          "mark": this.form.mark
        };
      }
      let equipment = this.form2.sbList;
      for (let j = 0; j < equipment.length; j++) {
        let array = []
        for (let k = 0; k < equipment[j].dev_ids.length; k++) {
          let str = equipment[j].dev_ids[k].id;
          array.push(str)
        }
        equipment[j].dev_ids = array;
      }
      if (this.form.id) {
        this.$api.vehicleManagement.updatecar({ basic: basic, equipment: equipment }).then((data) => {
          if (data.code == 0) {
            this.$message({
              message: data.msg,
              type: 'success'
            });
            this.$emit("close");
          } else {
            this.$message.error(data.msg);
          }
        });
      } else {
        this.$api.vehicleManagement.addcar({ basic: basic, equipment: equipment }).then((data) => {
          if (data.code == 0) {
            this.$message({
              message: data.msg,
              type: 'success'
            });
            this.$emit("close");
          } else {
            this.$message.error(data.msg);
          }
        });
      }
    },
    closeDialog() {
      this.visible = false;
      this.$emit("close");
    },
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
  }
  .devicetab {
    position: absolute;
    top: 0.04rem;
    height: 0.4rem;
    line-height: 0.4rem;
    span {
      display: inline-block;
      margin-right: 0.3rem;
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
    }
    .el-form {
      ::v-deep .el-form-item {
        margin-bottom: .12rem;
      }
      .box {
        display: inline-block;
        width: 30%;
        ::v-deep .el-input__inner {
          height: 0.3rem !important;
        }
      }
      .box2 {
        width: 90%;
      }
      ::v-deep .el-form-item__error{
            padding-top: 0px;
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
      // .fr {
      //   margin-right: 3%;
      //   margin-top: 0.05rem;
      // }
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
       margin: 0.1rem 0 0.06rem;
          ::v-deep .el-form-item {
            margin-bottom: 0rem;
          }
          .box {
            display: inline-block;
            width: 26%;
            ::v-deep .el-form-item__label {
              color: #8c8c8c;
            }
            ::v-deep .el-input__inner {
              height: 0.26rem !important;
            }
          }
          .el-button {
            margin-top: 0.05rem;
            margin-right: 0.1rem;
          }
          .addinstrument,
          .deleteinstrument {
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
    .equipment {
      margin-bottom: 0.1rem;
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