package jadvise.gui;

import jadvise.objects.MySQLAccount;
import jadvise.objects.PortFormat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author David Pirraglia
 */
public class Login extends JFrame {

	private final String TITLE = "JAdvise - Connect to MySQL Database";

	public static void main(String[] args) {
		new Login();
	}

	private static final MouseAdapter rightClickPaste = new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) {
				((JTextField) e.getSource()).paste();
			}
		}
	};

	public Login() {
		setTitle(TITLE);
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
		passField.setMinimumSize(passField.getPreferredSize());
		passField.addMouseListener(rightClickPaste);

		JLabel ipDomainLabel = new JLabel("IP/Domain:  ");
		JTextField ipDomainField = new JTextField("127.0.0.1", 20);
		ipDomainField.setMinimumSize(ipDomainField.getPreferredSize());
		ipDomainField.addMouseListener(rightClickPaste);

		JLabel portLabel = new JLabel("Port:  ");
		JTextField portField = new JFormattedTextField(new PortFormat());
		portField.setMinimumSize(portField.getPreferredSize());
		portField.addMouseListener(rightClickPaste);
		portField.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (portField.getText().isEmpty()) {
					portField.setText("3306");
				}
			}
		});
		portField.setText("3306");
		portField.setColumns(20);

		JLabel tableLabel = new JLabel("Table:  ");
		JTextField tableField = new JTextField("jadvise", 20);
		tableField.setMinimumSize(tableField.getPreferredSize());
		tableField.addMouseListener(rightClickPaste);

		JButton loginButton = new JButton("Login");
		rootPane.setDefaultButton(loginButton);
		loginButton.addActionListener(e -> {
			new JAdvise(new MySQLAccount(
					userField.getText(),
					new String(passField.getPassword()),
					ipDomainField.getText(),
					Integer.parseUnsignedInt(portField.getText()),
					tableField.getText()
			));
			dispose();
		});

		GridBagConstraints cs = new GridBagConstraints();
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.weighty = 1;

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
		add(ipDomainField, cs);

		cs.gridx = 0;
		cs.gridy++;
		cs.gridwidth = 1;
		add(portLabel, cs);
		cs.gridx = 1;
		cs.gridwidth = 2;
		add(portField, cs);

		cs.gridx = 0;
		cs.gridy++;
		cs.gridwidth = 1;
		add(tableLabel, cs);
		cs.gridx = 1;
		cs.gridwidth = 2;
		add(tableField, cs);

		cs.gridx = 0;
		cs.gridy++;
		cs.gridwidth = 3;
		add(loginButton, cs);

		setVisible(true);
	}
}
