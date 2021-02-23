package jadvise.tools;

import java.util.regex.Pattern;

/**
 * @author David Pirraglia
 */
public class TelephoneTool {

	public static boolean isFormatted(String str) {
		return str.matches("\\([0-9]{3}\\) [0-9]{3}-[0-9]{4}");
	}

	public static String unFormat(String str) {
		return Pattern.compile("[^0-9]").matcher(str).replaceAll("");
	}

	public static String format(String str) {
		if (!isFormatted(str)) {
			str = unFormat(str);
			if (str.length() == 10) {
				return '(' + str.substring(0, 3) + ") " + str.substring(3, 6) + "-" + str.substring(6);
			}
		}
		return str;
	}
}
