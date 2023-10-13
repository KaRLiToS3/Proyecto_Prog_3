package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class MainMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Font mainT = new Font("Arial Black", Font.BOLD,  24);
	private static final Dimension bMenuMinDim = new Dimension(200,60);
	private static final Dimension bMenuMaxDim = new Dimension(600,200);
	private static final Dimension WEPanelsMinDim = new Dimension(200,60);
	private static final Dimension WEPanelsMaxDim = new Dimension(200,60);
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
		
		W.setBackground(Color.red);
		E.setBackground(Color.RED);
		setComponentSize(W, WEPanelsMinDim, WEPanelsMaxDim);
		setComponentSize(E, WEPanelsMinDim, WEPanelsMaxDim);
		
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
			ImageIcon originalLeftIcon = loadImageIcon("../images/left_image_menu.jpg");
			ImageIcon originalRightIcon = loadImageIcon("../images/right_image_menu.jpg");
			ImageIcon resizedIcon = resizeIcon(originalIcon,leftImgWidth,leftImgHeight);
			ImageIcon resizedLeftIcon = resizeIcon(originalLeftIcon,250,500);
			ImageIcon resizedRightIcon = resizeIcon(originalRightIcon,250,500);
			JLabel centerImg = new JLabel(resizedIcon);
			JLabel leftImg = new JLabel(resizedLeftIcon);
			JLabel rightImg = new JLabel(resizedRightIcon);
			centerImg.setAlignmentX(Component.CENTER_ALIGNMENT);
			C.add(centerImg);
			W.add(leftImg);
			E.add(rightImg);
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
				setComponentSize(buttons[i], bMenuMinDim, bMenuMaxDim);
			}else {
				S.add(buttons[i]);
				buttons[i].setAlignmentX(Component.LEFT_ALIGNMENT);
				if(i == 6) S.add(Box.createHorizontalGlue());
			}
		}
		
		//EVENTS
		
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
	
	private void setComponentSize(Component B, Dimension minDim, Dimension maxDim) {
		B.setMinimumSize(minDim);
		B.setMaximumSize(maxDim);
		B.setPreferredSize(B.getMaximumSize());
	}
	
	/**
	 * This method resizes a given ImageIcon, according to its height and width applying a SCALE_SMOOTH algorithm
	 * @param img
	 * @param width
	 * @param height
	 * @return Returns the ImageIcon resized to the given proportions
	 */
	private ImageIcon resizeIcon(ImageIcon img, int width, int height) {
		//.setPreferredSize(new Dimension((this.getWidth()-C.getWidth())/2,this.getHeight()-N.getHeight()-S.getHeight()));
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
