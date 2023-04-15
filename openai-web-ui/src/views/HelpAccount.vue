<template>
  <div class="help-wrapper">
    <el-card class="help-card">
      <div class="help-logo">
        <!-- 在此添加你的应用程序的标志 -->
        <h1>找回账号</h1>
        <el-link type="info">请填写注册时候绑定的邮箱，我们将会把您的账号信息发送到您的邮箱里！
        </el-link>
        <!-- <img src="../assets/logo.png"> -->
      </div>
      <div class="email-form">
        <el-form ref="emailForm" :model="emailForm" :rules="restRules" label-position="left" label-width="80px">
          <el-form-item label="绑定邮箱" prop="email">
            <el-input type="email" v-model="emailForm.email" placeholder="请输入账号绑定的邮箱"
              @keyup.enter.native="handleSubmit"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading=this.restLoding @click="handleSubmit">立即发送</el-button>
          </el-form-item>
          <el-link type="danger" @click="goHelp" style="margin-left: 220px;">忘记密码？</el-link>
        </el-form>


      </div>
    </el-card>
  </div>
</template>

<script>
import request from '../utils/request'
export default {
  name: 'Account',
  data() {
    return {
      emailForm: {
        email: '',
      },
      restLoding: false,
      restRules: {
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    //跳转修改密码页面
    goHelp() {
      this.$router.push("/help")
    },
    handleSubmit() {
      this.$refs.emailForm.validate(valid => {
        if (valid) {
          // 在此添加登录逻辑
          this.restLoding = true
          request.post('/email/find/account',{
            email:this.emailForm.email
          }).then(resp => {
            //密码修改成功！
            if (resp.code == 0) {
              //请求成功！ 提示信息
              this.$notify({
                title: '成功',
                message: resp.msg,
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

    //获取验证码的方法
    getCode() {
      if (this.emailForm.email) {
        //邮箱存在值 
        //判断邮箱 是否符合规则
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (emailRegex.test(this.emailForm.email)) {
          //开启加载
          this.codeLoding = true
          //发送请求获取重置密码的验证码
          request.post("/user/rest-code", {
            userName: this.emailForm.username,
            email: this.emailForm.email
          }).then(resp => {
            if (resp.code == 200) {
              //提示信息
              this.$message({
                message: '验证码发送成功！请注意查看邮箱....',
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
            }
            //邮箱错误
            if (resp.code == 509) {
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
            //邮箱未绑定该账号
            if (resp.code == 507) {
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

.email-form {
  padding: 20px;
}
</style>
