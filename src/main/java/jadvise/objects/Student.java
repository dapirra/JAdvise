package jadvise.objects;

import jadvise.exceptions.InvalidCourseException;
import jadvise.exceptions.InvalidEmailException;
import jadvise.exceptions.InvalidPhoneNumberException;
import jadvise.exceptions.InvalidZipCodeException;
import jadvise.exceptions.TooManyEmptyFieldsException;
import jadvise.exceptions.id.InvalidIDException;
import jadvise.tools.TelephoneFormatter;
import jadvise.tools.TitleCaseFormatter;

import java.util.Random;

import static jadvise.tools.Info.*;

/**
 * @author David Pirraglia
 */
public final class Student {

	// Campus Variales
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
	private String idNumber;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String gpa;
	private int homeCampus;
	private int major;
	private String houseNumber;
	private String street;
	private String city;
	private int state;
	private String zip;
	private String homePhone;
	private String cellPhone;
	private String emailAddress;
	private String CSTCoursesTakenForDegree;
	private String CSTCoursesCurrentlyTaking;
	private String CSTCoursesToBeTakenForDegree;
	private String notes;
	private static final Random random = new Random();

	public Student(String idNumber, String firstName, String lastName) throws InvalidIDException, TooManyEmptyFieldsException {
		if (firstName.isEmpty() && lastName.isEmpty()) {
			throw new TooManyEmptyFieldsException();
		}
		setIdNumber(idNumber);
		setFirstName(firstName);
		setLastName(lastName);
	}

	public Student(String idNumber, String firstName, String middleInitial, String lastName,
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
		setIdNumber(idNumber);
		setFirstName(firstName);
		setMiddleInitial(middleInitial);
		setMiddleInitial(middleInitial);
		setLastName(lastName);
		this.gpa = gpa;
		this.homeCampus = Integer.parseInt(homeCampus);
		this.major = Integer.parseInt(major);
		this.houseNumber = houseNumber;
		this.street = street;
		this.city = city;
		this.state = Integer.parseInt(state);
		setZip(zip);
		setHomePhone(homePhone);
		setCellPhone(cellPhone);
		setEmailAddress(emailAddress);
		setCSTCoursesTakenForDegree(CSTCoursesTakenForDegree);
		setCSTCoursesCurrentlyTaking(CSTCoursesCurrentlyTaking);
		setCSTCoursesToBeTakenForDegree(CSTCoursesToBeTakenForDegree);
		this.notes = notes;
	}

	public Student(String[] input) throws InvalidIDException, InvalidEmailException, InvalidPhoneNumberException {
		if (!isValidID(input[0])) {
			throw new InvalidIDException();
		}
		setIdNumber(input[0]);
		setFirstName(input[1]);
		setMiddleInitial(input[2]);
		setLastName(input[3]);
		this.gpa = input[4];
		this.homeCampus = Integer.parseInt(input[5]);
		this.major = Integer.parseInt(input[6]);
		this.houseNumber = input[7];
		this.street = input[8];
		this.city = input[9];
		this.state = Integer.parseInt(input[10]);
		setZip(input[11]);
		setHomePhone(input[12]);
		setCellPhone(input[13]);
		setEmailAddress(input[14]);
		setCSTCoursesTakenForDegree(input[15]);
		setCSTCoursesCurrentlyTaking(input[16]);
		setCSTCoursesToBeTakenForDegree(input[17]);
		this.notes = input[18];
	}

	public Student() {
		this.idNumber = String.format("%08d", random.nextInt(99999999));
		this.firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
		this.middleInitial = (char) (random.nextInt(26) + 65) + "";
		this.lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
		this.gpa = GPA_VALUES[random.nextInt(GPA_VALUES.length)];
		this.homeCampus = random.nextInt(3);
		this.major = random.nextInt(5);
		this.houseNumber = random.nextInt(999) + "";
		this.street = STREET_NAMES[random.nextInt(STREET_NAMES.length)] + " Street";
		this.city = TOWNS[random.nextInt(TOWNS.length)];
		this.state = random.nextInt(STATES.length);
		this.zip = String.format("%05d", random.nextInt(99999));
		this.emailAddress = String.format(
				"%s.%s@mail.sunysuffolk.edu",
				this.firstName.toLowerCase(),
				this.lastName.toLowerCase()
		);
		this.homePhone = String.format(
				"(%03d) %03d - %04d",
				random.nextInt(999),
				random.nextInt(999),
				random.nextInt(9999)
		);
		this.cellPhone = String.format(
				"(%03d) %03d - %04d",
				random.nextInt(999),
				random.nextInt(999),
				random.nextInt(9999)
		);
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) throws InvalidIDException {
		if (!isValidID(idNumber)) {
			throw new InvalidIDException();
		}
		this.idNumber = idNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = TitleCaseFormatter.toTitleCase(firstName);
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
		this.lastName = TitleCaseFormatter.toTitleCase(lastName);
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
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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
		if (!isValidEmail(emailAddress)) {
			throw new InvalidEmailException();
		}
		this.emailAddress = emailAddress;
	}

	public String getCSTCoursesTakenForDegree() {
		return CSTCoursesTakenForDegree;
	}

	public void setCSTCoursesTakenForDegree(String courses) throws InvalidCourseException {
		courses = courses.replace(" ", "").toUpperCase();
		if (!isValidCourseInfo(courses)) {
			throw new InvalidCourseException();
		}
		this.CSTCoursesTakenForDegree = courses;
	}

	public String getCSTCoursesCurrentlyTaking() {
		return CSTCoursesCurrentlyTaking;
	}

	public void setCSTCoursesCurrentlyTaking(String courses) throws InvalidCourseException {
		courses = courses.replace(" ", "").toUpperCase();
		if (!isValidCourseInfo(courses)) {
			throw new InvalidCourseException();
		}
		this.CSTCoursesCurrentlyTaking = courses;
	}

	public String getCSTCoursesToBeTakenForDegree() {
		return CSTCoursesToBeTakenForDegree;
	}

	public void setCSTCoursesToBeTakenForDegree(String courses) throws InvalidCourseException {
		courses = courses.replace(" ", "").toUpperCase();
		if (!isValidCourseInfo(courses)) {
			throw new InvalidCourseException();
		}
		this.CSTCoursesToBeTakenForDegree = courses;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public static boolean isValidID(String ID) {
		return ID.length() == 8 && ID.matches("\\d{8}");
	}

	public static boolean isValidEmail(String email) {
		return email.matches(".+@.+\\..+") || email.isEmpty();
//		return email.contains("@") && email.contains(".") || email.isEmpty();
	}

	public static boolean isValidZipCode(String zip) {
		return (zip.length() == 5 && zip.matches("\\d{5}")) || zip.isEmpty();
	}

	public static boolean isValidPhoneNumber(String num) {

		for (char c : num.toCharArray()) {
			if (Character.isAlphabetic(c)) {
				return false;
			}
		}
		return true;
//		return (!num.matches("[A-z]*")) || num.isEmpty();
	}

	private static boolean isValidCourseInfo(String courses) {
		return courses.matches("(CST\\d{3},?)*");
	}

	private String setPhoneNumber(String num) throws InvalidPhoneNumberException {
		if (!isValidPhoneNumber(num)) {
			throw new InvalidPhoneNumberException();
		} else {
			if (!TelephoneFormatter.isFormatted(num)) {
				num = TelephoneFormatter.format(num);
			}
			return num;
		}
	}
}
