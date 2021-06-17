
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

	private static FootballLeague league;
	
	private ArrayList<FootballTeam> teams;
	private ArrayList<Match> matches;
	private Date startDate;

	private FootballLeague() {
		matches = new ArrayList<>();
		teams = new ArrayList<>();
		startDate = new Date(2020, 9, 11);
	}

	public static FootballLeague getLeague() {
		if(league==null) {
			league=new FootballLeague();
		}
		return league;
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
			team.determinePower(team.getPlayers());
			team.determineFirstEleven();
		}
	}

	@Override

	public void createAllTeams() throws IOException {
		// File reading
		FileInputStream fstream = new FileInputStream("players.txt");
		DataInputStream dstream = new DataInputStream(fstream);
		BufferedReader breader = new BufferedReader(new InputStreamReader(dstream, "UTF-8"));
		String str = "";

		int lineCounter = 1;
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
				team.setImgIcon(Integer.toString(lineCounter));
		
				lineCounter++;
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
		int dayCounter = 255;
		Random rnd = new Random();
		FootballTeam constantTeam = teams.get(rnd.nextInt(n));
		teams.remove(constantTeam);
		n = teams.size();

		int dateMatchCounter = 0;
		int year = 2020;

		// in this algorithm, we delete the first element and call it constantTeam
		for (int i = 0; i < n; i++) {

			matches.add(new Match(constantTeam, teams.get(0), null));
			// in every iteration constantTeam plays with first element of array.

			for (int j = 0; j < (n - 1) / 2; j++) {
				// in the inner loop teams match with symmetric order in array
				// for ex: 1:6, 2:5, 3:4 and so on until the half.
				matches.add(new Match(teams.get(j + 1), teams.get(n - j - 1), null));

			}

			// We right-shift the array.
			FootballTeam shiftTeam = teams.get(n - 1);
			FootballTeam prevTeam = teams.get(n - 1);
			for (int j = 0; j < n; j++) {
				shiftTeam = teams.get(j);
				teams.set(j, prevTeam);
				prevTeam = shiftTeam;
			}

			dateMatchCounter = 0;

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
			tempMatches.add(new Match(m.getAwayTeam(), m.getHostTeam(), getExactDate(60, 2020)));
		}
		matches.addAll(tempMatches);

		// Setting date for each match..
		int dateRepeatCount = 0;
		for (int j = 0; j < matches.size(); j++) {

			if (j % 10 == 0) {
				dateRepeatCount = rnd.nextInt(4) + 1;
				dateMatchCounter = 0;
				dayCounter += 7;
			}

			if (dayCounter > 365) {
				dayCounter = dayCounter % 365;
				year++;
			}
			matches.get(j).setDate(getExactDate(dayCounter, year));

			if (dateMatchCounter == dateRepeatCount) { // same day match limit reached
				dayCounter++;
				dateMatchCounter = 0; // for new week
				dateRepeatCount = rnd.nextInt(4) + 1;
			} else {
				dateMatchCounter++;
			}
		}

	}

	public void makeMatch(Match match) {

		Random rnd = new Random();
		int powerTeam1 = rnd.nextInt(match.getHostTeam().getTeamStrength()) + 10;
		int powerTeam2 = rnd.nextInt(match.getAwayTeam().getTeamStrength()) + 10;

		int team1Score = 0;
		int team2Score = 0;

		if (Math.abs(powerTeam1 - powerTeam2) <= 10) { // tie

			team1Score = rnd.nextInt(4);
			team2Score = team1Score;
			match.getHostTeam().increaseTieCount();
			match.getAwayTeam().increaseTieCount();

		} else {
			if (powerTeam1 > powerTeam2) {
				team1Score = rnd.nextInt(powerTeam1 / 10) + 1;
				team2Score = rnd.nextInt(team1Score);
				match.getHostTeam().increaseWinCount();
				match.getAwayTeam().increaseLossCount();

			} else {
				team2Score = rnd.nextInt(powerTeam2 / 10) + 1;
				team1Score = rnd.nextInt(team2Score);
				match.getAwayTeam().increaseWinCount();
				match.getHostTeam().increaseLossCount();
			}
		}

		match.setHostTeamScore(team1Score);
		match.setAwayTeamScore(team2Score);

		match.getHostTeam().increaseGoalScored(team1Score); // increase goal score of teams
		match.getAwayTeam().increaseGoalScored(team2Score);

		match.getHostTeam().increaseAssistCount(team1Score); // increase assist score of teams
		match.getAwayTeam().increaseAssistCount(team2Score);

		match.getHostTeam().increaseGoalTaken(team2Score); // increase taken goal of teams
		match.getAwayTeam().increaseGoalTaken(team1Score);

		for (int i = 0; i < team1Score; i++) { // define which players goal score for host team

			Goal goal = new Goal();

			int index = rnd.nextInt(12) + 1;
			int indexAssist = rnd.nextInt(10);
			int whichPLayerScore;
			int whichPLayerAssist;
			String playerNameOfScorer;
			String playerNameOfAssister;
			if (index <= 2) { // if goal scorer position def
				whichPLayerScore = rnd.nextInt(4);
				goal.setScorePlayer(match.getHostTeam().getDEFplayers().get(whichPLayerScore));
				playerNameOfScorer = match.getHostTeam().getDEFplayers().get(whichPLayerScore).getName();
			} else if (index > 2 && index <= 6) { // if goal scorer position mid
				whichPLayerScore = rnd.nextInt(4);
				goal.setScorePlayer(match.getHostTeam().getMIDplayers().get(whichPLayerScore));
				playerNameOfScorer = match.getHostTeam().getMIDplayers().get(whichPLayerScore).getName();
			} else { // if goal scorer position fw
				whichPLayerScore = rnd.nextInt(2);
				goal.setScorePlayer(match.getHostTeam().getFWplayers().get(whichPLayerScore));
				playerNameOfScorer = match.getHostTeam().getFWplayers().get(whichPLayerScore).getName();
			}

			// goal.setScorePlayer(match.getHostTeam().DEFplayers.get(whichPLayerScore));
			while (match.getHostTeam().getFirstEleven().get(indexAssist).getName()
					.equals(goal.getScorePlayer().getName())
					|| match.getHostTeam().getFirstEleven().get(indexAssist).getPosition().equals("GK")) {

				indexAssist = rnd.nextInt(10);
			}
			goal.setAssistPlayer(match.getHostTeam().getFirstEleven().get(indexAssist));

			playerNameOfAssister = match.getHostTeam().getFirstEleven().get(indexAssist).getName();

			for (Player p : match.getHostTeam().getPlayers()) {
				if (p.getName().equals(playerNameOfScorer)) {
					p.increaseScoreCount();
				} else if (p.getName().equals(playerNameOfAssister)) {
					p.incraseAssistCount();
				}
			}

			match.addGoals(goal);
		}

		for (int i = 0; i < team2Score; i++) { // define which players goal score for away team

			Goal goal = new Goal();

			int index = rnd.nextInt(12) + 1;
			int indexAssist = rnd.nextInt(11);
			int whichPLayerScore;
			int whichPLayerAssist;
			String playerNameOfScorer;
			String playerNameOfAssister;
			if (index <= 2) { // if goal scorer position def
				whichPLayerScore = rnd.nextInt(4);
				goal.setScorePlayer(match.getAwayTeam().getDEFplayers().get(whichPLayerScore));
				playerNameOfScorer = match.getAwayTeam().getDEFplayers().get(whichPLayerScore).getName();
			} else if (index > 2 && index <= 6) { // if goal scorer position mid
				whichPLayerScore = rnd.nextInt(4);
				goal.setScorePlayer(match.getAwayTeam().getMIDplayers().get(whichPLayerScore));
				playerNameOfScorer = match.getAwayTeam().getMIDplayers().get(whichPLayerScore).getName();
			} else { // if goal scorer position fw
				whichPLayerScore = rnd.nextInt(2);
				goal.setScorePlayer(match.getAwayTeam().getFWplayers().get(whichPLayerScore));
				playerNameOfScorer = match.getAwayTeam().getFWplayers().get(whichPLayerScore).getName();
			}

			// goal.setScorePlayer(match.getAwayTeam().DEFplayers.get(whichPLayerScore));
			while (match.getAwayTeam().getFirstEleven().get(indexAssist).getName()
					.equals(goal.getScorePlayer().getName())
					|| match.getAwayTeam().getFirstEleven().get(indexAssist).getPosition().equals("GK")) {

				indexAssist = rnd.nextInt(11);
			}
			goal.setAssistPlayer(match.getAwayTeam().getFirstEleven().get(indexAssist));
			playerNameOfAssister = match.getAwayTeam().getFirstEleven().get(indexAssist).getName();

			for (Player p : match.getAwayTeam().getPlayers()) {
				if (p.getName().equals(playerNameOfScorer)) {
					p.increaseScoreCount();
				} else if (p.getName().equals(playerNameOfAssister)) {
					p.incraseAssistCount();
				}
			}

			match.addGoals(goal);
		}

		int average = 0;
		if (match.getGoals().size() != 0)
			average = 90 / match.getGoals().size();
		int goalMinutes = 0;
		int increase = 0;

		Collections.shuffle(match.getGoals());
		for (int i = 0; i < match.getGoals().size(); i++) {
			goalMinutes = rnd.nextInt(average) + increase;
			increase += average;
			match.getGoals().get(i).setGoalMinutes(goalMinutes);

		}

		System.out.println("ma� sonucu ");
		System.out.println(match.getHostTeam().getName() + " score: " + match.getHostTeamScore());
		System.out.println(match.getAwayTeam().getName() + " score: " + match.getAwayTeamScore());

		for (Goal g : match.getGoals()) {
			System.out.println();
			System.out.println("gol� atan: " + g.getScorePlayer().getName() + " "
					+ g.getScorePlayer().getTeam().getName() + " " + g.getScorePlayer().getPosition());
			System.out.println(
					"asist yapan: " + g.getAssistPlayer().getName() + " " + g.getAssistPlayer().getTeam().getName());
			System.out.println("gol� dakikas�: " + g.getGoalMinutes());
			System.out.println(" **************     ");
		}

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

	public Date getExactDate(int dayOfTheYear, int year) {

		ArrayList<Integer> monthDays = new ArrayList<>();
		int sum = 0;
		Collections.addAll(monthDays, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

		if (year % 4 == 0)
			monthDays.set(1, 29);

		int month = 0;
		while (sum < dayOfTheYear) {
			sum += monthDays.get(month);
			month++;
		}

		sum = 0;
		for (int i = 0; i < month - 1; i++) {
			sum += monthDays.get(i);
		}

		return new Date(year, month, dayOfTheYear - sum);

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
