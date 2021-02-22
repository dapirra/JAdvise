package jadvise.tools;

import junit.framework.TestCase;

public class TitleCaseFormatterTest extends TestCase {

	public void testToTitleCase() {
		assertEquals("Test T Ing", TitleCaseFormatter.toTitleCase("test t ing"));
	}

	public void testToTitleCase2() {
		assertEquals("Test T Ing", TitleCaseFormatter.toTitleCase2("test t ing"));
	}

	public void testToTitleCase3() {
		assertEquals("Test T Ing", TitleCaseFormatter.toTitleCase3("test t ing"));
	}

	public void testToTitleCase4() {
		assertEquals("Test T Ing", TitleCaseFormatter.toTitleCase4("test t ing"));
	}
}
