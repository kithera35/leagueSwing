import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class MainGUI extends JFrame {

	private JPanel contentPane;
	private JTable tableFixture;
	String[][] data;
	String[][] dataForScoreBoard;
	String[][] dataCurrentPlayer;
	String[][] dataReservePlayer;
	private JTable tableForScoreBoard;
	ArrayList<FootballTeam> sortedTeams;
	int week = 1;

	public ArrayList<FootballTeam> TeamsSortingForSB(ArrayList<FootballTeam> unSorted) {
		int max = 0;
		ArrayList<FootballTeam> sorted = new ArrayList();
		FootballTeam tempTeam = null;
		while (!unSorted.isEmpty()) {
			max = -1;
			for (FootballTeam i : unSorted) {
				if (i.getPoint() > max) {
					max = i.getPoint();
					tempTeam = i;
				}
			}
			for (FootballTeam i : unSorted) {
				if (i.getPoint() == max) {

					if ((i.getGoalScored() - i.getGoalTaken()) == (tempTeam.getGoalScored()
							- tempTeam.getGoalTaken())) {

						if (i.getGoalScored() == tempTeam.getGoalScored()) {
							if (i.getName().compareTo(tempTeam.getName()) < 0) {
								max = i.getPoint();
								tempTeam = i;
							}
						}

						else if (i.getGoalScored() > tempTeam.getGoalScored()) {
							max = i.getPoint();
							tempTeam = i;
						}

					}

					else if ((i.getGoalScored() - i.getGoalTaken()) > (tempTeam.getGoalScored()
							- tempTeam.getGoalTaken())) {
						max = i.getPoint();
						tempTeam = i;
					}

				}
			}
			unSorted.remove(tempTeam);
			sorted.add(tempTeam);
		}
		return sorted;
	}

	public void updateScoreBoard(FootballLeague lg) { // function for updating scoreboard after pressing play button
		for (int i = 0; i < lg.getTeams().size() / 2; i++) {
			Date date = lg.getMatches().get(i + (10 * (week - 1))).getDate();
			data[i][0] = date.getDay() + "." + date.getMonth() + "." + date.getYear();
			data[i][1] = lg.getMatches().get(i + (10 * (week - 1))).getHostTeam().getName();
			data[i][2] = Integer.toString(lg.getMatches().get(i + (10 * (week - 1))).getHostTeamScore());
			data[i][3] = Integer.toString(lg.getMatches().get(i + (10 * (week - 1))).getAwayTeamScore());
			data[i][4] = lg.getMatches().get(i + (10 * (week - 1))).getAwayTeam().getName();

		}

	}

	public void updateStandings(ArrayList<FootballTeam> SortedTeams) {
		for (int j = 0; j < SortedTeams.size(); j++) {
			dataForScoreBoard[j][1] = SortedTeams.get(j).getName();
			dataForScoreBoard[j][2] = Integer.toString(week);
			dataForScoreBoard[j][0] = Integer.toString(j + 1);
			dataForScoreBoard[j][3] = Integer.toString(SortedTeams.get(j).getPoint());
			dataForScoreBoard[j][4] = Integer.toString(SortedTeams.get(j).getWinCount());
			dataForScoreBoard[j][5] = Integer.toString(SortedTeams.get(j).getTieCount());
			dataForScoreBoard[j][6] = Integer.toString(SortedTeams.get(j).getLossCount());
			dataForScoreBoard[j][7] = Integer.toString(SortedTeams.get(j).getGoalScored());
			dataForScoreBoard[j][8] = Integer.toString(SortedTeams.get(j).getGoalTaken());
			dataForScoreBoard[j][9] = Integer
					.toString(SortedTeams.get(j).getGoalScored() - SortedTeams.get(j).getGoalTaken());
		}
	}

	public void updateFirstElevenTable(String[][] dataCurrentPlayer, User user) {
		FootballTeam team = user.getTeam();
		team.removeFirstEleven();		
		team.addFirstEleven(team.getFWplayers());
		team.addFirstEleven(team.getMIDplayers());
		team.addFirstEleven(team.getDEFplayers());
		team.addFirstEleven(team.getGoalKeeper());					

		ArrayList<Player> eleven = team.getFirstEleven();
		for (int i = 0; i < eleven.size(); i++) { // fill the table with players in first eleven
			dataCurrentPlayer[i][0] = eleven.get(i).getName();
			dataCurrentPlayer[i][1] = eleven.get(i).getPosition();
			dataCurrentPlayer[i][2] = Integer.toString(eleven.get(i).getStrength());
		}
	}

	public void updateReservePlayerTable(String[][] dataReservePlayer, User user) {
		FootballTeam team = user.getTeam();
		ArrayList<Player> eleven = team.getFirstEleven();
		
		team.clearAllPlayers();		
		team.addAllPlayers(team.getAllFWplayers());
		team.addAllPlayers(team.getAllMIDplayers());
		team.addAllPlayers(team.getAllDEFplayers());
		team.addAllPlayers(team.getAllGKplayers());	
		
		int index = 0;

		for (int i = 0; i < team.getPlayers().size(); i++) {
			Player player = team.getPlayers().get(i);
			if (!eleven.contains(player)) { // players except the first eleven

				dataReservePlayer[index][0] = player.getName();
				dataReservePlayer[index][1] = player.getPosition();
				dataReservePlayer[index][2] = Integer.toString(player.getStrength());
				index++;
			}

		}

	}

	public MainGUI(FootballLeague lg, User user) throws IOException {
		sortedTeams = new ArrayList<FootballTeam>();

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(500, 500, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(null);

		// panels for tab screen
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(5, 5, 1261, 678);
		contentPane.add(tabbedPane);

		JPanel panelFixture = new JPanel();
		tabbedPane.addTab("Fixture", null, panelFixture, null);

		String[] columnNames = { "DATE", "HOST", "SCORE", "SCORE", "AWAY" };

		data = new String[lg.getTeams().size() / 2][5];
		updateScoreBoard(lg);
		panelFixture.setLayout(null);

		tableFixture = new JTable(data, columnNames);
		tableFixture.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableFixture.setBounds(1, 25, 450, 0);
		tableFixture.setRowHeight(64);
		tableFixture.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < columnNames.length; i++) {
			tableFixture.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}

		panelFixture.add(tableFixture);

		JScrollPane scrollForFixtureTable = new JScrollPane(tableFixture);
		scrollForFixtureTable.setBounds(0, 10, 1246, 505);
		panelFixture.add(scrollForFixtureTable);

		JLabel lblWeek = new JLabel("Week " + week);
		lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeek.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
		lblWeek.setBounds(580, 538, 117, 48);
		panelFixture.add(lblWeek);
		JButton btnPlay = new JButton("Play Week");
		JButton btnPrevWeek = new JButton("<- Previous Week");
		JButton btnNextWeek = new JButton("Next Week ->");
		btnPrevWeek.setFont(new Font("Arial", Font.BOLD, 14));
		btnPrevWeek.setBounds(174, 539, 168, 48);
		panelFixture.add(btnPrevWeek);
		btnPrevWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (week > 1) {
					btnPlay.setEnabled(false);
					btnNextWeek.setEnabled(true);
					week--;
					lblWeek.setText("Week " + week);

					for (int i = 0; i < lg.getTeams().size() / 2; i++) {
						Date date = lg.getMatches().get(i + (10 * (week - 1))).getDate();
						data[i][0] = date.getDay() + "." + date.getMonth() + "." + date.getYear();
						data[i][1] = lg.getMatches().get(i + (10 * (week - 1))).getHostTeam().getName();
						data[i][2] = Integer.toString(lg.getMatches().get(i + (10 * (week - 1))).getHostTeamScore());
						data[i][3] = Integer.toString(lg.getMatches().get(i + (10 * (week - 1))).getAwayTeamScore());
						data[i][4] = lg.getMatches().get(i + 10 * (week - 1)).getAwayTeam().getName();
						repaint();
					}
				}
			}
		});
		btnPrevWeek.setBackground(Color.ORANGE);

		btnNextWeek.setFont(new Font("Arial", Font.BOLD, 14));
		btnNextWeek.setBounds(930, 539, 187, 48);
		btnNextWeek.setEnabled(false);
		panelFixture.add(btnNextWeek);
		btnNextWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (week < ((lg.getTeams().size() - 1) * 2)) {

					week++;
					if (lg.getMatches().get(0 + (10 * (week - 1))).isPlayed()) {
						btnPlay.setEnabled(false);
					} else {
						btnPlay.setEnabled(true);
					}

					if (btnPlay.isEnabled()) {
						btnNextWeek.setEnabled(false);
					}
					lblWeek.setText("Week " + week);

					for (int i = 0; i < (lg.getTeams().size() / 2); i++) { // updating table elements
						Date date = lg.getMatches().get(i + (10 * (week - 1))).getDate();
						data[i][0] = date.getDay() + "." + date.getMonth() + "." + date.getYear();
						data[i][1] = lg.getMatches().get(i + (10 * (week - 1))).getHostTeam().getName();
						data[i][2] = Integer.toString(lg.getMatches().get(i + (10 * (week - 1))).getHostTeamScore());
						data[i][3] = Integer.toString(lg.getMatches().get(i + (10 * (week - 1))).getAwayTeamScore());
						data[i][4] = lg.getMatches().get(i + (10 * (week - 1))).getAwayTeam().getName();
						repaint();

					}
				}
			}
		});
		btnNextWeek.setBackground(Color.GREEN);

		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < lg.getTeams().size() / 2; i++) { // making weekly matches
					lg.makeMatch(lg.getMatches().get(((week - 1) * lg.getTeams().size() / 2) + i));
				}

				btnNextWeek.setEnabled(true);
				btnPlay.setEnabled(false);
				updateScoreBoard(lg);
				sortedTeams = TeamsSortingForSB(sortedTeams); // sort scoreboard
				updateStandings(sortedTeams);

				repaint();

			}
		});

		btnPlay.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnPlay.setBounds(551, 596, 187, 45);
		panelFixture.add(btnPlay);

		JPanel panelProfile = new JPanel();
		tabbedPane.addTab("My Profile", null, panelProfile, null);
		panelProfile.setLayout(null);

		JLabel lblUsername = new JLabel("Username:" + user.getUsername());
		lblUsername.setFont(new Font("Calibri Light", Font.BOLD, 14));
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setBounds(24, 34, 271, 29);
		panelProfile.add(lblUsername);

		JLabel lblBudget = new JLabel("Budget:" + user.getTeam().getBudget());
		lblBudget.setFont(new Font("Calibri Light", Font.BOLD, 14));
		lblBudget.setBounds(24, 88, 271, 29);
		panelProfile.add(lblBudget);

		JLabel lblTeam = new JLabel("My Team:" + user.getTeam().getName());
		lblTeam.setFont(new Font("Calibri Light", Font.BOLD, 14));
		lblTeam.setBounds(24, 138, 271, 29);
		panelProfile.add(lblTeam);

		JLabel lblRanking = new JLabel("My Ranking:");
		lblRanking.setFont(new Font("Calibri Light", Font.BOLD, 14));
		lblRanking.setBounds(24, 192, 271, 29);
		panelProfile.add(lblRanking);

		JLabel lblTeamLogo = new JLabel(user.getTeam().getImgIcon());
		lblTeamLogo.setBounds(251, 10, 500, 500);
		panelProfile.add(lblTeamLogo);

		JPanel panelStandings = new JPanel();
		tabbedPane.addTab("Standings", null, panelStandings, null);

		panelStandings.setLayout(null);

		for (FootballTeam i : lg.getTeams()) {
			sortedTeams.add(i);
		}

		String[] ScoreBoardcolumn = { "NO", "TEAM", "WEEK", "POINT", "WIN", "TIE", "DEFEAT", "GOAL SCORED",
				"GOAL TAKEN", "AVERAGE" };
		dataForScoreBoard = new String[sortedTeams.size()][ScoreBoardcolumn.length];

		updateStandings(sortedTeams);

		tableForScoreBoard = new JTable(dataForScoreBoard, ScoreBoardcolumn);
		tableForScoreBoard.setBounds(1, 25, 1244, 0);
		tableForScoreBoard.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableForScoreBoard.setRowHeight(64);
		tableForScoreBoard.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelStandings.setLayout(null);

		DefaultTableCellRenderer dtcrforScoreBoard = new DefaultTableCellRenderer();
		dtcrforScoreBoard.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < ScoreBoardcolumn.length; i++) {
			tableForScoreBoard.getColumnModel().getColumn(i).setCellRenderer(dtcrforScoreBoard);
		}

		panelStandings.add(tableForScoreBoard);

		JScrollPane scrollforScoreBoard = new JScrollPane(tableForScoreBoard);
		scrollforScoreBoard.setBounds(0, 10, 1246, 505);
		panelStandings.add(scrollforScoreBoard);

		JPanel panelArrangeTeam = new JPanel();
		tabbedPane.addTab("Arrange Team", null, panelArrangeTeam, null);
		panelArrangeTeam.setLayout(null);

		JLabel lblCurrentPlayers = new JLabel("Current Players");
		lblCurrentPlayers.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCurrentPlayers.setBounds(89, 38, 141, 13);
		panelArrangeTeam.add(lblCurrentPlayers);

		JLabel lblReservePlayers = new JLabel("Reserve  Players");
		lblReservePlayers.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblReservePlayers.setBounds(467, 38, 136, 13);
		panelArrangeTeam.add(lblReservePlayers);

		JButton btnChangePlayer = new JButton("Change Selected Players");

		btnChangePlayer.setBackground(new Color(0, 255, 127));
		btnChangePlayer.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnChangePlayer.setBounds(377, 502, 253, 98);
		panelArrangeTeam.add(btnChangePlayer);
			
		JLabel lblShowTeamStrength = new JLabel("Total strength:"+" "+Integer.toString(user.getTeam().getTeamStrength()));
		lblShowTeamStrength.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblShowTeamStrength.setBounds(56, 520, 185, 67);
		panelArrangeTeam.add(lblShowTeamStrength);
		
		JTable tableCurrentPlayers;
		JTable tableReservePlayers;
		
		

		String[] playerDataColumn = { "NAME", "POSITION", "STRENGTH" };
		dataCurrentPlayer = new String[user.getTeam().getFirstEleven().size()][playerDataColumn.length];
		dataReservePlayer = new String[user.getTeam().getPlayers().size()
				- user.getTeam().getFirstEleven().size()][playerDataColumn.length];

		updateFirstElevenTable(dataCurrentPlayer, user);
		updateReservePlayerTable(dataReservePlayer, user);

		JScrollPane scrollForCurrentPlayers = new JScrollPane();
		
		scrollForCurrentPlayers.setBounds(10, 85, 279, 335);
		panelArrangeTeam.add(scrollForCurrentPlayers);

		tableCurrentPlayers = new JTable(dataCurrentPlayer, playerDataColumn);
		tableCurrentPlayers.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollForCurrentPlayers.setViewportView(tableCurrentPlayers);

		JScrollPane scrollForReservePlayers = new JScrollPane();
		scrollForReservePlayers.setBounds(377, 85, 284, 335);
		panelArrangeTeam.add(scrollForReservePlayers);

		tableReservePlayers = new JTable(dataReservePlayer, playerDataColumn);
		tableReservePlayers.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollForReservePlayers.setViewportView(tableReservePlayers);
		
		btnChangePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// If user did not select two players to exchange
				if(tableCurrentPlayers.getSelectedRow()==-1 ||tableReservePlayers.getSelectedRow()==-1 ) {
					JOptionPane.showMessageDialog(null, "You MUST select two players from each list !", "Error",
							JOptionPane.ERROR_MESSAGE);
					
					return;  // quit function
				}
				FootballTeam team = user.getTeam();		
				Player outPlayer = team.getPlayerByName(dataCurrentPlayer[tableCurrentPlayers.getSelectedRow()][0]);
				Player inPlayer = team.getPlayerByName(dataReservePlayer[tableReservePlayers.getSelectedRow()][0]);
				
				
				if (!outPlayer.getPosition().equals(inPlayer.getPosition())) {
					JOptionPane.showMessageDialog(null, "You cannot change players with different positions !", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				} else {

					team.getFirstEleven().remove(outPlayer);  // players to be out and in
					team.getFirstEleven().add(inPlayer);
						
					// setting up position arrays of the team
					if (inPlayer.getPosition().equals("DEF")) {  
						team.getDEFplayers().add(inPlayer);
					}
					else if (inPlayer.getPosition().equals("MID")) {
						team.getMIDplayers().add(inPlayer);
					}
					else if (inPlayer.getPosition().equals("FW")) {
						team.getFWplayers().add(inPlayer);
					}
					else {
						team.setGoalKeeper(inPlayer);
					}
					
					// out player position arrangement
					if (outPlayer.getPosition().equals("DEF")) {  
						team.removeDEF(outPlayer);
					}
					else if (outPlayer.getPosition().equals("MID")) {
						team.removeMID(outPlayer);
					}
					else if (outPlayer.getPosition().equals("FW")) {
						team.removeFW(outPlayer);
					}
									
					// clearing first eleven to rearrange sorting by position	
					
					updateFirstElevenTable(dataCurrentPlayer, user);
					updateReservePlayerTable(dataReservePlayer, user);
					team.determinePower(team.getFirstEleven());
					lblShowTeamStrength.setText("Total strength:"+" "+Integer.toString(user.getTeam().getTeamStrength()));
					repaint();
				}

			}
		});

		JLabel lblPlayerChangeLogo = new JLabel(new ImageIcon("playerChange.png"));
		lblPlayerChangeLogo.setBounds(671, 10, 585, 631);
		panelArrangeTeam.add(lblPlayerChangeLogo);
		
		

		JPanel panelPlayerMarket = new JPanel();
		tabbedPane.addTab("Player Market", null, panelPlayerMarket, null);

		System.out.println();

	}
}
