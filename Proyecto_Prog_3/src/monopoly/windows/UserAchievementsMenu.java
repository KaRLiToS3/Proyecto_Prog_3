package monopoly.windows;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class UserAchievementsMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	public UserAchievementsMenu() {
		setSize(1000,800);
		setLocationRelativeTo(null);
		setDefaultWindowIcon();
		setTitle("USER ACHIEVEMENTS MENU");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		
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
		return MasterFrame.UserAchievementsMenu;
	}
	
}
