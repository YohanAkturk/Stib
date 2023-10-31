----------------------
-- Créer les tables
----------------------


CREATE TABLE IF NOT EXISTS `STATIONS` (
	`id`	INTEGER,
	`name`	TEXT NOT NULL,
	PRIMARY KEY(`id`)
);
CREATE TABLE IF NOT EXISTS `LINES` (
	`id`	INTEGER,
	PRIMARY KEY(`id`)
);
CREATE TABLE IF NOT EXISTS `STOPS` (
	`id_line`	INTEGER NOT NULL,
	`id_station`	INTEGER NOT NULL,
	`id_order`	INTEGER NOT NULL,
	FOREIGN KEY(`id_line`) REFERENCES `LINES`(`id`),
	FOREIGN KEY(`id_station`) REFERENCES `STATIONS`(`id`),
	PRIMARY KEY(`id_line`,`id_station`)
);
CREATE TABLE FAVORI(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT NOT NULL,
	departure INTEGER NOT NULL,
	arrival INTEGER NOT NULL,
	FOREIGN KEY (departure) REFERENCES STATIONS(id),
	FOREIGN KEY (arrival) REFERENCES STATIONS(id)
);
