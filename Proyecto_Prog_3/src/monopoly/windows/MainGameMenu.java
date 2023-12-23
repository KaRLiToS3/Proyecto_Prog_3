package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.nio.file.Paths;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;

import monopoly.objects.Cell;
import monopoly.objects.Cell.CellType;
import monopoly.objects.Token;

public class MainGameMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	

	private static Font font1 = new Font("Arial Black", Font.BOLD, 24);
	
	private final URL boardPath = getClass().getResource(getStringProperty("board_img"));
	private final URL dicePath = getClass().getResource(getStringProperty("dice_img"));
	public static final Dimension defaultWindowDimension = getDimensionProperty("mainGameMenuSizeX", "mainGameMenuSizeY");
	private static String cellPositionsPath = Paths.get(getStringProperty("cellPositions1")).toAbsolutePath().toString();

	
	// cell position setter
	/////////////
//	private static List<Point> posList = new ArrayList<>();
	/////////////

	private static List<Cell> cellList = new ArrayList<>();
	private static List<Token> tokenList = new ArrayList<>();
	
	private static int turn;
	
	public MainGameMenu() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(defaultWindowDimension);
		setMinimumSize(defaultWindowDimension);
		setDefaultWindowIcon();
		setLocationRelativeTo(null);
		setTitle("MONOPOLY");
		
		
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
		boardPanel.setBackground(Color.BLACK);
		add(boardPanel, BorderLayout.WEST);
		
		
		// PANEL FOR EVENTS
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout( new BorderLayout() );
		add(eventPanel, BorderLayout.CENTER);
		eventPanel.setPreferredSize(new Dimension((int) (this.getWidth()*0.3), HEIGHT));

		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		eventPanel.setBackground(Color.BLACK);
		
		//PARTS OF THE PANEL
		JLabel Turn = new JLabel("Turn from user");
		Turn.setFont(font1);
		Turn.setForeground(Color.RED); //TODO The color must change according to the player that's playing
		Turn.setText("Turn from ?"); //TODO The user should also appear
		Turn.setAlignmentX(CENTER_ALIGNMENT);
		eventPanel.add(Turn, BorderLayout.NORTH);
		
		
		eventPanel.add(new Box.Filler(new Dimension(100, 100), null, null));

		//DICE PANEL
		JPanel dicePanel = new JPanel();
		dicePanel.setLayout(new FlowLayout());
		dicePanel.setAlignmentX(CENTER_ALIGNMENT);
		dicePanel.setBackground(Color.black);
		dicePanel.setMaximumSize(new Dimension(getWidth(),110));
		
		eventPanel.add(dicePanel,BorderLayout.SOUTH);
		
		//DICE RESULT
		JLabel diceResult = new JLabel();
		diceResult.setForeground(Color.white);
		diceResult.setFont(font1);
		diceResult.setPreferredSize(new Dimension(40, 80));
		
		dicePanel.add(diceResult);
		
		//DICE BUTTON
		JButton diceButton = new JButton(getIconifiedImage(dicePath, 100, 100));
//		diceButton.setAlignmentX(CENTER_ALIGNMENT);
		diceButton.setBackground(Color.white);
		setComponentDimension(diceButton, 100, 80);
		
		dicePanel.add(diceButton);

//		addMouseListener( new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				 Token position setter
//				///////////
//				posList.add(getMousePosition());
//				///////////
//			}
//		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
			}
		});
		
		turn = 0;
		setVisible(true);
		
		
//		Insets insets = getInsets();
		diceButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				// cell position setter
				/////////////
//				PrintStream stream = null;
//				try {
//					stream = new PrintStream(new FileOutputStream(cellPositionsPath,true)); 
//					for (Point p : posList) {
//						stream.println(((p.getX()-insets.left)/boardPanel.getSize().getWidth())+"_"+((p.getY()-insets.top)/boardPanel.getSize().getHeight()));
//					}
//				} catch (FileNotFoundException e1) {
//					e1.printStackTrace();
//				} finally {
//					if (stream!=null) {
//						stream.close();
//					}
//				}
				///////////
				diceButton.setEnabled(false);
				Random dice = new Random();
				int hops = dice.nextInt(1, 13);
				diceResult.setText(""+hops);
				Runnable thread = new Runnable() {
					
					@Override
					public void run() {
						Token t = tokenList.get(turn);
						for (int i = 0; i<hops;i++) {
							t.setCellNumber(t.getCellNumber()+1);
							if ( t.getCellNumber()==cellList.size()) t.setCellNumber(0);
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
//						switch (cellList.get(t.getCellNumber()).getcType()) {
//						case Property: {
//							
//							
//						}
//						default:
//							throw new IllegalArgumentException("Unexpected value: " + cellList.get(t.getCellNumber()).getcType());
//						} 
						
						
						turn++;
						if (turn==tokenList.size()) turn = 0;
						diceButton.setEnabled(true);
					}
				};
				Thread t =  new Thread(thread);
				t.start();
				
//				Token t = tokenList.get(0);
//				t.setCellNumber(t.getCellNumber()+1);
			}
		});
		
		
		loadCellPositions(boardPanel);
		tokenList.add(new Token(Color.RED, boardPanel, 0));
		tokenList.add(new Token(Color.GREEN, boardPanel, 0));
		tokenList.add(new Token(Color.BLUE, boardPanel, 0));
		tokenList.add(new Token(Color.YELLOW, boardPanel, 0));
		
		// -------------tryin token in each cell----------------------
//		for (Cell c : cellList) {
//			tokenList.add(new Token(c.getTopLeft(), Color.RED, boardPanel, c.getCellNumber()));
//			tokenList.add(new Token(c.getTopRight(), Color.GREEN, boardPanel, c.getCellNumber()));
//			tokenList.add(new Token(c.getBottomLeft(), Color.BLUE, boardPanel, c.getCellNumber()));
//			tokenList.add(new Token(c.getBottomRight(), Color.YELLOW, boardPanel, c.getCellNumber()));
//		}

	}
	// TODO revisar que si cogemos la proporcion de la posicion de los tokens respecto a las dimensiones del board panel o main window (teniendo en cuenta insets)
	@Override
	public String windowName() {
		return MasterFrame.MainGameMenu;
	}
	
	public void loadCellPositions(JPanel panel) {
		File file = new File(cellPositionsPath);
		// TODO probar a crear un input stream en vez de un file para utilizar el this.geclas... y tener el fichero fuera de src
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				
				
//				int separation = line.indexOf("_");
//				cellList.add(new Cell(Double.parseDouble( line.substring(0, separation)), Double.parseDouble(line.substring(separation+1)), panel));
			
				String[] splitedLine = line.split("_");
				cellList.add(new Cell(Double.parseDouble( splitedLine[0].strip() ), Double.parseDouble( splitedLine[1].strip() ),  CellType.valueOf(splitedLine[2].strip()),panel));

				
			
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("no hay");
		}	
	}
}
