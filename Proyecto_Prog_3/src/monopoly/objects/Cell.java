package monopoly.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.bouncycastle.oer.its.ieee1609dot2.Opaque;

import monopoly.windows.MainGameMenu;

public class Cell extends JComponent{
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	Color color;
	int width;
	int height;
	double perX;
	double perY;
	JPanel panel;
	String name;
	private int cellNumber;


	
	
	static int counter = 0;
	public static final List<double[]> cellPositionList = new ArrayList<>();
	
	public enum CellType {
		Property(30,40),
		Start(50,50),
		Free(50,50),
		Gojail(50,50),
		Jail(50,50),
		Tax(40,40),
		Train(40,40),
		Utility(40,40),
		Chest(40,40),
		Chance(40,40);
		
		
		int width;
		int height;
		
		private CellType(int w, int h) {
			width=w;
			height=h;
		}
	}
	
	CellType cType;
	
	
	public Cell(double perX, double perY, CellType type, String name, JPanel panel) {
		this.perX = perX;
		this.perY = perY;
		this.panel = panel;
		this.cellNumber = counter;
		this.cType=type;
		this.width=type.width;
		this.height=type.height;
		this.color=Color.black;
		this.name=name;
		setX((int)(this.getPerX()*this.getPanel().getWidth()));
		setY((int)(this.getPerY()*this.getPanel().getWidth()));
		counter++;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(int cellNumber) {
		this.cellNumber = cellNumber;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getWidth() {
		return (int)(width*(getPanel().getWidth()/MainGameMenu.defaultWindowDimension.getWidth()));
	}
	public int getHeight() {
		return (int)(height*(getPanel().getWidth()/MainGameMenu.defaultWindowDimension.getWidth()));
	}
	public CellType getcType() {
		return cType;
	}
	
	
	// Corner getters
	

	public Point getTopLeft() {
		return new Point(getX(), getY());
	}
	public Point getTopRight() {
		return new Point(getX()+getWidth(), getY());
	}
	public Point getBottomLeft() {
		return new Point(getX(), getY()+getHeight());
	}
	public Point getBottomRight() {
		return new Point(getX()+getWidth(), getY()+getHeight());
	}



	public void updateCell() {
		setX((int)(this.getPerX()*this.getPanel().getWidth()));
		setY((int)(this.getPerY()*this.getPanel().getWidth()));
	}
	
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (!getColor().equals(Color.black)) {
			Graphics2D graphics2d = (Graphics2D) g;
			
			graphics2d.setColor(getColor());
			graphics2d.fillRect(getX(), getY(), getWidth(), getHeight());
			graphics2d.setColor(Color.black);
			graphics2d.drawRect(getX(), getY(), getWidth(), getHeight());
		}
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName()+" Selling Price: "+getX()+", Current Rent: "+getY();
	}
}
