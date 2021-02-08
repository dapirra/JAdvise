package jadvise.exceptions.id;

/**
 * @author David Pirraglia
 */
public class DuplicateIDException extends IllegalArgumentException {

	/**
	 * Creates a new instance of <code>DuplicateIDException</code> without
	 * detail message.
	 */
	public DuplicateIDException() {
	}

	/**
	 * Constructs an instance of <code>DuplicateIDException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public DuplicateIDException(String msg) {
		super(msg);
	}
}
