<template>
    <!-- 当前行 -->
    <el-row :gutter="24">
            <!-- 左边 缩进 -->
        <el-col :xs="0" :sm="2" :md="5" :lg="5" :xl="5"><div class="grid-content "></div></el-col>
            <!-- 中间内容 -->
            <el-col :xs="24" :sm="20" :md="14" :lg="14" :xl="14">
                <div class="grid-content ">
                    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                        <el-menu-item index="1">ChatGPT</el-menu-item>
                        <el-menu-item index="2">关于我们</el-menu-item>
                        <el-submenu index="3">
                            <template slot="title">个人中心</template>
                            <el-menu-item index="3-1"><router-link class="router-link" to="/userInfo">个人信息</router-link></el-menu-item>
                            <!-- <el-menu-item index="3-2">邀请新用户</el-menu-item> -->
                            <el-menu-item index="3-3" @click="restPwd">修改密码</el-menu-item>
                            <el-menu-item index="3-4" v-if="this.type==1" @click="admin">管理用户</el-menu-item>
                            <el-menu-item index="3-5" @click="exitLogin">退出登录</el-menu-item>
                          </el-submenu>


                      </el-menu>
                </div>
        </el-col>
            <!-- 右边 缩进 -->
            <el-col :xs="0" :sm="2" :md="5" :lg="5" :xl="5"><div class="grid-content "></div></el-col>
    </el-row>
</template>


<script>
export default {
    name: "Head",
    data() {
        return {
            title: "ChatGPT",
            auth:"BY NIIT_CXL",
            activeIndex: '1',
            type:localStorage.getItem("userType")
        }
    },
    methods:{
        handleSelect(key, keyPath) {
        if(key==1){
            this.$router.push("/chat")
        }
        if(key==2){
            this.$router.push("/about")
        }

      },
      exitLogin(){//退出登录
        //删除token
        localStorage.removeItem("token")
        this.$router.push("/login")
      },
      restPwd(){//重置密码
        this.$router.push("/restpwd")
      },
      admin(){//跳转到管理员页面
        this.$router.push("/admin")

      }
    }
}
</script>


<style scoped>
.router-link{
    color: inherit;
    text-decoration: none;
}

.el-col {
    border-radius: 4px;
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: #d3dce6;
  }
  .bg-purple-light {
    background: #e5e9f2;
  }
  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

body{
    margin: 0px;
  }
.el-row {
    margin-bottom: 20px;

    &:last-child {
        margin-bottom: 0;
    }
}

#title {
    color: rgb(52, 53, 65);
}

.el-col {
    border-radius: 4px;
}

.bg-purple-dark {
    background: #99a9bf;
}

.bg-purple {
    background: #d3dce6;
}

.bg-purple-light {
    background: #e5e9f2;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}

.row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
}
</style>
