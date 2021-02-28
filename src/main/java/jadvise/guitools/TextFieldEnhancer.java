package jadvise.guitools;

import com.sun.javafx.PlatformUtil;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextFieldEnhancer {

	/**
	 * Adds many features to a textfield such as:
	 * <ul>
	 *     <li>Context menu shows upon right click:</li>
	 *     <li>
	 *         <ul>
	 *             <li>Cut</li>
	 *             <li>Copy</li>
	 *             <li>Paste</li>
	 *             <li>Select All</li>
	 *             <li>Undo</li>
	 *             <li>Redo</li>
	 *         </ul>
	 *     </li>
	 *     <li>Ability to undo/redo with CTRL+Z/CTRL+Y</li>
	 *     <li>Middle click will paste</li>
	 * </ul>
	 *
	 * @param textField The textfield to enhance
	 */
	public static void enhanceTextField(JTextField textField) {
		JPopupMenu popupmenu = new JPopupMenu();
		UndoManager undoManager = new UndoManager();
		textField.getDocument().addUndoableEditListener(undoManager);

		JMenuItem cut = new JMenuItem("Cut");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");
		JMenuItem selectAll = new JMenuItem("Select All");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem redo = new JMenuItem("Redo");

		// Binds CTRL+Z to undo
		textField.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "UNDO");
		textField.getActionMap().put("UNDO", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (undoManager.canUndo()) {
					undoManager.undo();
				}
			}
		});

		// Binds CTRL+Y to redo
		textField.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "REDO");
		textField.getActionMap().put("REDO", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (undoManager.canRedo()) {
					undoManager.redo();
				}
			}
		});

		undo.addActionListener(e -> {
			if (!textField.hasFocus()) {
				textField.requestFocusInWindow();
			}
			if (undoManager.canUndo()) {
				undoManager.undo();
			}
		});

		redo.addActionListener(e -> {
			if (!textField.hasFocus()) {
				textField.requestFocusInWindow();
			}
			if (undoManager.canRedo()) {
				undoManager.redo();
			}
		});

		cut.addActionListener(e -> {
			if (!textField.hasFocus()) {
				textField.requestFocusInWindow();
			}
			textField.cut();
		});

		copy.addActionListener(e -> {
			if (!textField.hasFocus()) {
				textField.requestFocusInWindow();
			}
			textField.copy();
		});

		paste.addActionListener(e -> {
			if (!textField.hasFocus()) {
				textField.requestFocusInWindow();
			}
			textField.paste();
		});

		selectAll.addActionListener(e -> {
			if (!textField.hasFocus()) {
				textField.requestFocusInWindow();
			}
			textField.selectAll();
		});

		popupmenu.add(cut);
		popupmenu.add(copy);
		popupmenu.add(paste);
		popupmenu.add(selectAll);
		popupmenu.addSeparator();
		popupmenu.add(undo);
		popupmenu.add(redo);

		// Hide popup menu when escape is pressed
		popupmenu.getInputMap(JPopupMenu.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("ESCAPE"),
				"ESC"
		);
		popupmenu.getActionMap().put("ESC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupmenu.setVisible(false);
			}
		});

		// Pressing Escape on a textfield will deselect selected text if any text
		// is selected and if not, it will unfocus the textfield.
		textField.getInputMap(JPopupMenu.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke("ESCAPE"),
				"ESC"
		);
		textField.getActionMap().put("ESC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textField.getSelectedText() == null) {
					textField.getRootPane().requestFocusInWindow();
				} else {
					textField.select(0, 0);
				}
			}
		});

		// Middle clicking on a textfield will paste
		if (PlatformUtil.isWindows()) {
			textField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON2) {
						if (!textField.hasFocus()) {
							textField.requestFocusInWindow();
						}
						textField.paste();
					}
				}
			});
		}

		textField.setComponentPopupMenu(popupmenu);
	}
}
