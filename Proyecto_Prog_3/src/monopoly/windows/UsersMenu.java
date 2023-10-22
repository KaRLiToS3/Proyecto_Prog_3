package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class UsersMenu extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Boolean that is used to let the window know which window
	//has to be generated when it is closed
	private boolean createUserWindow;
	
	public UsersMenu(){
		
		createUserWindow = false;
		
		Font UserFont = new Font("Arial Black", Font.BOLD, 24);
		Font TextFont = new Font("Arial Black", Font.ITALIC, 12);
		
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
		JPanel SEARCH = new JPanel();
		JPanel BUTTONS = new JPanel();
		
		add(N, BorderLayout.NORTH);
		add(C, BorderLayout.CENTER);
		add(S, BorderLayout.SOUTH);
		
		
		//UP
		JLabel TextUser = new JLabel("USERS");
		TextUser.setFont(UserFont);
		N.add(TextUser);
		
		//CENTER
		
		//DOWN
		S.setLayout(new FlowLayout());
		S.add(SEARCH);
		JLabel TextSearch = new JLabel("SEARCH USER: ");
		TextSearch.setFont(TextFont);
		JTextField SearchUser = new JTextField(20);
		SEARCH.add(TextSearch);
		SEARCH.add(SearchUser);
		S.add(BUTTONS);
		BUTTONS.setLayout(new GridLayout(1,2));
		JButton CreateUser = new JButton("Create User");
		JButton DeleteUser = new JButton("Delete User");
		BUTTONS.add(CreateUser);
		BUTTONS.add(DeleteUser);
		
		//EVENTS
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				SwingUtilities.invokeLater(() -> {
					if (createUserWindow) {
						dispose();
					} else {
						new MainMenu();
						dispose();
					}		
				});
			}
		});
		
		CreateUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					new CreateUser();
					createUserWindow = true;
					dispose();
				});	
			}
		});
	}
}