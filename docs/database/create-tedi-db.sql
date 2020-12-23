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
  `providerId` Varchar(255),
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
  `provider` Varchar(255),
  `providerId` Varchar(255),
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
  PRIMARY KEY (`RelationId`),
  FOREIGN KEY (`SupplierId`)
    REFERENCES Suppliers(SupplierId),
  FOREIGN KEY (`CustomerId`)
    REFERENCES Customers(CustomerId)

);

CREATE TABLE IF NOT EXISTS `DeliveryOrders` (
  `DeliveryOrderId` Integer NOT NULL AUTO_INCREMENT,
  `DocNumber` Integer NOT NULL,
  `DocStatus` Varchar(1) NOT NULL , -- close, open, cancelled
  `SupplierId` Integer,
  `UserId` Integer,
  `CreationDate` Timestamp NOT NULL,
  `DocTotal` Numeric(10,2),  -- total value order
  `DocNet` Numeric(10,2), -- net value
  `DocVatSum` Numeric(10,2), -- tax value
  `DocNumberPosition` Integer,
  PRIMARY KEY (`DeliveryOrderId`)
);

CREATE TABLE IF NOT EXISTS `DetailsDeliveryOrders` (
  `DetailsDeliveryOrderId` Integer NOT NULL AUTO_INCREMENT,
  `LineNum` Integer NOT NULL,
  `DocNumber` Integer NOT NULL,
  `SupplierId` Varchar(20),
  `UserId` Varchar(32),
  `ItemCode` Varchar(100),
  `ItemName` Varchar(255),
  `Quantity` Numeric(10,2),
  `CodeBars` Varchar(13),
  `ShipDate` Timestamp NOT NULL,
  `Price` Numeric(10,2),
  `Currency` Varchar(10),
  `LineTotal` Numeric(10,2),
  `LineVat` Numeric(10,2),
  `VatPrcnt` Numeric(10,2),
  `VatGroup` Varchar(10),
  `Active` boolean,
  `CreationDate` Timestamp NOT NULL,
  PRIMARY KEY (`DetailsDeliveryOrderId`)
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

