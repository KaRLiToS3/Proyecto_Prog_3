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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CreateUser extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreateUser() {
		
		//FONTS
		Font UserFont = new Font("Arial Black", Font.BOLD, 24);
		Font TextFont = new Font("Arial Black", Font.ITALIC, 12);
		
		//GENERAL WINDOW SETTINGS
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(600,280);
		setLocationRelativeTo(null);
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
	               System.out.println("Fichero seleccionado: " + ImageFile.toString());
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
		HEADERS.setLayout(new GridLayout(HEADERSNAMES.length,2));
		FIELDS.setLayout(new GridLayout(HEADERSNAMES.length,1));
				
		for (String elem: HEADERSNAMES) {
			JLabel Name = new JLabel(elem);
			Name.setFont(TextFont);
			JTextField Field = new JTextField(20);
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
			
			//Storage the JTextField in the map in order to use it later
			textFieldMap.put(elem, Field);
			HEADERS.add(Name);
			FIELDS.add(Field);
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
				
			}
		});
		
		cancelNewUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new JOptionPane();
				int option = JOptionPane.showConfirmDialog(CreateUser.this, "Are you sure you want to cancel?","Confirmation",JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					dispose();
				} else if(option == JOptionPane.NO_OPTION) {
					
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				SwingUtilities.invokeLater(() -> {
					new UsersMenu();
					dispose();
				});
			}
		});
	}
	
	public static void EnableButton() {
		
	}
}
