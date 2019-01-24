-- --------------------------------------------------------
-- ホスト:                          127.0.0.1
-- サーバーのバージョン:                   5.7.21 - MySQL Community Server (GPL)
-- サーバー OS:                      Win64
-- HeidiSQL バージョン:               9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- hsys のデータベース構造をダンプしています
CREATE DATABASE IF NOT EXISTS `hsys` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `hsys`;

--  テーブル hsys.attendance_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `attendance_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_date` date DEFAULT NULL COMMENT '日期',
  `c_start` time DEFAULT NULL COMMENT '进入时间',
  `c_end` time DEFAULT NULL COMMENT '离开时间',
  `c_comment` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_user_id_c_date` (`c_user_id`,`c_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- テーブル hsys.attendance_tbl: ~93 rows (約) のデータをダンプしています
DELETE FROM `attendance_tbl`;
/*!40000 ALTER TABLE `attendance_tbl` DISABLE KEYS */;
INSERT INTO `attendance_tbl` (`c_id`, `c_user_id`, `c_date`, `c_start`, `c_end`, `c_comment`) VALUES
	(1, 12, '2016-03-21', '04:48:00', '18:37:09', '迟到 加班'),
	(2, 12, '2016-03-22', '09:03:38', '20:48:04', '迟到 加班'),
	(3, 12, '2016-03-23', '08:27:12', '20:59:30', 'null 加班'),
	(4, 12, '2016-03-24', '08:38:06', '21:08:56', '迟到 加班'),
	(5, 12, '2016-03-25', '08:32:42', '18:39:04', 'null 加班'),
	(6, 12, '2016-03-26', NULL, NULL, '休息 休息'),
	(7, 12, '2016-03-27', NULL, NULL, '休息 休息'),
	(8, 12, '2016-03-28', NULL, NULL, '旷工 旷工'),
	(9, 12, '2016-03-29', '08:10:36', '17:55:16', 'null null'),
	(10, 12, '2016-03-30', NULL, NULL, '旷工 旷工'),
	(11, 12, '2016-03-31', '08:29:54', '16:35:18', 'null 早退'),
	(12, 12, '2016-04-01', '08:34:08', '13:21:00', 'null 旷工'),
	(13, 12, '2016-04-02', NULL, NULL, '休息 休息'),
	(14, 12, '2016-04-03', NULL, NULL, '休息 休息'),
	(15, 12, '2016-04-04', NULL, NULL, '旷工 旷工'),
	(16, 12, '2016-04-05', '08:25:36', '18:45:26', 'null 加班'),
	(17, 12, '2016-04-06', '08:26:10', '12:12:54', 'null 旷工'),
	(18, 12, '2016-04-07', '08:26:02', '17:38:02', 'null null'),
	(19, 12, '2016-04-08', '08:35:36', '18:12:00', '迟到 null'),
	(20, 12, '2016-04-09', NULL, NULL, '休息 休息'),
	(21, 12, '2016-04-10', NULL, NULL, '休息 休息'),
	(22, 12, '2016-04-11', '08:31:58', '17:59:48', 'null null'),
	(23, 12, '2016-04-12', '08:11:20', '18:18:12', 'null null'),
	(24, 12, '2016-04-13', '08:32:44', '18:16:20', 'null null'),
	(25, 12, '2016-04-14', '08:30:34', '18:26:02', 'null null'),
	(26, 12, '2016-04-15', '08:35:42', '18:18:48', '迟到 null'),
	(27, 12, '2016-04-16', NULL, NULL, '休息 休息'),
	(28, 12, '2016-04-17', NULL, NULL, '休息 休息'),
	(29, 12, '2016-04-18', NULL, NULL, '旷工 旷工'),
	(30, 12, '2016-04-19', '08:25:32', '18:10:50', 'null null'),
	(31, 12, '2016-04-20', '08:31:56', '19:21:10', 'null 加班'),
	(32, 13, '2016-03-21', '08:47:02', '18:00:54', '迟到 null'),
	(33, 13, '2016-03-22', '08:42:48', '18:05:16', '迟到 null'),
	(34, 13, '2016-03-23', '08:39:48', '17:58:08', '迟到 null'),
	(35, 13, '2016-03-24', '08:21:46', '17:59:30', 'null null'),
	(36, 13, '2016-03-25', '08:40:00', '17:55:56', '迟到 null'),
	(37, 13, '2016-03-26', '08:44:54', '15:31:16', '加班 加班'),
	(38, 13, '2016-03-27', NULL, NULL, '休息 休息'),
	(39, 13, '2016-03-28', '08:29:38', '18:01:48', 'null null'),
	(40, 13, '2016-03-29', '08:23:14', '20:37:38', 'null 加班'),
	(41, 13, '2016-03-30', '08:28:06', '20:35:08', 'null 加班'),
	(42, 13, '2016-03-31', '08:27:58', '08:30:24', 'null 旷工'),
	(43, 13, '2016-04-01', '08:28:38', '18:16:56', 'null null'),
	(44, 13, '2016-04-02', NULL, NULL, '休息 休息'),
	(45, 13, '2016-04-03', NULL, NULL, '休息 休息'),
	(46, 13, '2016-04-04', NULL, NULL, '旷工 旷工'),
	(47, 13, '2016-04-05', '08:33:10', '18:08:54', 'null null'),
	(48, 13, '2016-04-06', '08:48:48', '18:12:28', '迟到 null'),
	(49, 13, '2016-04-07', '08:40:00', '18:16:10', '迟到 null'),
	(50, 13, '2016-04-08', '08:27:26', '18:19:44', 'null null'),
	(51, 13, '2016-04-09', NULL, NULL, '休息 休息'),
	(52, 13, '2016-04-10', '09:11:22', '17:52:18', '加班 加班'),
	(53, 13, '2016-04-11', '08:32:30', '18:05:08', 'null null'),
	(54, 13, '2016-04-12', '08:49:56', '18:16:04', '迟到 null'),
	(55, 13, '2016-04-13', '08:39:18', '18:14:26', '迟到 null'),
	(56, 13, '2016-04-14', '08:42:24', '18:10:00', '迟到 null'),
	(57, 13, '2016-04-15', '08:28:58', '17:51:40', 'null null'),
	(58, 13, '2016-04-16', NULL, NULL, '休息 休息'),
	(59, 13, '2016-04-17', NULL, NULL, '休息 休息'),
	(60, 13, '2016-04-18', '08:40:16', '18:10:06', '迟到 null'),
	(61, 13, '2016-04-19', '08:34:44', '18:10:32', 'null null'),
	(62, 13, '2016-04-20', '08:37:52', '18:04:52', '迟到 null'),
	(63, 14, '2016-03-21', '08:58:50', '18:00:36', '迟到 null'),
	(64, 14, '2016-03-22', '08:57:40', '18:04:10', '迟到 null'),
	(65, 14, '2016-03-23', '08:57:58', '17:58:18', '迟到 null'),
	(66, 14, '2016-03-24', '08:54:46', '17:59:24', '迟到 null'),
	(67, 14, '2016-03-25', '08:59:10', '17:57:00', '迟到 null'),
	(68, 14, '2016-03-26', NULL, NULL, '休息 休息'),
	(69, 14, '2016-03-27', NULL, NULL, '休息 休息'),
	(70, 14, '2016-03-28', '08:54:38', '18:01:42', '迟到 null'),
	(71, 14, '2016-03-29', '08:52:16', '20:37:16', '迟到 加班'),
	(72, 14, '2016-03-30', '08:56:20', '18:04:20', '迟到 null'),
	(73, 14, '2016-03-31', '08:54:54', '18:13:02', '迟到 null'),
	(74, 14, '2016-04-01', '09:01:04', '18:16:50', '迟到 null'),
	(75, 14, '2016-04-02', NULL, NULL, '休息 休息'),
	(76, 14, '2016-04-03', NULL, NULL, '休息 休息'),
	(77, 14, '2016-04-04', NULL, NULL, '旷工 旷工'),
	(78, 14, '2016-04-05', '08:56:50', '18:09:10', '迟到 null'),
	(79, 14, '2016-04-06', '08:58:56', '18:12:32', '迟到 null'),
	(80, 14, '2016-04-07', '08:59:36', '18:16:04', '迟到 null'),
	(81, 14, '2016-04-08', '08:54:18', '18:19:42', '迟到 null'),
	(82, 14, '2016-04-09', NULL, NULL, '休息 休息'),
	(83, 14, '2016-04-10', NULL, NULL, '休息 休息'),
	(84, 14, '2016-04-11', '08:52:54', '18:06:20', '迟到 null'),
	(85, 14, '2016-04-12', '08:56:38', '18:25:42', '迟到 null'),
	(86, 14, '2016-04-13', '08:53:20', '18:14:20', '迟到 null'),
	(87, 14, '2016-04-14', '08:57:06', '18:09:54', '迟到 null'),
	(88, 14, '2016-04-15', NULL, NULL, '旷工 旷工'),
	(89, 14, '2016-04-16', NULL, NULL, '休息 休息'),
	(90, 14, '2016-04-17', NULL, NULL, '休息 休息'),
	(91, 14, '2016-04-18', '08:56:50', '18:09:50', '迟到 null'),
	(92, 14, '2016-04-19', '08:53:12', NULL, '迟到 未刷卡'),
	(93, 14, '2016-04-20', '08:50:26', '18:04:38', '迟到 null');
/*!40000 ALTER TABLE `attendance_tbl` ENABLE KEYS */;

--  テーブル hsys.device_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `device_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(7) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_comment` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '说明',
  `c_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '使用者ID',
  `c_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 正常(0) 损坏(1) 交还(2)',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='设备表';

-- テーブル hsys.device_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `device_tbl`;
/*!40000 ALTER TABLE `device_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_tbl` ENABLE KEYS */;

--  テーブル hsys.expense_item_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `expense_item_tbl` (
  `c_id` int(11) NOT NULL COMMENT 'ID',
  `c_date` date NOT NULL COMMENT '报销日期',
  `c_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '报销人',
  `c_payee_id` int(11) NOT NULL DEFAULT '0' COMMENT '领款人',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数额',
  `c_comment` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '备注',
  `c_type` int(1) NOT NULL DEFAULT '0' COMMENT '种类 加班(0) 办公用品(1) 小组活动(2) 住宿费(3) 交通费(4) 电脑设备(5) 其他(6)',
  `c_receipt_id` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='报销明细';

-- テーブル hsys.expense_item_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `expense_item_tbl`;
/*!40000 ALTER TABLE `expense_item_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_item_tbl` ENABLE KEYS */;

--  テーブル hsys.expense_receipt_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `expense_receipt_tbl` (
  `c_id` int(11) NOT NULL COMMENT 'ID',
  `c_no` varchar(12) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_submit_date` date NOT NULL COMMENT '提交日期',
  `c_type` int(1) NOT NULL DEFAULT '0' COMMENT '种类 用餐(0) 出租车(1) 支付核销(2)',
  `c_pay_date` date NOT NULL COMMENT '领款日',
  `c_payee_id` int(11) NOT NULL COMMENT '经办领款人',
  `c_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 填单(0) 提交(1) 领款(2)',
  `c_comment` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='报销单';

-- テーブル hsys.expense_receipt_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `expense_receipt_tbl`;
/*!40000 ALTER TABLE `expense_receipt_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_receipt_tbl` ENABLE KEYS */;

--  テーブル hsys.extra_time_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `extra_time_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_date` date NOT NULL COMMENT '日期',
  `c_start` time NOT NULL COMMENT '开始时间',
  `c_end` time NOT NULL COMMENT '结束时间',
  `c_type` int(1) NOT NULL DEFAULT '0' COMMENT '种类 平时(0) 周末(1) 节假(2)',
  `c_len` float NOT NULL DEFAULT '0' COMMENT '时长',
  `c_sumary` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '备注',
  `c_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '加班用户ID',
  `c_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 登录中(0) 批准(1)',
  `c_meal_lunch` int(1) NOT NULL DEFAULT '0' COMMENT '午餐',
  `c_meal_supper` int(1) NOT NULL DEFAULT '0' COMMENT '晚餐',
  `c_approval_user_id` int(11) DEFAULT NULL COMMENT '批准者',
  `c_approval_time` datetime DEFAULT NULL COMMENT '批准日',
  `c_expense_item_id` int(11) NOT NULL DEFAULT '0' COMMENT '报销明细ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='加班时间表';

-- テーブル hsys.extra_time_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `extra_time_tbl`;
/*!40000 ALTER TABLE `extra_time_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `extra_time_tbl` ENABLE KEYS */;

--  テーブル hsys.group_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `group_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(24) COLLATE utf8_bin NOT NULL COMMENT '组名',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='分组表';

-- テーブル hsys.group_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `group_tbl`;
/*!40000 ALTER TABLE `group_tbl` DISABLE KEYS */;
INSERT INTO `group_tbl` (`c_id`, `c_name`) VALUES
	(1, '1111'),
	(2, '2222');
/*!40000 ALTER TABLE `group_tbl` ENABLE KEYS */;

--  テーブル hsys.qm_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `qm_tbl` (
  `c_id` int(11) DEFAULT NULL COMMENT 'ID',
  `c_code` char(6) COLLATE utf8_bin DEFAULT NULL COMMENT '代码',
  `c_name` char(8) COLLATE utf8_bin DEFAULT NULL COMMENT '名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='级别表';

-- テーブル hsys.qm_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `qm_tbl`;
/*!40000 ALTER TABLE `qm_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `qm_tbl` ENABLE KEYS */;

--  テーブル hsys.rest_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `rest_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_date` date NOT NULL COMMENT '日期',
  `c_start` time NOT NULL COMMENT '开始',
  `c_end` time NOT NULL COMMENT '结束',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '种类 休(0) 病(1) 事(2) 婚(3) 丧(4) 公(5)',
  `c_sumary` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '备注',
  `c_user_id` int(11) NOT NULL COMMENT '请假人',
  `c_status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 登录中(0) 批准(1)',
  `c_approval_user_id` int(11) DEFAULT NULL COMMENT '批准者',
  `c_approval_time` datetime DEFAULT NULL COMMENT '批准日期',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='休假表';

-- テーブル hsys.rest_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `rest_tbl`;
/*!40000 ALTER TABLE `rest_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `rest_tbl` ENABLE KEYS */;

--  テーブル hsys.user_group_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_group_tbl` (
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_group_id` int(11) NOT NULL COMMENT '组ID',
  `c_is_admin` int(1) NOT NULL DEFAULT '0' COMMENT '是否该组Leader 是(1) 否(0)',
  KEY `c_user_id` (`c_user_id`,`c_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户所在分组表';

-- テーブル hsys.user_group_tbl: ~6 rows (約) のデータをダンプしています
DELETE FROM `user_group_tbl`;
/*!40000 ALTER TABLE `user_group_tbl` DISABLE KEYS */;
INSERT INTO `user_group_tbl` (`c_user_id`, `c_group_id`, `c_is_admin`) VALUES
	(1, 1, 0),
	(2, 1, 0),
	(3, 2, 0),
	(11, 2, 0),
	(4, 2, 0),
	(7, 2, 0);
/*!40000 ALTER TABLE `user_group_tbl` ENABLE KEYS */;

--  テーブル hsys.user_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '工号',
  `c_name` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '名字',
  `c_phone_number` varchar(14) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `c_sex` int(11) DEFAULT NULL COMMENT '性别',
  `c_password` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `c_mail` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '邮件',
  `c_place` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '工作地点',
  `c_qm_id` int(11) DEFAULT NULL COMMENT 'QM ID',
  `c_address` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '家庭地址',
  `c_id_number` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `c_school` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '毕业学校',
  `c_major` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '主修专业',
  `c_degree` int(11) DEFAULT NULL COMMENT '学历级别 大专(0) 本科1(1) 本科2(2) 本科3(3) 硕士(4) 博士(5)',
  `c_graduate_date` date DEFAULT NULL COMMENT '毕业日期',
  `c_enter_date` date DEFAULT NULL COMMENT '入社日期',
  `c_exit_date` date DEFAULT NULL COMMENT '离职日期',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- テーブル hsys.user_tbl: ~14 rows (約) のデータをダンプしています
DELETE FROM `user_tbl`;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` (`c_id`, `c_no`, `c_name`, `c_phone_number`, `c_sex`, `c_password`, `c_mail`, `c_place`, `c_qm_id`, `c_address`, `c_id_number`, `c_school`, `c_major`, `c_degree`, `c_graduate_date`, `c_enter_date`, `c_exit_date`) VALUES
	(0, '2-', '3', NULL, 0, '123', '44', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(2, 'demo', '663', NULL, 0, NULL, '7', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(3, '5', '欧阳震华13', NULL, 0, '123', '7', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(4, '6', '7', NULL, 0, NULL, '8', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(5, 'demo', 'ZZZ', NULL, 0, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(6, 's', 'ss', NULL, 0, '123', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(7, 'sss', '22', NULL, 0, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(8, 'demo', 'ZZZ', NULL, 0, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(9, 'demo', 'ZZZ', NULL, 0, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(10, 'demo', 'ZZZ', NULL, 0, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(11, '51', '4sss', NULL, 1, NULL, 'wwww', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(12, 'NT010', 'aaa', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(13, 'NT011', 'NT011', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(14, 'NT012', 'NT012', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
