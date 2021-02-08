package jadvise.exceptions;

/**
 * @author David Pirraglia
 */
public class InvalidEmailException extends IllegalArgumentException {

	/**
	 * Creates a new instance of <code>InvalidEmailException</code> without
	 * detail message.
	 */
	public InvalidEmailException() {
	}

	/**
	 * Constructs an instance of <code>InvalidEmailException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public InvalidEmailException(String msg) {
		super(msg);
	}
}
