package monopoly.windows;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class CreditsMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	public CreditsMenu() {
		saveWindowReference("GameSettingsMenu", this);
		setSize(1000,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SwingUtilities.invokeLater(() -> {
					if(!isReferenceInMemory("MainMenu")) {						
						new MainMenu();
						setVisible(false);
					}else {
						JFrame w = returnWindow("MainMenu");
						w.setVisible(true);
						setVisible(false);
					}
				});
			}
		});
		
		setVisible(true);
	}
}
