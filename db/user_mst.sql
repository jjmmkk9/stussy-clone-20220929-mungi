-- --------------------------------------------------------
-- 호스트:                          web-study.ca7yqvnelwuw.ap-northeast-2.rds.amazonaws.com
-- 서버 버전:                        10.6.8-MariaDB - managed by https://aws.amazon.com/rds/
-- 서버 OS:                        Linux
-- HeidiSQL 버전:                  12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- stussy-20220929 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `stussy-20220929` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `stussy-20220929-junil`;

-- 테이블 stussy-20220929.user_mst 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_mst` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '가입 아이디',
  `oauth_username` varchar(100) DEFAULT NULL,
  `password` varchar(150) NOT NULL,
  `name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL COMMENT '가입 이메일',
  `provider` varchar(30) DEFAULT NULL COMMENT '브라우저 저장',
  `role_id` int(1) NOT NULL COMMENT 'role_mst',
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='회원가입 로그인 ';

-- 테이블 데이터 stussy-20220929.user_mst:~4 rows (대략적) 내보내기
INSERT INTO `user_mst` (`id`, `username`, `oauth_username`, `password`, `name`, `email`, `provider`, `role_id`, `create_date`, `update_date`) VALUES
	(1, 'test1@navaer.com', NULL, '$2a$10$zQejbtOQZd3BLf5PQj2PGOpaXDaKkKPKEWMVO5pGf/7OWCJtOdYRG', '조문기', 'test1@navaer.com', NULL, 3, '2022-10-08 16:29:15', '2022-10-08 16:29:15'),
	(2, 'test2@naver.com', NULL, '$2a$10$oIIgDv8xOTl.UwTDiZkSzOLMkGxRd4vzGZQbfwO9O1cA1gbBEnwJu', '조문기', 'test2@naver.com', NULL, 1, '2022-10-10 17:28:59', '2022-10-10 17:28:59'),
	(3, 'hjh9658@naver.com', NULL, '$2a$10$T6ftCJKC/tOEKYGqDpmnAOiTMZr6j/7DBD.0ST5EY7DmpOIBKro3y', '황지환', 'hjh9658@naver.com', NULL, 1, '2022-10-10 17:30:56', '2022-10-10 17:30:56'),
	(4, 'test3@naver.com', NULL, '$2a$10$SMYJGC6vtPBFQiKiUbLevuXoYZ2eugzJ3v0AVtH0wwC45QZ6Smxsi', '조문기', 'test3@naver.com', NULL, 3, '2022-10-22 13:45:25', '2022-10-22 13:45:25');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
