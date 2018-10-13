layui.define(['layer'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer;

    var baseedit = {

        // 提交
        submitForm: function (url, record) {
            $.ajax({
                type: "POST",
                url: url,
                data: record,
                dataType: "json",
                success: function (res) {
                    if (res.success) {
                        layer.alert(res.message, {
                            icon: 1,
                        }, function () {
                            parent.layer.closeAll();
                        });
                        parent.layui.table.reload("tb");
                    } else {
                        layer.msg(res.message, {icon: 2});
                    }
                }
            })
        }
    };

    exports('baseedit', baseedit);
});