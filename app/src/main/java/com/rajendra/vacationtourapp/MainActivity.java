package com.rajendra.vacationtourapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rajendra.vacationtourapp.Views.FavoriteFragment;
import com.rajendra.vacationtourapp.Views.HotelFragment;
import com.rajendra.vacationtourapp.Views.PlaceFragment;
import com.rajendra.vacationtourapp.Views.ProfileFragment;
import com.rajendra.vacationtourapp.adapter.PlaceAdapter;
import com.rajendra.vacationtourapp.model.PlaceModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recentRecycler;
    PlaceAdapter placeAdapter;

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

//
//    private  void setPlaceRecycler(List<PlaceModel> placeModelList){
//        recentRecycler = findViewById(R.id.recent_recycler);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
//        recentRecycler.setLayoutManager(layoutManager);
//        placeAdapter = new PlaceAdapter(this, placeModelList);
//        recentRecycler.setAdapter(placeAdapter);
//    }
//
//    private void ExData(){
//        List<PlaceModel> placeModelList = new ArrayList<>();
//        placeModelList.add(new PlaceModel("Kinh thành Huế","TP Huế","",R.drawable.img_hue));
//        placeModelList.add(new PlaceModel("Bãi bụt", "Đà Nẵng","", R.drawable.img_baibut));
//        placeModelList.add(new PlaceModel("Cầu rồng", "Đà Nẵng", "", R.drawable.img_caurong));
//        placeModelList.add(new PlaceModel("Chùa thiên mụ", "TP Huế", "", R.drawable.img_chuathienmu));
//        placeModelList.add(new PlaceModel("Cầu tràng tiền","TP Huế", "", R.drawable.img_cautrangtien));
//        placeModelList.add(new PlaceModel("Đèo hải vân", "Đà Nẵng", "", R.drawable.img_deohaivan));
//        placeModelList.add(new PlaceModel("Đỉnh bàn cờ", "Đà Nẵng", "", R.drawable.img_dinhbanco));
//        setPlaceRecycler(placeModelList);
//    }


}
