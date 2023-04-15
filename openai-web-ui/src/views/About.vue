<template>
    <!-- 行标签 -->
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
                <div style="height: 80vh">
                    <p style="font-weight: bold;  font-size: 35px;">公告</p>
                    <p style="font-weight: bold;  font-size: 20px;">本项目由个人搭建</p>

                    <!--  -->
                    <ul>
                        <li>由于制造以极维护此系统及费心血</li>
                        <li>所以我们需要您的支持与推广，才能支撑此平台的正常运行</li>
                    </ul>
                    <p style="font-weight: bold;  font-size: 20px;">赞助</p>
                    <ul>
                        <li>由于此平台使用人数不断激增</li>
                        <li>使用成本不断上升</li>
                        <li>如果确实对你有帮助</li>
                        <li>为我们分担一些成本和费用，感激不尽...</li>
                        <li><el-button type="text" @click="centerDialogVisible = true">赞助我们</el-button></li>
                    </ul>
                    <p style="font-weight: bold;  font-size: 20px;">如何联系我们？</p>
                    <ul>
                        <li>联系方式</li>
                        <li>微信：Docker-cxl</li>
                        <li>QQ：1226242536</li>
                        <li>QQ群：558133902</li>
                    </ul>
                    <p style="font-weight: bold;  font-size: 20px;">如果我需要更多的资源和技术支持？</p>
                    <ul>
                        <li>如果您需要更多的资源和定制化服务，请联系我们的客服，</li>
                        <li>我们将根据您的需求和业务情况提供相应的服务和解决方案。</li>
                    </ul>

                    <p style="font-weight: bold;  font-size: 20px;"> 使用中遇见问题？</p>
                    <ul>
                        <li>当您正在使用中出现问题时候，请您及时与我们进行联系，提出您宝贵建议和反馈！</li>
                        <li>以便我们及时的更新和维护。</li>
                    </ul>

                </div>
                <el-dialog title="意见反馈" :fullscreen="true" :visible.sync="dialogFormVisible">
                    <el-form :model="form">
                        <el-form-item label="您的意见" :label-width="formLabelWidth">
                            <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="form.content">
                            </el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="saveContent()">确 定</el-button>
                    </div>
                </el-dialog>


            </div>
        </el-col>

        <!-- 右边 缩进 -->
        <el-col :xs="0" :sm="2" :md="5" :lg="5" :xl="5">
            <div class="grid-content "></div>
        </el-col>

        <el-dialog title="赞助我们" :visible.sync="centerDialogVisible" width="80%" center class="el-dialog__body">
            <div>
                <img src="@/img/wxPay.jpg" alt="" style="width: 270px; height:350px">
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="centerDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
            </span>
        </el-dialog>
    </el-row>
</template>

<script>
import request from '../utils/request';
import { Loading } from 'element-ui';
export default {
    name: "about",
    data() {
        return {
            dialogFormVisible: false,
            form: {//意见反馈表单
                content: ''//意见内容
            },
            formLabelWidth: '100px',
            centerDialogVisible: false
        }
    },
    methods: {
        saveContent() {
            if (this.form.content == '') {
                this.$message.error('不能提交空意见！');
                return
            }
            let loadingInstance = Loading.service({ fullscreen: true });
            //发送请求
            request.post("/feed", this.form).then(resp => {
                if (resp.code == 200) {
                    this.$message({
                        message: '感谢您的意见反馈！',
                        type: 'success'
                    });
                    this.dialogFormVisible = false
                    loadingInstance.close();
                    this.form.content = ''
                }
            })
        }
    }
}
</script>
<style>
.grid-content {
    border-radius: 4px;
    min-height: 36px;
}
.el-dialog__body {
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
