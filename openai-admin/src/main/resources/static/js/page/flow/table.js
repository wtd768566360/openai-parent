layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#currentTableId',
        url: '/flow/query',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [
            [
                {field: 'id', title: 'ID', hide: true},
                {
                    field: 'account', width: 200, title: '登录名', templet: function (d) {
                        return d.user.account;
                    }
                },
                {
                    field: 'name', width: 200, title: '昵称', templet: function (d) {
                        return d.user.name;
                    }
                },
                {
                    field: 'email', width: 200, title: '邮箱', templet: function (d) {
                        return d.user.email;
                    }
                },
                {field: 'total', width: 200, title: '总量'},
                {field: 'used', width: 200, title: '使用量'},
                {field: 'unused', width: 200, title: '剩余量'},
                {
                    field: 'createTime', width: 200, title: '更新时间', templet: function (d) {
                        return dateTime(d.updateTime);
                    }
                },
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
                "account": data.field.account,
                "name": data.field.name,
                "email": data.field.email
            }
        }, 'data');
        return false;
    });

    /**
     * toolbar监听事件
     */
    table.on('toolbar(currentTableFilter)', function (obj) {
        if (obj.event === 'sync') {  // 监听添加操作
            $.ajax({
                url: "/flow/sync",
                type: "POST",
                success: function (data) {
                    tableReload();
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
        }
    });

    table.on('tool(currentTableFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'add') {
            var index = layer.open({
                title: '添加访问量',
                type: 2,
                shade: 0.2,
                maxmin: true,
                shadeClose: true,
                area: ['100%', '100%'],
                content: '/page/flow/add',
                success: function (layero, index) {
                    // #aaa 表示的是弹出子页面的div的id值，
                    var updateBody = layer.getChildFrame('body', index);
                    //给子页面id=aaa的dev里的不同ID值的元素赋值
                    updateBody.find("input[name=userId]").val(data.user.id);
                    updateBody.find("input[name=name]").val(data.user.name);
                    updateBody.find("input[name=total]").val(data.total);
                    updateBody.find("input[name=unused ]").val(data.unused);
                },
                end: function () {
                    tableReload();
                }
            });
            $(window).on("resize", function () {
                layer.full(index);
            });
            return false;
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