
import java.awt.Color;
import javax.swing.JPanel;
import monopoly.objects.Cell;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CellTest {

    private Cell cell;
    private JPanel panel;

    @Before
    public void setUp() {
        double perX = 0.5;
        double perY = 0.5;
        Cell.CellType type = Cell.CellType.Property;
        String name = "TestCell";
        panel = new JPanel();
        panel.setSize(1000, 1000);

        cell = new Cell(perX, perY, type, name, panel);
    }

    @Test
    public void testCellInitialization() {
        assertNotNull(cell);
        assertTrue(0.5==cell.getPerX());
        assertTrue(0.5==cell.getPerY());
        assertEquals(Cell.CellType.Property, cell.getcType());
        assertEquals("TestCell", cell.getName());
        assertEquals(panel, cell.getPanel());
        assertEquals(Color.black, cell.getColor());
    }

    @Test
    public void testCellPositionUpdate() {
        // Update of cell's position and check if it reflects the changes
        cell.setPerX(0.8);
        cell.setPerY(0.3);
        cell.updateCell();

        assertEquals((int) (0.8 * panel.getWidth()), cell.getX());
        assertEquals((int) (0.3 * panel.getWidth()), cell.getY());
    }

    @Test
    public void testCellDimensions() {
        // Check if the cell dimensions are calculated correctly
        assertEquals(30, cell.getWidth()); // Adjust the expected value based on your calculations
        assertEquals(40, cell.getHeight()); // Adjust the expected value based on your calculations
    }

    // Add more tests as needed for other methods and functionalities

}

