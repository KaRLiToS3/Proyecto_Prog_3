package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import monopoly.objects.User;

public class UsersMenu extends MasterFrame{
	private static final long serialVersionUID = 1L;
	
//<<<<<<< UserWindows
	//Boolean that is used to let the window know which window
	//has to be generated when it is closed
	private boolean createUserWindow;
	public ArrayList<User> listUser = new User().loadUsers();
	
//=======
//>>>>>>> karlitosBranch
	public UsersMenu(){
		saveWindowReference("UsersMenu", this);
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
		//JTable model
		DefaultTableModel tableModel = new DefaultTableModel();
		String[] HEADERSNAMES = {"ALIAS:","NAME:","EMAIL:"};
		for (String values:HEADERSNAMES) {
			tableModel.addColumn(values);
			System.out.println("added");
		}
		//SEARCHING USERS
		for (User user: listUser) {
			Object[] UserRow = {user.getAlias(),user.getName(),user.getEmail()};
			tableModel.addRow(UserRow);
		}
		//JTable
		JTable table = new JTable(tableModel);
		C.add(table);
		
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
		CreateUser.setBackground(Color.GREEN);
		JButton DeleteUser = new JButton("Delete User");
		DeleteUser.setBackground(Color.RED);
		BUTTONS.add(CreateUser);
		BUTTONS.add(DeleteUser);
		
		//EVENTS
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				SwingUtilities.invokeLater(() -> {
					if(!isReferenceInMemory("MainMenu")) {						
						new MainMenu();
						setVisible(false);
					}else {
						JFrame w = returnWindow("MainMenu");
						w.setVisible(true);
						setVisible(false);
					}
				});
			}
		});
		
		CreateUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					if(!isReferenceInMemory("CreateUser")) {						
						new CreateUser();
						setVisible(false);
					}else {
						JFrame w = returnWindow("CreateUser");
						w.setVisible(true);
						setVisible(false);
					}
				});	
			}
		});
	}
}