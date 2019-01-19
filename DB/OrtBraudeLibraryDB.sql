-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ortbraudelibrarydb
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
-- Table structure for table `bookorder`
--

DROP TABLE IF EXISTS `bookorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `bookorder` (
  `BookOrderNum` int(11) NOT NULL,
  `OrderDate` date NOT NULL,
  `subscriptionNumber` int(11) NOT NULL,
  `bookID` int(11) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`BookOrderNum`),
  KEY `fk_BookOrder_reading cards1_idx` (`subscriptionNumber`),
  KEY `fk_BookOrder_books1_idx` (`bookID`),
  CONSTRAINT `fk_BookOrder_books1` FOREIGN KEY (`bookID`) REFERENCES `books` (`bookid`),
  CONSTRAINT `fk_BookOrder_reading cards1` FOREIGN KEY (`subscriptionNumber`) REFERENCES `reading cards` (`subscriptionnumber`)
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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `books` (
  `bookID` int(11) NOT NULL,
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
  PRIMARY KEY (`bookID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'The Lord of the Rings - The Fellowship of the Ring','J. R. R. Tolkien',2,'2001-01-18','Fantasy','The Lord of the Rings is an epic high fantasy novel written by English author and scholar J. R. R. Tolkien. The story began as a sequel to Tolkien\'s 1937 fantasy novel The Hobbit, but eventually developed into a much larger work.',4,'2001-01-19','100A','temp',4,0),(2,'The Lord of the Rings - The Two Towers','J. R. R. Tolkien',1,'2001-01-18','Fantasy','The Two Towers is the second volume of J. R. R. Tolkien\'s high fantasy novel The Lord of the Rings. It is preceded by The Fellowship of the Ring and followed by The Return of the King.',3,'2002-07-18','101A','temp',3,0),(3,'Harry Potter and the Philosopher\'s Stone','J. K. Rowling',2013,'2001-01-18','Fantasy','Harry Potter and the Philosopher\'s Stone is a fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling\'s debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry.',2,'2003-07-18','200A','temp',1,0),(4,'Harry Potter and the Chamber of Secrets','J. K. Rowling',2013,'2001-01-18','Fantasy','Harry Potter and the Chamber of Secrets is a fantasy novel written by British author J. K. Rowling and the second novel in the Harry Potter series. The plot follows Harry\'s second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the school\'s corridors warn that the Chamber of Secrets\" has been opened and that the \"\"heir of Slytherin\"\" would kill all pupils who do not come from all-magical families.\"\"\"',3,'2012-12-18','201A','temp',0,0),(5,'Harry Potter and the Prisoner of Azkaban','J. K. Rowling',2010,'2001-01-18','Fantasy','Harry Potter and the Prisoner of Azkaban is a fantasy novel written by British author J. K. Rowling and the third in the Harry Potter series. The book follows Harry Potter, a young wizard, in his third year at Hogwarts School of Witchcraft and Wizardry. Along with friends Ronald Weasley and Hermione Granger, Harry investigates Sirius Black, an escaped prisoner from Azkaban who they believe is one of Lord Voldemort\'s old allies.',2,'2029-12-18','203A','temp',2,0),(6,'Alice\'s Adventures in Wonderland','Lewis Carroll',1984,'2001-01-18','Fantasy','It tells of a girl named Alice falling through a rabbit hole into a fantasy world populated by peculiar, anthropomorphic creatures. The tale plays with logic, giving the story lasting popularity with adults as well as with children.',1,'2021-09-17','314B','temp',0,0),(7,'Database Management Systems','Raghu Ramakrishnan, Johannes Gehrke',3,'2001-01-18','Software','Database Management Systems provides comprehensive and up-to-date coverage of the fundamentals of database systems. Coherent explanations and practical examples have made this one of the leading texts in the field.',7,'2001-01-19','411C','temp',6,0),(8,'Java Foundation Classes','Matthew T. Nelson',2013,'2001-01-18','Software','References are references, and tutorials are tutorials. So it is for most books in the programming pantheon. Java Foundation Classes, however, does a curious thing: it blends the genres.',4,'2025-05-18','320C','temp',0,0);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowed books`
--

DROP TABLE IF EXISTS `borrowed books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `borrowed books` (
  `subscriptionNumber` int(11) NOT NULL,
  `bookId` int(11) NOT NULL,
  `returnDate` date DEFAULT NULL,
  `borrowDate` date DEFAULT NULL,
  `lost book` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`bookId`,`subscriptionNumber`),
  KEY `bookFK_idx` (`bookId`),
  KEY `subscriberFK` (`subscriptionNumber`),
  CONSTRAINT `bookFK` FOREIGN KEY (`bookId`) REFERENCES `books` (`bookid`),
  CONSTRAINT `subscriberFK` FOREIGN KEY (`subscriptionNumber`) REFERENCES `reading cards` (`subscriptionnumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowed books`
--

LOCK TABLES `borrowed books` WRITE;
/*!40000 ALTER TABLE `borrowed books` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrowed books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extensions`
--

DROP TABLE IF EXISTS `extensions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `extensions` (
  `extension date` date NOT NULL,
  `WorkerNumber` int(11) NOT NULL,
  PRIMARY KEY (`extension date`,`WorkerNumber`),
  KEY `fk_extensions_librarians1_idx` (`WorkerNumber`),
  CONSTRAINT `fk_extensions_librarians` FOREIGN KEY (`WorkerNumber`) REFERENCES `librarians` (`workernumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extensions`
--

LOCK TABLES `extensions` WRITE;
/*!40000 ALTER TABLE `extensions` DISABLE KEYS */;
/*!40000 ALTER TABLE `extensions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `librarians`
--

DROP TABLE IF EXISTS `librarians`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `librarians` (
  `WorkerNumber` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `FirstName` varchar(15) DEFAULT NULL,
  `LastName` varchar(15) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Role` varchar(200) DEFAULT NULL,
  `OrganizationalAffiliation` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`WorkerNumber`),
  KEY `fk_librarians_users1_idx` (`userID`),
  CONSTRAINT `fk_librarians_users1` FOREIGN KEY (`userID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librarians`
--

LOCK TABLES `librarians` WRITE;
/*!40000 ALTER TABLE `librarians` DISABLE KEYS */;
INSERT INTO `librarians` VALUES (1,101,'Dalia','Zeerman','dzeierman@braude.ac.il','Library Director','NULL'),(2,102,'Maii','Ibrahim','maii@braude.ac.il','Responsible for the connection with the Department of Electrical and Electronics Engineering, the English Unit and the Department of Mechanical Engineering (BA)','NULL'),(3,100,'Yarden ','Greenberg','yarden12@braude.ac.il','Responsible for the connection with the Department of Systems Engineering and Mathematics','NULL'),(4,103,'Sharon ','Sofer','sharonh@braude.ac.il','Responsible for the connection with the Department of Industrial and Management Engineering','NULL'),(5,107,'Erika ','Varsescu','erika@braude.ac.il','Responsible for the connection with the Department of Biotechnology Engineering and the Unit for Teaching and General Studies','NULL'),(6,105,'Mazor','Israela','israela@braude.ac.il','Responsible for databases','NULL'),(7,104,'Natalia','Kokuyev','natash@braude.ac.il','Responsible for the connection with the Department of Optical Engineering and Physics and Department of Mechanical Engineering (MA)','NULL'),(8,106,'Jana','Rosenstock','yanna70@braude.ac.il','Responsible for the connection with software and information systems engineering departments','NULL');
/*!40000 ALTER TABLE `librarians` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reading cards`
--

DROP TABLE IF EXISTS `reading cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reading cards` (
  `subscriptionNumber` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `firstName` varchar(15) DEFAULT NULL,
  `lastName` varchar(15) DEFAULT NULL,
  `phoneNumber` int(11) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `subscriberStatus` varchar(15) NOT NULL,
  PRIMARY KEY (`subscriptionNumber`),
  KEY `fk_reading cards_users1_idx` (`userID`),
  CONSTRAINT `fk_reading cards_users1` FOREIGN KEY (`userID`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reading cards`
--

LOCK TABLES `reading cards` WRITE;
/*!40000 ALTER TABLE `reading cards` DISABLE KEYS */;
INSERT INTO `reading cards` VALUES (1,200,'shalev','kubi',502171234,'shalevku@gmail.com','Locked'),(2,201,'yarin','belker',502272234,'yarin@gmail.com','Frozen'),(3,202,'hai','chasidi',502373234,'hai@gmail.com','Active'),(4,203,'omri','braymok',502474234,'omri@gmail.com','Active'),(5,204,'bibi','netanyahu',502575234,'bibi@gmail.com','Frozen'),(6,205,'roman','cohen',502676234,'roman@gmail.com','Locked'),(7,206,'michal','yanay',502777234,'michal@gmail.com','Frozen'),(8,207,'eyal','golan',502878234,'eyal@gmail.com','Active'),(9,208,'moshe','peretz',502979234,'moshe@gmail.com','Active');
/*!40000 ALTER TABLE `reading cards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report history`
--

DROP TABLE IF EXISTS `report history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `report history` (
  `ReportDate` date NOT NULL,
  `ReportName` varchar(45) NOT NULL,
  `WorkerNumber` int(11) NOT NULL,
  KEY `fk_Report history_librarians1_idx` (`WorkerNumber`),
  CONSTRAINT `fk_Report history_librarians1` FOREIGN KEY (`WorkerNumber`) REFERENCES `librarians` (`workernumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report history`
--

LOCK TABLES `report history` WRITE;
/*!40000 ALTER TABLE `report history` DISABLE KEYS */;
/*!40000 ALTER TABLE `report history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (100,'aA123456'),(101,'skre544'),(102,'Aa123456'),(103,'1010abba'),(104,'5646bird'),(105,'lock55'),(106,'Aa123456'),(107,'beholy1'),(200,'12345'),(201,'Aa1342'),(202,'654re'),(203,'hey123'),(204,'204204'),(205,'0000'),(206,'1111'),(207,'5454aA'),(208,'Aa123456');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'ortbraudelibrarydb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-16 18:22:43
