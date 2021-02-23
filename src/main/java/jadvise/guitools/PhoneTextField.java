package jadvise.guitools;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class PhoneTextField extends JFormattedTextField {

	public PhoneTextField(int columns) {
		try {
			MaskFormatter mask = new MaskFormatter("(###) ###-####");
			mask.setAllowsInvalid(false);
			setFormatter(mask);
			setFocusLostBehavior(JFormattedTextField.PERSIST);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		setColumns(columns);
	}
}
