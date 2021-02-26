package jadvise.guitools;

import javax.swing.JComboBox;

/**
 * @author David Pirraglia
 */

public class JScrollableComboBox<E> extends JComboBox<E> {

	private boolean ignoresFocus;
	private boolean loopable;

	/**
	 * Creates a <code>JComboBox</code> that contains the elements
	 * in the specified array. By default the first item in the array
	 * (and therefore the data model) becomes selected. Adds ability to use
	 * the mouse wheel to go to previous/next values.
	 */
	public JScrollableComboBox() {
		ignoresFocus = false;
		loopable = false;
		addListener();
	}

	/**
	 * Creates a <code>JScrollableComboBox</code> that contains the elements
	 * in the specified array. By default the first item in the array
	 * (and therefore the data model) becomes selected. Adds ability to use
	 * the mouse wheel to go to previous/next values.
	 *
	 * @param e An array of objects to insert into the combo box
	 */
	public JScrollableComboBox(E[] e) {
		super(e);
		ignoresFocus = false;
		loopable = false;
		addListener();
	}

	/**
	 * Creates a <code>JComboBox</code> that contains the elements
	 * in the specified array. By default the first item in the array
	 * (and therefore the data model) becomes selected. Adds ability to use
	 * the mouse wheel to go to previous/next values.
	 *
	 * @param e            An array of objects to insert into the combo box
	 * @param ignoresFocus Whether or not focus is required to scroll with the mousewheel
	 * @param loopable     Determines whether the data will cycle when the end is reached
	 */
	public JScrollableComboBox(E[] e, boolean ignoresFocus, boolean loopable) {
		super(e);
		this.loopable = loopable;
		this.ignoresFocus = ignoresFocus;
		addListener();
	}

	public boolean isIgnoringFocus() {
		return ignoresFocus;
	}

	public void setIgnoresFocus(boolean ignoresFocus) {
		this.ignoresFocus = ignoresFocus;
	}

	public boolean isLoopable() {
		return loopable;
	}

	public void setLoopable(boolean loopable) {
		this.loopable = loopable;
	}

	private void addListener() {
		addMouseWheelListener(mouseWheelEvent -> {
			if (ignoresFocus || isFocusOwner()) {
				// Scroll Down
				if (mouseWheelEvent.getWheelRotation() > 0) {
					if (getSelectedIndex() < getItemCount() - 1) {
						setSelectedIndex(getSelectedIndex() + 1);
					} else if (loopable && getSelectedIndex() == getItemCount() - 1) {
						setSelectedIndex(0);
					}
				// Scroll Up
				} else {
					if (getSelectedIndex() > 0) {
						setSelectedIndex(getSelectedIndex() - 1);
					} else if (loopable && getSelectedIndex() == 0) {
						setSelectedIndex(getItemCount() - 1);
					}
				}
			}
		});
	}
}
