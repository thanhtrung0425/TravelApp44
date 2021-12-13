package com.rajendra.vacationtourapp.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.rajendra.vacationtourapp.model.DAO;
import com.rajendra.vacationtourapp.model.FoodModel;
import com.rajendra.vacationtourapp.model.Images;

import java.util.HashMap;

public class AddFoodActivity extends AppCompatActivity {

    private EditText edtID, edtName, edtAddress, edtImg, edtPrice, edtRate;
    private Button btnInsert;
    private FoodModel food;
    private Images images;

    private HashMap<String, Object> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        setFindViewByID();


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtID.getText().toString();
                CheckIDinDatabase(id);
            }
        });


    }

    private void CheckIDinDatabase(String idItem){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("food");
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (CheckData()){
                    AddDatatoModel();
                    if(snapshot.hasChild(idItem)){
                        CheckAnswer(idItem);
                    }else {
                        DAO dao = new DAO("food");
                        dao.add(food, idItem)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(AddFoodActivity.this, "INSERT success", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AddFoodActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddFoodActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }

                }
                else {
                    Toast.makeText(AddFoodActivity.this, "Fill all the information", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddFoodActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CheckAnswer(String idItem){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFoodActivity.this);
        builder.setTitle("WARING!");
        builder.setMessage("The ID already exist. Do you want update?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DAO dao = new DAO("food");
                dao.update(food, idItem, hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddFoodActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddFoodActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddFoodActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(AddFoodActivity.this, "Please change ID to INSERT!", Toast.LENGTH_SHORT).show();
                //dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void setFindViewByID(){

        Drawable drawable = getResources().getDrawable(R.drawable.ic_baseline_close_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(drawable);

        edtID = findViewById(R.id.FedtInsertId);
        edtName = findViewById(R.id.FedtInsertname);
        edtAddress = findViewById(R.id.FedtInsertAddress);
        edtImg = findViewById(R.id.FedtInsertImage1);
        edtPrice = findViewById(R.id.FedtInsertPrice);
        edtRate = findViewById(R.id.FedtInsertRate);
        btnInsert = findViewById(R.id.FbtnInsert);
    }

    private void AddDatatoModel(){
        try {

            int id = Integer.parseInt(edtID.getText().toString());
            String name = edtName.getText().toString();
            String address = edtAddress.getText().toString();
            String Img = edtImg.getText().toString();
            int price = Integer.parseInt(edtPrice.getText().toString());
            String rate = edtRate.getText().toString();

            images = new Images(Img);
            food = new FoodModel(id, name, address, images, price, rate);

            hashMap = new HashMap<>();
            hashMap.put("name_food", name);
            hashMap.put("address_food", address);
            hashMap.put("img_food", images);
            hashMap.put("price", price);
            hashMap.put("rate", rate);

        }catch (Exception e){
            Toast.makeText(AddFoodActivity.this, "" + e.getMessage() , Toast.LENGTH_SHORT).show();
        }
    }

    private boolean CheckData() {
        int id = Integer.parseInt(edtID.getText().toString());
        String name = edtName.getText().toString();
        String address = edtAddress.getText().toString();
        String Img = edtImg.getText().toString();
        int price = Integer.parseInt(edtPrice.getText().toString());
        String rate = edtName.getText().toString();

        if (!edtID.getText().toString().isEmpty() && !name.isEmpty() && !address.isEmpty() && !Img.isEmpty() &&
                !edtPrice.getText().toString().isEmpty() && !rate.isEmpty()) {
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
}