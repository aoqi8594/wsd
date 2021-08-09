<template>
  <div class="light-up2" :style="{ height: height }" >
    <p>报警信息</p>
    <el-scrollbar :style="{ width: '100%', height: height2 }" >
      <div class="lists" v-if="lists.length==0">
        <div style=" color: #666666;">暂无数据</div>
      </div>
      <div class="lists" v-if="lists.length!=0">
        <div class="list" v-for="(item, index) in lists" :key="index" @click="gopage(item.dev_no)">
         <span>{{item.dev_name}}报警，{{item.alarm_content}}</span> 
          <i class="iconfont iconqita2_huaban1fuben3 fr" ></i>
        </div>
      </div>
    </el-scrollbar>
  </div>
</template>
<script>
export default {
  props: {
    height: {
      type: [Number, String],
      default: "auto",
    },
    height2: {
      type: [Number, String],
      default: "auto",
    },
  },
  data() {
    return {
      lists: [],
    };
  },
  created() { },
  mounted() {

  },
  methods: {
    init(data) {
      this.lists = data;
    },
    gopage(dev_no){
      this.$router.push({name:'Alertmanager',params:{dev_no:dev_no}})
    }
  },
}; 
</script>
<style lang="scss" scoped>
.light-up2 {
  margin-top: 0.14rem;
  border: 1px solid #f0f0f0;
  opacity: 1;
  border-radius: 0.09rem;
  padding: 0.2rem 0;
  p {
    font-size: 0.14rem;
    font-weight: bold;
    color: #1a1a1a;
    padding: 0 0.2rem 0.1rem;
  }
  .lists {
    padding: 0 0.2rem;
    .list {
      height: 0.56rem;
      background: #ffffff;
      box-shadow: 0px 0.02rem 0.1rem rgba(0, 0, 0, 0.08);
      opacity: 1;
      margin-bottom: 0.08rem;
      border-radius: 0.08rem;
      border: 1px solid #eeeeee;
      line-height: 0.56rem;
      padding-left: 0.2rem;
      font-size: 0.14rem;
      font-weight: bold;
      color: #ff656d;
      
      span{
        display: inline-block;
        white-space: nowrap;
      text-overflow: ellipsis;
      overflow: hidden;
      word-break: break-all;
      width: 90%;
      }
      .iconfont {
        margin-right: 3%;
        color: #1a90fd;
        font-size: 0.24rem;
        font-weight: 400;
      }
    }
  }
}
</style>