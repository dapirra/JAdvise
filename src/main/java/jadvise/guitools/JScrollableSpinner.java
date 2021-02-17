package jadvise.guitools;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

/**
 * @author David Pirraglia
 */
public class JScrollableSpinner extends JSpinner {

	private boolean ignoresFocus;

	public JScrollableSpinner() {
		ignoresFocus = false;
		addListener();
	}

	public JScrollableSpinner(SpinnerModel model) {
		super(model);
		ignoresFocus = false;
		addListener();
	}

	public JScrollableSpinner(SpinnerModel model, boolean ignoresFocus) {
		super(model);
		this.ignoresFocus = ignoresFocus;
		addListener();
	}

	public boolean isIgnoringFocus() {
		return ignoresFocus;
	}

	public void setIgnoresFocus(boolean ignoresFocus) {
		this.ignoresFocus = ignoresFocus;
	}

	private void addListener() {
		addMouseWheelListener(mouseWheelEvent -> {
			if (ignoresFocus || ((DefaultEditor) getEditor()).getTextField().isFocusOwner()) {
				// Scroll Down
				if (mouseWheelEvent.getWheelRotation() > 0) {
					if (getModel().getPreviousValue() != null) {
						setValue(getModel().getPreviousValue());
					}
				// Scroll Up
				} else {
					if (getModel().getNextValue() != null) {
						setValue(getModel().getNextValue());
					}
				}
			}
		});
	}
}
