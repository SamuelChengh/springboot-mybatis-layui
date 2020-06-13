<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>角色管理</title>
    <@fm.header />
</head>

<body style="padding: 10px;">
<!-- 搜索栏 -->
<form id="searchForm" class="layui-form" action="" lay-filter="searchForm">
    <div class="layui-form-item">
        <div class="layui-inline">
            <input class="layui-input" id="roleName" name="roleName" placeholder="角色名称" autocomplete="off">
        </div>

        <div class="layui-inline">
            <input class="layui-input" id="roles" name="roles" placeholder="角色代码" autocomplete="off">
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="search"><i
                        class="layui-icon layui-icon-search"></i>查询
            </button>
            <button class="layui-btn layui-btn-primary" type="reset">重置</button>
        </div>
    </div>
</form>

<!-- table -->
<table id="lay_table" lay-filter="tbf"></table>

<!-- 工具栏toolbar -->
<script type="text/html" id="toolbar">
    <@shiro.hasPermission name="sys:role:create">
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-event="add"><i
                    class="layui-icon layui-icon-add-1"></i>新增
        </button>
    </@shiro.hasPermission>
</script>

<!-- 工具条tool -->
<script type="text/html" id="tool">
    <@shiro.hasPermission name="sys:role:update">
        <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
    </@shiro.hasPermission>

    <@shiro.hasPermission name="sys:role:delete">
        <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete"><i
                    class="layui-icon layui-icon-delete"></i>删除</a>
    </@shiro.hasPermission>
</script>

<script type="text/javascript">
    layui.config({
        base: '/resources/static/layui/base/'
    }).use(['table', 'baselist'], function () {
        var table = layui.table,
            baselist = layui.baselist;

        // 工具栏toolbar无按钮时，不显示
        var toolbar = "#toolbar";
        var toolbarHtml = $.trim($('#toolbar').html());
        if (toolbarHtml == '') {
            toolbar = false;
        }

        // 工具条tool无操作按钮时，隐藏列
        var isHideTool = false;
        var toolHtml = $.trim($('#tool').html());
        if (toolHtml == '') {
            isHideTool = true;
        }

        // 渲染表格
        baselist.renderTable({
            title: '角色',
            url: '/role/list',
            toolbar: toolbar,
            cols: [[
                {type: 'numbers'},
                {field: 'roleName', title: '角色名称', align: 'center'},
                {field: 'roles', title: '角色代码', align: 'center'},
                {field: 'remark', title: '角色描述', align: 'center'},
                {field: 'createdDate', title: '创建时间', align: 'center'},
                {title: '操作', align: 'center', hide: isHideTool, toolbar: '#tool'}
            ]]
        });

        // 工具栏
        table.on('toolbar(tbf)', function (obj) {
            switch (obj.event) {
                case 'add':
                    baselist.openIframeLayer({
                        title: '新增角色',
                        content: '/role/add',
                        area: ['420px', '96%']
                    });
                    break;
            }
        });

        // 操作栏
        table.on('tool(tbf)', function (obj) {
            var record = obj.data;
            switch (obj.event) {
                case 'edit':
                    baselist.openIframeLayer({
                        title: '编辑角色',
                        content: '/role/update/' + record.id,
                        area: ['420px', '96%']
                    });
                    break;
                case 'delete':
                    var rec = {};
                    rec.id = record.id;
                    baselist.removeRow('/role/delete', rec);
                    break;
            }
        });
    });
</script>
</body>
</html>