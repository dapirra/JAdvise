package jadvise;

import junit.framework.TestCase;

import javax.swing.UIManager;

public class DemoTest extends TestCase {

	public void testLookAndFeel() {

		// Windows L&F
//		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		// Linux L&F
//		UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

		for (UIManager.LookAndFeelInfo lf : UIManager.getInstalledLookAndFeels()) {
			System.out.println(lf);
		}
	}
}
