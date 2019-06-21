layui.define(['layer'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer;

    var baseedit = {
        submitForm: function (url, record) {
            $.ajax({
                type: "POST",
                url: url,
                data: record,
                dataType: "json",
                success: function (res) {
                	if (res.success) {
                		parent.layer.closeAll();
                		parent.layer.msg(res.message, {icon: 1});
                        parent.layui.$('.layui-laypage-btn').click();
        			}else{
        				layer.msg(res.message, {icon: 2});
        			}
                }
            })
        }
    };

    exports('baseedit', baseedit);
});