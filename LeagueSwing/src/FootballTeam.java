

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class FootballTeam extends SportsTeam {
	

	
	private int tieCount;
	private int goalScored;
	private int goalTaken;
	private ImageIcon imgIcon;
	private ArrayList<FootballTeam> possibleOpponents;
	
	public FootballTeam() {
		possibleOpponents=new ArrayList<>();
	}
	
	
	public ImageIcon getImgIcon() {
		return imgIcon;
	}


	public void setImgIcon(String index) {
		this.imgIcon=new ImageIcon("logos\\"+index+".png");
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
