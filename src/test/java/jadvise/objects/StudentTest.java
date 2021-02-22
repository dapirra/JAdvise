package jadvise.objects;

import junit.framework.TestCase;

public class StudentTest extends TestCase {

	public void testStudent() {
		Student student = new Student(-6837374334916766136L);
		assertEquals("Raymond", student.getFirstName());
	}
}
