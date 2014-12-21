/**
 * IOUtils: A collection of IO-related public static methods.
 */

package com.dekkoh.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {

	public static String toString(InputStream inputStream) {
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream));
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return stringBuilder.toString();
	}

	public static boolean closeStream(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
			closeable = null;
		} catch (final IOException e) {
		} finally {
			closeable = null;
		}
		return true;
	}

}
