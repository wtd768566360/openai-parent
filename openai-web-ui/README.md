## 项目环境

```js
nonde v10.14.0
npm v6.4.1
```

## 安装依赖

```js
npm i
```

## 配置后端接口地址

- 修改 src/utils/request.js 文件中修改

```js
const request = axios.create({
  //使用/api作为前端自己的基地址
  baseURL: "http://127.0.0.1:8083/api/",
  timeout: 120000 //请求超时时间
});
```

- 修改 config/index.js 文件中修改

```js
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {//代理配置
      '/api': {
        //后端服务目标url地址
       target: 'http://127.0.0.1:8080',
        ws:true,
        changeOrigin: true, //是否跨域
        pathRewrite: {
       // 其中/api等价于前面的目标地址
          '^/api': ''
        }
      }
    },

    // Various Dev Server settings
    host: '127.0.0.1', // 我们的前端地址
    port: 8083, // 前端服务启动端口
```

- 修改 chat 发送请求的地址 找到下面这段代码 修改为自己的后端地址

```js
request.post(
    '/chat/params',
    _this.chatRecords
).then(resp => {
    //请求数据的方法
    _this.eventSource = new EventSourcePolyfill('http://127.0.0.1:8080/chat/context/search/' + resp.data, {
    headers: {
        'token': localStorage.getItem('token')
    }
});
```

## 启动项目
```js
npm run dev 
```
## 打包项目
```js
npm run build
```

