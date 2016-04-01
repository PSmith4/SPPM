
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


class newStock extends JPanel
{
   
    final ArrayList<Object[]> CatContent= new ArrayList<Object[]>();
    
    public newStock()
    {
       setSize(836,546); 
        Object[][] tempCatContent =GUI_DB_Link.getFullCataloge();
        
        for (int i =0; i< tempCatContent.length; i++)
        {
            int buttoncount;
            CatContent.add(new Object[4]);
            buttoncount= CatContent.size()-1;
            CatContent.get(buttoncount)[0] =tempCatContent[i][0];
            CatContent.get(buttoncount)[1] =tempCatContent[i][1];
            CatContent.get(buttoncount)[2] =tempCatContent[i][2];
          
            JSpinner newstockSpin = new JSpinner(new SpinnerNumberModel());
            CatContent.get(buttoncount)[3] =newstockSpin;   
 
        }
    
  
    JTable CatalougeTable = new JTable(new CatTableModel(CatContent)); //"Add to Sale"
    CatalougeTable.setDefaultRenderer(JButton.class, new TabbleButtonRenderer("+"));
    CatalougeTable.setRowHeight(35);
    CatalougeTable.addMouseListener(new TableButtonListener(CatalougeTable));
    JScrollPane CatalougeScoll = new JScrollPane(CatalougeTable);
    CatalougeTable.setPreferredScrollableViewportSize(new Dimension(418, 242));
    CatalougeTable.setFillsViewportHeight(true);
    CatalougeTable.setRowMargin(5);
    
    JButton FinishAdding = new JButton("Add stock to system");
    
    
    add(CatalougeTable);
    add(FinishAdding);
    
    
    }
    
        
        
        
        class TableButtonListener extends MouseAdapter {
      private final JTable table;

      public TableButtonListener(JTable table) {
        this.table = table;
      }

       public void mouseClicked(MouseEvent e) {
        int column = table.getColumnModel().getColumnIndexAtX(e.getX());
        int row    = e.getY()/table.getRowHeight(); 
        
        if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0 ) {
          Object value = table.getValueAt(row, column);
          System.out.println("Value :"+value.getClass().getName());
          if (value instanceof JButton) {
            ((JButton)value).doClick();
          }

        }
      }
    }
        
        
        class TabbleButtonRenderer implements TableCellRenderer{
       private String text;
       public TabbleButtonRenderer(String text)
       {
           super();
           this.text=text;
           
       }
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        
        //System.out.println("addbuttonrndere");
      
        JButton showButton = new JButton(text);
        //JSpinner spinner = new JSpinner(new SpinnerNumberModel());

        JPanel panel = new JPanel();
        //panel.add(spinner);
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
      ArrayList<Object[]> stockitems;
      String[] colnames= {"ID","Name","Stock","Add to Sale"};
       public CatTableModel(ArrayList<Object[]> stockitems)  
       {   
           super();
           this.stockitems = stockitems;
         
       }
       
       public Class getColumnClass(int columnIndex) {
           if(columnIndex == 0)
               return Integer.class;
           else if (columnIndex ==1)
               return String.class;
           else if (columnIndex ==2)
               return Integer.class;
           return JSpinner.class;
       }
       public int getColumnCount() { return 4; }
       public int getRowCount() {return this.stockitems.size();}
       
       public void setValueAt(Object value, int row, int col)
       {
           Object[] temp = stockitems.get(row);
           temp[col]=value;
           stockitems.set(row, temp);
       }
       
       public Object getValueAt(int row, int col)
       {
           return stockitems.get(row)[col];
       }
       
       public String getColumnName(int col) {
            return colnames[col];
       }
      

  }
        
}