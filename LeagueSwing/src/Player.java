
public class Player extends SportsPlayer {

	private String position;
	private boolean foot;
	private int height;
	private double value;
	private int scoreCount;
	private int assistCount;
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isFoot() {
		return foot;
	}

	public double getValue() {
		return value;
	}

	public void setFoot(boolean foot) {
		this.foot = foot;
	}

	public void setHeight(int parseInt) {

	}
	public void increaseScoreCount() {
		this.scoreCount++;
	}
	public int getAssistCount() {
		return assistCount;
	}
	public  void incraseAssistCount() {
		this.assistCount++;
	}

	public int getHeight() {
		return height;
	}

	public void setValue(double d) {
		this.value=d;
	}

}
