-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: obldb
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activitylog`
--

DROP TABLE IF EXISTS `activitylog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `activitylog` (
  `actionDate` date NOT NULL,
  `actionDescription` varchar(45) NOT NULL,
  `subscriberNumber` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activitylog`
--

LOCK TABLES `activitylog` WRITE;
/*!40000 ALTER TABLE `activitylog` DISABLE KEYS */;
/*!40000 ALTER TABLE `activitylog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `book` (
  `bookId` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `author` varchar(45) NOT NULL,
  `edition` int(11) DEFAULT NULL,
  `printData` date DEFAULT NULL,
  `category` varchar(55) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `originalQuantity` int(11) DEFAULT NULL,
  `purchaseDate` date DEFAULT NULL,
  `location` varchar(55) DEFAULT NULL,
  `pdf` varchar(150) DEFAULT NULL,
  `currentQuantity` int(11) DEFAULT NULL,
  `wanted` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES ('1','The Lord of the Rings - The Fellowship of the Ring','J. R. R. Tolkien',2,'2018-01-01','Fantasy','The Lord of the Rings is an epic high fantasy novel written by English author and scholar J. R. R. Tolkien. The story began as a sequel to Tolkien\'s 1937 fantasy novel The Hobbit, but eventually developed into a much larger work.',4,'2019-01-01','100A','/Resource/tableOfContent/The Lord of the Rings - The Fellowship of the Ring.pdf\n',4,'not wanted'),('2','The Lord of the Rings - The Two Towers','J. R. R. Tolkien',1,'2018-01-01','Fantasy','The Two Towers is the second volume of J. R. R. Tolkien\'s high fantasy novel The Lord of the Rings. It is preceded by The Fellowship of the Ring and followed by The Return of the King.',3,'2018-07-02','101A','/Resource/tableOfContent/The Lord of the Rings - The Two Towers.pdf\n',3,'wanted'),('3','Harry Potter and the Philosopher\'s Stone','J. K. Rowling',2013,'2018-01-01','Fantasy','Harry Potter and the Philosopher\'s Stone is a fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling\'s debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry.',2,'2018-07-03','200A','/Resource/tableOfContent/Harry Potter and the Philosopher\'s Stone.pdf\n',1,'not wanted'),('4','Harry Potter and the Chamber of Secrets','J. K. Rowling',2013,'2018-01-01','Fantasy','Harry Potter and the Chamber of Secrets is a fantasy novel written by British author J. K. Rowling and the second novel in the Harry Potter series. The plot follows Harry\'s second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the school\'s corridors warn that the Chamber of Secrets has been opened and that the \"\"\"heir of Slytherin\"\"\"\" would kill all pupils who do not come from all-magical families.\"\"\"\"\"\"\"\"\"',3,'2018-12-12','201A','/Resource/tableOfContent/Harry Potter and the Chamber of Secrets.pdf\n',0,'not wanted'),('5','Harry Potter and the Prisoner of Azkaban','J. K. Rowling',2010,'2018-01-01','Fantasy','Harry Potter and the Prisoner of Azkaban is a fantasy novel written by British author J. K. Rowling and the third in the Harry Potter series. The book follows Harry Potter, a young wizard, in his third year at Hogwarts School of Witchcraft and Wizardry. Along with friends Ronald Weasley and Hermione Granger, Harry investigates Sirius Black, an escaped prisoner from Azkaban who they believe is one of Lord Voldemort\'s old allies.',2,'2018-12-29','203A','/Resource/tableOfContent/Harry Potter and the Prisoner of Azkaban.pdf\n',2,'wanted'),('6','Alice\'s Adventures in Wonderland','Lewis Carroll',1984,'2018-01-01','Fantasy','It tells of a girl named Alice falling through a rabbit hole into a fantasy world populated by peculiar, anthropomorphic creatures. The tale plays with logic, giving the story lasting popularity with adults as well as with children.',1,'2017-09-21','314B','/Resource/tableOfContent/Alice\'s Adventures in Wonderland.pdf',0,'not wanted'),('7','Database Management Systems','Raghu Ramakrishnan, Johannes Gehrke',3,'2018-01-01','Software','Database Management Systems provides comprehensive and up-to-date coverage of the fundamentals of database systems. Coherent explanations and practical examples have made this one of the leading texts in the field.',7,'2019-01-01','411C','/Resource/tableOfContent/Database Management Systems (Second Edition) Table of Contents.pdf\n',6,'wanted'),('8','Java Foundation Classes','Matthew T. Nelson',2013,'2018-01-01','Software','References are references, and tutorials are tutorials. So it is for most books in the programming pantheon. Java Foundation Classes, however, does a curious thing: it blends the genres.',4,'2018-05-25','320C','/Resource/tableOfContent/Java Foundation Classes.pdf\n',0,'not wanted');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookorder`
--

DROP TABLE IF EXISTS `bookorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bookorder` (
  `bookOrderNum` int(11) NOT NULL AUTO_INCREMENT,
  `orderDate` date NOT NULL,
  `subscriptionNumber` varchar(45) NOT NULL,
  `bookId` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `orderTime` time NOT NULL,
  PRIMARY KEY (`bookOrderNum`),
  KEY `fk_BookOrder_books1_idx` (`bookId`),
  KEY `fk_BookOrder_subscribers_idx` (`subscriptionNumber`),
  CONSTRAINT `fk_BookOrder_books1` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookid`),
  CONSTRAINT `fk_BookOrder_subscribers` FOREIGN KEY (`subscriptionNumber`) REFERENCES `subscriber` (`subscriberid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookorder`
--

LOCK TABLES `bookorder` WRITE;
/*!40000 ALTER TABLE `bookorder` DISABLE KEYS */;
INSERT INTO `bookorder` VALUES (1,'2019-01-15','207','6','wanted','12:55:03'),(2,'2019-01-10','208','8','not wanted','16:20:00');
/*!40000 ALTER TABLE `bookorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookreturn`
--

DROP TABLE IF EXISTS `bookreturn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bookreturn` (
  `bookID` varchar(45) NOT NULL,
  `returnDate` date NOT NULL,
  `returnAttempts` int(11) NOT NULL,
  PRIMARY KEY (`bookID`,`returnDate`),
  KEY `fk_returnAttempts_borrowedbook_idx` (`returnDate`),
  CONSTRAINT `fk_returnAttempts_borrowedbook` FOREIGN KEY (`returnDate`) REFERENCES `borrowedbook` (`returndate`),
  CONSTRAINT `fk_returnattempts_book` FOREIGN KEY (`bookID`) REFERENCES `book` (`bookid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookreturn`
--

LOCK TABLES `bookreturn` WRITE;
/*!40000 ALTER TABLE `bookreturn` DISABLE KEYS */;
INSERT INTO `bookreturn` VALUES ('1','2019-01-23',1),('4','2019-01-10',0);
/*!40000 ALTER TABLE `bookreturn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowedbook`
--

DROP TABLE IF EXISTS `borrowedbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `borrowedbook` (
  `subscriptionNumber` varchar(45) NOT NULL,
  `bookId` varchar(45) NOT NULL,
  `returnDate` date NOT NULL,
  `borrowDate` date NOT NULL,
  `lostBook` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`bookId`,`subscriptionNumber`),
  KEY `bookFK_idx` (`bookId`),
  KEY `fk_borrowedbook_subscriber_idx` (`subscriptionNumber`) /*!80000 INVISIBLE */,
  KEY `returnDate_idx` (`returnDate`),
  CONSTRAINT `fk_borrowedbook_book` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookid`),
  CONSTRAINT `fk_borrowedbook_subscriber` FOREIGN KEY (`subscriptionNumber`) REFERENCES `subscriber` (`subscriberid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowedbook`
--

LOCK TABLES `borrowedbook` WRITE;
/*!40000 ALTER TABLE `borrowedbook` DISABLE KEYS */;
INSERT INTO `borrowedbook` VALUES ('201','1','2019-01-23','2019-01-15',0),('203','4','2019-01-10','2019-01-01',0),('207','6','2019-01-02','2019-01-01',0);
/*!40000 ALTER TABLE `borrowedbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `borrowedbook_extended`
--

DROP TABLE IF EXISTS `borrowedbook_extended`;
/*!50001 DROP VIEW IF EXISTS `borrowedbook_extended`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8mb4;
/*!50001 CREATE VIEW `borrowedbook_extended` AS SELECT 
 1 AS `subscriptionNumber`,
 1 AS `firstName`,
 1 AS `bookId`,
 1 AS `title`,
 1 AS `borrowDate`,
 1 AS `returnDate`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `librarian`
--

DROP TABLE IF EXISTS `librarian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `librarian` (
  `workerNumber` varchar(45) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `firstName` varchar(15) NOT NULL,
  `lastName` varchar(15) NOT NULL,
  `email` varchar(45) NOT NULL,
  `role` varchar(200) NOT NULL,
  `organizationalAffiliation` varchar(100) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`workerNumber`),
  KEY `fk_librarians_users1_idx` (`userName`),
  CONSTRAINT `fk_librarians_users1` FOREIGN KEY (`userName`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librarian`
--

LOCK TABLES `librarian` WRITE;
/*!40000 ALTER TABLE `librarian` DISABLE KEYS */;
INSERT INTO `librarian` VALUES ('1','101','Dalia','Zeerman','dzeierman@braude.ac.il','Library Director','management','0547552266'),('3','100','Yarden ','Greenberg','yarden12@braude.ac.il','Responsible for the connection with the Department of Systems Engineering and Mathematics',' Department of Industrial and Management Engineering','0502155960'),('4','103','Sharon ','Sofer','sharonh@braude.ac.il','Responsible for the connection with the Department of Industrial and Management Engineering',' Department of Industrial and Management Engineering','0528400142'),('5','107','Erika ','Varsescu','erika@braude.ac.il','Responsible for the connection with the Department of Biotechnology Engineering and the Unit for Teaching and General Studies','Department of Biotechnology Engineering and the Unit for Teaching and General Studies','0509998765'),('6','105','Mazor','Israela','israela@braude.ac.il','Responsible for databases','Library','0545678125'),('7','104','Natalia','Kokuyev','natash@braude.ac.il','Responsible for the connection with the Department of Optical Engineering and Physics and Department of Mechanical Engineering (MA)','Department of Optical Engineering and Physics and Department of Mechanical Engineering (MA)','0521368043'),('8','106','Jana','Rosenstock','yanna70@braude.ac.il','Responsible for the connection with software and information systems engineering departments','software and information systems engineering departments','0528795421');
/*!40000 ALTER TABLE `librarian` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manualextension`
--

DROP TABLE IF EXISTS `manualextension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `manualextension` (
  `extensionDate` date NOT NULL,
  `workerNumber` varchar(45) NOT NULL,
  `bookId` varchar(45) NOT NULL,
  `subscriptionNumber` varchar(45) NOT NULL,
  PRIMARY KEY (`extensionDate`,`workerNumber`),
  KEY `fk_extensions_librarians1_idx` (`workerNumber`),
  KEY `fk_extension_borrowed book1_idx` (`bookId`,`subscriptionNumber`),
  CONSTRAINT `fk_extension_borrowed book1` FOREIGN KEY (`bookId`, `subscriptionNumber`) REFERENCES `borrowedbook` (`bookid`, `subscriptionnumber`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_extensions_librarians` FOREIGN KEY (`workerNumber`) REFERENCES `librarian` (`workernumber`) ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manualextension`
--

LOCK TABLES `manualextension` WRITE;
/*!40000 ALTER TABLE `manualextension` DISABLE KEYS */;
/*!40000 ALTER TABLE `manualextension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporthistory`
--

DROP TABLE IF EXISTS `reporthistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reporthistory` (
  `reportDate` date NOT NULL,
  `reportName` varchar(45) NOT NULL,
  `workerNumber` varchar(45) NOT NULL,
  KEY `fk_Report history_librarians1_idx` (`workerNumber`),
  CONSTRAINT `fk_Report history_librarians1` FOREIGN KEY (`workerNumber`) REFERENCES `librarian` (`workernumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporthistory`
--

LOCK TABLES `reporthistory` WRITE;
/*!40000 ALTER TABLE `reporthistory` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporthistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriber`
--

DROP TABLE IF EXISTS `subscriber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `subscriber` (
  `subscriberId` varchar(45) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `firstName` varchar(15) NOT NULL,
  `lastName` varchar(15) NOT NULL,
  `phoneNumber` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `subscriberStatus` varchar(15) NOT NULL,
  PRIMARY KEY (`subscriberId`),
  KEY `fk_subscriber_user_idx` (`userName`),
  CONSTRAINT `fk_subscriber_user` FOREIGN KEY (`userName`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriber`
--

LOCK TABLES `subscriber` WRITE;
/*!40000 ALTER TABLE `subscriber` DISABLE KEYS */;
INSERT INTO `subscriber` VALUES ('200','200','shalev','kubi','0502171234','shalevku@gmail.com','Locked'),('201','201','yarin','belker','0502272234','yarin@gmail.com','Frozen'),('202','202','hai','chasidi','0502373234','hai@gmail.com','Active'),('203','203','omri','braymok','0502474234','omri@gmail.com','Active'),('204','204','bibi','netanyahu','0502575234','bibi@gmail.com','Frozen'),('205','205','roman','cohen','0502676234','roman@gmail.com','Locked'),('206','206','michal','yanay','0502777234','michal@gmail.com','Frozen'),('207','207','eyal','golan','0502878234','eyal@gmail.com','Active'),('208','208','moshe','perets','0502712993','moshe@gmail.com','Frozen');
/*!40000 ALTER TABLE `subscriber` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('100','12','Librarian'),('101','skre544','Manager'),('102','Aa123456','Librarian'),('103','1010abba','Librarian'),('104','5646bird','Librarian'),('105','lock55','Librarian'),('106','Aa123456','Librarian'),('107','beholy1','Librarian'),('200','12345','Subscriber'),('201','Aa1342','Subscriber'),('202','654re','Subscriber'),('203','hey123','Subscriber'),('204','204204','Subscriber'),('205','0','Subscriber'),('206','1111','Subscriber'),('207','5454aA','Subscriber'),('208','ny5147','Subscriber');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'obldb'
--

--
-- Final view structure for view `borrowedbook_extended`
--

/*!50001 DROP VIEW IF EXISTS `borrowedbook_extended`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `borrowedbook_extended` AS select `bb`.`subscriptionNumber` AS `subscriptionNumber`,`s`.`firstName` AS `firstName`,`b`.`bookId` AS `bookId`,`b`.`title` AS `title`,`bb`.`borrowDate` AS `borrowDate`,`bb`.`returnDate` AS `returnDate` from ((`borrowedbook` `bb` join `book` `b`) join `subscriber` `s`) where ((`bb`.`bookId` = `b`.`bookId`) and (`bb`.`subscriptionNumber` = `s`.`subscriberId`)) */;
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

-- Dump completed on 2019-01-31 18:04:52
