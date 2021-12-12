package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.MainActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.DAO;
import com.rajendra.vacationtourapp.model.HotelModel;
import com.rajendra.vacationtourapp.model.Images;
import com.rajendra.vacationtourapp.model.Location;
import com.rajendra.vacationtourapp.model.PlaceModel;

import java.util.HashMap;

public class AddDataActivity extends AppCompatActivity {

    private TextView tvPrice;
    private EditText edtInsertID, edtInsertName, edtInsertAddress, edtInsertIMG1, edtInsertIMG2, edtInsertIMG3;
    private EditText edtInsertDecription, edtInsertLatitude, edtInsertLongtitude, edtInsertPrice;
    private Button btnInsert;
    private String key;
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
        if (getData != null){
            key = getData.getStringExtra("key");
        }

        setFindViewByID();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idItem = edtInsertID.getText().toString();
                CheckIDinDatabase(key, idItem);
            }
        });
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
            dao.update(hotel, idItem, hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AddDataActivity.this, "INSERT success", Toast.LENGTH_SHORT).show();
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
                hashMap.put("name_hotel", name);
                hashMap.put("address_hotel", address);
                hashMap.put("decription", decription);
                hashMap.put("price", price);
                hashMap.put("img_place", images);
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
        builder.setMessage("The ID already exist. Do you want update?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddDatatoModel(keys);
                DAO dao = new DAO(keys);
                if (keys.equals("place")){
                    dao.update(place, idItem, hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AddDataActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddDataActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddDataActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                if (key.equals("hotel")){
                    dao.update(hotel, idItem, hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AddDataActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddDataActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddDataActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(AddDataActivity.this, "Please change ID to INSERT!", Toast.LENGTH_SHORT).show();
                //dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void setFindViewByID(){

        btnInsert = findViewById(R.id.btnInsert);
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
                !name.isEmpty() && !address.isEmpty() && !img1.isEmpty() && !img2.isEmpty() && !img3.isEmpty() &&
                !decription.isEmpty() && !latitude.isEmpty() && !longtitude.isEmpty()){
                return true;
            }
            return false;
    }
}