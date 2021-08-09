<template>
  <div class="stairs">
    <div class="goback" @click="goback"><i class="iconfont iconweibiaoti-1_huaban1fuben4"></i>返回</div>
    <div class="tier">
      <el-cascader v-model="value" :options="options" size="mini" :props="{ expandTrigger: 'hover' }" @change="handleChange"></el-cascader>
    </div>
    <div class="jiantou" @click="queleft">
      <i class="el-icon-arrow-left"></i>
    </div>
    <el-scrollbar ref="myScrollbar" style="width: 74%; height: 0.5rem">
      <div class="stairway" @click="clicktier(index,item)" v-for="(item, index) in roomlist" :key="index">
        {{ item.room_cd }}
        <span class="xian" v-if="room == index"></span>
      </div>
    </el-scrollbar>
    <div class="jiantou" @click="queright">
      <i class="el-icon-arrow-right"></i>
    </div>
  </div>
</template>
<script>
export default {
  props: {},
  data() {
    return {
      roomlist: [],
      room: 0,
      value: [],
      options: [],
      originaldata: []
    };
  },
  created() { },
  mounted() { },
  methods: {
    //获取楼栋列表信息
    getbuilding(id, name, code, room_cd, tag) {
      this.$api.building.getBuildList({}).then((data) => {
        if (data.code == 0) {
          let datalist = this.buildFloor(data.data.list);
          this.originaldata = datalist;
          this.value[0] = id;
          this.value[1] = code + '楼';
          let options = [];
          for (let i = 0; i < datalist.length; i++) {
            let parentobj = {
              value: datalist[i].id,
              label: datalist[i].build_name,
              children: []
            }
            for (const j in datalist[i].bFloor) {
              let childobj = {
                value: j,
                label: j,
              }
              parentobj.children.push(childobj)
              if (datalist[i].id == this.value[0]) {
                if (j == this.value[1]) {
                  this.roomlist = datalist[i].bFloor[j];
                  if (tag == 1) {
                    for (let k = 0; k < this.roomlist.length; k++) {
                      if (this.roomlist[k].room_cd == room_cd) {
                        this.room = k
                      }
                    }
                  }
                }
              }
            }
            options.push(parentobj)
          }
          this.options = options;
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
    handleChange(value) {
      let array = this.originaldata;
      this.room = 0;
      for (let i = 0; i < array.length; i++) {
        if (value[0] == array[i].id) {
          this.roomlist = array[i].bFloor[value[1]]
          if (this.roomlist.length == 0) {
            this.$emit("callBackLayer", '')
          } else {
            this.$emit("callBackLayer", this.roomlist[0])
          }
        }
      }
    },
    goback() {
      this.$router.push({ name: localStorage.getItem("pash") })
    },
    queright() {
      let that = this;
      var t2 = window.setInterval(function () {
        that.$refs["myScrollbar"].wrap.scrollLeft =
          that.$refs["myScrollbar"].wrap.scrollLeft + 6;
      }, 10);
      setTimeout(function () {
        window.clearInterval(t2); // 去除定时器
      }, 100);
    },
    queleft() {
      let that = this;
      console.log(this.$refs["myScrollbar"].wrap.scrollLeft);
      var t2 = window.setInterval(function () {
        that.$refs["myScrollbar"].wrap.scrollLeft =
          that.$refs["myScrollbar"].wrap.scrollLeft - 6;
      }, 10);
      setTimeout(function () {
        window.clearInterval(t2); // 去除定时器
      }, 100);
    },
    clicktier(e, item) {
      this.room = e;
      this.$emit("callBackLayer", item)
    },
  },
};
</script>
<style lang="scss" scoped>
.stairs {
  height: 0.4rem;
  background-color: #fff;
  overflow: hidden;
  .goback {
    width: 4%;
    display: inline-block;
    float: left;
    height: 0.28rem;
    margin-top: 0.06rem;
    line-height: 0.28rem;
    font-size: 0.12rem;
    border-radius: 0.04rem;
    background-color: #1a90fd;
    text-align: center;
    color: #fff;
    margin-left: 1.5%;
    &:hover {
      background-color: #369efd;
      cursor: pointer;
    }
  }
  .tier {
    display: inline-block;
    float: left;
    height: 0.4rem;
    padding-left: 1%;
    width: 16%;
    line-height: 0.4rem;
    .iconfont {
      color: #cccccc;
      margin-left: 10%;
    }
  }
  .jiantou {
    display: inline-block;
    float: left;
    width: 2%;
    text-align: center;
    line-height: 0.4rem;
    font-size: 0.18rem;
    color: #cccccc;
    i {
      font-weight: 600;
    }
  }
  .el-scrollbar {
    float: left;
  }
  ::v-deep .el-scrollbar .el-scrollbar__wrap .el-scrollbar__view {
    white-space: nowrap;
    display: inline-block;
  }
  ::v-deep .el-scrollbar__wrap {
    overflow-x: hidden;
    height: 100%; //多出来的20px是横向滚动条默认的样式
  }
  .stairway {
    display: inline-block;
    width: 60px;
    height: 0.4rem;
    line-height: 0.4rem;
    text-align: center;
    color: #cccccc;
    position: relative;
    .xian {
      display: block;
      position: absolute;
      bottom: 1px;
      width: 30px;
      height: 0.02rem;
      left: 15px;
      background-color: #1a90fd;
      border-radius: 1px;
    }
  }
}
</style>