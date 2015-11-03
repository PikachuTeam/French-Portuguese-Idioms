package tatteam.com.ui.main.detailIdiom.smalllistindetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import tatteam.com.R;
import tatteam.com.app.BaseActivity;
import tatteam.com.app.BaseFragment;
import tatteam.com.ui.main.Page;
import tatteam.com.ui.main.detailIdiom.DetailIdiomFragment;


/**
 * Created by hoaba on 9/12/2015.
 */
public class SmallListPage extends Page implements SmallListAdapter.ClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> list;
    private SmallListAdapter mAdapter;

    private String prephrase;

    public SmallListPage(BaseActivity activity, List<String> list, String phrase) {
        super(activity);
        this.list = list;
        this.prephrase = phrase;
        mRecyclerView = (RecyclerView) getContent().findViewById(R.id.recycler_view_small_list_idioms);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(activity);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SmallListAdapter(list);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setMlisListener(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.page_small_list_idioms;
    }

    @Override
    public void ItemClicked(int position) {

        BaseFragment fragment = (BaseFragment) activity.getFragmentManager().findFragmentByTag(prephrase);
        BaseFragment fragment1 = (BaseFragment) activity.getFragmentManager().findFragmentByTag(list.get(position));
        if (fragment == null) {
            DetailIdiomFragment detailIdiomFragment = new DetailIdiomFragment();
            Bundle bundle = new Bundle();
            bundle.putString("phrase", list.get(position));
            detailIdiomFragment.setArguments(bundle);
            BaseFragment.replaceFragment(activity.getFragmentManager(), detailIdiomFragment, list.get(position), list.get(position));

        } else if (fragment1 == null) {
            DetailIdiomFragment detailIdiomFragment = new DetailIdiomFragment();
            Bundle bundle = new Bundle();
            bundle.putString("phrase", list.get(position));
            detailIdiomFragment.setArguments(bundle);
            BaseFragment.replaceFragment(activity.getFragmentManager(), detailIdiomFragment, list.get(position), list.get(position));
        } else {
            activity.getFragmentManager().popBackStack();
        }
    }


}