package jadvise.guitools;

import com.sun.javafx.PlatformUtil;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;
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
	 * @param textComponent The textfield to enhance
	 */
	public static void enhanceTextField(JTextComponent textComponent) {
		JPopupMenu popupmenu = new JPopupMenu();
		UndoManager undoManager = new UndoManager();
		textComponent.getDocument().addUndoableEditListener(undoManager);

		JMenuItem cut = new JMenuItem("Cut");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem paste = new JMenuItem("Paste");
		JMenuItem selectAll = new JMenuItem("Select All");
		JMenuItem undo = new JMenuItem("Undo");
		JMenuItem redo = new JMenuItem("Redo");

		// Binds CTRL+Z to undo
		textComponent.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "UNDO");
		textComponent.getActionMap().put("UNDO", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (undoManager.canUndo()) {
					undoManager.undo();
				}
			}
		});

		// Binds CTRL+Y to redo
		textComponent.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "REDO");
		textComponent.getActionMap().put("REDO", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (undoManager.canRedo()) {
					undoManager.redo();
				}
			}
		});

		undo.addActionListener(e -> {
			if (!textComponent.hasFocus()) {
				textComponent.requestFocusInWindow();
			}
			if (undoManager.canUndo()) {
				undoManager.undo();
			}
		});

		redo.addActionListener(e -> {
			if (!textComponent.hasFocus()) {
				textComponent.requestFocusInWindow();
			}
			if (undoManager.canRedo()) {
				undoManager.redo();
			}
		});

		cut.addActionListener(e -> {
			if (!textComponent.hasFocus()) {
				textComponent.requestFocusInWindow();
			}
			textComponent.cut();
		});

		copy.addActionListener(e -> {
			if (!textComponent.hasFocus()) {
				textComponent.requestFocusInWindow();
			}
			textComponent.copy();
		});

		paste.addActionListener(e -> {
			if (!textComponent.hasFocus()) {
				textComponent.requestFocusInWindow();
			}
			textComponent.paste();
		});

		selectAll.addActionListener(e -> {
			if (!textComponent.hasFocus()) {
				textComponent.requestFocusInWindow();
			}
			textComponent.selectAll();
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
		textComponent.getInputMap(JPopupMenu.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke("ESCAPE"),
				"ESC"
		);
		textComponent.getActionMap().put("ESC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textComponent.getSelectedText() == null) {
					textComponent.getRootPane().requestFocusInWindow();
				} else {
					textComponent.select(0, 0);
				}
			}
		});

		// Middle clicking on a textfield will paste
		if (PlatformUtil.isWindows()) {
			textComponent.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON2) {
						if (!textComponent.hasFocus()) {
							textComponent.requestFocusInWindow();
						}
						textComponent.paste();
					}
				}
			});
		}

		textComponent.setComponentPopupMenu(popupmenu);
	}

	public static void addPasteContextMenu(JTextComponent textComponent) {
		JPopupMenu popupmenu = new JPopupMenu();
		JMenuItem paste = new JMenuItem("Paste");

		paste.addActionListener(e -> {
			if (!textComponent.hasFocus()) {
				textComponent.requestFocusInWindow();
			}
			textComponent.paste();
		});

		popupmenu.add(paste);

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
		textComponent.getInputMap(JPopupMenu.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke("ESCAPE"),
				"ESC"
		);
		textComponent.getActionMap().put("ESC", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textComponent.getSelectedText() == null) {
					textComponent.getRootPane().requestFocusInWindow();
				} else {
					textComponent.select(0, 0);
				}
			}
		});

		// Middle clicking on a textfield will paste
		if (PlatformUtil.isWindows()) {
			textComponent.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON2) {
						if (!textComponent.hasFocus()) {
							textComponent.requestFocusInWindow();
						}
						textComponent.paste();
					}
				}
			});
		}

		textComponent.setComponentPopupMenu(popupmenu);
	}
}
