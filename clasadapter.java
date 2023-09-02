package com.example.advncd;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.advncd.databinding.Notification2sampleBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class clasadapter extends RecyclerView.Adapter<clasadapter.viewholder> {
ArrayList<clasmodel> list;
Context context;

    public clasadapter(ArrayList<clasmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        clasmodel clasmodel= list.get(position) ;
        String type = clasmodel.getType();
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(clasmodel.getClasid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        postmodel postmodel = snapshot.getValue(postmodel.class);
                        Picasso.get().load(postmodel.getPostImage())
                                .placeholder(R.drawable.profileuser)
                                .into(holder.binding.profileImg);
                        holder.binding.notification.setText(postmodel.getPostdescription());
                        if(type.equals("buy")){
                            holder.binding.notification.setText(  "buy");
                        } else  {
                            holder.binding.notification.setText( "posted this course");
                        }
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class viewholder extends RecyclerView.ViewHolder{
        Notification2sampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding =Notification2sampleBinding.bind(itemView);
        }
    }

}
