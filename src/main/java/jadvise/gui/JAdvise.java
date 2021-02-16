package jadvise.gui;

import jadvise.guitools.ErrorMessagePane;
import jadvise.guitools.JScrollableSpinner;
import jadvise.objects.MySQLAccount;
import jadvise.objects.Student;
import jadvise.objects.StudentDatabase;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.sql.SQLException;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
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
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileFilter;

/**
 * @author David Pirraglia
 */
public class JAdvise extends JFrame {

	private static final String TITLE = "JAdvise";
	private final StudentDatabase sd;

	// Menu
	private final JMenuBar menuBar;
	private final JMenu fileMenu;
	private final JMenu editMenu;
	private final JMenu helpMenu;
	private final JMenuItem printItem;
	private final JMenuItem clearItem;
	private final JMenuItem exportToCSVItem;
	private final JMenuItem exitItem;
	private final JMenuItem addStudentItem;
	private final JMenuItem addRandomStudentsItem;
	private final JMenuItem editStudentItem;
	private final JMenuItem removeStudentItem;
	private final JMenuItem aboutItem;

	// File Chooser
	private JFileChooser exportToCSV;

	// Table
	private final JTable table;
	private final JScrollPane tableScrollPane;

	public static final String[] COLUMN_NAMES = {
			"<html><b>ID Number</b></html>",
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
			"<html><b>Email Address</b></html>"
	};

	public static void resetAllColumnWidths(JTable table) {
		for (int i = 0; i < columnWidths.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
		}
	}

	public static final int[] columnWidths = {
			100, 100, 30, 200, 40,
			125, 60, 120, 200, 100,
			40, 60, 175, 175, 400
	};

	// Search Area
	private final JPanel searchPanel = new JPanel(new BorderLayout());
	private final JTextField searchField;
	private final JLabel searchLabel;

	protected static Container jAdvise;

	public JAdvise(MySQLAccount account) {
		jAdvise = getRootPane();
		setTitle(TITLE);
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setResizable(true);

		// Load Data
		sd = new StudentDatabase(account);
		try {
			sd.loadData();
		} catch (com.mysql.jdbc.exceptions.jdbc4.CommunicationsException ce) {
			ErrorMessagePane.showErrorMessage(jAdvise, "Can't connect to database.");
		} catch (SQLException ex) {
			ErrorMessagePane.showErrorMessage(jAdvise, "SQL Error: " + ex.getMessage());
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ErrorMessagePane.showErrorMessage(jAdvise,
					"Something went wrong that you probably won't understand (ClassNotFoundException).");
			System.exit(1);
		}

		// Table
		String[][] rowData = sd.getTableData();
		table = new JTable(rowData, COLUMN_NAMES) {
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // Disallow the editing of any cell
			}
		};
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		resetAllColumnWidths(table);

		// Double clicking on a row will edit it
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
					new AddEditStudent(jAdvise, sd, sd.getStudent(
							(String) table.getModel().getValueAt(table.getSelectedRow(), 0)
					));
				}
			}
		});

		// Pressing enter with a row selected will edit it
		// https://stackoverflow.com/questions/9091208/jtable-enter-key
		table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"\n"
		);
		table.getActionMap().put("\n", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					new AddEditStudent(jAdvise, sd, sd.getStudent(
							(String) table.getModel().getValueAt(table.getSelectedRow(), 0)
					));
				}
			}
		});

		tableScrollPane = new JScrollPane(table);
		tableScrollPane.getHorizontalScrollBar().setUnitIncrement(15);
		add(tableScrollPane, BorderLayout.CENTER);
		sd.setTable(table);

		// MenuBar
		menuBar = new JMenuBar();

		// File Menu
		printItem = new JMenuItem("Print");
		printItem.setMnemonic(KeyEvent.VK_P);
		printItem.addActionListener(actionEvent -> {
			try {
				table.print();
			} catch (PrinterException ex) {
				ErrorMessagePane.showErrorMessage(jAdvise, "Can't print table.");
			}
		});

		clearItem = new JMenuItem("Clear");
		clearItem.setMnemonic(KeyEvent.VK_C);
		clearItem.addActionListener(actionEvent -> {
			if (JOptionPane.showConfirmDialog(
					getRootPane(),
					"Are you sure you want to clear the table?",
					TITLE,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
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
		});

		exportToCSVItem = new JMenuItem("Export to CSV");
		exportToCSVItem.setMnemonic(KeyEvent.VK_E);
		exportToCSVItem.addActionListener(actionEvent -> {
			exportToCSV = new JFileChooser();
			exportToCSV.setDialogTitle(TITLE);
			exportToCSV.setAcceptAllFileFilterUsed(false);
			exportToCSV.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					return f.getName().endsWith(".csv") || f.isDirectory();
				}

				@Override
				public String getDescription() {
					return "*.csv";
				}
			});
			int userSelection = exportToCSV.showSaveDialog(jAdvise);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				String csvFile = exportToCSV.getSelectedFile().getAbsolutePath();
				if (!csvFile.endsWith(".csv")) {
					csvFile += ".csv";
				}
				sd.exportToCSV(csvFile);
			}
		});

		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(actionEvent -> System.exit(0));

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(printItem);
		fileMenu.add(clearItem);
		fileMenu.add(exportToCSVItem);
		fileMenu.add(exitItem);

		// Edit Menu
		addStudentItem = new JMenuItem("Add Student");
		addStudentItem.setMnemonic(KeyEvent.VK_A);
		addStudentItem.addActionListener(ae -> new AddEditStudent(jAdvise, sd, null));

		addRandomStudentsItem = new JMenuItem("Add Random Students");
		addRandomStudentsItem.setMnemonic(KeyEvent.VK_N);
		addRandomStudentsItem.addActionListener(actionEvent -> {
			JScrollableSpinner spinner = new JScrollableSpinner(
					new SpinnerNumberModel(1, 1, 100, 1)
			);
			int option = JOptionPane.showConfirmDialog(
					jAdvise,
					new JComponent[] {spinner},
					"How many students?",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE
			);
			if (option == JOptionPane.OK_OPTION) {
				for (int i = 0; i < (int) spinner.getValue(); i++) {
					sd.addStudent(new Student());
				}
				sd.updateTable();
				try {
					sd.saveData();
				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		editStudentItem = new JMenuItem("Edit Selected Student");
		editStudentItem.setMnemonic(KeyEvent.VK_E);
		editStudentItem.addActionListener(actionEvent -> {
			if (table.getSelectedRow() < 0) {
				ErrorMessagePane.showErrorMessage(jAdvise, "No student is selected.");
			} else {
				new AddEditStudent(jAdvise, sd, sd.getStudent(
						(String) table.getModel().getValueAt(table.getSelectedRow(), 0)
				));
			}
		});

		removeStudentItem = new JMenuItem("Remove Selected Student");
		removeStudentItem.setMnemonic(KeyEvent.VK_R);
		removeStudentItem.addActionListener(actionEvent -> {
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
		});

		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		editMenu.add(addStudentItem);
		editMenu.add(addRandomStudentsItem);
		editMenu.add(editStudentItem);
		editMenu.add(removeStudentItem);

		// Help Menu
		aboutItem = new JMenuItem("About");
		aboutItem.setMnemonic(KeyEvent.VK_A);
		aboutItem.addActionListener(actionEvent -> {
			JOptionPane.showMessageDialog(
					jAdvise,
					"Version: 1.0\nDate: 12/15/13\nCreated By David Pirraglia",
					TITLE,
					JOptionPane.INFORMATION_MESSAGE
			);
		});

		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

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

		setVisible(true);
	}

	public static void main(String[] args) {
		new JAdvise(new MySQLAccount("root", "usbw"));
	}
}
