package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.Border;

import monopoly.data.DataManager;
import monopoly.objects.User;

public class UserAchievementsMenu extends MasterFrame {
	private static final long serialVersionUID = 1L;
	private static final Font font1 = new Font("Arial Rounded MT Bold", Font.BOLD, 24);
	private static final Font font2 = new Font("Arial Rounded MT Bold", Font.PLAIN, 15);
	private static final Color gold = new Color(212, 175, 55);
	private static final Color bg = new Color(27, 27, 27);
	private URL backgroundImage = getClass().getResource("/monopoly/images/backgroundAchievements.jpg");
	private URL trophy = getClass().getResource("/monopoly/images/trophy.png");
	
	public UserAchievementsMenu() {
		setSize(1000,800);
		setLocationRelativeTo(null);
		setDefaultWindowIcon();
		setTitle("USER ACHIEVEMENTS MENU");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JLabel imgTrophy1 = new JLabel(getIconifiedImage(trophy, 50, 50));
		JLabel imgTrophy2 = new JLabel(getIconifiedImage(trophy, 50, 50));
		JLabel title = new JLabel("ACHIEVEMENTS");
		title.setFont(font1);
		title.setForeground(gold);
		JLabel plainText = new JLabel("Select the user to check his achievements!");
		plainText.setFont(font2);
		plainText.setForeground(gold);
		JComboBox<User> usersCombo = new JComboBox<>();
		
		JPanel N = new JPanel();
		JPanel N1 = new JPanel();
		JPanel C = new JPanel() {
			
			int imgW = 300;
			int imgH = 300;
			private Image img = getIconifiedImage(backgroundImage, imgW, imgH).getImage();

			@Override
			public void paintComponent(Graphics g) {
				double  X = this.getSize().getWidth();
				double Y = this.getSize().getHeight();
				if(X != 0 && Y != 0) {
					for(int x = 0; x < X; x += imgW) {
						for(int y = 0; y < Y; y += imgH) {
							g.drawImage(img, x, y, this);
						}
					}
				} else super.paintComponent(g);
			}
		};
		
		//LAYOUTS
		N.setLayout(new BoxLayout(N, BoxLayout.Y_AXIS));
		N1.setLayout(new FlowLayout());
		C.setLayout(new GridLayout());
		
		//NORTH
		N.add(N1);
		N1.setAlignmentX(CENTER_ALIGNMENT);
		N.add(plainText);
		plainText.setAlignmentX(CENTER_ALIGNMENT);
		N.add(new Box.Filler(new Dimension(5,15), new Dimension(5,15), new Dimension(5,15)));
		N.add(usersCombo);
		N.setBackground(bg);
		
		N1.setAlignmentX(CENTER_ALIGNMENT);
		N1.add(imgTrophy1);
		N1.add(title);
		N1.add(imgTrophy2);
		N1.setBackground(bg);
		
		//COMBO BOX
		for(User usr : DataManager.getManager().getRegisteredUsers()) {
			usersCombo.addItem(usr);
			usersCombo.setMaximumRowCount(10);
		}
		
		getContentPane().add(N, BorderLayout.NORTH);
		getContentPane().add(C, BorderLayout.CENTER);
		
		
		
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
