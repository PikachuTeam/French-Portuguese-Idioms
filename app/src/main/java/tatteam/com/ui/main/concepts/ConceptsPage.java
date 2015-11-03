package tatteam.com.ui.main.concepts;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import tatteam.com.R;
import tatteam.com.app.BaseActivity;
import tatteam.com.database.DataSource;
import tatteam.com.entiny.LetterEntity;
import tatteam.com.entiny.TopicEntity;
import tatteam.com.ui.main.Page;


/**
 * Created by ThanhNH on 9/11/2015.
 */
public class ConceptsPage extends Page implements CharacterAdapter.ClickListener, TopicAdapter.ClickListener, ListConceptsPage.OnActiveListConceptsListener {
    private RecyclerView rvTopics, rvCharacter;
    private RecyclerView.LayoutManager lmTopic, lmCharacter;

    private TopicAdapter adapterTopic;
    private CharacterAdapter adapterCharacter;

    private List<LetterEntity> listletter, listtemp;
    private List<TopicEntity> listtopic;
    private RelativeLayout conceptsContanier;
    private ListConceptsPage listConceptsPage;

    public ConceptsPage(BaseActivity activity) {
        super(activity);

        listletter = new ArrayList<>();
        listtemp = new ArrayList<>();
        listtemp = DataSource.getInstance().getLettersTopic();
        listletter = DataSource.getInstance().getLetters();

        for (int i = 0; i < listletter.size(); i++) {
            if (isLetterInList(listletter.get(i).letter, listtemp) == false)
                listletter.remove(listletter.get(i));
        }
        listtemp.clear();
        listletter.get(0).selected = true;

        rvCharacter = (RecyclerView) getContent().findViewById(R.id.rv_alphabettopic);
        rvCharacter.setHasFixedSize(true);
        lmCharacter = new LinearLayoutManager(activity);
        rvCharacter.setLayoutManager(lmCharacter);


        adapterCharacter = new CharacterAdapter(activity, listletter);
        rvCharacter.setAdapter(adapterCharacter);
        adapterCharacter.setMlistener(this);

        rvTopics = (RecyclerView) content.findViewById(R.id.rv_topic);
        rvTopics.setHasFixedSize(true);
        lmTopic = new LinearLayoutManager(activity);
        rvTopics.setLayoutManager(lmTopic);
        conceptsContanier = ((RelativeLayout) getContent().findViewById(R.id.list_concepts_container));

        listtopic = new ArrayList<>();
        listtopic = DataSource.getInstance().getTopicsByLetter(listletter.get(0).letter);
        adapterTopic = new TopicAdapter(listtopic);
        rvTopics.setAdapter(adapterTopic);
        adapterTopic.setMlisListener(this);
    }

    public void reloadListConceptsPage() {
        listConceptsPage.reloadData();
    }

    @Override
    protected int getContentId() {
        return R.layout.page_concepts;
    }

    @Override
    public void onCharacterClick(int position) {
        for (int i = 0; i < listletter.size(); i++) {
            listletter.get(i).selected = false;
        }
        listletter.get(position).selected = true;
        adapterCharacter.notifyDataSetChanged();
        listtopic = DataSource.getInstance().getTopicsByLetter(listletter.get(position).letter.toUpperCase());
        adapterTopic = new TopicAdapter(listtopic);
        rvTopics.setAdapter(adapterTopic);
        rvTopics.invalidate();
        adapterTopic.setMlisListener(this);
    }

    @Override
    public void onTopicClick(int position) {
        listConceptsPage = new ListConceptsPage(activity, conceptsContanier);
        listConceptsPage.setTopicName(listtopic.get(position).Concept);
        conceptsContanier.addView(listConceptsPage.getContent());
        listConceptsPage.setListener(this);
        listConceptsPage.open();
    }

    public boolean isLetterInList(String letter, List<LetterEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            if (letter.equalsIgnoreCase(list.get(i).letter)) return true;

        }
        return false;
    }

    @Override
    public void onListConceptsOpen(ListConceptsPage listConceptsPage) {
        adapterTopic.setMlisListener(null);
    }

    @Override
    public void onListConceptsOpened(ListConceptsPage listConceptsPage) {

    }

    @Override
    public void onListConceptsClosed(ListConceptsPage listConceptsPage) {
        conceptsContanier.removeAllViews();
        adapterTopic.setMlisListener(this);
        this.listConceptsPage = null;
    }

    public boolean isListConceptsPageOpened() {
        return listConceptsPage != null;
    }

    public void closeListConceptsPage() {
        listConceptsPage.close();
    }


}
