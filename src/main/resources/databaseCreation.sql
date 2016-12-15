--
-- Database: `cs548_batacademy`
--

DROP SCHEMA IF EXISTS `cs548_batacademy`;
CREATE SCHEMA `cs548_batacademy` ;
use `cs548_batacademy`;

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
`studentId` int(4) NOT NULL AUTO_INCREMENT,
`firstName` varchar(15) NOT NULL,
`lastName` varchar(15) NOT NULL,
`gender` varchar(1) NOT NULL,
`phone` varchar(15) NOT NULL,
`cgpa` float(3,2) NOT NULL,
`registered` tinyint(1) NOT NULL,
`password` varchar(15) NOT NULL,
primary key (`studentId`))
ENGINE=InnoDB
DEFAULT CHARACTER SET = latin1;

--
-- Table structure for table `faculty`
--

CREATE TABLE `faculty` (
`facultyId` int(2) NOT NULL AUTO_INCREMENT,
`firstName` varchar(15) NOT NULL,
`lastName` varchar(15) NOT NULL,
`gender` varchar(1) NOT NULL,
`phone` varchar(15) NOT NULL,
`designation` varchar(15) NOT NULL,
`enable` tinyint(1) NOT NULL,
`password` varchar(15) NOT NULL,
PRIMARY KEY (`facultyId`))
ENGINE=InnoDB
DEFAULT CHARACTER SET = latin1;

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
`courseId` int(2) NOT NULL AUTO_INCREMENT,
`courseName` varchar(20) NOT NULL,
`credits` int(1) NOT NULL,
`facultyId` int(2) NOT NULL,
PRIMARY KEY (`courseId`),
FOREIGN KEY (facultyId)
REFERENCES faculty(facultyId)
ON DELETE CASCADE)
ENGINE=InnoDB
DEFAULT CHARACTER SET = latin1;

--
-- Table structure for table `activity`
--

CREATE TABLE `activity` (
`studentId` int(4) NOT NULL,
`courseId` int(2) NOT NULL,
`a1` int(3) NOT NULL,
`a2` int(3) NOT NULL,
`a3` int(3) NOT NULL,
PRIMARY KEY (`studentId`, `courseId`),
FOREIGN KEY (studentId)
REFERENCES student(studentId)
ON DELETE CASCADE,
FOREIGN KEY (courseId)
REFERENCES course(courseId)
ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET = latin1;

--
-- Table structure for table `registeredCourses`
--

CREATE TABLE `registeredCourses` (
`courseId` int(2) NOT NULL,
`courseName` varchar(20) NOT NULL,
`studentId` int(4) NOT NULL,
`completed` tinyint(1) NOT NULL,
primary key (studentId, courseId),
FOREIGN KEY (studentId)
REFERENCES student(studentId)
ON DELETE CASCADE,
FOREIGN KEY (courseId)
REFERENCES course(courseId)
ON DELETE CASCADE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET = latin1;

--
-- Inserting data for table `student`
--

INSERT INTO `student` (`studentId`, `firstName`, `lastName`, `gender`, `phone`, `cgpa`, `registered`, `password`) VALUES
(1001, 'Olive', 'Silverlock', 'F', '111-111-1111', 0.00, 0, 'olive123'),
(1002, 'Mia', 'Miz', 'F', '222-222-2222', 4.00, 0, 'mia123'),
(1003, 'Kyle', 'Miz', 'M', '333-333-3333', 0.00, 0, 'kyle123');

--
-- Inserting data for table `faculty`
--

INSERT INTO `faculty` (`facultyId`, `firstName`, `lastName`, `gender`, `phone`, `designation`, `enable`, `password`) VALUES
(1, 'Alfred', 'Pennyworth', 'M', '999-999-9999', 'PRESIDENT', 0, 'alfred123'),
(2, 'Batman', '', 'M', '888-888-8888', 'BATMAN', 0, 'batman123'),
(3, 'Lucius', 'Fox', 'M', '777-777-7777', 'FACULTY', 0, 'lucius123'),
(4, 'Catwoman', '', 'F', '666-666-6666', 'FACULTY', 0, 'catwoman123');

--
-- Inserting data for table `course`
--

INSERT INTO `course` (`courseId`, `courseName`, `credits`, `facultyId`)
VALUES (11, 'Martial Arts', 3, 2), (12, 'Stealth', 3, 2), (13, 'Weaponary', 3, 2),
(14, 'Reasoning/Aptitude', 3, 3), (15, 'Dragon Style Kung Fu', 3, 4), (16, 'Jujutsu', 3, 4);

--
-- Inserting data for table `activity`
--

INSERT INTO `activity` (`studentId`, `courseId`, `a1`, `a2`, `a3`)
VALUES (1002, 13, 100, 100, 100), (1002, 14, 100, 100, 100), (1002, 15, 100, 100, 100), (1002, 16, 100, 100, 100);


--
-- Inserting data for table `registeredCourses`
--

INSERT INTO `registeredCourses` (`courseId`, `courseName`, `studentId`, `completed`) VALUES
(13, 'Weaponary', 1002, 0),
(14, 'Reasoning/Aptitude', 1002, 0),
(15, 'Dragon Style Kung Fu', 1002, 1), 
(16, 'Jujutsu', 1002, 1);
