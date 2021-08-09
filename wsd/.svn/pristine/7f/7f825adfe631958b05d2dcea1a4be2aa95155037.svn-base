<template>
  <el-dialog class="room" :title="title" @close="Shut" :visible.sync="visible" :width="width" :top="top" :modal="modal" append-to-body :close-on-press-escape="false" destroy-on-close :close-on-click-modal="false" :show-close="false">
    <div class="roombutton">
      <el-button size="mini" type="primary" @click="saveroom">保存</el-button>
      <el-button size="mini" @click="closeDialog">取消</el-button>
    </div>
    <div class="roomcontent">
      <p>基本信息 </p>
      <el-form ref="form2" label-width="80px">
        <el-form-item class="box" label="楼栋:">
          <el-input size="mini" v-model="build_name" disabled></el-input>
        </el-form-item>
        <el-form-item class="box" label="楼层:">
          <el-input size="mini" v-model="floor_cd" disabled></el-input>
        </el-form-item>
      </el-form>
    </div>
    <div class="roomcontent roomdom">
      <p>房间设置</p>
      <el-scrollbar ref="myScrollbar" style="height:60vh; width: 100%">
        <el-form ref="environmentForm" label-width="80px" :model="environmentForm">
          <div class="" v-for="(item, index) in environmentForm.lists" :key="index">
            <el-form-item class="box" label="房间号:" :prop="'lists.' + index + '.room_cd'" :rules="{required: true, message: '房间号不能为空', trigger: 'blur'}">
              <el-input size="mini" v-model.trim="item.room_cd"></el-input>
            </el-form-item> 
            <el-form-item class="box" label="负责人:" :prop="'lists.' + index + '.duty_name'" :rules="{required: true, message: '负责人不能为空', trigger: 'change'}">
               <el-select v-model="item.duty_name" class="win50" placeholder="请选择" size="mini" @change="changeduty_name(index,item.duty_name)">
                <el-option v-for="(it, inex) in personchargelist" :key="inex" :label="it.user_name" :value="it.login_name"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item class="box" label="电话:" >
              <el-input size="mini" v-model.trim.number="item.contact_phone1" disabled></el-input>
            </el-form-item>
            <el-form-item class="box3">
              <span @click="removeroom(index)"><i class="iconfont iconqita_huaban1fuben7"></i></span>
              <span v-if="index==0" @click="addroom"><i class="iconfont iconqita_huaban1fuben6"></i></span>
            </el-form-item>
            <el-form-item class="box2" label="备注:" :prop="'lists.' + index + '.remark'">
              <el-input type="textarea" v-model.trim="item.remark"></el-input>
            </el-form-item>
          </div>
        </el-form>
      </el-scrollbar>
    </div>
  </el-dialog>
</template>
<script>
export default {
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
      default: "5vh",
    },
    modal: {
      type: Boolean,
      default: true,
    },
    // build_id: {
    //   type: [Number, String],
    //   default: "auto",
    // },
    // floor_cd: {
    //   type: [Number, String],
    //   default: "auto",
    // },
    // build_name: {
    //   type: [Number, String],
    //   default: "auto",
    // }
  },
  data() {
    return {
      floorList: [],
      form: {
        name: ''
      },
      build_name:'',
      floor_cd:'',
      build_id:'',
      environmentForm: {
        lists: []
      },
      personchargelist:[],
      visible:false
    };
  },
  computed: {
    // visible: {
    //   get() {
    //     return this.show;
    //   },
    //   set(val) {
    //     // 当visible改变的时候，触发父组件的 updateVisible方法，在该方法中更改传入子组件的 centerDialogVisible的值
    //     // this.$emit('updateVisible', val)
    //   },
    // },
  },
  created() { },
  mounted() {
    let that = this;
    this.getDriverList()
  },
  methods: {
    changeduty_name(num,e){
       let  array=this.personchargelist;
       for (let i = 0; i < array.length; i++) {
         if(array[i].login_name==e){
            this.environmentForm.lists[num].contact_phone1=array[i].mobile_phone;
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
    Shut() {
      this.$refs['environmentForm'].resetFields();
    },
    drawLine(id, key, name) {
      this.build_name = name;
      this.floor_cd = key;
      this.visible = true;
      this.build_id = id;
      this.$api.building.buildingfloor({ build_id: id, floor_cd:key }).then((data) => {
        if (data.code == 0) {
          let lists = []
          for (let i = 0; i < data.data.list.length; i++) {
            let lis = {
              id: data.data.list[i].id,
              room_cd: data.data.list[i].room_cd,
              duty_name: data.data.list[i].duty_name,
              contact_phone1: data.data.list[i].contact_phone1,
              remark: data.data.list[i].remark
            }
            lists.push(lis)
          }
          this.environmentForm.lists = lists;
        } else {
          this.environmentForm.lists = [
            {
              id: "",
              room_cd: "",
              duty_name: "",
              contact_phone1: "",
              remark: ""
            }
          ];
        }
      });
    },
    // drawLine() {
    //   this.$api.building.buildingfloor({ build_id: this.build_id, floor_cd: this.floor_cd }).then((data) => {
    //     if (data.code == 0) {
    //       let lists = []
    //       for (let i = 0; i < data.data.list.length; i++) {
    //         let lis = {
    //           id: data.data.list[i].id,
    //           room_cd: data.data.list[i].room_cd,
    //           duty_name: data.data.list[i].duty_name,
    //           contact_phone1: data.data.list[i].contact_phone1,
    //           remark: data.data.list[i].remark
    //         }
    //         lists.push(lis)
    //       }
    //       this.environmentForm.lists = lists;
    //     } else {
    //       this.environmentForm.lists = [
    //         {
    //           id: "",
    //           room_cd: "",
    //           duty_name: "",
    //           contact_phone1: "",
    //           remark: ""
    //         }
    //       ];
    //     }
    //   });
    // },
    //添加房间
    addroom() {
      let obj = {
        id: "",
        room_cd: "",
        duty_name: "",
        contact_phone1: "",
        remark: ""
      };
      this.environmentForm.lists.push(obj)
    },
    //移除房间
    removeroom(index) {
      let arr = this.environmentForm.lists.splice(index, 1);
    },
    // 保存房间
    saveroom() {
      this.$refs['environmentForm'].validate((valid) => {
        if (valid) {
          let res = {
            build_id: this.build_id,
            floor_cd: this.floor_cd,
            roomList: this.environmentForm.lists
          }
          this.$api.building.roommanage(res).then((data) => {
            if (data.code == 0) {
              this.$message({
                message: data.msg,
                type: 'success'
              });
              this.$emit("close");
              this.visible = false;
            } else {
              this.$message.error(data.msg);
            }
          });
        } else {
          return false;
        }
      });
    },
    closeDialog() {
      this.visible = false;
      this.$emit("close");
    },
  },
};
</script>
<style lang="scss" scoped>
.room {
  ::v-deep .el-dialog__header {
    border-bottom: 1px solid #e6e6e6;
    padding: 0;
    height: 0.48rem;
    line-height: 0.48rem;
    .el-dialog__title {
      padding-left: 0.25rem;
      font-size: 0.18rem;
      font-weight: bold;
      color: #1a1a1a;
    }
  }
  ::v-deep .el-dialog__body {
    padding: 0.16rem;

    .roombutton {
      position: absolute;
      top: 0.1rem;
      right: 0.25rem;
      .el-button--mini {
        padding: 0.06rem 0.17rem;
        font-size: 0.14rem;
      }
    }
    .roomdom {
      margin-top: 0.16rem;
    }
    .roomcontent {
      border: 1px solid #f0f0f0;
      opacity: 1;
      border-radius: 0.09rem;
      p {
        height: 0.4rem;
        line-height: 0.4rem;
        padding-left: 0.2rem;
        font-size: 0.14rem;
        font-weight: bold;
        color: #1a1a1a;
      }
      .el-form {
        .el-form-item {
          margin-bottom: 0.14rem;
          .el-form-item__error {
            padding-top: 0;
          }
        }
        .box {
          display: inline-block;
          width: 30%;
          .el-form-item__label {
            color: #8c8c8c;
          }
        }
        .box2 {
          width: 90%;
          .el-form-item__label {
            color: #8c8c8c;
          }
        }
        .box3 {
          display: inline-block;
          width: 10%;
          .el-form-item__label {
            color: #8c8c8c;
          }
          .el-form-item__content {
            margin-left: 0 !important;
            span {
              text-align: center;
              line-height: 0.2rem;
              display: inline-block;
              width: 0.2rem;
              height: 0.2rem;
              border-radius: 50%;
              border: 1px solid #e5e5e5;
              margin-left: 0.1rem;
              .iconfont {
                font-size: 0.14rem;
                color: #999999;
              }
            }
          }
        }
      }
    }
  }
}
</style>