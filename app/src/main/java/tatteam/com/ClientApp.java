package tatteam.com;

import android.app.Application;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.util.AppSpeaker;
import tatteam.com.database.DataSource;


/**
 * Created by ThanhNH on 2/1/2015.
 */
public class ClientApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataSource.getInstance().init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        AppCommon.getInstance().destroy();
        AppSpeaker.getInstance().destroy();
        super.onTerminate();
    }
}
