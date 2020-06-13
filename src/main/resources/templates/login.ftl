<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>米得教育</title>
    <link rel="stylesheet" href="/resources/static/css/login.css" type="text/css"/>
</head>

<script>
    if (window.parent.length > 0) {
        window.parent.location = "/user/login";
    }
</script>

<body>
<div class="login_box">
    <div class="login-name">用户名登录</div>
    <form id="login_form" class="login_form" action="/login" method="post" accept-charset="utf-8">
        <div class="login-input">
            <div>用户名</div>
            <input id="username" type="text" name="username" autocomplete="off" placeholder="请输入用户名" required
                   oninvalid="setCustomValidity('请输入用户名')" oninput="setCustomValidity('')"/>
        </div>
        <div class="login-input">
            <div>密码</div>
            <input id="password" type="password" name="password" autocomplete="off" placeholder="请输入密码" maxlength="16"
                   required oninvalid="setCustomValidity('请输入密码')" oninput="setCustomValidity('')"/>
        </div>
        <div class="login-input">
            <div>验证码</div>
            <input id="randomCode" type="text" name="randomCode" autocomplete="off" placeholder="请输入验证码" maxlength="4"
                   required style="width: 65%;" oninvalid="setCustomValidity('请输入验证码')"
                   oninput="setCustomValidity('')"/>
            <img id="codeImg" title="看不清，换一张" src="/captchaImage" style="width: 32%;"
                 onclick="document.getElementById('codeImg').src='/captchaImage?'+Math.random()"/>
        </div>
        <div class="error">
            <#if msg?exists>${msg}</#if>
        </div>
        <div class="login">
            <input id="btn-submit" type="submit" class="submit" value="立即登录"/>
        </div>
    </form>
</div>
</body>

<script src="/resources/static/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#btn-submit").click(function () {
            login();
        });

        document.onkeydown = function (e) {
            if (e.keyCode == 13) {
                login();
            }
        };
    });

    function login() {
        var username = $("#username").val();
        username = $.trim(username);
        var password = $("#password").val();
        password = $.trim(password);
        if (username != "" && password != "") {
            $("#login_form").submit();
        }
    }
</script>
</html>