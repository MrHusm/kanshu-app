/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.56-log : Database - kanshu_chapter
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`kanshu_chapter` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `kanshu_chapter`;

/*Table structure for table `chapter0` */

DROP TABLE IF EXISTS `chapter0`;

CREATE TABLE `chapter0` (
  `chapter_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `volume_id` bigint(11) DEFAULT NULL,
  `book_id` bigint(11) NOT NULL COMMENT '图书ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '章节名称',
  `idx` int(11) NOT NULL COMMENT '排序',
  `price` int(5) NOT NULL COMMENT '以5 分/千字计算出的本章售价',
  `word_count` int(8) NOT NULL COMMENT '字数',
  `shelf_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1：上架 0：下架',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `content_md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用于对章节内容的一致性进',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `copyright_chapter_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3311 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `chapter1` */

DROP TABLE IF EXISTS `chapter1`;

CREATE TABLE `chapter1` (
  `chapter_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `volume_id` bigint(11) DEFAULT NULL,
  `book_id` bigint(11) NOT NULL COMMENT '图书ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '章节名称',
  `idx` int(11) NOT NULL COMMENT '排序',
  `price` int(5) NOT NULL COMMENT '以5 分/千字计算出的本章售价',
  `word_count` int(8) NOT NULL COMMENT '字数',
  `shelf_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1：上架 1：下架',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `content_md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用于对章节内容的一致性进',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `copyright_chapter_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7573 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `chapter2` */

DROP TABLE IF EXISTS `chapter2`;

CREATE TABLE `chapter2` (
  `chapter_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `volume_id` bigint(11) DEFAULT NULL,
  `book_id` bigint(11) NOT NULL COMMENT '图书ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '章节名称',
  `idx` int(11) NOT NULL COMMENT '排序',
  `price` int(5) NOT NULL COMMENT '以5 分/千字计算出的本章售价',
  `word_count` int(8) NOT NULL COMMENT '字数',
  `shelf_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1：上架 1：下架',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `content_md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用于对章节内容的一致性进',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `copyright_chapter_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6801 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `chapter3` */

DROP TABLE IF EXISTS `chapter3`;

CREATE TABLE `chapter3` (
  `chapter_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `volume_id` bigint(11) DEFAULT NULL,
  `book_id` bigint(11) NOT NULL COMMENT '图书ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '章节名称',
  `idx` int(11) NOT NULL COMMENT '排序',
  `price` int(5) NOT NULL COMMENT '以5 分/千字计算出的本章售价',
  `word_count` int(8) NOT NULL COMMENT '字数',
  `shelf_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1：上架 1：下架',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `content_md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用于对章节内容的一致性进',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `copyright_chapter_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5027 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `chapter4` */

DROP TABLE IF EXISTS `chapter4`;

CREATE TABLE `chapter4` (
  `chapter_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `volume_id` bigint(11) DEFAULT NULL,
  `book_id` bigint(11) NOT NULL COMMENT '图书ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '章节名称',
  `idx` int(11) NOT NULL COMMENT '排序',
  `price` int(5) NOT NULL COMMENT '以5 分/千字计算出的本章售价',
  `word_count` int(8) NOT NULL COMMENT '字数',
  `shelf_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1：上架 1：下架',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `content_md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用于对章节内容的一致性进',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `copyright_chapter_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4596 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `chapter5` */

DROP TABLE IF EXISTS `chapter5`;

CREATE TABLE `chapter5` (
  `chapter_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `volume_id` bigint(11) DEFAULT NULL,
  `book_id` bigint(11) NOT NULL COMMENT '图书ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '章节名称',
  `idx` int(11) NOT NULL COMMENT '排序',
  `price` int(5) NOT NULL COMMENT '以5 分/千字计算出的本章售价',
  `word_count` int(8) NOT NULL COMMENT '字数',
  `shelf_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1：上架 1：下架',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `content_md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用于对章节内容的一致性进',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `copyright_chapter_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5098 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `chapter6` */

DROP TABLE IF EXISTS `chapter6`;

CREATE TABLE `chapter6` (
  `chapter_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `volume_id` bigint(11) DEFAULT NULL,
  `book_id` bigint(11) NOT NULL COMMENT '图书ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '章节名称',
  `idx` int(11) NOT NULL COMMENT '排序',
  `price` int(5) NOT NULL COMMENT '以5 分/千字计算出的本章售价',
  `word_count` int(8) NOT NULL COMMENT '字数',
  `shelf_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1：上架 1：下架',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `content_md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用于对章节内容的一致性进',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `copyright_chapter_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6844 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `chapter7` */

DROP TABLE IF EXISTS `chapter7`;

CREATE TABLE `chapter7` (
  `chapter_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `volume_id` bigint(11) DEFAULT NULL,
  `book_id` bigint(11) NOT NULL COMMENT '图书ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '章节名称',
  `idx` int(11) NOT NULL COMMENT '排序',
  `price` int(5) NOT NULL COMMENT '以5 分/千字计算出的本章售价',
  `word_count` int(8) NOT NULL COMMENT '字数',
  `shelf_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1：上架 1：下架',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `content_md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用于对章节内容的一致性进',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `copyright_chapter_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3477 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `chapter8` */

DROP TABLE IF EXISTS `chapter8`;

CREATE TABLE `chapter8` (
  `chapter_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `volume_id` bigint(11) DEFAULT NULL,
  `book_id` bigint(11) NOT NULL COMMENT '图书ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '章节名称',
  `idx` int(11) NOT NULL COMMENT '排序',
  `price` int(5) NOT NULL COMMENT '以5 分/千字计算出的本章售价',
  `word_count` int(8) NOT NULL COMMENT '字数',
  `shelf_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1：上架 1：下架',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `content_md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用于对章节内容的一致性进',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `copyright_chapter_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4338 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `chapter9` */

DROP TABLE IF EXISTS `chapter9`;

CREATE TABLE `chapter9` (
  `chapter_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `volume_id` bigint(11) DEFAULT NULL,
  `book_id` bigint(11) NOT NULL COMMENT '图书ID',
  `title` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '章节名称',
  `idx` int(11) NOT NULL COMMENT '排序',
  `price` int(5) NOT NULL COMMENT '以5 分/千字计算出的本章售价',
  `word_count` int(8) NOT NULL COMMENT '字数',
  `shelf_status` smallint(1) NOT NULL DEFAULT '1' COMMENT '1：上架 1：下架',
  `is_free` smallint(1) NOT NULL DEFAULT '1' COMMENT '是否免费 1：收费 0：免费',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `content_md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '用于对章节内容的一致性进',
  `copyright_code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `copyright_book_id` bigint(20) DEFAULT NULL,
  `copyright_volume_id` bigint(20) DEFAULT NULL,
  `copyright_chapter_id` bigint(20) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4981 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
