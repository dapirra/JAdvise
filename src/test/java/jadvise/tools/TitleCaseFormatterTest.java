package jadvise.tools;

import junit.framework.TestCase;

public class TitleCaseFormatterTest extends TestCase {

	public void testToTitleCase() {
		assertEquals("Test T Ing", TitleCaseFormatter.toTitleCase("test t ing"));
	}
}
