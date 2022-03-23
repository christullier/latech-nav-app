package com.example.navapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.core.View;

public class AccountActivity extends AppCompatActivity {
    TextView username, email;
    Button log_out;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    //String userID;
    SharedPreferences sharedPreferences;
    public static final String Username = "username";
    public static final String pass_wrd = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        username = findViewById(R.id.profilename);
        email = findViewById(R.id.profileemail);
        log_out = findViewById(R.id.sign_out);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //userID = fAuth.getCurrentUser().getUid();
        sharedPreferences = getApplicationContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("username", "");

        DocumentReference documentReference = fStore.collection("user_profile").document(name);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                username.setText(documentSnapshot.getString("username"));
                email.setText(documentSnapshot.getString("email"));
            }
        });

        log_out.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            }
        });
    }



}