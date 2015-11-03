package tatteam.com;

import android.app.Application;

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
        DataSource.getInstance().destroy();
        super.onTerminate();
    }
}
