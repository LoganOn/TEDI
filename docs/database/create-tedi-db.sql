DROP DATABASE IF EXISTS tedi_db;
CREATE DATABASE tedi_db;
CREATE USER IF NOT EXISTS 'tedi'@'localhost' IDENTIFIED BY 'tedi';
GRANT ALL PRIVILEGES ON tedi_db.* TO 'tedi'@'localhost';
FLUSH PRIVILEGES;

USE tedi_db;
ALTER DATABASE server_db CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `Users` (
  `UserId` Integer NOT NULL AUTO_INCREMENT,
  `Role` Varchar(20),
  `Name` Varchar(32),
  `Phone` Varchar(20),
  `Email` Varchar(64) NOT NULL UNIQUE,
  `Password` Varchar(64) NOT NULL,
  `imageUrl` MEDIUMTEXT,
  `provider` Varchar(255),
  `providerId` Varchar(255),
  `CreationDate` Timestamp NOT NULL,
  `UserVerified` boolean,
  PRIMARY KEY (`UserId`)
);
