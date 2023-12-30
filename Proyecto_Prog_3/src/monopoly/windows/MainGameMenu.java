package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.nio.file.Paths;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListDataListener;

import org.jfree.ui.tabbedui.VerticalLayout;

import com.orsoncharts.util.json.parser.ParseException;

import monopoly.objects.Cell;
import monopoly.objects.Cell.CellType;
import monopoly.objects.Token;

public class MainGameMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	

	private static Font font1 = new Font("Arial Black", Font.BOLD, 24);
	private static Font font2 = new Font("Arial Black", Font.PLAIN, 16);
	private static Font font3 = new Font("Arial Black", Font.PLAIN, 12);


	
	private final URL boardPath = getClass().getResource(getStringProperty("board_img"));
	private final URL dicePath = getClass().getResource(getStringProperty("dice_img"));
	public static final Dimension defaultWindowDimension = getDimensionProperty("mainGameMenuSizeX", "mainGameMenuSizeY");
	private static String cellPositionsPath = Paths.get(getStringProperty("cellPositions1")).toAbsolutePath().toString();
	private static String cellPricesPath = Paths.get(getStringProperty("cellPrices")).toAbsolutePath().toString();

	
	// cell position setter
	/////////////
//	private static List<Point> posList = new ArrayList<>();
	/////////////

	private static List<Cell> cellList = new ArrayList<>();
	private static List<Token> tokenList = new ArrayList<>();
	private static Map<Integer, Integer[]> priceList = new HashMap<>();
	
	private static int turn;
	private static Color turnColor;
	
	Random dice = new Random();
	
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
		eventPanel.setBackground(Color.black);
		add(eventPanel, BorderLayout.CENTER);
		eventPanel.setPreferredSize(new Dimension((int) (this.getWidth()*0.3), HEIGHT));

		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
		
		//PARTS OF THE EVENT PANEL
		
		//TURN LABEL
		JLabel Turn = new JLabel("Turn of user");
		Turn.setFont(font1);
		Turn.setText("Turn of ?"); //TODO The user should also appear
		Turn.setAlignmentX(CENTER_ALIGNMENT);
		
		eventPanel.add(Turn);
		
		//INFORMATION AREA
		JTextArea infoText = new JTextArea();
		infoText.setEditable(false);
		infoText.setBackground(Color.black);
		infoText.setForeground(Color.white);
		infoText.setFont(font2);
		infoText.setLineWrap(true);
		infoText.setWrapStyleWord(true);
		infoText.setMaximumSize(new Dimension(10000, 200));

		
		eventPanel.add(infoText);
		
		//BUYIN BUTTON
		JPanel optionPanel = new JPanel(new VerticalLayout());
		optionPanel.setBackground(Color.black);
		
		JButton buyButton = new JButton("Buy");
		buyButton.setFont(font2);

		optionPanel.add(buyButton);
		
		eventPanel.add(optionPanel);
		optionPanel.setVisible(false);
		
		// ElecWater Dice
		JPanel elecWaterPanel = new JPanel(new VerticalLayout());
		elecWaterPanel.setBackground(Color.black);
		
		JButton elecWaterDice = new JButton("Roll!");
		elecWaterDice.setFont(font2);

		elecWaterPanel.add(elecWaterDice);
		
		eventPanel.add(elecWaterPanel);
		elecWaterPanel.setVisible(false);
		
		//JLIST
		DefaultListModel<String> optionModel = new DefaultListModel<>();
		
		JList<String> optionSelector = new JList<String>();
		
		optionSelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		eventPanel.add(optionSelector);
		
		eventPanel.add(new Box.Filler(new Dimension(100, 100), null, null));
		
		//MONEY DISPLAY
		JLabel money = new JLabel("Money");
		JPanel moneyPanel = new JPanel();
		moneyPanel.setLayout(new GridLayout(2, 2));
		moneyPanel.setBackground(Color.black);
		moneyPanel.setMaximumSize(new Dimension(getWidth(),400));
		
		eventPanel.add(money);
		eventPanel.add(moneyPanel);

		JLabel money0 = new JLabel();
		money0.setFont(font1);
		money0.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel money1 = new JLabel();
		money1.setFont(font1);
		money1.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel money2 = new JLabel();
		money2.setFont(font1);
		money2.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel money3 = new JLabel();
		money3.setFont(font1);
		money3.setHorizontalAlignment(SwingConstants.CENTER);
		
		moneyPanel.add(money0);
		moneyPanel.add(money1);
		moneyPanel.add(money2);
		moneyPanel.add(money3);
		
		//DICE PANEL
		JPanel dicePanel = new JPanel();
		dicePanel.setLayout(new FlowLayout());
		dicePanel.setAlignmentX(CENTER_ALIGNMENT);
		dicePanel.setBackground(Color.black);
		dicePanel.setMaximumSize(new Dimension(getWidth(),110));
		
		eventPanel.add(dicePanel);
		
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
		
//		JButton button = new JButton("ff");
//		eventPanel.add(button);
		
		

//		addMouseListener( new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
////				 Token position setter
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
		
		turn = -1;
		loadPrices();
		setVisible(true);
		
//		Insets insets = getInsets();
//		System.out.println(insets);
//		button.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// cell position setter
//				/////////////
//				PrintStream stream = null;
//				try {
//					stream = new PrintStream(new FileOutputStream("data/cellPositions3.txt",true)); 
//					for (Point p : posList) {
//						stream.println(((p.getX()-insets.left)/boardPanel.getSize().getWidth())+";"+((p.getY()-insets.top)/boardPanel.getSize().getHeight()));
//						cellList.add(new Cell(((p.getX()-insets.left)/boardPanel.getSize().getWidth()), ((p.getY()-insets.top)/boardPanel.getSize().getHeight()), CellType.Property, boardPanel));
//					}
//				} catch (FileNotFoundException e1) {
//					e1.printStackTrace();
//				} finally {
//					if (stream!=null) {
//						stream.close();
//					}
//				}
//			}
//		});
		
		buyButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Token t = tokenList.get(turn);
				cellList.get(t.getCellNumber()).setColor(t.getColor());
				t.setMoney(t.getMoney()-priceList.get(t.getCellNumber())[0]);
				updateMoney(money0, money1, money2, money3);
			}
		});
		
		elecWaterDice.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Token t = tokenList.get(turn);
				int result = dice.nextInt(1, 13);
				for (Token propietary:tokenList) {
					if (cellList.get(t.getCellNumber()).getColor().equals(propietary.getColor())) {
						int elecwaterCounter=0;
						for (Cell cell:cellList) {
							if(cell.getcType().equals(CellType.ElecWater) && cell.getColor().equals(propietary.getColor())) {
								elecwaterCounter++;
							}
						}
						diceResult.setText(result+"");
						if (elecwaterCounter==1) {
							t.setMoney(t.getMoney()-result*4);
							propietary.setMoney(propietary.getMoney()+result*4);						
						} else {
							t.setMoney(t.getMoney()-result*10);
							propietary.setMoney(propietary.getMoney()+result*10);	
						}
					}
				}
				elecWaterPanel.setVisible(false);
				updateMoney(money0, money1, money2, money3);
			}
		});
		
		
//		Insets insets = getInsets();
		diceButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				

				
				
				
				
				optionPanel.setVisible(false);
				nextTurn();
				turnColor=tokenList.get(turn).getColor();
				Turn.setForeground(turnColor);
				diceButton.setEnabled(false);
//				int hops = dice.nextInt(1, 13);
				int hops = 4;
				diceResult.setText(""+hops);
				Runnable thread = new Runnable() {
					
					@Override
					public void run() {
						Token t = tokenList.get(turn);
						for (int i = 0; i<hops;i++) {
							t.setCellNumber(t.getCellNumber()+1);
							if ( t.getCellNumber()==cellList.size()) t.setCellNumber(0);
//							try {
//								Thread.sleep(500);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
						}
						diceButton.setEnabled(true);
						switch (cellList.get(t.getCellNumber()).getcType()) {
						case Property: {
							if (cellList.get(t.getCellNumber()).getColor().equals(Color.black)) {
								optionPanel.setVisible(true);
							} else {
								t.setMoney(t.getMoney()-priceList.get(t.getCellNumber())[1]);
								for (Token propietary:tokenList) {
									if (cellList.get(t.getCellNumber()).getColor().equals(propietary.getColor())) {
										propietary.setMoney(propietary.getMoney()+priceList.get(t.getCellNumber())[1]);
									}
								}
							}
							updateMoney(money0, money1, money2, money3);
							break;
						}
						case Train: {
							if (cellList.get(t.getCellNumber()).getColor().equals(Color.black)) {
								optionPanel.setVisible(true);
							} else {
								
								for (Token propietary:tokenList) {
									if (cellList.get(t.getCellNumber()).getColor().equals(propietary.getColor())) {
										int trainCounter=0;
										for (Cell cell:cellList) {
											if(cell.getcType().equals(CellType.Train) && cell.getColor().equals(propietary.getColor())) {
												trainCounter++;
											}
										}
										t.setMoney(t.getMoney()-priceList.get(t.getCellNumber())[1] *(int)(Math.pow(2, trainCounter-1)));
										propietary.setMoney(propietary.getMoney()+priceList.get(t.getCellNumber())[1] *(int)(Math.pow(2, trainCounter-1)));
									}
								}
							}
							updateMoney(money0, money1, money2, money3);
							break;
						}
						case Tax: {
							if (t.getCellNumber()==4) {
								t.setMoney(t.getMoney()-200);
							} else {
								t.setMoney(t.getMoney()-100);
							}
							updateMoney(money0, money1, money2, money3);

							break;
						}
						case Free: {
							//TODO no hace nada
							break;
						}
						case Start: {
							//TODO no hace nada, igual unificarlo con Free
							break;
						}
						case Gojail: {
							//TODO tp a la casilla 11 y entras en modo carcel
							break;
						}
						case Jail: {
							//TODO mecanicas de encarcelado, si estas encarcelado, si no nada
							break;
						}
						case ElecWater: { // TODO se podria meter una flag global y utilizar el boton diceButton para esto en vez de crear otro
							if (cellList.get(t.getCellNumber()).getColor().equals(Color.black)) {
								optionPanel.setVisible(true);
							} else {
								elecWaterPanel.setVisible(true);
							}
							break;
						}
						
						case Chest: {
							//TODO todavia por decidir
							break;
						}
						case Chance: {
							//TODO todavia por decidir
							break;
						}

						default:
//							throw new IllegalArgumentException("Unexpected value: " + cellList.get(t.getCellNumber()).getcType());
							System.out.println("not there"+ cellList.get(t.getCellNumber()).getcType());
						}
//						System.out.println(t.getCellNumber()+" "+ cellList.get(t.getCellNumber()).getcType());
						turnColor=tokenList.get(turn).getColor();
						Turn.setForeground(turnColor);
					}
				};
				
				Thread t =  new Thread(thread);
				t.start();
			}
		});
		
		loadCellPositions(boardPanel);
		tokenList.add(new Token(Color.RED, boardPanel, 0));
		tokenList.add(new Token(Color.GREEN, boardPanel, 0));
//		tokenList.add(new Token(Color.BLUE, boardPanel, 0));
//		tokenList.add(new Token(Color.YELLOW, boardPanel, 0));
		
		money0.setForeground(tokenList.get(0).getColor());
		money1.setForeground(tokenList.get(1).getColor());
//		money2.setForeground(tokenList.get(2).getColor());
//		money3.setForeground(tokenList.get(3).getColor());
		updateMoney(money0, money1, money2, money3);

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
				String[] splitedLine = line.split(";");
				Cell cell = new Cell(Double.parseDouble( splitedLine[0].strip() ), Double.parseDouble( splitedLine[1].strip() ),  CellType.valueOf(splitedLine[2].strip()),panel);
				cellList.add(cell);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("no hay");
		}	
	}
	// TODO poner pop-ups en los catches
	public void loadPrices() {
		File file = new File(cellPricesPath);
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] splitedLine = line.split(";");
				Integer[] intarray = {Integer.parseInt(splitedLine[1]), Integer.parseInt(splitedLine[2])};
				priceList.putIfAbsent(Integer.parseInt(splitedLine[0]), intarray );
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("no hay");
		}	
	}
	
	public void nextTurn() {
		turn++;
		if (turn==tokenList.size()) turn = 0;
		
	}
	
	public void updateMoney(JLabel label0, JLabel label1, JLabel label2, JLabel label3) {
		label0.setText(tokenList.get(0).getMoney()+"");
		label1.setText(tokenList.get(1).getMoney()+"");
//		label2.setText(tokenList.get(2).getMoney()+"");
//		label3.setText(tokenList.get(3).getMoney()+"");
	}
}
