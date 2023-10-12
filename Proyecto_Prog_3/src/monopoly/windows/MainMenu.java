package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class MainMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Font mainT = new Font("Arial Black", Font.BOLD,  24);
	private static final Dimension bMenu = new Dimension(200,60);
	private static final int leftImgWidth = 75;
	private static final int leftImgHeight = 75;
	
	//TEST MAIN
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainMenu());
		
	}
	
	public MainMenu() {
		//LOOK AND FEEL SETUP
		setUpLookAndFeel();
		
		//GENERAL WINDOW SETTINGS
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
		setTitle("MONOPOLY");
		setLayout(new BorderLayout());
		
		//DECLARATION OF COMPONENTS
		JPanel N = new JPanel();
		JPanel W = new JPanel();
		JPanel C = new JPanel();
		JPanel E = new JPanel();
		JPanel S = new JPanel();
		
		C.setAlignmentX(Component.CENTER_ALIGNMENT);
		C.setLayout(new BoxLayout(C, BoxLayout.Y_AXIS));
		C.setAlignmentX(Component.CENTER_ALIGNMENT);
		S.setLayout(new BoxLayout(S, BoxLayout.X_AXIS));
		
		add(N, BorderLayout.NORTH);
		add(W, BorderLayout.WEST);
		add(C, BorderLayout.CENTER);
		add(E, BorderLayout.EAST);
		add(S, BorderLayout.SOUTH);
		
		JLabel title = new JLabel("MONOPOLY GAME");
		title.setFont(mainT);
		

		//ADDING THE COMPONENTS TO THE PANELS
		N.add(title);
		
		//IMAGES
		try{
			ImageIcon originalIcon = loadImageIcon("../images/monopoly_guy.jpg");
			ImageIcon resizedIcon = resizeIcon(originalIcon,leftImgWidth,leftImgHeight);
			JLabel i1 = new JLabel(resizedIcon);
			i1.setAlignmentX(Component.CENTER_ALIGNMENT);
			C.add(i1);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//BUTTON SETTINGS
		JButton[] buttons = new JButton[8];
		String[] bText = {"PLAY", "GAME SETTINGS", "USER ACHIEVEMENTS", "MATCH RECORD", "MANAGE USERS", "CREDITS", "HELP", "LEAVE GAME"};
		
		for(int i = 0; i < 8; i++) {
			buttons[i] = new JButton(bText[i]);
			if(i <6) {
				C.add(new Box.Filler(new Dimension(1, 1), null, null));
				C.add(buttons[i]);
				buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
				setButtonSize(buttons[i], bMenu);
			}else {
				S.add(buttons[i]);
				buttons[i].setAlignmentX(Component.LEFT_ALIGNMENT);
				if(i == 6) S.add(Box.createHorizontalGlue());
			}
		}
		
		setVisible(true);
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
	
	private void setButtonSize(JButton B, Dimension dim) {
		B.setPreferredSize(dim);
		B.setMinimumSize(dim);
		B.setMaximumSize(dim);
	}
	
	/**
	 * This method resizes a given ImageIcon, according to its height and width applying a SCALE_SMOOTH algorithm
	 * @param img
	 * @param width
	 * @param height
	 * @return Returns the ImageIcon resized to the given proportions
	 */
	private ImageIcon resizeIcon(ImageIcon img, int width, int height) {
		Image image = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}
	
	/**
	 * Loads the image resource form the memory into the ImageIcon object
	 * @param path A relative path to the file
	 * @return	Returns the ImageIcon with the file associated
	 * @throws FileNotFoundException	In case the path is wrong
	 */
	private ImageIcon loadImageIcon(String path) throws FileNotFoundException{
		URL url = MainMenu.class.getResource(path); //Obtains the image directory
        if (url != null) {
            return new ImageIcon(url);
        }else throw new FileNotFoundException("Image not found at path: " + path);
	}
}
