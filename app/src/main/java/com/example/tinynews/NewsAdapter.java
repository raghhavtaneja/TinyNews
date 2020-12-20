package com.example.tinynews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>  {
    private Context mContext;
    private ArrayList<News> mNewsItemsList;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int postition);
        void onShareButtonClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener=listener;
    }

    public NewsAdapter(Context context,ArrayList<News> items) {
        mNewsItemsList=items;
        mContext=context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        News currentNews = mNewsItemsList.get(position);
        holder.mNewsTitle.setText(currentNews.getTitle());
        holder.mNewsSource.setText(currentNews.getSource());
        Glide.with(mContext).load(currentNews.getImageUrl()).into(holder.mNewsImage);
    }

    @Override
    public int getItemCount() {
        return mNewsItemsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView mNewsTitle;
        public TextView mNewsSource;
        public ImageView mNewsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNewsTitle = itemView.findViewById(R.id.news_title);
            mNewsSource = itemView.findViewById(R.id.news_source);
            mNewsImage = itemView.findViewById(R.id.news_image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
            itemView.findViewById(R.id.share_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    mListener.onShareButtonClick(pos);
                }
            });

        }


    }


}
