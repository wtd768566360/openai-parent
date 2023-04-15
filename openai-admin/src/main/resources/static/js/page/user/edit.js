layui.use(['form'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.$;
    //监听提交
    form.on('submit(saveBtn)', function (data) {
        $.ajax({
            url: "/user/modify",
            type: "PUT",
            data: JSON.stringify(data.field),
            success: function (data) {
                var index = layer.alert(data.msg, {
                    title: '提示'
                }, function () {
                    // 关闭弹出层
                    layer.close(index);
                    var iframeIndex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(iframeIndex);
                });

            }
        })
        return false;
    });
});