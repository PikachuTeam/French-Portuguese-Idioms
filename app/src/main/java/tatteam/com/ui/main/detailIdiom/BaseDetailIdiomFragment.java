package tatteam.com.ui.main.detailIdiom;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

import tatteam.com.R;
import tatteam.com.app.BaseActivity;
import tatteam.com.app.BaseFragment;
import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.util.AppSpeaker;
import tatteam.com.database.DataSource;
import tatteam.com.entiny.IdiomeEntity;
import tatteam.com.ui.main.Page;
import tatteam.com.ui.main.detailIdiom.ggsearchidiom.SearchIdiomFragment;
import tatteam.com.ui.main.detailIdiom.smalllistindetail.SmallListPage;
import tatteam.com.utility.CommonUtil;
import tatteam.com.utility.ShareUtil;


public abstract class BaseDetailIdiomFragment extends BaseFragment {

    private String phrase;
    private MyPageAdapter pagerAdapter;
    private ViewPager viewPager;
    private IdiomeEntity idiomeEntity;
    private TextView tvTitle, tvDescription, tvDefinition, tvExample;

    private FloatingActionButton fabGoogleSearch;
    private RelativeLayout ivPlay;
    protected abstract Locale getLocale();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        refeshData();
        ivPlay = (RelativeLayout) rootView.findViewById(R.id.play_layout);
        AppCommon.getInstance().initIfNeeded(getBaseActivity().getApplicationContext());
        AppSpeaker.getInstance().initIfNeeded(getBaseActivity().getApplicationContext(), getLocale());
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppSpeaker.getInstance().ready())
                    AppSpeaker.getInstance().speak(phrase);
            }
        });
        tvTitle = (TextView) rootView.findViewById(R.id.tvTittle_detail);
        tvDescription = (TextView) rootView.findViewById(R.id.tvDescription);
        tvDefinition = (TextView) rootView.findViewById(R.id.tvDefinition);
        tvExample = (TextView) rootView.findViewById(R.id.tvExample);

        fabGoogleSearch = (FloatingActionButton) rootView.findViewById(R.id.fab_google_search);
        fabGoogleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchIdiomFragment searchIdiomFragment = new SearchIdiomFragment();
                Bundle bundle = new Bundle();
                bundle.putString("phrase", phrase);
                searchIdiomFragment.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_silde_bot_enter, R.anim.fragment_slide_top_exit,
                        R.anim.fragment_silde_top_enter, R.anim.fragment_silde_bot_exit);
                transaction.add(R.id.main_fragment, searchIdiomFragment, "WebView");
                transaction.addToBackStack("WebView");
                transaction.commit();
            }
        });

        setText();
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager_detail);

        pagerAdapter = new MyPageAdapter(getBaseActivity());
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout_detail);
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        CommonUtil.fixedChangeTextSizeTabLayout(tabLayout, getActivity().getResources().getDimensionPixelSize(R.dimen.text_size_tab_layout_detail_idiom));

        return rootView;
    }

    private void refeshData() {
        getBaseActivity().getActionsMenu().setVisibility(View.VISIBLE);
        Bundle bundle = this.getArguments();
        phrase = bundle.getString("phrase");

        idiomeEntity = new IdiomeEntity();
        idiomeEntity = DataSource.getInstance().getIdiomByPharse(phrase);

        deletePhraseNotInData(idiomeEntity.listSynonym);
        deletePhraseNotInData(idiomeEntity.listAntonym);
        DataSource.getInstance().updateRecent(phrase);
        setUpToolbar();

    }

    public void setText() {
        tvTitle.setText(idiomeEntity.Phrase);
        tvDescription.setText(idiomeEntity.Description);
        tvDefinition.setText(idiomeEntity.Definition);
        tvExample.setText(Html.fromHtml(idiomeEntity.Example));
    }

    void deletePhraseNotInData(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            IdiomeEntity idiomeEntity = new IdiomeEntity();
            idiomeEntity = DataSource.getInstance().getIdiomByPharse(list.get(i));
            if (!idiomeEntity.isInData) {
                list.remove(i);
                i--;
            }
        }
    }

    public void setUpToolbar() {
        getBaseActivity().getBtnToolBarBack().setVisibility(View.VISIBLE);

        getBaseActivity().getTvToolBarTitle().setText(getResources().getString(R.string.idiom));
        getBaseActivity().getToolBarBack().setVisibility(View.VISIBLE);
        getBaseActivity().getToolBarItem1().setVisibility(View.VISIBLE);
        getBaseActivity().getToolBarItem2().setVisibility(View.VISIBLE);

        if (idiomeEntity.isFavorite > 0)
            getBaseActivity().getBtnToolBarItem2().setBackgroundResource(R.drawable.star_favorite);
        else
            getBaseActivity().getBtnToolBarItem2().setBackgroundResource(R.drawable.star_icon_white);


        getBaseActivity().getToolBarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getBaseActivity().getBtnToolBarBack().setClickable(false);
                getFragmentManager().popBackStack();
            }
        });
        getBaseActivity().getToolBarItem2().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSource.getInstance().changeFavorite(phrase);
                if (idiomeEntity.isFavorite > 0) {
                    idiomeEntity.isFavorite = 0;
                    getBaseActivity().getBtnToolBarItem2().setBackgroundResource(R.drawable.star_icon_white);
                    makeSnackBar(getString(R.string.removed_from_favorite));
                } else {
                    idiomeEntity.isFavorite = 1;
                    getBaseActivity().getBtnToolBarItem2().setBackgroundResource(R.drawable.star_favorite);
                    makeSnackBar(getString(R.string.added_to_favorite));
                }
            }
        });
        getBaseActivity().getBtnToolBarItem1().setBackgroundResource(R.drawable.share_icon);

        // Share
        getBaseActivity().getToolBarItem1().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //share
                ShareUtil.shareViaIntent(getActivity(), "Hey! An Amazing Portugal Idioms App", ShareUtil.getSharingMessage(getActivity()));
            }
        });
    }

    @Override
    public void onResume() {
        setUpToolbar();
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (AppSpeaker.getInstance().ready()) {
            AppSpeaker.getInstance().stop();
        }
    }

    private class MyPageAdapter extends PagerAdapter {

        public Page[] myPages;
        SearchIdiomFragment SearchIdiomFragment;
        private BaseActivity activity;

        public MyPageAdapter(BaseActivity activity) {
            this.activity = activity;
            myPages = new Page[]{new SmallListPage(activity, idiomeEntity.listSynonym, phrase),
                    new SmallListPage(activity, idiomeEntity.listAntonym, phrase)};
        }

        @Override
        public int getCount() {
            return myPages.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View layout = myPages[position].getContent();
            container.addView(layout);
            return layout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Page page = myPages[position];
            page.destroy();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) return activity.getString(R.string.tab_detail1);
            return activity.getString(R.string.tab_detail2);
        }
    }
}