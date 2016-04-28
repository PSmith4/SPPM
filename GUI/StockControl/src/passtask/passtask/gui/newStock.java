package passtask.gui;

import java.awt.BorderLayout;
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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import passtask.db.DataBaseAccess;

public class newStock extends JPanel
{
    private DataBaseAccess dba;

    /**
     * 
     */
    private static final long serialVersionUID = -1802872436430640877L;
    final ArrayList<Object[]> CatContent = new ArrayList<Object[]>();
    final CatTableModel catTableModel = new CatTableModel(CatContent);


    private void fetchTable()
    {
        Object[][] tempCatContent = DataBaseAccess.getShipmentUpdate();
CatContent.clear();
	for(int i = 0; i < tempCatContent.length; i++)
	{
	    int buttoncount;
	    CatContent.add(new Object[4]);
	    buttoncount = CatContent.size() - 1;
	    CatContent.get(buttoncount)[0] = tempCatContent[i][0];
	    CatContent.get(buttoncount)[1] = tempCatContent[i][1];
	    CatContent.get(buttoncount)[2] = tempCatContent[i][2];

	    JSpinner newstockSpin = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
	    CatContent.get(buttoncount)[3] = newstockSpin;

	}
        
    }
    
    public void update()
    {
        fetchTable();
        catTableModel.fireTableDataChanged();
    }
    
    public newStock() throws SQLException
    {
        this.dba = DataBaseAccess.getInstance();
	setSize(836, 546);
	
        fetchTable();
	JTable CatalougeTable = new JTable(catTableModel);

	CatalougeTable.setDefaultRenderer(JSpinner.class, new TabbleSpinnerRenderer());
	CatalougeTable.addMouseListener(new TableSpinnerListener(CatalougeTable));

	CatalougeTable.setRowHeight(35);
	JScrollPane CatalougeScoll = new JScrollPane(CatalougeTable);
	CatalougeTable.setPreferredScrollableViewportSize(new Dimension(618, 242));
	CatalougeTable.setFillsViewportHeight(true);
	CatalougeTable.setRowMargin(5);

	JButton FinishAdding = new JButton("Add stock to system");

	FinishAdding.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {

		ArrayList<Object[]> output = new ArrayList<Object[]>();

		for(Object[] item : CatContent)
		{
		    int newStock = ((Integer) ((JSpinner) item[3]).getValue());
		    if(newStock > 0)
		    {// System.out.println(newStock);
			newStock = (Integer) item[2] + newStock;
			// System.out.println(newStock);
			output.add(new Object[] { item[0], newStock });
			((JSpinner) item[3]).setValue(0);
			item[2] = newStock;
		    }
		}

		Object[][] outputArray = new Object[output.size()][2];

		int loopcount = 0;
		for(Object[] item : output)
		{
		    outputArray[loopcount] = item;
		    loopcount++;
		}
		DataBaseAccess.addShipment(outputArray);
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

	JLabel heading = new JLabel("Add Stock");
	heading.setFont(new Font("Times New Roman", 18, 18));
	add(heading, con);

	con.gridx = 0;
	con.gridy = 3;
	con.gridwidth = 200;
	con.gridheight = 100;
	add(CatalougeScoll, con);

	con.gridx = 200;
	con.gridy = 103;
	con.gridwidth = 1;
	con.gridheight = 1;
	add(FinishAdding, con);

    }

    class TabbleSpinnerRenderer extends JSpinner implements TableCellRenderer
    {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column)
	{
	    // System.out.println("addbuttonrndere");

	    JSpinner newStock = (JSpinner) value;
	    // JSpinner spinner = new JSpinner(new SpinnerNumberModel());

	    JPanel panel = new JPanel();
	    // panel.add(spinner);
	    panel.setLayout(new BorderLayout());
	    panel.add(newStock);

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

    class CatTableModel extends AbstractTableModel
    {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5761529725947025769L;
	ArrayList<Object[]> stockitems;
	String[] colnames = { "ID", "Name", "Current Stock", "New Stock" };

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
	    return JSpinner.class;
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

    class TableSpinnerListener extends MouseAdapter
    {
	private final JTable table;

	public TableSpinnerListener(JTable table)
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
		if(value instanceof JSpinner)
		{
		    if(e.getY() > (row + 0.5) * table.getRowHeight())
		    {
			System.out.println(
				"Bottom" + ((JSpinner) value).getValue() + ((JSpinner) value).getPreviousValue());
			((JSpinner) value).setValue(((JSpinner) value).getPreviousValue());
			// ((JSpinner)value).setValue(value);
		    }
		    else
		    {
			System.out.println("top[");
			((JSpinner) value).setValue(((JSpinner) value).getNextValue());
			// ((JSpinner)value).setValue(( (Integer) (((JSpinner)
			// value).getValue())) +1 );
		    }
		}
		catTableModel.fireTableDataChanged();

	    }
	}
    }

}
