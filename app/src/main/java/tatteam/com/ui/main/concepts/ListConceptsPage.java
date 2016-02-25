package tatteam.com.ui.main.concepts;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tatteam.com.R;
import tatteam.com.app.BaseActivity;
import tatteam.com.app.BaseFragment;
import tatteam.com.database.DataSource;
import tatteam.com.entiny.IdiomeEntity;
import tatteam.com.entiny.TopicEntity;
import tatteam.com.ui.main.Page;
import tatteam.com.ui.main.detailIdiom.DetailIdiomFragment;
import tatteam.com.ui.main.listidioms.ListIdiomsAdapter;


/**
 * Created by ThanhNH on 9/11/2015.
 */
public class ListConceptsPage extends Page implements ListIdiomsAdapter.ClickListener {

    private RecyclerView mRecyclerView;
    private ListIdiomsAdapter mAdapter;
    private TextView tvBack, tvBack1;
    private List<IdiomeEntity> listIdiomes;
    private String topicName;

    private OnActiveListConceptsListener listener;

    public ListConceptsPage(BaseActivity activity, ViewGroup parent) {
        super(activity, parent);

        setupRecyclerView();
        tvBack1 = (TextView) content.findViewById(R.id.back1);
        tvBack = (TextView) content.findViewById(R.id.back2);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
        tvBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });
        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_list_in_topic;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
        tvBack.setText(topicName);


        reloadData();
    }

    public void reloadData() {
        listIdiomes = new ArrayList<>();
        TopicEntity topic = DataSource.getTopicsByConcept(this.topicName);
        listIdiomes = DataSource.getListIdiomByPharse(topic.listPhrase);

        mAdapter = new ListIdiomsAdapter(listIdiomes);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMlisListener(this);
    }

    public void open() {
        AnimatorSet animationOpen = (AnimatorSet) AnimatorInflater.loadAnimator(activity,
                R.anim.fragment_silde_bot_enter);
        animationOpen.setTarget(getContent());
        animationOpen.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (listener != null) {
                    listener.onListConceptsOpen(ListConceptsPage.this);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (listener != null) {
                    listener.onListConceptsOpened(ListConceptsPage.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animationOpen.start();
    }

    public void close() {
        AnimatorSet animationClose = (AnimatorSet) AnimatorInflater.loadAnimator(activity,
                R.anim.fragment_silde_bot_exit);
        animationClose.setTarget(getContent());
        animationClose.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (listener != null) {
                    listener.onListConceptsClosed(ListConceptsPage.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animationClose.start();
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) content.findViewById(R.id.recycler_view_list_idioms_intopic);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onIdiomClicked(int position) {
        DetailIdiomFragment detailIdiomFragment = new DetailIdiomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("phrase", listIdiomes.get(position).Phrase);
        detailIdiomFragment.setArguments(bundle);
        BaseFragment.replaceFragment(activity.getFragmentManager(), detailIdiomFragment, listIdiomes.get(position).Phrase, listIdiomes.get(position).Phrase);
    }

    @Override
    public void onFavoriteChange(int position) {
        if (listIdiomes.get(position).isFavorite == 1) {
            listIdiomes.get(position).isFavorite = 0;
            activity.makeSnackBar(activity.getString(R.string.removed_from_favorite));
        } else {
            listIdiomes.get(position).isFavorite = 1;
            activity.makeSnackBar(activity.getString(R.string.added_to_favorite));

        }
        mAdapter = new ListIdiomsAdapter(listIdiomes);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setMlisListener(this);

        DataSource.changeFavorite(listIdiomes.get(position).Phrase);
    }

    public void setListener(OnActiveListConceptsListener listener) {
        this.listener = listener;
    }

    public static interface OnActiveListConceptsListener {
        public void onListConceptsOpen(ListConceptsPage listConceptsPage);

        public void onListConceptsOpened(ListConceptsPage listConceptsPage);

        public void onListConceptsClosed(ListConceptsPage listConceptsPage);
    }
}
