package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.PlaceModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsPlaces extends AppCompatActivity {

    private TextView txtNamePlace, txtAddressPlace, txtDecreption;
    private ImageView imgCenter, img1, img2, img3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_place);

        setFindViewByID();
        txtDecreption.setMovementMethod(new ScrollingMovementMethod());

        Bundle getPlace = getIntent().getExtras();
        if (getPlace != null){
            String data = getPlace.getString("id_place");
            int id = Integer.parseInt(data);
            getDetailPlaceByID(id);
        }

    }

    private void getDetailPlaceByID(int id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefPlace = database.getReference("place");

        myRefPlace.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PlaceModel> placeItem = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Log.e("onDataChangeDetails", dataSnapshot.getValue().toString());
                    PlaceModel place = dataSnapshot.getValue(PlaceModel.class);
                    placeItem.add(place);
                }
                Picasso.get().load(placeItem.get(id).getImg_place().getImg1()).into(imgCenter);
                Picasso.get().load(placeItem.get(id).getImg_place().getImg1()).into(img1);
                Picasso.get().load(placeItem.get(id).getImg_place().getImg2()).into(img2);
                Picasso.get().load(placeItem.get(id).getImg_place().getImg3()).into(img3);
                txtNamePlace.setText(placeItem.get(id).getName_place());
                txtAddressPlace.setText(placeItem.get(id).getAddress_place());
                txtDecreption.setText(placeItem.get(id).getDecription());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setFindViewByID(){

        imgCenter = findViewById(R.id.imgcenter);
        img1 = findViewById(R.id.image_1);
        img2 = findViewById(R.id.image_2);
        img3 = findViewById(R.id.image_3);

        txtNamePlace = findViewById(R.id.txt_NamePlace);
        txtAddressPlace = findViewById(R.id.txt_addressPlace);
        txtDecreption  = findViewById(R.id.txt_decreptionPlace);
        txtDecreption.setMovementMethod(LinkMovementMethod.getInstance());
    }


}
