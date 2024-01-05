package monopoly.windows;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

import javax.swing.JButton;
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
	private User user;
	private JLabel label;
	
	public PasswordVerification(){
		user = ((UsersMenu) returnWindow(MasterFrame.UsersMenu)).getSelectedUser();
        // Configuración de la ventana principal
        setTitle("Password Verification");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(300, 110);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        
        //Components
        label = new JLabel("Insert the password for " + user.getAlias() + ":");
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
					// If the user don't have a image there's no need to search for repetitions
					if (user.getImage() != null) {
						int counter = 0;
						for (User sameUserImage : DataManager.getManager().getRegisteredUsers()) {
							// Check if the deleted user's image is repeated 
							try {
								if (sameUserImage.getImage().equals(user.getImage())){
									// We count 2 because 1 is the same user and it is enough with a repetition to not delete the file 
									if (counter<2) {
										counter++;
									}	
								}	
							}catch(NullPointerException e1) {
								// This catch is here because there are some users without an image
							}		
						}
						if (counter < 2) {
							// Here the image is deleted
							try {
								Files.delete(user.getImage().toPath());
							} catch (IOException e1) {
								logger.log(Level.WARNING, "Deleting user " + user.getName()+ "'s image has failed");
							}
						}
					}
					//Delete of User
					DataManager.getManager().getRegisteredUsers().removeObject(user);
					JOptionPane.showMessageDialog(PasswordVerification.this, "User deleted");
					DataManager.getManager().saveDataInDB();
					//dispose();
					//setVisible(false);
                    switchToNextWindow(MasterFrame.UsersMenu);
				} else {
					JOptionPane.showMessageDialog(PasswordVerification.this, "Incorrect password");
				}
				passwordField.setText("");
			}
		});
        
        this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowActivated(WindowEvent e) {
				updateLabelText();
			}
			
			@Override
            public void windowClosing(WindowEvent e) {
                switchToNextWindow(MasterFrame.UsersMenu);
            }
		});
	}

	@Override
	public String windowName() {
		return MasterFrame.PasswordVerification;
	}
	
	private void updateLabelText() {
        user = ((UsersMenu) returnWindow(MasterFrame.UsersMenu)).getSelectedUser();
        label.setText("Insert the password for " + user.getAlias() + ":");
    }
}
