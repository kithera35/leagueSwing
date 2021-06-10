
import java.util.ArrayList;

public abstract class SportsTeam {
	
	
	private String name;
	private int point=0;
	private int winCount=0;
	private int lossCount=0;
	private ArrayList<Player> players=new ArrayList<Player>();
	private int MAX_PLAYER;
	private int MIN_PLAYER;
	private int teamStrength;
	private int teamMoral;
	private double budget;
	private int currentPlayer;
	
	
	
	public int getMIN_PLAYER() {
		return MIN_PLAYER;
	}



	public void setMIN_PLAYER(int mIN_PLAYER) {
		this.MIN_PLAYER = mIN_PLAYER;
	}



	public void addPlayerToTeam(Player sp) {
		players.add(sp);
	}
	
	
	
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWinCount() {
		return winCount;
	}
	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}
	public int getLossCount() {
		return lossCount;
	}
	public void setLossCount(int lossCount) {
		this.lossCount = lossCount;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public int getMAX_PLAYER() {
		return MAX_PLAYER;
	}
	public void setMAX_PLAYER(int mAX_PLAYER) {
		MAX_PLAYER = mAX_PLAYER;
	}
	public int getTeamStrength() {
		return teamStrength;
	}
	public void setTeamStrength(int teamStrength) {
		this.teamStrength = teamStrength;
	}
	public int getTeamMoral() {
		return teamMoral;
	}
	public void setTeamMoral(int teamMoral) {
		this.teamMoral = teamMoral;
	}
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public void addPlayer(Player e) {
		this.players.add(e);
	}
	public void removePlayer(Player e) {
		this.players.remove(e);
	}
	
	
}
