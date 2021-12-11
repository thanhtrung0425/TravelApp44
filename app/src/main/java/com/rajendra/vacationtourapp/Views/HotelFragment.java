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
import com.rajendra.vacationtourapp.ViewModels.HotelAdapter;
import com.rajendra.vacationtourapp.model.HotelModel;

import java.util.ArrayList;
import java.util.List;

public class HotelFragment extends Fragment {

    private RecyclerView hotelRecycler;
    private EditText txtSearch;
    private HotelAdapter hotelAdapter;
    private List<HotelModel> hotelItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hotel_fragment,container ,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hotelRecycler = getView().findViewById(R.id.hotelRecycler);
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
                filterHotel(editable.toString());
            }
        });

        hotelItem = new ArrayList<>();
        getListHotelFromDataBase();
        hotelRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        hotelAdapter = new HotelAdapter(getContext(), hotelItem);
        hotelRecycler.setAdapter(hotelAdapter);
    }

    private void filterHotel(String text) {
        List<HotelModel> filterList = new ArrayList<>();

        for (HotelModel item: hotelItem){
            if (item.getName_hotel().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        hotelAdapter.filterListHotel(filterList);
    }

    private void getListHotelFromDataBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefHotel = database.getReference("hotel");

        myRefHotel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    HotelModel hotel = dataSnapshot.getValue(HotelModel.class);
                    hotelItem.add(hotel);
                }
                hotelAdapter.setDataHotel(hotelItem);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Get list Place falled", Toast.LENGTH_SHORT).show();

            }
        });


    }


}
