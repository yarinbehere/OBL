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
INSERT INTO `books` VALUES (1,'The Lord of the Rings - The Fellowship of the Ring','J. R. R. Tolkien','Fantasy','The Lord of the Rings is an epic high fantasy novel written by English author and scholar J. R. R. Tolkien. The story began as a sequel to Tolkien\'s 1937 fantasy novel The Hobbit, but eventually developed into a much larger work.','temp',2,4,4,'01/01/19','100A','',1),(2,'The Lord of the Rings - The Two Towers','J. R. R. Tolkien','Fantasy','The Two Towers is the second volume of J. R. R. Tolkien\'s high fantasy novel The Lord of the Rings. It is preceded by The Fellowship of the Ring and followed by The Return of the King.','temp',1,3,3,'02/07/18','101A','',1),(3,'Harry Potter and the Philosopher\'s Stone','J. K. Rowling','Fantasy','Harry Potter and the Philosopher\'s Stone is a fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling\'s debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry.','temp',2013,2,1,'03/07/18','200A','',1),(4,'Harry Potter and the Chamber of Secrets','J. K. Rowling','Fantasy','Harry Potter and the Chamber of Secrets is a fantasy novel written by British author J. K. Rowling and the second novel in the Harry Potter series. The plot follows Harry\'s second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the school\'s corridors warn that the Chamber of Secrets\"\" has been opened and that the \"\"heir of Slytherin\"\" would kill all pupils who do not come from all-magical families.\"\"','temp',2013,3,0,'12/12/18','201A','04/01/19',0),(5,'Harry Potter and the Prisoner of Azkaban','J. K. Rowling','Fantasy','Harry Potter and the Prisoner of Azkaban is a fantasy novel written by British author J. K. Rowling and the third in the Harry Potter series. The book follows Harry Potter, a young wizard, in his third year at Hogwarts School of Witchcraft and Wizardry. Along with friends Ronald Weasley and Hermione Granger, Harry investigates Sirius Black, an escaped prisoner from Azkaban who they believe is one of Lord Voldemort\'s old allies.','temp',2010,2,2,'29/12/18','203A','',1),(6,'Alice\'s Adventures in Wonderland','Lewis Carroll','Fantasy','It tells of a girl named Alice falling through a rabbit hole into a fantasy world populated by peculiar, anthropomorphic creatures. The tale plays with logic, giving the story lasting popularity with adults as well as with children.','temp',1984,1,0,'21/09/17','314B','02/01/19',0),(7,'Database Management Systems','Raghu Ramakrishnan, Johannes Gehrke','Software','Database Management Systems provides comprehensive and up-to-date coverage of the fundamentals of database systems. Coherent explanations and practical examples have made this one of the leading texts in the field.','temp',3,7,6,'01/01/19','411C','',1),(8,'Java Foundation Classes','Matthew T. Nelson','Software','References are references, and tutorials are tutorials. So it is for most books in the programming pantheon. Java Foundation Classes, however, does a curious thing: it blends the genres.','temp',2013,4,0,'25/05/18','320C','03/01/19',0);
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
  PRIMARY KEY (`WorkerNumber`)
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
  `firstName` text,
  `lastName` text,
  `phoneNumber` int(11) DEFAULT NULL,
  `email` text,
  `subscriberStatus` text NOT NULL,
  PRIMARY KEY (`subscriptionNumber`)
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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-15 19:49:54
