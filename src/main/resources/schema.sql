-- -----------------------------------------
-- Database Creation (Optional)
-- -----------------------------------------
CREATE DATABASE IF NOT EXISTS family_health DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE family_health;

-- -----------------------------------------
-- Table Structure for `family_member`
-- -----------------------------------------
DROP TABLE IF EXISTS `health_data`;
DROP TABLE IF EXISTS `medication`;
DROP TABLE IF EXISTS `health_record`;
DROP TABLE IF EXISTS `family_member`;

CREATE TABLE `family_member` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '成员唯一ID',
  `member_type` VARCHAR(20) NOT NULL COMMENT '成员类型 (例如: self, spouse, child, parent)',
  `member_name` VARCHAR(50) NOT NULL COMMENT '成员姓名或昵称',
  PRIMARY KEY (`id`),
  INDEX `idx_member_type` (`member_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='家庭成员表';

-- -----------------------------------------
-- Table Structure for `health_record`
-- -----------------------------------------
CREATE TABLE `health_record` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录唯一ID',
  `family_member_id` BIGINT NOT NULL COMMENT '关联的家庭成员ID',
  `record_date` DATE NOT NULL COMMENT '记录发生的日期',
  `record_type` VARCHAR(50) NULL COMMENT '记录类型 (例如: 症状描述, 就诊记录, 检查报告, 手术记录)',
  `title` VARCHAR(100) NOT NULL COMMENT '记录标题',
  `content` TEXT NULL COMMENT '记录详细内容',
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_family_member_id` (`family_member_id`),
  INDEX `idx_record_date` (`record_date`),
  FOREIGN KEY (`family_member_id`) REFERENCES `family_member`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康记录表';

-- -----------------------------------------
-- Table Structure for `medication`
-- -----------------------------------------
CREATE TABLE `medication` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用药记录唯一ID',
  `family_member_id` BIGINT NOT NULL COMMENT '关联的家庭成员ID',
  `medicine_name` VARCHAR(100) NOT NULL COMMENT '药品名称',
  `dosage` VARCHAR(50) NULL COMMENT '每次用量 (例如: 1片, 10mg)',
  `frequency` VARCHAR(100) NULL COMMENT '服用频率 (例如: 每日一次, 每日三次)',
  `remind_times` VARCHAR(255) NULL COMMENT '提醒时间点列表 (例如: 08:00,12:00,18:00)',
  `start_date` DATE NULL COMMENT '开始服用日期',
  `end_date` DATE NULL COMMENT '结束服用日期 (可为空)',
  `notes` VARCHAR(255) NULL COMMENT '备注信息 (例如: 饭后服用)',
  `status` TINYINT(1) DEFAULT 1 COMMENT '用药状态 (1: 正在使用, 0: 已停用)',
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_family_member_id` (`family_member_id`),
  FOREIGN KEY (`family_member_id`) REFERENCES `family_member`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用药及提醒表';

-- -----------------------------------------
-- Table Structure for `health_data`
-- -----------------------------------------
CREATE TABLE `health_data` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '健康数据唯一ID',
  `family_member_id` BIGINT NOT NULL COMMENT '关联的家庭成员ID',
  `data_type` VARCHAR(50) NOT NULL COMMENT '数据类型 (主要包括: blood_pressure, blood_sugar, weight)',
  `value` VARCHAR(50) NOT NULL COMMENT '记录的数值 (例如: 血压存"120/80", 血糖存"5.5", 体重存"68.5")',
  `unit` VARCHAR(20) NULL COMMENT '数值单位 (例如: mmHg, mmol/L, kg)',
  `record_time` DATETIME NOT NULL COMMENT '数据测量或记录的时间',
  `notes` VARCHAR(255) NULL COMMENT '备注信息',
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_family_member_id` (`family_member_id`),
  INDEX `idx_data_type` (`data_type`),
  INDEX `idx_record_time` (`record_time`),
  FOREIGN KEY (`family_member_id`) REFERENCES `family_member`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康数据记录表';


-- -----------------------------------------
-- Initial Data for `family_member`
-- -----------------------------------------
INSERT INTO `family_member` (`member_type`, `member_name`) VALUES
('self', '本人'),
('spouse', '配偶'),
('child', '孩子'),
('parent', '父母');

-- -----------------------------------------
-- Sample Data (Assuming '本人' has ID 1 after insertion)
-- -----------------------------------------
-- Sample Health Data for '本人'
INSERT INTO `health_data` (`family_member_id`, `data_type`, `value`, `unit`, `record_time`, `notes`) VALUES
(1, 'blood_pressure', '120/80', 'mmHg', NOW() - INTERVAL 1 DAY, '早上测量'),
(1, 'blood_sugar', '5.8', 'mmol/L', NOW() - INTERVAL 1 DAY, '餐后2小时'),
(1, 'weight', '70.5', 'kg', NOW() - INTERVAL 1 WEEK, '晨起空腹');

-- Sample Medication for '本人'
INSERT INTO `medication` (`family_member_id`, `medicine_name`, `dosage`, `frequency`, `remind_times`, `start_date`, `notes`, `status`) VALUES
(1, '维生素C片', '1片', '每日一次', '08:00', CURDATE() - INTERVAL 5 DAY, '早餐后服用', 1);

-- Sample Health Record for '本人'
INSERT INTO `health_record` (`family_member_id`, `record_date`, `record_type`, `title`, `content`) VALUES
(1, CURDATE() - INTERVAL 2 DAY, '症状描述', '轻微头痛', '昨天下午开始感觉轻微头痛，集中在额头部位。');

