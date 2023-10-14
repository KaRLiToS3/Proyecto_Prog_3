package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
		JPanel HEADERS = new JPanel();
		JPanel FIELDS = new JPanel();
		
		add(N, BorderLayout.NORTH);
		add(C, BorderLayout.CENTER);
		add(S, BorderLayout.SOUTH);
		
		//CENTER-RIGHT
		
		//I want to use the data that the user gives me, so i use a Map in order to later
		//identified the JTextField that i need to use.
		
		String[] HEADERSNAMES = {"NAME:","EMAIL:","PASSWORD:"};
		Map<String, JTextField> textFieldMap = new HashMap<>();
		
		C.add(CR, BorderLayout.EAST);
		CR.setLayout(new GridLayout(1,2));
		CR.add(HEADERS, BorderLayout.WEST);
		CR.add(FIELDS, BorderLayout.EAST);
		HEADERS.setLayout(new GridLayout(HEADERSNAMES.length,2));
		FIELDS.setLayout(new GridLayout(HEADERSNAMES.length,1));
		
		for (String elem: HEADERSNAMES) {
			JLabel Name = new JLabel(elem);
			JTextField Field = new JTextField(20);
			
			//Storage the JTextField in the map in order to use it later
			textFieldMap.put(elem, Field);
			HEADERS.add(Name);
			FIELDS.add(Field);
		}
		
		
		//CENTER-LEFT
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
