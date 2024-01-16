package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box.Filler;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import monopoly.data.DataManager;
import monopoly.objects.User;

public class GameSettingsMenu extends MasterFrame implements Updatable{
	private static final long serialVersionUID = 1L;
	private static final Dimension frameSize = getDimensionProperty("gameSettingsMenuSizeX", "gameSettingsMenuSizeY");
	private static final String[] tabNames = {"Select Users", "Cash Modifications", "Match Name"};
	private static final String windowTitle = "GAME SETTINGS MENU";
	private static final Font font1 = new Font("Arial Rounded MT Bold", Font.BOLD, 24);
	private static final Font font2 = new Font("Rockwell Nova", Font.PLAIN, 15);
	private static final Font font3 = new Font("Cascadia Code", Font.BOLD, 15);
	private static final Color gold = new Color(212, 175, 55);
	private static final Color bg = new Color(27, 27, 27);
	private static final int compSpace = 20;
	private DefaultListModel<User> modelUserSelectionList;
	private static List<User> selectedUsers = new ArrayList<>();
	private static int startingCash;
	private static int cashMultiplier;
	private static String matchName;

	public GameSettingsMenu() {
		setSize(frameSize);
		setResizable(false);
		setDefaultWindowIcon();
		setLocationRelativeTo(null);
		setTitle(windowTitle);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel N = new JPanel();
		JTabbedPane C = new JTabbedPane();
		JScrollPane T1 = new JScrollPane();
		JPanel T2 = new JPanel();
		JPanel T3 = new JPanel();
		JPanel S = new JPanel();

		C.setTabPlacement(SwingConstants.LEFT);
		C.addTab(tabNames[0], T1);
		C.addTab(tabNames[1], T2);
		C.addTab(tabNames[2], T3);
		C.setFont(font2);

		getContentPane().add(N, BorderLayout.NORTH);
		getContentPane().add(C, BorderLayout.CENTER);
		getContentPane().add(S, BorderLayout.SOUTH);

		//NORTH
		JLabel title = new JLabel(windowTitle);
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setFont(font1);
		N.add(title);

		//SOUTH
		JButton accept = new JButton("Accept");
		accept.setFont(font3);
		accept.setBackground(Color.GREEN);
		S.add(accept);

		//T1
		JList<User> userSelection = new JList<>();

		modelUserSelectionList = new DefaultListModel<>();
		userSelection.setModel(modelUserSelectionList);
		modelUserSelectionList.addAll(DataManager.getManager().getRegisteredUsers().getRegisteredData());

		T1.setViewportView(userSelection);

		//T2
		T2.setLayout(new BoxLayout(T2, BoxLayout.Y_AXIS));
		JPanel[] panelListT2 = new JPanel[2];

		for(int i = 0; i < panelListT2.length; i++) {
			panelListT2[i] = new JPanel();
			panelListT2[i].setLayout(new FlowLayout(FlowLayout.LEFT));
			T2.add(new Filler(new Dimension(1,1), new Dimension(compSpace,compSpace), new Dimension(compSpace,compSpace)));
			T2.add(panelListT2[i]);
		}
		T2.add(new Filler(new Dimension(1,1), new Dimension(compSpace,compSpace), new Dimension(compSpace,compSpace)));

		createInstructionLabel(panelListT2[0], "Starting Cash", font3, null, null);
		createInstructionLabel(panelListT2[1], "Cash Multiplier", font3, null, null);
		
		SpinnerNumberModel startingCashModel = new SpinnerNumberModel(1500, 0, 3000, 100);
		JSpinner startingCashSp = createSpinner(panelListT2[0], startingCashModel, true);

		SpinnerNumberModel cashMultiplierModel = new SpinnerNumberModel(1, 1, 5, 1);
		JSpinner cashMultiplierSp = createSpinner(panelListT2[1], cashMultiplierModel, true);

		
		//T3
		T3.setLayout(new BoxLayout(T3, BoxLayout.Y_AXIS));
		T3.add(new Filler(new Dimension(1,1), new Dimension(compSpace,40), new Dimension(compSpace,40)));
		JPanel panel1 = new JPanel();
		createInstructionLabel(panel1, "Match name", font3, null, null);
		JTextField nameField = new JTextField(15);
		panel1.add(nameField);
		T3.add(panel1);
		

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
			}
		});

		userSelection.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(userSelection.getSelectedIndices().length > 4) {
					userSelection.removeSelectionInterval(e.getFirstIndex(), e.getLastIndex());
				}
			}
		});

		accept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(userSelection.getSelectedValuesList().size() > 1 && !nameField.getText().isEmpty()) {					

					int choice = JOptionPane.showConfirmDialog(null, "All the selected options will apply to the game. Are you shure you want to save?", "Confirm choices?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if (choice == JOptionPane.YES_OPTION) {
						selectedUsers.clear();
						selectedUsers.addAll(userSelection.getSelectedValuesList());
						startingCash = (int) startingCashSp.getValue();
						cashMultiplier = (int) cashMultiplierSp.getValue();
						matchName = nameField.getText();
					}
				} else JOptionPane.showMessageDialog(null, "There are not enough Users selected for the match or the match name is blank", "Users not selected/Blank match name", JOptionPane.WARNING_MESSAGE);
			}
		});

		setVisible(true);
	}

	private JSpinner createSpinner(JPanel panel, SpinnerModel model, boolean editable) {
		if(model instanceof SpinnerNumberModel) {
			SpinnerNumberModel numbModel = (SpinnerNumberModel) model;
			JSpinner spinner = new JSpinner();
			spinner.setModel(numbModel);
			((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setEditable(editable);
			panel.add(spinner);
			return spinner;
		} else {
			System.out.println("Cannot handle that model yet");
			return null;
		}
	}

	private void createInstructionLabel(JPanel panel, String text, Font font, Color foregroundColor, Color bgColor) {
		JLabel lab = new JLabel(text);
		if(font != null) lab.setFont(font);
		if( foregroundColor != null) lab.setForeground(foregroundColor);
		if(bgColor != null) lab.setBackground(bgColor);
		panel.add(lab);
	}

	@Override
	public void updateAllData() {
		modelUserSelectionList.removeAllElements();
		modelUserSelectionList.addAll(DataManager.getManager().getRegisteredUsers().getRegisteredData());
	}

	public static List<User> getSelectedUsers() {
		return selectedUsers;
	}
	public static int getStartingCash() {
		return startingCash;
	}
	public static int getCashMultiplier() {
		return cashMultiplier;
	}
	public static String getMatchName() {
		return matchName;
	}


	@Override
	public String windowName() {
		return MasterFrame.GameSettingsMenu;
	}
}
