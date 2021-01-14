USE tedi_db;

INSERT INTO `Users`
(`UserId`, `Role`, `Name`, `Phone`, `Email`, `Password`, `ImageUrl`, `Provider`,`ProviderId`, `CreationDate`, `UserVerified`, `Active`)
VALUES
(1, "Customer", "Femax", "555666777", "femax@femax.pl", "$2y$11$abnU1/30SXSjOKEmLTiUCOvBs3ksBZ7SyB6n5rUKTLnWj32gPAYAm",null, null, null, NOW(), true, true),
(2, "Customer", "ABC", "444555666", "abc@abc.pl", "#ABC1234",null, null, null, NOW() - INTERVAL 1 WEEK, true, true),
(3, "Customer", "AGD sp zoo", "503444123", "agd@agd.pl", "maleAgd1234",null, null, null, NOW() - INTERVAL 5 DAY, false, true),
(4, "Supplier", "Terma", "482034578", "terma@terma.pl", "terma123", null, null, null, NOW(), true, true),
(5, "Supplier", "Purmo", "482074578", "purmo@purmogroup.com.pl", "purmo123", null, null, null, NOW() - INTERVAL 1 WEEK, true, true),
(6, "Supplier", "Grohe", "600700800", "grohe@grohe.de", "grohe123", null, null, null, NOW() - INTERVAL 12 WEEK, true, true),
(7, "Supplier", "TECE", "678876321", "tece@tece.pl", "tece123", null, null, null, NOW() - INTERVAL 10 MONTH, true, true);

INSERT INTO `RelationsUsers`
  (`RelationUsersId`, `UserId1`, `UserId2`, `Active`, `CreationDate`, `ModifyDate`)
VALUES
(1, 1, 1, true, NOW(), NOW()),
(2, 1, 2, true, NOW() - INTERVAL 1 WEEK, NOW()),
(3, 1, 3, true, NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 2 DAY),
(4, 2, 1, false, NOW() - INTERVAL 1 DAY, NOW() - INTERVAL 10 MINUTE),
(5, 2, 3, true, NOW() - INTERVAL 45 MINUTE, NOW()),
(6, 3, 1, false, NOW() - INTERVAL 3 HOUR, NOW() - INTERVAL 1 HOUR),
(7, 3, 2, false , NOW() - INTERVAL 2 DAY, NOW() - INTERVAL 1 DAY),
(8, 3, 3, false, NOW() - INTERVAL 10 HOUR, NOW() - INTERVAL 150 MINUTE);

INSERT INTO `DeliveryOrders`
  (`DeliveryOrderId`, `BaseRef`, `NumberOrderCustomer`, `DocNumberPositions`, `DocStatus`, `UserId1`, `UserId2`, `DocTotal`, `DocNet`, `DocVatSum`, `Description`, `CreationDate`, `ModifyDate`)
VALUES
(1, "ZAM-2020-20000", "202012101234", 6, "O", 1, 1, 6000, 4878.05, 1121.95, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam sit amet dui justo. Nullam et elit velit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur ac imperdiet odio. Praesent sed fringilla lorem. Nulla facilisi. Cras eget eleifend mauris, eget euismod massa. Vivamus ultricies eu elit a fringilla. In mattis, ipsum et accumsan egestas, sem erat lobortis justo, et ullamcorper eros tortor eget ipsum.", NOW(), NOW());

INSERT INTO `DetailsDeliveryOrders`
  (`id`, `DeliveryOrderId`, `LineNum`, `BaseRef`, `UserId1`, `UserId2`, `ItemCode`, `ItemName`, `Quantity`, `CodeBars`, `Price`, `Currency`, `LineTotal`, `LineNet`, `LineVat`, `DiscountPrcnt`, `VatPrcnt`, `VatGroup`, `Active`, `OnTheWay`, `ScheduledShipDate`, `CreationDate`, `ModifyDate`)
VALUES
(1, 1, 0, "ZAM-2020-20000", 1, 1, "XXX-Item1", "ItemName1", 1, "5900000000000", 1000,"PLN", 1000, 787, 813, "10%", 23, "DS23", 1, 0, "7 dni roboczych", NOW() - Interval 2 DAY, NOW() - Interval 2 DAY),
(2, 1, 1, "ZAM-2020-20000", 1, 1, "XXX-Item2", "ItemName2", 1, "5900000000000", 1000,"PLN", 1000, 787, 813, "10%", 23, "DS23", 1, 0, "7 dni roboczych", NOW() - Interval 2 DAY, NOW() - Interval 2 DAY),
(3, 1, 2, "ZAM-2020-20000", 1, 1, "XXX-Item3", "ItemName3", 2, "5900000000000", 500,"PLN", 1000, 787, 813, "10%", 23, "DS23", 1, 1, "7 dni roboczych", NOW(), NOW()),
(4, 1, 3, "ZAM-2020-20000", 1, 1, "XXX-Item4", "ItemName4", 1, "5900000000000", 1000,"PLN", 1000, 787, 813, "10%", 23, "DS23", 1, 0, "7 dni roboczych", NOW() - Interval 2 DAY, NOW() - Interval 2 DAY),
(5, 1, 4, "ZAM-2020-20000", 1, 1, "XXX-Item5", "ItemName5", 1, "5900000000000", 1000,"PLN", 1000, 787, 813, "10%", 23, "DS23", 1, 0, "7 dni roboczych", NOW() - Interval 2 DAY, NOW() - Interval 2 DAY),
(6, 1, 5, "ZAM-2020-20000", 1, 1, "XXX-Item6", "ItemName6", 1, "5900000000000", 1000,"PLN", 1000, 787, 813, "10%", 23, "DS23", 1, 0, "7 dni roboczych", NOW() - Interval 2 DAY, NOW() - Interval 2 DAY);

INSERT INTO `Items`
  (`ItemId`, `ItemCode`, `ItemName`, `CodeBars`, `Price`, `Currency`, `VatPrcnt`, `VatGroup`, `Active`, `Availability`)
VALUES
(1, "XXX-Item1", "ItemName1", "5900000000001", 1000, "PLN", 23, "DS23", True, "Na magazynie"),
(2, "XXX-Item2", "ItemName2", "5900000000002", 1000, "PLN", 23, "DS23", false, null),
(3, "XXX-Item3", "ItemName3", "5900000000003", 1000, "PLN", 23, "DS23", True, "Na magazynie"),
(4, "XXX-Item4", "ItemName4", "5900000000004", 1000, "PLN", 23, "DS23", false, "Do wyczerpania zapasów"),
(5, "XXX-Item5", "ItemName5", "5900000000005", 1000, "PLN", 23, "DS23", True, "Na magazynie"),
(6, "XXX-Item6", "ItemName6", "5900000000006", 1000, "PLN", 23, "DS23", True, "Dostępny na zamówienie"),
(7, "XXX-Item7", "ItemName7", "5900000000007", 1000, "PLN", 23, "DS23", True, "Na magazynie"),
(8, "XXX-Item8", "ItemName8", "5900000000008", 1000, "PLN", 8, "DS08", True, "Na magazynie"),
(9, "XXX-Item9", "ItemName9", "5900000000009", 1000, "PLN", 8, "DS08", True, "Na magazynie"),
(10, "XXX-Item10", "ItemName10", "5900000000010", 1000, "PLN", 8, "DS08", True, "Dostępny na zamówienie");
