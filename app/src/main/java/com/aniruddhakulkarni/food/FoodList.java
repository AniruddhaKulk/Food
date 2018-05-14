package com.aniruddhakulkarni.food;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.aniruddhakulkarni.food.common.ItemClickListener;
import com.aniruddhakulkarni.food.model.Food;
import com.aniruddhakulkarni.food.viewholder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    private RecyclerView rvFood;
    private String categoryID;
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    private MaterialSearchBar searchBar;
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    List<String> suggestionList = new ArrayList<>();
    private DatabaseReference food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        rvFood = findViewById(R.id.rv_food);
        searchBar = findViewById(R.id.search_bar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        food = database.getReference("Foods");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvFood.setLayoutManager(layoutManager);

        if(getIntent() != null){
            categoryID = getIntent().getStringExtra("CategoryID");
        }

        if(categoryID != null && !categoryID.trim().equals("")){
            updateUI(food, categoryID);

        }

        loadSuggestions(food);
        searchBar.setLastSuggestions(suggestionList);
        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<>();
                for(String search : suggestionList){
                    if(search.toLowerCase().contains(charSequence.toString().toLowerCase())){
                        suggest.add(search);
                    }
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    rvFood.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.row_food, FoodViewHolder.class,
                food.orderByChild("Name").equalTo(text.toString())) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.tvFoodName.setText(model.getName());
                Picasso.with(FoodList.this).load(model.getImage()).into(viewHolder.ivFood);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(FoodList.this, foodModel.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FoodList.this, FoodDetails.class);
                        intent.putExtra("FoodID", searchAdapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };

        rvFood.setAdapter(searchAdapter);
    }

    private void loadSuggestions(DatabaseReference food) {
        food.orderByChild("MenuId").equalTo(categoryID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Food item = postSnapshot.getValue(Food.class);
                    suggestionList.add(item.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
