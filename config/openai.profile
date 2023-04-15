#!/bin/sh
#进入脚本的上级目录
#cd $(cd "$(dirname "$0")"&&pwd)

export OPENAI_HOME=/home/pearl #安装目录
export PATH=$PATH:$OPENAI_HOME/bin/

alias  openai='cd $OPENAI_HOME'

#如果需要自行配置Java环境，则启用如下配置
#export JAVA_HOME=$OPENAI_HOME/lib/jdk1.8.0_92
#export PATH=$JAVA_HOME/bin:$PATH
#export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

