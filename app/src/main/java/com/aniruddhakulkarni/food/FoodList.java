package com.aniruddhakulkarni.food;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.aniruddhakulkarni.food.common.ItemClickListener;
import com.aniruddhakulkarni.food.model.Food;
import com.aniruddhakulkarni.food.viewholder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    private RecyclerView rvFood;
    private String categoryID;
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        rvFood = findViewById(R.id.rv_food);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference food = database.getReference("Foods");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvFood.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryID = getIntent().getStringExtra("CategoryID");
        }

        if(categoryID != null && !categoryID.trim().equals("")){
            updateUI(food, categoryID);

        }


    }

    private void updateUI(DatabaseReference food, String categoryID) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.row_food,
                FoodViewHolder.class,
                food.orderByChild("MenuId").equalTo(categoryID)) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.tvFoodName.setText(model.getName());
                Picasso.with(FoodList.this).load(model.getImage()).into(viewHolder.ivFood);
                final Food foodModel = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this, foodModel.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FoodList.this, FoodDetails.class);
                        intent.putExtra("FoodID", adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };

        rvFood.setAdapter(adapter);
    }
}
