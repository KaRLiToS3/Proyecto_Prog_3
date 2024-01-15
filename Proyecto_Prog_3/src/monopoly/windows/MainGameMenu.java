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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.ui.tabbedui.VerticalLayout;

import com.orsoncharts.util.json.parser.ParseException;

import monopoly.objects.Cell;
import monopoly.objects.Cell.CellType;
import monopoly.objects.Token;
import monopoly.objects.User;

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
	private static final Color[] defaultColors = {Color.red,Color.green,Color.blue,Color.yellow,};
	public static List<User> gameUsers = new ArrayList<>(monopoly.windows.GameSettingsMenu.getSelectedUsers());
	
	private static int turn;
	private static Color turnColor;
	
	JLabel money0 = new JLabel();
	JLabel money1 = new JLabel();
	JLabel money2 = new JLabel();
	JLabel money3 = new JLabel();
	JLabel[] moneyLabels = {money0,money1,money2,money3};
	JTextArea infoText = new JTextArea();		
	JLabel turnLabel = new JLabel();
	DefaultListModel<String> optionModel = new DefaultListModel<>();


	
	Random dice = new Random();
	
	public MainGameMenu() {
		System.out.println("New window");
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
		turnLabel.setFont(font1);
//		turnLabel.setText("Turn of "+monopoly.windows.GameSettingsMenu.getSelectedUsers().get(turn).getAlias()); //TODO The user should also appear
		turnLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		eventPanel.add(turnLabel);
		
		//INFORMATION AREA
		infoText.setEditable(false);
		infoText.setBackground(Color.black);
		infoText.setForeground(Color.white);
		infoText.setFont(font2);
		infoText.setLineWrap(true);
		infoText.setWrapStyleWord(true);
		infoText.setMaximumSize(new Dimension(10000, 200));

		
		eventPanel.add(infoText);
		
		//BUYIN BUTTON
		JPanel buyingPanel = new JPanel(new VerticalLayout());
		buyingPanel.setBackground(Color.black);
		buyingPanel.setMaximumSize(new Dimension(getWidth(),50));
		
		JButton buyButton = new JButton("Buy");
		buyButton.setFont(font2);

		buyingPanel.add(buyButton);
		
		eventPanel.add(buyingPanel);
		buyingPanel.setVisible(false);
		
		// UTILITY PANEL
		JPanel utilityPanel = new JPanel(new VerticalLayout());
		utilityPanel.setBackground(Color.black);
		utilityPanel.setMaximumSize(new Dimension(getWidth(),50));
		
		JButton utilityDice = new JButton("Roll!");
		utilityDice.setFont(font2);

		utilityPanel.add(utilityDice);
		
		eventPanel.add(utilityPanel);
		utilityPanel.setVisible(false);
		
		// JAIL PANEL
		JPanel jailPanel = new JPanel(new VerticalLayout());
		jailPanel.setBackground(Color.black);
		jailPanel.setMaximumSize(new Dimension(getWidth(),50));
		
		JButton jailPayButton = new JButton("Pay 50");
		jailPayButton.setFont(font2);
		jailPanel.add(jailPayButton);
		JButton jailWaitButton = new JButton("Wait");
		jailWaitButton.setFont(font2);
		jailPanel.add(jailWaitButton);
		
		eventPanel.add(jailPanel);
		jailPanel.setVisible(false);
		
		
		//JLIST
//		JPanel propertyPanel = new JPanel();
		JLabel properties = new JLabel("Your Properties:");
		properties.setForeground(Color.white);
		properties.setFont(font2);
		properties.setAlignmentX(CENTER_ALIGNMENT);
		JList<String> propertySelector = new JList<String>(optionModel);
		propertySelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		propertySelector.setFont(font3);
		JButton sellButton = new JButton("Sell");
		sellButton.setFont(font2);
		sellButton.setEnabled(false);
		sellButton.setAlignmentX(CENTER_ALIGNMENT);
		
		eventPanel.add(properties);
		eventPanel.add(propertySelector);
		eventPanel.add(sellButton);
		
//propertyPanel.add(optionSelector);
//		
//		eventPanel.add(optionSelector);
		
		
		// SPACER
		eventPanel.add(new Box.Filler(new Dimension(100, 100), null, null));
		
		//MONEY DISPLAY
		JLabel money = new JLabel("Money");
		money.setForeground(Color.white);
		money.setBackground(Color.gray);
		money.setFont(font2);
		money.setAlignmentX(CENTER_ALIGNMENT);
		JPanel moneyPanel = new JPanel();
		moneyPanel.setLayout(new GridLayout(2, 2));
		moneyPanel.setBackground(Color.black);
		moneyPanel.setMaximumSize(new Dimension(getWidth(),400));
		
		eventPanel.add(money);
		eventPanel.add(moneyPanel);

		
		money0.setFont(font1);
		money0.setHorizontalAlignment(SwingConstants.CENTER);
		money1.setFont(font1);
		money1.setHorizontalAlignment(SwingConstants.CENTER);
		money2.setFont(font1);
		money2.setHorizontalAlignment(SwingConstants.CENTER);
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
				updateMoney();
				updatePropertyList(t);
			}
		});
		
		utilityDice.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Token t = tokenList.get(turn);
				int result = dice.nextInt(1, 13);
				for (Token propietary:tokenList) {
					if (cellList.get(t.getCellNumber()).getColor().equals(propietary.getColor())) {
						int utilityCounter=0;
						for (Cell cell:cellList) {
							if(cell.getcType().equals(CellType.Utility) && cell.getColor().equals(propietary.getColor())) {
								utilityCounter++;
							}
						}
						diceResult.setText(result+"");
						if (utilityCounter==1) {
							t.setMoney(t.getMoney()-result*4);
							propietary.setMoney(propietary.getMoney()+result*4);						
						} else {
							t.setMoney(t.getMoney()-result*10);
							propietary.setMoney(propietary.getMoney()+result*10);	
						}
					}
				}
				utilityPanel.setVisible(false);
				updatePropertyList(t);
				updateMoney();
			}
		});
		
		jailPayButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Token t = tokenList.get(turn);
				t.setMoney(t.getMoney()-50);
				t.setInJail(false);
				diceButton.setEnabled(true);
				updateMoney();
				jailPanel.setVisible(false);
			}
		});
		
		jailWaitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Token t = tokenList.get(turn);
				t.setJailTurnCounter(t.getJailTurnCounter()+1);
				diceButton.setEnabled(true);
				jailPanel.setVisible(false);
			}
		});
		
		propertySelector.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!propertySelector.isSelectionEmpty()) sellButton.setEnabled(true);
			}
		});
		
		sellButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String selection = propertySelector.getSelectedValue();
				int selectionCellNumber = Integer.parseInt(selection.substring(selection.length()-2, selection.length()).strip());
				cellList.get(selectionCellNumber).setColor(Color.black);
				Token t = tokenList.get(turn);
				t.setMoney(t.getMoney()+(int)(priceList.get(selectionCellNumber)[0]*0.5));
				updateMoney();
				updatePropertyList(t);
				sellButton.setEnabled(false);
			}
		});
		
		
//		Insets insets = getInsets();
		diceButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// prepare display for next turn
				buyingPanel.setVisible(false);
				infoText.setText("");
				nextTurn();
				diceButton.setEnabled(false);
				propertySelector.clearSelection();
				sellButton.setEnabled(false);
				// get the current token turn and check if in jail
				Token token = tokenList.get(turn);
				updatePropertyList(token);
				if (token.isInJail() && token.getJailTurnCounter()<3) {
					//if in jail
					jailPanel.setVisible(true);
				} else {
					// if out of jail
					token.setInJail(false);
					token.setJailTurnCounter(0);
//					int hops = dice.nextInt(1, 13);
					int hops = 1;
					diceResult.setText(""+hops);
					Runnable thread = new Runnable() {

						@Override
						public void run() {

							jumpAnimation(token, hops);
							
							cellMechanics(token, buyingPanel, utilityPanel);
							
							diceButton.setEnabled(true);
							
						}
					};

					Thread t =  new Thread(thread);
					t.start();
				}
			}
		});
		
		loadCellPositions(boardPanel);
		
		for (int i=0;i<gameUsers.size();i++) {
			tokenList.add(new Token(defaultColors[i], boardPanel));
			moneyLabels[i].setForeground(tokenList.get(i).getColor());
		}

		updateMoney();

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
				Cell cell = new Cell(Double.parseDouble( splitedLine[0].strip() ), Double.parseDouble( splitedLine[1].strip() ), CellType.valueOf(splitedLine[2].strip()), splitedLine[3], panel);
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
		turnColor=tokenList.get(turn).getColor();
		turnLabel.setForeground(turnColor);
		turnLabel.setText("Turn of "+gameUsers.get(turn).getAlias());
	}
	
	public void updatePropertyList(Token token) {
		optionModel.clear();
		for (Cell cell:cellList) {
			if(cell.getColor().equals(token.getColor())) {
				optionModel.addElement(cell.getName()+", Selling Price: "+(int)(priceList.get(cell.getCellNumber())[0]*0.5)+", Rent: "+priceList.get(cell.getCellNumber())[1]+", Cell Number: "+cell.getCellNumber());
			}
		}
	}
	
	public void cellMechanics(Token token, JPanel optionPanel, JPanel utilityPanel) {
		switch (cellList.get(token.getCellNumber()).getcType()) {
		case Property: {
			propertyCase(token,optionPanel);
			break;
		}
		case Train: {
			trainCase(token, optionPanel);
			break;
		}
		case Tax: {
			taxCase(token);
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
			gojailCase(token);
			break;
		}
		case Jail: {
		
			break;
		}
		case Utility: { // TODO se podria meter una flag global y utilizar el boton diceButton para esto en vez de crear otro
			utilityCase(token, optionPanel, utilityPanel);
			break;
		}
		case Chest: {
			//TODO todavia por decidir
			chestCase(token);
			break;
		}
		case Chance: {
			chanceCase(token, optionPanel, utilityPanel);
			break;
			
		}

		default:
//			throw new IllegalArgumentException("Unexpected value: " + cellList.get(t.getCellNumber()).getcType());
			System.out.println("not there"+ cellList.get(token.getCellNumber()).getcType());
		}
	}
	
	public void updateMoney() {
		for (int i=0;i<tokenList.size();i++) {
			moneyLabels[i].setText(tokenList.get(i).getMoney()+"");
		}
//		money1.setText(tokenList.get(1).getMoney()+"");
//		money2.setText(tokenList.get(2).getMoney()+"");
//		money3.setText(tokenList.get(3).getMoney()+"");
	}
	
	public void jumpAnimation(Token token, int hops) {
		for (int i = 0; i<hops;i++) {
			token.setCellNumber(token.getCellNumber()+1);
			if ( token.getCellNumber()==cellList.size()) {token.setCellNumber(0); token.setMoney(token.getMoney()+200); updateMoney();}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void propertyCase(Token token, JPanel optionPanel) {
		if (cellList.get(token.getCellNumber()).getColor().equals(Color.black)) {
			optionPanel.setVisible(true);
		} else {
			token.setMoney(token.getMoney()-priceList.get(token.getCellNumber())[1]);
			for (Token propietary:tokenList) {
				if (cellList.get(token.getCellNumber()).getColor().equals(propietary.getColor())) {
					propietary.setMoney(propietary.getMoney()+priceList.get(token.getCellNumber())[1]);
				}
			}
		}
		updateMoney();
	}
	
	public void trainCase(Token token, JPanel optionPanel) {
		if (cellList.get(token.getCellNumber()).getColor().equals(Color.black)) {
			optionPanel.setVisible(true);
		} else {

			for (Token propietary:tokenList) {
				if (cellList.get(token.getCellNumber()).getColor().equals(propietary.getColor())) {
					int trainCounter=0;
					for (Cell cell:cellList) {
						if(cell.getcType().equals(CellType.Train) && cell.getColor().equals(propietary.getColor())) {
							trainCounter++;
						}
					}
					token.setMoney(token.getMoney()-priceList.get(token.getCellNumber())[1] *(int)(Math.pow(2, trainCounter-1)));
					propietary.setMoney(propietary.getMoney()+priceList.get(token.getCellNumber())[1] *(int)(Math.pow(2, trainCounter-1)));
				}
			}
		}
		updateMoney();
	}
	
	public void taxCase(Token token) {
		if (token.getCellNumber()==4) {
			token.setMoney(token.getMoney()-200);
		} else {
			token.setMoney(token.getMoney()-100);
		}
		updateMoney();
	}
	
	public void utilityCase(Token token, JPanel optionPanel, JPanel utilityPanel) {
		if (cellList.get(token.getCellNumber()).getColor().equals(Color.black)) {
			optionPanel.setVisible(true);
		} else {
			utilityPanel.setVisible(true);
		}
	}
	
	public void gojailCase(Token token) {
		token.setCellNumber(10);
		token.setInJail(true);
	}
	
	public void chestCase(Token token) {
		int cardNum = dice.nextInt(1,9);
//		int cardNum = 6;
		switch (cardNum) {
		case 1: {
			infoText.setText("You advance to Go (Collect 200)");
			int jumpNum = 40-token.getCellNumber();;
			jumpAnimation(token, jumpNum);
			break;
		}
		case 2: {
			infoText.setText("Bank error in your favor. Collect 200");
			token.setMoney(token.getMoney()+200);
			break;
		}
		case 3: {
			infoText.setText("Doctor's fee. Pay 50");
			token.setMoney(token.getMoney()-50);
			break;
		}
		case 4: {
			infoText.setText("From sale of stock you get 50");
			token.setMoney(token.getMoney()+50);
			break;
		}
		case 5: {
			infoText.setText("Get out of Jail Free");
			break;
		}
		case 6: {
			infoText.setText("Go to Jail. Go directly to jail");
			gojailCase(token);
			break;
		}
		case 7: {
			infoText.setText("Holiday fund matures. Receive 100");
			token.setMoney(token.getMoney()+100);
			break;
		}
		case 8: {
			infoText.setText("Income tax refund. Collect 20");
			token.setMoney(token.getMoney()+20);
			break;
		}
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + cardNum);
		}
		updateMoney();
	}
	
	public void chanceCase(Token token, JPanel optionPanel, JPanel utilityPanel) {
		int cardNum = dice.nextInt(1,9);
//		int cardNum = 3;
		switch (cardNum) {
		case 1: {
			infoText.setText("Advance to Boardwalk");
			int jumpNum = 39-token.getCellNumber();
			jumpAnimation(token, jumpNum);
			cellMechanics(token, optionPanel, utilityPanel);
			break;
		}
		case 2: {
			infoText.setText("Advance to Go (Collect 200)");
			int jumpNum = 40-token.getCellNumber();
			jumpAnimation(token, jumpNum);
			break;
		}
		case 3: {
			infoText.setText("Advance to Illinois Avenue. If you Pass Go, collect 200");
			int jumpNum;
			if (24-token.getCellNumber()<0) {
			jumpNum = 24+40-token.getCellNumber();
			} else {
			jumpNum = 24-token.getCellNumber();
			}
			jumpAnimation(token, jumpNum);
			cellMechanics(token, optionPanel, utilityPanel);
			break;
		}
		case 4: {
			infoText.setText("Advance to St. Charles Place. If you Pass Go, collect 200");
			int jumpNum;
			if (11-token.getCellNumber()<0) {
			jumpNum = 11+40-token.getCellNumber();
			} else {
			jumpNum = 11-token.getCellNumber();
			}
			jumpAnimation(token, jumpNum);
			cellMechanics(token, optionPanel, utilityPanel);
			break;
		}
		case 5: {
			infoText.setText("Get out of Jail Free");
			break;
		}
		case 6: {
			infoText.setText("Advance to the nearest Railroad. if unowned, you may buy it from the Bank. If owned, pay the rental");
			if (token.getCellNumber()<5) {
				int jumpNum = 5-token.getCellNumber();
				jumpAnimation(token, jumpNum);
			} else if (token.getCellNumber()<15) {
				int jumpNum = 15-token.getCellNumber();
				jumpAnimation(token, jumpNum);
			} else if (token.getCellNumber()<25) {
				int jumpNum = 25-token.getCellNumber();
				jumpAnimation(token, jumpNum);
			} else if (token.getCellNumber()<35) {
				int jumpNum = 35-token.getCellNumber();
				jumpAnimation(token, jumpNum);
				cellMechanics(token, optionPanel, utilityPanel);
			}
			break;
		}
		case 7: {
			infoText.setText("Advance to the nearest Utility. if unowned, you may buy it from the Bank. If owned, pay the rental");
			if (token.getCellNumber()<12) {
				int jumpNum = 12-token.getCellNumber();
				jumpAnimation(token, jumpNum);
			} else if (token.getCellNumber()<28) {
				int jumpNum = 28-token.getCellNumber();
				jumpAnimation(token, jumpNum);
				cellMechanics(token, optionPanel, utilityPanel);
			}
			break;
		}
		case 8: {
			infoText.setText("Speeding fine, pay 15");
			token.setMoney(token.getMoney()-15);
			break;
		}
		
		default:
			throw new IllegalArgumentException("Unexpected value: " + cardNum);
		}
		updateMoney();
	}
	
	

}
