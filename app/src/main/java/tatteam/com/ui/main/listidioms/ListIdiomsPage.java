package tatteam.com.ui.main.listidioms;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
 * Created by hoaba on 9/11/2015.
 */
public class ListIdiomsPage extends Page implements ListIdiomsAdapter.ClickListener {
    private List<IdiomeEntity> list;
    private RecyclerView mRecyclerView;
    private ListIdiomsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String letter;


    public ListIdiomsPage(BaseActivity activity, String letter) {
        super(activity);
        this.letter = letter;
        list = new ArrayList<>();
        list = DataSource.getInstance().getIdiomsByLetter(letter);

        mRecyclerView = (RecyclerView) getContent().findViewById(R.id.recycler_view_list_idioms);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(activity);

        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new ListIdiomsAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMlisListener(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.page_list_idoms;
    }

    @Override
    public void onIdiomClicked(int position) {
        DetailIdiomFragment detailIdiomFragment = new DetailIdiomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phrase", list.get(position).Phrase);
        detailIdiomFragment.setArguments(bundle);
        BaseFragment.replaceFragment(activity.getFragmentManager(), detailIdiomFragment, list.get(position).Phrase, list.get(position).Phrase);


    }

    @Override
    public void onFavoriteChange(int position) {
        if (list.get(position).isFavorite == 1) {
            list.get(position).isFavorite = 0;
            activity.makeSnackBar(activity.getString(R.string.removed_from_favorite));
        } else {
            list.get(position).isFavorite = 1;
            activity.makeSnackBar(activity.getString(R.string.added_to_favorite));
        }
        mAdapter = new ListIdiomsAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMlisListener(this);
        DataSource.getInstance().changeFavorite(list.get(position).Phrase);
    }
}
