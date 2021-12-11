package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.Login.LoginScreen;
import com.rajendra.vacationtourapp.MainActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.User;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtnameuser, txtCountry, txtEmail, txtPhone;
    private ImageView imgBackFromProfile;
    private Button btnLogout;
    private String emailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtnameuser = findViewById(R.id.txtUsername);
        txtCountry = findViewById(R.id.textCountry);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        btnLogout = findViewById(R.id.btnLogout);
        imgBackFromProfile = findViewById(R.id.imgBackFromProfile);

        Intent getProfile = getIntent();
        emailUser = getProfile.getStringExtra("email");
        txtEmail.setText(emailUser);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");
        Log.e("USERID", userRef.getKey());


        userRef.addValueEventListener(new ValueEventListener() {
            String username, email, country, phone;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot keyID : snapshot.getChildren()){
                    if(keyID.child("email").getValue().equals(emailUser)){
                        username = keyID.child("user_name").getValue(String.class);
                        email = keyID.child("email").getValue(String.class);
                        country = keyID.child("country").getValue(String.class);
                        phone = keyID.child("phone").getValue(String.class);
                        break;
                    }
                }
                txtnameuser.setText(username);
                txtCountry.setText(country);
                txtEmail.setText(email);
                txtPhone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error", "Failed to read value.", error.toException());
            }
        });

        imgBackFromProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });


    }

    public void logout(View view) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        Intent logoutIntent = new Intent(ProfileActivity.this, LoginScreen.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logoutIntent);
        finish();
    }
}