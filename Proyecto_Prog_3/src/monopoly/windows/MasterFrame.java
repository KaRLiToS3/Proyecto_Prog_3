package monopoly.windows;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MasterFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Loads the image resource form the memory into the ImageIcon object
	 * @param path A relative path to the file
	 * @return	Returns the ImageIcon with the file associated
	 * @throws FileNotFoundException	In case the path is wrong
	 */
	protected static ImageIcon loadImageIcon(String path) throws FileNotFoundException{
		URL url = MainMenu.class.getResource(path); //Obtains the image directory
        if (url != null) {
            return new ImageIcon(url);
        }else throw new FileNotFoundException("Image not found at path: " + path);
	}
	
	protected static ImageIcon getIconifiedImage(String path, int width, int height){
		try {
			ImageIcon originalImg = loadImageIcon(path);
			ImageIcon resizedImg = resizeIcon(originalImg, width, height);
			return resizedImg;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method resizes a given ImageIcon, according to its height and width applying a SCALE_SMOOTH algorithm
	 * @param img
	 * @param width
	 * @param height
	 * @return Returns the ImageIcon resized to the given proportions
	 */
	protected static ImageIcon resizeIcon(ImageIcon img, int width, int height) {
		Image image = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}
	
	protected static void setComponentDimension(Component C, int x, int y) {
		C.setPreferredSize(new Dimension(x, y));
		C.setMaximumSize(new Dimension(x, y));
	}
	
	protected Dimension getMainWindowDimension() {
		return new Dimension(this.getWidth(), this.getHeight());
	}
}
