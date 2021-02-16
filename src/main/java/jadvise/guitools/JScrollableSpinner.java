package jadvise.guitools;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

/**
 * @author David Pirraglia
 */
public class JScrollableSpinner extends JSpinner {

	public JScrollableSpinner() {
		addListener();
	}

	public JScrollableSpinner(SpinnerModel model) {
		super(model);
		addListener();
	}

	private void addListener() {
		addMouseWheelListener(mouseWheelEvent -> {
			if (((DefaultEditor) getEditor()).getTextField().isFocusOwner()) {
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
