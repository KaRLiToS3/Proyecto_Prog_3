package monopoly.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import monopoly.data.DataManager;
import monopoly.objects.Achievement;
import monopoly.objects.User;

public class UserAchievementsMenu extends MasterFrame implements Updatable{
	private static final long serialVersionUID = 1L;
	private static final Dimension frameSize = getDimensionProperty("userAchievementsMenuSizeX", "userAchievementsMenuSizeY");
	private static final Font font1 = new Font("Arial Rounded MT Bold", Font.BOLD, 24);
	private static final Font font2 = new Font("Arial Rounded MT Bold", Font.PLAIN, 15);
	private static final Color gold = new Color(212, 175, 55);
	private static final Color bg = new Color(27, 27, 27);
	private static final String infoText = getStringProperty("initial_text_info");
	private static int achievementSize = 150;
	private static final int numPossibleAchievements = Achievement.Type.values().length;
	private static final int numGridSize = calculateGridSize();
	private JLabel info;
	private URL backgroundImage = getClass().getResource(getStringProperty("mosaic_bg"));
	private URL trophy = getClass().getResource(getStringProperty("trophy_img"));
	private Set<Thread> threadList = new HashSet<>();
	private JComboBox<User> usersCombo;

	public UserAchievementsMenu() {
		setSize(frameSize);
		setLocationRelativeTo(null);
		setDefaultWindowIcon();
		setMinimumSize(new Dimension(600,700));
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
		usersCombo = new JComboBox<>();

		JPanel N = new JPanel();
		JPanel N1 = new JPanel();
		JPanel C = new JPanel() {
			private static final long serialVersionUID = -4785966286785495776L;
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

		JPanel S = new JPanel();

		JPanel C1 = new JPanel(){
			private static final long serialVersionUID = 4305615840200453190L;
			@Override
			public Dimension getPreferredSize() {
				return new Dimension((int)(C.getWidth()*0.75), (C.getHeight()));
			}
		};

		//LAYOUTS
		N.setLayout(new BoxLayout(N, BoxLayout.Y_AXIS));
		N1.setLayout(new FlowLayout());
		C1.setLayout(new GridLayout(numGridSize, numGridSize, 20, 20));
		C.setLayout(new FlowLayout(FlowLayout.CENTER, 1, (int) (C.getHeight()*0.25)));

		//CENTER

		C1.setOpaque(false);
		C.setAlignmentX(CENTER_ALIGNMENT);
		C.add(C1);

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

		//SOUTH
		info = new JLabel(infoText);
		info.setFont(font2);
		info.setForeground(gold);
		S.setAlignmentX(CENTER_ALIGNMENT);
		S.setBackground(Color.BLACK);
		S.add(info);

		//COMBO BOX
		for(User usr : DataManager.getManager().getRegisteredUsers()) {
			usersCombo.addItem(usr);
			usersCombo.setMaximumRowCount(10);
		}

		getContentPane().add(N, BorderLayout.NORTH);
		getContentPane().add(C, BorderLayout.CENTER);
		getContentPane().add(S, BorderLayout.SOUTH);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switchToNextWindow(MasterFrame.MainMenu);
				killThreads();
				C1.removeAll();
				info.setText(infoText);
			}
		});

		usersCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				killThreads();
				C1.removeAll();
				C1.revalidate();
				C1.repaint();
				info.setText(infoText);
				User selUser = (User) usersCombo.getSelectedItem();
				if(selUser != null) {
					if(selUser.getAchievements() != null) {
						for(Achievement ach : selUser.getAchievements()) {
							URL logoAch = getClass().getResource(ach.getType().getImg()[0]);
							URL textAch = getClass().getResource(ach.getType().getImg()[1]);
							JLabel label = new JLabel(getIconifiedImage(logoAch, achievementSize, achievementSize));
							label.addMouseListener(new ImageSwitchActionListener(label, ach.getTimes(), logoAch, textAch));
							C1.add(label);
						}
						for(int i = selUser.getAchievements().size(); i < numPossibleAchievements; i++) {
							URL questionBlock = getClass().getResource("/monopoly/images/Question_Block.png");
							JLabel labQB = new JLabel(getIconifiedImage(questionBlock, achievementSize, achievementSize));
							C1.add(labQB);
						}
				}
					revalidate();
					repaint();
				}
			}
		});

		setVisible(true);
	}
	@Override
	public String windowName() {
		return MasterFrame.UserAchievementsMenu;
	}

	private void killThreads() {
		for(Thread thr : threadList) thr.interrupt();
	}

	@Override
	public void updateAllData() {
		usersCombo.removeAllItems();
		for(User usr : DataManager.getManager().getRegisteredUsers()) {
			usersCombo.addItem(usr);
		}
	}

	private static int calculateGridSize() {
		double i = 1;
		while(Math.pow(i, 2) < numPossibleAchievements ) {
			if(i != 1) achievementSize = (int) (achievementSize - i*10);
			i++;
		}
		return (int) i;
	}

	class ImageSwitchActionListener extends MouseAdapter{
		private boolean readyToShift = true;
		private Thread shiftImage;
		private JLabel label;
		private int times;
		private ImageIcon img;
		private ImageIcon logo;
		private ImageIcon text;

		public ImageSwitchActionListener(JLabel label, int times,URL logo, URL text) {
			this.label  = label;
			this.logo = getIconifiedImage(logo, achievementSize, achievementSize);
			this.text = getIconifiedImage(text, achievementSize, achievementSize);
			this.times = times;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			shiftImage = new Thread(() -> {
				if(readyToShift) {
					readyToShift = false;
					info.setText("This user received this award " + times + " times!!! ");
					try {
						for(int width = achievementSize; width > 0 ; width -= 2) {
							img = resizeIcon(logo, width, achievementSize);
							SwingUtilities.invokeLater(() -> label.setIcon(img));
						}

						for(int width = 1; width <= achievementSize ; width += 2) {
							img = resizeIcon(text, width, achievementSize);
							SwingUtilities.invokeLater(() -> label.setIcon(img));
						}
						Thread.sleep(5000);
						for(int width = achievementSize; width > 0 ; width -= 2) {
							img = resizeIcon(text, width, achievementSize);
							SwingUtilities.invokeLater(() -> label.setIcon(img));
						}

						for(int width = 1; width <= achievementSize ; width += 2) {
							img = resizeIcon(logo, width, achievementSize);
							SwingUtilities.invokeLater(() -> label.setIcon(img));
						}
						readyToShift = true;
						threadList.remove(shiftImage);
					}catch (InterruptedException e1) {
						SwingUtilities.invokeLater(() -> label.setIcon(logo));
					}
				}
			});
			threadList.add(shiftImage);
			shiftImage.start();
		}
	}

}
