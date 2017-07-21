package sber.tech.habr.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sber.tech.habr.R;
import sber.tech.habr.models.HabrItem;

public class HabrItemsAdapter extends RecyclerView.Adapter<HabrItemsAdapter.ViewHolder> {
    private ArrayList<HabrItem> mDataset;

    public HabrItemsAdapter(ArrayList<HabrItem> items) {
        mDataset = items;
    }

    public void updateDataset(ArrayList<HabrItem> items) {
        mDataset = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habritem_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HabrItem hItem = mDataset.get(position);
        holder.mLink.setText(hItem.getLink());
        holder.mTitle.setText(hItem.getTitle());

        holder.mLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = holder.mLink.getText().toString();
                if (url!=null) {
                    Uri uri = Uri.parse(url);
                    Context context = holder.mView.getContext();
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mLink;
        public final TextView mTitle;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLink = (TextView) view.findViewById(R.id.link);
            mTitle = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public void clear() {
        int size = this.mDataset.size();
        this.mDataset.clear();
        notifyItemRangeRemoved(0, size);
    }
}
