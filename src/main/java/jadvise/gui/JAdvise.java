package jadvise.gui;

import jadvise.guitools.JScrollableSpinner;
import jadvise.guitools.PrebuiltDialogs;
import jadvise.objects.MySQLAccount;
import jadvise.objects.Student;
import jadvise.objects.StudentDatabase;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.sql.SQLException;
import java.util.stream.IntStream;

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
	private final JMenuItem exportToCSVItem;
	private final JMenuItem exitItem;
	private final JMenuItem addStudentItem;
	private final JMenuItem addRandomStudentsItem;
	private final JMenuItem editStudentItem;
	private final JMenuItem removeStudentItem;
	private final JMenuItem removeAllStudentsItem;
	private final JMenuItem aboutItem;

	// File Chooser
	private JFileChooser exportToCSVSaveDialog;

	// Table
	private final JTable table;
	public static TableRowSorter<TableModel> tableSorter;
	private final JScrollPane tableScrollPane;
	private int previousColumn = -1;

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
			"<html><b>Home Phone</b></html>",
			"<html><b>Cell Phone</b></html>",
			"<html><b>Email Address</b></html>",
			"<html><b>Courses Taken</b></html>",
			"<html><b>Current Courses</b></html>",
			"<html><b>Courses Needed</b></html>",
			"<html><b>Notes</b></html>",
	};

	public static final int[] COLUMN_WIDTHS = {
			80, // ID Number
			100, // First Name
			30, // MI
			200, // Last Name
			40, // GPA
			125, // Home Campus
			60, // Major
			120, // House Number
			200, // Street
			100, // City
			40, // State
			50, // Zip
			125, // Home Phone Number
			125, // Cell Phone Number
			300, // Email Address
			200, // Courses Taken
			200, // Current Courses
			200, // Courses Needed
			200 // Notes
	};

	// Search Area
	private final JPanel searchPanel = new JPanel(new BorderLayout());
	private final JTextField searchField;
	private final JLabel searchLabel;
	private final JButton clearSearch;

	protected static JRootPane jAdvise;

	public JAdvise(MySQLAccount account) throws SQLException, ClassNotFoundException {

		// Load Data
		sd = new StudentDatabase(account);
		sd.loadData();

		// Create JAdvise GUI
		jAdvise = getRootPane();
		setTitle(TITLE);
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setResizable(true);

		// Pressing escape will ask if you'd like to quit
		jAdvise.registerKeyboardAction(
				actionEvent -> {
					if (PrebuiltDialogs.showConfirmDialog(
							this,
							"Are you sure you want to quit?",
							TITLE
					)) {
						System.exit(0);
					};
				},
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		// Search Area
		searchLabel = new JLabel(" Search:  ");
		searchField = new JTextField();
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search();
			}
		});
		clearSearch = new JButton("\u274C"); // X
		clearSearch.addActionListener(actionEvent -> {
			searchField.setText("");
			tableSorter.setSortKeys(null);
		});

		searchPanel.add(searchLabel, BorderLayout.WEST);
		searchPanel.add(searchField, BorderLayout.CENTER);
		searchPanel.add(clearSearch, BorderLayout.EAST);
		add(searchPanel, BorderLayout.NORTH);

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

		setUpSorter(table);

		// Clicking on columns will now sort by ASCENDING, DESCENDING, and UNSORTED
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				// Do nothing if not left click
				if (event.getButton() != MouseEvent.BUTTON1) return;

				Point point = event.getPoint();
				int column = table.columnAtPoint(point);
				if (tableSorter.getSortKeys().get(0).getSortOrder()
						.toString().charAt(0) == 'A'
						&& column == previousColumn) {
					tableSorter.setSortKeys(null);
					previousColumn = -1;
				} else {
					previousColumn = column;
				}
			}
		});

		// Double clicking on a row will edit it
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() == 2
						&& mouseEvent.getButton() == MouseEvent.BUTTON1) {
					editStudentAction();
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
				editStudentAction();
			}
		});

		// Pressing delete with a row selected will delete it
		table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
				"DELETE"
		);
		table.getActionMap().put("DELETE", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeStudentAction();
			}
		});

		tableScrollPane = new JScrollPane(table);
		tableScrollPane.getHorizontalScrollBar().setUnitIncrement(15);
		add(tableScrollPane, BorderLayout.CENTER);
		sd.setTable(table);

		// MenuBar
		menuBar = new JMenuBar();

		// File Menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		printItem = new JMenuItem("Print");
		printItem.setMnemonic(KeyEvent.VK_P);
		printItem.addActionListener(actionEvent -> {
			try {
				table.print();
			} catch (PrinterException ex) {
				PrebuiltDialogs.showErrorDialog(jAdvise, "Can't print table.");
			}
		});
		fileMenu.add(printItem);

		exportToCSVItem = new JMenuItem("Export to CSV");
		exportToCSVItem.setMnemonic(KeyEvent.VK_E);

		exportToCSVSaveDialog = new JFileChooser();
		exportToCSVSaveDialog.setDialogTitle(TITLE);
		exportToCSVSaveDialog.setAcceptAllFileFilterUsed(false);
		exportToCSVSaveDialog.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.getName().endsWith(".csv") || f.isDirectory();
			}

			@Override
			public String getDescription() {
				return "*.csv";
			}
		});

		exportToCSVItem.addActionListener(actionEvent -> {
			int userSelection;
			do {
				// Show the save dialog to the user and get their response
				userSelection = exportToCSVSaveDialog.showSaveDialog(jAdvise);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File csvFile = exportToCSVSaveDialog.getSelectedFile();

					// If the file already exists, ask to overwrite it
					if (csvFile.exists() &&
							!PrebuiltDialogs.showYesNoDialog(
							jAdvise,
							"Do you want to overwrite this file?",
							TITLE
					)) {
						continue;
					}

					// Get the path of where to save the CSV file
					String csvPath = csvFile.getAbsolutePath();

					// Add the file extension ".csv" if it doesn't exist
					if (!csvPath.endsWith(".csv")) {
						csvPath += ".csv";
					}

					// Save the CSV file
					sd.exportToCSV(csvPath);
					return;
				}
			} while (userSelection != JFileChooser.CANCEL_OPTION);
		});
		fileMenu.add(exportToCSVItem);

		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(actionEvent -> System.exit(0));
		fileMenu.add(exitItem);

		// Edit Menu
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);

		addStudentItem = new JMenuItem("Add Student");
		addStudentItem.setMnemonic(KeyEvent.VK_A);
		addStudentItem.addActionListener(ae -> {
			new AddEditStudent(jAdvise, sd);
			if (!searchField.getText().isEmpty()) {
				search();
			}
		});
		editMenu.add(addStudentItem);

		addRandomStudentsItem = new JMenuItem("Add Random Students");
		addRandomStudentsItem.setMnemonic(KeyEvent.VK_N);
		addRandomStudentsItem.addActionListener(actionEvent -> {
			JScrollableSpinner spinner = new JScrollableSpinner(
					new SpinnerNumberModel(1, 1, 100, 1),
					true
			);
			if (JOptionPane.showConfirmDialog(
					jAdvise,
					new JComponent[] {
							new JLabel("How many random students?"),
							spinner
					},
					TITLE,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE
			) == JOptionPane.OK_OPTION) {
				for (int i = 0; i < (int) spinner.getValue(); i++) {
					sd.addStudent(new Student());
				}
				sd.updateTable();
				try {
					sd.saveData();
				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				if (!searchField.getText().isEmpty()) {
					search();
				}
			}
		});
		editMenu.add(addRandomStudentsItem);

		editStudentItem = new JMenuItem("Edit Selected Student");
		editStudentItem.setMnemonic(KeyEvent.VK_E);
		editStudentItem.addActionListener(actionEvent -> editStudentAction());
		editMenu.add(editStudentItem);

		removeStudentItem = new JMenuItem("Remove Selected Student");
		removeStudentItem.setMnemonic(KeyEvent.VK_R);
		removeStudentItem.addActionListener(actionEvent -> removeStudentAction());
		editMenu.add(removeStudentItem);

		removeAllStudentsItem = new JMenuItem("Remove All Students");
		removeAllStudentsItem.addActionListener(actionEvent -> {
			if (PrebuiltDialogs.showConfirmDialog(
					jAdvise,
					"Are you sure you want to remove all students?",
					TITLE
			)) {
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
		editMenu.add(removeAllStudentsItem);

		// Help Menu
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		aboutItem = new JMenuItem("About");
		aboutItem.setMnemonic(KeyEvent.VK_A);
		aboutItem.addActionListener(actionEvent -> JOptionPane.showMessageDialog(
				jAdvise,
				"Version: 1.0\nDate: 12/15/13\nCreated by David Pirraglia",
				TITLE,
				JOptionPane.INFORMATION_MESSAGE
		));
		helpMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

		setVisible(true);
	}

	private void editStudentAction() {
		if (table.getSelectedRow() < 0) {
			PrebuiltDialogs.showErrorDialog(jAdvise, "No student is selected.");
			return;
		}
		new AddEditStudent(jAdvise, sd, getIndex(table), sd.getStudent(
				(String) table.getModel().getValueAt(getIndex(table), 0)
		));
		if (!searchField.getText().isEmpty()) {
			search();
		}
	}

	private void removeStudentAction() {
		if (table.getSelectedRow() < 0) {
			PrebuiltDialogs.showErrorDialog(jAdvise, "No student is selected.");
			return;
		}
		if (PrebuiltDialogs.showConfirmDialog(
				jAdvise,
				"Are you sure you want to delete this student?",
				TITLE
		)) {
			sd.removeStudent(getIndex(table));
			sd.updateTable();
			if (!searchField.getText().isEmpty()) {
				search();
			}
			try {
				sd.saveData();
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.exit(1);
			} catch (ClassNotFoundException ex) {
				System.exit(1);
			}
		}
	}

	/**
	 * Used for testing. Primary main method is located in {@link jadvise.Demo}
	 */
	public static void main(String[] args) {
		try {
			// Set Java L&F to Nimbus
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

			// Create JAdvise GUI
			new JAdvise(new MySQLAccount("root", ""));
		} catch (SQLException | ClassNotFoundException | IllegalAccessException
				| InstantiationException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static void setUpSorter(JTable table) {
		TableModel tableModel = table.getModel();
		tableSorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(tableSorter);
	}

	public static void resetAllColumnWidths(JTable table) {
		for (int i = 0; i < COLUMN_WIDTHS.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(COLUMN_WIDTHS[i]);
		}
	}

	public static int getIndex(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}

	public void search() {
		try {
			RowFilter<TableModel, Object> rf = RowFilter.regexFilter(
					"(?i)" + searchField.getText(),
					IntStream.rangeClosed(0, COLUMN_NAMES.length).toArray()
			);
			tableSorter.setRowFilter(rf);
		} catch (java.util.regex.PatternSyntaxException e) {
			e.printStackTrace();
		}
	}
}
