package web.util;


public final class StringUtils {
	
	public static String normalizeNull(String s) {
		if (s == null)
			return "";
		return s;
	}
	
}
