package util;

/**
 * Created by kim on 2017. 12. 25..
 */

public class LogUtil {
    public static String getStackTrace(Throwable e) {
        StringBuffer buffer = new StringBuffer();
        printStackTrace(buffer, e, false);
        return buffer.toString();
    }

    private static void printStackTrace(StringBuffer buffer, Throwable e, boolean cause) {
        if(cause) {
            buffer.append("Caused by: ");
        }
        buffer.append(e);
        buffer.append("\r\n");
        StackTraceElement[] trace = e.getStackTrace();
        for (int i=0; i < trace.length; i++) {
            buffer.append("\tat " + trace[i]);
            buffer.append("\r\n");
        }
        Throwable ourCause = e.getCause();
        if (ourCause != null) {
            printStackTrace(buffer, ourCause, true);
        }
    }
}
