package tatteam.com.ui.main.recent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import tatteam.com.R;
import tatteam.com.app_common.ui.drawable.FlatEffectDark;
import tatteam.com.entiny.IdiomeEntity;


/**
 * Created by hoaba on 9/8/2015.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    private ClickListener mlisListener;
    private List<IdiomeEntity> list;


    public RecentAdapter(List<IdiomeEntity> list) {
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_idiom, viewGroup, false);
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
        public void ItemClicked(int position);

        public void FavoriteChange(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle;
        public TextView tvMeaning;
        public FlatEffectDark item_favorite;
        public ImageView imageView;
        public LinearLayout imageView_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTittle_List);
            tvMeaning = (TextView) itemView.findViewById(R.id.tvMeanning_List);
            imageView = (ImageView) itemView.findViewById(R.id.favorite_icon);
            item_favorite = (FlatEffectDark) itemView.findViewById(R.id.card_view2);
            item_favorite.setOnClickListener(this);
            imageView_layout = (LinearLayout) itemView.findViewById(R.id.favorite_icon_layout);

            imageView_layout.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mlisListener != null) {
                if (v == item_favorite) {
//                    v.setClickable(false);
                    mlisListener.ItemClicked(getAdapterPosition());
                } else mlisListener.FavoriteChange(getAdapterPosition());
            }
        }

    }
}