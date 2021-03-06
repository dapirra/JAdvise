package jadvise.gui;

import jadvise.exceptions.id.DuplicateIDException;
import jadvise.guitools.JScrollableSpinner;
import jadvise.guitools.PrebuiltDialogs;
import jadvise.objects.MySQLAccount;
import jadvise.objects.Student;
import jadvise.objects.StudentDatabase;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.stream.IntStream;

import static jadvise.guitools.textfields.TextFieldEnhancer.enhanceTextField;

/**
 * @author David Pirraglia
 */
@SuppressWarnings("FieldCanBeLocal")
public class JAdvise extends JFrame {

	public static final String TITLE = "JAdvise";
	private final StudentDatabase sd;
	private boolean disableRegex = false;
	private final Color SEARCH_FIELD_FOREGROUND_BACKUP;

	// Menu
	private final JMenuBar menuBar;
	private final JMenu fileMenu;
	private final JMenu editMenu;
	private final JMenu searchMenu;
	private final JMenu helpMenu;
	private final JMenuItem printItem;
	private final JMenuItem exportToCSVItem;
	private final JMenuItem exitItem;
	private final JMenuItem addStudentItem;
	private final JMenuItem addRandomStudentsItem;
	private final JMenuItem addRandomStudentFromSeedItem;
	private final JMenuItem editStudentItem;
	private final JMenuItem removeStudentItem;
	private final JMenuItem removeAllStudentsItem;
	private final JMenuItem disableRegexItem;
	private final JMenuItem clearSearchItem;
	private final JMenuItem aboutItem;

	// File Chooser
	private final JFileChooser exportToCSVSaveDialog;

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

	public static final int[] COLUMN_INDICES =
			IntStream.rangeClosed(0, COLUMN_NAMES.length).toArray();

	// Search Area
	private final JPanel searchPanel = new JPanel(new BorderLayout());
	private final JTextField searchField;
	private final JLabel searchLabel;
	private final JButton clearSearch;

	// Status Bar
	private final JPanel statusPanel;
	private final JLabel totalRowsLabel;
	private final JLabel selectedStudentStatusLabel;
	private final JLabel totalStudentsStatusLabel;

	public JAdvise(MySQLAccount account) throws SQLException, ClassNotFoundException {

		// Load Data
		sd = new StudentDatabase(account);
		sd.loadData();

		// Create JAdvise GUI
		setTitle(TITLE);
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setResizable(true);
		setMinimumSize(new Dimension(250, 300));

		// Pressing escape will ask if the user would like to quit
		rootPane.registerKeyboardAction(
				actionEvent -> PrebuiltDialogs.showQuitDialog(this),
				KeyStroke.getKeyStroke("ESCAPE"),
				JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		// Search Area
		searchLabel = new JLabel(" Search:  ");
		searchField = new JTextField();
		SEARCH_FIELD_FOREGROUND_BACKUP = searchField.getForeground();
		enhanceTextField(searchField);
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
		clearSearch.addActionListener(actionEvent -> clearSearchAction());

		searchPanel.add(searchLabel, BorderLayout.WEST);
		searchPanel.add(searchField, BorderLayout.CENTER);
		searchPanel.add(clearSearch, BorderLayout.EAST);
		add(searchPanel, BorderLayout.NORTH);

		// Pressing Ctrl+F or F3 will focus on the search field
		rootPane.registerKeyboardAction(
				actionEvent -> searchField.requestFocusInWindow(),
				KeyStroke.getKeyStroke("ctrl F"),
				JComponent.WHEN_IN_FOCUSED_WINDOW
		);
		rootPane.registerKeyboardAction(
				actionEvent -> searchField.requestFocusInWindow(),
				KeyStroke.getKeyStroke("F3"),
				JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		// Table
		String[][] rowData = sd.getTableData();
		table = new JTable(rowData, COLUMN_NAMES) {
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // Disallow the editing of any cell
			}

			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				super.changeSelection(rowIndex, columnIndex, toggle, extend);
				updateSelectedStudentStatus();
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
						.toString().charAt(0) == 'A' // ASCENDING
						&& column == previousColumn) {
					tableSorter.setSortKeys(null);
					previousColumn = -1;
				} else {
					previousColumn = column;
				}
			}
		});

		// Pressing enter with a row selected will edit it
		// https://stackoverflow.com/questions/9091208/jtable-enter-key
		table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
				KeyStroke.getKeyStroke("ENTER"),
				"\n"
		);
		table.getActionMap().put("\n", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editStudentAction();
			}
		});

		// Created a context menu for the table with Edit/Delete options
		// https://stackoverflow.com/questions/3558293/java-swing-jtable-right-click-menu-how-do-i-get-it-to-select-aka-highlight-t
		JPopupMenu popupmenu = new JPopupMenu();
		JMenuItem popupEditItem = new JMenuItem("Edit");
		JMenuItem popupDeleteItem = new JMenuItem("Delete");
		popupEditItem.addActionListener(e -> editStudentAction());
		popupDeleteItem.addActionListener(e -> removeStudentAction());
		popupmenu.add(popupEditItem);
		popupmenu.add(popupDeleteItem);

		// Adds mouse listeners to the table
		table.addMouseListener(new MouseAdapter() {
			// Right clicking selects the current row and shows the popup menu
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					int row = table.rowAtPoint(e.getPoint());
					if (row != -1) {
						table.setRowSelectionInterval(row, row);
						updateSelectedStudentStatus();
						popupmenu.show(table, e.getX(), e.getY());
					}
				}
			}

			// Double clicking on a row will edit it
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					updateSelectedStudentStatus();
					editStudentAction();
				}
			}
		});

		// Pressing escape clears focus from the table
		table.getInputMap(JPopupMenu.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke("ESCAPE"),
				"ESC"
		);
		table.getActionMap().put("ESC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.getRootPane().requestFocusInWindow();
			}
		});

		tableScrollPane = new JScrollPane(table);
		tableScrollPane.getHorizontalScrollBar().setUnitIncrement(15);
		add(tableScrollPane, BorderLayout.CENTER);
		sd.setTable(table);

		// Status Bar
		// https://stackoverflow.com/questions/3035880/how-can-i-create-a-bar-in-the-bottom-of-a-java-app-like-a-status-bar
		statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setPreferredSize(new Dimension(getWidth(), 20));
		statusPanel.add(new JLabel("Total Rows: "));
		totalRowsLabel = new JLabel();
		statusPanel.add(totalRowsLabel);
		totalRowsLabel.setText(sd.getTotalStudents() + "");
		statusPanel.add(new JLabel(" "));
		statusPanel.add(new JSeparator(JSeparator.VERTICAL));
		statusPanel.add(Box.createHorizontalGlue());
		statusPanel.add(new JSeparator(JSeparator.VERTICAL));
		selectedStudentStatusLabel = new JLabel("0");
		selectedStudentStatusLabel.setToolTipText("Index of the selected student");
		statusPanel.add(selectedStudentStatusLabel);
		statusPanel.add(new JLabel("/"));
		totalStudentsStatusLabel = new JLabel();
		totalStudentsStatusLabel.setToolTipText("Total number of students");
		updateTotalStudentsStatus();
		statusPanel.add(totalStudentsStatusLabel);
		add(statusPanel, BorderLayout.SOUTH);

		// Menu Bar
		menuBar = new JMenuBar();

		// File Menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		printItem = new JMenuItem("Print");
		printItem.setMnemonic(KeyEvent.VK_P);
		printItem.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
		printItem.addActionListener(actionEvent -> {
			try {
				table.print();
			} catch (PrinterException e) {
				e.printStackTrace();
				PrebuiltDialogs.showErrorDialog(this, "Can't print table.");
			}
		});
		fileMenu.add(printItem);

		exportToCSVItem = new JMenuItem("Export to CSV");
		exportToCSVItem.setMnemonic(KeyEvent.VK_E);
		exportToCSVItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

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
				userSelection = exportToCSVSaveDialog.showSaveDialog(this);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File csvFile = exportToCSVSaveDialog.getSelectedFile();

					// If the file already exists, ask to overwrite it
					if (csvFile.exists() &&
							!PrebuiltDialogs.showYesNoDialog(
									this,
									"Do you want to overwrite this file?",
									TITLE
							)
					) {
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
		exitItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		exitItem.addActionListener(actionEvent -> System.exit(0));
		fileMenu.add(exitItem);

		// Edit Menu
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);

		addStudentItem = new JMenuItem("Add Student");
		addStudentItem.setMnemonic(KeyEvent.VK_A);
		addStudentItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		addStudentItem.addActionListener(ae -> {
			new AddEditStudent(this, sd);
			updateTotalStudentsStatus();
			if (!searchField.getText().isEmpty()) {
				search();
			}
		});
		editMenu.add(addStudentItem);

		addRandomStudentsItem = new JMenuItem("Add Random Students");
		addRandomStudentsItem.setMnemonic(KeyEvent.VK_N);
		addRandomStudentsItem.setAccelerator(KeyStroke.getKeyStroke("ctrl shift N"));
		addRandomStudentsItem.addActionListener(actionEvent -> {
			JScrollableSpinner spinner = new JScrollableSpinner(
					new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1),
					true
			);
			if (JOptionPane.showConfirmDialog(
					this,
					new JComponent[]{
							new JLabel("How many random students?"),
							spinner
					},
					TITLE,
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE
			) == JOptionPane.OK_OPTION) {
				int numberOfStudents = (int) spinner.getValue();
				if (numberOfStudents > 100 && !PrebuiltDialogs.showYesNoDialog(
						this,
						String.format(
								"Are you sure you want to generate %d students?",
								numberOfStudents
						),
						TITLE
				)) {
					return;
				}
				for (int i = 0; i < numberOfStudents; i++) {
					try {
						sd.addStudent(new Student());
					} catch (DuplicateIDException e) {
						// In the unlikely event that a random item is added
						// with an ID that already exists, catch the exception
						// and try again.
						i--;
					}
				}
				sd.updateTable();
				try {
					sd.saveData();
				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
					PrebuiltDialogs.showErrorDialog(this, e.getMessage());
				}
				updateTotalStudentsStatus();
				if (!searchField.getText().isEmpty()) {
					search();
				}
			}
		});
		editMenu.add(addRandomStudentsItem);

		addRandomStudentFromSeedItem = new JMenuItem("Add Random Student From Seed");
		addRandomStudentFromSeedItem.setMnemonic(KeyEvent.VK_S);
		addRandomStudentFromSeedItem.setAccelerator(KeyStroke.getKeyStroke("ctrl alt N"));
		addRandomStudentFromSeedItem.addActionListener(actionEvent -> {
			long seed;
			String input = "";
			while (true) {
				input = (String) JOptionPane.showInputDialog(
						this,
						"Enter seed to generate student:",
						TITLE,
						JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						input
				);
				if (input != null) { // Cancel wasn't pressed
					try {
						seed = Long.parseLong(input);
						sd.addStudent(new Student(seed));
						sd.updateTable();
						sd.saveData();
						updateTotalStudentsStatus();
					} catch (NumberFormatException e) {
						PrebuiltDialogs.showErrorDialog(this, "Invalid seed.");
						continue;
					} catch (DuplicateIDException e) {
						PrebuiltDialogs.showErrorDialog(this, "This ID already exists.");
						continue;
					} catch (SQLException | ClassNotFoundException e) {
						PrebuiltDialogs.showErrorDialog(this, e.getMessage());
					}
					if (!searchField.getText().isEmpty()) {
						search();
					}
				}
				return;
			}
		});
		editMenu.add(addRandomStudentFromSeedItem);

		editStudentItem = new JMenuItem("Edit Selected Student");
		editStudentItem.setMnemonic(KeyEvent.VK_E);
		editStudentItem.setAccelerator(KeyStroke.getKeyStroke("ENTER"));
		editStudentItem.addActionListener(actionEvent -> editStudentAction());
		editMenu.add(editStudentItem);

		removeStudentItem = new JMenuItem("Remove Selected Student");
		removeStudentItem.setMnemonic(KeyEvent.VK_R);
		removeStudentItem.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
		removeStudentItem.addActionListener(actionEvent -> removeStudentAction());
		editMenu.add(removeStudentItem);

		removeAllStudentsItem = new JMenuItem("Remove All Students");
		removeAllStudentsItem.setAccelerator(KeyStroke.getKeyStroke("ctrl DELETE"));
		removeAllStudentsItem.addActionListener(actionEvent -> {
			if (PrebuiltDialogs.showConfirmDialog(
					this,
					"Are you sure you want to remove all students?",
					TITLE
			)) {
				try {
					sd.clearData();
					sd.updateTable();
					clearSearchAction();
					updateTotalStudentsStatus();
				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
					PrebuiltDialogs.showErrorDialog(this, e.getMessage());
				}
			}
		});
		editMenu.add(removeAllStudentsItem);

		// Search Menu
		searchMenu = new JMenu("Search");
		searchMenu.setMnemonic(KeyEvent.VK_S);

		disableRegexItem = new JCheckBoxMenuItem("Disable Regex", false);
		disableRegexItem.setMnemonic(KeyEvent.VK_D);
		disableRegexItem.setAccelerator(KeyStroke.getKeyStroke("ctrl SLASH"));
		disableRegexItem.addActionListener(e -> {
			disableRegex = !disableRegex;
			search();
		});
		searchMenu.add(disableRegexItem);

		clearSearchItem = new JMenuItem("Clear Search");
		clearSearchItem.setMnemonic(KeyEvent.VK_C);
		clearSearchItem.setAccelerator(KeyStroke.getKeyStroke("ctrl COMMA"));
		clearSearchItem.addActionListener(e -> clearSearchAction());
		searchMenu.add(clearSearchItem);


		// Help Menu
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		aboutItem = new JMenuItem("About");
		aboutItem.setMnemonic(KeyEvent.VK_A);
		aboutItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
		JLabel websiteHyperLinkLabel = new JLabel(
				"<html><a href=\"https://dapirra.github.io/\">" +
						"https://dapirra.github.io/</p></html>");
		websiteHyperLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		websiteHyperLinkLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				try {
					Desktop.getDesktop().browse(new URI("https://dapirra.github.io/"));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		});
		aboutItem.addActionListener(actionEvent -> JOptionPane.showMessageDialog(
				this,
				new JComponent[] {
						new JLabel("Version: 2.0"),
						new JLabel("Date: 3/5/2021"),
						new JLabel("Created by David Pirraglia"),
						websiteHyperLinkLabel
				},
				TITLE,
				JOptionPane.INFORMATION_MESSAGE
		));
		helpMenu.add(aboutItem);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(searchMenu);
		menuBar.add(helpMenu);
		setJMenuBar(menuBar);

		setVisible(true);
	}

	private void clearSearchAction() {
		searchField.setText("");
		tableSorter.setSortKeys(null);
	}

	private void editStudentAction() {
		if (table.getSelectedRow() < 0) {
			PrebuiltDialogs.showErrorDialog(this, "No student is selected.");
			return;
		}
		new AddEditStudent(this, sd, getIndex(table), sd.getStudent(
				(String) table.getModel().getValueAt(getIndex(table), 0)
		));
		if (!searchField.getText().isEmpty()) {
			search();
		}
	}

	private void removeStudentAction() {
		if (table.getSelectedRow() < 0) {
			PrebuiltDialogs.showErrorDialog(this, "No student is selected.");
			return;
		}
		if (PrebuiltDialogs.showConfirmDialog(
				this,
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
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				PrebuiltDialogs.showErrorDialog(this, e.getMessage());
			}
			updateTotalStudentsStatus();
		}
	}

	/**
	 * Used for testing. Primary main method is located in {@link jadvise.Demo}
	 */
	@SuppressWarnings("SpellCheckingInspection")
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

	/**
	 * Gets the index of the currently selected item from a specified table.
	 * This function is useful for when there are rows that are filtered from
	 * the table and the original index of the row needs to be retrieved.
	 *
	 * @param table The table to read from
	 * @return The index of the currently selected item
	 */
	public static int getIndex(JTable table) {
		return table.convertRowIndexToModel(table.getSelectedRow());
	}

	public void updateSelectedStudentStatus() {
		selectedStudentStatusLabel.setText(getIndex(table) + 1 + "");
	}

	public void updateTotalStudentsStatus() {
		totalStudentsStatusLabel.setText(sd.getTotalStudents() + "");
	}

	public void search() {
		try {
			String regex = searchField.getText();
			if (disableRegex) {
				regex = regex.replaceAll("([+*?^$\\\\.\\[\\]{}()|])", "\\\\$1");
			}
			RowFilter<TableModel, Object> rf = RowFilter.regexFilter(
					"(?i)" + regex,
					COLUMN_INDICES
			);
			tableSorter.setRowFilter(rf);
			totalRowsLabel.setText(table.getRowCount() + "");
			searchField.setForeground(SEARCH_FIELD_FOREGROUND_BACKUP);
		} catch (java.util.regex.PatternSyntaxException e) {
			searchField.setForeground(Color.RED);
		}
	}
}
