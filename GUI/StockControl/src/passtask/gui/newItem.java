package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import db.DataBaseAccess;

public class newItem extends JPanel
{

    public newItem()
    {

	JTable CatalougeTable = new JTable(new DefaultTableModel(DataBaseAccess.getCatalogeWithDescription(),
		new String[]
	{ "ID", "Name", "Description", "Price" }));

	CatalougeTable.setRowHeight(35);

	JScrollPane CatalougeScoll = new JScrollPane(CatalougeTable);
	CatalougeTable.setPreferredScrollableViewportSize(new Dimension(418, 242));
	CatalougeTable.setFillsViewportHeight(true);
	CatalougeTable.setRowMargin(5);

	CatalougeTable.getColumnModel().getColumn(0).setPreferredWidth(40);
	CatalougeTable.getColumnModel().getColumn(1).setPreferredWidth(100);

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
	JLabel heading = new JLabel("Catalogue Edit");
	heading.setFont(new Font("Times New Roman", 18, 18));
	add(heading, con);

	con.gridx = 10;
	con.gridy = 2;
	add(new JLabel("New Item"), con);

	con.gridx = 105;
	add(new JLabel("Current Items"), con);

	con.gridx = 500;
	con.gridy = 3;
	con.gridheight = 242;
	con.gridwidth = 418;
	add(CatalougeScoll, con);

	con.gridx = 0;
	add(new ItemAdder(), con);
    }

    class ItemAdder extends JPanel
    {
	public ItemAdder()
	{
	    setPreferredSize(new Dimension(400, 242));
	    // setBackground(java.awt.Color.red);

	    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	    setAlignmentX(Component.LEFT_ALIGNMENT);
	    JLabel namel = new JLabel("Name");
	    namel.setAlignmentX(Component.LEFT_ALIGNMENT);
	    add(namel);

	    JTextField name = new JTextField();
	    name.setPreferredSize(new Dimension(200, 20));
	    name.setMaximumSize(new Dimension(200, 20));
	    add(name);

	    add(new JLabel("Description"));
	    JTextArea Description = new JTextArea();
	    Description.setPreferredSize(new Dimension(200, 50));
	    Description.setMaximumSize(new Dimension(200, 50));
	    add(Description);

	    add(new JLabel("Price"));
	    JSpinner price = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 0.05));
	    price.setPreferredSize(new Dimension(100, 20));
	    price.setMaximumSize(new Dimension(100, 20));
	    add(price);

	    JButton add = new JButton("Add");
	    add(add);
	    add.addActionListener(new ActionListener()
	    {
		public void actionPerformed(ActionEvent arg0)
		{
		    Object[] output = new Object[3];
		    output[0] = name.getText();
		    name.setText("");
		    output[1] = Description.getText();
		    Description.setText("");
		    output[2] = price.getValue();
		    price.setValue(0);
		    DataBaseAccess.NewITem(output);
		}

	    }

	    );

	}
    }

}
