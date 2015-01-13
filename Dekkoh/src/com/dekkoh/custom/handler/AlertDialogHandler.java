package com.dekkoh.custom.handler;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Build;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.dekkoh.util.Log;

public final class AlertDialogHandler {
	private static final String TAG = "AlertDialogHandler";
	private static AlertDialogHandler alertDialogHandler;
	private Map<String, Boolean> dialogStatusMap = new HashMap<String, Boolean>();
	private Toast toast;

	private AlertDialogHandler() {
		if (dialogStatusMap == null) {
			dialogStatusMap = new HashMap<String, Boolean>();
		}
	}

	synchronized public static AlertDialogHandler getInstance() {
		if (alertDialogHandler != null) {
			return alertDialogHandler;
		} else {
			alertDialogHandler = new AlertDialogHandler();
			return alertDialogHandler;
		}
	}

	synchronized public void dismissAlertDialog(AlertDialog alertDialog) {
		dialogStatusMap.remove(alertDialog.getClass().getName());
	}

	synchronized public void showAlertDialog(AlertDialog alertDialog) {
		dialogStatusMap.put(alertDialog.getClass().getName(), true);
	}

	public void showAlertDialog(Activity activity, String title, String message) {
		showSingleButtonAlertDialog(activity, title, message,
				activity.getString(android.R.string.ok), null, false);
	}

	public void showSingleButtonAlertDialog(Activity activity, String title,
			String message, String positiveButtonText,
			DialogInterface.OnClickListener positiveButtonListener,
			boolean isCancelable) {
		if (activity == null)
			return;
		AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
		if (title != null)
			alertbox.setTitle(title);
		alertbox.setMessage(message);
		alertbox.setCancelable(isCancelable);
		alertbox.setPositiveButton(positiveButtonText, positiveButtonListener);
		alertbox.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				return keyCode == KeyEvent.KEYCODE_SEARCH
						&& event.getRepeatCount() == 0;
			}
		});
		alertbox.show();
	}

	public void showDoubleButtonAlertDialog(Activity activity, String title,
			String message, String positiveButtonText,
			DialogInterface.OnClickListener positiveButtonListener,
			String negativeButtonText,
			DialogInterface.OnClickListener negativeButtonListener,
			boolean isCancelable) {
		if (activity == null)
			return;
		AlertDialog.Builder alertbox = new AlertDialog.Builder(activity);
		if (title != null)
			alertbox.setTitle(title);
		alertbox.setMessage(message);
		alertbox.setCancelable(isCancelable);
		alertbox.setPositiveButton(positiveButtonText, positiveButtonListener);
		alertbox.setNegativeButton(negativeButtonText, negativeButtonListener);
		alertbox.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				return keyCode == KeyEvent.KEYCODE_SEARCH
						&& event.getRepeatCount() == 0;
			}
		});
		alertbox.show();
	}

	public void showAlertDialog(Activity activity, String alertTitle,
			String btnLabel, String alertMessage) {
		try {
			new AlertDialog.Builder(activity)
					.setTitle(alertTitle)
					.setMessage(alertMessage)
					.setPositiveButton(
							btnLabel,
							new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int arg1) {
								}
							}).show();
		} catch (final Exception ex) {
			Log.e(TAG, ex.getMessage(), ex);
		}
	}

	public void showAlertDialogAndCloseActivity(final Activity activity,
			String alertTitle, String btnLabel, String alertMessage) {
		try {
			new AlertDialog.Builder(activity)
					.setTitle(alertTitle)
					.setMessage(alertMessage)
					.setPositiveButton(
							btnLabel,
							new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										final DialogInterface dialog,
										final int arg1) {
									activity.finish();
								}
							}).show();
		} catch (final Exception ex) {
			Log.e(TAG, ex.getMessage(), ex);
		}
	}

	public void showToast(Activity activity, String message) {
		showShortToast(activity, message);
	}

	public void showToast(final Activity activity, final String message,
			boolean isRunOnUIThread) {
		if (isRunOnUIThread) {
			activity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					showShortToast(activity, message);
				}
			});
		} else {
			showShortToast(activity, message);
		}

	}

	public void showShortToast(Activity activity, String message) {
		if (!activity.isFinishing()) {

			showToast(activity, message, Toast.LENGTH_SHORT);
		}
	}

	public void showLongToast(Activity activity, String message) {
		if (!activity.isFinishing()) {
			showToast(activity, message, Toast.LENGTH_LONG);
		}
	}

	private void showToast(Activity activity, String message, int duration) {
		boolean condition = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;
		if (toast != null) {
			toast.cancel();
		}
		if ((toast == null && condition) || !condition) {
			toast = Toast.makeText(activity, message, duration);
		}
		if ((toast != null && condition)) {
			toast = new Toast(activity);
			toast.setText(message);
		}
		toast.setDuration(duration);
		if (activity.isFinishing()) {
			toast.cancel();
		} else {
			toast.show();
		}
	}

	public void showToastInCenter(Activity activity, String message) {
		showShortToastInCenter(activity, message);
	}

	public void showShortToastInCenter(Activity activity, String message) {
		if (!activity.isFinishing()) {
			Toast alertToast = Toast.makeText(activity, message,
					Toast.LENGTH_SHORT);
			alertToast.setGravity(Gravity.CENTER, 0, 0);
			alertToast.show();
		}
	}

	public void showLongToastInCenter(Activity activity, String message) {
		if (!activity.isFinishing()) {
			Toast alertToast = Toast.makeText(activity, message,
					Toast.LENGTH_LONG);
			alertToast.setGravity(Gravity.CENTER, 0, 0);
			alertToast.show();
		}
	}
}
