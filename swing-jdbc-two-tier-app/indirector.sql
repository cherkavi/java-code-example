-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 24, 2014 at 08:42 AM
-- Server version: 5.5.37-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `indirector`
--

-- --------------------------------------------------------

--
-- Table structure for table `asset`
--

CREATE TABLE IF NOT EXISTS `asset` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 PACK_KEYS=0 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `asset`
--

INSERT INTO `asset` (`id`, `name`) VALUES
(1, 'автомобиль'),
(2, 'помощник'),
(4, 'сварочный аппарат');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `parent_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `skill_category_parent` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 PACK_KEYS=0 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `parent_id`) VALUES
(2, 'сантехника', NULL),
(5, 'электрика', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `district`
--

CREATE TABLE IF NOT EXISTS `district` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `parent_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 PACK_KEYS=0 AUTO_INCREMENT=20 ;

--
-- Dumping data for table `district`
--

INSERT INTO `district` (`id`, `name`, `parent_id`) VALUES
(1, 'Киев', NULL),
(2, 'Левый берег', 1),
(3, 'Правый берег', 1),
(12, 'Печерский', 3),
(14, 'Шевченковский', 3),
(15, 'Соломенский', 3),
(16, 'Подольский', 3),
(17, 'Днепровский', 2),
(18, 'Деснянский', 2),
(19, 'Дарницкий', 2);

-- --------------------------------------------------------

--
-- Table structure for table `phone`
--

CREATE TABLE IF NOT EXISTS `phone` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `id_worker` int(10) unsigned DEFAULT NULL,
  UNIQUE KEY `phone_pk` (`id`),
  KEY `worker_phone` (`id_worker`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 PACK_KEYS=0 AUTO_INCREMENT=48 ;

--
-- Dumping data for table `phone`
--

INSERT INTO `phone` (`id`, `phone`, `description`, `id_worker`) VALUES
(46, '5412212', NULL, 21),
(47, '0954671213', NULL, 21);

-- --------------------------------------------------------

--
-- Table structure for table `phone_attempt`
--

CREATE TABLE IF NOT EXISTS `phone_attempt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_phone_dirty` int(11) NOT NULL,
  `id_result` int(11) NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `createdat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `phone_attempt`
--

INSERT INTO `phone_attempt` (`id`, `id_phone_dirty`, `id_result`, `description`, `createdat`) VALUES
(1, 2, 3, '', '2014-09-14 01:01:51'),
(2, 2, 2, '', '2014-09-14 01:10:28'),
(3, 2, 1, 'передумал', '2014-09-14 01:14:56'),
(4, 2, 1, '', '2014-09-14 01:15:28'),
(5, 1, 3, '???', '2014-09-14 01:18:38'),
(6, 2, 2, 'хочет больше денег', '2014-09-14 07:19:31');

-- --------------------------------------------------------

--
-- Table structure for table `phone_attempt_result`
--

CREATE TABLE IF NOT EXISTS `phone_attempt_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `phone_attempt_result`
--

INSERT INTO `phone_attempt_result` (`id`, `name`) VALUES
(1, 'Ошибка, услуги не оказывает'),
(2, 'Согласен на сотрудничество'),
(3, 'Отказ сотрудничать');

-- --------------------------------------------------------

--
-- Table structure for table `phone_dirty`
--

CREATE TABLE IF NOT EXISTS `phone_dirty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `phone_dirty`
--

INSERT INTO `phone_dirty` (`id`, `phone`) VALUES
(1, '0445622625'),
(2, '0445671157');

-- --------------------------------------------------------

--
-- Table structure for table `skill`
--

CREATE TABLE IF NOT EXISTS `skill` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `category_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `category2skill` (`category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 PACK_KEYS=0 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `skill`
--

INSERT INTO `skill` (`id`, `name`, `category_id`) VALUES
(1, 'установка унитаза', 2),
(2, 'установка бойлера', 2),
(7, 'установка розеток', 5),
(8, 'установка включателей', 5),
(9, 'установка смесителя', 2),
(10, 'установка ванной', 2),
(11, 'установка раковины', 2);

-- --------------------------------------------------------

--
-- Table structure for table `worker`
--

CREATE TABLE IF NOT EXISTS `worker` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `surname` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `fathername` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `id_live_district` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 PACK_KEYS=0 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `worker`
--

INSERT INTO `worker` (`id`, `surname`, `name`, `fathername`, `description`, `age`, `id_live_district`) VALUES
(21, 'two5', 'one5', 'three5', '2222 333', NULL, NULL),
(24, 'Пупков', 'Вася', '', 'вася он такой как есть ', 27, 16),
(25, '2222', '111', '', '', 32, 12);

-- --------------------------------------------------------

--
-- Table structure for table `worker2asset`
--

CREATE TABLE IF NOT EXISTS `worker2asset` (
  `id_worker` int(10) unsigned NOT NULL,
  `id_asset` int(10) unsigned NOT NULL,
  KEY `id_worker` (`id_worker`),
  KEY `id_asset` (`id_asset`),
  KEY `id_worker_2` (`id_worker`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 PACK_KEYS=0;

--
-- Dumping data for table `worker2asset`
--

INSERT INTO `worker2asset` (`id_worker`, `id_asset`) VALUES
(21, 2),
(21, 4),
(24, 1),
(24, 2),
(24, 4),
(25, 1),
(25, 2);

-- --------------------------------------------------------

--
-- Table structure for table `worker2district`
--

CREATE TABLE IF NOT EXISTS `worker2district` (
  `id_worker` int(10) unsigned NOT NULL,
  `id_district` int(10) unsigned NOT NULL,
  KEY `id_worker` (`id_worker`),
  KEY `id_district` (`id_district`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 PACK_KEYS=0;

--
-- Dumping data for table `worker2district`
--

INSERT INTO `worker2district` (`id_worker`, `id_district`) VALUES
(21, 14),
(21, 15),
(21, 17),
(24, 19),
(24, 18),
(25, 19),
(25, 18),
(25, 17);

-- --------------------------------------------------------

--
-- Table structure for table `worker2hour`
--

CREATE TABLE IF NOT EXISTS `worker2hour` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_worker` int(10) unsigned DEFAULT NULL,
  `day` int(10) unsigned NOT NULL,
  `hour` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `worker_hours` (`id_worker`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 PACK_KEYS=0 AUTO_INCREMENT=1754 ;

--
-- Dumping data for table `worker2hour`
--

INSERT INTO `worker2hour` (`id`, `id_worker`, `day`, `hour`) VALUES
(1392, 21, 4, 16),
(1393, 21, 4, 17),
(1394, 21, 4, 18),
(1395, 21, 4, 11),
(1396, 21, 4, 12),
(1397, 21, 4, 13),
(1399, 21, 4, 10),
(1400, 21, 4, 19),
(1401, 21, 5, 18),
(1402, 21, 5, 19),
(1405, 21, 5, 14),
(1406, 21, 5, 15),
(1407, 21, 5, 12),
(1408, 21, 5, 13),
(1409, 21, 5, 10),
(1410, 21, 5, 11),
(1413, 21, 2, 12),
(1414, 21, 6, 10),
(1417, 21, 6, 12),
(1419, 21, 6, 11),
(1421, 21, 6, 14),
(1423, 21, 6, 13),
(1425, 21, 6, 16),
(1427, 21, 6, 15),
(1430, 21, 6, 17),
(1440, 21, 2, 19),
(1443, 21, 3, 10),
(1444, 21, 3, 11),
(1446, 21, 3, 17),
(1459, 21, 3, 19),
(1460, 21, 3, 18),
(1461, NULL, 0, 15),
(1462, NULL, 0, 16),
(1463, NULL, 0, 14),
(1464, NULL, 0, 13),
(1465, NULL, 0, 15),
(1466, NULL, 0, 16),
(1467, NULL, 0, 14),
(1468, NULL, 0, 13),
(1469, 21, 0, 13),
(1470, 21, 0, 12),
(1471, 21, 0, 11),
(1472, 21, 0, 17),
(1473, 21, 0, 18),
(1614, 24, 4, 15),
(1615, 24, 4, 16),
(1616, 24, 4, 17),
(1617, 24, 4, 18),
(1618, 24, 4, 11),
(1619, 24, 4, 12),
(1620, 24, 4, 13),
(1621, 24, 4, 14),
(1622, 24, 4, 10),
(1623, 24, 4, 19),
(1624, 24, 5, 18),
(1625, 24, 5, 19),
(1626, 24, 5, 16),
(1627, 24, 5, 17),
(1628, 24, 5, 14),
(1629, 24, 5, 15),
(1630, 24, 5, 12),
(1631, 24, 5, 13),
(1632, 24, 5, 10),
(1633, 24, 5, 11),
(1634, 24, 2, 10),
(1635, 24, 2, 11),
(1636, 24, 2, 12),
(1637, 24, 6, 10),
(1638, 24, 2, 13),
(1639, 24, 2, 14),
(1640, 24, 6, 12),
(1641, 24, 2, 15),
(1642, 24, 6, 11),
(1643, 24, 2, 16),
(1644, 24, 6, 14),
(1645, 24, 1, 16),
(1646, 24, 6, 13),
(1647, 24, 1, 17),
(1648, 24, 6, 16),
(1649, 24, 1, 18),
(1650, 24, 6, 15),
(1651, 24, 1, 19),
(1652, 24, 6, 18),
(1653, 24, 6, 17),
(1654, 24, 6, 19),
(1655, 24, 1, 11),
(1656, 24, 1, 10),
(1657, 24, 1, 13),
(1658, 24, 1, 12),
(1659, 24, 1, 15),
(1660, 24, 1, 14),
(1661, 24, 2, 18),
(1662, 24, 2, 17),
(1663, 24, 2, 19),
(1664, 24, 3, 12),
(1665, 24, 3, 13),
(1666, 24, 3, 10),
(1667, 24, 3, 11),
(1668, 24, 3, 16),
(1669, 24, 3, 17),
(1670, 24, 3, 14),
(1671, 24, 3, 15),
(1672, 24, 0, 17),
(1673, 24, 0, 18),
(1674, 24, 0, 15),
(1675, 24, 0, 16),
(1676, 24, 0, 19),
(1677, 24, 0, 10),
(1678, 24, 0, 14),
(1679, 24, 0, 13),
(1680, 24, 0, 12),
(1681, 24, 0, 11),
(1682, 24, 3, 19),
(1683, 24, 3, 18),
(1684, 25, 4, 15),
(1685, 25, 4, 16),
(1686, 25, 4, 17),
(1687, 25, 4, 18),
(1688, 25, 4, 11),
(1689, 25, 4, 12),
(1690, 25, 4, 13),
(1691, 25, 4, 14),
(1692, 25, 4, 10),
(1693, 25, 4, 19),
(1694, 25, 5, 18),
(1695, 25, 5, 19),
(1696, 25, 5, 16),
(1697, 25, 5, 17),
(1698, 25, 5, 14),
(1699, 25, 5, 15),
(1700, 25, 5, 12),
(1701, 25, 5, 13),
(1702, 25, 5, 10),
(1703, 25, 5, 11),
(1704, 25, 2, 10),
(1705, 25, 2, 11),
(1706, 25, 2, 12),
(1707, 25, 6, 10),
(1708, 25, 2, 13),
(1709, 25, 2, 14),
(1710, 25, 6, 12),
(1711, 25, 2, 15),
(1712, 25, 6, 11),
(1713, 25, 2, 16),
(1714, 25, 6, 14),
(1715, 25, 1, 16),
(1716, 25, 6, 13),
(1717, 25, 1, 17),
(1718, 25, 6, 16),
(1719, 25, 1, 18),
(1720, 25, 6, 15),
(1721, 25, 1, 19),
(1722, 25, 6, 18),
(1723, 25, 6, 17),
(1724, 25, 6, 19),
(1725, 25, 1, 11),
(1726, 25, 1, 10),
(1727, 25, 1, 13),
(1728, 25, 1, 12),
(1729, 25, 1, 15),
(1730, 25, 1, 14),
(1731, 25, 2, 18),
(1732, 25, 2, 17),
(1733, 25, 2, 19),
(1734, 25, 3, 12),
(1735, 25, 3, 13),
(1736, 25, 3, 10),
(1737, 25, 3, 11),
(1738, 25, 3, 16),
(1739, 25, 3, 17),
(1740, 25, 3, 14),
(1741, 25, 3, 15),
(1742, 25, 0, 17),
(1743, 25, 0, 18),
(1744, 25, 0, 15),
(1745, 25, 0, 16),
(1746, 25, 0, 19),
(1747, 25, 0, 10),
(1748, 25, 0, 14),
(1749, 25, 0, 13),
(1750, 25, 0, 12),
(1751, 25, 0, 11),
(1752, 25, 3, 19),
(1753, 25, 3, 18);

-- --------------------------------------------------------

--
-- Table structure for table `worker2skill`
--

CREATE TABLE IF NOT EXISTS `worker2skill` (
  `id_worker` int(10) unsigned NOT NULL,
  `id_skill` int(10) unsigned NOT NULL,
  KEY `id_worker` (`id_worker`),
  KEY `id_skill` (`id_skill`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 PACK_KEYS=0;

--
-- Dumping data for table `worker2skill`
--

INSERT INTO `worker2skill` (`id_worker`, `id_skill`) VALUES
(21, 8),
(21, 7),
(25, 8),
(25, 11),
(25, 9),
(25, 10),
(25, 1),
(25, 2);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
