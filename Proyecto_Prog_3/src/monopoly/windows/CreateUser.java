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
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import monopoly.data.DataManager;
import monopoly.objects.User;

public class CreateUser extends MasterFrame{
	private static final long serialVersionUID = 1L;
	private File ImageUser;

	public CreateUser() {
		//FONTS
		Font UserFont = new Font("Arial Black", Font.BOLD, 24);
		Font TextFont = new Font("Arial Black", Font.ITALIC, 12);
		
		//GENERAL WINDOW SETTINGS
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600,280);
		setLocationRelativeTo(null);
		setDefaultWindowIcon();
		setTitle("CREATE NEW USER");
		setLayout(new BorderLayout());
		setVisible(true);
		
		JPanel JTitle = new JPanel();
		JPanel LeftSide = new JPanel();
		JPanel RightSide = new JPanel();
		JPanel Bottom = new JPanel();
		JPanel HEADERS = new JPanel();
		JPanel FIELDS = new JPanel();
		JPanel Center = new JPanel();
		this.add(JTitle,BorderLayout.NORTH);
		this.add(Center,BorderLayout.CENTER);
		this.add(Bottom,BorderLayout.SOUTH);
		
		//TITLE
		JLabel Title = new JLabel("New user data");
		Title.setFont(UserFont);
		JTitle.add(Title);
		
		//LEFT SIDE
		Center.add(LeftSide,BorderLayout.WEST);
		JButton IUser = new JButton("Upload Image");
		LeftSide.add(IUser);
		IUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser ImageChooser = new JFileChooser();  
	            // Only ".jpeg" extension 
	            FileFilter ImageFilter = new FileNameExtensionFilter("File JPG", "jpeg");
	            ImageChooser.setFileFilter(ImageFilter);
	            int result = ImageChooser.showSaveDialog(CreateUser.this);
	            if (result == JFileChooser.APPROVE_OPTION) {
	               File ImageFile = ImageChooser.getSelectedFile();
	               //In the future we will save file for the user in the database
	               logger.log(Level.INFO, "Fichero seleccionado: " + ImageFile.toString());
	            }
			}
			
		});
		
		//RIGHT SIDE
		//These button don't go here, but we need to use it for the document listener.
		JButton createNewUser = new JButton("Create User");
		createNewUser.setBackground(Color.GREEN);
		
		//I want to use the data that the user gives me, so i use a Map in order to later
		//identified the JTextField that i need to use.
		Center.add(RightSide,BorderLayout.EAST);		
		String[] HEADERSNAMES = {"ALIAS:","NAME:","EMAIL:","PASSWORD:","CODE:"};
		Map<String, JTextField> textFieldMap = new HashMap<>();

		RightSide.setLayout(new GridLayout(1,2));
		RightSide.add(HEADERS, BorderLayout.WEST);
		RightSide.add(FIELDS, BorderLayout.EAST);
		HEADERS.setLayout(new GridLayout(HEADERSNAMES.length,1));
		FIELDS.setLayout(new GridLayout(HEADERSNAMES.length,1));
				
		for (String elem: HEADERSNAMES) {
			JLabel Name = new JLabel(elem);
			Name.setFont(TextFont);
			
			//CREATING THE FIELDS, ADDING TO THE MAP AND PUTTING THEM IN FIELDS
			if (elem.equals("PASSWORD:")) {
				JPasswordField password = new JPasswordField(20);
				textFieldMap.put(elem, password);
				FIELDS.add(password);
			} else {
				JTextField field = new JTextField(20);
				textFieldMap.put(elem, field);
				FIELDS.add(field);
			}
			
			//DOCUMENT LISTENER FOR EACH FIELD
			for (JTextField Field:textFieldMap.values()) {
				Field.getDocument().addDocumentListener(new DocumentListener() {
					
					@Override
					public void removeUpdate(DocumentEvent e) {
						for (JTextField fields: textFieldMap.values()) {
							if(fields.getText().isEmpty()) {
								createNewUser.setEnabled(false);
								break;
							} else{
								createNewUser.setEnabled(true);
							};
						}
					}
					
					@Override
					public void insertUpdate(DocumentEvent e) {
						for (JTextField fields: textFieldMap.values()) {
							if(fields.getText().isEmpty()) {
								createNewUser.setEnabled(false);
								break;
							} else{
								createNewUser.setEnabled(true);
							};
						}
					}
					
					@Override
					public void changedUpdate(DocumentEvent e) {
						for (JTextField fields: textFieldMap.values()) {
							if(fields.getText().isEmpty()) {
								createNewUser.setEnabled(false);
								break;
							} else{
								createNewUser.setEnabled(true);
							};
						} 		
					}
				});
				//PUT THE HEADERS
				HEADERS.add(Name);
			}
			
		}
		
		//BOTTOM
		Bottom.setLayout(new FlowLayout());
		JButton cancelNewUser = new JButton("Cancel");
		cancelNewUser.setBackground(Color.RED);
		Bottom.add(createNewUser);
		Bottom.add(cancelNewUser);
		createNewUser.setEnabled(false);
		
		//EVENTS
		createNewUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String Alias = textFieldMap.get("ALIAS:").getText();
				String Name = textFieldMap.get("NAME:").getText();
				String Email = textFieldMap.get("EMAIL:").getText();
				String Password = textFieldMap.get("PASSWORD:").getText();
				User NewUser = new User(Name,Email,Password,Alias,ImageUser);
				logger.log(Level.INFO, "New User created");
				DataManager.getManager().saveUser(NewUser);
				for (JTextField removeField: textFieldMap.values()) {
					removeField.setText("");
				}
			}
		});
		
		cancelNewUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(CreateUser.this, "Are you sure you want to cancel?","Confirmation",JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					switchToNextWindow(MasterFrame.UsersMenu);
					for (JTextField removeField: textFieldMap.values()) {
						removeField.setText("");
					}
				} 
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.UsersMenu);
				for (JTextField removeField: textFieldMap.values()) {
					removeField.setText("");
				}
			}
		});
	}
	
	@Override
	public String windowName() {
		return MasterFrame.CreateUser;
	}
}
