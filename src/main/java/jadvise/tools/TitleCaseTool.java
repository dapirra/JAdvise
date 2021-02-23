package jadvise.tools;

import java.util.stream.Stream;

/**
 * @author David Pirraglia
 */
public class TitleCaseTool {

	/**
	 * Converts a string to title case.
	 */
	public static String toTitleCase(String input) {
		return String.join(" ", Stream.of(input.split(" ")).map(i -> {
			if (i.length() > 1) {
				return i.substring(0, 1).toUpperCase() +
						i.substring(1).toLowerCase();
			} else {
				return i.toUpperCase();
			}
		}).toArray(String[]::new));
	}
}
