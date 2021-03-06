package jadvise.guitools;

import jadvise.gui.JAdvise;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * @author David Pirraglia
 */
public class PrebuiltDialogs {

	/**
	 * Creates an Error dialog.
	 *
	 * @param parentComponent The parent window.
	 * @param msg             The message to display.
	 */
	public static void showErrorDialog(Component parentComponent, Object msg) {
		JOptionPane.showMessageDialog(
				parentComponent,
				msg,
				"Error",
				JOptionPane.ERROR_MESSAGE
		);
	}

	/**
	 * Creates an Ok/Cancel dialog with a warning icon.
	 *
	 * @param parentComponent The parent window.
	 * @param msg             The message to display.
	 * @param title           The title of the dialog.
	 * @return True if user selects OK, false otherwise.
	 */
	public static boolean showConfirmDialog(Component parentComponent, Object msg, String title) {
		return JOptionPane.showConfirmDialog(
				parentComponent,
				msg,
				title,
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE
		) == JOptionPane.OK_OPTION;
	}

	/**
	 * Creates a Yes/No dialog with a warning icon.
	 *
	 * @param parentComponent The parent window.
	 * @param msg             The message to display.
	 * @param title           The title of the dialog.
	 * @return True if user selects Yes, false otherwise.
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean showYesNoDialog(Component parentComponent, Object msg, String title) {
		return JOptionPane.showConfirmDialog(
				parentComponent,
				msg,
				title,
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE
		) == JOptionPane.YES_OPTION;
	}

	/**
	 * Creates a Yes/No dialog asking if the user would like to quit.
	 *
	 * @param parentComponent The parent window.
	 */
	public static void showQuitDialog(Component parentComponent) {
		if (showConfirmDialog(
				parentComponent,
				"Are you sure you want to quit?",
				JAdvise.TITLE
		)) {
			System.exit(0);
		}
	}
}
