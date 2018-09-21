/*
* 用户表
*/
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login_name` VARCHAR(50) NOT NULL COMMENT '登录名',
  `nick_name` VARCHAR(50) NOT NULL COMMENT '昵称',
  `password` VARCHAR(50) NOT NULL COMMENT '密码',
  `salt` VARCHAR(50) NOT NULL COMMENT '密码盐',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态 1:开启 0:禁用',
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  `updated_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*
* 角色表
*/
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '名称',
  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  `updated_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*
* 权限表
*/
DROP TABLE IF EXISTS `sys_authority`;
CREATE TABLE `sys_authority` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '名称',
  `auth_url` VARCHAR(100) NOT NULL COMMENT '路径',
  `parent` INT(11) DEFAULT NULL COMMENT '父节点',
  `display_sort` INT(11) DEFAULT NULL COMMENT '顺序',
  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  `updated_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `auth_url` (`auth_url`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*
* 角色-权限
*/
DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority` (
  `role_id` INT(11) NOT NULL,
  `authority_id` INT(11) NOT NULL,
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  `updated_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`,`authority_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*
* 用户-角色
*/
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` INT(12) NOT NULL,
  `role_id` INT(12) NOT NULL,
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  `updated_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
