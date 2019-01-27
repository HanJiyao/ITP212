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
                       `longitude` FLOAT( 10, 6 ) DEFAULT NULL,
                       `latitude` FLOAT( 10, 6 ) DEFAULT NULL,
                       `email` varchar(100) NOT NULL,
                       `balance` float(10,2) NOT NULL DEFAULT 0.00,
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
                                      `reviewFor` VARCHAR(255) NULL,
                                      `reviewItem` VARCHAR(255) NULL,
                                      PRIMARY KEY (`id`)) ENGINE = InnoDB CHARSET=utf8;

DROP TABLE IF EXISTS `likes`;
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
                      `id` int(11) NOT NULL AUTO_INCREMENT,
                      `blogTitle` varchar(45) DEFAULT NULL,
                      `blogContent` varchar(45) DEFAULT NULL,
                      `blogCategory` varchar(45) DEFAULT NULL,
                      `blogDate` DATETIME DEFAULT CURRENT_TIMESTAMP,
                      `blogPoster` varchar(45) DEFAULT NULL,
                      PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET=utf8;


CREATE TABLE `likes` (
                       `id` int(11) NOT NULL AUTO_INCREMENT,
                       `likeTitle` varchar(45) default null,
                       `blogLikeId` int(11) default null,
                       foreign key (blogLikeId)
                         references blog(id)
                         on delete cascade,
                       PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET=utf8;



DROP TABLE IF EXISTS `items`;
CREATE TABLE `items` (
                       `id` int(11) NOT NULL AUTO_INCREMENT,
                       `name` varchar(45) NOT NULL,
                       `desc` varchar(45) DEFAULT NULL,
                       `type` varchar(45) NOT NULL,
                       `quantity` int(11) NOT NULL,
                       `price` float(10,2) NOT NULL,
                       `discount` float(3,2) DEFAULT NULL,
                       `user` varchar(255) NOT NULL,
                       `created` datetime DEFAULT current_timestamp(),
                       `image` varchar(100) NOT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `full_name` varchar(45) NOT NULL,
                        `card_num` varchar(45) DEFAULT NULL,
                        `CVV` int(11) NOT NULL,
                        `postal_code` INT NOT NULL,
                        `expiry_date` DATE NOT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `order_details`;
CREATE TABLE `order_details` (
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `order_id` int(11) NOT NULL,
                               `item_id` int(11) NOT NULL,
                               `seller_id` int(11) NOT NULL,
                               `quantity` int(11) NOT NULL,
                               `price` float(10,2) NOT NULL,
                               `created` datetime NOT NULL,
                               `delivery_status` varchar(255) NOT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `transaction_date` datetime NOT NULL,
                              `transaction_made_to_seller` int(11) DEFAULT NULL,
                              `transaction_received_from_buyer` int(11) DEFAULT NULL,
                              `description` varchar(255) NOT NULL,
                              `debit` float(10,2) DEFAULT 0.00,
                              `credit` float(10,2) DEFAULT 0.00,
                              `balance` float(10,2) NOT NULL DEFAULT 0.00,
                              `user_id` int(11) NOT NULL,
                              `created` datetime NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `delivery`;
CREATE TABLE `delivery` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `buyerID` varchar(255) NOT NULL,
                          `sellerID` varchar(255) NOT NULL,
                          `buyerLOCATION` varchar(255) NOT NULL,
                          `deliverymenLOCATION` varchar(255) NOT NULL,
                          `shopLOCATION` varchar(255) NOT NULL,
                          `deliverySTATUS` varchar(255) NOT NULL,
                          PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET=utf8;

DROP TABLE IF EXISTS `credit_card_details`;
CREATE TABLE IF NOT EXISTS `credit_card_details` (
                                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                                   `full_name` varchar(45) NOT NULL,
                                                   `card_num` varchar(45) DEFAULT NULL,
                                                   `CVV` int(11) NOT NULL,
                                                   `postal_code` INT NOT NULL,
                                                   `expiry_date` DATE NOT NULL,
                                                   `balance` INT NOT NULL ,
                                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8;


