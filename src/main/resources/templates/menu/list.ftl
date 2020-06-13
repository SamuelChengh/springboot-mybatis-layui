<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>菜单管理</title>
    <@fm.header />
</head>

<body style="padding: 10px;overflow-y: auto;">
<!-- table -->
<table id="lay_table" lay-filter="tbf"></table>

<!-- 头工具栏toolbar -->
<script type="text/html" id="toolbar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-event="add"><i class="layui-icon layui-icon-add-1"></i>新增</button>
    </div>
</script>

<!-- 工具条tool -->
<script type="text/html" id="tool">
    <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>
</script>

<script type="text/javascript">
    layui.config({
        base: '/resources/static/layui/'
    }).extend({
        treetable: 'plugin/treetable/treetable'
    }).use(['table', 'treetable', 'element'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var treetable = layui.treetable;

        var renderTable = function () {
            layer.load(3);

            treetable.render({
                id: 'tb',
                title: '菜单',
                elem: '#lay_table',
                method: 'post',
                url: '/menu/list',
                page: false,
                treeColIndex: 0,     		// 树形图标显示在第几列
                treeSpid: 0,         		// 最上级的父级id
                treeIdName: 'id',  			// id字段的名称
                treePidName: 'parentId',	// pid字段的名称
                treeDefaultClose: true,   	// 是否默认折叠
                treeLinkage: false,         // 父级展开时是否自动展开所有子级
                cellMinWidth: 100,
                toolbar: '#toolbar',
                defaultToolbar: [],
                cols: [[
                    {field: 'menuName', title: '菜单名称', width: '18%', align: 'left'},
                    {field: 'menuUrl', title: '菜单地址', width: '20%', align: 'center', edit: 'text'},
                    {field: 'displayType', title: '菜单类型', width: '12%', align: 'center', edit: 'text',
                        templet: function (row) {
                            if (row.displayType == 1) {
                                return '<span class="layui-badge-dot"></span><span style="padding-left: 10px;">模块</span>';
                            } else if (row.displayType == 2) {
                                return '<span class="layui-badge-dot layui-bg-blue"></span><span style="padding-left: 10px;">菜单</span>'
                            }   else if (row.displayType == 3) {
                                return '<span class="layui-badge-dot layui-bg-grass"></span><span style="padding-left: 10px;">按钮</span>'
                            }
                        }
                    },
                    {field: 'icon', title: '菜单图标', width: '12%', align: 'center', edit: 'text',
                        templet: function (row) {
                            return '<i class="layui-icon ' + row.icon + '" style="color: #42b983;"></i>';
                        }
                    },
                    {field: 'displaySort', title: '菜单序号', width: '9%', align: 'center', edit: 'text'},
                    {field: 'permission', title: '授权标识', width: '20%', align: 'center', edit: 'text'},
                    {title: '操作', align: 'center', width: '9%', toolbar: '#tool'}
                ]],
                done: function () {
                    layer.closeAll('loading');
                    $("table").css("width", "100%");
                    $(".layui-table th").css("font-weight", "600");
                },
                text: {
                    none: '暂无相关数据'
                }
            });
        };

        renderTable();

        // 监听头工具栏toolbar
        table.on('toolbar(tbf)', function (obj) {
            switch (obj.event) {
                case 'add':
                    layer.open({
                        type: 2,
                        title: '新增菜单',
                        content: '/menu/add',
                        area: ['380px', '455px'],
                        resize: false,
                        btn: ['确定', '取消'],
                        btnAlign: 'c',
                        yes: function (index, layero) {
                            // 获取iframe页的窗口对象
                            var iframeWin = window[layero.find('iframe')[0]['name']];
                            // 获取表单中的确定按钮
                            var submitButton = iframeWin.document.getElementById("submit");
                            // 触发确定按钮事件
                            submitButton.click();

                            renderTable();
                        }
                    })
                    break;
            }
        });

        // 监听工具条tool
        table.on('tool(tbf)', function (obj) {
            var record = obj.data;

            switch (obj.event) {
                case 'delete':
                    layer.confirm('您确定要删除该条记录吗？', {
                        title: '温馨提示',
                        icon: 3
                    }, function (index) {
                        $.post('/menu/delete', {id: record.id}, function (res) {
                            if (res.success) {
                                layer.closeAll();
                                parent.parent.layer.msg(res.message, {icon: 1, offset: '10px', anim: 1});
                                renderTable();
                            } else {
                                parent.parent.layer.msg(res.message, {icon: 2, offset: '10px', anim: 1});
                            }
                        });
                    });
                    break;
            };
        });

        // 监听单元格编辑
        table.on('edit(tbf)', function (obj) {
            var record = obj.data;
            delete record.createdDate;
            delete record.updatedDate;
            $.ajax({
                type: "POST",
                url: "/menu/update",
                data: record,
                dataType: "json",
                success: function (res) {
                    if (res.success) {
                        parent.parent.layer.msg(res.message, {icon: 1, offset: '10px', anim: 1});
                    } else {
                        parent.parent.layer.msg(res.message, {icon: 2, offset: '10px', anim: 1});
                    }
                }
            })
        });
    });
</script>
</body>
</html>