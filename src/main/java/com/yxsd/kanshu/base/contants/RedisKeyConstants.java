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

    //章节+内容key
    public final static String CACHE_CHAPTER_TYPE_KEY = "chapter_content_cid_%s_type_%s";

    //图书信息key
    public final static String CACHE_BOOK_KEY = "book_id_";

    //充值赠返最大金额key
    public final static String CACHE_RECHARGE_MAX_VIRTUAL_KEY = "recharge_max_virtual";

    //版本信息key
    public final static String CACHE_VERSION_INFO_CHANNEL_KEY = "version_info_channel_";

    //父id获取子分类key
    public final static String CACHE_CATEGORY_LIST_PID_KEY = "category_list_pid_";
    
    public final static String CACHE_SPECIAL_KEY="special_";


}
