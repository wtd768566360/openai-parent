<template>
  <el-row :gutter="24">

    <!-- 左边 缩进 -->
    <el-col :xs="0" :sm="2" :md="5" :lg="5" :xl="5">
      <div class="grid-content "></div>
    </el-col>
    <!-- 内容主体 行 -->
    <el-col :xs="24" :sm="20" :md="14" :lg="14" :xl="14">
      <!-- 内容主体的div -->
      <div class="grid-content">
        <h1>修改密码</h1>
        <el-form label-position="top" :rules="restPwdRole" ref="restPwdRole" label-width="80px" :model="restPwd">
          <el-form-item label="原密码:" prop="oled">
            <el-input v-model="restPwd.oled" placeholder="请输入原始密码"></el-input>
          </el-form-item>
          <el-form-item label="新密码:" prop="pwd1">
            <el-input v-model="restPwd.pwd1" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码:" prop="pwd2">
            <el-input v-model="restPwd.pwd2" placeholder="请再次输入密码"></el-input>
          </el-form-item>
          <el-button type="primary" @click="submitPwd">修改密码</el-button>
        </el-form>
      </div>
    </el-col>
    <!-- 右边 缩进 -->
    <el-col :xs="0" :sm="2" :md="5" :lg="5" :xl="5">
      <div class="grid-content "></div>
    </el-col>

  </el-row>
</template>
<script>
import request from '../utils/request';
export default {
  name: "restPwd",
  data() {
    return {
      restPwd: {
        oled: '',//原始密码
        pwd1: '',//新密码
        pwd2: ''//确认密码
      },
      restPwdRole: {
        oled: [
          { required: true, message: '请输入原始密码', trigger: 'blur' }
        ],
        pwd1: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        pwd2: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value === this.restPwd.pwd1) {
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
    submitPwd() {
      // 执行按钮的点击操作
      //非空判断  
      if (!this.checkValue(this.restPwd.oled)) {
        this.$notify.error({
          title: '错误',
          message: "原始密码不能为空！"
        });
        return
      }

      if (!this.checkValue(this.restPwd.pwd1)) {
        this.$notify.error({
          title: '错误',
          message: "新密码不能为空！"
        });
        return
      }

      if (!this.checkValue(this.restPwd.pwd2)) {
        this.$notify.error({
          title: '错误',
          message: "新密码不能为空！"
        });
        return
      }

      request.patch("/system/password",
        {
          "oldPassword": this.restPwd.oled,//原始密码
          "newPassword": this.restPwd.pwd2//新密码
        }
      ).then(resp => {
        if (resp.code == 0) {
          this.$notify({
            title: '成功',
            message: '密码修改成功！',
            type: 'success'
          });
          //访问注销接口
          request.post("/system/logout").then(resp => {
            //删除jwt
            localStorage.removeItem("token")
          })
          //跳转登录页面
          this.$router.push("/login")
        } else {
          this.$notify.error({
            title: '错误',
            message: resp.msg
          });
        }
      })
    },
    checkValue(value) {
      if (value == null || value == "") {
        return false;
      } else {
        return true;
      }
    }
  }
}
</script>
<style>
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>