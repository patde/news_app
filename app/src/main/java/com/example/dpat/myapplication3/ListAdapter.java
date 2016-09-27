package com.example.dpat.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Devang on 2016-09-25.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    ArrayList<FeedItem> feedItems;
    Context context;
    public ListAdapter(Context context, ArrayList<FeedItem> feedItems){
        this.context = context;
        this.feedItems = feedItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row_news_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FeedItem current = feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Date.setText(current.getPubDate());
        holder.Author.setText(current.getAuthor());
/*
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsInformation.class);
                intent.putExtra("Link", current.getLink());
                context.startActivity(intent);
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Date, Author;
        ImageView Thumbnail;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.title_text);
            Date = (TextView) itemView.findViewById(R.id.date_text);
            Author = (TextView) itemView.findViewById(R.id.author_text);

        }
    }
}
