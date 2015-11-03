package tatteam.com.ui.main.listidioms;


import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import tatteam.com.R;
import tatteam.com.app.BaseActivity;
import tatteam.com.app.BaseFragment;
import tatteam.com.ui.main.Page;


/**
 * Created by hoaba on 9/11/2015.
 */
public class ListIdiomsFragment extends BaseFragment {
    private ViewPager mViewPager;
    private int index;
    private List<String> listLetter;
    private MyPageAdapter pagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        index = bundle.getInt("index");
        listLetter = new ArrayList<>();
        listLetter = bundle.getStringArrayList("listLetter");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_list_idioms, container, false);
        setupToolBar();

        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager_idioms);
        pagerAdapter = new MyPageAdapter(getBaseActivity(), listLetter);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(index);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                getBaseActivity().getTvToolBarTitle().setText(listLetter.get(position).toUpperCase());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return rootView;
    }

    public void setupToolBar() {

        getBaseActivity().getToolBarItem1().setVisibility(View.GONE);
        getBaseActivity().getToolBarItem2().setVisibility(View.GONE);
        getBaseActivity().getToolBarBack().setVisibility(View.VISIBLE);
        getBaseActivity().getBtnToolBarBack().setVisibility(View.VISIBLE);

        getBaseActivity().getTvToolBarTitle().setText(listLetter.get(index).toUpperCase());


        getBaseActivity().getToolBarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getBaseActivity().getBtnToolBarBack().setClickable(false);
                getFragmentManager().popBackStack();
            }
        });
    }

    private static class MyPageAdapter extends PagerAdapter {

        private BaseActivity activity;
        private List<String> myPagesName;
        private List<Page> myPages;

        public MyPageAdapter(BaseActivity activity, List<String> myPagesName) {
            this.activity = activity;
            this.myPagesName = myPagesName;
            myPages = new ArrayList<>();
            for (int i = 0; i < myPagesName.size(); i++) {
                myPages.add(new ListIdiomsPage(activity, myPagesName.get(i)));
            }
        }

        @Override
        public int getCount() {
            return myPagesName.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View layout = myPages.get(position).getContent();
            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((FrameLayout) object);
        }
    }
}
