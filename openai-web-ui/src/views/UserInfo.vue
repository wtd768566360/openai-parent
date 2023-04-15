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
        <h1>个人信息</h1>
        <el-form label-position="left" v-loading="loading" label-width="80px" :model="userInfo">
          <el-form-item label="账号:">
            <el-input v-model="userInfo.userName" disabled></el-input>
          </el-form-item>
          <el-form-item label="昵称:">
            <el-input placeholder="请输入昵称" v-model="userInfo.nikName" class="input-with-select">
              <el-button slot="append" @click="onSubmit()" :disabled="this.update" icon="el-icon-edit">保存</el-button>
            </el-input>
          </el-form-item>
          <el-form-item label="邮箱:">
            <el-input v-model="userInfo.email" disabled></el-input>
          </el-form-item>
          <!-- <el-form-item label="手机号:">
            <el-input v-model="userInfo.phone"></el-input>
          </el-form-item> -->
          <!-- <el-form-item label="提问次数:">
            <el-input v-model="userInfo.totalCount" disabled></el-input>
          </el-form-item> -->
          <!-- <el-form-item label="剩余次数:">
            <el-input v-model="userInfo.balanceCount" disabled></el-input>
          </el-form-item> -->
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
  name: "userInfo",
  data() {
    return {
      userInfo: {
        userName: '',//账号
        nikName: '',//昵称
        email: '',//邮箱
        phone:'',//手机号码
        totalCount: 0,//总提问次数
        balanceCount: 0//剩余有效提问次数
      },
      loading: false,//是否开启加载
      update: false,//是否禁用按钮
      lastClickTime:0//上一次点击的时间
    }
  },
  methods: {
    getUserData() {
        this.userInfo.userName = localStorage.getItem("account")//账号
        this.userInfo.nikName = localStorage.getItem("name")//昵称
        this.userInfo.email = localStorage.getItem("email")//邮箱 
    },
    onSubmit() {
      var currentTime = Date.now();
      if (currentTime - this.lastClickTime >= 3000) {
        // 执行按钮的点击操作
        //非空判断  
        if (!this.checkValue(this.userInfo.nikName)) {
          this.$notify.error({
            title: '错误',
            message: "昵称不能为空！"
          });
          return
        }

        request.put("/system/modify",
          {
            "name": this.userInfo.nikName,
            "phone":this.userInfo.phone,
            "email":this.userInfo.email
          }
        ).then(resp => {
          localStorage.setItem("nikName", this.userInfo.nikName)
          if (resp.code == 0) {
            this.$notify({
              title: '成功',
              message: '保存成功！',
              type: 'success'
            });
           this.lastClickTime=Date.now()
          } else {
            this.$notify.error({
              title: '错误',
              message: "更新失败！请联系管理员"
            });
          }

        })
        this.lastClickTime = currentTime;
      } else {
        this.$notify.error({
          title: '错误',
          message: "请勿频繁点击！3秒后重试"
        });
      }

    },
    checkValue(value) {
      if (value == null || value == "") {
        return false;
      } else {
        return true;
      }
    }
  },
  created() {
    this.getUserData()

  }
}
</script>
<style>
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
</style>