/*
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : auth-user

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-06-09 20:21:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `refresh_token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `login_pwd` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `modify_by` bigint(20) DEFAULT NULL,
  `gmt_modify` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for user_access_token
-- ----------------------------
DROP TABLE IF EXISTS `user_access_token`;
CREATE TABLE `user_access_token` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `access_token` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `expire_at` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `modify_by` bigint(20) DEFAULT NULL,
  `gmt_modify` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
