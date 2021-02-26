package jadvise.guitools;

import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;

public class PortTextField extends JFormattedTextField {

	private final Color FOREGROUND_BACKUP = getForeground();

	public PortTextField(String text) {
		super(new PortFormat());

		setText(text);

		getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent de) {
				checkPort();
			}

			@Override
			public void removeUpdate(DocumentEvent de) {
				checkPort();
			}

			@Override
			public void changedUpdate(DocumentEvent de) {
				checkPort();
			}
		});

		addPropertyChangeListener("value", evt -> {
			if (getText().isEmpty()) {
				setText(text);
			}
		});
	}

	private void checkPort() {
		try {
			int i = Integer.parseInt(getText());
			if (i <= 65535 && i >= 0) {
				setForeground(FOREGROUND_BACKUP);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setForeground(Color.RED);
	}

	public static class PortFormat extends DecimalFormat {

		public PortFormat() {
			super("0");
			setMaximumIntegerDigits(5);
		}

		public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
			return super.format(number, toAppendTo, pos);
		}

		public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
			return super.format(number, toAppendTo, pos);
		}

		@SuppressWarnings("UnnecessaryBoxing")
		public Number parse(String source, ParsePosition parsePosition) {
			Number result = super.parse(source, parsePosition);
			if (result == null) {
				result = new Long(3306);
			}
			return Math.abs(result.longValue());
		}
	}
}
