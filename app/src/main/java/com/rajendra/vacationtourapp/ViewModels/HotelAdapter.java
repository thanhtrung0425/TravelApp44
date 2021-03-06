package com.rajendra.vacationtourapp.ViewModels;

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
import com.rajendra.vacationtourapp.Views.DetailsActivity;
import com.rajendra.vacationtourapp.model.HotelModel;
import com.squareup.picasso.Picasso;


import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private Context hotelcontext;
    private List<HotelModel> HotelModelList;

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
        if (!HotelModelList.get(position).getImg_hotel().getImg1().isEmpty())
            Picasso.get().load(HotelModelList.get(position).getImg_hotel().getImg1()).into(holder.hotelImage);
        else if (!HotelModelList.get(position).getImg_hotel().getImg2().isEmpty())
            Picasso.get().load(HotelModelList.get(position).getImg_hotel().getImg2()).into(holder.hotelImage);
        else if (!HotelModelList.get(position).getImg_hotel().getImg3().isEmpty())
            Picasso.get().load(HotelModelList.get(position).getImg_hotel().getImg3()).into(holder.hotelImage);
        int id = HotelModelList.get(position).getId_hotel();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(hotelcontext, DetailsActivity.class);
                i.putExtra("keys", "hotel");
                i.putExtra("id_item","" + id);
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

            hotelImage = itemView.findViewById(R.id.food_image);
            hotelName = itemView.findViewById(R.id.food_name);
            addressHotel = itemView.findViewById(R.id.food_address);
            hotelPrice = itemView.findViewById(R.id.food_price);
        }
    }
}
