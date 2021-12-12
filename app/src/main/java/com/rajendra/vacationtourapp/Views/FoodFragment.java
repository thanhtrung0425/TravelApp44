package com.rajendra.vacationtourapp.Views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.ViewModels.FoodAdapter;
import com.rajendra.vacationtourapp.model.FoodModel;
import com.rajendra.vacationtourapp.model.Review;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment {

    private RecyclerView foodRecycler;
    private EditText txtSearch;
    private FoodAdapter foodAdapter;
    private List<FoodModel> foodItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.food_fragment,container ,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodRecycler = getView().findViewById(R.id.food_recycler);
        txtSearch = getActivity().findViewById(R.id.editTextSearch);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterFood(editable.toString());
            }
        });

        foodItems = new ArrayList<>();
        getListFoodFromDataBase();
        foodRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        foodAdapter = new FoodAdapter(getContext(), foodItems);
        foodRecycler.setAdapter(foodAdapter);
    }

    private void getListFoodFromDataBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefHotel = database.getReference("food");

        myRefHotel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    FoodModel food = dataSnapshot.getValue(FoodModel.class);
                    foodItems.add(food);
                }
                foodAdapter.setDataHotel(foodItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Get list Place falled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterFood(String text) {
        List<FoodModel> filterList = new ArrayList<>();

        for (FoodModel item: foodItems){
            if (item.getName_food().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        foodAdapter.filterListFood(filterList);
    }
}
