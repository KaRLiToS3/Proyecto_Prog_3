package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import monopoly.data.DataManager;
import monopoly.objects.GraphFactory;
import monopoly.objects.Match;

public class MatchRecordMenu extends MasterFrame{
	private static final long serialVersionUID = 1L;
	private static final Font font1 = new Font("Cooper Black", Font.ITALIC, 15);
	private static final Dimension frameSize = getDimensionProperty("matchRecordMenuSizeX", "matchRecordMenuSizeY");
	private static final Dimension frameMinSize = getDimensionProperty("matchRecordMenuMinSizeX", "matchRecordMenuMinSizeY");
	private final URL path1 = getClass().getResource(getStringProperty("search_icon"));
	private JList<Match> list;
	
	private JTextField searchBar = new JTextField(12);

	public MatchRecordMenu() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(frameSize);
		setDefaultWindowIcon();
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
		
		W.setLayout(new BoxLayout(W, BoxLayout.Y_AXIS));
		W.setAlignmentX(CENTER_ALIGNMENT);
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
		
		add(W, BorderLayout.WEST);
		add(C, BorderLayout.CENTER);
		
		//SEARCH
		JLabel txtSearchBar = new JLabel("  Search: ");
		JButton searchImg = new JButton(getIconifiedImage(path1, 25, 25));
		searchPanel.add(txtSearchBar);
		searchPanel.add(searchBar);
		searchPanel.add(searchImg);
		
		JButton deleteMatch = new JButton("Delete Match");
		deleteMatch.setAlignmentX(CENTER_ALIGNMENT);
		deleteMatch.setBackground(Color.RED);
		deleteMatch.setFont(font1);
		deleteMatch.setEnabled(false);
		W.add(searchPanel);
		
		/////////////////////DATA EXAMPLE//////////////////////
		List<Match> testList = new ArrayList<>();
		for(Match match : DataManager.getManager().getRegisteredMatches()) {
			testList.add(match);
		}
		/////////////////////DATA EXAMPLE//////////////////////
		
		//LIST MODEL
		
		DefaultListModel<Match> model = new DefaultListModel<>();
		model.addAll(testList);
		list = new JList<Match>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		setComponentDimension(list, 250, 200);
		
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		W.add(scroll);
		W.add(deleteMatch);
		
		//CHART
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!list.isSelectionEmpty()) deleteMatch.setEnabled(true);
				else deleteMatch.setEnabled(false);
				JFreeChart chart = GraphFactory.createLineChart("Currency Statistics", "Turn", "Currency", list.getSelectedValue());
				logger.log(Level.INFO, "Match " + list.getSelectedValue() + " selected");
				C.removeAll();
				ChartPanel panel = new ChartPanel(chart) {
					private static final long serialVersionUID = 1L;

					@Override
					public Dimension getPreferredSize() {
						return C.getSize();
					}
					
				};
				C.add(panel);
				revalidate();
				repaint();
			}
		});
		
		//EVENTS
		
		searchImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.removeAllElements();
				if(!searchBar.getText().isBlank()) {
					for(Match m : testList) {
						if(m.getName().startsWith(searchBar.getText())){
							model.insertElementAt(m, 0);
						}
					}
				}else model.addAll(testList);
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
			}
		});
		
		deleteMatch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Match m = list.getSelectedValue();
				model.removeElement(m);
				DataManager.getManager().deleteMatch(m);
			}
		});
		setVisible(true);
	}

	@Override
	public String windowName() {
		return MasterFrame.MatchRecordMenu;
	}
}
