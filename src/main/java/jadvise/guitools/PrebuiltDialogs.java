package jadvise.guitools;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * @author David Pirraglia
 */
public class PrebuiltDialogs {

	public static void showErrorDialog(Component parentComponent, String msg) {
		JOptionPane.showMessageDialog(
				parentComponent,
				msg,
				"Error",
				JOptionPane.ERROR_MESSAGE
		);
	}

	public static int showConfirmDialog(String msg, String title) {
		return JOptionPane.showConfirmDialog(
				null,
				msg,
				title,
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE
		);
	}
}
