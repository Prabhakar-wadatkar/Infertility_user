package com.example.infertility.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infertility.ItemModels.DiscussionItem;
import com.example.infertility.R;

import java.util.List;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {

    Context context;
    List<DiscussionItem> discussionItems;

    public DiscussionAdapter(Context context, List<DiscussionItem> discussionItems) {
        this.context = context;
        this.discussionItems = discussionItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.discussion_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiscussionItem discussionItem = discussionItems.get(position);
        holder.discussionTitle.setText(discussionItem.getDiscussionTitle());
        holder.discussionImage.setImageResource(discussionItem.getImageResId());
    }

    @Override
    public int getItemCount() {
        return discussionItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView discussionTitle;
        ImageView discussionImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            discussionTitle = itemView.findViewById(R.id.discussionTitle);
            discussionImage = itemView.findViewById(R.id.discussionImage);
        }
    }
}
