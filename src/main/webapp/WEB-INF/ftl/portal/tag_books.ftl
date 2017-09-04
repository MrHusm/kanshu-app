<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <title>${tag}</title>
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/channel.css">
</head>
<body class="newBg">
<article class="article pageLoad">
</#if>
    <#if pageFinder??>
        <#list pageFinder.data as book>
            <section class="bookListBox" onclick="bookInfo(${book.bookId?c},'${book.title}')">
                <div class="bookListImg">
                    <img data-echo="${book.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                </div>
                <div class="bookList">
                    <div class="bookName">${book.title}</div>
                    <div class="bookInfo">
                        <#if book.intro??>
                            <#if book.intro?length gt 40>
                                ${book.intro?substring(0,40)}...
                            <#else>
                                ${book.intro}
                            </#if>
                        </#if>
                    </div>
                    <div class="authorBox">
                        <div class="authorNmae">${book.authorPenname}</div>
                        <div class="bookGenre">
                            <div class="bookGenrePublic">${book.categorySecName}</div>
                            <div class="bookGenrePublic bookGenrePublicStyle">${book.categoryThrName}</div>
                        </div>
                    </div>
                </div>
            </section>
        </#list>
    </#if>
<#if syn=='0'>
</article>
<div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/tagBooks.go?page=${pageFinder.pageNo+1}&syn=1&tag=${tag}" style="display:none;"></div>
<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script type="text/javascript" src="/js/echo.min.js"></script>
<script type="application/javascript">
    function bookInfo(bookId,title) {
        var url = "/book/bookDetail.go?bookId="+bookId;
        window.JSHandle.goToHtml(url,title,1,1);
    }
</script>
</#if>
<script type="application/javascript">
    Echo.init({
        offset: 0,
        throttle: 0
    });
</script>
<#if syn=='0'>
</body>
</html>
</#if>