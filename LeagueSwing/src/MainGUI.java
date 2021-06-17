import static java.util.Comparator.comparing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class MainGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	String[][] data;
	String[][] dataForScoreBoard;
	private JTable tableForScoreBoard;
	ArrayList<FootballTeam> sortedTeams;
	int week = 1;

	public ArrayList<FootballTeam> TeamsSortingForSB(ArrayList<FootballTeam> unSorted) {
		int max=0;
		ArrayList<FootballTeam> sorted = new ArrayList();
		FootballTeam tempTeam = null;
		while(!unSorted.isEmpty()) {
			max=-1;
			for (FootballTeam i : unSorted) {
				if(i.getPoint()>max) {
					max = i.getPoint();
					tempTeam = i;
				}
			}
			for (FootballTeam i : unSorted) {
				if(i.getPoint()==max) {
					
					if((i.getGoalScored()-i.getGoalTaken())==(tempTeam.getGoalScored()-tempTeam.getGoalTaken())) {
						
						if(i.getGoalScored()==tempTeam.getGoalScored()) {
							if(i.getName().compareTo(tempTeam.getName())<0) {
								max = i.getPoint();
								tempTeam = i;
							}
						}
						
						else if(i.getGoalScored()>tempTeam.getGoalScored()) {
							max = i.getPoint();
							tempTeam = i;
						}
						
					}
					
					else if((i.getGoalScored()-i.getGoalTaken())>(tempTeam.getGoalScored()-tempTeam.getGoalTaken())) {
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

		table = new JTable(data, columnNames);
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setBounds(1, 25, 450, 0);
		table.setRowHeight(64);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < columnNames.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}

		panelFixture.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 10, 1246, 505);
		panelFixture.add(scrollPane);

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
				sortedTeams=TeamsSortingForSB(sortedTeams);	// sort scoreboard
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
		dataForScoreBoard = new String[sortedTeams.size()][10];

		updateStandings(sortedTeams);

		tableForScoreBoard = new JTable(dataForScoreBoard, ScoreBoardcolumn);
		tableForScoreBoard.setBounds(1, 25, 1244, 0);
		tableForScoreBoard.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tableForScoreBoard.setRowHeight(64);
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

		JPanel panelPlayerMarket = new JPanel();
		tabbedPane.addTab("Player Market", null, panelPlayerMarket, null);

		System.out.println();

	}
}
