package tatteam.com.ui.main.favorite;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tatteam.com.R;
import tatteam.com.app.BaseFragment;
import tatteam.com.database.DataSource;
import tatteam.com.entiny.IdiomeEntity;
import tatteam.com.entiny.LetterEntity;
import tatteam.com.ui.main.detailIdiom.DetailIdiomFragment;

/**
 * Created by hoaba on 9/10/2015.
 */
public class FavoriteFragment extends BaseFragment implements FavoriteAdapter.ClickListener {
    private List<IdiomeEntity> list;
    private RecyclerView mRecyclerView;
    private FavoriteAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RelativeLayout noFavorite;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setUpToolbar();
        list = getListFavorite();

        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        noFavorite = (RelativeLayout) rootView.findViewById(R.id.non_favorite);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_favorite);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        list = getListFavorite();
        if (list.size() > 0) noFavorite.setVisibility(View.GONE);
        else noFavorite.setVisibility(View.VISIBLE);
        mAdapter = new FavoriteAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setMlisListener(this);
        return rootView;

    }

    @Override
    public void onIdiomClicked(int position) {
        DetailIdiomFragment detailIdiomFragment = new DetailIdiomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phrase", list.get(position).Phrase);
        detailIdiomFragment.setArguments(bundle);
        replaceFragment(getActivity().getFragmentManager(), detailIdiomFragment, list.get(position).Phrase, list.get(position).Phrase);
    }

    @Override
    public void onFavoriteChange(int position) {
        DataSource.getInstance().changeFavorite(list.get(position).Phrase);
        makeSnackBar(getString(R.string.removed_from_favorite));
        list = getListFavorite();
        mAdapter.updateData(list);

        if (list.size() == 0)
            noFavorite.setVisibility(View.VISIBLE);
        else noFavorite.setVisibility(View.GONE);
    }

    public List<IdiomeEntity> getListFavorite() {

        List<LetterEntity> letterEntities = DataSource.getInstance().getLetters();

        list = DataSource.getInstance().getListFavorite();
        List<IdiomeEntity> listFavorite = new ArrayList<>();

        //create favorites
        for (int i = 0; i < list.size(); i++) {
            IdiomeEntity idiomeEntity = list.get(i);
            char character = idiomeEntity.Phrase.charAt(0);
            if (!checkExitsCharacter(listFavorite, character)) {
                IdiomeEntity idiomeEntityHeader = new IdiomeEntity();
                idiomeEntityHeader.Phrase = character + "";
                idiomeEntityHeader.isHeader = true;
                listFavorite.add(idiomeEntityHeader);

            }
            listFavorite.add(idiomeEntity);
        }

        //set index indioms
        for (int i = 0; i < listFavorite.size(); i++) {
            IdiomeEntity idiomeEntity = listFavorite.get(i);
            for (int k = 0; k < letterEntities.size(); k++) {
                LetterEntity letterEntity = letterEntities.get(k);
                if (letterEntity.letter.charAt(0) == idiomeEntity.Phrase.charAt(0)) {
                    idiomeEntity.index = letterEntity.id;
                    if (idiomeEntity.isHeader) {
                        idiomeEntity.index -= 0.5f;
                    }
                    break;
                }
            }
        }

        //sort indioms
        for (int i = 0; i < listFavorite.size(); i++) {
            for (int j = i; j < listFavorite.size(); j++) {
                if (listFavorite.get(i).index > listFavorite.get(j).index) {
                    Collections.swap(listFavorite, i, j);
                }
            }
        }


        return listFavorite;
    }

    private boolean checkExitsCharacter(List<IdiomeEntity> listFavorite, char character) {
        for (int i = 0; i < listFavorite.size(); i++) {
            if (character == listFavorite.get(i).Phrase.charAt(0)) {
                return true;
            }
        }
        return false;
    }

    public void setUpToolbar() {
        getBaseActivity().getToolBarBack().setVisibility(View.VISIBLE);
        getBaseActivity().getTvToolBarTitle().setText(getResources().getString(R.string.favorite));
        getBaseActivity().getToolBarItem1().setVisibility(View.GONE);
        getBaseActivity().getToolBarItem2().setVisibility(View.GONE);
        getBaseActivity().getBtnToolBarBack().setVisibility(View.VISIBLE);
        getBaseActivity().getToolBarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

    }
}
