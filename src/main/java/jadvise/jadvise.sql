-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 20, 2013 at 03:55 PM
-- Server version: 5.6.14
-- PHP Version: 5.5.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `jadvise`
--

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE IF NOT EXISTS `students` (
  `idNumber` varchar(8) NOT NULL,
  `firstName` varchar(30) NOT NULL,
  `middleInitial` varchar(1) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `gpa` varchar(5) NOT NULL,
  `homeCampus` tinyint(4) NOT NULL,
  `major` tinyint(4) NOT NULL,
  `houseNumber` varchar(10) NOT NULL,
  `street` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `zip` varchar(5) NOT NULL,
  `homePhone` varchar(16) NOT NULL,
  `cellPhone` varchar(16) NOT NULL,
  `emailAddress` varchar(200) NOT NULL,
  `CSTCoursesTakenForDegree` varchar(1000) NOT NULL,
  `CSTCoursesCurrentlyTaking` varchar(500) NOT NULL,
  `CSTCoursesToBeTakenForDegree` varchar(1000) NOT NULL,
  `notes` varchar(2500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
