package com.rajendra.vacationtourapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.Views.FoodFragment;
import com.rajendra.vacationtourapp.Views.HotelFragment;
import com.rajendra.vacationtourapp.Views.PlaceFragment;
import com.rajendra.vacationtourapp.Views.ProfileActivity;
import com.rajendra.vacationtourapp.Views.ReviewActivity;
import com.rajendra.vacationtourapp.model.Review;
import com.rajendra.vacationtourapp.model.User;

public class MainActivity extends AppCompatActivity {

    private TextView txtAdd, txtPlace;
    private ImageView imgProfile;
    private String getEmail;
    private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPlace = findViewById(R.id.txtPlace);
        txtAdd = findViewById(R.id.addPlace);
        imgProfile = findViewById(R.id.imgProfile);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        getEmail = firebaseUser.getEmail();


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(toProfile);
            }
        });


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framefragment, new PlaceFragment())
                .commit();

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(navListener);


    }



    private BottomNavigationView.OnNavigationItemSelectedListener
                navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId())
            {
                case R.id.hotel:
                    txtPlace.setText("HOTEL");
                    fragment = new HotelFragment();
                    loadFragment(fragment);
                    break;
                case R.id.home:
                    txtPlace.setText("PLACE");
                    fragment = new PlaceFragment();
                    loadFragment(fragment);
                    break;
                case R.id.food:
                    txtPlace.setText("FOOD");
                    fragment = new FoodFragment();
                    loadFragment(fragment);
                    break;
            }
            return true;
        }
    };

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framefragment, fragment)
                .commit();
    }

}
