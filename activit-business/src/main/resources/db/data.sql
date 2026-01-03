-- 清空旧数据（可选，根据你的 DatabaseInitializer 逻辑决定）
SET
FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE `t_user`;
TRUNCATE TABLE `t_biz_leave`;
SET
FOREIGN_KEY_CHECKS = 1;

-- 1. 插入初始化用户 (ID 模拟雪花算法生成的长整数)
INSERT INTO `t_user` (`id`, `username`, `password`, `name`, `age`, `email`, `created_by`, `created_time`)
VALUES (1610203040506070101, 'admin', '123456', '系统管理员', 30, 'admin@example.com', 'system', NOW()),
       (1610203040506070102, 'zhangsan', '123456', '张三', 25, 'zhangsan@example.com', 'system', NOW()),
       (1610203040506070103, 'lisi', '123456', '李四', 28, 'lisi@example.com', 'system', NOW());

-- 2. 插入初始化请假单
-- 场景：一条已关联流程，一条未关联流程
INSERT INTO `t_biz_leave` (`id`, `user_name`, `duration`, `process_instance_id`, `created_by`, `created_time`)
VALUES (1620203040506070201, 'zhangsan', 3, '5001', 'zhangsan', NOW()),
       (1620203040506070202, 'lisi', 1, NULL, 'lisi', NOW());