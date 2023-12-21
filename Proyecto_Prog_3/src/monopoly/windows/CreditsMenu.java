package monopoly.windows;

import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreditsMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	private URL meme = getClass().getResource("/monopoly/images/meme.png");

	public CreditsMenu() {
		setSize(600,650);
		toBack();
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setDefaultWindowIcon();
		
		showMeme();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		JLabel lab = new JLabel(new ImageIcon(meme));
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.CENTER));
		p.add(lab);
		getContentPane().add(p);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
			}
		});
		
		setVisible(true);
	}
	
	private static void showMeme() {
		//https://www.youtube.com/shorts/4O_DwPlJQvc
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
