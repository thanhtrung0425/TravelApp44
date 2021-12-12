package com.rajendra.vacationtourapp.model;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DAO {

    private DatabaseReference databaseReference;
    public DAO(String key){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        if (key.equals("place"))
            databaseReference = db.getReference(key);
        else if (key.equals("hotel"))
            databaseReference = db.getReference(key);
        else if (key.equals("food"))
            databaseReference = db.getReference(key);
    }

    public Task<Void> add(PlaceModel place, String idItem){
//        if (place == null)
        return databaseReference.child(idItem).setValue(place);
    }

    public Task<Void> add(HotelModel hotel, String idItem){
//        if (place == null)
        return databaseReference.child(idItem).setValue(hotel);
    }

    public Task<Void> add(FoodModel food, String idItem){
//        if (place == null)
        return databaseReference.child(idItem).setValue(food);
    }

    public Task<Void> update(PlaceModel place, String idItem, HashMap<String, Object> hashMap){
//        if (place == null)
        return databaseReference.child(idItem).updateChildren(hashMap);
    }

    public Task<Void> update(HotelModel hotel, String idItem, HashMap<String, Object> hashMap){
//        if (place == null)
        return databaseReference.child(idItem).updateChildren(hashMap);
    }

    public Task<Void> update(FoodModel food, String idItem, HashMap<String, Object> hashMap){
//        if (place == null)
        return databaseReference.child(idItem).updateChildren(hashMap);
    }

}
