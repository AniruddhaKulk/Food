package com.aniruddhakulkarni.food.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniruddhakulkarni.food.R;
import com.aniruddhakulkarni.food.common.ItemClickListener;

/**
 * Created by aniruddhakulkarni on 11/05/18.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvCategoryName;
    public ImageView ivCategory;
    public ItemClickListener itemClickListener;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        tvCategoryName = itemView.findViewById(R.id.tv_category);
        ivCategory = itemView.findViewById(R.id.iv_category);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
