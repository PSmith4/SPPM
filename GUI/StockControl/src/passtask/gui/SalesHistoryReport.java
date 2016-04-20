package passtask.gui;





import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;
import passtask.db.DataBaseAccess;

  public class SalesHistoryReport extends JPanel
     {
        public SalesHistoryReport()
        {
       
           Object[][] saleshistory = DataBaseAccess.getSaleHistory();
            
          // create a dataset...
 
          DefaultCategoryDataset dataset = new DefaultCategoryDataset();
          
 
 ArrayList<Integer[]> itemcounts = new ArrayList<Integer[]>();
   for(int i = 0; i < saleshistory.length; i++)
    {
        Boolean found=false;
        Integer count=0;
        for(Integer[] item : itemcounts)
        {
            System.out.println("comp"+item[0]+" "+(Integer)saleshistory[i][0]);
            if(item[0].equals((Integer)saleshistory[i][0]))
            {
                System.out.println("match");
                item[1]++;
                found=true;
                count=item[1];
                break;
            } 
        }
        if(!found)
        {
            itemcounts.add(new Integer[]{(Integer)saleshistory[i][0],0});
        }
        System.out.print((Integer)saleshistory[i][2]+" ");
        System.out.print(count+" ");
        System.out.println((String)saleshistory[i][1]);
        dataset.addValue((Integer)saleshistory[i][2] ,count, (String)saleshistory[i][1]);
    }
 
 SlidingCategoryDataset datasetSlide = new SlidingCategoryDataset(dataset, 0,4);
 /*
  DefaultMultiValueCategoryDataset  dataset = new DefaultMultiValueCategoryDataset();
  
  ArrayList<Integer> fistitem = new ArrayList<Integer> ();
   for(int i = 0; i < saleshistory.length; i++)
    {
        if(saleshistory[i][0].equals(saleshistory[0][0]))
        {
            fistitem.add((Integer)saleshistory[i][2]); 
        }
    }
  for(Integer i : fistitem)
  {
      System.out.println(i);
  }
    dataset.add(fistitem ,(String)saleshistory[0][1], (String)saleshistory[0][1]);
*/
 
        //dataset.addValue(4297, "JFreeSVG", "Test");
        //dataset.addValue(24448, "Batik", "Warm-up");
        //ataset.addValue(21022, "Batik", "Test");

// create a chart...
JFreeChart chart = ChartFactory.createBarChart("Sales history","Item","Stock",datasetSlide);

        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        
        NumberAxis rangeAxis = new NumberAxis(0, plot.getRangeAxis().getUpperBound());
        rangeAxis.setAutoRangeIncludesZero(true);
        
        rangeAxis.setAutoRangeStickyZero(true);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setShadowXOffset(0);
         renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("##")));
    renderer.setItemLabelsVisible(true);
    
        renderer.setMaximumBarWidth(0.2);
        renderer.setDrawBarOutline(true);
        chart.removeLegend();;
        
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.setPreferredSize(new Dimension(400,300));
        //chartPanel.setPreferredSize(new Dimension(500,250));
// create and display a frame...

//JPanel tedious = new JPanel();

//Tedium.add(chartPanel);
 //JScrollPane ReportScroll = new JScrollPane(chartPanel);	
//  tedious.setPreferredSize(new Dimension(360,420));
// ReportScroll.setPreferredSize(new Dimension(380,350));
  add(chartPanel);
  JButton back =new JButton("<");
  back.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
                datasetSlide.setFirstCategoryIndex(datasetSlide.getFirstCategoryIndex()-1);
	    }
	});
  
 
  JButton foward =new JButton(">");
  
   foward.addActionListener(new ActionListener()
	{
	    public void actionPerformed(ActionEvent arg0)
	    {
                datasetSlide.setFirstCategoryIndex(datasetSlide.getFirstCategoryIndex()+1);
	    }
	});
     add(back); add(foward);
        }
     }