package monopoly.objects;

public class Achievement {
	
	private int times;
	private Type type;

	public enum Type {
		MBP("Always there to be the champ"),
		CHEAPSKATE("I wouldn't ask this guy for a loan");
		
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
