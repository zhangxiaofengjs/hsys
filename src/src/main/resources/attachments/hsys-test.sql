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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='出勤时间表';

-- テーブル hsys.attendance_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `attendance_tbl`;
/*!40000 ALTER TABLE `attendance_tbl` DISABLE KEYS */;
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
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_date` date NOT NULL COMMENT '报销日期',
  `c_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '报销人',
  `c_payee_id` int(11) NOT NULL DEFAULT '0' COMMENT '领款人',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数额',
  `c_comment` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '备注',
  `c_type` tinyint(11) NOT NULL DEFAULT '0' COMMENT '种类 加班(0) 办公用品(1) 小组活动(2) 住宿费(3) 交通费(4) 电脑设备(5) 其他(6)',
  `c_receipt_id` int(11) DEFAULT '0' COMMENT '报销单ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='报销明细';

-- テーブル hsys.expense_item_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `expense_item_tbl`;
/*!40000 ALTER TABLE `expense_item_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_item_tbl` ENABLE KEYS */;

--  テーブル hsys.expense_receipt_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `expense_receipt_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_submit_date` date DEFAULT NULL COMMENT '提交日期',
  `c_type` int(1) NOT NULL DEFAULT '0' COMMENT '种类 用餐(0) 出租车(1) 支付核销(2)',
  `c_pay_date` date DEFAULT NULL COMMENT '领款日',
  `c_payee_id` int(11) DEFAULT NULL COMMENT '经办领款人',
  `c_status` tinyint(11) NOT NULL DEFAULT '0' COMMENT '状态 填单中(0) 待审核(1) 财务处理中(2) 已领款(3)',
  `c_comment` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `c_project_id` int(11) DEFAULT NULL COMMENT '报销项目ID',
  `c_attachment_path` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '附件路径',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='报销单';

-- テーブル hsys.expense_receipt_tbl: ~1 rows (約) のデータをダンプしています
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
  `c_comment` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '备注',
  `c_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '加班用户ID',
  `c_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 登録中(0) 批准(1)',
  `c_meal_lunch` int(1) NOT NULL DEFAULT '0' COMMENT '午餐',
  `c_meal_supper` int(1) NOT NULL DEFAULT '0' COMMENT '晚餐',
  `c_approval_user_id` int(11) DEFAULT NULL COMMENT '批准者',
  `c_approval_time` datetime DEFAULT NULL COMMENT '批准日',
  `c_expense_item_id` int(11) DEFAULT '0' COMMENT '报销明细ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='加班时间表';

-- テーブル hsys.extra_time_tbl: ~54 rows (約) のデータをダンプしています
DELETE FROM `extra_time_tbl`;
/*!40000 ALTER TABLE `extra_time_tbl` DISABLE KEYS */;
INSERT INTO `extra_time_tbl` (`c_id`, `c_date`, `c_start`, `c_end`, `c_type`, `c_len`, `c_comment`, `c_user_id`, `c_status`, `c_meal_lunch`, `c_meal_supper`, `c_approval_user_id`, `c_approval_time`, `c_expense_item_id`) VALUES
	(2, '2019-03-27', '18:00:00', '21:00:00', 1, 3, '开发用PC333', 1, 0, 1, 1, NULL, NULL, 0),
	(3, '2019-03-27', '18:00:00', '21:00:00', 2, 3, '222w', 1, 0, 1, 1, NULL, NULL, 0);
/*!40000 ALTER TABLE `extra_time_tbl` ENABLE KEYS */;

--  テーブル hsys.group_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `group_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(24) COLLATE utf8_bin NOT NULL COMMENT '组名',
  `c_parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父组ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='分组表';

-- テーブル hsys.group_tbl: ~7 rows (約) のデータをダンプしています
DELETE FROM `group_tbl`;
/*!40000 ALTER TABLE `group_tbl` DISABLE KEYS */;
INSERT INTO `group_tbl` (`c_id`, `c_name`, `c_parent_id`) VALUES
	(1, '5本部', 0),
	(2, '研发3部', 1),
	(3, '图研组', 2),
	(4, 'NTTD组', 2),
	(5, 'E3Cus小组', 3),
	(6, 'E3Plugin小组', 3),
	(7, 'PLM小组', 3),
	(8, 'EP小组', 4),
	(9, 'EC小组', 4),
	(10, '管理本部', 0),
	(11, '人力资源部', 10);
/*!40000 ALTER TABLE `group_tbl` ENABLE KEYS */;

--  テーブル hsys.holiday_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `holiday_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_date` date NOT NULL COMMENT '日期',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '正常休息(0) 出勤(1) 调休(2)',
  `c_comment` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '备注',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公休假表';

-- テーブル hsys.holiday_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `holiday_tbl`;
/*!40000 ALTER TABLE `holiday_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `holiday_tbl` ENABLE KEYS */;

--  テーブル hsys.project_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `project_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` char(50) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '项目名',
  `c_funds` float NOT NULL COMMENT '经费',
  `c_pm_id` int(11) NOT NULL COMMENT '项目经理ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目管理表';

-- テーブル hsys.project_tbl: ~1 rows (約) のデータをダンプしています
DELETE FROM `project_tbl`;
/*!40000 ALTER TABLE `project_tbl` DISABLE KEYS */;
INSERT INTO `project_tbl` (`c_id`, `c_no`, `c_name`, `c_funds`, `c_pm_id`) VALUES
	(1, '18-D3-002', 'ZUKEN/PLM离岸开发', 10000, 0);
/*!40000 ALTER TABLE `project_tbl` ENABLE KEYS */;

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
  `c_date_start` datetime NOT NULL COMMENT '开始',
  `c_date_end` datetime NOT NULL COMMENT '结束',
  `c_len` float NOT NULL COMMENT '日期',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '种类 休(0) 病(1) 事(2) 婚(3) 丧(4) 公(5)',
  `c_summary` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '备注',
  `c_user_id` int(11) NOT NULL COMMENT '请假人',
  `c_status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 登录中(0) 批准(1) 驳回(2) 撤销请求(3) 撤销(4)',
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

-- テーブル hsys.user_group_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `user_group_tbl`;
/*!40000 ALTER TABLE `user_group_tbl` DISABLE KEYS */;
INSERT INTO `user_group_tbl` (`c_user_id`, `c_group_id`, `c_is_admin`) VALUES
	(1, 4, 0),
	(8, 4, 0),
	(7, 3, 0),
	(9, 11, 0),
	(10, 11, 0),
	(2, 4, 0),
	(3, 4, 0),
	(5, 3, 0),
	(6, 2, 0),
	(11, 10, 0);
/*!40000 ALTER TABLE `user_group_tbl` ENABLE KEYS */;

--  テーブル hsys.user_role_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_role_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_role` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '角色名',
  `c_enable` int(11) NOT NULL DEFAULT '0' COMMENT '无效(0) 有效(1)',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_user_id_c_role` (`c_user_id`,`c_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色表';

-- テーブル hsys.user_role_tbl: ~12 rows (約) のデータをダンプしています
DELETE FROM `user_role_tbl`;
/*!40000 ALTER TABLE `user_role_tbl` DISABLE KEYS */;
INSERT INTO `user_role_tbl` (`c_id`, `c_user_id`, `c_role`, `c_enable`) VALUES
	(1, 1, 'admin', 1),
	(2, 1, 'user_edit', 1),
	(3, 1, 'rest_approve', 1),
	(4, 1, 'rest_list', 1),
	(5, 1, 'attendance_list', 1),
	(6, 1, 'extratime_list', 1),
	(16, 1, 'rest_list_all', 1),
	(17, 1, 'attendance_list_all', 1),
	(18, 1, 'attendance_upload', 1),
	(19, 1, 'extratime_list_all', 1),
	(20, 1, 'device_edit', 1),
	(21, 1, 'extratime_approve', 1),
	(22, 7, 'extratime_approve', 1),
	(23, 7, 'extratime_list', 1),
	(24, 7, 'rest_approve', 1),
	(25, 7, 'rest_list', 1),
	(26, 7, 'attendance_list', 1),
	(27, 8, 'extratime_approve', 1),
	(28, 8, 'extratime_list', 1),
	(29, 8, 'rest_approve', 1),
	(30, 8, 'rest_list', 1),
	(31, 8, 'attendance_list', 1),
	(32, 9, 'user_edit', 1),
	(33, 9, 'extratime_list_all', 0),
	(34, 9, 'rest_list_all', 0),
	(35, 9, 'attendance_list_all', 0),
	(36, 6, 'extratime_approve', 1),
	(37, 6, 'extratime_list_all', 1),
	(38, 6, 'rest_approve', 1),
	(39, 6, 'rest_list_all', 1),
	(40, 6, 'attendance_list_all', 1),
	(41, 10, 'user_edit', 1),
	(42, 10, 'extratime_list_all', 1),
	(43, 10, 'rest_list_all', 1),
	(44, 10, 'attendance_upload', 1),
	(45, 10, 'attendance_list_all', 1),
	(46, 11, 'device_edit', 1);
/*!40000 ALTER TABLE `user_role_tbl` ENABLE KEYS */;

--  テーブル hsys.user_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '工号',
  `c_name` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '名字',
  `c_phone_number` varchar(14) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `c_sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别',
  `c_password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `c_mail` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '邮件',
  `c_place` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '工作地点',
  `c_company_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名字(分公司名字)',
  `c_qm_id` int(11) DEFAULT NULL COMMENT 'QM ID',
  `c_address` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '家庭地址',
  `c_id_number` char(20) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `c_school` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '毕业学校',
  `c_major` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '主修专业',
  `c_degree` int(11) DEFAULT NULL COMMENT '学历级别 大专(0) 本科3(1) 本科2(2) 本科1(3) 硕士(4) 博士(5)',
  `c_graduate_date` date DEFAULT NULL COMMENT '毕业日期',
  `c_enter_date` date DEFAULT NULL COMMENT '入社日期',
  `c_exit_date` date DEFAULT NULL COMMENT '离职日期',
  PRIMARY KEY (`c_id`),
  KEY `c_no` (`c_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- テーブル hsys.user_tbl: ~1 rows (約) のデータをダンプしています
DELETE FROM `user_tbl`;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` (`c_id`, `c_no`, `c_name`, `c_phone_number`, `c_sex`, `c_password`, `c_mail`, `c_place`, `c_company_name`, `c_qm_id`, `c_address`, `c_id_number`, `c_school`, `c_major`, `c_degree`, `c_graduate_date`, `c_enter_date`, `c_exit_date`) VALUES
	(1, 'admin', '管理员', '13678998121458', 0, '$2a$10$yggjrP1Vte9RKgbHQ1SQs.HBJ2NKGzZIUCRASqV4E7gIPM.CJr6s.', '@asd', '123', 'hyron', NULL, 'asdadadasda', 'asdasdasd', '南京林业大学', '数学', 2, '2019-02-07', '2019-02-27', NULL),
	(2, '001', '普通员工1', NULL, 0, '$2a$10$93etRoOhl4CdApkXXvkiDe2e5CAdPOI/gcb9QqmtpeN3T2AaXegjO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(3, '002', '普通员工2', NULL, 1, '$2a$10$vFlXgk603/o7fIPHF9dWW.g0IXJxUaP8Cco0OXdWD9YA3RkACckaO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(4, '003', '离职员工', NULL, 0, '$2a$10$6Em9ihhpsd5hkg1vfl4jMOBoO95sWi4dF5yWzCbF08/NQHOcGwOJu', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2019-02-13'),
	(5, '004', '普通员工4', NULL, 0, '$2a$10$8Xsa/pu2uWa3Hf14YZ2o0ObYeLtGwpj2lJwVNsVQKo6B9/lMFSHRC', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(6, '010', '部长', NULL, 0, '$2a$10$FX4EX2c7pGZVPIlZuy9YPex5RY/9pY1kjuUtyS23eNzVMHf/DIQA6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(7, '005', 'PM', NULL, 0, '$2a$10$srF.6SHv/IXCtbOiQrv.TugHbcATdPjWe0t7lS8.bdwlGX7Em/AXC', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(8, '006', 'PM2', NULL, 0, '$2a$10$1US86SE1Q43qZHm3z8sehuUUJKgCwku.YxqRd3EIqU8J63SVOvkZC', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(9, '007', 'HR', NULL, 0, '$2a$10$ariIkUkiot4MpkZK74aXW.EwjQI9S1.YDPZodyQ7vKrC70g0db4uG', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(10, '008', '出勤管理秘书', NULL, 0, '$2a$10$WfQ/tntgwm1N9Pm6njx4wO5icqfcYJOhnthzT5PrJ9p2BQ6D1P1AO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(11, '009', '设备科管理员', NULL, 0, '$2a$10$petuNGphSpM.FyCmW/guRu.xmXkspgs6ZfEQKxCxJr5HtTqb1E.NO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL);
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
