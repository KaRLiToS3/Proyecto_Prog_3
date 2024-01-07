package monopoly.windows;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JOptionPane;

public class WarningPanel extends JOptionPane {
	private static final long serialVersionUID = 759619185189569360L;

	public WarningPanel(String message) {
		showMessageDialog(null, message, "WARNING", WARNING_MESSAGE);

		this.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				requestFocus();
			}
		});
	}
}
