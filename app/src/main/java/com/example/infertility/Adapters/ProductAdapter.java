package com.example.infertility.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infertility.ItemModels.ProductItem;
import com.example.infertility.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    Context context;
    ArrayList<ProductItem> productItems;

    public ProductAdapter(Context context, ArrayList<ProductItem> productItems) {
        this.context = context;
        this.productItems = productItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductItem item = productItems.get(position);
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.price.setText(item.getPrice());
        holder.ratings.setText(item.getRatings());
        holder.imageView.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, subtitle, price, ratings;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            subtitle = itemView.findViewById(R.id.subtitleText);
            price = itemView.findViewById(R.id.txtPrice);
            ratings = itemView.findViewById(R.id.txtRating);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
