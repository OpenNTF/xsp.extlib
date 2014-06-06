package org.openntf.xsp.extlib.converter.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataScrubber {
	private static Pattern BOLD_PATTERN = Pattern.compile("<b>(.*?)</b>");
	private static Pattern ITALIC_PATTERN = Pattern.compile("<i>(.*?)</i>");

	private static Map<String, Pattern> FONT_PATTERN_MAP = new ConcurrentHashMap<String, Pattern>();

	private static Pattern getFontPattern(String parameter) {
		Pattern result = FONT_PATTERN_MAP.get(parameter);
		if (result == null) {
			// synchronized (FONT_PATTERN_MAP) {
			result = Pattern.compile("[<font ](" + parameter + ")=[\"']?((?:.(?![\"']?\\s+(?:" + parameter
					+ ")=|[>\"']))+.)[\"']?(>)??");
			FONT_PATTERN_MAP.put(parameter, result);
			// }
		}
		return result;
	}

	public static String removeFontParameter(String html, String[] parameters) {
		String returnVal = html;
		for (String param : parameters) {
			Pattern pat = getFontPattern(param);
			Matcher match = pat.matcher(returnVal);
			returnVal = match.replaceAll("");
			match.reset();
		}
		return returnVal;
	}

	public static String replaceFontParameter(String html, String[] source, String[] target) {
		String returnVal = html;
		if (source.length != target.length) {
			System.out.println("WARNING: Error replacing table parameters. Source array is "
					+ source.length + " and target array is " + target.length);
		}
		for (int i = 0; i < source.length; i++) {
			Pattern pat = getFontPattern(source[i]);
			Matcher match = pat.matcher(returnVal);
			returnVal = match.replaceAll(target[i]);
			match.reset();
		}
		return returnVal;
	}

	private static Map<String, Pattern> TABLE_PATTERN_MAP = new ConcurrentHashMap<String, Pattern>();

	private static Pattern getTablePattern(String parameter) {
		Pattern result = TABLE_PATTERN_MAP.get(parameter);
		if (result == null) {
			// synchronized (TABLE_PATTERN_MAP) {
			result = Pattern.compile("[<table ](" + parameter + ")=[\"']?((?:.(?![\"']?\\s+(?:" + parameter
					+ ")=|[>\"']))+.)[\"']?(>)??");
			TABLE_PATTERN_MAP.put(parameter, result);
			// }
		}
		return result;
	}

	public static String removeTableParameter(String html, String[] parameters) {
		String returnVal = html;
		for (String param : parameters) {
			Pattern pat = getTablePattern(param);
			Matcher match = pat.matcher(returnVal);
			returnVal = match.replaceAll("");
			match.reset();
		}
		return returnVal;
	}

	public static String replaceTableParameter(String html, String[] source, String[] target) {
		String returnVal = html;
		if (source.length != target.length) {
			System.out.println("WARNING: Error replacing table parameters. Source array is "
					+ source.length + " and target array is " + target.length);
		}
		for (int i = 0; i < source.length; i++) {
			Pattern pat = getTablePattern(source[i]);
			Matcher match = pat.matcher(returnVal);
			returnVal = match.replaceAll(target[i]);
			match.reset();
		}
		return returnVal;
	}

	private static Map<String, Pattern> TD_PATTERN_MAP = new ConcurrentHashMap<String, Pattern>();

	private static Pattern getTdPattern(String parameter) {
		Pattern result = TD_PATTERN_MAP.get(parameter);
		if (result == null) {
			// synchronized (TD_PATTERN_MAP) {
			result = Pattern.compile("[<td ](" + parameter + ")=[\"']?((?:.(?![\"']?\\s+(?:" + parameter
					+ ")=|[>\"']))+.)[\"']?(>)??");
			TD_PATTERN_MAP.put(parameter, result);
			// }
		}
		return result;
	}

	public static String replaceTdParameter(String html, String[] source, String[] target) {
		String returnVal = html;
		if (source.length != target.length) {
			System.out.println("WARNING: Error replacing td parameters. Source array is "
					+ source.length + " and target array is " + target.length);
		}
		for (int i = 0; i < source.length; i++) {
			Pattern pat = getTdPattern(source[i]);
			Matcher match = pat.matcher(returnVal);
			returnVal = match.replaceAll(target[i]);
			match.reset();
		}
		return returnVal;
	}

	public static String removeTdParameter(String html, String[] parameters) {
		String returnVal = html;
		for (String param : parameters) {
			Pattern pat = getTdPattern(param);
			Matcher match = pat.matcher(returnVal);
			returnVal = match.replaceAll("");
			match.reset();
		}
		return returnVal;
	}

}
