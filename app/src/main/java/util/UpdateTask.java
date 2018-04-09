package util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import com.ridecrew.ridecrew.callback.HttpTaskCallback;

/**
 * Created by KIM on 2018-04-09.
 */

public class UpdateTask extends AsyncTask<Object, Object, Boolean> {

    Context context;
    HttpTaskCallback callback;

    @Override
    protected void onPostExecute(Boolean ret) {
        if(ret)
            callback.update();
        else
            callback.next();
    }

    @Override
    protected Boolean doInBackground(Object... params) {
        return isAppUpdate();
    }

    public void setInit(Context context, HttpTaskCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    /***
     *                  [Version]
     *                  1.          0.          10
     *                  Major       Minor       Patch
     *
     *                  Major   :   대규모 업데이트 ( UI, 새로운 기능 )
     *                  Minor   :   심각한 버그, 사소한 버그 수정
     *                  Patch   :   업데이트하지 않아도 될 사소한 수정
     *
     *                  ** Major, Minor가 상위일 경우 업데이트 진행함 **
     *
     * ex1) Store       1.0.11
     *      Device      1.0.10
     *      업데이트 안함
     *
     * ex2) Store       1.1.0
     *      Device      1.0.10
     *      버그 수정으로 인한 업데이트
     *
     * ex3) Store       2.0.0
     *      Device      1.0.10
     *      대규모 업데이트
     *
     * @return
     */
    private boolean isAppUpdate() {

        String store_version = MarketVersionChecker.getMarketVersion(context.getPackageName());
        if(store_version == null || store_version.equals(""))
            return false;

        String device_version = "";
        try {
            device_version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        int lastStoreIndex = store_version.lastIndexOf(".");
        int lastDeviceIndex = device_version.lastIndexOf(".");

        /* Major, Minor 까지만 버전 비교*/
        store_version = store_version.substring(0, lastStoreIndex);
        device_version = device_version.substring(0, lastDeviceIndex);

        Version store = new Version(store_version);
        Version device = new Version(device_version);

        if( store.compareTo(device) == 1 )  /* store > device */
            return true;
        else
            return false;
    }
}
