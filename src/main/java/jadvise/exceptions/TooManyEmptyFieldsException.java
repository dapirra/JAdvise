package jadvise.exceptions;

/**
 * @author David Pirraglia
 */
public class TooManyEmptyFieldsException extends IllegalArgumentException {

	/**
	 * Creates a new instance of <code>TooManyEmptyFieldsException</code>
	 * without detail message.
	 */
	public TooManyEmptyFieldsException() {
	}

	/**
	 * Constructs an instance of <code>TooManyEmptyFieldsException</code> with
	 * the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public TooManyEmptyFieldsException(String msg) {
		super(msg);
	}
}
