package com.rajendra.vacationtourapp.ViewModels;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.Views.ReviewActivity;
import com.rajendra.vacationtourapp.model.FoodModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context foodContext;
    List<FoodModel> foodModelList;

    public FoodAdapter(Context foodContext, List<FoodModel> foodModelList) {
        this.foodContext = foodContext;
        this.foodModelList = foodModelList;
    }

    public void setDataHotel(List<FoodModel> foodModelList)
    {
        this.foodModelList = foodModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(foodContext).inflate(R.layout.food_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new FoodAdapter.FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {

        holder.foodName.setText(foodModelList.get(position).getName_food());
        holder.foodAddress.setText(foodModelList.get(position).getAddress_food());
        holder.foodPrice.setText(foodModelList.get(position).getPrice() + "đ");
        holder.foodRate.setText("Rate: " + foodModelList.get(position).getRate());
        Picasso.get().load(foodModelList.get(position).getImg_food().getImg1()).into(holder.foodImg);

        int id = foodModelList.get(position).getId_food();
        String ratefood = foodModelList.get(position).getRate();

        holder.btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(foodContext, ReviewActivity.class);
                i.putExtra("id_food", ""+ id);
                i.putExtra("rate", ratefood);
                i.putExtra("votes","" + foodModelList.size());
                foodContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public void filterListFood(List<FoodModel> filterList){
        foodModelList = filterList;
        notifyDataSetChanged();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImg;
        TextView foodName, foodAddress, foodPrice, foodRate;
        Button btnReview;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImg = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            foodAddress = itemView.findViewById(R.id.food_address);
            foodPrice = itemView.findViewById(R.id.food_price);
            foodRate = itemView.findViewById(R.id.food_rate);
            btnReview = itemView.findViewById(R.id.btnReview);
        }
    }
}
