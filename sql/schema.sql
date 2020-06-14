DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(20) NOT NULL COMMENT '名称',
  `menu_url` varchar(100) DEFAULT NULL COMMENT '路径',
  `parent_id` int(11) DEFAULT NULL COMMENT '父节点',
  `display_sort` int(11) DEFAULT NULL COMMENT '顺序',
  `display_type` int(11) DEFAULT NULL COMMENT '类型',
  `permission` varchar(100) DEFAULT NULL COMMENT '授权标识',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
insert  into `sys_menu`(`id`,`menu_name`,`menu_url`,`parent_id`,`display_sort`,`display_type`,`permission`,`icon`,`remark`,`created_date`,`updated_date`) values (1,'系统管理','',0,1,1,'','layui-icon-util','系统权限','2018-09-25 21:30:04','2019-11-15 16:34:28'),(2,'菜单管理','/menu/list',1,1,2,'','','菜单','2018-09-25 21:32:58',NULL),(3,'角色管理','/role/list',1,2,2,'','','角色','2018-09-25 21:34:02',NULL),(4,'账号管理','/user/list',1,3,2,'','','账号','2018-09-25 21:34:02',NULL),(5,'查询','',4,0,3,'sys:user:select','','','2019-11-15 20:14:59','2019-11-18 14:18:30'),(6,'新增','',4,0,3,'sys:user:create','','','2019-11-15 20:16:15','2019-11-18 14:18:09'),(7,'编辑','',4,0,3,'sys:user:update','','','2019-11-15 20:18:22','2019-11-18 14:18:12'),(8,'删除','',4,0,3,'sys:user:delete','',NULL,'2019-11-18 20:53:27',NULL),(9,'查询','',3,0,3,'sys:role:select','',NULL,'2019-11-18 21:03:00',NULL),(10,'新增','',3,0,3,'sys:role:create','',NULL,'2019-11-18 21:03:27',NULL),(11,'编辑','',3,0,3,'sys:role:update','',NULL,'2019-11-18 21:03:51',NULL),(12,'删除','',3,0,3,'sys:role:delete','',NULL,'2019-11-18 21:04:12',NULL);

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `roles` varchar(20) NOT NULL COMMENT '授权标识',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
insert  into `sys_role`(`id`,`role_name`,`roles`,`remark`,`created_date`,`updated_date`) values (1,'系统管理员','admin','管理员','2018-09-25 21:26:07','2019-11-18 21:04:52');

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert  into `sys_role_menu`(`role_id`,`menu_id`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12);

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(10) NOT NULL COMMENT '用户名',
  `nick_name` varchar(10) NOT NULL COMMENT '昵称',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1:开启 0:禁用',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
insert  into `sys_user`(`id`,`user_name`,`nick_name`,`password`,`status`,`remark`,`created_date`,`updated_date`) values (1,'admin','admin','d4dc566564e942188de625e9de521b89',1,'','2018-09-26 15:36:28','2019-11-15 19:14:31');

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` int(12) NOT NULL,
  `role_id` int(12) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert  into `sys_user_role`(`user_id`,`role_id`) values (1,1);
