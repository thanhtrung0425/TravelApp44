package com.rajendra.vacationtourapp.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.adapter.HotelAdapter;
import com.rajendra.vacationtourapp.adapter.PlaceAdapter;
import com.rajendra.vacationtourapp.model.HotelModel;
import com.rajendra.vacationtourapp.model.PlaceModel;

import java.util.ArrayList;
import java.util.List;

public class HotelFragment extends Fragment {

    private RecyclerView hotelRecycler;
    private HotelAdapter hotelAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.hotel_fragment,container ,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
