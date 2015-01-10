
package com.dekkoh.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dekkoh.R;

public class ProgressDialogHandler {

    private static ProgressDialogHandler photoDialogUtil;

    private TransparentProgressDialog transparentProgressDialog = null;

    private DialogInterface.OnKeyListener progressDialogKeyListener;

    private ProgressDialogHandler() {
    }

    public static synchronized ProgressDialogHandler getInstance() {
        if (photoDialogUtil == null) {
            photoDialogUtil = new ProgressDialogHandler();
        }
        return photoDialogUtil;
    }

    public void showCustomProgressDialog(Activity activity) {
        dismissCustomProgressDialog(activity);
        if (!activity.isFinishing()) {
            if (this.transparentProgressDialog == null
                    || (this.transparentProgressDialog != null && !this.transparentProgressDialog
                            .isShowing())) {
                this.transparentProgressDialog = new TransparentProgressDialog(activity,
                        R.drawable.loader);
                this.transparentProgressDialog.setCancelable(false);
                this.transparentProgressDialog.setOnKeyListener(progressDialogKeyListener);
                this.transparentProgressDialog
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {
                                transparentProgressDialog = null;

                            }
                        });
                this.transparentProgressDialog.show();
            }

        }
    }

    public void setProgressDialogKeyListener(DialogInterface.OnKeyListener spinnerKeyListener) {
        this.progressDialogKeyListener = spinnerKeyListener;
    }

    public DialogInterface.OnKeyListener getProgressDialogKeyListener(final Activity activity,
            final boolean cancelable) {
        if (progressDialogKeyListener != null)
            return progressDialogKeyListener;
        else {
            progressDialogKeyListener = new DialogInterface.OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getRepeatCount() == 0) {
                        return true;
                    } else if (event.getAction() == KeyEvent.ACTION_DOWN
                            && keyCode == KeyEvent.KEYCODE_BACK && cancelable) {
                        dialog.dismiss();
                        activity.onBackPressed();
                        return true;
                    }
                    return false;
                }
            };
            return progressDialogKeyListener;
        }
    }

    public void dismissCustomProgressDialog(Activity activity) {
        try {
            if (this.transparentProgressDialog != null
                    && this.transparentProgressDialog.isShowing()
                    && !activity.isFinishing()) {
                this.transparentProgressDialog.dismiss();
                this.transparentProgressDialog = null;
            }
        } catch (RuntimeException exception) {
        } catch (Exception exception) {
        }
    }

    public void showIndeterminateProgressBar(Activity activity) {
        showIndeterminateProgressBar(activity, false);
    }

    public void showIndeterminateProgressBar(final Activity activity, boolean isRunOnUIThread) {
        if (isRunOnUIThread) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.setProgressBarIndeterminateVisibility(true);
                }
            });
        }
        else {
            activity.setProgressBarIndeterminateVisibility(true);
        }
    }

    public void dismissIndeterminateProgressBar(final Activity activity) {
        dismissIndeterminateProgressBar(activity, false);
    }

    public void dismissIndeterminateProgressBar(final Activity activity, boolean isRunOnUIThread) {
        if (isRunOnUIThread) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.setProgressBarIndeterminateVisibility(false);
                }
            });
        }
        else {
            activity.setProgressBarIndeterminateVisibility(false);
        }
    }

}

class TransparentProgressDialog extends Dialog {

    private ImageView iv;

    public TransparentProgressDialog(Context context, int resourceIdOfImage) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        iv = new ImageView(context);
        iv.setImageResource(resourceIdOfImage);
        layout.addView(iv, params);
        addContentView(layout, params);
    }

    @Override
    public void show() {
        super.show();
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, .5f,
                Animation.RELATIVE_TO_SELF, .5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(3000);
        iv.setAnimation(anim);
        iv.startAnimation(anim);
    }
}
