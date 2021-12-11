package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.MainActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.ViewModels.ImagesAdapter;
import com.rajendra.vacationtourapp.model.HotelModel;
import com.rajendra.vacationtourapp.model.Photos;
import com.rajendra.vacationtourapp.model.PlaceModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailsPlaces extends AppCompatActivity {

    private TextView txtNamePlace, txtAddressPlace, txtDecreption, txtPrice;
    private ImageView imgBack, img1, img2, img3;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private ImagesAdapter imagesAdapter;
    private List<Photos> imagesList;
    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_place);

        setFindViewByID();

        Bundle getPlace = getIntent().getExtras();
        if (getPlace != null){
            userEmail = getPlace.getString("email");
            String keys = getPlace.getString("keys");
            String id_data = getPlace.getString("id_item");
            int id = Integer.parseInt(id_data);
            getDetailPlaceByID(keys, id);
        }

    }


    private void getDetailPlaceByID(String keys, int id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefPlace = database.getReference(keys);

            myRefPlace.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    imagesList = new ArrayList<>();
                    if (keys.equals("place")) {
                        List<PlaceModel> placeItem = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Log.e("onDataChangeDetails", dataSnapshot.getValue().toString());
                            PlaceModel place = dataSnapshot.getValue(PlaceModel.class);
                            placeItem.add(place);
                        }
                        imagesList.add(new Photos(placeItem.get(id).getImg_place().getImg1()));
                        imagesList.add(new Photos(placeItem.get(id).getImg_place().getImg2()));
                        imagesList.add(new Photos(placeItem.get(id).getImg_place().getImg3()));

                        Picasso.get().load(placeItem.get(id).getImg_place().getImg1()).into(img1);
                        Picasso.get().load(placeItem.get(id).getImg_place().getImg2()).into(img2);
                        Picasso.get().load(placeItem.get(id).getImg_place().getImg3()).into(img3);
                        txtNamePlace.setText(placeItem.get(id).getName_place());
                        txtAddressPlace.setText(placeItem.get(id).getAddress_place());
                        txtDecreption.setText(placeItem.get(id).getDecription());
                    }
                    else if (keys.equals("hotel")){
                        List<HotelModel> hotelItem = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            HotelModel hotel = dataSnapshot.getValue(HotelModel.class);
                            hotelItem.add(hotel);
                        }
                        imagesList.add(new Photos(hotelItem.get(id).getImg_hotel().getImg1()));
                        imagesList.add(new Photos(hotelItem.get(id).getImg_hotel().getImg2()));
                        imagesList.add(new Photos(hotelItem.get(id).getImg_hotel().getImg3()));

                        Picasso.get().load(hotelItem.get(id).getImg_hotel().getImg1()).into(img1);
                        Picasso.get().load(hotelItem.get(id).getImg_hotel().getImg2()).into(img2);
                        Picasso.get().load(hotelItem.get(id).getImg_hotel().getImg3()).into(img3);
                        txtNamePlace.setText(hotelItem.get(id).getName_hotel());
                        txtAddressPlace.setText(hotelItem.get(id).getAddress_hotel());
                        txtDecreption.setText(hotelItem.get(id).getDecription());
                        txtPrice.setText("Price: " + hotelItem.get(id).getPrice() + "đ");
                    }

                    imagesAdapter = new ImagesAdapter(DetailsPlaces.this, imagesList);
                    viewPager.setAdapter(imagesAdapter);
                    circleIndicator.setViewPager(viewPager);
                    imagesAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private void setFindViewByID(){

        viewPager = findViewById(R.id.imgcenter);
        circleIndicator = findViewById(R.id.circle_center);

        img1 = findViewById(R.id.image_1);
        img2 = findViewById(R.id.image_2);
        img3 = findViewById(R.id.image_3);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsPlaces.this, MainActivity.class);
                startActivity(intent);
            }
        });

        txtNamePlace = findViewById(R.id.txt_NamePlace);
        txtAddressPlace = findViewById(R.id.txt_addressPlace);
        txtDecreption  = findViewById(R.id.txt_decreptionPlace);
        txtPrice = findViewById(R.id.txtPrice);
    }

}
