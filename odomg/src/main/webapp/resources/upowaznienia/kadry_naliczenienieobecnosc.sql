CREATE DATABASE  IF NOT EXISTS `kadry` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kadry`;
-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: kadry
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `naliczenienieobecnosc`
--

DROP TABLE IF EXISTS `naliczenienieobecnosc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `naliczenienieobecnosc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nieobecnosc` int DEFAULT NULL,
  `skladnikwynagrodzenia` int DEFAULT NULL,
  `kwota` double DEFAULT NULL,
  `kwotastatystyczna` double DEFAULT NULL,
  `kwotabezzus` double DEFAULT NULL,
  `kwotazus` double DEFAULT NULL,
  `skladnikistale` double DEFAULT NULL,
  `skladnikizmiennesrednia` double DEFAULT NULL,
  `sredniazailemcy` double DEFAULT NULL,
  `procentzazwolnienie` double DEFAULT NULL,
  `stawkadzienna` double DEFAULT NULL,
  `liczbagodzinroboczych` double DEFAULT NULL,
  `liczbagodzinurlopu` double DEFAULT NULL,
  `stawkadziennaredukcji` double DEFAULT NULL,
  `kwotaredukcji` double DEFAULT NULL,
  `pasekwynagrodzen` int NOT NULL,
  `jakiskladnikredukowalny` varchar(128) COLLATE utf8mb4_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_naliczenienieobecnosc_nieobecnosc1_idx` (`nieobecnosc`),
  KEY `fk_naliczenienieobecnosc_skladnikwynagrodzenia1_idx` (`skladnikwynagrodzenia`),
  KEY `fk_naliczenienieobecnosc_pasekwynagrodzen1_idx` (`pasekwynagrodzen`),
  CONSTRAINT `fk_naliczenienieobecnosc_nieobecnosc1` FOREIGN KEY (`nieobecnosc`) REFERENCES `nieobecnosc` (`id`),
  CONSTRAINT `fk_naliczenienieobecnosc_pasekwynagrodzen1` FOREIGN KEY (`pasekwynagrodzen`) REFERENCES `pasekwynagrodzen` (`id`),
  CONSTRAINT `fk_naliczenienieobecnosc_skladnikwynagrodzenia1` FOREIGN KEY (`skladnikwynagrodzenia`) REFERENCES `skladnikwynagrodzenia` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `naliczenienieobecnosc`
--

LOCK TABLES `naliczenienieobecnosc` WRITE;
/*!40000 ALTER TABLE `naliczenienieobecnosc` DISABLE KEYS */;
INSERT INTO `naliczenienieobecnosc` VALUES (42,5,1,636.36,636.36,0,0,3500,0,0,0,19.89,176,32,0,0,55,'wyn. zasadnicze'),(43,3,3,140.62,0,0,140.62,600,0,0,0,3.515625,170.66666666666666,40,0,140.62,55,'premia uznaniowa'),(44,3,1,795.45,0,0,795.45,3500,0,0,0,19.886363636363637,176,40,0,795.45,55,'wyn. zasadnicze'),(45,1,1,402.68,0,402.68,0,3020.15,0,0,0.8,80.536,0,0,116.67,583.35,55,'wyn. zasadnicze'),(46,3,2,16.41,0,0,16.41,70,0,0,0,0.41015625,170.66666666666666,40,0,16.41,55,'nadgodziny 50');
/*!40000 ALTER TABLE `naliczenienieobecnosc` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-06 21:08:25
