package jadvise.gui;

import jadvise.objects.MySQLAccount;

import javax.swing.*;
import java.awt.*;

/**
 * @author David Pirraglia
 */
public class Login extends JFrame {

	private final String TITLE = "JAdvise - Connect to MySQL Database";

	protected static Container login;

	public static void main(String[] args) {
		new Login();
	}

	public Login() {
		login = getRootPane();
		setTitle(TITLE);
		setSize(400, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setLocationRelativeTo(null);
		setResizable(false);

		JLabel userLabel = new JLabel("Username:  ");
		JTextField userField = new JTextField(20);

		JLabel passLabel = new JLabel("Password:  ");
		JPasswordField passField = new JPasswordField(20);

		JLabel ipDomainLabel = new JLabel("IP/Domain:  ");
		JTextField ipDomainTextField = new JTextField("127.0.0.1", 20);

		JLabel portLabel = new JLabel("Port:  ");
		JTextField portTextField = new JTextField("3307", 20);

		JLabel tableLabel = new JLabel("Table:  ");
		JTextField tableTextField = new JTextField("jadvise", 20);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(e -> {
			new JAdvise(new MySQLAccount(
					userField.getText(),
					new String(passField.getPassword()),
					ipDomainTextField.getText(),
					Integer.parseUnsignedInt(portTextField.getText()),
					tableTextField.getText()
			));
			dispose();
		});

		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;

		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		add(userLabel, cs);
		cs.gridx = 1;
		cs.gridwidth = 2;
		add(userField, cs);

		cs.gridx = 0;
		cs.gridy++;
		cs.gridwidth = 1;
		add(passLabel, cs);
		cs.gridx = 1;
		cs.gridwidth = 2;
		add(passField, cs);

		cs.gridx = 0;
		cs.gridy++;
		cs.gridwidth = 1;
		add(ipDomainLabel, cs);
		cs.gridx = 1;
		cs.gridwidth = 2;
		add(ipDomainTextField, cs);

		cs.gridx = 0;
		cs.gridy++;
		cs.gridwidth = 1;
		add(portLabel, cs);
		cs.gridx = 1;
		cs.gridwidth = 2;
		add(portTextField, cs);

		cs.gridx = 0;
		cs.gridy++;
		cs.gridwidth = 1;
		add(tableLabel, cs);
		cs.gridx = 1;
		cs.gridwidth = 2;
		add(tableTextField, cs);

		cs.gridx = 0;
		cs.gridy++;
		cs.gridwidth = 3;
		add(loginButton, cs);

		setVisible(true);
	}
}
