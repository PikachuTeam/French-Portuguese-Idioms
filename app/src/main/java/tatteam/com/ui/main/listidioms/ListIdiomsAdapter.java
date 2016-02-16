package tatteam.com.ui.main.listidioms;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import tatteam.com.R;
import tatteam.com.app.FlatEffectDark;
import tatteam.com.entiny.IdiomeEntity;


/**
 * Created by hoaba on 9/8/2015.
 */
public class ListIdiomsAdapter extends RecyclerView.Adapter<ListIdiomsAdapter.ViewHolder> {

    private ClickListener mlisListener;
    private List<IdiomeEntity> list;


    public ListIdiomsAdapter(List<IdiomeEntity> list) {
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

        viewHolder.tvTitle.setText(list.get(i).Phrase);
        viewHolder.tvMeaning.setText(list.get(i).Definition);
        if (list.get(i).isFavorite > 0)
            viewHolder.imageView.setBackgroundResource(R.drawable.star_icon_purple);
        else viewHolder.imageView.setBackgroundResource(R.drawable.star_icon_white2);


    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setMlisListener(ClickListener mlisListener) {
        this.mlisListener = mlisListener;

    }

    public interface ClickListener {
        public void onIdiomClicked(int position);

        public void onFavoriteChange(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle;
        public TextView tvMeaning;

        public ImageView imageView;
        public LinearLayout imageViewLayout;
        public FlatEffectDark item_favorite;


        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTittle_List);
            tvMeaning = (TextView) itemView.findViewById(R.id.tvMeanning_List);
            imageView = (ImageView) itemView.findViewById(R.id.favorite_icon);
            imageViewLayout = (LinearLayout) itemView.findViewById(R.id.favorite_icon_layout);
            imageViewLayout.setOnClickListener(this);
            item_favorite = (FlatEffectDark) itemView.findViewById(R.id.card_view2);
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