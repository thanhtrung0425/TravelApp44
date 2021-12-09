package com.rajendra.vacationtourapp.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.adapter.PlaceAdapter;
import com.rajendra.vacationtourapp.model.PlaceModel;

import java.util.ArrayList;
import java.util.List;

public class PlaceFragment extends Fragment {

    private RecyclerView placeRecycler;
    private PlaceAdapter placeAdapter;
    private List<PlaceModel> placeItem;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.place_fragment,container ,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeRecycler = getView().findViewById(R.id.place_recycler);
        placeItem = new ArrayList<>();
        placeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        placeAdapter = new PlaceAdapter(getContext(), placeItem);
        placeRecycler.setAdapter(placeAdapter);
        getListPlaceFromDataBase();
    }

    private void getListPlaceFromDataBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("place");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PlaceModel place = dataSnapshot.getValue(PlaceModel.class);
                    placeItem.add(place);
                }
                placeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Get list Place falled", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
