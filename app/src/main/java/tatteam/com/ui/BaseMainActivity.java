package tatteam.com.ui;

import android.os.Bundle;

import java.util.Locale;

import tatteam.com.app.BaseActivity;
import tatteam.com.app.BaseFragment;
import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.util.AppSpeaker;
import tatteam.com.database.DataSource;
import tatteam.com.ui.main.MainFragment;


public abstract class BaseMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseFragment getFragmentContent() {
        return new MainFragment();
    }





    @Override
    protected void onDestroy() {
        DataSource.getInstance().destroy();
        super.onDestroy();
    }
}
