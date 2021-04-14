/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50623
Source Host           : localhost:3306
Source Database       : im_database

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2015-05-30 12:51:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `im_friendlist`
-- ----------------------------
DROP TABLE IF EXISTS `im_friendlist`;
CREATE TABLE `im_friendlist` (
  `master` int(11) NOT NULL,
  `friendid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_friendlist
-- ----------------------------

-- ----------------------------
-- Table structure for `im_msg`
-- ----------------------------
DROP TABLE IF EXISTS `im_msg`;
CREATE TABLE `im_msg` (
  `sendid` int(11) NOT NULL,
  `getid` int(11) DEFAULT NULL,
  `msg` varchar(3000) DEFAULT NULL,
  `trantype` tinyint(3) DEFAULT NULL,
  `time` varchar(30) DEFAULT NULL,
  `resulttype` tinyint(4) DEFAULT NULL,
  `messagetype` tinyint(4) DEFAULT NULL,
  `sendname` varchar(18) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of im_msg
-- ----------------------------

-- ----------------------------
-- Table structure for `im_user`
-- ----------------------------
DROP TABLE IF EXISTS `im_user`;
CREATE TABLE `im_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '自动生成的ID',
  `account` varchar(18) NOT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `photo` mediumblob,
  `location` varchar(50) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `isOnline` tinyint(4) DEFAULT '0',
  `password` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
