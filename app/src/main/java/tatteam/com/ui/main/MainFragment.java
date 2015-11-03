package tatteam.com.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import tatteam.com.R;
import tatteam.com.app.BaseActivity;
import tatteam.com.app.BaseFragment;
import tatteam.com.app_common.util.CloseAppHandler;
import tatteam.com.ui.main.alphabet.AlphabetPage;
import tatteam.com.ui.main.concepts.ConceptsPage;
import tatteam.com.ui.main.recent.RecentPage;
import tatteam.com.utility.CommonUtil;

/**
 * Created by ThanhNH on 9/11/2015.
 */
public class MainFragment extends BaseFragment implements CloseAppHandler.OnCloseAppListener {
    private MyPageAdapter pagerAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CloseAppHandler closeAppHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setupToolBar();

        setupTab(rootView);
        reloadDataIfNeeded();
        closeAppHandler = new CloseAppHandler(getBaseActivity());
        closeAppHandler.setListener(this);
        return rootView;
    }

    private void reloadDataIfNeeded() {
        if (viewPager.getCurrentItem() == 0) {
            ConceptsPage conceptsPage = (ConceptsPage) pagerAdapter.myPages[0];
            if (conceptsPage.isListConceptsPageOpened()) {
                conceptsPage.reloadListConceptsPage();
                return;
            }
        }
    }


    private void setupTab(View rootView) {
        //viewpager
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager_tab);
        if (pagerAdapter == null) {
            pagerAdapter = new MyPageAdapter(getBaseActivity());
        }
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.primary));
        CommonUtil.fixedChangeTextSizeTabLayout(tabLayout, getResources().getDimensionPixelSize(R.dimen.text_size_tab_layout_main));
    }


    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            ConceptsPage conceptsPage = (ConceptsPage) pagerAdapter.myPages[0];
            if (conceptsPage.isListConceptsPageOpened()) {
                conceptsPage.closeListConceptsPage();
                return;
            } else {

                    closeAppHandler.handlerKeyBack(getBaseActivity());

            }
        } else {
            closeAppHandler.handlerKeyBack(getBaseActivity());
        }
    }



    public void setupToolBar() {
        getBaseActivity().getToolBarItem1().setVisibility(View.GONE);
        getBaseActivity().getToolBarItem2().setVisibility(View.GONE);
        getBaseActivity().getToolBarBack().setVisibility(View.VISIBLE);
        getBaseActivity().getBtnToolBarBack().setVisibility(View.GONE);
        getBaseActivity().getTvToolBarTitle().setText(getResources().getString(R.string.app_name));
        getBaseActivity().getToolBarBack().setClickable(false);

    }

    @Override
    public void onRateAppDialogClose() {
        getActivity().finish();
    }

    @Override
    public void onTryToCloseApp() {
        makeSnackBar(getResources().getString(R.string.please_click_back_again_to_exit));

    }

    @Override
    public void onReallyWantToCloseApp() {
        getActivity().finish();
    }

    private static class MyPageAdapter extends PagerAdapter {

        public Page[] myPages;
        private BaseActivity activity;

        public MyPageAdapter(BaseActivity activity) {
            this.activity = activity;
            myPages = new Page[]{new ConceptsPage(activity), new AlphabetPage(activity), new RecentPage(activity)};
        }

        @Override
        public int getCount() {
            return myPages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View layout = myPages[position].getContent();
            container.addView(layout);
            if (position == 2) {
                RecentPage recentPage = (RecentPage) myPages[2];
                recentPage.refreshData();
            }
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Page page = myPages[position];
            page.destroy();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) return activity.getString(R.string.tab1);
            else if (position == 1) return activity.getString(R.string.tab2);
            return activity.getString(R.string.tab3);
        }


    }

}
