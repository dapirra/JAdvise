package jadvise.tools;

import java.util.stream.Stream;

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

	public static String toTitleCase2(String input) {
		String[] array = input.split(" ");
		StringBuilder output = new StringBuilder();
		for (String i : array) {
			if (i.length() > 1) {
				output.append(i.substring(0, 1).toUpperCase())
						.append(i.substring(1).toLowerCase());
			} else {
				output.append(i.toUpperCase());
			}
			output.append(" ");
		}
		return output.substring(0, output.length() - 1);
	}

	public static String toTitleCase3(String input) {
		String[] array = input.split(" ");
		for (int i = 0; i < array.length; i++) {
			if (array[i].length() > 1) {
				array[i] = array[i].substring(0, 1).toUpperCase()
						+ array[i].substring(1).toLowerCase();
			} else {
				array[i] = array[i].toUpperCase();
			}
		}
		return String.join(" ", array);
	}

	public static String toTitleCase4(String input) {
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
