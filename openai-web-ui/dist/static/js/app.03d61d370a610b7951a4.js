webpackJsonp([11],{"+K7A":function(e,t){},MQXg:function(e,t){},NHnr:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o=n("7+uW"),r={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},staticRenderFns:[]};var a=n("VU/8")({name:"App",components:{}},r,!1,function(e){n("MQXg")},null,null).exports,i=n("/ocq"),s=n("vLgD"),l={name:"Login",data:function(){return{loginForm:{username:"",password:""},loginLoding:!1,loginRules:{username:[{required:!0,message:"请输入用户名",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"}]}}},methods:{goHelp:function(){console.log("页面跳转"),this.$router.push("/help")},handleSubmit:function(){var e=this;this.$refs.loginForm.validate(function(t){if(!t)return!1;e.loginLoding=!0,s.a.post("/system/login",{account:e.loginForm.username,password:e.loginForm.password}).then(function(t){console.log(t),0==t.code?(localStorage.setItem("token",t.data.token),localStorage.setItem("name",t.data.name),localStorage.setItem("account",t.data.account),localStorage.setItem("email",t.data.email),e.$notify({title:"成功",message:"恭喜您，登录成功！",type:"success"}),e.loginLoding=!1,e.$router.push("/chat")):(e.$notify.error({title:"错误",message:t.msg}),e.loginLoding=!1)})})},toReigster:function(){this.$router.push("/register")}}},u={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"login-wrapper"},[n("el-card",{staticClass:"login-card"},[n("div",{staticClass:"login-logo"},[n("h1",[e._v("ChatGPT系统登录")])]),e._v(" "),n("div",{staticClass:"login-form"},[n("el-form",{ref:"loginForm",attrs:{model:e.loginForm,rules:e.loginRules,"label-position":"left","label-width":"80px"}},[n("el-form-item",{attrs:{label:"账号",prop:"username"}},[n("el-input",{attrs:{placeholder:"请输入用户名"},model:{value:e.loginForm.username,callback:function(t){e.$set(e.loginForm,"username",t)},expression:"loginForm.username"}})],1),e._v(" "),n("el-form-item",{attrs:{label:"密码",prop:"password"}},[n("el-input",{attrs:{type:"password",placeholder:"请输入密码"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleSubmit.apply(null,arguments)}},model:{value:e.loginForm.password,callback:function(t){e.$set(e.loginForm,"password",t)},expression:"loginForm.password"}})],1),e._v(" "),n("el-form-item",[n("el-button",{attrs:{type:"primary",loading:this.loginLoding},on:{click:e.handleSubmit}},[e._v("登录")]),e._v(" "),n("el-button",{attrs:{type:"success"},on:{click:e.toReigster}},[e._v("注册账号")])],1)],1),e._v(" "),n("el-link",{attrs:{type:"danger"},on:{click:e.goHelp}},[e._v("忘记账号密码？")])],1)])],1)},staticRenderFns:[]};var c=n("VU/8")(l,u,!1,function(e){n("xWRv")},"data-v-6d4e3fb2",null).exports;o.default.use(i.a);var d=new i.a({routes:[{path:"/",name:"Index",component:function(){return n.e(9).then(n.bind(null,"mlqX"))},redirect:"/chat",children:[{path:"/chat",name:"Chat",component:function(){return n.e(0).then(n.bind(null,"7/ED"))}},{path:"/about",name:"About",component:function(){return n.e(2).then(n.bind(null,"vu9I"))}},{path:"/userInfo",name:"userInfo",component:function(){return n.e(4).then(n.bind(null,"tKxd"))}},{path:"/restpwd",name:"restpwd",component:function(){return n.e(5).then(n.bind(null,"PJMS"))}}]},{path:"/login",name:"Login",component:c},{path:"/register",name:"Register",component:function(){return n.e(8).then(n.bind(null,"tcoL"))}},{path:"/help",name:"help",component:function(){return n.e(6).then(n.bind(null,"LbE0"))}},{path:"/account",name:"Account",component:function(){return n.e(7).then(n.bind(null,"0ZhZ"))}},{path:"/error",name:"error",component:function(){return n.e(3).then(n.bind(null,"xLdf"))}}]});d.beforeEach(function(e,t,n){var o=!["/account","/help","/register","/login","/error"].includes(e.path),r=localStorage.getItem("token");if(o&&!r)return n("/login");n()});var m=d,p=n("zL8q"),g=n.n(p),f=(n("tvR6"),n("mtWM")),h=n("aLYK"),v={name:"Head",data:function(){return{title:"ChatGPT",auth:"BY NIIT_CXL",activeIndex:"1",type:localStorage.getItem("userType")}},methods:{handleSelect:function(e,t){1==e&&this.$router.push("/chat"),2==e&&this.$router.push("/about")},exitLogin:function(){localStorage.removeItem("token"),this.$router.push("/login")},restPwd:function(){this.$router.push("/restpwd")},admin:function(){this.$router.push("/admin")}}},_={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-row",{attrs:{gutter:24}},[n("el-col",{attrs:{xs:0,sm:2,md:5,lg:5,xl:5}},[n("div",{staticClass:"grid-content"})]),e._v(" "),n("el-col",{attrs:{xs:24,sm:20,md:14,lg:14,xl:14}},[n("div",{staticClass:"grid-content"},[n("el-menu",{staticClass:"el-menu-demo",attrs:{"default-active":e.activeIndex,mode:"horizontal"},on:{select:e.handleSelect}},[n("el-menu-item",{attrs:{index:"1"}},[e._v("ChatGPT")]),e._v(" "),n("el-menu-item",{attrs:{index:"2"}},[e._v("关于我们")]),e._v(" "),n("el-submenu",{attrs:{index:"3"}},[n("template",{slot:"title"},[e._v("个人中心")]),e._v(" "),n("el-menu-item",{attrs:{index:"3-1"}},[n("router-link",{staticClass:"router-link",attrs:{to:"/userInfo"}},[e._v("个人信息")])],1),e._v(" "),n("el-menu-item",{attrs:{index:"3-3"},on:{click:e.restPwd}},[e._v("修改密码")]),e._v(" "),1==this.type?n("el-menu-item",{attrs:{index:"3-4"},on:{click:e.admin}},[e._v("管理用户")]):e._e(),e._v(" "),n("el-menu-item",{attrs:{index:"3-5"},on:{click:e.exitLogin}},[e._v("退出登录")])],2)],1)],1)]),e._v(" "),n("el-col",{attrs:{xs:0,sm:2,md:5,lg:5,xl:5}},[n("div",{staticClass:"grid-content"})])],1)},staticRenderFns:[]};var b=n("VU/8")(v,_,!1,function(e){n("i9Jd")},"data-v-5f008b8e",null).exports,x={render:function(){var e=this.$createElement;return(this._self._c||e)("router-view")},staticRenderFns:[]};var y=n("VU/8")({name:"Content"},x,!1,function(e){n("+K7A")},"data-v-0750df28",null).exports;o.default.use(i.a),o.default.use(g.a),o.default.use(h.a,f.a),o.default.config.productionTip=!1,o.default.component("MyHead",b),o.default.component("MyContent",y),new o.default({el:"#app",router:m,components:{App:a},template:"<App/>"})},i9Jd:function(e,t){},tvR6:function(e,t){},vLgD:function(e,t,n){"use strict";var o=n("//Fk"),r=n.n(o),a=this,i=n("mtWM").a.create({baseURL:"http://web.niitcxl.cn/api/",timeout:12e4});i.interceptors.request.use(function(e){return e.headers["Content-Type"]="application/json;charset=utf-8",e.headers.token=localStorage.getItem("token"),e},function(e){return r.a.reject(e)}),i.interceptors.response.use(function(e){var t=e.data;return"blob"===e.config.responseType?t:"text/event-stream;charset=UTF-8"===e.headers["content-type"]?(console.log("流返回类型"),t):("string"==typeof t&&(t=t?JSON.parse(t):t),t)},function(e){return 401==e.response.status&&a.$router.push("/login"),r.a.reject(e)}),t.a=i},xWRv:function(e,t){}},["NHnr"]);
//# sourceMappingURL=app.03d61d370a610b7951a4.js.map