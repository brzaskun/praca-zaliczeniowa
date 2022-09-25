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
-- Table structure for table `naliczeniepotracenie`
--

DROP TABLE IF EXISTS `naliczeniepotracenie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `naliczeniepotracenie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `kwota` double DEFAULT NULL,
  `skladnikpotracenia` int DEFAULT NULL,
  `pasekwynagrodzen` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_naliczeniepotracenie_skladnikpotracenia1_idx` (`skladnikpotracenia`),
  KEY `fk_naliczeniepotracenie_pasekwynagrodzen1_idx` (`pasekwynagrodzen`),
  CONSTRAINT `fk_naliczeniepotracenie_pasekwynagrodzen1` FOREIGN KEY (`pasekwynagrodzen`) REFERENCES `pasekwynagrodzen` (`id`),
  CONSTRAINT `fk_naliczeniepotracenie_skladnikpotracenia1` FOREIGN KEY (`skladnikpotracenia`) REFERENCES `skladnikpotracenia` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `naliczeniepotracenie`
--

LOCK TABLES `naliczeniepotracenie` WRITE;
/*!40000 ALTER TABLE `naliczeniepotracenie` DISABLE KEYS */;
/*!40000 ALTER TABLE `naliczeniepotracenie` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-06 21:08:26
