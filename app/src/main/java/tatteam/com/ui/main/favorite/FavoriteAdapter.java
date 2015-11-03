package tatteam.com.ui.main.favorite;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import tatteam.com.R;
import tatteam.com.entiny.IdiomeEntity;


/**
 * Created by hoaba on 9/8/2015.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private ClickListener mlisListener;
    private List<IdiomeEntity> list;

    public FavoriteAdapter(List<IdiomeEntity> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_idiom, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        if (!list.get(i).isHeader) {
            viewHolder.item_favorite.setVisibility(View.VISIBLE);
            viewHolder.item_favorite_character.setVisibility(View.GONE);

            viewHolder.tvTitle.setText(list.get(i).Phrase);
            viewHolder.tvMeaning.setText(list.get(i).Definition);
            if (list.get(i).isFavorite > 0)
                viewHolder.imageView.setBackgroundResource(R.drawable.star_icon_purple);
            else viewHolder.imageView.setBackgroundResource(R.drawable.star_icon_white2);
        } else {
            viewHolder.item_favorite.setVisibility(View.GONE);
            viewHolder.item_favorite_character.setVisibility(View.VISIBLE);
            viewHolder.tvFavoriteCharacter.setText(list.get(i).Phrase.toUpperCase());

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setMlisListener(ClickListener mlisListener) {
        this.mlisListener = mlisListener;
    }

    public interface ClickListener {
        void onIdiomClicked(int position);

        void onFavoriteChange(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle;
        public TextView tvMeaning;
        public CardView item_favorite_character;
        public CardView item_favorite;
        public ImageView imageView;
        public TextView tvFavoriteCharacter;
        public LinearLayout imageView_layout;


        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTittle_List);
            tvMeaning = (TextView) itemView.findViewById(R.id.tvMeanning_List);
            tvFavoriteCharacter = (TextView) itemView.findViewById(R.id.tvTittle_List_favorite);
            imageView = (ImageView) itemView.findViewById(R.id.favorite_icon);
            imageView_layout = (LinearLayout) itemView.findViewById(R.id.favorite_icon_layout);
            item_favorite = (CardView) itemView.findViewById(R.id.card_view2);
            item_favorite_character = (CardView) itemView.findViewById(R.id.list_item_favorite_character);
            imageView_layout.setOnClickListener(this);
            item_favorite.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (mlisListener != null) {
                if (v == item_favorite) {

                    mlisListener.onIdiomClicked(getAdapterPosition());
                } else mlisListener.onFavoriteChange(getAdapterPosition());
            }
        }
    }

}