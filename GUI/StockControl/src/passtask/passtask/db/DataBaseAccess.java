package passtask.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataBaseAccess
{
    private static Connection dbConn = null;
    private static DataBaseAccess instance = null;
    private static String userName = "sppm_1";
    private static String password = "sppm1234";
    // private static String serverName = "101.188.15.208";
    private static String serverName = "101.181.52.75";
    private static String portNumber = "3306";
    private static String databaseName = "sppm_phpsrs";
    private static String testDatabaseName = "sppm_test";
    private static boolean testEnv = false;

    /**
     * Gets the current connection.
     * 
     * @return the current connection.
     */
    public Connection getConn()
    {
	if(dbConn == null)
	{
	    try
	    {
		getConnection();
	    }
	    catch(SQLException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return DataBaseAccess.dbConn;
    }

    /**
     * Empty constructor for singleton.
     * 
     * @throws SQLException
     *             if connection fails.
     */
    private DataBaseAccess() throws SQLException
    {
	if(testEnv)
	{
	    getTestConnection();
	}
	else
	{
	    getConnection();
	}
    };

    /**
     * Singleton implementation.
     * 
     * @return an instance of the database access class.
     * @throws SQLException
     *             if connection fails.
     */
    public static DataBaseAccess getInstance() throws SQLException
    {
	if(instance == null)
	{
	    instance = new DataBaseAccess();
	}

	return instance;
    }

    /**
     * Gets a copy of the connection to the database.
     * 
     * @throws SQLException
     *             if there is a problem connecting to the database.
     */
    private static void getConnection() throws SQLException
    {
	Connection conn = null;
	Properties connectionProps = new Properties();
	connectionProps.put("user", userName);
	connectionProps.put("password", password);
	connectionProps.put("useSSL", "false");

	conn = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + portNumber + "/" + databaseName,
		connectionProps);

	dbConn = conn;
    }

    /**
     * Gets a copy of the connection to the test database.
     * 
     * @throws SQLException
     *             if there is a problem connecting to the database.
     */
    private static void getTestConnection() throws SQLException
    {
	Connection conn = null;
	Properties connectionProps = new Properties();
	connectionProps.put("user", userName);
	connectionProps.put("password", password);
	connectionProps.put("useSSL", "false");

	conn = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + portNumber + "/" + testDatabaseName,
		connectionProps);

	dbConn = conn;
    }

    /**
     * Gets the ID, Name, Current Stock Level and Price of all items.
     * 
     * @return a two dimensional array containing ID (barcode), Name, Current
     *         Stock Levels and Price of all items.
     */
    public static Object[][] getPOSInterfaceData()
    {
	if(dbConn == null)
	{
	    try
	    {
		DataBaseAccess.getInstance();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}

	Statement getData = null;
	Object[][] returnData = null;

	String selectData = "SELECT product.barcode, product.prod_name, product.price, inventory.stock FROM PRODUCT INNER JOIN INVENTORY on product.barcode = inventory.barcode";

	try
	{
	    getData = dbConn.createStatement();
	    ResultSet results = getData.executeQuery(selectData);
	    int rowCount = 0, i = 0;

	    if(results.last())
	    {
		rowCount = results.getRow();
		results.beforeFirst();
	    }

	    returnData = new Object[rowCount][4];

	    while(results.next())
	    {
		returnData[i][0] = results.getInt(1);
		returnData[i][1] = results.getString(2);
		returnData[i][2] = results.getDouble(3);
		returnData[i][3] = results.getDouble(4);
		i++;
	    }
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}

	return returnData;
    }

    /**
     * Updates the database with the information pertaining to any sale.
     * 
     * @param data
     *            a multidimensional object array containing sale item ID
     *            (barcode) and updated stock amount (old current - amount
     *            sold).
     */
    public static void makeSale(Object[][] data)
    {
	if(dbConn == null)
	{
	    try
	    {
		DataBaseAccess.getInstance();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}

	PreparedStatement updateInventory = null;
	PreparedStatement updateSaleHistory = null;

	String updateInventoryString = "UPDATE " + databaseName + ".inventory SET stock = ? WHERE barcode = ?";
	String updateHistoryString = "INSERT INTO SALE_HISTORY (barcode, updated_stock) VALUES (?, ?)";

	try
	{
	    dbConn.setAutoCommit(false);
	    updateInventory = dbConn.prepareStatement(updateInventoryString);
	    updateSaleHistory = dbConn.prepareStatement(updateHistoryString);

	    for(int i = 0; i < data.length; i++)
	    {
		updateInventory.setInt(1, (int) data[i][1]);
		updateInventory.setInt(2, (int) data[i][0]);
		updateInventory.executeUpdate();
		updateSaleHistory.setInt(1, (int) data[i][0]);
		updateSaleHistory.setInt(2, (int) data[i][1]);
		updateSaleHistory.executeUpdate();

		dbConn.commit();
	    }
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
    }

    /**
     * Gets the ID (barcode), name, and description from the database for the
     * add a new item interface.
     * 
     * @return a two dimensional array containing ID (barcode), Name and
     *         Description.
     */
    public static Object[][] getItemCatalogue()
    {
	if(dbConn == null)
	{
	    try
	    {
		DataBaseAccess.getInstance();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}

	Statement getData = null;
	Object[][] returnData = null;

	String selectData = "SELECT barcode, prod_name, price, description FROM product";

	try
	{
	    getData = dbConn.createStatement();
	    ResultSet results = getData.executeQuery(selectData);
	    int rowCount = 0, i = 0;

	    if(results.last())
	    {
		rowCount = results.getRow();
		results.beforeFirst();
	    }

	    returnData = new Object[rowCount][4];

	    while(results.next())
	    {
		returnData[i][0] = results.getInt(1);
		returnData[i][1] = results.getString(2);
		returnData[i][2] = results.getInt(3);
		returnData[i][3] = results.getString(4);
		i++;
	    }
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}

	return returnData;
    }

    /**
     * Add A new item to the product catalog.
     * 
     * @param data
     *            a one dimensional array containing the new product name, the
     *            description and the price.
     */
    public static void addItem(Object[] data)
    {
	if(dbConn == null)
	{
	    try
	    {
		DataBaseAccess.getInstance();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}

	PreparedStatement updateProduct = null;
	PreparedStatement getBarcode = null;
	PreparedStatement updateInventory = null;

	String updateProductString = "INSERT INTO PRODUCT (prod_name, description, price) VALUES (?, ?, ?)";
	String getBarcodeString = "SELECT barcode FROM PRODUCT WHERE prod_name = ?";
	String updateInventoryString = "INSERT INTO INVENTORY (barcode, stock) VALUES (?, ?)";

	try
	{
	    dbConn.setAutoCommit(false);
	    updateProduct = dbConn.prepareStatement(updateProductString);

	    updateProduct.setString(1, (String) data[0]);
	    updateProduct.setString(2, (String) data[1]);
	    updateProduct.setDouble(3, (double) data[2]);
	    System.out.println(updateProduct.executeUpdate());

	    dbConn.commit();

	    getBarcode = dbConn.prepareStatement(getBarcodeString);
	    getBarcode.setString(1, (String) data[0]);

	    ResultSet results = getBarcode.executeQuery();
	    results.next();
	    int barcode = results.getInt(1);

	    updateInventory = dbConn.prepareStatement(updateInventoryString);

	    updateInventory.setInt(1, barcode);
	    updateInventory.setInt(2, 0);
	    System.out.println(updateInventory.executeUpdate());

	    dbConn.commit();

	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
    }

    /**
     * Gets the ID (barcode), name and stock of all products.
     * 
     * @return a two dimensional array that has the ID (barcode), name and
     *         stock of all products.
     */
    public static Object[][] getShipmentUpdate()
    {
	if(dbConn == null)
	{
	    try
	    {
		DataBaseAccess.getInstance();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}

	Statement getData = null;
	Object[][] returnData = null;

	String selectData = "SELECT product.barcode, product.prod_name, inventory.stock FROM PRODUCT LEFT JOIN INVENTORY on product.barcode = inventory.barcode ";

	try
	{
	    getData = dbConn.createStatement();
	    ResultSet results = getData.executeQuery(selectData);
	    int rowCount = 0, i = 0;

	    if(results.last())
	    {
		rowCount = results.getRow();
		results.beforeFirst();
	    }

	    returnData = new Object[rowCount][3];

	    while(results.next())
	    {
		returnData[i][0] = results.getInt(1);
		returnData[i][1] = results.getString(2);
		returnData[i][2] = results.getInt(3);
		i++;
	    }
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}

	return returnData;
    }

    /**
     * Adds stock to existing items.
     * 
     * @param data
     *            a two variable array with ID (barcode) and current stock
     *            levels.
     */
    public static void addShipment(Object[][] data)
    {
	if(dbConn == null)
	{
	    try
	    {
		DataBaseAccess.getInstance();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}

	PreparedStatement updateInventory = null;

	String updateInventoryString = "UPDATE INVENTORY SET stock = ? WHERE barcode = ?";

	try
	{
	    dbConn.setAutoCommit(false);
	    updateInventory = dbConn.prepareStatement(updateInventoryString);

	    for(int i = 0; i < data.length; i++)
	    {
		updateInventory.setInt(1, (Integer) data[i][1]);
		updateInventory.setInt(2, (Integer) data[i][0]);
		updateInventory.executeUpdate();

		dbConn.commit();
	    }
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
    }

    /**
     * Gets sale history information.
     * 
     * @return barcode, name, stock level at time, time.
     */
    public static Object[][] getSaleHistory()
    {
	if(dbConn == null)
	{
	    try
	    {
		DataBaseAccess.getInstance();
	    }
	    catch(SQLException e)
	    {
		e.printStackTrace();
	    }
	}

	Statement getData = null;
	Object[][] returnData = null;

	String selectData = "SELECT sale_history.barcode, product.prod_name, sale_history.updated_stock, sale_history.sale_time FROM PRODUCT LEFT JOIN SALE_HISTORY on product.barcode = sale_history.barcode ";

	try
	{
	    getData = dbConn.createStatement();
	    ResultSet results = getData.executeQuery(selectData);
	    int rowCount = 0, i = 0;

	    if(results.last())
	    {
		rowCount = results.getRow();
		results.beforeFirst();
	    }

	    returnData = new Object[rowCount][4];

	    while(results.next())
	    {
		returnData[i][0] = results.getInt(1);
		returnData[i][1] = results.getString(2);
		returnData[i][2] = results.getInt(3);
		returnData[i][3] = results.getTimestamp(4);
		i++;
	    }
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}

	return returnData;
    }

    /**
     * Sets whether to connect to the test database or the real database.
     * 
     * @param b
     *            the boolean determining whether to connect to the test
     *            database (true) or not (false).
     */
    public static void setTestConnection(boolean b)
    {
	testEnv = b;
    }
}
