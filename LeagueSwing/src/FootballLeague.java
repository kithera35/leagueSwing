
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class FootballLeague implements ILeague {

	private ArrayList<FootballTeam> teams;
	private ArrayList<Match> matches = new ArrayList<>();

	public FootballLeague() {
		teams = new ArrayList<>();
	}

	@Override
	public FootballTeam matchTeams(FootballTeam team1, FootballTeam team2) {
		return null;

	}

	@Override
	public void increasePoint(FootballTeam team) {

	}

	public void createBudgetAllTeams(ArrayList<FootballTeam> allteams) {

		for (FootballTeam team : allteams) {
			double sumBudgetOfPlayers = 0;
			for (Player p : team.getPlayers()) {
				sumBudgetOfPlayers += p.getValue();
			}
			team.setBudget(sumBudgetOfPlayers / 15);
		}
	}

	@Override

	public void createAllTeams() throws IOException {
		// File reading
		FileInputStream fstream = new FileInputStream("players.txt");
		DataInputStream dstream = new DataInputStream(fstream);
		BufferedReader breader = new BufferedReader(new InputStreamReader(dstream, "UTF-8"));
		String str = "";

		while ((str = breader.readLine()) != null) {

			Player player = new Player();
			FootballTeam team = new FootballTeam();
			team.setName(str.split(",")[4]); // setting team and player attributes

			player.setName(str.split(",")[0]);
			player.setAge(Integer.parseInt(str.split(",")[1]));
			player.setHeight(Integer.parseInt(str.split(",")[2]));
			player.setPosition(str.split(",")[3]);
			player.setMoral(Integer.parseInt(str.split(",")[5])); //

			int strength = Integer.parseInt(str.split(",")[6]);
			player.setStrength(strength);

			// controlling 5 major team to evaluate value of players
			if ((team.getName().equals("Fenerbahce")) || (team.getName().equals("Trabzonspor"))
					|| (team.getName().equals("Istanbul Basaksehir")) || (team.getName().equals("Besiktas"))
					|| (team.getName().equals("Galatasaray"))) {
				player.setValue(((double) strength / 2) / 10);
			} else {
				player.setValue(((double) strength / 4) / 10);
			}

			boolean isHere = false;

			for (FootballTeam t : teams) {
				if (t.getName().equals(team.getName())) {
					player.setTeam(t);
					isHere = true;
					t.addPlayer(player);
					break;
				}
			}

			if (isHere == false) {
				team.setMAX_PLAYER(40);
				team.setMIN_PLAYER(20);
				teams.add(team);
				player.setTeam(teams.get(teams.indexOf(team)));
				teams.get(teams.indexOf(team)).addPlayer(player);
			}

		}

		createBudgetAllTeams(teams); // creating budget for each team.

	}

	@Override
	public void createFixture() {

		// reference : http://www.nuhazginoglu.com/tag/fixture-algorithm/

		int n = teams.size();
		int randIndex = new Random().nextInt(n);
		FootballTeam constantTeam = teams.get(randIndex);
		teams.remove(constantTeam);
		n = teams.size();

		// in this algorithm, we delete the first element and call it constantTeam
		for (int i = 0; i < n; i++) {
			matches.add(new Match(constantTeam, teams.get(0)));
			// in every iteration constantTeam plays with first element of array.

			for (int j = 0; j < (n - 1) / 2; j++) {
				// in the inner loop teams match with symmetric order in array
				// for ex: 1:6, 2:5, 3:4 and so on until the half.
				matches.add(new Match(teams.get(j + 1), teams.get(n - j - 1)));
				System.out.println(teams.get(j + 1).getName() + "-" + teams.get(n - j - 1).getName());
			}

			// We right-shift the array.
			FootballTeam shiftTeam = teams.get(n - 1);
			FootballTeam prevTeam = teams.get(n - 1);
			for (int j = 0; j < n; j++) {
				shiftTeam = teams.get(j);
				teams.set(j, prevTeam);
				prevTeam = shiftTeam;
			}
		}

		teams.add(constantTeam);

		// Shuffling every week in itself.
		ArrayList<Match> tempMatches = new ArrayList<>();
		tempMatches.add(matches.get(0));
		for (int j = 1; j < matches.size(); j++) {
			tempMatches.add(matches.get(j));
			if (j % 10 == 0) {
				Collections.shuffle(tempMatches.subList(j - teams.size() / 2, j));
			}
		}

		matches.clear(); // obtaining last form of the fixture.
		matches.addAll(tempMatches);
		tempMatches.clear();
		int s = 0;

		for (Match m : matches) { // adding second-term of league. vice-versa matches.
			tempMatches.add(new Match(m.getAwayTeam(), m.getHostTeam()));
		}
		matches.addAll(tempMatches);

	}

	@Override
	public boolean isBudgetEnough(FootballTeam team) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSizeofTeamConvenient(FootballTeam team) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void makeTransfer(Player player, FootballTeam newTeam) {
		if (newTeam.getPlayers().size() < 40 && player.getTeam().getPlayers().size() > 20
				&& newTeam.getBudget() > player.getValue()) {
			newTeam.addPlayer(player);
			player.getTeam().removePlayer(player);
			newTeam.setBudget(newTeam.getBudget() - player.getValue());
		}
	}

	public ArrayList<FootballTeam> getTeams() {
		return teams;
	}

	public String[] getTeamString() {

		String[] teams = new String[getTeams().size()];

		for (int i = 0; i < teams.length; i++) {
			teams[i] = getTeams().get(i).getName();
		}

		return teams;
	}

	public FootballTeam getTeam(String team) {

		for (FootballTeam t : teams) {
			if (t.getName().equals(team)) {
				return t;
			}
		}
		return null;
	}

	public void setTeams(ArrayList<FootballTeam> teams) {
		this.teams = teams;
	}

	public ArrayList<Match> getMatches() {
		return matches;
	}

	public void setMatches(ArrayList<Match> matches) {
		this.matches = matches;
	}
}
