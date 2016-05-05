package passtask.db;

import static org.junit.Assert.fail;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DatabaseTests
{
    @Before
    public void resetTestTable()
    {
	String resetQuery = "CALL SPPM_TEST.RESET_SPPM_TEST";
	java.sql.Statement resetTable = null;
	try
	{
	    DataBaseAccess.setTestConnection(true);
	    resetTable = DataBaseAccess.getInstance().getConn().createStatement();
	    resetTable.execute(resetQuery);
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("Connection Failed");
	}
    }

    @Test
    public void testConnection()
    {
	try
	{
	    DataBaseAccess.setTestConnection(true);
	    DataBaseAccess.getInstance();
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("Connection Failed");
	}
    }

    @Test
    public void testGetPOSInterfaceData()
    {
	try
	{
	    DataBaseAccess.setTestConnection(true);
	    DataBaseAccess.getInstance();
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("Connection Failed");
	}

	// barcode, name, price, stock
	Object[][] returnData = { { 1, "Test Prod 1", 2.0, 10.0 }, { 2, "Test Prod 2", 2.0, 5.0 } };
	Assert.assertArrayEquals(returnData, DataBaseAccess.getPOSInterfaceData());

    }

    @Test
    public void testGetShipmentUpdate()
    {
	try
	{
	    DataBaseAccess.setTestConnection(true);
	    DataBaseAccess.getInstance();

	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("something went wrong");
	}

	// barcode, name, stock
	Object[][] returnData = { { 1, "Test Prod 1", 10.0 }, { 2, "Test Prod 2", 5.0 } };
	Assert.assertArrayEquals(returnData, DataBaseAccess.getShipmentUpdate());
    }

    @Test
    public void testMakeSale()
    {
	try
	{
	    DataBaseAccess.setTestConnection(true);
	    DataBaseAccess.getInstance();

	    Object[][] sendData = { { 1, 11.0 }, { 2, 6.0 } };
	    DataBaseAccess.makeSale(sendData);

	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("something went wrong");
	}

	// barcode, name, price, stock
	Object[][] returnData = { { 1, "Test Prod 1", 11.0 }, { 2, "Test Prod 2", 6.0 } };
	Assert.assertArrayEquals(returnData, DataBaseAccess.getShipmentUpdate());
    }

    @Test
    public void testGetItemCatalogue()
    {
	try
	{
	    DataBaseAccess.setTestConnection(true);
	    DataBaseAccess.getInstance();

	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("something went wrong");
	}

	// barcode, name, description
	Object[][] returnData = { { 1, "Test Prod 1", "This is the first test product" },
		{ 2, "Test Prod 2", "This is the second test product" } };
	Assert.assertArrayEquals(returnData, DataBaseAccess.getItemCatalogue());
    }

    @Test
    public void testGetSaleHistory()
    {
	try
	{
	    DataBaseAccess.setTestConnection(true);
	    DataBaseAccess.getInstance();

	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("something went wrong");
	}

	// TODO: Test sale history
	// barcode, name, updated_stock, saletime (I don't think I'll be able to
	// test for sale time because it's only included by the DB and therefore
	// I need to look and read in order to find) I'm also uncertain how to
	// test for only partial array matching.

    }

    @Test
    public void testAddItem()
    {
	try
	{
	    DataBaseAccess.setTestConnection(true);
	    DataBaseAccess.getInstance();

	    Object[] sendData = { "Test Prod 3", "Description 3", 2.0 };
	    DataBaseAccess.addItem(sendData);

	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("something went wrong");
	}

	// barcode, name, description
	Object[][] returnData = { { 1, "Test Prod 1", 2.0, "This is the first test product" },
		{ 2, "Test Prod 2", 2.0, "This is the second test product" },
		{ 3, "Test Prod 3", 2.0, "Description 3" } };
	Assert.assertArrayEquals(returnData, DataBaseAccess.getItemCatalogue());
    }

    @Test
    public void testAddShipment()
    {
	try
	{
	    DataBaseAccess.setTestConnection(true);
	    DataBaseAccess.getInstance();

	    Object[][] sendData = { { 3, 5.0 } };
	    DataBaseAccess.addShipment(sendData);

	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("something went wrong");
	}

	// barcode, name, stock
	Object[][] returnData = { { 1, "Test Prod 1", 11.0 }, { 2, "Test Prod 2", 6.0 }, { 3, "Test Prod 3", 5.0 } };
	Assert.assertArrayEquals(returnData, DataBaseAccess.getShipmentUpdate());
    }
}
