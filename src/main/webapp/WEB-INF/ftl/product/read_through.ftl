<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>已读完</title>
    <link rel="stylesheet" href="/css/rec.css">
</head>
<script>
    (function () {
        document.addEventListener('DOMContentLoaded', function () {
            var html = document.documentElement;
            var windowWidth = html.clientWidth;
            html.style.fontSize = windowWidth / 7.2 + 'px';
            // 等价于html.style.fontSize = windowWidth / 720 * 100 + 'px';
        }, false);
    })();

    window.JSHandle.setBookIsOver(${isFull});
</script>
<body>
<div class="over">恭喜！已读完本书</div>
<#if authorBooks?? && authorBooks?size gt 1>
<div class="centers">
    <div class="centerLogo">
        <div class="icons"></div>
        <div class="writer">作者其他书</div>
    </div>
    <div class="nameBooks">
        <ul class="nameRests">
            <#list authorBooks as authorBook >
                <#if authorBook.bookId != bookId>
                    <li  onclick="bookInfo(${authorBook.bookId?c},'${authorBook.title}')">
                        <img data-echo="${authorBook.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';" alt="">
                        <span>${authorBook.title}</span>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
</div>
<div class="hers"></div>
</#if>

<#if relatedBooks?? && relatedBooks?size gt 0>
<div class="centers">
    <div class="centerLogo">
        <div class="icons"></div>
        <div class="writer">看了本书的人还看了</div>
    </div>
    <div class="nameBooks">
        <ul class="nameRests">
            <#list relatedBooks as relatedBook>
                <#if relatedBook.bookId != bookId>
                    <li onclick="bookInfo(${relatedBook.bookId?c},'${relatedBook.title}')">
                        <img  data-echo="${relatedBook.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                        <span>${relatedBook.title}</span>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
</div>
</#if>

<script type="text/javascript" src="/js/echo.min.js"></script>
<script>
    function bookInfo(bookId,title) {
        var version = <#if version??>${version}<#else>null</#if>;
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