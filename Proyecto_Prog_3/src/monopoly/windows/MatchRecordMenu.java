package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class MatchRecordMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Dimension frameMinSize = new Dimension(500,300);
	private static final String path1 = "../images/searchIcon.png";
	
	private JTextField searchBar = new JTextField(12);

	public MatchRecordMenu() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(800,600);
		setMinimumSize(frameMinSize);
		setLocationRelativeTo(null);
		setTitle("MATCH RECORD");
		
		//PANELS AND LAYOUTS
		setLayout(new BorderLayout());
		
		JPanel W = new JPanel();
		JPanel C = new JPanel();
		JPanel searchPanel = new JPanel();
		
		setComponentDimension(W, 230, 200);
		setComponentDimension(searchPanel, 230, 30);
		
//		W.setBackground(Color.RED);
		W.setLayout(new BoxLayout(W, BoxLayout.Y_AXIS));
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
		
		add(W, BorderLayout.WEST);
		add(C, BorderLayout.CENTER);
		
		
		//SEARCH
		JLabel txtSearchBar = new JLabel("  Search: ");
		JButton searchImg = new JButton(getIconifiedImage(path1, 25, 25));
		searchPanel.add(txtSearchBar);
		searchPanel.add(searchBar);
		searchPanel.add(searchImg);
		
		W.add(searchPanel);
		
		/////////////////////DATA EXAMPLE//////////////////////
		List<String> testList = new ArrayList<>();
		testList.add("Name 1");
		testList.add("Name 2");
		testList.add("Name 3");
		testList.add("Name 4");
		/////////////////////DATA EXAMPLE//////////////////////
		
		//LIST MODEL
		
		DefaultListModel<String> model = new DefaultListModel<>();
		model.addAll(testList);
		JList<String> list = new JList<String>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		setComponentDimension(list, 230, 200);
		
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		W.add(scroll);
		
		//EVENTS
		
		searchImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.removeAllElements();
				if(!searchBar.getText().isBlank()) {
					for(String str : testList) {
						if(str.startsWith(searchBar.getText())){
							model.insertElementAt(searchBar.getText(), 0);
						}
					}
				}else model.addAll(testList);
			}
		});
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				SwingUtilities.invokeLater(() -> new MainMenu());
			}
		});
		
		setVisible(true);
	}
	
	public void setComponentDimension(Component C, int x, int y) {
		C.setPreferredSize(new Dimension(x, y));
		C.setMaximumSize(new Dimension(x, y));
	}
	
	public ImageIcon getIconifiedImage(String path, int width, int height){
		try {
			ImageIcon originalImg = MainMenu.loadImageIcon(path);
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
	private ImageIcon resizeIcon(ImageIcon img, int width, int height) {
		Image image = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}
}
