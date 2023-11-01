package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Ellipse2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MainGameMenu extends MasterFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String boardPath = "../images/board_monopoly.png";
	private static final String dicePath = "../images/dice.png";
	
	
	
	public static void main(String[] args) {
		new MainGameMenu();
	}
	
	public MainGameMenu() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000,700);
		setMinimumSize(new Dimension(1000, 700));
		setLocationRelativeTo(null);
		setTitle("MONOPOLY");
		
		// PANEL FOR MAIN DISTRIBUTION
		 
//		setLayout(new BorderLayout());
		
		// LAYERED PANEL
		JLayeredPane layeredPanel = new JLayeredPane();
		add(layeredPanel);
		
		// PANEL FOR BOARD
		
		JPanel boardPanel = new PanelImageBuilder(boardPath, 0.7, 1, false); //{
//
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void setBounds(int x, int y, int width, int height) {
//				
//				Dimension panelDim;
//				Dimension windowDim;
//				try {
//					windowDim = new Dimension(getMainWindowDimension());
//					panelDim = new Dimension(this.getWidth(), this.getHeight());
//					System.out.println(panelDim);
//					
//					if (panelDim.getHeight()/windowDim.getWidth() < 0.7) {
//						super.setBounds(0, 0, this.getHeight(), (int)(this.getHeight()*0.7));
//					} else {
//						super.setBounds(0, 0, (int)(windowDim.getWidth()*0.7), this.getHeight());
//					}
//					
//				} catch (ClassNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//			
//		};
//		JPanel boardPanel = new JPanel();
		
		boardPanel.setBounds(0,0,(int)(this.getWidth()*0.7),this.getHeight());
		layeredPanel.add(boardPanel, JLayeredPane.DEFAULT_LAYER);
//		add(boardPanel);
		
		
		// PANEL FOR EVENTS
//		JPanel eventPanel = new JPanel();
//		eventPanel.setLayout( new BorderLayout() );
//		add(eventPanel, BorderLayout.CENTER);
//		eventPanel.setPreferredSize(new Dimension((int) (this.getWidth()*0.3), HEIGHT));
//
//		eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
//		eventPanel.setBackground(Color.BLACK);
		
		// DICE BUTTON
//		eventPanel.add(new Box.Filler(new Dimension(100, 100), null, null));

//		JButton diceButton = new JButton(getIconifiedImage(dicePath, 100, 100));
//		diceButton.setAlignmentX(CENTER_ALIGNMENT);
//		diceButton.setBackground(Color.WHITE);
//		eventPanel.add(diceButton);
//		setComponentDimension(diceButton, 100, 80);
		
		// PANEL FOR TOKENS
//		add(cdp);
		
		// TOKEN CREATION
		
//		diceButton.addActionListener( new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Token t1 = new Token();
//				cdp.addToken(t1);				
//			}
//		});
		
//		Token t1 = new Token(100, 100, Color.black);
//		Token t2 = new Token(400, 300, Color.green);
//		CustomDrawingPanel cdp = new CustomDrawingPanel();
//		cdp.setBounds(new Rectangle(999999, 999999));
//		cdp.addToken(t1);
//		cdp.addToken(t2);
		
//		layeredPanel.add(cdp, JLayeredPane.DEFAULT_LAYER);
//		Timer timer = new Timer(10000, e->{revalidate();repaint();});
//		timer.start();
		
		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Dimension panelDim;
				Dimension windowDim;
				try {
					windowDim = new Dimension(getMainWindowDimension());
					panelDim = new Dimension(getWidth(), getHeight());
					System.out.println(panelDim);
					
					if (panelDim.getHeight()/windowDim.getWidth() > 0.7) {
						boardPanel.setBounds(0, 0, getHeight(), (int)(getHeight()*0.7));
					} else {
						boardPanel.setBounds(0, 0, (int)(windowDim.getWidth()*0.7), getHeight());
					}
					
				} catch (ClassNotFoundException t) {
					t.printStackTrace();
				}
			}
		});

		
		setVisible(true);
	}

//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		try {
//			 Image img  = MasterFrame.loadImageIcon(boardPath).getImage();
//			 g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();		
//		}
//	}

	class CustomDrawingPanel extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public List<Token> tokenList;
		
		public CustomDrawingPanel() {
			tokenList = new ArrayList<>();
			setOpaque(false);
		}
		
		public void addToken(Token token) {
			tokenList.add(token);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			
			for (Token token : tokenList) {
	            g2d.setPaint(token.getColor());
	            int diameter = Token.radius * 2;
	            Ellipse2D.Double circle = new Ellipse2D.Double(token.getX(), token.getY(), diameter, diameter);
	            g2d.fill(circle);
	        }
		}
	}


}
