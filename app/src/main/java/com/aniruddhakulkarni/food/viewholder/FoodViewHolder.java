package com.aniruddhakulkarni.food.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniruddhakulkarni.food.R;
import com.aniruddhakulkarni.food.common.ItemClickListener;

/**
 * Created by aniruddhakulkarni on 12/05/18.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvFoodName;
    public ImageView ivFood;
    public ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView) {
        super(itemView);
        tvFoodName = itemView.findViewById(R.id.tv_food);
        ivFood = itemView.findViewById(R.id.iv_food);
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
