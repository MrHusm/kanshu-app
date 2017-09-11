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
    <div class="h6"><i class="h6Icon"></i><span style="vertical-align: middle;font-size: 22px">本书作者还写了</span></div>
    <#list authorBooks as authorBook >
        <#if authorBook.bookId != book.bookId>
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
        </#if>
    </#list>
</div>
</#if>

<#if relatedBooks?? && relatedBooks?size gt 0>
<div class="hr"></div>
<div class="pd1Box">
    <div class="h6"><i class="h6Icon"></i><span style="vertical-align: middle;font-size: 22px">看了本书的用户还看了</span></div>
    <#list relatedBooks as relatedBook>
        <#if relatedBook.bookId != book.bookId>
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