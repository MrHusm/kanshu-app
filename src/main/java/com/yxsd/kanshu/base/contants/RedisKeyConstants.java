package com.yxsd.kanshu.base.contants;

/**
 * @author hushengmeng
 * @date 2017/8/4.
 */
public class RedisKeyConstants {

     //根据用户id获取用户key
    public final static String CACHE_USER_ID_KEY="user_id_";

    //根据用户id获取绑定qqkey
    public final static String CACHE_USER_QQ_ID_KEY = "user_qq_id_";

    //根据用户id获取绑定微信key
    public final static String CACHE_USER_WEIXIN_ID_KEY = "user_weixin_id_";

    //根据用户id获取绑定微博key
    public final static String CACHE_USER_WEIBO_ID_KEY = "user_weibo_id_";

    //图书目录缓存key
    public final static String CACHE_BOOK_CATALOG_KEY = "book_catalog_";

    //图书驱动key
    public final static String CACHE_DRIVE_BOOK_KEY = "drive_book_type_%s_status_%s";

    //图书驱动具体图书key
    public final static String CACHE_DRIVE_BOOK_ONE_KEY = "drive_book_type_%s_bid_%s_status_%s";

    //限时免费图书
    public final static String CACHE_TIME_FREE_BOOK_KEY = "time_free_books";

    //章节+内容key
    public final static String CACHE_CHAPTER_TYPE_KEY = "chapter_content_cid_%s_type_%s";

    //图书信息key
    public final static String CACHE_BOOK_KEY = "book_id_";

    //作者图书信息key
    public final static String CACHE_BOOKS_AUTHOR_KEY = "books_author_";

    //图书相关图书信息key
    public final static String CACHE_BOOKS_HIGH_CLICK_CID_KEY = "books_high_click_cid_";

    //充值赠返最大金额key
    public final static String CACHE_RECHARGE_MAX_VIRTUAL_KEY = "recharge_max_virtual";

    //版本信息key
    public final static String CACHE_VERSION_INFO_CHANNEL_KEY = "version_info_channel_";

    //父id获取子分类key
    public final static String CACHE_CATEGORY_LIST_PID_KEY = "category_list_pid_";

    //点击量最高图书key
    public final static String CACHE_MAX_CLICK_BOOK_KEY = "max_click_book";

    //充值信息key
    public final static String CACHE_RECHARGE_ITEM_LIST_KEY = "recharge_item_list_type_";

    //搜索词推荐key
    public final static String CACHE_SEARCH_WORD_KEY = "search_word";

    public final static String CACHE_SPECIAL_KEY="special_";

    //渠道图书默认计费点key
    public final static String CACHE_BOOK_CHANNEL_POINT_KEY = "book_channel_point";

    //图书计费点key
    public final static String CACHE_BOOK_POINT_MAP_KEY = "book_point_map";


}
