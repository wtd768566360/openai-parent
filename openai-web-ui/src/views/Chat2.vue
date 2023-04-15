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
        <!-- 默认显示内容的div -->
        <div style="text-align: center; height: 80vh" v-if="this.showDefault">
          <p style="font-weight: bold;  font-size: 35px;">Open AI ChatGPT</p>
          <p style="font-weight: bold;  font-size: 20px;">软件持续开发更新中...</p>
          <span>基于 OpenAI 的 ChatGPT 自然语言模型人工智能对话。</span><br>
          <span>ChatGPT 可以为您做些什么：</span><br>
          <span>⑴. 帮写代码、文案、论文、小说</span><br>
          <span>⑵. 帮文案润色、写诗作词、谱曲</span><br>
          <span>⑶. 帮扮演面试官、书籍电影角色</span><br>
          <span>⑷. 陪聊倾诉、解忧解惑、讲故事</span><br>
          <span>⑸. 帮您翻译、绘图、设计、构思</span><br>
          <span><strong>注意：以上内容均为原创内容！！</strong></span><br>
          <span>【例1：写一篇800字关于春天的作文，要有花有草，有动物。】</span><br>
          <span>【例2：写一首关于爱情的歌，主题是午夜思念曾经深爱的她。】</span><br>
          <span>【例3：翻译xxxxx为英文.中文】</span><br>
          <span>【例4：用C#语言写一个SMTP邮件群发软件。】</span><br>
        </div>

        <!-- 聊天的Card 逐个添加 -->
        <div v-if="!this.showDefault" class="scroll_box">
          <!-- <el-card class="box-card"
            v-for="(chat,index) in this.userQuerys"
            :key="index"
            >
              <div slot="header" class="clearfix">
                <span style="font-weight: blod;">Me:{{chat.user}}</span>
              </div>
              <div>
               Ai: {{ chat.system }}
              </div>
            </el-card> -->


          <div v-for="(chat, index) in this.userQuerys" :key="index" class="list_item" :id="'div-' + index">
            <div class="ti">回复时间：{{ chat.time }} <el-link @click="cancelRequest" :underline="false"
                type="danger">取消回复</el-link></div>
            <div class="mine">
              <!-- <img src="" alt=""> -->
              <strong>M: </strong>
              <div>{{ chat.user }}</div>
            </div>
            <!-- <div>{{ chat.system }}</div> -->
            <!-- <div v-html="chat.system"> Ai: </div> -->
            <div class="ai" :id="'div-' + index">
              <!-- <img src="" alt=""> -->
              <!-- <vue-markdown :source="chat.system" :options="{highlight: codeHighlight}" ></vue-markdown> -->
              <strong>A: </strong>
              <div v-html="chat.system" style="white-space: pre-wrap"></div>
              <!-- <div>{{chat.system}}</div> -->
              <!-- <mavon-editor v-model="chat.system"XSS
                style="width: 100%; height=100%; min-height: 20px; overflow: hidden; overflow-y: hidden;"
                :scrollStyle=true placeholder="Ai正在输入..." previewBackground="#ffffff" :boxShadow=false :subfield=false
                transition=true editable=false preview=true :toolbarsFlag=false defaultOpen="preview"></mavon-editor> -->
              <!-- <div v-highlight>
                {{ chat.system }}
              </div> -->
            </div>
          </div>
        </div>
      </div>
      <!-- 下部分  输入框 -->
      <div class="input">
        <el-form :rules="rules" class="queryForm">
          <el-input placeholder="请输入内容" id="userInput" v-model="ruleForm.msg" class="input-with-select"
            @keyup.enter.native="submitQuestion">
            <el-button slot="append" icon="el-icon-search" @click="submitQuestion" class="append"
              :loading="this.submit"></el-button>
          </el-input>
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
import { EventSourcePolyfill } from 'event-source-polyfill';
export default {
  name: "Chat",
  data() {
    return {
      // imgUrl: require('@/assets/favicon.ico'),
      ruleForm: {
        msg: ""//用户的输入
      },
      token: '',//用户的token令牌
      nikName: "",//用户昵称
      balanceCount: 0,//剩余提问次数
      userType: '',//用户类型 0普通用户 1管理员
      rules: {
        msg: [
          { required: true, message: '请输入你的问题或者你要发送的信息', trigger: 'blur' }
        ]
      },

      loading: false,//是否开启加载模式
      answer: '',//ai的回答用于上下文关联的
      chatRecords: [
        // {
        //   //'role': '',//角色
        //   //'msg': ''//内容
        // }
      ], //用户与AI的聊天记录
      showDefault: true,//是否展示默认介绍页面
      queryCount: 0,//提问总次数
      userQuerys: [

      ],
      submit: false,//提问的按钮
      eventSource: null//发送流数据请求的对象
    }
  },
  methods: {
    cancelRequest() {
      //按钮可以点击
      this.submit = false
      //关闭连接
      this.eventSource.close();
      //滑动页面
      this.myScroll()
      //添加聊天记录
      let reco = { 'role': 'system', 'content': this.answer }
      this.chatRecords.push(reco)
      this.answer = ''
    },
    codeHighlight(code) {
      // console.log("代码" + code)
      return hljs.highlightAuto(code).value;
    },
    submitQuestion() {

      if (this.ruleForm.msg.trim() === '') {
        this.$notify.error({
          title: '错误',
          message: "请输入您的问题！"
        });
        return;
      }
      if (this.submit == true) {
        this.$notify.error({
          title: '错误',
          message: "请等待Ai回答完成！"
        });
        return;
      }
      //开启回答模式
      this.submit = true
      this.ruleForm.msg = ''
      // if (this.balanceCount == 0 || this.balanceCount < 0) {
      //   //无查询次数
      //   this.$notify.error({
      //     title: '错误',
      //     message: "无查询次数，联系管理员充值！"
      //   });
      //   this.submit = false
      //   return;
      // }
      //关闭默认展示页面
      this.showDefault = false
      //滑动页面
      this.myScroll()
      //提升作用域
      let _this = this
      //获取用户输入
      let userInput = document.getElementById("userInput").value;
      //把用户的问题添加到问题集合里面
      let chat = { "user": userInput, "system": "" }
      _this.userQuerys.push(chat)
      //创建用户输入的对象
      let reco = { 'role': 'user', 'content': userInput }
      //添加到聊天记录对象数组里面
      _this.chatRecords.push(reco)
      var reqData = JSON.stringify(_this.chatRecords)
      request.post(
        '/chat/params',
        _this.chatRecords
      ).then(resp => {
        console.log(resp)
        // //请求数据的方法
        _this.eventSource = new EventSourcePolyfill('http://127.0.0.1:8080/chat/context/search/' + resp.data, {
          headers: {
            'token': localStorage.getItem('token')
          }
        });
        _this.eventSource.addEventListener("open", function (e) {
          _this.submit = true
          //获取最后一个元素 
          const now = new Date();
          _this.userQuerys[_this.userQuerys.length - 1].time = formatDate(now)
          // _this.answer = ''//清空上一次的回答
          //清空输入框的内容
          _this.ruleForm.msg = ''
          // 获取当前时间
          // 定义格式化函数
          function formatDate(date) {
            const year = date.getFullYear();
            const month = date.getMonth() + 1;
            const day = date.getDate();
            const hour = date.getHours();
            const minute = date.getMinutes();
            const second = date.getSeconds();
            // 返回格式化后的时间字符串
            return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
          }
        })
        /*
        * message：后端返回信息，格式可以和后端协商
        */
        _this.eventSource.addEventListener("message", function (e) {
          console.log(e.data)

          if (e.data === "[DONE]") {//说明数据传输完成
            //按钮可以点击
            _this.submit = false
            //关闭连接
            _this.eventSource.close();
            //滑动页面
            _this.myScroll()
            //添加聊天记录
            let reco = { 'role': 'system', 'content': _this.answer }
            _this.chatRecords.push(reco)
            _this.answer = ''
          } else {
            //拼接数据
            let msg = e.data
            //获取最后一个元素 
            let lastChat = _this.userQuerys[_this.userQuerys.length - 1]
            //修改ai聊天内容
            // lastChat.system += msg.replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\\n+/g, '<br/>');
            lastChat.system += msg.replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/\\n+/g, '<br/>');
            _this.answer += msg
          }

        })
        /*
        * error：错误（可能是断开，可能是后端返回的信息）
        */
        _this.eventSource.addEventListener("error", function (err) {
          // 类似的返回信息验证，这里是实例
          err && err.status === 401 && console.log('not authorized')
          if (err.status === 500) {
            //获取最后一个元素 
            let lastChat = _this.userQuerys[_this.userQuerys.length - 1]
            //修改ai聊天内容
            lastChat.system = '我卡住了，请重试！'
             //按钮可以点击
             _this.submit = false
            //关闭连接
            _this.eventSource.close();
          }
        })
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //跳转管理员页面
    goAdminViews() {
      this.$router.push('/admin')
    },
    //刷新用户信息
    flushUserInfo() {
      request.get("/user").then(resp => {
        //对剩余次数进行重新赋值 以保证数据是最新的

        this.nikName = resp.nikName
        this.balanceCount = resp.balanceCount
        this.userType = resp.userType
        if (resp.code == 503) {
          this.$notify.error({
            title: '错误',
            message: "会话过期,请重新登录!"
          });
          this.$router.push("/login")
        }
      })
    },
    //退出账户的方法
    exit() {
      localStorage.removeItem("token")
      this.$router.push("/login")
    },
    myScroll() {
      let cardId = 'div-' + (this.userQuerys.length - 1)
      // console.log(cardId)
      this.$nextTick(() => {
        var element = document.getElementById(cardId);
        element.scrollIntoView();
      });
    }
  },
  created() {
    //赋值token
    this.token = localStorage.getItem("token")

    //清空数据
    this.chatRecords = []
    this.userQuerys = []

  }
}
</script>


<style scoped>
.queryForm {
  border: 1px solid rgb(102, 177, 255);
}

.v-note-wrapper markdown-body {
  overflow: hidden;
  height: 100%;

}

.el-card__body,
.el-main {
  overflow-y: scroll;
  height: 160%;
  margin-bottom: 200px;
}

.scroll_box {
  max-width: 1100px;
  width: 90%;
  height: 80vh;
  position: fixed;
  overflow-y: auto;
  z-index: 999;
}

.input {
  max-width: 1100px;
  width: 90vw;
  position: fixed;
  z-index: 999999;
  bottom: 50px;
  left: auto;
}

.list_item {
  border: 1px solid rgb(230, 222, 222);
  width: auto;
  word-break: break-all;
  word-wrap: break-word;
  white-space: pre-wrap;
  background: #f3f1f1c9;
  border-radius: 10px;
  padding: 0px 0 0px 0;
  margin: 30px 0;
  position: relative;
}

.ti {
  position: absolute;
  top: -20px;
  left: 0;
  z-index: 999;
  font-size: 5px;
}


.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
  overflow-y: scroll;
  height: 200%;
  margin-bottom: 200px;
  scrollbar-width: none;
  /* firefox */
  -ms-overflow-style: none;
  /* IE 10+ */
}

#contitle {

  padding: 10px;
  border-radius: 20px;
  background-color: rgb(238, 238, 238);
}

.el-row {
  margin-bottom: 20px;

  &:last-child {
    margin-bottom: 0;
  }
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

.mine {
  margin-left: 5%;
  width: 90%;
  display: flex;
  /*justify-content: flex-end;*/
  justify-content: flex-start;
  margin-bottom: 0px;
  /*border-bottom: 1px solid rgb(141, 141, 141);*/
  padding-bottom: 12px;
}

.mine img,
.ai img {}

.ai {
  padding: 0 5% 10px 5%;
  width: 90%;
  display: flex;
  justify-content: flex-start;
  margin-top: -10px;
  background: rgb(255, 253, 253);
  min-height: 50px;
  border-radius: 0px 0px 7px 7px;
}

.mine div {
  padding: 5px 20px;
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;
  /*border-radius: 7px 7px 0 7px;*/
  /*background: rgb(149, 236, 105);*/
}

.ai div {
  padding: 5px 20px;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  border-radius: 0px 0px 7px 7px;

  /* background: rgb(229, 229, 229);*/
}

.mine div strong {
  position: relative;
  left: -2em;
}

.ai div strong {
  position: relative;
  left: -2em;
  top: 5px;
}

/* 隐藏垂直滚动条 */
::-webkit-scrollbar {
  width: 0;
  background-color: transparent;
}

/* 隐藏水平滚动条 */
::-webkit-scrollbar-horizontal {
  height: 0;
}

/* 隐藏滚动条上的按钮 */
::-webkit-scrollbar-button {
  display: none;
}

body {
  overflow: auto;
}
</style>
