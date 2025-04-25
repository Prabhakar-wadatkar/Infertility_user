package com.example.infertility.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infertility.ItemModels.PublishItem;
import com.example.infertility.R;

import java.util.List;

public class PublishAdapter extends RecyclerView.Adapter<PublishAdapter.ViewHolder> {

    Context context;
    List<PublishItem> publishItems;

    public PublishAdapter(Context context, List<PublishItem> publishItems) {
        this.context = context;
        this.publishItems = publishItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.publish_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PublishItem publishItem = publishItems.get(position);
        holder.title.setText(publishItem.getTitle());
        holder.imageView.setImageResource(publishItem.getImageResId());
    }

    @Override
    public int getItemCount() {
        return publishItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleText);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
