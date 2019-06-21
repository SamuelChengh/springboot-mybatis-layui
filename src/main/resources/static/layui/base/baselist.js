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
    table.on('sort(tbf)', function(obj){
    	table.reload('tb', {
    		initSort : obj,
			where : {
				sort : obj.field,
				order : obj.type
			}					
    	});
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
                cols: [],
                page: {
                    layout: ['prev', 'page', 'next', 'skip', 'limit', 'count'], //自定义分页布局                
                    theme: "#1E9FFF"
                },
                limit: 15,
                limits: [15, 30, 60, 100],
                loading: true,
                cellMinWidth: 100,
                height: 'full-40',
                size: 'sm',
                text: {
                    none: '暂无相关数据'
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
            if(cfg.url == ""){
                layer.msg('参数url不能为空', {icon: 5});
                return false;
            }
            if(cfg.cols == ""){
                layer.msg('参数cols不能为空', {icon: 5});
                return false;
            }
            return true;
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
        removeRow: function (url) {
            layer.confirm('您确定要删除该条记录吗？', {
                title: '温馨提示',
                icon: 3
            }, function (index) {
            	$.post(url, function(res) {
				  	if (res.return_code == '1') {				  		
				  		layer.closeAll();
                		layer.msg(res.return_msg, {icon: 1});
                		table.reload("tb");
					} else {
						layer.msg(res.return_msg, {icon: 2});
					}
				});
            });
        },

        // 双击行事件
        doubleRow: function (param) {
            table.on('rowDouble(tbf)', function (obj) {
                var record = obj.data;

                baselist.updateRow(param, record);
            });
        },

        // iframe弹出层参数
        iframeLayerConfig: function(param){

            // 弹出层基础参数
            var cfg = {
                type: 2,
                resize: false,
                btn: ['确定', '取消'],
                yes: function(index, layero){

                    // 获取弹出层中的form表单
                    var form = layer.getChildFrame('form', index);

                    // 获取表单中的确定按钮
                    var formButton = form.find('button')[0];

                    // 触发确定按钮事件
                    formButton.click();
                }
            };

            cfg = $.extend(cfg, param);

            return cfg;
        },

        // 验证必须只有一行选中
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

        // 验证必须选中
        validateSelected: function () {
            var checkStatus = table.checkStatus('tb');
            var records = checkStatus.data;
            if (records == null || records.length == 0) {
                layer.alert('请至少选中一条记录!', {icon: 5});
                return false;
            }
            return true;
        },
        
        // 验证必须选中,并返回选中记录
        validateMultiSelected: function () {
            var checkStatus = table.checkStatus('tb');
            var records = checkStatus.data;
            if (records == null || records.length == 0) {
                layer.alert('请先选中一条记录!', {icon: 5});
                return false;
            }
            return records;
        }, 

        ajaxPost: function (url, record) {
            $.ajax({
                type: "POST",
                url: url,
                data: record,
                dataType: "json",
                success: function (res) {
                	if (res.return_code == '1') {
                        table.reload("tb");
                        layer.msg(res.return_msg, {icon: 1});
                    } else {
                    	layer.msg(res.return_msg, {icon: 2});
                    }
                }
            })
        },
        
        ajaxGet: function (url) {
        	$.ajax({
                type: "GET",
                url: url,
                dataType: "json",
                success: function(res){
                	if (res.return_code == '1') {
                        table.reload("tb");
                        layer.msg(res.return_msg, {icon: 1});
                    } else {
                    	layer.msg(res.return_msg, {icon: 2});
                    }
                }
            });
        },
    };

    exports('baselist', baselist);
});