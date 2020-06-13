/**
 * 用户表
 *
 */
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`           INT(11)     NOT NULL AUTO_INCREMENT,
    `user_name`    VARCHAR(10) NOT NULL COMMENT '用户名',
    `nick_name`    VARCHAR(10) NOT NULL COMMENT '昵称',
    `password`     VARCHAR(50) NOT NULL COMMENT '密码',
    `status`       TINYINT(1)  NOT NULL DEFAULT '1' COMMENT '状态 1:开启 0:禁用',
    `remark`       VARCHAR(100)         DEFAULT NULL COMMENT '备注',
    `created_date` TIMESTAMP   NULL     DEFAULT NULL,
    `updated_date` TIMESTAMP   NULL     DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_name` (`user_name`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;
insert into `sys_user`(`id`, `user_name`, `nick_name`, `password`, `status`, `remark`, `created_date`, `updated_date`)
values (1, 'admin', 'admin', 'd4dc566564e942188de625e9de521b89', 1, '', '2018-09-26 15:36:28', '2019-11-15 19:14:31');

/**
 * 角色表
 *
 */
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`           INT(11)     NOT NULL AUTO_INCREMENT,
    `role_name`    VARCHAR(20) NOT NULL COMMENT '角色名称',
    `roles`        VARCHAR(20) NOT NULL COMMENT '授权标识',
    `remark`       VARCHAR(100)     DEFAULT NULL COMMENT '备注',
    `created_date` TIMESTAMP   NULL DEFAULT NULL,
    `updated_date` TIMESTAMP   NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `role_name` (`role_name`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;
insert into `sys_role`(`id`, `role_name`, `roles`, `remark`, `created_date`, `updated_date`)
values (1, '系统管理员', 'admin', '管理员', '2018-09-25 21:26:07', '2019-11-18 21:04:52');

/**
 * 资源表
 *
 */
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`
(
    `id`           INT(11)     NOT NULL AUTO_INCREMENT,
    `menu_name`    VARCHAR(20) NOT NULL COMMENT '名称',
    `menu_url`     VARCHAR(100)     DEFAULT NULL COMMENT '路径',
    `parent_id`    INT(11)          DEFAULT NULL COMMENT '父节点',
    `display_sort` INT(11)          DEFAULT NULL COMMENT '顺序',
    `display_type` INT(11)          DEFAULT NULL COMMENT '类型',
    `permission`   VARCHAR(100)     DEFAULT NULL COMMENT '授权标识',
    `icon`         VARCHAR(50)      DEFAULT NULL COMMENT '图标',
    `remark`       VARCHAR(100)     DEFAULT NULL COMMENT '备注',
    `created_date` TIMESTAMP   NULL DEFAULT NULL,
    `updated_date` TIMESTAMP   NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (1, '系统管理', '', 0, 1, 1, '', 'layui-icon-util', '系统权限', '2018-09-25 21:30:04', '2019-11-15 16:34:28');
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (2, '菜单管理', '/menu/list', 1, 1, 2, '', '', '菜单', '2018-09-25 21:32:58', NULL);
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (3, '角色管理', '/role/list', 1, 2, 2, '', '', '角色', '2018-09-25 21:34:02', NULL);
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (4, '账号管理', '/user/list', 1, 3, 2, '', '', '账号', '2018-09-25 21:34:02', NULL);
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (5, '查询', '', 4, 0, 3, 'user:select', '', '', '2019-11-15 20:14:59', '2019-11-18 14:18:30');
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (6, '新增', '', 4, 0, 3, 'user:create', '', '', '2019-11-15 20:16:15', '2019-11-18 14:18:09');
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (7, '编辑', '', 4, 0, 3, 'user:update', '', '', '2019-11-15 20:18:22', '2019-11-18 14:18:12');
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (8, '删除', '', 4, 0, 3, 'user:delete', '', NULL, '2019-11-18 20:53:27', NULL);
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (9, '查询', '', 3, 0, 3, 'role:select', '', NULL, '2019-11-18 21:03:00', NULL);
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (10, '新增', '', 3, 0, 3, 'role:create', '', NULL, '2019-11-18 21:03:27', NULL);
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (11, '编辑', '', 3, 0, 3, 'role:update', '', NULL, '2019-11-18 21:03:51', NULL);
insert into `sys_menu`(`id`, `menu_name`, `menu_url`, `parent_id`, `display_sort`, `display_type`, `permission`, `icon`,
                       `remark`, `created_date`, `updated_date`)
values (12, '删除', '', 3, 0, 3, 'role:delete', '', NULL, '2019-11-18 21:04:12', NULL);

/**
 * 用户-角色
 *
 */
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
    `user_id` INT(12) NOT NULL,
    `role_id` INT(12) NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;
insert into `sys_user_role`(`user_id`, `role_id`)
values (1, 1);

/**
 * 角色-权限
 *
 */
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`
(
    `role_id` INT(11) NOT NULL,
    `menu_id` INT(11) NOT NULL,
    PRIMARY KEY (`role_id`, `menu_id`)
) ENGINE = INNODB DEFAULT CHARSET = utf8;
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 1);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 2);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 3);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 4);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 5);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 6);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 7);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 8);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 9);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 10);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 11);
insert into `sys_role_menu`(`role_id`, `menu_id`) values (1, 12);