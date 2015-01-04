package com.dekkoh.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;

public class FileManager {
	private static FileManager fileManager;

	private FileManager() {

	}

	public static FileManager getInstance() {
		if (fileManager == null) {
			fileManager = new FileManager();
		}
		return fileManager;
	}

	public String readFileFromInternalStorage(Activity activity, String fileName)
			throws Exception {
		BufferedReader input = null;
		File file = null;
		try {
			file = new File(activity.getFilesDir(), fileName);
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = input.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(input);
		}
	}

	public String readFileFromInternalCache(Activity activity, String fileName)
			throws Exception {
		BufferedReader input = null;
		File file = null;
		try {
			file = new File(activity.getCacheDir(), fileName);
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = input.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(input);
		}
	}

	public String readFileFromExternalStorage(Activity activity, String fileName)
			throws Exception {
		BufferedReader input = null;
		File file = null;
		try {
			file = new File(activity.getExternalFilesDir(null), fileName);
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = input.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(input);
		}
	}

	public String readFileFromExternalCache(Activity activity, String fileName)
			throws Exception {
		BufferedReader input = null;
		File file = null;
		try {
			file = new File(activity.getExternalCacheDir(), fileName);
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line;
			StringBuffer buffer = new StringBuffer();
			while ((line = input.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(input);
		}
	}

	public void writeFileInInternalStorage(Activity activity, String fileName,
			String dataString) throws Exception {
		FileOutputStream outputStream = null;
		try {
			outputStream = activity.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			outputStream.write(dataString.getBytes());
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(outputStream);
		}
	}

	public void writeFileInInternalCache(Activity activity, String fileName,
			String dataString) throws Exception {
		File file;
		FileOutputStream outputStream = null;
		try {
			// file = File.createTempFile("MyCache", null, getCacheDir());
			file = new File(activity.getCacheDir(), fileName);
			outputStream = new FileOutputStream(file);
			outputStream.write(dataString.getBytes());
			outputStream.close();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(outputStream);
		}
	}

	public void writeFileInExternalStorage(Activity activity, String fileName,
			String dataString) throws Exception {
		File file = new File(activity.getExternalFilesDir(null), fileName);
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			outputStream.write(dataString.getBytes());
			outputStream.close();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(outputStream);
		}
	}

	public void writeFileInExternalCache(Activity activity, String fileName,
			String dataString) throws Exception {
		File file;
		FileOutputStream outputStream = null;
		try {
			// file = File.createTempFile("MyCache", null, getCacheDir());
			file = new File(activity.getExternalCacheDir(), fileName);
			outputStream = new FileOutputStream(file);
			outputStream.write(dataString.getBytes());
			outputStream.close();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(outputStream);
		}
	}

	public void deleteFileFromInternalStorage(Activity activity, String fileName) {
		File file = new File(activity.getFilesDir(), fileName);
		if (file != null) {
			file.delete();
		}
	}

	public void deleteFileFromInternalCache(Activity activity, String fileName) {
		File file = new File(activity.getCacheDir(), fileName);
		if (file != null) {
			file.delete();
		}
	}

	public void deleteFileFromExternalStorage(Activity activity, String fileName) {
		File file = new File(activity.getExternalFilesDir(null), fileName);
		if (file != null) {
			file.delete();
		}
	}

	public void deleteFileFromExternalCache(Activity activity, String fileName) {
		File file = new File(activity.getExternalCacheDir(), fileName);
		if (file != null) {
			file.delete();
		}
	}

	public boolean isFileExistsInInternalStorage(Activity activity,
			String fileName) {
		File file = new File(activity.getFilesDir(), fileName);
		if (file != null) {
			return file.exists();
		} else
			return false;
	}

	public boolean isFileExistsInInternalCache(Activity activity,
			String fileName) {
		File file = new File(activity.getCacheDir(), fileName);
		if (file != null) {
			return file.exists();
		} else
			return false;
	}

	public boolean isFileExistsInExternalStorage(Activity activity,
			String fileName) {
		File file = new File(activity.getExternalFilesDir(null), fileName);
		if (file != null) {
			return file.exists();
		} else
			return false;
	}

	public boolean isFileExistsInExternalcache(Activity activity,
			String fileName) {
		File file = new File(activity.getExternalCacheDir(), fileName);
		if (file != null) {
			return file.exists();
		} else
			return false;
	}

	public boolean isFileExists(Activity activity, String fileName) {
		return isFileExistsInExternalcache(activity, fileName)
				|| isFileExistsInExternalStorage(activity, fileName)
				|| isFileExistsInInternalCache(activity, fileName)
				|| isFileExistsInInternalStorage(activity, fileName);
	}

}
