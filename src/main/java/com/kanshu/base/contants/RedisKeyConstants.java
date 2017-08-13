package com.kanshu.base.contants;

/**
 * @author hushengmeng
 * @date 2017/8/4.
 */
public class RedisKeyConstants {

     //根据用户id获取用户key
    public final static String CACHE_USER_ID_KEY="user_id_";

    //图书目录缓存key
    public final static String CACHE_BOOK_CATALOG_KEY = "book_catalog_";

    //图书驱动key
    public final static String CACHE_DRIVE_BOOK_KEY = "drive_book_type_";

    //图书驱动具体图书key
    public final static String CACHE_DRIVE_BOOK_ONE_KEY = "drive_book_type_%s_bid_%s";

    //章节+内容key
    public final static String CACHE_CHAPTER_CONTENT_KEY = "chapter_content_cid_";
}
