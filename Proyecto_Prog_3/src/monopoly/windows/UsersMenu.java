package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EventObject;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import monopoly.data.DataManager;
import monopoly.objects.User;


public class UsersMenu extends MasterFrame{
	private static final long serialVersionUID = 1L;
	JTextField SearchUser;
	User selectedUser;
	
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
		class MyTableModel extends DefaultTableModel { 
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
			
		}
		
		DefaultTableModel tableModel = new MyTableModel();
		String[] HEADERSNAMES = {"ALIAS:","NAME:","EMAIL:"};
		for (String values : HEADERSNAMES) {
			tableModel.addColumn(values);
		}
		//SEARCHING USERS
		//When the windows reactivates, the users are updated
		
		//JTable
		JTable table = new JTable(tableModel);
		JScrollPane scrollTable = new JScrollPane(table);
		C.add(scrollTable);
		
		class MyCellRenderer extends JLabel implements TableCellRenderer{

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				String strCell = value.toString();
				setText(strCell);
				String searchText = SearchUser.getText(); 
				if (column == 0) {
					if (!searchText.isEmpty() && strCell.toLowerCase().startsWith(SearchUser.getText().toLowerCase())) {
						setBackground(Color.LIGHT_GRAY);
						setFont(new Font("Arial", Font.BOLD, 12));
					} else {
						setBackground(Color.WHITE);
						setFont(new Font("Arial", Font.PLAIN, 12));
					}
				}
				if (hasFocus) {
					setBackground(Color.LIGHT_GRAY);		
				}
				setOpaque(true);
				return this;
			}
			
			
		}
		
		table.setDefaultRenderer(Object.class, new MyCellRenderer());

		//DOWN
		S.setLayout(new FlowLayout());
		S.add(SEARCH);
		JLabel TextSearch = new JLabel("SEARCH USER: ");
		TextSearch.setFont(TextFont);
		SearchUser = new JTextField(20);
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
//				DataManager.getManager().uploadDataFromDB();
				tableModel.setRowCount(0);
				//AddUsers
				for (User user: DataManager.getManager().getRegisteredUsers()) {
					Object[] UserRow = {user.getAlias(),user.getName(),user.getEmail()};
					tableModel.addRow(UserRow);
				}
			}
		});

		CreateUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToNextWindow(MasterFrame.CreateUser);
			}
		});
		
		SearchUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!SearchUser.getText().isEmpty()) {
					tableModel.setRowCount(0);
					for (User user: DataManager.getManager().getRegisteredUsers()) {
						if (user.getAlias().toLowerCase().startsWith(SearchUser.getText().toLowerCase())) {
							Object[] UserRow = {user.getAlias(),user.getName(),user.getEmail()};
							tableModel.addRow(UserRow);
						}
					}
				} else {
					tableModel.setRowCount(0);
					for (User user: DataManager.getManager().getRegisteredUsers()) {
						Object[] UserRow = {user.getAlias(),user.getName(),user.getEmail()};
						tableModel.addRow(UserRow);
					}
				}
			}
		});
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
//				if (!e.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                    	String selectedValue = (String) table.getValueAt(selectedRow, 2);
                    	Set<User> listUser = DataManager.getManager().getRegisteredUsers().getRegisteredData();
                    	for (User user:listUser) {
                    		if(user.getEmail().equals(selectedValue)) {
                    			selectedUser = user;
                    		}
                    	}
                    }
				}
//			}
		});
		
		DeleteUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedUser != null){
					switchToNextWindow(MasterFrame.PasswordVerification);
				}
			}
		});
	}
	
	@Override
	public String windowName() {
		return MasterFrame.UsersMenu;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	
}
