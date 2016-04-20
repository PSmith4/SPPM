package passtask.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.BorderFactory; 
import javax.swing.border.Border;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import passtask.db.DataBaseAccess;




public class ReportRequestGUI extends JPanel
{
    Border bascicBord= BorderFactory.createLineBorder(Color.black);
    final RTypePanel RType = new RTypePanel();
    final GTypePanel GType = new GTypePanel();
    final JPanel Report = new JPanel();
    public ReportRequestGUI()
    {
        
        Report.setPreferredSize(new Dimension(400,350));
        Report.setLayout(new BorderLayout());
        JButton GenerateBut = new JButton("Generate");
        setLayout(new GridBagLayout());
        
        /*Test colourings*/
        Report.add(new JLabel("no report generated for display"));
       // RType.setBackground(Color.blue);
        //GType.setBackground(Color.yellow);
        /**/
        
        
        GenerateBut.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
                if(RType.SalesHistory.isSelected())
                {
                    if(GType.Display.isSelected())
                    {
                        Report.removeAll();;
                        Report.add(new SalesHistoryReport());
                        Report.revalidate();
           
                    }
                    else if(GType.CSVGen.isSelected())    
                    {
                        System.out.println("Output CSV File to"+GType.FileLoc.getSelectedFile());
                    }
                }
                else if(RType.CurrentStock.isSelected())
                {
                    if(GType.Display.isSelected())
                    {
                        Report.removeAll();;
                        Report.add(new CurrentReport());
                        Report.revalidate();
           
                    }
                    else if(GType.CSVGen.isSelected())    
                    {
                        System.out.println("Output CSV File to"+GType.FileLoc.getSelectedFile());
                    }
                      
                }
            }
         });
        
       
            

      
	GridBagConstraints con = new GridBagConstraints();
	con.weightx = 1;
	con.weighty = 1;
	con.anchor = GridBagConstraints.FIRST_LINE_START;
	con.insets = new Insets(5, 5, 5, 5);
	con.gridx = 0;
	con.gridy = 0;
	con.gridheight = 1;
	con.gridwidth = 1;
        
        JLabel heading = new JLabel("Report Generator");
	heading.setFont(new Font("Times New Roman", 18, 18));
	add(heading, con);
        
        con.anchor = GridBagConstraints.CENTER;
        con.gridy=2;
        con.gridheight=5;
        con.gridwidth = 2;
        add(Report,con);
        
        con.gridx=3;
        con.gridheight=2;
        add(RType,con);
        
        con.gridy=4;
        add(GType,con);
        
        
        con.gridx=4;
        con.gridy=6;
        con.gridwidth=1;
        con.gridheight=1;
        add(GenerateBut,con);
        
    }
    
    private class RTypePanel extends JPanel implements ActionListener
    {
        final public JRadioButton SalesHistory = new JRadioButton("Sales History");
        final public JPanel HistorySetting = new JPanel(new GridLayout(2,2));
        final public JRadioButton CurrentStock = new JRadioButton("Current Stock");
        final public JComboBox history = new JComboBox(new String[]{"Last Day"   });//   ,"Last Week","Last Month","Last Year"}); \\more histories can be done later
        final public JComboBox step = new JComboBox(new String[]{"Hour"          });//         ,"Day","Week","Month"}); \\more step sizes later
            //should make these private and create a single getter for all... will do oneday
            
        public RTypePanel()
        {
            setLayout(new GridLayout(0, 1));
            setPreferredSize(new Dimension(400,175));
            setBorder(bascicBord);
         
           
            //SalesHistory.setActionCommand("Sales History");

            CurrentStock.setSelected(true);

            ButtonGroup rtype = new ButtonGroup();
            rtype.add(CurrentStock);
            rtype.add(SalesHistory);

            add(new JLabel("Report Type")); 
            JPanel buttons=new JPanel(new GridLayout(0,1));
            buttons.add(CurrentStock);
            buttons.add(SalesHistory);
            add(buttons);
            
            HistorySetting.add(new JLabel("History Period"));

            
            HistorySetting.add(history);

            HistorySetting.add(new JLabel("History Step"));
            
            HistorySetting.add(step);

            HistorySetting.setBorder(bascicBord);

            add(HistorySetting); 
            for(Component c: HistorySetting.getComponents())
                    c.setEnabled(false);

            SalesHistory.addActionListener(this);
            CurrentStock.addActionListener(this);
            
             //SalesHistory.setEnabled(false);   //this in effect until next sprint
        }
        
        public void actionPerformed(ActionEvent e) {
            if(SalesHistory.isSelected() )
            {
                  for(Component c: HistorySetting.getComponents())
                    c.setEnabled(true);
            }
            else
            {
                for(Component c: HistorySetting.getComponents())
                    c.setEnabled(false);
            
            }
        }
        
    }
    
     private class GTypePanel extends JPanel implements ActionListener
    {
        final public JRadioButton Display = new JRadioButton("Display Report");
        final public JRadioButton CSVGen = new JRadioButton("Generate in CSV");
       final public JFileChooser FileLoc = new JFileChooser();
            //should make these private and create a single getter for all... will do oneday
       
       final private JPanel FilePanel = new JPanel(new GridLayout(0,1));     
       final private JTextField FileLoctext = new JTextField();
       
        public GTypePanel()
        {
            setLayout(new GridLayout(0, 1));
            setPreferredSize(new Dimension(400,175));
            setBorder(bascicBord);
         
           
            //SalesHistory.setActionCommand("Sales History");

            Display.setSelected(true);

            ButtonGroup rtype = new ButtonGroup();
            rtype.add(Display);
            rtype.add(CSVGen);

            add(new JLabel("Report Type"));  
          
            JPanel buttons=new JPanel(new GridLayout(0,1));
            buttons.add(Display);
            buttons.add(CSVGen);
            add(buttons);
            

           
            FilePanel.add(new JLabel("Save To"));
            
            
           
            JPanel filePanelTricker = new JPanel();
            FilePanel.add(filePanelTricker);
            filePanelTricker.add(FileLoctext);
            JButton UpdatePath = new JButton("Choose");
            UpdatePath.setPreferredSize(new Dimension(75,20));
            FileLoctext.setPreferredSize(new Dimension(200,20));
             filePanelTricker.add(UpdatePath);
            FileLoc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
             UpdatePath.addActionListener( new ActionListener()
             {
                public void actionPerformed(ActionEvent arg0)
                {
                    if(FileLoc.showOpenDialog(CSVGen)==JFileChooser.APPROVE_OPTION)
                    {
                       //System.out.println(FileLoc.getSelectedFile().toString());
                        FileLoctext.setText(FileLoc.getSelectedFile().toString());
                       int size;
                        do
                        {
                            size = FileLoctext.getText().length();
                            String temp =FileLoctext.getText();
                            temp=temp.substring(0, size/2-2)+".."+temp.substring(size/2+2, size);
                            System.out.println(temp);
                            FileLoctext.setText(temp);
                        }while(size>35);
                        
                    }
                }
             }
             
             );
             
            FilePanel.setBorder(bascicBord);

            add(FilePanel); 
            for(Component c: FilePanel.getComponents())
            {      c.setEnabled(false);
                    if(c.getClass() == JPanel.class)
                      {
                           for(Component c2: ((JPanel)c).getComponents())
                            {
                                c2.setEnabled(false);
                            }
                      }
            }

            CSVGen.addActionListener(this);
            Display.addActionListener(this);
            

        }
        
        public void actionPerformed(ActionEvent e) {
            if(CSVGen.isSelected() )
            {
                  for(Component c: FilePanel.getComponents())
                  {
                      c.setEnabled(true);
                      if(c.getClass() == JPanel.class)
                      {
                           for(Component c2: ((JPanel)c).getComponents())
                            {
                                c2.setEnabled(true);
                            }
                      }
                  
                  }
            }
            else
            {
                for(Component c: FilePanel.getComponents())
                {    c.setEnabled(false);
                    if(c.getClass() == JPanel.class)
                      {
                           for(Component c2: ((JPanel)c).getComponents())
                            {
                                c2.setEnabled(false);
                            }
                      }
                }
            
            }
        }
        
    }
    
     private class CurrentReport extends JPanel
     {
        public CurrentReport()
        {
            JTable StockList;
           
            DefaultTableModel model = new DefaultTableModel(DataBaseAccess.getFullCataloge(), new String[]{"ID","Name","Stock"});
            StockList = new JTable(model);
            StockList.setDefaultRenderer(Object.class, new RowColourRenderer());
              
           JScrollPane CatalougeScoll = new JScrollPane(StockList);
           StockList.setPreferredScrollableViewportSize(new Dimension(360,320));

           /*
           for(int row=0; row< StockList.getRowCount(); row++)
           if((Integer)StockList.getValueAt(row,3)<3)
               {
                   if((Integer)StockList.getValueAt(row,3)<3)
                    {
                        StockList.setBackground(model.set);
                    }
                    return Color.red;
               }
          */           
            add(CatalougeScoll);
        }
     }
     
     
     static class RowColourRenderer extends DefaultTableCellRenderer
     {
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
         {
             Component out = super.getTableCellRendererComponent(table,  value,  isSelected,  hasFocus,  row,  column);
             
             //System.out.println(table.getValueAt(row,2));
             out.setBackground(Color.white);
            if((Integer)table.getValueAt(row,2)<5)
               {
                   int aintEasyBeingGreen =148+40*((Integer)table.getValueAt(row,2));
                   if(aintEasyBeingGreen>255)
                       aintEasyBeingGreen=255;
                   
                   out.setBackground(new Color(255,aintEasyBeingGreen,148));
                   //if((Integer)table.getValueAt(row,2)<1)
                    //{
                      //  out.setBackground(new Color(255,148,148));
                   // }
                   
                   
               }
            
            return out;
         }
     }
      
    
    
}