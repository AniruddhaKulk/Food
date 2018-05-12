package com.aniruddhakulkarni.food;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniruddhakulkarni.food.model.Food;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {

    private TextView tvFoodName;
    private TextView tvDescription;
    private ImageView imageView;
    private ElegantNumberButton numberButton;
    private TextView tvPrice;
    private FloatingActionButton fab;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference foods = database.getReference("Foods");

        tvFoodName = findViewById(R.id.tv_food_name);
        tvDescription = findViewById(R.id.tv_description);
        tvPrice = findViewById(R.id.tv_food_price);
        imageView = findViewById(R.id.iv_detail);
        numberButton = findViewById(R.id.number_btn);
        fab = findViewById(R.id.fab);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if(getIntent() != null){
            String foodID = getIntent().getStringExtra("FoodID");
            
            if(!foodID.trim().equals("")){
                updateUI(foods, foodID);
            }
        }
    }

    private void updateUI(DatabaseReference foods, final String foodID) {
        foods.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);
                Picasso.with(FoodDetails.this).load(food.getImage()).into(imageView);
                collapsingToolbarLayout.setTitle(food.getName());
                tvFoodName.setText(food.getName());
                tvDescription.setText(food.getDescription());
                tvPrice.setText(food.getPrice());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
