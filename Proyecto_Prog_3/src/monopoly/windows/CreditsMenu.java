package monopoly.windows;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreditsMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;

	public CreditsMenu() {
		setSize(1000,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setDefaultWindowIcon();
		
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
		return MasterFrame.CreditsMenu;
	}
}
