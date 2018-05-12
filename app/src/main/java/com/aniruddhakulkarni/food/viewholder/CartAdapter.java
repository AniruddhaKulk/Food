package com.aniruddhakulkarni.food.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.aniruddhakulkarni.food.R;
import com.aniruddhakulkarni.food.common.ItemClickListener;
import com.aniruddhakulkarni.food.model.Order;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by aniruddhakulkarni on 12/05/18.
 */

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView tvFoodName;
    TextView tvFoodPrice;
    ImageView ivFood;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    CartViewHolder(View itemView) {
        super(itemView);
        tvFoodName = itemView.findViewById(R.id.tv_cart_food_name);
        tvFoodPrice = itemView.findViewById(R.id.tv_cart_food_price);
        ivFood = itemView.findViewById(R.id.iv_cart_food);
    }

    @Override
    public void onClick(View view) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> list;

    public CartAdapter(List<Order> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CartViewHolder(inflater.inflate(R.layout.row_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(list.get(position).getQuantity(), Color.RED);
        holder.ivFood.setImageDrawable(textDrawable);

        Locale locale = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        int price = Integer.parseInt(list.get(position).getPrice()) * Integer.parseInt(list.get(position).getQuantity());

        holder.tvFoodName.setText(list.get(position).getProductName());
        holder.tvFoodPrice.setText(numberFormat.format(price));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
