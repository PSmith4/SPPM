package db;

import static org.junit.Assert.fail;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;

public class DatabaseTests
{

    @Test
    public void testGetPOSInterfaceData()
    {
	try
	{
	    DataBaseAccess dba = DataBaseAccess.getInstance();

	    Object[][] returnData = { { 1, "Test Prod 1", 2.0, 10.0 }, { 2, "Test Prod 2", 2.0, 5.0 } };
	    Assert.assertArrayEquals(returnData, DataBaseAccess.getPOSInterfaceData());
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("something went wrong");
	}

    }

    @Test
    public void testMakeSale()
    {
	fail("Not yet implemented");
    }

    @Test
    public void testGetItemCatalogue()
    {
	fail("Not yet implemented");
    }

    @Test
    public void testConnection()
    {
	try
	{
	    DataBaseAccess dba = DataBaseAccess.getInstance();
	}
	catch(SQLException e)
	{
	    e.printStackTrace();
	    fail("Connection Failed");
	}
    }

    @Test
    public void testAddItem()
    {
	fail("Not yet implemented");
    }

    @Test
    public void testGetShipmentUpdate()
    {
	fail("Not yet implemented");
    }

    @Test
    public void testAddShipment()
    {
	fail("Not yet implemented");
    }

}