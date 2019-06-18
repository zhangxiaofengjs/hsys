-- --------------------------------------------------------
-- ホスト:                          127.0.0.1
-- サーバーのバージョン:                   5.7.21 - MySQL Community Server (GPL)
-- サーバー OS:                      Win64
-- HeidiSQL バージョン:               10.1.0.5464
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
  `c_comment_start` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注1',
  `c_comment_end` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注2',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_user_id_c_date` (`c_user_id`,`c_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='出勤时间表';

-- エクスポートするデータが選択されていません
--  テーブル hsys.company_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `company_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `c_address` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `c_phone_number` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公司列表';

-- エクスポートするデータが選択されていません
--  テーブル hsys.device_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `device_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(7) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_comment` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '说明',
  `c_user_id` int(11) DEFAULT '0' COMMENT '使用者ID',
  `c_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 正常(0) 损坏(1) 交还(2)',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='设备表';

-- エクスポートするデータが選択されていません
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

-- エクスポートするデータが選択されていません
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

-- エクスポートするデータが選択されていません
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

-- エクスポートするデータが選択されていません
--  テーブル hsys.group_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `group_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(24) COLLATE utf8_bin NOT NULL COMMENT '组名',
  `c_parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父组ID',
  `c_level` int(11) NOT NULL DEFAULT '0' COMMENT '同一组织级别，越小级别越大',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='分组表';

-- エクスポートするデータが選択されていません
--  テーブル hsys.holiday_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `holiday_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_date` date NOT NULL COMMENT '日期',
  `c_type` int(11) NOT NULL DEFAULT '0' COMMENT '休息(0) 出勤(1) ',
  `c_comment` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '备注',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公休假表';

-- エクスポートするデータが選択されていません
--  テーブル hsys.project_leader_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `project_leader_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_project_id` int(11) NOT NULL COMMENT '项目ID',
  `c_leader_id` int(11) NOT NULL COMMENT 'LeaderID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目和Leader关系表';

-- エクスポートするデータが選択されていません
--  テーブル hsys.project_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `project_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` char(50) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '项目名',
  `c_funds` float NOT NULL COMMENT '经费',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目管理表';

-- エクスポートするデータが選択されていません
--  テーブル hsys.province_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `province_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='全国省表';

-- エクスポートするデータが選択されていません
--  テーブル hsys.qm_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `qm_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_code` char(6) COLLATE utf8_bin NOT NULL COMMENT '代码',
  `c_name` char(8) COLLATE utf8_bin NOT NULL COMMENT '名称',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='级别表';

-- エクスポートするデータが選択されていません
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

-- エクスポートするデータが選択されていません
--  テーブル hsys.school_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `school_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `c_province` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '所在省份',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学校表';

-- エクスポートするデータが選択されていません
--  テーブル hsys.user_group_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_group_tbl` (
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_group_id` int(11) NOT NULL COMMENT '组ID',
  `c_leader` int(1) NOT NULL DEFAULT '0' COMMENT '是否该组Leader 是(1) 否(0)',
  KEY `c_user_id` (`c_user_id`,`c_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户所在分组表';

-- エクスポートするデータが選択されていません
--  テーブル hsys.user_role_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_role_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_role` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '角色名',
  `c_enable` int(11) NOT NULL DEFAULT '0' COMMENT '无效(0) 有效(1)',
  PRIMARY KEY (`c_id`),
  UNIQUE KEY `c_user_id_c_role` (`c_user_id`,`c_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色表';

-- エクスポートするデータが選択されていません
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

-- エクスポートするデータが選択されていません
--  テーブル hsys.user_track_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_track_tbl` (
  `c_id` int(11) NOT NULL COMMENT 'ID',
  `c_user_id` int(11) NOT NULL COMMENT '员工ID',
  `c_date_start` date DEFAULT NULL COMMENT '开始日',
  `c_date_end` date DEFAULT NULL COMMENT '结束日',
  `c_company_id` int(11) DEFAULT NULL COMMENT '公司ID',
  `c_position` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '职位',
  `c_comment` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='员工履历表';

-- エクスポートするデータが選択されていません
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
