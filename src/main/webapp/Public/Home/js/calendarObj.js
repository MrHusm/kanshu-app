/*****************************************************************************
 日期资料
 *****************************************************************************/

var lunarInfo = new Array(0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950,
    0x5554, 0x56af, 0x9ad0, 0x55d2, 0x4ae0, 0xa5b6, 0xa4d0, 0xd250, 0xd295,
    0xb54f, 0xd6a0, 0xada2, 0x95b0, 0x4977, 0x497f, 0xa4b0, 0xb4b5, 0x6a50,
    0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970, 0x6566, 0xd4a0, 0xea50,
    0x6a95, 0x5adf, 0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f, 0xd4a0, 0xd8a6,
    0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2, 0xa950, 0xb557, 0x6ca0,
    0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573, 0x52bf, 0xa9a8, 0xe950, 0x6aa0,
    0xaea6, 0xab50, 0x4b60, 0xaae4, 0xa570, 0x5260, 0xf263, 0xd950, 0x5b57,
    0x56a0, 0x96d0, 0x4dd5, 0x4ad0, 0xa4d0, 0xd4d4, 0xd250, 0xd558, 0xb540,
    0xb6a0, 0x95a6, 0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a, 0x6a50, 0x6d40,
    0xaf46, 0xab60, 0x9570, 0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58,
    0x5ac0, 0xab60, 0x96d5, 0x92e0, 0xc960, 0xd954, 0xd4a0, 0xda50, 0x7552,
    0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5, 0xa950, 0xb4a0, 0xbaa4, 0xad50,
    0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930, 0x7954, 0x6aa0, 0xad50,
    0x5b52, 0x4b60, 0xa6e6, 0xa4e0, 0xd260, 0xea65, 0xd530, 0x5aa0, 0x76a3,
    0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520, 0xdd45, 0xb5a0,
    0x56d0, 0x55b2, 0x49b0, 0xa577, 0xa4b0, 0xaa50, 0xb255, 0x6d2f, 0xada0,
    0x4b63, 0x937f, 0x49f8, 0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4,
    0xaaef, 0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50, 0x5d55, 0x56a0,
    0xa6d0, 0x55d4, 0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6, 0xad50, 0x55a0,
    0xaba4, 0xa5b0, 0x52b0, 0xb273, 0x6930, 0x7337, 0x6aa0, 0xad50, 0x4b55,
    0x4b6f, 0xa570, 0x54e4, 0xd260, 0xe968, 0xd520, 0xdaa0, 0x6aa6, 0x56df,
    0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252, 0xd520);

var solarMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
var Gan = new Array("甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸");
var Zhi = new Array("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥");
var Animals = new Array("鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗",
    "猪");
var solarTerm = new Array("小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏",
    "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬",
    "小雪", "大雪", "冬至");
var sTermInfo = new Array(0, 21208, 42467, 63836, 85337, 107014, 128867,
    150921, 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033,
    353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758);
var nStr1 = new Array('日', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十');
var nStr2 = new Array('初', '十', '廿', '卅', '□');
var monthName = new Array("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
    "AUG", "SEP", "OCT", "NOV", "DEC");
var cmonthName = new Array('正', '二', '三', '四', '五', '六', '七', '八', '九', '十',
    '十一', '腊');

var sFtv = new Array('0101*元旦 http://baike.baidu.com/view/3116.htm',
    '0126 国际海关日  http://baike.baidu.com/view/182693.htm',
    '0202 世界湿地日  http://baike.baidu.com/view/22240.htm',
    '0210 国际气象节 http://baike.baidu.com/view/59198.htm',
    '0214#情人节 http://baike.baidu.com/view/2533.htm',
    '0224 第三世界青年日  http://baike.baidu.com/view/1273832.htm',
    '0301 国际海豹日  http://baike.baidu.com/view/182669.htm',
    '0303 全国爱耳日 http://baike.baidu.com/view/63268.htm',
    '0305 学雷锋日 http://baike.baidu.com/view/165596.htm',
    '0308#妇女节 http://baike.baidu.com/view/328605.htm',
    '0312#植树节 http://baike.baidu.com/view/21813.htm',
    '0314 白色情人节 http://baike.baidu.com/view/9083.htm',
    '0315 消费者权益日 http://baike.baidu.com/view/807967.htm',
    '0317 中国国医节 http://baike.baidu.com/view/160937.htm',
    '0320 全球法语日 http://baike.baidu.com/view/1175338.htm',
    '0321 世界睡眠日 http://baike.baidu.com/view/21357.htm',
    '0322 世界水日 http://baike.baidu.com/view/21371.htm',
    '0323 世界气象日 http://baike.baidu.com/view/25437.htm',
    '0327 世界戏剧日 http://baike.baidu.com/view/1254377.htm',
    '0401#愚人节 http://baike.baidu.com/view/1710.htm',
    '0402 国际儿童图书日 http://baike.baidu.com/view/537990.htm',
    '0407 世界卫生日 http://baike.baidu.com/view/22478.htm',
    '0408 复活节 http://baike.baidu.com/view/1462.htm',
    '0411 世界帕金森病日 http://baike.baidu.com/view/21386.htm',
    '0415 非洲自由日 http://baike.baidu.com/view/395720.htm',
    '0418 国际古迹遗址日 http://baike.baidu.com/view/1254445.htm',
    '0422 地球日 http://baike.baidu.com/view/2183.htm',
    '0423 世界读书日 http://baike.baidu.com/view/23287.htm',
    '0424 亚非新闻工作者节 http://baike.baidu.com/view/28406.htm',
    '0426 世界知识产权日 http://baike.baidu.com/view/2186.htm',
    '0430 交通安全反思日 http://baike.baidu.com/view/36429.htm',
    '0501*劳动节 http://baike.baidu.com/view/44253.htm',
    '0503 世界新闻自由日 http://baike.baidu.com/view/27588.htm',
    '0504#青年节 http://baike.baidu.com/view/27635.htm',
    '0508 世界红十字日 http://baike.baidu.com/view/26823.htm',
    '0512 国际护士节 http://baike.baidu.com/view/25735.htm',
    '0515 国际家庭日 http://baike.baidu.com/view/62008.htm',
    '0517 世界电信日 http://baike.baidu.com/view/23216.htm',
    '0518 国际博物馆日 http://baike.baidu.com/view/37249.htm',
    '0520 全国助残日 http://baike.baidu.com/view/21415.htm',
    '0521 世界文化发展日 http://baike.baidu.com/view/1607380.htm',
    '0525 非洲解放日 http://baike.baidu.com/view/395767.htm',
    '0529 国际维和人员日 http://baike.baidu.com/view/395770.htm',
    '0530@"五卅"运动纪念 http://baike.baidu.com/view/2340194.htm',
    '0531 世界无烟日 http://baike.baidu.com/view/22249.htm',
    '0601#六一儿童节 http://baike.baidu.com/view/16194.htm',
    '0605 世界环境日 http://baike.baidu.com/view/22254.htm',
    '0606 全国爱眼日 http://baike.baidu.com/view/21347.htm',
    '0611 中国人口日 http://baike.baidu.com/view/58964.htm',
    '0612 世界无童工日 http://baike.baidu.com/view/395779.htm',
    '0614 世界献血者日 http://baike.baidu.com/view/278751.htm',
    '0620 世界难民日 http://baike.baidu.com/view/294658.htm',
    '0622 中国儿童慈善活动日 http://baike.baidu.com/view/2340240.htm',
    '0623 国际奥林匹克日 http://baike.baidu.com/view/37371.htm',
    '0624 世界骨质疏松日 http://baike.baidu.com/view/950288.htm',
    '0625 全国土地日 http://baike.baidu.com/view/37338.htm',
    '0626 国际禁毒日 http://baike.baidu.com/view/106800.htm',
    '0630 世界青年联欢节 http://baike.baidu.com/view/890704.htm',
    '0701#香港回归日 http://baike.baidu.com/view/58870.htm',
    '0702 国际体育记者日 http://baike.baidu.com/view/160976.htm',
    '0707@抗日战争纪念 http://baike.baidu.com/view/80378.htm',
    '0711 世界人口日 http://baike.baidu.com/view/23435.htm',
    '0720 人类月球日 http://baike.baidu.com/view/1734698.htm',
    '0728 世界肝炎日 http://baike.baidu.com/view/1595022.htm',
    '0730 非洲妇女日 http://baike.baidu.com/view/160993.htm',
    '0801#八一建军节 http://baike.baidu.com/view/27204.htm',
    '0809 国际土著人日 http://baike.baidu.com/view/101757.htm',
    '0812 国际青年日 http://baike.baidu.com/view/950303.htm',
    '0815 日本无条件投降日 http://baike.baidu.com/view/1387589.htm',
    '0826 全国律师咨询日 http://baike.baidu.com/view/1629212.htm',
    '0908 国际扫盲日 http://baike.baidu.com/view/62062.htm',
    '0910#教师节 http://baike.baidu.com/view/25833.htm',
    '0914 世界清洁地球日 http://baike.baidu.com/view/62054.htm',
    '0916 国际臭氧层保护日 http://baike.baidu.com/view/22443.htm',
    '0918@九·一八事变纪念日 http://baike.baidu.com/view/34028.htm',
    '0920 国际爱牙日 http://baike.baidu.com/view/147917.htm',
    '0921 国际和平日 http://baike.baidu.com/view/27269.htm',
    '0922 世界无车日 http://baike.baidu.com/view/489563.htm',
    '0923 国际聋人节 http://baike.baidu.com/view/62032.htm',
    '0927 世界旅游日 http://baike.baidu.com/view/59496.htm',
    '1001*国庆节 http://baike.baidu.com/view/14446.htm',
    '1004 世界动物日 http://baike.baidu.com/view/59207.htm',
    '1008 全国高血压日 http://baike.baidu.com/view/21442.htm',
    '1009 世界邮政日 http://baike.baidu.com/view/26600.htm',
    '1010@辛亥革命纪念日http://baike.baidu.com/view/59004.htm',
    '1013 世界保健日 http://baike.baidu.com/view/1917210.htm',
    '1014 世界标准日 http://baike.baidu.com/view/158147.htm',
    '1015 国际盲人节 http://baike.baidu.com/view/21456.htm',
    '1016 世界粮食日 http://baike.baidu.com/view/124252.htm',
    '1017 国际消除贫困日 http://baike.baidu.com/view/165572.htm',
    '1020 世界厨师日 http://baike.baidu.com/view/395884.htm',
    '1022 世界传统医药日 http://baike.baidu.com/view/124255.htm',
    '1024 联合国日 http://baike.baidu.com/view/59045.htm',
    '1031 世界勤俭日 http://baike.baidu.com/view/522014.htm',
    '1101#万圣节 http://baike.baidu.com/view/2532.htm',
    '1109 中国消防宣传日 http://baike.baidu.com/view/59105.htm',
    '1110 世界青年节 http://baike.baidu.com/view/165356.htm',
    '1108 中国记者日 http://baike.baidu.com/view/45983.htm',
    '1111 光棍节 http://baike.baidu.com/view/23105.htm',
    '1114 世界糖尿病日 http://baike.baidu.com/view/22284.htm',
    '1116 国际宽容日 http://baike.baidu.com/view/614745.htm',
    '1117 国际大学生节 http://baike.baidu.com/view/165576.htm',
    '1119 世界厕所日 http://baike.baidu.com/view/395915.htm',
    '1120 世界儿童日 http://baike.baidu.com/view/27462.htm',
    '1121 世界电视日 http://baike.baidu.com/view/163041.htm',
    '1201@世界艾滋病日 http://baike.baidu.com/view/28884.htm',
    '1203 国际残疾人日 http://baike.baidu.com/view/155500.htm',
    '1204 中国法制宣传日 http://baike.baidu.com/view/59086.htm',
    '1205 国际志愿人员日 http://baike.baidu.com/view/60704.html',
    '1207 国际民航日 http://baike.baidu.com/view/182725.htm',
    '1209@一二·九运动 http://baike.baidu.com/view/2055278.htm',
    '1210 世界人权日 http://baike.baidu.com/view/59173.htm',
    '1211 世界防治哮喘日 http://baike.baidu.com/view/22268.htm',
    '1212 西安事变纪念日 http://baike.baidu.com/view/961033.htm',
    '1213@南京大屠杀纪念日 http://baike.baidu.com/view/22274.htm',
    '1215 世界强化免疫日 http://baike.baidu.com/view/21469.html',
    '1218 国际迁徙者日 http://baike.baidu.com/view/7741807.htm',
    '1219 南南合作日 http://baike.baidu.com/view/2563969.htm',
    '1220@澳门回归纪念日 http://baike.baidu.com/view/80418.htm',
    '1221 国际篮球日 http://baike.baidu.com/view/22264.htm',
    '1224#平安夜 http://baike.baidu.com/view/28259.htm',
    '1225#圣诞节 http://baike.baidu.com/view/2547.htm',
    '1229 国际生物多样性日 http://baike.baidu.com/view/28904.htm'
);
//某月的第几个星期几。 5,6,7,8 表示到数第 1,2,3,4 个星期几
var wFtv = new Array('0520*母亲节 http://baike.baidu.com/view/2516.htm',
    '0630*父亲节 http://baike.baidu.com/view/8463.htm',
    '1144 感恩节 http://baike.baidu.com/view/2525.htm'
);
//农历节日
var lFtv = new Array("0101*春节 http://baike.baidu.com/view/3108.htm",
    "0115*元宵节 http://baike.baidu.com/view/1949.htm",
    "0202*龙抬头 http://baike.baidu.com/view/26664.htm",
    "0505*端午节 http://baike.baidu.com/view/2567.htm",
    "0707*七夕 http://baike.baidu.com/view/8489.htm",
    "0715*中元节 http://baike.baidu.com/view/57902.htm",
    "0815*中秋节 http://baike.baidu.com/view/2568.htm",
    "0909*重阳节 http://baike.baidu.com/view/2572.htm",
    "1208*腊八节 http://baike.baidu.com/view/22439.htm",
    "1223*小年 http://baike.baidu.com/view/15128.htm",
    "0100*除夕 http://baike.baidu.com/view/2562.htm"
);
/*****************************************************************************
 日期计算
 *****************************************************************************/


//====================================== 返回农历 y年的总天数
function lYearDays(y) {
    var i, sum = 348;
    for (i = 0x8000; i > 0x8; i >>= 1)
        sum += (lunarInfo[y - 1900] & i) ? 1 : 0;
    return (sum + leapDays(y));
}

//====================================== 返回农历 y年闰月的天数
function leapDays(y) {
    if (leapMonth(y))
        return ((lunarInfo[y - 1899] & 0xf) == 0xf ? 30 : 29);
    else
        return (0);
}

//====================================== 返回农历 y年闰哪个月 1-12 , 没闰返回 0
function leapMonth(y) {
    var lm = lunarInfo[y - 1900] & 0xf;
    return (lm == 0xf ? 0 : lm);
}

//====================================== 返回农历 y年m月的总天数
function monthDays(y, m) {
    return ((lunarInfo[y - 1900] & (0x10000 >> m)) ? 30 : 29);
}
//====================================== 算出农历, 传入日期控件, 返回农历日期控件
//                                       该控件属性有 .year .month .day .isLeap
//sDObj = new Date(y,m,i+1);   当月1日日期
function Lunar(objDate) {
    var i, leap = 0, temp = 0;
    var offset = (Date.UTC(objDate.getFullYear(), objDate.getMonth(), objDate
            .getDate()) - Date.UTC(1900, 0, 31)) / 86400000;
    for (i = 1900; i < 2100 && offset > 0; i++) {
        temp = lYearDays(i);
        offset -= temp;
    }
    if (offset < 0) {
        offset += temp;
        i--;
    }
    this.year = i;
    leap = leapMonth(i); //闰哪个月
    this.isLeap = false;
    for (i = 1; i < 13 && offset > 0; i++) {
        //闰月
        if (leap > 0 && i == (leap + 1) && this.isLeap == false) {
            --i;
            this.isLeap = true;
            temp = leapDays(this.year);
        } else {
            temp = monthDays(this.year, i);
        }
        //解除闰月
        if (this.isLeap == true && i == (leap + 1)) {
            this.isLeap = false;
        }
        offset -= temp;
    }
    if (offset == 0 && leap > 0 && i == leap + 1) {
        if (this.isLeap) {
            this.isLeap = false;
        } else {
            this.isLeap = true;
            --i;
        }
    }
    if (offset < 0) {
        offset += temp;
        --i;
    }
    this.month = i;
    this.day = offset + 1;
}

//alert(getSolarDate(2012,3,1,false));
function getSolarDate(lyear, lmonth, lday, isLeap) {
    var offset = 0;

    // increment year
    for ( var i = 1900; i < lyear; i++) {
        offset += lYearDays(i);
    }

    // increment month
    // add days in all months up to the current month
    for ( var i = 1; i < lmonth; i++) {
        // add extra days for leap month
        if (i == leapMonth(lyear)) {
            offset += leapDays(lyear);
        }
        offset += monthDays(lyear, i);
    }
    // if current month is leap month, add days in normal month
    if (isLeap) {
        offset += monthDays(lyear, i);
    }

    // increment
    offset += parseInt(lday) - 1;

    var baseDate = new Date(1900, 0, 31);
    var solarDate = new Date(baseDate.valueOf() + offset * 86400000);
    return solarDate;
}

//==============================返回公历 y年某m+1月的天数
function solarDays(y, m) {
    if (m == 1)
        return (((y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0)) ? 29 : 28);
    else
        return (solarMonth[m]);
}

//============================== 传入 offset 返回干支, 0=甲子
function cyclical(num) {
    return (Gan[num % 10] + Zhi[num % 12]);
}

//============================== 阴历属性
function calElement(sYear, sMonth, sDay, week, lYear, lMonth, lDay, isLeap,
                    cYear, cMonth, cDay) {

    this.isToday = false;
    //瓣句
    this.sYear = sYear; //公元年4位数字
    this.sMonth = sMonth; //公元月数字
    this.sDay = sDay; //公元日数字
    this.week = week; //星期, 1个中文
    //农历
    this.lYear = lYear; //公元年4位数字
    this.lMonth = lMonth; //农历月数字
    this.lDay = lDay; //农历日数字
    this.isLeap = isLeap; //是否为农历闰月?
    //八字
    this.cYear = cYear; //年柱, 2个中文
    this.cMonth = cMonth; //月柱, 2个中文
    this.cDay = cDay; //日柱, 2个中文

    this.color = '';

    this.lunarFestival = ''; //农历节日
    this.solarFestival = ''; //公历节日
    this.solarTerms = ''; //节气
}
//某天是该年的第几天
function dayOfYear(y,m,d) {
    y=parseInt(y);
    m=parseInt(m);
    d=parseInt(d);
    var days = 0;
    for(var i = 1; i < m; i++ ){
        switch(i){
            case 1:;
            case 3:;
            case 5:;
            case 7:;
            case 8:;
            case 10:;
            case 12:{
                days += 31;
                break;
            }
            case 4:;
            case 6:;
            case 9:;
            case 11:{
                days += 30;
                break;
            }
            case 2:{
                if(isLeapYear(y)){
                    days += 29;
                }else {
                    days += 28;
                }
                break;
            }
            default:days=0;
        }
    }
    days += d;
    return days;
}
//判断年份是 闰年还是平年
function isLeapYear(year){
    if( (year % 4 == 0 && year % 100 != 0)|| year % 400 == 0){
        return true;
    }else {
        return false;
    }
}
//===== 某年的第n个节气为几日(从0小寒起算)
function sTerm(y, n) {
    var offDate = new Date((31556925974.7 * (y - 1900) + sTermInfo[n] * 60000)
        + Date.UTC(1900, 0, 6, 2, 5));
    return (offDate.getUTCDate());
}
//获取某一天的节气
function getStermDay(y,m,d){
    if(y==2012&&m==1&&d==20){
        return '';
    }else if(y==2012&&m==1&&d==21){
        return '大寒';
    }else if(y==2012&&m==5&&d==20){
        return '小满';
    }else if(y==2012&&m==5&&d==21){
        return '';
    }
    var date=new Date(1900, 1, 6, 2, 5, 0);
    for (var i = 1; i <= 24; i++){
        var seconds=31556925974.7 * (y - 1900) + sTermInfo[i-1] * 60000;
        var newDate=new Date(seconds+ Date.UTC(1900, 0, 6, 2, 5));
        var year=newDate.getUTCFullYear();
        var month=newDate.getUTCMonth()+1;
        var day=newDate.getUTCDate();
        var str='';
        if (dayOfYear(year,month,day) == dayOfYear(y,m,d)){
            str = solarTerm[i - 1];
            break;
        }
    }
    return str;
}

//获取某一天的下一个节气
function getStermNextDay(y,m,d){
    var date=new Date(1900, 1, 6, 2, 5, 0);
    for (var i = 1; i <= 24; i++){
        var seconds=31556925974.7 * (y - 1900) + sTermInfo[i-1] * 60000;
        var newDate=new Date(seconds+ Date.UTC(1900, 0, 6, 2, 5));
        var year=newDate.getUTCFullYear();
        var month=newDate.getUTCMonth()+1;
        var day=newDate.getUTCDate();
        var str='';
        if (dayOfYear(year,month,day)> dayOfYear(y,m,d)){
            str = solarTerm[i-1];
            break;
        }
    }
    return str;

}

//根据节气名称获得节日次序
function getNumForName(name){
    for(var i = 0;i<24;i++){
        if(solarTerm[i] == name){
            return ++i;
        }
    }
}
//alert(getStermNextDay(2012,04,05));
//===================================获取某一天的相关信息

//============================== 返回阴历控件 (y年,m+1月)
/*
 功能说明: 返回整个月的日期资料控件

 使用方式: OBJ = new calendar(年,零起算月);

 OBJ.length      返回当月最大日
 OBJ.firstWeek   返回当月一日星期

 由 OBJ[日期].属性名称 即可取得各项值

 OBJ[日期].isToday  返回是否为今日 true 或 false

 其他 OBJ[日期] 属性参见 calElement() 中的注解
 */
function calendar(y, m,d) {
    var sDObj, lDObj, lY, lM, lD = 1, lL, lX = 0, tmp1, tmp2, tmp3;
    var cY, cM, cD; //年柱,月柱,日柱
    var lDPOS = new Array(3);
    var n = 0;
    var firstLM = 0;

    sDObj = new Date(y, m, 1, 0, 0, 0, 0); //当月一日日期

    this.length = solarDays(y, m); //公历当月天数
    this.firstWeek = sDObj.getDay(); //公历当月1日星期几

    ////////年柱 1900年立春后为庚子年(60进制36)
    if (m < 2)
        cY = cyclical(y - 1900 + 36 - 1);
    else
        cY = cyclical(y - 1900 + 36);
    var term2 = sTerm(y, 2); //立春日期

    ////////月柱 1900年1月小寒以前为 丙子月(60进制12)
    var firstNode = sTerm(y, m * 2) //返回当月「节」为几日开始
    cM = cyclical((y - 1900) * 12 + m + 12);

    //当月一日与 1900/1/1 相差天数
    //1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
    var dayCyclical = Date.UTC(y, m, 1, 0, 0, 0, 0) / 86400000 + 25567 + 10;

    for ( var i = 0; i < this.length; i++) {
        //console.log(i);
        if (lD > lX) {
            sDObj = new Date(y, m, i + 1); //当月一日日期
            lDObj = new Lunar(sDObj); //农历
            lY = lDObj.year; //农历年
            lM = lDObj.month; //农历月
            lD = lDObj.day; //农历日
            lL = lDObj.isLeap; //农历是否闰月
            lX = lL ? leapDays(lY) : monthDays(lY, lM); //农历当月最后一天

            if (n == 0)
                firstLM = lM;
            lDPOS[n++] = i - lD + 1;
        }

        //依节气调整二月分的年柱, 以立春为界
        if (m == 1 && (i + 1) == term2)
            cY = cyclical(y - 1900 + 36);
        //依节气月柱, 以「节」为界
        if ((i + 1) == firstNode)
            cM = cyclical((y - 1900) * 12 + m + 13);
        //日柱
        cD = cyclical(dayCyclical + i);

        //sYear,sMonth,sDay,week,
        //lYear,lMonth,lDay,isLeap,
        //cYear,cMonth,cDay
        this[i] = new calElement(y, m + 1, i + 1,
            nStr1[(i + this.firstWeek) % 7], lY, lM, lD++, lL, cY, cM, cD);
    }
    //节气
    if(y==2017&&m==11){
        tmp1 = sTerm(y, m * 2);
        tmp2 = sTerm(y, m * 2 + 1);
    }else{
        tmp1 = sTerm(y, m * 2) - 1;
        tmp2 = sTerm(y, m * 2 + 1) - 1;
    }
    this[tmp1].solarTerms = solarTerm[m * 2];
    this[tmp2].solarTerms = solarTerm[m * 2 + 1];
    if(y==2012&&m==4){
        this[19].solarTerms = solarTerm[m * 2 + 1];
        this[20].solarTerms = '';
    }

    if(m==3) this[tmp1].color = '#d74145'; //清明颜色

    //公历节日
    for (i in sFtv)
        if (sFtv[i].match(/^(\d{2})(\d{2})([\s\*|\s\#|\s@])(.+)\s+(.+)$/))
            if (Number(RegExp.$1) == (m + 1)) {
                this[Number(RegExp.$2) - 1].solarFestival += RegExp.$4 + ' ';
                if (RegExp.$3 == '*'){
                    this[Number(RegExp.$2) - 1].color = '#D63639';
                }else if(RegExp.$3=='#'){
                    this[Number(RegExp.$2) - 1].color = '#009900';
                }else if(RegExp.$3=='@'){
                    this[Number(RegExp.$2) - 1].color = 'green';
                }else{
                    this[Number(RegExp.$2) - 1].color='';
                }
            }

    //月周节日
    for (i in wFtv)
        if (wFtv[i].match(/^(\d{2})(\d)(\d)([\s\*|\s\#|\s@])(.+)\s+(.+)$/))
            if (Number(RegExp.$1) == (m + 1)) {
                tmp1 = Number(RegExp.$2);
                tmp2 = Number(RegExp.$3);
                if (tmp1 < 5){
                    this[((this.firstWeek > tmp2) ? 7 : 0) + 7 * (tmp1 - 1)
                    + tmp2 - this.firstWeek].solarFestival = RegExp.$5
                        + ' ';
                    if (RegExp.$4 == '*'){
                        this[((this.firstWeek > tmp2) ? 7 : 0) + 7 * (tmp1 - 1)
                        + tmp2 - this.firstWeek].color = '#009900';
                    }
                }else {
                    tmp1 -= 5;
                    tmp3 = (this.firstWeek + this.length - 1) % 7; //当月最后一天星期?
                    this[this.length - tmp3 - 7 * tmp1 + tmp2
                    - (tmp2 > tmp3 ? 7 : 0) - 1].solarFestival = RegExp.$5
                        + ' ';
                    if (RegExp.$4 == '*'){
                        this[this.length - tmp3 - 7 * tmp1 + tmp2
                        - (tmp2 > tmp3 ? 7 : 0) - 1].color = '#009900';
                    }
                }

            }

    //农历节日
    for (i in lFtv)
        if (lFtv[i].match(/^(\d{2})(.{2})([\s\*])(.+)\s+(.+)$/)) {
            tmp1 = Number(RegExp.$1) - firstLM;
            if (tmp1 == -11)
                tmp1 = 1;
            if (tmp1 >= 0 && tmp1 < n) {
                tmp2 = lDPOS[tmp1] + Number(RegExp.$2) - 1;
                if (tmp2 >= 0 && tmp2 < this.length
                    && this[tmp2].isLeap != true) {
                    this[tmp2].lunarFestival += RegExp.$4 + ' ';
                    if (RegExp.$3 == '*'){
                        this[tmp2].color = '#d74146';
                    }

                }
            }
        }
    //复活节只出现在3或4月
    //  if(m==2 || m==3) {
    //      var estDay = new easter(y);
    //      if(m == estDay.m)
    //         this[estDay.d-1].solarFestival = this[estDay.d-1].solarFestival+' 复活节 Easter Sunday';
    //   }

    //黑色星期五
    //	if((this.firstWeek+12)%7==5)
    //      this[12].solarFestival += '黑色星期五';

    //今日
    //if(y==g_tY && m==g_tM) {this[g_tD-1].isToday = true;}
}

//***********计算给出的日期对应的节日(节气,公历节日,月周节日,农历节日)
function calendarForOne(y, m, d) {
    var sDObj, lDObj, lY, lM, lL, lD = 1, tmp1, tmp2;
    sDObj = new Date(y, m, d);
    // 今天周几
    var nowWeek = sDObj.getDay();
    // 农历
    lDObj = new Lunar(sDObj);
    // 农历年
    lY = lDObj.year;
    // 今天农历月
    lM = lDObj.month;
    // 今天农历日
    lD = lDObj.day;
    // 农历是否闰月
    lL = lDObj.isLeap;
    // 农历当月最后一天
    lX = lL ? leapDays(lY) : monthDays(lY, lM);
    // 节气,公历节日,月周节日,农历节日
    var holiday = new Array('', '', '','','','','');

    // 今天农历节日
    for (i in lFtv) {
        if (lFtv[i].match(/^(\d{2})(.{2})([\s\*])(.+)\s+(.+)$/)) {
            if (lM == 12 && lD == lX&&lL!=true) {
                holiday[1] = "除夕|http://baike.baidu.com/view/2562.htm";
                break;
            }else{
                if (Number(RegExp.$1) == lM && Number(RegExp.$2) == lD&&lL!=true) {
                    holiday[1] = RegExp.$4+'|'+RegExp.$5;
                }
            }

        }
    }

    // 今天的公历节日
    for (i in sFtv) {
        if (sFtv[i].match(/^(\d{2})(\d{2})([\s\*|\s\#|\s@])(.+)\s+(.+)$/)){
            if (Number(RegExp.$1) == (m + 1) && Number(RegExp.$2) == d) {
                if(RegExp.$3=='*'){
                    holiday[0]= RegExp.$4+'|'+RegExp.$5;
                }else if(RegExp.$3=='#'){
                    holiday[2]= RegExp.$4+'|'+RegExp.$5;
                }else if(RegExp.$3=='@'){
                    holiday[3]= RegExp.$4+'|'+RegExp.$5;
                }else{
                    holiday[4]= RegExp.$4+'|'+RegExp.$5;
                }
            }
        }
    }

    // 今天的节气
    if(y==2013&&m==1&&d==3){
        holiday[5]='';
    }else if(y==2013&&m==1&&d==4){
        holiday[5]='立春|jieqi.html?y=2013&jq='+escape('立春');
    }else{
        holiday[5]=getStermDay(y,m+1,d)+'|jieqi.html?y='+y+'&jq='+escape(getStermDay(y,m+1,d));
    }

    // 今天是本月第几周
    var witchWeek = Math.ceil((d + nowWeek) / 7);
    // 今天的月周节日 (6月第3个周日=>"0630 父亲节")
    for (i in wFtv) {
        if (wFtv[i].match(/^(\d{2})(\d)(\d)([\s\*])(.+)\s+(.+)$/)) {
            if (Number(RegExp.$1) == (m + 1)) {
                // 第几周
                tmp1 = Number(RegExp.$2);
                // 周几
                tmp2 = Number(RegExp.$3);
                if (tmp1 == witchWeek && tmp2 == nowWeek) {
                    holiday[6] = RegExp.$5+'|'+RegExp.$6;
                }
            }
        }
    }
    return holiday;
}

// ======================================= 返回该年的复活节(春分后第一次满月周后的第一主日)
function easter(y) {

    var term2 = sTerm(y, 5); // 取得春分日期
    var dayTerm2 = new Date(Date.UTC(y, 2, term2, 0, 0, 0, 0)); // 取得春分的公历日期控件(春分一定出现在3月)
    var lDayTerm2 = new Lunar(dayTerm2); // 取得取得春分农历

    if (lDayTerm2.day < 15) // 取得下个月圆的相差天数
        var lMlen = 15 - lDayTerm2.day;
    else
        var lMlen = (lDayTerm2.isLeap ? leapDays(y) : monthDays(y,
                lDayTerm2.month))
            - lDayTerm2.day + 15;

    //一天等于 1000*60*60*24 = 86400000 毫秒
    var l15 = new Date(dayTerm2.getTime() + 86400000 * lMlen); //求出第一次月圆为公历几日
    var dayEaster = new Date(l15.getTime() + 86400000 * (7 - l15.getUTCDay())); //求出下个周日

    this.m = dayEaster.getUTCMonth();
    this.d = dayEaster.getUTCDate();

}

//====================== 中文日期
function cDay(d) {
    var s;

    switch (d) {
        case 10:
            s = '初十';
            break;
        case 20:
            s = '二十';
            break;
            break;
        case 30:
            s = '三十';
            break;
            break;
        default:
            s = nStr2[Math.floor(d / 10)];
            s += nStr1[d % 10];
    }
    return (s);
}