<#if syn=='0'>
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
<div class="pageLoad">
    <div class="rechargeList">
        <div class="chapterSum">
            <img src="/img/icon/bookIcon.png" alt=""  class="chapterSumIcon"/>
            &nbsp;
            该书已购<sapn>${num}</sapn>章
        </div>
    </div>
</#if>
<#if data??>
    <#list data as result>
        <div class="rechargeList">
            <div>${result.name}</div>
            <div class="rechargeTime">${result.charge?c}钻&nbsp;|&nbsp;<time>${result.createDate?string("yyyy-MM-dd HH:mm:ss")}</time></div>
        </div>
    </#list>
</#if>
<#if syn=='0'>
</div>
<div class="bookLoad" id="autopbn" curpage="${pageNo+1}" totalpage="${pageCount}" rel="/user/findBookAccountLog.go?userId=${userId?c}&page=${pageNo+1}&syn=1&bookId=${bookId?c}" style="display:none;"></div>

<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script>

</script>
</body>
</html>
</#if>