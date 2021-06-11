import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
	int week = 1;

	public MainGUI(FootballLeague lg,User user) throws IOException {

		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(500, 500, 649, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(null);

		// panels for tab screen
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(5, 5, 789, 461);
		contentPane.add(tabbedPane);

		JPanel panelFixture = new JPanel();
		tabbedPane.addTab("Fixture", null, panelFixture, null);

		String[] columnNames = { "HOST", "SCORE", "SCORE", "AWAY" };

		data = new String[lg.getTeams().size() / 2][4];
		for (int i = 0; i < lg.getTeams().size() / 2; i++) {
			data[i][0] = lg.getMatches().get(i).getHostTeam().getName();
			data[i][1] = "0";
			data[i][2] = "0";
			data[i][3] = lg.getMatches().get(i).getAwayTeam().getName();

		}
		panelFixture.setLayout(null);

		table = new JTable(data, columnNames);
		table.setBounds(1, 25, 450, 0);

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);

		for (int i = 0; i < columnNames.length; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(dtcr);
		}

		panelFixture.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 10, 619, 236);
		panelFixture.add(scrollPane);

		JLabel lblWeek = new JLabel("Week " + week);
		lblWeek.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeek.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
		lblWeek.setBounds(253, 268, 117, 48);
		panelFixture.add(lblWeek);

		JButton btnPrevWeek = new JButton("<- Previous Week");
		btnPrevWeek.setFont(new Font("Arial", Font.BOLD, 14));
		btnPrevWeek.setBounds(26, 269, 168, 48);
		panelFixture.add(btnPrevWeek);
		btnPrevWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (week > 1) {
					week--;
					lblWeek.setText("Week " + week);

					for (int i = 0; i < lg.getTeams().size() / 2; i++) {
						data[i][0] = lg.getMatches().get(i + (10 * (week - 1))).getHostTeam().getName();
						data[i][1] = "0";
						data[i][2] = "0";
						data[i][3] = lg.getMatches().get(i + 10 * (week - 1)).getAwayTeam().getName();
						repaint();

					}
				}
			}
		});
		btnPrevWeek.setBackground(Color.ORANGE);

		JButton btnNextWeek = new JButton("Next Week ->");
		btnNextWeek.setFont(new Font("Arial", Font.BOLD, 14));
		btnNextWeek.setBounds(427, 269, 187, 48);
		panelFixture.add(btnNextWeek);
		btnNextWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (week < ((lg.getTeams().size() - 1) * 2)) {
					week++;
					lblWeek.setText("Week " + week);

					for (int i = 0; i < lg.getTeams().size() / 2; i++) {
						data[i][0] = lg.getMatches().get(i + (10 * (week - 1))).getHostTeam().getName();
						data[i][1] = "0";
						data[i][2] = "0";
						data[i][3] = lg.getMatches().get(i + (10 * (week - 1))).getAwayTeam().getName();
						repaint();

					}
				}
			}
		});
		btnNextWeek.setBackground(Color.GREEN);

		JPanel panelProfile = new JPanel();
		tabbedPane.addTab("My Profile", null, panelProfile, null);
		panelProfile.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:"+user.getUsername());
		lblUsername.setFont(new Font("Calibri Light", Font.BOLD, 14));
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsername.setBounds(24, 34, 271, 29);
		panelProfile.add(lblUsername);
		
		JLabel lblBudget = new JLabel("Budget:"+user.getTeam().getBudget());
		lblBudget.setFont(new Font("Calibri Light", Font.BOLD, 14));
		lblBudget.setBounds(24, 88, 271, 29);
		panelProfile.add(lblBudget);
		
		JLabel lblTeam = new JLabel("My Team:"+user.getTeam().getName());
		lblTeam.setFont(new Font("Calibri Light", Font.BOLD, 14));
		lblTeam.setBounds(24, 138, 271, 29);
		panelProfile.add(lblTeam);
		
		JLabel lblRanking = new JLabel("My Ranking:");
		lblRanking.setFont(new Font("Calibri Light", Font.BOLD, 14));
		lblRanking.setBounds(24, 192, 271, 29);
		panelProfile.add(lblRanking);

		JPanel panelStandings = new JPanel();
		tabbedPane.addTab("Standings", null, panelStandings, null);

		JPanel panelArrangeTeam = new JPanel();
		tabbedPane.addTab("Arrange Team", null, panelArrangeTeam, null);

		JPanel panelPlayerMarket = new JPanel();
		tabbedPane.addTab("Player Market", null, panelPlayerMarket, null);

		System.out.println();

	}
}
