-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 21, 2023 at 11:24 PM
-- Server version: 10.1.22-MariaDB
-- PHP Version: 7.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `xyz`
--

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `name` varchar(111) NOT NULL,
  `emp_id` int(111) NOT NULL,
  `license` varchar(111) NOT NULL DEFAULT 'N/A',
  `position` varchar(111) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`name`, `emp_id`, `license`, `position`) VALUES
('KOROM KOROM', 52434, '23242', 'Driver'),
('DEBRA xyz', 2343242, '334344', 'Manager');

-- --------------------------------------------------------

--
-- Table structure for table `hardware`
--

CREATE TABLE `hardware` (
  `SN` int(111) NOT NULL,
  `asset_no` int(111) NOT NULL,
  `asset_name` varchar(111) NOT NULL,
  `description` varchar(111) NOT NULL,
  `model` varchar(111) NOT NULL,
  `model_no` varchar(111) NOT NULL,
  `serial_no` varchar(111) NOT NULL,
  `date_received` date NOT NULL,
  `purchase_price` int(111) NOT NULL,
  `staff` varchar(111) NOT NULL,
  `asset_condition` varchar(111) NOT NULL,
  `status` varchar(111) NOT NULL DEFAULT 'Available'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hardware`
--

INSERT INTO `hardware` (`SN`, `asset_no`, `asset_name`, `description`, `model`, `model_no`, `serial_no`, `date_received`, `purchase_price`, `staff`, `asset_condition`, `status`) VALUES
(1, 1, 'Crimmping Tool', 'New', 'N/A', 'CT001', '1235368', '2023-04-22', 2500, '101', '-----------', 'Available'),
(2, 2, 'HAMMER', 'RED METALLIC', 'CHINA', 'CH23847', '242397234927', '2023-04-22', 450, 'Snr ICTO', 'Good Condition', 'Available'),
(3, 3, 'CAT-6 CABLE', 'DARK', '2323', 'GDGRTR1212', '353465623', '2023-04-22', 4500, 'Snr ICTO', 'Good Condition', 'Issued'),
(4, 4, 'COMPUTER', 'GYUUVG N', 'NU', '988U8676', '2345675643123456', '2023-04-22', 40000, 'Snr ICTO', 'Good Condition', 'Issued'),
(5, 5, 'computer', 'hybnol', 'hp', '7878978', '4567', '2023-04-22', 789, 'INTERN', 'Good Condition', 'Available'),
(6, 6, 'vbn', 'xcvbnm', 'sdfgh', 'cvbn', 'c v', '2023-04-22', 34567, 'Snr ICTO', 'Good Condition', 'Issued'),
(7, 7, 'vbn', 'xcvbnm', 'sdfgh', 'cvbn', 'c v', '2023-04-22', 34567, 'Snr ICTO', 'Good Condition', 'Available'),
(8, 8, 'crimping tool', 'TYTRE', 'CT 43', '34534TGG', '45435422002', '2023-04-22', 4500, 'Snr ICTO', 'Good Condition', 'Available'),
(9, 9, 'COMPUTER', 'GJKKUJ', 'ACER', 'A123', '7980', '2023-04-22', 100, 'ICTO', 'Broken/Faulty', 'Issued'),
(10, 10, 'Jembe', 'received', 'Ex Japan', 'j3', 'j3424', '2023-04-22', 120, 'user', 'Old Good', 'Issued');

-- --------------------------------------------------------

--
-- Table structure for table `issue`
--

CREATE TABLE `issue` (
  `issue_no` varchar(111) NOT NULL,
  `total_items` int(111) NOT NULL,
  `issued_by` varchar(111) NOT NULL,
  `issuer_designation` varchar(111) NOT NULL,
  `Date_issued` date NOT NULL,
  `Return_date` date NOT NULL,
  `received_by` varchar(111) NOT NULL,
  `receiver_designation` varchar(111) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `issue`
--

INSERT INTO `issue` (`issue_no`, `total_items`, `issued_by`, `issuer_designation`, `Date_issued`, `Return_date`, `received_by`, `receiver_designation`) VALUES
('1', 2, 'Juma Koech', 'Engineer', '2019-09-02', '2019-09-07', 'KOROM KOROM', 'Driver'),
('2', 2, 'PAT', 'Engineer', '2023-03-06', '2023-03-12', 'DEBRA JEPKOSGEI', 'Manager'),
('3', 1, '', 'Engineer', '2023-04-22', '2023-04-24', 'KOROM KOROM', 'Driver'),
('4', 1, 'sdgsdfg', 'Engineer', '2023-04-22', '2023-04-24', 'DEBRA xyz', 'Manager');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `SN` int(111) NOT NULL,
  `Issue_No` int(111) NOT NULL,
  `Code_No` varchar(111) NOT NULL,
  `Asset_No` varchar(111) NOT NULL,
  `Asset_Name` varchar(111) NOT NULL,
  `model` varchar(111) NOT NULL DEFAULT 'N/A',
  `Serial_no` varchar(111) NOT NULL DEFAULT 'N/A',
  `Number_of_days` int(111) NOT NULL DEFAULT '1',
  `return_date` date NOT NULL,
  `Date` date NOT NULL,
  `Status` varchar(111) NOT NULL DEFAULT 'Active',
  `Cleared` varchar(111) NOT NULL DEFAULT 'Yes'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`SN`, `Issue_No`, `Code_No`, `Asset_No`, `Asset_Name`, `model`, `Serial_no`, `Number_of_days`, `return_date`, `Date`, `Status`, `Cleared`) VALUES
(117, 1, '1', '1', 'Crimmping Tool', 'N/A', '1235368', 5, '2019-09-07', '2023-04-22', 'Inactive', 'Yes'),
(118, 1, '2', '8', 'crimping tool', 'CT 43', '45435422002', 5, '2019-09-07', '2019-09-02', 'Inactive', 'Yes'),
(119, 2, '1', '3', 'CAT-6 CABLE', '2323', '353465623', 6, '2023-03-12', '2023-03-06', 'Inactive', 'Yes'),
(120, 2, '2', '5', 'computer', 'hp', '4567', 6, '2023-03-12', '2023-03-06', 'Inactive', 'Yes'),
(121, 3, '1', '3', 'CAT-6 CABLE', '2323', '353465623', 2, '2023-04-24', '2023-04-22', 'Inactive', 'No'),
(122, 4, '1', '4', 'COMPUTER', 'NU', '2345675643123456', 2, '2023-04-24', '2023-04-22', 'Inactive', 'No');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `SN` int(111) NOT NULL,
  `Staff_no` varchar(111) NOT NULL,
  `Name` varchar(111) NOT NULL,
  `position` varchar(111) NOT NULL,
  `password` varchar(111) NOT NULL,
  `AccountType` varchar(111) NOT NULL DEFAULT 'User'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`SN`, `Staff_no`, `Name`, `position`, `password`, `AccountType`) VALUES
(1, '101', 'GILBERT CHER', 'Snr ICTO', 'admin', 'Admin'),
(2, '102', 'TONNY CHEP', 'ICTO', '1234', 'User'),
(3, '103', 'MESHACK XWS', 'INTERN', '1234', 'User'),
(4, '104', 'KESEM JGFDD', 'ICT DIRECTOR', 'admin', 'Admin');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `SN` int(111) NOT NULL,
  `supplier_no` int(111) NOT NULL,
  `supplier_name` varchar(111) NOT NULL,
  `address` varchar(111) NOT NULL,
  `email` varchar(111) NOT NULL,
  `phone` varchar(111) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`SN`, `supplier_no`, `supplier_name`, `address`, `email`, `phone`) VALUES
(1, 2000, 'WINTON TECHOLOGIES', '2345, ELD', 'info@wintontechnologies.co.ke', '+254711167'),
(2, 2001, 'SMARTDEV TECHNOLOGIES', '2345 KERIC', 'info@smartdevtechnologies.ac.ke', '+25471116'),
(3, 3, 'WINTONN TECHNOLOGIES', 'P.O. BOX 456 EL', 'info@wintonntechnologies.co.ke', '0711162');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `Name` varchar(111) NOT NULL,
  `UserName` varchar(111) NOT NULL,
  `Password` varchar(111) NOT NULL,
  `AccountType` varchar(111) NOT NULL,
  `position` varchar(111) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`Name`, `UserName`, `Password`, `AccountType`, `position`) VALUES
('PAT', 'ADMIN', 'ADMIN', 'Admin', 'Engineer'),
('June Deb', 'JD', '1234', 'User', 'Inter');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `hardware`
--
ALTER TABLE `hardware`
  ADD PRIMARY KEY (`SN`),
  ADD UNIQUE KEY `asset_no` (`asset_no`);

--
-- Indexes for table `issue`
--
ALTER TABLE `issue`
  ADD PRIMARY KEY (`issue_no`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`SN`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`SN`),
  ADD UNIQUE KEY `Staff_no` (`Staff_no`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`SN`),
  ADD UNIQUE KEY `supplier_no` (`supplier_no`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD UNIQUE KEY `UserName` (`UserName`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hardware`
--
ALTER TABLE `hardware`
  MODIFY `SN` int(111) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `SN` int(111) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123;
--
-- AUTO_INCREMENT for table `staff`
--
ALTER TABLE `staff`
  MODIFY `SN` int(111) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `SN` int(111) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
