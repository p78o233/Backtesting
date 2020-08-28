/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : backtesting

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-08-28 19:27:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `stock`
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `id` int(11) NOT NULL COMMENT '股票表主键',
  `stoid` varchar(10) DEFAULT NULL COMMENT '接口返回的股票id',
  `symbol` varchar(50) DEFAULT NULL COMMENT '股票编号',
  `sname` varchar(50) DEFAULT NULL COMMENT '股票名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of stock
-- ----------------------------

-- ----------------------------
-- Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', '1');

-- ----------------------------
-- Table structure for `testgroup`
-- ----------------------------
DROP TABLE IF EXISTS `testgroup`;
CREATE TABLE `testgroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组别表',
  `userId` int(11) DEFAULT NULL COMMENT '用户id',
  `groupName` varchar(255) DEFAULT NULL COMMENT '组别名',
  `isdel` tinyint(4) DEFAULT '0' COMMENT '是否删除0正常 1删除',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of testgroup
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表',
  `account` varchar(11) DEFAULT NULL COMMENT '账号',
  `pwd` varchar(36) DEFAULT NULL COMMENT '密码',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime DEFAULT NULL COMMENT '修改时间',
  `isdel` tinyint(4) DEFAULT '0' COMMENT '是否正常 0正常1删除',
  `token` varchar(36) DEFAULT NULL COMMENT '令牌',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
