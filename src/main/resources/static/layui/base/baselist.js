layui.define(['layer', 'form', 'table'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
        table = layui.table;

    // 查询
    form.on('submit(search)', function (data) {
        var where = {};
        var formArray = $("#searchForm").serializeArray();
        for (var i = 0; i < formArray.length; i++) {
            where[formArray[i].name] = formArray[i].value;
        }
        table.reload('tb', {
            where: where,
            page: {
                curr: 1 //重新从第 1 页开始
            }
        });
        return false;
    });

    // 排序
    table.on('sort(tbf)', function (obj) {
        table.reload('tb', {
            initSort: obj,
            where: {
                sort: obj.field,
                order: obj.type
            }
        });
    });

    // 输入框字符串长度限制(需要在input框中添加maxlength属性)
    form.verify({
        length: function (value, item) {
            var maxlength = item.getAttribute('maxlength');
            if (value.length > maxlength) {
                return '最多只能输入' + maxlength + '个字符';
            }

            // unicode编码中，中文双字符，英文单字符
            // var sum = 0;
            // for (i = 0; i < value.length; i++) {
            //     if ((value.charCodeAt(i) >= 0) && (value.charCodeAt(i) <= 255)) {
            //         sum = sum + 1;
            //     } else {
            //         sum = sum + 2;
            //     }
            // }
        }
    });

    var baselist = {

        // 渲染表格
        renderTable: function (param) {

            // 基础参数
            var cfg = {
                id: 'tb',
                elem: '#lay_table',
                title: '',
                method: 'post',
                url: '',
                toolbar: '#toolbar',
                defaultToolbar: [],
                cols: [],
                page: {
                    layout: ['prev', 'page', 'next', 'skip', 'limit', 'count'], //自定义分页布局                
                    theme: "#1E9FFF"
                },
                limit: 10,
                limits: [10, 30, 60, 100],
                loading: true,
                cellMinWidth: 150,
                size: 'lg',
                text: {
                    none: '暂无相关数据'
                },
                done: function (res, curr, count) {
                    $("table").css("width", "100%");
                    $(".layui-table th").css("font-weight", "600");
                }
            };

            cfg = $.extend(cfg, param);

            // 参数校验
            if (!baselist.checkTableCfg(cfg)) {
                return;
            }

            table.render(cfg);
        },

        // 表格参数校验
        checkTableCfg: function (cfg) {
            if (cfg.cols == "") {
                layer.msg('参数cols不能为空', {icon: 5, offset: '10px', anim: 1});
                return false;
            }
            return true;
        },

        /*
         * 弹出层(iframe)
         *
         * */
        openIframeLayer: function (param) {

            // 基础参数
            var cfg = {
                type: 2,
                resize: false,
                btn: ['保存', '取消'],
                btnAlign: 'c',
                scrollbar: false,
                yes: function (index, layero) {
                    // 获取iframe页的窗口对象
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                    // 获取表单中的确定按钮
                    var submitButton = iframeWin.document.getElementById("submit");
                    // 触发确定按钮事件
                    submitButton.click();
                }
            };

            cfg = $.extend(cfg, param);

            layer.open(cfg);
        },

        /*
         * 验证必须选中行
         *
         * */
        validateSelected: function () {
            var checkStatus = table.checkStatus('tb');
            var records = checkStatus.data;
            if (records == null || records.length == 0) {
                layer.alert('请至少选中一条记录!', {icon: 5});
                return false;
            }
            return true;
        },

        /*
         * 验证单行选中，并返回选中行
         *
         * */
        validateSingleSelected: function () {
            var checkStatus = table.checkStatus('tb');
            var records = checkStatus.data;
            if (records == null || records.length == 0) {
                layer.alert('请先选中一条记录!', {icon: 5});
                return false;
            } else if (records.length > 1) {
                layer.alert('不能同时选择多条记录!', {icon: 2});
                return false;
            }
            return records[0];
        },

        /*
         * 验证多行选中，并返回选中行
         *
         * */
        validateMultiSelected: function () {
            var checkStatus = table.checkStatus('tb');
            var records = checkStatus.data;
            if (records == null || records.length == 0) {
                layer.alert('请至少选中一条记录!', {icon: 5});
                return false;
            }
            return records;
        },

        /*
         * xm-select单选配置
         *
         * */
        renderRadioSelect: function (options) {

            // 基础配置
            var cfg = {
                radio: true,
                clickClose: true,
                filterable: true,
                model: {
                    label: {
                        icon: 'hidden',
                        type: 'block',
                        block: {
                            showCount: 0,
                            showIcon: false
                        }
                    }
                },
                theme: {
                    color: '#1E9FFF'
                }
            };

            cfg = $.extend(cfg, options);

            return xmSelect.render(cfg);
        },

        /*
         * xm-select多选配置
         *
         * */
        renderMultiSelect: function (options) {

            // 基础配置
            var cfg = {
                filterable: true,
                toolbar: {
                    show: true,
                    list: ['ALL', 'CLEAR', 'REVERSE']
                },
                theme: {
                    color: '#1E9FFF'
                }
            };

            cfg = $.extend(cfg, options);

            return xmSelect.render(cfg);
        },

        /*
         * xm-select获取远程数据
         *
         * */
        remoteSelectData: function (selectObject, url, data) {
            $.ajax({
                type: "GET",
                url: url,
                data: data,
                dataType: "json",
                success: function (res) {
                    if (res.data != null) {
                        selectObject.update({
                            data: res.data
                        });
                    }
                }
            });
        },

        doPost: function (url, record) {
            $.ajax({
                type: "POST",
                url: url,
                data: record,
                dataType: "json",
                success: function (res) {
                    if (res.success) {
                        parent.layer.msg(res.message, {icon: 1, offset: '10px', anim: 1});
                        $('.layui-laypage-btn').click();
                    } else {
                        parent.layer.alert(res.message, {icon: 5});
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    parent.layer.alert(textStatus, {icon: 5});
                }
            })
        },

        /*
         * 删除
         *
         * */
        removeRow: function (url, data) {
            layer.confirm('您确定要删除该条记录吗？', {
                title: '温馨提示',
                icon: 3
            }, function (index) {
                $.post(url, data, function (res) {
                    layer.closeAll();
                    if (res.success) {
                        parent.layer.msg(res.message, {icon: 1, offset: '10px', anim: 1});
                        $('.layui-laypage-btn').click();
                    } else {
                        parent.layer.alert(res.message, {icon: 5});
                    }
                });
            });
        },

        /*
        * 弹出层表单提交
        *
        * */
        submitForm: function (url, data) {
            var loadIndex = parent.layer.load(1, {
                shade: [0.5, '#c2c2c2']
            });
            $.ajax({
                type: "POST",
                url: url,
                data: data,
                dataType: "json",
                success: function (res) {
                    parent.layer.close(loadIndex);

                    if (res.success) {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        parent.parent.layer.msg(res.message, {icon: 1, offset: '10px', anim: 1});

                        var layPageBtn = parent.$('.layui-laypage-btn');
                        if (layPageBtn.length > 0) {
                            layPageBtn.click();	// 刷新表格(分页控件的"确定"按钮)
                        } else {
                            parent.layui.table.reload('tb');	// 重载表格
                        }
                    } else {
                        parent.layer.alert(res.message, {icon: 5});
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.close(loadIndex);

                    parent.layer.alert(textStatus, {icon: 5});
                }
            })
        }
    };

    exports('baselist', baselist);
});