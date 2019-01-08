CREATE DATABASE  IF NOT EXISTS `itp212`;
USE `itp212`;

DROP TABLE IF EXISTS `review`;
CREATE TABLE `users` (
  `email` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review`;
CREATE TABLE `user_groups` (
  `email` varchar(255) NOT NULL,
  `groupname` varchar(32) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review`;
CREATE TABLE IF NOT EXISTS `review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `reviewUId` INT NULL,
  `displayName` VARCHAR(45) NOT NULL,
  `reviewText` VARCHAR(45) NULL,
  `rating` INT NULL,
  PRIMARY KEY (`id`)) ENGINE = InnoDB CHARSET=utf8;