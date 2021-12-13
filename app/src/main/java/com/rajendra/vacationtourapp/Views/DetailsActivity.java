package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.MainActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.ViewModels.ImagesAdapter;
import com.rajendra.vacationtourapp.model.DAO;
import com.rajendra.vacationtourapp.model.HotelModel;
import com.rajendra.vacationtourapp.model.Location;
import com.rajendra.vacationtourapp.model.Photos;
import com.rajendra.vacationtourapp.model.PlaceModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;

public class DetailsActivity extends AppCompatActivity {

    private TextView txtName, txtAddress, txtDecreption, txtPrice;
    private ImageView img1, img2, img3;
    private Button btnFindLocation;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private ImagesAdapter imagesAdapter;
    private List<Photos> imagesList;
    private String userEmail, keys, id_data;
    private double latitude, longtitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setFindViewByID();

        Bundle getPlace = getIntent().getExtras();
        if (getPlace != null){
            userEmail = getPlace.getString("email");
            keys = getPlace.getString("keys");
            id_data = getPlace.getString("id_item");
            int id = Integer.parseInt(id_data);
            getDetailByID(keys, id);
        }

        btnFindLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //btnFindLocation.setText(latitude + "+" + longtitude);
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longtitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });


    }


    private void getDetailByID(String keys, int id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefPlace = database.getReference(keys);

            myRefPlace.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    imagesList = new ArrayList<>();
                    if (keys.equals("place")) {
                        List<PlaceModel> placeItem = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            //Log.e("onDataChangeDetails", dataSnapshot.getValue().toString());
                            PlaceModel place = dataSnapshot.getValue(PlaceModel.class);
                            placeItem.add(place);
                        }
                        latitude = placeItem.get(id).getLocation().getLatitude();
                        longtitude = placeItem.get(id).getLocation().getLongtitude();
                        imagesList.add(new Photos(placeItem.get(id).getImg_place().getImg1()));
                        imagesList.add(new Photos(placeItem.get(id).getImg_place().getImg2()));
                        imagesList.add(new Photos(placeItem.get(id).getImg_place().getImg3()));

                        Picasso.get().load(placeItem.get(id).getImg_place().getImg1()).into(img1);
                        Picasso.get().load(placeItem.get(id).getImg_place().getImg2()).into(img2);
                        Picasso.get().load(placeItem.get(id).getImg_place().getImg3()).into(img3);
                        txtName.setText(placeItem.get(id).getName_place());
                        txtAddress.setText(placeItem.get(id).getAddress_place());
                        txtDecreption.setText(placeItem.get(id).getDecription());
                    }
                    else if (keys.equals("hotel")){
                        List<HotelModel> hotelItem = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            HotelModel hotel = dataSnapshot.getValue(HotelModel.class);
                            hotelItem.add(hotel);
                        }

                        latitude = hotelItem.get(id).getLocation().getLatitude();
                        longtitude = hotelItem.get(id).getLocation().getLongtitude();

                        imagesList.add(new Photos(hotelItem.get(id).getImg_hotel().getImg1()));
                        imagesList.add(new Photos(hotelItem.get(id).getImg_hotel().getImg2()));
                        imagesList.add(new Photos(hotelItem.get(id).getImg_hotel().getImg3()));

                        Picasso.get().load(hotelItem.get(id).getImg_hotel().getImg1()).into(img1);
                        Picasso.get().load(hotelItem.get(id).getImg_hotel().getImg2()).into(img2);
                        Picasso.get().load(hotelItem.get(id).getImg_hotel().getImg3()).into(img3);
                        txtName.setText(hotelItem.get(id).getName_hotel());
                        txtAddress.setText(hotelItem.get(id).getAddress_hotel());
                        txtDecreption.setText(hotelItem.get(id).getDecription());
                        txtPrice.setText("Price: " + hotelItem.get(id).getPrice() + "đ");
                    }

                    imagesAdapter = new ImagesAdapter(DetailsActivity.this, imagesList);
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

        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_close_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);


        viewPager = findViewById(R.id.imgcenter);
        circleIndicator = findViewById(R.id.circle_center);

        img1 = findViewById(R.id.image_1);
        img2 = findViewById(R.id.image_2);
        img3 = findViewById(R.id.image_3);

        txtName = findViewById(R.id.txt_Name);
        txtAddress = findViewById(R.id.txt_address);
        txtDecreption  = findViewById(R.id.txt_decription);
        txtPrice = findViewById(R.id.txtPrice);
        btnFindLocation = findViewById(R.id.btnFindLocation);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
