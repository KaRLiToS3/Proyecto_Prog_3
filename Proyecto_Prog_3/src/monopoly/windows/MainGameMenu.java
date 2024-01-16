package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.net.URL;
import java.nio.file.Paths;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.ui.tabbedui.VerticalLayout;

import monopoly.data.DataManager;
import monopoly.objects.Achievement;
import monopoly.objects.Cell;
import monopoly.objects.Cell.CellType;
import monopoly.objects.Match;
import monopoly.objects.Token;
import monopoly.objects.User;

public class MainGameMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	
	private static Font font1 = new Font("Arial Black", Font.BOLD, 24);
	private static Font font2 = new Font("Arial Black", Font.PLAIN, 16);
	private static Font font3 = new Font("Arial Black", Font.PLAIN, 12);
	private static Font buttonFont = new Font("Dubai", Font.BOLD,  18);
	private static final Color gold = new Color(212, 175, 55);
	
	private final URL boardPath = getClass().getResource(getStringProperty("board_img"));
	private final URL dicePath = getClass().getResource(getStringProperty("dice_img"));
	public static final Dimension defaultWindowDimension = getDimensionProperty("mainGameMenuSizeX", "mainGameMenuSizeY");
	private static String cellPositionsPath = Paths.get(getStringProperty("cellPositions")).toAbsolutePath().toString();
	private static String cellPricesPath = Paths.get(getStringProperty("cellPrices")).toAbsolutePath().toString();
	private static DataManager dataManager = DataManager.getManager();
	

	private static List<Cell> cellList = new ArrayList<>();
	private static List<Token> tokenList = new ArrayList<>();
	private static Map<Integer, Integer[]> priceList = new HashMap<>();
	private static final Color[] defaultColors = {Color.red,Color.green,Color.blue,Color.yellow,};
	private static final List<User> gameUsers = new ArrayList<>(monopoly.windows.GameSettingsMenu.getSelectedUsers());
	private static List<String> userNames = new ArrayList<>();
	
	private static int turn = -1;
	private static int fullSpin = 0;
	private static Color turnColor;
	private Random dice = new Random();
	
	private JButton buyButton = new JButton("Buy");
	private JButton utilityDice = new JButton("Roll!");
	private List<JLabel> moneyLabels = new ArrayList<>();
	private JPanel moneyPanel = new JPanel();
	private JTextArea infoText = new JTextArea();		
	private JLabel turnLabel = new JLabel();
	private DefaultListModel<String> optionModel = new DefaultListModel<>();
	private JButton diceButton = new JButton(getIconifiedImage(dicePath, 100, 100));

	
	private final static int maxWidth = 2000;
// tener en cuenta que si luego ponemos que la ventana se reinicie (la partida) hay que meterlos en el constructor MainGameMenu()
	private static Map<String, TreeMap<Integer,Integer>> turnCurrencyPerUser = new HashMap<String, TreeMap<Integer,Integer>>();
		
	
	public MainGameMenu() {
		logger.log(Level.INFO, "Initializing MainGameMenu");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(defaultWindowDimension);
		setMinimumSize(defaultWindowDimension);
		setDefaultWindowIcon();
		setLocationRelativeTo(null);
		setTitle("MONOPOLY");
		
		for (User u:gameUsers) {
			turnCurrencyPerUser.put(u.getEmail(), new TreeMap<>());
			userNames.add(u.getAlias());
		}

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
		turnLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		eventPanel.add(turnLabel);
		
		//INFORMATION AREA
		infoText.setEditable(false);
		infoText.setBackground(Color.black);
		infoText.setForeground(Color.white);
		infoText.setFont(font2);
		infoText.setLineWrap(true);
		infoText.setWrapStyleWord(true);

		setComponentDimension(infoText, maxWidth, 300);
		infoText.setText("Roll the dice to start the game!");

		
		eventPanel.add(infoText);
		
		//BUYIN BUTTON
		buyButton.setFont(buttonFont);
		buyButton.setAlignmentX(CENTER_ALIGNMENT);
		buyButton.setBackground(gold);
		setComponentDimension(buyButton, maxWidth, 70);

		eventPanel.add(buyButton);
		buyButton.setEnabled(false);
		
		// UTILITY BUTTON
		utilityDice.setFont(font2);
		utilityDice.setVisible(false);
		setComponentDimension(utilityDice, 100, 80);

		eventPanel.add(utilityDice);
				
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
		properties.setFont(font1);
		properties.setAlignmentX(CENTER_ALIGNMENT);
		JList<String> propertySelector = new JList<String>(optionModel);
		propertySelector.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		propertySelector.setFont(font3);
		propertySelector.setBackground(Color.white);
		JButton sellButton = new JButton("Sell");
		sellButton.setFont(buttonFont);
		sellButton.setEnabled(false);
		sellButton.setAlignmentX(CENTER_ALIGNMENT);
		sellButton.setBackground(gold);
		setComponentDimension(sellButton, maxWidth, 70);
		
		JScrollPane scrollpane = new JScrollPane(propertySelector,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setComponentDimension(scrollpane, maxWidth, 200);
		
		eventPanel.add(properties);
		eventPanel.add(scrollpane);
		eventPanel.add(sellButton);
		
		// SPACER
		eventPanel.add(new Box.Filler(new Dimension(100, 100), null, null));
		
		//MONEY DISPLAY
		JLabel money = new JLabel("Money");
		money.setForeground(Color.white);
		money.setBackground(Color.gray);
		money.setFont(font1);
		money.setAlignmentX(CENTER_ALIGNMENT);

		moneyPanel.setLayout(new GridLayout(2, 2));
		moneyPanel.setBackground(Color.black);
		setComponentDimension(moneyPanel, maxWidth, 200);
		
		eventPanel.add(money);
		eventPanel.add(moneyPanel);
		
		//DICE PANEL
		JPanel dicePanel = new JPanel();
		dicePanel.setLayout(new FlowLayout());
		dicePanel.setAlignmentX(CENTER_ALIGNMENT);
		dicePanel.setBackground(Color.black);
		setComponentDimension(dicePanel, maxWidth, 110);
		
		eventPanel.add(dicePanel);
		
		//DICE RESULT
		JLabel diceResult = new JLabel();
		diceResult.setForeground(Color.white);
		diceResult.setFont(font1);
		setComponentDimension(diceResult, 40, 80);
		
		dicePanel.add(diceResult);
		
		//DICE BUTTON
		diceButton.setBackground(Color.white);
		setComponentDimension(diceButton, 100, 80);
		
		dicePanel.add(diceButton);


		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
			}
		});

		setVisible(true);

		
		buyButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Token t = tokenList.get(turn);
				cellList.get(t.getCellNumber()).setColor(t.getColor());
				t.modifyMoney(-priceList.get(t.getCellNumber())[0]);
				updatePropertyList(t);
				buyButton.setEnabled(false);
				infoText.setText("You can now sell your properties, in case you have, or hit the dice to switch turns");
				updateMoney();
			}
		});
		
		utilityDice.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
						if (utilityCounter==1) {
							t.modifyMoney(-result*4);
							propietary.modifyMoney(result*4);
							infoText.setText("You rolled");
						} else {
							t.modifyMoney(-result*10);
							propietary.modifyMoney(result*10);	
						}

					}
				}
				utilityDice.setVisible(false);
				updatePropertyList(t);
				updateMoney();
			}
		});
		
		jailPayButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Token t = tokenList.get(turn);
				t.modifyMoney(-50);
				t.setInJail(false);
				diceButton.setEnabled(true);
				jailPanel.setVisible(false);
				diceButton.setEnabled(true);
				infoText.setText("Your next turn will be normal. You can now sell your properties, if you have, or hit the dice to switch turns");
				updateMoney();
			}
		});
		
		jailWaitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Token t = tokenList.get(turn);
				t.setJailTurnCounter(t.getJailTurnCounter()-1);
				diceButton.setEnabled(true);
				jailPanel.setVisible(false);
				diceButton.setEnabled(true);
				infoText.setText("You can now sell your properties, in case you have, or hit the dice to switch turns");
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
				t.modifyMoney((int)(priceList.get(selectionCellNumber)[0]*0.5));;
				updatePropertyList(t);
				sellButton.setEnabled(false);
				updateMoney();
			}
		});
		
		diceButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// prepare display for next turn
				buyButton.setEnabled(false);
				infoText.setText("");
				nextTurn();
				diceButton.setEnabled(false);
				propertySelector.clearSelection();
				sellButton.setEnabled(false);
				// get the current token turn and check if in jail
				Token token = tokenList.get(turn);
				updatePropertyList(token);
				if (token.isInJail() && token.getJailTurnCounter()>0) {
					//if in jail
					diceButton.setEnabled(false);
					jailPanel.setVisible(true);
					infoText.setText("You are in jail, so you can now pay 50 to get out and continue in the next turn, or wait another "+token.getJailTurnCounter()+" turns inside");
				} else {
					// if out of jail
					token.setInJail(false);
					token.setJailTurnCounter(3);
					int hops = dice.nextInt(1, 13);
//					int hops = 1;
					diceResult.setText(""+hops);

					Thread t =  new Thread() {
						@Override
						public void run() {

							jumpAnimation(token, hops);
							
							cellMechanics(token);
							if (tokenList.size()!=1) diceButton.setEnabled(true);					
						}
					};
					t.start();
				}
			}
		});

		loadPrices();
		loadCellPositions(boardPanel);
		loadTokens(boardPanel);

		updateMoney();
	}
	
	// TODO revisar que si cogemos la proporcion de la posicion de los tokens respecto a las dimensiones del board panel o main window (teniendo en cuenta insets)
	@Override
	public String windowName() {
		return MasterFrame.MainGameMenu;
	}

	public void loadCellPositions(JPanel panel) {
		logger.log(Level.INFO, "Loading cells");
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
	
	public void loadTokens(JPanel panel) {
		logger.log(Level.INFO, "Loading tokens");
		for (int i=0;i<gameUsers.size();i++) {
			tokenList.add(new Token(defaultColors[i], panel, monopoly.windows.GameSettingsMenu.getStartingCash(), gameUsers.get(i).getEmail()));
			moneyLabels.add(new JLabel());
			moneyLabels.get(i).setForeground(tokenList.get(i).getColor());
			moneyLabels.get(i).setFont(font1);
			moneyLabels.get(i).setHorizontalAlignment(SwingConstants.CENTER);
			moneyPanel.add(moneyLabels.get(i));
		}
	}
	
	public void loadPrices() {
		logger.log(Level.INFO, "Loading cell prices");
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
		if (turn==tokenList.size()) turn = 0; fullSpin++;
		turnColor=tokenList.get(turn).getColor();
		turnLabel.setForeground(turnColor);
		turnLabel.setText("Turn of "+userNames.get(turn));
		storeStatistics();
	}
	
	public void storeStatistics() {
		for (Token token:tokenList) {
			turnCurrencyPerUser.get(token.getUserEmail()).put(fullSpin, token.getMoney());
		}
	}
	
	public void updatePropertyList(Token token) {
		optionModel.clear();
		for (Cell cell:cellList) {
			if(cell.getColor().equals(token.getColor())) {
				optionModel.addElement(cell.getName()+", Selling Price: "+(int)(priceList.get(cell.getCellNumber())[0]*0.5)+", Rent: "+priceList.get(cell.getCellNumber())[1]+", Cell Number: "+cell.getCellNumber());
			}
		}
	}
	
	public void updateMoney() {
		for (int i=tokenList.size()-1;i>=0;i--) {
			moneyLabels.get(i).setText(tokenList.get(i).getMoney()+"");
			// losing mechanic
			if (tokenList.get(i).getMoney()<=0) {
				infoText.setText("You lost all your money! You are out of the game now!");
				for (Cell c:cellList) {
					if (tokenList.get(i).getColor().equals(c.getColor())) {
						c.setColor(Color.black);
					}
				}
				updatePropertyList(tokenList.get(i));
				tokenList.remove(tokenList.get(i));
				userNames.remove(i);
				moneyLabels.get(i).setForeground(Color.black);
				moneyLabels.remove(i);
				turn--;
			}
		}
		if (tokenList.size()==1) {
			infoText.setText("Looks like we have got a winner!");
			logger.log(Level.INFO, "Saving match");
			dataManager.saveMatch(new Match(new Date(), monopoly.windows.GameSettingsMenu.getMatchName(), turnCurrencyPerUser));
			assignAchievements();
			optionModel.clear();
			diceButton.setEnabled(false);
			
		}
	}
	
	public void assignAchievements() {
		logger.log(Level.INFO, "Assigning achivements");
		for (User u:gameUsers) {
			if (u.getEmail().equals(tokenList.get(0).getUserEmail())) {
				u.addAchievement(new Achievement(Achievement.Type.MVP));
				u.addAchievement(new Achievement(Achievement.Type.CHEAPSKATE));
				u.addAchievement(new Achievement(Achievement.Type.IMPERIALIST));
			} else {
				u.addAchievement(new Achievement(Achievement.Type.FLAT_BROKE));
			}
			int modestprobability = dice.nextInt(1, 5);
			if (modestprobability==1) u.addAchievement(new Achievement(Achievement.Type.MODEST));
			turnCurrencyPerUser.get(u.getEmail());
		}
		

	}
	
	public void cellMechanics(Token token) {
		switch (cellList.get(token.getCellNumber()).getcType()) {
		case Property: {
			propertyCase(token);
			break;
		}
		case Train: {
			trainCase(token);
			break;
		}
		case Tax: {
			taxCase(token);
			break;
		}
		case Free: {
			infoText.setText("Free parking! You can now sell your properties, in case you have, or hit the dice to switch turns");
			break;
		}
		case Start: {
			break;
		}
		case Gojail: {
			gojailCase(token);
			break;
		}
		case Jail: {
			infoText.setText("Just visiting. You can now sell your properties, in case you have, or hit the dice to switch turns");
			break;
		}
		case Utility: {
			utilityCase(token);
			break;
		}
		case Chest: {
			chestCase(token);
			break;
		}
		case Chance: {
			chanceCase(token);
			break;
		}
		default:
//			throw new IllegalArgumentException("Unexpected value: " + cellList.get(t.getCellNumber()).getcType());
			System.out.println("not there"+ cellList.get(token.getCellNumber()).getcType());
		}
	}
	
	
	public void jumpAnimation(Token token, int hops) {
		try {
			for (int i = 0; i<hops;i++) {
				token.setCellNumber(token.getCellNumber()+1);
				if ( token.getCellNumber()==cellList.size()) {
					token.setCellNumber(0); 
					token.modifyMoney(200); 
					infoText.setText("You get 200 for passing Go!");
					updateMoney();
				}
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {

		}
	}
	
	public void propertyCase(Token token) {
		if (cellList.get(token.getCellNumber()).getColor().equals(Color.black)) {
			infoText.setText("Now you decide to buy the property for "+priceList.get(token.getCellNumber())[0]+", or you can sell your properties, in case you have, or hit the dice to switch turns");
			buyButton.setEnabled(true);
		} else {
			for (Token propietary:tokenList) {
				if (cellList.get(token.getCellNumber()).getColor().equals(propietary.getColor())) {
					int rent = priceList.get(token.getCellNumber())[1];
					token.modifyMoney(-rent);
					propietary.modifyMoney(rent);
					infoText.setText("You paid "+rent+" for rent. You can now sell your properties, in case you have, or hit the dice to switch turns");
				}
			}
		}
		updateMoney();
	}
	
	public void trainCase(Token token) {
		if (cellList.get(token.getCellNumber()).getColor().equals(Color.black)) {
			infoText.setText("Now you decide to buy the train station for "+priceList.get(token.getCellNumber())[0]+", or you can sell your properties, in case you have, or hit the dice to switch turns");
			buyButton.setEnabled(true);
		} else {

			for (Token propietary:tokenList) {
				if (cellList.get(token.getCellNumber()).getColor().equals(propietary.getColor())) {
					int trainCounter=0;
					for (Cell cell:cellList) {
						if(cell.getcType().equals(CellType.Train) && cell.getColor().equals(propietary.getColor())) {
							trainCounter++;
						}
					}
					int rent = priceList.get(token.getCellNumber())[1] *(int)(Math.pow(2, trainCounter-1));
					token.modifyMoney(-rent);
					propietary.modifyMoney(rent);
					infoText.setText("You paid "+rent+" for rent. You can now sell your properties, in case you have, or hit the dice to switch turns");
				}
			}
		}
		updateMoney();
	}
	
	public void taxCase(Token token) {
		if (token.getCellNumber()==4) {
			token.modifyMoney(-200);
			infoText.setText("You pay 200 for tax");
		} else {
			token.modifyMoney(-100);
			infoText.setText("You pay 100 for tax");
		}
		updateMoney();
	}
	
	public void utilityCase(Token token) {
		if (cellList.get(token.getCellNumber()).getColor().equals(Color.black)) {
			infoText.setText("Now you decide to buy the utility for "+priceList.get(token.getCellNumber())[0]+", or you can sell your properties, in case you have, or hit the dice to switch turns");
			buyButton.setEnabled(true);
		} else {
			utilityDice.setVisible(true);
		}
	}
	
	public void gojailCase(Token token) {
		infoText.setText("You got caught! You are now in jail!");
		token.setCellNumber(10);
		token.setInJail(true);
	}
	
	public void chestCase(Token token) {
		int cardNum = dice.nextInt(1,8);
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
			token.modifyMoney(200);
			break;
		}
		case 3: {
			infoText.setText("Doctor's fee. Pay 50");
			token.modifyMoney(-50);
			break;
		}
		case 4: {
			infoText.setText("From sale of stock you get 50");
			token.modifyMoney(50);
			break;
		}
		case 5: {
			infoText.setText("Go to Jail. Go directly to jail");
			gojailCase(token);
			break;
		}
		case 6: {
			infoText.setText("Holiday fund matures. Receive 100");
			token.modifyMoney(100);
			break;
		}
		case 7: {
			infoText.setText("Income tax refund. Collect 20");
			token.modifyMoney(20);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + cardNum);
		}
		updateMoney();
	}
	
	public void chanceCase(Token token) {
		int cardNum = dice.nextInt(1,8);
//		int cardNum = 5;
		switch (cardNum) {
		case 1: {
			infoText.setText("Advance to Boardwalk");
			int jumpNum = 39-token.getCellNumber();
			jumpAnimation(token, jumpNum);
			cellMechanics(token);
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
			cellMechanics(token);
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
			cellMechanics(token);
			break;
		}
		case 5: {
			infoText.setText("Advance to the nearest Railroad. if unowned, you may buy it from the Bank. If owned, pay the rental");
			int jumpNum = 0;
			if (token.getCellNumber()<5) {
				jumpNum = 5-token.getCellNumber();
			} else if (token.getCellNumber()<15) {
				jumpNum = 15-token.getCellNumber();
			} else if (token.getCellNumber()<25) {
				jumpNum = 25-token.getCellNumber();
			} else if (token.getCellNumber()<35) {
				jumpNum = 35-token.getCellNumber();
			}
			jumpAnimation(token, jumpNum);
			cellMechanics(token);
			break;
		}
		case 6: {
			infoText.setText("Advance to the nearest Utility. if unowned, you may buy it from the Bank. If owned, pay the rental");
			if (token.getCellNumber()<12) {
				int jumpNum = 12-token.getCellNumber();
				jumpAnimation(token, jumpNum);
				cellMechanics(token);
			} else if (token.getCellNumber()<28) {
				int jumpNum = 28-token.getCellNumber();
				jumpAnimation(token, jumpNum);
				cellMechanics(token);
			}
			break;
		}
		case 7: {
			infoText.setText("Speeding fine, pay 15");
			token.modifyMoney(-15);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + cardNum);
		}
		updateMoney();
	}
}
