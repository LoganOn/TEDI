DROP DATABASE IF EXISTS tedi_db;
CREATE DATABASE tedi_db;
CREATE USER IF NOT EXISTS 'tedi'@'localhost' IDENTIFIED BY 'tedi';
GRANT ALL PRIVILEGES ON tedi_db.* TO 'tedi'@'localhost';
FLUSH PRIVILEGES;

USE tedi_db;
ALTER DATABASE server_db CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `Customers` (
  `CustomerId` Integer NOT NULL AUTO_INCREMENT,
  `Role` Varchar(20),
  `Name` Varchar(32),
  `Phone` Varchar(20),
  `Email` Varchar(64) NOT NULL UNIQUE,
  `Password` Varchar(64) NOT NULL,
  `imageUrl` MEDIUMTEXT,
  `provider` Varchar(255),
  `providerId` Integer,
  `CreationDate` Timestamp NOT NULL,
  `UserVerified` boolean,
  PRIMARY KEY (`CustomerId`)
);

CREATE TABLE IF NOT EXISTS `Suppliers` (
  `SupplierId` Integer NOT NULL AUTO_INCREMENT,
  `Role` Varchar(20),
  `Name` Varchar(32),
  `Phone` Varchar(20),
  `Email` Varchar(64) NOT NULL UNIQUE,
  `Password` Varchar(64) NOT NULL,
  `imageUrl` MEDIUMTEXT,
  `recipient` Varchar(255),
  `recipientId` Integer,
  `CreationDate` Timestamp NOT NULL,
  `UserVerified` boolean,
  PRIMARY KEY (`SupplierId`)
);

CREATE TABLE IF NOT EXISTS `Relations` (
  `RelationId` Integer NOT NULL AUTO_INCREMENT,
  `SupplierId` Integer,
  `CustomerId` Integer,
  `Active` boolean,
  `CreationDate` Timestamp NOT NULL,
  `ModifyDate` Timestamp NOT NULL,
  PRIMARY KEY (`RelationId`),
  FOREIGN KEY (`SupplierId`)
    REFERENCES Suppliers(SupplierId),
  FOREIGN KEY (`CustomerId`)
    REFERENCES Customers(CustomerId)

);

CREATE TABLE IF NOT EXISTS `DeliveryOrders` (
  `DeliveryOrderId` Integer NOT NULL AUTO_INCREMENT,
  `BaseRef` Varchar(64) NOT NULL,
  `NumberOrderCustomer` Varchar(64) NOT NULL,
  `DocNumberPositions` Integer,
  `DocStatus` Varchar(1) NOT NULL , -- close, open, cancelled
  `SupplierId` Integer,
  `UserId` Integer,
  `DocTotal` Numeric(10,2),  -- total value order
  `DocNet` Numeric(10,2), -- net value
  `DocVatSum` Numeric(10,2), -- tax value
  `Description` Text,
  `CreationDate` Timestamp NOT NULL,
  `ModifyDate` Timestamp NOT NULL,
  PRIMARY KEY (`DeliveryOrderId`)
);

CREATE TABLE IF NOT EXISTS `DetailsDeliveryOrders` (
  `DeliveryOrderId` Integer,
  `LineNum` Integer NOT NULL,
  `BaseRef` Varchar(64) NOT NULL,
  `SupplierId` Integer,
  `UserId` Integer,
  `ItemCode` Varchar(100),
  `ItemName` Varchar(255),
  `Quantity` Numeric(10,2),
  `CodeBars` Varchar(13),
  `Price` Numeric(10,2),
  `Currency` Varchar(10),
  `LineTotal` Numeric(10,2),
  `LineVat` Numeric(10,2),
  `DiscountPrcnt` Varchar(10),
  `VatPrcnt` Numeric(10,2),
  `VatGroup` Varchar(10),
  `Active` boolean,
  `OnTheWay` boolean,
  `ScheduledShipDate` Varchar(64) NOT NULL,
  `CreationDate` Timestamp NOT NULL,
  `ModifyDate` Timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS `Items` (
  `ItemId` Integer NOT NULL AUTO_INCREMENT,
  `ItemCode` Varchar(100),
  `ItemName` Varchar(255),
  `CodeBars` Varchar(13),
  `Price` Numeric(10,2),
  `Currency` Varchar(10),
  `VatPrcnt` Numeric(10,2),
  `VatGroup` Varchar(10),
  `Active` boolean,
  `Availability` Varchar(255),
  PRIMARY KEY (`ItemId`)
);

