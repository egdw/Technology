# Technology
学校的科技文化节作品
/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost
 Source Database       : technology

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 04/16/2017 20:40:15 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `auth`
-- ----------------------------
DROP TABLE IF EXISTS `auth`;
CREATE TABLE `auth` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `a_permission` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `auth`
-- ----------------------------
BEGIN;
INSERT INTO `auth` VALUES ('1', 'admin'), ('2', 'worker'), ('3', 'user');
COMMIT;

-- ----------------------------
--  Table structure for `task`
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT,
  `t_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `task`
-- ----------------------------
BEGIN;
INSERT INTO `task` VALUES ('1', '阿萨德');
COMMIT;

-- ----------------------------
--  Table structure for `task_complete`
-- ----------------------------
DROP TABLE IF EXISTS `task_complete`;
CREATE TABLE `task_complete` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) DEFAULT NULL,
  `t_id` int(11) DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL,
  `agent` int(11) DEFAULT NULL,
  `iscomplete` tinyint(1) DEFAULT NULL,
  `complete_password` varchar(255) DEFAULT NULL,
  `t_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `u_id` (`u_id`),
  KEY `t_id` (`t_id`),
  KEY `agent` (`agent`),
  CONSTRAINT `complete_agent` FOREIGN KEY (`agent`) REFERENCES `user` (`u_id`),
  CONSTRAINT `complete_task` FOREIGN KEY (`t_id`) REFERENCES `task` (`t_id`) ON DELETE CASCADE,
  CONSTRAINT `complete_user` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_name` varchar(255) DEFAULT NULL,
  `u_password` varchar(255) DEFAULT NULL,
  `auth` int(11) DEFAULT NULL,
  `creadt_date` datetime DEFAULT NULL,
  `task` int(11) DEFAULT NULL,
  PRIMARY KEY (`u_id`),
  KEY `auth` (`auth`),
  KEY `task` (`task`),
  KEY `task_2` (`task`),
  KEY `task_3` (`task`),
  KEY `task_4` (`task`),
  KEY `task_5` (`task`),
  CONSTRAINT `user_auth` FOREIGN KEY (`auth`) REFERENCES `auth` (`a_id`) ON DELETE SET NULL,
  CONSTRAINT `user_task3` FOREIGN KEY (`task`) REFERENCES `task` (`t_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'admin', 'admin', '1', '2017-04-14 13:20:30', null), ('2', 'test', 'test', '3', '2017-04-16 20:40:02', null)
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
