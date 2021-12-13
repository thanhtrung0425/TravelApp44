package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.MainActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.DAO;
import com.rajendra.vacationtourapp.model.FoodModel;
import com.rajendra.vacationtourapp.model.HotelModel;
import com.rajendra.vacationtourapp.model.Images;
import com.rajendra.vacationtourapp.model.Location;
import com.rajendra.vacationtourapp.model.PlaceModel;

import java.util.HashMap;

public class AddDataActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvPrice;
    private EditText edtInsertID, edtInsertName, edtInsertAddress, edtInsertIMG1, edtInsertIMG2, edtInsertIMG3;
    private EditText edtInsertDecription, edtInsertLatitude, edtInsertLongtitude, edtInsertPrice;
    private Button btnInsert, btnShowData, btnUpdate;
    private String key, job;
    private PlaceModel place;
    private HotelModel hotel;
    private Images images;
    private Location location;
    private HashMap<String, Object> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);




        Intent getData = getIntent();
        key = getData.getStringExtra("key");
        job = getData.getStringExtra("job");
        setFindViewByID();
        if (job.equals("update")){
            btnInsert.setVisibility(View.INVISIBLE);
            btnShowData.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
        }


        btnInsert.setOnClickListener(this::onClick);
        btnShowData.setOnClickListener(this::onClick);
        btnUpdate.setOnClickListener(this::onClick);
    }

    private void CheckIDinDatabase(String keys, String idItem){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference(keys);
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (CheckData()){
                    if (snapshot.hasChild(idItem)){
                        CheckAnswer(keys, idItem);
                    }else {
                        AddDatatoModel(keys);
                        PushDataToRealtimeDataBase(keys, idItem);
                    }
                }else
                    Toast.makeText(AddDataActivity.this, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddDataActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PushDataToRealtimeDataBase(String keys, String idItem){
        DAO dao =  new DAO(keys);
        if (keys.equals("place")){
            dao.add(place, idItem).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AddDataActivity.this, "INSERT success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddDataActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddDataActivity.this, "INSERT failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (keys.equals("hotel")){
            dao.update(idItem, hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AddDataActivity.this, "INSERT success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddDataActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddDataActivity.this, "INSERT failed", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void AddDatatoModel(String keyModel){
        try {

            int id = Integer.parseInt(edtInsertID.getText().toString());
        String name = edtInsertName.getText().toString();
        String address = edtInsertAddress.getText().toString();
        String img1 = edtInsertIMG1.getText().toString();
        String img2 = edtInsertIMG2.getText().toString();
        String img3 = edtInsertIMG3.getText().toString();
        String decription = edtInsertDecription.getText().toString();
        String latitude = edtInsertLatitude.getText().toString();
        String longtitude = edtInsertLongtitude.getText().toString();

        images = new Images(img1, img2, img3);
        location = new Location(Double.parseDouble(latitude), Double.parseDouble(longtitude));



        if (keyModel.equals("place")) {
            place = new PlaceModel(id, name, address, decription, images, location);
            hashMap = new HashMap<>();
            hashMap.put("id_place", id);
            hashMap.put("name_place", name);
            hashMap.put("address_place", address);
            hashMap.put("decription", decription);
            hashMap.put("img_place", images);
            hashMap.put("location", location);

        } else if (keyModel.equals("hotel")) {

            if (!edtInsertPrice.getText().toString().isEmpty()){
                int price = Integer.parseInt(edtInsertPrice.getText().toString());
                hotel = new HotelModel(id, name, address, decription, price, images, location);
                hashMap = new HashMap<>();
                hashMap.put("id_hotel", id);
                hashMap.put("name_hotel", name);
                hashMap.put("address_hotel", address);
                hashMap.put("decription", decription);
                hashMap.put("price", price);
                hashMap.put("img_hotel", images);
                hashMap.put("location", location);
            }
            else
                Toast.makeText(AddDataActivity.this, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
        }
        }catch (Exception e) {
            Toast.makeText(AddDataActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void CheckAnswer(String keys, String idItem){

        AlertDialog.Builder builder = new AlertDialog.Builder(AddDataActivity.this);
        builder.setTitle("WARING!");
        builder.setMessage("The ID already exist. Please change ID ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();
    }

    private void setFindViewByID(){


        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_close_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        btnShowData = findViewById(R.id.btnShowData);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnInsert = findViewById(R.id.btnInsertData);
        tvPrice = findViewById(R.id.tvPrice);
        edtInsertID = findViewById(R.id.edtInsertId);
        edtInsertName = findViewById(R.id.edtInsertname);
        edtInsertAddress = findViewById(R.id.edtInsertAddress);
        edtInsertIMG1 = findViewById(R.id.edtInsertImage1);
        edtInsertIMG2 = findViewById(R.id.edtInsertImage2);
        edtInsertIMG3 = findViewById(R.id.edtInsertImage3);
        edtInsertDecription = findViewById(R.id.edtInsertDecription);
        edtInsertLatitude = findViewById(R.id.edtInsertLatitude);
        edtInsertLongtitude = findViewById(R.id.edtInsertLongtitude);
        edtInsertPrice = findViewById(R.id.edtInsertPrice);
        if (key.equals("hotel")){
            tvPrice.setVisibility(View.VISIBLE);
            edtInsertPrice.setVisibility(View.VISIBLE);
        }

    }

    private boolean CheckData(){
            String name = edtInsertName.getText().toString();
            String address = edtInsertAddress.getText().toString();
            String img1 = edtInsertIMG1.getText().toString();
            String img2 = edtInsertIMG2.getText().toString();
            String img3 = edtInsertIMG3.getText().toString();
            String decription = edtInsertDecription.getText().toString();
            String latitude = edtInsertLatitude.getText().toString();
            String longtitude = edtInsertLongtitude.getText().toString();
            if (!edtInsertID.getText().toString().isEmpty() &&
                !name.isEmpty() && !address.isEmpty() && !img1.isEmpty() &&
                !decription.isEmpty() && !latitude.isEmpty() && !longtitude.isEmpty()){
                return true;
            }
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        String idItem = edtInsertID.getText().toString().trim();
        if (view.getId() == R.id.btnInsertData){
            CheckIDinDatabase(key, idItem);
        }
        if (view.getId() == R.id.btnShowData){
            if(!idItem.isEmpty())
                ShowDataByID(idItem, key);
            else
                Toast.makeText(getApplicationContext(), "Please fill id to get DATA", Toast.LENGTH_SHORT).show();

        }
        if (view.getId() == R.id.btnUpdate){
            if (CheckData()){
                UpdateData(idItem, key);
            }
            else
                Toast.makeText(getApplicationContext(), "Please show data before update", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdateData(String idItem, String key) {
        AddDatatoModel(key);
        DAO dao = new DAO(key);
        dao.update(idItem, hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                        edtInsertName.setText("");
                        edtInsertAddress.setText("");
                        edtInsertIMG1.setText("");
                        edtInsertIMG2.setText("");
                        edtInsertIMG3.setText("");
                        edtInsertDecription.setText("");
                        edtInsertLatitude.setText("");
                        edtInsertLongtitude.setText("");
                        edtInsertPrice.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddDataActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void ShowDataByID(String idItem, String key) {
        FirebaseDatabase Database = FirebaseDatabase.getInstance();
        DatabaseReference nameRef = Database.getReference(key);
        nameRef.child(idItem).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebaseFailed: ", "Error getting data", task.getException());
                }
                else {
                    Log.e("firebaseSuccess: ", String.valueOf(task.getResult().getValue()));
                    DataSnapshot dataSnapshot = task.getResult();
                    if (key.equals("place")) {
                        PlaceModel placeModel = dataSnapshot.getValue(PlaceModel.class);
                        edtInsertName.setText(placeModel.getName_place());
                        edtInsertAddress.setText(placeModel.getAddress_place());
                        edtInsertIMG1.setText(placeModel.getImg_place().getImg1());
                        edtInsertIMG2.setText(placeModel.getImg_place().getImg2());
                        edtInsertIMG3.setText(placeModel.getImg_place().getImg3());
                        edtInsertDecription.setText(placeModel.getDecription());
                        edtInsertLatitude.setText(placeModel.getLocation().getLatitude() + "");
                        edtInsertLongtitude.setText(placeModel.getLocation().getLongtitude() + "");
                    }
                    else if (key.equals("hotel")){
                        HotelModel hotelModel = dataSnapshot.getValue(HotelModel.class);
                        edtInsertName.setText(hotelModel.getName_hotel());
                        edtInsertAddress.setText(hotelModel.getAddress_hotel());
                        edtInsertIMG1.setText(hotelModel.getImg_hotel().getImg1());
                        edtInsertIMG2.setText(hotelModel.getImg_hotel().getImg2());
                        edtInsertIMG3.setText(hotelModel.getImg_hotel().getImg3());
                        edtInsertDecription.setText(hotelModel.getDecription());
                        edtInsertLatitude.setText(hotelModel.getLocation().getLatitude() + "");
                        edtInsertLongtitude.setText(hotelModel.getLocation().getLongtitude() + "");
                        edtInsertPrice.setText(hotelModel.getPrice() + "");
                    }
                }
            }
        });
    }
}