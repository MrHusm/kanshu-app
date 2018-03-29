<#if syn=='0'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${category.name}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel="stylesheet" type="text/css" href="/css/main.css" />
    <script src="/js/jquery.min.js"></script>
    <script type="text/javascript">
        var _categoryId = ${categoryId};
        var _childCategoryId = <#if childCategoryId??>${childCategoryId}<#else>null</#if>;
        var _isFull = <#if isFull??>${isFull}<#else>null</#if>;

        function userClick(obj,categoryId,childCategoryId){
            $(".chooseList li").removeClass("active");
            $(obj).addClass("active");
            var url = "/portal/categoryBooks.go?categoryId="+categoryId+"&childCategoryId="+(childCategoryId==null?'':childCategoryId)+"&isFull="+(_isFull==null?"":_isFull);
            window.location.href=url;
        }

        function userClick2(obj,isFull){
            $(".chooseType span").removeClass("active");
            $(obj).addClass("active");
            var url = "/portal/categoryBooks.go?categoryId="+_categoryId+"&childCategoryId="+(_childCategoryId==null?'':_childCategoryId)+"&isFull="+(isFull==null?"":isFull);
            window.location.href=url;
        }

        function bookInfo(bookId,title) {
            var version = <#if version??>${version}<#else>null</#if>;
            if(version != null && version >= 120){
                window.JSHandle.openBookIntroduction(bookId);
            }else{
                var url = "/book/bookDetail.go?bookId="+bookId;
                window.JSHandle.goToHtml(url,title,1,1);
            }
        }
    </script>
</head>
<body>
<div class="content">
    <div class="topSuspen"><span id="suspenTxt">${category.name}、不限</span><img src="/img/icon/downIcon.png" alt="" /></div>
    <div class="topOpen">
        <div class="classifyList">
            <ul class="chooseList clearfix">
                <li <#if !childCategoryId?? || childCategoryId==''>class="active"</#if> onclick="userClick(this,${category.categoryId?c},null)">全部</li>
                <#if childCategorys??>
                    <#list childCategorys as childCategory>
                        <li <#if childCategoryId?? && childCategoryId == '${childCategory.categoryId?c}'>class="active"</#if> onclick="userClick(this,${category.categoryId?c},${childCategory.categoryId?c})">${childCategory.name}</li>
                    </#list>
                </#if>
            </ul>
            <div class="chooseType clearfix">
                <span <#if !isFull?? || isFull==''>class="active"</#if> onclick="userClick2(this,null)">不限</span>
                <span <#if isFull?? && isFull=='1'>class="active"</#if> onclick="userClick2(this,1)">完结</span>
                <span <#if isFull?? && isFull=='0'>class="active"</#if> onclick="userClick2(this,0)">连载</span>
            </div>
        </div>
    </div>

    <div class="classifyList">
        <ul class="chooseList clearfix">
            <li <#if !childCategoryId?? || childCategoryId==''>class="active"</#if> onclick="userClick(this,${category.categoryId?c},null)">全部</li>
            <#if childCategorys??>
                <#list childCategorys as childCategory>
                    <li <#if childCategoryId?? && childCategoryId == '${childCategory.categoryId?c}'>class="active"</#if> onclick="userClick(this,${category.categoryId?c},${childCategory.categoryId?c})">${childCategory.name}</li>
                </#list>
            </#if>
        </ul>
        <div class="chooseType clearfix">
            <span <#if !isFull?? || isFull==''>class="active"</#if> onclick="userClick2(this,null)">不限</span>
            <span <#if isFull?? && isFull=='1'>class="active"</#if> onclick="userClick2(this,1)">完结</span>
            <span <#if isFull?? && isFull=='0'>class="active"</#if> onclick="userClick2(this,0)">连载</span>
        </div>
    </div>

<!--内容-->
<div class="bookList">
    <ul class="clearfix pageLoad">
</#if>
    <#if pageFinder??>
        <#list pageFinder.data as book>
            <li onclick="bookInfo(${book.bookId?c},'${book.title}')" <#if pageFinder.pageNo != 1 && book_index ==0>style="border-top: 1px solid #ddd" </#if> <#if !book_has_next>style="border-bottom: none;"</#if>>
                <div class="bookImg">
                    <img data-echo="${book.coverUrl}" src="/img/default.jpg" onerror="javascript:this.src='/img/default.jpg';">
                </div>
                <div class="bookCon">
                    <h3>${book.title}</h3>
                    <p><#if book.intro??>${book.intro?replace("　","")?replace("　","")}</#if>
                    </p>
                    <div class="bookInstr">
                        <span>${book.authorPenname}</span>
                        <div class="bookKey">
                            <b>${book.categorySecName}</b>
                            <b>${book.categoryThrName}</b>
                        </div>
                    </div>
                </div>
            </li>
        </#list>
    </#if>
<#if syn=='0'>
        </ul>
    </div>
</div>
<div class="bookLoad" id="autopbn" curpage="${pageFinder.pageNo+1}" totalpage="${pageFinder.pageCount}" rel="/portal/categoryBooks.go?page=${pageFinder.pageNo+1}&syn=1&categoryId=${categoryId}&childCategoryId=<#if childCategoryId??>${childCategoryId}</#if>&isFull=<#if isFull??>${isFull}</#if>" style="display:none;"></div>
<script type="text/javascript" src="/js/base.js"></script>
<script type="text/javascript" src="/js/autopage.js"></script>
<script type="text/javascript" src="/js/echo.min.js"></script>
<script type="text/javascript">
    var topH = $(".bookList")[0].offsetTop;
    $(window).scroll(function() {
        $('.topOpen').hide();
        var scrH = $(this).scrollTop();
        if(scrH >= topH) {
            $(".topSuspen").css("top","0");
        } else {
            $(".topSuspen").css("top","-30px");
        }
    });

    $('.topSuspen').click(function(){
        $(this).css("top","-30px");
        $('.topOpen').show();
    })

    var firstTxt = $(".topOpen li.active").text();
    var secTxt = $(".topOpen span.active").text();
    if( firstTxt == '全部'){
        var txt = '${category.name}、' + secTxt;
        $("#suspenTxt").text(txt);
    }else{
        var txt = firstTxt + '、' + secTxt;
        $("#suspenTxt").text(txt);
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