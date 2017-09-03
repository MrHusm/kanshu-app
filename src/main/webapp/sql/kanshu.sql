
DROP TABLE IF EXISTS `alipay_order`;

CREATE TABLE `alipay_order` (
  `alipay_order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL COMMENT '用户id',
  `product_id` BIGINT(11) NOT NULL COMMENT '购买的产品ID',
  `WIDout_trade_no` VARCHAR(100) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `WIDsubject` VARCHAR(200) COLLATE utf8_bin NOT NULL COMMENT '商品名称',
  `WIDtotal_amount` DOUBLE NOT NULL COMMENT '付款金额',
  `WIDbody` VARCHAR(300) COLLATE utf8_bin DEFAULT NULL COMMENT '商品描述',
  `channel` INT(8) DEFAULT NULL COMMENT '渠道号',
  `type` SMALLINT(2) NOT NULL COMMENT '类型 1：支付宝充值 -1:充值并单章购买 -2：充值并批量购买 -3：充值并全本购买 -4：VIP购买',
  `comment` VARCHAR(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`alipay_order_id`),
  UNIQUE KEY `idx_out_trade_no` (`WIDout_trade_no`),
  KEY `idx_uid` (`user_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_type` (`type`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `alipay_response` */

DROP TABLE IF EXISTS `alipay_response`;

CREATE TABLE `alipay_response` (
  `alipay_response_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `trade_no` VARCHAR(100) COLLATE utf8_bin NOT NULL COMMENT '支付宝交易号',
  `out_trade_no` VARCHAR(100) COLLATE utf8_bin NOT NULL COMMENT '商户订单号',
  `trade_status` VARCHAR(100) COLLATE utf8_bin NOT NULL COMMENT '交易状态',
  `total_amount` DOUBLE DEFAULT NULL COMMENT '订单金额（元）',
  `receipt_amount` DOUBLE DEFAULT NULL COMMENT '实收金额（元）',
  `status` SMALLINT(1) DEFAULT '0' COMMENT '0：未处理 1：已处理',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`alipay_response_id`),
  UNIQUE KEY `idx_out_trade_no` (`out_trade_no`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `author` */

DROP TABLE IF EXISTS `author`;

CREATE TABLE `author` (
  `author_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作者名字',
  `penname` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作者笔名',
  `desc` VARCHAR(500) COLLATE utf8_bin DEFAULT NULL COMMENT '作者简介',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`author_id`),
  KEY `idx_name` (`name`),
  KEY `idx_penname` (`penname`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `book_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(300) COLLATE utf8_bin NOT NULL COMMENT '书名',
  `word_count` INT(9) NOT NULL COMMENT '字数',
  `cover_url` VARCHAR(200) COLLATE utf8_bin NOT NULL COMMENT '图书封面',
  `author_id` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作者名',
  `author_name` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL COMMENT '作者名称',
  `author_penname` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL COMMENT '笔名',
  `intro` VARCHAR(3000) COLLATE utf8_bin DEFAULT NULL COMMENT '简介',
  `shelf_status` SMALLINT(1) NOT NULL COMMENT '在架状态 0：下架 1：在架',
  `category_sec_id` VARCHAR(60) COLLATE utf8_bin DEFAULT NULL COMMENT '二级分类',
  `category_sec_name` VARCHAR(30) COLLATE utf8_bin DEFAULT NULL,
  `category_thr_id` VARCHAR(60) COLLATE utf8_bin DEFAULT NULL COMMENT '三级分类',
  `category_thr_name` VARCHAR(30) COLLATE utf8_bin DEFAULT NULL,
  `keyword` VARCHAR(200) COLLATE utf8_bin DEFAULT NULL COMMENT '关键字',
  `charge_type` SMALLINT(1) NOT NULL DEFAULT '1' COMMENT '计费方式 1:按章 2:按本',
  `is_full` SMALLINT(1) NOT NULL COMMENT '是否完结 0：连载 1：完结',
  `price` INT(8) DEFAULT NULL COMMENT '全本购买价格',
  `is_free` SMALLINT(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `tag` VARCHAR(300) COLLATE utf8_bin DEFAULT NULL COMMENT '标签',
  `last_chapter_update_date` DATETIME DEFAULT NULL COMMENT '最近一个章节的更新时间',
  `copyright_code` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL COMMENT '版权方',
  `copyright_book_id` BIGINT(20) DEFAULT NULL COMMENT '版权方图书id',
  `type` INT(2) NOT NULL COMMENT '类型 1：原创 2 出版物',
  `unit_price` INT(2) NOT NULL COMMENT '千字价格',
  `file_format` INT(2) DEFAULT NULL COMMENT '书文件格式 1:txt 2:腾讯精排简版 3:腾讯精排完整 4: 以上三种都支持',
  `is_monthly` SMALLINT(1) NOT NULL DEFAULT '0' COMMENT '是否允许包月 0：不允许 1：允许',
  `monthly_start_date` DATETIME DEFAULT NULL COMMENT '包月开始时间',
  `monthly_end_date` DATETIME DEFAULT NULL COMMENT '包月结束时间',
  `update_date` DATETIME NOT NULL,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE KEY `idx_cpCode_cpBookId` (`copyright_book_id`,`copyright_code`),
  KEY `idx_title` (`title`(255)),
  KEY `idx_a_name` (`author_name`),
  KEY `idx_a_penname` (`author_penname`),
  KEY `idx_shelf_status` (`shelf_status`),
  KEY `idx_category_sec` (`category_sec_id`),
  KEY `idx_category_thr` (`category_thr_id`),
  KEY `idx_is_full` (`is_full`),
  KEY `idx_author_id` (`author_id`),
  KEY `idx_tag` (`tag`(255))
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `book_expand` */

DROP TABLE IF EXISTS `book_expand`;

CREATE TABLE `book_expand` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `book_id` BIGINT(11) NOT NULL,
  `book_name` VARCHAR(100) COLLATE utf8_bin NOT NULL,
  `click_num` BIGINT(11) DEFAULT NULL COMMENT '点击量',
  `sale_num` BIGINT(11) DEFAULT NULL COMMENT '销售量',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_book_id` (`book_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `level` SMALLINT(2) NOT NULL COMMENT '层级',
  `pid` BIGINT(11) NOT NULL COMMENT '父ID',
  `copyright_code` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL COMMENT '版权方code',
  `copyright_category_id` BIGINT(20) DEFAULT NULL,
  `update_date` DATETIME NOT NULL,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `idx_cpCode_cpId` (`copyright_category_id`,`copyright_code`),
  KEY `idx_pid` (`pid`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `copyright` */

DROP TABLE IF EXISTS `copyright`;

CREATE TABLE `copyright` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(20) COLLATE utf8_bin NOT NULL,
  `name` VARCHAR(100) COLLATE utf8_bin NOT NULL,
  `desc` VARCHAR(500) COLLATE utf8_bin DEFAULT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_code` (`code`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `drive_book` */

DROP TABLE IF EXISTS `drive_book`;

CREATE TABLE `drive_book` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `book_id` BIGINT(11) NOT NULL COMMENT '图书id',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `type` SMALLINT(1) NOT NULL COMMENT '类型 1：首页驱动 2：首页男生最爱 3：首页女生频道 4：首页二次元 5：大家都在搜索 6：书库全站畅销 7：书库完结精选 8：书库重磅新书 9：限免 10：书籍相关图书',
  `score` INT(8) DEFAULT NULL COMMENT '排序分数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_type_bookId` (`book_id`,`type`),
  KEY `idx_bookId` (`book_id`),
  KEY `idx_type` (`type`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `key_word` */

DROP TABLE IF EXISTS `key_word`;

CREATE TABLE `key_word` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `word` VARCHAR(200) COLLATE utf8_bin DEFAULT NULL,
  `score` INT(8) DEFAULT NULL,
  `set_source` VARCHAR(200) COLLATE utf8_bin DEFAULT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `pay_before` */

DROP TABLE IF EXISTS `pay_before`;

CREATE TABLE `pay_before` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `param` VARCHAR(100) COLLATE utf8_bin DEFAULT NULL,
  `return_url` VARCHAR(200) COLLATE utf8_bin DEFAULT NULL COMMENT '回跳地址',
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `pull_book` */

DROP TABLE IF EXISTS `pull_book`;

CREATE TABLE `pull_book` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `copyright_code` VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '版权方code',
  `copyright_book_id` BIGINT(20) NOT NULL COMMENT '版权方图书id',
  `pull_status` SMALLINT(1) NOT NULL COMMENT '拉取状态 1：拉取成功 0：拉取失败 3:正在拉取',
  `pull_failure_cause` TEXT COLLATE utf8_bin COMMENT '失败原因',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_cpCode_cpBookId` (`copyright_code`,`copyright_book_id`),
  KEY `idx_cpBookId` (`copyright_book_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `pull_chapter` */

DROP TABLE IF EXISTS `pull_chapter`;

CREATE TABLE `pull_chapter` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `copyright_code` VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '版权方code',
  `copyright_book_id` BIGINT(20) NOT NULL COMMENT '版权方图书id',
  `copyright_volume_id` BIGINT(20) NOT NULL COMMENT '版权方卷id',
  `copyright_chapter_id` BIGINT(20) NOT NULL COMMENT '版权方章节id',
  `pull_status` SMALLINT(1) NOT NULL COMMENT '拉取状态 1：拉取成功 0：拉取失败',
  `pull_failure_cause` VARCHAR(300) COLLATE utf8_bin DEFAULT NULL COMMENT '失败原因',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_cpCode_cpChapterId` (`copyright_code`,`copyright_chapter_id`),
  KEY `idx_cpBookId` (`copyright_book_id`),
  KEY `idx_cpChpaterId` (`copyright_chapter_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `pull_volume` */

DROP TABLE IF EXISTS `pull_volume`;

CREATE TABLE `pull_volume` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `copyright_code` VARCHAR(20) COLLATE utf8_bin NOT NULL COMMENT '版权方code',
  `copyright_book_id` BIGINT(20) NOT NULL COMMENT '版权方图书id',
  `copyright_volume_id` BIGINT(20) NOT NULL COMMENT '版权方卷id',
  `pull_status` SMALLINT(1) NOT NULL COMMENT '拉取状态 1：拉取成功 0：拉取失败',
  `pull_failure_cause` VARCHAR(300) COLLATE utf8_bin DEFAULT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_cpCode_cpVolumeId` (`copyright_code`,`copyright_volume_id`),
  KEY `idx_cpBookId` (`copyright_book_id`),
  KEY `idx_cpVolumeId` (`copyright_volume_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `recharge_item` */

DROP TABLE IF EXISTS `recharge_item`;

CREATE TABLE `recharge_item` (
  `recharge_item_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `price` DOUBLE NOT NULL COMMENT '价格（元）',
  `money` INT(8) NOT NULL COMMENT '充值金额（钻）',
  `virtual` INT(8) DEFAULT NULL COMMENT '赠送金额（钻）',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`recharge_item_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `search_heat` */

DROP TABLE IF EXISTS `search_heat`;

CREATE TABLE `search_heat` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `book_id` BIGINT(11) NOT NULL,
  `heat_value` VARCHAR(300) COLLATE utf8_bin DEFAULT NULL,
  `set_source` VARCHAR(300) COLLATE utf8_bin DEFAULT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` VARCHAR(50) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `nick_name` VARCHAR(100) COLLATE utf8_bin NOT NULL COMMENT '别名',
  `logo` VARCHAR(200) COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  `sex` SMALLINT(1) DEFAULT NULL COMMENT '性别 0：女 1：男',
  `channel` INT(8) DEFAULT NULL COMMENT '注册时渠道号',
  `channel_now` INT(8) NOT NULL COMMENT '当前渠道号',
  `email` VARCHAR(60) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `tel` VARCHAR(30) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  `device_serial_no` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'imei->android_id->serialNunber ->UUID生成的',
  `device_type` VARCHAR(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'Android或IOS或H5',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `idx_name` (`name`),
  KEY `idx_nick_name` (`nick_name`),
  KEY `idx_tel` (`tel`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_account` */

DROP TABLE IF EXISTS `user_account`;

CREATE TABLE `user_account` (
  `account_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL COMMENT '用户ID',
  `money` BIGINT(11) NOT NULL COMMENT '账户金额',
  `virtual_money` BIGINT(11) NOT NULL COMMENT '虚拟币金额',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `idx_uid` (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_account_log` */

DROP TABLE IF EXISTS `user_account_log`;

CREATE TABLE `user_account_log` (
  `account_log_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL COMMENT '用户ID',
  `order_no` VARCHAR(100) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `unit_money` INT(9) NOT NULL COMMENT '花费金额',
  `unit_virtual` INT(9) NOT NULL COMMENT '花费虚拟币',
  `type` INT(3) NOT NULL COMMENT '1：支付宝充值 2：微信充值 -1:单章购买 -2：批量购买 -3：全本购买 -4：VIP消费',
  `product_id` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
  `comment` VARCHAR(300) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `channel` BIGINT(10) DEFAULT NULL,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`account_log_id`),
  UNIQUE KEY `idx_orderNo` (`order_no`),
  KEY `idx_uid` (`user_id`),
  KEY `idx_type` (`type`),
  KEY `idx_productId` (`product_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_pay_book` */

DROP TABLE IF EXISTS `user_pay_book`;

CREATE TABLE `user_pay_book` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `order_no` VARCHAR(100) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `book_id` BIGINT(11) NOT NULL COMMENT '图书id',
  `type` SMALLINT(1) NOT NULL COMMENT '1：批量购买 2：全本购买',
  `start_chapter_id` BIGINT(11) NOT NULL COMMENT '开始章节id',
  `start_chapter_idx` INT(6) NOT NULL COMMENT '开始章节序号',
  `end_chapter_id` BIGINT(11) NOT NULL COMMENT '结束章节id',
  `end_chapter_idx` INT(6) NOT NULL COMMENT '结束章节序号',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`user_id`),
  KEY `idx_bid` (`book_id`),
  KEY `idx_uid_bid` (`user_id`,`book_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_pay_chapter` */

DROP TABLE IF EXISTS `user_pay_chapter`;

CREATE TABLE `user_pay_chapter` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL COMMENT '用户ID',
  `order_no` VARCHAR(100) COLLATE utf8_bin NOT NULL COMMENT '订单号',
  `book_id` BIGINT(11) NOT NULL COMMENT '图书id',
  `chapter_id` BIGINT(11) NOT NULL COMMENT '章节id',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_uid` (`user_id`),
  KEY `idx_bid` (`book_id`),
  KEY `idx_uid_bid` (`user_id`,`book_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_qq` */

DROP TABLE IF EXISTS `user_qq`;

CREATE TABLE `user_qq` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_receive` */

DROP TABLE IF EXISTS `user_receive`;

CREATE TABLE `user_receive` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `vip_status` SMALLINT(1) NOT NULL COMMENT '新手礼包vip领取状态 0:未领取 1：已领取',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uid` (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_uuid` */

DROP TABLE IF EXISTS `user_uuid`;

CREATE TABLE `user_uuid` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_vip` */

DROP TABLE IF EXISTS `user_vip`;

CREATE TABLE `user_vip` (
  `user_vip_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `channel` INT(8) DEFAULT NULL,
  `end_date` DATETIME NOT NULL COMMENT '到期日期',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`user_vip_id`),
  UNIQUE KEY `idx_uid` (`user_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_weibo` */

DROP TABLE IF EXISTS `user_weibo`;

CREATE TABLE `user_weibo` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_weixin` */

DROP TABLE IF EXISTS `user_weixin`;

CREATE TABLE `user_weixin` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `version_info` */

DROP TABLE IF EXISTS `version_info`;

CREATE TABLE `version_info` (
  `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `version` INT(8) NOT NULL COMMENT '版本号',
  `channel` INT(8) NOT NULL COMMENT '渠道号',
  `type` SMALLINT(1) NOT NULL COMMENT '1:强制升级 0:手动升级',
  `update_date` DATETIME DEFAULT NULL,
  `create_date` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_channel` (`channel`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `vip` */

DROP TABLE IF EXISTS `vip`;

CREATE TABLE `vip` (
  `vip_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `days` INT(5) NOT NULL COMMENT 'vip时长(天)',
  `price` INT(5) NOT NULL COMMENT 'vip原价(元)',
  `discount_price` INT(5) NOT NULL COMMENT 'vip折扣价格(元)',
  `virtual_money` INT(6) DEFAULT '0' COMMENT '赠送金额（钻）',
  `create_date` DATETIME NOT NULL,
  `update_date` DATETIME NOT NULL,
  PRIMARY KEY (`vip_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `volume` */

DROP TABLE IF EXISTS `volume`;

CREATE TABLE `volume` (
  `volume_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `book_id` BIGINT(11) NOT NULL COMMENT '书籍ID',
  `name` VARCHAR(100) COLLATE utf8_bin NOT NULL COMMENT '卷名称',
  `desc` VARCHAR(500) COLLATE utf8_bin DEFAULT NULL COMMENT '卷描述',
  `idx` INT(6) NOT NULL COMMENT '排序',
  `copyright_code` VARCHAR(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` BIGINT(20) DEFAULT NULL,
  `copyright_volume_id` BIGINT(20) DEFAULT NULL,
  `shelf_status` SMALLINT(1) DEFAULT NULL,
  `update_date` DATETIME NOT NULL,
  `create_date` DATETIME NOT NULL,
  PRIMARY KEY (`volume_id`),
  UNIQUE KEY `idx_cpCode_cpVolumeId` (`copyright_code`,`copyright_volume_id`),
  KEY `idx_bookId` (`book_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
