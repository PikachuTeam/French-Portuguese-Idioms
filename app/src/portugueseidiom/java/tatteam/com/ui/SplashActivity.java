package tatteam.com.ui;

import java.util.Locale;


/**
 * Created by ThanhNH on 9/11/2015.
 */
public class SplashActivity extends BaseSplashActivity {


    @Override
    protected Locale getLocale() {
        return new Locale("pt", "BR");
    }

    @Override
    protected String getDatabaseName() {
        return "portuguese_idioms.db";
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}