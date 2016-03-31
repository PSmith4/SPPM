# create database SPPM_PHPSRS;

CREATE TABLE PRODUCT (
	barcode		int NOT NULL AUTO_INCREMENT,
    prod_name	VARCHAR(100) NOT NULL,
    description	VARCHAR(500) NOT NULL,
    price		DECIMAL(10,2) DEFAULT 2.00,
    PRIMARY KEY (barcode)
);

CREATE TABLE INVENTORY (
	barcode		int NOT NULL,
    stock		int NOT NULL,
    PRIMARY KEY (barcode),
    FOREIGN KEY (barcode) REFERENCES PRODUCT (barcode)
);

CREATE TABLE SALE_HISTORY (
	sale_id		int NOT NULL AUTO_INCREMENT,
    barcode		int NOT NULL,
    updated_stock	int NOT NULL,
    sale_time	TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    PRIMARY KEY (sale_id),
    FOREIGN KEY (barcode) REFERENCES INVENTORY (barcode)
);
