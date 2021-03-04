package jadvise.guitools.textfields;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;

/**
 * @author David Pirraglia
 */
public class ZipTextField extends JFormattedTextField {

	public ZipTextField(int columns) {
		try {
			MaskFormatter mask = new MaskFormatter("#####");
			mask.setAllowsInvalid(false);
			setFormatter(mask);
			setFocusLostBehavior(JFormattedTextField.PERSIST);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		setColumns(columns);
		setText("");

		// Automatically move cursor to beginning when there's
		// no information in the TextField.
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (getText().equals("     ")) {
					setCaretPosition(0);
				}
			}
		});
	}
}
