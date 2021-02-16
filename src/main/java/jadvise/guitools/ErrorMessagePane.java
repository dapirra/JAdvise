package jadvise.guitools;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * @author David Pirraglia
 */
public class ErrorMessagePane {

	public static void showErrorMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void showErrorMessage(Component c, String msg) {
		JOptionPane.showMessageDialog(c, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static int showConfirmError(String msg) {
		return JOptionPane.showConfirmDialog(null, msg, "Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
	}
}
