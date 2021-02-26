package jadvise.gui;

import jadvise.guitools.PortTextField;
import jadvise.guitools.PrebuiltDialogs;
import jadvise.objects.MySQLAccount;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

/**
 * @author David Pirraglia
 */
public class Login extends JFrame {

	public Login() {
		setTitle("JAdvise - Connect to a MySQL Database");
		setSize(400, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setLocationRelativeTo(null);
		setResizable(false);

		JLabel userLabel = new JLabel("Username:  ");
		JTextField userField = new JTextField(20);
		userField.setMinimumSize(userField.getPreferredSize());
		userField.addMouseListener(rightClickPaste);

		JLabel passLabel = new JLabel("Password:  ");
		JPasswordField passField = new JPasswordField(20);
		passField.setEchoChar('\u2022');
		passField.setMinimumSize(passField.getPreferredSize());
		passField.addMouseListener(rightClickPaste);

		JLabel ipDomainLabel = new JLabel("IP/Domain:  ");
		JTextField ipDomainField = new JTextField("127.0.0.1", 20);
		ipDomainField.setMinimumSize(ipDomainField.getPreferredSize());
		ipDomainField.addMouseListener(rightClickPaste);

		JLabel portLabel = new JLabel("Port:  ");
		JTextField portField = new PortTextField("3306");
		portField.setMinimumSize(portField.getPreferredSize());
		portField.addMouseListener(rightClickPaste);
		portField.setColumns(20);

		JLabel databaseLabel = new JLabel("Database:  ");
		JTextField databaseField = new JTextField("jadvise", 20);
		databaseField.setMinimumSize(databaseField.getPreferredSize());
		databaseField.addMouseListener(rightClickPaste);

		JButton loginButton = new JButton("Login");
		rootPane.setDefaultButton(loginButton);
		loginButton.addActionListener(actionEvent -> {
			try {
				new JAdvise(new MySQLAccount(
						userField.getText(),
						new String(passField.getPassword()),
						ipDomainField.getText(),
						Integer.parseUnsignedInt(portField.getText()),
						databaseField.getText()
				));
				dispose();
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				PrebuiltDialogs.showErrorDialog(this, e.getMessage());
			}
		});

		// Pressing escape will ask if the user would like to quit
		rootPane.registerKeyboardAction(
				actionEvent -> PrebuiltDialogs.showQuitDialog(this),
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.HORIZONTAL;
		grid.weighty = 1;

		grid.gridx = 0;
		grid.gridy = 0;
		grid.gridwidth = 1;
		add(userLabel, grid);
		grid.gridx = 1;
		grid.gridwidth = 2;
		add(userField, grid);

		grid.gridx = 0;
		grid.gridy++;
		grid.gridwidth = 1;
		add(passLabel, grid);
		grid.gridx = 1;
		grid.gridwidth = 2;
		add(passField, grid);

		grid.gridx = 0;
		grid.gridy++;
		grid.gridwidth = 1;
		add(ipDomainLabel, grid);
		grid.gridx = 1;
		grid.gridwidth = 2;
		add(ipDomainField, grid);

		grid.gridx = 0;
		grid.gridy++;
		grid.gridwidth = 1;
		add(portLabel, grid);
		grid.gridx = 1;
		grid.gridwidth = 2;
		add(portField, grid);

		grid.gridx = 0;
		grid.gridy++;
		grid.gridwidth = 1;
		add(databaseLabel, grid);
		grid.gridx = 1;
		grid.gridwidth = 2;
		add(databaseField, grid);

		grid.gridx = 0;
		grid.gridy++;
		grid.gridwidth = 3;
		add(loginButton, grid);

		setVisible(true);
	}

	private static final MouseAdapter rightClickPaste = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				((JTextField) e.getSource()).paste();
			}
		}
	};

	/**
	 * Used for testing. Primary main method is located in {@link jadvise.Demo}
	 */
	public static void main(String[] args) {
		new Login();
	}
}
