package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.logging.Level;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import monopoly.data.DataManager;
import monopoly.windows.GameSettingsMenu;

public class MainMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	private static Font buttonFont = new Font("Dubai", Font.BOLD,  18);
	private static final Color gold = new Color(212, 175, 55);
	private static final Dimension frameMinSize = getDimensionProperty("mainMenuMinSizeX", "mainMenuMinSizeY");
	private static final Dimension frameSize = getDimensionProperty("mainMenuSizeX", "mainMenuSizeY");
	private static final int buttonSize = 250;
	private static final int buttonMargin = 50;
	private static final double percentagePanelsWE = 0.25;
	private final URL path1 = getClass().getResource(getStringProperty("monopoly_title"));
	private final URL path2 = getClass().getResource(getStringProperty("left_image_menu"));
	private final URL path3 = getClass().getResource(getStringProperty("right_image_menu"));
	private final URL path4 = getClass().getResource(getStringProperty("cash_bg"));
	
//	//TEST MAIN
//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(() -> new MainMenu());
//	}
	
	public MainMenu() {
		logger.log(Level.INFO, "MainMenu running");
		
		//GENERAL WINDOW SETTINGS
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(frameSize);
		setMinimumSize(frameMinSize);
		setDefaultWindowIcon();
		setLocationRelativeTo(null);
		setTitle("MONOPOLY");
		
        // ADD PANEL FOR BACKGROUND IMAGE
		JPanel backgroundPanel = new PanelImageBuilder(path4, 1);
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);
		
		//DECLARATION OF COMPONENTS
		
		JPanel N = new JPanel();
		JPanel W = new PanelImageBuilder(path2, percentagePanelsWE);
		JPanel C = new JPanel();
		JPanel E = new PanelImageBuilder(path3, percentagePanelsWE);
		JPanel S = new JPanel();
		JPanel pCentralImg = new PanelImageBuilder(path1, 0.5);
		
		//INICIALISATION FOR BUTTON AND PANEL PARAMETERS
		
		N.setOpaque(false);
		W.setOpaque(false);
		C.setOpaque(false);
		E.setOpaque(false);
		S.setOpaque(false);
		
		C.setAlignmentX(Component.CENTER_ALIGNMENT);
		C.setLayout(new BoxLayout(C, BoxLayout.Y_AXIS));
		S.setLayout(new BoxLayout(S, BoxLayout.X_AXIS));
		
		backgroundPanel.add(N, BorderLayout.NORTH);
		backgroundPanel.add(W, BorderLayout.WEST);
		backgroundPanel.add(C, BorderLayout.CENTER);
		backgroundPanel.add(E, BorderLayout.EAST);
		backgroundPanel.add(S, BorderLayout.SOUTH);
		
		C.add(pCentralImg);
		
		//BUTTON SETTINGS
		JButton[] buttons = new JButton[8];
		String[] bText = 
			{"PLAY", 
			"GAME SETTINGS", 
			"USER ACHIEVEMENTS", 
			"MATCH RECORD", 
			"MANAGE USERS", 
			"HELP", 
			"CREDITS", 
			"LEAVE GAME"};
		
		for(int i = 0; i < 8; i++) {
			if(i <6) {
				buttons[i] = new JButton(bText[i]) {
					private static final long serialVersionUID = 1L;
					@Override
					public Dimension getMaximumSize() {
						return new Dimension(C.getWidth()-buttonMargin, C.getHeight()-buttonSize);
					}
					@Override
					public Dimension getPreferredSize() {
						return new Dimension(C.getWidth()-buttonMargin, C.getHeight()-buttonSize);
					}
				};
				C.add(new Box.Filler(new Dimension(5, 5), null, null));
				C.add(buttons[i]);
				buttons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
				buttons[i].setBackground(gold);
				buttons[i].addActionListener(new ButtonActionListener(MasterFrame.windowArray[i]));
			}else {
				buttons[i] = new JButton(bText[i]);
				S.add(buttons[i]);
				buttons[i].setAlignmentX(Component.LEFT_ALIGNMENT);
				buttons[i].setBackground(Color.GREEN);
				if(i == 6) {
					S.add(Box.createHorizontalGlue());
					buttons[i].addActionListener(new ButtonActionListener(MasterFrame.windowArray[i]));
				}
			}
			buttons[i].setFont(buttonFont);
		}
		
		////////////////////////////////////xiker
		buttons[0].setEnabled(false);
		
		//////////////////////////////////
		
		
		//EVENTS

		buttons[7].addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the game?", "WARNING", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(option == JOptionPane.YES_OPTION) dispose();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				DataManager.getManager().saveDataInDB();
				disposeAllFrames();
			}
			////////xiker
			@Override
			public void windowActivated(WindowEvent e) {
				if (monopoly.windows.GameSettingsMenu.getSelectedUsers().size()!=0) {
					buttons[0].setEnabled(true);
				} else {
					buttons[0].setEnabled(false);
				}
					
			}
			///////
		});
		
		setVisible(true);
		logger.log(Level.INFO, "Window building ended");
	}
	
	
	class ButtonActionListener implements ActionListener{
		private String className;
		public ButtonActionListener(String className) {
			this.className = className;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			switchToNextWindow(className);
		}
	}

	@Override
	public String windowName() {
		return MasterFrame.MainMenu;
	}
}
