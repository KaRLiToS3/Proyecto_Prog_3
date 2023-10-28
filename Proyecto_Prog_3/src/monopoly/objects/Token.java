package monopoly.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Token extends JComponent{
	private static final long serialVersionUID = 1L;
	
	int x;
	int y;
	Color color;
	int cell;
	
	
	static int counter = 0;
	final static int radius = 10;
	final static int[] CELL_NUMBERS = new int[40];
	final static Color[] TOKEN_COLORS = {Color.RED, Color.ORANGE, Color.BLUE, Color.GREEN};
	
	public Token(int x, int y, Color color) {
		for ( int i = 0; i < 40; i++) {
			CELL_NUMBERS[i] = i;
		}
		this.x = x;
		this.y = y;
		this.color = color;
		counter++;
	}
	
	public Token() {
		this (100*(counter+1),100*(counter+1),TOKEN_COLORS[counter]);
		for ( int i = 0; i < 40; i++) {
			CELL_NUMBERS[i] = i;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics2D = (Graphics2D)g;
		
		graphics2D.setPaint(color);
		graphics2D.fillOval(x, y, radius*2, radius*2);
	}

	
	
	

}
