package com.aniruddhakulkarni.food;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aniruddhakulkarni.food.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import info.hoang8f.widget.FButton;

public class SignUp extends AppCompatActivity {

    private MaterialEditText edPhone;
    private MaterialEditText edName;
    private MaterialEditText edPassword;
    private FButton btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userTable = database.getReference("User");

        edName = findViewById(R.id.ed_name);
        edPhone = findViewById(R.id.ed_phone);
        edPassword = findViewById(R.id.ed_password);
        btnSignUp = findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                progressDialog.setMessage("Please wait.....");
                progressDialog.show();

                userTable.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        if(dataSnapshot.child(edPhone.getText().toString()).exists()){
                            Toast.makeText(SignUp.this, "Phone number already exists", Toast.LENGTH_SHORT).show();
                        }else {
                            User user = new User(edName.getText().toString(), edPassword.getText().toString());
                            userTable.child(edPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "User successfully registered", Toast.LENGTH_SHORT).show();
                            finish();
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
