package com.smartlab.tsu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static String replaceBlank(String str) {
		if (str != null) {

			Pattern p = Pattern.compile("\\s*|\r|\n|\r\n");

			Matcher m = p.matcher(str);

			str = m.replaceAll("");
		}
		return str;
	}
}
