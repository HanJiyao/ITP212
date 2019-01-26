CREATE DATABASE  IF NOT EXISTS `itp212`;
USE `itp212`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(64) NOT NULL,
  `name` varchar(30) NOT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `postal_code` varchar(10) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
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
  `reviewTitle` VARCHAR(255) NULL,
  `reviewUId` VARCHAR(255) NULL,
  `displayName` VARCHAR(45) NOT NULL,
  `reviewText` VARCHAR(1000) NOT NULL,
  `rating` INT NOT NULL,
  `reviewDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reviewPhoto` VARCHAR(45) NULL,
  `reviewFor` VARCHAR(255) NULL,
  `reviewItem` VARCHAR(255) NULL;
  PRIMARY KEY (`id`)) ENGINE = InnoDB CHARSET=utf8;
 
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blogTitle` varchar(45) DEFAULT NULL,
  `blogContent` varchar(45) DEFAULT NULL,
  `blogCategory` varchar(45) DEFAULT NULL,
  `blogPoster` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET=utf8;

DROP TABLE IF EXISTS `credit_card_details`;
create table credit_card_details (
  id          int auto_increment primary key,
  card_num    varchar(25) charset utf8 not null,
  full_name   varchar(45)              not null,
  CVV         int(3)                   not null,
  expiry_date linestring               not null,
  postal_code int                      not null,
  constraint credit_card_details_card_num_uindex unique (card_num));
 
 DROP TABLE IF EXISTS `order_details`;
 CREATE TABLE `order_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `seller_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` float(10,2) NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_date` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `total_price` float(10,2) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_date` datetime NOT NULL,
  `payment_made_to` int(11) DEFAULT NULL,
  `payment_received_from` int(11) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `debit` float(10,2) DEFAULT 0.00,
  `credit` float(10,2) DEFAULT 0.00,
  `balance` float(10,2) NOT NULL DEFAULT 0.00,
  `user_id` int(11) NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
