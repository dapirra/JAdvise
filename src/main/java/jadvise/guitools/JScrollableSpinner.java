package jadvise.guitools;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

/**
 * @author David Pirraglia
 */
public class JScrollableSpinner extends JSpinner {

	public JScrollableSpinner() {
		addListener();
	}

	public JScrollableSpinner(SpinnerListModel model) {
		super(model);
		addListener();
	}

	private void addListener() {
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent mwe) {
				if (((JSpinner.DefaultEditor) getEditor()).getTextField().isFocusOwner()) {
					// Scroll Down
					if (mwe.getWheelRotation() > 0) {
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
			}
		});
	}
}
