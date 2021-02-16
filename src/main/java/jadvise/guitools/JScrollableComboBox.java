package jadvise.guitools;

import javax.swing.JComboBox;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * @author David Pirraglia
 */

public class JScrollableComboBox<E> extends JComboBox<E> {

	private boolean loopable;

	public JScrollableComboBox() {
		loopable = false;
		addListener();
	}

	public JScrollableComboBox(E[] e) {
		super(e);
		loopable = false;
		addListener();
	}

	public boolean isLoopable() {
		return loopable;
	}

	public void setLoopable(boolean loopable) {
		this.loopable = loopable;
	}

	private void addListener() {
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent mwe) {
				if (isFocusOwner()) {
					// Scroll Down
					if (mwe.getWheelRotation() > 0) {
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
			}
		});
	}
}
