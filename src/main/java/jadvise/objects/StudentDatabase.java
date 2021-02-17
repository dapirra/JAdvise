package jadvise.objects;

import com.opencsv.CSVWriter;
import jadvise.exceptions.id.DuplicateIDException;
import jadvise.gui.JAdvise;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author David Pirraglia
 */
public class StudentDatabase {

	public static final String[] CSV_HEADERS = {
			"ID Number",
			"First Name",
			"MI",
			"Last Name",
			"GPA",
			"Home Campus",
			"Major",
			"House Number",
			"Street",
			"City",
			"State",
			"Zip",
			"Home Phone Number",
			"Cell Phone Number",
			"Email Address",
			"CST Courses Taken",
			"CST Courses Currently Taking",
			"CST Courses To Be Taken",
			"Notes"
	};

	private ArrayList<Student> students;
	private ArrayList<Student> searchBackup;
	private int searchPreviousLength = 0;
	private JTable table;
	private final MySQLAccount account;

	public StudentDatabase(MySQLAccount account) {
		this.students = new ArrayList<>();
		this.account = account;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void addStudent(Student s) throws DuplicateIDException {
		if (findStudent(s.getIdNumber()) != -1) {
			throw new DuplicateIDException();
		} else {
			students.add(s);
		}
	}

	public void removeStudent(String ID) {
		students.remove(findStudent(ID));
	}

	public void updateStudent(Student s, String previousID) {
//		if (findStudent(s.getIdNumber()) != -1) {
//			throw new DuplicateIDException();
//		} else {
		students.set(findStudent(previousID), s);
//		}
	}

	public ArrayList<Student> getStudents() {
		return (ArrayList<Student>) students.clone();
	}

	public Student getStudent(int index) {
		System.out.println(index + "\n" + students.size());
		return students.get(findStudent(index + ""));
	}

	public Student getStudent(String ID) {
		System.out.println(ID + "\n" + students.size());
		return students.get(findStudent(ID));
//		return students.get(findStudent(index + ""));
	}

	public int findStudent(String ID) {
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i).getIdNumber().equals(ID)) {
				return i;
			}
		}
		return -1;
	}

	public String[][] getTableData() {
		String[][] output = new String[students.size()][19];
		for (int i = 0; i < output.length; i++) {
			output[i] = students.get(i).toArray(false);
		}
		return output;
	}

	public void search(String search) {
		int currentLength = search.length();
		if (searchPreviousLength == 0 && currentLength >= 1) { // Backup
			System.out.println("Backup");
			searchBackup = (ArrayList<Student>) students.clone();
		} else if (searchPreviousLength >= 1 && currentLength == 0) { //Restore
			System.out.println("Restore");
			students = searchBackup;
		}
		if (searchPreviousLength < currentLength) { // Text Added
			System.out.println("Text Added:\t" + search);
			for (int i = 0; i < students.size(); i++) {
				if (!students.get(i).contains(search)) {
					students.remove(i);
					i--;
				}
			}
		} else if (searchPreviousLength > currentLength && currentLength != 0) { // Text Removed
			System.out.println("Text Removed:\t" + search);
			students = (ArrayList<Student>) searchBackup.clone();
			for (int i = 0; i < students.size(); i++) {
				if (!students.get(i).contains(search)) {
					students.remove(i);
					i--;
				}
			}
		}
		searchPreviousLength = currentLength;
	}

	public void updateTable() {
		if (table != null) {
			table.setModel(new DefaultTableModel(getTableData(), JAdvise.COLUMN_NAMES));
			JAdvise.resetAllColumnWidths(table);
		} else {
			System.out.println("No table");
		}
	}

	public void exportToCSV(String fileName) {
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(fileName));
			csvWriter.writeNext(CSV_HEADERS);
			for (Student student : students) {
				csvWriter.writeNext(student.toArray(false));
			}
			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadData() throws SQLException, ClassNotFoundException {

		// Connect to a database
		Connection connection = connectToDatabase();

		// Create a statement
		Statement statement = connection.createStatement();

		// Execute statement to retrieve all data
		ResultSet resultSet = statement.executeQuery("SELECT * FROM `students` WHERE 1");

		// Iterate through the result and add the new student
		String[] tempStudent = new String[19];
		while (resultSet.next()) {
			for (int i = 0; i < 19; i++) {
				tempStudent[i] = resultSet.getString(i + 1);
			}
			students.add(new Student(tempStudent));
		}

		// Close the connection
		connection.close();
	}

	// TODO: use sql UPDATE to save changes instead of this
	public void saveData() throws SQLException, ClassNotFoundException {

		// Connect to a database
		Connection connection = connectToDatabase();

		// Create a statement
		Statement statement = connection.createStatement();

		// Execute statement to clear all data
		statement.executeUpdate("DELETE FROM `students` WHERE 1");

		// Add all students to back to the database
		String temp, tempArray[];
		for (Student student : students) {
			tempArray = student.toArray(true);
			temp = "";
			for (String s : tempArray) {
				if (s == null) {
					s = "";
				}
				temp += ",'" + s + "'";
			}
			System.out.println(temp.substring(1));
			statement.executeUpdate("INSERT INTO `students`("
					+ "`idNumber`, `firstName`, `middleInitial`, `lastName`, "
					+ "`gpa`, `homeCampus`, `major`, `houseNumber`, `street`, "
					+ "`city`, `state`, `zip`, `homePhone`, `cellPhone`, "
					+ "`emailAddress`, `CSTCoursesTakenForDegree`, "
					+ "`CSTCoursesCurrentlyTaking`, "
					+ "`CSTCoursesToBeTakenForDegree`, `notes`)"
					+ " VALUES (" + temp.substring(1) + ")");
		}
		System.out.println("----------");

		// Close the connection
		connection.close();
	}

	public void clearData() throws SQLException, ClassNotFoundException {

		// Clears Arraylist
		students.clear();

		// Connect to a database
		Connection connection = connectToDatabase();

		// Create a statement
		Statement statement = connection.createStatement();

		// Execute statement clear all data
		statement.executeUpdate("DELETE FROM `students` WHERE 1");

		// Close the connection
		connection.close();
	}

	private Connection connectToDatabase() throws SQLException, ClassNotFoundException {
		// Load the JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		// Connect to the database
		return DriverManager.getConnection(
				account.getMySQLLink(),
				account.getUsername(),
				account.getPassword()
		);
	}
}
