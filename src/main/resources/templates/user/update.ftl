<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>编辑账号</title>
    <@fm.header />
</head>

<body style="overflow-y: auto;">
<form class="layui-form" action="" lay-filter="form" style="padding: 10px 20px 0 0;">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"><span style="color: red">*</span>账号:</label>
            <div class="layui-input-inline">
                <input class="layui-input" type="text" name="userName" placeholder="请输入1-10个字符" maxlength="10"
                       lay-verify="required|length" lay-verType="tips" readonly style="background: #eee;"
                       autocomplete="off">
            </div>
        </div>

        <div class="layui-inline">
            <label class="layui-form-label"><span style="color: red">*</span>密码:</label>
            <div class="layui-input-inline">
                <input class="layui-input" type="password" name="password" lay-verify="required" lay-verType="tips"
                       autocomplete="off">
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"><span style="color: red">*</span>昵称:</label>
            <div class="layui-input-inline">
                <input class="layui-input" type="text" name="nickName" placeholder="请输入1-10个字符" maxlength="10"
                       lay-verify="required|length" lay-verType="tips" autocomplete="off">
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: red">*</span>所属角色:</label>
        <div class="layui-input-block" style="width: 75%;">
            <div class="layui-xm-select" id="roleIds" style="width: 512px;"></div>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">状态:</label>
        <div class="layui-input-inline">
            <input type="checkbox" checked name="status" lay-filter="status" lay-skin="switch" lay-text="开启|禁用">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">备注:</label>
        <div class="layui-input-block" style="width: 75%;">
            <textarea class="layui-textarea" name="remark" autocomplete="off">${user.remark}</textarea>
        </div>
    </div>

    <div style="display: none">
        <button id="submit" class="layui-btn" lay-submit lay-filter="submit">确定</button>
    </div>
</form>

<script type="text/javascript">
    layui.config({
        base: '/resources/static/layui/'
    }).extend({
        baselist: 'base/baselist'
    }).use(['form', 'table', 'baselist'], function () {
        var form = layui.form,
            baselist = layui.baselist;

        // 所属角色
        var selectedData = [];
        <#list user.roles as r>
        selectedData.push(${r.id});
        </#list>
        var roleSelect = baselist.renderMultiSelect({
            el: '#roleIds',
            name: 'roleIds',
            layVerify: 'required',
            layVerType: 'tips',
            height: '120px',
            initValue: selectedData,
            data: []
        });
        baselist.remoteSelectData(roleSelect, '/role/getRole', null);

        //表单初始赋值
        var status;
        if ("${user.status}" == 1) {
            status = true;
        } else {
            status = false;
        }
        form.val('form', {
            "userName": "${user.userName}",
            "nickName": "${user.nickName}",
            "password": "${user.password}",
            "status": status
        });

        // 提交
        form.on('submit(submit)', function (data) {
            var record = data.field;
            record.id = "${user.id}";
            if (record.status == "on") {
                record.status = 1;
            } else {
                record.status = 0;
            }

            baselist.submitForm("/user/update", record);

            return false;
        });
    });
</script>
</body>
</html>