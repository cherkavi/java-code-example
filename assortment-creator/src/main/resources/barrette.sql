-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 04, 2015 at 12:52 AM
-- Server version: 5.5.40-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `barrette`
--

-- --------------------------------------------------------

--
-- Table structure for table `ASSORTMENT`
--

CREATE TABLE IF NOT EXISTS `ASSORTMENT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(150) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ID_TYPE` int(11) DEFAULT NULL,
  `ID_PRICE` int(11) DEFAULT NULL,
  `BAR_CODE` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `BAR_CODE_COMPANY` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `ID_PHOTO` int(11) DEFAULT NULL,
  `CREATEDAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `ID_TYPE` (`ID_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ASSORTMENT_TYPE`
--

CREATE TABLE IF NOT EXISTS `ASSORTMENT_TYPE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATEDAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ID_PARENT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_PARENT` (`ID_PARENT`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=12 ;

--
-- Dumping data for table `ASSORTMENT_TYPE`
--

INSERT INTO `ASSORTMENT_TYPE` (`ID`, `NAME`, `DESCRIPTION`, `CREATEDAT`, `ID_PARENT`) VALUES
(7, 'серьги', NULL, '0000-00-00 00:00:00', NULL),
(8, 'браслет', NULL, '0000-00-00 00:00:00', NULL),
(9, 'заколка', NULL, '0000-00-00 00:00:00', NULL),
(10, 'ожерелье', NULL, '0000-00-00 00:00:00', NULL),
(11, 'брошь', NULL, '0000-00-00 00:00:00', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `CLIENT`
--

CREATE TABLE IF NOT EXISTS `CLIENT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BAR_CODE` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `PHONE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `NAME` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `SURNAME` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `FATHER_NAME` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION` varchar(75) COLLATE utf8_bin DEFAULT NULL,
  `PERCENT` float NOT NULL DEFAULT '0',
  `CREATEDAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `COMMODITY`
--

CREATE TABLE IF NOT EXISTS `COMMODITY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_ASSORTMENT` int(11) DEFAULT NULL,
  `QUANTITY` int(11) DEFAULT NULL,
  `ID_OPERATION` int(11) DEFAULT NULL,
  `DATE_IN_OUT` date DEFAULT NULL,
  `ID_POINT` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATEDAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ID_SERIAL` int(11) DEFAULT NULL,
  `DISCOUNT` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `CURRENCY`
--

CREATE TABLE IF NOT EXISTS `CURRENCY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DOLLAR` decimal(15,3) DEFAULT NULL,
  `ROWSTATUS` tinyint(1) DEFAULT NULL,
  `CREATEDAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `EXPENSES`
--

CREATE TABLE IF NOT EXISTS `EXPENSES` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `SIGN` int(11) DEFAULT NULL,
  `SIGN_SELLER` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `MONEY`
--

CREATE TABLE IF NOT EXISTS `MONEY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_POINT` int(11) DEFAULT NULL,
  `ID_EXPENSES` int(11) DEFAULT NULL,
  `ID_PEOPLE` int(11) DEFAULT NULL,
  `AMOUNT` decimal(15,2) DEFAULT NULL,
  `DATE_IN_OUT` date DEFAULT NULL,
  `CREATEDAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DESCRIPTION` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ID_COMMODITY` int(11) DEFAULT NULL,
  `ID_CLIENT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `OPERATION`
--

CREATE TABLE IF NOT EXISTS `OPERATION` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATEDAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `PEOPLE`
--

CREATE TABLE IF NOT EXISTS `PEOPLE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `ID_PEOPLE_POSITION` int(11) DEFAULT NULL,
  `PASSWORD` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `IS_HIDE` tinyint(1) DEFAULT NULL,
  `PERCENT` float DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `PEOPLE2POINT`
--

CREATE TABLE IF NOT EXISTS `PEOPLE2POINT` (
  `ID_PEOPLE` int(11) NOT NULL,
  `ID_POINT` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `PEOPLE_POSITION`
--

CREATE TABLE IF NOT EXISTS `PEOPLE_POSITION` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `PHOTO`
--

CREATE TABLE IF NOT EXISTS `PHOTO` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FILENAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `POINT`
--

CREATE TABLE IF NOT EXISTS `POINT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `ADDRESS` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_TRADE` tinyint(1) DEFAULT NULL,
  `PHONE` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `PRICE`
--

CREATE TABLE IF NOT EXISTS `PRICE` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRICE_BUY` decimal(15,2) DEFAULT NULL,
  `PRICE_SELL` decimal(15,2) DEFAULT NULL,
  `ROWSTATUS` tinyint(1) DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATEDAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `NEXT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `REDISCOUNT`
--

CREATE TABLE IF NOT EXISTS `REDISCOUNT` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IS_SOURCE` tinyint(1) DEFAULT NULL,
  `ID_POINT` int(11) DEFAULT NULL,
  `DATE_REDISCOUNT` date DEFAULT NULL,
  `ID_ASSORTMENT` int(11) DEFAULT NULL,
  `ID_SERIAL` int(11) DEFAULT NULL,
  `CREATEDAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `SERIAL`
--

CREATE TABLE IF NOT EXISTS `SERIAL` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BAR_CODE` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `BAR_CODE_SELLER` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `CREATEDAT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `SUPPLIER_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `SUPPLIER`
--

CREATE TABLE IF NOT EXISTS `SUPPLIER` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
