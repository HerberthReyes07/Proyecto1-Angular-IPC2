CREATE DATABASE  IF NOT EXISTS `bd_paqueteria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bd_paqueteria`;
-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: localhost    Database: bd_paqueteria
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.22.04.1

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `nit` varchar(7) NOT NULL,
  `sexo` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'David Orozco','1234567','Masculino'),(3,'Juan Rodríguez','9876543','Masculino'),(6,'Pablo Hernández','4753642','Masculino'),(7,'Pedro Rojas','8765432','Masculino'),(8,'Julian Reyes','3275619','Masculino');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `destino`
--

DROP TABLE IF EXISTS `destino`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `destino` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cuota_destino` decimal(8,2) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `direccion` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destino`
--

LOCK TABLES `destino` WRITE;
/*!40000 ALTER TABLE `destino` DISABLE KEYS */;
INSERT INTO `destino` VALUES (1,250.00,'Guatemala-01','2da calle, 25-96 zona 1, Ciudad de Guatemala'),(2,175.50,'Chimaltenango-01','4ta calle, 20-56 zona 2, Ciudad de Chimaltenango'),(3,150.25,'Escuintla-01','6ta calle, 15-66 zona 3, Ciudad de Escuintla'),(7,201.00,'Petén-01','4ta calle, 13-61 zona 1, Ciudad de Flores');
/*!40000 ALTER TABLE `destino` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_proceso`
--

DROP TABLE IF EXISTS `detalle_proceso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_proceso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tiempo` int NOT NULL,
  `costo_proceso` decimal(8,2) NOT NULL,
  `fecha_proceso` date NOT NULL,
  `proceso_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_detalle_proceso_proceso1_idx` (`proceso_id`),
  CONSTRAINT `fk_detalle_proceso_proceso1` FOREIGN KEY (`proceso_id`) REFERENCES `proceso` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_proceso`
--

LOCK TABLES `detalle_proceso` WRITE;
/*!40000 ALTER TABLE `detalle_proceso` DISABLE KEYS */;
INSERT INTO `detalle_proceso` VALUES (1,1,90.00,'2024-04-02',2),(2,2,160.00,'2024-04-02',8),(3,2,140.50,'2024-04-02',9),(4,1,95.50,'2024-04-02',10),(5,1,90.00,'2024-04-02',3),(6,2,160.00,'2024-04-05',11);
/*!40000 ALTER TABLE `detalle_proceso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paquete`
--

DROP TABLE IF EXISTS `paquete`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paquete` (
  `id` int NOT NULL AUTO_INCREMENT,
  `peso` decimal(8,2) NOT NULL,
  `costo_envio` decimal(8,2) NOT NULL,
  `estado` int NOT NULL,
  `no_factura` int NOT NULL,
  `fecha_ingreso` date NOT NULL,
  `cliente_id` int NOT NULL,
  `destino_id` int NOT NULL,
  `parametro_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_paquete_cliente_idx` (`cliente_id`),
  KEY `fk_paquete_destino1_idx` (`destino_id`),
  KEY `fk_paquete_parametro1_idx` (`parametro_id`),
  CONSTRAINT `fk_paquete_cliente` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`),
  CONSTRAINT `fk_paquete_destino1` FOREIGN KEY (`destino_id`) REFERENCES `destino` (`id`),
  CONSTRAINT `fk_paquete_parametro1` FOREIGN KEY (`parametro_id`) REFERENCES `parametro` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paquete`
--

LOCK TABLES `paquete` WRITE;
/*!40000 ALTER TABLE `paquete` DISABLE KEYS */;
INSERT INTO `paquete` VALUES (1,3.00,550.00,4,100,'2024-03-29',3,1,1),(3,1.00,350.00,3,101,'2024-03-29',1,1,1),(4,1.10,360.00,2,102,'2024-03-29',3,1,1),(5,1.20,295.50,1,102,'2024-03-29',3,2,1),(6,1.40,315.50,1,103,'2024-03-29',7,2,1),(7,1.30,380.00,2,103,'2024-03-29',7,1,1),(8,1.50,400.00,2,104,'2024-03-29',7,1,1),(9,1.60,410.00,2,105,'2024-03-29',7,1,1),(10,1.70,345.50,3,106,'2024-03-29',1,2,1),(11,1.80,355.50,3,106,'2024-03-29',1,2,1),(12,1.90,440.00,2,107,'2024-04-02',8,1,1),(13,1.00,275.50,2,108,'2024-04-05',8,2,1);
/*!40000 ALTER TABLE `paquete` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parametro`
--

DROP TABLE IF EXISTS `parametro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parametro` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tarifa_operacion_global` decimal(8,2) NOT NULL,
  `precio_libra` decimal(8,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parametro`
--

LOCK TABLES `parametro` WRITE;
/*!40000 ALTER TABLE `parametro` DISABLE KEYS */;
INSERT INTO `parametro` VALUES (1,75.50,100.00),(3,85.50,101.00);
/*!40000 ALTER TABLE `parametro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proceso`
--

DROP TABLE IF EXISTS `proceso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proceso` (
  `id` int NOT NULL AUTO_INCREMENT,
  `realizado` tinyint NOT NULL,
  `paquete_id` int NOT NULL,
  `punto_control_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_paquete_punto_control_paquete1_idx` (`paquete_id`),
  KEY `fk_paquete_punto_control_punto_control1_idx` (`punto_control_id`),
  CONSTRAINT `fk_paquete_punto_control_paquete1` FOREIGN KEY (`paquete_id`) REFERENCES `paquete` (`id`),
  CONSTRAINT `fk_paquete_punto_control_punto_control1` FOREIGN KEY (`punto_control_id`) REFERENCES `punto_control` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proceso`
--

LOCK TABLES `proceso` WRITE;
/*!40000 ALTER TABLE `proceso` DISABLE KEYS */;
INSERT INTO `proceso` VALUES (2,1,3,1),(3,1,4,1),(4,0,7,1),(5,0,8,1),(6,0,9,1),(7,0,12,1),(8,1,3,2),(9,1,3,7),(10,1,3,8),(11,1,4,2),(12,0,4,7),(13,0,13,20);
/*!40000 ALTER TABLE `proceso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `punto_control`
--

DROP TABLE IF EXISTS `punto_control`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `punto_control` (
  `id` int NOT NULL AUTO_INCREMENT,
  `no_orden` int NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `capacidad_cola` int NOT NULL,
  `tarifa_operacion_local` decimal(8,2) NOT NULL,
  `ruta_id` int NOT NULL,
  `usuario_operador_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_punto_control_ruta1_idx` (`ruta_id`),
  KEY `fk_punto_control_usuario1_idx` (`usuario_operador_id`),
  CONSTRAINT `fk_punto_control_ruta1` FOREIGN KEY (`ruta_id`) REFERENCES `ruta` (`id`),
  CONSTRAINT `fk_punto_control_usuario1` FOREIGN KEY (`usuario_operador_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `punto_control`
--

LOCK TABLES `punto_control` WRITE;
/*!40000 ALTER TABLE `punto_control` DISABLE KEYS */;
INSERT INTO `punto_control` VALUES (1,1,'Punto-Control-A',5,90.00,1,4),(2,2,'Punto-Control-B',5,80.00,1,5),(3,1,'Punto-Control-A',5,85.50,2,4),(7,3,'Punto-Control-C',5,70.25,1,6),(8,4,'Punto-Control-D',7,95.50,1,5),(9,2,'Punto-Control-B',6,85.50,2,6),(10,3,'Punto-Control-C',8,88.25,2,14),(11,4,'Punto-Control-D',7,92.80,2,13),(12,1,'Punto-Control-A',6,85.50,3,4),(13,2,'Punto-Control-B',7,82.25,3,5),(14,3,'Punto-Control-C',5,90.75,3,6),(15,4,'Punto-Control-D',8,65.80,3,13),(20,1,'Punto-Control-A',5,85.50,7,14),(21,2,'Punto-Control-B',7,82.25,7,15),(22,3,'Punto-Control-C',6,90.75,7,15),(23,4,'Punto-Control-D',8,65.80,7,13),(24,1,'Punto-Control-A',5,78.20,8,14),(25,2,'Punto-Control-B',8,91.75,8,14),(26,3,'Punto-Control-C',3,64.90,8,13),(27,4,'Punto-Control-D',4,72.40,8,13),(28,1,'Punto-Control-A',7,95.50,10,15),(29,2,'Punto-Control-B',5,90.50,10,14);
/*!40000 ALTER TABLE `punto_control` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ruta`
--

DROP TABLE IF EXISTS `ruta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ruta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(25) NOT NULL,
  `activa` tinyint NOT NULL,
  `destino_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_ruta_destino1_idx` (`destino_id`),
  CONSTRAINT `fk_ruta_destino1` FOREIGN KEY (`destino_id`) REFERENCES `destino` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ruta`
--

LOCK TABLES `ruta` WRITE;
/*!40000 ALTER TABLE `ruta` DISABLE KEYS */;
INSERT INTO `ruta` VALUES (1,'Ruta-Guatemala-01',1,1),(2,'Ruta-Guatemala-02',1,1),(3,'Ruta-Chimaltenango-01',1,2),(7,'Ruta-Chimaltenango-02',1,2),(8,'Ruta-Escuintla-01',1,3),(9,'Ruta-Escuintla-02',1,3),(10,'Ruta-Guatemala-03',1,1);
/*!40000 ALTER TABLE `ruta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(20) NOT NULL,
  `dpi` varchar(13) NOT NULL,
  `sexo` varchar(10) NOT NULL,
  `tipo_usuario` int NOT NULL,
  `activo` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Pedro Pérez','admin1','admin1','8416301275134','Masculino',1,1),(2,'Maria González','admin2','admin2','0372562184212','Femenino',1,1),(3,'Juan López','admin3','admin3','1756391759147','Masculino',1,1),(4,'Ana Hernández','oper1','oper1','1234567890123','Femenino',2,1),(5,'José García','oper2','oper2','9876543210987','Masculino',2,1),(6,'Sofía Ramírez','oper3','oper3','2345678901234','Femenino',2,1),(7,'Carlos Morales','recep1','recep1','8901234567890','Masculino',3,1),(8,'Norma Martínez','recep2','recep2','3456789012345','Femenino',3,1),(9,'Julio Vásquez','recep3','recep3','6789012345678','Masculino',3,1),(13,'Luis Rodríguez','oper4','oper4','1111222233334','Masculino',2,1),(14,'Ana Gómez','oper5','oper5','4444555566667','Femenino',2,1),(15,'Carlos Sánchez','oper6','oper6','7777888899998','Masculino',2,1),(16,'Francisco Reyes','admin4','admin4','3486274618759','Masculino',1,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-06  4:33:51
