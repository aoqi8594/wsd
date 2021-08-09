<template>
  <div class="stairs">
    <div class="goback" @click="goback"><i class="iconfont iconweibiaoti-1_huaban1fuben4"></i>返回</div>
    <div class="jiantou" @click="queleft">
      <i class="el-icon-arrow-left"></i>
    </div>
    <el-scrollbar ref="myScrollbar" style="width: 90%; height: 0.5rem">
      <div class="stairway" @click="clicktier(index,item)" v-for="(item, index) in roomlist" :key="index">
        {{item.car_no}}
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
      parent: '',
      id: ''
    };
  },
  created() { },
  mounted() { },
  methods: {
    init(data, id, parent) {
      this.parent = parent;
      this.id = id;
      for (let i = 0; i < data.length; i++) {
        if (data[i].id == id) {
          this.room = i;
        }
      }
      this.roomlist = data;
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
      this.$emit('returnvalue', item.id,this.parent);
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
    },
    goback() {
      this.$router.push({ name: this.parent, params: { id: this.id } })
    },
  }
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
    width: 100px;
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
      left: 36px;
      background-color: #1a90fd;
      border-radius: 1px;
    }
  }
}
</style>