CREATE DATABASE  IF NOT EXISTS `itp212`;
USE `itp212`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `email` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_groups`;
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
 
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blogTitle` varchar(45) DEFAULT NULL,
  `blogContent` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET=utf8;

DROP TABLE IF EXISTS `items`;
CREATE TABLE IF NOT EXISTS `items` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `image` VARCHAR(100) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `desc` VARCHAR(45) NULL,
  `type` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL,
  `price` FLOAT(10,2) NOT NULL,
  `discount` FLOAT(3,2) NULL,
  `user` varchar(255) NOT NULL,
  `created` DATE NULL,
  PRIMARY KEY (`id`)) ENGINE = InnoDB CHARSET=utf8;
