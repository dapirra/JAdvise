package jadvise.guitools;

import javax.swing.JComboBox;

/**
 * @author David Pirraglia
 */

public class JScrollableComboBox<E> extends JComboBox<E> {

	private boolean ignoresFocus;
	private boolean loopable;

	public JScrollableComboBox() {
		ignoresFocus = false;
		loopable = false;
		addListener();
	}

	public JScrollableComboBox(E[] e) {
		super(e);
		ignoresFocus = false;
		loopable = false;
		addListener();
	}

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
