package jadvise.gui;

import jadvise.guitools.ErrorMessagePane;
import jadvise.objects.StudentDatabase;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * @author David Pirraglia
 */
public class JAdvise extends JFrame {

	private final String TITLE = "JAdvise";
	private final StudentDatabase sd;

	// Menu
	private final JMenuBar menuBar;
	private final JMenu fileMenu;
	private final JMenu editMenu;
	private final JMenu helpMenu;
	private final JMenuItem printItem;
	private final JMenuItem clearItem;
	private final JMenuItem exitItem;
	private final JMenuItem addStudentItem;
	private final JMenuItem editStudentItem;
	private final JMenuItem removeStudentItem;
	private final JMenuItem aboutItem;

	// Table
	private final JTable table;
	private final JScrollPane tableScrollPane;

	// Search Area
	private final JPanel searchPanel = new JPanel(new BorderLayout());
	private final JTextField searchField;
	private final JLabel searchLabel;

	protected static Container jAdvise;

	public static void main(String[] args) {
		jAdvise = new JAdvise();
	}

	public JAdvise() {
		jAdvise = getRootPane();
		setTitle(TITLE);
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setResizable(true);

		// MenuBar
		menuBar = new JMenuBar();

		//File Menu
		printItem = new JMenuItem("Print");
		printItem.setMnemonic(KeyEvent.VK_P);
		printItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					table.print();
				} catch (PrinterException ex) {
					ErrorMessagePane.showErrorMessage(jAdvise, "Can't print table.");
				}
			}
		});

		clearItem = new JMenuItem("Clear");
		clearItem.setMnemonic(KeyEvent.VK_C);
		clearItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (0 == JOptionPane.showConfirmDialog(getRootPane(),
						"Are you sure you want to clear the table?",
						TITLE, JOptionPane.WARNING_MESSAGE,
						JOptionPane.OK_CANCEL_OPTION)) {
					try {
						sd.clearData();
						sd.updateTable();
					} catch (SQLException ex) {
						ex.printStackTrace();
						System.exit(1);
					} catch (ClassNotFoundException ex) {
						System.exit(1);
					}
				}
			}
		});

		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(printItem);
		fileMenu.add(clearItem);
		fileMenu.add(exitItem);

		//Edit Menu
		addStudentItem = new JMenuItem("Add Student");
		addStudentItem.setMnemonic(KeyEvent.VK_A);
		addStudentItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				AddStudent addStudent = new AddStudent(jAdvise, sd, null);
			}
		});

		editStudentItem = new JMenuItem("Edit Selected Student");
		editStudentItem.setMnemonic(KeyEvent.VK_E);
		editStudentItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (table.getSelectedRow() < 0) {
					ErrorMessagePane.showErrorMessage(jAdvise, "No student is selected.");
				} else {
					AddStudent addStudent = new AddStudent(
							jAdvise, sd, sd.getStudent((String)
									table.getModel().getValueAt(
											table.getSelectedRow(), 0)));
//					sd.findStudent(TITLE)
//					String id = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
//					System.out.println("ID:" + id);
//					AddStudent addStudent = new AddStudent(jAdvise, sd, sd.getStudent(sd.findStudent(id)));
				}
			}
		});

		removeStudentItem = new JMenuItem("Remove Selected Student");
		removeStudentItem.setMnemonic(KeyEvent.VK_R);
		removeStudentItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				sd.removeStudent(table.getModel().getValueAt(table.getSelectedRow(), 0) + "");
				sd.updateTable();
				try {
					sd.saveData();
				} catch (SQLException ex) {
					ex.printStackTrace();
					System.exit(1);
				} catch (ClassNotFoundException ex) {
					System.exit(1);
				}
			}
		});

		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		editMenu.add(addStudentItem);
		editMenu.add(editStudentItem);
		editMenu.add(removeStudentItem);

		//Help Menu
		aboutItem = new JMenuItem("About");
		aboutItem.setMnemonic(KeyEvent.VK_A);
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showMessageDialog(jAdvise,
						"Version: 1.0\nDate: 12/15/13\nCreated By David Pirraglia",
						TITLE, JOptionPane.INFORMATION_MESSAGE);
			}
		});

		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

		// Load Data
		sd = new StudentDatabase();
		try {
			sd.loadData();
		} catch (com.mysql.jdbc.exceptions.jdbc4.CommunicationsException ce) {
			ErrorMessagePane.showErrorMessage(jAdvise, "Can't connect to database.");
			System.exit(1);
		} catch (SQLException ex) {
			ErrorMessagePane.showErrorMessage(jAdvise, "SQL Error: " + ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		} catch (ClassNotFoundException ex) {
			ErrorMessagePane.showErrorMessage(jAdvise,
					"Something went wrong that you probably won't understand (ClassNotFoundException).");
			System.exit(1);
		}

		//Table
		String columnNames[] = {"<html><b>ID Number</b></html>",
			"<html><b>First Name</b></html>",
			"<html><b>MI</b></html>",
			"<html><b>Last Name</b></html>",
			"<html><b>GPA</b></html>",
			"<html><b>Home Campus</b></html>",
			"<html><b>Major</b></html>",
			"<html><b>House Number</b></html>",
			"<html><b>Street</b></html>",
			"<html><b>City</b></html>",
			"<html><b>State</b></html>",
			"<html><b>Zip</b></html>",
			"<html><b>Home Phone Number</b></html>",
			"<html><b>Cell Phone Number</b></html>",
			"<html><b>Email Address</b></html>"};
		String rowData[][] = sd.getTableData();
		table = new JTable(rowData, columnNames) {
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; //Disallow the editing of any cell
			}
		};
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getColumnModel().getColumn(5).setPreferredWidth(125);
		table.getColumnModel().getColumn(6).setPreferredWidth(60);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		table.getColumnModel().getColumn(8).setPreferredWidth(200);
		table.getColumnModel().getColumn(12).setPreferredWidth(175);
		table.getColumnModel().getColumn(13).setPreferredWidth(175);
		table.getColumnModel().getColumn(14).setPreferredWidth(400);
		tableScrollPane = new JScrollPane(table);
		tableScrollPane.getHorizontalScrollBar().setUnitIncrement(15);
		add(tableScrollPane, BorderLayout.CENTER);

		sd.setTable(table);

		// Search Area
		searchLabel = new JLabel(" Search:  ");
		searchField = new JTextField();
		searchField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent ke) {
			}

			@Override
			public void keyPressed(KeyEvent ke) {
			}

			@Override
			public void keyReleased(KeyEvent ke) {
				sd.search(searchField.getText());
				sd.updateTable();
			}
		});
		searchPanel.add(searchLabel, BorderLayout.WEST);
		searchPanel.add(searchField, BorderLayout.CENTER);
		add(searchPanel, BorderLayout.NORTH);

		// Bottom Buttons Area?
		setVisible(true);
	}
}
