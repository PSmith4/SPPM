class GUI_DB_Link{
	public void GUI_DB_Link(){};
	
        /*
        Output, 2d array of stock in system, with ID number, Name and Stock
        */
        
	public static Object[][] getFullCataloge() 
	{
	return (
	new Object[][] {{new Integer(165615), "Panadol", new Integer(4)},
	{new Integer(186166), "Other thing1",new Integer(5)},
	{new Integer(186166), "Other thing2",new Integer(9)},
	{new Integer(186166), "Other thing3",new Integer(2)},
	{new Integer(186166), "Other thing4",new Integer(1)},
	{new Integer(186166), "thing with no stock",new Integer(0)},
	{new Integer(186166), "Other thing5",new Integer(5)},
	{new Integer(375734), "Third thing6",new Integer(10)}}	);
        }
        
        //takes an array: ID | stock change.
        public static void StockChange(Object[][] changelist)
        {
            for(int i=0; i< changelist.length; i++)
                System.out.println(changelist[i][0]+" "+changelist[i][1]);
        }

}