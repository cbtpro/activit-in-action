DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`           BIGINT       NOT NULL COMMENT '主键ID (雪花算法生成)',
    `username`     VARCHAR(50)  NOT NULL COMMENT '用户名',
    `password`     VARCHAR(100) NOT NULL COMMENT '密码',
    `name`         VARCHAR(50)  DEFAULT NULL COMMENT '真实姓名',
    `age`          INT          DEFAULT NULL COMMENT '年龄',
    `email`        VARCHAR(100) DEFAULT NULL COMMENT '邮箱',

    /* BaseEntity 公共字段 */
    `revision`     INT          DEFAULT 1 COMMENT '乐观锁版本号',
    `created_by`   VARCHAR(50)  DEFAULT NULL COMMENT '创建人',
    `created_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   VARCHAR(50)  DEFAULT NULL COMMENT '更新人',
    `updated_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 (0-未删除 1-已删除)',
    `deleted_by`   VARCHAR(50)  DEFAULT NULL COMMENT '删除人',
    `deleted_time` DATETIME     DEFAULT NULL COMMENT '删除时间',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) COMMENT='用户表';

DROP TABLE IF EXISTS `t_biz_leave`;
CREATE TABLE `t_biz_leave`
(
    `id`                  BIGINT NOT NULL COMMENT '主键ID (雪花算法生成)',
    `user_name`           VARCHAR(50) DEFAULT NULL COMMENT '申请人用户名',
    `duration`            INT         DEFAULT NULL COMMENT '请假时长 (天/小时)',
    `process_instance_id` VARCHAR(64) DEFAULT NULL COMMENT 'Activiti流程实例ID',

    /* BaseEntity 公共字段 */
    `revision`            INT         DEFAULT 1 COMMENT '乐观锁版本号',
    `created_by`          VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    `created_time`        DATETIME    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`          VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    `updated_time`        DATETIME    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 (0-未删除 1-已删除)',
    `deleted_by`          VARCHAR(50) DEFAULT NULL COMMENT '删除人',
    `deleted_time`        DATETIME    DEFAULT NULL COMMENT '删除时间',

    PRIMARY KEY (`id`),
    INDEX                 `idx_process_instance` (`process_instance_id`)
) COMMENT='请假申请表';