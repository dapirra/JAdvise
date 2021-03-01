package jadvise.gui;

import jadvise.exceptions.InvalidCourseException;
import jadvise.exceptions.InvalidEmailException;
import jadvise.exceptions.InvalidPhoneNumberException;
import jadvise.exceptions.InvalidZipCodeException;
import jadvise.exceptions.TooManyEmptyFieldsException;
import jadvise.exceptions.id.DuplicateIDException;
import jadvise.exceptions.id.InvalidIDException;
import jadvise.guitools.IDTextField;
import jadvise.guitools.JScrollableComboBox;
import jadvise.guitools.JScrollableSpinner;
import jadvise.guitools.PhoneTextField;
import jadvise.guitools.PrebuiltDialogs;
import jadvise.objects.Student;
import jadvise.objects.StudentDatabase;
import jadvise.tools.Info;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerListModel;
import javax.swing.TransferHandler;
import javax.swing.text.DefaultCaret;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Arrays;

import static jadvise.guitools.TextFieldEnhancer.enhanceTextField;

/**
 * @author David Pirraglia
 */
public class AddEditStudent extends JDialog {

	private final String previousID;

	private final JPanel mainPanel;
	private final JScrollPane scrollPane;

	private final JPanel personalInfoPanel;

	private final JPanel IDPanel;
	private final JLabel IDLabel;
	private final JTextField IDField;

	private final JPanel name1Panel;
	private final JLabel firstNameLabel;
	private final JTextField firstNameField;
	private final JLabel middleInitialLabel;
	private final JTextField middleInitialField;

	private final JPanel name2Panel;
	private final JLabel lastNameLabel;
	private final JTextField lastNameField;

	private final JPanel collegeInfoPanel;

	private final JPanel GPAAndMajorPanel;
	private final JLabel GPALabel;
	private final JScrollableSpinner GPASpinner;
	private final JLabel majorLabel;
	private final JScrollableComboBox<String> majorComboBox;

	private final JPanel homeCampusPanel;
	private final JLabel homeCampusLabel;
	private final JScrollableComboBox<String> homeCampusComboBox;

	private final JPanel homeAddressPanel;

	private final JPanel houseNumberPanel;
	private final JLabel houseNumberLabel;
	private final JTextField houseNumberField;

	private final JPanel streetPanel;
	private final JLabel streetLabel;
	private final JTextField streetField;

	private final JPanel cityPanel;
	private final JLabel cityLabel;
	private final JTextField cityField;

	private final JPanel stateAndZipPanel;
	private final JLabel stateLabel;
	private final JScrollableComboBox<String> stateComboBox;
	private final JLabel zipLabel;
	private final JTextField zipField;

	private final JPanel contactInfoPanel;

	private final JPanel homePhonePanel;
	private final JLabel homePhoneLabel;
	private final JTextField homePhoneField;

	private final JPanel cellPhonePanel;
	private final JLabel cellPhoneLabel;
	private final JTextField cellPhoneField;

	private final JPanel emailPanel;
	private final JLabel emailLabel;
	private final JTextField emailField;

	private final JPanel courseInfoPanel;

	private final JList<String> coursesTaken;
	private final DefaultListModel<String> coursesTakenModel = new DefaultListModel<>();
	private final JList<String> currentCourses;
	private final DefaultListModel<String> currentCoursesModel = new DefaultListModel<>();
	private final JList<String> coursesNeeded;
	private final DefaultListModel<String> coursesNeededModel = new DefaultListModel<>();

	private final JPanel notesAreaPanel;
	private final JTextArea notesArea;

	private final JButton doneButton;

	/**
	 * Used for adding a new student.
	 *
	 * @param parentComponent Main window
	 * @param sd              Student database
	 */
	public AddEditStudent(Component parentComponent, final StudentDatabase sd) {
		this(parentComponent, sd, -1, null);
	}

	/**
	 * Used for editing a student.
	 *
	 * @param parentComponent Main window
	 * @param sd              Student database
	 * @param index           Index of the student
	 * @param editStudent     Student object being edited
	 */
	public AddEditStudent(Component parentComponent, final StudentDatabase sd, int index, final Student editStudent) {

		if (editStudent == null) {
			setTitle("Add Student");
		} else {
			setTitle("Edit Student");
		}
		setSize(500, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// Makes parent not focusable
		if (parentComponent != null) {
			setModal(true);
		}

		setLayout(new BorderLayout());
		setLocationRelativeTo(parentComponent);
		setResizable(true);

		// Pressing Escape will close the window
		rootPane.registerKeyboardAction(
				actionEvent -> dispose(),
				KeyStroke.getKeyStroke("ESCAPE"),
				JComponent.WHEN_IN_FOCUSED_WINDOW
		);

		// Personal Info
		IDPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		IDLabel = new JLabel("<html><b>ID Number: </b></html>");
		IDField = new IDTextField(15);
		enhanceTextField(IDField);
		IDPanel.add(IDLabel);
		IDPanel.add(IDField);

		name1Panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		firstNameLabel = new JLabel("<html><b>First Name: </b></html>");
		firstNameField = new JTextField(17);
		enhanceTextField(firstNameField);
		middleInitialLabel = new JLabel("Middle Initial: ");
		middleInitialField = new JTextField(3);
		enhanceTextField(middleInitialField);
		name1Panel.add(firstNameLabel);
		name1Panel.add(firstNameField);
		name1Panel.add(middleInitialLabel);
		name1Panel.add(middleInitialField);

		name2Panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		lastNameLabel = new JLabel("<html><b>Last Name: </b></html>");
		lastNameField = new JTextField(30);
		enhanceTextField(lastNameField);
		name2Panel.add(lastNameLabel);
		name2Panel.add(lastNameField);

		personalInfoPanel = createBox(
				addSeparator(),
				IDPanel,
				name1Panel,
				name2Panel
		);

		// College Info
		GPAAndMajorPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		GPALabel = new JLabel("GPA: ");
		GPASpinner = new JScrollableSpinner(new SpinnerListModel(Info.GPA_VALUES));
		GPASpinner.setPreferredSize(new Dimension(50, 23));
		majorLabel = new JLabel("Major: ");
		majorComboBox = new JScrollableComboBox<>(new String[]{
				Student.getMajor(0), Student.getMajor(1), Student.getMajor(2),
				Student.getMajor(3), Student.getMajor(4)});
		GPAAndMajorPanel.add(GPALabel);
		GPAAndMajorPanel.add(GPASpinner);
		GPAAndMajorPanel.add(majorLabel);
		GPAAndMajorPanel.add(majorComboBox);

		homeCampusPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		homeCampusLabel = new JLabel("Home Campus: ");
		homeCampusComboBox = new JScrollableComboBox<>(new String[]{
				Student.getHomeCampus(0), Student.getHomeCampus(1),
				Student.getHomeCampus(2)});
		homeCampusPanel.add(homeCampusLabel);
		homeCampusPanel.add(homeCampusComboBox);

		collegeInfoPanel = createBox(
				addSeparator(),
				GPAAndMajorPanel,
				homeCampusPanel
		);

		// Home Address
		houseNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		houseNumberLabel = new JLabel("House Number: ");
		houseNumberField = new JTextField(10);
		enhanceTextField(houseNumberField);
		houseNumberPanel.add(houseNumberLabel);
		houseNumberPanel.add(houseNumberField);

		streetPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		streetLabel = new JLabel("Street: ");
		streetField = new JTextField(20);
		enhanceTextField(streetField);
		streetPanel.add(streetLabel);
		streetPanel.add(streetField);

		cityPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		cityLabel = new JLabel("City: ");
		cityField = new JTextField(20);
		enhanceTextField(cityField);
		cityPanel.add(cityLabel);
		cityPanel.add(cityField);

		stateAndZipPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		stateLabel = new JLabel("State: ");
		stateComboBox = new JScrollableComboBox<>(Info.STATES);
		stateComboBox.setSelectedIndex(31); // NY
		zipLabel = new JLabel("Zip: ");
		zipField = new JTextField(7);
		enhanceTextField(zipField);
		stateAndZipPanel.add(stateLabel);
		stateAndZipPanel.add(stateComboBox);
		stateAndZipPanel.add(zipLabel);
		stateAndZipPanel.add(zipField);

		homeAddressPanel = createBox(
				addSeparator(),
				houseNumberPanel,
				streetPanel,
				cityPanel,
				stateAndZipPanel
		);

		// Contact Info
		homePhonePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		homePhoneLabel = new JLabel("Home Phone Number: ");
		homePhoneField = new PhoneTextField(15);
		enhanceTextField(homePhoneField);
		homePhonePanel.add(homePhoneLabel);
		homePhonePanel.add(homePhoneField);

		cellPhonePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		cellPhoneLabel = new JLabel("Cell Phone Number: ");
		cellPhoneField = new PhoneTextField(15);
		enhanceTextField(cellPhoneField);
		cellPhonePanel.add(cellPhoneLabel);
		cellPhonePanel.add(cellPhoneField);

		emailPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		emailLabel = new JLabel("Email Address: ");
		emailField = new JTextField(30);
		enhanceTextField(emailField);
		emailPanel.add(emailLabel);
		emailPanel.add(emailField);

		contactInfoPanel = createBox(
				addSeparator(),
				homePhonePanel,
				cellPhonePanel,
				emailPanel
		);

		// Course Info
		coursesTaken = new JList<>();
		currentCourses = new JList<>();
		coursesNeeded = new JList<>();
		JPanel innerCoursePanel = new JPanel(new GridLayout(1, 3, 20, 5));
		innerCoursePanel.add(makeCSTPanel("Courses Taken:", coursesTaken, coursesTakenModel));
		innerCoursePanel.add(makeCSTPanel("Current Courses:", currentCourses, currentCoursesModel));
		innerCoursePanel.add(makeCSTPanel("Courses Needed:", coursesNeeded, coursesNeededModel));
		courseInfoPanel = createBox(addSeparator(), innerCoursePanel);

		// Notes
		notesAreaPanel = new JPanel(new BorderLayout());
		notesArea = new JTextArea(10, 20);
		notesArea.setLineWrap(true);
		notesArea.setWrapStyleWord(true);
		enhanceTextField(notesArea);
		notesAreaPanel.add(notesArea, BorderLayout.CENTER);

		// Edit Mode - Set fields
		if (editStudent != null) {
			DefaultCaret notesAreaCaret = (DefaultCaret) notesArea.getCaret();
			boolean studentHasNotes = !editStudent.getNotes().isEmpty();

			// Prevents the UI from automatically scrolling down to the notesArea
			// whenever a student contains any notes written.
			// https://stackoverflow.com/questions/23365847/how-to-auto-scroll-down-jtextarea-after-append
			if (studentHasNotes) {
				notesAreaCaret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
			}

			System.out.println("Edit Mode");
			IDField.setText(editStudent.getIdNumber());
			firstNameField.setText(editStudent.getFirstName());
			middleInitialField.setText(editStudent.getMiddleInitial());
			lastNameField.setText(editStudent.getLastName());

			GPASpinner.setValue(editStudent.getGpa());
			majorComboBox.setSelectedIndex(editStudent.getMajor());
			homeCampusComboBox.setSelectedIndex(editStudent.getHomeCampus());

			houseNumberField.setText(editStudent.getHouseNumber());
			streetField.setText(editStudent.getStreet());
			cityField.setText(editStudent.getCity());
			stateComboBox.setSelectedIndex(editStudent.getState());
			zipField.setText(editStudent.getZip());

			homePhoneField.setText(editStudent.getHomePhone());
			cellPhoneField.setText(editStudent.getCellPhone());
			emailField.setText(editStudent.getEmailAddress());

			String courses = editStudent.getCSTCoursesTakenForDegree();
			if (!courses.isEmpty()) {
				for (String course : courses.split(",")) {
					coursesTakenModel.addElement(course);
				}
			}

			courses = editStudent.getCSTCoursesCurrentlyTaking();
			if (!courses.isEmpty()) {
				for (String course : courses.split(",")) {
					currentCoursesModel.addElement(course);
				}
			}

			courses = editStudent.getCSTCoursesToBeTakenForDegree();
			if (!courses.isEmpty()) {
				for (String course : courses.split(",")) {
					coursesNeededModel.addElement(course);
				}
			}

			notesArea.setText(editStudent.getNotes());

			previousID = editStudent.getIdNumber();

			// Fixes problem where cursor doesn't move at all caused by stopping
			// the window from scrolling when the notes field was set.
			if (studentHasNotes) {
				notesAreaCaret.setUpdatePolicy(DefaultCaret.UPDATE_WHEN_ON_EDT);
			}
		} else {
			previousID = "";
		}

		// Main Panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(addHeader("Personal Info:", personalInfoPanel));
		mainPanel.add(personalInfoPanel);
		mainPanel.add(addHeader("College Info:", collegeInfoPanel));
		mainPanel.add(collegeInfoPanel);
		mainPanel.add(addHeader("Home Address: ", homeAddressPanel));
		mainPanel.add(homeAddressPanel);
		mainPanel.add(addHeader("Contact Info:", contactInfoPanel));
		mainPanel.add(contactInfoPanel);
		mainPanel.add(addHeader("Course Info:", courseInfoPanel));
		mainPanel.add(courseInfoPanel);
		mainPanel.add(addHeader("Notes:", notesAreaPanel));
		mainPanel.add(notesAreaPanel);
		scrollPane = new JScrollPane(mainPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);

		// Done Button
		doneButton = new JButton("Done");
		doneButton.addActionListener(actionEvent -> {
			try {
				Student readInStudent = new Student(
						IDField.getText(),
						firstNameField.getText(),
						middleInitialField.getText(),
						lastNameField.getText(),
						GPASpinner.getValue() + "",
						homeCampusComboBox.getSelectedIndex() + "",
						majorComboBox.getSelectedIndex() + "",
						houseNumberField.getText(),
						streetField.getText(),
						cityField.getText(),
						stateComboBox.getSelectedIndex() + "",
						zipField.getText(),
						homePhoneField.getText(),
						cellPhoneField.getText(),
						emailField.getText(),
						String.join(",", Arrays.stream(coursesTakenModel.toArray()).toArray(String[]::new)),
						String.join(",", Arrays.stream(currentCoursesModel.toArray()).toArray(String[]::new)),
						String.join(",", Arrays.stream(coursesNeededModel.toArray()).toArray(String[]::new)),
						notesArea.getText()
				);
				if (editStudent == null) { // Add new
					sd.addStudent(readInStudent);
				} else { // Edit
					sd.updateStudent(
							readInStudent,
							previousID,
							index
					);
				}
			} catch (TooManyEmptyFieldsException e) {
				PrebuiltDialogs.showErrorDialog(AddEditStudent.this,
						"ID, Firstname, and Lastname are required.");
				return;
			} catch (InvalidIDException e) {
				PrebuiltDialogs.showErrorDialog(AddEditStudent.this,
						"Invalid ID. Must be 8 digits.");
				return;
			} catch (DuplicateIDException e) {
				PrebuiltDialogs.showErrorDialog(AddEditStudent.this,
						"This ID already exists.");
				return;
			} catch (InvalidZipCodeException e) {
				PrebuiltDialogs.showErrorDialog(AddEditStudent.this,
						"Invalid Zip Code.");
				return;
			} catch (InvalidPhoneNumberException e) {
				PrebuiltDialogs.showErrorDialog(AddEditStudent.this,
						"Invalid Phone Number.");
				return;
			} catch (InvalidEmailException e) {
				PrebuiltDialogs.showErrorDialog(AddEditStudent.this,
						"Invalid Email.");
				return;
			} catch (InvalidCourseException e) {
				PrebuiltDialogs.showErrorDialog(AddEditStudent.this,
						"Invalid Course Input.");
				return;
			}
			try {
				sd.updateTable();
				sd.saveData();
			} catch (SQLException ex) {
				ex.printStackTrace();
				System.exit(1);
			} catch (ClassNotFoundException ex) {
				System.exit(1);
			}
			dispose();
		});

		add(scrollPane, BorderLayout.CENTER);
		add(doneButton, BorderLayout.SOUTH);

		// Sets the ID field to be the initial focused item
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				super.windowOpened(e);
				IDField.requestFocusInWindow();
				e.getWindow().removeWindowListener(this);
			}
		});

		setVisible(true);
	}

	/**
	 * @return A JPanel with a separator in it.
	 */
	private static JPanel addSeparator() {
		JPanel outputPanel = new JPanel(new BorderLayout());
		JSeparator separator = new JSeparator();
		outputPanel.add(separator, BorderLayout.CENTER);
		outputPanel.setMaximumSize(new Dimension(
				outputPanel.getMaximumSize().width, 1
		));
		return outputPanel;
	}

	private static JPanel addHeader(String title, final JPanel hidePanel) {
		JPanel outputPanel = new JPanel(new BorderLayout());

		JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		final JButton hideButton = new JButton("\u2796"); // -
		JLabel mainLabel = new JLabel(title);
		innerPanel.add(hideButton);
		innerPanel.add(mainLabel);

		outputPanel.add(addSeparator(), BorderLayout.NORTH);
		outputPanel.add(innerPanel, BorderLayout.CENTER);
		outputPanel.setMaximumSize(new Dimension(
				outputPanel.getMaximumSize().width,
				outputPanel.getPreferredSize().height
		));
		if (hidePanel != null) {
			hideButton.addActionListener(actionEvent ->
					togglePanelVisibility(hidePanel, hideButton)
			);
			outputPanel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent mouseEvent) {
					if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
						togglePanelVisibility(hidePanel, hideButton);
					}
				}
			});
		}
		return outputPanel;
	}

	private static void togglePanelVisibility(JPanel hidePanel, JButton hideButton) {
		if (hidePanel.isVisible()) {
			hidePanel.setVisible(false);
			hideButton.setText("\u2795"); // +
		} else {
			hidePanel.setVisible(true);
			hideButton.setText("\u2796"); // -
		}
	}

	/**
	 * @param components A whole bunch of components.
	 * @return A single panel with all of the components in it using vertical
	 * BoxLayout.
	 */
	private static JPanel createBox(JComponent... components) {
		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
		for (JComponent c : components) {
			outputPanel.add(c);
		}
		return outputPanel;
	}

	private JPanel makeCSTPanel(String header, JList<String> list, DefaultListModel<String> model) {
		list.setModel(model);
		JPanel outputPanel = new JPanel(new BorderLayout(10, 5));
		JLabel headerLabel = new JLabel(header);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setDragEnabled(true);
		list.setDropMode(DropMode.INSERT);
		list.setTransferHandler(new DragNDropTransferHandler(list, model));

		// Pressing Delete will remove the selected item
		list.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke("DELETE"),
				"DEL"
		);
		list.getActionMap().put("DEL", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				System.out.println("DEL: " + index);
				if (index != -1) {
					model.remove(index);
				}
				if (model.size() > 0) {
					if (model.size() == index) {
						list.setSelectedIndex(index - 1);
					} else {
						list.setSelectedIndex(index);
					}
				}
			}
		});

		// Clear selection when focus is lost
		list.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				list.clearSelection();
			}
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
		JButton addButton = new JButton("+");
		JButton removeButton = new JButton("-");
		addButton.setToolTipText("Add a course");
		removeButton.setToolTipText("Remove a course");
		removeButton.setFocusable(false);
		addButton.addActionListener(e -> {
			String courses = "";
			while (true) {
				courses = (String) JOptionPane.showInputDialog(
						this,
						"Add a course (e.g. CST101, CST242):",
						header,
						JOptionPane.PLAIN_MESSAGE,
						null,
						null,
						courses
				);
				if (courses != null) {
					courses = Student.cleanUpCourses(courses);
					if (Student.isValidCourseInfo(courses)) {
						for (String course : courses.split(",")) {
							model.addElement(course);
						}
					} else {
						PrebuiltDialogs.showErrorDialog(this, "Invalid Course Input.");
						continue;
					}
				}
				return;
			}
		});
		removeButton.addActionListener(e -> {
			int index = list.getSelectedIndex();
			if (index != -1) {
				model.remove(index);
			}
			if (model.size() > 0) {
				if (model.size() == index) {
					list.setSelectedIndex(index - 1);
				} else {
					list.setSelectedIndex(index);
				}
			}
		});
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);

		outputPanel.add(headerLabel, BorderLayout.NORTH);
		outputPanel.add(list, BorderLayout.CENTER);
		outputPanel.add(buttonPanel, BorderLayout.SOUTH);

		return outputPanel;
	}

	private static class DragNDropTransferHandler extends TransferHandler {
		private final JList<String> list;
		private final DefaultListModel<String> model;

		public DragNDropTransferHandler(JList<String> list, DefaultListModel<String> model) {
			this.list = list;
			this.model = model;
		}

		@Override
		public boolean canImport(TransferHandler.TransferSupport ts) {
			return ts.isDataFlavorSupported(DataFlavor.stringFlavor);
		}

		@Override
		public boolean importData(TransferSupport ts) {
			Transferable t = ts.getTransferable();
			String data;

			try {
				data = t.getTransferData(DataFlavor.stringFlavor).toString();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

			JList.DropLocation d = list.getDropLocation();
			model.add(d.getIndex(), data);
			return true;
		}

		@Override
		public int getSourceActions(JComponent c) {
			return TransferHandler.MOVE;
		}

		@Override
		public Transferable createTransferable(JComponent c) {
			return new StringSelection(list.getSelectedValue());
		}

		@Override
		public void exportDone(JComponent source, Transferable data, int action) {
			model.remove(list.getSelectedIndex());
		}
	}
}
