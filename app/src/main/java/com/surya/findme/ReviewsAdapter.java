package com.surya.findme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Surya on 19-05-2017.
 */

class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    public ReviewsAdapter(Context context) {
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
