package com.dekkoh.util;

import android.text.TextUtils;
import android.webkit.URLUtil;

public class CommonUtils {
	public static boolean isValidURL(String urlString) {
		if (TextUtils.isEmpty(urlString)) {
			return false;
		} else
			return URLUtil.isValidUrl(urlString);
	}

	public static boolean isValidEmail(CharSequence emailAddressString) {
		if (TextUtils.isEmpty(emailAddressString)) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(
					emailAddressString).matches();
		}
	}
}
