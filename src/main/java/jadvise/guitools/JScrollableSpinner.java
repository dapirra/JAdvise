package jadvise.guitools;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

/**
 * @author David Pirraglia
 */
public class JScrollableSpinner extends JSpinner {

	private boolean ignoresFocus;

	/**
	 * Creates a <code>JScrollableSpinner</code> for the given model.
	 * The spinner has a set of previous/next buttons, and an editor appropriate
	 * for the model. Adds ability to use the mouse wheel to go to previous/next values.
	 */
	public JScrollableSpinner() {
		ignoresFocus = false;
		addListener();
	}

	/**
	 * Creates a <code>JScrollableSpinner</code> for the given model.
	 * The spinner has a set of previous/next buttons, and an editor appropriate
	 * for the model. Adds ability to use the mouse wheel to go to previous/next values.
	 *
	 * @param model Determines spinner contents
	 */
	public JScrollableSpinner(SpinnerModel model) {
		super(model);
		ignoresFocus = false;
		addListener();
	}

	/**
	 * Creates a <code>JScrollableSpinner</code> for the given model.
	 * The spinner has a set of previous/next buttons, and an editor appropriate
	 * for the model. Adds ability to use the mouse wheel to go to previous/next values.
	 *
	 * @param model Determines spinner contents
	 * @param ignoresFocus Whether or not focus is required to scroll with the mousewheel
	 */
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
