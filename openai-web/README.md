# OPENAI WEB

#### OpenAI-Web 欢迎指教

#### 欢迎加入技术讨论群
#### QQ群:558133902

# 成果展示

#### 项目演示
#### http://chat.niitcxl.cn/

# 技术实现

#### 后端:spring-boot
#### 前端:Layui


### 已实现
|   功能    | 特性  |
|:-------:|:---:|
|  聊天对话   | 支持  |
|  用户注册   | 支持  |
|  用户登录   | 支持  |
|  7天免登录  | 支持  |
|  邮箱验证   | 支持  |
|  个人信息   | 支持  |
|  修改密码   | 支持  |
|  重置密码   | 支持  |



### 配置文件
```yaml
#项目使用MySQL数据库,使用jpa为orm层框架
#项目自定义配置:
openai:
  #openai的官网地址
  base-url: https://api.openai.com/
  proxy:
    enabled: false
    host: 127.0.0.1
    port: 4780
  api_key:
    - 你的API-KEY
    - 你的API-KEY
```

### 数据库配置
```text
1. 将项目database下的openai.sql导入你的数据库
2. 可以通过正常页面注册账号,也可以通过admin来添加
```

### 启动
```text
1.将项目导入ide
2.引入mavne依赖
3.修改application.yml数据库配置
4.启动com.alone.openai.web.Bootstrap下的main方法
```

### 部署介绍
```text
服务器端存放的文件目录列表关系如下
-openai
 -app
  openai-admin-0.0.1.jar
  openai-web-0.0.1.jar
 -conf
  application-web.yml
  application-admin.yml
  openai.profile
 -logs
  -admin
   app.log
  -web
   app.log
 -bin
  openaistart
  openails
  openaistop
  
有一个总的目录为:openai
在openai目录下有四个目录:app,conf,bin,logs

app存放可执行jar
conf下存放环境配置和jar所启动时需要用到的配置文件
bin下存放启动,查看状态,停止的脚本
logs下存放日志文件
```

### 部署安装
```text
1. 先在服务器上创建如上所述的文件目录
2. 将本地打包好的jar上传到admin
   注意:如果修改jar包名字或版本,需要重新修改shell脚本文件
3. 将项目上shell文件夹的三个脚本放入bin里面
4. 将项目上openai.profile文件放入conf目录下
   4.1 修改openai.profile文件中变量OPENAI_HOME=openai这个目录在你的服务器上的目录
   4.2 将openai.profile加入到用户配置文件里面
   4.3 执行source ~/.bash_profile 来重载用户配置文件
   目的 为了让bin下面的脚本生效
5. 上传配置文件到conf下
   5.1 admin端配置文件默认:application-admin.yml
   5.2 web端配置文件默认:application-web.yml
   注意 如果修改配置文件夹名字需要修改对应的脚本文件
```

### 运维
```text
在bin文件夹下的shell命令
openaistart -a :启动admin
openaistart -w :启动web端

openails :查看应用状态

openaistop -a :停止admin
openaistop -w :停止web端
```

#### 联系
```text
搭建过程中遇到问题,请添加558133902群号联系开发者帮忙解决
欢迎进群讨论
```

