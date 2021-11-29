CREATE DATABASE  IF NOT EXISTS `online_banking` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `online_banking`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: online_banking
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `idAuthority` int NOT NULL AUTO_INCREMENT,
  `authority` varchar(45) NOT NULL,
  PRIMARY KEY (`idAuthority`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,'Сотрудник'),(2,'Директор');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currency` (
  `idCurrency` int NOT NULL AUTO_INCREMENT,
  `currencyCode` varchar(45) NOT NULL,
  `currencyName` varchar(45) NOT NULL,
  PRIMARY KEY (`idCurrency`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency`
--

LOCK TABLES `currency` WRITE;
/*!40000 ALTER TABLE `currency` DISABLE KEYS */;
INSERT INTO `currency` VALUES (1,'RU','Российский рубль'),(2,'BYN','Белорусский рубль'),(3,'USD','Доллар США');
/*!40000 ALTER TABLE `currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `familyaccount`
--

DROP TABLE IF EXISTS `familyaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `familyaccount` (
  `idFamilyAccount` int NOT NULL AUTO_INCREMENT,
  `sum` float NOT NULL,
  `familyLogin` varchar(45) NOT NULL,
  `familyPassword` varchar(255) NOT NULL,
  `idCurrency` int NOT NULL,
  PRIMARY KEY (`idFamilyAccount`),
  KEY `familyAccountIdCurrency_idx` (`idCurrency`),
  CONSTRAINT `familyAccountIdCurrency` FOREIGN KEY (`idCurrency`) REFERENCES `currency` (`idCurrency`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `familyaccount`
--

LOCK TABLES `familyaccount` WRITE;
/*!40000 ALTER TABLE `familyaccount` DISABLE KEYS */;
INSERT INTO `familyaccount` VALUES (1,88107,'Epam','123',1),(2,1131,'MTZ','log',2),(8,12377,'ITechArt','rty',2),(9,888,'BelITSoft','hhh',3),(10,550,'Integral','123',1);
/*!40000 ALTER TABLE `familyaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `familyaccount_view`
--

DROP TABLE IF EXISTS `familyaccount_view`;
/*!50001 DROP VIEW IF EXISTS `familyaccount_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `familyaccount_view` AS SELECT 
 1 AS `idFamilyAccount`,
 1 AS `sum`,
 1 AS `familyLogin`,
 1 AS `familyPassword`,
 1 AS `idCurrency`,
 1 AS `currencyCode`,
 1 AS `currencyName`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `idTransaction` int NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `sum` float NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `idUser` int NOT NULL,
  `idType` int NOT NULL,
  PRIMARY KEY (`idTransaction`),
  KEY `transactionIdUser_idx` (`idUser`),
  KEY `transactionIdType_idx` (`idType`),
  CONSTRAINT `transactionIdType` FOREIGN KEY (`idType`) REFERENCES `type` (`idType`),
  CONSTRAINT `transactionIdUser` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,'Новая операция..',330,'2021-11-03 17:35:29',1,1),(32,NULL,440,'2021-10-02 07:53:37',5,6),(33,NULL,444,'2021-10-02 07:56:13',5,7),(34,NULL,559,'2021-10-02 07:58:15',5,1),(35,NULL,44,'2021-10-02 07:58:39',5,7),(36,NULL,32,'2021-10-02 07:59:08',5,1),(37,NULL,76,'2021-10-02 07:59:29',5,8),(38,'yuiyuyu',660,'2021-10-01 21:12:34',5,1),(41,'',2500,'2021-10-01 22:42:33',3,1),(42,'',5500,'2021-10-01 22:42:47',3,1),(44,'',440,'2021-10-01 22:44:01',3,7),(45,'',1000,'2021-10-01 22:44:15',3,9);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`mysql`@`127.0.0.1`*/ /*!50003 TRIGGER `transaction_AFTER_INSERT` AFTER INSERT ON `transaction` FOR EACH ROW BEGIN
	DECLARE  id INT;
    DECLARE sumNew float;
    DECLARE categoryType VARCHAR(45);
    SET categoryType = (SELECT `category` FROM `type` WHERE  `type`.`idType` = NEW.`idType`);
    IF categoryType LIKE "Расход" THEN
		SET sumNew = (-1)*(NEW.`sum`);
	ELSE
		SET sumNew = NEW.`sum`;
	END IF;
    SET id = (SELECT `idFamilyAccount` FROM `user` WHERE `idUser` = NEW.`idUser`);
    UPDATE `familyAccount`
    SET `familyAccount`.sum = `familyAccount`.sum + sumNew
    WHERE `familyAccount`.`idFamilyAccount` = id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Temporary view structure for view `transaction_view`
--

DROP TABLE IF EXISTS `transaction_view`;
/*!50001 DROP VIEW IF EXISTS `transaction_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `transaction_view` AS SELECT 
 1 AS `idTransaction`,
 1 AS `comment`,
 1 AS `sum`,
 1 AS `date`,
 1 AS `idUser`,
 1 AS `idType`,
 1 AS `firstName`,
 1 AS `lastName`,
 1 AS `idFamilyAccount`,
 1 AS `category`,
 1 AS `name`,
 1 AS `authority`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type` (
  `idType` int NOT NULL AUTO_INCREMENT,
  `category` enum('Расход','Доход') NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idType`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES (1,'Доход','Поступление ЗП'),(2,'Доход','Поступление Аванса'),(3,'Доход','Премия'),(4,'Доход','Перевод на карту'),(5,'Расход','Оплата в магазине'),(6,'Расход','Оплата кредита'),(7,'Расход','Оплата в магазине'),(8,'Расход','Оплата в такси'),(9,'Расход','Комисия за уведомления'),(10,'Расход','Перевод на другую карту'),(11,'Расход','Оплата паркинга'),(12,'Расход','Коммунальный платеж');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `phoneNumber` varchar(45) NOT NULL,
  `isBlocked` varchar(45) NOT NULL DEFAULT 'Активен',
  `idFamilyAccount` int NOT NULL,
  `idAuthority` int NOT NULL,
  PRIMARY KEY (`idUser`),
  KEY `userIdFamilyAccount_idx` (`idFamilyAccount`),
  KEY `userIdAuthority_idx` (`idAuthority`),
  CONSTRAINT `userIdAuthority` FOREIGN KEY (`idAuthority`) REFERENCES `authority` (`idAuthority`),
  CONSTRAINT `userIdFamilyAccount` FOREIGN KEY (`idFamilyAccount`) REFERENCES `familyaccount` (`idFamilyAccount`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Петров','Петр','petrov','petr','+375 33 808-21-22','Активен',1,1),(2,'Елизавета','Елизина','lisa','lisa','+375 33 808-44-33','Активен',1,2),(3,'Николай','Павловский','pavlo','123','+375 33 808-86-54','Активен',1,2),(5,'Роман','Зайцев','rty','rty','8(029) 302-12-23','Заблокирован',2,2),(11,'Арсений','Романов','ivanj','ivanj','375 33 23782983','Активен',2,1),(12,'asd','fgh','qwe','rty','zxc','Активен',1,2),(13,'ooo','ppp','uuu','iii','kkk','Активен',1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `user_view`
--

DROP TABLE IF EXISTS `user_view`;
/*!50001 DROP VIEW IF EXISTS `user_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `user_view` AS SELECT 
 1 AS `idUser`,
 1 AS `firstName`,
 1 AS `lastName`,
 1 AS `login`,
 1 AS `password`,
 1 AS `phoneNumber`,
 1 AS `isBlocked`,
 1 AS `idFamilyAccount`,
 1 AS `idAuthority`,
 1 AS `sum`,
 1 AS `familyLogin`,
 1 AS `familyPassword`,
 1 AS `idCurrency`,
 1 AS `currencyCode`,
 1 AS `currencyName`,
 1 AS `authority`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'courses'
--

--
-- Dumping routines for database 'courses'
--

--
-- Final view structure for view `familyaccount_view`
--

/*!50001 DROP VIEW IF EXISTS `familyaccount_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mysql`@`127.0.0.1` SQL SECURITY DEFINER */
/*!50001 VIEW `familyaccount_view` AS select `familyaccount`.`idFamilyAccount` AS `idFamilyAccount`,`familyaccount`.`sum` AS `sum`,`familyaccount`.`familyLogin` AS `familyLogin`,`familyaccount`.`familyPassword` AS `familyPassword`,`familyaccount`.`idCurrency` AS `idCurrency`,`currency`.`currencyCode` AS `currencyCode`,`currency`.`currencyName` AS `currencyName` from (`familyaccount` left join `currency` on((`currency`.`idCurrency` = `familyaccount`.`idCurrency`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `transaction_view`
--

/*!50001 DROP VIEW IF EXISTS `transaction_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mysql`@`127.0.0.1` SQL SECURITY DEFINER */
/*!50001 VIEW `transaction_view` AS select `transaction`.`idTransaction` AS `idTransaction`,`transaction`.`comment` AS `comment`,`transaction`.`sum` AS `sum`,`transaction`.`date` AS `date`,`user`.`idUser` AS `idUser`,`type`.`idType` AS `idType`,`user`.`firstName` AS `firstName`,`user`.`lastName` AS `lastName`,`user`.`idFamilyAccount` AS `idFamilyAccount`,`type`.`category` AS `category`,`type`.`name` AS `name`,`authority`.`authority` AS `authority` from (((`transaction` left join `user` on((`transaction`.`idUser` = `user`.`idUser`))) left join `type` on((`transaction`.`idType` = `type`.`idType`))) left join `authority` on((`user`.`idAuthority` = `authority`.`idAuthority`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `user_view`
--

/*!50001 DROP VIEW IF EXISTS `user_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`mysql`@`127.0.0.1` SQL SECURITY DEFINER */
/*!50001 VIEW `user_view` AS select `user`.`idUser` AS `idUser`,`user`.`firstName` AS `firstName`,`user`.`lastName` AS `lastName`,`user`.`login` AS `login`,`user`.`password` AS `password`,`user`.`phoneNumber` AS `phoneNumber`,`user`.`isBlocked` AS `isBlocked`,`user`.`idFamilyAccount` AS `idFamilyAccount`,`user`.`idAuthority` AS `idAuthority`,`familyaccount`.`sum` AS `sum`,`familyaccount`.`familyLogin` AS `familyLogin`,`familyaccount`.`familyPassword` AS `familyPassword`,`familyaccount`.`idCurrency` AS `idCurrency`,`currency`.`currencyCode` AS `currencyCode`,`currency`.`currencyName` AS `currencyName`,`authority`.`authority` AS `authority` from (((`user` left join `familyaccount` on((`familyaccount`.`idFamilyAccount` = `user`.`idFamilyAccount`))) left join `currency` on((`currency`.`idCurrency` = `familyaccount`.`idCurrency`))) left join `authority` on((`user`.`idAuthority` = `authority`.`idAuthority`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-16 19:54:19
