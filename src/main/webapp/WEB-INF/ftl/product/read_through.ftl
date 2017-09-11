<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>已读完</title>
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/readThrough.css">
</head>
<script>
    window.JSHandle.setBookIsOver(${isFull});
</script>
<body>
<#if authorBooks??>
<div class="pd1Box">
    <div class="h6"><i class="h6Icon"></i><span style="vertical-align: middle;font-size: 22px">本书作者还写了</span></div>
    <#list authorBooks as authorBook >
        <section class="bookListBox" onclick="bookInfo(${authorBook.bookId?c},'${authorBook.title}')">
            <img class="bookListImg" data-echo="${authorBook.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
            <div class="bookList">
                <div class="bookName">${authorBook.title}</div>
                <div class="bookInfo">
                    <#if authorBook.intro??>
                        ${authorBook.intro?replace("　","")?replace("　","")}
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

<#if relatedBooks?? && relatedBooks?size gt 0>
<div class="hr"></div>
<div class="pd1Box">
    <div class="h6"><i class="h6Icon"></i><span style="vertical-align: middle;font-size: 22px">看了本书的用户还看了</span></div>
    <#list relatedBooks as relatedBook>
        <#if relatedBook.bookId != bookId>
            <section class="bookListBox" onclick="bookInfo(${relatedBook.bookId?c},'${relatedBook.title}')">
                <img class="bookListImg" data-echo="${relatedBook.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                <div class="bookList">
                    <div class="bookName">${relatedBook.title}</div>
                    <div class="bookInfo">
                        <#if relatedBook.intro??>
                            ${relatedBook.intro?replace("　","")?replace("　","")}
                        </#if>
                    </div>
                    <div class="authorBox">
                        <div class="authorNmae">${relatedBook.authorPenname}</div>
                        <div class="bookGenre">
                            <div class="bookGenrePublic">${relatedBook.categorySecName}</div>
                            <div class="bookGenrePublic bookGenrePublicStyle">${relatedBook.categoryThrName}</div>
                        </div>
                    </div>
                </div>
            </section>
        </#if>
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