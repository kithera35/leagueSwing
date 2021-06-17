
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import static java.util.Comparator.comparing;

import javax.swing.ImageIcon;

public class FootballTeam extends SportsTeam {

	private int tieCount;
	private int goalScored;
	private int goalTaken;
	private ImageIcon imgIcon;
	private ArrayList<FootballTeam> possibleOpponents;
	private int assistCount;

	private ArrayList<Player> firstEleven;
	private ArrayList<Player> DEFplayers;
	private ArrayList<Player> MIDplayers;
	private ArrayList<Player> FWplayers;

	public FootballTeam() {
		possibleOpponents = new ArrayList<>();
		firstEleven = new ArrayList<Player>();
		DEFplayers = new ArrayList<Player>();
		MIDplayers = new ArrayList<Player>();
		FWplayers = new ArrayList<Player>();
	}

	public void determineFirstEleven() {

		int GKcount = 0;
		int DEFcount = 0;
		int MIDcount = 0;
		int FWcount = 0;

		super.getPlayers().sort(comparing(Player::getStrength));
		Collections.reverse(super.getPlayers());

		for (Player p : super.getPlayers()) {

			if (p.getPosition().equals("GK") && GKcount == 0) {
				firstEleven.add(p);
				GKcount++;
			}
			if (p.getPosition().equals("DEF") && DEFcount < 4) {
				firstEleven.add(p);
				DEFplayers.add(p);
				DEFcount++;
			}
			if (p.getPosition().equals("MID") && MIDcount < 4) {
				firstEleven.add(p);
				MIDplayers.add(p);
				MIDcount++;
			}
			if (p.getPosition().equals("FW") && FWcount < 2) {
				firstEleven.add(p);
				FWplayers.add(p);
				FWcount++;
			}
		}

		/*
		 * for(Player p:firstEleven) {
		 * System.out.println(p.getName()+" "+p.getStrength()+" "+p.getPosition()); }
		 */
	}

	public void increaseAssistCount(int assistCount) {
		this.assistCount += assistCount;
	}

	public void increaseTieCount() {
		this.tieCount++;
	}

	public ImageIcon getImgIcon() {
		return imgIcon;
	}

	public void setImgIcon(String index) {
		this.imgIcon = new ImageIcon("logos\\" + index + ".png");
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

	public void increaseGoalScored(int goalScored) {
		this.goalScored += goalScored;
	}

	public void increaseGoalTaken(int goalTaken) {
		this.goalTaken += goalTaken;
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

	public int getAssistCount() {
		return assistCount;
	}

	public void setAssistCount(int assistCount) {
		this.assistCount = assistCount;
	}

	public ArrayList<Player> getFirstEleven() {
		return firstEleven;
	}

	public void setFirstEleven(ArrayList<Player> firstEleven) {
		this.firstEleven = firstEleven;
	}

	public ArrayList<Player> getDEFplayers() {
		return DEFplayers;
	}

	public void setDEFplayers(ArrayList<Player> dEFplayers) {
		DEFplayers = dEFplayers;
	}

	public ArrayList<Player> getMIDplayers() {
		return MIDplayers;
	}

	public void setMIDplayers(ArrayList<Player> mIDplayers) {
		MIDplayers = mIDplayers;
	}

	public ArrayList<Player> getFWplayers() {
		return FWplayers;
	}

	public void setFWplayers(ArrayList<Player> fWplayers) {
		FWplayers = fWplayers;
	}

	public ArrayList<FootballTeam> getPossibleOpponents() {
		return possibleOpponents;
	}

	public void setPossibleOpponents(ArrayList<FootballTeam> possibleOpponents) {
		this.possibleOpponents = possibleOpponents;
		this.possibleOpponents.remove(this);
	}

	public void determinePower(ArrayList<Player> players) {

		int sumPowerOfPlayers = 0;
		for (Player p : players) {
			sumPowerOfPlayers += p.getStrength();

		}
		this.setTeamStrength(sumPowerOfPlayers / players.size());

	}

	private int determineMoral(ArrayList<Player> players) {
		return 0;
	}

}
