package util;

public class StringUtil {
	public  final static String KEEP = "KEEP SAME";
	public final  static String UNSELECTED = "NOT SELECTED";
	public static boolean isNumeric(String str) {
		return str.matches("^\\d+$");  //match a number with optional '-' and decimal.
	}
}
