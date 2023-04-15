<template>
  <div class="register-wrapper">
    <el-card class="register-card">
      <div class="register-logo">
        <!-- 在此添加你的应用程序的标志 -->
        <h1>ChatGPT系统注册</h1>
      </div>
      <div class="register-form">
        <el-form ref="registerForm" status-icon :model="registerForm" :rules="registerRules" label-position="left"
          label-width="80px">
          <el-form-item label="账号" prop="username">
            <el-input v-model="registerForm.username" placeholder="请输入账号（作为登录账号）"></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nikName">
            <el-input v-model="registerForm.nikName" placeholder="请输入您的昵称"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <el-input v-model="registerForm.code" placeholder="请输入验证码" style="width: 100px;"></el-input>
            <el-button @click="getCode" :loading=this.codeLoding type="text">{{ this.codeText }}</el-button>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="registerForm.password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请确认密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSubmit">注册</el-button>
          </el-form-item>
        </el-form>
        <el-button @click="toLogin" type="text" style="margin-left: 160px;">以有账号？点击立即登录</el-button>
      </div>
    </el-card>
  </div>
</template>
  
<script>
import request from '../utils/request';
export default {
  name: 'Register',
  data() {
    //校验用户名
    var checkUserName = (rule, value, callback) => {
      //用户名正则，4到16位（字母，数字，下划线，减号）
      var uPattern = /^[a-zA-Z0-9_-]{4,16}$/;
      if (!uPattern.test(value)) {
        callback(new Error('长度4到16位(字母,数字,下划线,减号)'));
      } else {
        callback();
      }
    }
    return {
      registerForm: {
        username: '',//登录账号
        nikName: '',//昵称
        email: '',//邮箱
        code: '',//验证码
        password: '',//密码
        confirmPassword: ''//密码
      },
      codeLoding: false,//是否开启验证码的加载
      codeText: "点击获取验证码",
      codeTime: 60,//用于验证码的倒计时
      registerRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { validator: checkUserName, trigger: 'blur' }
        ],
        nikName: [
          { required: true, message: '请输入昵称', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value === this.registerForm.password) {
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
    handleSubmit() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          // 在此添加注册逻辑
          // console.log(this.registerForm)
          //发起请求进行注册
          request.post("/system/registry", {
            "account": this.registerForm.username,
            "name": this.registerForm.nikName,
            "phone": this.registerForm.phone,
            "email": this.registerForm.email,
            "password": this.registerForm.confirmPassword,
            "code": this.registerForm.code
          }).then(resp => {
            console.log(resp)
            if (resp.code == 0) {
              this.$message({
                message: '恭喜你，注册成功！',
                type: 'success'
              });
              //跳转到登录页面
              this.$router.push("/login")
            } else {
              //错误信息  
              this.$message({
                message: resp.msg,
                type: 'error'
              });
              // 如果倒计时结束，恢复按钮状态
              clearInterval(timer);
              this.codeLoding = false
              this.codeText = "点击获取验证码"
              this.codeTime = 60
            }
          })
        } else {
          return false
        }
      })
    },
    toLogin() {
      this.$router.push("/login")
    },
    //获取验证码的方法
    getCode() {
      if (this.registerForm.email) {
        //邮箱存在值 
        //判断邮箱 是否符合规则
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (emailRegex.test(this.registerForm.email)) {
          //开启加载
          this.codeLoding = true
          //发送请求获取验证码
          request.post("/email/create/code", {
            account: this.registerForm.username,
            email: this.registerForm.email
          }).then(resp => {
            if (resp.code == 0) {
              //提示信息
              this.$message({
                message: '验证码发送成功！请注意查看邮箱',
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
            } else {
              //错误信息  
              //邮箱绑定其他账号
              this.$message({
                message: resp.msg,
                type: 'error'
              });
              // 如果倒计时结束，恢复按钮状态
              clearInterval(timer);
              this.codeLoding = false
              this.codeText = "点击获取验证码"
              this.codeTime = 60

            }
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
  }
}
</script>
  
<style scoped>
.register-wrapper {
  background-color: #f2f2f2;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-card {
  width: 400px;
}

.register-logo {
  text-align: center;
  margin-bottom: 20px;
}

.register-form {
  padding: 20px;
}
</style>
  