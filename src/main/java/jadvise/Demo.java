package jadvise;

import jadvise.gui.Login;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author David Pirraglia
 */
public class Demo {

	@SuppressWarnings("SpellCheckingInspection")
	public static void main(String[] args) {
		try {
			// Set Java L&F to Nimbus
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException |
				InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		new Login();
	}
}
