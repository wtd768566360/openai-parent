// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'

import VueRouter from 'vue-router'//全局使用路由模块
//导入刚刚创建的路由表配置
import router from './router'//导入我们的路由配置
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import axios from 'axios'
import VueAxios from 'vue-axios'

//组件注册
import MyHead from './components/Header'
import MyContent from './components/Content'

Vue.use(VueRouter)
Vue.use(ElementUI);
Vue.use(VueAxios,axios)
Vue.config.productionTip = false

//全局注册组件
Vue.component("MyHead",MyHead)
Vue.component("MyContent",MyContent)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  //配置路由
  router,
  components: { App },
  template: '<App/>'
})
