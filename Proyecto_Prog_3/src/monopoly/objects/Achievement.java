package monopoly.objects;

import java.io.Serializable;

public class Achievement implements Serializable{
	private static final long serialVersionUID = -4777254735179279044L;
	
	private int times;
	public Type type;

	public enum Type{
		MVP("Feels good to win every match, but even more to remind it to everybody"),
		CHEAPSKATE("This player had the highest amount of money at the end of the game, he doesn't like sharing cash at all"),
		BEGGINER("The first match is always tough, however you made it through, Congrats!!!! :)"),
		FLAT_BROKE("This player risked too much with his investments and could not get the best out of them, so ended behind the other players in cash");
		
		private String desc;
		private Type(String desc) {
			this.desc = desc;
		}
		public String getDesc() {
			return desc;
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
	
}
