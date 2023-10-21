package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainGameMenu extends MasterFrame {
	
	private static final String boardPath = "../images/board_monopoly.png";
	private static final String dicePath = "../images/dice.png";



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		new MainGameMenu();
	}
	
	public MainGameMenu() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000,700);
		setLocationRelativeTo(null);
		setTitle("MONOPOLY");
		
		// PANEL FOR MAIN DISTRIBUTION
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.add(mainPanel);
		
		// PANEL FOR BOARD
		
		JPanel boardPanel = new PanelImageBuilder(boardPath, 0.3);
		mainPanel.add(boardPanel, BorderLayout.CENTER);
		
		// PANEL FOR EVENTS
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout( new BorderLayout() );
		mainPanel.add(eventPanel, BorderLayout.EAST);
		eventPanel.setPreferredSize(getPreferredSize());
		
		JPanel sEventPanel = new JPanel();
		eventPanel.add(sEventPanel, BorderLayout.SOUTH);
		
		
		
		// DICE BUTTON
		
		JButton diceButton = new JButton(getIconifiedImage(dicePath, 100, 100));
		sEventPanel.add(diceButton);
		diceButton.setAlignmentX(LEFT_ALIGNMENT);
		diceButton.setBackground(Color.BLACK);
		diceButton.setPreferredSize(new Dimension(100, 80));
		
		
		setVisible(true);
	}
}
