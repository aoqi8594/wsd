<template>
  <div class="build">
    <!-- 头部 -->
    <div class="operation">
      楼栋管理
      <div class="operation-button">
        <el-button size="mini" type="primary" @click="addbuilding">新增</el-button>
        <el-button size="mini" @click="removebuilding">移除</el-button>
      </div>
    </div>
    <!-- 主体楼栋 -->
    <el-checkbox-group v-model="checkList" @change='getdong'>
      <div class="build-dong">
        <div class="donglists" v-for="(item, index) in lists" :key="index">
          <div class="dong">
            <div class="dongname">
              <el-checkbox :label="item.id"></el-checkbox>
              <div @click="buildname(item.id)"> <i class="iconfont iconqita_huaban1fuben10"></i>{{item.build_name}}</div>
            </div>
            <div class="layer">
              <div class="layerul">
                <el-scrollbar ref="myScrollbar" style="height: calc(100vh - 2.07rem); width: 100%">
                  <div class="layerli" v-for="(val,key,inx) in item.bFloor" :key="inx">
                    <div class="storey">
                      <i class="iconfont iconqita_huaban1fuben9"></i>
                      {{key}}
                      <div class="storeyoperation">
                        <span v-if="inx == 0" @click="deletebFloor(item.id,key,item.bFloor,'1')"><i class="iconfont iconqita_huaban1fuben6"></i></span>
                        <span @click="deletebFloor(item.id,key,item.bFloor,'0')"><i class="iconfont iconqita_huaban1fuben7"></i></span>
                        <span @click="roomdialog(item.id,key,item.build_name)"><i class="iconfont iconqita_huaban1fuben8"></i></span>
                      </div>
                    </div>
                    <div class="storeyul">
                      <el-button class="storeyli" @click="roomopen(item.build_name,key,em)" size="mini" v-for="(em, ix) in val" :key="ix">{{ em.room_cd}}</el-button>
                    </div>
                  </div>
                </el-scrollbar>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-checkbox-group>
    <!-- <Roomdialog title="楼层管理" ref="floor" :build_name="build_name2" :build_id="build_id" :floor_cd="floor_cd" :show.sync="dialogTableVisible" width="8.78rem" @close="closefoom"> -->
    <Roomdialog title="楼层管理" ref="floor"  width="8.78rem" @close="closefoom">
    </Roomdialog>
    <!-- <Roomopen title="房间管理" :show.sync="dialogopen" width="10.6rem" ref="roomopen" :build_name="build_name2" :build_id="build_id" :floor_cd="floor_cd" @close="refresh"> -->
    <Roomopen title="房间管理"  width="10.6rem" ref="roomopen"  @close="refresh">
    </Roomopen>
    <el-dialog class="newbuilding" title="新增楼栋" :visible.sync="newbuilding" :close-on-click-modal="false" width="20%" @close="Shut">
      <el-form ref="form" :model="form" label-width="80px" :rules="rules">
        <el-form-item label="楼栋名称" prop="build_name">
          <el-input size="mini" v-model.trim="form.build_name"></el-input>
        </el-form-item>
        <el-form-item label="楼层数量" prop="build_num">
          <el-input size="mini" v-model.trim.number="form.build_num"></el-input>
        </el-form-item>
        <div class="boxbutton">
          <el-button size="mini" class="fr" type="primary" @click="onSubmit('form')">确认</el-button>
          <el-button size="mini" class="fr" @click="newbuilding = false">取消</el-button>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import Roomdialog from "@/components/Roomdialog.vue";
import Roomopen from "@/components/Roomopen.vue";
export default {
  components: {
    Roomdialog,
    Roomopen,
  },
  data() {
    return {
      max: 30,
      lists: [],
      // dialogTableVisible: false,
      dialogopen: false,
      checkList: [],
      // build_name2: '',//选中的楼栋名
      // build_id: '',//楼栋ID
      // floor_cd: '',//楼层号
      newbuilding: false,//新增楼栋弹框
      form: {
        build_name: '',
        build_num: ''
      },
      rules: {
        build_name: [
          { required: true, message: '请输入楼栋名称', trigger: 'blur' },
          { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        build_num: [
          { required: true, message: "请输入楼栋层数", trigger: "change" },
          { type: 'number', min: 1, max: 30, message: '楼层必须为1-30的整数' }
        ]
      }
    };
  },
  mounted() {
    this.getbuilding(); //获取楼栋列表信息
  },
  methods: {
    // 重置新增楼栋弹框
    Shut() {
      this.$refs['form'].resetFields();
    },
    onSubmit(formName) {
      let that = this;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let num = parseInt(that.form.build_num);
          let arrlou = [];
          that.$api.building.addbuilding({ build_name: that.form.build_name, all_floor_cd: num }).then((data) => {
            // that.$api.building.addbuilding({ build_name: that.form.build_name, all_floor_cd: arrlou.toString() }).then((data) => {
            if (data.code == 0) {
              that.newbuilding = false;
              that.$message({
                message: data.msg,
                type: 'success'
              });
              that.getbuilding();
              that.checkList = [];
            } else {
              that.$message.error(data.msg);
            }
          });
        } else {
          return false;
        }
      });
    },
    //获取楼栋列表信息
    getbuilding() {
      this.$api.building.getBuildList({}).then((data) => {
        if (data.code == 0) {
          this.lists = this.buildFloor(data.data.list)
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //楼栋数据处理
    buildFloor(list) {
      for (let i = 0; i < list.length; i++) {
        let floor = [];
        let room = [];
        let arr = {};
        if (list[i].all_floor_cd) {
          floor = list[i].all_floor_cd.split(",");
        }
        if (list[i].roomList.length > 0) {
          room = list[i].roomList;
        }
        if (floor.length > 0) {
          floor.map(item => {
            let key = item + "楼";
            arr[key] = [];
          })
        }
        for (let j = 0; j < room.length; j++) {
          for (let k in arr) {
            if (k === room[j].floor_cd + "楼") {
              arr[k].push(room[j])
            }
          }
        }
        list[i].bFloor = arr;
      }
      return list
    },
    //添加楼栋
    addbuilding() {
      this.newbuilding = true
    },
    //选中楼栋
    getdong(e) {
      this.checkList = e
    },
    //点击楼栋名
    buildname(id) {
      let that = this;
      this.$prompt('请输入新的楼栋名称', '修改楼栋名', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S/,
        inputErrorMessage: '新的楼栋名称不能为空'
      }).then(({ value }) => {
        this.editbuildingname(id, value)//修改楼栋名
      }).catch(() => {});
    },
    //修改楼栋名
    editbuildingname(id, name) {
      this.$api.building.updateFloor({ id: id, build_name: name }).then((data) => {
        if (data.code == 0) {
          this.$message({
            message: data.msg,
            type: 'success'
          });
          this.getbuilding();
          this.checkList = [];
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //移除楼栋
    removebuilding() {
      if (this.checkList.length != 0) {
        this.$confirm('此操作将移除楼栋, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.open();
        }).catch(() => { });
      } else {
        this.$message({
          message: '移除需要选择楼栋',
          type: 'warning'
        });
      }
    },
    open() {
      this.$api.building.removebuilding({ ids: this.checkList }).then((data) => {
        if (data.code == 0) {
          this.$message({
            message: data.msg,
            type: 'success'
          });
          this.getbuilding();
          this.checkList = [];
        } else {
          this.$message.error(data.msg);
        }
      });
    },
    //打开添加房间窗口
    roomdialog(id, key, name) {
      // let that = this;
      // this.floor_cd = parseInt(key);
      // this.build_id = id;
      // this.build_name2 = name;
      // setTimeout(function () {
      //   that.$refs.floor.drawLine()
      // }, 100);
      // this.dialogTableVisible = true;
      this.$refs.floor.drawLine(id,parseInt(key),name)
    },
    //查看编辑房间
    roomopen(build_name, key, em) {
      // this.dialogopen = true;
      this.$refs.roomopen.drawLine(build_name, key, em)
    },
    //关闭添加房间窗口刷新数据
    closefoom() {
      // this.dialogTableVisible = false;
      this.getbuilding();
    },
    //关闭房间管理窗口刷新数据
    refresh() {
      // this.dialogopen = false;
      this.getbuilding(); //获取楼栋列表信息
    },
    deletebFloor(id, key2, bFloor, flag) {
      if (flag == 0) {
        let arr = Object.keys(bFloor).length;
        if (arr == 1) {
          this.$message({
            message: '已经是最后一层了,不能在移除了',
            type: 'warning'
          });
          return false;
        }
        this.$confirm('此操作将移除楼层, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.request(id, key2, bFloor, flag);
        }).catch(() => {
          return false;
        });
      } else {
        this.request(id, key2, bFloor, flag);
      }
    },
    request(id, key2, bFloor, flag) {
      let arr = []
      for (let key in bFloor) {
        arr.push(parseInt(key))
      }
      Array.prototype.remove = function () {
        for (var i = 0; i < arguments.length; i++) {
          var ele = arguments[i];
          var index = this.indexOf(ele);
          if (index > -1) {
            this.splice(index, 1);
          }
        }
      };
      if (flag == 0) {
        arr.remove(parseInt(key2));
      } else {
        let num = arr[arr.length - 1];
        arr.push(num + 1);
      }
      this.$api.building.updateFloor({ id: id, all_floor_cd: arr.toString() }).then((data) => {
        if (data.code == 0) {
          this.$message({
            message: data.msg,
            type: 'success'
          });
          this.getbuilding()
        } else {
          this.$message.error(data.msg);
          // this.lists = [
          //   {
          //     id: "",
          //     room_cd: "",
          //     duty_name: "",
          //     contact_phone1: "",
          //     remark: ""
          //   }
          // ];
        }
      });
    },
    // addFloor(id) {
    //   this.$api.building.updateFloor({ id: id, all_floor_cd: arr.toString() }).then((data) => {
    //     if (data.code == 0) {
    //       this.lists = data.data.list
    //     } else {
    //       this.$message.error(data.msg);
    //       this.lists = [
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
    // }
  },
};
</script>
<style lang="scss" scoped>
.build {
  height: calc(100vh - 50px);
  background: #f5f7fa;
  .newbuilding {
    .el-form {
      .boxbutton {
        padding-bottom: 0.2rem;
        .el-button {
          margin-left: 0.2rem;
        }
      }
      .el-input {
        width: 80%;
      }
    }
  }
  .operation {
    height: 0.4rem;
    line-height: 0.4rem;
    padding-left: 0.2rem;
    font-size: 0.16rem;
    color: #1a1a1a;
    background-color: #fff;
    .operation-button {
      display: inline-block;
      float: right;
      margin-right: 0.2rem;
      .el-input {
        width: 20%;
        margin-right: 0.2rem;
      }
    }
  }
  .build-dong {
    margin: 0.2rem;
    display: flex;
    overflow-y: hidden;
    overflow-x: scroll;
    width: calc(100vw - 1.22rem);
    .donglists {
      display: flex;
      margin-right: 0.1rem;
      .dong {
        margin-bottom: 0.1rem;
        width: 4rem;
        height: calc(100vh - 1.5rem);
        background-color: #fff;
        border-radius: 0.05rem;
        .dongname {
          height: 0.56rem;
          line-height: 0.56rem;
          font-size: 0.2rem;
          font-weight: 500;
          color: #1a90fd;
          text-align: center;
          border-bottom: 1px solid #ebebeb;
          position: relative;
          .el-checkbox {
            position: absolute;
            top: 0;
            left: 3%;
            ::v-deep .el-checkbox__inner {
              width: 0.16rem;
              height: 0.16rem;
            }
            ::v-deep .el-checkbox__inner::after {
              height: 0.08rem;
              left: 0.05rem;
            }
          }
          ::v-deep .el-checkbox__label {
            font-size: 0.24rem;
            font-weight: 500;
            color: #1a90fd;
            display: none;
            line-height: 0;
          }
          .el-checkbox:last-of-type {
            margin-right: 0.1rem;
          }
          .iconqita_huaban1fuben10 {
            font-size: 0.24rem;
            color: #f0f0f0;
          }
        }
        .layer {
          height: calc(100% - 0.57rem);
          position: relative;
          .layerul {
            position: absolute;
            position: absolute;
            bottom: 0;
            width: 100%;
            .layerli {
              padding: 0 0.1rem 0.01rem;
              border-bottom: 1px solid #ebebeb;
              .storey {
                height: 0.4rem;
                line-height: 0.4rem;
                margin-bottom: 0.1rem;
                font-size: 0.16rem;
                color: #1a1a1a;
                .iconqita_huaban1fuben9 {
                  color: #cccccc;
                }
                .storeyoperation {
                  display: inline-block;
                  float: right;
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
              .storeyul {
                display: flex;
                flex-wrap: wrap;
                width: 100%;
                .storeyli {
                  margin-right: 0.1rem;
                  margin-bottom: 0.1rem;
                  margin-left: 0 !important;
                  background: #f2f7fd;
                  color: #75bcfc;
                  border: 1px solid #75bcfc;
                  opacity: 1;
                  border-radius: 0.04rem;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
