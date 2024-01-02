package monopoly.objects;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Achievement implements Serializable{
	private static final long serialVersionUID = -4777254735179279044L;
	private static final String[] mvp = {"/monopoly/images/MVP.jpg", "/monopoly/images/MVPtext.png"};
	private static final String[] chpsk = {"/monopoly/images/cheapSkate.jpg", "/monopoly/images/cheapSkateText.png"};
	private static final String[] flat_broke = {"/monopoly/images/flat_broke.jpg", "/monopoly/images/flatBrokeText.png"};
	private static final String[] beginner = {"/monopoly/images/beginner.jpg", "/monopoly/images/beginnerText.png"};
	private static final String[] veteran = {"/monopoly/images/veteran.jpg", "/monopoly/images/veteranText.png"};
	private static final String[] imperialist = {"/monopoly/images/imperialist.jpg", "/monopoly/images/imperialistText.png"};
	private static final String[] modest = {"/monopoly/images/modest.jpg", "/monopoly/images/modestText.png"};
	private int times;
	public Type type;

	public enum Type {
		MVP(mvp),
		CHEAPSKATE(chpsk),
		BEGINNER(beginner),
		FLAT_BROKE(flat_broke),
		VETERAN(veteran),
		IMPERIALIST(imperialist),
		MODEST(modest);
		
		private String[] img;
		private Type(String[] img) {
			this.img = img;
		}
		
		public String[] getImg() {
			return img;
		}
	}
	
	public Achievement(Type type, int times) {
		this.times = times;
		this.type = type;
	}
	
	public Achievement(Type type) {
		this.type = type;
		times = 1;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
	public void incrementTimes() {
		times++;
	}

	public Type getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Achievement) {
			Achievement ach = (Achievement) obj;
			return type.equals(ach.getType());
		} else return false;
	}

	@Override
	public String toString() {
		return getType().toString() + "/" + getTimes();
	}
}
