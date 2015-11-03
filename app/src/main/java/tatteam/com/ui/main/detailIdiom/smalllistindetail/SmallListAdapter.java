package tatteam.com.ui.main.detailIdiom.smalllistindetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tatteam.com.R;


/**
 * Created by hoaba on 9/12/2015.
 */
public class SmallListAdapter extends RecyclerView.Adapter<SmallListAdapter.ViewHolder> {

    private ClickListener mlisListener;
    private List<String> phrases;

    public SmallListAdapter(List<String> phrases) {
        this.phrases = phrases;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_small_list_idioms, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        viewHolder.tvTitle.setText(phrases.get(i));


    }

    @Override
    public int getItemCount() {

        return phrases.size();
    }

    public void setMlisListener(ClickListener mlisListener) {
        this.mlisListener = mlisListener;

    }

    public interface ClickListener {
        public void ItemClicked(int position);


    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTitle;


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTittle_small_List);


        }


        @Override
        public void onClick(View v) {
            if (mlisListener != null) {
                mlisListener.ItemClicked(getAdapterPosition());

            }
        }
    }
}