package jadvise.tools;

import java.util.regex.Pattern;

/**
 * @author David Pirraglia
 */
public class TelephoneTool {

	public static boolean isFormatted(String phoneNumber) {
		return phoneNumber.matches("\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}");
	}

	public static String unFormat(String phoneNumber) {
		return Pattern.compile("[^0-9]").matcher(phoneNumber).replaceAll("");
	}

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
