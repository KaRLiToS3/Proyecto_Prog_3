package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class HelpMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	private static final Dimension frameSize = getDimensionProperty("helpMenuSizeX", "helpMenuSizeY");
	private static final Font titleFont = new Font("Arial Black", Font.BOLD, 24);
	private static final Font scrollFont = new Font("Consolas", Font.ITALIC, 18);
	private static final Color gold = new Color(212, 175, 55);
	private static final Dimension frameMinSize = getDimensionProperty("helpMenuMinSizeX", "helpMenuMinSizeY");
	private static final String pdf1 = getStringProperty("interface_pdf");
	private static final String pdf2 = getStringProperty("how_to_start_a_match_pdf");
	private static final String pdf3 = getStringProperty("how_to_play_pdf");

	private final URL path1 = getClass().getResource(getStringProperty("interface_logo"));
	private final URL path2 = getClass().getResource(getStringProperty("match_logo"));
	private final URL path3 = getClass().getResource(getStringProperty("rules_logo"));
	private ArrayList<JScrollPane> scrollList = new ArrayList<>();

	private static final String[] pdfDirectory = {pdf1,pdf2,pdf3};
	InputStream[] pdfPaths = new InputStream[pdfDirectory.length];

	public HelpMenu() {
		loadPDFintoArray();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(frameSize);
		setMinimumSize(frameMinSize);
		setDefaultWindowIcon();
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
		C.addTab("INTERFACE GUIDE", getIconifiedImage(path1, 50, 50), pInteface);
		C.addTab("HOW TO CREATE A MATCH", getIconifiedImage(path2, 50, 50), pMatch);
		C.addTab("HOW TO PLAY", getIconifiedImage(path3, 50, 50), pRules);

		JLabel title = new JLabel("HELP WINDOW");
		title.setFont(titleFont);
		title.setForeground(gold);
		N.add(title);
		setVisible(true);

		for(int i= 0; i < C.getTabCount(); i++) {
			JScrollPane p = scrollList.get(i);
			JLabel lab = new JLabel("Loading...");
			InputStream path = pdfPaths[i];
			lab.setFont(scrollFont);
			p.setViewportView(lab);

			Thread hilo = new Thread(() -> {
				p.getVerticalScrollBar().setUnitIncrement(50);
				p.setViewportView(loadPDFintoPanel(path));
				p.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				p.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			});
		hilo.start();
		}

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
			}
		});
	}

	private JPanel loadPDFintoPanel(InputStream file) {
		try {
			PDDocument doc = PDDocument.load(file); //Loads the document
			PDFRenderer render = new PDFRenderer(doc); //Prepares the document for display
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			for(int pags = 0; pags < doc.getNumberOfPages(); pags++ ) {
				BufferedImage bim = render.renderImageWithDPI(pags, 300); //Loads the image into memory
				ImageIcon img = resizeIcon(new ImageIcon(bim), 1000, 1500);
				JLabel page = new JLabel(img);

				logger.log(Level.INFO, String.format("Page %d from file %s is ready", pags, file.toString()));

				page.setAlignmentX(Component.CENTER_ALIGNMENT);
				JPanel fillPanel = new JPanel();
				fillPanel.setBackground(new Color(225,225,225));
				setComponentDimension(fillPanel, 1000, 20);

				panel.add(page);
				panel.add(fillPanel);
			}
			logger.log(Level.INFO, "PDF file " + file + " loaded correctly");
			doc.close();
			return panel;
		} catch (IOException e) {
			logger.log(Level.SEVERE, "The PDF file " + file + " was not found");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String windowName() {
		return MasterFrame.HelpMenu;
	}

	private void loadPDFintoArray() {
		for (int i = 0; i < pdfDirectory.length; i++) {
			pdfPaths[i] = getClass().getResourceAsStream(pdfDirectory[i]);
		}
	}
}
