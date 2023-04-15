<template>
  <div class="login-wrapper">
    <el-card class="login-card">
      <div class="login-logo">
        <!-- 在此添加你的应用程序的标志 -->
        <h1>ChatGPT系统登录</h1>
        <!-- <img src="../assets/logo.png"> -->
      </div>
      <div class="login-form">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" label-position="left" label-width="80px">
          <el-form-item label="账号" prop="username">
            <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"
              @keyup.enter.native="handleSubmit"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading=this.loginLoding @click="handleSubmit">登录</el-button>
            <el-button type="success" @click="toReigster">注册账号</el-button>
          </el-form-item>

        </el-form>

        <!-- <el-button @click="toReigster" type="text" style="margin-left: 160px;">没有账号？点击立即注册</el-button> -->

        <el-link type="danger" @click="goHelp">忘记账号密码？</el-link>

      </div>
    </el-card>
  </div>
</template>

<script>
import request from '../utils/request'
export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginLoding: false,
      loginRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    goHelp() {
      console.log("页面跳转")
      this.$router.push("/help")
    },
    handleSubmit() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          // 在此添加登录逻辑
          this.loginLoding = true
          request.post('/system/login', {
            'account': this.loginForm.username,
            'password': this.loginForm.password
          }).then(resp => {
            console.log(resp)
            if (resp.code == 0) {
              //登录成功  token,name，account存入浏览器存储
              localStorage.setItem("token", resp.data.token)
              localStorage.setItem("name", resp.data.name)
              localStorage.setItem("account", resp.data.account)
              localStorage.setItem("email", resp.data.email)
              //提示框
              this.$notify({
                title: '成功',
                message: '恭喜您，登录成功！',
                type: 'success'
              });
              //关闭加载
              this.loginLoding = false;
              //路由跳转
              this.$router.push("/chat")
            } else {
              this.$notify.error({
                title: '错误',
                message: resp.msg
              });
              this.loginLoding = false;
            }
          })
        } else {
          return false
        }
      })
    },
    //跳转注册页面
    toReigster() {
      this.$router.push("/register")
    }
  },
  // 挂载之前
  // beforeMount() {
  //   //封装axios的时候，已经设置了/api作为基地址，所以在此没有添加/api
  //   let httpURL = `/baidu/?city=${this.$route.params.city}`; //目标地址后接的详细地址
  //   request.get(httpURL).then(function (res) {
  //     console.log(res);
  //   });
  // }
}
</script>

<style scoped>
.login-wrapper {
  background-color: #f2f2f2;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-card {
  width: 400px;
}

.login-logo {
  text-align: center;
  margin-bottom: 20px;
}

.login-form {
  padding: 20px;
}
</style>
