layui.define(['layer', 'form', 'table'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;

    var baselist = {

        // 渲染表格
        renderTable: function (param) {

            // 表格基础参数
            var cfg = {
                id: 'tb',
                elem: '#lay_table',
                title: '',
                method: 'post',
                url: '',
                toolbar: '#toolbar',
                cols: [],
                page: {
                    layout: ['prev', 'page', 'next', 'skip', 'limit', 'count'], //自定义分页布局
                    groups: 1,     //只显示 1 个连续页码
                    first: false,  //不显示首页
                    last: false    //不显示尾页
                },
                limit: 15,
                limits: [15, 30, 60, 100],
                loading: true,
                cellMinWidth: 100,
                text: {
                    none: '暂无相关数据'
                }
            };

            // 重写cfg参数, 让param继承cfg
            for (var c in cfg) {
                for (var p in param) {
                    if (c == p) {
                        if (cfg[c] != param[p]) {
                            cfg[c] = param[p];
                        }
                    } else {
                        cfg[p] = param[p];
                    }
                }
            }

            // 参数校验
            if (!baselist.checkTableCfg(cfg)) {
                return;
            }

            table.render(cfg);
        },

        // 新增
        createRow: function (param) {
            var cfg = baselist.iframeLayerConfig(param);

            layer.open(cfg);
        },

        // 编辑
        updateRow: function (param, record) {
            var cfg = baselist.iframeLayerConfig(param);
            cfg.success = function(layero, index){
                var iframe = layero.find("iframe")[0].contentWindow;
                iframe.setRecordData(record);
            }

            layer.open(cfg);
        },

        // 删除
        removeRow: function (url, record) {
            parent.layer.confirm('您确定要删除该条记录吗？', {
                title: '温馨提示',
                icon: 3
            }, function (index) {
                $.post(url, {id: record.id}, function (res) {
                    if (res.success) {
                        layer.alert(res.message, {
                            icon: 1,
                        }, function (index) {
                            layer.close(index);
                            table.reload("tb");
                        });
                    } else {
                        layer.msg(res.message, {icon: 2});
                    }
                });
                parent.layer.close(index);
            });
        },

        // 双击行事件
        doubleRow: function (param) {
            table.on('rowDouble(tbf)', function (obj) {
                var record = obj.data;

                baselist.updateRow(param, record);
            });
        },

        // 查询
        querySubmit: function () {
            form.on('submit(search)', function (data) {
                var where = {};
                var formArray = $("#searchForm").serializeArray();
                for (var i = 0; i < formArray.length; i++) {
                    where[formArray[i].name] = formArray[i].value;
                }
                table.reload('tb', {
                    where: where
                });
                return false;
            });
        },

        // iframe弹出层参数
        iframeLayerConfig: function(param){

            // 弹出层基础参数
            var cfg = {
                type: 2,
                skin: 'layui-layer-molv',
                resize: false
            };

            // 重写cfg参数, 让param继承cfg
            for (var c in cfg) {
                for (var p in param) {
                    if (c == p) {
                        if (cfg[c] != param[p]) {
                            cfg[c] = param[p];
                        }
                    } else {
                        cfg[p] = param[p];
                    }
                }
            }

            return cfg;
        },

        // 表格参数校验
        checkTableCfg: function (cfg) {
            if(cfg.url == ""){
                parent.layer.msg('参数url不能为空', {icon: 5});
                return false;
            }
            if(cfg.cols == ""){
                parent.layer.msg('参数cols不能为空', {icon: 5});
                return false;
            }
            return true;
        },

        // 验证必须只有一行选中
        validateSingleSelected: function () {
            var checkStatus = table.checkStatus('tb');
            var records = checkStatus.data;
            if (records == null || records.length == 0) {
                parent.layer.alert('请先选中一条记录!', {
                    icon: 5
                });
                return false;
            } else if (records.length > 1) {
                parent.layer.alert('不能同时选择多条记录!', {
                    icon: 2
                });
                return false;
            }
            return records[0];
        },

        // 验证必须选中
        validateSelected: function () {
            var checkStatus = table.checkStatus('tb');
            var records = checkStatus.data;
            if (records == null || records.length == 0) {
                parent.layer.alert('请至少选中一条记录!', {
                    icon: 5
                });
                return false;
            }
            return true;
        }
    };

    exports('baselist', baselist);
});