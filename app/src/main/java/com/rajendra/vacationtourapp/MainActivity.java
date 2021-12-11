package com.rajendra.vacationtourapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rajendra.vacationtourapp.ViewModels.SoftInputAssist;
import com.rajendra.vacationtourapp.Views.FavoriteFragment;
import com.rajendra.vacationtourapp.Views.HotelFragment;
import com.rajendra.vacationtourapp.Views.PlaceFragment;
import com.rajendra.vacationtourapp.Views.ProfileActivity;
import com.rajendra.vacationtourapp.Views.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private TextView txtAdd, txtPlace;
    private ImageView imgProfile;
    private String getEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPlace = findViewById(R.id.txtPlace);
        txtAdd = findViewById(R.id.addPlace);
        imgProfile = findViewById(R.id.imgProfile);


        Intent getLogin = getIntent();
        if (getLogin != null){
             getEmail= getLogin.getStringExtra("email");
             txtAdd.setText("" + getEmail);
        }


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfile = new Intent(MainActivity.this, ProfileActivity.class);
                toProfile.putExtra("email", getEmail);
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
                case R.id.home:
                    txtPlace.setText("PLACE");
                    fragment = new PlaceFragment();
                    loadFragment(fragment);
                    break;
                case R.id.hotel:
                    txtPlace.setText("HOTEL");
                    fragment = new HotelFragment();
                    loadFragment(fragment);
                    break;
                case R.id.favorite:
                    txtPlace.setText("FAVORITE");
                    fragment = new FavoriteFragment();
                    loadFragment(fragment);
                    break;
                case R.id.profile:
                    txtPlace.setText("PROFILE");
                    fragment = new ProfileFragment();
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
