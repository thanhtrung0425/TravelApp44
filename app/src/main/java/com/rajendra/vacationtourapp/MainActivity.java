package com.rajendra.vacationtourapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rajendra.vacationtourapp.Views.FavoriteFragment;
import com.rajendra.vacationtourapp.Views.HotelFragment;
import com.rajendra.vacationtourapp.Views.PlaceFragment;
import com.rajendra.vacationtourapp.Views.ProfileFragment;
import com.rajendra.vacationtourapp.adapter.PlaceAdapter;

public class MainActivity extends AppCompatActivity {

    private TextView txtAdd, txtPlace;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPlace = findViewById(R.id.txtPlace);
        txtAdd = findViewById(R.id.addPlace);

        checkAdmin();

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

    private void checkAdmin() {
        GoogleSignInAccount googleSignInAccountAdmin = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccountAdmin != null) {
            if (googleSignInAccountAdmin.getEmail().trim().equals("thanhtrung250301@gmail.com"))
                txtAdd.setText("+");
        }
    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framefragment, fragment)
                .commit();
    }

}
