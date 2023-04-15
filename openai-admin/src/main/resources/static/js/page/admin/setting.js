layui.use(['form', 'miniTab'], function () {
    var form = layui.form,
        layer = layui.layer,
        miniTab = layui.miniTab,
        $ = layui.$;

    loadData();

    //监听提交
    form.on('submit(saveBtn)', function (data) {
        $.ajax({
            url: "/system/modify",
            type: "PUT",
            data: JSON.stringify(data.field),
            success: function (result) {
                reloadData(data.field);
                var index = layer.alert(result.msg, {
                    title: '提示'
                }, function () {
                    // 关闭弹出层
                    layer.close(index);
                    miniTab.deleteCurrentByIframe();
                });

            }
        })
        return false;
    });

    //重载数据
    function reloadData(newData) {
        $("#showName", window.parent.document).text(newData.name);
        const data = JSON.parse(sessionStorage.getItem("user"));
        data.name = newData.name;
        data.phone = newData.phone;
        data.email = newData.email;
        sessionStorage.setItem("user", JSON.stringify(data));
    }

    //加载数据
    function loadData() {
        if (sessionStorage.getItem("user")) {
            const data = JSON.parse(sessionStorage.getItem("user"));
            form.val("formFilter", {
                "id": data.id,
                "account": data.account,
                "name": data.name,
                "phone": data.phone,
                "email": data.email
            });
        }
    }
});