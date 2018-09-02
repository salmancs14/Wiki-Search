package com.repo.wiki.wikipediasearch.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.repo.wiki.wikipediasearch.R;
import com.repo.wiki.wikipediasearch.entity.Pages;
import com.repo.wiki.wikipediasearch.interfaces.WikiSearchAdapterListener;
import com.repo.wiki.wikipediasearch.utils.GlideApp;

import java.util.ArrayList;

public class WikiSearchAdapter extends RecyclerView.Adapter<WikiSearchAdapter.ViewHolder> {

    private ArrayList<Pages> pages;
    private WikiSearchAdapterListener wikiSearchAdapterListener;

    WikiSearchAdapter(WikiSearchAdapterListener wikiSearchAdapterListener) {
        pages = new ArrayList<>();
        this.wikiSearchAdapterListener = wikiSearchAdapterListener;
    }

    public void setItems(ArrayList<Pages> pages) {
        this.pages.clear();
        if(pages != null) {
            this.pages.addAll(pages);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wiki_search, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.titleTextView.setText(pages.get(position).title);
        if (pages.get(position).terms.description.size() > 0) {
            holder.description.setText(pages.get(position).terms.description.get(0));
        }
        if (!TextUtils.isEmpty(pages.get(position).thumbnail.source)) {
            GlideApp
                    .with(holder.itemView.getContext())
                    .load(pages.get(position).thumbnail.source)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wikiSearchAdapterListener.onWikiItemClick(pages.get(holder.getAdapterPosition()).title);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView description;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.search_icon);
            titleTextView = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
    }
}
