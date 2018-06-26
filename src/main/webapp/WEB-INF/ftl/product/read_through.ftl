<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="/css/main.css" />
    <title>已读完</title>
    <script>
        window.JSHandle.setBookIsOver(${isFull});
    </script>
</head>

<body>
<div class="content">
    <p class="readFinish"><#if isFull == 0>未完待续，作者持续更新中<#else>已读完本书</#if></p>
<#if authorBooks?? && (authorBooks?size>0)>
    <div class="otherBook">
        <h3 class="hTitle">作者其他书</h3>
        <ul class="otherList clearfix">
            <#list authorBooks as authorBook>
                <li onclick="bookInfo(${authorBook.bookId?c},'${authorBook.title}')">
                    <div class="coverImg">
                        <img data-echo="${authorBook.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                        <p>${authorBook.title}</p>
                    </div>
                </li>
            </#list>
        </ul>
    </div>
</#if>
<#if relatedBooks??  && (relatedBooks?size>0)>
    <div class="otherBook">
        <h3 class="hTitle">看过这本书的人也看过</h3>
        <ul class="otherList clearfix">
            <#list relatedBooks as relatedBook>
                <li onclick="bookInfo(${relatedBook.bookId?c},'${relatedBook.title}')">
                    <div class="coverImg">
                        <img data-echo="${relatedBook.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                        <p>${relatedBook.title}</p>
                    </div>
                </li>
            </#list>
        </ul>
    </div>
</#if>
</div>
<script type="text/javascript" src="/js/echo.min.js"></script>
<script>
    function bookInfo(bookId,title) {
        var version = <#if version??>${version?c}<#else>null</#if>;
        if(version != null && version >= 120){
            window.JSHandle.openBookIntroduction(bookId);
        }else{
            var url = "/book/bookDetail.go?bookId="+bookId;
            window.JSHandle.goToHtml(url,title,1,1);
        }
    }

    Echo.init({
        offset: 0,
        throttle: 0
    });
</script>
</body>
</html>