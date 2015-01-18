
package com.dekkoh.util;

public class Log {
    public static boolean DEBUG = !Constants.PRODUCTION;

    public static void i(String logmessage) {
        if (DEBUG) {
            i(getCallerCallerClassName(), logmessage);
        }
    }

    public static void i(String TAG, String logmessage) {
        if (DEBUG) {
            android.util.Log.i(TAG, logmessage);
        }
    }

    public static void v(String logmessage) {
        if (DEBUG) {
            v(getCallerCallerClassName(), logmessage);
        }
    }

    public static void v(String TAG, String logmessage) {
        if (DEBUG) {
            android.util.Log.v(TAG, logmessage);
        }
    }

    public static void d(String logmessage) {
        if (DEBUG) {
            d(getCallerCallerClassName(), logmessage);
        }
    }

    public static void d(String TAG, String logmessage) {
        if (DEBUG) {
            android.util.Log.d(TAG, logmessage);
        }
    }

    public static void w(String logmessage) {
        if (DEBUG) {
            w(getCallerCallerClassName(), logmessage);
        }
    }

    public static void w(String TAG, String logmessage) {
        if (DEBUG) {
            android.util.Log.w(TAG, logmessage);
        }
    }

    public static void e(String logmessage) {
        if (DEBUG) {
            e(getCallerCallerClassName(), logmessage);
        }
    }

    public static void e(String TAG, String logmessage) {
        if (DEBUG) {
            android.util.Log.e(TAG, logmessage);
        }
    }

    public static void e(Exception exception) {
        if (DEBUG) {
            e(getCallerCallerClassName(), exception.getMessage() + "");
            exception.printStackTrace();
        }
    }

    public static void e(String TAG, Exception exception) {
        if (DEBUG) {
            android.util.Log.e(TAG, exception.getMessage() + "");
            exception.printStackTrace();
        }
    }

    public static void e(String TAG, String message, Exception exception) {
        if (DEBUG && exception != null) {
            android.util.Log.e(TAG + "", message + "", exception);
        }
    }

    public static String getCallerCallerClassName() {
        try {

            StackTraceElement[] stackTraceElements = Thread.currentThread()
                    .getStackTrace();
            String callerClassName = null;
            for (int i = 1; i < stackTraceElements.length; i++) {
                StackTraceElement stackTraceElement = stackTraceElements[i];
                if (!stackTraceElement.getClassName().equals(
                        Log.class.getName())
                        && stackTraceElement.getClassName().indexOf(
                                "java.lang.Thread") != 0) {
                    if (callerClassName == null) {
                        callerClassName = stackTraceElement.getClassName();
                    } else if (!callerClassName.equals(stackTraceElement
                            .getClassName())) {
                        return stackTraceElement.getClassName();
                    }
                }
            }
            return Log.class.getName();
        } catch (Exception exception) {
            return Log.class.getName();
        }
    }
}
