package tatteam.com.ui;

import java.util.Locale;


/**
 * Created by ThanhNH on 9/11/2015.
 */
public class SplashActivity extends BaseSplashActivity {
    @Override
    protected Locale getLocale() {
        return Locale.FRENCH;
    }

    @Override
    protected String getDatabaseName() {
        return "french_idioms.db";
    }
}