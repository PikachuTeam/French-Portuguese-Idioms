package tatteam.com.ui.main.alphabet;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tatteam.com.R;
import tatteam.com.app.BaseActivity;
import tatteam.com.app.BaseFragment;
import tatteam.com.database.DataSource;
import tatteam.com.entiny.LetterEntity;
import tatteam.com.ui.main.Page;
import tatteam.com.ui.main.listidioms.ListIdiomsFragment;


/**
 * Created by ThanhNH on 9/11/2015.
 */
public class AlphabetPage extends Page implements AlphabetAdapter.ClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AlphabetAdapter mAdapter;
    private ArrayList<String> mCharacter;


    public AlphabetPage(BaseActivity activity) {
        super(activity);

        mCharacter = new ArrayList<>();
        List<LetterEntity> list = DataSource.getLetters();
        for (int i = 0; i < list.size(); i++) {
            mCharacter.add(list.get(i).letter);
        }

        mRecyclerView = (RecyclerView) content.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(activity, 5);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AlphabetAdapter(mCharacter);
        mAdapter.setMlisListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected int getContentId() {
        return R.layout.page_alphabet;
    }

    @Override
    public void ItemClicked(int position) {


        ListIdiomsFragment listIdiomsFragment = new ListIdiomsFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("listLetter", mCharacter);

        bundle.putInt("index", position);
        listIdiomsFragment.setArguments(bundle);

        BaseFragment.replaceFragment(activity.getFragmentManager(), listIdiomsFragment, ListIdiomsFragment.class.getName(), ListIdiomsFragment.class.getName());


    }
}
