package jadvise.guitools;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JComboBox;

/**
 * @author David Pirraglia
 */

public class JScrollableComboBox extends JComboBox {

	private boolean loopable;

	public JScrollableComboBox() {
		loopable = false;
		addListener();
	}

	public JScrollableComboBox(Object[] o) {
		super(o);
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
