package com.aniruddhakulkarni.food;

import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aniruddhakulkarni.food.common.Common;
import com.aniruddhakulkarni.food.database.Database;
import com.aniruddhakulkarni.food.model.Order;
import com.aniruddhakulkarni.food.model.Request;
import com.aniruddhakulkarni.food.viewholder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    private RecyclerView rvCart;
    private FButton btnPlaceOrder;
    private TextView tvTotal;

    private List<Order> list = new ArrayList<>();
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference requestOrder = firebaseDatabase.getReference("Requests");

        rvCart = findViewById(R.id.rv_cart);
        btnPlaceOrder = findViewById(R.id.btn_place_order);
        tvTotal = findViewById(R.id.tv_total);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setHasFixedSize(true);
        //adapter = new CartAdapter(list);
        //rvCart.setAdapter(adapter);

        updateUI(requestOrder);

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(requestOrder);
            }
        });
    }

    private void showAlertDialog(final DatabaseReference requestOrder){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Where to deliver?");
        alertDialog.setMessage("Please enter the delivery address");

        final EditText editText = new EditText(Cart.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editText.setLayoutParams(params);
        alertDialog.setView(editText);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        editText.getText().toString(),
                        tvTotal.getText().toString(),
                        list

                );

                requestOrder.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                //Delete cart
                new Database(Cart.this).cleanCart();
                Toast.makeText(Cart.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void updateUI(DatabaseReference requestOrder) {
        list = new Database(Cart.this).getCarts();
        adapter = new CartAdapter(list);
        rvCart.setAdapter(adapter);

        int total = 0;
        for(Order order : list){
            total += (Integer.parseInt(order.getPrice()) * (Integer.parseInt(order.getQuantity())));
        }
        Locale locale = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        tvTotal.setText(numberFormat.format(total));
    }
}
