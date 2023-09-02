package com.example.advncd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advncd.databinding.AppBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.viewHolder> {
  ArrayList<appmodel>lists;
  Context context;

    public AppAdapter(ArrayList<appmodel> list, Context context) {
        this.lists = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AppAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.app,parent,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppAdapter.viewHolder holder, int position) {
appmodel appmodel= lists.get(position);
        Picasso.get().load(appmodel.getPhoto())
                .into(holder.binding.Aphoto);
        holder.binding.Atitle.setText(appmodel.getTopic());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        AppBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AppBinding.bind(itemView);
        }
    }
}
