package jadvise.objects;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;

public class PortFormat extends DecimalFormat {

	public PortFormat() {
		super("0");
		setMaximumIntegerDigits(5);
	}

	public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
		return super.format(number, toAppendTo, pos);
	}

	public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
		return super.format(number, toAppendTo, pos);
	}

	public Number parse(String source, ParsePosition parsePosition) {
		Number result = super.parse(source, parsePosition);
		if (result == null) {
			result = new Long(3306);
		}
		return Math.abs(result.longValue());
	}
}
