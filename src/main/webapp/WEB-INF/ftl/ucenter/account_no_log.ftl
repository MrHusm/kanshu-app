<!DOCTYPE html>
<html class="newBg" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <title>无记录</title>
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/index.css">
</head>
<body>
<div class="pageBox">
    <img class="pageBoxImg" src="/img/icon/notIcon.png" alt="" />
    <div class="pageBoxTxt">
        <#if type='1'>
            暂无充值记录
        <#elseif type = '2'>
            暂无消费记录
        </#if>
    </div>
    <#--<div class="pageBoxBtn">-->
        <#--<input class="pageBtnPub" type="button" onclick="window.history.go(-1)" value="返回">-->
        <#--<input class="pageBtnPub" onclick="window.location.reload()" type="button" value="重新加载">-->
    <#--</div>-->
</div>
</body>
</html>