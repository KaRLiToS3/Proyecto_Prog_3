package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Font;

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
		
		add(N, BorderLayout.NORTH);
		add(W, BorderLayout.WEST);
		add(C, BorderLayout.CENTER);
		add(E, BorderLayout.EAST);
		add(S, BorderLayout.SOUTH);
		
		JLabel title = new JLabel("MONOPOLY GAME");
		title.setFont(mainT);
		
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
