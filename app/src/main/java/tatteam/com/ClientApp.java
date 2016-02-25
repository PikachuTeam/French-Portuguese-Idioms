package tatteam.com;

import android.app.Application;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.sqlite.DatabaseLoader;
import tatteam.com.app_common.util.AppSpeaker;
import tatteam.com.database.DataSource;


/**
 * Created by ThanhNH on 2/1/2015.
 */
public class ClientApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCommon.getInstance().initIfNeeded(getApplicationContext());
        DatabaseLoader.getInstance().restoreState(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
