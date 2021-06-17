import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtFieldUsername;
	private static LoginGUI frame;
	User user;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginGUI() throws IOException {

		final FootballLeague lg;
		lg = FootballLeague.getLeague();
		lg.createAllTeams();
		lg.createFixture();

		// Creating components
		JLabel lblTitle = new JLabel("Sports League Managament System");
		JLabel lblSelectTeam = new JLabel("Select Team");
		JLabel lblUsername = new JLabel("Username");
		ImageIcon imgLogin = new ImageIcon("sportoto.png");
		JLabel lblImage = new JLabel(imgLogin);
		JButton btnLogin = new JButton("Login");
		JComboBox comboBoxSelectTeam = new JComboBox(lg.getTeamString());

		user = new User(); // init user
		user.setTeam(lg.getTeam(comboBoxSelectTeam.getItemAt(0).toString()));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 673, 663);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblUsername.setBounds(39, 63, 71, 26);
		contentPane.add(lblUsername);

		// txt field
		txtFieldUsername = new JTextField();
		txtFieldUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (txtFieldUsername.getText().equals("")) {
					btnLogin.setEnabled(false);
				} else {
					btnLogin.setEnabled(true);
				}
				user.setUsername(txtFieldUsername.getText());
			}
		});
		txtFieldUsername.setBounds(142, 65, 181, 23);
		contentPane.add(txtFieldUsername);
		txtFieldUsername.setColumns(10);

		// login button
		btnLogin.setEnabled(false);
		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					// Building league and creating teams, players.
					new MainGUI(lg, user);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.setVisible(false);

			}
		});
		btnLogin.setBounds(516, 256, 121, 45);
		contentPane.add(btnLogin);

		lblTitle.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblTitle.setBounds(29, 10, 294, 45);
		contentPane.add(lblTitle);

		// combobox
		comboBoxSelectTeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.setTeam(
						lg.getTeam(comboBoxSelectTeam.getItemAt(comboBoxSelectTeam.getSelectedIndex()).toString()));
			}
		});
		comboBoxSelectTeam.setBounds(459, 66, 162, 21);
		contentPane.add(comboBoxSelectTeam);

		
		lblSelectTeam.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		lblSelectTeam.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectTeam.setBounds(459, 15, 162, 34);
		contentPane.add(lblSelectTeam);

		lblImage.setBounds(0, 124, 500, 500);
		contentPane.add(lblImage);

	}
}
