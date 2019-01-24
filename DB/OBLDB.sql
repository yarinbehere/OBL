CREATE DATABASE  IF NOT EXISTS `obldb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `obldb`;
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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `book` (
  `bookId` int(11) NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  `edition` int(11) DEFAULT NULL,
  `printData` date DEFAULT NULL,
  `category` varchar(55) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `originalQuantity` int(11) DEFAULT NULL,
  `purchaseDate` date DEFAULT NULL,
  `location` varchar(55) DEFAULT NULL,
  `pdf` varchar(150) DEFAULT NULL,
  `currentQuantity` int(11) DEFAULT NULL,
  `wanted` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'The Lord of the Rings - The Fellowship of the Ring','J. R. R. Tolkien',2,'2018-01-01','Fantasy','The Lord of the Rings is an epic high fantasy novel written by English author and scholar J. R. R. Tolkien. The story began as a sequel to Tolkien\'s 1937 fantasy novel The Hobbit, but eventually developed into a much larger work.',4,'2019-01-01','100A','temp',4,0),(2,'The Lord of the Rings - The Two Towers','J. R. R. Tolkien',1,'2018-01-01','Fantasy','The Two Towers is the second volume of J. R. R. Tolkien\'s high fantasy novel The Lord of the Rings. It is preceded by The Fellowship of the Ring and followed by The Return of the King.',3,'2018-07-02','101A','temp',3,0),(3,'Harry Potter and the Philosopher\'s Stone','J. K. Rowling',2013,'2018-01-01','Fantasy','Harry Potter and the Philosopher\'s Stone is a fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling\'s debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry.',2,'2018-07-03','200A','temp',1,0),(4,'Harry Potter and the Chamber of Secrets','J. K. Rowling',2013,'2018-01-01','Fantasy','Harry Potter and the Chamber of Secrets is a fantasy novel written by British author J. K. Rowling and the second novel in the Harry Potter series. The plot follows Harry\'s second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the school\'s corridors warn that the Chamber of Secrets has been opened and that the \"\"\"heir of Slytherin\"\"\"\" would kill all pupils who do not come from all-magical families.\"\"\"\"\"\"\"\"\"',3,'2018-12-12','201A','temp',0,0),(5,'Harry Potter and the Prisoner of Azkaban','J. K. Rowling',2010,'2018-01-01','Fantasy','Harry Potter and the Prisoner of Azkaban is a fantasy novel written by British author J. K. Rowling and the third in the Harry Potter series. The book follows Harry Potter, a young wizard, in his third year at Hogwarts School of Witchcraft and Wizardry. Along with friends Ronald Weasley and Hermione Granger, Harry investigates Sirius Black, an escaped prisoner from Azkaban who they believe is one of Lord Voldemort\'s old allies.',2,'2018-12-29','203A','temp',2,0),(6,'Alice\'s Adventures in Wonderland','Lewis Carroll',1984,'2018-01-01','Fantasy','It tells of a girl named Alice falling through a rabbit hole into a fantasy world populated by peculiar, anthropomorphic creatures. The tale plays with logic, giving the story lasting popularity with adults as well as with children.',1,'2017-09-21','314B','temp',0,0),(7,'Database Management Systems','Raghu Ramakrishnan, Johannes Gehrke',3,'2018-01-01','Software','Database Management Systems provides comprehensive and up-to-date coverage of the fundamentals of database systems. Coherent explanations and practical examples have made this one of the leading texts in the field.',7,'2019-01-01','411C','temp',6,0),(8,'Java Foundation Classes','Matthew T. Nelson',2013,'2018-01-01','Software','References are references, and tutorials are tutorials. So it is for most books in the programming pantheon. Java Foundation Classes, however, does a curious thing: it blends the genres.',4,'2018-05-25','320C','temp',0,0);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookorder`
--

DROP TABLE IF EXISTS `bookorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bookorder` (
  `bookOrderNum` int(11) NOT NULL,
  `orderDate` date NOT NULL,
  `subscriptionNumber` int(11) NOT NULL,
  `bookId` int(11) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`bookOrderNum`),
  KEY `fk_BookOrder_books1_idx` (`bookId`),
  KEY `fk_BookOrder_subscribers_idx` (`subscriptionNumber`),
  CONSTRAINT `fk_BookOrder_books1` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookid`),
  CONSTRAINT `fk_BookOrder_subscribers` FOREIGN KEY (`subscriptionNumber`) REFERENCES `subscriber` (`subscriptionnumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookorder`
--

LOCK TABLES `bookorder` WRITE;
/*!40000 ALTER TABLE `bookorder` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowedbook`
--

DROP TABLE IF EXISTS `borrowedbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `borrowedbook` (
  `subscriptionNumber` int(11) NOT NULL,
  `bookId` int(11) NOT NULL,
  `returnDate` date NOT NULL,
  `borrowDate` date NOT NULL,
  `lostBook` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`bookId`,`subscriptionNumber`),
  KEY `bookFK_idx` (`bookId`),
  KEY `fk_borrowedbook_subscriber_idx` (`subscriptionNumber`),
  CONSTRAINT `fk_borrowedbook_book` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookid`),
  CONSTRAINT `fk_borrowedbook_subscriber` FOREIGN KEY (`subscriptionNumber`) REFERENCES `subscriber` (`subscriptionnumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowedbook`
--

LOCK TABLES `borrowedbook` WRITE;
/*!40000 ALTER TABLE `borrowedbook` DISABLE KEYS */;
INSERT INTO `borrowedbook` VALUES (201,1,'2019-01-23','2019-01-15',0),(203,4,'2019-01-10','2019-01-01',0),(207,6,'2019-01-02','2019-01-01',0);
/*!40000 ALTER TABLE `borrowedbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extension`
--

DROP TABLE IF EXISTS `extension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `extension` (
  `extensionDate` date NOT NULL,
  `workerNumber` int(11) NOT NULL,
  `bookId` int(11) NOT NULL,
  `subscriptionNumber` int(11) NOT NULL,
  PRIMARY KEY (`extensionDate`,`workerNumber`),
  KEY `fk_extensions_librarians1_idx` (`workerNumber`),
  KEY `fk_extension_borrowed book1_idx` (`bookId`,`subscriptionNumber`),
  CONSTRAINT `fk_extension_borrowed book1` FOREIGN KEY (`bookId`, `subscriptionNumber`) REFERENCES `borrowedbook` (`bookid`, `subscriptionnumber`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_extensions_librarians` FOREIGN KEY (`workerNumber`) REFERENCES `librarian` (`workernumber`) ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extension`
--

LOCK TABLES `extension` WRITE;
/*!40000 ALTER TABLE `extension` DISABLE KEYS */;
/*!40000 ALTER TABLE `extension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `librarian`
--

DROP TABLE IF EXISTS `librarian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `librarian` (
  `workerNumber` int(11) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `firstName` varchar(15) DEFAULT NULL,
  `lastName` varchar(15) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `role` varchar(200) DEFAULT NULL,
  `organizationalAffiliation` varchar(100) DEFAULT NULL,
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
INSERT INTO `librarian` VALUES (1,'101','Dalia','Zeerman','dzeierman@braude.ac.il','Library Director','management'),(3,'100','Yarden ','Greenberg','yarden12@braude.ac.il','Responsible for the connection with the Department of Systems Engineering and Mathematics',' Department of Industrial and Management Engineering'),(4,'103','Sharon ','Sofer','sharonh@braude.ac.il','Responsible for the connection with the Department of Industrial and Management Engineering',' Department of Industrial and Management Engineering'),(5,'107','Erika ','Varsescu','erika@braude.ac.il','Responsible for the connection with the Department of Biotechnology Engineering and the Unit for Teaching and General Studies','Department of Biotechnology Engineering and the Unit for Teaching and General Studies'),(6,'105','Mazor','Israela','israela@braude.ac.il','Responsible for databases','Library'),(7,'104','Natalia','Kokuyev','natash@braude.ac.il','Responsible for the connection with the Department of Optical Engineering and Physics and Department of Mechanical Engineering (MA)','Department of Optical Engineering and Physics and Department of Mechanical Engineering (MA)'),(8,'106','Jana','Rosenstock','yanna70@braude.ac.il','Responsible for the connection with software and information systems engineering departments','software and information systems engineering departments');
/*!40000 ALTER TABLE `librarian` ENABLE KEYS */;
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
  `workerNumber` int(11) NOT NULL,
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
  `subscriptionNumber` int(11) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `firstName` varchar(15) NOT NULL,
  `lastName` varchar(15) NOT NULL,
  `phoneNumber` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `subscriberStatus` varchar(15) NOT NULL,
  PRIMARY KEY (`subscriptionNumber`),
  KEY `fk_subscriber_user_idx` (`userName`),
  CONSTRAINT `fk_subscriber_user` FOREIGN KEY (`userName`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriber`
--

LOCK TABLES `subscriber` WRITE;
/*!40000 ALTER TABLE `subscriber` DISABLE KEYS */;
INSERT INTO `subscriber` VALUES (200,'200','shalev','kubi','0502171234','shalevku@gmail.com','Locked'),(201,'201','yarin','belker','0502272234','yarin@gmail.com','Frozen'),(202,'202','hai','chasidi','0502373234','hai@gmail.com','Active'),(203,'203','omri','braymok','0502474234','omri@gmail.com','Active'),(204,'204','bibi','netanyahu','0502575234','bibi@gmail.com','Frozen'),(205,'205','roman','cohen','0502676234','roman@gmail.com','Locked'),(206,'206','michal','yanay','0502777234','michal@gmail.com','Frozen'),(207,'207','eyal','golan','0502878234','eyal@gmail.com','Active'),(208,'208','moshe','perets','0502712993','moshe@gmail.com','Active');
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
INSERT INTO `user` VALUES ('100','aA123456','Librarian'),('101','skre544','Manager'),('102','Aa123456','Librarian'),('103','1010abba','Librarian'),('104','5646bird','Librarian'),('105','lock55','Librarian'),('106','Aa123456','Librarian'),('107','beholy1','Librarian'),('200','12345','Subscriber'),('201','Aa1342','Subscriber'),('202','654re','Subscriber'),('203','hey123','Subscriber'),('204','204204','Subscriber'),('205','0','Subscriber'),('206','1111','Subscriber'),('207','5454aA','Subscriber'),('208','gb9789','Subscriber');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-24 22:10:25
