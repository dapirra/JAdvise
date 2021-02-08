package jadvise.tools;

/**
 * @author David Pirraglia
 */
public class TitleCaseFormatter {

	public static String toTitleCase(String input) {
		String array[] = input.split(" "), output = "";
		for (String i : array) {
			if (i.length() > 1) {
				output += i.substring(0, 1).toUpperCase() + i.substring(1).toLowerCase() + " ";
			} else {
				output += i.toUpperCase() + " ";
			}
		}
		return output.trim();
	}
}
