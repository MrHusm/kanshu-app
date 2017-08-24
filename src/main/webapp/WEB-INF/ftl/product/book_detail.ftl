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
                <li class="readingBoxsize">${book.wordCount}</li>
            </ul>
        </div>
        <div class="readingBtn">
            <input type="button" value="<#if readBtn == 0>免费试读<#else>阅读</#if>" class="readingBtnPub readingActive">
            <input type="button" value="收藏" class="readingBtnPub">
        </div>
        <div class="readingCont">
            <p class="readingContP">
                <#if book.intro?length gt 200>
                    ${book.intro?substring(0,200)}...
                <#else>
                    ${book.intro}
                </#if>
            </p>
            <div class="tag newClear">
                <div class="tagPub"><span class="tagCont tagPubStyle1"><#if book.tag??>${book.tag}</#if></span></div>
                <div class="tagPub"><span class="tagCont tagPubStyle2">言情</span></div>
                <div class="tagPub"><span class="tagCont tagPubStyle3">武侠</span></div>
                <div class="tagPub"><span class="tagCont tagPubStyle4">军事</span></div>
            </div>
        </div>
        <div class="radingBottonBox">
            <div class="readingBotL">
                <img src="/img/icon/menuIcon.png" alt="" class="readingBotLImg" />
                <span>目录</span>
            </div>
            <div class="readingBotR">更新时间：<time datetime="1">${book.lastChapterUpdateDate?string("yyyy-MM-dd")}</time>天前</div>
        </div>
    </div>
</figure>
<#if authorBooks??>
<div class="hr"></div>
<div class="pd1Box">
    <div class="h6"><i class="h6Icon"></i>本书作者还写了</div>
    <#list authorBooks as authorBook >
        <section class="bookListBox">
            <img class="bookListImg" src="${authorBook.coverUrl}">
            <div class="bookList">
                <div class="bookName">${authorBook.title}</div>
                <div class="bookInfo">
                    <#if authorBook.intro?length gt 50>
                        ${authorBook.intro?substring(0,50)}...
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
    <#list driveBooks as driveBook>
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
</div>
</#if>
<div class="hr"></div>
<small>
    版权来源：阅文集团QQ阅读
</small>
</body>
</html>