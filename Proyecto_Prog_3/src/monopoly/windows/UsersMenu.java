package monopoly.windows;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class UsersMenu extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		//TEST USERSMENU
		public static void main(String[] args) {
			SwingUtilities.invokeLater(() -> new UsersMenu());
			
		}
	
	public UsersMenu(){
		//LOOK AND FEEL SETUP
		setUpLookAndFeel();
		
		//GENERAL WINDOW SETTINGS
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
		setTitle("USERS");
		setLayout(new BorderLayout());
		setVisible(true);
		
		//DECLARATION OF COMPONENTS
		JPanel N = new JPanel();
		JPanel C = new JPanel();
		JPanel S = new JPanel();
		JPanel CR = new JPanel();
		JPanel CL = new JPanel();
		
		add(N, BorderLayout.NORTH);
		add(C, BorderLayout.CENTER);
		add(S, BorderLayout.SOUTH);
		C.add(CR, BorderLayout.EAST);
		C.add(CL, BorderLayout.WEST);
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
