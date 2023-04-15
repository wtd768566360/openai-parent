layui.use(['form', 'miniTab'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.$;
    //监听提交
    form.on('submit(saveBtn)', function (data) {
        if (data.field.newPassword != data.field.againPassword) {
            layer.msg('两次密码不一致');
            return false;
        }
        //删除againPassword元素
        delete data.field.againPassword;
        data.field.oldPassword = RSAEncrypt(data.field.oldPassword);
        data.field.newPassword = RSAEncrypt(data.field.newPassword);
        $.ajax({
            url: "/system/password",
            type: "PATCH",
            data: JSON.stringify(data.field),
            success: function (data) {
                var index = layer.alert(data.msg, {
                    title: '提示'
                }, function () {
                    layer.close(index);
                    if (data.code == 0) {
                        sessionStorage.removeItem("user");
                        window.location = '/page/login';
                    }
                });
            }
        })
        return false;
    });

});