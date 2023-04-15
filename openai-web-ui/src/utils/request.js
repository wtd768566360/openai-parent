import axios from "axios";

const request = axios.create({
  // baseURL: "http://web.niitcxl.cn/api/", //使用/api作为前端自己的基地址  prod
  baseURL: "http://127.0.0.1:8083/api/",//dev地址
  timeout: 120000 //请求超时时间
});

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(
  config => {
    config.headers["Content-Type"] = "application/json;charset=utf-8";

    config.headers["token"] = localStorage.getItem("token"); // 设置请求头
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
  response => {
    let res = response.data;
    // 如果是返回的文件
    if (response.config.responseType === "blob") {
      return res;
    }

    if (
      response.headers["content-type"] === "text/event-stream;charset=UTF-8"
    ) {
      console.log("流返回类型");
      return res; // 如果是 Server Sent Event 数据类型，则直接返回文本流数据
    }
    // 兼容服务端返回的字符串数据
    if (typeof res === "string") {
      res = res ? JSON.parse(res) : res;
    }
    return res;
  },
  error => {
    //获取状态码
    let status = error.response.status;
    if (status == 401) {
      //未登录
      // localStorage.removeItem("token");
      this.$router.push("/login"); //跳转登录页面
    }
    // if (status == 500) {
    //   //服务器出错
    //   this.$router.push("/error"); //跳转登录页面
    // }
    // console.log("err" + error); // for debug
    return Promise.reject(error);
  }
);

export default request;
