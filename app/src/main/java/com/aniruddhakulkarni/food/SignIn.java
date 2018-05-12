package com.aniruddhakulkarni.food;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aniruddhakulkarni.food.common.Common;
import com.aniruddhakulkarni.food.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class SignIn extends AppCompatActivity {

    private MaterialEditText edPhone;
    private MaterialEditText edPassword;
    private FButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edPassword = findViewById(R.id.ed_password);
        edPhone = findViewById(R.id.ed_phone);
        btnLogin = findViewById(R.id.btn_login);
        edPhone.setText("999");
        edPassword.setText("1234");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userTable = database.getReference("User");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog progressDialog = new ProgressDialog(SignIn.this);
                progressDialog.setMessage("Please wait.....");
                progressDialog.show();

                userTable.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        if (dataSnapshot.child(edPhone.getText().toString()).exists()) {
                            User user = dataSnapshot.child(edPhone.getText().toString()).getValue(User.class);

                            if (user.getPassword().equals(edPassword.getText().toString())) {
                                //Toast.makeText(SignIn.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Common.currentUser = user;
                                startActivity(new Intent(SignIn.this, Home.class));
                                finish();
                            }
                        }else {
                            Toast.makeText(SignIn.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }
}
