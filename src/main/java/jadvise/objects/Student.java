package jadvise.objects;

import jadvise.exceptions.InvalidCourseException;
import jadvise.exceptions.InvalidEmailException;
import jadvise.exceptions.InvalidPhoneNumberException;
import jadvise.exceptions.InvalidZipCodeException;
import jadvise.exceptions.TooManyEmptyFieldsException;
import jadvise.exceptions.id.InvalidIDException;
import jadvise.tools.TelephoneFormatter;

import java.util.Random;
import java.util.regex.Pattern;

import static jadvise.tools.Info.*;
import static jadvise.tools.TitleCaseFormatter.toTitleCase;

/**
 * @author David Pirraglia
 */
public final class Student {

	// Student Type Variables
	public static final int EXISTING_STUDENT = 0;
	public static final int MODIFIED_STUDENT = 1;
	public static final int NEW_STUDENT = 2;

	// Campus Variables
	public static final int AMMERMAN_CAMPUS = 0;
	public static final int EASTERN_CAMPUS = 1;
	public static final int GRANT_CAMPUS = 2;

	// Major Variables
	public static final int CS_MAJOR = 0;
	public static final int NETWORK_MAJOR = 1;
	public static final int CIS_MAJOR = 2;
	public static final int WEB_MAJOR = 3;
	public static final int OTHER_MAJOR = 4;

	// Instance Variables
	private int studentType;
	private String previousIdNumber = "";
	private String idNumber = "";
	private String firstName = "";
	private String middleInitial = "";
	private String lastName = "";
	private String gpa = "";
	private int homeCampus;
	private int major;
	private String houseNumber = "";
	private String street = "";
	private String city = "";
	private int state;
	private String zip = "";
	private String homePhone = "";
	private String cellPhone = "";
	private String emailAddress = "";
	private String CSTCoursesTakenForDegree = "";
	private String CSTCoursesCurrentlyTaking = "";
	private String CSTCoursesToBeTakenForDegree = "";
	private String notes = "";

	/**
	 * Creates a student with the minimum number of fields needed.
	 *
	 * @param idNumber Student's ID number
	 * @param firstName Student's first name
	 * @param lastName Student's last name
	 * @throws InvalidIDException Student ID is invalid
	 * @throws TooManyEmptyFieldsException ID, first, and last name are required
	 */
	@SuppressWarnings("unused")
	public Student(String idNumber, String firstName, String lastName) throws InvalidIDException, TooManyEmptyFieldsException {
		if (firstName.isEmpty() && lastName.isEmpty()) {
			throw new TooManyEmptyFieldsException();
		}
		this.studentType = NEW_STUDENT;
		setIdNumber(idNumber);
		setFirstName(firstName);
		setLastName(lastName);
	}

	/**
	 * Creates a new student requiring all parameters to be passed.
	 *
	 * @param idNumber Student's ID number
	 * @param firstName Student's first name
	 * @param middleInitial Student's middle initial
	 * @param lastName Student's last name
	 * @param gpa Student's GPA
	 * @param homeCampus <code>AMMERMAN_CAMPUS</code> = 0,
	 *                   <code>EASTERN_CAMPUS</code> = 1,
	 *                   <code>GRANT_CAMPUS</code> = 2
	 * @param major <code>CS_MAJOR</code> = 0,
	 *              <code>NETWORK_MAJOR</code> = 1,
	 *              <code>CIS_MAJOR</code> = 2,
	 *              <code>WEB_MAJOR</code> = 3,
	 *              <code>OTHER_MAJOR</code> = 4
	 * @param houseNumber Student's house number
	 * @param street Student's street address
	 * @param city Student's city
	 * @param state Student's state
	 * @param zip Student's zipcode
	 * @param homePhone Student's home phone number
	 * @param cellPhone Student's cell phone number
	 * @param emailAddress Student's email address
	 * @param CSTCoursesTakenForDegree Courses already taken
	 * @param CSTCoursesCurrentlyTaking Courses currently taking
	 * @param CSTCoursesToBeTakenForDegree Courses needed to be taken
	 * @param notes Notes regarding the student
	 * @throws InvalidIDException Student ID is invalid
	 * @throws InvalidEmailException Email is improperly formatted
	 * @throws InvalidPhoneNumberException Phone number is improperly formatted
	 * @throws TooManyEmptyFieldsException ID, first, and last name are required
	 */
	public Student(String idNumber,
			String firstName, String middleInitial, String lastName,
			String gpa, String homeCampus, String major,
			String houseNumber, String street, String city, String state, String zip,
			String homePhone, String cellPhone, String emailAddress,
			String CSTCoursesTakenForDegree, String CSTCoursesCurrentlyTaking,
			String CSTCoursesToBeTakenForDegree, String notes) throws
			InvalidIDException, InvalidEmailException,
			InvalidPhoneNumberException, TooManyEmptyFieldsException {
		if (firstName.isEmpty() || lastName.isEmpty()) {
			throw new TooManyEmptyFieldsException();
		}
		this.studentType = EXISTING_STUDENT;
		setIdNumber(idNumber);
		setFirstName(firstName);
		setMiddleInitial(middleInitial);
		setMiddleInitial(middleInitial);
		setLastName(lastName);
		setGpa(gpa);
		setHomeCampus(Integer.parseInt(homeCampus));
		setMajor(Integer.parseInt(major));
		setHouseNumber(houseNumber);
		setStreet(street);
		setCity(city);
		setState(Integer.parseInt(state));
		setZip(zip);
		setHomePhone(homePhone);
		setCellPhone(cellPhone);
		setEmailAddress(emailAddress);
		setCSTCoursesTakenForDegree(CSTCoursesTakenForDegree);
		setCSTCoursesCurrentlyTaking(CSTCoursesCurrentlyTaking);
		setCSTCoursesToBeTakenForDegree(CSTCoursesToBeTakenForDegree);
		setNotes(notes);
	}

	/**
	 * Creates a new student from an array of data.
	 *
	 * @param input Array of length 19 that holds each of the fields
	 * @throws InvalidIDException Student ID is invalid
	 * @throws InvalidEmailException Email is improperly formatted
	 * @throws InvalidPhoneNumberException Phone number is improperly formatted
	 */
	public Student(String[] input) throws InvalidIDException, InvalidEmailException, InvalidPhoneNumberException {
		if (!isValidID(input[0])) {
			throw new InvalidIDException();
		}
		this.studentType = EXISTING_STUDENT;
		setIdNumber(input[0]);
		setFirstName(input[1]);
		setMiddleInitial(input[2]);
		setLastName(input[3]);
		setGpa(input[4]);
		setHomeCampus(Integer.parseInt(input[5]));
		setMajor(Integer.parseInt(input[6]));
		setHouseNumber(input[7]);
		setStreet(input[8]);
		setCity(input[9]);
		setState(Integer.parseInt(input[10]));
		setZip(input[11]);
		setHomePhone(input[12]);
		setCellPhone(input[13]);
		setEmailAddress(input[14]);
		setCSTCoursesTakenForDegree(input[15]);
		setCSTCoursesCurrentlyTaking(input[16]);
		setCSTCoursesToBeTakenForDegree(input[17]);
		setNotes(input[18]);
	}

	/**
	 * Generates a random student from a given seed.
	 */
	public Student(long seed) {
		final Random random = new Random();
		random.setSeed(seed);
		this.studentType = NEW_STUDENT;
		setIdNumber(String.format("%08d", random.nextInt(99999999)));
		setFirstName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]);
		setMiddleInitial((char) (random.nextInt(26) + 65) + "");
		setLastName(LAST_NAMES[random.nextInt(LAST_NAMES.length)]);
		setGpa(GPA_VALUES[random.nextInt(GPA_VALUES.length)]);
		setHomeCampus(random.nextInt(3));
		setMajor(random.nextInt(5));
		setHouseNumber(random.nextInt(999) + "");
		setStreet(STREET_NAMES[random.nextInt(STREET_NAMES.length)] + " Street");
		setCity(TOWNS[random.nextInt(TOWNS.length)]);
		setState(random.nextInt(STATES.length));
		setZip(String.format("%05d", random.nextInt(99999)));
		setEmailAddress(String.format(
				"%s.%s@mail.sunysuffolk.edu",
				this.firstName.toLowerCase(),
				this.lastName.toLowerCase()
		));
		setHomePhone(generateRandomPhoneNumber(random));
		setCellPhone(generateRandomPhoneNumber(random));
		setCSTCoursesTakenForDegree(generateRandomCourseList(random));
		setCSTCoursesCurrentlyTaking(generateRandomCourseList(random));
		setCSTCoursesToBeTakenForDegree(generateRandomCourseList(random));
		setNotes("Seed used: " + seed);
	}

	/**
	 * Generates a random student.
	 */
	public Student() {
		this(new Random().nextLong());
	}

	public int getStudentType() {
		return studentType;
	}

	public void setStudentType(int studentType) {
		this.studentType = studentType;
	}

	public String getPreviousIdNumber() {
		return previousIdNumber;
	}

	public void setPreviousIdNumber(String previousIdNumber) {
		this.previousIdNumber = previousIdNumber;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) throws InvalidIDException {
		idNumber = idNumber.trim();
		if (!isValidID(idNumber)) {
			throw new InvalidIDException();
		}
		this.idNumber = idNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = toTitleCase(firstName.trim());
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		if (middleInitial.isEmpty()) {
			this.middleInitial = "";
		} else {
			this.middleInitial = middleInitial.substring(0, 1).toUpperCase();
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = toTitleCase(lastName.trim());
	}

	public String getGpa() {
		return gpa;
	}

	public void setGpa(String gpa) {
		this.gpa = gpa;
	}

	public int getHomeCampus() {
		return homeCampus;
	}

	public static String getHomeCampus(int campus) {
		switch (campus) {
			case AMMERMAN_CAMPUS:
				return "Ammerman";
			case EASTERN_CAMPUS:
				return "Eastern";
			case GRANT_CAMPUS:
				return "Grant";
			default:
				throw new IllegalArgumentException();
		}
	}

	public void setHomeCampus(int homeCampus) {
		this.homeCampus = homeCampus;
	}

	public int getMajor() {
		return major;
	}

	public static String getMajor(int major) {
		switch (major) {
			case CS_MAJOR:
				return "CS";
			case NETWORK_MAJOR:
				return "Network";
			case CIS_MAJOR:
				return "CIS";
			case WEB_MAJOR:
				return "Web";
			case OTHER_MAJOR:
				return "Other";
			default:
				throw new IllegalArgumentException();
		}
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber.trim();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city.trim();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) throws InvalidZipCodeException {
		zip = zip.trim();
		if (!isValidZipCode(zip)) {
			throw new InvalidZipCodeException();
		}
		this.zip = zip;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) throws InvalidPhoneNumberException {
		this.homePhone = setPhoneNumber(homePhone);
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) throws InvalidPhoneNumberException {
		this.cellPhone = setPhoneNumber(cellPhone);
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) throws InvalidEmailException {
		emailAddress = emailAddress.trim();
		if (!isValidEmail(emailAddress)) {
			throw new InvalidEmailException();
		}
		this.emailAddress = emailAddress;
	}

	public String getCSTCoursesTakenForDegree() {
		return CSTCoursesTakenForDegree;
	}

	public void setCSTCoursesTakenForDegree(String courses) throws InvalidCourseException {
		courses = courses.trim().replace(" ", "").toUpperCase();
		if (!isValidCourseInfo(courses)) {
			throw new InvalidCourseException();
		}
		this.CSTCoursesTakenForDegree = courses;
	}

	public String getCSTCoursesCurrentlyTaking() {
		return CSTCoursesCurrentlyTaking;
	}

	public void setCSTCoursesCurrentlyTaking(String courses) throws InvalidCourseException {
		courses = courses.trim().replace(" ", "").toUpperCase();
		if (!isValidCourseInfo(courses)) {
			throw new InvalidCourseException();
		}
		this.CSTCoursesCurrentlyTaking = courses;
	}

	public String getCSTCoursesToBeTakenForDegree() {
		return CSTCoursesToBeTakenForDegree;
	}

	public void setCSTCoursesToBeTakenForDegree(String courses) throws InvalidCourseException {
		courses = courses.trim().replace(" ", "").toUpperCase();
		if (!isValidCourseInfo(courses)) {
			throw new InvalidCourseException();
		}
		this.CSTCoursesToBeTakenForDegree = courses;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes.trim();
	}

	public String[] toArray(boolean containsNumbers) {
		String[] outputArray = new String[19];
		outputArray[0] = idNumber;
		outputArray[1] = firstName;
		outputArray[2] = middleInitial;
		outputArray[3] = lastName;
		outputArray[4] = gpa + "";
		if (containsNumbers) {
			outputArray[5] = homeCampus + "";
			outputArray[6] = major + "";
			outputArray[10] = state + "";
		} else {
			outputArray[5] = getHomeCampus(homeCampus);
			outputArray[6] = getMajor(major);
			outputArray[10] = STATES[state];
		}
		outputArray[7] = houseNumber;
		outputArray[8] = street;
		outputArray[9] = city;
		outputArray[11] = zip;
		outputArray[12] = homePhone;
		outputArray[13] = cellPhone;
		outputArray[14] = emailAddress;
		outputArray[15] = CSTCoursesTakenForDegree;
		outputArray[16] = CSTCoursesCurrentlyTaking;
		outputArray[17] = CSTCoursesToBeTakenForDegree;
		outputArray[18] = notes;
		return outputArray;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean contains(String search) {
		search = search.toLowerCase();
		return idNumber.contains(search)
				|| firstName.toLowerCase().contains(search)
				|| middleInitial.toLowerCase().contains(search)
				|| lastName.toLowerCase().contains(search)
				|| (gpa + "").contains(search)
				|| getHomeCampus(homeCampus).toLowerCase().contains(search)
				|| getMajor(major).toLowerCase().contains(search)
				|| houseNumber.toLowerCase().contains(search)
				|| street.toLowerCase().contains(search)
				|| city.toLowerCase().contains(search)
				|| STATES[state].toLowerCase().contains(search)
				|| zip.contains(search)
				|| homePhone.contains(search)
				|| cellPhone.contains(search)
				|| emailAddress.toLowerCase().contains(search)
				|| CSTCoursesTakenForDegree.toLowerCase().contains(search)
				|| CSTCoursesCurrentlyTaking.toLowerCase().contains(search)
				|| CSTCoursesToBeTakenForDegree.toLowerCase().contains(search)
				|| notes.toLowerCase().contains(search);
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean isValidID(String ID) {
		return ID.length() == 8 && ID.matches("[0-9]{8}");
	}

	public static boolean isValidEmail(String email) {
		return email.matches(".+@.+\\..+") || email.isEmpty();
	}

	public static boolean isValidZipCode(String zip) {
		return (zip.length() == 5 && zip.matches("[0-9]{5}")) || zip.isEmpty();
	}

	public static boolean isValidPhoneNumber(String num) {
		for (char c : num.toCharArray()) {
			if (Character.isAlphabetic(c)) {
				return false;
			}
		}
		num = TelephoneFormatter.unFormat(num);
		return num.isEmpty() || num.length() == 10;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private static boolean isValidCourseInfo(String courses) {
		return courses.matches("(CST[0-9]{3},?)*");
	}

	private String setPhoneNumber(String num) throws InvalidPhoneNumberException {
		if (!isValidPhoneNumber(num)) {
			throw new InvalidPhoneNumberException();
		}
		return TelephoneFormatter.format(num);
	}
}
