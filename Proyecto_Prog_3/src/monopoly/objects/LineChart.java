package monopoly.objects;

import java.awt.BasicStroke;
import java.util.TreeMap;

import javax.swing.JList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class LineChart {
	
	private JFreeChart chart;
	
	public LineChart(String title, Match match) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		chart = ChartFactory.createXYLineChart(title, "Currency", "Time", dataset );
		
		for(int i = 0; i < match.getTurnCurrencyPerUser().keySet().size(); i++) {
			User usr = match.getUsers().get(i);
			TreeMap<Long, Integer> timeAndCurrencyData = match.getTurnCurrencyPerUser().get(usr);
			
			XYSeries leyend = new XYSeries(usr.getName());
			
			for(Long lng : timeAndCurrencyData.keySet()) {
				leyend.add(convertToMinutes(lng), (double) timeAndCurrencyData.get(lng));
			}
			
			dataset.addSeries(leyend);
			XYPlot line = (XYPlot) chart.getPlot();
			line.getRenderer().setSeriesStroke(i, new BasicStroke(5.0f));
			
		}		
	}
	
	public double convertToMinutes(long data) {
		return data/60000.0;
	}

	public JFreeChart getChart() {
		return chart;
	}
}
