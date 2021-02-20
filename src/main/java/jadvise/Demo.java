package jadvise;

import jadvise.gui.Login;

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

//			for (UIManager.LookAndFeelInfo lf : UIManager.getInstalledLookAndFeels()) {
//				System.out.println(lf);
//			}
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException |
				InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		new Login();
	}
}
