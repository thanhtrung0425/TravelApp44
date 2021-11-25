package com.rajendra.vacationtourapp.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.rajendra.vacationtourapp.MainActivity;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.adapter.PlaceAdapter;
import com.rajendra.vacationtourapp.model.PlaceModel;

import java.util.ArrayList;
import java.util.List;

public class PlaceFragment extends Fragment {

    private RecyclerView placeRecycler;


    private PlaceAdapter placeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.place_fragment,container ,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ExDataPlace();
    }

    private void ExDataPlace(){
        List<PlaceModel> placeModelList = new ArrayList<>();
        placeModelList.add(new PlaceModel("Kinh thành Huế","TP Huế","",R.drawable.img_hue));
        placeModelList.add(new PlaceModel("Bãi bụt", "Đà Nẵng","", R.drawable.img_baibut));
        placeModelList.add(new PlaceModel("Cầu rồng", "Đà Nẵng", "", R.drawable.img_caurong));
        placeModelList.add(new PlaceModel("Chùa thiên mụ", "TP Huế", "", R.drawable.img_chuathienmu));
        placeModelList.add(new PlaceModel("Cầu tràng tiền","TP Huế", "", R.drawable.img_cautrangtien));
        placeModelList.add(new PlaceModel("Đèo hải vân", "Đà Nẵng", "", R.drawable.img_deohaivan));
        placeModelList.add(new PlaceModel("Đỉnh bàn cờ", "Đà Nẵng", "", R.drawable.img_dinhbanco));
        setPlaceRecycler(placeModelList);
    }

    private void setPlaceRecycler(List<PlaceModel> placeModelList){
        placeRecycler = getView().findViewById(R.id.place_recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        placeRecycler.setLayoutManager(layoutManager);
        placeAdapter = new PlaceAdapter(getContext(), placeModelList);
        placeRecycler.setAdapter(placeAdapter);
    }

}
