/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.56-log : Database - kanshu
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`kanshu` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `kanshu`;

/*Table structure for table `alipay_order` */

DROP TABLE IF EXISTS `alipay_order`;

CREATE TABLE `alipay_order` (
  `alipay_order_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL COMMENT '用户id',
  `product_id` bigint(11) NOT NULL COMMENT '购买的产品ID',
  `WIDout_trade_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `WIDsubject` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `WIDtotal_amount` double NOT NULL COMMENT '付款金额',
  `WIDbody` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '商品描述',
  `channel` int(8) DEFAULT NULL COMMENT '渠道号',
  `type` smallint(2) NOT NULL COMMENT '类型 1：支付宝充值 -1:充值并单章购买 -2：充值并批量购买 -3：充值并全本购买 -4：VIP购买',
  `comment` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`alipay_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `alipay_response` */

DROP TABLE IF EXISTS `alipay_response`;

CREATE TABLE `alipay_response` (
  `alipay_response_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `trade_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '支付宝交易号',
  `out_trade_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '商户订单号',
  `trade_status` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '交易状态',
  `total_amount` double DEFAULT NULL COMMENT '订单金额（元）',
  `receipt_amount` double DEFAULT NULL COMMENT '实收金额（元）',
  `status` smallint(1) DEFAULT '0' COMMENT '0：未处理 1：已处理',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`alipay_response_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `author` */

DROP TABLE IF EXISTS `author`;

CREATE TABLE `author` (
  `author_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作者名字',
  `penname` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作者笔名',
  `desc` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '作者简介',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`author_id`),
  KEY `idx_name` (`name`),
  KEY `idx_penname` (`penname`)
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `book_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(300) COLLATE utf8_bin NOT NULL COMMENT '书名',
  `word_count` int(9) NOT NULL COMMENT '字数',
  `cover_url` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '图书封面',
  `author_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作者名',
  `author_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作者名称',
  `author_penname` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '笔名',
  `intro` varchar(3000) COLLATE utf8_bin DEFAULT NULL COMMENT '简介',
  `shelf_status` smallint(1) NOT NULL COMMENT '在架状态 0：下架 1：在架',
  `category_sec_id` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '二级分类',
  `category_sec_name` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `category_thr_id` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '三级分类',
  `category_thr_name` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `keyword` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '关键字',
  `charge_type` smallint(1) NOT NULL DEFAULT '1' COMMENT '???????? 0???? 1???',
  `is_full` smallint(1) NOT NULL COMMENT '是否完结 0：连载 1：完结',
  `price` int(8) DEFAULT NULL COMMENT '全本购买价格',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `tag` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '标签',
  `last_chapter_update_date` datetime DEFAULT NULL COMMENT '最近一个章节的更新时间',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '版权方',
  `copyright_book_id` bigint(20) DEFAULT NULL COMMENT '版权方图书id',
  `type` int(2) NOT NULL COMMENT '类型 1：原创 2 出版物',
  `unit_price` int(2) NOT NULL COMMENT '千字价格',
  `file_format` int(2) DEFAULT NULL COMMENT '书文件格式 1:txt 2:腾讯精排简版 3:腾讯精排完整 4: 以上三种都支持',
  `is_monthly` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否允许包月 0：不允许 1：允许',
  `monthly_start_date` datetime DEFAULT NULL COMMENT '包月开始时间',
  `monthly_end_date` datetime DEFAULT NULL COMMENT '包月结束时间',
  `update_date` datetime NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE KEY `idx_cp_bookid` (`copyright_book_id`),
  KEY `idx_title` (`title`(255)),
  KEY `idx_a_name` (`author_name`),
  KEY `idx_a_penname` (`author_penname`),
  KEY `idx_shelf_status` (`shelf_status`),
  KEY `idx_category_sec` (`category_sec_id`),
  KEY `idx_category_thr` (`category_thr_id`),
  KEY `idx_is_full` (`is_full`)
) ENGINE=InnoDB AUTO_INCREMENT=280 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `book_expand` */

DROP TABLE IF EXISTS `book_expand`;

CREATE TABLE `book_expand` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(11) NOT NULL,
  `book_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `click_num` bigint(11) DEFAULT NULL COMMENT '点击量',
  `sale_num` bigint(11) DEFAULT NULL COMMENT '销售额',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `level` smallint(2) NOT NULL COMMENT '层级',
  `pid` bigint(11) NOT NULL COMMENT '父ID',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '版权方code',
  `copyright_category_id` bigint(20) DEFAULT NULL,
  `update_date` datetime NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `idx_cp_id` (`copyright_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8997 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `copyright` */

DROP TABLE IF EXISTS `copyright`;

CREATE TABLE `copyright` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `desc` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `drive_book` */

DROP TABLE IF EXISTS `drive_book`;

CREATE TABLE `drive_book` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(11) NOT NULL COMMENT '图书id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `type` smallint(1) NOT NULL COMMENT '类型 1：限免榜 2：搜索榜 3：畅销榜',
  `score` int(8) NOT NULL COMMENT '排序分数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=864 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `key_word` */

DROP TABLE IF EXISTS `key_word`;

CREATE TABLE `key_word` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `score` int(8) DEFAULT NULL,
  `set_source` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `pay_before` */

DROP TABLE IF EXISTS `pay_before`;

CREATE TABLE `pay_before` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `param` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `return_url` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '回跳地址',
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `pull_book` */

DROP TABLE IF EXISTS `pull_book`;

CREATE TABLE `pull_book` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `copyright_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'ç‰ˆæƒæ–¹code',
  `copyright_book_id` bigint(20) NOT NULL COMMENT 'ç‰ˆæƒæ–¹å›¾ä¹¦id',
  `pull_status` smallint(1) NOT NULL COMMENT 'æ‹‰å–çŠ¶æ€ 1ï¼šæˆåŠŸ 0ï¼šå¤±è´¥',
  `pull_failure_cause` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT 'å¤±è´¥åŽŸå› ',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=430 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `pull_chapter` */

DROP TABLE IF EXISTS `pull_chapter`;

CREATE TABLE `pull_chapter` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `copyright_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'ç‰ˆæƒæ–¹code',
  `copyright_book_id` bigint(20) NOT NULL COMMENT 'ç‰ˆæƒæ–¹å›¾ä¹¦id',
  `copyright_volume_id` bigint(20) NOT NULL COMMENT 'ç‰ˆæƒæ–¹å·id',
  `copyright_chapter_id` bigint(20) NOT NULL COMMENT 'ç‰ˆæƒæ–¹ç« èŠ‚id',
  `pull_status` smallint(1) NOT NULL COMMENT 'æ‹‰å–çŠ¶æ€ 1ï¼šæˆåŠŸ 0ï¼šå¤±è´¥',
  `pull_failure_cause` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT 'å¤±è´¥åŽŸå› ',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `pull_volume` */

DROP TABLE IF EXISTS `pull_volume`;

CREATE TABLE `pull_volume` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `copyright_code` varchar(20) COLLATE utf8_bin NOT NULL COMMENT 'ç‰ˆæƒæ–¹code',
  `copyright_book_id` bigint(20) NOT NULL COMMENT 'ç‰ˆæƒæ–¹å›¾ä¹¦id',
  `copyright_volume_id` bigint(20) NOT NULL COMMENT 'ç‰ˆæƒæ–¹å·id',
  `pull_status` smallint(1) NOT NULL COMMENT 'æ‹‰å–çŠ¶æ€ 1ï¼šæˆåŠŸ 0ï¼šå¤±è´¥',
  `pull_failure_cause` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=590 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `recharge_item` */

DROP TABLE IF EXISTS `recharge_item`;

CREATE TABLE `recharge_item` (
  `recharge_item_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `price` double NOT NULL COMMENT '价格（元）',
  `money` int(8) NOT NULL COMMENT '充值金额（钻）',
  `virtual` int(8) DEFAULT NULL COMMENT '赠送金额（钻）',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`recharge_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `search_heat` */

DROP TABLE IF EXISTS `search_heat`;

CREATE TABLE `search_heat` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(11) NOT NULL,
  `heat_value` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `set_source` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `nick_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '别名',
  `logo` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `sex` smallint(1) DEFAULT NULL COMMENT '性别 0：女 1：男',
  `channel` int(8) DEFAULT NULL COMMENT '注册时渠道号',
  `channel_now` int(8) NOT NULL COMMENT '当前渠道号',
  `email` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `tel` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `device_serial_no` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'imei->android_id->serialNunber ->UUID生成的',
  `device_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'Android或IOS或H5',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_account` */

DROP TABLE IF EXISTS `user_account`;

CREATE TABLE `user_account` (
  `account_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `money` bigint(11) NOT NULL COMMENT '账户金额',
  `virtual_money` bigint(11) NOT NULL COMMENT '虚拟币金额',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_account_log` */

DROP TABLE IF EXISTS `user_account_log`;

CREATE TABLE `user_account_log` (
  `account_log_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `order_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `unit_money` int(9) NOT NULL COMMENT '花费金额',
  `unit_virtual` int(9) NOT NULL COMMENT '花费虚拟币',
  `type` int(3) NOT NULL COMMENT '1：支付宝充值 2：微信充值 -1:单章购买 -2：批量购买 -3：全本购买 -4：VIP消费',
  `product_id` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `comment` varchar(300) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `channel` bigint(10) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`account_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_pay_book` */

DROP TABLE IF EXISTS `user_pay_book`;

CREATE TABLE `user_pay_book` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `order_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `book_id` bigint(11) NOT NULL COMMENT '图书id',
  `type` smallint(1) NOT NULL COMMENT '1：批量购买 2：全本购买',
  `start_chapter_id` bigint(11) NOT NULL COMMENT '开始章节id',
  `start_chapter_idx` int(6) NOT NULL COMMENT '开始章节序号',
  `end_chapter_id` bigint(11) NOT NULL COMMENT '结束章节id',
  `end_chapter_idx` int(6) NOT NULL COMMENT '结束章节序号',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`user_id`),
  KEY `idx_bid` (`book_id`),
  KEY `idx_uid_bid` (`user_id`,`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_pay_chapter` */

DROP TABLE IF EXISTS `user_pay_chapter`;

CREATE TABLE `user_pay_chapter` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `order_no` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `book_id` bigint(11) NOT NULL COMMENT '图书id',
  `chapter_id` bigint(11) NOT NULL COMMENT '章节id',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`user_id`),
  KEY `idx_bid` (`book_id`),
  KEY `idx_uid_bid` (`user_id`,`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_qq` */

DROP TABLE IF EXISTS `user_qq`;

CREATE TABLE `user_qq` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_receive` */

DROP TABLE IF EXISTS `user_receive`;

CREATE TABLE `user_receive` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `vip_status` smallint(1) NOT NULL COMMENT '新手礼包vip领取状态 0:未领取 1：已领取',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_uuid` */

DROP TABLE IF EXISTS `user_uuid`;

CREATE TABLE `user_uuid` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_vip` */

DROP TABLE IF EXISTS `user_vip`;

CREATE TABLE `user_vip` (
  `user_vip_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `channel` int(8) DEFAULT NULL,
  `end_date` datetime NOT NULL COMMENT '到期日期',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`user_vip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_weibo` */

DROP TABLE IF EXISTS `user_weibo`;

CREATE TABLE `user_weibo` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_weixin` */

DROP TABLE IF EXISTS `user_weixin`;

CREATE TABLE `user_weixin` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `version_info` */

DROP TABLE IF EXISTS `version_info`;

CREATE TABLE `version_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `version` int(8) NOT NULL COMMENT '版本号',
  `channel` int(8) NOT NULL COMMENT '渠道号',
  `type` smallint(1) NOT NULL COMMENT '1:强制升级 0:手动升级',
  `update_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `vip` */

DROP TABLE IF EXISTS `vip`;

CREATE TABLE `vip` (
  `vip_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `days` int(5) NOT NULL COMMENT 'vip时长(天)',
  `price` int(5) NOT NULL COMMENT 'vip原价(元)',
  `discount_price` int(5) NOT NULL COMMENT 'vip折扣价格(元)',
  `virtual_money` int(6) DEFAULT '0' COMMENT '赠送金额（钻）',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`vip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `volume` */

DROP TABLE IF EXISTS `volume`;

CREATE TABLE `volume` (
  `volume_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(11) NOT NULL COMMENT '书籍ID',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '卷名称',
  `desc` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '卷描述',
  `idx` int(6) NOT NULL COMMENT '排序',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `shelf_status` smallint(1) DEFAULT NULL,
  `update_date` datetime NOT NULL,
  `create_date` datetime NOT NULL,
  PRIMARY KEY (`volume_id`)
) ENGINE=InnoDB AUTO_INCREMENT=590 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
