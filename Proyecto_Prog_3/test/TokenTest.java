import java.awt.Color;
import javax.swing.JPanel;
import org.junit.Before;
import org.junit.Test;

import monopoly.objects.Token;

import static org.junit.Assert.*;

public class TokenTest {

    private Token token;
    private JPanel panel;

    @Before
    public void setUp() {
        panel = new JPanel();
        token = new Token(Color.RED, panel, 1500, "example@gmail.com");
    }

    @Test
    public void testTokenInitialization() {
        assertNotNull(token);
        assertEquals(Color.RED, token.getColor());
        assertEquals(panel, token.getPanel());
        assertEquals(0, token.getCellNumber());
        assertEquals(1500, token.getMoney());
        assertFalse(token.isInJail());
        assertEquals(3, token.getJailTurnCounter());
        assertEquals(1500, token.getMoney());
        assertEquals("example@gmail.com", token.getUserEmail());
    }

    @Test
    public void testTokenSettersAndGetters() {
        token.setColor(Color.GREEN);
        token.setPanel(panel);
        token.setCellNumber(1);
        token.setMoney(2000);
        token.setInJail(true);
        token.setJailTurnCounter(3);

        assertEquals(Color.GREEN, token.getColor());
        assertEquals(panel, token.getPanel());
        assertEquals(1, token.getCellNumber());
        assertEquals(2000, token.getMoney());
        assertTrue(token.isInJail());
        assertEquals(3, token.getJailTurnCounter());
    }
}

