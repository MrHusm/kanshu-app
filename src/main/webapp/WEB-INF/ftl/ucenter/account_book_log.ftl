<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>消费记录</title>
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/rechargeRecord.css">
</head>
<body>
<#if records??>
    <div class="pageLoad">
        <#list records as consume>
            <div class="rechargeList">
                <div>${consume.title}</div>
                <div class="rechargeTime">${consume.charge?c}钻&nbsp;|&nbsp;<time>${consume.createDate}</time></div>
            </div>
        </#list>
    </div>
</#if>
<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script>

</script>
</body>
</html>