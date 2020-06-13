<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>新增账号</title>
    <@fm.header />
</head>

<body style="overflow-y: auto;">
<form class="layui-form" action="" lay-filter="form" style="padding: 10px 20px 0 0;">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"><span style="color: red">*</span>账号:</label>
            <div class="layui-input-inline">
                <input class="layui-input" type="text" name="userName" placeholder="请输入1-10个字符" maxlength="10"
                       lay-verify="required|length" lay-verType="tips" autocomplete="off">
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
            <textarea class="layui-textarea" name="remark" autocomplete="off"></textarea>
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
    }).use(['form', 'baselist'], function () {
        var form = layui.form,
            baselist = layui.baselist;

        // 所属角色
        var roleFlag = 1;
        var roleSelect = baselist.renderRadioSelect({
            el: '#roleIds',
            name: 'roleIds',
            layVerify: 'required',
            layVerType: 'tips',
            height: '120px',
            data: []
        });
        baselist.remoteSelectData(roleSelect, '/role/getRole', null);

        // 提交
        form.on('submit(submit)', function (data) {
            var record = data.field;
            if (record.status == "on") {
                record.status = 1;
            } else {
                record.status = 0;
            }

            baselist.submitForm("/user/add", record);

            return false;
        });
    });
</script>
</body>
</html>