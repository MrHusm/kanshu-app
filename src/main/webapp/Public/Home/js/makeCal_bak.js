var HuangLi = {};
var calData = new Array();
var currentDate = new Date();
var his = new Array();
var lmanac = new Array();
var rows;
var showingToday = true;
var record = {
    elem_id: "",
    nav_date: new Date()
};
var lunarImg = {
    '鼠': 'mouse',
    '牛': 'ox',
    '虎': 'tiger',
    '兔': 'rabbit',
    '龙': 'dragon',
    '蛇': 'snake',
    '马': 'horse',
    '羊': 'sheep',
    '猴': 'monkey',
    '鸡': 'chook',
    '狗': 'dog',
    '猪': 'pig'
};
var calander = {
    init: function() {
        makeCal.pareData(new Date());
        makeCal.showCal();
        makeCal.initAction();
        makeCal.makeHuangli(currentDate);
        var today = new Date(currentDate).format("yyyy-MM-dd");
        var day = today.split("-");
        day = day[1] + day[2];
        $('#huangli_more').attr('href', 'huangli.html?date=' + currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate());
        //var fileName = "http://tools.2345.com/his_short/" + day + ".js";
        //loadJs(fileName)
        /*var _tianqiUrl = 'http://www.tianqi.com/index.php?c=history&md=';
         var url = "http://www.xingzuo.com/run/rili/run_rili.php";
         var data="actions=his&today="+day+"";
         $.ajax({
         type: "post",
         url: url,
         data: data,
         success: function(result)
         {
         $("#history_today").html(result);
         $("#more_href").attr("href", _tianqiUrl + day);
         }
         });*/
    },
    pareData: function(date) {
        date = makeCal.setTimeZero(date);
        monthFirstD = makeCal.getMonthFirst(date);
        tableFirstD = makeCal.getWeekFirst(monthFirstD);
        nextMonthFirstD = makeCal.addTime(monthFirstD, 1, "month");
        monthLastD = makeCal.addTime(nextMonthFirstD, -1, "day");
        rows = Math.ceil((nextMonthFirstD.valueOf() - tableFirstD.valueOf()) / (60 * 60 * 24 * 1000 * 7));
        indexDay = new Date(tableFirstD);
        nowDay = makeCal.setTimeZero(new Date());
        calData = [];
        for (var i = 0; i < rows; i++) {
            week = [];
            for (var j = 0; j < 7; j++) {
                aDay = makeCal.createDay();
                aDay.year = indexDay.getFullYear();
                aDay.month = indexDay.getMonth();
                aDay.date = indexDay.getDate();
                if (indexDay.getTime() < monthFirstD.getTime()) {
                    aDay.before = true
                } else if (indexDay.getTime() > monthLastD.getTime()) {
                    aDay.after = true
                }
                if (indexDay.getTime() == nowDay.getTime()) {
                    aDay.today = true
                }
                if (j == 5 || j == 6) {
                    aDay.weekend = true
                }
                aDay.rows = rows;
                aDay.inrow = i + 1;
                aDay.value = indexDay;
                aDay.china = templates.lunar_Info(aDay.value);
                var date_detail = aDay.value.getFullYear() + "-" + (aDay.value.getMonth() + 1) + "-" + aDay.value.getDate();
                if ((aDay.value.getMonth() + 1) + "-" + aDay.value.getDate() == "1-1") {
                    aDay.china.l_day = '元旦'
                }
                switch (date_detail) {
                    case '2011-11-22':
                        aDay.china.l_day = '廿七';
                        aDay.china.color = "";
                        break;
                    case '2011-11-23':
                        aDay.china.l_day = '小雪';
                        aDay.china.color = "#3399CC";
                        break;
                    case '2012-1-20':
                        aDay.china.l_day = '廿七';
                        aDay.china.color = "";
                        break;
                    case '2012-1-21':
                        aDay.china.l_day = '大寒';
                        aDay.china.color = "#3399CC";
                        break
                }
                week.push(aDay);
                indexDay = makeCal.addTime(indexDay, 1, "day")
            }
            calData.push(week)
        }
        calendarHandler.prepareData4(date.getFullYear(), (date.getMonth() + 1));
        this.injectWorkDotData()
    },
    injectWorkDotData: function(date) {
        if (typeof date != "object") {
            calendarHandler.travelsal(function(d, num) {
                if (num <= 0) return;
                var t = d.split("-");
                for (var i = 0; i < rows; i++) {
                    var found = false;
                    for (var j = 0; j < 7; j++) {
                        with(calData[i][j]) {
                            if (year == parseInt(t[0]) && month == (parseInt(t[1]) - 1) && date == parseInt(t[2])) {
                                calData[i][j].hasWork = found = true;
                                break
                            }
                        }
                    }
                    if (found) break
                }
            })
        } else {
            var y = date.getFullYear(),
                m = date.getMonth(),
                d = date.getDate();
            for (var i = 0; i < rows; i++) {
                var found = false;
                for (var j = 0; j < 7; j++) {
                    with(calData[i][j]) {
                        if (year == y && month == m && date == d) {
                            calData[i][j].hasWork = found = true;
                            break
                        }
                    }
                }
                if (found) break
            }
        }
    },
    showCal: function() {
        var table = "<table>" + "<thead class='tablehead'>" + "<tr>" + "<td class='thead" + rows + "'>星期一</td>" + "<td class='thead" + rows + "'>星期二</td>" + "<td class='thead" + rows + "'>星期三</td>" + "<td class='thead" + rows + "'>星期四</td>" + "<td class='thead" + rows + "'>星期五</td>" + "<td class='thead" + rows + " thead5spe'>星期六</td>" + "<td class='thead" + rows + " thead5spe'>星期日</td>" + "</tr>" + "</thead>" + "<tbody>";
        var tdclass = "";
        for (var i = 0; i < rows; i++) {
            var aWeek = "<tr>";
            for (var j = 0; j < 7; j++) {
                var tdclass = "";
                if (calData[i][j].before == true) {
                    tdclass = 'before'
                } else if (calData[i][j].after == true) {
                    tdclass = 'after'
                }
                var datestr = getMonthDateStr(calData[i][j].value);
                var workT = "";
                try {
                    var work_T = worktime["y" + calData[i][j].year];
                    if (work_T) {
                        workT = work_T["d" + datestr]
                    }
                } catch(e) {}
                var workType = "";
                if (workT) {
                    if (workT.w == "上班") {
                        tdclass = "workBlock";
                        workType = "work"
                    } else {
                        tdclass = "restBlock";
                        workType = "rest"
                    }
                }
                if (calData[i][j].today == true) {
                    tdclass = 'today today' + calData[i][j].rows + ' clickBlock6'
                }
                var numtype = "number";
                if (calData[i][j].weekend == true) {
                    numtype = "weekendNum"
                }
                if (calData[i][j].before) {
                    numtype = "number before"
                } else if (calData[i][j].after) {
                    numtype = "number after"
                }
                var aDay = "<td i=" + i + " j=" + j + " class='block block" + calData[i][j].rows + " " + tdclass + "'>";
                aDay += "<div class='block_content block_content" + calData[i][j].rows + "'>";
                if (workType == "work" && calData[i][j].today == false) {
                    aDay += "<div class='workrest work'></div>"
                } else if (workType == "rest" && calData[i][j].today == false) {
                    if (datestr != "0405") {
                        aDay += "<div class='workrest rest'></div>"
                    }
                }
                aDay += "<div class='" + numtype + " number" + calData[i][j].rows + "'>" + calData[i][j].date + "</div>" + "<div class='chinaday chinaday" + calData[i][j].rows + " festival' style='color: " + calData[i][j].china.color + "'>" + calData[i][j].china.l_day + "</div>";
                if (calData[i][j].hasWork) {
                    aDay += "<img class='workDot workDot" + calData[i][j].rows + "' src=''/>"
                }
                aDay += "</div></td>";
                aWeek += aDay
            }
            aWeek += "</tr>";
            table += aWeek
        }
        table += "</tbody></table>";
        $('#mainCal').empty();
        $('#mainCal').append(table);
        makeCal.makeAction();
        $('.block').bind('mouseover',
            function(e) {
                ele = $(e.target);
                while (1) {
                    if (ele.hasClass('block')) {
                        break
                    } else {
                        ele = ele.parent()
                    }
                }
                var date = calData[ele.attr('i')][ele.attr('j')].value;
                makeCal.getHoliday(date, e)
            });
        $('.block').bind('mouseout',
            function(e) {
                $('#hint').hide()
            })
    },
    initAction: function() {
        $('#next_buttons').bind('click',
            function(e) {
                makeCal.nextMonth()
            });
        $('#prev_buttons').bind('click',
            function(e) {
                makeCal.prevMonth()
            });
        $('#today_button').bind('click',
            function(e) {
                makeCal.showToday();
                $('#today_button_a').removeClass();
                $('#today_button_a').addClass("btn_t");
                $('#today_button_a').html('今日');
                year = $(e.target).attr('value');
                month = $("#month_select").val();
                if (year == 2050 && month == 11) {
                    $("#next_buttons").css({
                        "display": "none"
                    });
                    $("#next_button_no").css({
                        "display": ""
                    })
                } else {
                    $("#next_buttons").css({
                        "display": ""
                    });
                    $("#next_button_no").css({
                        "display": "none"
                    });
                    $("#prev_buttons").css({
                        "display": ""
                    });
                    $("#prev_button_no").css({
                        "display": "none"
                    })
                }
                if (year == 1900 && month == 0) {
                    $("#prev_buttons").css({
                        "display": "none"
                    });
                    $("#prev_button_no").css({
                        "display": ""
                    })
                } else {
                    $("#prev_buttons").css({
                        "display": ""
                    });
                    $("#prev_button_no").css({
                        "display": "none"
                    })
                }
            });
        $('#top_bar_time').text(makeCal.get365riliTime());
        var fullYear = currentDate.getFullYear();
        var fullMonth = currentDate.getMonth() + 1;
        setInterval(function() {
                var time = makeCal.get365riliTime();
                $('#top_bar_time').text(makeCal.get365riliTime());
                if (time == '00:00:00' && showingToday) {
                    var d = new Date();
                    makeCal.pareData(d);
                    makeCal.showCal()
                }
            },
            1000);
        makeCal.bind_funcbutton('year_list_button', 'year_select_block', 'year_select_selecter');
        makeCal.bind_funcbutton('month_list_button', 'month_select_block', 'month_select_selecter');
        makeCal.bind_funcbutton('festival_button', 'festival_select_block', 'festival_select_selecter');
        $('#festival').bind('click',
            function(e) {
                if ($('#festival_select_selecter').css('display') == 'block') {
                    $('#festival_select_block').css({
                        'display': 'none'
                    });
                    $('#festival_select_selecter').css({
                        'display': 'none'
                    });
                    return
                }
                $('#festival_select_selecter').css({
                    'display': 'block'
                })

            });

        for (var i = 1900; i <= 2050; i++) {
            if (i == fullYear) $('#year_select').append('<option data=' + i + ' value="' + i + '" selected="selected">' + i + '年</option>');
            else $('#year_select').append('<option data=' + i + ' value="' + i + '">' + i + '年</option>')
        }
        for (var i = 1; i <= 12; i++) {
            if (i == fullMonth) $('#month_select').append('<option data=' + (i - 1) + ' value="' + (i - 1) + '" selected="selected">' + i + '月</option>');
            else $('#month_select').append('<option data=' + (i - 1) + ' value="' + (i - 1) + '">' + i + '月</option>')
        }
        for (var key in festival_main) {
            $('#festival_select_selecter').append('<div data=' + key + ' class="select_item festival_item">' + festival_main[key] + '</div>')
        }
        for (var i = 0; i < 24; i++) {
            $('#popHourSelectList').append('<div class="hourItem" data="' + i + '">' + i + '</div>')
        }
        for (var i = 0; i < 60; i += 5) {
            $('#popMinuteSelectList').append('<div class="minItem" data="' + i + '">' + i + '</div>')
        }
        $('#year_select').bind('change',
            function(e) {
                data = $(e.target).attr('value');
                year = $(e.target).attr('value');
                month = $("#month_select").val();
                $('#today_button_a').removeClass();
                $('#today_button_a').addClass("btn_back");
                $('#today_button_a').html('返回今日');
                if (year == 2050 && month == 11) {
                    $("#next_buttons").css({
                        "display": "none"
                    });
                    $("#next_button_no").css({
                        "display": ""
                    })
                } else {
                    $("#next_buttons").css({
                        "display": ""
                    });
                    $("#next_button_no").css({
                        "display": "none"
                    });
                    $("#prev_buttons").css({
                        "display": ""
                    });
                    $("#prev_button_no").css({
                        "display": "none"
                    })
                }
                if (year == 1900 && month == 0) {
                    $("#prev_buttons").css({
                        "display": "none"
                    });
                    $("#prev_button_no").css({
                        "display": ""
                    })
                } else {
                    $("#prev_buttons").css({
                        "display": ""
                    });
                    $("#prev_button_no").css({
                        "display": "none"
                    })
                }
                currentDate.setFullYear(data);
                makeCal.pareData(currentDate);
                makeCal.showCal();
                makeCal.makeHuangli(currentDate);
                var today = new Date(currentDate).format("yyyy-MM-dd");
                var day = today.split("-");
                day = day[1] + day[2];
                //var fileName = "http://tools.2345.com/his_short/" + day + ".js";
                //loadJs(fileName);
                $('#huangli_more').attr('href', 'huangli.html?date=' + currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate());
                //$('#festival').text('假期安排');
                showingToday = false
            });
        $('#month_select').bind('change',
            function(e) {
                data = $(e.target).attr('value');
                month = $(e.target).attr('value');
                year = $("#year_select").val();

                $('#today_button_a').removeClass();
                $('#today_button_a').addClass("btn_back");
                $('#today_button_a').html('返回今日');
                currentDate.setMonth(data);
                if (year == 2050 && month == 11) {
                    $("#next_buttons").css({
                        "display": "none"
                    });
                    $("#next_button_no").css({
                        "display": ""
                    })
                } else {
                    $("#next_buttons").css({
                        "display": ""
                    });
                    $("#next_button_no").css({
                        "display": "none"
                    });
                    $("#prev_buttons").css({
                        "display": ""
                    });
                    $("#prev_button_no").css({
                        "display": "none"
                    })
                }
                if (year == 1900 && month == 0) {
                    $("#prev_buttons").css({
                        "display": "none"
                    });
                    $("#prev_button_no").css({
                        "display": ""
                    })
                } else {
                    $("#prev_buttons").css({
                        "display": ""
                    });
                    $("#prev_button_no").css({
                        "display": "none"
                    })
                }
                if (currentDate.getMonth() != data) {
                    currentDate.setDate(1);
                    currentDate.setMonth(data)
                }
                makeCal.pareData(currentDate);
                makeCal.showCal();
                makeCal.makeHuangli(currentDate);
                var today = new Date(currentDate).format("yyyy-MM-dd");
                var day = today.split("-");
                day = day[1] + day[2];
                //var fileName = "http://tools.2345.com/his_short/" + day + ".js";
                //loadJs(fileName);
                $('#huangli_more').attr('href', 'huangli.html?date=' + currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate());
                $('#festival').text('假期安排');
                showingToday = false
            });
        $('.festival_item').bind('click',
            function(e) {
                var data = $(e.target).attr('data').split('_');
                currentDate.setFullYear(data[0]);
                currentDate.setMonth(data[1] - 1);
                currentDate.setDate(data[2]);
                $('#today_button_a').removeClass();
                $('#today_button_a').addClass("btn_back");
                $('#today_button_a').html('返回今日');
                $('#festival').text($(e.target).text());
                makeCal.pareData(currentDate);
                makeCal.showCal();
                makeCal.makeHuangli(currentDate);
                $('#month_select').attr('value', data[1] - 1);
                var today = new Date(currentDate).format("yyyy-MM-dd");
                var day = today.split("-");
                day = day[1] + day[2];
                for (var i = 1900; i <= 2050; i++) {
                    if (i == 2016) $('#year_select').append('<option data=' + i + ' value="' + i + '" selected="selected">' + i + '年</option>');
                    else $('#year_select').append('<option data=' + i + ' value="' + i + '">' + i + '年</option>')
                }
                //var fileName = "http://tools.2345.com/his_short/" + day + ".js";
                //loadJs(fileName);
                $('#huangli_more').attr('href', 'huangli.html?date=' + data[0] + '-' + data[1] + '-' + data[2]);
                showingToday = false
            });
        $('body').bind('click',
            function(e) {
                if (e.target.id != 'year_list_button' && e.target.id != 'year_num' && e.target.id != 'year_str') {
                    $('#year_select_block').css({
                        'display': 'none'
                    });
                    $('#year_select_selecter').css({
                        'display': 'none'
                    })
                }
                if (e.target.id != 'month_list_button' && e.target.id != 'month_num' && e.target.id != 'month_str') {
                    $('#month_select_block').css({
                        'display': 'none'
                    });
                    $('#month_select_selecter').css({
                        'display': 'none'
                    })
                }
                if (e.target.id != 'festival_button' && e.target.id != 'festival') {
                    $('#festival_select_block').css({
                        'display': 'none'
                    });
                    $('#festival_select_selecter').css({
                        'display': 'none'
                    })
                }
                if (e.target.id != 'popMinuteSelectNumb') {
                    $('#popMinuteSelectList').css({
                        'display': 'none'
                    })
                }
                if (e.target.id != 'popHourSelectNumb') {
                    $('#popHourSelectList').css({
                        'display': 'none'
                    })
                }
                if ($(e.target).attr('class') != 'YJdiv' && $(e.target).attr('class') != 'popYJ' && $(e.target).attr('class') != 'popWord' && $(e.target).attr('class') != 'popHuangliItemY' && $(e.target).attr('class') != 'popHuangliItemJ' && $(e.target).attr('class') != 'popStr') {
                    $('#huangliDiv').css({
                        'display': 'none'
                    })
                }
            });
        var indiv = false;
        var inhuangli = false;
        $('#YJdiv').bind('mouseover',
            function(e) {
                indiv = true;
                setTimeout(function() {
                        if (indiv || inhuangli) $('#huangliDiv').css({
                            'display': 'block'
                        })
                    },
                    500)
            });
        $('#YJdiv').bind('mouseout',
            function(e) {
                indiv = false;
                if (inhuangli == false) {
                    setTimeout(function() {
                            if (inhuangli == false && indiv == false) $('#huangliDiv').css({
                                'display': 'none'
                            })
                        },
                        500)
                }
            });
        $('#huangliDiv').bind('mouseover',
            function(e) {
                inhuangli = true;
                setTimeout(function() {
                        if (indiv || inhuangli) $('#huangliDiv').css({
                            'display': 'block'
                        })
                    },
                    500)
            });
        $('#huangliDiv').bind('mouseout',
            function(e) {
                inhuangli = false;
                if (indiv == false) {
                    setTimeout(function() {
                            if (inhuangli == false && indiv == false) $('#huangliDiv').css({
                                'display': 'none'
                            })
                        },
                        500)
                }
            })
    },
    makeAction: function() {
        $('.block').bind('click',
            function(e) {
                ele = $(e.target);
                while (1) {
                    if (ele.hasClass('block')) {
                        break
                    } else {
                        ele = ele.parent()
                    }
                }
                $('.block').removeClass('clickBlock4');
                $('.block').removeClass('clickBlock5');
                $('.block').removeClass('clickBlock6');
                ele_num = ele.children('div').children('div')[0];
                ele_cn = ele.children('div').children('div')[1];
                if (ele.hasClass('block4')) {
                    ele.addClass('clickBlock4')
                } else if (ele.hasClass('block5')) {
                    ele.addClass('clickBlock5')
                } else if (ele.hasClass('block6')) {
                    ele.addClass('clickBlock6')
                }
                makeCal.makeHuangli(calData[ele.attr('i')][ele.attr('j')].value);
                var noToday = currentDate.format("yyyy-MM-dd");
                var todays = new Date().format("yyyy-MM-dd");
                if (noToday != todays) {
                    $('#today_button_a').removeClass();
                    $('#today_button_a').addClass("btn_back");
                    $('#today_button_a').html('返回今日')
                } else {
                    $('#today_button_a').removeClass();
                    $('#today_button_a').addClass("btn_t");
                    $('#today_button_a').html('今日')
                }
                var today = new Date(currentDate).format("yyyy-MM-dd");
                var day = today.split("-");
                day = day[1] + day[2];
                //var fileName = "http://tools.2345.com/his_short/" + day + ".js";
                //loadJs(fileName);
                $('#huangli_more').attr('href', 'huangli.html?date=' + currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate())
            })
    },
    makeHuangli: function(date) {
        currentDate = date;
        date = makeCal.setTimeZero(date);
        var datestr = date.getDate();
        lunar = templates.lunar_Info_detail(date);
        $('#right_big_date').text(datestr);
        var MonthStr = date.getFullYear() + "年" + (date.getMonth() + 1) + "月";
        $("#MonthStr").html(MonthStr);
        $("#top_year_info").html(MonthStr + " ");
        var gregorianDayStr = '';
        switch (date.getDay()) {
            case 1:
                gregorianDayStr += '星期一';
                break;
            case 2:
                gregorianDayStr += '星期二';
                break;
            case 3:
                gregorianDayStr += '星期三';
                break;
            case 4:
                gregorianDayStr += '星期四';
                break;
            case 5:
                gregorianDayStr += '星期五';
                break;
            case 6:
                gregorianDayStr += '星期六';
                break;
            case 0:
                gregorianDayStr += '星期日';
                break
        }
        $('#gregorianDayStr').text(gregorianDayStr);
        $('#popDateStr').text(getFullDateStr(date));
        $('#popChineseStr').text((lunar.lunar).substring(2));
        var nowDate = makeCal.setTimeZero(new Date());
        var nowMiliSecond = nowDate.getTime();
        var targetMiliSecond = date.getTime();
        var passedTime = Math.ceil((targetMiliSecond - nowMiliSecond) / 86400000);
        var dayafterorbeforeStr = "";
        if (nowDate.getDate() == date.getDate()) {
            dayafterorbeforeStr = '今天'
        }
        if (passedTime < 0) {
            dayafterorbeforeStr = (0 - passedTime) + "天前"
        } else if (passedTime > 0) {
            dayafterorbeforeStr = passedTime + "天后"
        }
        $('#dayafterorbefore').text(dayafterorbeforeStr);
        $('#chinaDay').text('农历' + (lunar.lunar).substring(2));
        if ((date.getMonth() + 1) < 10) {
            var m = "0" + (date.getMonth() + 1)
        } else {
            var m = (date.getMonth() + 1)
        }
        if (date.getDate() < 10) {
            var d = "0" + date.getDate()
        } else {
            var d = date.getDate()
        }
        var yjdate_ = date.getFullYear() + "-" + m + "-" + d;
        showYiAndJi(yjdate_);
        if (date.getFullYear() == 2012 && (date.getMonth() == 0 && date.getDate() < 23)) {
            lunar.y_Info = lunar.y_Info.replace("龙", "兔")
        }
        info = lunar.y_Info;
        var yInfo = info.split(" ");
        $('#chinaDay2').text(yInfo[0]);
        $('#chinaDay3').text(yInfo[1]);
        var festival = '';
        var y = date.getFullYear();
        var holiday_y = date.getFullYear();
        var holiday_m = date.getMonth();
        var holiday_d = date.getDate();
        var holiday = new calendarForOne(holiday_y, holiday_m, holiday_d);
        var sj = shujiu(date);
        var h2 = new Array();
        if (holiday[5] != '') {
            h2 = holiday[5].split('|')
        } else {
            h2[0] = ''
        }
        if (holiday[0] != '') {
            h1 = holiday[0].split('|');
            festival += '<a style="width:110px;display:inline-block;" href="' + h1[1] + '"  target="_blank">' + h1[0] + '</a>';
            if (holiday[1] != '' || holiday[2] != '' || holiday[3] != '' || holiday[4] != '' || h2[0] != '' || holiday[6] != '' || sj != '') {
                festival += '<em>|</em>'
            }
        }
        if (holiday[1] != '') {
            h1 = holiday[1].split('|');
            festival += '<a style="width:110px;display:inline-block;" href="' + h1[1] + '"  target="_blank">' + h1[0] + '</a>';
            if (holiday[2] != '' || holiday[3] != '' || holiday[4] != '' || h2[0] != '' || holiday[6] != '' || sj != '') {
                festival += '<em>|</em>'
            }
        }
        if (holiday[2] != '') {
            h1 = holiday[2].split('|');
            festival += '<a style="width:110px;display:inline-block;" href="' + h1[1] + '"  target="_blank">' + h1[0] + '</a>';
            if (holiday[3] != '' || holiday[4] != '' || h2[0] != '' || holiday[6] != '' || sj != '') {
                festival += '<em>|</em>'
            }
        }
        if (holiday[5] != '') {
            h1 = holiday[5].split('|');
            if (h1[0] != '') {
                var q = '';
                if (h1[0] == '清明') {
                    q = '节';
                    var cls = '';
                    cls = 'class="tradition"'
                } else {
                    cls = 'class="solarterms"'
                }
                festival += '<a style="width:110px;display:inline-block;" href="' + h1[1] + '"  target="_blank">' + h1[0] + q + '</a>';
                if (holiday[4] != '' || holiday[6] != '' || sj != '') {
                    festival += '<em>|</em>';
                }
            }
        }
        if (sj != '') {
            festival += '<a href="http://baike.baidu.com/view/1688.htm"  target="_blank">' + '数九：' + sj + '</a>';
            if (holiday[6] != '' || holiday[4] != '') {
                festival += '<em>|</em>'
            }
        }
        if (holiday[4] != '') {
            h1 = holiday[4].split('|');
            festival += '<a style="width:110px;display:inline-block;" href="' + h1[1] + '"  target="_blank">' + h1[0] + '</a>';
            if (holiday[6] != '') {
                festival += '<em>|</em>'
            }
        }
        if (holiday[6] != '') {
            h1 = holiday[6].split('|');
            festival += '<a style="width:110px;display:inline-block;" href="' + h1[1] + '"  target="_blank">' + h1[0] + '</span>';
            if (sj != '') {
                festival += '<em>|</em>'
            }
        }
        var date = y + "-" + parseInt(m + 1) + "-" + d;
        switch (date) {
            case "2012-7-18":
                festival += '<a href="' + h1[1] + '"  target="_blank">头伏</a><em>|</em>';
                break;
            case "2012-7-28":
                festival += '<a href="' + h1[1] + '"  target="_blank">中伏</a><em>|</em>';
                break;
            case "2012-8-7":
                festival += '<em>|</em><a href="' + h1[1] + '"  target="_blank">三伏</a><em>|</em>';
                break
        }
        $('#festival_list').html(festival);
        var top_chinayear_info = "农历" + yInfo[0];
        top_chinayear_info = top_chinayear_info.replace("(", "【");
        top_chinayear_info = top_chinayear_info.replace("年)", "】");
        $("#top_chinayear_info").html(top_chinayear_info);
        var year = top_chinayear_info.substr(6, 1);
        $("#lunar_img").removeClass();
        $("#lunar_img").addClass("animal " + lunarImg[year]);
        Y = lunar.huangliY;
        J = lunar.huangliJ;
        calendarHandler.setSelectedDate(date);
        calendarHandler.drawSch()
    },
    makeHuangliForTop: function(date) {
        currentDate = date;
        date = makeCal.setTimeZero(date);
        var datestr = date.getDate();
        lunar = templates.lunar_Info_detail(date);
        var gregorianDayStr = "";
        switch (date.getDay()) {
            case 1:
                gregorianDayStr += '星期一';
                break;
            case 2:
                gregorianDayStr += '星期二';
                break;
            case 3:
                gregorianDayStr += '星期三';
                break;
            case 4:
                gregorianDayStr += '星期四';
                break;
            case 5:
                gregorianDayStr += '星期五';
                break;
            case 6:
                gregorianDayStr += '星期六';
                break;
            case 0:
                gregorianDayStr += '星期日';
                break
        }
        $('#riqi').text(getFullDateStr(date) + "  " + gregorianDayStr);
        var chineseDay = "  农历" + (lunar.lunar).substring(2);
        var nowDate = makeCal.setTimeZero(new Date());
        var nowMiliSecond = nowDate.getTime();
        var targetMiliSecond = date.getTime();
        var passedTime = Math.ceil((targetMiliSecond - nowMiliSecond) / 86400000);
        if (date.getFullYear() == 2012 && (date.getMonth() == 0 && date.getDate() < 23)) {
            lunar.y_Info = lunar.y_Info.replace("龙", "兔")
        }
        info = lunar.y_Info;
        var yInfo = info.split(" ");
        chineseDay += "   " + yInfo[0].substr(0, 3) + "   " + yInfo[1].substr(0, 3) + "   " + yInfo[1].substr(3, 3);
        $("#chineseDay").text(chineseDay);
        Y = lunar.huangliY;
        J = lunar.huangliJ;
        calendarHandler.setSelectedDate(date);
        calendarHandler.drawSch()
    },
    getHoliday: function(date, ev) {
        var y = date.getFullYear();
        var m = date.getMonth();
        var d = date.getDate();
        var mp = new getMousePoint(ev);
        $("#hint").css("left", (mp.x + 5) + "px");
        $("#hint").css("top", (mp.y + 15) + "px");
        var nongli = getNongli(date);
        $('#hint_rq').text(y + '年' + (m + 1) + '月' + d + '日' + ' ' + nongli.week);
        $('#hint_nl').text('农历' + nongli.nongli);
        $('#hint_nyr').text(nongli.month + '月 ' + nongli.day);
        var xingzuo = getXingzuo(date);
        $('#hint_xz').text(xingzuo);
        var holiday = new calendarForOne(y, m, d);
        var sj = shujiu(date);
        var jr = '';
        var h2 = new Array();
        if (holiday[5] != '') {
            h2 = holiday[5].split('|')
        } else {
            h2[0] = ''
        }
        if (holiday[0] != '') {
            h1 = holiday[0].split('|');
            jr += '<span class="tradition">' + h1[0] + '</span>';
            if (holiday[1] != '' || holiday[2] != '' || holiday[3] != '' || holiday[4] != '' || h2[0] != '' || holiday[6] != '' || sj != '') {
                jr += '<em>|</em>'
            }
        }
        if (holiday[1] != '') {
            h1 = holiday[1].split('|');
            jr += '<span class="tradition">' + h1[0] + '</span>';
            if (holiday[2] != '' || holiday[3] != '' || holiday[4] != '' || h2[0] != '' || holiday[6] != '' || sj != '') {
                jr += '<em>|</em>'
            }
        }
        if (holiday[2] != '') {
            h1 = holiday[2].split('|');
            jr += '<span class="international">' + h1[0] + '</span>';
            if (holiday[3] != '' || holiday[4] != '' || h2[0] != '' || holiday[6] != '' || sj != '') {
                jr += '<em>|</em>'
            }
        }
        if (holiday[5] != '') {
            h1 = holiday[5].split('|');
            if (h1[0] != '') {
                var q = '';
                if (h1[0] == '清明') {
                    q = '节';
                    var cls = '';
                    cls = 'class="tradition"'
                } else {
                    cls = 'class="solarterms"'
                }
                jr += '<span ' + cls + '>' + h1[0] + q + '</span>';
                if (holiday[4] != '' || holiday[6] != '' || sj != '') {
                    jr += '<em>|</em>'
                }
            }
        }
        if (sj != '') {
            jr += '<span class="solarterms">数九：' + sj + '</span>';
            if (holiday[6] != '' || holiday[4] != '') {
                jr += '<em>|</em>'
            }
        }
        if (holiday[4] != '') {
            h1 = holiday[4].split('|');
            jr += '<span class="international">' + h1[0] + '</span>';
            if (holiday[6] != '') {
                jr += '<em>|</em>'
            }
        }
        if (holiday[6] != '') {
            h1 = holiday[6].split('|');
            jr += '<span class="international">' + h1[0] + '</span>';
            if (sj != '') {
                jr += '<em>|</em>'
            }
        }
        var date = y + "-" + parseInt(m + 1) + "-" + d;
        switch (date) {
            case "2012-7-18":
                jr += '<span class="international">头伏</span><em>|</em>';
                break;
            case "2012-7-28":
                jr += '<em>|</em><span class="international">中伏</span><em>|</em>';
                break;
            case "2012-8-7":
                jr += '<em>|</em><span class="international">三伏</span><em>|</em>';
                break
        }
        $('#hint_jr').html(jr);
        $('#hint').show()
    },
    getWeekFirst: function(date) {
        var day = date.getDay();
        if (day == 0) {
            day = 7
        }
        return makeCal.addTime(date, 0 - day + 1, "day")
    },
    getMonthFirst: function(date) {
        ndate = new Date(date);
        ndate.setDate(1);
        return ndate
    },
    addTime: function(date, inc, mode) {
        ndate = new Date(date);
        switch (mode) {
            case "day":
                ndate.setDate(date.getDate() + inc);
                break;
            case "week":
                ndate.setDate(date.getDate() + 7 * inc);
                break;
            case "month":
                ndate.setMonth(date.getMonth() + inc);
                break;
            case "year":
                ndate.setYear(date.getFullYear() + inc);
                break;
            case "hour":
                ndate.setHours(date.getHours() + inc);
                break;
            case "minute":
                ndate.setMinutes(date.getMinutes() + inc);
                break;
            default:
                return ndate
        }
        return ndate
    },
    setTimeZero: function(date) {
        ndate = new Date(date);
        ndate.setHours(0);
        ndate.setMinutes(0);
        ndate.setSeconds(0);
        ndate.setMilliseconds(0);
        return ndate
    },
    createDay: function() {
        obj = new Object();
        obj.year = 0;
        obj.month = 0;
        obj.date = 0;
        obj.day = 0;
        obj.before = false;
        obj.after = false;
        obj.weekend = false;
        obj.china = null;
        obj.rows = 0;
        obj.inrow = 0;
        obj.today = false;
        obj.value = null;
        obj.hasWork = false;
        return obj
    },
    nextMonth: function() {
        var month = currentDate.getMonth();
        var year = currentDate.getFullYear();
        if (year == 2050 && month == 10) {
            $("#next_buttons").hide();
            $("#next_button_no").show()
        } else {
            $("#prev_buttons").show();
            $("#prev_button_no").hide()
        }
        $('#today_button_a').removeClass();
        $('#today_button_a').addClass("btn_back");
        $('#today_button_a').html('返回今日');
        month++;
        if (month > 11) {
            month = 0;
            year++
        }
        var currentMonth = currentDate.getMonth();
        currentDate = makeCal.addTime(currentDate, 1, "month");
        if (currentDate.getMonth() != (currentMonth + 1) % 12) {
            currentDate.setDate(1);
            currentDate.setMonth(currentMonth + 1)
        }
        makeCal.pareData(currentDate);
        makeCal.showCal();
        $('#year_select').attr('value', year);
        $('#month_select').attr('value', month);
        $('#festival').text('假期安排');
        var date = currentDate.getDate();
        var today = new Date(currentDate).format("yyyy-MM-dd");
        var day = today.split("-");
        day = day[1] + day[2];
        //var fileName = "http://tools.2345.com/his_short/" + day + ".js";
        //loadJs(fileName);
        $('#huangli_more').attr('href', 'huangli.html?date=' + year + '-' + (month + 1) + '-' + date);
        showingToday = false;
        makeCal.makeHuangli(currentDate)
    },
    prevMonth: function() {
        var month = _m = currentDate.getMonth();
        var year = currentDate.getFullYear();
        if (year == 1900 && _m == 1) {
            $("#prev_buttons").hide();
            $("#prev_button_no").show()
        } else {
            $("#next_buttons").show();
            $("#next_button_no").hide()
        }
        $('#today_button_a').removeClass();
        $('#today_button_a').addClass("btn_back");
        $('#today_button_a').html('返回今日');
        month--;
        if (month < 0) {
            month = 11;
            year--
        }
        var currentMonth = currentDate.getMonth();
        currentDate = makeCal.addTime(currentDate, -1, "month");
        if (currentDate.getMonth() != (currentMonth + 11) % 12) {
            currentDate.setDate(1);
            currentDate.setMonth((currentMonth + 11) % 12)
        }
        makeCal.pareData(currentDate);
        makeCal.showCal();
        $('#year_select').attr('value', year);
        $('#month_select').attr('value', month);
        $('#festival').text('假期安排');
        var date = currentDate.getDate();
        var today = new Date(currentDate).format("yyyy-MM-dd");
        var day = today.split("-");
        day = day[1] + day[2];
        //var fileName = "http://tools.2345.com/his_short/" + day + ".js";
        //loadJs(fileName);
        $('#huangli_more').attr('href', 'huangli.html?date=' + year + '-' + (month + 1) + '-' + date);
        showingToday = false;
        makeCal.makeHuangli(currentDate)
    },
    showToday: function() {
        currentDate = new Date();
        makeCal.pareData(currentDate);
        makeCal.showCal();
        $('#festival').text('假期安排');
        $('#year_select').attr('value', currentDate.getFullYear());
        $('#month_select').attr('value', currentDate.getMonth());
        var today = new Date(currentDate).format("yyyy-MM-dd");
        var day = today.split("-");
        day = day[1] + day[2];
        //var fileName = "http://tools.2345.com/his_short/" + day + ".js";
        //loadJs(fileName);
        $('#huangli_more').attr('href', 'huangli.html?date=' + currentDate.getFullYear() + '-' + (currentDate.getMonth() + 1) + '-' + currentDate.getDate());
        showingToday = true;
        makeCal.makeHuangli(currentDate)
    },
    bind_funcbutton: function(button, block, selecter, dataid, item) {
        $('#' + block).bind('click',
            function() {
                $('#' + block).css({
                    'display': 'none'
                });
                $('#' + selecter).css({
                    'display': 'none'
                })
            });
        $('#' + button).bind('click',
            function(e) {
                $('#' + block).css({
                    'top': $('#' + button).offset().top + 'px'
                });
                $('#' + block).css({
                    'left': $('#' + button).position().left + 'px'
                });
                $('#' + block).css({
                    'display': 'block'
                });
                $('#' + selecter).css({
                    'top': $('#' + button).offset().top + 24 + 'px'
                });
                $('#' + selecter).css({
                    'left': $('#' + button).position().left - 53 + 'px'
                });
                $('#' + selecter).css({
                    'display': 'block'
                });
                if (button == 'year_list_button') {
                    var yearNum = $('#year_num').text();
                    var offset = $('#yearitem' + yearNum).position().top;
                    $('#' + selecter).scrollTop(offset)
                }
            })
    },
    get365riliTime: function() {
        var time = new Date();
        hour = time.getHours();
        minute = time.getMinutes();
        second = time.getSeconds();
        if (hour < 10) {
            hour = "0" + hour
        }
        if (minute < 10) {
            minute = "0" + minute
        }
        if (second < 10) {
            second = "0" + second
        }
        return hour + ":" + minute + ":" + second
    }
};
function StringBuffer() {
    this._strings = new Array()
};
StringBuffer.prototype.append = function(str) {
    this._strings.push(str);
    return this
};
StringBuffer.prototype.toString = function() {
    var str = arguments.length == 0 ? '': arguments[0];
    return this._strings.join(str)
};
var templates = {
    month_day: function(date) {
        var d = date || new Date();
        return d.getDate()
    },
    lunar_Info: function(date) {
        var cld = cacheMgr.getCld(date.getFullYear(), date.getMonth(), date.getDay());
        var sj = shujiu(date);
        var day = date.getDate();
        var cld_day = cld[day - 1];
        var lunar_detail = {
            l_day: "",
            l_month: "",
            l_day_full: ""
        };
        lunar_detail.l_day = cDay(cld_day.lDay);
        lunar_detail.l_month = cld_day.lMonth;
        lunar_detail.color = "";
        lunar_detail.color = cld_day.color;
        var s, s2;
        s = cld_day.lunarFestival;
        if (s.length == 0) {
            color = cld_day.color;
            if (color == '#D63639' || color == '#009900') {
                s = cld_day.solarFestival;
                s2 = s.toString()
            } else if (color == '' || color == '#d74145') {
                var solarTerm = cld_day.solarTerms;
                if (solarTerm.length > 0) {
                    s = solarTerm;
                    lunar_detail.color = "#3399CC";
                    if (s == '清明') {
                        s = '清明节';
                        lunar_detail.color = cld_day.color
                    }
                }
            }
        }
        if (s.length > 0) {
            lunar_detail.l_day = s;
            lunar_detail.l_day_full = s2
        }
        return lunar_detail
    },
    lunar_Info_detail: function(date) {
        var cld = cacheMgr.getCld(date.getFullYear(), date.getMonth(), date.getDay());
        var year = date.getFullYear();
        var day = date.getDate();
        var cld_day = cld[day - 1];
        var festival = [];
        var info = {
            lunar: "",
            y_Info: "",
            huangliY: "无",
            huangliJ: "无"
        };
        info.lunar = '农历' + (cld_day.isLeap ? '闰 ': '') + templates.getChinaNum(cld_day.lMonth) + "月" + cDay(cld_day.lDay);
        info.y_Info = cld_day.cYear + '年' + this.lunar_year(date) + " " + cld_day.cMonth + '月' + cld_day.cDay + '日';
        return info
    },
    lunar_year: function(date) {
        var l_year = '(' + Animals[(date.getFullYear() - 4) % 12] + '年)';
        return l_year
    },
    getChinaNum: function(Num) {
        var monthEn;
        switch (Num) {
            case 1:
                monthEn = "一";
                break;
            case 2:
                monthEn = "二";
                break;
            case 3:
                monthEn = "三";
                break;
            case 4:
                monthEn = "四";
                break;
            case 5:
                monthEn = "五";
                break;
            case 6:
                monthEn = "六";
                break;
            case 7:
                monthEn = "七";
                break;
            case 8:
                monthEn = "八";
                break;
            case 9:
                monthEn = "九";
                break;
            case 10:
                monthEn = "十";
                break;
            case 11:
                monthEn = "十一";
                break;
            case 12:
                monthEn = "腊";
                break
        }
        return monthEn
    },
    init_sel_festival: function() {
        var festival_m = festival_main;
        if (festival_main) {
            var str = new StringBuffer();
            str.append('<div id="festival_sel_body">');
            for (var i in festival_main) {
                str.append('<div date="' + i).append('">').append(festival_main[i] + '</div>')
            }
            str.append('</div>')
        }
        $("#festival_sel_div").html(str.toString());
        $("#festival_sel_body>div").each(function() {
            $(this).click(function() {
                var y = $(this).attr("date").split("_");
                record.nav_date.setFullYear(y[0]);
                record.nav_date.setMonth(Number(y[1]) - 1);
                generic_cal(record.nav_date, record.elem_id);
                $("#festival_sel_div").hide()
            });
            $(this).hover(function() {
                    $(this).addClass("year_bg")
                },
                function() {
                    $(this).removeClass("year_bg")
                })
        })
    },
    init_sel_year: function() {
        var str = new StringBuffer();
        str.append('<div id="sel_body">');
        for (var i = 1900; i < 2050; i++) {
            str.append('<div>').append(i).append('</div>')
        }
        str.append('</div>');
        var scroll_top = record.nav_date.getFullYear() - 1900;
        $("#open_sel_div").html(str.toString());
        $("#sel_body>div").each(function() {
            $(this).click(function() {
                var y = $(this).html();
                record.nav_date.setFullYear(y);
                generic_cal(record.nav_date, record.elem_id);
                $("#open_sel_div").hide()
            });
            $(this).hover(function() {
                    $(this).addClass("year_bg")
                },
                function() {
                    $(this).removeClass("year_bg")
                })
        })
    },
    mousedown_hide_ele: function(id) {
        $(document).bind("mousedown." + id,
            function(r) {
                var p = r.target;
                var q = document.getElementById(id);
                while (true) {
                    if (p == q) {
                        return true
                    } else {
                        if (p == document) {
                            $(document).unbind("mousedown." + id);
                            $("#" + id).hide();
                            return false
                        } else {
                            p = $(p).parent()[0]
                        }
                    }
                }
            })
    }
};
var cacheMgr = {
    cldCache: {},
    getCld: function(year, month, day) {
        var key = getMonthKey(year, month);
        var cld = this.cldCache[key];
        if (typeof cld == 'undefined') {
            cld = new calendar(year, month, day);
            this.cldCache[key] = cld
        }
        return cld
    }
};
function getMonthKey(year, month) {
    return year.toString() + (month + 1).toString().leftpad(2)
}
String.prototype.leftpad = function(len, str) {
    if (!str) {
        str = '0'
    }
    var s = '';
    for (var i = 0; i < len - this.length; i++) {
        s += str
    }
    return s + this
};
window.makeCal = calander;
function getMonthDateStr(date) {
    month = date.getMonth() + 1;
    day = date.getDate();
    if (month < 10) {
        month = "0" + month
    }
    if (day < 10) {
        day = "0" + day
    }
    return month + "" + day
}
function getFullDateStr(date) {
    month = date.getMonth() + 1;
    day = date.getDate();
    year = date.getFullYear();
    return year + "年" + month + "月" + day + "日"
}
function showYiAndJi(date) {
    var year = date.substr(0, 4);
    //var jsFile = "api/app/yjcs/" + year + ".js";
    var jsFile = "Public/Home/js/api/yjs/" + year + ".js";
    loadJs(jsFile)
}
function lmanac_2345() {
    var today = new Date(currentDate).format("yyyy-MM-dd");
    var _date = today.split('-');
    var year = _date[0];
    var month = _date[1];
    var day = _date[2];
    var index_ = "d" + month + day;
    var yjcs_ = lmanac[year][index_];
    var yiHtml = '',
        jiHtml = '';
    if (yjcs_.y.length > 0) {
        if (yjcs_.y.length >= 15) {
            yi_length = 15
        } else {
            yi_length = yjcs_.y.length
        }
        for (var i = 0; i < yi_length; i++) {
            yiHtml += '<em onmouseover="huangliinfo(\'' + yjcs_.y[i] + '\',this)" onmouseout="unhuangliinfo()">' + yjcs_.y[i] + '</em>'
        }
    }
    $("#luck_yi").html(yiHtml);
    if (yjcs_.j.length > 0) {
        if (yjcs_.j.length >= 15) {
            ji_length = 15
        } else {
            ji_length = yjcs_.j.length
        }
        for (var j = 0; j < ji_length; j++) {
            jiHtml += '<em onmouseover="huangliinfo(\'' + yjcs_.j[j] + '\',this)" onmouseout="unhuangliinfo()">' + yjcs_.j[j] + '</em>'
        }
    }
    $("#bad_ji").html(jiHtml)
}
function huangliinfo(name, evt) {
    var top = Math.ceil($(evt).offset().top);
    var left = Math.ceil($(evt).offset().left);
    $("#tip").css("left", (left + 5) + "px");
    $("#tip").css("top", (top + 15) + "px");
    $("#tip").show();
    $("#huangli_content").html(huangli[name])
}
function unhuangliinfo() {
    $("#tip").hide();
    $("#huangli_content").html('')
}
function getXingzuo(date) {
    var xingzuo = new Array("0120-0218-水瓶座", "0219-0320-双鱼座", "0321-0420-白羊座", "0421-0520-金牛座", "0521-0621-双子座", "0622-0722-巨蟹座", "0723-0822-狮子座", "0823-0922-处女座", "0923-1022-天秤座", "1023-1121-天蝎座", "1122-1221-射手座", "1222-0119-摩羯座");
    var m = date.getMonth() + 1;
    var d = date.getDate();
    for (i in xingzuo) {
        if (xingzuo[i].match(/^(\d{2})(\d{2})\-(\d{2})(\d{2})\-(.+)$/)) {
            if ((m == Number(RegExp.$1) && d >= Number(RegExp.$2)) || (m == Number(RegExp.$3) && d <= Number(RegExp.$4))) {
                return RegExp.$5
            }
        }
    }
}
function getNongli(date) {
    var date = makeCal.setTimeZero(date);
    lunar = templates.lunar_Info_detail(date);
    var gregorianDayStr = '';
    switch (date.getDay()) {
        case 1:
            gregorianDayStr += '星期一';
            break;
        case 2:
            gregorianDayStr += '星期二';
            break;
        case 3:
            gregorianDayStr += '星期三';
            break;
        case 4:
            gregorianDayStr += '星期四';
            break;
        case 5:
            gregorianDayStr += '星期五';
            break;
        case 6:
            gregorianDayStr += '星期六';
            break;
        case 0:
            gregorianDayStr += '星期日';
            break
    }
    info = lunar.y_Info;
    var _date = info.split('月');
    var _m = _date[0].replace(/\(.*\)/g, '');
    var rs = {
        week: gregorianDayStr,
        nongli: (lunar.lunar).substring(2),
        month: _m,
        day: _date[1]
    };
    return rs
}
function shujiu(date) {
    var month = date.getMonth() + 1;
    if (month != 12 && month != 1 && month != 2 && month != 3) {
        return ''
    }
    var year = date.getFullYear();
    var day = date.getDate();
    year = year.toString();
    var century = parseInt(year.substring(0, 2)) + 1;
    var c = '',
        d = 0.2422;
    if (century == 21) {
        c = 21.94
    } else if (century == 20) {
        c = 22.60
    } else {
        return
    }
    y = year.substring(2);
    var dongzhi = '';
    if (month == 12) {
        dongzhi = parseInt(y * d + c) - parseInt(y / 4)
    } else {
        dongzhi = parseInt((y - 1) * d + c) - parseInt((y - 1) / 4)
    }
    if (year == '1918' || year == '2021') {
        dongzhi = dongzhi - 1
    }
    year = parseInt(year);
    var feb_days = 0;
    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
        feb_days = 28
    }
    var sj = new Array('12 ' + (dongzhi) + ' 12 ' + (dongzhi + 8) + ' 一九', '12 ' + (dongzhi + 9) + ' 1 ' + (dongzhi + 17 - 31) + ' 二九', '1 ' + (dongzhi + 18 - 31) + ' 1 ' + (dongzhi + 26 - 31) + ' 三九', '1 ' + (dongzhi + 27 - 31) + ' 1 ' + (dongzhi + 35 - 31) + ' 四九', '1 ' + (dongzhi + 36 - 31) + ' 2 ' + (dongzhi + 44 - 62) + ' 五九', '2 ' + (dongzhi + 45 - 62) + ' 2 ' + (dongzhi + 53 - 62) + ' 六九', '2 ' + (dongzhi + 54 - 62) + ' 2 ' + (dongzhi + 62 - 62) + ' 七九', '2 ' + (dongzhi + 63 - 62) + ' 3 ' + (dongzhi + 71 - 62 - feb_days) + ' 八九', '3 ' + (dongzhi + 72 - 62 - feb_days) + ' 3 ' + (dongzhi + 80 - 62 - feb_days) + ' 九九');
    var sj_days = new Array('九', '八', '七', '六', '五', '四', '三', '二', '一');
    var result = '';
    for (j in sj) {
        if (sj[j].match(/^(\d{1,2})\s+(\d{1,2})\s+(\d{1,2})\s+(\d{1,2})\s+(.*?)$/)) {
            if (month == Number(RegExp.$1) && month == Number(RegExp.$3) && day >= Number(RegExp.$2) && day <= Number(RegExp.$4)) {
                result = RegExp.$5 + '第' + sj_days[parseInt(RegExp.$4) - day] + '天'
            }
            if ((RegExp.$1 != RegExp.$3) && (month == RegExp.$1 && day >= RegExp.$2)) {
                result = RegExp.$5 + '第' + sj_days[8 - (day - RegExp.$2)] + '天'
            }
            if ((RegExp.$1 != RegExp.$3) && (month == RegExp.$3 && day <= RegExp.$4)) {
                result = RegExp.$5 + '第' + sj_days[parseInt(RegExp.$4) - day] + '天'
            }
        }
    }
    return result
}

function his_2345() {
    var today = new Date(currentDate).format("yyyy-MM-dd");
    var day = today.split("-");
    today = day[1] + day[2];
    var _index = 1;

    var _output = "";
    var _selMonth = day[1];
    var _selDay = day[2];
    //var _hisUrl = "http://tools.2345.com/his1.htm";
    var _tianqiUrl = "http://www.tianqi.com/index.php?c=history&md=";
    var _max = 3;

    /*var url = "http://www.xingzuo.com/run/rili/run_rili.php";
     var data="actions=his&today="+today+"";
     $.ajax({
     type: "post",
     url: url,
     data: data,
     success: function(result)
     {
     $("#history_today").html(result);
     }
     });


     $("#more_href").attr("href", _tianqiUrl + today);*/
    return true
}
function getMousePoint(evt) {
    var x = y = 0,
        doc = document.documentElement,
        body = document.body;
    ev = evt || window.event;
    if (window.pageYoffset) {
        x = window.pageXOffset;
        y = window.pageYOffset
    } else {
        x = (doc && doc.scrollLeft || body && body.scrollLeft || 0) - (doc && doc.clientLeft || body && body.clientLeft || 0);
        y = (doc && doc.scrollTop || body && body.scrollTop || 0) - (doc && doc.clientTop || body && body.clientTop || 0)
    }
    x += ev.clientX;
    y += ev.clientY;
    return {
        'x': x,
        'y': y
    }
}