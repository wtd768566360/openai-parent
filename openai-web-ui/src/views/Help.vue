<template>
  <div class="help-wrapper">
    <el-card class="help-card">
      <div class="help-logo">
        <!-- 在此添加你的应用程序的标志 -->
        <h1>重置密码</h1>
        <el-link type="info">为了保护你的帐号安全，我们需要验证你的身份，验证通过后即可找回你的密码。
        </el-link>
        <!-- <img src="../assets/logo.png"> -->
      </div>
      <div class="rest-form">

        <el-form ref="restForm" :model="restForm" :rules="restRules" label-position="left" label-width="80px">
          <el-form-item label="账号" prop="username">
            <el-input v-model="restForm.username" placeholder="请输入账号"></el-input>
          </el-form-item>

          <el-form-item label="绑定邮箱" prop="email">
            <el-input type="email" v-model="restForm.email" placeholder="请输入该账号绑定的邮箱"
              @keyup.enter.native="handleSubmit"></el-input>
          </el-form-item>

          <el-form-item label="验证码" prop="code">
            <el-input v-model="restForm.code" placeholder="验证码" style="width: 100px;"></el-input>
            <el-button @click="getCode" :loading=this.codeLoding type="text">{{ this.codeText }}</el-button>
          </el-form-item>

          <el-form-item label="新密码" prop="password">
            <el-input type="password" v-model="restForm.password" placeholder="请输入新密码"
              @keyup.enter.native="handleSubmit"></el-input>
          </el-form-item>

          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input type="password" v-model="restForm.confirmPassword" placeholder="请再次输入密码"
              @keyup.enter.native="handleSubmit"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" :loading=this.restLoding @click="handleSubmit">重置密码</el-button>
          </el-form-item>
        </el-form>

        <el-link type="danger" @click="goAccount">忘记账号？</el-link>
        <el-link type="primary" @click="toLogin" style="margin-left: 130px;">点击返回登陆页面</el-link>
      </div>
    </el-card>
  </div>
</template>

<script>
import request from '../utils/request'
export default {
  name: 'help',
  data() {
    return {
      restForm: {
        username: '',
        email: '',
        code: '',//验证码
        password: '',//密码
        confirmPassword: ''//密码
      },
      codeTime: 60,//用于验证码的倒计时
      codeText: "点击获取验证码",
      codeLoding: false,//是否开启验证码的加载
      restLoding: false,
      restRules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value === this.restForm.password) {
                callback()
              } else {
                callback(new Error('两次输入的密码不一致'))
              }
            },
            trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    //跳转获取账号页面
    goAccount() {
      this.$router.push("/account")
    },
    handleSubmit() {
      this.$refs.restForm.validate(valid => {
        if (valid) {
          // 在此添加登录逻辑
          this.restLoding = true
          request.post('/system/forget/password', {
            'account': this.restForm.username,
            'email': this.restForm.email,
            'code': this.restForm.code,
            'password': this.restForm.confirmPassword
          }).then(resp => {
            //密码修改成功！
            if (resp.code == 0) {
              //请求成功！ 删除原来的token
              localStorage.removeItem("token")
              //提示信息
              this.$notify({
                title: '成功',
                message: '恭喜您，密码修改成功！',
                type: 'success'
              });
              this.restLoding = false;
              this.$router.push("/login")
            } else {
              this.$notify.error({
                title: '错误',
                message: resp.msg
              });
              this.restLoding = false;
            }
          })
        } else {
          return false
        }
      })
    },
    //跳转登录页面
    toLogin() {
      this.$router.push("/login")
    },


    //获取验证码的方法
    getCode() {
      if (this.restForm.email) {
        //邮箱存在值 
        //判断邮箱 是否符合规则
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (emailRegex.test(this.restForm.email)) {
          //开启加载
          this.codeLoding = true
          //发送请求获取重置密码的验证码
          request.post("/email/forget/password/code", {
            email: this.restForm.email,
            account: this.restForm.username
          }).then(resp => {
            console.log(resp)
            if (resp.code == 0) {
              //提示信息
              this.$message({
                message: resp.msg,
                type: 'success'
              });
              //开启倒计时
              var timer = setInterval(() => {
                // 显示倒计时
                this.codeTime--
                if (this.codeTime <= 0) {
                  // 如果倒计时结束，恢复按钮状态
                  clearInterval(timer);
                  this.codeLoding = false
                  this.codeText = "点击获取验证码"
                  this.codeTime = 60
                } else {
                  this.codeText = this.codeTime + '秒后重试！'
                }
              }, 1000)
            } else {  //邮箱未绑定该账号
              this.$message({
                message: resp.msg,
                type: 'error'
              });
              //恢复获取验证码的按钮
              clearInterval(timer);
              this.codeLoding = false
              this.codeText = "点击获取验证码"
              this.codeTime = 60
            }
            //邮箱错误
          })
        } else {
          this.$message({
            message: '请先填写正确的邮箱！',
            type: 'error'
          });
        }
      } else {
        this.$message({
          message: '请先填写邮箱！',
          type: 'error'
        });
      }
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
.help-wrapper {
  background-color: #f2f2f2;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.help-card {
  width: 400px;
}

.help-logo {
  text-align: center;
  margin-bottom: 20px;
}

.rest-form {
  padding: 20px;
}
</style>
