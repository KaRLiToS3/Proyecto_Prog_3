package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class MainMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Font mainT = new Font("Arial Black", Font.BOLD,  24);
	
	//TEST MAIN
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainMenu());
		
	}
	
	public MainMenu() {
		//LOOK AND FEEL SETUP
		setUpLookAndFeel();
		
		//GENERAL WINDOW SETTINGS
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600,500);
		setLocationRelativeTo(null);
		setTitle("MONOPOLY");
		setLayout(new BorderLayout());
		
		//DECLARATION OF COMPONENTS
		JPanel N = new JPanel();
		JPanel W = new JPanel();
		JPanel C = new JPanel();
		JPanel E = new JPanel();
		JPanel S = new JPanel();
		
		C.setAlignmentX(Component.CENTER_ALIGNMENT);
		C.setLayout(new BoxLayout(C, BoxLayout.Y_AXIS));
		C.setAlignmentX(Component.CENTER_ALIGNMENT);
		S.setLayout(new BoxLayout(S, BoxLayout.X_AXIS));
		
		add(N, BorderLayout.NORTH);
		add(W, BorderLayout.WEST);
		add(C, BorderLayout.CENTER);
		add(E, BorderLayout.EAST);
		add(S, BorderLayout.SOUTH);
		
		JLabel title = new JLabel("MONOPOLY GAME");
		title.setFont(mainT);
		
		JButton[] buttons = new JButton[8];
		String[] bText = {"PLAY", "GAME SETTINGS", "USER ACHIEVEMENTS", "MATCH RECORD", "MANAGE USERS", "CREDITS", "HELP", "LEAVE GAME"};
		Dimension bMenu = new Dimension(200,60);
		for(int i = 0; i < 8; i++) {
			buttons[i] = new JButton(bText[i]);
			if(i <6) {
				C.add(buttons[i]);
				buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
				buttons[i].setPreferredSize(bMenu);
				buttons[i].setMinimumSize(bMenu);
				buttons[i].setMaximumSize(bMenu);
				C.add(new Box.Filler(new Dimension(1, 1), null, null));
			}else {
				S.add(buttons[i]);
				buttons[i].setAlignmentX(Component.LEFT_ALIGNMENT);
				if(i == 6) S.add(Box.createHorizontalGlue());
			}
		}
		
		//ADDING THE COMPONENTS TO THE PANELS
		N.add(title);
		
		setVisible(true);
	}
	
	/**
	 * This method searches for the predefined look and feel "Nimbus" 
	 */
	private void setUpLookAndFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            return;
		        }
		    }
		} catch (Exception e) {e.printStackTrace();}
	}
}
