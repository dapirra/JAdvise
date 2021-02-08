package jadvise.exceptions.id;

/**
 * @author David Pirraglia
 */
public class InvalidIDException extends IllegalArgumentException {

	/**
	 * Creates a new instance of <code>InvalidIDException</code> without detail
	 * message.
	 */
	public InvalidIDException() {
	}

	/**
	 * Constructs an instance of <code>InvalidIDException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public InvalidIDException(String msg) {
		super(msg);
	}
}
