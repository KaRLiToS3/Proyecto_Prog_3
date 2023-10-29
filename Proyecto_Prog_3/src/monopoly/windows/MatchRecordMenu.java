package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jfree.chart.ChartPanel;

import monopoly.objects.LineChart;
import monopoly.objects.Match;

public class MatchRecordMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	private static final Dimension frameMinSize = new Dimension(500,300);
	private final URL path1 = getClass().getResource("/monopoly/images/searchIcon.png");
	
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
		List<Match> testList = new ArrayList<>();
		testList.add(new Match());
//		testList.add(new Match("Name 2", 2));
//		testList.add(new Match("Name 3", 2));
//		testList.add(new Match("Name 4", 2));
		/////////////////////DATA EXAMPLE//////////////////////
		
		//LIST MODEL
		
		DefaultListModel<Match> model = new DefaultListModel<>();
		model.addAll(testList);
		JList<Match> list = new JList<Match>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		setComponentDimension(list, 230, 200);
		
		JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		W.add(scroll);
		
		//CHART
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				LineChart chart = new LineChart("Currency Statistics",list.getSelectedValue());
				C.removeAll();
				ChartPanel panel = new ChartPanel(chart.getChart()) {
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
		setVisible(true);
	}

	@Override
	public String windowName() {
		return MasterFrame.MatchRecordMenu;
	}
	
	
}
