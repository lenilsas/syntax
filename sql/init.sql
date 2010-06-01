INSERT INTO kontoart (id, name) VALUES (1, 'Erlöse');
INSERT INTO kontoart (id, name) VALUES (2, 'Aufwände');
INSERT INTO kontoart (id, name) VALUES (3, 'Geldkonto');
INSERT INTO kontoart (id, name) VALUES (4, 'Anlagevermögen');
INSERT INTO kontoart (id, name) VALUES (5, 'Privatkonto');
INSERT INTO kontoart (id, name) VALUES (6, 'Steuer-Sammelkonto');

INSERT INTO kontotyp (id, name) VALUES (1, 'Einnahmen');
INSERT INTO kontotyp (id, name) VALUES (2, 'Ausgaben');

INSERT INTO kontenrahmen (id,name) VALUES (1,'SKR 03');
INSERT INTO kontenrahmen (id,name) VALUES (2,'SKR 04');

-- Steuerkonten SKR03
INSERT INTO konto (id, kontoart_id, kontotyp_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1, 6, 2, '1570', 'Abziehbare Vorsteuer', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontotyp_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (2, 6, 2, '1571', 'Abziehbare Vorsteuer 7%', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontotyp_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (3, 6, 1, '1770', 'Umsatzsteuer', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontotyp_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (135, 6, 2, '1576', 'Abziehbare Vorsteuer 19%', 1, NULL);

-- Steuern SKR03
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (1,'Vorsteuer 16%', '16', 1);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (2,'Vorsteuer 7%', '7', 2);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (3,'Umsatzsteuer 16%', '16', 3);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (4,'Umsatzsteuer 15%', '15', 3);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (5,'Umsatzsteuer 7%', '7', 3);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (6,'Vorsteuer 0%', '0', 1);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (7,'Umsatzsteuer 0%', '0', 3);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (8,'Vorsteuer 19%', '19', 135);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (9,'Umsatzsteuer 19%', '19', 3);


-- restliche SKR03-Konten
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (100, 4, '0027', 'EDV-Software', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (101, 4, '0050', 'Grundstücke und Bauten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (102, 4, '0110', 'Garagen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (103, 4, '0112', 'Hof- und Wegbefestigungen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (104, 4, '0113', 'Einrichtungen für Geschäfts- und Fabrikbauten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (105, 4, '0115', 'Andere Bauten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (106, 4, '0140', 'Wohnbauten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (107, 4, '0145', 'Garagen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (108, 4, '0146', 'Außenanlagen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (109, 4, '0147', 'Hof- und Wegbefestigungen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (110, 4, '0148', 'Einrichtungen für Wohnbauten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (111, 4, '0165', 'Geschäftsbauten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (112, 4, '0170', 'Fabrikbauten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (113, 4, '0210', 'Maschinen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (114, 4, '0220', 'Maschinengebundene Werkzeuge', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (115, 4, '0240', 'Maschinelle Anlagen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (116, 4, '0260', 'Transportanlagen u. ä.', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (117, 4, '0280', 'Betriebsvorrichtungen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (118, 4, '0300', 'Andere Anlagen, Betriebs- und Geschäftsausstattun', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (119, 4, '0310', 'Andere Anlagen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (120, 4, '0320', 'Pkw', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (121, 4, '0350', 'Lkw', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (122, 4, '0380', 'Sonstige Transportmittel', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (123, 4, '0400', 'Betriebsausstattung', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (124, 4, '0410', 'Geschäftausstattung', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (125, 4, '0420', 'Büroeinrichtung', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (126, 4, '0430', 'Ladeneinrichtung', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (127, 4, '0440', 'Werkzeuge', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (128, 4, '0480', 'Geringwertige Wirtschaftsgüter bis EUR 400.-', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (129, 4, '0490', 'Sonstige Betriebs- und Geschäftsausstattung', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (130, 3, '1000', 'Kasse', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (131, 3, '1100', 'Postgiro', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (132, 3, '1200', 'Bank', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (133, 3, '1360', 'Geldtransit', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (134, 3, '1370', 'Verrechnungskonto Gewinnermittlung § 4 Abs.3 EStG', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (136, 3, '1590', 'Durchlaufende Posten', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (137, 3, '1700', 'Sonstige Verbindlichkeiten', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (138, 2, '1780', 'Umsatzsteuer-Vorauszahlungen', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (139, 2, '1789', 'Umsatzsteuer laufendes Jahr', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (140, 2, '1790', 'Umsatzsteuer Vorjahr', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (141, 2, '1791', 'Umsatzsteuer frühere Jahre', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (142, 5, '1800', 'Privatentnahmen', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (143, 5, '1810', 'Privatsteuern', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (144, 5, '1820', 'Sonderausgaben', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (145, 5, '1850', 'Außergewöhnliche Belastungen', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (146, 5, '1880', 'Eigenverbrauch', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (147, 5, '1890', 'Privateinlagen', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (148, 2, '2100', 'Zinsen und ähnliche Aufwendungen', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (149, 2, '3100', 'Fremdleistungen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (150, 2, '3200', 'Wareneingang', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (151, 2, '3300', 'Wareneingang 7% Vorsteuer', 1, 2);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (152, 2, '3400', 'Wareneingang 19% Vorsteuer', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (153, 2, '4100', 'Löhne und Gehälter', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (154, 2, '4110', 'Löhne', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (155, 2, '4120', 'Gehälter', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (156, 2, '4125', 'Ehegattengehalt', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (157, 2, '4130', 'Gesetzliche soziale Aufwendungen', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (158, 2, '4138', 'Beiträge zur Berufsgenossenschaft', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (159, 2, '4190', 'Aushilfslöhne', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (160, 2, '4199', 'Lohnsteuer für Aushilfen', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (161, 2, '4200', 'Raumkosten', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (162, 2, '4210', 'Miete', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (163, 2, '4220', 'Pacht', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (164, 2, '4230', 'Heizung', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (165, 2, '4240', 'Gas, Strom, Wasser (Verwaltung, Vertrieb)', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (166, 2, '4250', 'Reinigung', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (167, 2, '4260', 'Instandhaltung betrieblicher Räume', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (168, 2, '4280', 'Sonstige Raumkkosten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (169, 2, '4300', 'Nicht anrechenbare Vorsteuer', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (170, 2, '4360', 'Versicherungen', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (171, 2, '4380', 'Beiträge', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (172, 2, '4390', 'Sonstige Abgaben', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (173, 2, '4500', 'Fahrzeugkosten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (174, 2, '4510', 'Kfz-Steuern', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (175, 2, '4520', 'Kfz-Versicherungen', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (176, 2, '4530', 'Laufende Kfz-Betriebskosten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (177, 2, '4540', 'Kfz-Reparaturen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (178, 2, '4550', 'Garagenmieten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (179, 2, '4570', 'Fremdfahrzeuge', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (180, 2, '4580', 'Sonstige Kfz-Kosten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (181, 2, '4610', 'Werbekosten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (182, 2, '4640', 'Repräsentationskosten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (183, 2, '4650', 'Bewirtungskosten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (184, 2, '4660', 'Reisekosten Arbeitnehmer', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (185, 2, '4670', 'Reisekosten Unternehmer', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (186, 2, '4710', 'Verpackungmaterial', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (187, 2, '4780', 'Fremdarbeiten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (188, 2, '4800', 'Reparaturen und Instandhaltung von techn. Anlagen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (189, 2, '4805', 'Reparaturen und Instandhaltung BGA', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (190, 2, '4809', 'Sonstige Reparaturen und Instandhaltungen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (191, 2, '4810', 'Mietleasing', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (192, 2, '4815', 'Kaufleasing', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (193, 2, '4820', 'Abschreibungen auf Aufwendungen für Ingangsetzung', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (194, 2, '4855', 'Sofortabschreibung GWGs', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (195, 2, '4900', 'Sonstige betriebliche Aufwendungen', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (196, 2, '4910', 'Porto', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (197, 2, '4920', 'Telefon', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (198, 2, '4930', 'Bürobedarf', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (199, 2, '4940', 'Zeitschriften, Bücher', 1, 2);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (200, 2, '4950', 'Rechts- und Beratungskosten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (201, 2, '4955', 'Buchführungskosten', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (202, 2, '4970', 'Kosten des Geldverkehrs', 1, 6);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (203, 2, '4980', 'Betriebsbedarf', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (204, 2, '4985', 'Werkzeuge und Kleingeräte', 1, 8);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (205, 1, '8100', 'Steuerfreie Umsätze', 1, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (206, 1, '8200', 'Erlöse (keine UST)', 1, 7);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (207, 1, '8300', 'Erlöse (7% UST)', 1, 5);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (208, 1, '8400', 'Erlöse (19% UST)', 1, 9);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (209, 1, '8800', 'Erlöse aus Anlagenverkäufen', 1, 9);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (210, 1, '8900', 'Eigenverbrauch', 1, 9);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (211, 1, '8955', 'Umsatzsteuervergütungen', 1, NULL);


-- Steuerkonten SKR04
INSERT INTO konto (id,kontoart_id, kontotyp_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1001, 6, 2, '1400', 'Abziehbare Vorsteuer', 2, NULL);
INSERT INTO konto (id,kontoart_id, kontotyp_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1002, 6, 2, '1401', 'Abziehbare Vorsteuer 7 %', 2, NULL);
INSERT INTO konto (id,kontoart_id, kontotyp_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1003, 6, 1, '3800', 'Umsatzsteuer', 2, NULL);

-- Steuern SKR04
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (1001,'Vorsteuer 16%', '16', 1001);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (1002,'Vorsteuer 7%', '7', 1002);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (1003,'Umsatzsteuer 16%', '16', 1003);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (1004,'Umsatzsteuer 15%', '15', 1003);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (1005,'Umsatzsteuer 7%', '7', 1003);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (1006,'Vorsteuer 0%', '0', 1001);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (1007,'Umsatzsteuer 0%', '0', 1003);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (1008,'Vorsteuer 19%', '19', 1001);
INSERT INTO steuer (id,name, satz, steuerkonto_id) VALUES (1009,'Umsatzsteuer 19%', '19', 1003);

-- Restliche SKR04-Konten
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1100, 4, '0135', 'EDV-Software', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1101, 4, '0200', 'Grundstücke und Bauten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1102, 4, '0270', 'Garagen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1103, 4, '0285', 'Hof- und Wegbefestigungen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1104, 4, '0290', 'Einrichtungen für Geschäfts- und Fabrikbauten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1105, 4, '0370', 'Andere Bauten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1106, 4, '0360', 'Wohnbauten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1107, 4, '0305', 'Garagen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1108, 4, '0310', 'Außenanlagen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1109, 4, '0315', 'Hof- und Wegbefestigungen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1110, 4, '0320', 'Einrichtungen für Wohnbauten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1111, 4, '0240', 'Geschäftsbauten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1112, 4, '0250', 'Fabrikbauten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1113, 4, '0440', 'Maschinen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1114, 4, '0460', 'Maschinengebundene Werkzeuge', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1115, 4, '0420', 'Technische Anlagen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1116, 4, '0510', 'Andere Anlagen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1117, 4, '0470', 'Betriebsvorrichtungen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1118, 4, '0500', 'Andere Anlagen, Betriebs- und Geschäftsausstattun', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1119, 4, '0520', 'Pkw', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1120, 4, '0540', 'Lkw', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1121, 4, '0560', 'Sonstige Transportmittel', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1122, 2, '6220', 'Abschreibungen', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1123, 4, '0650', 'Büroeinrichtung', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1124, 4, '0640', 'Ladeneinrichtung', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1125, 4, '0620', 'Werkzeuge', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1126, 4, '0670', 'Geringwertige Wirtschaftsgüter bis EUR 400.-', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1127, 4, '0690', 'Sonstige Betriebs- und Geschäftsausstattung', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1128, 3, '1600', 'Kasse', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1129, 3, '1700', 'Postgiro', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1130, 3, '1800', 'Bank', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1131, 3, '1460', 'Geldtransit', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1132, 3, '1485', 'Verrechnungskonto Gewinnermittlung § 4 Abs.3 EStG', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1133, 6, '1406', 'Abziehbare Vorsteuer 16%', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1134, 3, '1370', 'Durchlaufende Posten', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1135, 3, '3500', 'Sonstige Verbindlichkeiten', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1136, 2, '3820', 'Umsatzsteuer-Vorauszahlungen', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1137, 2, '3840', 'Umsatzsteuer laufendes Jahr', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1138, 2, '3841', 'Umsatzsteuer Vorjahr', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1139, 2, '3845', 'Umsatzsteuer frühere Jahre', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1140, 5, '2100', 'Privatentnahmen', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1141, 5, '2550', 'Privatsteuern', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1142, 5, '2200', 'Sonderausgaben', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1143, 5, '2280', 'Außergewöhnliche Belastungen', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1144, 5, '2130', 'Eigenverbrauch', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1145, 5, '2180', 'Privateinlagen', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1146, 2, '7300', 'Zinsen und ähnliche Aufwendungen', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1147, 2, '5900', 'Fremdleistungen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1148, 2, '5200', 'Wareneingang', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1149, 2, '5300', 'Wareneingang 7% Vorsteuer', 2, 1002);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1150, 2, '5400', 'Wareneingang 19% Vorsteuer', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1151, 2, '6000', 'Löhne und Gehälter', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1152, 2, '6010', 'Löhne', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1153, 2, '6020', 'Gehälter', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1154, 2, '6050', 'Ehegattengehalt', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1155, 2, '6110', 'Gesetzliche soziale Aufwendungen', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1156, 2, '6120', 'Beiträge zur Berufsgenossenschaft', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1157, 2, '6030', 'Aushilfslöhne', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1158, 2, '6040', 'Lohnsteuer für Aushilfen', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1159, 2, '6305', 'Raumkosten', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1160, 2, '6310', 'Miete', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1161, 2, '6315', 'Pacht', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1162, 2, '6320', 'Heizung', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1163, 2, '6325', 'Gas, Strom, Wasser (Verwaltung, Vertrieb)', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1164, 2, '6330', 'Reinigung', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1165, 2, '6335', 'Instandhaltung betrieblicher Räume', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1166, 2, '6345', 'Sonstige Raumkkosten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1167, 2, '6860', 'Nicht anrechenbare Vorsteuer', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1168, 2, '6400', 'Versicherungen', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1169, 2, '6420', 'Beiträge', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1170, 2, '6430', 'Sonstige Abgaben', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1171, 2, '6500', 'Fahrzeugkosten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1172, 2, '7685', 'Kfz-Steuern', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1173, 2, '6520', 'Kfz-Versicherungen', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1174, 2, '6530', 'Laufende Kfz-Betriebskosten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1175, 2, '6540', 'Kfz-Reparaturen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1176, 2, '6550', 'Garagenmieten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1177, 2, '6560', 'Fremdfahrzeuge', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1178, 2, '6570', 'Sonstige Kfz-Kosten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1179, 2, '6600', 'Werbekosten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1180, 2, '6630', 'Repräsentationskosten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1181, 2, '6640', 'Bewirtungskosten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1182, 2, '6650', 'Reisekosten Arbeitnehmer', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1183, 2, '6670', 'Reisekosten Unternehmer', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1184, 2, '6710', 'Verpackungmaterial', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1185, 2, '6780', 'Fremdarbeiten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1186, 2, '6460', 'Reparaturen und Instandhaltung von techn. Anlagen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1187, 2, '6470', 'Reparaturen und Instandhaltung BGA', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1188, 2, '6490', 'Sonstige Reparaturen und Instandhaltungen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1189, 2, '6840', 'Mietleasing', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1190, 2, '6250', 'Kaufleasing', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1191, 2, '6268', 'Abschreibungen auf Aufwendungen für Ingangsetzung', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1192, 2, '6260', 'Sofortabschreibung GWG''s', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1193, 2, '6300', 'Sonstige betriebliche Aufwendungen', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1194, 2, '6800', 'Porto', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1195, 2, '6805', 'Telefon', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1196, 2, '6815', 'Bürobedarf', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1197, 2, '6820', 'Zeitschriften, Bücher', 2, 1002);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1198, 2, '6825', 'Rechts- und Beratungskosten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1199, 2, '6830', 'Buchführungskosten', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1200, 2, '6855', 'Kosten des Geldverkehrs', 2, 1006);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1201, 2, '6850', 'Betriebsbedarf', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1202, 2, '6845', 'Werkzeuge und Kleingeräte', 2, 1008);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1203, 1, '4100', 'Steuerfreie Umsätze', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1204, 1, '4200', 'Erlöse (keine UST)', 2, 1007);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1205, 1, '4300', 'Erlöse (7% UST)', 2, 1005);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1206, 1, '4400', 'Erlöse (19% UST)', 2, 1009);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1207, 1, '4855', 'Erlöse aus Anlagenverkäufen', 2, 1009);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1208, 1, '4600', 'Eigenverbrauch', 2, 1009);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1209, 1, '4695', 'Umsatzsteuervergütungen', 2, NULL);
INSERT INTO konto (id, kontoart_id, kontonummer, name, kontenrahmen_id, steuer_id) VALUES (1210, 1, '7100', 'Zinserträge', 2, 1007);

INSERT INTO version (name,version) values ('db',1);
