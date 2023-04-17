-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 02 jan. 2023 à 13:29
-- Version du serveur : 10.4.24-MariaDB
-- Version de PHP : 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `rentify`
--

-- --------------------------------------------------------

--
-- Structure de la table `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL,
  `CIN_client` varchar(20) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `feedback`
--

INSERT INTO `feedback` (`id`, `CIN_client`, `description`) VALUES
(1, 'JT12365', 'Very nice service and easy process of car rental Thank you!'),
(3, 'JC47946', 'Nice experience'),
(4, 'D583016', 'Bonne experience');

-- --------------------------------------------------------

--
-- Structure de la table `location`
--

CREATE TABLE `location` (
  `MatriculeV` varchar(20) NOT NULL,
  `CIN` varchar(30) NOT NULL,
  `Prix_Total` int(11) DEFAULT NULL,
  `Prix_Avance` int(11) DEFAULT NULL,
  `datedeb` varchar(10) DEFAULT NULL,
  `datefin` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `location`
--

INSERT INTO `location` (`MatriculeV`, `CIN`, `Prix_Total`, `Prix_Avance`, `datedeb`, `datefin`) VALUES
('M1000-3', 'D1326494', 700, 450, '2022-12-31', '2023-01-01'),
('B12390-45', 'D34355', 6000, 1000, '2022-12-24', '2022-12-30'),
('A2365-25', 'D583016', 1200, 600, '2022-12-31', '2023-01-03'),
('B12390-45', 'JC12348', 2000, 1000, '2022-12-31', '2023-01-04'),
('M1000-1', 'JC47946', 460, 250, '2022-12-31', '2023-01-02'),
('M1000-2', 'JT12365', 4600, 3000, '2023-01-16', '2023-01-26'),
('D583016', 'N1212', 2400, 1000, '2022-12-28', '2022-12-30');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `username` varchar(30) NOT NULL,
  `CIN` varchar(30) NOT NULL,
  `prenom` varchar(30) NOT NULL,
  `nom` varchar(30) NOT NULL,
  `age` int(11) NOT NULL,
  `tel` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  `N_permis` int(30) NOT NULL,
  `Categorie_permis` varchar(10) NOT NULL,
  `date_finVali` varchar(20) NOT NULL,
  `isAdmin` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`username`, `CIN`, `prenom`, `nom`, `age`, `tel`, `password`, `N_permis`, `Categorie_permis`, `date_finVali`, `isAdmin`) VALUES
('root', '', '', '', 0, '', 'root', 0, '', '0000-00-00', b'1'),
('mery', 'D1326494', 'Meryem', 'El hajoui', 21, '0653164529', '111', 36454, 'A', '2023-12-30', b'0'),
('rimel', 'D34355', 'rima', 'Elhajoui', 22, '0632164589', '123', 0, '', '2022-08-16', b'0'),
('meryemEl', 'D583016', 'meryem', 'Elhajoui', 21, '0657984612', '123', 0, 'A', '2024-11-20', b'0'),
('meryel', 'JC12348', 'Meryem', 'El Hajoui', 21, '063597612', '123', 365479, 'B', '2026-12-30', b'0'),
('oum', 'JC47946', 'Oum', 'Azeroual', 21, '06349876', '123', 6579, 'B', '2027-12-30', b'0'),
('oumeima', 'JT12365', 'Omayma', 'Azeroual', 21, '0653454329', '123', 123645, 'B', '2027-12-23', b'0'),
('ouma', 'N1212', 'rima', 'Azeroual', 22, '067916459', '123', 111, 'A', '2012-02-21', b'0');

-- --------------------------------------------------------

--
-- Structure de la table `voiture`
--

CREATE TABLE `voiture` (
  `MatriculeV` varchar(20) NOT NULL,
  `DateVisite` varchar(20) DEFAULT NULL,
  `DateAssu` varchar(20) DEFAULT NULL,
  `Marque` varchar(30) DEFAULT NULL,
  `Modele` varchar(20) DEFAULT NULL,
  `PrixParJour` int(11) DEFAULT NULL,
  `image` text DEFAULT NULL,
  `etat` varchar(20) DEFAULT 'disponible'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `voiture`
--

INSERT INTO `voiture` (`MatriculeV`, `DateVisite`, `DateAssu`, `Marque`, `Modele`, `PrixParJour`, `image`, `etat`) VALUES
('A2365-25', '2024-10-29', '2024-12-20', 'Dacia ', 'Dacia Duster', 400, 'E:\\School\\S3\\Java\\JavaProject\\src\\main\\resources\\image\\Dacia-Duster-UK-00.jpg', 'loue'),
('A263497', '2022-12-21', '2023-01-06', 'Skoda', 'Octavia', 700, 'E:\\School\\S3\\Java\\JavaProject\\src\\main\\resources\\image\\7560ab94-skoda-octavia-rs-245-0.jpg', 'disponible'),
('B12390-45', '2023-11-01', '2023-02-21', 'Mercedes ', 'Mercedes amg A', 500, 'E:\\School\\S3\\Java\\JavaProject\\src\\main\\resources\\image\\18c0618_102.jpg', 'loue'),
('D583016', '2024-02-21', '2023-03-12', 'Mercedes ', 'Mercedes class A', 1250, 'E:\\School\\S3\\Java\\JavaProject\\src\\main\\resources\\image\\2019-Mercedes-Benz-A-Class-Review-47.jpeg', 'disponible'),
('M1000-1', '2025-12-22', '2026-12-25', 'Fiat ', 'Fiat 500e', 230, 'E:\\School\\S3\\Java\\JavaProject\\src\\main\\resources\\image\\Fiat-500e-3-1-electric-car-2020-2021-o10.jpg', 'loue'),
('M1000-2', '2025-12-23', '2024-09-23', 'Kia ', 'Kia Niro Hybrid', 460, 'E:\\School\\S3\\Java\\JavaProject\\src\\main\\resources\\image\\20190305_121720.jpg', 'loue'),
('M1000-3', '2026-12-24', '2026-12-22', 'Toyota', 'Toyota Hilux 2019', 700, 'E:\\School\\S3\\Java\\JavaProject\\src\\main\\resources\\image\\2019-Toyota-Hilux-UK-Exterior.png', 'loue'),
('Z12438-32', '2022-02-21', '2023-03-12', 'BMW', 'BMW serie E 12', 360, 'E:\\School\\S3\\Java\\JavaProject\\src\\main\\resources\\image\\bmw-serie-1-2009-preto-201205-1.jpg', 'disponible');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_CIN_client` (`CIN_client`);

--
-- Index pour la table `location`
--
ALTER TABLE `location`
  ADD UNIQUE KEY `CIN` (`CIN`),
  ADD KEY `MatriculeV` (`MatriculeV`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`CIN`),
  ADD KEY `CIN` (`CIN`);

--
-- Index pour la table `voiture`
--
ALTER TABLE `voiture`
  ADD PRIMARY KEY (`MatriculeV`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `fk_CIN_client` FOREIGN KEY (`CIN_client`) REFERENCES `users` (`CIN`);

--
-- Contraintes pour la table `location`
--
ALTER TABLE `location`
  ADD CONSTRAINT `CIN` FOREIGN KEY (`CIN`) REFERENCES `users` (`CIN`),
  ADD CONSTRAINT `MatriculeV` FOREIGN KEY (`MatriculeV`) REFERENCES `voiture` (`MatriculeV`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
