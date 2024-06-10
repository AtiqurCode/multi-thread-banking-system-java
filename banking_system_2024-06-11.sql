# ************************************************************
# Sequel Ace SQL dump
# Version 20046
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# Host: 127.0.0.1 (MySQL 8.0.27)
# Database: banking_system
# Generation Time: 2024-06-10 18:27:59 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table accounts
# ------------------------------------------------------------

DROP TABLE IF EXISTS `accounts`;

CREATE TABLE `accounts` (
    `account_id` int NOT NULL AUTO_INCREMENT,
    `account_holder_name` varchar(255) DEFAULT NULL,
    `balance` decimal(10,2) DEFAULT '0.00',
    PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;

INSERT INTO `accounts` (`account_id`, `account_holder_name`, `balance`)
VALUES
    (100000,'MD Atiqur Rahman',1000.00),
    (100001,'Frahim Hossain',20000.00),
    (100002,'Khokon Hasan',2500000.00),
    (100003,'Mr. Mosarrof Hossain',5000000.00),
    (100004,'Khaled Rana',222000.00),
    (100005,'Ashraful Haque',1000.00),
    (100006,'Azim Ali',1000.00),
    (100007,'Apu Roy',1900.00),
    (100008,'Hasibul Hasan',1000.00),
    (100009,'Arafat Hasan',9000.00),
    (100010,'Asfa',8734.00),
    (100011,'Afran Alam',8999.00),
    (100012,'Afrah Hasan',1822.00),
    (100013,'Afran Hasan',101349.00),
    (100014,'Amit Hasan',2000.00),
    (100015,'Asraf Hossain',40000.00),
    (100016,'Frahmin Proc',2901.00),
    (100017,'Arifa Hossain',3100.00),
    (100018,'Afran Khan',12097.00),
    (100019,'Azim Ali',4900.00),
    (100020,'MD Arfat Alam',1090.00),
    (100021,'Arifa Husna',980.00),
    (100022,'MD Mosiur Rahman',3007.00);

/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table transactions
# ------------------------------------------------------------

DROP TABLE IF EXISTS `transactions`;

CREATE TABLE `transactions` (
    `transaction_id` int NOT NULL AUTO_INCREMENT,
    `account_id` int DEFAULT NULL,
    `transaction_type` enum('DEPOSIT','WITHDRAWAL','TRANSFER') DEFAULT NULL,
    `amount` decimal(10,2) DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`transaction_id`),
    KEY `account_id` (`account_id`),
    CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;

INSERT INTO `transactions` (`transaction_id`, `account_id`, `transaction_type`, `amount`, `created_at`, `updated_at`)
VALUES
    (1,100008,'DEPOSIT',900.00,'2024-06-07 01:32:53','2024-06-07 01:32:53'),
    (2,100008,'TRANSFER',900.00,'2024-06-07 01:36:01','2024-06-07 01:36:01'),
    (3,100007,'DEPOSIT',900.00,'2024-06-07 01:36:01','2024-06-07 01:36:01'),
    (4,100016,'DEPOSIT',1000.00,'2024-06-09 23:29:54','2024-06-09 23:29:54'),
    (5,100017,'DEPOSIT',1000.00,'2024-06-09 23:31:12','2024-06-09 23:31:12'),
    (6,100016,'DEPOSIT',1001.00,'2024-06-09 23:32:05','2024-06-09 23:32:05'),
    (7,100016,'TRANSFER',100.00,'2024-06-09 23:32:42','2024-06-09 23:32:42'),
    (8,100017,'DEPOSIT',100.00,'2024-06-09 23:32:42','2024-06-09 23:32:42'),
    (9,100012,'WITHDRAWAL',120.00,'2024-06-09 23:45:22','2024-06-09 23:45:22'),
    (10,100013,'DEPOSIT',12000.00,'2024-06-09 23:46:14','2024-06-09 23:46:14'),
    (11,100018,'DEPOSIT',12097.00,'2024-06-09 23:48:08','2024-06-09 23:48:08'),
    (12,100019,'DEPOSIT',5990.00,'2024-06-10 00:49:08','2024-06-10 00:49:08'),
    (13,100021,'DEPOSIT',980.00,'2024-06-10 23:19:13','2024-06-10 23:19:13'),
    (14,100014,'DEPOSIT',999.00,'2024-06-10 23:22:01','2024-06-10 23:22:01'),
    (15,100019,'TRANSFER',1090.00,'2024-06-10 23:24:05','2024-06-10 23:24:05'),
    (16,100020,'DEPOSIT',1090.00,'2024-06-10 23:24:05','2024-06-10 23:24:05'),
    (17,100022,'DEPOSIT',1000.00,'2024-06-10 23:29:45','2024-06-10 23:29:45'),
    (18,100022,'DEPOSIT',5009.00,'2024-06-10 23:30:35','2024-06-10 23:30:35'),
    (19,100022,'WITHDRAWAL',1002.00,'2024-06-10 23:31:04','2024-06-10 23:31:04'),
    (20,100022,'TRANSFER',2000.00,'2024-06-10 23:31:40','2024-06-10 23:31:40'),
    (21,100017,'DEPOSIT',2000.00,'2024-06-10 23:31:40','2024-06-10 23:31:40');

/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
