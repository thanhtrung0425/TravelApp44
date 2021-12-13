package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.MainActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.ViewModels.FoodAdapter;
import com.rajendra.vacationtourapp.ViewModels.ReviewAdapter;
import com.rajendra.vacationtourapp.model.FoodModel;
import com.rajendra.vacationtourapp.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private TextView txtRate, txtVotes;
    private ImageView imgbackToMain;
    private RecyclerView recyclerReview;

    private String id_food, rateFood, votes;
    private List<Review> reviewItem;
    private ReviewAdapter reviewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Reviews");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        txtRate = findViewById(R.id.txtRate);
        txtVotes = findViewById(R.id.txtVote);
        recyclerReview = findViewById(R.id.recycler_review);

        Intent getFood = getIntent();
        if (getFood != null){
            id_food = getFood.getStringExtra("id_food");
            rateFood = getFood.getStringExtra("rate");
            votes = getFood.getStringExtra("votes");
            txtRate.setText(rateFood);
        }

        reviewItem = new ArrayList<>();
        getListReviewFromDataBase();
        recyclerReview.setLayoutManager(new LinearLayoutManager(ReviewActivity.this));
        reviewAdapter = new ReviewAdapter(ReviewActivity.this, reviewItem);
        recyclerReview.setAdapter(reviewAdapter);


    }

    private void getListReviewFromDataBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefHotel = database.getReference("review");


        myRefHotel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("id_food").getValue().toString().equals(id_food)){
                        Review rv = dataSnapshot.getValue(Review.class);
                        reviewItem.add(rv);
                    }
                }
                txtVotes.setText("based on " + reviewItem.size() + " reviews");
                reviewAdapter.setDataReview(reviewItem);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReviewActivity.this, "Get list Place falled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}