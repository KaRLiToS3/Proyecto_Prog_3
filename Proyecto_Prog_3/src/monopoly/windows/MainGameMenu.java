package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

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
		setMinimumSize(new Dimension(1000, 700));
		setLocationRelativeTo(null);
		setTitle("MONOPOLY");
		
		// PANEL FOR MAIN DISTRIBUTION
		 
		setLayout(new BorderLayout());
		
		// PANEL FOR BOARD
		
		JPanel boardPanel = new PanelImageBuilder(boardPath, 0.7, 1, true);
//		boardPanel.setBackground(Color.BLACK);
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
				Token t1 = new Token();
				add(t1);				
			}
		});
		
		setVisible(true);
	}



}
