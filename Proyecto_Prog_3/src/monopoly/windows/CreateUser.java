package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
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
import monopoly.objects.Achievement;
import monopoly.objects.User;

public class CreateUser extends MasterFrame{
	private static final long serialVersionUID = 1L;
	//File selected by the user
	private File ImageFile;
	//Copied File in folder for images
	private static final Dimension frameSize= getDimensionProperty("createUserSizeX", "createUserSizeY");
	private File ImageUser;
	//Folder where the images are saved
	private File destinationFolder = new File("data/UserImage");
	
	Color BackgroundColor = Color.GRAY;
	Color JLabelColor = Color.WHITE;
	
	public CreateUser() {
		//FONTS
		Font UserFont = new Font("Arial Black", Font.BOLD, 24);
		Font TextFont = new Font("Arial Black", Font.ITALIC, 12);

		//GENERAL WINDOW SETTINGS
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(650,340);
		setSize(frameSize);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultWindowIcon();
		setTitle("CREATE NEW USER");
		setLayout(new BorderLayout());
		setVisible(true);

		JPanel JTitle = new JPanel();
		JPanel CenterContainer = new JPanel();
		JPanel Bottom = new JPanel();
		JPanel HEADERS = new JPanel();
		JPanel FIELDS = new JPanel();
		JPanel Center = new JPanel();
		JPanel leftButtons = new JPanel();
		JPanel rightButtons = new JPanel();
		
		JTitle.setBackground(BackgroundColor);
		CenterContainer.setBackground(BackgroundColor);
		Bottom.setBackground(BackgroundColor);
		HEADERS.setBackground(BackgroundColor);
		FIELDS.setBackground(BackgroundColor);
		Center.setBackground(BackgroundColor);
		leftButtons.setBackground(BackgroundColor);
		rightButtons.setBackground(BackgroundColor);
		
		this.add(JTitle,BorderLayout.NORTH);
		this.add(Center,BorderLayout.CENTER);
		this.add(Bottom,BorderLayout.SOUTH);

		//TITLE
		JLabel Title = new JLabel("New user data");
		Title.setForeground(JLabelColor);
		Title.setFont(UserFont);
		JTitle.add(Title);
		
		//CENTER
		//These button don't go here, but we need to use it for the document listener.
		JButton createNewUser = new JButton("Create User");
		createNewUser.setBackground(Color.GREEN);

		//I want to use the data that the user gives me, so i use a Map in order to later
		//identified the JTextField that i need to use.
		Center.add(CenterContainer,BorderLayout.CENTER);
		String[] HEADERSNAMES = {"ALIAS:","NAME:","EMAIL:","PASSWORD:"};
		Map<String, JTextField> textFieldMap = new HashMap<>();

		CenterContainer.setLayout(new GridLayout(1,2));
		CenterContainer.add(HEADERS, BorderLayout.WEST);
		CenterContainer.add(FIELDS, BorderLayout.EAST);
		HEADERS.setLayout(new GridLayout(HEADERSNAMES.length,1));
		FIELDS.setLayout(new GridLayout(HEADERSNAMES.length,1));

		for (String elem: HEADERSNAMES) {
			JLabel Name = new JLabel(elem);
			Name.setForeground(JLabelColor);
			Name.setFont(TextFont);

			//CREATING THE FIELDS, ADDING TO THE MAP AND PUTTING THEM IN FIELDS
			if (elem.equals("PASSWORD:")) {
				JPasswordField password = new JPasswordField(20);
				textFieldMap.put(elem, password);
				FIELDS.add(password);
			} else{
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
							}
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
							}
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
							}
						}
					}
				});
				//PUT THE HEADERS
				HEADERS.add(Name);
			}

		}

		//BOTTOM
		Bottom.setLayout(new GridLayout(1, 2));
		rightButtons.setLayout(new FlowLayout());
		//Button for cancel
		JButton cancelNewUser = new JButton("Cancel");
		cancelNewUser.setBackground(Color.RED);
		
		//Button for user image
		JButton IUser = new JButton("Upload Image");
		
		//ORDER
		leftButtons.add(IUser);
		
		rightButtons.add(createNewUser);
		rightButtons.add(cancelNewUser);
		
		Bottom.add(leftButtons);
		Bottom.add(rightButtons);
		
		createNewUser.setEnabled(false);
		
			
		

		//EVENTS		
		IUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser ImageChooser = new JFileChooser();
	            // Only ".jpeg , .jpg , .png" extension
	            FileFilter ImageFilter = new FileNameExtensionFilter("JPEG, JPG, PNG Files", "jpeg", "jpg", "png");
	            ImageChooser.setFileFilter(ImageFilter);
	            int result = ImageChooser.showSaveDialog(CreateUser.this);
	            if (result == JFileChooser.APPROVE_OPTION) {
	            	// Save the path for the moment
	               ImageFile = ImageChooser.getSelectedFile();
	               ImageUser = new File(destinationFolder, ImageFile.getName());
	            }
			}

		});
		
		createNewUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Set<Achievement> list = new LinkedHashSet<>();
				list.add(new Achievement(Achievement.Type.MVP, 2));
				list.add(new Achievement(Achievement.Type.BEGINNER, 3));
				list.add(new Achievement(Achievement.Type.CHEAPSKATE, 4));
				list.add(new Achievement(Achievement.Type.FLAT_BROKE, 1));
//				list.add(new Achievement(Achievement.Type.VETERAN, 1));
//				list.add(new Achievement(Achievement.Type.IMPERIALIST, 1));
//				list.add(new Achievement(Achievement.Type.MODEST, 1));
				String Alias = textFieldMap.get("ALIAS:").getText();
				String Name = textFieldMap.get("NAME:").getText();
				String Email = textFieldMap.get("EMAIL:").getText();
				String Password = textFieldMap.get("PASSWORD:").getText();
				User NewUser = new User(Name,Email,Password,Alias,ImageUser, list);
				logger.log(Level.INFO, "New User created");
				//Only when we are sure user was created we save the copy of the Image
				if (ImageFile != null) {
					saveImage();
				}
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

	public void saveImage() {
		try {
     	   Files.copy(ImageFile.toPath(), ImageUser.toPath(), StandardCopyOption.REPLACE_EXISTING);
     	   logger.log(Level.INFO, "Fichero seleccionado: " + ImageFile.toString());
        }catch(FileNotFoundException e1) {
     	   logger.log(Level.SEVERE, "File was not found");
        }catch(NoSuchFileException e2){
     	   logger.log(Level.SEVERE, "Folder does not exist");
        }
        catch(IOException ex){
     	   logger.log(Level.SEVERE,"Error when copying file: " + ImageFile.toString());
        }
	}
}
