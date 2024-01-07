import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jfree.chart.JFreeChart;
import org.junit.Test;

import monopoly.objects.GraphFactory;
import monopoly.objects.Match;

public class GraphFactoryTest {

	@Test
	public void lineChartTest() {
		Match defaultMatch = new Match();
		JFreeChart chart = GraphFactory.createLineChart("Test Match", "Turn", "Currency", defaultMatch);
		assertNotNull(defaultMatch);
		assertNotNull(chart);

		assertEquals("Test Match", chart.getTitle().getText());
		assertEquals("Paco", chart.getXYPlot().getDataset().getSeriesKey(0));
		assertEquals("Juan", chart.getXYPlot().getDataset().getSeriesKey(1));
		assertEquals("Damian", chart.getXYPlot().getDataset().getSeriesKey(2));

		//																int series ("Damian"), int item(x)
		assertEquals(100, chart.getXYPlot().getDataset().getY(0, 0).doubleValue(), 0);
		assertEquals(300, chart.getXYPlot().getDataset().getY(1, 1).doubleValue(), 0);
		assertEquals(1000, chart.getXYPlot().getDataset().getY(2, 3).doubleValue(), 0);
		assertEquals(200, chart.getXYPlot().getDataset().getY(2, 2).doubleValue(), 0);
		assertEquals(900, chart.getXYPlot().getDataset().getY(0, 3).doubleValue(), 0);
		assertEquals(100, chart.getXYPlot().getDataset().getY(1, 3).doubleValue(), 0);
	}

}
