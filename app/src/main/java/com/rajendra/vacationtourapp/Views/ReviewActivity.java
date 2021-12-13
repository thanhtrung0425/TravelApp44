package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.rajendra.vacationtourapp.ViewModels.FoodAdapter;
import com.rajendra.vacationtourapp.ViewModels.ReviewAdapter;
import com.rajendra.vacationtourapp.model.DAO;
import com.rajendra.vacationtourapp.model.FoodModel;
import com.rajendra.vacationtourapp.model.Review;
import com.rajendra.vacationtourapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReviewActivity extends AppCompatActivity {

    private TextView txtRate, txtVotes;
    private EditText edtReview;
    private ImageView imgSendReview;
    private RecyclerView recyclerReview;

    private String id_food, rateFood, votes;
    private List<Review> reviewItem;
    private ReviewAdapter reviewAdapter;
    private String user_name, img_user;
    private int id = 0;


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
        edtReview = findViewById(R.id.edtReview);
        imgSendReview = findViewById(R.id.imgSendReview);
        recyclerReview = findViewById(R.id.recycler_review);

        Intent getFood = getIntent();
        id_food = getFood.getStringExtra("id_food");
        rateFood = getFood.getStringExtra("rate");
        votes = getFood.getStringExtra("votes");
        txtRate.setText(rateFood);

        setIDReview();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        getNameImgPerson(firebaseUser.getEmail());

        reviewItem = new ArrayList<>();
        getListReviewFromDataBase();
        recyclerReview.setLayoutManager(new LinearLayoutManager(ReviewActivity.this));
        reviewAdapter = new ReviewAdapter(ReviewActivity.this, reviewItem);
        recyclerReview.setAdapter(reviewAdapter);


        imgSendReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review = edtReview.getText().toString().trim();
                sendReviewToDatabase(id_food, review);
            }
        });

    }

    private void sendReviewToDatabase(String id_food, String review) {
        reviewItem.clear();
        Review sendReview = new Review(id, Integer.parseInt(id_food), user_name, review, "", img_user);
        DAO dao = new DAO("review");
        dao.add(sendReview, id).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                edtReview.setText("");
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNameImgPerson(String email) {
        FirebaseDatabase Database = FirebaseDatabase.getInstance();
        DatabaseReference nameRef = Database.getReference("user");
        nameRef.child(getIdUser(getIdUser(email))).get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebaseFailed", "Error getting data", task.getException());
                        }
                        else {
                            Log.e("firebaseSuccess", String.valueOf(task.getResult().getValue()));
                            DataSnapshot dataSnapshot = task.getResult();
                            User user = dataSnapshot.getValue(User.class);
                            user_name = user.getUser_name();
                            img_user = user.getImg_avatar();

                        }
                    }
                });
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
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private String getIdUser(String getEmail){
        String key = "";
        for (int i = 0; i < getEmail.length(); i++){
            if (getEmail.charAt(i) != '@'){
                key += getEmail.charAt(i);
            }
            else
                break;
        }
        return key.trim();
    }

    private void setIDReview(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference getSize = firebaseDatabase.getReference("review");
        getSize.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Review> size = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Review rv = dataSnapshot.getValue(Review.class);
                    size.add(rv);
                }
                id = size.size() + 1;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}