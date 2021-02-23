package jadvise.objects;

import junit.framework.TestCase;

import static jadvise.objects.Student.isValidPhoneNumber;

public class StudentTest extends TestCase {

	public void testRandomStudent() {
		Student student = new Student(-6837374334916766136L);
		assertEquals("Raymond", student.getFirstName());
	}

	public void testPhoneNumber() {
		assertTrue(isValidPhoneNumber("1234567890"));
		assertTrue(isValidPhoneNumber("(123) 456-7890"));
		assertTrue(isValidPhoneNumber("123-456-7890"));
		assertTrue(isValidPhoneNumber("(-123-456-7890-)"));

		assertFalse(isValidPhoneNumber("abc def ghij"));
		assertFalse(isValidPhoneNumber("abcdefg1234567890"));
		assertFalse(isValidPhoneNumber("1234567890A"));

		assertFalse(isValidPhoneNumber("123"));
		assertFalse(isValidPhoneNumber("123456789"));

		assertTrue(isValidPhoneNumber(""));
	}
}
