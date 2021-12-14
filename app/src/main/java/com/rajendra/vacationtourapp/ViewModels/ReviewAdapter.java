package com.rajendra.vacationtourapp.ViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rajendra.vacationtourapp.R;
import com.rajendra.vacationtourapp.model.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context reviewContext;
    List<Review> reviewList;

    public ReviewAdapter(Context reviewContext, List<Review> reviewList) {
        this.reviewContext = reviewContext;
        this.reviewList = reviewList;
    }

    public void setDataReview(List<Review> reviewList)
    {
        this.reviewList = reviewList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(reviewContext).inflate(R.layout.review_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        holder.txtNameReview.setText(reviewList.get(position).getNamePerson());
        holder.txtRate.setText(reviewList.get(position).getRate_review());
        holder.txtReview.setText(reviewList.get(position).getReview());

        if (reviewList.get(position).getImage_reviewer() != null && !reviewList.get(position).getImage_reviewer().equals(""))
            Picasso.get().load(reviewList.get(position).getImage_reviewer()).into(holder.img_reviewer);
        else
            holder.img_reviewer.setImageResource(R.drawable.boy_avatar);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        ImageView img_reviewer;
        TextView txtNameReview, txtRate, txtReview;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            img_reviewer = itemView.findViewById(R.id.reviewer_image);
            txtNameReview = itemView.findViewById(R.id.txtNameReview);
            txtRate = itemView.findViewById(R.id.txtRateReview);
            txtReview = itemView.findViewById(R.id.txtReview);
        }
    }
}
