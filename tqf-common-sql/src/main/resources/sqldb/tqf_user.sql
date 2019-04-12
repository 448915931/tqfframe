/*
Navicat MySQL Data Transfer

Source Server         : 10.121.22.166
Source Server Version : 50725
Source Host           : 10.121.22.166:3306
Source Database       : testdba

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-04-12 11:47:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tqf_user`
-- ----------------------------
DROP TABLE IF EXISTS `tqf_user`;
CREATE TABLE `tqf_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `authority` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tqf_user
-- ----------------------------
INSERT INTO `tqf_user` VALUES ('1', 'admin', '$2a$10$CwnLqshfwyweSEQkVzQ9lOi9.uL/njsxzeIYAykX9R4KwZh5O6c42', 'admin', '桂林');
INSERT INTO `tqf_user` VALUES ('2', 'tang', '$2a$10$Y1iBeX.lGFi.9NfDd1gHcOn2HrWrOOvQgjcHvpbXv6HEmyQk9nmCy', 'user', '深圳');
INSERT INTO `tqf_user` VALUES ('3', 'user', '$2a$10$DHzbBuizM8IXD9NjBfBujeYnIwgxlzDQE8Z9LkT7gxTXaU4vCbH2a', 'user', '北京');
INSERT INTO `tqf_user` VALUES ('5', 'tangqifeng', '$2a$10$F.By8ePTKisKS2NyiCymRu/QD/73sC8HiPCYzfzZJsK9uzSh4zrQq', 'admin', '深圳');
