package tatteam.com.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import tatteam.com.R;
import tatteam.com.ui.main.favorite.FavoriteFragment;
import tatteam.com.utility.ShareUtil;

/**
 * Created by hoaba on 8/21/2015.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected FloatingActionButton test, fbMyTopic, fbFavorite, fbFeeback, fbRemoveAd;
    protected AdView mAdView;
    private FloatingActionsMenu actionsMenu;
    private Toolbar toolbar;
    private RelativeLayout fabListen;
    private ImageView btnToolBarBack, btnToolBarBackFake;
    private ImageView btnToolBarItem1;
    private ImageView btnToolBarItem2;
    private TextView tvToolBarTitle, tvToolBarTitleFake;
    private LinearLayout contenToolBar, toolBarFake, toolBarItem1, toolBarItem2, toolBarBack, toolBarBackFake;
    private boolean isAdLoadingFine = false;

    protected abstract BaseFragment getFragmentContent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        setupToolBar();
        setFabMenu();

        addFragmentContent();
    }

    @Override
    public void onBackPressed() {
        if (actionsMenu.isExpanded()) {
            actionsMenu.collapse();
        } else {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                BaseFragment currentFragment = (BaseFragment) fragmentManager.findFragmentById(R.id.main_fragment);
                currentFragment.onBackPressed();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void addFragmentContent() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        BaseFragment fragment = getFragmentContent();
        transaction.add(R.id.main_fragment, fragment, fragment.getClass().getName());
        transaction.commit();
    }


    public void makeSnackBar(String s) {
        Snackbar.make(findViewById(R.id.main_fragment), s, Snackbar.LENGTH_SHORT).show();
    }


    protected boolean enableAdMod() {
        return false;
    }

    public void setFabMenu() {
        actionsMenu = (FloatingActionsMenu) findViewById(R.id.fabmenu);
        test = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSnackBar(getString(R.string.coming_soon));
            }
        });
        fbMyTopic = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_myconcept);
        fbMyTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeSnackBar(getString(R.string.coming_soon));
            }
        });

        fabListen = (RelativeLayout) findViewById(R.id.fablisten);
        actionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                fabListen.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                fabListen.setVisibility(View.INVISIBLE);
            }
        });
        fabListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionsMenu.collapse();
            }
        });

        fbFavorite = (FloatingActionButton) findViewById(R.id.fab_favorite);
        fbFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionsMenu.collapse();
                BaseFragment fragment = (BaseFragment) getFragmentManager().findFragmentByTag("Favorite");
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                if (fragment == null) {
                    if (getFragmentManager().findFragmentByTag("WebView") == null) {
                        BaseFragment.replaceFragment(getFragmentManager(), favoriteFragment, "Favorite", "Favorite");
                    } else {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.fragment_silde_top_enter,
                                R.anim.fragment_silde_bot_exit, R.anim.fragment_silde_bot_enter, R.anim.fragment_slide_top_exit);
                        transaction.replace(R.id.main_fragment, favoriteFragment, "Favorite");
                        transaction.addToBackStack("Favorite");
                        transaction.commit();
                    }
                }
            }
        });

        fbFeeback = (FloatingActionButton) findViewById(R.id.fab_feedback);
        fbFeeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionsMenu.collapse();
                ShareUtil.shareToGMail(BaseActivity.this, new String[]{ShareUtil.MAIL_ADDRESS_DEFAULT}, getString(R.string.subject_mail_feedback), "");
            }
        });

        fbRemoveAd = (FloatingActionButton) findViewById(R.id.fab_removead);

    }

    public void setupToolBar() {
        //real
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnToolBarBack = (ImageView) toolbar.findViewById(R.id.img_btn);
        btnToolBarItem1 = (ImageView) toolbar.findViewById(R.id.toolbar_item1);
        btnToolBarItem2 = (ImageView) toolbar.findViewById(R.id.toolbar_item2);
        tvToolBarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        contenToolBar = (LinearLayout) toolbar.findViewById(R.id.realtoolbar);

        toolBarItem1 = (LinearLayout) toolbar.findViewById(R.id.toolbar_item1_layout);
        toolBarItem2 = (LinearLayout) toolbar.findViewById(R.id.toolbar_item2_layout);
        toolBarBack = (LinearLayout) toolbar.findViewById(R.id.img_btn_layout);


        //fake
        tvToolBarTitleFake = (TextView) toolbar.findViewById(R.id.toolbar_title_fake);
        btnToolBarBackFake = (ImageView) toolbar.findViewById(R.id.img_btn_fake);
        toolBarFake = (LinearLayout) toolbar.findViewById(R.id.faketoolBar);
        toolBarBackFake = (LinearLayout) toolbar.findViewById(R.id.img_btn__fake_layout);
        toolBarFake.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
    }


    protected void onRemoveAdClick() {

    }

    public FloatingActionsMenu getActionsMenu() {
        return actionsMenu;
    }
//getChildOfToolBar

    public LinearLayout getContenToolBar() {
        return contenToolBar;
    }

    public TextView getTvToolBarTitle() {
        return tvToolBarTitle;
    }

    public ImageView getBtnToolBarBack() {
        return btnToolBarBack;
    }

    public ImageView getBtnToolBarItem1() {
        return btnToolBarItem1;
    }

    public ImageView getBtnToolBarItem2() {
        return btnToolBarItem2;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public LinearLayout getToolBarItem2() {
        return toolBarItem2;
    }

    public LinearLayout getToolBarItem1() {
        return toolBarItem1;
    }
    //getChildOfToolBarFake


    public LinearLayout getToolBarBackFake() {
        return toolBarBackFake;
    }

    public LinearLayout getToolBarBack() {
        return toolBarBack;
    }

    public ImageView getBtnToolBarBackFake() {
        return btnToolBarBackFake;
    }

    public TextView getTvToolBarTitleFake() {
        return tvToolBarTitleFake;
    }

    public LinearLayout getToolBarFake() {
        return toolBarFake;
    }
}