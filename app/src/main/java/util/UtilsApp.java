package util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by KIM on 2018-01-02.
 */

public class UtilsApp {

    public static String getAppVersionName(Context context) {
        String res = "0.0.0.0";
        try {
            res = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public static int getAppVersionCode(Context context) {
        int res = 0;
        try {
            res = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public static void requestFocus(Activity context, View view) {
        if (view.requestFocus()) {
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
