<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>榜单</title>
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/reset_5.css">
    <script src="/js/jquery.min.js"></script>
</head>
<body>
    <article class="pageLoad">
</#if>
        <#if pageFinder.data??>
            <#list pageFinder.data as driveBook>
                <section class="bookListBox">
                    <img class="bookListImg" src="${driveBook.book.coverUrl}">
                    <div class="bookList">
                        <div class="bookName">${driveBook.book.title}</div>
                        <div class="bookInfo">
                            <#if driveBook.book.intro?length gt 50>
                                ${driveBook.book.intro?substring(0,50)}...
                            <#else>
                                ${driveBook.book.intro}
                            </#if>
                        </div>
                        <div class="authorBox">
                            <div class="authorNmae">${driveBook.book.authorPenname}</div>
                            <div class="bookGenre">
                                <div class="bookGenrePublic">${driveBook.book.categorySecName}</div>
                                <div class="bookGenrePublic bookGenrePublicStyle">${driveBook.book.categoryThrName}</div>
                            </div>
                        </div>
                    </div>
                </section>
            </#list>

        </#if>
<#if syn=='0'>
    </article>
    <div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/rankList.go?&page=${pageFinder.pageNo+1}&syn=1&type=${type}" style="display:none;"></div>

<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script>

</script>
</body>
</html>
</#if>