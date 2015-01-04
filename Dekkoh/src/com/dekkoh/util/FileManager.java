package com.dekkoh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;

public class FileManager {
	private static FileManager fileManager;
	private FileInputStream fileInputStream = null;
	private ObjectInputStream objectInputStream = null;
	private FileOutputStream fileOutputStream = null;
	private ObjectOutputStream objectOutputStream = null;
	private File file = null;

	private FileManager() {

	}

	public static FileManager getInstance() {
		if (fileManager == null) {
			fileManager = new FileManager();
		}
		return fileManager;
	}

	public Object readObjectFromInternalStorage(Activity activity,
			String fileName) throws Exception {
		try {
			file = new File(activity.getFilesDir(), fileName);
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			return objectInputStream.readObject();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(fileInputStream);
			IOUtils.closeStream(objectInputStream);
		}
	}

	public Object readObjectFromInternalCache(Activity activity, String fileName)
			throws Exception {
		try {
			file = new File(activity.getCacheDir(), fileName + ".dat");
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			return objectInputStream.readObject();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(fileInputStream);
			IOUtils.closeStream(objectInputStream);
		}
	}

	public Object readObjectFromExternalStorage(Activity activity,
			String fileName) throws Exception {
		try {
			file = new File(activity.getExternalFilesDir(null), fileName
					+ ".dat");
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			return objectInputStream.readObject();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(fileInputStream);
			IOUtils.closeStream(objectInputStream);
		}
	}

	public Object readObjectFromExternalCache(Activity activity, String fileName)
			throws Exception {
		try {
			file = new File(activity.getExternalCacheDir(), fileName + ".dat");
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			return objectInputStream.readObject();
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(fileInputStream);
			IOUtils.closeStream(objectInputStream);
		}
	}

	public void writeObjectInInternalStorage(Activity activity,
			String fileName, Object object) throws Exception {
		try {
			fileOutputStream = activity.openFileOutput(fileName + ".dat",
					Context.MODE_PRIVATE);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(fileOutputStream);
			IOUtils.closeStream(objectOutputStream);
		}
	}

	public void writeObjectInInternalCache(Activity activity, String fileName,
			Object object) throws Exception {
		File file;
		try {
			// file = File.createTempFile("MyCache", null, getCacheDir());
			file = new File(activity.getCacheDir(), fileName + ".dat");
			fileOutputStream = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(fileOutputStream);
			IOUtils.closeStream(objectOutputStream);
		}
	}

	public void writeObjectInExternalStorage(Activity activity,
			String fileName, Object object) throws Exception {
		File file = new File(activity.getExternalFilesDir(null), fileName
				+ ".dat");
		OutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(fileOutputStream);
			IOUtils.closeStream(objectOutputStream);
		}
	}

	public void writeObjectInExternalCache(Activity activity, String fileName,
			Object object) throws Exception {
		File file;
		try {
			// file = File.createTempFile("MyCache", null, getCacheDir());
			file = new File(activity.getExternalCacheDir(), fileName + ".dat");
			fileOutputStream = new FileOutputStream(file);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
		} catch (Exception exception) {
			Log.e(exception);
			throw exception;
		} finally {
			IOUtils.closeStream(fileOutputStream);
			IOUtils.closeStream(objectOutputStream);
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
