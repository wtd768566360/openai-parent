layui.use(['form', 'jquery'], function () {
    var $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;

    // 登录过期的时候，跳出ifram框架
    if (top.location != self.location) top.location = self.location;

    $('.bind-password').on('click', function () {
        if ($(this).hasClass('icon-5')) {
            $(this).removeClass('icon-5');
            $("input[name='password']").attr('type', 'password');
        } else {
            $(this).addClass('icon-5');
            $("input[name='password']").attr('type', 'text');
        }
    });

    $('.icon-nocheck').on('click', function () {
        if ($(this).hasClass('icon-check')) {
            $(this).removeClass('icon-check');
        } else {
            $(this).addClass('icon-check');
        }
    });

    // 进行登录操作
    form.on('submit(login)', function (data) {
        data = data.field;
        if (data.account == '') {
            layer.msg('用户名不能为空');
            return false;
        }
        if (data.password == '') {
            layer.msg('密码不能为空');
            return false;
        }
        var encrypted = RSAEncrypt(data.password);
        data.password = encrypted;
        $.ajax({
            url: "/system/login",
            type: "POST",
            data: JSON.stringify(data),
            success: function (data) {
                if (data.code == 0) {
                    sessionStorage.setItem('user', JSON.stringify(data.data));
                    layer.msg(data.msg, {time: 1500}, function () {
                        window.location = '/index';
                    });
                } else {
                    layer.msg(data.msg);
                }
            }
        })
        return false;
    });
});