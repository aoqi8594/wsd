<template>
  <div class="mod-login">
    <el-container>
      <el-main></el-main>
      <el-aside width="30%">
        <img class="logo" src="../../assets/img/logo.png" alt="" />
        <p>温湿度管理系统</p>
        <el-form ref="loginForm" :model="loginForm" :rules="rules">
          <el-form-item class="login_form_item" prop="userName">
            <el-input
              type="text"
              size="meddium"
              v-model.trim="loginForm.userName"
              placeholder="用户账号"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item class="login_form_item" prop="password">
            <el-input
              type="password"
              size="meddium"
              clearable
              v-model.trim="loginForm.password"
              placeholder="输入密码"
              @paste.native.capture.prevent="handlePaste"
            ></el-input>
          </el-form-item>
          <el-form-item class="login_form_item login_item">
            <el-button type="primary" @click="submit">立即登录</el-button>
          </el-form-item>
        </el-form>
        <el-footer>
          技术支持：<img src="../../assets/img/logo2.png" alt="" />
        </el-footer>
      </el-aside>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      loading: false,
      loginForm: {
        userName: "",
        password: "",
      },
      rules: {
        userName: [{ trigger: "blur", required: true, message: "请输入用户名" }],
        password: [{ trigger: "blur", required: true, message: "请输入密码" }],
      },
    };
  },
  created() {},
  methods: {
    submit() {
      let that=this;
      this.$refs["loginForm"].validate( (valid) => { 
        if (valid) {
          // that.loading = true;
          // that.$api.user.login(that.loginForm).then(({data})=>{

          // });
           that.$router.push({name:'home'})
        }
      });
    },
  },
};
</script>
<style  lang="scss" scoped>
.mod-login {
  .el-aside {
    position: relative;
    color: #333;
    text-align: center;
    .logo {
      margin-top: 1rem;
      height: 0.9rem;
    }
    p {
      font-size: 0.18rem;
      font-family: Source Han Sans CN;
      font-weight: 400;
      color: #595959;
      letter-spacing: 0.06rem;
    }
    .el-form {
      width: 60%;
      margin: 0.6rem auto;
      .login_form_item {
        margin-bottom: 0.4rem;
        .el-button {
          width: 100%;
          height: 0.5rem;
          font-size: .17rem;
          border-radius: 0.5rem;
        }
      }
      ::v-deep .el-input__inner {
        height: 0.5rem !important;
        line-height: 0.5rem;
        border-radius: 0.5rem;
      }
    }
    .el-footer {
      width: 100%;
      position: absolute;
      bottom: 0;
      font-size: .16rem;
      font-family: Microsoft YaHei;
      font-weight: 400;
      color: #8c8c8c;
      img {
        width: 0.5rem;
        height: 0.5rem;
      }
    }
    .el-footer * {
      vertical-align: middle;
    }
  }
  .el-main {
    background-image: url(../../assets/img/background3.jpg);
    background-repeat: no-repeat;
    background-size: 100% 100%;
    -moz-background-size: 100% 100%;
    color: #333;
    text-align: center;
    height: 100vh;
    line-height: 160px;
  }
}
</style>