package monopoly.windows;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MasterFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * @author KaRLiToS3
	 *Class specially designed to draw images into panels
	 */
	class PanelImageBuilder extends JPanel{
		private static final long serialVersionUID = 1L;
		private double percentagePanelsWidth;
		private double percentagePanelsHeight;
		private String path;
		
		/**Main builder, only demands the width percentage it should use from the window where the panel is placed
		 * @param path
		 * @param percentagePanelsWidth	Values should be between 0 and 1, otherwise the Layout will handle it
		 */
		public PanelImageBuilder(String path, double percentagePanelsWidth) {
			this.path = path;
			this.percentagePanelsWidth = percentagePanelsWidth;
			percentagePanelsHeight = 1;
		}
		
		/**Second builder that demands the width and height percentage it should use from the window where the panel is placed
		 * @param path
		 * @param percentagePanelsWidth	Values should be between 0 and 1, otherwise the Layout will handle it
		 * @param percentagePanelsHeight	Values should be between 0 and 1, otherwise the Layout will handle it
		 */
		public PanelImageBuilder(String path, double percentagePanelsWidth, double percentagePanelsHeight) {
			this.path = path;
			this.percentagePanelsWidth = percentagePanelsWidth;
			this.percentagePanelsHeight = percentagePanelsHeight;
		}
		
		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {                	
            	Image img  = loadImageIcon(path).getImage();
            	g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }catch (FileNotFoundException e) {
            	e.printStackTrace();
            }
        }
		@Override
		public Dimension getPreferredSize() {
			Dimension windowDim;
			try {
				windowDim = getMainWindowDimension();
				return new Dimension((int)(windowDim.getWidth()*percentagePanelsWidth), (int) (windowDim.getHeight()*percentagePanelsHeight));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return super.getSize();
			}
		}
	}
	
	
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
	
	/**This method is designed to fit any image within a given component by returning a ImageIcon object
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
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
	
	/**Sets the dimension of any component, however it may interfere with the LayoutManager, thus not being applied
	 * @param C receives any component
	 * @param width	refers to the width of the component
	 * @param height	refers to the height of the component
	 */
	protected static void setComponentDimension(Component C, int width, int height) {
		C.setPreferredSize(new Dimension(width, height));
		C.setMaximumSize(new Dimension(width, height));
	}
	
	
	/**Provides the dimension of the window from where it is called, it should only be applied if the class extends JFrame
	 * @return	The dimension of the JFrame
	 * @throws ClassNotFoundException	In case it is used in a class that is not a JFrame
	 */
	protected Dimension getMainWindowDimension() throws ClassNotFoundException{
		if(this instanceof JFrame) {
			return new Dimension(this.getWidth(), this.getHeight());			
		}else {
			throw new ClassNotFoundException();
		}
	}
}
