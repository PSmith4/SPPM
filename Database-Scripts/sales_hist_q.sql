# Simple extract queries for the database - will most likely need to be updated at a later stage

# Query to return the complete sales history from the 24 hours
SELECT 	shst.sale_id, 
		shst.barcode, 
        prod.prod_name, 
        shst.updated_stock, 
        shst.sale_time
FROM 	sale_history shst,
		product prod
WHERE	shst.barcode = prod.barcode
AND		timestampdiff(day, shst.sale_time, timestamp(sysdate())) <= 1 #Calculates the number of days different between the sale_time record and today's current timestamp - If less than 7 days, return record.
;

/* Below queries are for alternate reporting periods (week, month, year), and are not required at the moment

# Query to return the complete sales history from the last week
SELECT 	shst.sale_id, 
		shst.barcode, 
        prod.prod_name, 
        shst.updated_stock, 
        shst.sale_time
FROM 	sale_history shst,
		product prod
WHERE	shst.barcode = prod.barcode
AND		timestampdiff(day, shst.sale_time, timestamp(sysdate())) <= 7 #Calculates the number of days different between the sale_time record and today's current timestamp - If less than 7 days, return record.
;

# Query to return the complete sales history from the last month
SELECT 	shst.sale_id, 
		shst.barcode, 
        prod.prod_name, 
        shst.updated_stock, 
        shst.sale_time
FROM 	sale_history shst,
		product prod
WHERE	shst.barcode = prod.barcode
AND		timestampdiff(month, shst.sale_time, timestamp(sysdate())) < 1 #Calculates the number of months different between the sale_time record and today's current timestamp - If less than 1 month, return record.
;

# Query to return the complete sales history from the last year
SELECT 	shst.sale_id, 
		shst.barcode, 
        prod.prod_name, 
        shst.updated_stock, 
        shst.sale_time
FROM 	sale_history shst,
		product prod
WHERE	shst.barcode = prod.barcode
AND		timestampdiff(year, shst.sale_time, timestamp(sysdate())) < 1 #Calculates the number of years different between the sale_time record and today's current timestamp - If less than 1 year, return record.
;

*/