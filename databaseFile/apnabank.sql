-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 03, 2022 at 11:34 AM
-- Server version: 10.5.12-MariaDB
-- PHP Version: 7.3.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id18039971_apnabank`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `bID` int(11) NOT NULL,
  `ACC_NO` varchar(12) COLLATE utf8mb4_unicode_ci NOT NULL,
  `Balance` decimal(20,2) DEFAULT 0.00,
  `PAN_NO` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`bID`, `ACC_NO`, `Balance`, `PAN_NO`) VALUES
(100001, '123543654098', 10000000.00, 'BOZPK7777A'),
(149694, '126540986578', 20000200.00, 'BOZPK7777A'),
(243624, '148053904257', 51338.14, 'AFZPK7190K'),
(353629, '654368907645', 100000.00, 'CPZPK5757A'),
(353629, '678954309876', 100000.00, 'TQZPK5757B'),
(149694, '765478903216', 502000.00, 'AFZPK7290L'),
(243624, '786543213456', 6000000.00, 'CPZPK5757A'),
(149694, '798657909816', 100000.00, 'AFZPK8760E'),
(100001, '871243041934', 63692.50, 'AFZPK7190K'),
(243639, '876768908765', 600000.00, 'CPZPK5734B'),
(243624, '980768908765', 2000000.00, 'BOZPK7777A'),
(145684, '985437041934', 205706.60, 'AFZPK7190K'),
(100001, '987098567098', 40000000.00, 'BOZPK5747A');

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `bID` int(11) NOT NULL,
  `bNAME` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bCITY` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`bID`, `bNAME`, `bCITY`) VALUES
(100001, 'Mumbai Main Branch', 'Mumbai'),
(145684, 'Shakurpur Branch', 'Delhi'),
(149694, 'Connaught Place Branch', 'New Delhi'),
(243624, 'Hazratganj Branch', 'Lucknow'),
(243639, 'Chander Nagar Branch', 'Lucknow'),
(353629, 'Boring Road Branch', 'Patna');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `PAN_NO` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cNAME` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bDATE` date NOT NULL,
  `ADDRESS` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PhNUMBER` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `UID` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `PWD` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MPIN` char(6) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`PAN_NO`, `cNAME`, `bDATE`, `ADDRESS`, `PhNUMBER`, `UID`, `PWD`, `MPIN`) VALUES
('AFZPK7190K', 'Ramu Kumar', '1990-10-10', '55-B Rishi Nagar Delhi', '9988776655', 'ramu@1990', '1990@ramu', '136902'),
('AFZPK7290L', 'Vipin Sharma', '1980-11-18', '11-C Rani Bagh Nagar Delhi', '9979618087', 'vipin#18', '181980@vipin', '012981'),
('AFZPK8760E', 'Nidhi Sharma', '2001-01-28', '17-G Patel Nagar New Delhi', '8976541237', 'nsharma@2001', 'nidhi@28', NULL),
('BOZPK5747A', 'Amit Rawat', '1997-02-23', 'E-3 Bandra Mumbai', '7853426812', 'Amit@Rawat', '7991@rawat', '199023'),
('BOZPK7777A', 'Aman Gupta', '1991-07-07', '01-A Thane Mumbai', '9876543210', 'guptaaman@07', '0707@aman', '875094'),
('CPZPK5734B', 'Deepali Sharma', '1999-11-30', '55/189K Alambagh Lucknow', '6393047521', 'ds@3011', '1991@deepali', '583920'),
('CPZPK5757A', 'Anil Kumar', '1987-01-23', '55/124K Hussainganj Lucknow', '6393054290', 'kumar@1987', 'anil@23', NULL),
('TQZPK5757B', 'Deepak Kumar', '1989-12-30', '55-C Mahavira Nagar Patna', '6393047556', 'deepak30kumar', '1989$deepak', '044582');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `bID` int(11) NOT NULL,
  `eID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `eNAME` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bDATE` date NOT NULL,
  `ADDRESS` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phNUMBER` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`bID`, `eID`, `eNAME`, `bDATE`, `ADDRESS`, `phNUMBER`) VALUES
(145684, 'A-19356', 'Tulsi Kumar', '1985-01-23', '53-T Lajpat Nagar New Delhi', '9454120945'),
(149694, 'A-20356', 'Ramesh Rawat', '1969-04-23', '54-P Patel Nagar New Delhi', '8798120945'),
(100001, 'G-12456', 'Tilak Verma', '2002-10-29', '551/T Alambagh Lucknow', '8707740980'),
(353629, 'G-45345', 'Raghunath Singh', '1979-01-23', '22-C Indira Nagar Chapra', '9876540912'),
(243639, 'L-56765', 'Kishan Singh', '1977-02-11', '34-E Kanpur-Cantt Kanpur', '7687124509'),
(243624, 'L-98765', 'Anuj Rawat', '1997-01-31', '45-R Almora Uttrakhand', '6545609871');

-- --------------------------------------------------------

--
-- Table structure for table `loan`
--

CREATE TABLE `loan` (
  `LOAN_ID` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ACC_NO` varchar(12) COLLATE utf8mb4_unicode_ci NOT NULL,
  `LOAN_TYPE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `AMOUNT` decimal(20,2) NOT NULL,
  `DURATION_MONTHS` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `loan`
--

INSERT INTO `loan` (`LOAN_ID`, `ACC_NO`, `LOAN_TYPE`, `AMOUNT`, `DURATION_MONTHS`) VALUES
('CXYZ578709', '876768908765', 'Car Loan', 500000.00, 48),
('HJOP578098', '987098567098', 'Business Loan', 600000000.00, 120),
('REYJ983056', '980768908765', 'Home Loan', 4000000.00, 84);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `T_ID` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `TYPE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ACC_NO` varchar(12) COLLATE utf8mb4_unicode_ci NOT NULL,
  `AMOUNT` decimal(20,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`T_ID`, `TYPE`, `ACC_NO`, `AMOUNT`) VALUES
('2414219427', 'CREDIT', '148053904257', 2000.00),
('2422361915', 'CREDIT', '985437041934', 500.00),
('2532049568', 'CREDIT', '985437041934', 1006.60),
('3025373490', 'DEBIT', '148053904257', 102.00),
('3633550946', 'DEBIT', '985437041934', 50.00),
('4108126606', 'CREDIT', '765478903216', 2000.00),
('4367531696', 'CREDIT', '876768908765', 120000.00),
('4825835988', 'CREDIT', '985437041934', 250.00),
('4841175169', 'CREDIT', '985437041934', 4000.00),
('5115964080', 'DEBIT', '148053904257', 1000.00),
('5180284555', 'CREDIT', '871243041934', 102.00),
('5519020934', 'CREDIT', '871243041934', 2000.00),
('5536475486', 'DEBIT', '148053904257', 205.50),
('6154525227', 'CREDIT', '871243041934', 205.50),
('7041631049', 'CREDIT', '148053904257', 500.00),
('7384912053', 'CREDIT', '148053904257', 102.00),
('7457321936', 'DEBIT', '148053904257', 200.00),
('7810625998', 'CREDIT', '126540986578', 200.00),
('8171782595', 'CREDIT', '985437041934', 136.00),
('8664636835', 'CREDIT', '148053904257', 643.64),
('8765431696', 'DEBIT', '876768908765', 20000.00),
('8765431764', 'CREDIT', '985437041934', 50000.00),
('8925458088', 'CREDIT', '871243041934', 1000.00),
('8970168620', 'DEBIT', '148053904257', 300.00),
('9387439791', 'DEBIT', '871243041934', 126.00),
('9754820062', 'DEBIT', '148053904257', 500.00),
('9876545643', 'DEBIT', '126540986578', 5000000.00),
('9876567890', 'CREDIT', '126540986578', 10000000.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`ACC_NO`),
  ADD KEY `FK_ACCOUNT` (`bID`),
  ADD KEY `FK_PAN` (`PAN_NO`);

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`bID`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`PAN_NO`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`eID`),
  ADD KEY `FK_EMPLOYEE` (`bID`);

--
-- Indexes for table `loan`
--
ALTER TABLE `loan`
  ADD PRIMARY KEY (`LOAN_ID`),
  ADD KEY `FK_LOAN` (`ACC_NO`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`T_ID`),
  ADD KEY `FK_TRANSACTION` (`ACC_NO`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `FK_ACCOUNT` FOREIGN KEY (`bID`) REFERENCES `branch` (`bID`),
  ADD CONSTRAINT `FK_PAN` FOREIGN KEY (`PAN_NO`) REFERENCES `customer` (`PAN_NO`);

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `FK_EMPLOYEE` FOREIGN KEY (`bID`) REFERENCES `branch` (`bID`);

--
-- Constraints for table `loan`
--
ALTER TABLE `loan`
  ADD CONSTRAINT `FK_LOAN` FOREIGN KEY (`ACC_NO`) REFERENCES `account` (`ACC_NO`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `FK_TRANSACTION` FOREIGN KEY (`ACC_NO`) REFERENCES `account` (`ACC_NO`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
