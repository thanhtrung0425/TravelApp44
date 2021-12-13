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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.ViewModels.PlaceAdapter;
import com.rajendra.vacationtourapp.model.PlaceModel;

import java.util.ArrayList;
import java.util.List;

public class PlaceFragment extends Fragment {

    private RecyclerView placeRecycler;
    private PlaceAdapter placeAdapter;
    private List<PlaceModel> placeItem;
    private EditText txtSearch;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.place_fragment,container ,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeRecycler = getView().findViewById(R.id.place_recycler);
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
                filterPlace(editable.toString());
            }
        });

        placeItem = new ArrayList<>();
        getListPlaceFromDataBase();
        placeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        placeAdapter = new PlaceAdapter(getContext(), placeItem);
        placeRecycler.setAdapter(placeAdapter);
        placeAdapter.notifyDataSetChanged();
    }

    private void filterPlace(String text) {

        List<PlaceModel> filterList = new ArrayList<>();
        for (PlaceModel item: placeItem){
            if (item.getAddress_place().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        placeAdapter.filterListPlace(filterList);
    }

    private void getListPlaceFromDataBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefPlace = database.getReference("place");

        myRefPlace.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PlaceModel place = dataSnapshot.getValue(PlaceModel.class);
                    placeItem.add(place);
                }
                placeAdapter.setDataPlace(placeItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Get list Place falled", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
