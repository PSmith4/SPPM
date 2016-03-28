import javax.swing.*;
import java.awt.event.*;
/**
 * Stock Control GUI 
 * 
 * @author Phillip Smith, 7191731
 * @version 1.0 29-3-16
 */
class StockControlGUI{
	public StockControlGUI() {
		
		JFrame f = new JFrame();
		f.setTitle("Stock Controller");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		f.add(new StorePanel());
		
		f.pack();
		f.setSize(936,546); 
		f.setLocation(10,200); 
		
		f.setVisible(true);
}
}