package tatteam.com.ui.main.recent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import tatteam.com.R;
import tatteam.com.app.BaseActivity;
import tatteam.com.app.BaseFragment;
import tatteam.com.database.DataSource;
import tatteam.com.entiny.IdiomeEntity;
import tatteam.com.ui.main.Page;
import tatteam.com.ui.main.detailIdiom.DetailIdiomFragment;

/**
 * Created by ThanhNH on 9/12/2015.
 */
public class RecentPage extends Page implements RecentAdapter.ClickListener {
    private static int MAX_COUNT = 100;

    private RecyclerView mRecyclerView;
    private RecentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RelativeLayout noRecent;
    private List<IdiomeEntity> list;


    public RecentPage(BaseActivity activity) {
        super(activity);
        noRecent = (RelativeLayout) content.findViewById(R.id.non_recent);

        mRecyclerView = (RecyclerView) content.findViewById(R.id.recycler_view_list_idioms);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_recent;
    }

    public void refreshData() {
        list = new ArrayList<>();
        list = DataSource.getIdiomsRecent(MAX_COUNT);
        if (list.isEmpty()) {
            noRecent.setVisibility(View.VISIBLE);
        } else {
            noRecent.setVisibility(View.GONE);
        }
        mAdapter = new RecentAdapter(list);
        mAdapter.setMlisListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void ItemClicked(int position) {
        DetailIdiomFragment detailIdiomFragment = new DetailIdiomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phrase", list.get(position).Phrase);
        detailIdiomFragment.setArguments(bundle);
        BaseFragment.replaceFragment(activity.getFragmentManager(), detailIdiomFragment, list.get(position).Phrase, list.get(position).Phrase);
    }

    @Override
    public void FavoriteChange(int position) {
        if (list.get(position).isFavorite == 1) {
            list.get(position).isFavorite = 0;
            activity.makeSnackBar(activity.getString(R.string.removed_from_favorite));
        } else {
            list.get(position).isFavorite = 1;
            activity.makeSnackBar(activity.getString(R.string.added_to_favorite));
        }
        mAdapter = new RecentAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMlisListener(this);

        DataSource.changeFavorite(list.get(position).Phrase);
    }

    @Override
    public void destroy() {
        list = null;
        mAdapter = null;
        super.destroy();
    }
}
