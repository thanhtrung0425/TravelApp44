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

import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.Views.DetailsPlaces;
import com.rajendra.vacationtourapp.model.HotelModel;
import com.rajendra.vacationtourapp.model.PlaceModel;
import com.squareup.picasso.Picasso;


import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private Context hotelcontext;
    List<HotelModel> HotelModelList;

    public HotelAdapter(Context hotelcontext, List<HotelModel> hotelModelList) {
        this.hotelcontext = hotelcontext;
        this.HotelModelList = hotelModelList;
    }

    public void setDataHotel(List<HotelModel> HotelModelList)
    {
        this.HotelModelList = HotelModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotelAdapter.HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(hotelcontext).inflate(R.layout.hotels_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new HotelAdapter.HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.HotelViewHolder holder, int position) {

        holder.addressHotel.setText(HotelModelList.get(position).getAddress_hotel());
        holder.hotelName.setText(HotelModelList.get(position).getName_hotel());
        holder.hotelPrice.setText("Price: " + HotelModelList.get(position).getPrice() + "đ");
        Picasso.get().load(HotelModelList.get(position).getImg_hotel().getImg1()).into(holder.hotelImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(hotelcontext, DetailsPlaces.class);
                hotelcontext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return HotelModelList.size();
    }

    public void filterListHotel(List<HotelModel> filterList){
        HotelModelList = filterList;
        notifyDataSetChanged();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {

        ImageView hotelImage;
        TextView hotelName, addressHotel, hotelPrice;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);

            hotelImage = itemView.findViewById(R.id.hotel_image);
            hotelName = itemView.findViewById(R.id.hotel_name);
            addressHotel = itemView.findViewById(R.id.address_hotel);
            hotelPrice = itemView.findViewById(R.id.price_hotel);
        }
    }
}
