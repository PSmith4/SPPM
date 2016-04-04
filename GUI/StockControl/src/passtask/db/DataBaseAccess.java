package passtask.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseAccess
{
    private Connection dbConn = null;
    private static DataBaseAccess instance = null;
    private static String userName = "user";
    private static String password = "password";
    private static String serverName = "localhost";
    private static String portNumber = "5432";

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
     */
    private static Connection getConnection() throws SQLException
    {
	Connection conn = null;
	Properties connectionProps = new Properties();
	connectionProps.put("user", userName);
	connectionProps.put("password", password);

	// if (this.dbms.equals("mysql"))
	conn = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + portNumber + "/", connectionProps);

	return conn;
    }

    // public

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
		{ new Integer(186166), "thing with no stock", new Integer(0) },
		{ new Integer(186166), "Other thing5", new Integer(5) },
		{ new Integer(375734), "Third thing6", new Integer(10) } });
    }

    // takes an array: ID | stock change.
    public static void StockChange(Object[][] changelist)
    {
	for(int i = 0; i < changelist.length; i++)
	    System.out.println(changelist[i][0] + " " + changelist[i][1]);
    }

}
