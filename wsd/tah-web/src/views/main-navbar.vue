<template>
  <nav class="navbar">
    <div class="navbar-content">
      <div class="content-left">
        <img src="../assets/img/logo2.png" alt="">温湿度管理系统
      </div>
      <div class="content-right">
           2021-05-20 14:12 周四<i class="iconfont icon-ziyuan57"></i>鹿野
      </div>
    </div>
  </nav>
</template>
<script>
export default {};
</script>
<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  .navbar-content{
    display: flex;
    .content-right,.content-left{
      font-size: .14rem;
      font-family: PingFang SC;
      font-weight: 500;
      color: #666666;
    }
    .content-left{
      width: 80%;
      margin-left: .2rem;
      line-height: .2rem;
      img{
        width: .5rem;
        height: .5rem;
      }
    }
    .content-left *{
      vertical-align: middle;
    }
    .content-right{
      width: 20%;
      line-height: .5rem;
      .iconfont{
        padding-left: .2rem;
        padding-right: .2rem;
      }
    }
    .content-right *{
      vertical-align: middle;
    }
  }
}
</style>
