package jadvise.exceptions;

/**
 * @author David Pirraglia
 */
public class InvalidZipCodeException extends IllegalArgumentException {

	/**
	 * Creates a new instance of <code>InvalidZipCodeException</code> without
	 * detail message.
	 */
	public InvalidZipCodeException() {
	}

	/**
	 * Constructs an instance of <code>InvalidZipCodeException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public InvalidZipCodeException(String msg) {
		super(msg);
	}
}
