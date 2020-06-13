<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>后台</title>
    <@fm.header />
    <link href="/resources/static/css/index.css" rel="stylesheet"/>
</head>

<style>
    .layui-tab-title li:nth-child(1) i {
        display: none;
    }
</style>

<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <!-- 左侧导航 -->
    <div class="layui-side-body">
        <div class="layui-logo" id="logo_text">后台管理系统</div>
        <div class="layui-logo" id="logo_image" style="text-align: left;display: none;">
            <i class="layui-icon layui-icon-windows" style="color: #42b983;padding-left: 20px;"></i>
        </div>
        <div class="layui-side layui-bg-black">
            <div class="layui-side-scroll">
                <ul id="nav-tree" class="layui-nav layui-nav-tree" lay-filter="test"></ul>
            </div>
        </div>
    </div>

    <!-- 右侧顶部 -->
    <div class="layui-header">
        <div class="layui-nav" style="padding: 0 10px;">
            <div class="layui-nav-item">
                <a href="javascript:;" onclick="javascript:switchNav();" style="color: #333;padding: 0 0;">
                    <i id="icon-shrink" class="layui-icon layui-icon-shrink-right" style="font-size: 15px;"></i>
                </a>
            </div>
        </div>

        <div class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;" onclick="javascript:switchFullScreen();" style="color: #333;">
                    <i id="icon-screen" class="layui-icon layui-icon-screen-full" style="font-size: 15px;"></i>
                </a>
            </li>

            <div class="layui-nav-item">
                <a href="javascript:;">
                    <img src="/resources/static/images/head.gif" class="user-avatar">
                    <span style="color: #333;"><#if user?exists && user.nickName != ''>${user.nickName!''}</#if></span>
                </a>
                <dl class="layui-nav-child">
                    <dd>
                        <a href="#" onclick="javascript:resetPwd();">修改密码</a>
                    </dd>
                    <dd>
                        <a href="#" onclick="javascript:logout();">退出系统</a>
                    </dd>
                </dl>
            </div>
        </div>
    </div>

    <!-- 右侧内容 -->
    <div class="layui-body">
        <div class="layui-tab" lay-filter="tabs_box" lay-allowclose="true">
            <ul id="top_tabs" class="layui-tab-title">
                <li class="layui-this" lay-id="0">首页</li>
            </ul>
            <div class="layui-tab-content">
                <div class="layui-tab-item layui-show"></div>
            </div>
        </div>
    </div>

    <!-- 底部 -->
    <#--	<div class="layui-footer footer">-->
    <#--		<center>Copyright © 2011-2020, All Rights Reserved.</center>-->
    <#--	</div>-->
</div>

<script type="text/javascript">
    layui.use(['element', 'layer'], function () {
        $ = layui.jquery;
        element = layui.element;

        // 左侧导航
        createLeftNav();

        element.on('nav(test)', function (data) {
            spreadNav();
        });
    });

    // 左侧导航
    function createLeftNav() {
        $.ajax({
            type: "GET",
            url: "user/getMenuList",
            dataType: "json",
            success: function (res) {
                var parentHtml = document.getElementById("nav-tree");
                for (var i = 0; i < res.length; i++) {
                    var menu = res[i];

                    var li = document.createElement("li");
                    li.setAttribute("class", "layui-nav-item layui-nav-itemed");

                    var a = document.createElement("a");
                    a.setAttribute("href", "javascript:;");
                    var icon = document.createElement("i");
                    icon.setAttribute("class", "layui-icon " + menu.parentIcon);
                    icon.setAttribute("style", "color: #42b983;");
                    a.appendChild(icon);
                    var cite = document.createElement("cite");
                    cite.setAttribute("style", "padding-left: 20px;");
                    cite.appendChild(document.createTextNode(menu.parentName));
                    a.appendChild(cite);
                    li.appendChild(a);

                    var dl = document.createElement("dl");
                    dl.setAttribute("class", "layui-nav-child");
                    for (var j = 0; j < menu.childMenus.length; j++) {
                        var dd = document.createElement("dd");

                        var aa = document.createElement("a");
                        aa.setAttribute("class", "nav-tab");
                        aa.setAttribute("style", "padding-left: 52px;");
                        aa.setAttribute("href", "javascript:;");
                        aa.setAttribute("src", menu.childMenus[j].pageUrl);
                        aa.setAttribute("onclick", "navClick('" + JSON.stringify(menu.childMenus[j]) + "')");
                        aa.appendChild(document.createTextNode(menu.childMenus[j].name));
                        dd.appendChild(aa);

                        dl.appendChild(dd);
                    }
                    li.appendChild(dl);

                    parentHtml.appendChild(li);
                }

                element.render('nav');
            }
        })
    }

    // 左侧导航
    function navClick(menu) {
        var menuObj = JSON.parse(menu);
        addTab(menuObj.id, menuObj.name, menuObj.pageUrl);
    }

    function addTab(id, title, url) {
        var isActive = false;
        $.each($(".layui-tab-title li[lay-id]"), function () {
            if ($(this).attr("lay-id") == id) {
                isActive = true;
            }
        });

        if (isActive) {
            element.tabChange('tabs_box', id);
            // 切换后刷新iframe
            $(".layui-tab-item.layui-show").find("iframe").attr("src", url);
        } else {
            element.tabAdd('tabs_box', {
                id: id,
                title: title,
                content: createFrame(url)
            });
            element.tabChange('tabs_box', id);
        }
    }

    function createFrame(url) {
        return '<iframe id="mainiframe" scrolling="auto" src="' + url + '" style="width: 100%;height: 100%;position: absolute;border: none;"></iframe>';
    }

    // 菜单切换
    var isShow = true;

    function switchNav() {
        if (isShow) {
            shrinkNav();
        } else {
            spreadNav();
        }
    }

    // 展开菜单
    function spreadNav() {
        $("#logo_text").show();
        $("#logo_image").hide();

        //左侧导航宽度
        $('.layui-side.layui-bg-black').width(200);

        //将footer和body的宽度修改
        $('.layui-header').css('left', 200 + 'px');
        $('.layui-body').css('left', 200 + 'px');
        $('.layui-footer').css('left', 200 + 'px');

        $('#icon-shrink').removeClass("layui-icon layui-icon-spread-left");
        $('#icon-shrink').addClass("layui-icon layui-icon-shrink-right");

        isShow = true;
    }

    // 收缩菜单
    function shrinkNav() {
        $("#logo_text").hide();
        $("#logo_image").show();

        //左侧导航宽度
        $('.layui-side.layui-bg-black').width(52);

        //将footer和body的宽度修改
        $('.layui-header').css('left', 52 + 'px');
        $('.layui-body').css('left', 52 + 'px');
        $('.layui-footer').css('left', 52 + 'px');

        $('#icon-shrink').removeClass("layui-icon layui-icon-shrink-right");
        $('#icon-shrink').addClass("layui-icon layui-icon-spread-left");

        isShow = false;
    }

    // 全屏切换
    function switchFullScreen() {
        var fullscreenElement = document.fullscreenElement || document.mozFullScreenElement
            || document.webkitFullscreenElement;

        if (fullscreenElement == null) {
            entryFullScreen();
            $('#icon-screen').removeClass("layui-icon layui-icon-screen-full");
            $('#icon-screen').addClass("layui-icon layui-icon-screen-restore");
        } else {
            exitFullScreen();
            $('#icon-screen').removeClass("layui-icon layui-icon-screen-restore");
            $('#icon-screen').addClass("layui-icon layui-icon-screen-full");
        }
    }

    // 进入全屏
    function entryFullScreen() {
        var docE = document.documentElement;
        if (docE.requestFullScreen) {
            docE.requestFullScreen();
        } else if (docE.mozRequestFullScreen) {
            docE.mozRequestFullScreen();
        } else if (docE.webkitRequestFullScreen) {
            docE.webkitRequestFullScreen();
        }
    }

    // 退出全屏
    function exitFullScreen() {
        var docE = document;
        if (docE.exitFullscreen) {
            docE.exitFullscreen();
        } else if (docE.mozCancelFullScreen) {
            docE.mozCancelFullScreen();
        } else if (docE.webkitCancelFullScreen) {
            docE.webkitCancelFullScreen();
        }
    }

    // 修改密码
    function resetPwd() {
        layer.open({
            type: 2,
            title: '修改密码',
            content: '/user/resetPwd',
            resize: false,
            area: ['360px', '285px'],
            btn: ['确定', '取消'],
            btnAlign: 'c',
            yes: function (index, layero) {
                // 获取iframe页的窗口对象
                var iframeWin = window[layero.find('iframe')[0]['name']];
                // 获取表单中的确定按钮
                var submitButton = iframeWin.document.getElementById("submit");
                // 触发确定按钮事件
                submitButton.click();
            }
        })
    }

    // 退出系统
    function logout() {
        layer.confirm('您确定现在退出系统吗？', {
            title: '温馨提示',
            icon: 3
        }, function () {
            window.location.href = "/logout";
        });
    }
</script>
</body>
</html>