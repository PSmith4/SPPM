package passtask.gui;

import java.sql.SQLException;
import passtask.gui.newStock;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * Stock Control passtask.gui
 * 
 * @author Phillip Smith, 7191731
 * @version 1.0 29-3-16
 */
public class StockControlGUI 
{
    static StorePanel sales ;
    static newStock incomingStock ;
    static newItem additem ;
    static ReportRequestGUI reporter ;
    static JFrame f = new JFrame();
    static JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
    public StockControlGUI() throws SQLException 
    {

       sales = new StorePanel();
       incomingStock= new newStock();
       additem= new newItem();
       reporter= new ReportRequestGUI();
       tabbedPane.removeAll();
       tabbedPane.add("Sale", sales);
       tabbedPane.add("Stock delivery", incomingStock);
       tabbedPane.add("Add item", additem);
       tabbedPane.add("Get Report", reporter);
   
          
	
	f.setTitle("Stock Controller");
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// tabbedPane.pr


	f.pack();
	f.add(tabbedPane);
	f.pack();
	f.setSize(936, 546);
	f.setLocation(10, 200);

	f.setVisible(true);
    }
    public static void update() throws SQLException
    {
        System.out.println("Rebooting GUI");
       sales.update();
       additem.update();
       incomingStock.update();
    }
    
}
