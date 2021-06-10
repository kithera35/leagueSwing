

import java.util.ArrayList;
import java.util.Random;

public class FootballTeam extends SportsTeam {
	

	
	private int tieCount;
	private int goalScored;
	private int goalTaken;
	private ArrayList<FootballTeam> possibleOpponents;
	
	public FootballTeam() {
		possibleOpponents=new ArrayList<>();
	}
	
	
	public FootballTeam getRandomOpponent() {
		return possibleOpponents.get(new Random().nextInt(possibleOpponents.size()));
	}
	
	public int getTieCount() {
		return tieCount;
	}



	public void setTieCount(int tieCount) {
		this.tieCount = tieCount;
	}



	public int getGoalScored() {
		return goalScored;
	}



	public void setGoalScored(int goalScored) {
		this.goalScored = goalScored;
	}



	public int getGoalTaken() {
		return goalTaken;
	}



	public void setGoalTaken(int goalTaken) {
		this.goalTaken = goalTaken;
	}



	public ArrayList<FootballTeam> getPossibleOpponents() {
		return possibleOpponents;
	}



	public void setPossibleOpponents(ArrayList<FootballTeam> possibleOpponents) {
		this.possibleOpponents = possibleOpponents;
		this.possibleOpponents.remove(this);
	}



	private int determinePower(ArrayList<Player> players) {
		return 0;
	}
	
	private int determineMoral(ArrayList<Player> players) {
		return 0;
	}

	
	
}
