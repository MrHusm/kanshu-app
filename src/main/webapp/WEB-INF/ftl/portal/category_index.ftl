<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>精选</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/reset_5.css">
    <script src="/js/jquery.min.js"></script>
</head>
<body>
    <aside class="newBookBox">
        <ul class="bookPopular">
            <li class="newbook">重磅新书</li>
            <li class="newInfo">最新大神著作</li>
            <li class="newbookBox"><img class="newbookImg" src="/img/other/book1.jpg"></li>
        </ul>
        <ul class="bookPopular">
            <li class="newbook">男生最爱</li>
            <li class="newInfo">都市玄幻万本爽文</li>
            <li class="newImg"><img class="newbookImg" src="/img/other/book2.jpg"></li>
        </ul>
        <ul class="bookPopular">
            <li class="newbook">女生最爱</li>
            <li class="newInfo">言情后宫跌宕起伏</li>
            <li class="newImg"><img class="newbookImg" src="/img/other/book3.jpg"></li>
        </ul>
    </aside>
    <div class="hr"></div>
        <#if data??>
            <#list data as map>
                <#list map?keys as key>
                    ${key}<br>
                    <#list map[key] as category>
                        ${category.name}
                    </#list>
                </#list>
                -----------------------------------
            </#list>
        </#if>


<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script>

</script>
</body>
</html>