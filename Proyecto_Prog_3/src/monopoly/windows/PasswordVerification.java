package monopoly.windows;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import monopoly.data.DataManager;
import monopoly.objects.User;

public class PasswordVerification extends MasterFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public PasswordVerification(){
		User user = ((UsersMenu) returnWindow(MasterFrame.UsersMenu)).getSelectedUser();
        // Configuración de la ventana principal
        setTitle("Password Verification");
        setSize(300, 110);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        //Components
        JLabel label = new JLabel("Insert the password for " + user.getAlias() + ":");
        label.setFont(new Font("Arial Black", Font.BOLD, 12));
        JPasswordField passwordField = new JPasswordField(20);
        JButton verification = new JButton("Accept");
        
        //Add to windows
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        JPanel UpPanel = new JPanel();
        JPanel DownPanel = new JPanel();
        panel.add(UpPanel);
        panel.add(DownPanel);
        UpPanel.add(label);
        DownPanel.add(passwordField);
        DownPanel.add(verification);
        add(panel);
        
        //Listeners
        verification.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(user.getPassword().equals(new String(passwordField.getPassword()))) {
					DataManager.getManager().getRegisteredUsers().removeObject(user);
					JOptionPane.showMessageDialog(PasswordVerification.this, "User deleted");
					DataManager.getManager().saveDataInDB();
					dispose();
				} else {
					JOptionPane.showMessageDialog(PasswordVerification.this, "Incorrect password");
				}
				passwordField.setText("");
			}
		});
        
        this.addWindowListener(new WindowAdapter() {
        	
			@Override
			public void windowDeactivated(WindowEvent e) {
				passwordField.setText("");
				switchToNextWindow(MasterFrame.UsersMenu);
				}
			});
	}

	@Override
	public String windowName() {
		return MasterFrame.PasswordVerification;
	}
	
}
