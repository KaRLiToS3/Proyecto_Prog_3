package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;

import monopoly.objects.Cell;
import monopoly.objects.Token;

public class MainGameMenu extends MasterFrame {


	private static final long serialVersionUID = 1L;
	
	private final URL boardPath = getClass().getResource("/monopoly/images/board_monopoly.png");
	private final URL dicePath = getClass().getResource("/monopoly/images/dice.png");
	public static final Dimension defaultWindowDimension = new Dimension(1000, 700);
	//	private static final String boardPath = "../images/board_monopoly.png";
	//	private static final String dicePath = "../images/dice.png";
	//	private static final Point[] positionList = {new Point(599,626),new Point(543,643),new Point(487,631),new Point(434,645),new Point(377,635),new Point(322,637),new Point(267,649),new Point(212,638),new Point(162,653),new Point(109,644),new Point(18,622),new Point(21,568),new Point(22,514),new Point(24,461),new Point(22,405),new Point(21,351),new Point(24,295),new Point(27,239),new Point(25,184),new Point(28,137),new Point(32,57),new Point(107,52),new Point(162,59),new Point(216,56),new Point(269,55),new Point(323,60),new Point(379,60),new Point(433,62),new Point(484,59),new Point(543,58),new Point(599,52),new Point(616,134),new Point(614,186),new Point(597,239),new Point(618,291),new Point(600,349),new Point(596,405),new Point(620,458),new Point(618,565)};
	private static List<Point> cellPosList = new ArrayList<>();
	private static final List<double[]> posdoublelist = new ArrayList<>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		add(new double[]{0.9167927382753404,0.9515885022692889});
		add(new double[]{0.8184568835098336,0.9757942511346445});
		add(new double[]{0.7367624810892587,0.9652042360060514});
		add(new double[]{0.028744326777609682,0.9440242057488654});
		add(new double[]{0.03479576399394856,0.8608169440242057});
		add(new double[]{0.030257186081694403,0.7776096822995462});
		add(new double[]{0.039334341906202726,0.08169440242057488});
		add(new double[]{0.16036308623298035,0.08018154311649017});
		add(new double[]{0.23903177004538578,0.08925869894099848});
		add(new double[]{0.9334341906202723,0.2087745839636914});
		add(new double[]{0.8986384266263238,0.3661119515885023});
	}};
	
	// Token position setter
	private static List<Point> posList = new ArrayList<>();

	private List<Cell> cellList = new ArrayList<>();
	private List<Token> tokenList = new ArrayList<>();
	
	public MainGameMenu() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000,700);
		setMinimumSize(defaultWindowDimension);
		setDefaultWindowIcon();
		setLocationRelativeTo(null);
		setTitle("MONOPOLY");
		
		
		
		cellPosList.add( new Point(100, 300));
		cellPosList.add( new Point(200, 200));
		cellPosList.add( new Point(200, 100));
		
		// PANEL FOR MAIN DISTRIBUTION
		 
		setLayout(new BorderLayout());
		
		// PANEL FOR BOARD
		
		JPanel boardPanel = new PanelImageBuilder(boardPath, 0.7, 1, true) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (Cell c : cellList) {
					c.updateCell();
					for (Token t : tokenList) {
						if (t.getCellNumber()==c.getCellNumber()) {
							t.updateToken(c);
						}
					}
				}
				for (Cell c : cellList) {
					c.paintComponent(g);
				}
				
				for (Token token : tokenList) {
					token.paintComponent(g);
				}
				
				repaint();
			}
		};
//		boardPanel.setBackground(Color.BLACK);
		boardPanel.setBackground(Color.BLACK);
		add(boardPanel, BorderLayout.WEST);
		
		
		// PANEL FOR EVENTS
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout( new BorderLayout() );
		add(eventPanel, BorderLayout.CENTER);
		eventPanel.setPreferredSize(new Dimension((int) (this.getWidth()*0.3), HEIGHT));

		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		eventPanel.setBackground(Color.BLACK);
		
		// DICE BUTTON
		eventPanel.add(new Box.Filler(new Dimension(100, 100), null, null));

		JButton diceButton = new JButton(getIconifiedImage(dicePath, 100, 100));
		diceButton.setAlignmentX(CENTER_ALIGNMENT);
		diceButton.setBackground(Color.WHITE);
		eventPanel.add(diceButton);
		setComponentDimension(diceButton, 100, 80);
		

		addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				 Token position setter
				posList.add(getMousePosition());
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
			}
		});
		
		// CELL CREATION
		// TODO crear una manera para que se actualizen los valores x,y cuando haya resize (mandando los jpanels o asi)
//		for ( Point p : cellPosList) {
//			cellList.add(new Cell((int)p.getX(), (int)p.getY(), defaultWindowDimension, this.getSize()));
//		}
		setVisible(true);
		Insets insets = getInsets();
		diceButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				 Token position setter
				//				String str = "";
				//				for (Point p : posList) {
				//					str = str + +(int)p.getX()/boardPanel.getSize().getWidth()+"_"+(int)p.getY()/boardPanel.getSize().getHeight()+"\n";
				//				}
				//				System.out.println(str);
				PrintStream stream = null;
				try {
					stream = new PrintStream(new FileOutputStream("src/monopoly/files/cellPositions.txt")); 
					for (Point p : posList) {
						stream.println(((p.getX()-insets.left)/boardPanel.getSize().getWidth())+"_"+((p.getY()-insets.top)/boardPanel.getSize().getHeight()));
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} finally {
					if (stream!=null) {
						stream.close();
					}
				}
				
//				0.9038652130822596_0.9266600594648167
//				0.8166501486620417_0.9514370664023786
//				0.7314172447968286_0.9355797819623389
//				0.6481665014866204_0.9484638255698712
				
//				String strX = "{";
//				String strY = "{";
//				for (Point p : posList) {
//					strX = strX + (p.getX()/boardPanel.getSize().getWidth())+",";
//					strY = strY + (p.getY()/boardPanel.getSize().getHeight())+",";
//				}
//				strX=strX+"}";
//				strY=strY+"}";
//			
//				System.out.println(strX);
//				System.out.println(strY);
//				
//				String str="";
//				for (int i = 0; i < posdoubleX.length; i++) {
//					str = str + "add(new double[]{"+posdoubleX[i]+","+posdoubleY[i]+"});";
//				}
//				System.out.println(str);
//				
//				System.out.println(getInsets());
				
			}
		});
		
		
		loadCellPositions(boardPanel);
		
		// tryin token in each cell
		for (Cell c : cellList) {
			System.out.println(c.getTopLeft()+", "+c.getTopRight()+", "+c.getBottomLeft()+", "+c.getBottomRight());
			tokenList.add(new Token(c.getTopLeft(), Color.RED, boardPanel, c.getCellNumber()));
			tokenList.add(new Token(c.getTopRight(), Color.GREEN, boardPanel, c.getCellNumber()));
			tokenList.add(new Token(c.getBottomLeft(), Color.BLUE, boardPanel, c.getCellNumber()));
			tokenList.add(new Token(c.getBottomRight(), Color.YELLOW, boardPanel, c.getCellNumber()));
		}
		
//		System.out.println(boardPanel.getWidth());
//		System.out.println(cellList);
		
		
		
		
		// trying tokens on cells
		
//		cellList.add(new Cell(0.3, 0.3, boardPanel));
//		cellList.add(new Cell(0.2, 0.4, boardPanel));
//		for (Cell c : cellList) {
//			tokenList.add(new Token(c.getX(), c.getY(), Color.RED, boardPanel));
//		}

		
		
		
		
//		System.out.println(tokenList);
		
		//try tokens	
//		for (Point p : positionList) {
//			tokenList.add(new Token((int)p.getX()-insets.left, (int)p.getY()-insets.top, Color.RED));
//		}
//		Insets insets = getInsets();
//		loadCellPositions();
//		for (double[] p : posdoublelist) {
////			tokenList.add(new Token((int)(p[0]*boardPanel.getSize().getWidth()-insets.left-insets.right), (int)(p[1]*boardPanel.getSize().getWidth()-insets.top-insets.bottom), Color.RED));
//			tokenList.add(new Token(p[0], p[1], Color.RED,boardPanel,insets));
//		}
//		loadCellPositions();
//		for (double[] p : Cell.cellPositionList) {
//			tokenList.add(new Token((int)(p[0]*boardPanel.getSize().getWidth()-insets.left-insets.right), (int)(p[1]*boardPanel.getSize().getWidth()-insets.top-insets.bottom), Color.RED));
			
//			tokenList.add(new Token(p[0], p[1], Color.RED,boardPanel,insets));
//		}
	}
	// TODO revisar que si cogemos la proporcion de la posicion de los tokens respecto a las dimensiones del board panel o main window (teniendo en cuenta insets)
	@Override
	public String windowName() {
		return MasterFrame.MainGameMenu;
	}
	
	public void loadCellPositions(JPanel panel) {
		File file = new File("src/monopoly/files/cellPositions.txt");
//		InputStream is = new i/
//		this.getClass().getResource("src/monopoly/files/cellPositions.txt")
		// TODO probar a crear un input stream en vez de un file para utilizar el this.geclas... y tener el fichero fuera de src
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
//				System.out.println(scanner.nextLine());
				int separation = line.indexOf("_");
//				double[] tuple = { Double.parseDouble( line.substring(0, separation)), Double.parseDouble(line.substring(separation+1)) };
//				Cell.cellPositionList.add(tuple);
				cellList.add(new Cell(Double.parseDouble( line.substring(0, separation)), Double.parseDouble(line.substring(separation+1)), panel));
//				System.out.println(scanner.nextLine());
			}
			scanner.close();
//			System.out.println("list: "+Cell.cellPositionList);
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			System.out.println("no hay");
		}	
	}
}
