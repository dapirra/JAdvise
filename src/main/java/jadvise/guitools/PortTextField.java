package jadvise.guitools;

import jadvise.objects.PortFormat;

import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Color;

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
		} catch (Exception ignored) {}
		setForeground(Color.RED);
	}
}
