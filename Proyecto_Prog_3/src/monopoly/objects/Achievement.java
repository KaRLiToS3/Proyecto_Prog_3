package monopoly.objects;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Achievement implements Serializable{
	private static final long serialVersionUID = -4777254735179279044L;
	private static final String[] mvp = {"/monopoly/images/MVP.jpg", "/monopoly/images/textMVP.jpg"};
	private static final String[] chpsk = {"/monopoly/images/cheapSkate.jpg", "/monopoly/images/cheapSkateText.jpg"};
	
	private int times;
	public Type type;

	public enum Type {
		MVP("MVP", mvp),
		CHEAPSKATE("CHEAPSKATE", chpsk),
		BEGGINER("BEGGINER", mvp),
		FLAT_BROKE("FLAT_BROKE", mvp),
		VETERAN("VETERAN", mvp);
		
		private String desc;
		private String[] img;
		private Type(String desc, String[] img) {
			this.desc = desc;
			this.img = img;
		}
		public String getDesc() {
			return desc;
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
		times = 0;
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
		return getType().getDesc() + "/" + getTimes();
	}
}
