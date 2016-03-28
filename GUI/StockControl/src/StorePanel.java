
import javax.swing.*;
import java.awt.Dimension;
import java.util.Arrays;
class StorePanel extends JPanel {
	public StorePanel()
  {
	 
	 setSize(836,546); 
	 JButton finishButton = new JButton("Finish");

	 Object[][] tempCatContent =GUI_DB_Link.getFullCataloge();
	 System.out.println(tempCatContent.length);
  Object[][] CatContent= new Object[tempCatContent.length][3];
  System.out.println(CatContent.length);
  for (int i =0; i< tempCatContent.length; i++)
  {
	  CatContent[i][0]=tempCatContent[i][0];
	  CatContent[i][1]=tempCatContent[i][1];
	  CatContent[i][2] = new JButton("Add");
  }
  
    JTable CatalougeTable = new JTable(CatContent, new String[]{"ID","Name","Add to Sale"}); //"Add to Sale"
	JScrollPane CatalougeScoll = new JScrollPane(CatalougeTable);
	CatalougeTable.setPreferredScrollableViewportSize(new Dimension(418, 242));
    CatalougeTable.setFillsViewportHeight(true);
	add(CatalougeScoll);
	add(finishButton);
	
	
   }
  

}