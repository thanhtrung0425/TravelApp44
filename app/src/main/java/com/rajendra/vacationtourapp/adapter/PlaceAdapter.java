package com.rajendra.vacationtourapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.vacationtourapp.Views.DetailsPlaces;
import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.PlaceModel;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private Context context;
    private List<PlaceModel> placeModelList;

    public PlaceAdapter(Context context, List<PlaceModel> placeModelList) {
        this.context = context;
        this.placeModelList = placeModelList;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.places_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {

        holder.addressPlace.setText(placeModelList.get(position).getAddressName());
        holder.placeName.setText(placeModelList.get(position).getPlaceName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, DetailsPlaces.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return placeModelList.size();
    }

    public static final class PlaceViewHolder extends RecyclerView.ViewHolder{

        private ImageView placeImage;
        private TextView placeName, addressPlace;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            addressPlace = itemView.findViewById(R.id.address_place);
        }
    }
}