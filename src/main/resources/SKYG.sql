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

 Date: 04/09/2017 15:56:01 PM
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
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '持有者id(在生成时指定持有人)',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `is_effect` tinyint(1) DEFAULT NULL COMMENT '是否有效(0:无效 1:有效)',
  `is_expired` tinyint(1) DEFAULT NULL COMMENT '是否过期(0:未过期 1:已过期)',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `use_date` datetime DEFAULT NULL COMMENT '使用日期',
  `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID(后台人员)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠卷表(一人限一张)';

-- ----------------------------
--  Records of `sk_coupon`
-- ----------------------------
BEGIN;
INSERT INTO `sk_coupon` VALUES ('1', '00010102', '101.52', '1', '0', '2017-04-08 22:18:12', '2017-04-08 22:18:18', '00010102'), ('2', '00010102', '101.52', '1', '0', '2017-04-09 00:17:30', '2017-04-09 00:17:36', '00010102'), ('3', '00010102', '111.00', '1', '0', '2017-04-09 00:18:09', '2017-04-09 00:18:13', '00010102');
COMMIT;

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
  `mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '体检人手机',
  `identity_id` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '身份证ID',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
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
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '金额',
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
INSERT INTO `sk_product` VALUES ('2FC0791BAC744601893E78F1EA599FEB', '产品12~', '/image/icon_12.jpg', '172.55', '产品12的描述......', '2017-04-09 15:35:06'), ('3FE2A699E16D4F05BCC34572DDD89BF5', '产品2~', '/image/icon_2.jpg', '112.55', '产品2的描述......', '2017-04-09 15:35:06'), ('4DFFB6884C12438DBE2694230E27EE22', '产品11~', '/image/icon_11.jpg', '166.55', '产品11的描述......', '2017-04-09 15:35:06'), ('6F8F18D75BB84833BBC54DE379CC5F55', '产品15~', '/image/icon_15.jpg', '190.55', '产品15的描述......', '2017-04-09 15:35:06'), ('751DE588D623498697C95AD4A43260AF', '产品13~', '/image/icon_13.jpg', '178.55', '产品13的描述......', '2017-04-09 15:35:06'), ('7B4E90DBB8E2496E80C82E9A6BFD9D75', '产品9~', '/image/icon_9.jpg', '154.55', '产品9的描述......', '2017-04-09 15:35:06'), ('833D9FEF7E544DCA972A6E5A13366252', '产品6~', '/image/icon_6.jpg', '136.55', '产品6的描述......', '2017-04-09 15:35:06'), ('A26785D77E944E42A23D1BDA09855D21', '产品7~', '/image/icon_7.jpg', '142.55', '产品7的描述......', '2017-04-09 15:35:06'), ('AF86A43CDFA4473184BB385EBE809900', '产品8~', '/image/icon_8.jpg', '148.55', '产品8的描述......', '2017-04-09 15:35:06'), ('BE01DCD0EC61423BBEEA33BF408EBD74', '产品16~', '/image/icon_16.jpg', '196.55', '产品16的描述......', '2017-04-09 15:35:06'), ('C5801448590C43C49D8F68B4D225EF49', '产品4~', '/image/icon_4.jpg', '124.55', '产品4的描述......', '2017-04-09 15:35:06'), ('D54CACEA241849AFBE2C5FD66545FAB5', '产品3~', '/image/icon_3.jpg', '118.55', '产品3的描述......', '2017-04-09 15:35:06'), ('E57551511445462F9FD597D6D779695C', '产品1~', '/image/icon_1.jpg', '106.55', '产品1的描述......', '2017-04-09 15:35:06'), ('E7491E5B907547A7970C1830830EFCC6', '产品14~', '/image/icon_14.jpg', '184.55', '产品14的描述......', '2017-04-09 15:35:06'), ('F47A7DEB504B48EC9F173391D6862A22', '产品10~', '/image/icon_10.jpg', '160.55', '产品10的描述......', '2017-04-09 15:35:06'), ('FE13F756DA374B099C20E27E4F93CBF4', '产品5~', '/image/icon_5.jpg', '130.55', '产品5的描述......', '2017-04-09 15:35:06');
COMMIT;

-- ----------------------------
--  Table structure for `sk_user`
-- ----------------------------
DROP TABLE IF EXISTS `sk_user`;
CREATE TABLE `sk_user` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `p_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '父级代理',
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
INSERT INTO `sk_user` VALUES ('00010102', 'be9e9b0c-39df-456b-9223-c03fe0f3e77c', '100', null, '1', '1', 'test', 'e10adc3949ba59abbe56e057f20f883e', '15828645447'), ('be9e9b0c-39df-456b-9223-c03fe0f3e77c', '', '0', null, '0', '0', 'doyo', 'e10adc3949ba59abbe56e057f20f883e', '15828645446');
COMMIT;

-- ----------------------------
--  Table structure for `sk_voucher`
-- ----------------------------
DROP TABLE IF EXISTS `sk_voucher`;
CREATE TABLE `sk_voucher` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '代金劵id',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '持有者id(在生成时指定持有人)',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `is_effect` tinyint(4) DEFAULT NULL COMMENT '是否有效(0:无效 1:有效)',
  `is_expired` tinyint(4) DEFAULT NULL COMMENT '是否过期(0:未过期 1:已过期)',
  `create_date` datetime DEFAULT NULL COMMENT '代金劵创建日期',
  `use_date` datetime DEFAULT NULL COMMENT '使用日期',
  `create_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建人ID(后台人员)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代金劵表';

-- ----------------------------
--  Records of `sk_voucher`
-- ----------------------------
BEGIN;
INSERT INTO `sk_voucher` VALUES ('1', '00010102', '123.00', '1', '0', '2017-04-09 10:22:36', '2017-04-09 10:22:39', '00010102'), ('2', '00010102', '134.56', '1', '0', '2017-04-09 10:23:22', '2017-04-09 10:23:25', '00010102'), ('3', '00010102', '111.11', '1', '0', '2017-04-09 10:24:14', '2017-04-09 10:24:16', '00010102');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
