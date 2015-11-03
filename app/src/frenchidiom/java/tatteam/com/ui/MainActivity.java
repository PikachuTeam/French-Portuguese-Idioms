package tatteam.com.ui;

import android.os.Bundle;

import java.util.Locale;

import tatteam.com.app.BaseActivity;
import tatteam.com.app.BaseFragment;
import tatteam.com.database.DataSource;
import tatteam.com.ui.main.MainFragment;


public class MainActivity extends BaseMainActivity {
    @Override
    protected Locale getLocale() {
        return Locale.FRENCH;
    }
}
