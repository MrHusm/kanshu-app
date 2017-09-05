<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>${book.title}</title>
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/Reading.css">
</head>
<body>
<figure>
    <div class="readingBox1">
        <div class="readingBoxBook">
            <img src="${book.coverUrl}" alt="" class="readingBook" />
            <ul class="readingBoxBookInfo">
                <li class="readingBoxName">${book.title}</li>
                <li class="readingBoxAuthor">${book.authorPenname}</li>
                <li class="readingBoxType">${book.categoryThrName}</li>
                <li class="readingBoxsize">${wordCount}</li>
            </ul>
        </div>
        <div class="readingBtn">
            <input type="button" onclick="window.JSHandle.openRead(${book.bookId?c},'${book.title}','${book.coverUrl}',${maxChapterIndex?c})" value="<#if readBtn == 0>免费试读<#else>阅读</#if>" class="readingBtnPub readingActive">
            <input type="button" onclick="window.JSHandle.addToShelf(${book.bookId?c},'${book.title}','${book.coverUrl}',${maxChapterIndex?c})" value="+书架" class="readingBtnPub">
        </div>
        <div class="readingCont">
            <p class="readingContP">
                <#if book.intro??>
                    <#if book.intro?length gt 200>
                        ${book.intro?substring(0,200)}...
                    <#else>
                        ${book.intro}
                    </#if>
                </#if>
            </p>
            <#if tags??>
                <div class="tag newClear">
                    <#list tags as tag>
                        <div class="tagPub" onclick="bookTag('${tag}')"><span class="tagCont tagPubStyle${(tag_index % 3)+1}">${tag}</span></div>
                    </#list>
                </div>
            </#if>

        </div>
        <div class="radingBottonBox" onclick="window.JSHandle.openCatalog(${book.bookId?c},'${book.title}','${book.coverUrl}',${maxChapterIndex?c})">
            <div class="readingBotL">
                <img src="/img/icon/menuIcon.png" alt="" class="readingBotLImg" />
                <span>目录</span>
            </div>
            <div class="readingBotR">更新时间：<time datetime="1">${updateDay}</time></div>
        </div>
    </div>
</figure>
<#if (authorBooks?size>1)>
<div class="hr"></div>
<div class="pd1Box">
    <div class="h6"><i class="h6Icon"></i>本书作者还写了</div>
    <#list authorBooks as authorBook >
        <#if authorBook.bookId != book.bookId>
            <section class="bookListBox" onclick="bookInfo(${authorBook.bookId?c},'${authorBook.title}')">
                <img class="bookListImg" data-echo="${authorBook.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                <div class="bookList">
                    <div class="bookName">${authorBook.title}</div>
                    <div class="bookInfo">
                        <#if authorBook.intro??>
                            <#if authorBook.intro?replace("　","")?replace("　","")?length gt 40>
                                ${authorBook.intro?replace("　","")?replace("　","")?substring(0,40)}...
                            <#else>
                                ${authorBook.intro?replace("　","")?replace("　","")}
                            </#if>
                        </#if>
                    </div>
                    <div class="authorBox">
                        <div class="authorNmae">作者：${authorBook.authorPenname}</div>
                        <div class="bookGenre">
                            <div class="bookGenrePublic">${authorBook.categorySecName}</div>
                            <div class="bookGenrePublic bookGenrePublicStyle">${authorBook.categoryThrName}</div>
                        </div>
                    </div>
                </div>
            </section>
        </#if>
    </#list>
</div>
</#if>

<#if relatedBooks??>
<div class="hr"></div>
<div class="pd1Box">
    <div class="h6"><i class="h6Icon"></i>看了本书的用户还看了</div>
    <#list relatedBooks as driveBook>
        <section class="bookListBox" onclick="bookInfo(${driveBook.book.bookId?c},'${driveBook.book.title}')">
            <img class="bookListImg" data-echo="${driveBook.book.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
            <div class="bookList">
                <div class="bookName">${driveBook.book.title}</div>
                <div class="bookInfo">
                    <#if driveBook.book.intro??>
                        <#if driveBook.book.intro?replace("　","")?replace("　","")?length gt 40>
                            ${driveBook.book.intro?replace("　","")?replace("　","")?substring(0,40)}...
                        <#else>
                            ${driveBook.book.intro?replace("　","")?replace("　","")}
                        </#if>
                    </#if>
                </div>
                <div class="authorBox">
                    <div class="authorNmae">作者：${driveBook.book.authorPenname}</div>
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
<div class="hr"></div>
<small>
    版权来源：阅文集团QQ阅读
</small>
<script type="text/javascript" src="/js/echo.min.js"></script>
<script>
    function bookTag(tag){
        var url = "/portal/tagBooks.go?tag="+encodeURI(encodeURI(tag));
        window.JSHandle.goToHtml(url,tag,0,0);
    }

    function bookInfo(bookId,title) {
        var url = "/book/bookDetail.go?bookId="+bookId;
        window.JSHandle.goToHtml(url,title,1,1);
    }

    Echo.init({
        offset: 0,
        throttle: 0
    });
</script>
<script type="text/javascript" src="/book/statisBookExpand.go?bookId=${book.bookId?c}&type=1"></script>
</body>
</html>