package jadvise.tools;

/**
 * @author David Pirraglia
 */
public class TelephoneFormatter {

	public static boolean isFormatted(String str) {
		return str.length() == 16 && str.matches("\\(\\d{3}\\) \\d{3} - \\d{4}");
	}

	public static String unFormat(String str) {
//		if (isFormatted(str)) {
			return java.util.regex.Pattern.compile("\\(|\\)| |-").matcher(str).replaceAll("");
//		}
//		return str;
	}

	public static String format(String str) {
		if (str.length() >= 10) {
			return '(' + str.substring(0, 3) + ") " + str.substring(3, 6) + " - " + str.substring(6);
		}
		return str;
	}
}
