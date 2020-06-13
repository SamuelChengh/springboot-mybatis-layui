<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>账号管理</title>
    <@fm.header />
</head>

<body style="padding: 10px;">
<!-- 搜索栏 -->
<form id="searchForm" class="layui-form" action="" lay-filter="searchForm">
    <div class="layui-form-item">
        <div class="layui-inline">
            <input class="layui-input" id="userName" name="userName" placeholder="账号" autocomplete="off">
        </div>

        <div class="layui-inline">
            <div class="layui-xm-select" id="roleId"></div>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="search"><i
                        class="layui-icon layui-icon-search"></i>查询
            </button>
            <button class="layui-btn layui-btn-primary" type="reset">重置</button>
        </div>
    </div>
</form>

<!-- table -->
<table id="lay_table" lay-filter="tbf"></table>

<!-- 头工具栏toolbar -->
<script type="text/html" id="toolbar">
    <@shiro.hasPermission name="sys:user:create">
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-event="add"><i
                    class="layui-icon layui-icon-add-1"></i>新增
        </button>
    </@shiro.hasPermission>
</script>

<!-- 工具条tool -->
<script type="text/html" id="tool">
    <@shiro.hasPermission name="sys:user:update">
        <a class="layui-btn layui-btn-xs layui-btn-normal" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
    </@shiro.hasPermission>

    <@shiro.hasPermission name="sys:user:delete">
        <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete"><i
                    class="layui-icon layui-icon-delete"></i>删除</a>
    </@shiro.hasPermission>
</script>

<script type="text/javascript">
    layui.config({
        base: '/resources/static/layui/'
    }).extend({
        baselist: 'base/baselist'
    }).use(['table', 'form', 'baselist'], function () {
        var table = layui.table,
            form = layui.form,
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

        // 所属角色
        var roleSelect = baselist.renderRadioSelect({
            el: '#roleId',
            name: 'roleId',
            tips: '所属角色',
            data: []
        });
        baselist.remoteSelectData(roleSelect, '/role/getRole', null);

        // 渲染表格
        baselist.renderTable({
            title: '账号',
            url: '/user/list',
            toolbar: toolbar,
            cols: [[
                {type: 'numbers'},
                {field: 'userName', title: '账号', align: 'center'},
                {field: 'nickName', title: '昵称', align: 'center'},
                {
                    field: 'status', title: '状态', align: 'center',
                    templet: function (d) {
                        if (d.status == 1) {
                            return '<input type="checkbox" checked name="status" value="' + d.id + '" lay-skin="switch" lay-text="开启|禁用" lay-filter="status">';
                        } else if (d.status == 0) {
                            return '<input type="checkbox" name="status" value="' + d.id + '" lay-skin="switch" lay-text="开启|禁用" lay-filter="status">';
                        }
                    }
                },
                {
                    field: 'roles', title: '所属角色', align: 'center',
                    templet: function (d) {
                        var roles = d.roles;
                        var roleNames = [];
                        for (var i = 0; i < roles.length; i++) {
                            roleNames.push(roles[i].roleName);
                        }
                        return roleNames.toString();
                    }
                },
                {title: '操作', align: 'center', hide: isHideTool, toolbar: '#tool'}
            ]]
        });

        table.on('toolbar(tbf)', function (obj) {
            switch (obj.event) {
                case 'add':
                    baselist.openIframeLayer({
                        title: '新增账号',
                        content: '/user/add',
                        area: ['700px', '440px']
                    });
                    break;
            }
        });

        table.on('tool(tbf)', function (obj) {
            var record = obj.data;
            switch (obj.event) {
                case 'edit':
                    baselist.openIframeLayer({
                        title: '编辑账号',
                        content: '/user/update/' + record.id,
                        area: ['700px', '440px']
                    });
                    break;
                case 'delete':
                    var rec = {};
                    rec.id = record.id;
                    baselist.removeRow('/user/delete', rec);
                    break;
            }
        });

        // 监听switch开关: 状态status
        form.on('switch(status)', function (data) {
            var record = {};
            record.id = data.value;
            if (data.elem.checked) {
                record.status = 1;
            } else {
                record.status = 0;
            }

            baselist.doPost("/user/update", record);
        });
    });
</script>
</body>
</html>