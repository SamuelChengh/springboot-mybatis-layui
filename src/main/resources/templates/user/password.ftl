<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>修改密码</title>
    <@fm.header />
</head>

<body style="overflow-y: auto;">
<form class="layui-form" action="" lay-filter="form" style="padding: 10px 20px 0 0;">
    <div class="layui-form-item">
        <label class="layui-form-label">原密码<font color="red">*</font>:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="password" name="pwd" lay-verify="required" lay-verType="tips" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">新密码<font color="red">*</font>:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="password" name="npwd" lay-verify="required" lay-verType="tips" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">密码确认<font color="red">*</font>:</label>
        <div class="layui-input-inline">
            <input class="layui-input" type="password" name="rpwd" lay-verify="required" lay-verType="tips" autocomplete="off">
        </div>
    </div>

    <div style="display: none">
        <button id="submit" class="layui-btn" lay-submit lay-filter="submit">确定</button>
    </div>
</form>

<script type="text/javascript">
layui.use('form', function(){
	var form = layui.form;
  	
  	// 提交
    form.on('submit(submit)', function(data){
    	var record = data.field;
    	$.ajax({
    		type : "POST",
    		url : "/user/resetPwd",
    		data: record,
    		dataType : "json",
    		success : function(res) {
    			if (res.success) {
    				parent.layer.alert(res.message + ',请重新登录!', {
    					icon: 1
    				},function(){
    					parent.layer.closeAll();
        				window.parent.location.reload();
    				});
    			}else{
    				parent.layer.msg(res.message, {icon: 2, offset: '10px', anim: 1});
    			}
    		}
    	});
     	return false;
    });
});
</script>
</body>
</html>