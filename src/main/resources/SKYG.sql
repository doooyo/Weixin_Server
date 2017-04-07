/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost
 Source Database       : SKYG

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 04/08/2017 00:35:09 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sk_company`
-- ----------------------------
DROP TABLE IF EXISTS `sk_company`;
CREATE TABLE `sk_company` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '集团id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '集团(公司)名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='集团';

-- ----------------------------
--  Table structure for `sk_coupon`
-- ----------------------------
DROP TABLE IF EXISTS `sk_coupon`;
CREATE TABLE `sk_coupon` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '优惠卷id',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发放者id',
  `amount` decimal(10,0) DEFAULT NULL COMMENT '金额',
  `effectiveness` tinyint(1) DEFAULT NULL COMMENT '是否有效(0:无效 1:有效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠卷表(一人限一张)';

-- ----------------------------
--  Table structure for `sk_examinee`
-- ----------------------------
DROP TABLE IF EXISTS `sk_examinee`;
CREATE TABLE `sk_examinee` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '体检者',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '姓名',
  `age` int(2) DEFAULT NULL COMMENT '年龄',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮寄地址',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别(0: 男1:女)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='体检者';

-- ----------------------------
--  Table structure for `sk_gift`
-- ----------------------------
DROP TABLE IF EXISTS `sk_gift`;
CREATE TABLE `sk_gift` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '赠品id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '赠品名',
  ` detail` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT ' 赠品描述',
  `deadline` date DEFAULT NULL COMMENT '截止日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='赠品';

-- ----------------------------
--  Table structure for `sk_gift_release`
-- ----------------------------
DROP TABLE IF EXISTS `sk_gift_release`;
CREATE TABLE `sk_gift_release` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '赠品发放信息id',
  `gift_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '赠品id',
  `is_mailed` tinyint(1) DEFAULT NULL COMMENT '是否已邮寄(后台使用, 0:未邮寄 1:已邮寄)',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='赠品发放信息';

-- ----------------------------
--  Table structure for `sk_order`
-- ----------------------------
DROP TABLE IF EXISTS `sk_order`;
CREATE TABLE `sk_order` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单id',
  `product_id_list` varchar(600) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单商品id(以'',''逗号分隔, 这是一个冗余字段, 有一个order_products表)',
  `coupon_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '优惠卷id(优惠卷只能一张)',
  `vouchers_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '代金劵(可以无限张, 使用逗号分隔id)',
  `examinee_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '体检人id',
  `amount` decimal(10,0) unsigned zerofill DEFAULT '0000000000' COMMENT '金额',
  `state` int(1) unsigned zerofill DEFAULT '0' COMMENT '订单状态(0:未付款, 1:已付款 2:已结算 3:已取消)',
  `is_invoice` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '是否需要发票(0: 无需发票1:需要发票)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息表';

-- ----------------------------
--  Table structure for `sk_order_detectionbox`
-- ----------------------------
DROP TABLE IF EXISTS `sk_order_detectionbox`;
CREATE TABLE `sk_order_detectionbox` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '订单id',
  `detectionbox_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '检测盒id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单检测盒关系表';

-- ----------------------------
--  Table structure for `sk_product`
-- ----------------------------
DROP TABLE IF EXISTS `sk_product`;
CREATE TABLE `sk_product` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '产品id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '产品名字',
  `img_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '产品图片相对路径',
  `price` decimal(10,2) DEFAULT NULL COMMENT '产品价格',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '产品描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
--  Records of `sk_product`
-- ----------------------------
BEGIN;
INSERT INTO `sk_product` VALUES ('001', '产品A', '/xxx/xxx/1.jpg', '15.00', '产品A测试', null), ('002', '产品B', '/xxx/xxx/2.jpg', '16.00', '产品B测试', null), ('7e8779bb-5a0e-4197-9a68-db2be52fb51a', '产品D', '/user/xxx/4.jpg', '30.52', '产品D的描述......', '2017-04-08 00:34:03'), ('f1366c77-b2f4-4edf-aaf5-a4b863a9417a', '产品C', '/user/xxx/3.jpg', '31.00', '产品C的描述......', '2017-04-08 00:24:53');
COMMIT;

-- ----------------------------
--  Table structure for `sk_user`
-- ----------------------------
DROP TABLE IF EXISTS `sk_user`;
CREATE TABLE `sk_user` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `p_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '父级代理',
  `points` int(6) DEFAULT '0' COMMENT '积分',
  `photo` mediumblob COMMENT '用户照片',
  `audit_state` tinyint(1) DEFAULT '0' COMMENT '审核状态(注册需要审核) 0:未审核 1:已审核',
  `is_agent` tinyint(1) DEFAULT '0' COMMENT '是否区域代理 0:普通用户 1:区域代理',
  `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手机',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Records of `sk_user`
-- ----------------------------
BEGIN;
INSERT INTO `sk_user` VALUES ('00010102', '001', '50', null, '0', '0', 'test', 'e10adc3949ba59abbe56e057f20f883e', '15828645446');
COMMIT;

-- ----------------------------
--  Table structure for `sk_voucher`
-- ----------------------------
DROP TABLE IF EXISTS `sk_voucher`;
CREATE TABLE `sk_voucher` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '代金劵id',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发放者id',
  `amount` decimal(10,0) DEFAULT NULL COMMENT '金额',
  `effectiveness` tinyint(4) DEFAULT NULL COMMENT '是否有效(0:无效 1:有效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代金劵表';

SET FOREIGN_KEY_CHECKS = 1;
