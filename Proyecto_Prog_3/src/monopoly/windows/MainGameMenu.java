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
import java.util.ArrayList;
import java.util.List;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import monopoly.objects.Token;

public class MainGameMenu extends MasterFrame {


	private static final long serialVersionUID = 1L;
	
private final URL boardPath = getClass().getResource("/monopoly/images/board_monopoly.png");
private final URL dicePath = getClass().getResource("/monopoly/images/dice.png");
//	private static final String boardPath = "../images/board_monopoly.png";
//	private static final String dicePath = "../images/dice.png";
	private static final Point[] positionList = {new Point(599,626),new Point(543,643),new Point(487,631),new Point(434,645),new Point(377,635),new Point(322,637),new Point(267,649),new Point(212,638),new Point(162,653),new Point(109,644),new Point(18,622),new Point(21,568),new Point(22,514),new Point(24,461),new Point(22,405),new Point(21,351),new Point(24,295),new Point(27,239),new Point(25,184),new Point(28,137),new Point(32,57),new Point(107,52),new Point(162,59),new Point(216,56),new Point(269,55),new Point(323,60),new Point(379,60),new Point(433,62),new Point(484,59),new Point(543,58),new Point(599,52),new Point(616,134),new Point(614,186),new Point(597,239),new Point(618,291),new Point(600,349),new Point(596,405),new Point(620,458),new Point(618,565)};
	// Token position setter
//	private static List<Point> posList = new ArrayList<>();

	
	private List<Token> tokenList = new ArrayList<>();

	public static void main(String[] args) {
	}
	
	public MainGameMenu() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000,700);
		setMinimumSize(new Dimension(1000, 700));
		setLocationRelativeTo(null);
		setTitle("MONOPOLY");
				
		// PANEL FOR MAIN DISTRIBUTION
		 
		setLayout(new BorderLayout());
		
		// PANEL FOR BOARD
		
		JPanel boardPanel = new PanelImageBuilder(boardPath, 0.7, 1, true) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				for (Token token : tokenList) {
					token.paintComponent(g);
					repaint();
				}
				
				
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
		
		diceButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				tokenList.get(0).setX(tokenList.get(0).getX()+100);
				// Token position setter
//				String str = "{";
//				for (Point p : posList) {
//					str = str + "new Point("+(int)p.getX()+","+(int)p.getY()+"),";
//				}
//				str=str+"}";
//			
//				System.out.println(str);
			}
		});

		addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Token position setter
//				posList.add(getMousePosition());
				System.out.println(getMousePosition());
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
			}
		});
		
		setVisible(true);
		
		//try tokens	
		Insets insets = getInsets();
		for (Point p : positionList) {
			tokenList.add(new Token((int)p.getX()-insets.left, (int)p.getY()-insets.top, Color.RED));
		}
	}
	@Override
	public String windowName() {
		return MasterFrame.MainGameMenu;
	}
}
