import Vue from 'vue'
import Router from 'vue-router'


//导入自定义组件 这个是常用组件
import Login from '../views/Login'

//一下是懒加载的页面
const Index = () => import('@/views/Index');
const Chat = () => import('@/views/Chat');
const Chat2 = () => import('@/views/Chat2');
const Register = () => import('@/views/Register');
const Help = () => import('@/views/Help');
const HelpAccount = () => import('@/views/HelpAccount');
const About = () => import('@/views/About');
const UserInfo = () => import('@/views/UserInfo');
const restPwd = () => import('@/views/RestPwd');
const error = () => import('@/views/Error');

//安装路由
Vue.use(Router)

//配置路由
const router = new Router({
  routes:[
    {
      //路由路径
      path: "/",
      //路由名称
      name: 'Index',
      //路由组件
      component: Index,
      //默认访问回话页面
      redirect: "/chat",
      //子路由
      children: [
        {
          path: "/chat",
          name: 'Chat',
          component: Chat2,

        },
        {
          path: "/about",
          name: 'About',
          component: About,

        },
        {
          path: "/userInfo",
          name: 'userInfo',
          component: UserInfo,
        },
        {
          path: "/restpwd",
          name: 'restpwd',
          component: restPwd,
        }
      ]
    },
    {
      path: "/login",
      name: "Login",
      component: Login,
    },
    {
      path: "/register",
      name: "Register",
      component: Register,

    },
    {
      path: "/help",
      name: "help",
      component: Help,
    },
    {
      path: "/account",
      name: "Account",
      component: HelpAccount,
    },
    {
      path:"/error",
      name:"error",
      component: error
    }
  ]
})

//前置路由守卫
router.beforeEach((to, from, next) => {
  // 检查用户是否已经登录
  const publicPages = ['/account', '/help', '/register', '/login',"/error"]
  const authRequired = !publicPages.includes(to.path)
  const token = localStorage.getItem('token')

  if (authRequired && !token) {
    return next('/login')
  }

  next()
});

export default router
