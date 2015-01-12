package com.dekkoh.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.TextUtils;
import android.webkit.URLUtil;

public class CommonUtils {
	private static final String TAG = "CommonUtils";

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

	public static String getDateDifference(Date oldDate, Date newDate) {
		Log.i(TAG, "getDateDifference");
		if (oldDate != null && newDate != null) {
			long diff = newDate.getTime() - oldDate.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if (Log.DEBUG) {
				SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				Log.i(TAG, " date01 : " + simpleDateFormatter.format(oldDate));
				Log.i(TAG, " date02 : " + simpleDateFormatter.format(newDate));
				Log.d(TAG, " diff : " + diff);
				Log.d(TAG, " diffSeconds : " + diffSeconds);
				Log.d(TAG, " diffMinutes : " + diffMinutes);
				Log.d(TAG, " diffHours : " + diffHours);
				Log.d(TAG, " diffDays : " + diffDays);
			}
			if (diffDays > 365) {
				return diffDays % 365 + "y ago";
			} else if (diffDays <= 0) {
				if (diffHours > 0) {
					return diffHours + "h ago";
				} else {
					return diffMinutes + "m ago";
				}
			} else {
				return diffDays + "d ago";
			}
		}
		return "";
	}
}
