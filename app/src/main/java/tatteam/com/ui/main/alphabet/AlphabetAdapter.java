package tatteam.com.ui.main.alphabet;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tatteam.com.R;


/**
 * Created by hoaba on 9/8/2015.
 */
public class AlphabetAdapter extends RecyclerView.Adapter<AlphabetAdapter.ViewHolder> {


    ArrayList<String> mCharacter = null;
    private ClickListener mlisListener;

    public AlphabetAdapter(ArrayList<String> mCharacter) {
        super();
        this.mCharacter = mCharacter;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_alphabet, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String character = mCharacter.get(i).toUpperCase();
        viewHolder.tvCharacter.setText(character);
    }

    @Override
    public int getItemCount() {

        return mCharacter.size();
    }

    public void setMlisListener(ClickListener mlisListener) {
        this.mlisListener = mlisListener;
    }

    public interface ClickListener {
        public void ItemClicked(int position);
    }

    ////////////////////////////////////////////////////////////////
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvCharacter;
        public CardView cvCharacter;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            cvCharacter = (CardView) itemView.findViewById(R.id.card_view);
            tvCharacter = (TextView) itemView.findViewById(R.id.tvCharacter);
        }

        public void setItem(String s) {
            tvCharacter.setText(s);

        }

        @Override
        public void onClick(View v) {
            if (mlisListener != null) {
//                v.setClickable(false);
                mlisListener.ItemClicked(getAdapterPosition());
            }
        }
    }
}