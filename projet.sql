-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : sam. 19 mars 2022 à 18:25
-- Version du serveur :  5.7.31
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `projet`
--

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

DROP TABLE IF EXISTS `etudiant`;
CREATE TABLE IF NOT EXISTS `etudiant` (
  `matricule` varchar(20) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `dateNais` varchar(100) DEFAULT NULL,
  `sexe` varchar(10) DEFAULT NULL,
  `annee` varchar(3) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`matricule`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`matricule`, `nom`, `dateNais`, `sexe`, `annee`, `adresse`) VALUES
('149 H-F', 'Jean Paul', '2022-03-01', 'Homme', 'M2', 'Fianatantsoa'),
('159 H-F', 'Anialy', '2022-02-28', 'femme', 'L3', 'France'),
('416 H-F', 'Ranto', '2022-02-28', 'Homme', 'L1', 'Tanambao'),
('458 H-F', 'Fifaliana', '2022-03-09', 'Femme', 'L1', 'Fianarantsoa');

-- --------------------------------------------------------

--
-- Structure de la table `matiere`
--

DROP TABLE IF EXISTS `matiere`;
CREATE TABLE IF NOT EXISTS `matiere` (
  `codeMat` varchar(20) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `coef` int(11) DEFAULT NULL,
  PRIMARY KEY (`codeMat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `matiere`
--

INSERT INTO `matiere` (`codeMat`, `libelle`, `coef`) VALUES
('ANG1', 'Anglais', 3),
('ARC1', 'Archi', 2),
('HTM1', 'HTML', 2),
('MAT1', 'Mathematiques', 3),
('PHY1', 'Physiques', 4),
('TCOM', 'Tech Communication', 1);

-- --------------------------------------------------------

--
-- Structure de la table `note`
--

DROP TABLE IF EXISTS `note`;
CREATE TABLE IF NOT EXISTS `note` (
  `matricule` varchar(20) NOT NULL,
  `codeMat` varchar(20) NOT NULL,
  `note` int(11) NOT NULL,
  `annee` varchar(10) NOT NULL,
  PRIMARY KEY (`matricule`,`codeMat`),
  KEY `matricule` (`matricule`),
  KEY `codeMat` (`codeMat`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `note`
--

INSERT INTO `note` (`matricule`, `codeMat`, `note`, `annee`) VALUES
('149 H-F', 'MAT1', 15, 'L2'),
('416 H-F', 'ANG1', 12, 'L1'),
('416 H-F', 'MAT1', 18, 'L1'),
('416 H-F', 'PHY1', 12, 'L1'),
('458 H-F', 'ANG1', 18, 'L1'),
('458 H-F', 'ARC1', 11, 'L2'),
('458 H-F', 'HTM1', 9, 'L2'),
('458 H-F', 'MAT1', 8, 'L1'),
('458 H-F', 'PHY1', 10, 'L1'),
('458 H-F', 'TCOM', 8, 'L2');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `note`
--
ALTER TABLE `note`
  ADD CONSTRAINT `note_ibfk_1` FOREIGN KEY (`codeMat`) REFERENCES `matiere` (`codeMat`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `note_ibfk_2` FOREIGN KEY (`matricule`) REFERENCES `etudiant` (`matricule`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
