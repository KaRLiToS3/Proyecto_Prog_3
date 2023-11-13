package monopoly.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Token extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int x;
	int y;
	double perX;
	double perY;
	JPanel panel;
	Color color;
	Insets insets;
	int cell;
	
	
	static int counter = 0;
	final static int radius = 10;
	final static double perRadius = 0.0146198830409357;
	final static int[] CELL_NUMBERS = {0,1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,34,35,36,37,38,39,40}; 
	final static Color[] TOKEN_COLORS = {Color.RED, Color.ORANGE, Color.BLUE, Color.GREEN};
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	

	public double getPerX() {
		return perX;
	}

	public void setPerX(double perX) {
		this.perX = perX;
	}

	public double getPerY() {
		return perY;
	}

	public void setPerY(double perY) {
		this.perY = perY;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getCell() {
		return cell;
	}

	public void setCell(int cell) {
		this.cell = cell;
	}

	public Token(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		counter++;
	}
	
	public Token(double perX, double perY, Color color, JPanel panel,Insets insets) {
		this.perX = perX;
		this.perY = perY;
		this.color = color;
		this.panel = panel;
		this.insets = insets;
		counter++;
	}
	
	
	
	public Token() {
		this (100*(counter+1),100*(counter+1),TOKEN_COLORS[counter]);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics2D = (Graphics2D)g;
		
		graphics2D.setPaint(color);
//		graphics2D.fillOval(x, y, radius*2, radius*2);
		double panelWidth = panel.getSize().getWidth();
		int radiusCalc = (int)(perRadius*panelWidth);
		graphics2D.fillOval((int)(perX*panelWidth-insets.left-radiusCalc), (int)(perY*panelWidth-insets.top-radiusCalc), radiusCalc*2, radiusCalc*2);
	
	}

	@Override
	public String toString() {
		return "x = " + getX() + ", y = " + getY() + ", perx = " + getPerX() + ", pery = " + getPerY();
	}

	
	
	

}
