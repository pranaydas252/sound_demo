package com.sound.demo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.realm.RealmResults;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
    private Context mCon;
    private List<LocationModal> data;
    private LayoutInflater inflater;
    private RealmResults<LocationModal> mData;

    public RecyclerViewAdapter(Context mCon, List<LocationModal> data) {
        this.mCon = mCon;
        this.data = data;
    }


    public void setData(RealmResults<LocationModal> newData) {
        mData = newData;
    }

    public void clear() {
        if (data != null) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LocationModal current = data.get(position);

        holder.latitude.setText(current.getLat_());
        holder.longitude.setText(current.getLong_());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView latitude;
        public TextView longitude;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.latitude = (TextView) itemView.findViewById(R.id.latitude);
            this.longitude = (TextView) itemView.findViewById(R.id.longitude);
        }
    }
}
