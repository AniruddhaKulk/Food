package com.aniruddhakulkarni.food;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aniruddhakulkarni.food.common.Common;
import com.aniruddhakulkarni.food.model.Request;
import com.aniruddhakulkarni.food.viewholder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    private RecyclerView rvOrder;
    private FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference orderReference = database.getReference("Requests");

        rvOrder = findViewById(R.id.rv_order);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));

        updateUI(orderReference);
    }

    private void updateUI(DatabaseReference orderReference) {
        final String phone = Common.currentUser.getPhone();
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(Request.class,
                R.layout.row_order,
                OrderViewHolder.class,
                orderReference.orderByChild("phone").equalTo(phone)) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.tvOrderID.setText(adapter.getRef(position).getKey());
                viewHolder.tvOrderStatus.setText(String.valueOf(convertIntToString(model.getStatus())));
                viewHolder.tvOrderPhone.setText(model.getPhone());
                viewHolder.tvOrderAddress.setText(model.getAddress());
            }
        };

        rvOrder.setAdapter(adapter);
    }

    private String convertIntToString(String status) {
        switch (status){
            case "0":
                return "Placed";
            case "1":
                return "On the way";
            case "2":
                return "Delivered";
            default:
                return "Unknown status";
        }
    }

}
