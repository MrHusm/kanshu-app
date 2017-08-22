
<!DOCTYPE html>
<html>


<head>
    <meta charset="UTF-8">
    <title>原创风云榜_起点中文网</title>
    <meta name="description" content="起点中文网小说排行榜提供最新、流行、经典、精品原创小说排行榜,涵盖:玄幻小说排行榜,奇幻小说排行榜,武侠小说排行榜,仙侠小说排行榜,都市小说排行榜,历史小说排行榜,军事小说排行榜,竞技小说排行榜,灵异小说排行榜,二次元小说排行榜,免费小说排行榜,新书排行榜,完本小说排行榜,粉丝打赏排行榜">
    <meta name="keywords" content="小说,原创文学风云榜单，月票榜，排行榜,小说排行榜,热门小说排行榜,网络小说排行榜,经典小说排行,免费小说排行榜,精品小说推荐榜">
    <meta name="robots" content="all">
    <meta name="googlebot" content="all">
    <meta name="baiduspider" content="all">
    <meta http-equiv="mobile-agent" content="format=wml; url=http://m.qidian.com">
    <meta http-equiv="mobile-agent" content="format=xhtml; url=http://m.qidian.com">
    <meta http-equiv="mobile-agent" content="format=html5; url=http://h5.qidian.com/bookstore.html">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta name="renderer" content="webkit" />
    <script>
        document.domain = 'qidian.com';
    </script>
    <script>
        -1
        var speedTimer = [],
                speedZero = new Date().getTime();
    </script>
    <script>
        //遇到cookie tf=1的话留在本站，否则跳转移动站
        if (getCookie('tf') != 1) {
            //判断是以下设备后跳转到m站
            if (navigator.userAgent.match(/(iPhone|iPod|Android)/i)) {
                location.href = "//m.qidian.com"
            }
        }else {
            // M站设置了一年，这里fixed
            setCookie('tf', 1, 'qidian.com', '/', 0);
        }
        // start 防劫持
        //设置cookie
        function setCookie(name, value, domain, path, expires) {
            if(expires){
                expires = new Date(+new Date() + expires);
            }
            var tempcookie = name + '=' + escape(value) +
                    ((expires) ? '; expires=' + expires.toGMTString() : '') +
                    ((path) ? '; path=' + path : '') +
                    ((domain) ? '; domain=' + domain : '');
            //Ensure the cookie's size is under the limitation
            if(tempcookie.length < 4096) {
                document.cookie = tempcookie;
            }
        }
        //获取cookie
        function getCookie(name) {
            var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
            if (arr = document.cookie.match(reg))
                return (arr[2]);
            else
                return null;
        }
        //创建并发送请求
        function createSender(url){
            var img = new Image();
            img.onload = img.onerror = function(){
                img = null;
            };
            img.src = url;
        };
        (function(){
            /*
             *防劫持逻辑所需参数在此处设置参数即可
             *cookieName:用于记录连续被劫持的次数，为防止死循环，cookie值为3以上则不进行url重置
             *cookieDomain:cookie所在的域
             *reportUrl:非连续性劫持时上报的接口地址【如无需上报，可不填】
             *reportUrl2：连续性劫持时上报的接口地址【如无需上报，可不填】
             */
            var cookieName = 'hiijack';
            var cookieDomain = '.qidian.com';
            var reportUrl = '//book.qidian.com/ajax/safe/hiijackReport?times=1&_csrfToken='+ getCookie('_csrfToken')||'';
            var reportUrl2 = '//book.qidian.com/ajax/safe/hiijackReport?times=3&_csrfToken='+ getCookie('_csrfToken')||'';
            //判断是否被iframe
            if (top.location.href !== self.location.href) {
                //用于记录被劫持的次数
                var countHijack;
                //如果未设置cookie，则需要set一下cookie，否则获取此cookie的值
                if(!getCookie(cookieName)){
                    setCookie(cookieName,0 ,cookieDomain, '', 30*24*60*60*1000);
                    countHijack = 0;
                }else{
                    countHijack = parseInt(getCookie(cookieName));
                }
                //如果连续被劫持的次数大于等于3次，则发请求上报此情况,否则上报非连续性的情况,同时累加被劫持次数、重置当前url
                if(countHijack >= 3){
                    reportUrl2 && reportUrl2!='' && createSender(reportUrl2);
                }else{
                    reportUrl && reportUrl!=''&& createSender(reportUrl);
                    countHijack ++;
                    setCookie(cookieName, countHijack ,cookieDomain, '', 30*24*60*60*1000);
                    top.location = self.location.href;
                }
            }
            //每次成功进入页面则计数清0
            setCookie(cookieName, 0 ,cookieDomain, '', 30*24*60*60*1000);
        })();
        //end 防劫持
    </script>

    <link data-ignore="true" rel="shortcut icon" type="image/x-icon" href="//qidian.gtimg.com/qd/favicon/qd_icon.0.3.ico">
    <link data-ignore="true" rel="Bookmark" type="image/x-icon" href="//qidian.gtimg.com/qd/favicon/qd_icon.0.3.ico">



    <link rel="stylesheet" data-ignore="true" href="//qidian.gtimg.com/c/=/qd/css/reset.0.79.css,/qd/css/global.0.53.css,/qd/css/font.0.89.css,/qd/css/header.01.4.css,/qd/css/module.1.30.css,/qd/css/list_module.0.92.css,/qd/css/rank.0.85.css,/qd/css/layout.0.80.css,/qd/css/qd_popup.0.91.css,/qd/css/footer.0.85.css,/qd/css/lbfUI/css/ComboBox.0.92.css,/qd/css/lbfUI/css/Button.0.78.css" />


</head>
<body class="rank-type-1" id="-1">
<div class="share-img">
    <img src="//qidian.gtimg.com/qd/images/common/share.0.3.png" width='300' height="300">
</div>

<div class="wrap">
    <div class="top-nav" data-l1="1">
        <div class="box-center cf">
            <div class="login-box fr">
                <div class="sign-in hidden">
                    <span>你好，</span><a class="black" id="user-name" href="//me.qidian.com" target="_blank" data-eid="qd_A08"></a><em>|</em><a class="black" id="msg-btn" href="//me.qidian.com/msg/systems.aspx?page=1" target="_blank" data-eid="qd_A09">消息<cite id="msg-box">(<i></i>)</cite></a><em>|</em><a id="exit-btn" href="javascript:" data-eid="qd_A10">退出</a>
                </div>
                <div class="sign-out">
                    <a id="login-btn" class="black" href="javascript:" data-eid="qd_A06">登录</a><em>|</em><a id="reg-btn" href="//passport.qidian.com/reg.html?appid=10&areaid=1&target=iframe&ticket=1&auto=1&autotime=30&returnUrl=http%3A%2F%2Fwww.qidian.com" target="_blank" data-eid="qd_A07">注册</a>
                </div>
            </div>
            <div class="nav-link fl">
                <a class="act" href="//www.qidian.com" data-eid="qd_A01">起点中文网</a>
                <a href="//www.qdmm.com" target="_blank" data-eid="qd_A02">起点女生网</a><em>|</em>
                <a href="http://chuangshi.qq.com" target="_blank" data-eid="qd_A03">创世中文网</a><em>|</em>
                <a href="http://yunqi.qq.com" target="_blank" data-eid="qd_A04">云起书院</a><em>|</em>

                <a href="javascript:" id="switchEl" data-eid="qd_A182">繁体版</a>
                <!--<em>|</em>
                <a class="black" id="back-old" href="javascript:" data-eid="qd_A05">返回旧版</a>-->
            </div>
        </div>
    </div>

    <div class="top-op-box" id="j-topOpBox"></div>

    <div class="logo-wrap box-center" data-l1="2">
        <div class="box-center cf">
            <div class="book-shelf fr">
                <a href="//me.qidian.com/bookCase/bookCase.aspx?caseId=-2" target="_blank" data-eid="qd_A14"><em class="iconfont shelf">&#xe60c;</em><i>我的书架</i>
                </a>
            </div>
            <div class="logo fl">
                <a href="//www.qidian.com" data-eid="qd_A11"></a>
            </div>
            <div class="search-wrap fl">
                <form id="formUrl" action="//se.qidian.com" method="get" target="_blank">
                    <p><input class="search-box" id="s-box" name="kw" type="text" placeholder="修真四万年"></p>
                    <input class="submit-input" type="submit" id="searchSubmit" data-eid="qd_A13">
                    <label id="search-btn" class="search-btn" for="searchSubmit"><em class="iconfont" data-eid="qd_A13">
                        &#xe60d;</em></label>
                </form>
            </div>
        </div>
    </div>


    <div class="main-nav-wrap" data-l1="3">
        <div class="main-nav box-center cf" id="type-hover">
            <div class="classify-list fl so-awesome" id="classify-list" data-l1="3">
                <dl>
                    <dd>
                        <a href="//xuanhuan.qidian.com/" target="_blank" data-eid="qd_A71">
                            <cite>
                                <em class="iconfont">&#xe642;</em>
                                    <span class="info">
                                        <i>玄幻</i>
                                        <b>589078</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd>
                        <a href="//qihuan.qidian.com/" target="_blank" data-eid="qd_A72">
                            <cite>
                                <em class="iconfont">&#xe62f;</em>
                                    <span class="info">
                                        <i>奇幻
                                        </i>
                                        <b>125582</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd class="even">
                        <a href="//wuxia.qidian.com/" target="_blank" data-eid="qd_A73">
                            <cite>
                                <em class="iconfont">&#xe632;</em>
                                    <span class="info">
                                        <i>武侠</i>
                                        <b>34109</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd class="even">
                        <a href="//xianxia.qidian.com/" target="_blank" data-eid="qd_A74">
                            <cite>
                                <em class="iconfont">&#xe610;</em>
                                    <span class="info">
                                        <i>仙侠</i>
                                        <b>203492</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd>
                        <a href="//dushi.qidian.com/" target="_blank" data-eid="qd_A75">
                            <cite>
                                <em class="iconfont">&#xe62c;</em>
                                    <span class="info">
                                        <i>都市</i>
                                        <b>320832</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd>
                        <a href="//xianshi.qidian.com/" target="_blank" data-eid="qd_A76">
                            <cite>
                                <em class="iconfont">&#xe614;</em>
                                    <span class="info">
                                        <i>现实</i>
                                        <b>14555</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd class="even">
                        <a href="//junshi.qidian.com/" target="_blank" data-eid="qd_A77">
                            <cite>
                                <em class="iconfont">&#xe602;</em>
                                    <span class="info">
                                        <i>军事</i>
                                        <b>17954</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd class="even">
                        <a href="//lishi.qidian.com/" target="_blank" data-eid="qd_A78">
                            <cite>
                                <em class="iconfont">&#xe62d;</em>
                                    <span class="info">
                                        <i>历史</i>
                                        <b>68073</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd>
                        <a href="//youxi.qidian.com/" target="_blank" data-eid="qd_A79">
                            <cite>
                                <em class="iconfont">&#xe634;</em>
                                    <span class="info">
                                        <i>游戏</i>
                                        <b>91337</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd>
                        <a href="//tiyu.qidian.com/" target="_blank" data-eid="qd_A80">
                            <cite>
                                <em class="iconfont tiyu">&#xe631;</em>
                                    <span class="info">
                                        <i>体育</i>
                                        <b>8888</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd class="even">
                        <a href="//kehuan.qidian.com/" target="_blank" data-eid="qd_A81">
                            <cite>
                                <em class="iconfont">&#xe603;</em>
                                    <span class="info">
                                        <i>科幻</i>
                                        <b>122287</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd class="even">
                        <a href="//lingyi.qidian.com/" target="_blank" data-eid="qd_A82">
                            <cite>
                                <em class="iconfont lingyi">&#xe641;</em>
                                    <span class="info">
                                        <i>灵异</i>
                                        <b>48985</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd>
                        <a href="//www.qdmm.com/" target="_blank" data-eid="qd_A83">
                            <cite>
                                <em class="iconfont">&#xe67c;</em>
                                    <span class="info">
                                        <i>女生网</i>
                                        <b>605983</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                    <dd>
                        <a href="//2cy.qidian.com/" target="_blank" data-eid="qd_A84">
                            <cite>
                                <em class="iconfont erciyuan">&#xe617;</em>
                                    <span class="info">
                                        <i>二次元</i>
                                        <b>82589</b>
                                    </span>
                            </cite>
                        </a>
                    </dd>
                </dl>
            </div>

            <ul>
                <li class="first"><b><strong></strong></b><span><em>
                    <i></i>
                    <i></i>
                    <i></i>
                </em>作品分类</span>
                </li>
                <li class="nav-li"><a href="//a.qidian.com" data-eid="qd_A15" >全部作品</a></li>
                <li class="nav-li"><a href="//r.qidian.com" data-eid="qd_A16" class=act>排行</a></li>
                <li class="nav-li"><a href="//fin.qidian.com" data-eid="qd_A17" >完本</a></li>
                <li class="nav-li"><a href="//f.qidian.com" data-eid="qd_A18" >免费</a></li>
                <li class="nav-li"><a href="http://write.qq.com/public/login.html" target="_blank" data-eid="qd_A19">作家专区</a>
                </li>
                <li class="nav-li"><a href="http://www.yuewen.com/app.html#appqd" target="_blank" data-eid="qd_A20"><b class="iconfont client">&#xe604;</b>客户端</a>
                </li>
                <li class="game phone" id="game-phone">
                    <a class="phone-game" href="http://sy.qidian.com" target="_blank" data-eid="qd_A22"><b class="iconfont webgame">
                        &#xe630;</b>手游</a>
                    <div class="game-dropdown hidden" id="phone-dropdown">
                        <cite></cite>
                        <dl class="phone-list">

                            <dd>
                                <h3>

                                    <a href="" data-eid="qd_A39" target="_blank">仙剑奇侠传五</a><i>正版授权</i></h3>
                                <p><a href="http://collect.game.qidian.com/Home/Stat/Index?gourl=aHR0cDovL3N5LnFpZGlhbi5jb20vSG9tZS9QYy9JbmRleC9kZXRhaWwvZ2FtZWlkLzIwMDU4OQ==&amp;code=5733ed85f2cd0" target="_blank" data-eid="qd_A39"><img src="//qidian.qpic.cn/qidian_common/349573/a9384ee6a0147276d8055e8849a0468a/0"><i class="op-tag"></i></a></p>
                            </dd>

                            <dd>
                                <h3>

                                    <span></span>

                                    <a href="" data-eid="qd_A40" target="_blank">齐天战神</a><i>超燃MMO</i></h3>
                                <p><a href="http://collect.game.qidian.com/Home/Stat/Index?gourl=aHR0cDovL3N5LnFpZGlhbi5jb20vSG9tZS9QYy9JbmRleC9kZXRhaWwvZ2FtZWlkLzIwMDU5MA==&amp;code=5733edc4a3435" target="_blank" data-eid="qd_A40"><img src="//qidian.qpic.cn/qidian_common/349573/841f731bb1c4a708cbae04b40ab768a9/0"><i class="op-tag"></i></a></p>
                            </dd>

                            <dd>
                                <h3>

                                    <a href="" data-eid="qd_A41" target="_blank">王城霸业</a><i>一统三国</i></h3>
                                <p><a href="http://collect.game.qidian.com/Home/Stat/Index?gourl=aHR0cDovL3N5LnFpZGlhbi5jb20vYXBpL3N5eHMvc2dhbWUvUGxheS5waHA/Z2FtZWlkPTIwMDU5NiZzZXJ2ZXJpZD0xJnR5PTE=&amp;code=5769f94031529" target="_blank" data-eid="qd_A41"><img src="//qidian.qpic.cn/qidian_common/349573/492dcbf6005586b358b3c3639a60bb9c/0"><i class="op-tag"></i></a></p>
                            </dd>

                        </dl>
                        <div class="bottom">
                            <h4>新游上线|卧虎藏龙贰</h4>
                            <p><a href="http://sy.qidian.com/Home/Pc/Index/detail/gameid/200593?qd_game_key=192x86&amp;qd_dd_p1=141" data-eid="qd_A42" target="_blank"><img src="//qidian.qpic.cn/qidian_common/349573/fab4c67c79f4a547c3ef97bead4a9d30/0" alt=""><i class="op-tag"></i></a></p>
                        </div>
                    </div>
                </li>
                <li class="game web" id="game-web">
                    <a class="web-game" href="http://game.qidian.com/" target="_blank" data-eid="qd_A21"><b class="iconfont">
                        &#xe60b;</b>页游</a>
                    <div class="game-dropdown hidden" id="web-dropdown">
                        <cite></cite>
                        <div class="web-game-list">
                            <dl class="lately" id="lately">
                                <dt>最近玩过</dt>
                                <dd data-rid="1"><a class="name" href="javascript:" target="_blank" data-eid="qd_A29"></a><strong></strong><a class="link" href="javascript:" target="_blank" data-eid="qd_A29">GO<i>&gt;</i></a></dd>
                                <dd data-rid="2"><a class="name" href="javascript:" target="_blank" data-eid="qd_A30"></a><strong></strong><a class="link" href="javascript:" target="_blank" data-eid="qd_A30">GO<i>&gt;</i></a></dd>
                            </dl>
                            <dl class="web-list" id="log-web-list">
                                <dt>今日开服</dt>
                                <dd class="act" data-rid="1">
                                    <h3>

                                        <span></span>

                                        <i>08-22</i><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=fhly" data-eid="qd_A31" target="_blank">烽火燎原</a><strong>4区</strong></h3>
                                    <p><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=fhly" data-eid="qd_A31" target="_blank"><img src="//qidian.qpic.cn/qidian_common/349573/77ffc4fcaec3a2a09231a7299d9c90ab/0"><i class="op-tag"></i></a></p>
                                </dd>

                                <dd data-rid="2">
                                    <h3>

                                        <span></span>

                                        <i>08-22</i><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=cqby" data-eid="qd_A32" target="_blank">传奇霸业</a><strong>157区</strong></h3>
                                    <p><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=cqby" data-eid="qd_A32" target="_blank"><img src="//qidian.qpic.cn/qidian_common/349573/ae624b620a6159ef2d23d54c7be7e6ef/0"><i class="op-tag"></i></a></p>
                                </dd>

                                <dd data-rid="3">
                                    <h3>

                                        <span></span>

                                        <i>08-21</i><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=sdyxz" data-eid="qd_A33" target="_blank">射雕英雄传</a><strong>2区</strong></h3>
                                    <p><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=sdyxz" data-eid="qd_A33" target="_blank"><img src="//qidian.qpic.cn/qidian_common/349573/55ee27650d0bbcb302e71c5cd0a17a69/0"><i class="op-tag"></i></a></p>
                                </dd>

                                <dd data-rid="4">
                                    <h3>

                                        <span></span>

                                        <i>08-21</i><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=zszx" data-eid="qd_A34" target="_blank">众神之下</a><strong>11区</strong></h3>
                                    <p><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=zszx" data-eid="qd_A34" target="_blank"><img src="//qidian.qpic.cn/qidian_common/349573/555aabf0850bc342b5088b51b0428b81/0"><i class="op-tag"></i></a></p>
                                </dd>

                                <dd data-rid="5">
                                    <h3>

                                        <span></span>

                                        <i>08-21</i><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=blr" data-eid="qd_A35" target="_blank">不良人</a><strong>15区</strong></h3>
                                    <p><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=blr" data-eid="qd_A35" target="_blank"><img src="//qidian.qpic.cn/qidian_common/349573/2cda244703215014934fc1fd06ec9623/0"><i class="op-tag"></i></a></p>
                                </dd>

                                <dd data-rid="6">
                                    <h3>

                                        <span></span>

                                        <i>08-20</i><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=sgqxz" data-eid="qd_A36" target="_blank">三国群雄传</a><strong>20区</strong></h3>
                                    <p><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=sgqxz" data-eid="qd_A36" target="_blank"><img src="//qidian.qpic.cn/qidian_common/349573/4a9ad2c56f16d3eada393ad0912b426b/0"><i class="op-tag"></i></a></p>
                                </dd>

                                <dd data-rid="7">
                                    <h3>

                                        <span></span>

                                        <i>08-20</i><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=jxqybqp" data-eid="qd_A37" target="_blank">剑侠情缘兵器谱</a><strong>7区</strong></h3>
                                    <p><a href="http://cpgame.qd.game.qidian.com/Home/GameServer/lists?name=jxqybqp" data-eid="qd_A37" target="_blank"><img src="//qidian.qpic.cn/qidian_common/349573/d9552f3caec6ed7e283f90b1e764c40d/0"><i class="op-tag"></i></a></p>
                                </dd>

                            </dl>
                            <div class="bottom">
                                <h4>悬赏</h4>
                                <p><a href="http://xs.game.qidian.com/?qd_game_key=xs192x86&amp;qd_dd_p1=124" data-eid="qd_A38" target="_blank"><img src="//qidian.qpic.cn/qidian_common/349573/88abf71a366b64ef0b5d53cf58d747ca/0"><span class="op-tag"></span></a></p>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="rank-box box-center cf">
        <div class="rank-nav-list fl" data-l1="4">
            <ul>

                <li class=""><a href="//r.qidian.com/" data-eid="qd_C01">人气榜单</a><cite></cite></li>
            </ul>
            <p class="line"></p>
            <h4>热门作品排行</h4>
            <ul class="list_type_detective">
                <li class="act"><a href="//r.qidian.com/yuepiao?style=1" data-eid="qd_C02">原创风云榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/hotsales?style=1" data-eid="qd_C03">24小时热销榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/click?style=1" data-eid="qd_C04">会员点击榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/recom?style=1" data-eid="qd_C05">推荐票榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/collect?style=1" data-eid="qd_C06">收藏榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/vipup?style=1" data-eid="qd_C07">VIP更新榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/vipcollect?style=1" data-eid="qd_C08">VIP收藏榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/vipreward?style=1" data-eid="qd_C09">VIP精品打赏榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/fin?style=1" data-eid="qd_C10">完本榜</a><cite></cite></li>
            </ul>
            <p class="line"></p>
            <h4>新书排行</h4>
            <ul class="list_type_detective">
                <li class=""><a href="//r.qidian.com/signnewbook?style=1" data-eid="qd_C11">签约作家新书榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/pubnewbook?style=1" data-eid="qd_C12">公众作者新书榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/newsign?style=1" data-eid="qd_C13">新人签约新书榜</a><cite></cite></li>
                <li class=""><a href="//r.qidian.com/newauthor?style=1" data-eid="qd_C14">新人作者新书榜</a><cite></cite></li>
            </ul>
            <p class="line"></p>
            <h4>社区排行</h4>
            <ul>
                <li class=""><a href="//r.qidian.com/fans" data-eid="qd_C15">打赏粉丝榜</a><cite></cite></li>
            </ul>
            <p class="line"></p>
            <h4>其他排行</h4>
            <ul>
                <li><a target="_blank" href="http://www.qdmm.com/MMWeb/TopTen.aspx" data-eid="qd_C16">女生精选榜</a></li>
                <li><a target="_blank" href="http://top.qdmm.com/MMWeb/TopDetail.aspx?TopType=7&Time=1" data-eid="qd_C17">女生原创风云榜</a></li>
                <!--<li><a target="_blank" href="http://wwwploy.qdmm.com/MMWeb/PKList.aspx" data-eid="qd_C18">女生PK榜</a></li>-->
            </ul>
        </div>
        <div class="main-content-wrap fl" data-l1="5">
            <div class="rank-header">
                <h3 class="lang">原创风云榜<span>当月VIP作品月票数排行</span></h3>

                <div class="rank-toolbar-wrap" data-year="2017" data-month="08">
                    <div class="tool-box">
                        <div class="btn-box cf">
                            <div class="view-mode fr" id="view-mode">
                                <a class="img-text iconfont act" href="javascript:" data-eid="qd_C35">&#xe62b;</a><em>|</em><a class="only-text iconfont " href="javascript:" data-eid="qd_C36">&#xe61a;</a>
                            </div>
                            <div id="year" class="lbf-combobox">
                                <select>

                                    <option value="2017"   selected >2017年</option>

                                    <option value="2016"  >2016年</option>

                                    <option value="2015"  >2015年</option>

                                    <option value="2014"  >2014年</option>

                                    <option value="2013"  >2013年</option>

                                    <option value="2012"  >2012年</option>

                                    <option value="2011"  >2011年</option>

                                    <option value="2010"  >2010年</option>

                                    <option value="2009"  >2009年</option>

                                    <option value="2008"  >2008年</option>

                                    <option value="2007"  >2007年</option>

                                    <option value="2006"  >2006年</option>

                                    <option value="2005"  >2005年</option>

                                </select>
                                <a href="javascript:" class="lbf-button lbf-combobox-label" hidefocus="true">
                                    <span class="lbf-combobox-caption">2017年</span>
                                    <span class="lbf-combobox-icon">&nbsp;</span>
                                </a>
                            </div>
                            <div id="month" class="lbf-combobox">
                                <select>




                                    <option value="08"  selected >08月</option>


                                    <option value="07" >07月</option>


                                    <option value="06" >06月</option>


                                    <option value="05" >05月</option>


                                    <option value="04" >04月</option>


                                    <option value="03" >03月</option>


                                    <option value="02" >02月</option>


                                    <option value="01" >01月</option>


                                </select>
                                <a href="javascript:" class="lbf-button lbf-combobox-label" hidefocus="true">
                                    <span class="lbf-combobox-caption">08月</span>
                                    <span class="lbf-combobox-icon">&nbsp;</span>
                                </a>
                            </div>
                        </div>
                        <p class="line"></p>
                        <div class="type-list">
                            <p>

                                <a data-chanid="-1" class="act" href="javascript:" data-eid="qd_C20">全部分类</a><em>&#183;</em>
                                <a data-chanid="21" class="" href="javascript:" data-eid="qd_C21">玄幻</a><em>&#183;</em>
                                <a data-chanid="1" class="" href="javascript:" data-eid="qd_C22">奇幻</a><em>&#183;</em>
                                <a data-chanid="2" class="" href="javascript:" data-eid="qd_C23">武侠</a><em>&#183;</em>
                                <a data-chanid="22" class="" href="javascript:" data-eid="qd_C24">仙侠</a><em>&#183;</em>
                                <a data-chanid="4" class="" href="javascript:" data-eid="qd_C25">都市</a><em>&#183;</em>
                                <a data-chanid="15" class="" href="javascript:" data-eid="qd_C26">现实</a><em>&#183;</em>
                                <a data-chanid="6" class="" href="javascript:" data-eid="qd_C27">军事</a><em>&#183;</em>
                                <a data-chanid="5" class="" href="javascript:" data-eid="qd_C28">历史</a><em>&#183;</em>
                                <a data-chanid="7" class="" href="javascript:" data-eid="qd_C29">游戏</a><em>&#183;</em>
                                <a data-chanid="8" class="" href="javascript:" data-eid="qd_C30">体育</a><em>&#183;</em>
                                <a data-chanid="9" class="" href="javascript:" data-eid="qd_C31">科幻</a><em>&#183;</em>
                                <a data-chanid="10" class="" href="javascript:" data-eid="qd_C32">灵异</a><em>&#183;</em>
                                <a data-chanid="12" class="" href="javascript:" data-eid="qd_C33">二次元</a>

                                <em>&#183;</em>
                                <a data-chanid="0" class=""  href="javascript:" data-eid="qd_C34">当月新书</a>


                            </p>
                        </div>


                    </div>
                </div>
            </div>
            <div class="rank-body">

                <div class="rank-view-list" id="rank-view-list">

                    <div class="book-img-text">
                        <ul>

                            <li data-rid="1">
                                <div class="book-img-box">
                                    <span class="rank-tag no1">1<cite></cite></span>
                                    <a href="//book.qidian.com/info/1004608738" target="_blank" data-eid="qd_C39" data-bid="1004608738"><img src="//qidian.qpic.cn/qdbimg/349573/1004608738/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1004608738" target="_blank" data-eid="qd_C40" data-bid="1004608738">圣墟</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4362453" target="_blank" data-eid="qd_C41">辰东</a><em>|</em><a href="//xuanhuan.qidian.com" target="_blank" data-eid="qd_C42">玄幻</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        在破败中崛起，在寂灭中复苏。沧海成尘，雷电枯竭，那一缕幽雾又一次临近大地，世间的枷锁被打开了，一个全新的世界就此揭开神秘的一角……
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1004608738/383367894" target="_blank" data-eid="qd_C43" data-bid="1004608738" data-cid="//vipreader.qidian.com/chapter/1004608738/383367894">最新更新 第五百九十章 英雄本色</a><em>&#183;</em><span>2017-08-21 23:41</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>48263</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1004608738" target="_blank" data-eid="qd_C37" data-bid="1004608738">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1004608738" data-bid="1004608738">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="2">
                                <div class="book-img-box">
                                    <span class="rank-tag no2">2<cite></cite></span>
                                    <a href="//book.qidian.com/info/1005986994" target="_blank" data-eid="qd_C39" data-bid="1005986994"><img src="//qidian.qpic.cn/qdbimg/349573/1005986994/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1005986994" target="_blank" data-eid="qd_C40" data-bid="1005986994">我是至尊</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4362475" target="_blank" data-eid="qd_C41">风凌天下</a><em>|</em><a href="//xuanhuan.qidian.com" target="_blank" data-eid="qd_C42">玄幻</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        药不成丹只是毒，人不成神终成灰。…………天道有缺，人间不平，红尘世外，魍魉横行；哀尔良善，怒尔不争；规则之外，吾来执行。布武天下，屠尽不平；手中有刀，心中有情；怀中美人，刀下奸雄；
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1005986994/383405501" target="_blank" data-eid="qd_C43" data-bid="1005986994" data-cid="//vipreader.qidian.com/chapter/1005986994/383405501">最新更新 第一百九十二章 赌局！【第二更！】</a><em>&#183;</em><span>2017-08-22 11:52</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>33067</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1005986994" target="_blank" data-eid="qd_C37" data-bid="1005986994">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1005986994" data-bid="1005986994">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="3">
                                <div class="book-img-box">
                                    <span class="rank-tag no3">3<cite></cite></span>
                                    <a href="//book.qidian.com/info/1003354631" target="_blank" data-eid="qd_C39" data-bid="1003354631"><img src="//qidian.qpic.cn/qdbimg/349573/1003354631/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1003354631" target="_blank" data-eid="qd_C40" data-bid="1003354631">一念永恒</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4362120" target="_blank" data-eid="qd_C41">耳根</a><em>|</em><a href="//xianxia.qidian.com" target="_blank" data-eid="qd_C42">仙侠</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        一念成沧海，一念化桑田。一念斩千魔，一念诛万仙。唯我念……永恒这是耳根继《仙逆》《求魔》《我欲封天》后，创作的第四部长篇小说《一念永恒》
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1003354631/383291235" target="_blank" data-eid="qd_C43" data-bid="1003354631" data-cid="//vipreader.qidian.com/chapter/1003354631/383291235">最新更新 第982章 无胆老鬼，进来一战！</a><em>&#183;</em><span>2017-08-22 11:50</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>24054</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1003354631" target="_blank" data-eid="qd_C37" data-bid="1003354631">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1003354631" data-bid="1003354631">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="4">
                                <div class="book-img-box">
                                    <span class="rank-tag no4">4<cite></cite></span>
                                    <a href="//book.qidian.com/info/1003307568" target="_blank" data-eid="qd_C39" data-bid="1003307568"><img src="//qidian.qpic.cn/qdbimg/349573/1003307568/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1003307568" target="_blank" data-eid="qd_C40" data-bid="1003307568">不朽凡人</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=2342811" target="_blank" data-eid="qd_C41">鹅是老五</a><em>|</em><a href="//xianxia.qidian.com" target="_blank" data-eid="qd_C42">仙侠</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        在这里，拥有灵根才能修仙，所有凡根注定只是凡人。莫无忌，只有凡根，一介凡人！
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1003307568/383321123" target="_blank" data-eid="qd_C43" data-bid="1003307568" data-cid="//vipreader.qidian.com/chapter/1003307568/383321123">最新更新 第九百八十九章 从容应对</a><em>&#183;</em><span>2017-08-21 21:00</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>20914</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1003307568" target="_blank" data-eid="qd_C37" data-bid="1003307568">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1003307568" data-bid="1003307568">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="5">
                                <div class="book-img-box">
                                    <span class="rank-tag no5">5<cite></cite></span>
                                    <a href="//book.qidian.com/info/3602691" target="_blank" data-eid="qd_C39" data-bid="3602691"><img src="//qidian.qpic.cn/qdbimg/349573/3602691/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/3602691" target="_blank" data-eid="qd_C40" data-bid="3602691">修真聊天群</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4362241" target="_blank" data-eid="qd_C41">圣骑士的传说</a><em>|</em><a href="//dushi.qidian.com" target="_blank" data-eid="qd_C42">都市</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        某天，宋书航意外加入了一个仙侠中二病资深患者的交流群，里面的群友们都以‘道友’相称，群名片都是各种府主、洞主、真人、天师。连群主走失的宠物犬都称为大妖犬离家出走。整天聊的是炼丹、闯
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/3602691/383368172" target="_blank" data-eid="qd_C43" data-bid="3602691" data-cid="//vipreader.qidian.com/chapter/3602691/383368172">最新更新 第1392章 液态金属球天道</a><em>&#183;</em><span>2017-08-21 23:42</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>20303</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/3602691" target="_blank" data-eid="qd_C37" data-bid="3602691">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="3602691" data-bid="3602691">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="6">
                                <div class="book-img-box">
                                    <span class="rank-tag no6">6<cite></cite></span>
                                    <a href="//book.qidian.com/info/3621897" target="_blank" data-eid="qd_C39" data-bid="3621897"><img src="//qidian.qpic.cn/qdbimg/349573/3621897/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/3621897" target="_blank" data-eid="qd_C40" data-bid="3621897">真武世界</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4362465" target="_blank" data-eid="qd_C41">蚕茧里的牛</a><em>|</em><a href="//xuanhuan.qidian.com" target="_blank" data-eid="qd_C42">玄幻</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        卷入了三十三天所有巅峰强者的一场浩劫，人皇与他的对手最终一战，打碎了深渊世界，曾经封镇深渊魔王的神器，一张神秘的紫色卡片，却消失在时空漩涡中，横穿无尽的时空。辽阔的蛮荒，武道文明还
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/3621897/383357160" target="_blank" data-eid="qd_C43" data-bid="3621897" data-cid="//vipreader.qidian.com/chapter/3621897/383357160">最新更新 第一千一百一十八章 十丈生死</a><em>&#183;</em><span>2017-08-21 22:39</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>19045</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/3621897" target="_blank" data-eid="qd_C37" data-bid="3621897">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="3621897" data-bid="3621897">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="7">
                                <div class="book-img-box">
                                    <span class="rank-tag no7">7<cite></cite></span>
                                    <a href="//book.qidian.com/info/1004179514" target="_blank" data-eid="qd_C39" data-bid="1004179514"><img src="//qidian.qpic.cn/qdbimg/349573/1004179514/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1004179514" target="_blank" data-eid="qd_C40" data-bid="1004179514">天道图书馆</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4362968" target="_blank" data-eid="qd_C41">横扫天涯</a><em>|</em><a href="//xuanhuan.qidian.com" target="_blank" data-eid="qd_C42">玄幻</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        【2017最火玄幻作品，海外点击第一】张悬穿越异界，成了一名光荣的教师，脑海中多出了一个神秘的图书馆。只要他看过的东西，无论人还是物，都能自动形成书籍，记录下对方各种各样的缺点，于
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1004179514/383323196" target="_blank" data-eid="qd_C43" data-bid="1004179514" data-cid="//vipreader.qidian.com/chapter/1004179514/383323196">最新更新 第六百五十七章 两块石碑</a><em>&#183;</em><span>2017-08-21 21:00</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>18576</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1004179514" target="_blank" data-eid="qd_C37" data-bid="1004179514">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1004179514" data-bid="1004179514">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="8">
                                <div class="book-img-box">
                                    <span class="rank-tag no8">8<cite></cite></span>
                                    <a href="//book.qidian.com/info/1003656831" target="_blank" data-eid="qd_C39" data-bid="1003656831"><img src="//qidian.qpic.cn/qdbimg/349573/1003656831/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1003656831" target="_blank" data-eid="qd_C40" data-bid="1003656831">神级幸运星</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=3760200" target="_blank" data-eid="qd_C41">辰机唐红豆</a><em>|</em><a href="//dushi.qidian.com" target="_blank" data-eid="qd_C42">都市</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        无意中获得了一枚运气骰子，还穿越到娱乐业匮乏的平行位面，王昊这下牛逼了。运气骰子的六个面分别是“非常倒霉，倒霉，普通，好运，非常好运，神级好运”，每天零点刷新！“不知道今天会是什么
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1003656831/383390627" target="_blank" data-eid="qd_C43" data-bid="1003656831" data-cid="//vipreader.qidian.com/chapter/1003656831/383390627">最新更新 第六二七章 洪家，洪天意</a><em>&#183;</em><span>2017-08-22 09:28</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>17711</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1003656831" target="_blank" data-eid="qd_C37" data-bid="1003656831">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1003656831" data-bid="1003656831">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="9">
                                <div class="book-img-box">
                                    <span class="rank-tag no9">9<cite></cite></span>
                                    <a href="//book.qidian.com/info/1004142144" target="_blank" data-eid="qd_C39" data-bid="1004142144"><img src="//qidian.qpic.cn/qdbimg/349573/1004142144/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1004142144" target="_blank" data-eid="qd_C40" data-bid="1004142144">武道宗师</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4362088" target="_blank" data-eid="qd_C41">爱潜水的乌贼</a><em>|</em><a href="//xuanhuan.qidian.com" target="_blank" data-eid="qd_C42">玄幻</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        在这里，武道不再是虚无缥缈的传说，而是切切实实的传承，经过与科技的对抗后，彻底融入了社会，有了各种各样的武道比赛，文无第一，武无第二！楼成得到武道一大流派断绝的传承后，向着最初的梦
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1004142144/383402452" target="_blank" data-eid="qd_C43" data-bid="1004142144" data-cid="//vipreader.qidian.com/chapter/1004142144/383402452">最新更新 一个乐极生悲的故事</a><em>&#183;</em><span>2017-08-22 11:26</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>15046</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1004142144" target="_blank" data-eid="qd_C37" data-bid="1004142144">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1004142144" data-bid="1004142144">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="10">
                                <div class="book-img-box">
                                    <span class="rank-tag no10">10<cite></cite></span>
                                    <a href="//book.qidian.com/info/3347598" target="_blank" data-eid="qd_C39" data-bid="3347598"><img src="//qidian.qpic.cn/qdbimg/349573/3347598/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/3347598" target="_blank" data-eid="qd_C40" data-bid="3347598">我真是大明星</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4362273" target="_blank" data-eid="qd_C41">尝谕</a><em>|</em><a href="//dushi.qidian.com" target="_blank" data-eid="qd_C42">都市</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        一心想当明星的张烨穿越到了一个类似地球的新世界。电视台。主持人招聘现场。一个声音高声朗诵：“在苍茫的大海上，狂风卷集着乌云。在乌云和大海之间，海燕像黑色的闪电，在高傲地飞翔……暴风
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/3347598/383398662" target="_blank" data-eid="qd_C43" data-bid="3347598" data-cid="//vipreader.qidian.com/chapter/3347598/383398662">最新更新 第1627章【进军好莱坞？】</a><em>&#183;</em><span>2017-08-22 10:53</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>14527</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/3347598" target="_blank" data-eid="qd_C37" data-bid="3347598">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="3347598" data-bid="3347598">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="11">
                                <div class="book-img-box">
                                    <span class="rank-tag no11">11<cite></cite></span>
                                    <a href="//book.qidian.com/info/1003694333" target="_blank" data-eid="qd_C39" data-bid="1003694333"><img src="//qidian.qpic.cn/qdbimg/349573/1003694333/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1003694333" target="_blank" data-eid="qd_C40" data-bid="1003694333">斗战狂潮</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4362443" target="_blank" data-eid="qd_C41">骷髅精灵</a><em>|</em><a href="//xianxia.qidian.com" target="_blank" data-eid="qd_C42">仙侠</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        双月当空，无限可能的英魂世界孤寂黑暗，神秘古怪的嬉命小丑百城联邦，三大帝国，异族横行，魂兽霸幽这是一个英雄辈出的年代，人类卧薪尝胆重掌地球主权，孕育着进军高纬度的野望！重点是……二
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1003694333/383311036" target="_blank" data-eid="qd_C43" data-bid="1003694333" data-cid="//vipreader.qidian.com/chapter/1003694333/383311036">最新更新 第五十二章 强制传送</a><em>&#183;</em><span>2017-08-22 00:00</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>13526</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1003694333" target="_blank" data-eid="qd_C37" data-bid="1003694333">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1003694333" data-bid="1003694333">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="12">
                                <div class="book-img-box">
                                    <span class="rank-tag no12">12<cite></cite></span>
                                    <a href="//book.qidian.com/info/1003541158" target="_blank" data-eid="qd_C39" data-bid="1003541158"><img src="//qidian.qpic.cn/qdbimg/349573/1003541158/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1003541158" target="_blank" data-eid="qd_C40" data-bid="1003541158">我的1979</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=1030909" target="_blank" data-eid="qd_C41">争斤论两花花帽</a><em>|</em><a href="//dushi.qidian.com" target="_blank" data-eid="qd_C42">都市</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        一觉醒来，回到70年代，再次面对过往，你猜不透的结局..........
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1003541158/383369758" target="_blank" data-eid="qd_C43" data-bid="1003541158" data-cid="//vipreader.qidian.com/chapter/1003541158/383369758">最新更新 571、工业</a><em>&#183;</em><span>2017-08-21 23:53</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>12614</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1003541158" target="_blank" data-eid="qd_C37" data-bid="1003541158">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1003541158" data-bid="1003541158">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="13">
                                <div class="book-img-box">
                                    <span class="rank-tag no13">13<cite></cite></span>
                                    <a href="//book.qidian.com/info/3681560" target="_blank" data-eid="qd_C39" data-bid="3681560"><img src="//qidian.qpic.cn/qdbimg/349573/3681560/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/3681560" target="_blank" data-eid="qd_C40" data-bid="3681560">龙王传说</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4921" target="_blank" data-eid="qd_C41">唐家三少</a><em>|</em><a href="//xuanhuan.qidian.com" target="_blank" data-eid="qd_C42">玄幻</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        伴随着魂导科技的进步，斗罗大陆上的人类征服了海洋，又发现了两片大陆。魂兽也随着人类魂师的猎杀无度走向灭亡，沉睡无数年的魂兽之王在星斗大森林最后的净土苏醒，它要带领仅存的族人，向人类
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/3681560/383129317" target="_blank" data-eid="qd_C43" data-bid="3681560" data-cid="//vipreader.qidian.com/chapter/3681560/383129317">最新更新 第一千二百四十二章 年轻的龙老</a><em>&#183;</em><span>2017-08-22 07:00</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>9489</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/3681560" target="_blank" data-eid="qd_C37" data-bid="3681560">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="3681560" data-bid="3681560">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="14">
                                <div class="book-img-box">
                                    <span class="rank-tag no14">14<cite></cite></span>
                                    <a href="//book.qidian.com/info/1003667321" target="_blank" data-eid="qd_C39" data-bid="1003667321"><img src="//qidian.qpic.cn/qdbimg/349573/1003667321/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1003667321" target="_blank" data-eid="qd_C40" data-bid="1003667321">美食供应商</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=7746844" target="_blank" data-eid="qd_C41">会做菜的猫</a><em>|</em><a href="//dushi.qidian.com" target="_blank" data-eid="qd_C42">都市</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        “在遥远的东方，存在着一个数次拒绝了米其林三星评价的奇怪小店。那里价格昂贵，一碗配汤蛋炒饭288RMB，哦忘了还有一碟泡菜，但就算是这样也有很多人排队等候。那里不接受预定，只接受本
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1003667321/383377556" target="_blank" data-eid="qd_C43" data-bid="1003667321" data-cid="//vipreader.qidian.com/chapter/1003667321/383377556">最新更新 第六百九十八章 钱箱的变化</a><em>&#183;</em><span>2017-08-22 01:23</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>9489</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1003667321" target="_blank" data-eid="qd_C37" data-bid="1003667321">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1003667321" data-bid="1003667321">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="15">
                                <div class="book-img-box">
                                    <span class="rank-tag no15">15<cite></cite></span>
                                    <a href="//book.qidian.com/info/3516230" target="_blank" data-eid="qd_C39" data-bid="3516230"><img src="//qidian.qpic.cn/qdbimg/349573/3516230/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/3516230" target="_blank" data-eid="qd_C40" data-bid="3516230">完美人生</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4609396" target="_blank" data-eid="qd_C41">刀一耕</a><em>|</em><a href="//dushi.qidian.com" target="_blank" data-eid="qd_C42">都市</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        李谦重生了。另外一个时空的1995年。在这里，他当然比普通人更容易获得成功。但成功是什么？钱么？或者，名气？地位？荣耀？都是，但不全是。有了那回眸的浅浅一笑，那牵手的刹那温暖，那入
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/3516230/383354823" target="_blank" data-eid="qd_C43" data-bid="3516230" data-cid="//vipreader.qidian.com/chapter/3516230/383354823">最新更新 第一九三章 与虎谋皮</a><em>&#183;</em><span>2017-08-21 22:25</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>8903</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/3516230" target="_blank" data-eid="qd_C37" data-bid="3516230">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="3516230" data-bid="3516230">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="16">
                                <div class="book-img-box">
                                    <span class="rank-tag no16">16<cite></cite></span>
                                    <a href="//book.qidian.com/info/1005401501" target="_blank" data-eid="qd_C39" data-bid="1005401501"><img src="//qidian.qpic.cn/qdbimg/349573/1005401501/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1005401501" target="_blank" data-eid="qd_C40" data-bid="1005401501">极道天魔</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=2660272" target="_blank" data-eid="qd_C41">滚开</a><em>|</em><a href="//xianxia.qidian.com" target="_blank" data-eid="qd_C42">仙侠</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        妖魔横行，世人苦难。神兵魔刃，遮耀天下。——————————————————————手机上的一款游戏修改器，意外成了路胜脑海里的异能。也成了他在这个黑暗乱世唯一的依靠。~~~~~~
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1005401501/383288714" target="_blank" data-eid="qd_C43" data-bid="1005401501" data-cid="//vipreader.qidian.com/chapter/1005401501/383288714">最新更新 第三百五十章 任务 四</a><em>&#183;</em><span>2017-08-21 17:00</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>8476</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1005401501" target="_blank" data-eid="qd_C37" data-bid="1005401501">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1005401501" data-bid="1005401501">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="17">
                                <div class="book-img-box">
                                    <span class="rank-tag no17">17<cite></cite></span>
                                    <a href="//book.qidian.com/info/3242304" target="_blank" data-eid="qd_C39" data-bid="3242304"><img src="//qidian.qpic.cn/qdbimg/349573/3242304/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/3242304" target="_blank" data-eid="qd_C40" data-bid="3242304">异常生物见闻录</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4362948" target="_blank" data-eid="qd_C41">远瞳</a><em>|</em><a href="//kehuan.qidian.com" target="_blank" data-eid="qd_C42">科幻</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        郝仁，人如其名，是个好人，理想是平平安安过一辈子，当个穷不死但也发不了财的小房东——起码在他家里住进去一堆神经病生物之前是这样。一栋偏僻陈旧的大屋，一堆不怎么正常的人外生物，还有一
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/3242304/383282788" target="_blank" data-eid="qd_C43" data-bid="3242304" data-cid="//vipreader.qidian.com/chapter/3242304/383282788">最新更新 第一千五百七十六章 群星深处</a><em>&#183;</em><span>2017-08-22 07:35</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>8009</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/3242304" target="_blank" data-eid="qd_C37" data-bid="3242304">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="3242304" data-bid="3242304">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="18">
                                <div class="book-img-box">
                                    <span class="rank-tag no18">18<cite></cite></span>
                                    <a href="//book.qidian.com/info/1003899336" target="_blank" data-eid="qd_C39" data-bid="1003899336"><img src="//qidian.qpic.cn/qdbimg/349573/1003899336/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1003899336" target="_blank" data-eid="qd_C40" data-bid="1003899336">最强反套路系统</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=5373653" target="_blank" data-eid="qd_C41">太上布衣</a><em>|</em><a href="//xianxia.qidian.com" target="_blank" data-eid="qd_C42">仙侠</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        原书名《最强装逼打脸系统》！最强反套路，我TM反手就是一个套路，横扫修仙界无敌手，就问一声还有谁？装逼如风，常伴吾身！长路漫漫，装逼相伴！生死看淡，不服就干！“年轻人，当年我开始装
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1003899336/383384916" target="_blank" data-eid="qd_C43" data-bid="1003899336" data-cid="//vipreader.qidian.com/chapter/1003899336/383384916">最新更新 第八百七十二章 不许跟我抢人头</a><em>&#183;</em><span>2017-08-22 07:49</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>7978</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1003899336" target="_blank" data-eid="qd_C37" data-bid="1003899336">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1003899336" data-bid="1003899336">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="19">
                                <div class="book-img-box">
                                    <span class="rank-tag no19">19<cite></cite></span>
                                    <a href="//book.qidian.com/info/1003723851" target="_blank" data-eid="qd_C39" data-bid="1003723851"><img src="//qidian.qpic.cn/qdbimg/349573/1003723851/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/1003723851" target="_blank" data-eid="qd_C40" data-bid="1003723851">天影</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=66" target="_blank" data-eid="qd_C41">萧鼎</a><em>|</em><a href="//xianxia.qidian.com" target="_blank" data-eid="qd_C42">仙侠</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        阴阳分天地，五行定乾坤。天穹之下岁月沧桑的中土神州，正是仙道昌盛的时代，亿万生灵欣欣向荣。纵横千万里间，总有人间一幕幕悲欢离合，在恢弘长生的仙道中上演着。有光便有暗，天穹之下光辉之
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/1003723851/383301567" target="_blank" data-eid="qd_C43" data-bid="1003723851" data-cid="//vipreader.qidian.com/chapter/1003723851/383301567">最新更新 第五百零三章 私心</a><em>&#183;</em><span>2017-08-21 16:30</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>7690</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/1003723851" target="_blank" data-eid="qd_C37" data-bid="1003723851">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="1003723851" data-bid="1003723851">加入书架</a>

                                    </p>
                                </div>
                            </li>

                            <li data-rid="20">
                                <div class="book-img-box">
                                    <span class="rank-tag no20">20<cite></cite></span>
                                    <a href="//book.qidian.com/info/3446747" target="_blank" data-eid="qd_C39" data-bid="3446747"><img src="//qidian.qpic.cn/qdbimg/349573/3446747/150"></a>
                                </div>
                                <div class="book-mid-info">
                                    <h4><a href="//book.qidian.com/info/3446747" target="_blank" data-eid="qd_C40" data-bid="3446747">太古神王</a></h4>
                                    <p class="author">
                                        <img src="//qidian.gtimg.com/qd/images/ico/user.0.3.png"><a class="name" href="//me.qidian.com/authorIndex.aspx?id=4406509" target="_blank" data-eid="qd_C41">净无痕</a><em>|</em><a href="//xuanhuan.qidian.com" target="_blank" data-eid="qd_C42">玄幻</a><em>|</em><span>连载</span>
                                    </p>
                                    <p class="intro">
                                        【玄幻爽文】九天大陆，天穹之上有九条星河，亿万星辰，皆为武命星辰，武道之人，可沟通星辰，觉醒星魂，成武命修士。传说，九天大陆最为厉害的武修，每突破一个境界，便能开辟一扇星门，从而沟
                                    </p>
                                    <p class="update"><a href="//vipreader.qidian.com/chapter/3446747/383408419" target="_blank" data-eid="qd_C43" data-bid="3446747" data-cid="//vipreader.qidian.com/chapter/3446747/383408419">最新更新 第1915章 降临</a><em>&#183;</em><span>2017-08-22 12:22</span>
                                    </p>
                                </div>
                                <div class="book-right-info">
                                    <div class="total">

                                        <p><span>7344</span>月票</p>

                                    </div>
                                    <p class="btn">
                                        <a class="red-btn" href="//book.qidian.com/info/3446747" target="_blank" data-eid="qd_C37" data-bid="3446747">书籍详情</a>

                                        <a class="blue-btn add-book" href="javascript:" data-eid="qd_C38" data-bookId="3446747" data-bid="3446747">加入书架</a>

                                    </p>
                                </div>
                            </li>

                        </ul>
                    </div>

                </div>

            </div>
            <div class="page-box cf" data-eid="qd_C44">
                <div class="pagination fr" id="page-container" data-page="1" data-pageMax="25"></div>
            </div>
        </div>
    </div>
</div>
</div>
<div class="footer">
    <div class="box-center cf">
        <div class="friend-link">
            <em><a class="yuewen" href="http://www.yuewen.com" target="_blank">阅文集团</a><cite>旗下网站：</cite></em>
            <a href="//www.qidian.com">起点中文网</a>
            <a href="//www.qdmm.com" target="_blank">起点女生网</a>
            <a href="http://chuangshi.qq.com" target="_blank">创世中文网</a>
            <a href="http://yunqi.qq.com" target="_blank">云起书院</a>
            <!--<a href="http://www.rongshuxia.com" target="_blank">榕树下</a>-->
            <a href="http://www.hongxiu.com" target="_blank">红袖添香</a>
            <a href="//www.readnovel.com" target="_blank">小说阅读网</a>
            <a href="http://www.xs8.cn" target="_blank">言情小说吧</a>
            <a href="http://www.xxsy.net" target="_blank">潇湘书院</a>
            <a href="http://www.tingbook.com" target="_blank">天方听书网</a>
            <a href="http://www.lrts.me" target="_blank">懒人听书</a>
            <a href="http://yuedu.yuewen.com" target="_blank">阅文悦读</a>
            <a href="http://www.yuewen.com/app.html#appqq" target="_blank">QQ阅读</a>
            <a href="http://www.yuewen.com/app.html#appqd" target="_blank">起点读书</a>
            <a href="http://www.yuewen.com/app.html#appzj" target="_blank">作家助手</a>
            <a href="//www.webnovel.com" target="_blank" title="Qidian International">起点国际版</a>
        </div>
        <div class="footer-menu dib-wrap">
            <a href="//www.qidian.com/about/intro" target="_blank">关于起点</a>
            <a href="//www.qidian.com/about/contact" target="_blank">联系我们</a>
            <a href="http://join.book.qq.com/index.html" target="_blank">加入我们</a>
            <a href="//www.qidian.com/help/index/2" target="_blank">帮助中心</a>
            <a href="http://bbs.qidian.com/list/53" target="_blank">提交建议</a>
            <a href="http://bbs.qidian.com" target="_blank">起点论坛</a>
            <a href="http://comic.qidian.com" target="_blank">动漫频道</a>
        </div>
        <div class="copy-right">
            <p><span>Copyright &copy; 2002-2017 www.qidian.com All Rights Reserved</span>版权所有 上海玄霆娱乐信息科技有限公司</p>
            <p>上海玄霆娱乐信息科技有限公司 增值电信业务经营许可证沪B2-20080046 沪网文[2015]0081-031 新出网证（沪）字010 沪ICP备08017520号-1</p>
            <p>请所有作者发布作品时务必遵守国家互联网信息管理办法规定，我们拒绝任何色情小说，一经发现，即作删除！举报电话：010-59357051</p>
            <p>本站所收录的作品、社区话题、用户评论、用户上传内容或图片等均属用户个人行为。如前述内容侵害您的权益，欢迎举报投诉，一经核实，立即删除，本站不承担任何责任</p>
            <p>联系方式 总机 021-61870500 地址：中国（上海）自由贸易试验区碧波路690号6号楼101、201室</p>
        </div>

    </div>
</div>

</div>
<script data-ignore="true" src="//qidian.gtimg.com/lbf/1.0.4/LBF.js?max_age=31536000"></script>
<script>
    LBF.config({"paths":{"site":"//qidian.gtimg.com/qd/js","qd":"//qidian.gtimg.com/qd"},"vars":{"theme":"//qidian.gtimg.com/qd/css"},"combo":true,"debug":false});
    // 全局的通用数据都放g_data变量里
    var g_data = {};
    g_data.adBanner = {
        adTop:{"title":"","adImgUrl":"//qidian.qpic.cn/qidian_common/349573/fea5c32b86b4ce09885cf9a3377a08a2/0","adCategoryName":"广告","colorType":0,"type":1,"isAdv":1,"adJumpUrl":"http://cpgame.qd.game.qidian.com/Home/Index/directLogin/name/fhly/way/1?qd_game_key=fhly1200x60&qd_dd_p1=1694","hasAd":1},
    };
    if (g_data.adBanner.adTop) {
        g_data.adBanner.adTop.eid = 'qd_C67'
    }
    // 环境变量，会按照环境选择性打log
    g_data.envType = 'pro';
    // 用作统计PV
    g_data.pageId = 'qd_P_rank_02';
    //环境域名
    g_data.domainPreFix = '';
    // 域名环境变量
    g_data.domainSearch = 'se.qidian.com';
</script>
<script>
    LBF.use(['lib.jQuery'], function ($) {
        window.$ = $;
    });
</script>
<script>
    LBF.use([ 'monitor.SpeedReport', 'qd/js/rank/hotnew.0.78.js'], function (SpeedReport, HotNew) {
        // 页面逻辑入口
        new HotNew({});
        $(window).on('load.speedReport', function () {
            // speedTimer[onload]
            speedTimer.push(new Date().getTime());
            var f1 = 7718, // china reading limited's ID
                    f2 = 219, // site ID
                    f3 = 15; // page ID
            // chrome & IE9 Performance API
            SpeedReport.reportPerformance({
                flag1: f1,
                flag2: f2,
                flag3IE: f3,
                flag3Chrome: f3,
                rate:0.1,
                url: '//isdspeed.qidian.com/cgi-bin/r.cgi'
            });
            // common speedTimer:['dom ready', 'onload']
            var speedReport = SpeedReport.create({
                flag1: f1,
                flag2: f2,
                flag3: f3,
                start: speedZero,
                rate:0.1,
                url: '//isdspeed.qidian.com/cgi-bin/r.cgi'
            });
            // chrome & IE9 Performance API range 1~19, common speedTimer use 20+
            for (var i = 0; i < speedTimer.length; i++) {
                speedReport.add(speedTimer[i], i + 20)
            }
            // http://isdspeed.qq.com/cgi-bin/r.cgi?flag1=7718&flag2=224&flag3=1&1=38&2=38&…
            speedReport.send();
        })
    });
    // speedTimer[dom ready], put it before </body>
    speedTimer.push(new Date().getTime());
</script>
<script>
    var _mtac = {};
    (function() {
        var mta = document.createElement("script");
        mta.src = "//pingjs.qq.com/h5/stats.js?v2.0.2";
        mta.setAttribute("name", "MTAH5");
        mta.setAttribute("sid", "500451537");
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(mta, s);
    })();
</script>

</body>
</html>
