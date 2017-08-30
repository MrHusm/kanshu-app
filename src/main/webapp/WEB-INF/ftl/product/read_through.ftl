<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>已读完</title>
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/readThrough.css">
</head>
<body>
<#if authorBooks??>
<div class="pd1Box">
    <div class="h6"><i class="h6Icon"></i>本书作者还写了</div>
    <#list authorBooks as authorBook >
        <section class="bookListBox" onclick="bookInfo(${authorBook.bookId},'${authorBook.title}')">
            <img class="bookListImg" src="${authorBook.coverUrl}">
            <div class="bookList">
                <div class="bookName">${authorBook.title}</div>
                <div class="bookInfo">
                    <#if authorBook.intro?length gt 40>
                        ${authorBook.intro?substring(0,40)}...
                    <#else>
                        ${authorBook.intro}
                    </#if>
                </div>
                <div class="authorBox">
                    <div class="authorNmae">${authorBook.authorPenname}</div>
                    <div class="bookGenre">
                        <div class="bookGenrePublic">${authorBook.categorySecName}</div>
                        <div class="bookGenrePublic bookGenrePublicStyle">${authorBook.categoryThrName}</div>
                    </div>
                </div>
            </div>
        </section>
    </#list>
</div>
</#if>

<#if driveBooks??>
<div class="hr"></div>
<div class="pd1Box">
    <div class="h6"><i class="h6Icon"></i>看了本书的用户还看了</div>
    <#list relatedBooks as driveBook>
        <section class="bookListBox" onclick="bookInfo(${driveBook.book.bookId},'${driveBook.book.title}')">
            <img class="bookListImg" src="${driveBook.book.coverUrl}">
            <div class="bookList">
                <div class="bookName">${driveBook.book.title}</div>
                <div class="bookInfo">
                    <#if driveBook.book.intro?length gt 40>
                        ${driveBook.book.intro?substring(0,40)}...
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
</div>
</#if>
<script>
    function bookInfo(bookId,title) {
        var url = "/book/bookDetail.go?bookId="+bookId;
        window.JSHandle.goToHtml(url,title,1,1);
    }
</script>
</body>
</html>