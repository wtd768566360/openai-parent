layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#currentTableId',
        url: '/user/query',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [
            [
                {field: 'id', title: 'ID', hide: true},
                {field: 'name', width: 200, title: '姓名'},
                {field: 'account', width: 200, title: '登录名'},
                {field: 'phone', width: 250, title: '手机号'},
                {field: 'email', width: 300, title: '邮箱'},
                {
                    field: 'userType', width: 150, title: '身份', templet: function (d) {
                        return d.userType.name;
                    }
                },
                {
                    field: 'createTime', width: 200, title: '创建时间', templet: function (d) {
                        var datetime = new Date(d.createTime);
                        return datetime.toLocaleDateString();
                    }
                },
                {field: 'state', title: '状态', minWidth: 150},
                {title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center"}
            ]
        ],
        limits: [10, 15, 20, 25, 50, 100],
        limit: 15,
        page: true,
        skin: 'line'
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('currentTableId', {
            page: {
                curr: 1
            },
            where: {
                "type": data.field.type,
                "name": data.field.name,
                "phone": data.field.phone,
                "email": data.field.email
            }
        }, 'data');
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(currentTableFilter)', function (obj) {
        if (obj.event === 'add') {  // 监听添加操作
            var index = layer.open({
                title: '添加用户',
                type: 2,
                shade: 0.2,
                maxmin: true,
                shadeClose: true,
                area: ['100%', '100%'],
                content: '/page/user/add',
                end: function () {
                    tableReload();
                }
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
        }
    });

    table.on('tool(currentTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            var index = layer.open({
                title: '编辑用户',
                type: 2,
                shade: 0.2,
                maxmin: true,
                shadeClose: true,
                area: ['100%', '100%'],
                content: '/page/user/edit',
                success: function (layero, index) {
                    // #aaa 表示的是弹出子页面的div的id值，
                    var updateBody = layer.getChildFrame('body', index);
                    //给子页面id=aaa的dev里的不同ID值的元素赋值
                    updateBody.find("input[name=id]").val(data.id);
                    updateBody.find("input[name=account]").val(data.account);
                    updateBody.find("input[name=name]").val(data.name);
                    updateBody.find("input[name=phone]").val(data.phone);
                    updateBody.find("input[name=email]").val(data.email);
                },
                end: function () {
                    tableReload();
                }
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
        } else if (obj.event === 'delete') {
            layer.confirm('确认删除此用户吗?', function (index) {
                var params = {
                    id: obj.data.id,
                    state: '删除'
                }
                $.ajax({
                    url: "/user/available",
                    type: "PATCH",
                    data: JSON.stringify(params),
                    success: function (data) {
                        if (data.code == 0) {
                            obj.del();
                        }
                        layer.close(index);
                        layer.msg(data.msg);
                    }
                })
            });
        } else if (obj.event === 'disable') {
            layer.confirm('确认禁用此用户吗?', function (index) {
                var params = {
                    id: obj.data.id,
                    state: '不可用'
                }
                $.ajax({
                    url: "/user/available",
                    type: "PATCH",
                    data: JSON.stringify(params),
                    success: function (data) {
                        tableReload();
                        layer.close(index);
                        layer.msg(data.msg);
                    }
                })
            });
        } else if (obj.event === 'available') {
            layer.confirm('确认启用此用户吗?', function (index) {
                var params = {
                    id: obj.data.id,
                    state: '正常'
                }
                $.ajax({
                    url: "/user/available",
                    type: "PATCH",
                    data: JSON.stringify(params),
                    success: function (data) {
                        tableReload();
                        layer.close(index);
                        layer.msg(data.msg);
                    }
                })
            });
        }
    });

    function tableReload() {
        table.reload('currentTableId', {
            page: {
                curr: 1
            }
        }, 'data');
    }
});