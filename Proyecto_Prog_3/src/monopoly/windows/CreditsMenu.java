package monopoly.windows;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class CreditsMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	private URL meme = getClass().getResource("/monopoly/images/meme.png");
	public CreditsMenu() {
		showMeme();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setSize(400,440);
		toBack();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setDefaultWindowIcon();
		
		JLabel lab = new JLabel(new ImageIcon(meme));
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.CENTER));
		p.add(lab);
		
		getContentPane().add(p);
		setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
			}
		});
		
		setVisible(true);
	}
	
	private void showMeme() {
		String url = "https://www.tiktok.com/@dev.c10/video/7306564034113883397?is_from_webapp=1&sender_device=pc";

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	@Override
	public String windowName() {
		return MasterFrame.CreditsMenu;
	}
}
