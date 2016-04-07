package passtask.gui;

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
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ReportRequestGUI extends JPanel
{
    Border bascicBord = BorderFactory.createLineBorder(Color.black);
    final RTypePanel RType = new RTypePanel();
    final GTypePanel GType = new GTypePanel();

    public ReportRequestGUI()
    {
	JPanel Report = new JPanel();
	Report.setPreferredSize(new Dimension(400, 350));

	JButton GenerateBut = new JButton("Generate");
	setLayout(new GridBagLayout());

	/* Test colourings */
	Report.setBackground(Color.red);
	// RType.setBackground(Color.blue);
	// GType.setBackground(Color.yellow);
	/**/

	GenerateBut.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
		if(RType.SalesHistory.isSelected())
		{
		    System.out.println("This will generate a sales history report in a future sprint");
		}
		else if(RType.CurrentStock.isSelected())
		{
		    // if()

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
	con.gridy = 2;
	con.gridheight = 5;
	con.gridwidth = 2;
	add(Report, con);

	con.gridx = 3;
	con.gridheight = 2;
	add(RType, con);

	con.gridy = 4;
	add(GType, con);

	con.gridx = 4;
	con.gridy = 6;
	con.gridwidth = 1;
	con.gridheight = 1;
	add(GenerateBut, con);

    }

    private class RTypePanel extends JPanel implements ActionListener
    {
	final public JRadioButton SalesHistory = new JRadioButton("Sales History");
	final public JPanel HistorySetting = new JPanel(new GridLayout(2, 2));
	final public JRadioButton CurrentStock = new JRadioButton("Current Stock");
	final public JComboBox history = new JComboBox(
		new String[]
	{ "Last Day", "Last Week", "Last Month", "Last Year" });
	final public JComboBox step = new JComboBox(new String[] { "Hour", "Day", "Week", "Month" });
	// should make these private and create a single getter for all... will
	// do oneday

	public RTypePanel()
	{
	    setLayout(new GridLayout(0, 1));
	    setPreferredSize(new Dimension(400, 175));
	    setBorder(bascicBord);

	    // SalesHistory.setActionCommand("Sales History");

	    CurrentStock.setSelected(true);

	    ButtonGroup rtype = new ButtonGroup();
	    rtype.add(CurrentStock);
	    rtype.add(SalesHistory);

	    add(new JLabel("Report Type"));
	    JPanel buttons = new JPanel(new GridLayout(0, 1));
	    buttons.add(CurrentStock);
	    buttons.add(SalesHistory);
	    add(buttons);

	    HistorySetting.add(new JLabel("History Period"));

	    HistorySetting.add(history);

	    HistorySetting.add(new JLabel("History Step"));

	    HistorySetting.add(step);

	    HistorySetting.setBorder(bascicBord);

	    add(HistorySetting);
	    for(Component c : HistorySetting.getComponents())
		c.setEnabled(false);

	    SalesHistory.addActionListener(this);
	    CurrentStock.addActionListener(this);

	    SalesHistory.setEnabled(false);   // this in effect until next
					      // sprint
	}

	public void actionPerformed(ActionEvent e)
	{
	    if(SalesHistory.isSelected())
	    {
		for(Component c : HistorySetting.getComponents())
		    c.setEnabled(true);
	    }
	    else
	    {
		for(Component c : HistorySetting.getComponents())
		    c.setEnabled(false);

	    }
	}

    }

    private class GTypePanel extends JPanel implements ActionListener
    {
	final public JRadioButton Display = new JRadioButton("Display Report");
	final public JPanel FilePanel = new JPanel(new GridLayout(0, 1));
	final public JRadioButton CSVGen = new JRadioButton("Generate in CSV");
	final public JTextField FileLoc = new JTextField();
	// should make these private and create a single getter for all... will
	// do oneday

	public GTypePanel()
	{
	    setLayout(new GridLayout(0, 1));
	    setPreferredSize(new Dimension(400, 175));
	    setBorder(bascicBord);

	    // SalesHistory.setActionCommand("Sales History");

	    Display.setSelected(true);

	    ButtonGroup rtype = new ButtonGroup();
	    rtype.add(Display);
	    rtype.add(CSVGen);

	    add(new JLabel("Report Type"));

	    JPanel buttons = new JPanel(new GridLayout(0, 1));
	    buttons.add(Display);
	    buttons.add(CSVGen);
	    add(buttons);

	    FilePanel.add(new JLabel("Save To"));

	    FilePanel.add(FileLoc);

	    FilePanel.setBorder(bascicBord);

	    add(FilePanel);
	    for(Component c : FilePanel.getComponents())
		c.setEnabled(false);

	    CSVGen.addActionListener(this);
	    Display.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
	    if(CSVGen.isSelected())
	    {
		for(Component c : FilePanel.getComponents())
		    c.setEnabled(true);
	    }
	    else
	    {
		for(Component c : FilePanel.getComponents())
		    c.setEnabled(false);

	    }
	}

    }

}
