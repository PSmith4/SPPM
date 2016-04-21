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
    public StockControlGUI() throws SQLException
    {

	JFrame f = new JFrame();
	f.setTitle("Stock Controller");
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
	// tabbedPane.pr

	StorePanel sales = new StorePanel();
	newStock incomingStock = new newStock();
	newItem additem = new newItem();
	ReportRequestGUI reporter = new ReportRequestGUI();

	tabbedPane.add("Sale", sales);
	tabbedPane.add("Stock delivery", incomingStock);
	tabbedPane.add("Add item", additem);
	tabbedPane.add("Get Report", reporter);

	f.add(tabbedPane);
	f.pack();
	f.setSize(936, 546);
	f.setLocation(10, 200);

	f.setVisible(true);
    }
}
