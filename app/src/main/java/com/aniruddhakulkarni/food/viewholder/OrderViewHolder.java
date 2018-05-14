package com.aniruddhakulkarni.food.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aniruddhakulkarni.food.R;
import com.aniruddhakulkarni.food.common.ItemClickListener;

/**
 * Created by aniruddhakulkarni on 14/05/18.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvOrderID;
    public TextView tvOrderStatus;
    public TextView tvOrderPhone;
    public TextView tvOrderAddress;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public OrderViewHolder(View itemView) {
        super(itemView);
        tvOrderAddress = itemView.findViewById(R.id.tv_order_address);
        tvOrderID = itemView.findViewById(R.id.tv_order_id);
        tvOrderPhone = itemView.findViewById(R.id.tv_order_phone);
        tvOrderStatus = itemView.findViewById(R.id.tv_order_status);

        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
