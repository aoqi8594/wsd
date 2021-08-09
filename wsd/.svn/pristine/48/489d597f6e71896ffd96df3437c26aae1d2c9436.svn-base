<template>
  <div class="stairs">
    <div class="tier">
      <el-select v-model="value" placeholder="请选择" size="mini" @change="handleChange">
        <el-option v-for="item in options" :key="item.id" :label="item.build_name" :value="item.id"></el-option>
      </el-select>
    </div>
    <div class="jiantou" @click="queleft">
      <i class="el-icon-arrow-left"></i>
    </div>
    <el-scrollbar ref="myScrollbar" style="width: 79%; height: 0.5rem">
      <div class="stairway" @click="clicktier(index,item)" v-for="(item, index) in roomlist" :key="index">
        {{ item}}
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
      value: '',//楼栋ID
      build_name: '',
      options: [],
      floor_cd: '',//楼层编号
    };
  },
  created() { },
  mounted() {
    this.getbuilding(); //获取楼栋列表信息
  },
  methods: {
    //获取楼栋列表信息
    getbuilding() {
      this.$api.building.getBuildList({}).then(data => {
        if (data.code == 0) {
          let datalist = this.buildFloor(data.data.list);
          this.value = datalist[0].id;
          this.build_name = datalist[0].build_name;
          this.options = datalist;
          let object = datalist[0].bFloor;
          let roomlist = [];
          for (const key in object) {
            roomlist.push(key)
          }
          this.roomlist = roomlist;
          this.floor_cd = roomlist[0].substr(0, roomlist[0].length - 1);
          this.$emit('returnvalue', this.value, this.floor_cd, this.build_name);
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
          });
        }
        for (let j = 0; j < room.length; j++) {
          for (let k in arr) {
            if (k === room[j].floor_cd + "楼") {
              arr[k].push(room[j]);
            }
          }
        }
        list[i].bFloor = arr;
      }
      return list;
    },
    queright() {
      let that = this;
      console.log(this.$refs["myScrollbar"].wrap.scrollLeft);
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
      this.floor_cd = item.substr(0, item.length - 1);
      this.$emit('returnvalue', this.value, this.floor_cd, this.build_name);
    },
    handleChange(value) {
      this.room = 0;
      this.value = value;
      for (let i = 0; i < this.options.length; i++) {
        if (this.options[i].id == value) {
          this.build_name = this.options[i].build_name;
          let object = this.options[i].bFloor;
          let roomlist = [];
          for (const key in object) {
            roomlist.push(key)
          }
          this.roomlist = roomlist;
          this.floor_cd = roomlist[0].substr(0, roomlist[0].length - 1);
          this.$emit('returnvalue', this.value, this.floor_cd, this.build_name);
        }
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.stairs {
  height: 0.4rem;
  background-color: #fff;
  overflow: hidden;
  .tier {
    display: inline-block;
    float: left;
    height: 0.4rem;
    padding-left: 2.5%;
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