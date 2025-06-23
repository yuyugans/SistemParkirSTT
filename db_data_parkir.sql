-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 23, 2025 at 11:28 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_data_parkir`
--

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(30) DEFAULT NULL,
  `jenis` varchar(20) DEFAULT NULL,
  `barcode` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`id_user`, `nama`, `jenis`, `barcode`) VALUES
(1, 'Mohamad Yusup', 'Mahasiswa', '221351083');

-- --------------------------------------------------------

--
-- Table structure for table `parkir`
--

CREATE TABLE `parkir` (
  `id_parkir` int(11) NOT NULL,
  `barcode` varchar(50) NOT NULL,
  `tipe` enum('anggota','guest') NOT NULL,
  `waktu_masuk` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `parkir`
--

INSERT INTO `parkir` (`id_parkir`, `barcode`, `tipe`, `waktu_masuk`) VALUES
(35, '20250621142425', 'guest', '2025-06-21 14:24:25'),
(38, '20250623160402', 'guest', '2025-06-23 16:04:02'),
(39, '20250623160411', 'guest', '2025-06-23 16:04:11'),
(40, '20250623160414', 'guest', '2025-06-23 16:04:14');

-- --------------------------------------------------------

--
-- Table structure for table `riwayat_parkir`
--

CREATE TABLE `riwayat_parkir` (
  `id` int(11) NOT NULL,
  `barcode` varchar(100) DEFAULT NULL,
  `tipe` varchar(50) DEFAULT NULL,
  `waktu_masuk` datetime DEFAULT NULL,
  `waktu_keluar` datetime DEFAULT NULL,
  `biaya` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `riwayat_parkir`
--

INSERT INTO `riwayat_parkir` (`id`, `barcode`, `tipe`, `waktu_masuk`, `waktu_keluar`, `biaya`) VALUES
(1, '20250623153202', 'guest', '2025-06-23 15:32:02', '2025-06-23 15:32:53', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `barcode` (`barcode`);

--
-- Indexes for table `parkir`
--
ALTER TABLE `parkir`
  ADD PRIMARY KEY (`id_parkir`);

--
-- Indexes for table `riwayat_parkir`
--
ALTER TABLE `riwayat_parkir`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `anggota`
--
ALTER TABLE `anggota`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `parkir`
--
ALTER TABLE `parkir`
  MODIFY `id_parkir` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT for table `riwayat_parkir`
--
ALTER TABLE `riwayat_parkir`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
