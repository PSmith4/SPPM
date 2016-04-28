package passtask.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import passtask.db.DataBaseAccess;

public class StorePanel extends JPanel
{

    /**
     * 
     */
    private static final long serialVersionUID = 932466194323993823L;
    final ArrayList<Object[]> SalesContent = new ArrayList<Object[]>();
    final SaleTableModel saleTableModel = new SaleTableModel();
    final ArrayList<Object[]> CatContent = new ArrayList<Object[]>();
    final CatTableModel catTableModel = new CatTableModel(CatContent);

    class TabbleButtonRenderer implements TableCellRenderer
    {
	private String text;

	public TabbleButtonRenderer(String text)
	{
	    super();
	    this.text = text;

	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column)
	{

	    // System.out.println("addbuttonrndere");

	    JButton showButton = new JButton(text);
	    // JSpinner spinner = new JSpinner(new SpinnerNumberModel());

	    JPanel panel = new JPanel();
	    // panel.add(spinner);
	    panel.add(showButton);

	    if(isSelected)
	    {
		panel.setBackground(table.getSelectionBackground());
	    }
	    else
	    {
		panel.setBackground(table.getBackground());
	    }

	    return panel;
	}
    }

    class SaleTableModel extends AbstractTableModel
    {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3839756339941658695L;
	String[] colnames = { "ID", "Name", "Count", "Remove" };

	public Class getColumnClass(int columnIndex)
	{
	    if(columnIndex == 0)
		return Integer.class;
	    else if(columnIndex == 1)
		return String.class;
	    else if(columnIndex == 2)
		return Integer.class;
	    return JButton.class;
	}

	public int getColumnCount()
	{
	    return 4;
	}

	public int getRowCount()
	{
	    return SalesContent.size();
	}

	public void setValueAt(Object value, int row, int col)
	{
	    Object[] temp = SalesContent.get(row);
	    temp[col] = value;
	    SalesContent.set(row, temp);
	}

	public Object getValueAt(int row, int col)
	{
	    if(col == 3)
	    {
		JButton remove = new JButton("remove");

		remove.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent arg0)
		    {
			System.out.println("remove sale");
			Object[] temp = SalesContent.get(row);
			temp[2] = (Integer) temp[2] - 1;
			if((Integer) SalesContent.get(row)[2] == 0)
			{
			    SalesContent.remove(row);
			}

			int loopcount = 0;
			for(Object[] Oldcatitem : CatContent)
			{
			    if((Integer) Oldcatitem[0] == (Integer) temp[0])
			    {
				Oldcatitem[2] = (Integer) Oldcatitem[2] + 1;
				CatContent.set(loopcount, Oldcatitem);

				break;
			    }
			    loopcount++;
			}

		    }
		});
                catTableModel.fireTableDataChanged();
	saleTableModel.fireTableDataChanged();
		

		return remove;
	    }
	    return SalesContent.get(row)[col];
	}

	public String getColumnName(int col)
	{
	    return colnames[col];
	}
    }

    class CatTableModel extends AbstractTableModel
    {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4197051759490408144L;
	ArrayList<Object[]> stockitems;
	String[] colnames = { "ID", "Name", "Stock", "Add to Sale" };

	public CatTableModel(ArrayList<Object[]> stockitems)
	{
	    super();
	    this.stockitems = stockitems;

	}

	public Class getColumnClass(int columnIndex)
	{
	    if(columnIndex == 0)
		return Integer.class;
	    else if(columnIndex == 1)
		return String.class;
	    else if(columnIndex == 2)
		return Integer.class;
	    return JButton.class;
	}

	public int getColumnCount()
	{
	    return 4;
	}

	public int getRowCount()
	{
	    return this.stockitems.size();
	}

	public void setValueAt(Object value, int row, int col)
	{
	    Object[] temp = stockitems.get(row);
	    temp[col] = value;
	    stockitems.set(row, temp);
	}

	public Object getValueAt(int row, int col)
	{
	    return stockitems.get(row)[col];
	}

	public String getColumnName(int col)
	{
	    return colnames[col];
	}

    }

    class TableButtonListener extends MouseAdapter
    {
	private final JTable table;

	public TableButtonListener(JTable table)
	{
	    this.table = table;
	}

	public void mouseClicked(MouseEvent e)
	{
	    int column = table.getColumnModel().getColumnIndexAtX(e.getX());
	    int row = e.getY() / table.getRowHeight();

	    if(row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0)
	    {
		Object value = table.getValueAt(row, column);
		System.out.println("Value :" + value.getClass().getName());
		if(value instanceof JButton)
		{
		    ((JButton) value).doClick();
		}

	    }
	}
    }

    public StorePanel()
    {

	setSize(836, 546);
        fetchTable();

	JTable CatalougeTable = new JTable(catTableModel); // "Add to Sale"
	CatalougeTable.setDefaultRenderer(JButton.class, new TabbleButtonRenderer("+"));
	CatalougeTable.setRowHeight(35);
	CatalougeTable.addMouseListener(new TableButtonListener(CatalougeTable));

	JScrollPane CatalougeScoll = new JScrollPane(CatalougeTable);
	CatalougeTable.setPreferredScrollableViewportSize(new Dimension(418, 242));
	CatalougeTable.setFillsViewportHeight(true);
	CatalougeTable.setRowMargin(5);

	CatalougeTable.getColumnModel().getColumn(0).setPreferredWidth(40);
	CatalougeTable.getColumnModel().getColumn(1).setPreferredWidth(150);
	CatalougeTable.getColumnModel().getColumn(2).setPreferredWidth(50);

	JTable SaleTable = new JTable(saleTableModel);
	SaleTable.setDefaultRenderer(JButton.class, new TabbleButtonRenderer("-"));
	SaleTable.setRowHeight(35);
	SaleTable.addMouseListener(new TableButtonListener(SaleTable));

	JScrollPane SaleScoll = new JScrollPane(SaleTable);
	SaleTable.setPreferredScrollableViewportSize(new Dimension(418, 242));
	SaleTable.setFillsViewportHeight(true);

	SaleTable.getColumnModel().getColumn(0).setPreferredWidth(40);
	SaleTable.getColumnModel().getColumn(1).setPreferredWidth(150);
	SaleTable.getColumnModel().getColumn(2).setPreferredWidth(50);

	JButton finishButton = new JButton("Finish");

	finishButton.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {

		Object[][] output = new Object[SalesContent.size()][2];
		System.out.println("Sale:");
		int loopcount = 0;
		for(Object[] saleitem : SalesContent)
		{
		    output[loopcount][0] = saleitem[0];

		    System.out.println(saleitem[0] + " " + saleitem[1] + " " + saleitem[2]);
		    loopcount++;
		}
                SalesContent.clear();
                
              
                
		for(Object[] catitem : CatContent)
		{
		    for(int i = 0; i < output.length; i++)
		    {
			if(catitem[0] == output[i][0])
			{
			    output[i][1] = (Integer) catitem[2];
			}
		    }

		}  
               

		DataBaseAccess.makeSale(output); 
                try {
                            StockControlGUI.update();
                        } catch (SQLException ex) {

                        }
	    }
	});

	setLayout(new GridBagLayout());

	GridBagConstraints con = new GridBagConstraints();
	con.weightx = 1;
	con.weighty = 1;
	con.anchor = GridBagConstraints.FIRST_LINE_START;
	con.insets = new Insets(5, 5, 5, 5);
	con.gridx = 10;
	con.gridy = 0;
	con.gridheight = 1;
	con.gridwidth = 1;
	JLabel heading = new JLabel("Sale");
	heading.setFont(new Font("Times New Roman", 18, 18));
	add(heading, con);

	con.gridx = 10;
	con.gridy = 2;
	add(new JLabel("Current Stock"), con);

	con.gridx = 105;
	add(new JLabel("Sale Stock"), con);

	con.gridx = 0;
	con.gridy = 3;
	con.gridwidth = 100;
	con.gridheight = 100;
	add(CatalougeScoll, con);

	con.gridx = 100;
	add(SaleScoll, con);

	con.gridx = 150;
	con.gridy = 103;
	con.gridwidth = 1;
	con.gridheight = 1;
	add(finishButton, con);

    }
   
    public void update()
    {
        fetchTable();
        catTableModel.fireTableDataChanged();
	saleTableModel.fireTableDataChanged();
    }
    


    private void fetchTable()
    {
    Object[][] tempCatContent = DataBaseAccess.getShipmentUpdate();
CatContent.clear();
            for(int i = 0; i < tempCatContent.length; i++)
            {
                int buttoncount;
                if((Integer) tempCatContent[i][2] > 0)
                {
                    CatContent.add(new Object[4]);
                    buttoncount = CatContent.size() - 1;
                    CatContent.get(buttoncount)[0] = tempCatContent[i][0];
                    CatContent.get(buttoncount)[1] = tempCatContent[i][1];
                    CatContent.get(buttoncount)[2] = tempCatContent[i][2];

                    JButton addButton = new JButton("Add");
                    CatContent.get(buttoncount)[3] = addButton;

                    addButton.addActionListener(new ActionListener()
                    {
                        private Object[] newSaleItem;

                        public void actionPerformed(ActionEvent arg0)
                        {
                            System.out.println("add " + newSaleItem[1] + " to sale");
                            Boolean found = false;
                            int loopcount = 0;
                            for(Object[] Oldsaleitem : SalesContent)
                            {
                                if(!found && (Integer) Oldsaleitem[0] == (Integer) newSaleItem[0])
                                {
                                    Oldsaleitem[2] = (Integer) Oldsaleitem[2] + 1;
                                    SalesContent.set(loopcount, Oldsaleitem);
                                    found = true;
                                    break;
                                }
                                loopcount++;
                            }
                            if(!found)
                            {
                                Object[] temp = { newSaleItem[0], newSaleItem[1], new Integer(1) };
                                SalesContent.add(temp);
                            }

                            newSaleItem[2] = (Integer) newSaleItem[2] - 1;
                            if((Integer) newSaleItem[2] > 0)
                                CatContent.set(CatContent.indexOf(newSaleItem), newSaleItem);

                            saleTableModel.fireTableDataChanged();
                            catTableModel.fireTableDataChanged();

                        }

                        private ActionListener init(Object var[])
                        {
                            newSaleItem = var;
                            return this;
                        }
                    }.init(CatContent.get(buttoncount)));
                }

            }
    }
}
