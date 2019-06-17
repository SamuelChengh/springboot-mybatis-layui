/*
* 用户表
*/
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login_name` VARCHAR(50) NOT NULL COMMENT '登录名',
  `nick_name` VARCHAR(50) NOT NULL COMMENT '昵称',
  `password` VARCHAR(50) NOT NULL COMMENT '密码',
  `status` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '状态 1:开启 0:禁用',
  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  `updated_date` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
INSERT INTO `sys_user`(`id`,`login_name`,`nick_name`,`password`,`status`,`remark`,`created_date`,`updated_date`) VALUES (1,'admin','admin','d4dc566564e942188de625e9de521b89',1,'','2018-09-26 15:36:28',NULL);

/*
* 角色表
*/
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '名称',
  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  `updated_date` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
INSERT INTO `sys_role`(`id`,`name`,`remark`,`created_date`,`updated_date`) VALUES (1,'系统管理员','管理员','2018-09-25 21:26:07',NULL);

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
  `display_type` INT(11) DEFAULT NULL COMMENT '类型',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
  `created_date` TIMESTAMP NULL DEFAULT NULL,
  `updated_date` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `auth_url` (`auth_url`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
INSERT INTO `sys_authority`(`id`,`name`,`auth_url`,`parent`,`display_sort`,`display_type`,`icon`,`remark`,`created_date`,`updated_date`) VALUES (1,'权限管理','',0,1,1,'','系统权限','2018-09-25 21:30:04','2018-09-25 21:32:07');
INSERT INTO `sys_authority`(`id`,`name`,`auth_url`,`parent`,`display_sort`,`display_type`,`icon`,`remark`,`created_date`,`updated_date`) VALUES (2,'菜单管理','/menu/list',1,11,2,'','菜单','2018-09-25 21:32:58',NULL);
INSERT INTO `sys_authority`(`id`,`name`,`auth_url`,`parent`,`display_sort`,`display_type`,`icon`,`remark`,`created_date`,`updated_date`) VALUES (3,'角色管理','/role/list',1,12,2,'','角色','2018-09-25 21:34:02',NULL);
INSERT INTO `sys_authority`(`id`,`name`,`auth_url`,`parent`,`display_sort`,`display_type`,`icon`,`remark`,`created_date`,`updated_date`) VALUES (4,'账号管理','/user/list',1,13,2,'','账号','2018-09-25 21:34:02',NULL);

/*
* 角色-权限
*/
DROP TABLE IF EXISTS `sys_role_authority`;
CREATE TABLE `sys_role_authority` (
  `role_id` INT(11) NOT NULL,
  `authority_id` INT(11) NOT NULL,
  PRIMARY KEY (`role_id`,`authority_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
INSERT INTO `sys_role_authority`(`role_id`,`authority_id`) VALUES (1,1);
INSERT INTO `sys_role_authority`(`role_id`,`authority_id`) VALUES (1,2);
INSERT INTO `sys_role_authority`(`role_id`,`authority_id`) VALUES (1,3);
INSERT INTO `sys_role_authority`(`role_id`,`authority_id`) VALUES (1,4);

/*
* 用户-角色
*/
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` INT(12) NOT NULL,
  `role_id` INT(12) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
INSERT INTO `sys_user_role`(`user_id`,`role_id`) VALUES (1,1);
