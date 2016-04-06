# Simple extract query for the database
# Query to return the current stock of all items
SELECT	invt.barcode, 
		prod.prod_name, 
        prod.price, 
        invt.stock
FROM	inventory invt, 
		product prod
WHERE	invt.barcode = prod.barcode;