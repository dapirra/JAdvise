package jadvise;

import jadvise.gui.JAdvise;
import jadvise.objects.MySQLAccount;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author David Pirraglia
 */
public class Demo {

	public static void main(String[] args) {
		try {
			// Set Java L&F to Nimbus
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

			// Set Java L&F to GTK Theme (Only works on Linux)
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

			// Don't remember what this was for
//			for (UIManager.LookAndFeelInfo lf : UIManager.getInstalledLookAndFeels()) {
//				System.out.println(lf);
//			}
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException |
				InstantiationException | IllegalAccessException e) {
		}
		new JAdvise(new MySQLAccount("root", "usbw"));
	}
}
