package jadvise.gui;

import jadvise.guitools.ErrorMessagePane;
import jadvise.objects.MySQLAccount;
import jadvise.objects.PortFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		portField.addPropertyChangeListener("value", evt -> {
			if (portField.getText().isEmpty()) {
				portField.setText("3306");
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
		loginButton.addActionListener(actionEvent -> {
			try {
				new JAdvise(new MySQLAccount(
						userField.getText(),
						new String(passField.getPassword()),
						ipDomainField.getText(),
						Integer.parseUnsignedInt(portField.getText()),
						tableField.getText()
				));
				dispose();
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				ErrorMessagePane.showErrorDialog(this, e.getMessage());
			}
		});

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
		add(tableLabel, grid);
		grid.gridx = 1;
		grid.gridwidth = 2;
		add(tableField, grid);

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
