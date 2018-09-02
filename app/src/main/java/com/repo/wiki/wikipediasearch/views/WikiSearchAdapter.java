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
import com.repo.wiki.wikipediasearch.utils.GlideApp;

import java.util.ArrayList;

public class WikiSearchAdapter extends RecyclerView.Adapter<WikiSearchAdapter.ViewHolder> {

    private ArrayList<Pages> pages;

    WikiSearchAdapter() {
        pages = new ArrayList<>();
    }

    public void setItems(ArrayList<Pages> pages) {
        this.pages.clear();
        this.pages.addAll(pages);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wiki_search, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
