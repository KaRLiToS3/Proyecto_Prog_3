package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.Ignore;

public class HelpMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	private static final Font titleFont = new Font("Arial Black", Font.BOLD, 24);
	private static final Font scrollFont = new Font("Consolas", Font.ITALIC, 18);
	private static final Color gold = new Color(212, 175, 55);
	private static final Dimension frameMinSize = new Dimension(500,300);
	private static final String path1 = "../images/help_interface.jpg";
	private static final String path2 = "../images/help_match.png";
	private static final String path3 = "../images/help_rules.png";
	private ArrayList<JScrollPane> scrollList = new ArrayList<>();
	private String[] pdfPaths = {"src\\monopoly\\pdf_files\\INTERFACE GUIDE.pdf", "src\\monopoly\\pdf_files\\INTERFACE GUIDE.pdf", "src\\monopoly\\pdf_files\\INTERFACE GUIDE.pdf"};
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new HelpMenu());
	}
	
	public HelpMenu() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1100,700);
		setMinimumSize(frameMinSize);
		setLocationRelativeTo(null);
		setTitle("HELP WINDOW");
		
		setLayout(new BorderLayout());
		
		JPanel N = new JPanel();
		JTabbedPane C = new JTabbedPane();
		
		JScrollPane pInteface = new JScrollPane();
		JScrollPane pMatch = new JScrollPane();
		JScrollPane pRules = new JScrollPane();
		scrollList.add(pInteface);
		scrollList.add(pMatch);
		scrollList.add(pRules);
		
		add(N, BorderLayout.NORTH);
		add(C, BorderLayout.CENTER);
		
		N.setBackground(Color.BLACK);
		C.setBackground(Color.BLACK);
		C.setForeground(gold);
		C.addTab("INTERFACE GUIDE", getIconifiedImage(path1, 50, 50), pInteface);
		C.addTab("HOW TO CREATE A MATCH", getIconifiedImage(path2, 50, 50), pMatch);
		C.addTab("RULES", getIconifiedImage(path3, 50, 50), pRules);
		
		JLabel title = new JLabel("HELP WINDOW");
		title.setFont(titleFont);
		title.setForeground(gold);
		N.add(title);	
		setVisible(true);
		
		for(int i= 0; i < 3; i++) {
			JScrollPane p = scrollList.get(i);
			JLabel lab = new JLabel("Loading...");
			String path = pdfPaths[i];
			lab.setFont(scrollFont);
			p.setViewportView(lab);
			
			Thread hilo = new Thread(() -> {
				p.getVerticalScrollBar().setUnitIncrement(50);
				p.setViewportView(loadPDFintoPanel(path));
				p.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				p.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			});
		hilo.start();
		}
	}
	
	private JPanel loadPDFintoPanel(String path) {
		try {
			PDDocument doc = Loader.loadPDF(new File(path)); //Loads the document
			PDFRenderer render = new PDFRenderer(doc); //Prepares the document for display
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			for(int pags = 0; pags < doc.getNumberOfPages(); pags++ ) {
				BufferedImage bim = render.renderImageWithDPI(pags, 300); //Loads the image into memory
				ImageIcon img = resizeIcon(new ImageIcon(bim), 1000, 1500);
				JLabel page = new JLabel(img);
				page.setAlignmentX(Component.CENTER_ALIGNMENT);
				JPanel fillPanel = new JPanel();
				fillPanel.setBackground(new Color(225,225,225));
				setComponentDimension(fillPanel, 1000, 20);
				panel.add(page);
				panel.add(fillPanel);
			}
			doc.close();
			return panel;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
