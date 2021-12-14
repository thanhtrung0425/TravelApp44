package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private String keys, id_data;
    private double latitude, longtitude;
    private static final String img_notFound = "https://www.semtek.com.vn/file/2021/01/loi-404-not-found-3-768x590.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setFindViewByID();

        Bundle getPlace = getIntent().getExtras();
        if (getPlace != null){
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

        myRefPlace.child(String.valueOf(id)).get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebaseFailed", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebaseSuccess", String.valueOf(task.getResult().getValue()));
                    DataSnapshot dataSnapshot = task.getResult();
                    imagesList = new ArrayList<>();
                    if(keys.equals("place")){
                        PlaceModel pItem = dataSnapshot.getValue(PlaceModel.class);

                        latitude = pItem.getLocation().getLatitude();
                        longtitude = pItem.getLocation().getLongtitude();

                        if (pItem.getImg_place().getImg1() != null && !pItem.getImg_place().getImg1().isEmpty()) {
                            Picasso.get().load(pItem.getImg_place().getImg1()).into(img1);
                            imagesList.add(new Photos(pItem.getImg_place().getImg1()));
                        }
                        else {
                            img1.setImageResource(R.drawable.img_not_found);
                            imagesList.add(new Photos(img_notFound));

                        }
                        if (pItem.getImg_place().getImg2() != null && !pItem.getImg_place().getImg2().isEmpty()) {
                            Picasso.get().load(pItem.getImg_place().getImg2()).into(img2);
                            imagesList.add(new Photos(pItem.getImg_place().getImg2()));
                        }
                        else{
                            img2.setImageResource(R.drawable.img_not_found);
                            imagesList.add(new Photos(img_notFound));
                        }

                        if (pItem.getImg_place().getImg3() != null && !pItem.getImg_place().getImg3().isEmpty()) {
                            Picasso.get().load(pItem.getImg_place().getImg3()).into(img3);
                            imagesList.add(new Photos(pItem.getImg_place().getImg3()));
                        }
                        else{
                            img3.setImageResource(R.drawable.img_not_found);
                            imagesList.add(new Photos(img_notFound));
                        }



                        txtName.setText(pItem.getName_place());
                        txtAddress.setText(pItem.getAddress_place());
                        txtDecreption.setText(pItem.getDecription());
                    }
                    else if(keys.equals("hotel")){
                        HotelModel hItem = dataSnapshot.getValue(HotelModel.class);

                        latitude = hItem.getLocation().getLatitude();
                        longtitude = hItem.getLocation().getLongtitude();





                        if (hItem.getImg_hotel().getImg1() != null && !hItem.getImg_hotel().getImg1().isEmpty()) {
                            Picasso.get().load(hItem.getImg_hotel().getImg1()).into(img1);
                            imagesList.add(new Photos(hItem.getImg_hotel().getImg1()));
                        }else {
                            img1.setImageResource(R.drawable.img_not_found);
                            imagesList.add(new Photos(img_notFound));
                        }

                        if (hItem.getImg_hotel().getImg2() != null && !hItem.getImg_hotel().getImg2().isEmpty()){
                            Picasso.get().load(hItem.getImg_hotel().getImg2()).into(img2);
                            imagesList.add(new Photos(hItem.getImg_hotel().getImg2()));
                        }else {
                            img2.setImageResource(R.drawable.img_not_found);
                            imagesList.add(new Photos(img_notFound));
                        }

                        if (hItem.getImg_hotel().getImg3() != null && !hItem.getImg_hotel().getImg3().isEmpty()){
                            Picasso.get().load(hItem.getImg_hotel().getImg3()).into(img3);
                            imagesList.add(new Photos(hItem.getImg_hotel().getImg3()));
                        }else {
                            img3.setImageResource(R.drawable.img_not_found);
                            imagesList.add(new Photos(img_notFound));
                        }

                        txtName.setText(hItem.getName_hotel());
                        txtAddress.setText(hItem.getAddress_hotel());
                        txtDecreption.setText(hItem.getDecription());
                        txtPrice.setText("Price: " + hItem.getPrice() + "đ");
                    }

                    imagesAdapter = new ImagesAdapter(DetailsActivity.this, imagesList);
                    viewPager.setAdapter(imagesAdapter);
                    circleIndicator.setViewPager(viewPager);
                    imagesAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = firebaseUser.getEmail();
        if (userEmail.equals("admin@gmail.com")){
            inflater.inflate(R.menu.menu_details, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.delete:
                RemoveData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void RemoveData() {
        DAO dao = new DAO(keys);
        dao.remove(id_data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Delete failed", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
