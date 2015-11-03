package tatteam.com.ui;

import android.os.Bundle;

import tatteam.com.app.BaseActivity;
import tatteam.com.app.BaseFragment;
import tatteam.com.database.DataSource;
import tatteam.com.ui.main.MainFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BaseFragment getFragmentContent() {
        return new MainFragment();
    }

    @Override
    protected boolean enableAdMod() {
        return true;
    }

    @Override
    protected void onRemoveAdClick() {
    }




    @Override
    protected void onDestroy() {
        DataSource.getInstance().destroy();
        super.onDestroy();
    }
}
