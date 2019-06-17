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
                	if (res.return_code == '1') {
                		parent.layer.closeAll();
                		parent.layer.msg(res.return_msg, {icon: 1});
        				parent.layui.table.reload("tb");
        			}else{
        				layer.msg(res.return_msg, {icon: 2});
        			}
                }
            })
        }
    };

    exports('baseedit', baseedit);
});