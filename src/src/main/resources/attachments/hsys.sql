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

--  テーブル hsys.company_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `company_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `c_address` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `c_phone_number` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公司列表';

-- テーブル hsys.company_tbl: ~6 rows (約) のデータをダンプしています
DELETE FROM `company_tbl`;
/*!40000 ALTER TABLE `company_tbl` DISABLE KEYS */;
INSERT INTO `company_tbl` (`c_id`, `c_name`, `c_address`, `c_phone_number`) VALUES
	(1, '海隆软件（南通）', NULL, NULL),
	(2, '海隆软件（南京）', NULL, NULL),
	(3, '海隆软件（昆山）', NULL, NULL),
	(4, '海隆软件（北京）', NULL, NULL),
	(5, '海隆软件（上海）', NULL, NULL),
	(6, '华钟', NULL, NULL);
/*!40000 ALTER TABLE `company_tbl` ENABLE KEYS */;

--  テーブル hsys.device_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `device_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(7) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_comment` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '说明',
  `c_user_id` int(11) DEFAULT '0' COMMENT '使用者ID',
  `c_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 正常(0) 损坏(1) 交还(2)',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='设备表';

-- テーブル hsys.device_tbl: ~1 rows (約) のデータをダンプしています
DELETE FROM `device_tbl`;
/*!40000 ALTER TABLE `device_tbl` DISABLE KEYS */;
INSERT INTO `device_tbl` (`c_id`, `c_no`, `c_comment`, `c_user_id`, `c_status`) VALUES
	(1, '22', '开发用PC', 18, 0);
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
  `c_status` int(11) NOT NULL DEFAULT '0' COMMENT '是否已经领款确认 (0)未收到 (1)已收到',
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
  `c_status` tinyint(11) NOT NULL DEFAULT '0' COMMENT '状态 填单中(0) 待审核(1) 财务处理中(2) 已领款(3) 驳回(4)',
  `c_comment` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `c_project_id` int(11) DEFAULT NULL COMMENT '报销项目ID',
  `c_attachment_path` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '附件路径',
  PRIMARY KEY (`c_id`)
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

-- テーブル hsys.extra_time_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `extra_time_tbl`;
/*!40000 ALTER TABLE `extra_time_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `extra_time_tbl` ENABLE KEYS */;

--  テーブル hsys.group_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `group_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(24) COLLATE utf8_bin NOT NULL COMMENT '组名',
  `c_parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父组ID',
  `c_level` int(11) NOT NULL DEFAULT '0' COMMENT '同一组织级别，越小级别越大',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='分组表';

-- テーブル hsys.group_tbl: ~8 rows (約) のデータをダンプしています
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
	(8, '管理本部', 0, 1),
	(9, 'FNC组', 2, 2);
/*!40000 ALTER TABLE `group_tbl` ENABLE KEYS */;

--  テーブル hsys.holiday_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `holiday_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_date` date NOT NULL COMMENT '日期',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '正常休息(0) 调休(1) 出勤(2) ',
  `c_comment` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '备注',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公休假表';

-- テーブル hsys.holiday_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `holiday_tbl`;
/*!40000 ALTER TABLE `holiday_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `holiday_tbl` ENABLE KEYS */;

--  テーブル hsys.project_leader_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `project_leader_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_project_id` int(11) NOT NULL COMMENT '项目ID',
  `c_leader_id` int(11) NOT NULL COMMENT 'LeaderID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目和Leader关系表';

-- テーブル hsys.project_leader_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `project_leader_tbl`;
/*!40000 ALTER TABLE `project_leader_tbl` DISABLE KEYS */;
INSERT INTO `project_leader_tbl` (`c_id`, `c_project_id`, `c_leader_id`) VALUES
	(1, 1, 28),
	(2, 2, 28);
/*!40000 ALTER TABLE `project_leader_tbl` ENABLE KEYS */;

--  テーブル hsys.project_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `project_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` char(50) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '项目名',
  `c_funds` float NOT NULL COMMENT '经费',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目管理表';

-- テーブル hsys.project_tbl: ~2 rows (約) のデータをダンプしています
DELETE FROM `project_tbl`;
/*!40000 ALTER TABLE `project_tbl` DISABLE KEYS */;
INSERT INTO `project_tbl` (`c_id`, `c_no`, `c_name`, `c_funds`) VALUES
	(1, '18-D3-001', 'ZUKEN/A&M离岸开发', 10000),
	(2, '18-D3-002', 'ZUKEN/PLM离岸开发', 10000);
/*!40000 ALTER TABLE `project_tbl` ENABLE KEYS */;

--  テーブル hsys.province_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `province_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='全国省表';

-- テーブル hsys.province_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `province_tbl`;
/*!40000 ALTER TABLE `province_tbl` DISABLE KEYS */;
INSERT INTO `province_tbl` (`c_id`, `c_name`) VALUES
	(1, '江苏'),
	(2, '上海'),
	(3, '重庆'),
	(4, '湖南');
/*!40000 ALTER TABLE `province_tbl` ENABLE KEYS */;

--  テーブル hsys.qm_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `qm_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_code` char(6) COLLATE utf8_bin NOT NULL COMMENT '代码',
  `c_name` char(8) COLLATE utf8_bin NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='级别表';

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

--  テーブル hsys.school_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `school_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `c_province` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '所在省份',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学校表';

-- テーブル hsys.school_tbl: ~12 rows (約) のデータをダンプしています
DELETE FROM `school_tbl`;
/*!40000 ALTER TABLE `school_tbl` DISABLE KEYS */;
INSERT INTO `school_tbl` (`c_id`, `c_name`, `c_province`) VALUES
	(1, '南京大学', ''),
	(2, '东南大学', ''),
	(3, '同济大学', ''),
	(4, '天津工业大学', NULL),
	(5, '江苏大学', NULL),
	(6, '南京林业大学', NULL),
	(7, '常州工业大学', NULL),
	(8, '河海大学', NULL),
	(9, '南通大学', NULL),
	(10, '南京航空航天大学', NULL),
	(11, '南京工业大学', NULL),
	(12, '南京信息工程大学', NULL),
	(13, '上海师范大学', NULL),
	(14, '南京气象学院', NULL);
/*!40000 ALTER TABLE `school_tbl` ENABLE KEYS */;

--  テーブル hsys.user_group_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_group_tbl` (
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_group_id` int(11) NOT NULL COMMENT '组ID',
  `c_leader` int(1) NOT NULL DEFAULT '0' COMMENT '是否该组Leader 是(1) 否(0)',
  KEY `c_user_id` (`c_user_id`,`c_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户所在分组表';

-- テーブル hsys.user_group_tbl: ~25 rows (約) のデータをダンプしています
DELETE FROM `user_group_tbl`;
/*!40000 ALTER TABLE `user_group_tbl` DISABLE KEYS */;
INSERT INTO `user_group_tbl` (`c_user_id`, `c_group_id`, `c_leader`) VALUES
	(17, 5, 0),
	(28, 7, 0),
	(18, 5, 0),
	(5, 5, 0),
	(6, 4, 0),
	(11, 4, 0),
	(22, 8, 0),
	(20, 6, 0),
	(21, 7, 0),
	(3, 9, 0),
	(2, 4, 0),
	(14, 6, 0),
	(32, 4, 0),
	(23, 3, 0),
	(16, 7, 0),
	(26, 4, 0),
	(24, 9, 0),
	(27, 5, 0),
	(7, 4, 0),
	(8, 7, 0),
	(31, 4, 0),
	(9, 4, 0),
	(10, 9, 0),
	(12, 5, 0),
	(19, 9, 0),
	(4, 7, 0),
	(30, 4, 0),
	(29, 9, 0);
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

-- テーブル hsys.user_role_tbl: ~39 rows (約) のデータをダンプしています
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
	(12, 1, 'extratime_approve', 1),
	(13, 28, 'admin', 1),
	(14, 28, 'user_edit', 1),
	(15, 28, 'extratime_approve', 1),
	(16, 28, 'extratime_list', 1),
	(17, 28, 'extratime_list_all', 1),
	(18, 28, 'rest_approve', 1),
	(19, 28, 'rest_list', 1),
	(20, 28, 'rest_list_all', 1),
	(21, 28, 'attendance_list', 1),
	(22, 28, 'attendance_list_all', 1),
	(23, 28, 'expense_list', 1),
	(24, 28, 'expense_list_all', 1),
	(25, 28, 'device_edit', 1),
	(26, 17, 'extratime_approve', 1),
	(27, 17, 'extratime_list', 1),
	(28, 17, 'rest_approve', 1),
	(29, 17, 'rest_list', 1),
	(30, 17, 'attendance_list', 0),
	(31, 17, 'expense_list', 1),
	(32, 14, 'extratime_list', 1),
	(33, 14, 'rest_list', 1),
	(34, 14, 'expense_list', 1),
	(35, 22, 'user_edit', 1),
	(36, 22, 'extratime_list_all', 1),
	(37, 22, 'rest_list_all', 1),
	(38, 22, 'attendance_upload', 1),
	(39, 22, 'attendance_list_all', 1),
	(40, 32, 'extratime_list', 1),
	(41, 32, 'rest_list', 1),
	(42, 3, 'extratime_list', 1),
	(43, 3, 'rest_list', 1);
/*!40000 ALTER TABLE `user_role_tbl` ENABLE KEYS */;

--  テーブル hsys.user_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '工号',
  `c_name` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '名字',
  `c_spelling` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '拼音或者片假名',
  `c_phone_number` varchar(23) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `c_sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别',
  `c_password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `c_mail` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '邮件',
  `c_place` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '工作地点',
  `c_company_id` int(11) DEFAULT NULL COMMENT '公司名字(分公司名字)',
  `c_qm_id` int(11) DEFAULT NULL COMMENT 'QM ID',
  `c_address` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '家庭地址',
  `c_id_number` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `c_school_id` int(11) DEFAULT NULL COMMENT '毕业学校ID',
  `c_major` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '主修专业',
  `c_degree` int(11) DEFAULT NULL COMMENT '学历级别 大专(0) 本科3(1) 本科2(2) 本科1(3) 硕士(4) 博士(5)',
  `c_graduate_date` date DEFAULT NULL COMMENT '毕业日期',
  `c_enter_date` date DEFAULT NULL COMMENT '入社日期',
  `c_exit_date` date DEFAULT NULL COMMENT '离职日期',
  `c_hidden_flag` int(11) NOT NULL DEFAULT '0' COMMENT '(0)正常 (1)隐藏',
  PRIMARY KEY (`c_id`),
  KEY `c_no` (`c_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- テーブル hsys.user_tbl: ~32 rows (約) のデータをダンプしています
DELETE FROM `user_tbl`;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` (`c_id`, `c_no`, `c_name`, `c_spelling`, `c_phone_number`, `c_sex`, `c_password`, `c_mail`, `c_place`, `c_company_id`, `c_qm_id`, `c_address`, `c_id_number`, `c_school_id`, `c_major`, `c_degree`, `c_graduate_date`, `c_enter_date`, `c_exit_date`, `c_hidden_flag`) VALUES
	(1, 'admin', '管理员', 'guanliyuan', '88888888888', 0, '$2a$10$Q5mnP133qYop/JSPhQ3wf.fJLG9ISK1zy4qkXYQsy0sxJgeiEkUj2', '@asd', '南通', 1, NULL, 'asdadadasda', 'asdasdasd', 1, '数学', 2, '2019-02-07', '2019-02-27', NULL, 1),
	(2, 'NT015', '金凤霞', '', NULL, 1, '$2a$10$sYDp3G8RjCRJsYxRlr8MFej79Wrs6lnw7eKvm6PGgmNOQzqXo7zoO', 'jinfengxia@hyron.com', '南通', 1, NULL, '江苏南通通州', '320683199210226868', NULL, '物联网', 3, '2015-07-01', '2015-07-01', NULL, 0),
	(3, 'NT014', '翟强', 'ざい　きょう', NULL, 0, '$2a$10$NEt8sUv/i0xNisp/7gGN/OVXicEaynb8GJimSlW113C8RLV9n/H4O', 'zhaiqiang@hyron.com', '南通', 1, NULL, '江苏淮安', '320826198604210032', NULL, '计算机', 3, '2012-07-01', '2014-07-01', NULL, 0),
	(4, 'NT031', '吴建兰', NULL, NULL, 0, '$2a$10$B2gB9b4w2hMdP5205Qd.Re.RNjAPX9BEE9lc1z0QP16bpFl8lhT4C', 'wujianlan@hyron.com', '南通', 1, NULL, '中国', '', NULL, '', 0, NULL, NULL, NULL, 0),
	(5, 'JS961', '唐赞红', NULL, NULL, 0, '$2a$10$pU/ywu9.aL7BUFyx4TKF6eovV4v0sqTKH3axBDOVEqJCBUBoSIPAe', 'tangzanhong@hyron.com', '南通', 2, NULL, '湖南衡阳', '43042319950615251X', NULL, '计算机', 3, '2017-07-01', '2017-07-01', NULL, 0),
	(6, 'JS963', '朱菲燕', NULL, NULL, 1, '$2a$10$/3dK4sdHX2gDQviG7SKnkO1hG2MgxyDZjTJ8lePKQekwC/alE758a', 'zhufeiyan@hyron.com', '南通', 2, NULL, '江苏南通如东', '320623199505176080', NULL, '计算机', 3, '2017-07-01', '2017-07-01', NULL, 0),
	(7, 'NT024', '韩才鹏', NULL, NULL, 0, '$2a$10$mjC74906D33MdqVoFxgwHOgxugAT5YZqUwVIMI3JikFuOVQ2TWa.2', 'hancaipeng@hyron.com', '南通', 1, NULL, '中国', '', NULL, '', 0, '1949-01-01', '1950-02-01', NULL, 0),
	(8, 'NT025', '周智超', NULL, NULL, 0, '$2a$10$gT/LaTx91eREvjd.ZxUjXuYF/cqn0Fvz7hQpnF5klDMEH9zvccsOS', 'zhouzhichao@hyron.com', '南通', 1, NULL, '中国', '', NULL, '', 0, '1949-01-01', '1949-01-01', NULL, 0),
	(9, 'NT027', '徐苏新', NULL, NULL, 1, '$2a$10$xqLHHDzZwozJRmOEwAq9e.1rUiYn3JBeghXnEC107T1MeGrB83Nym', 'xusuxin@hyron.com', '南通', 1, NULL, '中国', '', NULL, '', 0, '1949-01-01', '1949-01-01', NULL, 0),
	(10, 'NT028', '程旭', 'てい　きょく', NULL, 0, '$2a$10$Lgcy9L.JHjGociLhCAiEuuDXxZXGWe00wrx.v8l6LVj.EN6mz.6ea', 'chengxu@hyron.com', '南通', 1, NULL, '中国', '', NULL, '', 0, '1949-01-01', '1949-01-01', NULL, 0),
	(11, 'JS964', '凌夏鸣', NULL, NULL, 0, '$2a$10$ImQXnIr5s8DCf1cavQYkvO8uAi1e9hMfQlITjmXFdarp8SlsCFTVS', 'lingxiaming@hyron.com', '南通', 2, NULL, '江苏常州', '320581199507133410', NULL, '计算机', 3, '2017-07-01', '2017-07-01', NULL, 0),
	(12, 'NT029', '张金钰', NULL, NULL, 1, '$2a$10$V4/6Mwf/FhzzWnq4QAzTL.jkIzi38sqOZWMU/5wcqiO5qoKNT0twq', 'zhangjinyu@hyron.com', '南通', 1, NULL, '中国', '320683199405170323', NULL, '软件工程', 2, '2017-07-01', '2017-07-01', NULL, 0),
	(13, '未利用', '未利用', NULL, NULL, 1, '$2a$10$xJuSuj7pY7VTmTk4bfGmYuAsx5o6el.4qQSnTlQIt4gEpWI8Kkt.G', 'zhangyanhong@hyron.com', '南通', 1, NULL, '中国', '', NULL, '', 0, '1949-01-01', '1949-01-01', NULL, 0),
	(14, 'NT016', '缪卫卫', NULL, NULL, 0, '$2a$10$ewH9l8q7VAiZ4CeAyqmcg.gvyfgZDHkp.ba0kJg4yVDfG3J7X3rPG', 'miaoweiwei@hyron.com', '南通', 1, NULL, '江苏南通如东', '', NULL, '计算机', 3, '2011-07-01', '2011-07-01', NULL, 0),
	(15, 'NT013', '陆晨', NULL, NULL, 0, '$2a$10$w06smd7BVId6Erliq2Af/et1GSlKMuM3cvVkaK.rdoN/.YGB50qXm', 'luchen@hyron.com', '上海华钟', 1, NULL, '中国', '', NULL, '', 0, '2014-07-01', '2014-07-01', NULL, 0),
	(16, 'NT019', '郭敏敏', 'かく　びん　びん', NULL, 1, '$2a$10$BUQ9SehnSiKNFNkNHrj3IOgnd87TvzghVS3fIdIbbfA.bxO/5LPcC', 'guominmin@hyron.com', '南通', 1, NULL, '江苏南通如东', '', NULL, '计算机', 3, '2016-07-01', '2016-07-07', NULL, 0),
	(17, 'SH1149', '陈杰', NULL, NULL, 0, '$2a$10$/qrLHHl7jZ1KhoXgK7EEaeOnplKypz3SINq8px6O0pK6Sm1XQaJbO', 'chen-jie@hyron.com', '南通', 5, NULL, '江苏海门', '320684198503293177', NULL, '', 0, '2008-07-01', '2008-07-01', NULL, 0),
	(18, 'JS541', '任静', NULL, NULL, 1, '$2a$10$HNQLFSmaxzEK.j9k6EPAve2q2JkmhpzIVNsSkSmcYBUAP//FW4GAy', 'renjing@hyron.com', '南通', 2, NULL, '中国', '320683199104076261', NULL, '计算机科学与技术', 3, '2014-07-01', '2014-07-01', NULL, 0),
	(19, 'NT030', '张晨晨', 'ちょう　しん　しん', NULL, 1, '$2a$10$laVos3YA9EAxMMyDmY0YfOs0o1jOmq4oPFquoPQLLmXR79ZvXJb.q', 'zhangchenchen@hyron.com', '南通', 1, NULL, '中国', '', NULL, '', 0, '1949-01-01', '1949-01-01', NULL, 0),
	(20, 'NT011', '顾昊', NULL, NULL, 0, '$2a$10$KSfXPCWIy2x41EHVkP0GieeEjMQqiEUMAmWHWJnKVNUzMxFu/dpZG', 'guhao@hyron.com', '南通', 1, NULL, '江苏南通海安', '320621199201291415', NULL, '通信工程', 3, '2014-07-01', '2014-07-01', NULL, 0),
	(21, 'NT012', '季思建', NULL, NULL, 0, '$2a$10$F3hrJDySl3RocnIRfCUYIemm12SJNfedXqHmEZV82xiDlEzYqE1F6', 'jisijian@hyron.com', '南通', 1, NULL, '江苏南通如东', '320602199107166512', NULL, '软件工程', 3, '2014-07-01', '2014-07-01', NULL, 0),
	(22, 'NT010', '季冬梅', NULL, NULL, 1, '$2a$10$vS7Hf4uV/JCUenzYShN.cOhu67vMGi9.Kr.q461k9OPnGyHwwEs0W', 'jidongmei@hyron.com', '南通', 1, NULL, '江苏南通如东', '320623198312280020', NULL, '', 0, '2005-01-01', '2014-01-01', NULL, 0),
	(23, 'NT018', '李俊', NULL, NULL, 1, '$2a$10$RJI5IPsj/OLUzKB9jDS6QOnVz0djVRrS17BlkKte6/6w9qntoW/H2', 'li_jun@hyron.com', '南通', 1, NULL, '"江苏南通"', '', NULL, '', 0, '2016-07-25', '2016-04-25', '2017-04-25', 0),
	(24, 'NT021', '施华利', 'し　か　り', NULL, 1, '$2a$10$GkjXO.dkNLkSmJ7rYwPJoOvAapQgnSwq833noXcV3K.zfwWIBnUeu', 'shihuali@hyron.com', '南通', 1, NULL, '', '320683199205167429', NULL, '计算机', 3, '2015-07-01', '2016-02-20', NULL, 0),
	(25, 'NT023', '朱建', NULL, NULL, 1, '$2a$10$TbVSQDmXZI5eRXzZ3nX6X.FHuJ26UcD0jDupJY3Q3HmR/liKP8XIC', 'zhujian@hyron.com', '南通', 1, NULL, '""江苏南通""', '', NULL, '计算机', 3, '2014-06-01', '2017-07-04', '2017-08-31', 0),
	(26, 'NT020', '尹春磊', NULL, NULL, 0, '$2a$10$V5xTgx9TgQJlXae.YM2YcelEqBWOAai8p7JyFEm3CR0QtHkMaYmF6', 'yinchunlei@hyron.com', '南通', 1, NULL, '', '320683199002019215', NULL, '计算机', 3, '2014-07-01', '2014-07-01', NULL, 0),
	(27, 'NT022', '张超', NULL, NULL, 0, '$2a$10$lNRZmq7DIwvEuUbEp0rnuOvQZ/TdhPq23AgpMqfZ5waMIDAbh0dZS', 'zhangchao@hyron.com', '南通', 1, NULL, '江苏南通', '320602199112050514', NULL, '计算机', 2, '2014-06-01', '2017-06-01', NULL, 0),
	(28, 'SH869', '张小锋', NULL, NULL, 0, '$2a$10$/oMzHsp7TECnYcpp.TRS0ukF7pxE1QstEuAyB99phhyZ.uFg2.gfa', 'zhangxiaofeng@hyron.com', '南通', 5, NULL, '"江苏南通通州"', '320683198407080319', NULL, '数学', 3, '2006-07-01', '2006-07-01', NULL, 0),
	(29, 'NT033', '袁舒伟', 'えん　じょ　い', NULL, 1, '$2a$10$Xe0AdM87iP7wLT6sikHB2u/QMZ4wgN0KKExuoBS6PRrax5wh3LmaC', 'yuanshuwei@hyron.com', '南通', 1, NULL, '中国', '', NULL, '', 0, NULL, NULL, NULL, 0),
	(30, 'NT032', '张艳红', NULL, NULL, 1, '$2a$10$Fg0uwOpp6/bn4Chw7v3cnOozvblwTKSyTJFGGTsM5Mk8BtEldOV4S', 'zhangyanhong@hyron.com', '南通', 1, NULL, '中国', '', NULL, '', 0, NULL, NULL, NULL, 0),
	(31, 'NT026', '邱帅', NULL, NULL, 0, '$2a$10$bMhO7KF6OyNJqffIQibMB.fhn3XFrg8EO4zHKdYaHRRnhgQIDgRyq', 'qiushuai@hyron.com', '南通', 1, NULL, '中国', '', NULL, '', 0, '1949-01-01', '1950-01-01', NULL, 0),
	(32, 'NT017', '韩锐', NULL, NULL, 0, '$2a$10$Vx1YsVFXU/.dpwIUTQm.Yey.UXANzfZPrCifEq2D6lKyYfy7ZvN2a', 'hanrui@hyron.com', '南通', 1, NULL, '江苏南通如皋', '320682199011250033', NULL, '计算机', 4, '2015-07-01', '2015-07-01', NULL, 0);
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
