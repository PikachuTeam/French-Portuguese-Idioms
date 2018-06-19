package tatteam.com.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import tatteam.com.R;
import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.sqlite.DatabaseLoader;
import tatteam.com.app_common.util.AppConstant;
import tatteam.com.app_common.util.AppSpeaker;


/**
 * Created by ThanhNH on 9/11/2015.
 */
public abstract class BaseSplashActivity extends tatteam.com.app_common.ui.activity.BaseSplashActivity {

    protected abstract Locale getLocale();

    protected abstract String getDatabaseName();

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreateContentView() {

    }

    @Override
    protected void onInitAppCommon() {
        AppCommon.getInstance().initIfNeeded(getApplicationContext());
        AppCommon.getInstance().increaseLaunchTime();
        AppCommon.getInstance().syncAdsIfNeeded(AppConstant.AdsType.SMALL_BANNER_LANGUAGE_LEARNING, AppConstant.AdsType.BIG_BANNER_LANGUAGE_LEARNING);
        AppSpeaker.getInstance().initIfNeeded(getApplicationContext(), getLocale());
        DatabaseLoader.getInstance().createIfNeeded(getApplicationContext(), getDatabaseName());
    }

    @Override
    protected void onFinishInitAppCommon() {
        FirebaseAnalytics.getInstance(this);
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;

                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }

                        if (deepLink != null) {
                            String[] queries = deepLink.getQuery().split("&");
                            if (queries.length > 0) {
                                String[] data = queries[0].split("=");

                                String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                                Calendar now = Calendar.getInstance();
                                DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy HHmmss");
                                String dateTime = dateFormat.format(now.getTime());

                                HashMap<String, Object> toUpdate = new HashMap<>();

                                if (isFirstOpen()) {
                                    toUpdate.put("/" + androidId + "/source", data[1]);
                                    toUpdate.put("/" + androidId + "/" + dateTime, "Install app");
                                    updateFirstOpen();
                                } else {
                                    toUpdate.put("/" + androidId + "/" + dateTime, "Open app");
                                }

                                FirebaseDatabase.getInstance().getReference().updateChildren(toUpdate);
                            }
                        }

                        startActivity(new Intent(BaseSplashActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startActivity(new Intent(BaseSplashActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }

    private boolean isFirstOpen() {
        SharedPreferences sharedPreferences = getSharedPreferences("tatteam.com.frenchidiom", Activity.MODE_PRIVATE);
        return !sharedPreferences.contains("IS_FIRST_OPEN") || sharedPreferences.getBoolean("IS_FIRST_OPEN", true);
    }

    private void updateFirstOpen() {
        SharedPreferences sharedPreferences = getSharedPreferences("tatteam.com.frenchidiom", Activity.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("IS_FIRST_OPEN", false).apply();
    }
}