package jadvise.exceptions;

/**
 * @author David Pirraglia
 */
public class InvalidCourseException extends IllegalArgumentException {

	/**
	 * Creates a new instance of <code>InvalidCourseException</code> without
	 * detail message.
	 */
	public InvalidCourseException() {
	}

	/**
	 * Constructs an instance of <code>InvalidCourseException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public InvalidCourseException(String msg) {
		super(msg);
	}
}
