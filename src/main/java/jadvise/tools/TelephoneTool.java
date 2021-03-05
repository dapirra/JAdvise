package jadvise.tools;

import java.util.regex.Pattern;

/**
 * @author David Pirraglia
 */
public class TelephoneTool {

	/**
	 * Determines whether or not a phone number is formatted properly.
	 *
	 * @param phoneNumber The phone number to check
	 * @return True or False depending on if the number is formatted properly
	 */
	public static boolean isFormatted(String phoneNumber) {
		return phoneNumber.matches("\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}");
	}

	/**
	 * Strips everything from a phone number that isn't a number.
	 *
	 * @param phoneNumber The phone number to clean up
	 * @return A String containing only the numbers of a phone number
	 */
	public static String unFormat(String phoneNumber) {
		return Pattern.compile("[^0-9]").matcher(phoneNumber).replaceAll("");
	}

	/**
	 * Takes a phone number and returns it in the proper format.
	 *
	 * @param phoneNumber The phone number to format
	 * @return A String containing a formatted phone number
	 */
	public static String format(String phoneNumber) {
		if (!isFormatted(phoneNumber)) {
			phoneNumber = unFormat(phoneNumber);
			if (phoneNumber.length() == 10) {
				return String.format(
						"(%s) %s-%s",
						phoneNumber.substring(0, 3),
						phoneNumber.substring(3, 6),
						phoneNumber.substring(6)
				);
			}
		}
		return phoneNumber;
	}
}
