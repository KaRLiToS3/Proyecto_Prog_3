package monopoly.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JPanel;

import monopoly.windows.MainGameMenu;

public class Token extends JComponent{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	
	private int x;
	private int y;
	private double perX;
	private double perY;
	private JPanel panel;
	private Color color;
	private String userEmail;
	private int cellNumber;
	private int money;
	private boolean inJail;
	private int jailTurnCounter;

	
	private final static int radius = 10;	

	
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
	public int getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(int cell) {
		this.cellNumber = cell;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public boolean isInJail() {
		return inJail;
	}
	public void setInJail(boolean inJail) {
		this.inJail = inJail;
	}
	public int getJailTurnCounter() {
		return jailTurnCounter;
	}
	public void setJailTurnCounter(int jailTurnCounter) {
		this.jailTurnCounter = jailTurnCounter;
	}

	public void setCoordinates(Point point) {
		setX((int)point.getX());
		setY((int)point.getY());
	}
	
	public void modifyMoney(int value) {
		setMoney(getMoney()+value);
	}
	

	public Token(Color color, JPanel panel, int money, String userEmail) {
		this.color = color;
		this.panel = panel;
		this.cellNumber=0;
		this.money=money;
		this.userEmail=userEmail;
		this.inJail=false;
		this.jailTurnCounter=3;
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D graphics2D = (Graphics2D)g;

		
		if (!this.isInJail()) {
			graphics2D.setPaint(color);
			int radiusCalc = (int)(radius*(getPanel().getWidth()/MainGameMenu.defaultWindowDimension.getWidth()));
			graphics2D.fillOval(getX()-radiusCalc, getY()-radiusCalc, 2*radiusCalc, 2*radiusCalc);
		} else {
			int radiusCalc = (int)(radius*(getPanel().getWidth()/MainGameMenu.defaultWindowDimension.getWidth()));
			graphics2D.setPaint(color);
			graphics2D.fillOval(getX()-radiusCalc, getY()-radiusCalc, 2*radiusCalc, 2*radiusCalc);
			graphics2D.setColor(Color.gray);
			graphics2D.setStroke(new BasicStroke(3));
			graphics2D.drawLine(getX()-radiusCalc, getY()-radiusCalc, getX()+radiusCalc, getY()+radiusCalc);
			graphics2D.drawLine(getX()+radiusCalc, getY()-radiusCalc, getX()-radiusCalc, getY()+radiusCalc);
		}
	}

	public void updateToken(Cell cell) {
		if (this.getColor().equals(Color.RED)) {
			this.setCoordinates(cell.getTopLeft());
		} else if (this.getColor().equals(Color.GREEN)) {
			this.setCoordinates(cell.getTopRight());
		} else if (this.getColor().equals(Color.BLUE)) {
			this.setCoordinates(cell.getBottomLeft());
		} else if (this.getColor().equals(Color.YELLOW)) {
			this.setCoordinates(cell.getBottomRight());
		}
	}

	@Override
	public String toString() {
		return "x = " + getX() + ", y = " + getY() + ", perx = " + getPerX() + ", pery = " + getPerY();
	}





}
