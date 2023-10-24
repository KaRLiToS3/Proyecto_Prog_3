package monopoly.objects;

import java.awt.BasicStroke;

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
	
	public LineChart(String title, Match match, JList<Match> list) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		chart = ChartFactory.createXYLineChart(title, "X", "Y", dataset );
		for(int i = 0; i < list.getSelectedValue().getNumUsers(); i++) {
			XYSeries leyend = new XYSeries(list.getSelectedValue().getUsers().get(i).getName());
			
			leyend.add(1.0, 1.0);
			leyend.add(2.0, 4.0);
			leyend.add(3.0, 3.0);
			leyend.add(4.0, 10.0);
			dataset.addSeries(leyend);
			XYPlot line = (XYPlot) chart.getPlot();
			line.getRenderer().setSeriesStroke(i, new BasicStroke(5.0f));
			
		}		
	}

	public JFreeChart getChart() {
		return chart;
	}
}
