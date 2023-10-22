package monopoly.windows;

import java.awt.Dimension;

public class HelpMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	private static final Dimension frameMinSize = new Dimension(500,300);
	
	public HelpMenu() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000,700);
		setMinimumSize(frameMinSize);
		setLocationRelativeTo(null);
		setTitle("HELP WINDOW");
		
	}
}
