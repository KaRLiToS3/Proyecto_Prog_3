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
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import monopoly.data.DataManager;
import monopoly.objects.LogRecorder;
import monopoly.objects.User;


public class UsersMenu extends MasterFrame{
	private static final long serialVersionUID = 1L;
	public ArrayList<User> listUser;

	public UsersMenu(){
		Font UserFont = new Font("Arial Black", Font.BOLD, 24);
		Font TextFont = new Font("Arial Black", Font.ITALIC, 12);

		//GENERAL WINDOW SETTINGS
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultWindowIcon();
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
			logger.log(Level.INFO, "added");
		}
		//SEARCHING USERS
		//When the windows reactivates, the users are updated

		//JTable
		JTable table = new JTable(tableModel);
		JScrollPane scrollTable = new JScrollPane(table); 
		C.add(scrollTable);
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
			public void windowClosed(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
			}

			@Override
			public void windowActivated(WindowEvent e) {
				//Remove previous rows
				tableModel.setRowCount(0);
				//AddUsers
				for (User user: DataManager.getManager().getRegisteredUsers()) {
					Object[] UserRow = {user.getAlias(),user.getName(),user.getEmail()};
					tableModel.addRow(UserRow);
				}
				System.out.println(listUser);
			}
		});

		CreateUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToNextWindow(MasterFrame.CreateUser);
			}
		});
	}

	class UsersTableModel extends AbstractTableModel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public String windowName() {
		return MasterFrame.UsersMenu;
	}
}
