-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: ortbraudelibrarydb
-- ------------------------------------------------------
-- Server version	8.0.12

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
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `books` (
  `bookID` int(11) NOT NULL,
  `title` text,
  `author` text,
  `category` text,
  `description` text,
  `pdf` text,
  `edition` int(11) DEFAULT NULL,
  `originalQuantity` int(11) DEFAULT NULL,
  `currentQuantity` int(11) DEFAULT NULL,
  `purchaseDate` text,
  `location` text,
  `returnDate` text,
  `isAvailable` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`bookID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'The Lord of the Rings - The Fellowship of the Ring','J. R. R. Tolkien','Fantasy','The Lord of the Rings is an epic high fantasy novel written by English author and scholar J. R. R. Tolkien. The story began as a sequel to Tolkien\'s 1937 fantasy novel The Hobbit, but eventually developed into a much larger work.','temp',2,4,4,'01/01/19','100A','',1),(2,'The Lord of the Rings - The Two Towers','J. R. R. Tolkien','Fantasy','The Two Towers is the second volume of J. R. R. Tolkien\'s high fantasy novel The Lord of the Rings. It is preceded by The Fellowship of the Ring and followed by The Return of the King.','temp',1,3,3,'02/07/18','101A','',1),(3,'Harry Potter and the Philosopher\'s Stone','J. K. Rowling','Fantasy','Harry Potter and the Philosopher\'s Stone is a fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling\'s debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry.','temp',2013,2,1,'03/07/18','200A','',1),(4,'Harry Potter and the Chamber of Secrets','J. K. Rowling','Fantasy','Harry Potter and the Chamber of Secrets is a fantasy novel written by British author J. K. Rowling and the second novel in the Harry Potter series. The plot follows Harry\'s second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the school\'s corridors warn that the \"Chamber of Secrets\"\" has been opened and that the \"\"heir of Slytherin\"\" would kill all pupils who do not come from all-magical families.\"','temp',2013,3,0,'12/12/18','201A','04/01/19',0),(5,'Harry Potter and the Prisoner of Azkaban','J. K. Rowling','Fantasy','Harry Potter and the Prisoner of Azkaban is a fantasy novel written by British author J. K. Rowling and the third in the Harry Potter series. The book follows Harry Potter, a young wizard, in his third year at Hogwarts School of Witchcraft and Wizardry. Along with friends Ronald Weasley and Hermione Granger, Harry investigates Sirius Black, an escaped prisoner from Azkaban who they believe is one of Lord Voldemort\'s old allies.','temp',2010,2,2,'29/12/18','203A','',1),(6,'Alice\'s Adventures in Wonderland','Lewis Carroll','Fantasy','It tells of a girl named Alice falling through a rabbit hole into a fantasy world populated by peculiar, anthropomorphic creatures. The tale plays with logic, giving the story lasting popularity with adults as well as with children.','temp',1984,1,0,'21/09/17','314B','02/01/19',0),(7,'Database Management Systems','Raghu Ramakrishnan, Johannes Gehrke','Software','Database Management Systems provides comprehensive and up-to-date coverage of the fundamentals of database systems. Coherent explanations and practical examples have made this one of the leading texts in the field.','temp',3,7,6,'01/01/19','411C','',1),(8,'Java Foundation Classes','Matthew T. Nelson','Software','References are references, and tutorials are tutorials. So it is for most books in the programming pantheon. Java Foundation Classes, however, does a curious thing: it blends the genres.','temp',2013,4,0,'25/05/18','320C','03/01/19',0);
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
  `returnDate` date DEFAULT NULL,
  `borrowDate` date DEFAULT NULL,
  `bookId` int(11) NOT NULL,
  PRIMARY KEY (`subscriptionNumber`,`bookId`),
  KEY `bookFK_idx` (`bookId`),
  CONSTRAINT `bookFK` FOREIGN KEY (`bookId`) REFERENCES `books` (`bookid`),
  CONSTRAINT `subscriberFK` FOREIGN KEY (`subscriptionNumber`) REFERENCES `subscribers` (`subscriptionnumber`)
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
-- Table structure for table `librarians`
--

DROP TABLE IF EXISTS `librarians`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `librarians` (
  `IDNumber` int(11) NOT NULL,
  `FirstName` varchar(15) DEFAULT NULL,
  `LastName` varchar(15) DEFAULT NULL,
  `Password` varchar(15) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `WorkerNumber` int(11) NOT NULL,
  `OrganizationalAffiliation` varchar(45) DEFAULT NULL,
  `Role` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`IDNumber`,`WorkerNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `librarians`
--

LOCK TABLES `librarians` WRITE;
/*!40000 ALTER TABLE `librarians` DISABLE KEYS */;
INSERT INTO `librarians` VALUES (26532415,'Yarden ','Greenberg','Aa1234','yarden12@braude.ac.il',3,NULL,'Responsible for the connection with the Department of Systems Engineering and Mathematics'),(35621485,'Dalia','Zeerman','1234','dzeierman@braude.ac.il',1,NULL,'Library Director'),(56321568,'Maii','Ibrahim','1111','maii@braude.ac.il',2,NULL,'Responsible for the connection with the Department of Electrical and Electronics Engineering, the English Unit and the Department of Mechanical Engineering (BA)'),(125648569,'Sharon ','Sofer','00ab','sharonh@braude.ac.il',4,NULL,'Responsible for the connection with the Department of Industrial and Management Engineering'),(215632584,'Natalia','Kokuyev','5!5!5!','natash@braude.ac.il',7,NULL,'Responsible for the connection with the Department of Optical Engineering and Physics and Department of Mechanical Engineering (MA)'),(236514528,'Mazor','Israela','999A','israela@braude.ac.il',6,NULL,'Responsible for databases'),(302652145,'Jana','Rosenstock','Jana77','yanna70@braude.ac.il',8,NULL,'Responsible for the connection with software and information systems engineering departments'),(302654851,'Erika ','Varsescu','1523Y','erika@braude.ac.il',5,NULL,'Responsible for the connection with the Department of Biotechnology Engineering and the Unit for Teaching and General Studies');
/*!40000 ALTER TABLE `librarians` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library workers`
--

DROP TABLE IF EXISTS `library workers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `library workers` (
  `workerNumber` int(11) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `Role` varchar(45) NOT NULL,
  `OrganizationalAffiliation` varchar(45) NOT NULL,
  PRIMARY KEY (`workerNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library workers`
--

LOCK TABLES `library workers` WRITE;
/*!40000 ALTER TABLE `library workers` DISABLE KEYS */;
/*!40000 ALTER TABLE `library workers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reading cards -todo`
--

DROP TABLE IF EXISTS `reading cards -todo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reading cards -todo` (
  `historyBookBorrow` int(11) NOT NULL,
  `cardMalfunction` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`historyBookBorrow`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reading cards -todo`
--

LOCK TABLES `reading cards -todo` WRITE;
/*!40000 ALTER TABLE `reading cards -todo` DISABLE KEYS */;
/*!40000 ALTER TABLE `reading cards -todo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscribers`
--

DROP TABLE IF EXISTS `subscribers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `subscribers` (
  `subscriptionNumber` int(11) NOT NULL,
  `userName` text NOT NULL,
  `phoneNumber` text,
  `subscriberStatus` text NOT NULL,
  `amountOfPossesedBooks` int(11) NOT NULL,
  PRIMARY KEY (`subscriptionNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscribers`
--

LOCK TABLES `subscribers` WRITE;
/*!40000 ALTER TABLE `subscribers` DISABLE KEYS */;
INSERT INTO `subscribers` VALUES (1,'yivgeni','0502171234','Locked',1),(2,'shalevku','0502272234','Frozen',0),(3,'borlam','0502373234','Active',0),(4,'sshvilain','0502474234','Active',2),(5,'udikiryati','0502575234','Frozen',4),(6,'vamooseson','0502676234','Locked',1),(7,'jennyfromtheblock','0502777234','Frozen',2),(8,'britney123','0502878234','Active',1),(9,'hasem1a','0502979234','Active',0);
/*!40000 ALTER TABLE `subscribers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `firstName` text NOT NULL,
  `lastName` text NOT NULL,
  `email` text,
  `phoneNum` int(11) DEFAULT NULL,
  `status` text NOT NULL,
  `type` text NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Peter','Parker','peterparker@marvel.com',520000001,'Active','Librarian'),(2,'Tony','Stark','tonystark@marvel.com',500000002,'Active','Manager'),(100000000,'Deadpool','Deadpool','wadewilson@marvel.com',521000000,'Frozen','Subscriber'),(200000000,'Venom','Venom','eddiebrock@gmail.com',522000000,'Locked','Subscriber'),(203957295,'Omri','Braymok','omribraymok@gmail.com',528401211,'Active','Subscriber'),(302658935,'Hai','Sidi','haisidi@gmail.com',524594775,'Active','Subscriber'),(302962915,'Shalev','Kubi','shalevku@gmail.com',502172993,'Active','Subscriber'),(305718371,'Yarin','Belker','yarinbe@gmail.com',524884760,'Active','Subscriber'),(310458559,'Roman','Sapovich','ramidman@gmail.com',545338638,'Active','Subscriber');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-13 20:18:16
