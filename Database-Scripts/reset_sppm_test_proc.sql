CREATE DEFINER=`root`@`localhost` PROCEDURE `reset_sppm_test`()
BEGIN
#Drop existing tables
	DROP TABLE SALE_HISTORY;

	DROP TABLE INVENTORY;

	DROP TABLE PRODUCT;

#Create tables
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
    
#Load test values into tables
    INSERT INTO PRODUCT (prod_name, description)
	VALUES ('Test Prod 1', 'This is the first test product');
    
    INSERT INTO PRODUCT (prod_name, description)
	VALUES ('Test Prod 2', 'This is the second test product');
    
    INSERT INTO INVENTORY (barcode, stock)
	VALUES (1, 10);
    
	INSERT INTO INVENTORY (barcode, stock)
	VALUES (2, 5);
    
    INSERT INTO SALE_HISTORY (barcode, updated_stock)
	VALUES (1, 8);
    
    INSERT INTO SALE_HISTORY (barcode, updated_stock)
	VALUES (2, 5);
END