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

-- テーブル hsys.attendance_tbl: ~217 rows (約) のデータをダンプしています
DELETE FROM `attendance_tbl`;
/*!40000 ALTER TABLE `attendance_tbl` DISABLE KEYS */;
INSERT INTO `attendance_tbl` (`c_id`, `c_user_id`, `c_date`, `c_start`, `c_end`, `c_comment`) VALUES
	(1, 18, '2016-03-21', '08:37:00', '18:37:08', '迟到,加班'),
	(2, 18, '2016-03-22', '09:03:38', '20:48:04', '迟到,加班'),
	(3, 18, '2016-03-23', '08:27:12', '20:59:30', ',加班'),
	(4, 18, '2016-03-24', '08:38:06', '21:08:56', '迟到,加班'),
	(5, 18, '2016-03-25', '08:32:42', '18:39:04', ',加班'),
	(6, 18, '2016-03-26', NULL, NULL, '休息,休息'),
	(7, 18, '2016-03-27', NULL, NULL, '休息,休息'),
	(8, 18, '2016-03-28', NULL, NULL, '旷工,旷工'),
	(9, 18, '2016-03-29', '08:10:36', '17:55:16', ','),
	(10, 18, '2016-03-30', NULL, NULL, '旷工,旷工'),
	(11, 18, '2016-03-31', '08:29:54', '16:35:18', ',早退'),
	(12, 18, '2016-04-01', '08:34:08', '13:21:00', ',旷工'),
	(13, 18, '2016-04-02', NULL, NULL, '休息,休息'),
	(14, 18, '2016-04-03', NULL, NULL, '休息,休息'),
	(15, 18, '2016-04-04', NULL, NULL, '旷工,旷工'),
	(16, 18, '2016-04-05', '08:25:36', '18:45:26', ',加班'),
	(17, 18, '2016-04-06', '08:26:10', '12:12:54', ',旷工'),
	(18, 18, '2016-04-07', '08:26:02', '17:38:02', ','),
	(19, 18, '2016-04-08', '08:35:36', '18:12:00', '迟到,'),
	(20, 18, '2016-04-09', NULL, NULL, '休息,休息'),
	(21, 18, '2016-04-10', NULL, NULL, '休息,休息'),
	(22, 18, '2016-04-11', '08:31:58', '17:59:48', ','),
	(23, 18, '2016-04-12', '08:11:20', '18:18:12', ','),
	(24, 18, '2016-04-13', '08:32:44', '18:16:20', ','),
	(25, 18, '2016-04-14', '08:30:34', '18:26:02', ','),
	(26, 18, '2016-04-15', '08:35:42', '18:18:48', '迟到,'),
	(27, 18, '2016-04-16', NULL, NULL, '休息,休息'),
	(28, 18, '2016-04-17', NULL, NULL, '休息,休息'),
	(29, 18, '2016-04-18', NULL, NULL, '旷工,旷工'),
	(30, 18, '2016-04-19', '08:25:32', '18:10:50', ','),
	(31, 18, '2016-04-20', '08:31:56', '19:21:10', ',加班'),
	(32, 19, '2016-03-21', '08:47:02', '18:00:54', '迟到,'),
	(33, 19, '2016-03-22', '08:42:48', '18:05:16', '迟到,'),
	(34, 19, '2016-03-23', '08:39:48', '17:58:08', '迟到,'),
	(35, 19, '2016-03-24', '08:21:46', '17:59:30', ','),
	(36, 19, '2016-03-25', '08:40:00', '17:55:56', '迟到,'),
	(37, 19, '2016-03-26', '08:44:54', '15:31:16', '加班,加班'),
	(38, 19, '2016-03-27', NULL, NULL, '休息,休息'),
	(39, 19, '2016-03-28', '08:29:38', '18:01:48', ','),
	(40, 19, '2016-03-29', '08:23:14', '20:37:38', ',加班'),
	(41, 19, '2016-03-30', '08:28:06', '20:35:08', ',加班'),
	(42, 19, '2016-03-31', '08:27:58', '08:30:24', ',旷工'),
	(43, 19, '2016-04-01', '08:28:38', '18:16:56', ','),
	(44, 19, '2016-04-02', NULL, NULL, '休息,休息'),
	(45, 19, '2016-04-03', NULL, NULL, '休息,休息'),
	(46, 19, '2016-04-04', NULL, NULL, '旷工,旷工'),
	(47, 19, '2016-04-05', '08:33:10', '18:08:54', ','),
	(48, 19, '2016-04-06', '08:48:48', '18:12:28', '迟到,'),
	(49, 19, '2016-04-07', '08:40:00', '18:16:10', '迟到,'),
	(50, 19, '2016-04-08', '08:27:26', '18:19:44', ','),
	(51, 19, '2016-04-09', NULL, NULL, '休息,休息'),
	(52, 19, '2016-04-10', '09:11:22', '17:52:18', '加班,加班'),
	(53, 19, '2016-04-11', '08:32:30', '18:05:08', ','),
	(54, 19, '2016-04-12', '08:49:56', '18:16:04', '迟到,'),
	(55, 19, '2016-04-13', '08:39:18', '18:14:26', '迟到,'),
	(56, 19, '2016-04-14', '08:42:24', '18:10:00', '迟到,'),
	(57, 19, '2016-04-15', '08:28:58', '17:51:40', ','),
	(58, 19, '2016-04-16', NULL, NULL, '休息,休息'),
	(59, 19, '2016-04-17', NULL, NULL, '休息,休息'),
	(60, 19, '2016-04-18', '08:40:16', '18:10:06', '迟到,'),
	(61, 19, '2016-04-19', '08:34:44', '18:10:32', ','),
	(62, 19, '2016-04-20', '08:37:52', '18:04:52', '迟到,'),
	(63, 20, '2016-03-21', '08:58:50', '18:00:36', '迟到,'),
	(64, 20, '2016-03-22', '08:57:40', '18:04:10', '迟到,'),
	(65, 20, '2016-03-23', '08:57:58', '17:58:18', '迟到,'),
	(66, 20, '2016-03-24', '08:54:46', '17:59:24', '迟到,'),
	(67, 20, '2016-03-25', '08:59:10', '17:57:00', '迟到,'),
	(68, 20, '2016-03-26', NULL, NULL, '休息,休息'),
	(69, 20, '2016-03-27', NULL, NULL, '休息,休息'),
	(70, 20, '2016-03-28', '08:54:38', '18:01:42', '迟到,'),
	(71, 20, '2016-03-29', '08:52:16', '20:37:16', '迟到,加班'),
	(72, 20, '2016-03-30', '08:56:20', '18:04:20', '迟到,'),
	(73, 20, '2016-03-31', '08:54:54', '18:13:02', '迟到,'),
	(74, 20, '2016-04-01', '09:01:04', '18:16:50', '迟到,'),
	(75, 20, '2016-04-02', NULL, NULL, '休息,休息'),
	(76, 20, '2016-04-03', NULL, NULL, '休息,休息'),
	(77, 20, '2016-04-04', NULL, NULL, '旷工,旷工'),
	(78, 20, '2016-04-05', '08:56:50', '18:09:10', '迟到,'),
	(79, 20, '2016-04-06', '08:58:56', '18:12:32', '迟到,'),
	(80, 20, '2016-04-07', '08:59:36', '18:16:04', '迟到,'),
	(81, 20, '2016-04-08', '08:54:18', '18:19:42', '迟到,'),
	(82, 20, '2016-04-09', NULL, NULL, '休息,休息'),
	(83, 20, '2016-04-10', NULL, NULL, '休息,休息'),
	(84, 20, '2016-04-11', '08:52:54', '18:06:20', '迟到,'),
	(85, 20, '2016-04-12', '08:56:38', '18:25:42', '迟到,'),
	(86, 20, '2016-04-13', '08:53:20', '18:14:20', '迟到,'),
	(87, 20, '2016-04-14', '08:57:06', '18:09:54', '迟到,'),
	(88, 20, '2016-04-15', NULL, NULL, '旷工,旷工'),
	(89, 20, '2016-04-16', NULL, NULL, '休息,休息'),
	(90, 20, '2016-04-17', NULL, NULL, '休息,休息'),
	(91, 20, '2016-04-18', '08:56:50', '18:09:50', '迟到,'),
	(92, 20, '2016-04-19', '08:53:12', NULL, '迟到,未刷卡'),
	(93, 20, '2016-04-20', '08:50:26', '18:04:38', '迟到,'),
	(94, 21, '2016-03-21', NULL, NULL, '旷工,旷工'),
	(95, 21, '2016-03-22', '08:58:18', '18:06:04', '迟到,'),
	(96, 21, '2016-03-23', '08:50:24', '18:12:32', '迟到,'),
	(97, 21, '2016-03-24', '08:41:42', '17:57:56', '迟到,'),
	(98, 21, '2016-03-25', '08:21:06', '18:00:52', ','),
	(99, 21, '2016-03-26', NULL, NULL, '休息,休息'),
	(100, 21, '2016-03-27', NULL, NULL, '休息,休息'),
	(101, 21, '2016-03-28', '08:36:16', '18:09:40', '迟到,'),
	(102, 21, '2016-03-29', '08:33:22', '18:10:24', ','),
	(103, 21, '2016-03-30', '09:02:12', '18:09:18', '迟到,'),
	(104, 21, '2016-03-31', '08:22:46', '18:17:02', ','),
	(105, 21, '2016-04-01', '08:42:24', '18:15:44', '迟到,'),
	(106, 21, '2016-04-02', NULL, NULL, '休息,休息'),
	(107, 21, '2016-04-03', NULL, NULL, '休息,休息'),
	(108, 21, '2016-04-04', NULL, NULL, '旷工,旷工'),
	(109, 21, '2016-04-05', '08:45:58', '21:19:22', '迟到,加班'),
	(110, 21, '2016-04-06', '08:51:30', '22:32:34', '迟到,加班'),
	(111, 21, '2016-04-07', '08:38:50', '18:42:54', '迟到,加班'),
	(112, 21, '2016-04-08', '08:38:06', '20:19:24', '迟到,加班'),
	(113, 21, '2016-04-09', NULL, NULL, '休息,休息'),
	(114, 21, '2016-04-10', '08:27:10', '17:06:52', '加班,加班'),
	(115, 21, '2016-04-11', '08:31:52', '19:03:32', ',加班'),
	(116, 21, '2016-04-12', '08:39:20', '19:45:42', '迟到,加班'),
	(117, 21, '2016-04-13', '08:40:54', '18:15:08', '迟到,'),
	(118, 21, '2016-04-14', '09:01:12', '19:35:00', '迟到,加班'),
	(119, 21, '2016-04-15', '07:40:46', '18:11:00', ','),
	(120, 21, '2016-04-16', NULL, NULL, '休息,休息'),
	(121, 21, '2016-04-17', NULL, NULL, '休息,休息'),
	(122, 21, '2016-04-18', '08:23:20', '18:13:00', ','),
	(123, 21, '2016-04-19', '08:29:20', '18:10:14', ','),
	(124, 21, '2016-04-20', '08:32:30', '20:49:54', ',加班'),
	(125, 22, '2016-03-21', '08:37:12', '18:08:08', '迟到,'),
	(126, 22, '2016-03-22', '08:45:50', '18:06:40', '迟到,'),
	(127, 22, '2016-03-23', '08:43:46', '17:58:00', '迟到,'),
	(128, 22, '2016-03-24', '08:44:52', '17:58:38', '迟到,'),
	(129, 22, '2016-03-25', '08:53:56', '18:06:10', '迟到,'),
	(130, 22, '2016-03-26', NULL, NULL, '休息,休息'),
	(131, 22, '2016-03-27', NULL, NULL, '休息,休息'),
	(132, 22, '2016-03-28', '08:32:50', '18:08:08', ','),
	(133, 22, '2016-03-29', '08:48:30', '18:15:34', '迟到,'),
	(134, 22, '2016-03-30', '08:37:58', '18:05:34', '迟到,'),
	(135, 22, '2016-03-31', '08:43:02', '18:09:50', '迟到,'),
	(136, 22, '2016-04-01', '08:38:26', '18:08:50', '迟到,'),
	(137, 22, '2016-04-02', NULL, NULL, '休息,休息'),
	(138, 22, '2016-04-03', NULL, NULL, '休息,休息'),
	(139, 22, '2016-04-04', NULL, NULL, '旷工,旷工'),
	(140, 22, '2016-04-05', '08:42:20', '18:12:40', '迟到,'),
	(141, 22, '2016-04-06', '08:42:56', '18:11:30', '迟到,'),
	(142, 22, '2016-04-07', '08:42:46', '18:14:58', '迟到,'),
	(143, 22, '2016-04-08', '08:39:32', '18:19:56', '迟到,'),
	(144, 22, '2016-04-09', NULL, NULL, '休息,休息'),
	(145, 22, '2016-04-10', NULL, NULL, '休息,休息'),
	(146, 22, '2016-04-11', '08:44:34', '18:08:06', '迟到,'),
	(147, 22, '2016-04-12', '08:38:42', '18:23:30', '迟到,'),
	(148, 22, '2016-04-13', '08:43:22', '18:15:04', '迟到,'),
	(149, 22, '2016-04-14', '08:28:00', '18:13:10', ','),
	(150, 22, '2016-04-15', '08:43:32', '17:51:30', '迟到,'),
	(151, 22, '2016-04-16', NULL, NULL, '休息,休息'),
	(152, 22, '2016-04-17', NULL, NULL, '休息,休息'),
	(153, 22, '2016-04-18', '08:59:56', '18:09:20', '迟到,'),
	(154, 22, '2016-04-19', '08:31:24', '18:08:10', ','),
	(155, 22, '2016-04-20', '08:37:16', '18:05:58', '迟到,'),
	(156, 23, '2016-03-21', NULL, NULL, '旷工,旷工'),
	(157, 23, '2016-03-22', NULL, NULL, '旷工,旷工'),
	(158, 23, '2016-03-23', NULL, NULL, '旷工,旷工'),
	(159, 23, '2016-03-24', NULL, NULL, '旷工,旷工'),
	(160, 23, '2016-03-25', NULL, NULL, '旷工,旷工'),
	(161, 23, '2016-03-26', NULL, NULL, '休息,休息'),
	(162, 23, '2016-03-27', NULL, NULL, '休息,休息'),
	(163, 23, '2016-03-28', NULL, NULL, '旷工,旷工'),
	(164, 23, '2016-03-29', '09:25:02', '18:01:06', '迟到,'),
	(165, 23, '2016-03-30', '08:53:28', '18:04:22', '迟到,'),
	(166, 23, '2016-03-31', '08:51:32', '18:08:42', '迟到,'),
	(167, 23, '2016-04-01', '08:53:38', '18:03:18', '迟到,'),
	(168, 23, '2016-04-02', NULL, NULL, '休息,休息'),
	(169, 23, '2016-04-03', NULL, NULL, '休息,休息'),
	(170, 23, '2016-04-04', NULL, NULL, '旷工,旷工'),
	(171, 23, '2016-04-05', '08:54:56', '18:11:34', '迟到,'),
	(172, 23, '2016-04-06', '08:52:46', '18:14:06', '迟到,'),
	(173, 23, '2016-04-07', '08:54:38', '18:18:02', '迟到,'),
	(174, 23, '2016-04-08', '08:56:36', '18:11:30', '迟到,'),
	(175, 23, '2016-04-09', NULL, NULL, '休息,休息'),
	(176, 23, '2016-04-10', NULL, NULL, '休息,休息'),
	(177, 23, '2016-04-11', NULL, NULL, '旷工,旷工'),
	(178, 23, '2016-04-12', NULL, NULL, '旷工,旷工'),
	(179, 23, '2016-04-13', '08:52:06', '18:15:32', '迟到,'),
	(180, 23, '2016-04-14', '08:46:28', '18:09:58', '迟到,'),
	(181, 23, '2016-04-15', '08:46:34', '17:51:36', '迟到,'),
	(182, 23, '2016-04-16', NULL, NULL, '休息,休息'),
	(183, 23, '2016-04-17', NULL, NULL, '休息,休息'),
	(184, 23, '2016-04-18', '08:45:50', NULL, '迟到,未刷卡'),
	(185, 23, '2016-04-19', '08:51:52', '18:10:32', '迟到,'),
	(186, 23, '2016-04-20', '08:55:38', '18:08:10', '迟到,'),
	(187, 24, '2016-03-21', NULL, NULL, '旷工,旷工'),
	(188, 24, '2016-03-22', NULL, NULL, '旷工,旷工'),
	(189, 24, '2016-03-23', NULL, NULL, '旷工,旷工'),
	(190, 24, '2016-03-24', NULL, NULL, '旷工,旷工'),
	(191, 24, '2016-03-25', NULL, NULL, '旷工,旷工'),
	(192, 24, '2016-03-26', NULL, NULL, '休息,休息'),
	(193, 24, '2016-03-27', NULL, NULL, '休息,休息'),
	(194, 24, '2016-03-28', NULL, NULL, '旷工,旷工'),
	(195, 24, '2016-03-29', NULL, NULL, '旷工,旷工'),
	(196, 24, '2016-03-30', NULL, NULL, '旷工,旷工'),
	(197, 24, '2016-03-31', NULL, NULL, '旷工,旷工'),
	(198, 24, '2016-04-01', NULL, NULL, '旷工,旷工'),
	(199, 24, '2016-04-02', NULL, NULL, '休息,休息'),
	(200, 24, '2016-04-03', NULL, NULL, '休息,休息'),
	(201, 24, '2016-04-04', NULL, NULL, '旷工,旷工'),
	(202, 24, '2016-04-05', NULL, NULL, '旷工,旷工'),
	(203, 24, '2016-04-06', NULL, NULL, '旷工,旷工'),
	(204, 24, '2016-04-07', NULL, NULL, '旷工,旷工'),
	(205, 24, '2016-04-08', NULL, NULL, '旷工,旷工'),
	(206, 24, '2016-04-09', NULL, NULL, '休息,休息'),
	(207, 24, '2016-04-10', NULL, NULL, '休息,休息'),
	(208, 24, '2016-04-11', NULL, NULL, '旷工,旷工'),
	(209, 24, '2016-04-12', NULL, NULL, '旷工,旷工'),
	(210, 24, '2016-04-13', NULL, NULL, '旷工,旷工'),
	(211, 24, '2016-04-14', NULL, NULL, '旷工,旷工'),
	(212, 24, '2016-04-15', NULL, NULL, '旷工,旷工'),
	(213, 24, '2016-04-16', NULL, NULL, '休息,休息'),
	(214, 24, '2016-04-17', NULL, NULL, '休息,休息'),
	(215, 24, '2016-04-18', NULL, NULL, '旷工,旷工'),
	(216, 24, '2016-04-19', NULL, NULL, '旷工,旷工'),
	(217, 24, '2016-04-20', NULL, NULL, '旷工,旷工');
/*!40000 ALTER TABLE `attendance_tbl` ENABLE KEYS */;

--  テーブル hsys.company_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `company_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '名称',
  `c_address` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `c_phone_number` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '电话',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公司列表';

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
CREATE TABLE IF NOT EXISTS `device_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(7) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_comment` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '说明',
  `c_user_id` int(11) DEFAULT '0' COMMENT '使用者ID',
  `c_status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 正常(0) 损坏(1) 交还(2)',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='设备表';

-- テーブル hsys.device_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `device_tbl`;
/*!40000 ALTER TABLE `device_tbl` DISABLE KEYS */;
INSERT INTO `device_tbl` (`c_id`, `c_no`, `c_comment`, `c_user_id`, `c_status`) VALUES
	(5, '4', '开发用PC', 2, 1),
	(6, '001', '开发用PC', 2, 1),
	(7, '333', '开发用PC', NULL, 0);
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

-- テーブル hsys.expense_item_tbl: ~3 rows (約) のデータをダンプしています
DELETE FROM `expense_item_tbl`;
/*!40000 ALTER TABLE `expense_item_tbl` DISABLE KEYS */;
INSERT INTO `expense_item_tbl` (`c_id`, `c_date`, `c_user_id`, `c_payee_id`, `c_num`, `c_comment`, `c_type`, `c_receipt_id`) VALUES
	(1, '2019-03-01', 2, 2, 909, '开发用PC', 0, 2),
	(3, '2019-03-06', 2, 2, 66, '222w', 1, 0),
	(4, '2019-03-06', 3, 5, 666, '222w', 0, 2),
	(5, '2019-03-06', 2, 2, 33, 'rr', 0, 2);
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

-- テーブル hsys.expense_receipt_tbl: ~7 rows (約) のデータをダンプしています
DELETE FROM `expense_receipt_tbl`;
/*!40000 ALTER TABLE `expense_receipt_tbl` DISABLE KEYS */;
INSERT INTO `expense_receipt_tbl` (`c_id`, `c_no`, `c_submit_date`, `c_type`, `c_pay_date`, `c_payee_id`, `c_status`, `c_comment`, `c_project_id`, `c_attachment_path`) VALUES
	(2, '#20190301-', NULL, 0, NULL, 3, 2, '开发用PC', NULL, NULL),
	(3, '#20190305-', NULL, 0, NULL, 2, 2, 'aaaa', NULL, NULL),
	(4, '20190305-001', NULL, 0, NULL, 2, 2, '111', NULL, NULL),
	(5, '22', NULL, 0, NULL, 21, 2, '2', NULL, NULL),
	(6, '2', NULL, 1, NULL, 2, 2, '22', NULL, NULL),
	(7, '#20190305', NULL, 2, NULL, 2, 2, '1', NULL, NULL),
	(8, '#2019030', NULL, 1, NULL, 8, 2, '2', NULL, NULL),
	(9, '#20190306-', NULL, 0, NULL, 2, 1, '开发用PC', NULL, NULL);
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

-- テーブル hsys.extra_time_tbl: ~14 rows (約) のデータをダンプしています
DELETE FROM `extra_time_tbl`;
/*!40000 ALTER TABLE `extra_time_tbl` DISABLE KEYS */;
INSERT INTO `extra_time_tbl` (`c_id`, `c_date`, `c_start`, `c_end`, `c_type`, `c_len`, `c_comment`, `c_user_id`, `c_status`, `c_meal_lunch`, `c_meal_supper`, `c_approval_user_id`, `c_approval_time`, `c_expense_item_id`) VALUES
	(2, '2019-03-27', '18:00:00', '21:00:00', 1, 3, '开发用PC333', 1, 0, 1, 1, NULL, NULL, 0),
	(3, '2019-03-27', '18:00:00', '21:00:00', 2, 3, '222w', 1, 0, 1, 1, NULL, NULL, 0),
	(58, '2019-02-28', '18:00:00', '21:00:00', 0, 3, '111', 1, 1, 0, 1, NULL, NULL, 0),
	(60, '2019-02-28', '18:00:00', '21:00:00', 0, 3, '1', 1, 1, 0, 1, NULL, NULL, 0),
	(61, '2019-03-04', '18:00:00', '21:30:00', 0, 3.5, '', 1, 1, 0, 0, NULL, NULL, 0),
	(62, '2019-03-04', '18:00:00', '21:00:00', 0, 3, '1', 1, 0, 0, 0, NULL, NULL, 0),
	(63, '2019-03-04', '18:00:00', '21:00:00', 0, 3, '1', 21, 1, 0, 1, NULL, NULL, 0),
	(65, '2019-03-05', '18:00:00', '21:00:00', 0, 3, '2', 7, 1, 0, 1, NULL, NULL, 0),
	(66, '2019-03-05', '18:00:00', '21:00:00', 0, 3, '555', 5, 1, 0, 1, NULL, NULL, 0),
	(70, '2019-03-05', '19:00:00', '21:30:00', 0, 2.5, '', 21, 0, 1, 0, NULL, NULL, 0),
	(71, '2019-03-05', '18:00:00', '21:30:00', 0, 3.5, '', 21, 0, 0, 0, NULL, NULL, 0),
	(72, '2019-03-04', '18:00:00', '21:00:00', 0, 3, 'fff', 5, 1, 0, 1, NULL, NULL, 0),
	(73, '2019-03-05', '18:00:00', '21:00:00', 0, 3, '222', 9, 0, 0, 1, NULL, NULL, 0),
	(74, '2019-03-06', '18:00:00', '21:00:00', 0, 3, '开发用PC', 1, 0, 0, 1, NULL, NULL, 0);
/*!40000 ALTER TABLE `extra_time_tbl` ENABLE KEYS */;

--  テーブル hsys.group_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `group_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_name` varchar(24) COLLATE utf8_bin NOT NULL COMMENT '组名',
  `c_parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父组ID',
  `c_level` int(11) NOT NULL DEFAULT '0' COMMENT '同一组织级别，越小级别越大',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='分组表';

-- テーブル hsys.group_tbl: ~11 rows (約) のデータをダンプしています
DELETE FROM `group_tbl`;
/*!40000 ALTER TABLE `group_tbl` DISABLE KEYS */;
INSERT INTO `group_tbl` (`c_id`, `c_name`, `c_parent_id`, `c_level`) VALUES
	(1, '5本部', 0, 0),
	(2, '研发3部', 1, 1),
	(3, '图研组', 2, 2),
	(4, 'NTTD组', 2, 2),
	(5, 'E3Cus小组', 3, 2),
	(6, 'E3Plugin小组', 3, 2),
	(7, 'PLM小组', 3, 2),
	(8, 'EP小组', 4, 2),
	(9, 'EC小组', 4, 2),
	(10, '管理本部', 0, 1),
	(11, '人力资源部', 10, 2);
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

--  テーブル hsys.project_leader_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `project_leader_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `c_project_id` int(11) NOT NULL COMMENT '项目ID',
  `c_leader_id` int(11) NOT NULL COMMENT 'LeaderID',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目和Leader关系表';

-- テーブル hsys.project_leader_tbl: ~0 rows (約) のデータをダンプしています
DELETE FROM `project_leader_tbl`;
/*!40000 ALTER TABLE `project_leader_tbl` DISABLE KEYS */;
INSERT INTO `project_leader_tbl` (`c_id`, `c_project_id`, `c_leader_id`) VALUES
	(1, 1, 7),
	(2, 1, 8),
	(3, 2, 7);
/*!40000 ALTER TABLE `project_leader_tbl` ENABLE KEYS */;

--  テーブル hsys.project_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `project_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` char(50) COLLATE utf8_bin NOT NULL COMMENT '编号',
  `c_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '项目名',
  `c_funds` float NOT NULL COMMENT '经费',
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目管理表';

-- テーブル hsys.project_tbl: ~1 rows (約) のデータをダンプしています
DELETE FROM `project_tbl`;
/*!40000 ALTER TABLE `project_tbl` DISABLE KEYS */;
INSERT INTO `project_tbl` (`c_id`, `c_no`, `c_name`, `c_funds`) VALUES
	(1, '18-D3-002', 'ZUKEN/PLM离岸开发', 10000),
	(2, '18-D3-001', 'ZUKEN/A&M离岸开发', 10000);
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

-- テーブル hsys.rest_tbl: ~4 rows (約) のデータをダンプしています
DELETE FROM `rest_tbl`;
/*!40000 ALTER TABLE `rest_tbl` DISABLE KEYS */;
INSERT INTO `rest_tbl` (`c_id`, `c_date_start`, `c_date_end`, `c_len`, `c_type`, `c_summary`, `c_user_id`, `c_status`, `c_approval_user_id`, `c_approval_time`) VALUES
	(1, '2019-03-01 08:30:00', '2019-03-01 17:30:00', 8, 0, '33', 1, 2, 6, '2019-03-05 11:17:50'),
	(2, '2019-03-01 08:30:00', '2019-03-01 17:30:00', 8, 2, '111', 21, 4, 1, '2019-03-05 13:44:40'),
	(3, '2019-03-05 08:30:00', '2019-03-05 17:30:00', 8, 3, '5', 7, 1, 7, '2019-03-05 15:15:06'),
	(5, '2019-03-05 08:30:00', '2019-03-05 17:30:00', 8, 0, '222', 21, 1, 1, '2019-03-05 13:46:25'),
	(6, '2019-03-05 08:30:00', '2019-03-05 17:30:00', 8, 0, '3', 7, 0, NULL, NULL);
/*!40000 ALTER TABLE `rest_tbl` ENABLE KEYS */;

--  テーブル hsys.user_group_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_group_tbl` (
  `c_user_id` int(11) NOT NULL COMMENT '用户ID',
  `c_group_id` int(11) NOT NULL COMMENT '组ID',
  `c_leader` int(1) NOT NULL DEFAULT '0' COMMENT '是否该组Leader 是(1) 否(0)',
  KEY `c_user_id` (`c_user_id`,`c_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户所在分组表';

-- テーブル hsys.user_group_tbl: ~15 rows (約) のデータをダンプしています
DELETE FROM `user_group_tbl`;
/*!40000 ALTER TABLE `user_group_tbl` DISABLE KEYS */;
INSERT INTO `user_group_tbl` (`c_user_id`, `c_group_id`, `c_leader`) VALUES
	(1, 4, 0),
	(8, 4, 0),
	(7, 3, 0),
	(9, 11, 0),
	(10, 11, 0),
	(2, 4, 0),
	(3, 4, 0),
	(5, 3, 0),
	(6, 2, 0),
	(11, 10, 0),
	(12, 7, 0),
	(13, 9, 0),
	(14, 2, 0),
	(17, 9, 0),
	(16, 10, 0),
	(22, 3, 0),
	(21, 5, 0);
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

-- テーブル hsys.user_role_tbl: ~34 rows (約) のデータをダンプしています
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
	(46, 11, 'device_edit', 1),
	(47, 13, 'device_edit', 1);
/*!40000 ALTER TABLE `user_role_tbl` ENABLE KEYS */;

--  テーブル hsys.user_tbl の構造をダンプしています
CREATE TABLE IF NOT EXISTS `user_tbl` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `c_no` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '工号',
  `c_name` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '名字',
  `c_phone_number` varchar(23) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `c_sex` int(11) NOT NULL DEFAULT '0' COMMENT '性别',
  `c_password` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `c_mail` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '邮件',
  `c_place` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '工作地点',
  `c_company_id` int(11) DEFAULT NULL COMMENT '公司名字(分公司名字)',
  `c_qm_id` int(11) DEFAULT NULL COMMENT 'QM ID',
  `c_address` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '家庭地址',
  `c_id_number` char(18) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `c_school` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '毕业学校',
  `c_major` varchar(16) COLLATE utf8_bin DEFAULT NULL COMMENT '主修专业',
  `c_degree` int(11) DEFAULT NULL COMMENT '学历级别 大专(0) 本科3(1) 本科2(2) 本科1(3) 硕士(4) 博士(5)',
  `c_graduate_date` date DEFAULT NULL COMMENT '毕业日期',
  `c_enter_date` date DEFAULT NULL COMMENT '入社日期',
  `c_exit_date` date DEFAULT NULL COMMENT '离职日期',
  PRIMARY KEY (`c_id`),
  KEY `c_no` (`c_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- テーブル hsys.user_tbl: ~24 rows (約) のデータをダンプしています
DELETE FROM `user_tbl`;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` (`c_id`, `c_no`, `c_name`, `c_phone_number`, `c_sex`, `c_password`, `c_mail`, `c_place`, `c_company_id`, `c_qm_id`, `c_address`, `c_id_number`, `c_school`, `c_major`, `c_degree`, `c_graduate_date`, `c_enter_date`, `c_exit_date`) VALUES
	(1, 'admin', '管理员', '13678998121458', 0, '$2a$10$yggjrP1Vte9RKgbHQ1SQs.HBJ2NKGzZIUCRASqV4E7gIPM.CJr6s.', '@asd', '1231111', 1, NULL, 'asdadadasda', 'asdasdasd', '南京林业大学', '数学', 2, '2019-02-07', '2019-02-27', NULL),
	(2, '001', '普通员工1', 'ad', 0, '$2a$10$JqeuRmMZnXx02o2fu.eqreZuuPVTVyaI2iPFGPdML3IVHIXUZsijy', '123', '111', 3, NULL, NULL, '123456789123456789', NULL, NULL, 0, NULL, NULL, NULL),
	(3, '002', '普通员工2', NULL, 1, '$2a$10$hFQXVBGlm5TdYAXWQ1h12.P/1iLKxiZKg0cYrW54rFwxZb8L4JcAK', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(4, '003', '离职员工', NULL, 0, '$2a$10$6Em9ihhpsd5hkg1vfl4jMOBoO95sWi4dF5yWzCbF08/NQHOcGwOJu', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2019-02-13'),
	(5, '004', '普通员工4', NULL, 0, '$2a$10$8Xsa/pu2uWa3Hf14YZ2o0ObYeLtGwpj2lJwVNsVQKo6B9/lMFSHRC', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(6, '010', '部长', NULL, 0, '$2a$10$FX4EX2c7pGZVPIlZuy9YPex5RY/9pY1kjuUtyS23eNzVMHf/DIQA6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(7, '005', 'PM', NULL, 0, '$2a$10$srF.6SHv/IXCtbOiQrv.TugHbcATdPjWe0t7lS8.bdwlGX7Em/AXC', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(8, '006', 'PM2', NULL, 0, '$2a$10$1US86SE1Q43qZHm3z8sehuUUJKgCwku.YxqRd3EIqU8J63SVOvkZC', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(9, '007', 'HR', NULL, 0, '$2a$10$ariIkUkiot4MpkZK74aXW.EwjQI9S1.YDPZodyQ7vKrC70g0db4uG', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(10, '008', '出勤管理秘书', NULL, 0, '$2a$10$WfQ/tntgwm1N9Pm6njx4wO5icqfcYJOhnthzT5PrJ9p2BQ6D1P1AO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(11, '009', '设备科管理员', NULL, 0, '$2a$10$petuNGphSpM.FyCmW/guRu.xmXkspgs6ZfEQKxCxJr5HtTqb1E.NO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(12, '00000001', '12345678', '12345678901234', 1, '$2a$10$zNlxXDm.OMeVKFLli0RExOL6cGPW4Ot313eE99xovyrKtXo0wUNj.', '123456789012345678901234567890', '1234567890123456', 1, NULL, '12345678901234567890', '', '1234567890123456', '1234567890123456', 2, '2019-01-01', '2019-02-01', '2020-01-01'),
	(13, '12345678', '12345678', '12345678901234', 0, '$2a$10$ArjHsVBtslVlkpPmWf0R7OsGvDWTGs4MPXpSagMGZ.oTIfrWhusse', '123456789012345678901234567890', '1234567890123456', 1, NULL, '12345678901234567890', '', '1234567890123456', '1234567890123456', 1, '2018-01-01', '2018-02-01', '2021-01-01'),
	(14, '121212', '11111', '11111111111', 0, '$2a$10$HJHJ2MY4zu8VhOyzz2OQpOzfJ7nKT7QuCIFElaFUE8mo6msrcb9mu', '1111111111', '11111111111111', 1, NULL, '1111111', '12211', '1221212', '12121212', 1, '2019-12-27', '2019-02-15', '2019-02-05'),
	(15, '121', '111111', NULL, 0, '$2a$10$XN98oLTZIKQAzNYI1ItgNei7dLnfMXqQXR07kw5nzpiwz7MpdARES', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, '2019-02-22', '2019-02-22'),
	(16, '111', '1111', NULL, 0, '$2a$10$.9Am3TwqgrdcSo3i94o92uIViPs5Ck9nRAWr9x0FX2R9iruW/UqrG', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, '2019-02-21'),
	(17, '01234567', '01234567', '', 0, '$2a$10$Fhjmqlp072O2MDpNv.pVxuIU85sEEt1kGgNGGTIW3xStQClbvH/yS', '123', NULL, 1, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(18, 'NT010', '季冬梅', NULL, 1, '$2a$10$En6kqmMj4EQLE2zStqA6weQGF4r2Y4e2NyoTk1NupXw2j5u7RKbyu', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(19, 'NT011', '顾昊', NULL, 0, '$2a$10$IQQj1EZyzz6BbJAMBUw7zuEUcPFaFVGuvEABoHQBmq5UUKXpTyswe', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(20, 'NT012', '季思健', NULL, 0, '$2a$10$MSqLwTb9LatmKgo/JtWrMu2i9Q4KB4ZFpclDLQTIextm/hU8imie6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(21, 'NT014', '翟强', 'wqw', 0, '$2a$10$OGLHGDEpmAGgFAi7CIkrNeptglT8s6oV88R.lTD.R1fqycglfYfea', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(22, 'NT015', '金凤霞', NULL, 1, '$2a$10$c.3q9UvMCzzEezc501rEJOla01G02K1wWL4rfDBxUEt4UkMjh3.vO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(23, 'NT016', '缪卫卫', NULL, 0, '$2a$10$IF7KsT6DPnzl.k77HkCCYOQUfEUDmp6ZuO2hJEN/LD6bQzhqf1Gaq', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL),
	(24, 'NT013', '陆晨', NULL, 0, '$2a$10$mh/ZN74M5QZJvT9/.ixZ2.l7nUshEFy5dJKrhkQBBXBfgTuF1gysK', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL);
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
