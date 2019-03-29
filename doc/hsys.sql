-- --------------------------------------------------------
-- ホスト:                          127.0.0.1
-- サーバーのバージョン:                   8.0.13 - MySQL Community Server - GPL
-- サーバー OS:                      Win64
-- HeidiSQL バージョン:               9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- hsys のデータベース構造をダンプしています
DROP DATABASE IF EXISTS `hsys`;
CREATE DATABASE IF NOT EXISTS `hsys` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `hsys`;

--  テーブル hsys.attendance_tbl の構造をダンプしています
DROP TABLE IF EXISTS `attendance_tbl`;
CREATE TABLE IF NOT EXISTS `attendance_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_date` date DEFAULT NULL COMMENT '日期',
  `c_start` time DEFAULT NULL COMMENT '进入时间',
  `c_end` time DEFAULT NULL COMMENT '离开时间',
  `c_comment` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_user_id_c_date` (`c_user_id`,`c_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='出勤时间表';

-- テーブル hsys.attendance_tbl: ~217 rows (約) のデータをダンプしています
DELETE FROM `attendance_tbl`;
/*!40000 ALTER TABLE `attendance_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance_tbl` ENABLE KEYS */;

--  テーブル hsys.company_tbl の構造をダンプしています
DROP TABLE IF EXISTS `company_tbl`;
CREATE TABLE IF NOT EXISTS `company_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
  `c_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `c_phone_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公司列表';

-- テーブル hsys.company_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `company_tbl`;
/*!40000 ALTER TABLE `company_tbl` DISABLE KEYS */;
INSERT INTO `company_tbl` (`c_id`, `c_name`, `c_address`, `c_phone_number`) VALUES
	(1, '海隆软件（南通）', NULL, NULL),
	(2, '海隆软件（南京）', NULL, NULL),
	(3, '海隆软件（昆山）', NULL, NULL),
	(4, '海隆软件（北京）', NULL, NULL);
/*!40000 ALTER TABLE `company_tbl` ENABLE KEYS */;

--  テーブル hsys.device_tbl の構造をダンプしています
DROP TABLE IF EXISTS `device_tbl`;
CREATE TABLE IF NOT EXISTS `device_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(7) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_comment` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '说明',
  `c_user_id` int(11) DEFAULT '0' COMMENT '使用者ID',
  `c_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 正常(0) 损坏(1) 交还(2)',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='设备表';

-- テーブル hsys.device_tbl: ~5 rows (約) のデータをダンプしています
DELETE FROM `device_tbl`;
/*!40000 ALTER TABLE `device_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_tbl` ENABLE KEYS */;

--  テーブル hsys.expense_item_tbl の構造をダンプしています
DROP TABLE IF EXISTS `expense_item_tbl`;
CREATE TABLE IF NOT EXISTS `expense_item_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_date` date NOT NULL COMMENT '报销日期',
  `c_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '报销人',
  `c_payee_id` int(11) NOT NULL DEFAULT '0' COMMENT '领款人',
  `c_num` float NOT NULL DEFAULT '0' COMMENT '数额',
  `c_comment` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '备注',
  `c_type` tinyint(11) NOT NULL DEFAULT '0' COMMENT '种类 加班(0) 办公用品(1) 小组活动(2) 住宿费(3) 交通费(4) 电脑设备(5) 其他(6)',
  `c_receipt_id` int(11) DEFAULT '0' COMMENT '报销单ID',
  `c_status` int(11) NOT NULL DEFAULT '0' COMMENT '是否已经领款确认 (0)未收到 (1)已收到',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='报销明细';

-- テーブル hsys.expense_item_tbl: ~13 rows (約) のデータをダンプしています
DELETE FROM `expense_item_tbl`;
/*!40000 ALTER TABLE `expense_item_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_item_tbl` ENABLE KEYS */;

--  テーブル hsys.expense_receipt_tbl の構造をダンプしています
DROP TABLE IF EXISTS `expense_receipt_tbl`;
CREATE TABLE IF NOT EXISTS `expense_receipt_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_submit_date` date DEFAULT NULL COMMENT '提交日期',
  `c_type` int(1) NOT NULL DEFAULT '0' COMMENT '种类 用餐(0) 出租车(1) 支付核销(2)',
  `c_pay_date` date DEFAULT NULL COMMENT '领款日',
  `c_payee_id` int(11) DEFAULT NULL COMMENT '经办领款人',
  `c_status` tinyint(11) NOT NULL DEFAULT '0' COMMENT '状态 填单中(0) 待审核(1) 财务处理中(2) 已领款(3) 驳回(4)',
  `c_comment` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `c_project_id` int(11) DEFAULT NULL COMMENT '报销项目ID',
  `c_attachment_path` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '附件路径',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='报销单';

-- テーブル hsys.expense_receipt_tbl: ~12 rows (約) のデータをダンプしています
DELETE FROM `expense_receipt_tbl`;
/*!40000 ALTER TABLE `expense_receipt_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_receipt_tbl` ENABLE KEYS */;

--  テーブル hsys.extra_time_tbl の構造をダンプしています
DROP TABLE IF EXISTS `extra_time_tbl`;
CREATE TABLE IF NOT EXISTS `extra_time_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_date` date NOT NULL COMMENT '日期',
  `c_start` time NOT NULL COMMENT '开始时间',
  `c_end` time NOT NULL COMMENT '结束时间',
  `c_type` int(1) NOT NULL DEFAULT '0' COMMENT '种类 平时(0) 周末(1) 节假(2)',
  `c_len` float NOT NULL DEFAULT '0' COMMENT '时长',
  `c_comment` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '备注',
  `c_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '加班用户ID',
  `c_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 登録中(0) 批准(1)',
  `c_meal_lunch` int(1) NOT NULL DEFAULT '0' COMMENT '午餐',
  `c_meal_supper` int(1) NOT NULL DEFAULT '0' COMMENT '晚餐',
  `c_approval_user_id` int(11) DEFAULT NULL COMMENT '批准者',
  `c_approval_time` datetime DEFAULT NULL COMMENT '批准日',
  `c_expense_item_id` int(11) DEFAULT '0' COMMENT '报销明细ID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='加班时间表';

-- テーブル hsys.extra_time_tbl: ~55 rows (約) のデータをダンプしています
DELETE FROM `extra_time_tbl`;
/*!40000 ALTER TABLE `extra_time_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `extra_time_tbl` ENABLE KEYS */;

--  テーブル hsys.group_tbl の構造をダンプしています
DROP TABLE IF EXISTS `group_tbl`;
CREATE TABLE IF NOT EXISTS `group_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(24) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '组名',
  `c_parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父组ID',
  `c_level` int(11) NOT NULL DEFAULT '0' COMMENT '同一组织级别，越小级别越大',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='分组表';

-- テーブル hsys.group_tbl: ~11 rows (約) のデータをダンプしています
DELETE FROM `group_tbl`;
/*!40000 ALTER TABLE `group_tbl` DISABLE KEYS */;
INSERT INTO `group_tbl` (`c_id`, `c_name`, `c_parent_id`, `c_level`) VALUES
	(1, '5本部', 0, 0),
	(2, '3部', 1, 1),
	(3, '图研组', 2, 2),
	(4, 'NTTD组', 2, 2),
	(5, 'E3Cus小组', 3, 2),
	(6, 'ECX小组', 3, 2),
	(7, 'PLM小组', 3, 2),
	(8, '管理本部', 0, 1);
/*!40000 ALTER TABLE `group_tbl` ENABLE KEYS */;

--  テーブル hsys.holiday_tbl の構造をダンプしています
DROP TABLE IF EXISTS `holiday_tbl`;
CREATE TABLE IF NOT EXISTS `holiday_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_date` date NOT NULL COMMENT '日期',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '正常休息(0) 调休(1) 出勤(2) ',
  `c_comment` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '备注',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公休假表';

-- テーブル hsys.holiday_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `holiday_tbl`;
/*!40000 ALTER TABLE `holiday_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `holiday_tbl` ENABLE KEYS */;

--  テーブル hsys.project_leader_tbl の構造をダンプしています
DROP TABLE IF EXISTS `project_leader_tbl`;
CREATE TABLE IF NOT EXISTS `project_leader_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_project_id` int(11) NOT NULL COMMENT '项目ID',
  `c_leader_id` int(11) NOT NULL COMMENT 'LeaderID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目和Leader关系表';

-- テーブル hsys.project_leader_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `project_leader_tbl`;
/*!40000 ALTER TABLE `project_leader_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_leader_tbl` ENABLE KEYS */;

--  テーブル hsys.project_tbl の構造をダンプしています
DROP TABLE IF EXISTS `project_tbl`;
CREATE TABLE IF NOT EXISTS `project_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '项目名',
  `c_funds` float NOT NULL COMMENT '经费',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目管理表';

-- テーブル hsys.project_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `project_tbl`;
/*!40000 ALTER TABLE `project_tbl` DISABLE KEYS */;
INSERT INTO `project_tbl` (`c_id`, `c_no`, `c_name`, `c_funds`) VALUES
	(1, '18-D3-002', 'ZUKEN/PLM离岸开发', 10000),
	(2, '18-D3-001', 'ZUKEN/A&M离岸开发', 10000);
/*!40000 ALTER TABLE `project_tbl` ENABLE KEYS */;

--  テーブル hsys.province_tbl の構造をダンプしています
DROP TABLE IF EXISTS `province_tbl`;
CREATE TABLE IF NOT EXISTS `province_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='全国省表';

-- テーブル hsys.province_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `province_tbl`;
/*!40000 ALTER TABLE `province_tbl` DISABLE KEYS */;
INSERT INTO `province_tbl` (`c_id`, `c_name`) VALUES
	(1, '江苏'),
	(2, '上海'),
	(3, '重庆'),
	(4, '湖南');
/*!40000 ALTER TABLE `province_tbl` ENABLE KEYS */;

--  テーブル hsys.qm_tbl の構造をダンプしています
DROP TABLE IF EXISTS `qm_tbl`;
CREATE TABLE IF NOT EXISTS `qm_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_code` char(6) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '代码',
  `c_name` char(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='级别表';

-- テーブル hsys.qm_tbl: ~5 rows (約) のデータをダンプしています
DELETE FROM `qm_tbl`;
/*!40000 ALTER TABLE `qm_tbl` DISABLE KEYS */;
INSERT INTO `qm_tbl` (`c_id`, `c_code`, `c_name`) VALUES
	(1, '402', 'PM'),
	(2, '401', 'TM'),
	(3, '301', 'PL'),
	(4, '201', 'SE'),
	(5, '101', 'PG');
/*!40000 ALTER TABLE `qm_tbl` ENABLE KEYS */;

--  テーブル hsys.rest_tbl の構造をダンプしています
DROP TABLE IF EXISTS `rest_tbl`;
CREATE TABLE IF NOT EXISTS `rest_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_date_start` datetime NOT NULL COMMENT '开始',
  `c_date_end` datetime NOT NULL COMMENT '结束',
  `c_len` float NOT NULL COMMENT '日期',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '种类 休(0) 病(1) 事(2) 婚(3) 丧(4) 公(5)',
  `c_summary` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '备注',
  `c_user_id` int(11) NOT NULL COMMENT '请假人',
  `c_status` int(11) NOT NULL DEFAULT '0' COMMENT '状态 登录中(0) 批准(1) 驳回(2) 撤销请求(3) 撤销(4)',
  `c_approval_user_id` int(11) DEFAULT NULL COMMENT '批准者',
  `c_approval_time` datetime DEFAULT NULL COMMENT '批准日期',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='休假表';

-- テーブル hsys.rest_tbl: ~5 rows (約) のデータをダンプしています
DELETE FROM `rest_tbl`;
/*!40000 ALTER TABLE `rest_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `rest_tbl` ENABLE KEYS */;

--  テーブル hsys.school_tbl の構造をダンプしています
DROP TABLE IF EXISTS `school_tbl`;
CREATE TABLE IF NOT EXISTS `school_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
  `c_province` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所在省份',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学校表';

-- テーブル hsys.school_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `school_tbl`;
/*!40000 ALTER TABLE `school_tbl` DISABLE KEYS */;
INSERT INTO `school_tbl` (`c_id`, `c_name`, `c_province`) VALUES
	(1, '南京大学', ''),
	(2, '东南大学', ''),
	(3, '同济大学', '');
/*!40000 ALTER TABLE `school_tbl` ENABLE KEYS */;

--  テーブル hsys.user_group_tbl の構造をダンプしています
DROP TABLE IF EXISTS `user_group_tbl`;
CREATE TABLE IF NOT EXISTS `user_group_tbl` (
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_group_id` int(11) NOT NULL COMMENT '组ID',
  `c_leader` int(1) NOT NULL DEFAULT '0' COMMENT '是否该组Leader 是(1) 否(0)',
  KEY `c_user_id` (`c_user_id`,`c_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户所在分组表';

-- テーブル hsys.user_group_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `user_group_tbl`;
/*!40000 ALTER TABLE `user_group_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_group_tbl` ENABLE KEYS */;

--  テーブル hsys.user_role_tbl の構造をダンプしています
DROP TABLE IF EXISTS `user_role_tbl`;
CREATE TABLE IF NOT EXISTS `user_role_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_role` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名',
  `c_enable` int(11) NOT NULL DEFAULT '0' COMMENT '无效(0) 有效(1)',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_user_id_c_role` (`c_user_id`,`c_role`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色表';

-- テーブル hsys.user_role_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `user_role_tbl`;
/*!40000 ALTER TABLE `user_role_tbl` DISABLE KEYS */;
INSERT INTO `user_role_tbl` (`c_id`, `c_user_id`, `c_role`, `c_enable`) VALUES
	(1, 1, 'admin', 1),
	(2, 1, 'user_edit', 1),
	(3, 1, 'rest_approve', 1),
	(4, 1, 'rest_list', 1),
	(5, 1, 'attendance_list', 1),
	(6, 1, 'extratime_list', 1),
	(7, 1, 'rest_list_all', 1),
	(8, 1, 'attendance_list_all', 1),
	(9, 1, 'attendance_upload', 1),
	(10, 1, 'extratime_list_all', 1),
	(11, 1, 'device_edit', 1),
	(12, 1, 'extratime_approve', 1);
/*!40000 ALTER TABLE `user_role_tbl` ENABLE KEYS */;

--  テーブル hsys.user_tbl の構造をダンプしています
DROP TABLE IF EXISTS `user_tbl`;
CREATE TABLE IF NOT EXISTS `user_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '工号',
  `c_name` varchar(8) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名字',
  `c_spelling` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '拼音或者片假名',
  `c_phone_number` varchar(23) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `c_sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别',
  `c_password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `c_mail` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '邮件',
  `c_place` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '工作地点',
  `c_company_id` int(11) DEFAULT NULL COMMENT '公司名字(分公司名字)',
  `c_qm_id` int(11) DEFAULT NULL COMMENT 'QM ID',
  `c_address` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '家庭地址',
  `c_id_number` char(18) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `c_school_id` int(11) DEFAULT NULL COMMENT '毕业学校ID',
  `c_major` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '主修专业',
  `c_degree` int(11) DEFAULT NULL COMMENT '学历级别 大专(0) 本科3(1) 本科2(2) 本科1(3) 硕士(4) 博士(5)',
  `c_graduate_date` date DEFAULT NULL COMMENT '毕业日期',
  `c_enter_date` date DEFAULT NULL COMMENT '入社日期',
  `c_exit_date` date DEFAULT NULL COMMENT '离职日期',
  PRIMARY KEY (`c_id`),
  KEY `c_no` (`c_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- テーブル hsys.user_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `user_tbl`;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` (`c_id`, `c_no`, `c_name`, `c_spelling`, `c_phone_number`, `c_sex`, `c_password`, `c_mail`, `c_place`, `c_company_id`, `c_qm_id`, `c_address`, `c_id_number`, `c_school_id`, `c_major`, `c_degree`, `c_graduate_date`, `c_enter_date`, `c_exit_date`) VALUES
	(1, 'admin', '管理员', 'guanliyuan', '88888888888', 0, '$2a$10$yggjrP1Vte9RKgbHQ1SQs.HBJ2NKGzZIUCRASqV4E7gIPM.CJr6s.', '@asd', '1231111', 1, NULL, 'asdadadasda', 'asdasdasd', 1, '数学', 2, '2019-02-07', '2019-02-27', NULL);
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
