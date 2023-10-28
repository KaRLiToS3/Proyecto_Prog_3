package monopoly.windows;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class MasterFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	protected static Map<String, JFrame> windowRefs = new HashMap<>();
	protected static Map<URL, ImageIcon> imageCache = new HashMap<>();
	
	/**
	 * @author KaRLiToS3
	 *Class specially designed to draw images into panels
	 */
	class PanelImageBuilder extends JPanel{
		private static final long serialVersionUID = 1L;
		private double percentagePanelsWidth;
		private double percentagePanelsHeight;
		private boolean proportionalDimensions = false;
		private URL path;
		
		/**Main builder, only demands the width percentage it should use from the window where the panel is placed, thus
		 * allowing scaled resizes
		 * @param path
		 * @param percentagePanelsWidth	Values should be between 0 and 1, otherwise the Layout will handle it
		 */
		public PanelImageBuilder(URL path, double percentagePanelsWidth) {
			this (path, percentagePanelsWidth, 1, false);
		}

		
		/**Second builder that demands the width and height percentage it should use from the window where the panel is placed, thus
		 * allowing scaled resizes. It can also be resized proportionally, however depending on the layout it might be partially ignored (Specially designed
		 * for BorderLayout).
		 * @param path
		 * @param percentagePanelsWidth	Values should be between 0 and 1, otherwise the Layout will handle it
		 * @param percentagePanelsHeight	Values should be between 0 and 1, otherwise the Layout will handle it
		 * @param proportionalDimensions	Special limitation, if true the JPanel will escalate proportionally even if the window doesn't.
		 */
		public PanelImageBuilder(URL path, double percentagePanelsWidth, double percentagePanelsHeight, boolean proportionalDimensions) {
			this.path = path;
			this.percentagePanelsWidth = percentagePanelsWidth;
			this.percentagePanelsHeight = percentagePanelsHeight;
			this.proportionalDimensions = proportionalDimensions;
		}	
		
		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {                	
            	Image img  = loadImageIcon(path).getImage();
            	if (!proportionalDimensions) {
            		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            	} else {
            		if (getWidth()<getHeight()) {
            			g.drawImage(img, 0, 0, getWidth(), getWidth(), this);	
            		} else {
            			g.drawImage(img, 0, 0, getHeight(), getHeight(), this);
            		}
            	}            
            }catch (FileNotFoundException e) {
            	e.printStackTrace();
            }
		}

		@Override
		public Dimension getPreferredSize() {
			Dimension panelDim;
			Dimension windowDim;

			if(proportionalDimensions) {
				try {
					windowDim = new Dimension(getMainWindowDimension());
					panelDim = new Dimension(this.getWidth(), this.getHeight());

					if (panelDim.getHeight()/windowDim.getWidth() < percentagePanelsWidth) {	//Limits the reach of the resize according to percentages
						return new Dimension((int)(this.getHeight()),(int)(this.getHeight()*percentagePanelsHeight));			//Creates a square due to the fixed height
					} else {
						return new Dimension((int)(windowDim.getWidth()*percentagePanelsWidth),(int)(this.getHeight()*percentagePanelsHeight));			//Resolves the panel according to the width of the window
						//Height here is irrelevant
					}
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			} else {
				try {
					windowDim = new Dimension(getMainWindowDimension());
					return new Dimension((int)(windowDim.getWidth()*percentagePanelsWidth), (int) (windowDim.getHeight()*percentagePanelsHeight));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}
	
	protected void saveWindowReference(String name, JFrame frame) {
		if(!isReferenceInMemory(name))windowRefs.put(name, frame);
	}
	
	protected JFrame returnWindow(String name) {
		return windowRefs.get(name);
	}
	
	protected boolean isReferenceInMemory(String name) {
		return windowRefs.containsKey(name);
	}
	
	/**
	 * Loads the image resource form the memory into the ImageIcon object
	 * @param path A relative path to the file
	 * @return	Returns the ImageIcon with the file associated
	 * @throws FileNotFoundException	In case the path is wrong
	 */
	protected static ImageIcon loadImageIcon(URL path) throws FileNotFoundException{
		if (imageCache.containsKey(path)) {
			return imageCache.get(path);
		}
//		URL url = getClass().getResource(path); //Obtains the image directory
		
        if (path != null) {
        	ImageIcon img = new ImageIcon(path);
        	imageCache.put(path, img);
            return img;
        }else throw new FileNotFoundException("Image not found at path: " + path);
	}
	
	/**This method is designed to fit any image within a given component by returning a ImageIcon object
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	protected static ImageIcon getIconifiedImage(URL path, int width, int height){
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
			throw new ClassNotFoundException("This method cannot be implemented if the class doesn't extend JFrame");
		}
	}
}
