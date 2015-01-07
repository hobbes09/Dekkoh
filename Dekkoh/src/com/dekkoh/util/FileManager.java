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

	/**
	 * Method to read Object File from Internal Storage
	 * 
	 * @param activity
	 * @param fileName
	 * @return Object
	 * @throws Exception
	 */
	public Object readObjectFileFromInternalStorage(Activity activity,
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

	/**
	 * Method to read Object File from Internal Cache
	 * 
	 * @param activity
	 * @param fileName
	 * @return Object
	 * @throws Exception
	 */
	public Object readObjectFileFromInternalCache(Activity activity,
			String fileName) throws Exception {
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

	/**
	 * Method to read Object File from External Storage
	 * 
	 * @param activity
	 * @param fileName
	 * @return Object
	 * @throws Exception
	 */
	public Object readObjectFileFromExternalStorage(Activity activity,
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

	/**
	 * Method to read Object File from External Cache
	 * 
	 * @param activity
	 * @param fileName
	 * @return Object
	 * @throws Exception
	 */
	public Object readObjectFileFromExternalCache(Activity activity,
			String fileName) throws Exception {
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

	/**
	 * Method to write Object File In Internal Storage
	 * 
	 * @param activity
	 * @param fileName
	 * @param object
	 * @throws Exception
	 */
	public void writeObjectFileInInternalStorage(Activity activity,
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

	/**
	 * Method to write Object File In Internal Cache
	 * 
	 * @param activity
	 * @param fileName
	 * @param object
	 * @throws Exception
	 */
	public void writeObjectFileInInternalCache(Activity activity,
			String fileName, Object object) throws Exception {
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

	/**
	 * Method to write Object File In External Storage
	 * 
	 * @param activity
	 * @param fileName
	 * @param object
	 * @throws Exception
	 */
	public void writeObjectFileInExternalStorage(Activity activity,
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

	/**
	 * Method to write Object File In External Cache
	 * 
	 * @param activity
	 * @param fileName
	 * @param object
	 * @throws Exception
	 */
	public void writeObjectFileInExternalCache(Activity activity,
			String fileName, Object object) throws Exception {
		File file;
		try {
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

	/**
	 * Method to delete Object File From Internal Storage
	 * 
	 * @param activity
	 * @param fileName
	 */
	public void deleteObjectFileFromInternalStorage(Activity activity,
			String fileName) throws Exception {
		File file = new File(activity.getFilesDir(), fileName + ".dat");
		if (file != null) {
			file.delete();
		}
	}

	/**
	 * Method to delete Object File From Internal Cache
	 * 
	 * @param activity
	 * @param fileName
	 */
	public void deleteObjectFileFromInternalCache(Activity activity,
			String fileName) throws Exception {
		File file = new File(activity.getCacheDir(), fileName + ".dat");
		if (file != null) {
			file.delete();
		}
	}

	/**
	 * Method to delete Object File From External Storage
	 * 
	 * @param activity
	 * @param fileName
	 */
	public void deleteObjectFileFromExternalStorage(Activity activity,
			String fileName) throws Exception {
		File file = new File(activity.getExternalFilesDir(null), fileName
				+ ".dat");
		if (file != null) {
			file.delete();
		}
	}

	/**
	 * Method to delete Object File From External Cache
	 * 
	 * @param activity
	 * @param fileName
	 */
	public void deleteObjectFileFromExternalCache(Activity activity,
			String fileName) throws Exception {
		File file = new File(activity.getExternalCacheDir(), fileName + ".dat");
		if (file != null) {
			file.delete();
		}
	}

	/**
	 * Method to check if Object File Exists In InternalStorage
	 * 
	 * @param activity
	 * @param fileName
	 * @return boolean
	 */
	public boolean isObjectFileExistsInInternalStorage(Activity activity,
			String fileName) throws Exception {
		try {
			File file = new File(activity.getFilesDir(), fileName + ".dat");
			if (file != null) {
				return file.exists();
			}
		} catch (NullPointerException e) {
			if (readObjectFileFromInternalStorage(activity, fileName) != null) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Method to check if Object File Exists In Internal Cache
	 * 
	 * @param activity
	 * @param fileName
	 * @return boolean
	 */
	public boolean isObjectFileExistsInInternalCache(Activity activity,
			String fileName) throws Exception {
		try {
			File file = new File(activity.getCacheDir(), fileName + ".dat");
			if (file != null) {
				return file.exists();
			}
		} catch (NullPointerException e) {
			if (readObjectFileFromInternalCache(activity, fileName) != null) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Method to check if Object File Exists In External Storage
	 * 
	 * @param activity
	 * @param fileName
	 * @return boolean
	 */
	public boolean isObjectFileExistsInExternalStorage(Activity activity,
			String fileName) throws Exception {
		try {
			File file = new File(activity.getExternalFilesDir(null), fileName
					+ ".dat");
			if (file != null) {
				return file.exists();
			}
		} catch (NullPointerException e) {
			if (readObjectFileFromExternalStorage(activity, fileName) != null) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Method to check if Object File Exists In External Cache
	 * 
	 * @param activity
	 * @param fileName
	 * @return boolean
	 */
	public boolean isObjectFileExistsInExternalCache(Activity activity,
			String fileName) throws Exception {
		try {
			File file = new File(activity.getExternalCacheDir(), fileName
					+ ".dat");
			if (file != null) {
				return file.exists();
			}
		} catch (NullPointerException e) {
			if (readObjectFileFromExternalCache(activity, fileName) != null) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Method to check if Object File Exists
	 * 
	 * @param activity
	 * @param fileName
	 * @return boolean
	 */
	public boolean isObjectFileExists(Activity activity, String fileName)
			throws Exception {
		return isObjectFileExistsInExternalCache(activity, fileName + ".dat")
				|| isObjectFileExistsInExternalStorage(activity, fileName
						+ ".dat")
				|| isObjectFileExistsInInternalCache(activity, fileName
						+ ".dat")
				|| isObjectFileExistsInInternalStorage(activity, fileName
						+ ".dat");
	}

}
