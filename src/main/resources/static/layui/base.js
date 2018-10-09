layui.define(['layer', 'form', 'table'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var form = layui.form;
    var table = layui.table;

    var base = {

        // 查询
        querySubmit: function(dom){
            form.on('submit(search)', function (data) {
                var formArray = $(dom).serializeArray();
                table.reload('tb', {
                    where: formArray
                });
                return false;
            });
        }
    };

    exports('base', base);
});