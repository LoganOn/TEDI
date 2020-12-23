USE server_db;

INSERT INTO `Customers`
(`CustomerId`, `Role`, `Name`, `Phone`, `Email`, `Password`, `imageUrl`, `provider`,`providerId`, `CreationDate`, `UserVerified`)
VALUES
(1, "Customer", "Femax", "555666777", "femax@femax.pl", "#Femax123",null, null, null, NOW(), true),
(2, "Customer", "ABC", "444555666", "abc@abc.pl", "#ABC1234",null, null, null, NOW() - INTERVAL 1 WEEK, true),
(3, "Customer", "AGD sp zoo", "503444123", "agd@agd.pl", "maleAgd1234",null, null, null, NOW() - INTERVAL 5 DAY, false)

INSERT INTO `Suppliers`
  (`SupplierId`, `Role`, `Name`, `Phone`, `Email`, `Password`, `imageUrl`, `provider`, `providerId`, `CreationDate`, `UserVerified`,
VALUES
(1, "Supplier", "Terma", "482034578", "terma@terma.pl", null, null, ull, NOW(), true),
(1, "Supplier", "Purmo", "482074578", "purmo@purmogroup.com.pl", null, null, null, NOW() - INTERVAL 1 WEEK, true),
(1, "Supplier", "Grohe", "600700800", "grohe@grohe.de", null, null, null, NOW() - INTERVAL 12 WEEK, true),
(1, "Supplier", "TECE", "678876321", "tece@tece.pl", null, null, null, NOW() - INTERVAL 10 MONTH, true)

INSERT INTO `Relations`
  (`RelationId`, `SupplierId`, `CustomerId`, `Active`, `CreationDate`, `ModifyDate`)
VALUES
(1, 1, 1, true, NOW(), NOW()),
(2, 1, 2, true, NOW() - INTERVAL 1 WEEK, NOW()),
(3, 1, 3, true, NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 2 DAY),
(4, 2, 1, false, NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 10 MINUTE),
(5, 2, 3, true, NOW() - INTERVAL 45 MINUTE, NOW()),
(6, 3, 1, false, NOW() - INTERVAL 3 HOUR, NOW() - INTERVAL 1 HOUR),
(7, 3, 2, false , NOW() - INTERVAL 2 DAY, NOW() - INTERVAL) 1 DAY,
(8, 3, 3, false, NOW() - INTERVAL 10 HOUR, NOW() - INTERVAL 150 MINUTE),

INSERT INTO `DeliveryOrders`
  `DeliveryOrderId`, `BaseRef`, `NumberOrderCustomer`, `DocNumberPositions`, `DocStatus`, `SupplierId`, `UserId`, `DocTotal`, `DocNet`, `DocVatSum`, `Description`, `CreationDate`, `ModifyDate`,
VALUES
(1, "ZAM-2020-20000", "202012101234", 6, "O", 1, 1, 6000, 4878.05, 1121.95, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sit amet dui justo. Nullam et elit velit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur ac imperdiet odio. Praesent sed fringilla lorem. Nulla facilisi. Cras eget eleifend mauris, eget euismod massa. Vivamus ultricies eu elit a fringilla. In mattis, ipsum et accumsan egestas, sem erat lobortis justo, et ullamcorper eros tortor eget ipsum.", NOW(), NOW())

INSERT INTO `DetailsDeliveryOrders`
  `DeliveryOrderId`,
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
  `ModifyDate` Timestamp NOT NULL,
VALUES
(1, 0, "ZAM-2020-20000", 1, 1, "XXX-Item1", "ItemName1", 1, "5900000000000", 1000,"PLN", 1000, 813, "10%", 23, "DS23", 1, 0, "7 dni roboczych", NOW() - Interval 2 DAY, NOW() - Interval 2 DAY),
(1, 1, "ZAM-2020-20000", 1, 1, "XXX-Item2", "ItemName2", 1, "5900000000000", 1000,"PLN", 1000, 813, "10%", 23, "DS23", 1, 0, "7 dni roboczych", NOW() - Interval 2 DAY, NOW() - Interval 2 DAY),
(1, 2, "ZAM-2020-20000", 1, 1, "XXX-Item3", "ItemName3", 2, "5900000000000", 500,"PLN", 1000, 813, "10%", 23, "DS23", 1, 1, "7 dni roboczych", NOW(), NOW()),
(1, 3, "ZAM-2020-20000", 1, 1, "XXX-Item4", "ItemName4", 1, "5900000000000", 1000,"PLN", 1000, 813, "10%", 23, "DS23", 1, 0, "7 dni roboczych", NOW() - Interval 2 DAY, NOW() - Interval 2 DAY),
(1, 4, "ZAM-2020-20000", 1, 1, "XXX-Item5", "ItemName5", 1, "5900000000000", 1000,"PLN", 1000, 813, "10%", 23, "DS23", 1, 0, "7 dni roboczych", NOW() - Interval 2 DAY, NOW() - Interval 2 DAY),
(1, 5, "ZAM-2020-20000", 1, 1, "XXX-Item6", "ItemName6", 1, "5900000000000", 1000,"PLN", 1000, 813, "10%", 23, "DS23", 1, 0, "7 dni roboczych", NOW() - Interval 2 DAY, NOW() - Interval 2 DAY),
