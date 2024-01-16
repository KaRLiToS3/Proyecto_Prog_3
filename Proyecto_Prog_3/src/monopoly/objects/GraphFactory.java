package monopoly.objects;

import java.awt.BasicStroke;
import java.util.TreeMap;
import java.util.logging.Level;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import monopoly.data.LogRecorder;

public class GraphFactory {
	private static LogRecorder logger = new LogRecorder();
	
	/**This method creates a line graph using the JFreeChart library, in case it is to be displayed in a window, a ChartPanel shall be required
	 * @param graphTitle	The
	 * @param match
	 * @return
	 * @throws NullPointerException
	 */
	public static JFreeChart createLineChart(String graphTitle, String titleX, String titleY, Match match) throws NullPointerException{
		if(match != null) {
			XYSeriesCollection dataset = new XYSeriesCollection();	//The data container
			JFreeChart chart = ChartFactory.createXYLineChart(graphTitle, titleX, titleY, dataset );		//Creator of the chart with the data
			for(int i = 0; i < match.getTurnCurrencyPerUser().keySet().size(); i++) {
				String usr = match.getUsersEmails().get(i);
				TreeMap<Integer, Integer> turnAndCurrencyData = match.getTurnCurrencyPerUser().get(usr);

				XYSeries legend = new XYSeries(usr);	//Assigns a legend to the graph of a user

				for(Integer lng : turnAndCurrencyData.keySet()) {
					legend.add((double) lng, (double) turnAndCurrencyData.get(lng));	//Assigns points to the XY axles
				}

				dataset.addSeries(legend);	//The legend of every user and its data associated is added to the dataset
				XYPlot line = (XYPlot) chart.getPlot();		//The Plot is the line that is drawn
				line.getRenderer().setSeriesStroke(i, new BasicStroke(5.0f));		//This sets up the thickness of the line
				logger.log(Level.INFO, "Data from User: " + usr + " was properly obtained");
			}
			return chart;
		} else return null;
	}
}
