'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')

module.exports = {
  dev: {

    // Paths
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {//代理配置
      '/api': {
      //  target: 'http://web.niitcxl.cn',    // 后端服务目标url地址 prod
       target: 'http://127.0.0.1:8080',//dev地址
        ws:true,
        changeOrigin: true,                     // 是否跨域
        pathRewrite: {
          '^/api': ''                         //把utils里面的基地址/api代换为http://127.0.0.1:8080  其中/api等价于前面的目标地址
        }
      }
    },

    // Various Dev Server settings
    host: '127.0.0.1', // can be overwritten by process.env.HOST
    port: 8083, // 前端服务启动端口
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-


    /**
     * Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'cheap-module-eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,

    cssSourceMap: true
  },

  build: {
    // Template for index.html
    index: path.resolve(__dirname, '../dist/index.html'),

    // Paths
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    // proxyTable: {//代理配置
    //   '/api': {
    //     target: 'http://chat.niitcxl.cn:8080',     // 目标url
    //     changeOrigin: true,                     // 是否跨域
    //     pathRewrite: {
    //       '^/api': ''                         // 其中/api等价于前面的目标地址
    //     }
    //   }
    // },

    // // Various Dev Server settings
    // host: 'http://niitcxl.cn', // can be overwritten by process.env.HOST
    // port: 8866, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    // autoOpenBrowser: false,
    // errorOverlay: true,
    // notifyOnErrors: true,
    // poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-

    /**
     * Source Maps
     */

    productionSourceMap: true,
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  }
}

