package tatteam.com.ui.main.concepts;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tatteam.com.R;
import tatteam.com.app.BaseActivity;
import tatteam.com.entiny.LetterEntity;


/**
 * Created by hoaba on 9/8/2015.
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private BaseActivity activity;
    private ClickListener mlistener;
    private List<LetterEntity> listLetter;


    public CharacterAdapter(BaseActivity activity, List<LetterEntity> listLetter) {
        super();
        this.activity = activity;
        this.listLetter = new ArrayList<>();
        this.listLetter = listLetter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_character_in_concept, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvCharacter.setText(listLetter.get(i).letter.toUpperCase());
        if (listLetter.get(i).selected) {
            viewHolder.cvCharacter.setCardBackgroundColor(activity.getResources().getColor(R.color.topic_item));
            viewHolder.tvCharacter.setTextColor(activity.getResources().getColor(R.color.white));
        } else {
            viewHolder.cvCharacter.setCardBackgroundColor(activity.getResources().getColor(R.color.white));
            viewHolder.tvCharacter.setTextColor(activity.getResources().getColor(R.color.topic_item));
        }
    }

    @Override
    public int getItemCount() {

        return listLetter.size();
    }

    public void setMlistener(ClickListener listener) {
        this.mlistener = listener;
    }

    public interface ClickListener {
        public void onCharacterClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvCharacter;
        public CardView cvCharacter;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            cvCharacter = (CardView) itemView.findViewById(R.id.background_character_topic_item);
            tvCharacter = (TextView) itemView.findViewById(R.id.tv_item_character_topic);

        }


        @Override
        public void onClick(View v) {
            if (mlistener != null)
                mlistener.onCharacterClick(getAdapterPosition());
        }
    }
}
