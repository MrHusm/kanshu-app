<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
    <title>${category.name}</title>
    <link rel="stylesheet" href="/css/reset_5.css">
    <link rel="stylesheet" href="/css/bookStack.css">
    <script src="/js/jquery.min.js"></script>
</head>
<body class="newBg">
<div id="classifyBoxTwo" class="classifyBox">
    <div class="classifyBox2 newClassifyBox2 ">
        <div class="newClear">
            <div class="classifyBoxTit classifyBoxTit1 <#if !childCategoryId?? || childCategoryId==''>classifyBox2Active</#if>" data-id="tit1-1" onclick="userClick(this,${category.categoryId?c},'')">${category.name}</div>
            <ul class="classifyBoxUl classifyBoxUl1 newClear">
                <#if childCategorys??>
                    <#list childCategorys as childCategory>
                        <li <#if  childCategoryId?? && childCategoryId == '${childCategory.categoryId?c}'>class="classifyBox2Active"</#if> data-id="tit1-${childCategory_index+2}" onclick="userClick(this,${category.categoryId?c},${childCategory.categoryId?c})">${childCategory.name}</li>
                    </#list>
                </#if>
            </ul>
        </div>
        <div class="newClear">
            <div class="classifyBoxTit classifyBoxTit2 <#if !isFull?? || isFull==''>classifyBox2Active</#if>" data-id="tit2-1" onclick="userClick2(this,'')">不限</div>
            <ul class="classifyBoxUl classifyBoxUl2 newClear">
                <li data-id="tit2-2" <#if isFull?? && isFull=='1'>class="classifyBox2Active"</#if> onclick="userClick2(this,1)">完结</li>
                <li data-id="tit2-3" <#if isFull?? && isFull=='0'>class="classifyBox2Active"</#if> onclick="userClick2(this,0)">连载</li>
            </ul>
        </div>
    </div>
</div>
<div class="hr"></div>
<article class="article pageLoad">
</#if>
    <#if pageFinder??>
        <#list pageFinder.data as book>
            <section class="bookListBox" onclick="bookInfo(${book.bookId},'${book.title}')">
                <div class="bookListImg">
                    <img data-echo="${book.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                </div>
                <div class="bookList">
                    <div class="bookName">${book.title}</div>
                    <div class="bookInfo">
                        <#if book.intro?length gt 40>
                            ${book.intro?substring(0,40)}...
                        <#else>
                            ${book.intro}
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
<div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/categoryBooks.go?page=${pageFinder.pageNo+1}&syn=1&categoryId=${categoryId}&childCategoryId=<#if childCategoryId??>${childCategoryId}</#if>&isFull=<#if isFull??>${isFull}</#if>" style="display:none;"></div>
<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script type="text/javascript" src="/js/echo.min.js"></script>
<script type="application/javascript">
    var _categoryId = ${categoryId};
    var _childCategoryId = <#if childCategoryId??>${childCategoryId}<#else>null</#if>;
    var _isFull = <#if isFull??>${isFull}<#else>null</#if>;

    function userClick(obj,categoryId,childCategoryId){
        _categoryId = categoryId;
        _childCategoryId = childCategoryId;
        if ($(obj).is('.classifyBoxTit')) {
            $('.classifyBoxTit1').removeClass('classifyBox2Active');
            $('.classifyBoxTit1').next().find('li').removeClass('classifyBox2Active');
            $('.classifyBoxTit1').addClass('classifyBox2Active');
        }else {
            var val = $(obj).data('id');
            $('.classifyBoxUl1').find('li').each(function (i,el) {
                if ($(el).data('id') == val) {
                    $(this).parent().prev().removeClass('classifyBox2Active');
                    $(this).siblings('li').removeClass('classifyBox2Active');
                    $(this).addClass('classifyBox2Active');
                }
            })
        }
        var url = "/portal/categoryBooks.go?categoryId="+_categoryId+"&childCategoryId="+(_childCategoryId==null?'':_childCategoryId)+"&isFull="+(_isFull==null?"":_isFull);
        window.location.href=url;
    }
    function userClick2(obj,isFull){
        _isFull = isFull;
        if ($(obj).is('.classifyBoxTit')) {
            $('.classifyBoxTit2').removeClass('classifyBox2Active');
            $('.classifyBoxTit2').next().find('li').removeClass('classifyBox2Active');
            $('.classifyBoxTit2').addClass('classifyBox2Active');
        }else {
            var val = $(obj).data('id');
            $('.classifyBoxUl2').find('li').each(function (i,el) {
                if ($(el).data('id') == val) {
                    $(this).parent().prev().removeClass('classifyBox2Active');
                    $(this).siblings('li').removeClass('classifyBox2Active');
                    $(this).addClass('classifyBox2Active');
                }
            })
        }
        var url = "/portal/categoryBooks.go?categoryId="+_categoryId+"&childCategoryId="+(_childCategoryId==null?'':_childCategoryId)+"&isFull="+(_isFull==null?"":_isFull);
        window.location.href=url;
    }

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