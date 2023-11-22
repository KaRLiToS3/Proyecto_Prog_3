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

import monopoly.windows.MainGameMenu;

public class Cell extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	double perX;
	double perY;
	JPanel panel;
	private int cellNumber;
	
	private final static int cellWidth = 30;
	private final static int cellHeight = 40;

	
	
	static int counter = 0;
	public static final List<double[]> cellPositionList = new ArrayList<>();
	
//	public Cell(int x, int y, Dimension defaultWindowDimension, Dimension actualWindowDimension) {
//		this.x = (int) (x * ( defaultWindowDimension.getWidth() / actualWindowDimension.getWidth()));
//		this.y = (int) (y * ( defaultWindowDimension.getHeight() / actualWindowDimension.getHeight()));
//		this.cellNumber = counter;
//		counter++;
//	}
	
	
	
	public Cell(double perX, double perY, JPanel panel) {
		this.perX = perX;
		this.perY = perY;
		this.panel = panel;
		this.cellNumber = counter;
		setX((int)(this.getPerX()*this.getPanel().getWidth()));
		setY((int)(this.getPerY()*this.getPanel().getWidth()));
		counter++;
	}
	
//	public Cell(double perX, double perY, JPanel panel) {
//		this.x = (int)(perX*panel.getWidth());
//		this.y = (int)(perY*panel.getHeight());
//		this.cellNumber = counter;
////		this.panel = panel;
//		counter++;
//	}



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
	
	public int getWidth() {
		return (int)(cellWidth*(getPanel().getWidth()/MainGameMenu.defaultWindowDimension.getWidth()));
	}
	public int getHeight() {
		return (int)(cellHeight*(getPanel().getWidth()/MainGameMenu.defaultWindowDimension.getWidth()));
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
		
		Graphics2D graphics2d = (Graphics2D) g;
		
		graphics2d.fillRect(getX(), getY(), getWidth(), getHeight());
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "x: "+getX()+", y: "+getY();
	}

	
	

}
