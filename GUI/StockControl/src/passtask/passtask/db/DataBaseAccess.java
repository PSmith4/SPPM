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
    private Connection dbConn = null;
    private static DataBaseAccess instance = null;
    private static String userName = "sppm_1";
    private static String password = "sppm1234";
    private static String serverName = "101.188.15.208";
    private static String portNumber = "3306";
    private static String databaseName = "";

    /**
     * Empty constructor for singleton.
     */
    private DataBaseAccess()
    {

    };

    /**
     * Singleton implementation.
     * 
     * @return an instance of the database access class.
     */
    public static DataBaseAccess getInstance()
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
     * @return a connection to the database.
     * @throws SQLException
     *             if there is a problem connecting to the database.
     */
    private static Connection getConnection() throws SQLException
    {
	Connection conn = null;
	Properties connectionProps = new Properties();
	connectionProps.put("user", userName);
	connectionProps.put("password", password);

	conn = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + portNumber + "/", connectionProps);

	return conn;
    }

    /**
     * Gets the ID, Name, Current Stock Level and Price of all items.
     * 
     * @return a two dimensional array containing ID (barcode), Name, Current
     *         Stock Levels and Price of all items.
     */
    public static Object[][] getPOSInterfaceData()
    {
	Statement getData = null;
	Object[][] returnData = null;

	String selectData = "SELECT barcode, prod_name, price FROM product INNER JOIN inventory on product.barcode = inventory.barcode";

	try(Connection conn = getConnection())
	{
	    getData = conn.createStatement();
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
		returnData[i][0] = results.getInt(0);
		returnData[i][1] = results.getString(1);
		returnData[i][2] = results.getDouble(2); // TODO may need to
							 // inclease col index
							 // by one if the join
							 // puts both cols in
							 // resultSet.
		returnData[i][3] = results.getDouble(3);
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
    public void makeSale(Object[][] data)
    {
	PreparedStatement updateInventory = null;
	PreparedStatement updateSaleHistory = null;

	String updateInventoryString = "UPDATE " + databaseName + ".inventory SET stock = ? WHERE barcode = ?";
	String updateHistoryString = "INSERT INTO SALE_HISTORY (barcode, updated_stock) VALUES (?, ?)";

	try(Connection conn = getConnection())
	{
	    conn.setAutoCommit(false);
	    updateInventory = conn.prepareStatement(updateInventoryString);
	    updateSaleHistory = conn.prepareStatement(updateHistoryString);

	    for(int i = 0; i < data.length; i++)
	    {
		updateInventory.setInt(1, (int) data[i][1]);
		updateInventory.setInt(2, (int) data[i][0]);
		updateInventory.executeUpdate();
		updateSaleHistory.setInt(1, (int) data[i][0]);
		updateSaleHistory.setInt(2, (int) data[i][1]);
		updateSaleHistory.executeUpdate();

		conn.commit();
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
	Statement getData = null;
	Object[][] returnData = null;

	String selectData = "SELECT barcode, prod_name, description FROM product";

	try(Connection conn = getConnection())
	{
	    getData = conn.createStatement();
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
		returnData[i][0] = results.getInt(0);
		returnData[i][1] = results.getString(1);
		returnData[i][2] = results.getString(2);
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
     * Add a new item to the product catalog.
     * 
     * @param data
     *            a two dimensional array containing the new product name, the
     *            description and the price.
     */
    public void addItem(Object[][] data)
    {
	PreparedStatement updateProduct = null;

	String updateProductString = "INSERT INTO PRODUCT (prod_name, description, price) VALUES (?, ?, ?)";

	try(Connection conn = getConnection())
	{
	    conn.setAutoCommit(false);
	    updateProduct = conn.prepareStatement(updateProductString);

	    for(int i = 0; i < data.length; i++)
	    {
		updateProduct.setString(1, (String) data[i][0]);
		updateProduct.setString(2, (String) data[i][1]);
		updateProduct.setDouble(3, (int) data[i][2]);
		updateProduct.executeUpdate();

		conn.commit();
	    }
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	}
    }

    /*
     * Output, 2d array of stock in system, with ID number, Name and Stock
     */

    public static Object[][] getFullCataloge()
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

	return(new Object[][] { { new Integer(165615), "Panadol", new Integer(4) },
		{ new Integer(186166), "Other thing1", new Integer(5) },
		{ new Integer(186166), "Other thing2", new Integer(9) },
		{ new Integer(186166), "Other thing3", new Integer(2) },
		{ new Integer(186166), "Other thing4", new Integer(1) },
		{ new Integer(186166), "Other thing3", new Integer(2) },
		{ new Integer(186166), "Other thing4", new Integer(1) },
		{ new Integer(186166), "Other thing3", new Integer(2) },
		{ new Integer(186166), "Other thing4", new Integer(1) },
		{ new Integer(186166), "thing with no stock", new Integer(0) },
		{ new Integer(186166), "Other thing5", new Integer(5) },
		{ new Integer(375734), "Third thing6", new Integer(10) } });
    }

    public static Object[][] getCatalogeWithDescription()
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

	return(new Object[][] {
		{ new Integer(165615), "Panadol", "its this little pill that makes all your pain go away",
			new Integer(1) },
		{ new Integer(186166), "Other thing1", "words go here", new Integer(5) },
		{ new Integer(186166), "Other thing2", "words go here", new Integer(1) },
		{ new Integer(186166), "Other thing3", "words go here", new Integer(1) },
		{ new Integer(186166), "Other thing4", "words go here", new Integer(1) },
		{ new Integer(186166), "Other thing3", "words go here", new Integer(1) },
		{ new Integer(186166), "Other thing4", "words go here", new Integer(1) },
		{ new Integer(186166), "Other thing3", "words go here", new Integer(1) },
		{ new Integer(186166), "Other thing4", "words go here", new Integer(1) },
		{ new Integer(186166), "thing with no stock", "words go here", new Integer(1) },
		{ new Integer(186166), "Other thing5", "words go here", new Integer(1) },
		{ new Integer(375734), "Third thing6", "words go here", new Integer(1) } });
    }

    public static void NewITem(Object[] newItem)
    {
	System.out.println(newItem[0] + " " + newItem[1] + " " + newItem[2]);
    }

    // takes an array: ID | stock change.
    public static void StockChange(Object[][] changelist)
    {
	for(int i = 0; i < changelist.length; i++)
	    System.out.println(changelist[i][0] + " " + changelist[i][1]);
    }

}
