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
import java.sql.PreparedStatement;
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

	private final ArrayList<Student> students;
	private final ArrayList<Student> deletedStudents;
	private JTable table;
	private final MySQLAccount account;

	public StudentDatabase(MySQLAccount account) {
		this.students = new ArrayList<>();
		this.deletedStudents = new ArrayList<>();
		this.account = account;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public void addStudent(Student s) throws DuplicateIDException {
		s.setStudentType(Student.NEW_STUDENT);
		if (findStudent(s.getIdNumber()) != -1) {
			throw new DuplicateIDException();
		}
		students.add(s);
	}

	public void removeStudent(int studentIndex) {
		deletedStudents.add(students.get(studentIndex));
		students.remove(studentIndex);
	}

	public void removeStudent(String ID) {
		int studentIndex = findStudent(ID);
		deletedStudents.add(students.get(studentIndex));
		students.remove(studentIndex);
	}

	public void updateStudent(Student updatedStudent, String previousID, int index) {
		updatedStudent.setStudentType(Student.MODIFIED_STUDENT);
		int testIndex = findStudent(updatedStudent.getIdNumber());
		if (testIndex != -1 && testIndex != index) {
			throw new DuplicateIDException();
		}
		updatedStudent.setPreviousIdNumber(previousID);
		students.set(index, updatedStudent);
	}

	public int getTotalStudents() {
		return students.size();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Student> getStudents() {
		return (ArrayList<Student>) students.clone();
	}

	public Student getStudent(int index) {
		return students.get(index);
	}

	public Student getStudent(String ID) {
		System.out.println(ID + "\n" + students.size());
		return students.get(findStudent(ID));
	}

	public int findStudent(String ID) {
		return findStudent(ID, students);
	}

	public int findStudent(String ID, ArrayList<Student> students) {
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

	public void updateTable() {
		if (table != null) {
			table.setModel(new DefaultTableModel(getTableData(), JAdvise.COLUMN_NAMES));
			JAdvise.resetAllColumnWidths(table);
			JAdvise.setUpSorter(table);
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
		Connection connection = connectToDatabase(false);

		// Create a statement
		Statement statement = connection.createStatement();

		// Create the database if it doesn't exist
		statement.execute("CREATE DATABASE IF NOT EXISTS "
				+ account.getDatabase()
				+ " DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci"
		);

		// Select the newly created database
		statement.execute("USE " + account.getDatabase());

		// Create the student table if it doesn't exist
		statement.execute(
				"CREATE TABLE IF NOT EXISTS `students` (\n" +
						"`idNumber` varchar(8) NOT NULL,\n" +
						"`firstName` varchar(30) NOT NULL,\n" +
						"`middleInitial` varchar(1) NOT NULL,\n" +
						"`lastName` varchar(50) NOT NULL,\n" +
						"`gpa` varchar(5) NOT NULL,\n" +
						"`homeCampus` tinyint(4) NOT NULL,\n" +
						"`major` tinyint(4) NOT NULL,\n" +
						"`houseNumber` varchar(10) NOT NULL,\n" +
						"`street` varchar(50) NOT NULL,\n" +
						"`city` varchar(50) NOT NULL,\n" +
						"`state` tinyint(4) NOT NULL,\n" +
						"`zip` varchar(5) NOT NULL,\n" +
						"`homePhone` varchar(16) NOT NULL,\n" +
						"`cellPhone` varchar(16) NOT NULL,\n" +
						"`emailAddress` varchar(200) NOT NULL,\n" +
						"`CSTCoursesTakenForDegree` varchar(1000) NOT NULL,\n" +
						"`CSTCoursesCurrentlyTaking` varchar(500) NOT NULL,\n" +
						"`CSTCoursesToBeTakenForDegree` varchar(1000) NOT NULL,\n" +
						"`notes` varchar(2500) NOT NULL)\n" +
						"ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci"
		);

		// Execute statement to retrieve all data
		ResultSet resultSet = statement.executeQuery(
				"SELECT * FROM `students` WHERE 1"
		);

		// Iterate through the result and read in each student
		String[] currentStudent = new String[19];
		while (resultSet.next()) {
			for (int i = 0; i < currentStudent.length; i++) {
				currentStudent[i] = resultSet.getString(i + 1);
			}
			students.add(new Student(currentStudent));
		}

		// Close the connection
		connection.close();
	}

	public void saveData() throws SQLException, ClassNotFoundException {

		// Connect to a database
		Connection connection = connectToDatabase(true);

		// Create a statement
		Statement statement = connection.createStatement();

		// Delete all students marked for deletion
		for (Student student : deletedStudents) {
			System.out.println("Deleted Student: " + student.getIdNumber());
			statement.executeUpdate(
					"DELETE FROM `students` WHERE idNumber="
							+ student.getIdNumber() + " LIMIT 1"
			);
		}
		deletedStudents.clear();

		// Loop over all of the students, checking for new/modified ones
		for (Student student : students) {
			if (student.getStudentType() != Student.EXISTING_STUDENT) {

				String[] studentData = student.toArray(true);
				PreparedStatement sql;

				// If the student has been modified, delete and reinsert it
				if (student.getStudentType() == Student.MODIFIED_STUDENT) {
					// Create a prepared UPDATE statement which prevents SQL Injection
					sql = connection.prepareStatement("UPDATE `students` SET "
							+ "idNumber = ?, "
							+ "firstName = ?, "
							+ "middleInitial = ?, "
							+ "lastName = ?, "
							+ "gpa = ?, "
							+ "homeCampus = ?, "
							+ "major = ?, "
							+ "houseNumber = ?, "
							+ "street = ?, "
							+ "city = ?, "
							+ "state = ?, "
							+ "zip = ?, "
							+ "homePhone = ?, "
							+ "cellPhone = ?, "
							+ "emailAddress = ?, "
							+ "CSTCoursesTakenForDegree = ?, "
							+ "CSTCoursesCurrentlyTaking = ?, "
							+ "CSTCoursesToBeTakenForDegree = ?, "
							+ "notes = ? "
							+ "WHERE idNumber = ? LIMIT 1"
					);
					System.out.format(
							"Modified: %s %s %s%n",
							student.getPreviousIdNumber(),
							student.getIdNumber(),
							student.getFirstName()
					);
					sql.setString(
							studentData.length + 1,
							student.getPreviousIdNumber()
					);
				} else { // New Student
					// Create a prepared INSERT statement which prevents SQL Injection
					sql = connection.prepareStatement("INSERT INTO `students` ("
							+ "`idNumber`, "
							+ "`firstName`, "
							+ "`middleInitial`, "
							+ "`lastName`, "
							+ "`gpa`, "
							+ "`homeCampus`, "
							+ "`major`, "
							+ "`houseNumber`, "
							+ "`street`, "
							+ "`city`, "
							+ "`state`, "
							+ "`zip`, "
							+ "`homePhone`, "
							+ "`cellPhone`, "
							+ "`emailAddress`, "
							+ "`CSTCoursesTakenForDegree`, "
							+ "`CSTCoursesCurrentlyTaking`, "
							+ "`CSTCoursesToBeTakenForDegree`, "
							+ "`notes`) "
							+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
					);
				}

				// Add all the student's values to the prepared statement
				for (int i = 0; i < studentData.length; i++) {
					sql.setString(i + 1, studentData[i]);
				}

				// Execute the statement
				sql.executeUpdate();

				// Mark the student as an existing student
				student.setStudentType(Student.EXISTING_STUDENT);
			}
		}
		System.out.println("----------");

		// Close the connection
		connection.close();
	}

	public void clearData() throws SQLException, ClassNotFoundException {

		// Clears Arraylist
		students.clear();

		// Connect to a database
		Connection connection = connectToDatabase(true);

		// Create a statement
		Statement statement = connection.createStatement();

		// Execute statement to clear all data
		statement.executeUpdate("DELETE FROM `students` WHERE 1");

		// Close the connection
		connection.close();
	}

	private Connection connectToDatabase(boolean includeDatabase) throws SQLException, ClassNotFoundException {
		// Load the JDBC driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Connect to the database
		return DriverManager.getConnection(
				account.getMySQLLink(includeDatabase),
				account.getUsername(),
				account.getPassword()
		);
	}
}
