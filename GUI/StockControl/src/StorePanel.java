
import javax.swing.*;
import java.awt.Dimension;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.AbstractTableModel;

class StorePanel extends JPanel {
    
   class AddButtonRenderer implements TableCellRenderer{
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        
        //System.out.println("addbuttonrndere");
        
        JButton showButton = new JButton("View Articles");
       /* showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            JOptionPane.showMessageDialog(null, "HA-HA!");
            }
        });
 */
        JPanel panel = new JPanel();
        panel.add(showButton);
 
        if (isSelected) {
             panel.setBackground(table.getSelectionBackground());
        }else{
         panel.setBackground(table.getBackground());
        }
        return panel;
    }
}
    
  class CatTableModel extends AbstractTableModel
  {
      Object[][] stockitems;
      String[] colnames= {"ID","Name","Add to Sale"};
       public CatTableModel(Object[][] stockitems)  
       {   
           super();
           this.stockitems = stockitems;
         
       }
       
       public Class getColumnClass(int columnIndex) {
           if(columnIndex == 0)
               return Integer.class;
           else if (columnIndex ==1)
               return String.class;
           return JButton.class;
       }
       public int getColumnCount() { return 3; }
       public int getRowCount() {return this.stockitems.length;}
       
       public void setValueAt(Object value, int row, int col)
       {
           stockitems[row][col] = value;
       }
       public Object getValueAt(int row, int col)
       {
           return stockitems[row][col];
       }
       
       public String getColumnName(int col) {
            return colnames[col];
       }
       

  }
  
   public StorePanel()
  {
	 
	 setSize(836,546); 
	 JButton finishButton = new JButton("Finish");

	 Object[][] tempCatContent =GUI_DB_Link.getFullCataloge();

  Object[][] CatContent= new Object[tempCatContent.length][3];

  for (int i =0; i< tempCatContent.length; i++)
  {
	  CatContent[i][0]=tempCatContent[i][0];
	  CatContent[i][1]=tempCatContent[i][1];
	  CatContent[i][2] = new JButton("Add");
  }
  
    JTable CatalougeTable = new JTable(new CatTableModel(CatContent)); //"Add to Sale"
    CatalougeTable.setDefaultRenderer(JButton.class, new AddButtonRenderer());
    
    JScrollPane CatalougeScoll = new JScrollPane(CatalougeTable);
	CatalougeTable.setPreferredScrollableViewportSize(new Dimension(418, 242));
    CatalougeTable.setFillsViewportHeight(true);
	add(CatalougeScoll);
        
    add(finishButton);
	
	
   }
  

        
}
