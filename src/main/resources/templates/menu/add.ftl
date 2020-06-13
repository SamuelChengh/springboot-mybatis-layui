<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>新增 | 菜单</title>
    <@fm.header />
</head>

<style>
    .layui-form-select dl {
        max-height: 160px;
    }
</style>

<body style="overflow-y: auto;">
<form class="layui-form" action="" lay-filter="form" style="padding: 10px 20px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">菜单类型:</label>
        <div class="layui-input-inline">
            <input type="radio" name="displayType" lay-filter="displayType" value="1" title="模块" checked>
            <input type="radio" name="displayType" lay-filter="displayType" value="2" title="菜单">
            <input type="radio" name="displayType" lay-filter="displayType" value="3" title="按钮">
        </div>
    </div>

    <div class="layui-form-item">
        <label id="menu_name_label" class="layui-form-label"><span style="color: red">*</span>模块名称:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="text" name="menuName" lay-verify="required" lay-verType="tips"  autocomplete="off">
        </div>
    </div>

    <div id="menu_icon" class="layui-form-item">
        <label class="layui-form-label">模块图标:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="text" name="icon" autocomplete="off">
        </div>
    </div>

    <div id="menu_parent" class="layui-form-item">
        <label class="layui-form-label">所属模块:</label>
        <div class="layui-input-inline">
            <select id="parentId" name="parentId" lay-filter="parentId"></select>
        </div>
    </div>

    <div id="menu_url" class="layui-form-item">
        <label class="layui-form-label">菜单地址:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="text" name="menuUrl" autocomplete="off">
        </div>
    </div>

    <div id="menu_sort" class="layui-form-item">
        <label class="layui-form-label">展示顺序:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="text" name="displaySort" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">授权标识:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="text" name="permission" autocomplete="off">
        </div>
    </div>

    <div style="display: none">
        <button id="submit" class="layui-btn" lay-submit lay-filter="submit">确定</button>
    </div>
</form>

<script type="text/javascript">
    layui.use('form', function () {
        var form = layui.form;

        $("#menu_parent").hide();
        $("#menu_url").hide();

        // 监听radio
        form.on('radio(displayType)', function (data) {
            $("#parentId").empty();

            var value = data.value;
            if(value == 1){
                $("#menu_sort").show();
                $("#menu_icon").show();
                $("#menu_parent").hide();
                $("#menu_url").hide();

                $("#menu_name_label").text("模块名称:");
            }else if (value == 2) {
                $("#menu_sort").show();
                $("#menu_icon").hide();
                $("#menu_parent").show();
                $("#menu_url").show();

                $("#menu_name_label").text("菜单名称:");
                $("#parent_label").text("所属模块:");
                getParent(form, 1);
            }else if (value == 3) {
                $("#menu_sort").hide();
                $("#menu_icon").hide();
                $("#menu_parent").show();
                $("#menu_url").hide();

                $("#menu_name_label").text("按钮名称:");
                $("#parent_label").text("所属菜单:");
                getParent(form, 2);
            }
        });

        // 提交
        form.on('submit(submit)', function (data) {
            var record = data.field;
            $.ajax({
                type: "POST",
                url: "/menu/add",
                data: record,
                async: false,
                dataType: "json",
                success: function (res) {
                    if (res.success) {
                        parent.layer.closeAll();
                        parent.parent.layer.msg(res.message, {icon: 1, offset: '10px', anim: 1});
                    } else {
                        parent.parent.layer.msg(res.message, {icon: 2, offset: '10px', anim: 1});
                    }
                }
            });
            return false;
        });
    });

    function getParent(form, type) {
        $.ajax({
            type: "GET",
            url: "/menu/getParent?type=" + type,
            dataType: "json",
            success: function (res) {
                var data = res.data;

                var html = '<option value=""></option>';
                for (var i = 0; i < data.length; i++) {
                    html += '<option value=' + data[i].id + '>' + data[i].menuName + '</option>';
                }
                $("#parentId").append(html);

                form.render('select');
            }
        })
    }
</script>
</body>
</html>