layui.use(['form', 'table'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table;

    table.render({
        elem: '#currentTableId',
        url: '/api/key/query',
        toolbar: '#toolbarDemo',
        defaultToolbar: ['filter', 'exports', 'print', {
            title: '提示',
            layEvent: 'LAYTABLE_TIPS',
            icon: 'layui-icon-tips'
        }],
        cols: [
            [
                {field: 'id', title: 'ID', hide: true},
                {field: 'apiKey', width: 500, title: 'API-KEY'},
                {field: 'origin', width: 200, title: '来源'},
                {
                    field: 'admin', width: 150, title: '添加者', templet: function (d) {
                        return d.admin.name;
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
        skin: 'line',
        where: {"state": "正常,不正常"}
    });

    // 监听搜索操作
    form.on('submit(data-search-btn)', function (data) {
        //执行搜索重载
        table.reload('currentTableId', {
            page: {
                curr: 1
            },
            where: {
                "state": "正常,不正常",
                "origin": data.field.origin
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
                content: '/page/key/add',
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
        if (obj.event === 'delete') {
            layer.confirm('确认删除此API-KEY吗?', function (index) {
                $.ajax({
                    url: "/api/key/" + obj.data.id,
                    type: "DELETE",
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
            layer.confirm('确认禁用此API-KEY吗?', function (index) {
                var params = {
                    id: obj.data.id,
                    state: '不正常'
                }
                $.ajax({
                    url: "/api/key/available",
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
            layer.confirm('确认启用此API-KEY吗?', function (index) {
                var params = {
                    id: obj.data.id,
                    state: '正常'
                }
                $.ajax({
                    url: "/api/key/available",
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
            , where: {
                "state": "正常,不正常",
            }
        }, 'data');
    }
});