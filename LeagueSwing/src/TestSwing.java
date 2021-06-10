import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollBar;
import javax.swing.JLabel;
import java.awt.Font;

public class TestSwing extends JFrame {

	private JPanel contentPane;
	private JTable table;
	String[][] data;
	int week = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestSwing frame = new TestSwing();
					frame.setPreferredSize(new Dimension(500, 350));
					frame.pack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */

	public TestSwing() throws IOException {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		FootballLeague lg = new FootballLeague(); // Building league and creating teams, players.
		lg.createAllTeams();
		lg.createFixture();
		contentPane.setLayout(null);

		// panels for tab screen
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(5, 5, 474, 276);
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
		panelFixture.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 10, 452, 184);
		panelFixture.add(scrollPane);

		JLabel lblWeek = new JLabel("Week " + week);
		lblWeek.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		lblWeek.setBounds(204, 221, 81, 13);
		panelFixture.add(lblWeek);	
		
		
		JButton btnPrevWeek = new JButton("<- Previous Week");
		btnPrevWeek.setBounds(10, 218, 142, 21);
		panelFixture.add(btnPrevWeek);
		btnPrevWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(week>1) {
					week--;
					lblWeek.setText("Week " + week);
					System.out.println(week);

					for (int i = 0; i < lg.getTeams().size() / 2; i++) {
						data[i][0] = lg.getMatches().get(i+(10*(week-1))).getHostTeam().getName();
						data[i][1] = "0";
						data[i][2] = "0";
						data[i][3] = lg.getMatches().get(i+10*(week-1)).getAwayTeam().getName();
						repaint();

					}
				}
			}
		});
		btnPrevWeek.setBackground(Color.ORANGE);

		JButton btnNextWeek = new JButton("Next Week ->");
		btnNextWeek.setBounds(305, 218, 145, 21);
		panelFixture.add(btnNextWeek);
		btnNextWeek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(week<((lg.getTeams().size()-1)*2)) {
					week++;
					System.out.println(week);
					lblWeek.setText("Week " + week);

					for (int i = 0; i < lg.getTeams().size() / 2; i++) {
						data[i][0] = lg.getMatches().get(i + (10 * (week-1))).getHostTeam().getName();
						data[i][1] = "0";
						data[i][2] = "0";
						data[i][3] = lg.getMatches().get(i+(10 * (week-1))).getAwayTeam().getName();
						repaint();

					}
				}
			}
		});
		btnNextWeek.setBackground(Color.GREEN);

		

		JPanel panelProfile = new JPanel();
		tabbedPane.addTab("My Profile", null, panelProfile, null);

		JPanel panelStandings = new JPanel();
		tabbedPane.addTab("Standings", null, panelStandings, null);

		JPanel panelArrangeTeam = new JPanel();
		tabbedPane.addTab("Arrange Team", null, panelArrangeTeam, null);

		JPanel panelPlayerMarket = new JPanel();
		tabbedPane.addTab("Player Market", null, panelPlayerMarket, null);

		System.out.println();

	}
}
