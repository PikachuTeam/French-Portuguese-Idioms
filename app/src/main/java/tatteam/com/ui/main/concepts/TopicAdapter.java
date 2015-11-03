package tatteam.com.ui.main.concepts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tatteam.com.R;
import tatteam.com.entiny.TopicEntity;

/**
 * Created by hoaba on 9/8/2015.
 */
public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

    private ClickListener mlisListener;

    private List<TopicEntity> list;

    public TopicAdapter(List<TopicEntity> list) {

        super();
        this.list = new ArrayList<>();
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_concept, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvTopic.setText(list.get(i).Concept);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setMlisListener(ClickListener mlisListener) {
        this.mlisListener = mlisListener;
    }

    public interface ClickListener {
        public void onTopicClick(int position);

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvTopic;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTopic = (TextView) itemView.findViewById(R.id.tv_item_topic_name);
        }


        @Override
        public void onClick(View v) {
            if (mlisListener != null)
                mlisListener.onTopicClick(getAdapterPosition());
        }
    }
}