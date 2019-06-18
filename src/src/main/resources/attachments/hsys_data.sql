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

-- テーブル hsys.attendance_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `attendance_tbl`;
/*!40000 ALTER TABLE `attendance_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance_tbl` ENABLE KEYS */;

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

-- テーブル hsys.device_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `device_tbl`;
/*!40000 ALTER TABLE `device_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_tbl` ENABLE KEYS */;

-- テーブル hsys.expense_item_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `expense_item_tbl`;
/*!40000 ALTER TABLE `expense_item_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_item_tbl` ENABLE KEYS */;

-- テーブル hsys.expense_receipt_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `expense_receipt_tbl`;
/*!40000 ALTER TABLE `expense_receipt_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense_receipt_tbl` ENABLE KEYS */;

-- テーブル hsys.extra_time_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `extra_time_tbl`;
/*!40000 ALTER TABLE `extra_time_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `extra_time_tbl` ENABLE KEYS */;

-- テーブル hsys.group_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `group_tbl`;
/*!40000 ALTER TABLE `group_tbl` DISABLE KEYS */;
INSERT INTO `group_tbl` (`c_id`, `c_name`, `c_parent_id`, `c_level`) VALUES
	(1, '5本部', 0, 0),
	(2, 'D503', 1, 1),
	(3, '图研组', 2, 2),
	(4, 'NTTD组', 2, 2);
/*!40000 ALTER TABLE `group_tbl` ENABLE KEYS */;

-- テーブル hsys.holiday_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `holiday_tbl`;
/*!40000 ALTER TABLE `holiday_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `holiday_tbl` ENABLE KEYS */;

-- テーブル hsys.project_leader_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `project_leader_tbl`;
/*!40000 ALTER TABLE `project_leader_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_leader_tbl` ENABLE KEYS */;

-- テーブル hsys.project_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `project_tbl`;
/*!40000 ALTER TABLE `project_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_tbl` ENABLE KEYS */;

-- テーブル hsys.province_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `province_tbl`;
/*!40000 ALTER TABLE `province_tbl` DISABLE KEYS */;
INSERT INTO `province_tbl` (`c_id`, `c_name`) VALUES
	(1, '江苏'),
	(2, '上海'),
	(3, '重庆'),
	(4, '湖南');
/*!40000 ALTER TABLE `province_tbl` ENABLE KEYS */;

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

-- テーブル hsys.rest_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `rest_tbl`;
/*!40000 ALTER TABLE `rest_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `rest_tbl` ENABLE KEYS */;

-- テーブル hsys.school_tbl: ~14 rows (約) のデータをダンプしています
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

-- テーブル hsys.user_group_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `user_group_tbl`;
/*!40000 ALTER TABLE `user_group_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_group_tbl` ENABLE KEYS */;

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
	(7, 1, 'rest_list_all', 1),
	(8, 1, 'attendance_list_all', 1),
	(9, 1, 'attendance_upload', 1),
	(10, 1, 'extratime_list_all', 1),
	(11, 1, 'device_edit', 1),
	(12, 1, 'extratime_approve', 1);
/*!40000 ALTER TABLE `user_role_tbl` ENABLE KEYS */;

-- テーブル hsys.user_tbl: ~1 rows (約) のデータをダンプしています
DELETE FROM `user_tbl`;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` (`c_id`, `c_no`, `c_name`, `c_spelling`, `c_phone_number`, `c_sex`, `c_password`, `c_mail`, `c_place`, `c_company_id`, `c_qm_id`, `c_address`, `c_id_number`, `c_school_id`, `c_major`, `c_degree`, `c_graduate_date`, `c_enter_date`, `c_exit_date`, `c_hidden_flag`) VALUES
	(1, 'admin', '管理员', 'guanliyuan', '88888888888', 0, '$2a$10$Q5mnP133qYop/JSPhQ3wf.fJLG9ISK1zy4qkXYQsy0sxJgeiEkUj2', '@asd', '南通', 1, NULL, 'asdadadasda', '', 1, '数学', 2, '2019-02-07', '2019-02-27', NULL, 1);
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;

-- テーブル hsys.user_track_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `user_track_tbl`;
/*!40000 ALTER TABLE `user_track_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_track_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
