package monopoly.windows;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameSettingsMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	public GameSettingsMenu() {
		setSize(1000,800);
		setDefaultWindowIcon();
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
		return MasterFrame.GameSettingsMenu;
	}
}
