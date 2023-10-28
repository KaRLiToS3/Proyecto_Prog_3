package monopoly.objects;

import java.awt.BasicStroke;
import java.util.TreeMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class LineChart {
	
	private JFreeChart chart;
	
	public LineChart(String title, Match match) {
		
		XYSeriesCollection dataset = new XYSeriesCollection();	//The data container
		chart = ChartFactory.createXYLineChart(title, "Time", "Currency", dataset );		//Creator of the chart with the data
		
		for(int i = 0; i < match.getTurnCurrencyPerUser().keySet().size(); i++) {
			User usr = match.getUsers().get(i);
			TreeMap<Long, Integer> timeAndCurrencyData = match.getTurnCurrencyPerUser().get(usr);
			
			XYSeries legend = new XYSeries(usr.getName());	//Assigns a legend to the graph of a user
			
			for(Long lng : timeAndCurrencyData.keySet()) {
				legend.add(convertToMinutes(lng), (double) timeAndCurrencyData.get(lng));	//Assigns points to the XY axles 
			}
			
			dataset.addSeries(legend);	//The legend of every user and its data associated is added to the dataset
			XYPlot line = (XYPlot) chart.getPlot();		//The Plot is the line that is drawn
			line.getRenderer().setSeriesStroke(i, new BasicStroke(5.0f));		//This sets up the thickness of the line
			
		}		
	}
	
	public double convertToMinutes(long data) {
		return data/60000.0;
	}

	public JFreeChart getChart() {
		return chart;
	}
}
