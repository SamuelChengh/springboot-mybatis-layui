<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>编辑 | 角色</title>
    <@fm.header />
</head>

<body style="overflow-y: auto;">
<form class="layui-form" action="" lay-filter="form" style="padding: 10px 20px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: red">*</span>角色名称:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="text" name="roleName" lay-verify="required" lay-verType="tips"
                   autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: red">*</span>角色代码:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="text" name="roles" lay-verify="required" lay-verType="tips"
                   autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">角色描述:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="text" name="remark" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">角色授权:</label>
        <div class="layui-input-inline">
            <div id="auth_tree"></div>
        </div>
    </div>

    <div style="display: none">
        <button id="submit" class="layui-btn" lay-submit lay-filter="submit">确定</button>
    </div>
</form>

<script type="text/javascript">
    layui.config({
        base: '/resources/static/layui/base/'
    }).use(['form', 'tree', 'baselist'], function () {
        var form = layui.form,
            tree = layui.tree,
            baselist = layui.baselist;

        // 表单初始赋值
        form.val('form', {
            "roleName": "${role.roleName}",
            "roles": "${role.roles}",
            "remark": "${role.remark}"
        });

        var authTreeData = getAuthTreeData();
        tree.render({
            elem: '#auth_tree',
            data: authTreeData,
            id: 'treeId',
            showCheckbox: true,
        });

        var authTreeCheckedData = getAuthTreeCheckedData();
        tree.setChecked('treeId', authTreeCheckedData);

        // 提交
        form.on('submit(submit)', function (data) {
            var record = data.field;

            record.id = "${role.id}";

            // 获取选中的menuId
            var checkedList = tree.getChecked('treeId');
            var menuIdArray = getCheckedMenuId(checkedList, new Array());
            record.menuIds = menuIdArray.toString();

            baselist.submitForm("/role/update/", record);

            return false;
        });
    });

    function getAuthTreeData() {
        var authTreeData = [];
        $.ajax({
            type: "GET",
            url: "/role/authority",
            dataType: "json",
            async: false,
            success: function (res) {
                authTreeData = res.data;
            }
        });
        return authTreeData;
    }

    function getAuthTreeCheckedData() {
        var authTreeCheckedData = [];
        $.ajax({
            type: "GET",
            url: "/role/authority/checked",
            data: {roleId: "${role.id}"},
            dataType: "json",
            async: false,
            success: function (res) {
                authTreeCheckedData = res.data;
            }
        });
        return authTreeCheckedData;
    }

    // 递归
    function getCheckedMenuId(checkedList, menuIdArr) {
        for (var i = 0; i < checkedList.length; i++) {
            menuIdArr.push(checkedList[i].id);

            var sonList = checkedList[i].children;
            if (sonList != null) {
                getCheckedMenuId(sonList, menuIdArr);
            }
        }
        return menuIdArr;
    }
</script>
</body>
</html>