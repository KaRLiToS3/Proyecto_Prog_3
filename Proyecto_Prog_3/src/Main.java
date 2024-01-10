import java.util.logging.Level;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import monopoly.data.DataManager;
import monopoly.data.LogRecorder;
import monopoly.windows.MainMenu;

public class Main {
	public static void main(String[] args) {
		setUpLookAndFeel();
		if(DataManager.getManager().checkForUserChoice()) {
			SwingUtilities.invokeLater(() -> new MainMenu());
		}
	}
	
	/**
	 * This method searches for the predefined look and feel "Nimbus" 
	 */
	private static void setUpLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogRecorder logger = new LogRecorder();
			logger.log(Level.SEVERE, "LookAndFeel was not found");
		}
	}
}
