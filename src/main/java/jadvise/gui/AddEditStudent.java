package jadvise.gui;

import jadvise.exceptions.InvalidCourseException;
import jadvise.exceptions.InvalidEmailException;
import jadvise.exceptions.InvalidPhoneNumberException;
import jadvise.exceptions.InvalidZipCodeException;
import jadvise.exceptions.TooManyEmptyFieldsException;
import jadvise.exceptions.id.DuplicateIDException;
import jadvise.exceptions.id.InvalidIDException;
import jadvise.guitools.ErrorMessagePane;
import jadvise.guitools.JScrollableComboBox;
import jadvise.guitools.JScrollableSpinner;
import jadvise.objects.Student;
import jadvise.objects.StudentDatabase;
import jadvise.tools.States;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

/**
 * @author David Pirraglia
 */
public class AddEditStudent extends JDialog {

//	private final String TITLE = "Add Student";
	private final String previousID;

	private final JPanel mainPanel;
	private final JScrollPane scrollPane;
	protected static Container inputFrame;
//	protected static JDialog addStudent;

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
//	private final JTextField GPAField;
	private final JScrollableSpinner GPASpinner;
	private final JLabel majorLabel;
	private final JScrollableComboBox majorComboBox;

	private final JPanel homeCampusPanel;
	private final JLabel homeCampusLabel;
	private final JScrollableComboBox homeCampusComboBox;

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
	private final JScrollableComboBox stateComboBox;
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

	private final JPanel CSTCoursesTakenForDegreePanel;
	private final JLabel CSTCoursesTakenForDegreeLabel;
	private final JTextField CSTCoursesTakenForDegreeField;

	private final JPanel CSTCoursesCurrentlyTakingPanel;
	private final JLabel CSTCoursesCurrentlyTakingLabel;
	private final JTextField CSTCoursesCurrentlyTakingField;

	private final JPanel CSTCoursesToBeTakenForDegreePanel;
	private final JLabel CSTCoursesToBeTakenForDegreeLabel;
	private final JTextField CSTCoursesToBeTakenForDegreeField;

	private final JPanel notesAreaPanel;
	private final JTextArea notesArea;

	private final JButton doneButton;

//	public static void main(String[] args) {
//		new AddStudent(null, null, null);
//	}
	public AddEditStudent(Container frame, final StudentDatabase sd, final Student s) {

		inputFrame = frame;
//		addStudent = this;
		if (s == null) {
			setTitle("Add Student");
		} else {
			setTitle("Edit Student");
		}
		setSize(500, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// Makes parent not focusable
		if (inputFrame != null) {
			setModal(true);
		}

		setLayout(new BorderLayout());
		setLocationRelativeTo(inputFrame);
		setResizable(true);

		// Personal Info
		IDPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		IDLabel = new JLabel("<html><b>ID Number: </b></html>");
		IDField = new JTextField(15);
		IDPanel.add(IDLabel);
		IDPanel.add(IDField);

		name1Panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		firstNameLabel = new JLabel("<html><b>First Name: </b></html>");
		firstNameField = new JTextField(17);
		middleInitialLabel = new JLabel("Middle Initial: ");
		middleInitialField = new JTextField(3);
		name1Panel.add(firstNameLabel);
		name1Panel.add(firstNameField);
		name1Panel.add(middleInitialLabel);
		name1Panel.add(middleInitialField);

		name2Panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		lastNameLabel = new JLabel("<html><b>Last Name: </b></html>");
		lastNameField = new JTextField(30);
		name2Panel.add(lastNameLabel);
		name2Panel.add(lastNameField);

		personalInfoPanel = createBox(addSeparator(), IDPanel, name1Panel, name2Panel);

		// College Info
		GPAAndMajorPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		GPALabel = new JLabel("GPA: ");
		GPASpinner = new JScrollableSpinner(new SpinnerListModel(new String[]{
			"0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8",
			"0.9", "1.0", "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7",
			"1.8", "1.9", "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6",
			"2.7", "2.8", "2.9", "3.0", "3.1", "3.2", "3.3", "3.4", "3.5",
			"3.6", "3.7", "3.8", "3.9", "4.0"}));
		GPASpinner.setPreferredSize(new Dimension(50, 23));
		majorLabel = new JLabel("Major: ");
		majorComboBox = new JScrollableComboBox(new String[]{
			Student.getMajor(0), Student.getMajor(1), Student.getMajor(2),
			Student.getMajor(3), Student.getMajor(4)});
		GPAAndMajorPanel.add(GPALabel);
		GPAAndMajorPanel.add(GPASpinner);
		GPAAndMajorPanel.add(majorLabel);
		GPAAndMajorPanel.add(majorComboBox);

		homeCampusPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		homeCampusLabel = new JLabel("Home Campus: ");
		homeCampusComboBox = new JScrollableComboBox(new String[]{
			Student.getHomeCampus(0), Student.getHomeCampus(1),
			Student.getHomeCampus(2)});
		homeCampusPanel.add(homeCampusLabel);
		homeCampusPanel.add(homeCampusComboBox);

		collegeInfoPanel = createBox(addSeparator(), GPAAndMajorPanel, homeCampusPanel);

		// Home Address
		houseNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		houseNumberLabel = new JLabel("House Number: ");
		houseNumberField = new JTextField(10);
		houseNumberPanel.add(houseNumberLabel);
		houseNumberPanel.add(houseNumberField);

		streetPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		streetLabel = new JLabel("Street: ");
		streetField = new JTextField(20);
		streetPanel.add(streetLabel);
		streetPanel.add(streetField);

		cityPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		cityLabel = new JLabel("City: ");
		cityField = new JTextField(20);
		cityPanel.add(cityLabel);
		cityPanel.add(cityField);

		stateAndZipPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		stateLabel = new JLabel("State: ");
		stateComboBox = new JScrollableComboBox(States.getAbbreviatedStates());
		zipLabel = new JLabel("Zip: ");
		zipField = new JTextField(7);
		stateAndZipPanel.add(stateLabel);
		stateAndZipPanel.add(stateComboBox);
		stateAndZipPanel.add(zipLabel);
		stateAndZipPanel.add(zipField);

		homeAddressPanel = createBox(addSeparator(), houseNumberPanel, streetPanel, cityPanel, stateAndZipPanel);

		// Contact Info
		homePhonePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		homePhoneLabel = new JLabel("Home Phone Number: ");
		homePhoneField = new JTextField(15);
		homePhonePanel.add(homePhoneLabel);
		homePhonePanel.add(homePhoneField);
//		homePhonePanel = new JPanel(new BorderLayout());
//		homePhoneLabel = new JLabel("Home Phone Number: ");
//		homePhoneField = new JTextField(15);
//		homePhonePanel.add(homePhoneLabel, BorderLayout.WEST);
//		homePhonePanel.add(homePhoneField, BorderLayout.CENTER);

		cellPhonePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		cellPhoneLabel = new JLabel("Cell Phone Number: ");
		cellPhoneField = new JTextField(15);
		cellPhonePanel.add(cellPhoneLabel);
		cellPhonePanel.add(cellPhoneField);

		emailPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		emailLabel = new JLabel("Email Address: ");
		emailField = new JTextField(30);
		emailPanel.add(emailLabel);
		emailPanel.add(emailField);

		contactInfoPanel = createBox(addSeparator(), homePhonePanel, cellPhonePanel, emailPanel);

		// Course Info
		CSTCoursesTakenForDegreeLabel = new JLabel("<html>CST Courses<br>taken for degree: </html>");
		CSTCoursesTakenForDegreeField = new JTextField(25);
		CSTCoursesTakenForDegreePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		CSTCoursesTakenForDegreePanel.add(CSTCoursesTakenForDegreeLabel);
		CSTCoursesTakenForDegreePanel.add(CSTCoursesTakenForDegreeField);

		CSTCoursesCurrentlyTakingLabel = new JLabel("<html>CST Courses<br>currently taking: </html>");
		CSTCoursesCurrentlyTakingField = new JTextField(25);
		CSTCoursesCurrentlyTakingPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		CSTCoursesCurrentlyTakingPanel.add(CSTCoursesCurrentlyTakingLabel);
		CSTCoursesCurrentlyTakingPanel.add(CSTCoursesCurrentlyTakingField);

		CSTCoursesToBeTakenForDegreeLabel = new JLabel("<html>CST Courses to be<br>taken for degree: </html>");
		CSTCoursesToBeTakenForDegreeField = new JTextField(25);
		CSTCoursesToBeTakenForDegreePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		CSTCoursesToBeTakenForDegreePanel.add(CSTCoursesToBeTakenForDegreeLabel);
		CSTCoursesToBeTakenForDegreePanel.add(CSTCoursesToBeTakenForDegreeField);

		courseInfoPanel = createBox(addSeparator(),
				CSTCoursesTakenForDegreePanel,
				CSTCoursesCurrentlyTakingPanel,
				CSTCoursesToBeTakenForDegreePanel);
//		courseInfoPanel = makeCSTPanel("CST courses that are taken", new String[]{"asd", "dsads", "asdsada", "asdsad", "adsadas"});

		// Notes
		notesAreaPanel = new JPanel(new BorderLayout());
		notesArea = new JTextArea(10, 20);
		notesAreaPanel.add(notesArea, BorderLayout.CENTER);

		// Edit Mode - Set fields
		if (s != null) {
			System.out.println("Edit Mode");
			IDField.setText(s.getIdNumber());
//			IDField.setEditable(false);
			firstNameField.setText(s.getFirstName());
			middleInitialField.setText(s.getMiddleInitial());
			lastNameField.setText(s.getLastName());

//			GPAField.setText(s.getGpa() + "");
			GPASpinner.setValue(s.getGpa());
			majorComboBox.setSelectedIndex(s.getMajor());
			homeCampusComboBox.setSelectedIndex(s.getHomeCampus());

			houseNumberField.setText(s.getHouseNumber());
			streetField.setText(s.getStreet());
			cityField.setText(s.getCity());
			stateComboBox.setSelectedIndex(s.getState());
			zipField.setText(s.getZip());

			homePhoneField.setText(s.getHomePhone());
			cellPhoneField.setText(s.getCellPhone());
			emailField.setText(s.getEmailAddress());

			CSTCoursesTakenForDegreeField.setText(s.getCSTCoursesTakenForDegree());
			CSTCoursesCurrentlyTakingField.setText(s.getCSTCoursesCurrentlyTaking());
			CSTCoursesToBeTakenForDegreeField.setText(s.getCSTCoursesToBeTakenForDegree());

			notesArea.setText(s.getNotes());

			previousID = s.getIdNumber();
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
//		scrollPane.getVerticalScrollBar().setValue(0);

		// Done Button
		doneButton = new JButton("Done");
		doneButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					if (s == null) { // Add new
						sd.addStudent(new Student(IDField.getText(),
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
								CSTCoursesTakenForDegreeField.getText(),
								CSTCoursesCurrentlyTakingField.getText(),
								CSTCoursesToBeTakenForDegreeField.getText(),
								notesArea.getText()));
					} else { // Edit
						sd.updateStudent(new Student(IDField.getText(),
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
								CSTCoursesTakenForDegreeField.getText(),
								CSTCoursesCurrentlyTakingField.getText(),
								CSTCoursesToBeTakenForDegreeField.getText(),
								notesArea.getText()),
								previousID);
					}
				} catch (TooManyEmptyFieldsException e) {
					ErrorMessagePane.showErrorMessage(AddEditStudent.this,
							"ID, Firstname, and Lastname are required.");
					return;
				} catch (InvalidIDException e) {
					ErrorMessagePane.showErrorMessage(AddEditStudent.this,
							"Invalid ID.");
					return;
				} catch (DuplicateIDException e) {
					ErrorMessagePane.showErrorMessage(AddEditStudent.this,
							"This ID already exists.");
					return;
				} catch (InvalidZipCodeException e) {
					ErrorMessagePane.showErrorMessage(AddEditStudent.this,
							"Invalid Zip Code.");
					return;
				} catch (InvalidPhoneNumberException e) {
					ErrorMessagePane.showErrorMessage(AddEditStudent.this,
							"Invalid Phone Number.");
					return;
				} catch (InvalidEmailException e) {
					ErrorMessagePane.showErrorMessage(AddEditStudent.this,
							"Invalid Email.");
					return;
				} catch (InvalidCourseException e) {
					ErrorMessagePane.showErrorMessage(AddEditStudent.this,
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
			}
		});

		add(scrollPane, BorderLayout.CENTER);
		add(doneButton, BorderLayout.SOUTH);
		setVisible(true);
	}

	/**
	 * @return A JPanel with a separator in it.
	 */
	private static JPanel addSeparator() {
		JPanel outputPanel = new JPanel(new BorderLayout());
		JSeparator separator = new JSeparator();
		outputPanel.add(separator, BorderLayout.CENTER);
		return outputPanel;
	}

	private static JPanel addHeader(String title, final JPanel hidePanel) {
		JPanel outputPanel = new JPanel(new BorderLayout());

		JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
//		JPanel innerPanel = new JPanel(new BorderLayout());
		final JButton hideButton = new JButton("-");
		hideButton.setPreferredSize(new Dimension(45, 15));
//		hideButton.setMinimumSize(new Dimension(45, 15));
//		hideButton.setMaximumSize(new Dimension(45, 15));
		hideButton.setFocusable(false);
		JLabel mainLabel = new JLabel(title);
		innerPanel.add(hideButton);//, BorderLayout.WEST);
		innerPanel.add(mainLabel);//, BorderLayout.CENTER);

		outputPanel.add(addSeparator(), BorderLayout.NORTH);
		outputPanel.add(innerPanel, BorderLayout.CENTER);
		if (hidePanel != null) {
			hideButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (hidePanel.isVisible()) {
						hidePanel.setVisible(false);
						hideButton.setText("+");
					} else {
						hidePanel.setVisible(true);
						hideButton.setText("-");
					}
				}
			});
			outputPanel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (e.getClickCount() == 2) {
						if (hidePanel.isVisible()) {
							hidePanel.setVisible(false);
							hideButton.setText("+");
						} else {
							hidePanel.setVisible(true);
							hideButton.setText("-");
						}
					}
				}
			});
		}
		return outputPanel;
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

	private static JPanel makeCSTPanel(String msg, String[] data) {
		JPanel outputPanel = new JPanel(new BorderLayout(10, 5));
		JLabel msgLabel = new JLabel(msg);
		JList list = new JList(data);

		JButton addButton = new JButton("Add");//"   Add   "
//		addButton.setBounds(addButton.getX(), addButton.getY(), 50, 100);
//		addButton.setPreferredSize(new Dimension(50, 20));
		JButton removeButton = new JButton("Remove");
//		removeButton.setPreferredSize(new Dimension(100, 200));
//		JPanel buttonPanel = new JPanel(new BorderLayout(0, 10));
		JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 0, 20));
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
//		buttonPanel.add(addButton, BorderLayout.NORTH);
//		buttonPanel.add(removeButton, BorderLayout.NORTH);

		outputPanel.add(list, BorderLayout.WEST);
		outputPanel.add(msgLabel, BorderLayout.NORTH);
		outputPanel.add(buttonPanel, BorderLayout.CENTER);
		return outputPanel;
	}
}