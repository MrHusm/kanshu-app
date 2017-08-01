/**
 * Created by hushengmeng on 2017/7/6.
 */
var selYear=0;
var selMonth=0;
var selDay=0;
var _handle = 0;
var _handleW = 0;
var _handleL = 0;
var _handleH = 0;
var __handle = 0;
var __offset = 0;
var _weeks = "日一二三四五六";
var _init = false;
var lmanac = new Array();
var astro = new Array();
var his = new Array();
var _now = new Date();
var _year = _now.getFullYear();
//以下是相关配置
var _max = 3;//历史上的今天显示条数
var _hisUrl = "his1.htm";
var _astroUrl = "http://www.2345.com/xingzuo.htm#tab=today&xz=";
var cache = {"syear":0,"smonth":0,"sday":0,"eyear":0,"emonth":0,"eday":0,"type":"","cond":""};
var lang_default=["当前选择：","当日黄历：","年","月","日","星期","今 天 是：","今日黄历","开始日期应小于等于结束日期!","查询数据应在一年以内!","查询中","查  询","全部","农历","宜：","忌：","冲：","煞：","正冲：","胎神：","胎神：","值星：","宜","忌","综合运势：","爱情运势：","无对应年份黄历数据!","&nbsp;的日子一共有","天","您的浏览器屏蔽了弹窗，无法显示查询结果！\n\n是否在当前窗口显示查询结果?","吉神宜趋：","凶神宜忌："];
//var dir = ["/his_short/","/api/app/yjcs/","http://tianqi.2345.com/astro/"];
var dir = ["/his_short/","/Public/Home/js/api/yjs/","http://tianqi.2345.com/astro/"];

var _width = ["12%","7%","12%"];
var lmanac_len = [18,10,10,10];
function _g(_id){
    return document.getElementById(_id);
}
function $c(_tag){
    return document.createElement(_tag);
}

function init(){
    var _month = _now.getMonth()+1;
    var _day = _now.getDate();

    var _url = dir[0]+(_month<10?"0"+_month:_month)+(_day<10?"0"+_day:_day);
    //loadJs(_url);
    loadJs(dir[1]+_year+".js");
    //loadJs("calendar");
    //_g("astro").value = 'aries';
    //changeAstro();

    var _sPre = "s_";
    var _sPre2="rqjs_s";
    var _ePre = "e_";
    var _ePre2="rqjs_e";
    var _ePre3="rqjs_";

    var _yjPre = "yjcs_";
    _year = _now.getFullYear();
    _month = _now.getMonth()+1;
    _day = _now.getDate();
    var _nYear = _month!=12||_year==2011?_year:_year+1;
    var _nMonth = _year==2011?(_month<12?(_month+1)%12:_month):(_month+1)%12;
    _g(_sPre+"year").value=_year;
    _g(_ePre+"year").value=_nYear;
    _g(_sPre+"month").value=_month;
    _g(_ePre+"month").value=_nMonth;

    _g(_sPre2+"year").value=_year;
    _g(_ePre2+"year").value=_nYear;
    _g(_sPre2+"month").value=_month;
    _g(_ePre2+"month").value=_nMonth;

    _g(_ePre3+"year").value=_nYear;
    _g(_ePre3+"month").value=_month;


    _g(_yjPre+"year").value=_nYear;
    _g(_yjPre+"month").value=_month;

    changeMonthDay(0,1);
    changeMonthDay(1,1);
    changeMonthDay(0,2);
    changeMonthDay(1,2);
    changeMonthDay(3,3);
    changeMonthDay(4,4);

    var _sDay = _day;
    var _maxS = monthDay(_year,_month-1);
    var _maxE = monthDay(_nYear,_nMonth-1);
    _g(_sPre+"day").value=parseInt(_sDay,10)>_maxS?_maxS:_sDay;
    _g(_ePre+"day").value=parseInt(_sDay,10)>_maxE?_maxE:_sDay;

    _g(_sPre2+"day").value=parseInt(_sDay,10)>_maxS?_maxS:_sDay;
    _g(_ePre2+"day").value=parseInt(_sDay,10)>_maxE?_maxE:_sDay;

    _g(_ePre3+"day").value=parseInt(_sDay,10)>_maxE?_maxE:_sDay;
    _g(_yjPre+"day").value=parseInt(_sDay,10)>_maxE?_maxE:_sDay;

    jishiQuery();
}
function isLeapYear(_oYear){
    return(((_oYear%4===0)&&(_oYear%100!==0))||(_oYear%400===0));
}
function monthDay(_oYear,_oMonth){
    return[31,(isLeapYear(_oYear)?29:28),31,30,31,30,31,31,30,31,30,31][_oMonth];
}
function $d(_var){
    return typeof(_var)!="undefined";
}

function changeDate(_month,_day,_week){
    var _date = _year+lang_default[2]+_month+lang_default[3]+_day+lang_default[4]+"&nbsp;"+_week;
    var _html = '<b>'+lang_default[6]+'<font class="red">';
    _html += _date;
    _html += '</font></b>';
    _g("date").innerHTML = _html;
    _g("lm_txt").innerHTML = lang_default[1];

}

function changeMonthDay(_index,rqjs){
    var _pre;
    if(rqjs===1){
        _pre = _index==0?"s_":"e_";
    }else if(rqjs===2){
        _pre = _index==0?"rqjs_s":"rqjs_e";
    }else if(rqjs===3&&_index===3){
        _pre = "rqjs_";
    }else if(rqjs===4&&_index===4){
        _pre = "yjcs_";
    }

    var _daySel = _g(_pre+"day");
    _daySel.innerHTML = "";
    var _oYear = _g(_pre+"year").value;
    var _oMonth = _g(_pre+"month").value;
    var _monthDay = monthDay(_oYear,_oMonth-1);
    for(_i = 0;_i < _monthDay;_i++){
        var _o = new Option(_i+1+'日',_i+1);
        _daySel.options.add(_o);
    }

    if(_index==0&&isLarge()){
        _g("e_year").value = _g("s_year").value;
        //changeMonthDay(1);
        _g("e_month").value = _g("s_month").value;
        //changeMonthDay(1);
        changeDay();
    }
    _g("rqjs_emonth").value = _g("rqjs_smonth").value;
    _g("rqjs_eday").value = _g("rqjs_sday").value;
    return true;
}
function changeDay(){
    if(isLarge()){
        _g("e_day").value = _g("s_day").value;
    }
}

function changeAstro(){
    _v = _g("astro").value;
    var vArr = new Array("aries","taurus","gemini","cancer","leo","virgo","libra","scorpio","sagittarius","capricorn","aquarius","pisces");
    var _v_num = 0;
    for(var i = 0; i < 12; i++){
        if(_v == vArr[i]){
            _v_num = i;
            break;
        }
    }
    _cache = _now.getMonth()+1+"_"+_now.getDate()+"_"+(_now.getHours()>3?1:0);

    _g("astroUrl").href = _astroUrl+_v;
}

function astro_2345(_v){
    if(!astro[_v]){
        return false;
    }
    _z = lang_default[22];
    _a = lang_default[23];
    for(_i = 0;_i < 5;_i++){
        _z += astro[_v].z>_i?'<img src="Public/Home/img/star.gif"/>':'<img src="Public/Home/img/star0.gif"/>';
        _a += astro[_v].a>_i?'<img src="Public/Home/img/star.gif"/>':'<img src="Public/Home/img/star0.gif"/>';
    }
    _g("astro_z").innerHTML = _z;
    _g("astro_a").innerHTML = _a;
}
function isLarge(){//判断结束日期是否小于开始日期
    _sPre = "s_";
    _ePre = "e_";
    _sYear = +_g(_sPre+"year").value;
    _eYear = +_g(_ePre+"year").value;
    _sMonth = +_g(_sPre+"month").value;
    _eMonth = +_g(_ePre+"month").value;
    _sDay = +_g(_sPre+"day").value;
    _eDay = +_g(_ePre+"day").value;
    if((_eYear<_sYear)||(_sYear==_eYear&&_eMonth<_sMonth)||(_sYear==_eYear&&_sMonth==_eMonth&&_eDay<_sDay)){
        return true;
    }
    return false;
}
function getLmanac(){
    _type = _g('lmanacLuck').checked?"y":"j";
    _cond = _g("lmanacCond").value;
    _sPre = "s_";
    _ePre = "e_";
    _sYear = +_g(_sPre+"year").value;
    _eYear = +_g(_ePre+"year").value;
    _sMonth = +_g(_sPre+"month").value;
    _eMonth = +_g(_ePre+"month").value;
    _sDay = +_g(_sPre+"day").value;
    _eDay = +_g(_ePre+"day").value;
    if(isLarge()){
        alert(lang_default[8]);
        return false;
    }
    _sDate = new Date(_sYear,_sMonth-1,_sDay);
    _eDate = new Date(_eYear,_eMonth-1,_eDay);
    if(_eDate-_sDate>1000*60*60*24*367){
        alert(lang_default[9]);
        return false;
    }
    _g("query").value = lang_default[10];
    _g("query").blur();
    _g("query").disabled = true;

    __handle = setTimeout(callback,50);
    function callback(){
        var _ua = navigator.userAgent;
        _url = "glaohuangli.html?t="+_type+"&ct="+escape(_cond)+"&sy="+_sYear+"&ey="+_eYear+"&sm="+_sMonth+"&em="+_eMonth+"&sd="+_sDay+"&ed="+_eDay;
        if(_ua.indexOf("Chrome") > 0){
            location.href=_url;
        }else{
            var _window = window.open(_url,"_blank");
            if(!_window){if(confirm(lang_default[29])){location.href=_url}}
        }
        setTimeout(function(){
            _g("query").disabled = false;
            _g("query").value = lang_default[11];
        },300);
    }
    return true;
}
function lmanac_2345(){
    if(_init&&!arguments[0])return;
    var _month = (_now.getMonth()+1)<10?"0"+(_now.getMonth()+1):_now.getMonth()+1;
    var _day = _now.getDate()<10?"0"+_now.getDate():_now.getDate();
    var _output = "";
    _month = arguments[0]||_month;
    _day = arguments[1]||_day;
    var _today = "d"+_month+_day;
    if(!lmanac[_year]&&_g("_lmanac_"+_year)){
        _g("luck").innerHTML = lang_default[24];
        _g("bad").innerHTML = lang_default[24];
        _g("ch").innerHTML = lang_default[24];
        _g("s").innerHTML = lang_default[24];
        return false;
    }else if(!lmanac[_year]){
        loadJs(dir[1]+_year);
        _handleL = setTimeout(function(){lmanac_2345(_month,_day)},500);
        return false;
    }
    _g("luck").innerHTML = "";
    _g("bad").innerHTML = "";
    _g("ch").innerHTML = "";
    _g("s").innerHTML = "";
    for(_d in lmanac[_year][_today]){
        switch(_d.toLowerCase()){
            case "y":
                var _y = lmanac[_year][_today].y.split(".");
                var _tmp = "";
                for(_k = 0;_k < _y.length-1&&_k < lmanac_len[0];_k++){
                    _tmp += _y[_k]+",";
                }
                _tmp = _tmp.substr(0,_tmp.length-1);
                if (_tmp == ""){
                    _tmp = "&nbsp;";
                }
                _g("luck").innerHTML = _tmp;
                break;
            case "j":
                var _j = lmanac[_year][_today].j.split(".");
                var _tmp = "";
                for(_k = 0;_k < _j.length-1&&_k < lmanac_len[1];_k++){
                    _tmp += _j[_k]+",";
                }
                _tmp = _tmp.substr(0,_tmp.length-1);
                if (_tmp == ""){
                    _tmp = "&nbsp;";
                }
                _g("bad").innerHTML = _tmp;
                break;
            case "c":
                var _c = lmanac[_year][_today].c.split(".");
                var _tmp = "";
                for(_l = 0;_l < _c.length&&_l < lmanac_len[2];_l++){
                    _tmp += _c[_l]+",";
                }
                _tmp = _tmp.substr(0,_tmp.length-1);
                if (_tmp == ""){
                    _tmp = "&nbsp;";
                }
                _g("ch").innerHTML = _tmp;
                break;
            case "s":
                var _s = lmanac[_year][_today].s.split(".");
                var _tmp = "";
                for(_m = 0;_m < _s.length&&_m < lmanac_len[3];_m++){
                    _tmp += _s[_m]+",";
                }
                _tmp = _tmp.substr(0,_tmp.length-1);
                if (_tmp == ""){
                    _tmp = "&nbsp;";
                }
                _g("s").innerHTML = _tmp;
                break;
            default:
                _init = true;
                break;
        }
    }
}
function rqjs(){
    var s_y=_g('rqjs_syear').value;
    var s_m=_g('rqjs_smonth').value;
    var s_d=_g('rqjs_sday').value;
    var s_date=Date.UTC(s_y,s_m-1,s_d);

    var e_y=_g('rqjs_eyear').value;
    var e_m=_g('rqjs_emonth').value;
    var e_d=_g('rqjs_eday').value;
    var e_date=Date.UTC(e_y,e_m-1,e_d);

    var fl = Math.ceil((e_date-s_date)/86400000)
    _g("rqjs_result").style.display='';
    _g("rqjs_result").innerHTML = "两个日期相差"+fl+"天";
}

function rqjs2(){
    var _y=_g('rqjs_year').value;
    var _m=_g('rqjs_month').value;
    var _d=_g('rqjs_day').value;

    var days = _g("rqjs_days").value;
    if(days == ''){
        alert('请您输入天数');
        return;
    }
    var strP = /^-?\d+$/;
    if(!strP.test(days)){
        alert('天数需要输入阿拉伯数字');
        return;
    }

    ttt=new Date(_y,_m-1,_d).getTime()+days*24000*3600;
    theday=new Date();
    theday.setTime(ttt);
    document.getElementById("rqjs_result2").innerHTML = theday.getFullYear()+"年"+(theday.getMonth()+1)+"月"+theday.getDate()+"日";
    document.getElementById("rqjs_result2").style.display = '';
}

function jishiQuery(){
    var year = $("#yjcs_year").val();
    var month = $("#yjcs_month").val();
    var day = $("#yjcs_day").val();

    $("#jshi_query_href").attr("href","huangli.html?date="+year+"-"+month+"-"+day+"#1");

    if(month < 10){
        month = "0"+month;
    }
    if(day < 10){
        day = "0"+day;
    }

    //jsFile = "api/app/jrjs/"+year+"/"+year+"-"+month+"-"+day+".js";
    jsFile = "Public/Home/js/api/jjs/"+year+"/"+year+"-"+month+"-"+day+".js";
    loadJs(jsFile);

}

function jrjsCallBack(jrjs_){
    $("#chineseTime").html('<strong>甲子时[23-1点]</strong>'+jrjs_['html'][0]['t']);
    var yiHtml = "",jiHtml = "";
    var tmp1 = '';
    var _ci1 = '';
    if(jrjs_['html'][0]['y'].length < 5){
        var yiL = jrjs_['html'][0]['y'].length;
    }else{
        var yiL = 5;
    }
    for(var i = 0;i<yiL;i++){
        if(jrjs_['html'][0]['y'][0] == "白虎须用" || jrjs_['html'][0]['y'][0] == "青龙须用" || jrjs_['html'][0]['y'][0] == "朱雀须用"  || jrjs_['html'][0]['y'][0] == "玄武须用"){
            yiHtml += jrjs_['html'][0]['y'][i];
            _ci1 = jrjs_['html'][0]['y'][0];
            tmp1 = yiHtml;
        }else{
            yiHtml += '<em onmouseover="huangliinfo(\''+jrjs_['html'][0]['y'][i]+'\',this)" onmouseout="unhuangliinfo()">'+jrjs_['html'][0]['y'][i]+'</em>';
        }
    }
    if(tmp1 != ''){
        yiHtml = '<em onmouseover="huangliinfo(\''+_ci1+'\',this)" onmouseout="unhuangliinfo()">'+tmp1+"</em>";
    }
    $("#js_yi").html(yiHtml);
    if(jrjs_['html'][0]['j'].length < 5){
        var yiJ = jrjs_['html'][0]['j'].length;
    }else{
        var yiJ = 5;
    }
    var tmp = '';
    var _ci = '';
    for(var i = 0;i<yiJ;i++){
        if(jrjs_['html'][0]['j'][0] == "白虎须用" || jrjs_['html'][0]['j'][0] == "青龙须用" || jrjs_['html'][0]['j'][0] == "朱雀须用"  || jrjs_['html'][0]['j'][0] == "玄武须用"){
            jiHtml += jrjs_['html'][0]['j'][i];
            _ci = jrjs_['html'][0]['j'][0];
            tmp = jiHtml;
        }else{
            jiHtml += '<em onmouseover="huangliinfo(\''+jrjs_['html'][0]['j'][i]+'\',this)" onmouseout="unhuangliinfo()">'+jrjs_['html'][0]['j'][i]+'</em>';
        }
    }
    if(tmp != ''){
        jiHtml = '<em onmouseover="huangliinfo(\''+_ci+'\',this)" onmouseout="unhuangliinfo()">'+tmp+"</em>";
    }
    $("#js_ji").html(jiHtml);
}