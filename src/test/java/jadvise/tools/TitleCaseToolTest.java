package jadvise.tools;

import junit.framework.TestCase;

public class TitleCaseToolTest extends TestCase {

	public void testToTitleCase() {
		assertEquals("Test T Ing", TitleCaseTool.toTitleCase("test t ing"));
	}
}
