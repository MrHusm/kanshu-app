<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>万年历</title>
    <meta name="Keywords" content="万年历查询,万年历农历查询"/>
    <meta name="Description" content="天气网万年历" />
    <link rel="stylesheet" type="text/css" href="/Public/Home/css/newcal.css">
    <link rel="stylesheet" type="text/css" href="/Public/Home/css/baidu_wnl.css">
    <link rel="stylesheet" type="text/css" href="/Public/Home/css/render.css">
    <link rel="stylesheet" type="text/css" href="/Public/Home/css/base.css" />
    <link rel="stylesheet" type="text/css" href="/Public/Home/css/tool_calendar.css" />
    <link rel="stylesheet" type="text/css" href="/Public/Home/css/toolTop.css" />
    <script type="text/javascript" src="/Public/Home/js/locationsj.js"></script>
    <script type="text/javascript" src="http://www.tianqi.com/static/tqgg/gg.js"></script>
    <script type="text/javascript" src="/Public/Home/js/calender_wnl.js"></script>
    <script type="text/javascript" language="javascript" src="/Public/Home/js/gg.js"></script>
    <SCRIPT language="JavaScript">

        function formChaxun(type, fromobj, obja, objb)
        {
            fromobj.action = "http://www.xingzuo.com/run/chaxun.php";
            return true;
        }

    </SCRIPT>
    <style type="text/css">
        .wnlboxC{ float:left; width:490px; overflow: hidden;}
        #header {margin:0 auto; height:80px;z-index:999;position:relative; width:990px; z-index:9999999999}
        #header .top .logo {width:210px; height:80px; float:left; background:url("/Public/Home/img/logo_wnl.gif") no-repeat 0 0;}
        #cal_container{width: 990px;height: 496px;background: url("/Public/Home/img/calendar_default1.png") no-repeat;overflow: hidden;float:left; clear:both; margin-top:8px;}
        .today_event{height: 184px;overflow:hidden;}
        .today_event .bd{font-size:13px;height: 125px;padding: 6px 0 0 11px;}
        .today_event .bd a{height: 23px;line-height: 23px;display:block;overflow:hidden;}
        #header .top .main_nav { float:right; height:30px; padding:25px 0 0 0; margin:0; position:relative;}
        #header .top .main_nav li {float: left;position: relative; width:auto; margin: 0 0 0 6px;_margin:0 0 0 6px;}
        #header .top .main_nav li a {color: #333;display: block;text-decoration: none;line-height: 30px;text-align: center;font-family:"微软雅黑"; font-size: 14px; font-weight:bold; border:1px solid #dedede; padding:0 8px; background:#f8f8f8;}
        #header .top .main_nav li a:hover {border:1px solid #c00; color:#fff; background:#c00;}
        #header .top .main_nav li .hover {border:1px solid #c00;color:#fff;background:#c00; }
        .pkg-bomlinks li { padding-left: 0px; }
        .houche_tjbox{ float:left; width:960x; padding:0px 0px 5px; line-height:24px;}
        .houche_tjbox b{ float:left;  font-size:12px; font-weight:700; padding-right:5px;}
        .houche_tjbox p{ float:left; width:950px; padding:5px 5px; }
        .houche_tjbox p a{ padding:0 10px 0 0; color:#666;}
        .houche_tjbox b a{ color:#8c2b00;}
        .houche_tjbox .flclassbox{ float:left; width:960x; padding:0 0 0 0px;}
        .houche_tjbox h4{ float:left; width:960x; padding:4px 0 4px 0;}
        .houche_tjbox h4 a{ float:left; width:80px; text-align:center; overflow:hidden; height:22px; padding:0px 0; line-height:22px; background:#C87842; font-weight:bold; font-size:12px;color:#fff;-webkit-border-radius: 3px;-moz-border-radius: 3px;}

        .news_wnlrl_tj{ float:left;width:960px; padding:0px 0 10px 0px;}
        .news_wnlrl_tj li{float:left; width:148px; padding:0 12px 0 0; height:27px; font-size:12px; line-height:27px; overflow:hidden; color:#999;}
        .news_wnlrl_tj a{color:#333;}
        .news_wnlrl_tj a:hover {color:#f60;}
        .section .section_hd01 h3{position:absolute;left:-1px;top:0;padding-left:11px;width:180px;height:28px;line-height:28px;color:#8c2b00;font-weight:bold;font-size:14px;background: url("/Public/Home/img/tool_calendar_icon.png") no-repeat -250px -160px;}
        .foot_copy{min-width:990px;height:52px;background:#D02F12; padding:3px 0;}

        .tq_boxnav{ float:left; width:200px; padding:6px 10px; text-align:center; font-size:14px;color:#fff; border:1px solid #E8D0C4; background:#FFFCF7; text-align:center;font-family:"微软雅黑","宋体";  overflow:hidden; position:absolute; right:0; top:31px;}
        #header .top .main_nav li .tq_boxnav a{float:left;width:100px;background: none;margin:0;padding:0; height:30px; line-height:30px;text-decoration:none; text-align:center; border:none;font-family:"微软雅黑","宋体"; font-size:14px; font-weight:400; color:#333;}
        #header .top .main_nav li .tq_boxnav a:hover{color:#c00;text-decoration:underline;}
        #header .top .main_nav .current01 a{border:1px solid #c00; color:#fff; background:#c00;}
    </style>
</head>

<body  onload="init();">

<!-- main -->
<div class="wrap clearfix">
    <div class="wrapper clearfix">
        <div id="holiday" style="display:none;background-color:#fff;"></div>
        <!-- 使整个日历居中显示的 div add by wuzhq-->
        <div id="middle" style="left: 441px;">
            <div style="left: 40px;" class="rings"></div>
            <div style="left: 188px;" class="rings"></div>
            <div style="left: 337px;" class="rings"></div>
            <div style="left: 485px;" class="rings"></div>
            <div id="cal_container" class="cal_container">
                <div id="cal_body">
                    <div id="cal_down">
                        <div id="cal_downleft">
                            <div id="cal_funcbar">
                                <div class="cal_hd_box">
                                    <div class="animal dragon" id="lunar_img"></div>
                                    <h1>万年历</h1>
                                </div>
                                <div class="cal_content_bar">
                                    <p><span id="top_year_info"></span><span id="top_chinayear_info"></span></p>
                                    <div id="funcbar_content">
                                        <div id="prev_button"><a class="btn" href="javascript:void(0);" id="prev_buttons" >&lt;&lt;上月</a><span class="btn_no" id="prev_button_no" style="display:none" >&lt;&lt;上月</span></div>
                                        <div style="cursor:pointer;" id="year_func">
                                            <div id="year_num">
                                                <select id="year_select"></select>
                                            </div>
                                        </div>
                                        <div style="cursor:pointer;" id="month_func">
                                            <div id="month_num">
                                                <select id="month_select"></select>
                                            </div>
                                        </div>
                                        <div id="next_button"><a class="btn" id="next_buttons" href="javascript:void(0);">下月&gt;&gt;</a><span class="btn_no" id="next_button_no" style="display:none" >下月&gt;&gt;</span></div>
                                        <div id="today_button"><a class="btn_t" href="javascript:void(0);" id="today_button_a">今日</a></div>
                                    </div>
                                </div>
                            </div>
                            <div id="mainCal"></div>
                            <div class="desc">
                                <p>(1) 鼠标移动到某个日期格可以查看当日详情</p>
                                <p>(2) <span class="tradition">红色</span>表示农历传统节日，<span class="international">绿色</span>表示公众/国际节日，<span class="solarterms">蓝色</span>表示24节气日</p>
                            </div>
                        </div>
                        <div class="cal_downright">
                            <div class="cal_downright_bar">
                                <div id="cal_365riliTime">
                                    <div id="beijingtime" class="top_bar_text">北京时间</div>
                                    <div id="top_bar_time" class="top_bar_text"></div>
                                </div>
                                <p><a href="http://time.tianqi.com/" target="_blank" title="时间校对">时间校对</a></p>
                            </div>
                            <div id="cal_rightboard">
                                <div class="current_date_detail clearfix">
                                    <div class="date">
                                        <div id="gregorianDay">
                                            <div id="dayafterorbefore"></div>
                                            <div id="MonthStr"></div>
                                            <div id="right_big_date"></div>
                                            <div id="gregorianDayStr"></div>
                                        </div>
                                    </div>
                                    <div class="ch_date clearfix">
                                        <div class="lunar">
                                            <p id="chinaDay" class="chinaDay"></p>
                                            <p><span id="chinaDay2" class="chinaDay"></span><span id="chinaDay3" class="chinaDay"></span></p>
                                        </div>
                                        <a href="javascript:copyInfo();" class="copy">复制今日详情</a>
                                    </div>
                                    <div class="festival_list" id="festival_list"><a href="">立春</a><em>|</em><a href="">情人节</a><em>|</em><a href=""> 数九:七九第一天</a></div>
                                </div>
                                <div class="date_mod">
                                    <div class="hd"><h4>今日黄历<a href="../../Public/Home/html/huanglijieshi.html" target="_blank">黄历名词解释<i></i></a></h4><a class="more" id="huangli_more" href="" target="_blank">更多&gt;&gt;</a></div>
                                    <div id="YJdiv" class="bd">
                                        <div class="inner">
                                            <div class="inner1">
                                                <a href="http://wannianli.tianqi.com/laohuangli/" title="2017年10月黄历" target="_blank">
                                                    <dl class="clearfix">
                                                        <dt class="luck"><i></i></dt><dd id="luck_yi"></dd>
                                                    </dl>
                                                    <dl class="clearfix">
                                                        <dt class="bad"><i></i></dt><dd id="bad_ji"></dd>
                                                    </dl>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="today_luck" style="padding-top:20px; padding-left:10px; margin-bottom:0px;">
                                    <form method="post" onsubmit="return formChaxun(2,this,'#q_astro','#q_type')" target="_blank">
                                        <input type="hidden" name="actions" value="2">
                                        <input type="hidden" name="objb" value="1">
                                        <p>今日运程：
                                            <select onchange="changeAstro()" id="astro" name="obja">
                                                <option value="367" selected="selected">白羊座 03月21日-04月19日</option>
                                                <option value="368">金牛座 04月20日-05月20日</option>
                                                <option value="369">双子座 05月21日-06月21日</option>
                                                <option value="370">巨蟹座 06月22日-07月22日</option>
                                                <option value="371">狮子座 07月23日-08月22日</option>
                                                <option value="372">处女座 08月23日-09月22日</option>
                                                <option value="373">天秤座 09月23日-10月23日</option>
                                                <option value="374">天蝎座 10月24日-11月22日</option>
                                                <option value="375">射手座 11月23日-12月21日</option>
                                                <option value="376">摩羯座 12月22日-01月19日</option>
                                                <option value="377">水瓶座 01月20日-02月18日</option>
                                                <option value="378">双鱼座 02月19日-03月20日</option>
                                            </select>&nbsp;
                                            <button class="see" type="submit">查看</button>
                                        </p>
                                    </form>
                                    <p style="padding-top:6px;">一周运势：<a href="http://www.xingzuo.com/xingzuoyunshi/" target="_blank">星座网12星座一周运势</a></p>
                                </div>
                                <div class="date_mod today_event">
                                    <div class="hd"><h4>历史上的今天</a></h4><a class="more" id="more_href" href="http://www.tianqi.com/index.php?c=history" target="_blank">更多&gt;&gt;</a></div>
                                    <div id="history_today" class="bd" style="float:left; padding-left:10px; padding-top:0px;">
                                        <a target="_blank" href="http://www.tianqi.com/index.php?c=history&md=0706&s=2773"> 捷克的爱国主义者胡司遇难 </a><a target="_blank" href="http://www.tianqi.com/index.php?c=history&md=0706&s=2774"> 法国作家莫泊桑逝世 </a><a target="_blank" href="http://www.tianqi.com/index.php?c=history&md=0706&s=2775"> 中俄订立《四厘借款合同》 </a>									</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 使整个日历居中显示的 div end-->
        </div>
    </div>
    </div>
</div>

<!-- tip -->
<div class="tip" style="display:none" id="tip">
    <div class="tip_box">
        <div class="tip_arrow"></div>
        <div class="tip_content" id="huangli_content"></div>
    </div>
</div>
<!-- tip -->


<!-- 万年历鼠标移动层 -->
<div class="hint" id="hint" style="display:none;">
    <div class="hint_inner">
        <p ><strong id="hint_rq">2012年2月14日 星期三</strong><span class="astro" id="hint_xz">水瓶座</span></p>
        <p id="hint_nl">农历正月廿二日</p>
        <p id="hint_nyr">壬辰年 壬寅月、甲辰日</p>
        <p class="about" id="hint_jr"><span class="solarterms">立春</span><em>|</em><span class="international">情人节</span><em>|</em><span class="solarterms">数九：七九第一天</span></p>
    </div>
</div>
<!-- 万年历鼠标移动层 -->


<!-- foot -->

<script type="text/javascript" src="/Public/Home/js/jquery-1.js"></script>
</body>


<script type="text/javascript" src="/Public/Home/js/json.js"></script>
<script type="text/javascript" src="/Public/Home/js/calendarObj.js"></script>
<script type="text/javascript" src="/Public/Home/js/workTime.js"></script>
<script type="text/javascript" src="/Public/Home/js/common.js"></script>
<script type="text/javascript" src="/Public/Home/js/dataHandler.js"></script>
<script type="text/javascript" src="/Public/Home/js/calendarHandler.js"></script>
<script type="text/javascript" src="/Public/Home/js/makeCal.js"></script>
<script type="text/javascript" src="/Public/Home/js/huangli.js"></script>
<script type="text/javascript">

    $(document).ready(function(){

        calendarHandler.init(null);

        makeCal.init();

    });

    function copyInfo(){

        if (window.clipboardData) {

            var luck=$("#luck_yi").html();

            luck=luck.split(/<\/em>/i);

            var yi='';

            var reg=new RegExp(">.*?<",'im');

            for(i in luck){

                luck[i].match(/"huangliinfo\('(.*)',this\)"/i);

                yi+=(RegExp.$1)+'  ';

            }

            var bad=$("#bad_ji").html();

            bad=bad.split(/<\/em>/i);

            var  ji='';

            for(i in bad){

                luck[i].match(/"huangliinfo\('(.*)',this\)"/i);

                ji+=(RegExp.$1)+'  ';

            }

            var text = $("#MonthStr").text()+$("#right_big_date").text()+"日 "+$("#gregorianDayStr").text();

            text += "\n"+$("#chinaDay").text()+" \n "+$("#chinaDay2").text()+' '+$("#chinaDay3").text();

            text += "\n宜："+yi+"\n忌："+ji;

            text += "\n"+window.location.href;

            window.clipboardData.setData("Text", text);

            text=window.clipboardData.getData("Text");

            if(text.length!=0){

                $("#copy_pop_box").show();

            }

        }else{

            alert("此功能仅支持IE内核浏览器！");

        }

    }
</script>
</html>